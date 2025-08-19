package io.libcodec.serde;

import io.libcodec.CodecContext;
import io.libcodec.Encoder;

/**
 * Serializer interface for serializing objects to a specific format.
 */
public interface Serializer {
    /**
     * Serializes an object to a byte array.
     *
     * @param object the object to serialize
     * @param encoder the encoder to use for serialization
     * @param context the codec context
     */
    void serialize(Object object, Encoder encoder, CodecContext context);
}
