package io.libcodec.json.serde;

import io.libcodec.json.JSONGenerator;
import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;

import java.util.function.Predicate;

abstract class PropertySerializerBoolean extends PropertySerializer {
    private final boolean defaultValueBoolean;

    protected PropertySerializerBoolean(
            String propertyName,
            long features,
            boolean defaultValue
    ) {
        super(propertyName, boolean.class, boolean.class, features);
        this.defaultValueBoolean = defaultValue;
    }

    protected boolean skip(boolean value) {
        return value == defaultValueBoolean &&
                JSONGenerator.Feature.NotWriteDefaultValue.isEnabled(features);
    }

    public void serialize(Object object, JSONGeneratorUTF8 generator, SerializeContext context) {
        boolean value = getBoolean(object);
        if (skip(value)) {
            return;
        }
        generator.writeNameRaw(nameBytes())
                .writeBool(value);
    }

    public void serialize(Object object, JSONGeneratorUTF16 generator, SerializeContext context) {
        boolean value = getBoolean(object);
        if (skip(value)) {
            return;
        }
        generator.writeNameRaw(nameChars())
                .writeBool(value);
    }

    @Override
    public Object getValue(Object object) {
        return getBoolean(object);
    }

    public abstract boolean getBoolean(Object object);


}
