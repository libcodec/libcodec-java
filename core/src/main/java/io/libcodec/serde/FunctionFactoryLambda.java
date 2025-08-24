package io.libcodec.serde;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

final class FunctionFactoryLambda implements FunctionFactory {
    private final MethodHandles.Lookup lookup;

    public FunctionFactoryLambda(MethodHandles.Lookup lookup) {
        this.lookup = lookup;
    }

    @Override
    public ToIntFunction<Object> toInt(Field field) {
        try {
            MethodHandle handle = lookup.unreflectGetter(field);
            return (ToIntFunction<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "applyAsInt",
                    MethodType.methodType(ToIntFunction.class),
                    MethodType.methodType(int.class, Object.class),
                    handle,
                    MethodType.methodType(int.class, field.getDeclaringClass())
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for field: " + field, e);
        }
    }

    @Override
    public ToLongFunction<Object> toLong(Field field) {
        try {
            MethodHandle handle = lookup.unreflectGetter(field);
            return (ToLongFunction<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "applyAsLong",
                    MethodType.methodType(ToLongFunction.class),
                    MethodType.methodType(long.class, Object.class),
                    handle,
                    MethodType.methodType(long.class, field.getDeclaringClass())
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for field: " + field, e);
        }
    }

    @Override
    public ToDoubleFunction<Object> toFloat(Field field) {
        try {
            MethodHandle handle = lookup.unreflectGetter(field);
            return (ToDoubleFunction<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "applyAsDouble",
                    MethodType.methodType(ToDoubleFunction.class),
                    MethodType.methodType(double.class, Object.class),
                    handle,
                    MethodType.methodType(float.class, field.getDeclaringClass())
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for field: " + field, e);
        }
    }

    @Override
    public ToDoubleFunction<Object> toDouble(Field field) {
        try {
            MethodHandle handle = lookup.unreflectGetter(field);
            return (ToDoubleFunction<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "applyAsDouble",
                    MethodType.methodType(ToDoubleFunction.class),
                    MethodType.methodType(double.class, Object.class),
                    handle,
                    MethodType.methodType(double.class, field.getDeclaringClass())
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for field: " + field, e);
        }
    }

    @Override
    public Predicate<Object> toBoolean(Field field) {
        try {
            MethodHandle handle = lookup.unreflectGetter(field);
            return (Predicate<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "test",
                    MethodType.methodType(Predicate.class),
                    MethodType.methodType(boolean.class, Object.class),
                    handle,
                    MethodType.methodType(boolean.class, field.getDeclaringClass())
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for field: " + field, e);
        }
    }

    @Override
    public ToIntFunction<Object> toChar(Field field) {
        try {
            MethodHandle handle = lookup.unreflectGetter(field);
            return (ToIntFunction<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "applyAsInt",
                    MethodType.methodType(ToIntFunction.class),
                    MethodType.methodType(int.class, Object.class),
                    handle,
                    MethodType.methodType(char.class, field.getDeclaringClass())
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for field: " + field, e);
        }
    }

    @Override
    public Function<Object, Object> function(Field field) {
        try {
            MethodHandle handle = lookup.unreflectGetter(field);
            return (Function<Object, Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "apply",
                    MethodType.methodType(Function.class),
                    MethodType.methodType(Object.class, Object.class),
                    handle,
                    MethodType.methodType(field.getType(), field.getDeclaringClass())
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for field: " + field, e);
        }
    }

    @Override
    public ToIntFunction<Object> toInt(Method method) {
        try {
            MethodHandle handle = lookup.unreflect(method);
            return (ToIntFunction<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "applyAsInt",
                    MethodType.methodType(ToIntFunction.class),
                    MethodType.methodType(int.class, Object.class),
                    handle,
                    MethodType.methodType(int.class, method.getDeclaringClass())
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for method: " + method, e);
        }
    }

    @Override
    public ToLongFunction<Object> toLong(Method method) {
        try {
            MethodHandle handle = lookup.unreflect(method);
            return (ToLongFunction<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "applyAsLong",
                    MethodType.methodType(ToLongFunction.class),
                    MethodType.methodType(long.class, Object.class),
                    handle,
                    MethodType.methodType(long.class, method.getDeclaringClass())
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for method: " + method, e);
        }
    }

    @Override
    public ToDoubleFunction<Object> toFloat(Method method) {
        try {
            MethodHandle handle = lookup.unreflect(method);
            return (ToDoubleFunction<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "applyAsDouble",
                    MethodType.methodType(ToDoubleFunction.class),
                    MethodType.methodType(double.class, Object.class),
                    handle,
                    MethodType.methodType(float.class, method.getDeclaringClass())
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for method: " + method, e);
        }
    }

    @Override
    public ToDoubleFunction<Object> toDouble(Method method) {
        try {
            MethodHandle handle = lookup.unreflect(method);
            return (ToDoubleFunction<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "applyAsDouble",
                    MethodType.methodType(ToDoubleFunction.class),
                    MethodType.methodType(double.class, Object.class),
                    handle,
                    MethodType.methodType(double.class, method.getDeclaringClass())
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for method: " + method, e);
        }
    }

    @Override
    public Predicate<Object> toBoolean(Method method) {
        try {
            MethodHandle handle = lookup.unreflect(method);
            return (Predicate<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "test",
                    MethodType.methodType(Predicate.class),
                    MethodType.methodType(boolean.class, Object.class),
                    handle,
                    MethodType.methodType(boolean.class, method.getDeclaringClass())
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for method: " + method, e);
        }
    }

    @Override
    public ToIntFunction<Object> toChar(Method method) {
        try {
            MethodHandle handle = lookup.unreflect(method);
            return (ToIntFunction<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "applyAsInt",
                    MethodType.methodType(ToIntFunction.class),
                    MethodType.methodType(int.class, Object.class),
                    handle,
                    MethodType.methodType(char.class, method.getDeclaringClass())
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for method: " + method, e);
        }
    }

    @Override
    public Function<Object, Object> function(Method method) {
        try {
            MethodHandle handle = lookup.unreflect(method);
            return (Function<Object, Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "apply",
                    MethodType.methodType(Function.class),
                    MethodType.methodType(Object.class, Object.class),
                    handle,
                    MethodType.methodType(method.getReturnType(), method.getDeclaringClass())
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for method: " + method, e);
        }
    }
}
