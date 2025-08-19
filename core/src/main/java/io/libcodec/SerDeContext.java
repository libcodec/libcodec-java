package io.libcodec;

/**
 * SerDe context that holds configuration for serialization/deserialization.
 */
public class SerDeContext {
    private final String version;

    /**
     * Constructs a new SerDe context with the specified version.
     *
     * @param version the version of the SerDe library
     */
    public SerDeContext(String version) {
        this.version = version;
    }

    /**
     * Gets the version of the SerDe library.
     *
     * @return the version of the SerDe library
     */
    public String getVersion() {
        return version;
    }
}
