package io.libcodec.serde;

public class TestEntity {
    public int intValue = 42;
    public long longValue = 123456789L;
    public float floatValue = 1.23f;
    public double doubleValue = 3.14159;
    public boolean booleanValue = true;
    public char charValue = 'A';
    public String stringValue = "Hello World";

    // Private fields for testing setter methods
    private int privateIntValue = 100;
    private String privateStringValue = "private";

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public void setCharValue(char charValue) {
        this.charValue = charValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    // Getters and setters for private fields
    public int getPrivateIntValue() {
        return privateIntValue;
    }

    public void setPrivateIntValue(int privateIntValue) {
        this.privateIntValue = privateIntValue;
    }

    public String getPrivateStringValue() {
        return privateStringValue;
    }

    public void setPrivateStringValue(String privateStringValue) {
        this.privateStringValue = privateStringValue;
    }
}
