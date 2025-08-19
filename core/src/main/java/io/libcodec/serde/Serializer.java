package io.libcodec.serde;

import io.libcodec.CodecContext;

/**
 * Serializer interface for serializing objects to a specific format.
 * @param <T> the type of object to serialize
 */
public interface Serializer<T> {
    /**
     * Serializes an object to a byte array.
     *
     * @param object the object to serialize
     * @param context the codec context
     * @return the serialized byte array
     * @throws SerializationException if serialization fails
     */
    byte[] serialize(T object, CodecContext context) throws SerializationException;
}
