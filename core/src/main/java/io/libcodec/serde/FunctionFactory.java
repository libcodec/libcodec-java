package io.libcodec.serde;

import java.lang.invoke.MethodHandles;
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

public interface FunctionFactory {
    // Getter methods for fields
    ToIntFunction<Object> getInt(Field field);
    ToLongFunction<Object> getLong(Field field);
    ToDoubleFunction<Object> getFloat(Field field);
    ToDoubleFunction<Object> getDouble(Field field);
    Predicate<Object> getBoolean(Field field);
    ToIntFunction<Object> getChar(Field field);
    Function<Object, Object> getObject(Field field);

    // Setter methods for fields
    ObjIntConsumer<Object> setInt(Field field);
    ObjLongConsumer<Object> setLong(Field field);
    BiConsumer<Object, Float> setFloat(Field field);
    ObjDoubleConsumer<Object> setDouble(Field field);
    BiConsumer<Object, Boolean> setBoolean(Field field);
    BiConsumer<Object, Character> setChar(Field field);
    BiConsumer<Object, Object> setObject(Field field);

    // Getter methods for methods
    ToIntFunction<Object> getInt(Method method);
    ToLongFunction<Object> getLong(Method method);
    ToDoubleFunction<Object> getFloat(Method method);
    ToDoubleFunction<Object> getDouble(Method method);
    Predicate<Object> getBoolean(Method method);
    ToIntFunction<Object> getChar(Method method);
    Function<Object, Object> getObject(Method method);

    // Setter methods for methods (setters)
    ObjIntConsumer<Object> setInt(Method method);
    ObjLongConsumer<Object> setLong(Method method);
    BiConsumer<Object, Float> setFloat(Method method);
    ObjDoubleConsumer<Object> setDouble(Method method);
    BiConsumer<Object, Boolean> setBoolean(Method method);
    BiConsumer<Object, Character> setChar(Method method);
    BiConsumer<Object, Object> setObject(Method method);

    static FunctionFactory reflect() {
        return FunctionFactoryReflect.INSTANCE;
    }

    static FunctionFactory unsafe() {
        return FunctionFactoryUnsafe.INSTANCE;
    }

    static FunctionFactory lambda() {
        return new FunctionFactoryLambda(MethodHandles.lookup());
    }

    static FunctionFactory lambda(MethodHandles.Lookup lookup) {
        return new FunctionFactoryLambda(lookup);
    }
}
