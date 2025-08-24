package io.libcodec.json;

import io.libcodec.CodecException;
import io.libcodec.Generator;

/**
 * CSV generator implementation.
 */
public class CSVGenerator
        extends Generator {
    @Override
    public Generator writeObject(Object object) {
        throw new CodecException("Not implemented");
    }

    @Override
    public Object getBuffer() {
        throw new CodecException("Not implemented");
    }
}
