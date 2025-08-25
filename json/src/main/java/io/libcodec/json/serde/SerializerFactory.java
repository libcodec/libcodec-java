package io.libcodec.json.serde;

import io.libcodec.json.util.BeanUtils;
import io.libcodec.serde.FunctionFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class SerializerFactory {
    private FunctionFactory functionFactory;

    public SerializerFactory() {
        this(FunctionFactory.reflect());
    }

    public SerializerFactory(FunctionFactory functionFactory) {
        this.functionFactory = functionFactory;
    }

    public BeanSerializer serializer(Class<?> type) {
        LinkedHashMap<String, PropertySerializer> properties = new LinkedHashMap<>();

        BeanUtils.getters(type, method -> {

        });

        return new BeanSerializer(properties.values().toArray(new PropertySerializer[0]));
    }

    PropertySerializer getter(
            long features,
            Object defaultValue,
            Field field
    ) {
        return getter(field.getName(), features, defaultValue, field);
    }

    PropertySerializer getter(
            String propertyName,
            long features,
            Object defaultValue,
            Field field
    ) {
        Class<?> rawClass = field.getType();
        Type type = field.getGenericType();
        if (type == int.class) {
            return PropertySerializerInt.getInt(propertyName, features, (int) defaultValue, functionFactory.getInt(field));
        }
        if (type == long.class) {
            return getLong(propertyName, features, (long) defaultValue, functionFactory.getLong(field));
        }
        if (type == boolean.class) {
            return getBoolean(propertyName, features, (boolean) defaultValue, functionFactory.getBoolean(field));
        }
        if (type == char.class) {
            return getChar(propertyName, features, (char) defaultValue, functionFactory.getChar(field));
        }
        if (type == float.class) {
            return getFloat(propertyName, features, (float) defaultValue, functionFactory.getFloat(field));
        }
        if (type == double.class) {
            return getDouble(propertyName, features, (double) defaultValue, functionFactory.getDouble(field));
        }
        return getObject(propertyName, rawClass, type, features, defaultValue, functionFactory.getObject(field));
    }

    public static PropertySerializer getObject(
            String propertyName,
            Class<?> rawClass,
            Type type,
            long features,
            Object defaultValue,
            Function function
    ) {
        return new PropertySerializer(propertyName, rawClass, type, features, defaultValue) {
            @Override
            public Object getValue(Object object) {
                return function.apply(object);
            }
        };
    }

    public static PropertySerializerBoolean getBoolean(String propertyName, Predicate<Object> function) {
        return getBoolean(propertyName, 0, false, function);
    }

    public static PropertySerializerBoolean getBoolean(String propertyName, long features, boolean defaultValue, Predicate<Object> function) {
        return new PropertySerializerBoolean(propertyName, features, defaultValue) {
            @Override
            public boolean getBoolean(Object object) {
                return function.test(object);
            }
        };
    }

    public static PropertySerializerLong getLong(String propertyName, ToLongFunction<Object> function) {
        return getLong(propertyName, 0, 0L, function);
    }

    public static PropertySerializerLong getLong(String propertyName, long features, long defaultValue, ToLongFunction<Object> function) {
        return new PropertySerializerLong(propertyName, features, defaultValue) {
            @Override
            public long getLong(Object object) {
                return function.applyAsLong(object);
            }
        };
    }


    public static PropertySerializerChar getChar(String propertyName, ToIntFunction<Object> function) {
        return getChar(propertyName, 0, '\0', function);
    }

    public static PropertySerializerChar getChar(String propertyName, long features, char defaultValue, ToIntFunction<Object> function) {
        return new PropertySerializerChar(propertyName, features, defaultValue) {
            @Override
            public char getChar(Object object) {
                return (char) function.applyAsInt(object);
            }
        };
    }


    public static PropertySerializerFloat getFloat(String propertyName, ToDoubleFunction<Object> function) {
        return getFloat(propertyName, 0, 0.0f, function);
    }

    public static PropertySerializerFloat getFloat(String propertyName, long features, float defaultValue, ToDoubleFunction<Object> function) {
        return new PropertySerializerFloat(propertyName, features, defaultValue) {
            @Override
            public float getFloat(Object object) {
                return (float) function.applyAsDouble(object);
            }
        };
    }


    public static PropertySerializerDouble getDouble(String propertyName, ToDoubleFunction<Object> function) {
        return getDouble(propertyName, 0, 0.0, function);
    }

    public static PropertySerializerDouble getDouble(String propertyName, long features, double defaultValue, ToDoubleFunction<Object> function) {
        return new PropertySerializerDouble(propertyName, features, defaultValue) {
            @Override
            public double getDouble(Object object) {
                return function.applyAsDouble(object);
            }
        };
    }
}
