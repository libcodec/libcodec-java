package io.libcodec.json;

import io.libcodec.CodecContext;
import io.libcodec.CodecException;

/**
 * JSON generator implementation that generates objects to UTF-8 byte arrays.
 */
final class JSONGeneratorUTF8
        extends JSONGenerator {
    @Override
    public void write(Object object, CodecContext context) throws CodecException {
        super.write(object, context);
    }
}
