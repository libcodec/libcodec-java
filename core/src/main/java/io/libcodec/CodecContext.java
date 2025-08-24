package io.libcodec;

import io.libcodec.serde.Serializer;

import java.lang.reflect.Type;

/**
 * Codec context that holds configuration for codecs.
 */
public interface CodecContext {
    Serializer getSerializer(Type type);
}
