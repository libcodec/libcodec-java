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
