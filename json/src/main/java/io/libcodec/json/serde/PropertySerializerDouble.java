package io.libcodec.json.serde;

import io.libcodec.json.JSONException;
import io.libcodec.json.JSONGenerator;
import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.ToDoubleFunction;

public abstract class PropertySerializerDouble extends PropertySerializer {
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

    public static PropertySerializerDouble of(String propertyName, ToDoubleFunction<Object> function) {
        return of(propertyName, 0, 0.0, function);
    }

    public static PropertySerializerDouble of(String propertyName, long features, double defaultValue, ToDoubleFunction<Object> function) {
        return new PropertySerializerDouble(propertyName, features, defaultValue) {
            @Override
            public double getDouble(Object object) {
                return function.applyAsDouble(object);
            }
        };
    }

    public static PropertySerializerDouble of(String propertyName, long features, double defaultValue, Field field) {
        return of(propertyName, features, defaultValue, o -> {
            try {
                return field.getDouble(o);
            }
            catch (IllegalAccessException e) {
                throw new JSONException(e);
            }
        });
    }

    public static PropertySerializerDouble of(String propertyName, long features, double defaultValue, Method method) {
        return of(propertyName, features, defaultValue, o -> {
            try {
                return (double) method.invoke(o);
            }
            catch (ReflectiveOperationException e) {
                throw new JSONException(e);
            }
        });
    }
}
