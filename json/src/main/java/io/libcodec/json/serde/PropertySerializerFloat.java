package io.libcodec.json.serde;

import io.libcodec.json.JSONException;
import io.libcodec.json.JSONGenerator;
import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.ToDoubleFunction;

public abstract class PropertySerializerFloat extends PropertySerializer {
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

    public static PropertySerializerFloat of(String propertyName, ToDoubleFunction<Object> function) {
        return of(propertyName, 0, 0.0f, function);
    }

    public static PropertySerializerFloat of(String propertyName, long features, float defaultValue, ToDoubleFunction<Object> function) {
        return new PropertySerializerFloat(propertyName, features, defaultValue) {
            @Override
            public float getFloat(Object object) {
                return (float) function.applyAsDouble(object);
            }
        };
    }

    public static PropertySerializerFloat of(String propertyName, long features, float defaultValue, Field field) {
        return of(propertyName, features, defaultValue, o -> {
            try {
                return field.getFloat(o);
            }
            catch (IllegalAccessException e) {
                throw new JSONException(e);
            }
        });
    }

    public static PropertySerializerFloat of(String propertyName, long features, float defaultValue, Method method) {
        return of(propertyName, features, defaultValue, o -> {
            try {
                return (float) method.invoke(o);
            }
            catch (ReflectiveOperationException e) {
                throw new JSONException(e);
            }
        });
    }
}
