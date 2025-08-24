package io.libcodec.serde;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

final class FunctionFactoryReflect
        implements FunctionFactory
{
    static final FunctionFactoryReflect INSTANCE = new FunctionFactoryReflect();

    @Override
    public ToIntFunction<Object> toInt(Field field) {
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
    public ToLongFunction<Object> toLong(Field field) {
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
    public ToDoubleFunction<Object> toFloat(Field field) {
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
    public ToDoubleFunction<Object> toDouble(Field field) {
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
    public Predicate<Object> toBoolean(Field field) {
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
    public ToIntFunction<Object> toChar(Field field) {
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
    public Function<Object, Object> function(Field field) {
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
    public ToIntFunction<Object> toInt(Method method) {
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
    public ToLongFunction<Object> toLong(Method method) {
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
    public ToDoubleFunction<Object> toFloat(Method method) {
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
    public ToDoubleFunction<Object> toDouble(Method method) {
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
    public Predicate<Object> toBoolean(Method method) {
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
    public ToIntFunction<Object> toChar(Method method) {
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
    public Function<Object, Object> function(Method method) {
        return o -> {
            try {
                return method.invoke(o);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
