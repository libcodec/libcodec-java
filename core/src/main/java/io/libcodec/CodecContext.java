package io.libcodec;

/**
 * Codec context that holds configuration for codecs.
 */
public class CodecContext {
    private final String version;

    /**
     * Constructs a new codec context with the specified version.
     *
     * @param version the version of the codec library
     */
    public CodecContext(String version) {
        this.version = version;
    }

    /**
     * Gets the version of the codec library.
     *
     * @return the version of the codec library
     */
    public String getVersion() {
        return version;
    }
}
