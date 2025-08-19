package io.libcodec;

/**
 * Codec interface that provides version information and methods to get encoders and decoders.
 */
public interface Codec {
    /**
     * The version of the codec library.
     */
    String VERSION = "1.0.0";

    /**
     * Gets an encoder instance.
     *
     * @return an encoder instance
     */
    Encoder getEncoder();

    /**
     * Gets a decoder instance.
     *
     * @return a decoder instance
     */
    Decoder getDecoder();
}
