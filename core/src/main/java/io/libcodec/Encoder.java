package io.libcodec;

/**
 * Encoder interface for serializing objects
 */
public interface Encoder {
    /**
     * Encodes an object to a string representation
     * @param object the object to encode
     * @param context the codec context
     * @return the encoded string
     * @throws CodecException if encoding fails
     */
    String encode(Object object, CodecContext context) throws CodecException;
}
