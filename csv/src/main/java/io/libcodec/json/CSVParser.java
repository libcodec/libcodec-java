package io.libcodec.json;

import io.libcodec.CodecException;
import io.libcodec.Parser;

/**
 * CSV parser implementation.
 */
public class CSVParser
        implements Parser {
    @Override
    public <T> T parse(String data, Class<T> clazz) throws CodecException {
        throw new CodecException("Not implemented");
    }
}
