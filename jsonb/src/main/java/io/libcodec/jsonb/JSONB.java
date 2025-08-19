package io.libcodec.jsonb;

import io.libcodec.CodecContext;
import io.libcodec.CodecException;

/**
 * JSON-B utility class.
 */
public class JSONB {
    private static final JSONBCodec CODEC = new JSONBCodec();
    private static final CodecContext CONTEXT = new CodecContext("1.0.0");

    private JSONB() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts an object to its JSON-B representation.
     *
     * @param object the object to convert
     * @return the JSON-B representation of the object
     * @throws CodecException if the conversion fails
     */
    public static String toJson(Object object) throws CodecException {
        return CODEC.getGenerator().generate(object, CONTEXT);
    }

    /**
     * Converts a JSON-B string to an object of the specified type.
     *
     * @param json the JSON-B string to convert
     * @param type the type of the object to convert to
     * @param <T> the type of the object to convert to
     * @return the object represented by the JSON-B string
     * @throws CodecException if the conversion fails
     */
    public static <T> T fromJson(String json, Class<T> type) throws CodecException {
        // Note: This simple implementation doesn't fully utilize the type parameter
        // A more complete implementation would use it for type safety
        return (T) CODEC.getParser().parse(json, (Class<Object>) type);
    }
}
