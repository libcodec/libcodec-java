package io.libcodec.json;

/**
 * JSON serialization exception class.
 */
public class JSONException

        extends RuntimeException {
    /**
     * Constructs a new JSONException with null as its detail message.
     */
    public JSONException() {
        super();
    }

    /**
     * Constructs a new JSONException with the specified detail message.
     *
     * @param message the detail message
     */
    public JSONException(final String message) {
        super(message);
    }

    /**
     * Constructs a new JSONException with the specified detail message and
     * cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public JSONException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new JSONException with the specified cause.
     *
     * @param cause the cause
     */
    public JSONException(final Throwable cause) {
        super(cause);
    }
}
