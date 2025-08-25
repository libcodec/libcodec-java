package io.libcodec.json.serde;

import io.libcodec.json.JSONGenerator;
import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;

import java.util.function.ToDoubleFunction;

abstract class PropertySerializerDouble extends PropertySerializer {
    private final double defaultValueDouble;

    protected PropertySerializerDouble(
            String propertyName,
            long features,
            double defaultValue
    ) {
        super(propertyName, double.class, double.class, features);
        this.defaultValueDouble = defaultValue;
    }

    protected boolean skip(double value) {
        return value == defaultValueDouble &&
                JSONGenerator.Feature.NotWriteDefaultValue.isEnabled(features);
    }

    public void serialize(Object object, JSONGeneratorUTF8 generator, SerializeContext context) {
        double value = getDouble(object);
        if (skip(value)) {
            return;
        }
        generator.writeNameRaw(nameBytes())
                .writeDouble(value);
    }

    public void serialize(Object object, JSONGeneratorUTF16 generator, SerializeContext context) {
        double value = getDouble(object);
        if (skip(value)) {
            return;
        }
        generator.writeNameRaw(nameChars())
                .writeDouble(value);
    }

    @Override
    public Object getValue(Object object) {
        return getDouble(object);
    }

    public abstract double getDouble(Object object);
}
