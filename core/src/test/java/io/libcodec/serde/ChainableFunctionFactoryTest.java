package io.libcodec.serde;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.function.ObjIntConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChainableFunctionFactoryTest {
    private ChainableTestEntity testEntity;

    @BeforeEach
    void setUp() {
        testEntity = new ChainableTestEntity();
    }

    static FunctionFactory[] allFunctionFactories() {
        return new FunctionFactory[]{
                FunctionFactory.reflect(),
                FunctionFactory.unsafe(),
                FunctionFactory.lambda()
        };
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testSetIntMethodNonChainable(FunctionFactory factory) throws NoSuchMethodException {
        Method method = ChainableTestEntity.class.getMethod("setIntValue", int.class);
        ObjIntConsumer<Object> setter = factory.setInt(method);
        setter.accept(testEntity, 888);
        assertEquals(888, testEntity.getIntValue());
    }

    @ParameterizedTest
    @MethodSource("allFunctionFactories")
    void testSetIntMethodChainable(FunctionFactory factory) throws NoSuchMethodException {
        Method method = ChainableTestEntity.class.getMethod("setIntValueChainable", int.class);
        ObjIntConsumer<Object> setter = factory.setInt(method);
        setter.accept(testEntity, 999);
        assertEquals(999, testEntity.getIntValue());
    }
}
