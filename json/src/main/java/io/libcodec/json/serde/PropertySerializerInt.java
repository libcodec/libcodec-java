package io.libcodec.json.serde;

import io.libcodec.json.JSONGenerator;
import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;

import java.util.function.ToIntFunction;

abstract class PropertySerializerInt extends PropertySerializer {
    private final int defaultValueInt;
    protected PropertySerializerInt(
            String propertyName,
            long features,
            int defaultValue
    ) {
        super(propertyName, int.class, int.class, features);
        this.defaultValueInt = defaultValue;
    }

    protected boolean skip(int value) {
        return value == defaultValueInt &&
                JSONGenerator.Feature.NotWriteDefaultValue.isEnabled(features);
    }

    public void serialize(Object object, JSONGeneratorUTF8 generator, SerializeContext context) {
        int value = getInt(object);
        if (skip(value)) {
            return;
        }
        generator.writeNameRaw(nameBytes())
                .writeInt(value);
    }

    public void serialize(Object object, JSONGeneratorUTF16 generator, SerializeContext context) {
        int value = getInt(object);
        if (skip(value)) {
            return;
        }
        generator.writeNameRaw(nameChars())
                .writeInt(value);
    }

    @Override
    public Object getValue(Object object) {
        return getInt(object);
    }

    public abstract int getInt(Object object);

    public static PropertySerializerInt getInt(String propertyName, ToIntFunction<Object> function) {
        return getInt(propertyName, 0, 0, function);
    }

    public static PropertySerializerInt getInt(String propertyName, long features, int defaultValue, ToIntFunction<Object> function) {
        return new PropertySerializerInt(propertyName, features, defaultValue) {
            @Override
            public int getInt(Object object) {
                return function.applyAsInt(object);
            }
        };
    }
}
