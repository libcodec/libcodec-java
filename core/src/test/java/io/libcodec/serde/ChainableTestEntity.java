package io.libcodec.serde;

public class ChainableTestEntity {
    private int intValue = 42;
    private String stringValue = "Hello World";
    private long longValue = 987654321L;
    private double doubleValue = 2.71828;
    private boolean booleanValue;
    private char charValue = 'A';

    // Standard setters (non-chainable)
    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    // Chainable setters (return this)
    public ChainableTestEntity setIntValueChainable(int intValue) {
        this.intValue = intValue;
        return this;
    }

    public ChainableTestEntity setStringValueChainable(String stringValue) {
        this.stringValue = stringValue;
        return this;
    }

    public ChainableTestEntity setLongValueChainable(long longValue) {
        this.longValue = longValue;
        return this;
    }

    public ChainableTestEntity setDoubleValueChainable(double doubleValue) {
        this.doubleValue = doubleValue;
        return this;
    }

    public ChainableTestEntity setBooleanValueChainable(boolean booleanValue) {
        this.booleanValue = booleanValue;
        return this;
    }

    public ChainableTestEntity setCharValueChainable(char charValue) {
        this.charValue = charValue;
        return this;
    }

    // Getters
    public int getIntValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public boolean getBooleanValue() {
        return booleanValue;
    }

    public char getCharValue() {
        return charValue;
    }
}
