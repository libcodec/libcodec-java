package io.libcodec;

/**
 * Codec interface that provides version information and methods to get generators and parsers.
 */
public interface Codec {
    /**
     * The version of the codec library.
     */
    String VERSION = "1.0.0";

    /**
     * Gets a generator instance.
     *
     * @return a generator instance
     */
    Generator getGenerator();

    /**
     * Gets a parser instance.
     *
     * @return a parser instance
     */
    Parser getParser();
}
