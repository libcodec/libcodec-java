package io.libcodec.json.serde;

import io.libcodec.json.JSONGenerator;
import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;

import java.lang.reflect.Type;

public abstract class PropertySerializer {
    public final String name;
    public final Class<?> rawClass;
    public final Type type;
    public final long features;
    protected final Object defaultValue;
    private char[] nameChars;
    private byte[] nameBytes;

    protected PropertySerializer(String name, Class<?> rawClass, Type type, long features) {
        this.name = name;
        this.rawClass = rawClass;
        this.type = type;
        this.features = features;
        this.defaultValue = null;
    }

    protected PropertySerializer(String name, Class<?> rawClass, Type type, long features, Object defaultValue) {
        this.name = name;
        this.rawClass = rawClass;
        this.type = type;
        this.features = features;
        this.defaultValue = defaultValue;
    }

    final SerializeContext context(SerializeContext context) {
        context.setPropertyName(name);
        return context;
    }

    protected boolean skip(Object value) {
        return value == null
                || JSONGenerator.Feature.WriteMapNullValue.isEnabled(features);
    }

    protected char[] nameChars() {
        if (nameChars != null) {
            return nameChars;
        }
        return nameChars0();
    }

    private char[] nameChars0() {
        char[] buf = new char[name.length() + 3];
        buf[0] = '"';
        name.getChars(0, name.length(), buf, 1);
        buf[buf.length - 2] = '"';
        buf[buf.length - 1] = ':';
        return nameChars = buf;
    }

    protected byte[] nameBytes() {
        if (nameBytes != null) {
            return nameBytes;
        }
        return nameBytes0();
    }

    @SuppressWarnings("deprecation")
    private byte[] nameBytes0() {
        byte[] buf = new byte[name.length() + 3];
        buf[0] = '"';
        name.getBytes(0, name.length(), buf, 1);
        buf[buf.length - 2] = '"';
        buf[buf.length - 1] = ':';
        return nameBytes = buf;
    }

    protected Serializer getSerializer(SerializeContext context) {
        return context.getSerializer(type);
    }

    public void serialize(Object object, JSONGeneratorUTF8 generator, SerializeContext context) {
        Object value = getValue(object);
        if (skip(value)) {
            return;
        }

        generator.writeNameRaw(nameBytes());

        getSerializer(context)
                .serialize(value, generator, context(context));
    }

    public void serialize(Object object, JSONGeneratorUTF16 generator, SerializeContext context) {
        Object value = getValue(object);
        if (skip(value)) {
            return;
        }
        generator.writeRaw(nameChars());
        getSerializer(context)
                .serialize(value, generator, context(context));
    }

    public abstract Object getValue(Object object);
}
