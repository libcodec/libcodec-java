package io.libcodec.json.serde;

public class ClassInfo {
    public final Class<?> cls;
    public Serializer serializer;

    public ClassInfo(Class<?> cls) {
        this.cls = cls;
    }
}
