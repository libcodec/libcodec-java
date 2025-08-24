package io.libcodec.serde;

public class TestEntity {
    public int intValue = 42;
    public long longValue = 123456789L;
    public float floatValue = 3.14f;
    public double doubleValue = 2.71828;
    public boolean booleanValue = true;
    public char charValue = 'A';
    public String stringValue = "test";

    public int getIntValue() {
        return intValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
