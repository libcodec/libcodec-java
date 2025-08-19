package io.libcodec.jsonb;

import io.libcodec.CodecException;

/**
 * Exception thrown when a JSON-B operation fails.
 */
public class JSONBException
        extends CodecException {
    /**
     * Constructs a new JSONB exception with the specified detail message.
     *
     * @param message the detail message
     */
    public JSONBException(String message) {
        super(message);
    }

    /**
     * Constructs a new JSONB exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public JSONBException(String message, Throwable cause) {
        super(message, cause);
    }
}
