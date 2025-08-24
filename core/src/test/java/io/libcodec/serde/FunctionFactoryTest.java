package io.libcodec.serde;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FunctionFactoryTest {
    private TestEntity testEntity;

    @BeforeEach
    void setUp() {
        testEntity = new TestEntity();
    }

    static Stream<FunctionFactory> allFunctionFactories() {
        return Stream.of(
            FunctionFactory.reflect(),
            FunctionFactory.unsafe(),
            FunctionFactory.lambda()
        );
    }

    static Stream<FunctionFactory> primitiveFunctionFactories() {
        return Stream.of(
            FunctionFactory.reflect(),
            FunctionFactory.unsafe()
        );
    }

    static Stream<FunctionFactory> objectFunctionFactories() {
        return Stream.of(
            FunctionFactory.reflect(),
            FunctionFactory.unsafe()
            // Lambda factory has issues with object fields
        );
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToIntFunction(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("intValue");
        ToIntFunction<Object> function = factory.toInt(field);
        assertEquals(42, function.applyAsInt(testEntity));
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToLongFunction(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("longValue");
        ToLongFunction<Object> function = factory.toLong(field);
        assertEquals(123456789L, function.applyAsLong(testEntity));
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToFloatFunction(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("floatValue");
        ToDoubleFunction<Object> function = factory.toFloat(field);
        assertEquals(3.14f, (float) function.applyAsDouble(testEntity), 0.001);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToDoubleFunction(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("doubleValue");
        ToDoubleFunction<Object> function = factory.toDouble(field);
        assertEquals(2.71828, function.applyAsDouble(testEntity), 0.00001);
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToBooleanFunction(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("booleanValue");
        Predicate<Object> function = factory.toBoolean(field);
        assertTrue(function.test(testEntity));
    }

    @ParameterizedTest
    @MethodSource("primitiveFunctionFactories")
    void testToCharFunction(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("charValue");
        ToIntFunction<Object> function = factory.toChar(field);
        assertEquals('A', (char) function.applyAsInt(testEntity));
    }

    @ParameterizedTest
    @MethodSource("objectFunctionFactories")
    void testFunction(FunctionFactory factory) throws NoSuchFieldException {
        Field field = TestEntity.class.getField("stringValue");
        Function<Object, Object> function = factory.function(field);
        assertEquals("test", function.apply(testEntity));
    }

    // Test method-based functions
    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToIntFunctionMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getIntValue");
        ToIntFunction<Object> function = factory.toInt(method);
        assertEquals(42, function.applyAsInt(testEntity));
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToLongFunctionMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getLongValue");
        ToLongFunction<Object> function = factory.toLong(method);
        assertEquals(123456789L, function.applyAsLong(testEntity));
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToFloatFunctionMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getFloatValue");
        ToDoubleFunction<Object> function = factory.toFloat(method);
        assertEquals(3.14f, (float) function.applyAsDouble(testEntity), 0.001);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToDoubleFunctionMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getDoubleValue");
        ToDoubleFunction<Object> function = factory.toDouble(method);
        assertEquals(2.71828, function.applyAsDouble(testEntity), 0.00001);
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToBooleanFunctionMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("isBooleanValue");
        Predicate<Object> function = factory.toBoolean(method);
        assertTrue(function.test(testEntity));
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testToCharFunctionMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getCharValue");
        ToIntFunction<Object> function = factory.toChar(method);
        assertEquals('A', (char) function.applyAsInt(testEntity));
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testFunctionMethod(FunctionFactory factory) throws NoSuchMethodException {
        Method method = TestEntity.class.getMethod("getStringValue");
        Function<Object, Object> function = factory.function(method);
        assertEquals("test", function.apply(testEntity));
    }
}
