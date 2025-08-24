package io.libcodec.jsonb;

import io.libcodec.CodecException;
import io.libcodec.Parser;

/**
 * JSON-B Parser implementation.
 */
public class JSONBParser
        implements Parser {
    @Override
    public <T> T parse(String data, Class<T> type) throws CodecException {
        throw new CodecException("JSON-B parser is not supported");
    }
}
