package io.libcodec.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextIOTest {
    @Test
    void testWriteIntPositiveNumbers() {
        byte[] buf = new byte[20];

        // Test single digit
        int pos = TextIO.writeInt(buf, 0, 5);
        assertEquals(1, pos);
        assertArrayEquals("5".getBytes(), java.util.Arrays.copyOf(buf, pos));

        // Test two digits
        pos = TextIO.writeInt(buf, 0, 42);
        assertEquals(2, pos);
        assertArrayEquals("42".getBytes(), java.util.Arrays.copyOf(buf, pos));

        // Test three digits
        pos = TextIO.writeInt(buf, 0, 789);
        assertEquals(3, pos);
        assertArrayEquals("789".getBytes(), java.util.Arrays.copyOf(buf, pos));

        // Test four digits
        pos = TextIO.writeInt(buf, 0, 5432);
        assertEquals(4, pos);
        assertArrayEquals("5432".getBytes(), java.util.Arrays.copyOf(buf, pos));

        // Test larger number
        pos = TextIO.writeInt(buf, 0, 123456);
        assertEquals(6, pos);
        assertArrayEquals("123456".getBytes(), java.util.Arrays.copyOf(buf, pos));
    }

    @Test
    void testWriteIntNegativeNumbers() {
        byte[] buf = new byte[20];

        // Test negative single digit
        int pos = TextIO.writeInt(buf, 0, -7);
        assertEquals(2, pos);
        assertArrayEquals("-7".getBytes(), java.util.Arrays.copyOf(buf, pos));

        // Test negative multi-digit
        pos = TextIO.writeInt(buf, 0, -456);
        assertEquals(4, pos);
        assertArrayEquals("-456".getBytes(), java.util.Arrays.copyOf(buf, pos));

        // Test negative larger number
        pos = TextIO.writeInt(buf, 0, -987654);
        assertEquals(7, pos);
        assertArrayEquals("-987654".getBytes(), java.util.Arrays.copyOf(buf, pos));
    }

    @Test
    void testWriteIntZero() {
        byte[] buf = new byte[20];
        int pos = TextIO.writeInt(buf, 0, 0);
        assertEquals(1, pos);
        assertArrayEquals("0".getBytes(), java.util.Arrays.copyOf(buf, pos));
    }

    @Test
    void testWriteIntMinValue() {
        byte[] buf = new byte[20];
        int pos = TextIO.writeInt(buf, 0, Integer.MIN_VALUE);
        assertEquals(11, pos); // "-2147483648" has 11 characters
        assertArrayEquals("-2147483648".getBytes(), java.util.Arrays.copyOf(buf, pos));
    }

    @Test
    void testWriteIntMaxValue() {
        byte[] buf = new byte[20];
        int pos = TextIO.writeInt(buf, 0, Integer.MAX_VALUE);
        assertEquals(10, pos); // "2147483647" has 10 characters
        assertArrayEquals("2147483647".getBytes(), java.util.Arrays.copyOf(buf, pos));
    }

    @Test
    void testWriteIntWithOffset() {
        byte[] buf = new byte[30];
        // Fill buffer with some data first
        for (int i = 0; i < 5; i++) {
            buf[i] = 'X';
        }

        int pos = TextIO.writeInt(buf, 5, 12345);
        assertEquals(10, pos); // 5 (offset) + 5 (digits)

        // Check that prefix is unchanged
        assertArrayEquals("XXXXX".getBytes(), java.util.Arrays.copyOfRange(buf, 0, 5));
        // Check that number is written correctly
        assertArrayEquals("12345".getBytes(), java.util.Arrays.copyOfRange(buf, 5, 10));
    }
}
