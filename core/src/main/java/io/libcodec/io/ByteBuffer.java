package io.libcodec.io;

public interface ByteBuffer extends Buffer {
    byte[] ensureCapacity(int capacity);
}
