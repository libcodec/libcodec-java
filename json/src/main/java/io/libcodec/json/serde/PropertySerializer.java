package io.libcodec.json.serde;

import io.libcodec.json.JSONGenerator;

public abstract class PropertySerializer {
    public final String name;

    public PropertySerializer(String name) {this.name = name;}

    public void serialize(Object object, JSONGenerator generator, SerializeContext context) {
        Object value = getValue(object);
        if (value == null) {
            generator.writeNull();
            return;
        }
        generator.writeName(name);
        Serializer serializer = context.getSerializer(value.getClass());
        serializer.serialize(value, generator, context);
    }

    public abstract Object getValue(Object object);
}
