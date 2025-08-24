package io.libcodec.io;

/**
 * TextIO provides utility methods for text-based I/O operations.
 */
public class TextIO {
    private static final int[] DIGITS = new int[1000];
    private static final byte MINUS = '-';
    private static final byte[] MIN_INT = "-2147483648".getBytes();

    static {
        for (int i = 0; i < DIGITS.length; i++) {
            DIGITS[i] = (i < 10 ? (2 << 24) : i < 100 ? (1 << 24) : 0)
                    + (((i / 100) + '0') << 16)
                    + ((((i / 10) % 10) + '0') << 8)
                    + i % 10 + '0';
        }
    }

    /**
     * Writes an integer value to a byte array at the specified offset.
     * The integer is converted to its string representation and written to the buffer.
     *
     * @param buf    the byte array buffer to write to
     * @param pos the offset in the buffer where to start writing
     * @param value  the integer value to write
     * @return the updated offset after writing the value (offset + number of bytes written)
     */
    public static int writeInt(byte[] buf, int pos, int value) {
        int i;
        if (value < 0) {
            if (value == Integer.MIN_VALUE) {
                for (int x = 0; x < MIN_INT.length; x++) {
                    buf[pos + x] = MIN_INT[x];
                }
                return pos + MIN_INT.length;
            }
            i = -value;
            buf[pos++] = MINUS;
        } else {
            i = value;
        }
        final int q1 = i / 1000;
        if (q1 == 0) {
            pos += writeFirstBuf(buf, DIGITS[i], pos);
            return pos;
        }
        final int r1 = i - q1 * 1000;
        final int q2 = q1 / 1000;
        if (q2 == 0) {
            final int v1 = DIGITS[r1];
            final int v2 = DIGITS[q1];
            int off = writeFirstBuf(buf, v2, pos);
            writeBuf(buf, v1, pos + off);
            return pos + 3 + off;
        }
        final int r2 = q1 - q2 * 1000;
        final int q3 = q2 / 1000;
        final int v1 = DIGITS[r1];
        final int v2 = DIGITS[r2];
        if (q3 == 0) {
            pos += writeFirstBuf(buf, DIGITS[q2], pos);
        } else {
            final int r3 = q2 - q3 * 1000;
            buf[pos++] = (byte) (q3 + '0');
            writeBuf(buf, DIGITS[r3], pos);
            pos += 3;
        }
        writeBuf(buf, v2, pos);
        writeBuf(buf, v1, pos + 3);
        return pos + 6;
    }

    private static int writeFirstBuf(final byte[] buf, final int v, int pos) {
        final int start = v >> 24;
        if (start == 0) {
            buf[pos++] = (byte) (v >> 16);
            buf[pos++] = (byte) (v >> 8);
        } else if (start == 1) {
            buf[pos++] = (byte) (v >> 8);
        }
        buf[pos] = (byte) v;
        return 3 - start;
    }

    private static void writeBuf(final byte[] buf, final int v, int pos) {
        buf[pos] = (byte) (v >> 16);
        buf[pos + 1] = (byte) (v >> 8);
        buf[pos + 2] = (byte) v;
    }
}
