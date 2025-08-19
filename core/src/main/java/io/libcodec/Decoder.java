package io.libcodec;

/**
 * Decoder interface for deserializing objects
 * @param <T> the type of object to decode
 */
public interface Decoder<T> {
    /**
     * Decodes a string representation to an object
     * @param data the string to decode
     * @param clazz the class of the object to decode to
     * @return the decoded object
     * @throws CodecException if decoding fails
     */
    T decode(String data, Class<T> clazz) throws CodecException;
}
