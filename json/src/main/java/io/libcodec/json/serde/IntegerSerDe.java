package io.libcodec.json.serde;

import io.libcodec.json.JSONGenerator;

public class IntegerSerDe {
    public void serialize(Object object, JSONGenerator generator, SerializeContext context) {
        generator.writeInt((Integer) object);
    }
}
