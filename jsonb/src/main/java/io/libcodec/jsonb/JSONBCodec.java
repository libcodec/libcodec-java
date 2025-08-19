package io.libcodec.jsonb;

import io.libcodec.Codec;
import io.libcodec.Decoder;
import io.libcodec.Encoder;

/**
 * JSON-B Codec implementation.
 */
public class JSONBCodec
        implements Codec {
    private final JSONBEncoder encoder;
    private final JSONBDecoder decoder;

    public JSONBCodec() {
        this.encoder = new JSONBEncoder();
        this.decoder = new JSONBDecoder();
    }

    @Override
    public Encoder getEncoder() {
        return encoder;
    }

    @Override
    public Decoder getDecoder() {
        return decoder;
    }
}
