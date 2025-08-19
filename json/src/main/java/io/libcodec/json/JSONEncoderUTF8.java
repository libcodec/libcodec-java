package io.libcodec.json;

import io.libcodec.CodecContext;
import io.libcodec.CodecException;
import io.libcodec.Encoder;

import java.nio.charset.StandardCharsets;

/**
 * JSON encoder implementation that encodes objects to UTF-8 byte arrays.
 */
public class JSONEncoderUTF8
        implements Encoder {
    private final JSONEncoder jsonEncoder;

    /**
     * Constructs a new JSON encoder for UTF-8 byte arrays.
     */
    public JSONEncoderUTF8() {
        this.jsonEncoder = new JSONEncoder();
    }

    @Override
    public String encode(Object object, CodecContext context) throws CodecException {
        return jsonEncoder.encode(object, context);
    }

    /**
     * Encodes an object to a UTF-8 byte array.
     *
     * @param object the object to encode
     * @param context the codec context
     * @return the encoded UTF-8 byte array
     * @throws CodecException if encoding fails
     */
    public byte[] encodeToUTF8(Object object, CodecContext context) throws CodecException {
        try {
            String json = jsonEncoder.encode(object, context);
            return json.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new CodecException("Failed to encode object to UTF-8 JSON", e);
        }
    }
}
