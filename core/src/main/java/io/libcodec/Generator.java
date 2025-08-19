package io.libcodec;

/**
 * Generator interface for serializing objects
 */
public interface Generator {
    /**
     * Writes a string representation of an object
     * @param object the object to write
     * @param context the codec context
     * @throws CodecException if writing fails
     */
    void write(Object object, CodecContext context) throws CodecException;
}
