package io.libcodec.serde;

import io.libcodec.CodecContext;

/**
 * Serializer interface for serializing objects to a specific format.
 */
public interface Serializer {
    /**
     * Serializes an object to a byte array.
     *
     * @param object the object to serialize
     * @param context the codec context
     * @throws SerializationException if serialization fails
     */
    void serialize(Object object, CodecContext context) throws SerializationException;
}
