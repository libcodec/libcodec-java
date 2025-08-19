package io.libcodec;

/**
 * Encoder interface for serializing objects
 * @param <T> the type of object to encode
 */
public interface Encoder<T> {
    /**
     * Encodes an object to a string representation
     * @param object the object to encode
     * @return the encoded string
     * @throws CodecException if encoding fails
     */
    String encode(T object) throws CodecException;
}
