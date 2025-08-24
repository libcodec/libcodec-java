package io.libcodec.json;

/**
 * JSON generation exception class.
 */
public class JSONGenerationException
        extends JSONException {
    /**
     * Constructs a new JSONGenerationException with null as its detail message.
     */
    public JSONGenerationException() {
        super();
    }

    /**
     * Constructs a new JSONGenerationException with the specified detail message.
     *
     * @param message the detail message
     */
    public JSONGenerationException(final String message) {
        super(message);
    }

    /**
     * Constructs a new JSONGenerationException with the specified detail message and
     * cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public JSONGenerationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new JSONGenerationException with the specified cause.
     *
     * @param cause the cause
     */
    public JSONGenerationException(final Throwable cause) {
        super(cause);
    }
}
