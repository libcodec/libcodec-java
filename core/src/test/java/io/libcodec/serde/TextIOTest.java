package io.libcodec.serde;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TextIO class.
 */
public class TextIOTest {
    @Test
    public void testWriteInt_positiveNumbers_byteArray() {
        byte[] buffer = new byte[32];

        // Test single digit
        int newOffset = TextIO.writeInt(5, buffer, 0);
        assertEquals(3, newOffset); // New offset should be 3 (0 + 3)
        assertEquals('5', buffer[0]);

        // Test two digits
        newOffset = TextIO.writeInt(42, buffer, 0);
        assertEquals(4, newOffset); // New offset should be 4 (0 + 4)
        assertEquals('4', buffer[0]);
        assertEquals('2', buffer[1]);

        // Test three digits
        newOffset = TextIO.writeInt(123, buffer, 0);
        assertEquals(5, newOffset); // New offset should be 5 (0 + 5)
        assertEquals('1', buffer[0]);
        assertEquals('2', buffer[1]);
        assertEquals('3', buffer[2]);

        // Test four digits
        newOffset = TextIO.writeInt(4567, buffer, 0);
        assertEquals(6, newOffset); // New offset should be 6 (0 + 6)
        assertEquals('4', buffer[0]);
        assertEquals('5', buffer[1]);
        assertEquals('6', buffer[2]);
        assertEquals('7', buffer[3]);

        // Test five digits
        newOffset = TextIO.writeInt(12345, buffer, 0);
        assertEquals(7, newOffset); // New offset should be 7 (0 + 7)
        assertEquals('1', buffer[0]);
        assertEquals('2', buffer[1]);
        assertEquals('3', buffer[2]);
        assertEquals('4', buffer[3]);
        assertEquals('5', buffer[4]);
    }

    @Test
    public void testWriteInt_negativeNumbers_byteArray() {
        byte[] buffer = new byte[32];

        // Test negative single digit
        int newOffset = TextIO.writeInt(-5, buffer, 0);
        assertEquals(4, newOffset); // New offset should be 4 (0 + 1 + 3)
        assertEquals('-', buffer[0]);
        assertEquals('5', buffer[1]);

        // Test negative multi-digit
        newOffset = TextIO.writeInt(-123, buffer, 0);
        assertEquals(6, newOffset); // New offset should be 6 (0 + 1 + 5)
        assertEquals('-', buffer[0]);
        assertEquals('1', buffer[1]);
        assertEquals('2', buffer[2]);
        assertEquals('3', buffer[3]);
    }

    @Test
    public void testWriteInt_edgeCases_byteArray() {
        byte[] buffer = new byte[32];

        // Test zero
        int newOffset = TextIO.writeInt(0, buffer, 0);
        assertEquals(3, newOffset); // New offset should be 3 (0 + 3)
        assertEquals('0', buffer[0]);

        // Test maximum integer value
        newOffset = TextIO.writeInt(Integer.MAX_VALUE, buffer, 0);
        assertEquals(12, newOffset); // New offset should be 12 (0 + 12)
        assertEquals("2147483647", new String(buffer, 0, 10));

        // Test minimum integer value
        newOffset = TextIO.writeInt(Integer.MIN_VALUE, buffer, 0);
        assertEquals(13, newOffset); // New offset should be 13 (0 + 13)
        assertEquals("-2147483648", new String(buffer, 0, 11));
    }

    @Test
    public void testWriteInt_withOffset_byteArray() {
        byte[] buffer = new byte[32];

        // Test writing with offset
        int newOffset = TextIO.writeInt(123, buffer, 5);
        assertEquals(10, newOffset); // New offset should be 10 (5 + 5)
        assertEquals('1', buffer[5]);
        assertEquals('2', buffer[6]);
        assertEquals('3', buffer[7]);
    }

    @Test
    public void testWriteInt_positiveNumbers_charArray() {
        char[] buffer = new char[32];

        // Test single digit
        int newOffset = TextIO.writeInt(5, buffer, 0);
        assertEquals(3, newOffset); // New offset should be 3 (0 + 3)
        assertEquals('5', buffer[0]);

        // Test two digits
        newOffset = TextIO.writeInt(42, buffer, 0);
        assertEquals(4, newOffset); // New offset should be 4 (0 + 4)
        assertEquals('4', buffer[0]);
        assertEquals('2', buffer[1]);

        // Test three digits
        newOffset = TextIO.writeInt(123, buffer, 0);
        assertEquals(5, newOffset); // New offset should be 5 (0 + 5)
        assertEquals('1', buffer[0]);
        assertEquals('2', buffer[1]);
        assertEquals('3', buffer[2]);

        // Test four digits
        newOffset = TextIO.writeInt(4567, buffer, 0);
        assertEquals(6, newOffset); // New offset should be 6 (0 + 6)
        assertEquals('4', buffer[0]);
        assertEquals('5', buffer[1]);
        assertEquals('6', buffer[2]);
        assertEquals('7', buffer[3]);

        // Test five digits
        newOffset = TextIO.writeInt(12345, buffer, 0);
        assertEquals(7, newOffset); // New offset should be 7 (0 + 7)
        assertEquals('1', buffer[0]);
        assertEquals('2', buffer[1]);
        assertEquals('3', buffer[2]);
        assertEquals('4', buffer[3]);
        assertEquals('5', buffer[4]);
    }

    @Test
    public void testWriteInt_negativeNumbers_charArray() {
        char[] buffer = new char[32];

        // Test negative single digit
        int newOffset = TextIO.writeInt(-5, buffer, 0);
        assertEquals(4, newOffset); // New offset should be 4 (0 + 1 + 3)
        assertEquals('-', buffer[0]);
        assertEquals('5', buffer[1]);

        // Test negative multi-digit
        newOffset = TextIO.writeInt(-123, buffer, 0);
        assertEquals(6, newOffset); // New offset should be 6 (0 + 1 + 5)
        assertEquals('-', buffer[0]);
        assertEquals('1', buffer[1]);
        assertEquals('2', buffer[2]);
        assertEquals('3', buffer[3]);
    }

    @Test
    public void testWriteInt_edgeCases_charArray() {
        char[] buffer = new char[32];

        // Test zero
        int newOffset = TextIO.writeInt(0, buffer, 0);
        assertEquals(3, newOffset); // New offset should be 3 (0 + 3)
        assertEquals('0', buffer[0]);

        // Test maximum integer value
        newOffset = TextIO.writeInt(Integer.MAX_VALUE, buffer, 0);
        assertEquals(12, newOffset); // New offset should be 12 (0 + 12)
        assertEquals("2147483647", new String(buffer, 0, 10));

        // Test minimum integer value
        newOffset = TextIO.writeInt(Integer.MIN_VALUE, buffer, 0);
        assertEquals(13, newOffset); // New offset should be 13 (0 + 13)
        assertEquals("-2147483648", new String(buffer, 0, 11));
    }

    @Test
    public void testWriteInt_withOffset_charArray() {
        char[] buffer = new char[32];

        // Test writing with offset
        int newOffset = TextIO.writeInt(123, buffer, 5);
        assertEquals(10, newOffset); // New offset should be 10 (5 + 5)
        assertEquals('1', buffer[5]);
        assertEquals('2', buffer[6]);
        assertEquals('3', buffer[7]);
    }
}
