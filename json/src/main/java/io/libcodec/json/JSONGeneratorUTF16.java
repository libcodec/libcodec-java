package io.libcodec.json;

import io.libcodec.io.CharBuffer;
import io.libcodec.json.util.IOUtils;
import io.libcodec.json.util.NumberUtils;
import io.libcodec.json.util.StringUtils;

import java.util.Arrays;

import static io.libcodec.json.JSONGenerator.Feature.BrowserSecure;
import static io.libcodec.json.JSONGenerator.Feature.EscapeNoneAscii;
import static io.libcodec.json.JSONGenerator.Feature.WriteBooleanAsNumber;
import static io.libcodec.json.JSONGenerator.Feature.WriteNonStringValueAsString;

/**
 * JSON generator implementation that generates objects to UTF-16 byte arrays.
 */
public final class JSONGeneratorUTF16
        extends JSONGenerator
        implements CharBuffer {
    char[] chars;

    JSONGeneratorUTF16(long features) {
        super(features);
        this.chars = new char[1024];
    }

    @Override
    public JSONGenerator objectStart() {
        if (++level > maxLevel) {
            overflowLevel();
        }

        startObject = true;

        int off = this.off;
        char[] chars = this.chars;
        int minCapacity = off + 3 + pretty * level;
        if (minCapacity > chars.length) {
            chars = grow(minCapacity);
        }

        chars[off++] = (byte) '{';

        if (pretty != PRETTY_NON) {
            off = indent(chars, off);
        }
        this.off = off;
        return this;
    }

    @Override
    public final JSONGenerator writeComma() {
        startObject = false;
        int off = this.off;
        int minCapacity = off + 2 + pretty * level;
        char[] chars = this.chars;
        if (minCapacity > chars.length) {
            chars = grow(minCapacity);
        }

        chars[off++] = (byte) ',';
        if (pretty != PRETTY_NON) {
            off = indent(chars, off);
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

        boolean escapeNoneAscii = (features & EscapeNoneAscii.mask) != 0;
        boolean browserSecure = (features & BrowserSecure.mask) != 0;
        boolean escape = false;
        final char quote = this.quote;

        final int strlen = str.length();
        int minCapacity = off + strlen + 2;
        if (minCapacity >= chars.length) {
            grow(minCapacity);
        }

        for (int i = 0; i < strlen; i++) {
            char c = str.charAt(i);
            if (c == '\\'
                    || c == quote
                    || c < ' '
                    || (browserSecure && (c == '<' || c == '>' || c == '(' || c == ')'))
                    || (escapeNoneAscii && c > 0x007F)
            ) {
                escape = true;
                break;
            }
        }

        if (!escape) {
            int off = this.off;
            final char[] chars = this.chars;
            chars[off++] = quote;
            str.getChars(0, strlen, chars, off);
            off += strlen;
            chars[off] = quote;
            this.off = off + 1;
            return this;
        }

        writeStringEscape(str);
        return this;
    }

    final void ensureCapacityInternal(int minCapacity) {
        if (minCapacity > chars.length) {
            grow0(minCapacity);
        }
    }

    @Override
    public final JSONGenerator writeColon() {
        int off = this.off;
        char[] chars = this.chars;
        if (off == chars.length) {
            chars = grow(off + 1);
        }
        chars[off] = ':';
        this.off = off + 1;
        return this;
    }

    protected final void writeStringEscape(String str) {
        final int strlen = str.length();
        final char quote = this.quote;
        boolean escapeNoneAscii = (features & EscapeNoneAscii.mask) != 0;
        boolean browserSecure = (features & BrowserSecure.mask) != 0;

        int off = this.off;
        ensureCapacityInternal(off + strlen * 6 + 2);

        final char[] chars = this.chars;
        chars[off++] = quote;
        for (int i = 0; i < strlen; ++i) {
            char ch = str.charAt(i);
            switch (ch) {
                case '"':
                case '\'':
                    if (ch == quote) {
                        chars[off++] = '\\';
                    }
                    chars[off++] = ch;
                    break;
                case '\\':
                case '\r':
                case '\n':
                case '\b':
                case '\f':
                case '\t':
                    StringUtils.writeEscapedChar(chars, off, ch);
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
                    StringUtils.writeU4Hex2(chars, off, ch);
                    off += 6;
                    break;
                case '<':
                case '>':
                case '(':
                case ')':
                    if (browserSecure) {
                        StringUtils.writeU4HexU(chars, off, ch);
                        off += 6;
                    } else {
                        chars[off++] = ch;
                    }
                    break;
                default:
                    if (escapeNoneAscii && ch > 0x007F) {
                        StringUtils.writeU4HexU(chars, off, ch);
                        off += 6;
                    } else {
                        chars[off++] = ch;
                    }
                    break;
            }
        }
        chars[off] = quote;
        this.off = off + 1;
    }

    @Override
    public JSONGenerator objectEnd() {
        level--;
        int off = this.off;
        int minCapacity = off + 1 + (pretty == 0 ? 0 : pretty * level + 1);
        char[] chars = this.chars;
        if (minCapacity > chars.length) {
            chars = grow(minCapacity);
        }

        if (pretty != PRETTY_NON) {
            off = indent(chars, off);
        }

        chars[off] = (byte) '}';
        this.off = off + 1;
        startObject = false;
        return this;
    }

    private char[] grow(int minCapacity) {
        if (minCapacity > chars.length) {
            grow0(minCapacity);
        }
        return chars;
    }

    protected void grow0(int minCapacity) {
        chars = Arrays.copyOf(chars, newCapacity(minCapacity, chars.length));
    }

    private int indent(char[] chars, int off) {
        chars[off] = '\n';
        int toIndex = off + 1 + pretty * level;
        Arrays.fill(chars, off + 1, toIndex, pretty == PRETTY_TAB ? '\t' : ' ');
        return toIndex;
    }

    @Override
    public final String toString() {
        return new String(chars, 0, off);
    }

    @Override
    public final void writeRaw(String str) {
        int strlen = str.length();
        int off = this.off;
        char[] chars = this.chars;
        if (off + strlen > chars.length) {
            chars = grow(off + strlen);
        }
        str.getChars(0, strlen, chars, off);
        this.off = off + strlen;
    }

    @Override
    public final void writeRaw(char c0, char c1) {
        int off = this.off;
        char[] chars = this.chars;
        if (off + 2 > chars.length) {
            chars = grow(off + 2);
        }
        chars[off] = c0;
        chars[off + 1] = c1;
        this.off = off + 2;
    }

    @Override
    public final JSONGenerator writeInt(int i) {
        boolean writeAsString = (features & MASK_WRITE_NON_STRING_VALUE_AS_STRING) != 0;

        int off = this.off;
        int minCapacity = off + 13;
        char[] chars = this.chars;
        if (minCapacity > chars.length) {
            chars = grow(minCapacity);
        }

        if (writeAsString) {
            chars[off++] = quote;
        }
        off = IOUtils.writeInt32(chars, off, i);
        if (writeAsString) {
            chars[off++] = quote;
        }
        this.off = off;
        return this;
    }

    @Override
    public JSONGenerator writeLong(long i) {
        int off = this.off;
        int minCapacity = off + 23;
        char[] chars = this.chars;
        if (minCapacity > chars.length) {
            chars = grow(minCapacity);
        }
        boolean writeAsString = isWriteAsString(i, features);
        if (writeAsString) {
            chars[off++] = quote;
        }
        off = IOUtils.writeInt64(chars, off, i);
        if (writeAsString) {
            chars[off++] = quote;
        } else if ((features & MASK_WRITE_CLASS_NAME) != 0
                && (features & MASK_NOT_WRITE_NUMBER_CLASS_NAME) == 0
                && i >= Integer.MIN_VALUE && i <= Integer.MAX_VALUE
        ) {
            chars[off++] = 'L';
        }
        this.off = off;
        return this;
    }

    @Override
    public JSONGenerator writeBool(boolean value) {
        int minCapacity = off + 5;
        char[] chars = this.chars;
        if (minCapacity > chars.length) {
            chars = grow(minCapacity);
        }

        int off = this.off;
        if ((features & WriteBooleanAsNumber.mask) != 0) {
            chars[off++] = value ? '1' : '0';
        } else {
            if (!value) {
                chars[off] = 'f';
                chars[off + 1] = 'a';
                chars[off + 2] = 'l';
                chars[off + 3] = 's';
                chars[off + 4] = 'e';
                off += 5;
            } else {
                chars[off] = 't';
                chars[off + 1] = 'r';
                chars[off + 2] = 'u';
                chars[off + 3] = 'e';
                off += 4;
            }
        }
        this.off = off;
        return this;
    }

    @Override
    public final JSONGenerator writeFloat(float value) {
        boolean writeAsString = (features & WriteNonStringValueAsString.mask) != 0;

        int off = this.off;
        int minCapacity = off + 15;
        if (writeAsString) {
            minCapacity += 2;
        }

        char[] chars = this.chars;
        if (minCapacity > chars.length) {
            chars = grow(minCapacity);
        }

        if (writeAsString) {
            chars[off++] = '"';
        }

        off = NumberUtils.writeFloat(chars, off, value, true);

        if (writeAsString) {
            chars[off++] = '"';
        }
        this.off = off;
        return this;
    }

    @Override
    public final JSONGenerator writeDouble(double value) {
        boolean writeAsString = (features & WriteNonStringValueAsString.mask) != 0;

        int off = this.off;
        int minCapacity = off + 24;
        if (writeAsString) {
            minCapacity += 2;
        }

        char[] chars = this.chars;
        if (minCapacity > chars.length) {
            chars = grow(minCapacity);
        }

        if (writeAsString) {
            chars[off++] = '"';
        }

        off = NumberUtils.writeDouble(chars, off, value, true);

        if (writeAsString) {
            chars[off++] = '"';
        }
        this.off = off;
        return this;
    }

    public JSONGenerator writeNull() {
        int off = this.off;
        char[] chars = grow(off + 4);
        this.off = IOUtils.writeNull(chars, off);
        return this;
    }

    @Override
    public CharBuffer getBuffer() {
        return this;
    }

    @Override
    public char[] ensureCapacity(int minCapacity) {
        if (minCapacity > chars.length) {
            grow0(minCapacity);
        }
        return chars;
    }

    public void writeRaw(char[] raw) {
        writeRaw(raw, 0, raw.length);
    }

    public void writeRaw(char[] raw, int coff, int strlen) {
        int off = this.off;
        char[] chars = ensureCapacity(off + strlen);
        System.arraycopy(raw, coff, chars, off, strlen);
        this.off = off + strlen;
    }

    public JSONGeneratorUTF16 writeNameRaw(char[] name) {
        return writeNameRaw(name, 0, name.length);
    }

    public JSONGeneratorUTF16 writeNameRaw(char[] name, int coff, int len) {
        int off = this.off;
        int minCapacity = off + len + 2 + pretty * level;
        char[] chars = this.chars;
        if (minCapacity > chars.length) {
            chars = grow(minCapacity);
        }

        if (startObject) {
            startObject = false;
        } else {
            chars[off++] = ',';
        }
        System.arraycopy(name, coff, chars, off, len);
        this.off = off + len;
        return this;
    }
}
