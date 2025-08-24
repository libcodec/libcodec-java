package io.libcodec.json.serde;

import java.lang.reflect.Type;

public class SerializeContext {
    private Object object;
    private String fieldName;
    private int arrayIndex;
    private long features;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setPropertyName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Serializer getSerializer(Type type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long getFeatures() {
        return features;
    }

    public void setFeatures(long features) {
        this.features = features;
    }

    public int getArrayIndex() {
        return arrayIndex;
    }

    public void setArrayIndex(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }
}
