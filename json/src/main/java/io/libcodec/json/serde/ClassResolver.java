package io.libcodec.json.serde;

import io.libcodec.CodecException;
import io.libcodec.util.IdentityMap;

public class ClassResolver {
    private ClassInfo classInfoCache;
    private final IdentityMap<Class<?>, ClassInfo> classInfoMap = new IdentityMap<>();
    public ClassInfo getOrUpdateClassInfo(Class<?> cls) {
        ClassInfo classInfo = classInfoCache;
        if (classInfo.cls != cls) {
            classInfo = classInfoMap.get(cls);
            if (classInfo == null || classInfo.serializer == null) {
                addSerializer(cls, createSerializer(cls));
                classInfo = classInfoMap.get(cls);
            }
            classInfoCache = classInfo;
        }
        return classInfo;
    }

    private Serializer createSerializer(Class<?> cls) {
        throw new CodecException("Serializer not found for type: " + cls);
    }

    private void addSerializer(Class<?> cls, Serializer serializer) {
        throw new CodecException("Serializer not found for type: " + cls);
    }
}
