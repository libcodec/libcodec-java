package io.libcodec.serde;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

class FunctionFactoryReflect
        implements FunctionFactory
{
    static final FunctionFactoryReflect INSTANCE = new FunctionFactoryReflect();

    @Override
    public ToIntFunction<Object> getInt(Field field) {
        validateFieldAndType(field, int.class);
        return o -> {
            try {
                return field.getInt(o);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ToLongFunction<Object> getLong(Field field) {
        validateFieldAndType(field, long.class);
        return o -> {
            try {
                return field.getLong(o);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ToDoubleFunction<Object> getFloat(Field field) {
        validateFieldAndType(field, float.class);
        return o -> {
            try {
                return field.getFloat(o);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ToDoubleFunction<Object> getDouble(Field field) {
        validateFieldAndType(field, double.class);
        return o -> {
            try {
                return field.getDouble(o);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public Predicate<Object> getBoolean(Field field) {
        validateFieldAndType(field, boolean.class);
        return o -> {
            try {
                return field.getBoolean(o);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ToIntFunction<Object> getChar(Field field) {
        validateFieldAndType(field, char.class);
        return o -> {
            try {
                return field.getChar(o);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public Function<Object, Object> getObject(Field field) {
        validateField(field);
        return o -> {
            try {
                return field.get(o);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ObjIntConsumer<Object> setInt(Field field) {
        validateFieldAndType(field, int.class);
        return (o, v) -> {
            try {
                field.setInt(o, v);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ObjLongConsumer<Object> setLong(Field field) {
        validateFieldAndType(field, long.class);
        return (o, v) -> {
            try {
                field.setLong(o, v);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public BiConsumer<Object, Float> setFloat(Field field) {
        validateFieldAndType(field, float.class);
        return (o, v) -> {
            try {
                field.setFloat(o, v);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ObjDoubleConsumer<Object> setDouble(Field field) {
        validateFieldAndType(field, double.class);
        return (o, v) -> {
            try {
                field.setDouble(o, v);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public BiConsumer<Object, Boolean> setBoolean(Field field) {
        validateFieldAndType(field, boolean.class);
        return (o, v) -> {
            try {
                field.setBoolean(o, v);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public BiConsumer<Object, Character> setChar(Field field) {
        validateFieldAndType(field, char.class);
        return (o, v) -> {
            try {
                field.setChar(o, v);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public BiConsumer<Object, Object> setObject(Field field) {
        validateField(field);
        return (o, v) -> {
            try {
                field.set(o, v);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ToIntFunction<Object> getInt(Method method) {
        validateMethodAndReturnType(method, int.class);
        return o -> {
            try {
                return (int) method.invoke(o);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ToLongFunction<Object> getLong(Method method) {
        validateMethodAndReturnType(method, long.class);
        return o -> {
            try {
                return (long) method.invoke(o);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ToDoubleFunction<Object> getFloat(Method method) {
        validateMethodAndReturnType(method, float.class);
        return o -> {
            try {
                return (float) method.invoke(o);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ToDoubleFunction<Object> getDouble(Method method) {
        validateMethodAndReturnType(method, double.class);
        return o -> {
            try {
                return (double) method.invoke(o);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public Predicate<Object> getBoolean(Method method) {
        validateMethodAndReturnType(method, boolean.class);
        return o -> {
            try {
                return (boolean) method.invoke(o);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ToIntFunction<Object> getChar(Method method) {
        validateMethodAndReturnType(method, char.class);
        return o -> {
            try {
                return (char) method.invoke(o);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public Function<Object, Object> getObject(Method method) {
        validateMethod(method);
        return o -> {
            try {
                return method.invoke(o);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ObjIntConsumer<Object> setInt(Method method) {
        validateMethodAndParameterType(method, int.class);
        return (o, v) -> {
            try {
                method.invoke(o, v);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ObjLongConsumer<Object> setLong(Method method) {
        validateMethodAndParameterType(method, long.class);
        return (o, v) -> {
            try {
                method.invoke(o, v);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public BiConsumer<Object, Float> setFloat(Method method) {
        validateMethodAndParameterType(method, float.class);
        return (o, v) -> {
            try {
                method.invoke(o, v);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public ObjDoubleConsumer<Object> setDouble(Method method) {
        validateMethodAndParameterType(method, double.class);
        return (o, v) -> {
            try {
                method.invoke(o, v);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public BiConsumer<Object, Boolean> setBoolean(Method method) {
        validateMethodAndParameterType(method, boolean.class);
        return (o, v) -> {
            try {
                method.invoke(o, v);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public BiConsumer<Object, Character> setChar(Method method) {
        validateMethodAndParameterType(method, char.class);
        return (o, v) -> {
            try {
                method.invoke(o, v);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public BiConsumer<Object, Object> setObject(Method method) {
        validateMethod(method);
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1) {
            throw new IllegalArgumentException("Method must have exactly one parameter");
        }
        return (o, v) -> {
            try {
                method.invoke(o, v);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
