package io.libcodec;

/**
 * Parser interface for deserializing objects
 */
public interface Parser {
    /**
     * Parses a string representation to an object
     * @param data the string to parse
     * @param clazz the class of the object to parse to
     * @return the parsed object
     * @throws CodecException if parsing fails
     */
    <T> T parse(String data, Class<T> clazz) throws CodecException;
}
