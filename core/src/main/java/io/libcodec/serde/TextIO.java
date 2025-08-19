package io.libcodec.serde;

/**
 * Text I/O utility class for reading and writing text data.
 * Based on com.alibaba.fastjson2.util.IOUtils
 */
public class TextIO {
    // Precomputed digits arrays for fast integer to string conversion
    public static final int[] DIGITS_K_32 = new int[1024];
    public static final long[] DIGITS_K_64 = new long[1024];
    public static final short[] PACKED_DIGITS = new short[128];

    static {
        // Initialize DIGITS_K_32 and DIGITS_K_64
        for (int i = 0; i < 1000; i++) {
            int c0 = i < 10 ? 2 : i < 100 ? 1 : 0;
            int c1 = (i / 100) + '0';
            int c2 = ((i / 10) % 10) + '0';
            int c3 = i % 10 + '0';
            DIGITS_K_32[i] = c0 + (c1 << 8) + (c2 << 16) + (c3 << 24);
            long v = (c1 << 16) + (((long) c2) << 32) + (((long) c3) << 48);
            DIGITS_K_64[i] = c0 + v;
        }

        // Initialize PACKED_DIGITS
        for (int i = 0; i < 100; i++) {
            int q = i / 10;
            int r = i % 10;
            PACKED_DIGITS[i] = (short) (('0' + q) | (('0' + r) << 8));
        }
    }

    /**
     * Writes an int value to a byte array buffer.
     * Based on the writeInt32 method from com.alibaba.fastjson2.util.IOUtils
     *
     * @param buffer the byte array buffer to write to
     * @param offset the offset in the buffer to start writing
     * @param value the int value to write
     * @return the new offset after writing
     */
    public static int writeInt(byte[] buffer, int offset, int value) {
        long val = value;
        int off = offset;

        if (val < 0) {
            val = -val;
            buffer[off++] = '-';
        }

        int v, v1;
        if (val < 10000) {
            v = (int) val;
            if (v < 1000) {
                return writeInt3(buffer, off, v);
            } else {
                return writeInt4(buffer, off, v);
            }
        }

        long numValue = val;
        val = (int) ((numValue * 1759218605L) >> 44);  // numValue / 10000;
        v1 = (int) (numValue - val * 10000);
        if (val < 10000) {
            v = (int) val;
            if (v < 1000) {
                off = writeInt3(buffer, off, v);
                return writeInt4(buffer, off, v1);
            } else {
                return writeInt8(buffer, off, v, v1);
            }
        }

        numValue = val;
        val = (int) ((numValue * 1759218605L) >> 44);  // numValue / 10000;
        off = writeInt3(buffer, off, (int) val);
        return writeInt8(buffer, off, (int) (numValue - val * 10000), v1);
    }

    /**
     * Writes an int value to a char array buffer.
     * Based on the writeInt32 method from com.alibaba.fastjson2.util.IOUtils
     *
     * @param buffer the char array buffer to write to
     * @param offset the offset in the buffer to start writing
     * @param value the int value to write
     * @return the new offset after writing
     */
    public static int writeInt(char[] buffer, int offset, int value) {
        long val = value;
        int off = offset;

        if (val < 0) {
            val = -val;
            buffer[off++] = '-';
        }

        int v, v1;
        if (val < 10000) {
            v = (int) val;
            if (v < 1000) {
                return writeInt3(buffer, off, v);
            } else {
                return writeInt4(buffer, off, v);
            }
        }

        long numValue = val;
        val = (int) ((numValue * 1759218605L) >> 44);  // numValue / 10000;
        v1 = (int) (numValue - val * 10000);
        if (val < 10000) {
            v = (int) val;
            if (v < 1000) {
                off = writeInt3(buffer, off, v);
                return writeInt4(buffer, off, v1);
            } else {
                return writeInt8(buffer, off, v, v1);
            }
        }

        numValue = val;
        val = (int) ((numValue * 1759218605L) >> 44);  // numValue / 10000;
        off = writeInt3(buffer, off, (int) val);
        return writeInt8(buffer, off, (int) (numValue - val * 10000), v1);
    }

    // Helper methods based on fastjson2 implementation
    private static int writeInt3(byte[] buf, int off, int val) {
        int v = DIGITS_K_32[val & 0x3ff];
        buf[off] = (byte) (v >> 16);
        buf[off + 1] = (byte) (v >> 8);
        buf[off + 2] = (byte) v;
        return off + 3;
    }

    private static int writeInt3(char[] buf, int off, int val) {
        long v = DIGITS_K_64[val & 0x3ff];
        buf[off] = (char) (v >> 48);
        buf[off + 1] = (char) (v >> 32);
        buf[off + 2] = (char) (v >> 16);
        return off + 3;
    }

    private static int writeInt4(byte[] buf, int off, int v) {
        int v1 = (int) (v * 1374389535L >> 37); // v / 100;
        int v0 = v - v1 * 100;
        int v2 = PACKED_DIGITS[v1 & 0x7f] | (PACKED_DIGITS[v0 & 0x7f] << 16);
        buf[off] = (byte) (v2);
        buf[off + 1] = (byte) (v2 >> 8);
        buf[off + 2] = (byte) (v2 >> 16);
        buf[off + 3] = (byte) (v2 >> 24);
        return off + 4;
    }

    private static int writeInt4(char[] buf, int off, int v) {
        int v1 = (int) (v * 1374389535L >> 37); // v / 100;
        int v0 = v - v1 * 100;
        buf[off] = (char) (v1 / 10 + '0');
        buf[off + 1] = (char) (v1 % 10 + '0');
        buf[off + 2] = (char) (v0 / 10 + '0');
        buf[off + 3] = (char) (v0 % 10 + '0');
        return off + 4;
    }

    private static int writeInt8(byte[] buf, int off, int v1, int v2) {
        int r1 = (int) (v1 * 1374389535L >> 37); // v1 / 100;
        int r2 = (int) (v2 * 1374389535L >> 37); // v2 / 100;
        long v = (PACKED_DIGITS[r1 & 0x7f])
                | (PACKED_DIGITS[(v1 - r1 * 100) & 0x7f] << 16)
                | ((long) PACKED_DIGITS[r2 & 0x7f] << 32)
                | ((long) PACKED_DIGITS[(v2 - r2 * 100) & 0x7f] << 48);
        buf[off] = (byte) v;
        buf[off + 1] = (byte) (v >> 8);
        buf[off + 2] = (byte) (v >> 16);
        buf[off + 3] = (byte) (v >> 24);
        buf[off + 4] = (byte) (v >> 32);
        buf[off + 5] = (byte) (v >> 40);
        buf[off + 6] = (byte) (v >> 48);
        buf[off + 7] = (byte) (v >> 56);
        return off + 8;
    }

    private static int writeInt8(char[] buf, int off, int v1, int v2) {
        int r1 = (int) (v1 * 1374389535L >> 37); // v1 / 100;
        int r2 = (int) (v2 * 1374389535L >> 37); // v2 / 100;
        buf[off] = (char) (r1 / 10 + '0');
        buf[off + 1] = (char) (r1 % 10 + '0');
        buf[off + 2] = (char) ((v1 - r1 * 100) / 10 + '0');
        buf[off + 3] = (char) ((v1 - r1 * 100) % 10 + '0');
        buf[off + 4] = (char) (r2 / 10 + '0');
        buf[off + 5] = (char) (r2 % 10 + '0');
        buf[off + 6] = (char) ((v2 - r2 * 100) / 10 + '0');
        buf[off + 7] = (char) ((v2 - r2 * 100) % 10 + '0');
        return off + 8;
    }
}
