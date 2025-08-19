package io.libcodec.serde;

/**
 * Exception thrown when a deserialization error occurs.
 */
public class DeserializationException
        extends Exception {
    /**
     * Constructs a new deserialization exception with the specified detail message.
     *
     * @param message the detail message
     */
    public DeserializationException(String message) {
        super(message);
    }

    /**
     * Constructs a new deserialization exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public DeserializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
