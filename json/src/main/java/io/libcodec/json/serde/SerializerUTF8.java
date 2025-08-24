package io.libcodec.json.serde;

import io.libcodec.json.JSONGeneratorUTF8;

public interface SerializerUTF8 {
    void serialize(Object object, JSONGeneratorUTF8 generator, SerializeContext context);
}
