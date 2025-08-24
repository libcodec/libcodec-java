package io.libcodec.serde;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

final class FunctionFactoryUnsafe
        implements FunctionFactory
{
    static final FunctionFactoryUnsafe INSTANCE = new FunctionFactoryUnsafe();

    private static final Unsafe UNSAFE;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Unable to access Unsafe", e);
        }
    }

    @Override
    public ToIntFunction<Object> toInt(Field field) {
        long offset = UNSAFE.objectFieldOffset(field);
        return o -> UNSAFE.getInt(o, offset);
    }

    @Override
    public ToLongFunction<Object> toLong(Field field) {
        long offset = UNSAFE.objectFieldOffset(field);
        return o -> UNSAFE.getLong(o, offset);
    }

    @Override
    public ToDoubleFunction<Object> toFloat(Field field) {
        long offset = UNSAFE.objectFieldOffset(field);
        return o -> UNSAFE.getFloat(o, offset);
    }

    @Override
    public ToDoubleFunction<Object> toDouble(Field field) {
        long offset = UNSAFE.objectFieldOffset(field);
        return o -> UNSAFE.getDouble(o, offset);
    }

    @Override
    public Predicate<Object> toBoolean(Field field) {
        long offset = UNSAFE.objectFieldOffset(field);
        return o -> UNSAFE.getBoolean(o, offset);
    }

    @Override
    public ToIntFunction<Object> toChar(Field field) {
        long offset = UNSAFE.objectFieldOffset(field);
        return o -> UNSAFE.getChar(o, offset);
    }

    @Override
    public Function<Object, Object> function(Field field) {
        long offset = UNSAFE.objectFieldOffset(field);
        return o -> UNSAFE.getObject(o, offset);
    }

    @Override
    public ToIntFunction<Object> toInt(Method method) {
        // For methods, we fall back to reflection as Unsafe doesn't directly support method invocation
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
        // For methods, we fall back to reflection as Unsafe doesn't directly support method invocation
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
        // For methods, we fall back to reflection as Unsafe doesn't directly support method invocation
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
        // For methods, we fall back to reflection as Unsafe doesn't directly support method invocation
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
        // For methods, we fall back to reflection as Unsafe doesn't directly support method invocation
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
        // For methods, we fall back to reflection as Unsafe doesn't directly support method invocation
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
        // For methods, we fall back to reflection as Unsafe doesn't directly support method invocation
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
