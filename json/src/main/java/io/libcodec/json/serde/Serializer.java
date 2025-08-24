package io.libcodec.json.serde;

import io.libcodec.json.JSONGenerator;
import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;

public interface Serializer
        extends SerializerUTF8, SerializerUTF16 {
    default void serialize(Object object, JSONGenerator generator, SerializeContext context) {
        if (generator instanceof JSONGeneratorUTF8) {
            serialize(object, (JSONGeneratorUTF8) generator, context);
        } else {
            serialize(object, (JSONGeneratorUTF16) generator, context);
        }
    }

    void serialize(Object object, JSONGeneratorUTF16 generator, SerializeContext context);
}
