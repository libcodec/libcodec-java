package io.libcodec.io;

public interface CharBuffer extends Buffer {
    char[] ensureCapacity(int capacity);
}
