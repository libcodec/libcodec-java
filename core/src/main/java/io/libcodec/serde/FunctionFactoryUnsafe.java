package io.libcodec.serde;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

class FunctionFactoryUnsafe
        extends FunctionFactoryReflect {
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
    public ToIntFunction<Object> getInt(Field field) {
        validateFieldAndType(field, int.class);
        long offset = UNSAFE.objectFieldOffset(field);
        return o -> UNSAFE.getInt(o, offset);
    }

    @Override
    public ToLongFunction<Object> getLong(Field field) {
        validateFieldAndType(field, long.class);
        long offset = UNSAFE.objectFieldOffset(field);
        return o -> UNSAFE.getLong(o, offset);
    }

    @Override
    public ToDoubleFunction<Object> getFloat(Field field) {
        validateFieldAndType(field, float.class);
        long offset = UNSAFE.objectFieldOffset(field);
        return o -> UNSAFE.getFloat(o, offset);
    }

    @Override
    public ToDoubleFunction<Object> getDouble(Field field) {
        validateFieldAndType(field, double.class);
        long offset = UNSAFE.objectFieldOffset(field);
        return o -> UNSAFE.getDouble(o, offset);
    }

    @Override
    public Predicate<Object> getBoolean(Field field) {
        validateFieldAndType(field, boolean.class);
        long offset = UNSAFE.objectFieldOffset(field);
        return o -> UNSAFE.getBoolean(o, offset);
    }

    @Override
    public ToIntFunction<Object> getChar(Field field) {
        validateFieldAndType(field, char.class);
        long offset = UNSAFE.objectFieldOffset(field);
        return o -> UNSAFE.getChar(o, offset);
    }

    @Override
    public Function<Object, Object> getObject(Field field) {
        validateField(field);
        long offset = UNSAFE.objectFieldOffset(field);
        return o -> UNSAFE.getObject(o, offset);
    }

    @Override
    public ObjIntConsumer<Object> setInt(Field field) {
        validateFieldAndType(field, int.class);
        long offset = UNSAFE.objectFieldOffset(field);
        return (o, v) -> UNSAFE.putInt(o, offset, v);
    }

    @Override
    public ObjLongConsumer<Object> setLong(Field field) {
        validateFieldAndType(field, long.class);
        long offset = UNSAFE.objectFieldOffset(field);
        return (o, v) -> UNSAFE.putLong(o, offset, v);
    }

    @Override
    public BiConsumer<Object, Float> setFloat(Field field) {
        validateFieldAndType(field, float.class);
        long offset = UNSAFE.objectFieldOffset(field);
        return (o, v) -> UNSAFE.putFloat(o, offset, v);
    }

    @Override
    public ObjDoubleConsumer<Object> setDouble(Field field) {
        validateFieldAndType(field, double.class);
        long offset = UNSAFE.objectFieldOffset(field);
        return (o, v) -> UNSAFE.putDouble(o, offset, v);
    }

    @Override
    public BiConsumer<Object, Boolean> setBoolean(Field field) {
        validateFieldAndType(field, boolean.class);
        long offset = UNSAFE.objectFieldOffset(field);
        return (o, v) -> UNSAFE.putBoolean(o, offset, v);
    }

    @Override
    public BiConsumer<Object, Character> setChar(Field field) {
        validateFieldAndType(field, char.class);
        long offset = UNSAFE.objectFieldOffset(field);
        return (o, v) -> UNSAFE.putChar(o, offset, v);
    }

    @Override
    public BiConsumer<Object, Object> setObject(Field field) {
        validateField(field);
        long offset = UNSAFE.objectFieldOffset(field);
        return (o, v) -> UNSAFE.putObject(o, offset, v);
    }
}
