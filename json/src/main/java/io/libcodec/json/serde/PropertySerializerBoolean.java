package io.libcodec.json.serde;

import io.libcodec.json.JSONException;
import io.libcodec.json.JSONGenerator;
import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Predicate;

public abstract class PropertySerializerBoolean extends PropertySerializer {
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

    public static PropertySerializerBoolean of(String propertyName, Predicate<Object> function) {
        return of(propertyName, 0, false, function);
    }

    public static PropertySerializerBoolean of(String propertyName, long features, boolean defaultValue, Predicate<Object> function) {
        return new PropertySerializerBoolean(propertyName, features, defaultValue) {
            @Override
            public boolean getBoolean(Object object) {
                return function.test(object);
            }
        };
    }

    public static PropertySerializerBoolean of(String propertyName, long features, boolean defaultValue, Field field) {
        return of(propertyName, features, defaultValue, o -> {
            try {
                return field.getBoolean(o);
            }
            catch (IllegalAccessException e) {
                throw new JSONException(e);
            }
        });
    }

    public static PropertySerializerBoolean of(String propertyName, long features, boolean defaultValue, Method method) {
        return of(propertyName, features, defaultValue, o -> {
            try {
                return (boolean) method.invoke(o);
            }
            catch (ReflectiveOperationException e) {
                throw new JSONException(e);
            }
        });
    }
}
