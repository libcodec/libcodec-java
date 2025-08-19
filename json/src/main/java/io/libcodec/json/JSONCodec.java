package io.libcodec.json;

import io.libcodec.Codec;
import io.libcodec.Decoder;
import io.libcodec.Encoder;

/**
 * JSON codec implementation.
 */
public class JSONCodec
        implements Codec {
    /**
     * Gets a JSON encoder instance.
     *
     * @return a JSON encoder instance
     */
    @Override
    public Encoder getEncoder() {
        return new JSONEncoder();
    }

    /**
     * Gets a JSON decoder instance.
     *
     * @return a JSON decoder instance
     */
    @Override
    public Decoder getDecoder() {
        return new JSONDecoder();
    }
}
