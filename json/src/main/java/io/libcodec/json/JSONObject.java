package io.libcodec.json;

public class JSONObject {
    public interface Entry {
        String name();
        Object value();
    }

    protected record EntryInt(String name, int valueInt) implements Entry {
        @Override
        public Object value() {
            return valueInt;
        }
    }

    protected record EntryLong(String name, long valueLong) implements Entry {
        @Override
        public Object value() {
            return valueLong;
        }
    }

    protected record EntryString(String name, String valueString) implements Entry {
        @Override
        public Object value() {
            return valueString;
        }
    }

    protected record EntryBoolean(String name, boolean valueBoolean) implements Entry {
        @Override
        public Object value() {
            return valueBoolean;
        }
    }

    protected record EntryFloat(String name, float valueFloat) implements Entry {
        @Override
        public Object value() {
            return valueFloat;
        }
    }

    protected record EntryDouble(String name, double valueDouble) implements Entry {
        @Override
        public Object value() {
            return valueDouble;
        }
    }

    static Entry entry(String name, int valueInt) {
        return new EntryInt(name, valueInt);
    }

    static Entry entry(String name, long valueLong) {
        return new EntryLong(name, valueLong);
    }

    static Entry entry(String name, String valueString) {
        return new EntryString(name, valueString);
    }

    static Entry entry(String name, boolean valueBoolean) {
        return new EntryBoolean(name, valueBoolean);
    }

    static Entry entry(String name, float valueFloat) {
        return new EntryFloat(name, valueFloat);
    }

    static Entry entry(String name, double valueDouble) {
        return new EntryDouble(name, valueDouble);
    }
}
