package io.libcodec;

import io.libcodec.io.Buffer;

/**
 * Generator interface for serializing objects
 */
public abstract class Generator {
    protected int off;
    protected CodecContext context;

    /**
     * Writes a string representation of an object
     * @param object the object to write
     * @throws CodecException if writing fails
     */
    public abstract Generator writeObject(Object object);

    public int getOffset() {
        return off;
    }

    public void setOffset(int offset) {
        off = offset;
    }

    public abstract Buffer getBuffer();
}
