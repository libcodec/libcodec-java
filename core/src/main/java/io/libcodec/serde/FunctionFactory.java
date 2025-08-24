package io.libcodec.serde;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public interface FunctionFactory {
    ToIntFunction<Object> toInt(Field field);
    ToLongFunction<Object> toLong(Field field);
    ToDoubleFunction<Object> toFloat(Field field);
    ToDoubleFunction<Object> toDouble(Field field);
    Predicate<Object> toBoolean(Field field);
    ToIntFunction<Object> toChar(Field field);
    Function<Object, Object> function(Field field);

    ToIntFunction<Object> toInt(Method method);
    ToLongFunction<Object> toLong(Method method);
    ToDoubleFunction<Object> toFloat(Method method);
    ToDoubleFunction<Object> toDouble(Method method);
    Predicate<Object> toBoolean(Method method);
    ToIntFunction<Object> toChar(Method method);
    Function<Object, Object> function(Method method);

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
