package io.libcodec.json.serde;

import io.libcodec.json.JSONGenerator;

public class BeanSerializer implements Serializer {
    public PropertySerializer[] properties;

    @Override
    public void serialize(Object object, JSONGenerator generator, SerializeContext context) {
        if (object == null) {
            generator.writeNull();
        }
        generator.objectStart();

        for (PropertySerializer property : properties) {

        }

        generator.objectEnd();
    }
}
