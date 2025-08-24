package io.libcodec.json;

import io.libcodec.CodecContext;
import io.libcodec.CodecException;
import io.libcodec.json.util.IOUtils;
import io.libcodec.json.util.NumberUtils;
import io.libcodec.json.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static io.libcodec.json.util.JDKUtils.STRING_CODER;
import static io.libcodec.json.util.JDKUtils.STRING_VALUE;

/**
 * JSON generator implementation that generates objects to UTF-8 byte arrays.
 */
final class JSONGeneratorUTF8
        extends JSONGenerator {
    byte[] bytes;
    private final long byteVectorQuote;

    JSONGeneratorUTF8(long features) {
        super(features);
        this.bytes = new byte[1024];
        this.byteVectorQuote = this.useSingleQuote ? ~0x2727_2727_2727_2727L : ~0x2222_2222_2222_2222L;
    }

    @Override
    public void write(Object object, CodecContext context) throws CodecException {
        super.write(object, context);
    }

    @Override
    public JSONGenerator objectStart() {
        if (++level > maxLevel) {
            overflowLevel();
        }
        startObject = true;
        int off = this.off;
        int minCapacity = off + 3 + pretty * level;
        byte[] bytes = this.bytes;
        if (minCapacity > bytes.length) {
            bytes = grow(minCapacity);
        }
        bytes[off++] = '{';

        if (pretty != PRETTY_NON) {
            off = indent(bytes, off);
        }
        this.off = off;
        return this;
    }

    @Override
    public JSONGenerator objectEnd() {
        level--;
        int off = this.off;
        int minCapacity = off + 1 + (pretty == 0 ? 0 : pretty * level + 1);
        byte[] bytes = this.bytes;
        if (minCapacity > bytes.length) {
            bytes = grow(minCapacity);
        }
        if (pretty != PRETTY_NON) {
            off = indent(bytes, off);
        }

        bytes[off] = '}';
        this.off = off + 1;
        startObject = false;
        return this;
    }

    @Override
    public final JSONGenerator writeComma() {
        startObject = false;
        int off = this.off;
        int minCapacity = off + 2 + pretty * level;
        byte[] bytes = this.bytes;
        if (minCapacity > bytes.length) {
            bytes = grow(minCapacity);
        }
        bytes[off++] = ',';
        if (pretty != PRETTY_NON) {
            off = indent(bytes, off);
        }
        this.off = off;
        return this;
    }

    @Override
    public final JSONGenerator writeColon() {
        int off = this.off;
        grow1(off)[off] = ':';
        this.off = off + 1;
        return this;
    }

    @Override
    public final JSONGenerator writeInt(int i) {
        boolean writeAsString = (features & MASK_WRITE_NON_STRING_VALUE_AS_STRING) != 0;

        int off = this.off;
        int minCapacity = off + 13;
        byte[] bytes = this.bytes;
        if (minCapacity > bytes.length) {
            bytes = grow(minCapacity);
        }
        if (writeAsString) {
            bytes[off++] = (byte) quote;
        }
        off = IOUtils.writeInt32(bytes, off, i);
        if (writeAsString) {
            bytes[off++] = (byte) quote;
        }
        this.off = off;
        return this;
    }

    @Override
    public final JSONGenerator writeLong(long i) {
        int off = this.off;
        int minCapacity = off + 23;
        byte[] bytes = this.bytes;
        if (minCapacity > bytes.length) {
            bytes = grow(minCapacity);
        }
        boolean writeAsString = isWriteAsString(i, features);
        if (writeAsString) {
            bytes[off++] = (byte) quote;
        }
        off = IOUtils.writeInt64(bytes, off, i);
        if (writeAsString) {
            bytes[off++] = (byte) quote;
        } else if ((features & MASK_WRITE_CLASS_NAME) != 0
                && (features & MASK_NOT_WRITE_NUMBER_CLASS_NAME) == 0
                && i >= Integer.MIN_VALUE && i <= Integer.MAX_VALUE
        ) {
            bytes[off++] = 'L';
        }
        this.off = off;
        return this;
    }

    @Override
    public JSONGenerator writeString(String str) {
        if (str == null) {
            writeStringNull();
            return this;
        }

        if (STRING_VALUE != null) {
            byte[] value = STRING_VALUE.apply(str);
            if (STRING_CODER.applyAsInt(str) == 0) {
                writeStringLatin1(value);
            } else {
                writeStringUTF16(value);
            }
        } else {
            writeStringJDK8(str);
        }
        return this;
    }

    public final void writeStringUTF16(byte[] value) {
        if (value == null) {
            writeStringNull();
            return;
        }

        int off = this.off;
        byte[] bytes = this.bytes;
        int minCapacity = off + value.length * 6 + 2;
        if (minCapacity > bytes.length) {
            bytes = grow(minCapacity);
        }
        this.off = StringUtils.writeUTF16(bytes, off, value, (byte) quote, features);
    }

    public final void writeStringLatin1(byte[] value) {
        if ((features & MASK_BROWSER_SECURE) != 0) {
            writeStringLatin1BrowserSecure(value);
            return;
        }

        byte quote = (byte) this.quote;
        if (StringUtils.escaped(value, quote, byteVectorQuote)) {
            writeStringEscaped(value);
            return;
        }

        int off = this.off;
        int minCapacity = off + value.length + 2;
        byte[] bytes = this.bytes;
        if (minCapacity > bytes.length) {
            bytes = grow(minCapacity);
        }
        this.off = StringUtils.writeLatin1(bytes, off, value, quote);
    }

    protected final void writeStringLatin1BrowserSecure(byte[] value) {
        final byte quote = (byte) this.quote;
        int i = 0;
        for (; i < value.length; i++) {
            byte c = value[i];
            if (c == quote || c == '\\' || c < ' ' || c == '<' || c == '>' || c == '(' || c == ')') {
                break;
            }
        }

        int off = this.off;
        if (i == value.length) {
            int minCapacity = off + value.length + 2;
            byte[] bytes = this.bytes;
            if (minCapacity > bytes.length) {
                bytes = grow(minCapacity);
            }
            bytes[off] = quote;
            System.arraycopy(value, 0, bytes, off + 1, value.length);
            off += value.length + 1;
            bytes[off] = quote;
            this.off = off + 1;
            return;
        }
        writeStringEscaped(value);
    }

    protected final void writeStringEscaped(byte[] values) {
        int minCapacity = off + values.length * 6 + 2;
        byte[] bytes = this.bytes;
        if (minCapacity > bytes.length) {
            bytes = grow(minCapacity);
        }
        this.off = StringUtils.writeLatin1Escaped(bytes, off, values, (byte) quote, features);
    }

    private int indent(byte[] bytes, int off) {
        bytes[off] = '\n';
        int toIndex = off + 1 + pretty * level;
        Arrays.fill(bytes, off + 1, toIndex, pretty == PRETTY_TAB ? (byte) '\t' : (byte) ' ');
        return toIndex;
    }

    private byte[] grow(int minCapacity) {
        grow0(minCapacity);
        return bytes;
    }

    private byte[] grow1(int off) {
        byte[] bytes = this.bytes;
        if (off == bytes.length) {
            bytes = grow(off + 1);
        }
        return bytes;
    }

    private void grow0(int minCapacity) {
        // minCapacity is usually close to size, so this is a win:
        bytes = Arrays.copyOf(bytes, newCapacity(minCapacity, bytes.length));
    }

    @Override
    public final String toString() {
        return new String(bytes, 0, off, StandardCharsets.UTF_8);
    }

    private void writeStringJDK8(String str) {
        char[] chars = str.toCharArray();

        int off = this.off;
        // ensureCapacity
        int minCapacity = off
                + chars.length * 3 // utf8 3 bytes
                + 2;

        if (escapeNoneAscii || browserSecure) {
            minCapacity += chars.length * 3;
        }

        byte[] bytes = this.bytes;
        if (minCapacity > bytes.length) {
            bytes = grow(minCapacity);
        }

        bytes[off++] = (byte) quote;

        int i = 0;
        for (; i < chars.length; i++) {
            char c0 = chars[i];
            if (c0 == quote
                    || c0 == '\\'
                    || c0 < ' '
                    || c0 > 0x007F
                    || (browserSecure
                    && (c0 == '<' || c0 == '>' || c0 == '(' || c0 == ')'))
            ) {
                break;
            }

            bytes[off++] = (byte) c0;
        }

        if (i == chars.length) {
            bytes[off] = (byte) quote;
            this.off = off + 1;
            return;
        }

        this.off = off;
        if (i < chars.length) {
            writeStringEscapedRest(chars, chars.length, browserSecure, escapeNoneAscii, i);
        }

        this.bytes[this.off++] = (byte) quote;
    }

    protected final void writeStringEscapedRest(
            char[] chars,
            int end,
            boolean browserSecure,
            boolean escapeNoneAscii,
            int i
    ) {
        int rest = chars.length - i;
        int minCapacity = off + rest * 6 + 2;
        byte[] bytes = this.bytes;
        if (minCapacity > bytes.length) {
            bytes = grow(minCapacity);
        }

        int off = this.off;
        for (; i < end; ++i) { // ascii none special fast write
            char ch = chars[i];
            if (ch <= 0x007F) {
                switch (ch) {
                    case '\\':
                    case '\n':
                    case '\r':
                    case '\f':
                    case '\b':
                    case '\t':
                        StringUtils.writeEscapedChar(bytes, off, ch);
                        off += 2;
                        break;
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 11:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                        StringUtils.writeU4Hex2(bytes, off, ch);
                        off += 6;
                        break;
                    case '<':
                    case '>':
                    case '(':
                    case ')':
                        if (browserSecure) {
                            StringUtils.writeU4HexU(bytes, off, ch);
                            off += 6;
                        } else {
                            bytes[off++] = (byte) ch;
                        }
                        break;
                    default:
                        if (ch == quote) {
                            bytes[off] = '\\';
                            bytes[off + 1] = (byte) quote;
                            off += 2;
                        } else {
                            bytes[off++] = (byte) ch;
                        }
                        break;
                }
            } else if (escapeNoneAscii) {
                StringUtils.writeU4HexU(bytes, off, ch);
                off += 6;
            } else if (ch >= '\uD800' && ch < ('\uDFFF' + 1)) { //  //Character.isSurrogate(c)
                final int uc;
                if (ch < '\uDBFF' + 1) { // Character.isHighSurrogate(c)
                    if (chars.length - i < 2) {
                        uc = -1;
                    } else {
                        char d = chars[i + 1];
                        // d >= '\uDC00' && d < ('\uDFFF' + 1)
                        if (d >= '\uDC00' && d < ('\uDFFF' + 1)) { // Character.isLowSurrogate(d)
                            uc = ((ch << 10) + d) + (0x010000 - ('\uD800' << 10) - '\uDC00'); // Character.toCodePoint(c, d)
                        } else {
//                            throw new JSONException("encodeUTF8 error", new MalformedInputException(1));
                            bytes[off++] = '?';
                            continue;
                        }
                    }
                } else {
                    //
                    // Character.isLowSurrogate(c)
                    bytes[off++] = '?';
                    continue;
//                        throw new JSONException("encodeUTF8 error", new MalformedInputException(1));
                }

                if (uc < 0) {
                    bytes[off++] = '?';
                } else {
                    bytes[off] = (byte) (0xf0 | ((uc >> 18)));
                    bytes[off + 1] = (byte) (0x80 | ((uc >> 12) & 0x3f));
                    bytes[off + 2] = (byte) (0x80 | ((uc >> 6) & 0x3f));
                    bytes[off + 3] = (byte) (0x80 | (uc & 0x3f));
                    off += 4;
                    i++; // 2 chars
                }
            } else if (ch > 0x07FF) {
                bytes[off] = (byte) (0xE0 | ((ch >> 12) & 0x0F));
                bytes[off + 1] = (byte) (0x80 | ((ch >> 6) & 0x3F));
                bytes[off + 2] = (byte) (0x80 | (ch & 0x3F));
                off += 3;
            } else {
                bytes[off] = (byte) (0xC0 | ((ch >> 6) & 0x1F));
                bytes[off + 1] = (byte) (0x80 | (ch & 0x3F));
                off += 2;
            }
        }
        this.off = off;
    }

    @Override
    public final void writeRaw(String str) {
        char[] chars = str.toCharArray();
        int off = this.off;
        int minCapacity = off
                + chars.length * 3; // utf8 3 bytes

        byte[] bytes = this.bytes;
        if (minCapacity > bytes.length) {
            bytes = grow(minCapacity);
        }
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if ((c >= 0x0001) && (c <= 0x007F)) {
                bytes[off++] = (byte) c;
            } else if (c > 0x07FF) {
                bytes[off] = (byte) (0xE0 | ((c >> 12) & 0x0F));
                bytes[off + 1] = (byte) (0x80 | ((c >> 6) & 0x3F));
                bytes[off + 2] = (byte) (0x80 | (c & 0x3F));
                off += 3;
            } else {
                bytes[off] = (byte) (0xC0 | ((c >> 6) & 0x1F));
                bytes[off + 1] = (byte) (0x80 | (c & 0x3F));
                off += 2;
            }
        }
        this.off = off;
    }

    @Override
    public void writeRaw(char c0, char c1) {
        if ((c0 | c1) > 128) {
            throw new JSONException("not support " + c0 + ", " + c1);
        }

        int off = this.off;
        byte[] bytes = this.bytes;
        if (off + 2 > bytes.length) {
            bytes = grow(off + 2);
        }
        bytes[off] = (byte) c0;
        bytes[off + 1] = (byte) c1;
        this.off = off + 2;
    }

    @Override
    public JSONGenerator writeBool(boolean value) {
        int minCapacity = off + 5;
        byte[] bytes = this.bytes;
        if (minCapacity > bytes.length) {
            bytes = grow(minCapacity);
        }
        int off = this.off;
        if ((features & MASK_WRITE_BOOLEAN_AS_NUMBER) != 0) {
            bytes[off++] = (byte) (value ? '1' : '0');
        } else {
            off = IOUtils.putBoolean(bytes, off, value);
        }
        this.off = off;
        return this;
    }

    @Override
    public final JSONGenerator writeFloat(float value) {
        boolean writeAsString = (features & MASK_WRITE_NON_STRING_VALUE_AS_STRING) != 0;

        int off = this.off;
        int minCapacity = off + 17;
        byte[] bytes = this.bytes;
        if (minCapacity > bytes.length) {
            bytes = grow(minCapacity);
        }

        if (writeAsString) {
            bytes[off++] = '"';
        }

        off = NumberUtils.writeFloat(bytes, off, value, true);

        if (writeAsString) {
            bytes[off++] = '"';
        }
        this.off = off;
        return this;
    }

    @Override
    public final JSONGenerator writeDouble(double value) {
        boolean writeAsString = (features & MASK_WRITE_NON_STRING_VALUE_AS_STRING) != 0;

        int off = this.off;
        int minCapacity = off + 26;
        byte[] bytes = this.bytes;
        if (minCapacity > bytes.length) {
            bytes = grow(minCapacity);
        }
        if (writeAsString) {
            bytes[off++] = '"';
        }

        off = NumberUtils.writeDouble(bytes, off, value, true);

        if (writeAsString) {
            bytes[off++] = '"';
        }
        this.off = off;

        return this;
    }
}
