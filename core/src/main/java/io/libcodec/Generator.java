package io.libcodec;

/**
 * Generator interface for serializing objects
 */
public abstract class Generator {
    protected int off;
    /**
     * Writes a string representation of an object
     * @param object the object to write
     * @param context the codec context
     * @throws CodecException if writing fails
     */
    public abstract void write(Object object, CodecContext context) throws CodecException;
}
