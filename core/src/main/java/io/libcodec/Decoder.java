package io.libcodec;

/**
 * Decoder interface for deserializing objects
 */
public interface Decoder {
    /**
     * Decodes a string representation to an object
     * @param data the string to decode
     * @param clazz the class of the object to decode to
     * @return the decoded object
     * @throws CodecException if decoding fails
     */
    <T> T decode(String data, Class<T> clazz) throws CodecException;
}
