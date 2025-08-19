package io.libcodec.jsonb;

import io.libcodec.Codec;
import io.libcodec.Generator;
import io.libcodec.Parser;

/**
 * JSON-B Codec implementation.
 */
public class JSONBCodec
        implements Codec {
    private final JSONBEncoder generator;
    private final JSONBDecoder parser;

    public JSONBCodec() {
        this.generator = new JSONBEncoder();
        this.parser = new JSONBDecoder();
    }

    @Override
    public Generator getGenerator() {
        return generator;
    }

    @Override
    public Parser getParser() {
        return parser;
    }
}
