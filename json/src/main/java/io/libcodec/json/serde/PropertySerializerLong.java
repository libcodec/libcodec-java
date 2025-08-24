package io.libcodec.json.serde;

import io.libcodec.json.JSONException;
import io.libcodec.json.JSONGenerator;
import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.ToLongFunction;

public abstract class PropertySerializerLong extends PropertySerializer {
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

    public static PropertySerializerLong of(String propertyName, ToLongFunction<Object> function) {
        return of(propertyName, 0, 0L, function);
    }

    public static PropertySerializerLong of(String propertyName, long features, long defaultValue, ToLongFunction<Object> function) {
        return new PropertySerializerLong(propertyName, features, defaultValue) {
            @Override
            public long getLong(Object object) {
                return function.applyAsLong(object);
            }
        };
    }

    public static PropertySerializerLong of(String propertyName, long features, long defaultValue, Field field) {
        return of(propertyName, features, defaultValue, o -> {
            try {
                return field.getLong(o);
            }
            catch (IllegalAccessException e) {
                throw new JSONException(e);
            }
        });
    }

    public static PropertySerializerLong of(String propertyName, long features, long defaultValue, Method method) {
        return of(propertyName, features, defaultValue, o -> {
            try {
                return (long) method.invoke(o);
            }
            catch (ReflectiveOperationException e) {
                throw new JSONException(e);
            }
        });
    }
}
