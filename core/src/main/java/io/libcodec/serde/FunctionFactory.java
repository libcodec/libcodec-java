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
    ToIntFunction<Object> toInt(Field field);
    ToLongFunction<Object> toLong(Field field);
    ToDoubleFunction<Object> toFloat(Field field);
    ToDoubleFunction<Object> toDouble(Field field);
    Predicate<Object> toBoolean(Field field);
    ToIntFunction<Object> toChar(Field field);
    Function<Object, Object> function(Field field);

    // Setter methods for fields
    ObjIntConsumer<Object> toIntSetter(Field field);
    ObjLongConsumer<Object> toLongSetter(Field field);
    BiConsumer<Object, Float> toFloatSetter(Field field);
    ObjDoubleConsumer<Object> toDoubleSetter(Field field);
    BiConsumer<Object, Boolean> toBooleanSetter(Field field);
    BiConsumer<Object, Character> toCharSetter(Field field);
    BiConsumer<Object, Object> functionSetter(Field field);

    // Getter methods for methods
    ToIntFunction<Object> toInt(Method method);
    ToLongFunction<Object> toLong(Method method);
    ToDoubleFunction<Object> toFloat(Method method);
    ToDoubleFunction<Object> toDouble(Method method);
    Predicate<Object> toBoolean(Method method);
    ToIntFunction<Object> toChar(Method method);
    Function<Object, Object> function(Method method);

    // Setter methods for methods (setters)
    ObjIntConsumer<Object> toIntSetter(Method method);
    ObjLongConsumer<Object> toLongSetter(Method method);
    BiConsumer<Object, Float> toFloatSetter(Method method);
    ObjDoubleConsumer<Object> toDoubleSetter(Method method);
    BiConsumer<Object, Boolean> toBooleanSetter(Method method);
    BiConsumer<Object, Character> toCharSetter(Method method);
    BiConsumer<Object, Object> functionSetter(Method method);

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
