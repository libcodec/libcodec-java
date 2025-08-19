package io.libcodec.serde;

/**
 * Deserializer interface for deserializing objects from a specific format.
 * @param <T> the type of object to deserialize
 */
public interface Deserializer<T> {
    /**
     * Deserializes a byte array to an object.
     *
     * @param data the byte array to deserialize
     * @param clazz the class of the object to deserialize to
     * @return the deserialized object
     * @throws DeserializationException if deserialization fails
     */
    T deserialize(byte[] data, Class<T> clazz) throws DeserializationException;
}
