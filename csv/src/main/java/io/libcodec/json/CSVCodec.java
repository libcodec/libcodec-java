package io.libcodec.json;

import io.libcodec.Codec;
import io.libcodec.Generator;
import io.libcodec.Parser;

/**
 * CSV codec implementation.
 */
public class CSVCodec
        implements Codec {
    /**
     * Gets a CSV generator instance.
     *
     * @return a CSV generator instance
     */
    @Override
    public Generator getGenerator() {
        return new CSVEncoder();
    }

    /**
     * Gets a CSV parser instance.
     *
     * @return a CSV parser instance
     */
    @Override
    public Parser getParser() {
        return new CSVDecoder();
    }
}
