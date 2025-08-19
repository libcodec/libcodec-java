package io.libcodec.jsonb;

import io.libcodec.Codec;
import io.libcodec.Generator;
import io.libcodec.Parser;

/**
 * JSON-B Codec implementation.
 */
public class JSONBCodec
        implements Codec {
    private final JSONBGenerator generator;
    private final JSONBParser parser;

    public JSONBCodec() {
        this.generator = new JSONBGenerator();
        this.parser = new JSONBParser();
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
