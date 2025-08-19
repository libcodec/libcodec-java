package io.libcodec.json;

/**
 * JSON serialization interface.
 */
public interface JSON {
    /**
     * Serialize an object to JSON string.
     * @param object the object to serialize
     * @return JSON string
     */
    String toJSONString(Object object);

    /**
     * Deserialize JSON string to object.
     * @param json JSON string
     * @param clazz target object type
     * @param <T> object type parameter
     * @return deserialized object
     */
    <T> T parseObject(String json, Class<T> clazz);
}
