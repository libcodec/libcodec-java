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
    public ObjIntConsumer<Object> toIntSetter(Field field) {
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
    public ObjLongConsumer<Object> toLongSetter(Field field) {
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
    public BiConsumer<Object, Float> toFloatSetter(Field field) {
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
    public ObjDoubleConsumer<Object> toDoubleSetter(Field field) {
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
    public BiConsumer<Object, Boolean> toBooleanSetter(Field field) {
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
    public BiConsumer<Object, Character> toCharSetter(Field field) {
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
    public BiConsumer<Object, Object> functionSetter(Field field) {
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

    @Override
    public ObjIntConsumer<Object> toIntSetter(Method method) {
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
    public ObjLongConsumer<Object> toLongSetter(Method method) {
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
    public BiConsumer<Object, Float> toFloatSetter(Method method) {
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
    public ObjDoubleConsumer<Object> toDoubleSetter(Method method) {
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
    public BiConsumer<Object, Boolean> toBooleanSetter(Method method) {
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
    public BiConsumer<Object, Character> toCharSetter(Method method) {
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
    public BiConsumer<Object, Object> functionSetter(Method method) {
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
