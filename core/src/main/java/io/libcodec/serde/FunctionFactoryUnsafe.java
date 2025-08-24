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
    public ObjIntConsumer<Object> toIntSetter(Field field) {
        long offset = UNSAFE.objectFieldOffset(field);
        return (o, v) -> UNSAFE.putInt(o, offset, v);
    }

    @Override
    public ObjLongConsumer<Object> toLongSetter(Field field) {
        long offset = UNSAFE.objectFieldOffset(field);
        return (o, v) -> UNSAFE.putLong(o, offset, v);
    }

    @Override
    public BiConsumer<Object, Float> toFloatSetter(Field field) {
        long offset = UNSAFE.objectFieldOffset(field);
        return (o, v) -> UNSAFE.putFloat(o, offset, v);
    }

    @Override
    public ObjDoubleConsumer<Object> toDoubleSetter(Field field) {
        long offset = UNSAFE.objectFieldOffset(field);
        return (o, v) -> UNSAFE.putDouble(o, offset, v);
    }

    @Override
    public BiConsumer<Object, Boolean> toBooleanSetter(Field field) {
        long offset = UNSAFE.objectFieldOffset(field);
        return (o, v) -> UNSAFE.putBoolean(o, offset, v);
    }

    @Override
    public BiConsumer<Object, Character> toCharSetter(Field field) {
        long offset = UNSAFE.objectFieldOffset(field);
        return (o, v) -> UNSAFE.putChar(o, offset, v);
    }

    @Override
    public BiConsumer<Object, Object> functionSetter(Field field) {
        long offset = UNSAFE.objectFieldOffset(field);
        return (o, v) -> UNSAFE.putObject(o, offset, v);
    }
}
