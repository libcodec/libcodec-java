package io.libcodec.meta;

import java.lang.reflect.Type;

public class PropertyInfo {
    public final String name;
    public final Class<?> rawClass;
    public final Type type;

    public PropertyInfo(String name, Class<?> rawClass, Type type) {
        this.name = name;
        this.rawClass = rawClass;
        this.type = type;
    }
}
