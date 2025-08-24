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

    // Default validation methods
    default void validateField(Field field) {
        if (field == null) {
            throw new IllegalArgumentException("Field cannot be null");
        }
    }

    default void validateMethod(Method method) {
        if (method == null) {
            throw new IllegalArgumentException("Method cannot be null");
        }
    }

    default void validateFieldAndType(Field field, Class<?> expectedType) {
        validateField(field);
        if (!field.getType().equals(expectedType)) {
            throw new IllegalArgumentException(
                "Field type mismatch. Expected: " + expectedType.getSimpleName() +
                ", Actual: " + field.getType().getSimpleName());
        }
    }

    default void validateMethodAndReturnType(Method method, Class<?> expectedReturnType) {
        validateMethod(method);
        if (!method.getReturnType().equals(expectedReturnType)) {
            throw new IllegalArgumentException(
                "Method return type mismatch. Expected: " + expectedReturnType.getSimpleName() +
                ", Actual: " + method.getReturnType().getSimpleName());
        }
    }

    default void validateMethodAndParameterType(Method method, Class<?> expectedParameterType) {
        validateMethod(method);
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1 || !parameterTypes[0].equals(expectedParameterType)) {
            throw new IllegalArgumentException(
                "Method parameter type mismatch. Expected: " + expectedParameterType.getSimpleName() +
                ", Actual: " + (parameterTypes.length > 0 ? parameterTypes[0].getSimpleName() : "no parameters"));
        }
    }

    default boolean isChainableSetter(Method method) {
        return method.getReturnType() == method.getDeclaringClass();
    }

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
