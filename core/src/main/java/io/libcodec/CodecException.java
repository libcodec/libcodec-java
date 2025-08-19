package io.libcodec;

/**
 * Exception thrown when encoding or decoding fails.
 */
public class CodecException

        extends RuntimeException {
    /**
     * Constructs a new CodecException with null as its detail message.
     */
    public CodecException() {
        super();
    }

    /**
     * Constructs a new CodecException with the specified detail message.
     *
     * @param message the detail message
     */
    public CodecException(String message) {
        super(message);
    }

    /**
     * Constructs a new CodecException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public CodecException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new CodecException with the specified cause.
     *
     * @param cause the cause
     */
    public CodecException(Throwable cause) {
        super(cause);
    }
}
