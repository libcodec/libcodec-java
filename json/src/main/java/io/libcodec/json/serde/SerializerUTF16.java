package io.libcodec.json.serde;

import io.libcodec.json.JSONGeneratorUTF16;

public interface SerializerUTF16 {
    void serialize(Object object, JSONGeneratorUTF16 generator, SerializeContext context);
}
