package io.libcodec.serde;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChainableFunctionFactoryLambdaTest {
    private ChainableTestEntity testEntity;

    @BeforeEach
    void setUp() {
        testEntity = new ChainableTestEntity();
    }

    static FunctionFactory[] lambdaFunctionFactories() {
        return new FunctionFactory[]{
                FunctionFactory.lambda()
        };
    }

    @ParameterizedTest
    @MethodSource("lambdaFunctionFactories")
    void testSetIntMethodNonChainable(FunctionFactory factory) throws NoSuchMethodException {
        Method method = ChainableTestEntity.class.getMethod("setIntValue", int.class);
        ObjIntConsumer<Object> setter = factory.setInt(method);
        setter.accept(testEntity, 888);
        assertEquals(888, testEntity.getIntValue());
    }

    @ParameterizedTest
    @MethodSource("lambdaFunctionFactories")
    void testSetIntMethodChainable(FunctionFactory factory) throws NoSuchMethodException {
        Method method = ChainableTestEntity.class.getMethod("setIntValueChainable", int.class);
        ObjIntConsumer<Object> setter = factory.setInt(method);
        setter.accept(testEntity, 999);
        assertEquals(999, testEntity.getIntValue());
    }

    @ParameterizedTest
    @MethodSource("lambdaFunctionFactories")
    void testSetStringMethodChainable(FunctionFactory factory) throws NoSuchMethodException {
        Method method = ChainableTestEntity.class.getMethod("setStringValueChainable", String.class);
        BiConsumer<Object, Object> setter = factory.setObject(method);
        setter.accept(testEntity, "New Value");
        assertEquals("New Value", testEntity.getStringValue());
    }

    @ParameterizedTest
    @MethodSource("lambdaFunctionFactories")
    void testSetLongMethodChainable(FunctionFactory factory) throws NoSuchMethodException {
        Method method = ChainableTestEntity.class.getMethod("setLongValueChainable", long.class);
        ObjLongConsumer<Object> setter = factory.setLong(method);
        setter.accept(testEntity, 123456789L);
        assertEquals(123456789L, testEntity.getLongValue());
    }

    @ParameterizedTest
    @MethodSource("lambdaFunctionFactories")
    void testSetDoubleMethodChainable(FunctionFactory factory) throws NoSuchMethodException {
        Method method = ChainableTestEntity.class.getMethod("setDoubleValueChainable", double.class);
        ObjDoubleConsumer<Object> setter = factory.setDouble(method);
        setter.accept(testEntity, 3.14159);
        assertEquals(3.14159, testEntity.getDoubleValue(), 0.00001);
    }

    @ParameterizedTest
    @MethodSource("lambdaFunctionFactories")
    void testSetBooleanMethodChainable(FunctionFactory factory) throws NoSuchMethodException {
        Method method = ChainableTestEntity.class.getMethod("setBooleanValueChainable", boolean.class);
        BiConsumer<Object, Boolean> setter = factory.setBoolean(method);
        setter.accept(testEntity, true);
        assertEquals(true, testEntity.getBooleanValue());
    }

    @ParameterizedTest
    @MethodSource("lambdaFunctionFactories")
    void testSetCharMethodChainable(FunctionFactory factory) throws NoSuchMethodException {
        Method method = ChainableTestEntity.class.getMethod("setCharValueChainable", char.class);
        BiConsumer<Object, Character> setter = factory.setChar(method);
        setter.accept(testEntity, 'Z');
        assertEquals('Z', testEntity.getCharValue());
    }
}
