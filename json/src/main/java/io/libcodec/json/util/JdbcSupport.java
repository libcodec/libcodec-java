package io.libcodec.json.util;

public class JdbcSupport {
    static Class CLASS_STRUCT;
    static volatile boolean CLASS_STRUCT_ERROR;

    public static boolean isStruct(Class objectClass) {
        if (CLASS_STRUCT == null && !CLASS_STRUCT_ERROR) {
            try {
                CLASS_STRUCT = Class.forName("java.sql.Struct");
            } catch (Throwable e) {
                CLASS_STRUCT_ERROR = true;
            }
        }

        return CLASS_STRUCT != null && CLASS_STRUCT.isAssignableFrom(objectClass);
    }
}
