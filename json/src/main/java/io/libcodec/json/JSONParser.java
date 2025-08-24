package io.libcodec.json;

import io.libcodec.CodecException;
import io.libcodec.Parser;

/**
 * JSON parser implementation.
 */
public class JSONParser
        implements Parser {
    @Override
    public <T> T parse(String data, Class<T> type) throws CodecException {
        throw new CodecException("JSON parser not implemented");
    }
}
