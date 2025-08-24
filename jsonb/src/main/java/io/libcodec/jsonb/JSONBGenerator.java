package io.libcodec.jsonb;

import io.libcodec.CodecException;
import io.libcodec.Generator;

/**
 * JSON-B Generator implementation.
 */
public class JSONBGenerator
        extends Generator {
    @Override
    public Generator writeObject(Object object) throws CodecException {
        throw new CodecException("Not implemented");
    }
}
