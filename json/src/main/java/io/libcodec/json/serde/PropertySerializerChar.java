package io.libcodec.json.serde;

import io.libcodec.json.JSONException;
import io.libcodec.json.JSONGenerator;
import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.ToIntFunction;

public abstract class PropertySerializerChar extends PropertySerializer {
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

    public static PropertySerializerChar of(String propertyName, ToIntFunction<Object> function) {
        return of(propertyName, 0, '\0', function);
    }

    public static PropertySerializerChar of(String propertyName, long features, char defaultValue, ToIntFunction<Object> function) {
        return new PropertySerializerChar(propertyName, features, defaultValue) {
            @Override
            public char getChar(Object object) {
                return (char) function.applyAsInt(object);
            }
        };
    }

    public static PropertySerializerChar of(String propertyName, long features, char defaultValue, Field field) {
        return of(propertyName, features, defaultValue, o -> {
            try {
                return field.getChar(o);
            }
            catch (IllegalAccessException e) {
                throw new JSONException(e);
            }
        });
    }

    public static PropertySerializerChar of(String propertyName, long features, char defaultValue, Method method) {
        return of(propertyName, features, defaultValue, o -> {
            try {
                return (char) method.invoke(o);
            }
            catch (ReflectiveOperationException e) {
                throw new JSONException(e);
            }
        });
    }
}
