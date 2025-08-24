package io.libcodec.json.serde;

import java.lang.reflect.Type;

public class SerializeContext {
    private Object object;
    private String fieldName;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Serializer getSerializer(Type type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
