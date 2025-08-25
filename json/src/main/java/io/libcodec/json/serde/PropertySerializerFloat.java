package io.libcodec.json.serde;

import io.libcodec.json.JSONGenerator;
import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;

import java.util.function.ToDoubleFunction;

abstract class PropertySerializerFloat extends PropertySerializer {
    private final float defaultValueFloat;

    protected PropertySerializerFloat(
            String propertyName,
            long features,
            float defaultValue
    ) {
        super(propertyName, float.class, float.class, features);
        this.defaultValueFloat = defaultValue;
    }

    protected boolean skip(float value) {
        return value == defaultValueFloat &&
                JSONGenerator.Feature.NotWriteDefaultValue.isEnabled(features);
    }

    public void serialize(Object object, JSONGeneratorUTF8 generator, SerializeContext context) {
        float value = getFloat(object);
        if (skip(value)) {
            return;
        }
        generator.writeNameRaw(nameBytes())
                .writeFloat(value);
    }

    public void serialize(Object object, JSONGeneratorUTF16 generator, SerializeContext context) {
        float value = getFloat(object);
        if (skip(value)) {
            return;
        }
        generator.writeNameRaw(nameChars())
                .writeFloat(value);
    }

    @Override
    public Object getValue(Object object) {
        return getFloat(object);
    }

    public abstract float getFloat(Object object);
}
