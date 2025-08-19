package io.libcodec.json;

import io.libcodec.Codec;
import io.libcodec.Generator;
import io.libcodec.Parser;

/**
 * JSON codec implementation.
 */
public class JSONCodec
        implements Codec {
    /**
     * Gets a JSON generator instance.
     *
     * @return a JSON generator instance
     */
    @Override
    public Generator getGenerator() {
        return new JSONGenerator();
    }

    /**
     * Gets a JSON parser instance.
     *
     * @return a JSON parser instance
     */
    @Override
    public Parser getParser() {
        return new JSONParser();
    }
}
