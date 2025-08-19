package io.libcodec.json;

import io.libcodec.Codec;
import io.libcodec.Decoder;
import io.libcodec.Encoder;

/**
 * CSV codec implementation.
 */
public class CSVCodec
        implements Codec {
    /**
     * Gets a CSV encoder instance.
     *
     * @return a CSV encoder instance
     */
    @Override
    public Encoder getEncoder() {
        return new CSVEncoder();
    }

    /**
     * Gets a CSV decoder instance.
     *
     * @return a CSV decoder instance
     */
    @Override
    public Decoder getDecoder() {
        return new CSVDecoder();
    }
}
