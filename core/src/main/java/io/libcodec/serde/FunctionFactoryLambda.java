package io.libcodec.serde;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
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

final class FunctionFactoryLambda extends FunctionFactoryReflect {
    private final MethodHandles.Lookup lookup;

    public FunctionFactoryLambda(MethodHandles.Lookup lookup) {
        this.lookup = lookup;
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

    @Override
    public ObjIntConsumer<Object> toIntSetter(Method method) {
        try {
            MethodHandle handle = lookup.unreflect(method);
            return (ObjIntConsumer<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "accept",
                    MethodType.methodType(ObjIntConsumer.class),
                    MethodType.methodType(void.class, Object.class, int.class),
                    handle,
                    MethodType.methodType(void.class, method.getDeclaringClass(), int.class)
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for method: " + method, e);
        }
    }

    @Override
    public ObjLongConsumer<Object> toLongSetter(Method method) {
        try {
            MethodHandle handle = lookup.unreflect(method);
            return (ObjLongConsumer<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "accept",
                    MethodType.methodType(ObjLongConsumer.class),
                    MethodType.methodType(void.class, Object.class, long.class),
                    handle,
                    MethodType.methodType(void.class, method.getDeclaringClass(), long.class)
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for method: " + method, e);
        }
    }

    @Override
    public BiConsumer<Object, Float> toFloatSetter(Method method) {
        try {
            MethodHandle handle = lookup.unreflect(method);
            return (BiConsumer<Object, Float>) LambdaMetafactory.metafactory(
                    lookup,
                    "accept",
                    MethodType.methodType(BiConsumer.class),
                    MethodType.methodType(void.class, Object.class, Object.class),
                    handle,
                    MethodType.methodType(void.class, method.getDeclaringClass(), Float.class)
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for method: " + method, e);
        }
    }

    @Override
    public ObjDoubleConsumer<Object> toDoubleSetter(Method method) {
        try {
            MethodHandle handle = lookup.unreflect(method);
            return (ObjDoubleConsumer<Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "accept",
                    MethodType.methodType(ObjDoubleConsumer.class),
                    MethodType.methodType(void.class, Object.class, double.class),
                    handle,
                    MethodType.methodType(void.class, method.getDeclaringClass(), double.class)
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for method: " + method, e);
        }
    }

    @Override
    public BiConsumer<Object, Boolean> toBooleanSetter(Method method) {
        try {
            MethodHandle handle = lookup.unreflect(method);
            return (BiConsumer<Object, Boolean>) LambdaMetafactory.metafactory(
                    lookup,
                    "accept",
                    MethodType.methodType(BiConsumer.class),
                    MethodType.methodType(void.class, Object.class, Object.class),
                    handle,
                    MethodType.methodType(void.class, method.getDeclaringClass(), Boolean.class)
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for method: " + method, e);
        }
    }

    @Override
    public BiConsumer<Object, Character> toCharSetter(Method method) {
        try {
            MethodHandle handle = lookup.unreflect(method);
            return (BiConsumer<Object, Character>) LambdaMetafactory.metafactory(
                    lookup,
                    "accept",
                    MethodType.methodType(BiConsumer.class),
                    MethodType.methodType(void.class, Object.class, Object.class),
                    handle,
                    MethodType.methodType(void.class, method.getDeclaringClass(), Character.class)
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for method: " + method, e);
        }
    }

    @Override
    public BiConsumer<Object, Object> functionSetter(Method method) {
        try {
            MethodHandle handle = lookup.unreflect(method);
            return (BiConsumer<Object, Object>) LambdaMetafactory.metafactory(
                    lookup,
                    "accept",
                    MethodType.methodType(BiConsumer.class),
                    MethodType.methodType(void.class, Object.class, Object.class),
                    handle,
                    MethodType.methodType(void.class, method.getDeclaringClass(), method.getParameterTypes()[0])
            ).getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to create lambda for method: " + method, e);
        }
    }
}
