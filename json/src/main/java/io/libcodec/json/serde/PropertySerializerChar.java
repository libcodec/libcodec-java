package io.libcodec.json.serde;

import io.libcodec.json.JSONGenerator;
import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;

import java.util.function.ToIntFunction;

abstract class PropertySerializerChar extends PropertySerializer {
    private final char defaultValueChar;

    protected PropertySerializerChar(
            String propertyName,
            long features,
            char defaultValue
    ) {
        super(propertyName, char.class, char.class, features);
        this.defaultValueChar = defaultValue;
    }

    protected boolean skip(char value) {
        return value == defaultValueChar &&
                JSONGenerator.Feature.NotWriteDefaultValue.isEnabled(features);
    }

    public void serialize(Object object, JSONGeneratorUTF8 generator, SerializeContext context) {
        char value = getChar(object);
        if (skip(value)) {
            return;
        }
        generator.writeNameRaw(nameBytes())
                .writeString(String.valueOf(value));
    }

    public void serialize(Object object, JSONGeneratorUTF16 generator, SerializeContext context) {
        char value = getChar(object);
        if (skip(value)) {
            return;
        }
        generator.writeNameRaw(nameChars())
                .writeString(String.valueOf(value));
    }

    @Override
    public Object getValue(Object object) {
        return getChar(object);
    }

    public abstract char getChar(Object object);
}
