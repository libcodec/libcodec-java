package io.libcodec.json.serde;

import io.libcodec.json.JSONGenerator;
import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;

import java.util.function.ToLongFunction;

abstract class PropertySerializerLong extends PropertySerializer {
    private final long defaultValueLong;

    protected PropertySerializerLong(
            String propertyName,
            long features,
            long defaultValue
    ) {
        super(propertyName, long.class, long.class, features);
        this.defaultValueLong = defaultValue;
    }

    protected boolean skip(long value) {
        return value == defaultValueLong &&
                JSONGenerator.Feature.NotWriteDefaultValue.isEnabled(features);
    }

    public void serialize(Object object, JSONGeneratorUTF8 generator, SerializeContext context) {
        long value = getLong(object);
        if (skip(value)) {
            return;
        }
        generator.writeNameRaw(nameBytes())
                .writeLong(value);
    }

    public void serialize(Object object, JSONGeneratorUTF16 generator, SerializeContext context) {
        long value = getLong(object);
        if (skip(value)) {
            return;
        }
        generator.writeNameRaw(nameChars())
                .writeLong(value);
    }

    @Override
    public Object getValue(Object object) {
        return getLong(object);
    }

    public abstract long getLong(Object object);
}
