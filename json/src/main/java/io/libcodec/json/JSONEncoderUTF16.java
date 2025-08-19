package io.libcodec.json;

import io.libcodec.CodecContext;
import io.libcodec.CodecException;
import io.libcodec.Generator;

import java.nio.charset.StandardCharsets;

/**
 * JSON generator implementation that generates objects to UTF-16 byte arrays.
 */
public class JSONEncoderUTF16
        implements Generator {
    private final JSONEncoder jsonEncoder;

    /**
     * Constructs a new JSON generator for UTF-16 byte arrays.
     */
    public JSONEncoderUTF16() {
        this.jsonEncoder = new JSONEncoder();
    }

    @Override
    public String generate(Object object, CodecContext context) throws CodecException {
        return jsonEncoder.generate(object, context);
    }

    /**
     * Generates an object to a UTF-16 byte array.
     *
     * @param object the object to generate
     * @param context the codec context
     * @return the generated UTF-16 byte array
     * @throws CodecException if generation fails
     */
    public byte[] generateToUTF16(Object object, CodecContext context) throws CodecException {
        try {
            String json = jsonEncoder.generate(object, context);
            return json.getBytes(StandardCharsets.UTF_16);
        } catch (Exception e) {
            throw new CodecException("Failed to generate object to UTF-16 JSON", e);
        }
    }
}
