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
    void testToInt(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("intValue");
        ToIntFunction<Object> getter = factory.toInt(field);
        assertEquals(42, getter.applyAsInt(testEntity));
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToLong(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("longValue");
        ToLongFunction<Object> getter = factory.toLong(field);
        assertEquals(123456789L, getter.applyAsLong(testEntity));
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToFloat(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("floatValue");
        ToDoubleFunction<Object> getter = factory.toFloat(field);
        assertEquals(1.23f, (float) getter.applyAsDouble(testEntity), 0.00001);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToDouble(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("doubleValue");
        ToDoubleFunction<Object> getter = factory.toDouble(field);
        assertEquals(3.14159, getter.applyAsDouble(testEntity), 0.00001);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToBoolean(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("booleanValue");
        Predicate<Object> getter = factory.toBoolean(field);
        assertTrue(getter.test(testEntity));
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToChar(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("charValue");
        ToIntFunction<Object> getter = factory.toChar(field);
        assertEquals('A', getter.applyAsInt(testEntity));
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testFunction(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("stringValue");
        Function<Object, Object> getter = factory.function(field);
        assertEquals("Hello World", getter.apply(testEntity));
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToIntSetter(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("intValue");
        ObjIntConsumer<Object> setter = factory.toIntSetter(field);
        setter.accept(testEntity, 999);
        assertEquals(999, testEntity.intValue);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToLongSetter(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("longValue");
        ObjLongConsumer<Object> setter = factory.toLongSetter(field);
        setter.accept(testEntity, 999L);
        assertEquals(999L, testEntity.longValue);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToFloatSetter(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("floatValue");
        BiConsumer<Object, Float> setter = factory.toFloatSetter(field);
        setter.accept(testEntity, 9.99f);
        assertEquals(9.99f, testEntity.floatValue, 0.00001);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToDoubleSetter(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("doubleValue");
        ObjDoubleConsumer<Object> setter = factory.toDoubleSetter(field);
        setter.accept(testEntity, 9.99);
        assertEquals(9.99, testEntity.doubleValue, 0.00001);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToBooleanSetter(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("booleanValue");
        BiConsumer<Object, Boolean> setter = factory.toBooleanSetter(field);
        setter.accept(testEntity, false);
        assertEquals(false, testEntity.booleanValue);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToCharSetter(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("charValue");
        BiConsumer<Object, Character> setter = factory.toCharSetter(field);
        setter.accept(testEntity, 'Z');
        assertEquals('Z', testEntity.charValue);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testFunctionSetter(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("stringValue");
        BiConsumer<Object, Object> setter = factory.functionSetter(field);
        setter.accept(testEntity, "New Value");
        assertEquals("New Value", testEntity.stringValue);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToIntMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getIntValue");
        ToIntFunction<Object> getter = factory.toInt(method);
        assertEquals(42, getter.applyAsInt(testEntity));
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToLongMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getLongValue");
        ToLongFunction<Object> getter = factory.toLong(method);
        assertEquals(123456789L, getter.applyAsLong(testEntity));
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToFloatMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getFloatValue");
        ToDoubleFunction<Object> getter = factory.toFloat(method);
        assertEquals(1.23f, (float) getter.applyAsDouble(testEntity), 0.00001);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToDoubleMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getDoubleValue");
        ToDoubleFunction<Object> getter = factory.toDouble(method);
        assertEquals(3.14159, getter.applyAsDouble(testEntity), 0.00001);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToBooleanMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("isBooleanValue");
        Predicate<Object> getter = factory.toBoolean(method);
        assertTrue(getter.test(testEntity));
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToCharMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getCharValue");
        ToIntFunction<Object> getter = factory.toChar(method);
        assertEquals('A', getter.applyAsInt(testEntity));
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testFunctionMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getStringValue");
        Function<Object, Object> getter = factory.function(method);
        assertEquals("Hello World", getter.apply(testEntity));
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToIntSetterMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("setIntValue", int.class);
        ObjIntConsumer<Object> setter = factory.toIntSetter(method);
        setter.accept(testEntity, 888);
        assertEquals(888, testEntity.intValue);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToLongSetterMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("setLongValue", long.class);
        ObjLongConsumer<Object> setter = factory.toLongSetter(method);
        setter.accept(testEntity, 888L);
        assertEquals(888L, testEntity.longValue);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToFloatSetterMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("setFloatValue", float.class);
        BiConsumer<Object, Float> setter = factory.toFloatSetter(method);
        setter.accept(testEntity, 8.88f);
        assertEquals(8.88f, testEntity.floatValue, 0.00001);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToDoubleSetterMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("setDoubleValue", double.class);
        ObjDoubleConsumer<Object> setter = factory.toDoubleSetter(method);
        setter.accept(testEntity, 8.88);
        assertEquals(8.88, testEntity.doubleValue, 0.00001);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToBooleanSetterMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("setBooleanValue", boolean.class);
        BiConsumer<Object, Boolean> setter = factory.toBooleanSetter(method);
        setter.accept(testEntity, false);
        assertEquals(false, testEntity.booleanValue);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToCharSetterMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("setCharValue", char.class);
        BiConsumer<Object, Character> setter = factory.toCharSetter(method);
        setter.accept(testEntity, 'Y');
        assertEquals('Y', testEntity.charValue);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testFunctionSetterMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("setStringValue", String.class);
        BiConsumer<Object, Object> setter = factory.functionSetter(method);
        setter.accept(testEntity, "Another New Value");
        assertEquals("Another New Value", testEntity.stringValue);
    }
}
