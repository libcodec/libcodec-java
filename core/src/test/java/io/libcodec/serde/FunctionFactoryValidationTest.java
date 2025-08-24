package io.libcodec.serde;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FunctionFactoryValidationTest {
    static class TestEntity {
        public int intValue;
        public String stringValue;

        public int getIntValue() {
            return intValue;
        }

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        public String getStringValue() {
            return stringValue;
        }

        public void setStringValue(String stringValue) {
            this.stringValue = stringValue;
        }
    }

    @Test
    void testValidateFieldNull() {
        FunctionFactory factory = FunctionFactory.reflect();

        // Test that null field throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            factory.getInt((Field) null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            factory.setLong((Field) null);
        });
    }

    @Test
    void testValidateMethodNull() {
        FunctionFactory factory = FunctionFactory.reflect();

        // Test that null method throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            factory.getObject((Method) null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            factory.setObject((Method) null);
        });
    }

    @Test
    void testValidateFieldTypeMismatch() {
        FunctionFactory factory = FunctionFactory.reflect();

        try {
            Field intField = TestEntity.class.getField("intValue");

            // Test that validating a field with wrong expected type throws
            assertThrows(IllegalArgumentException.class, () -> {
                factory.validateFieldAndType(intField, double.class);
            });
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testValidateMethodReturnTypeMismatch() {
        FunctionFactory factory = FunctionFactory.reflect();

        try {
            Method getStringMethod = TestEntity.class.getMethod("getStringValue");

            // Test that validating a method with wrong expected return type throws
            assertThrows(IllegalArgumentException.class, () -> {
                factory.validateMethodAndReturnType(getStringMethod, int.class);
            });
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testValidateMethodParameterTypeMismatch() {
        FunctionFactory factory = FunctionFactory.reflect();

        try {
            Method setStringMethod = TestEntity.class.getMethod("setStringValue", String.class);

            // Test that validating a method with wrong expected parameter type throws
            assertThrows(IllegalArgumentException.class, () -> {
                factory.validateMethodAndParameterType(setStringMethod, int.class);
            });
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
