package io.libcodec.serde;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FunctionFactoryTest {
    private TestEntity testEntity;

    @BeforeEach
    void setUp() {
        testEntity = new TestEntity();
    }

    static FunctionFactory[] primitiveFunctionFactories() {
        return new FunctionFactory[]{
                FunctionFactory.reflect(),
                FunctionFactory.unsafe(),
                FunctionFactory.lambda()
        };
    }

    static FunctionFactory[] allFunctionFactories() {
        return new FunctionFactory[]{
                FunctionFactory.reflect(),
                FunctionFactory.unsafe(),
                FunctionFactory.lambda()
        };
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testGetInt(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("intValue");
        ToIntFunction<Object> getter = factory.getInt(field);
        assertEquals(42, getter.applyAsInt(testEntity));
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testGetLong(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("longValue");
        ToLongFunction<Object> getter = factory.getLong(field);
        assertEquals(123456789L, getter.applyAsLong(testEntity));
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testGetFloat(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("floatValue");
        ToDoubleFunction<Object> getter = factory.getFloat(field);
        assertEquals(1.23f, (float) getter.applyAsDouble(testEntity), 0.00001);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testGetDouble(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("doubleValue");
        ToDoubleFunction<Object> getter = factory.getDouble(field);
        assertEquals(3.14159, getter.applyAsDouble(testEntity), 0.00001);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testGetBoolean(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("booleanValue");
        Predicate<Object> getter = factory.getBoolean(field);
        assertTrue(getter.test(testEntity));
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testGetChar(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("charValue");
        ToIntFunction<Object> getter = factory.getChar(field);
        assertEquals('A', getter.applyAsInt(testEntity));
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testGetObject(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("stringValue");
        Function<Object, Object> getter = factory.getObject(field);
        assertEquals("Hello World", getter.apply(testEntity));
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testSetInt(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("intValue");
        ObjIntConsumer<Object> setter = factory.setInt(field);
        setter.accept(testEntity, 999);
        assertEquals(999, testEntity.intValue);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testSetLong(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("longValue");
        ObjLongConsumer<Object> setter = factory.setLong(field);
        setter.accept(testEntity, 999L);
        assertEquals(999L, testEntity.longValue);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testSetFloat(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("floatValue");
        BiConsumer<Object, Float> setter = factory.setFloat(field);
        setter.accept(testEntity, 9.99f);
        assertEquals(9.99f, testEntity.floatValue, 0.00001);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testSetDouble(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("doubleValue");
        ObjDoubleConsumer<Object> setter = factory.setDouble(field);
        setter.accept(testEntity, 9.99);
        assertEquals(9.99, testEntity.doubleValue, 0.00001);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testSetBoolean(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("booleanValue");
        BiConsumer<Object, Boolean> setter = factory.setBoolean(field);
        setter.accept(testEntity, false);
        assertEquals(false, testEntity.booleanValue);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testSetChar(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("charValue");
        BiConsumer<Object, Character> setter = factory.setChar(field);
        setter.accept(testEntity, 'Z');
        assertEquals('Z', testEntity.charValue);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testSetObject(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("stringValue");
        BiConsumer<Object, Object> setter = factory.setObject(field);
        setter.accept(testEntity, "New Value");
        assertEquals("New Value", testEntity.stringValue);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testGetIntMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getIntValue");
        ToIntFunction<Object> getter = factory.getInt(method);
        assertEquals(42, getter.applyAsInt(testEntity));
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testGetLongMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getLongValue");
        ToLongFunction<Object> getter = factory.getLong(method);
        assertEquals(123456789L, getter.applyAsLong(testEntity));
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testGetFloatMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getFloatValue");
        ToDoubleFunction<Object> getter = factory.getFloat(method);
        assertEquals(1.23f, (float) getter.applyAsDouble(testEntity), 0.00001);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testGetDoubleMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getDoubleValue");
        ToDoubleFunction<Object> getter = factory.getDouble(method);
        assertEquals(3.14159, getter.applyAsDouble(testEntity), 0.00001);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testGetBooleanMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("isBooleanValue");
        Predicate<Object> getter = factory.getBoolean(method);
        assertTrue(getter.test(testEntity));
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testGetCharMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getCharValue");
        ToIntFunction<Object> getter = factory.getChar(method);
        assertEquals('A', getter.applyAsInt(testEntity));
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testGetObjectMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getStringValue");
        Function<Object, Object> getter = factory.getObject(method);
        assertEquals("Hello World", getter.apply(testEntity));
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testSetIntMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("setIntValue", int.class);
        ObjIntConsumer<Object> setter = factory.setInt(method);
        setter.accept(testEntity, 888);
        assertEquals(888, testEntity.intValue);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testSetLongMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("setLongValue", long.class);
        ObjLongConsumer<Object> setter = factory.setLong(method);
        setter.accept(testEntity, 888L);
        assertEquals(888L, testEntity.longValue);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testSetFloatMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("setFloatValue", float.class);
        BiConsumer<Object, Float> setter = factory.setFloat(method);
        setter.accept(testEntity, 8.88f);
        assertEquals(8.88f, testEntity.floatValue, 0.00001);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testSetDoubleMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("setDoubleValue", double.class);
        ObjDoubleConsumer<Object> setter = factory.setDouble(method);
        setter.accept(testEntity, 8.88);
        assertEquals(8.88, testEntity.doubleValue, 0.00001);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testSetBooleanMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("setBooleanValue", boolean.class);
        BiConsumer<Object, Boolean> setter = factory.setBoolean(method);
        setter.accept(testEntity, false);
        assertEquals(false, testEntity.booleanValue);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testSetCharMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("setCharValue", char.class);
        BiConsumer<Object, Character> setter = factory.setChar(method);
        setter.accept(testEntity, 'Y');
        assertEquals('Y', testEntity.charValue);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testSetObjectMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("setStringValue", String.class);
        BiConsumer<Object, Object> setter = factory.setObject(method);
        setter.accept(testEntity, "Another New Value");
        assertEquals("Another New Value", testEntity.stringValue);
    }
}
