package io.libcodec.json;

/**
 * CSV serialization interface
 */
public interface CSV {
    /**
     * Serialize an object to CSV string
     * @param object the object to serialize
     * @return CSV string
     */
    String toCSVString(Object object);

    /**
     * Deserialize CSV string to object
     * @param csv CSV string
     * @param clazz target object type
     * @param <T> object type parameter
     * @return deserialized object
     */
    <T> T parseObject(String csv, Class<T> clazz);
}
