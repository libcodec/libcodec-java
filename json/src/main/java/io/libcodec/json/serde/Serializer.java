package io.libcodec.json.serde;

import io.libcodec.json.JSONGenerator;

public interface Serializer {
    void serialize(Object object, JSONGenerator generator, SerializeContext context);
}
