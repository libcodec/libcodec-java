package io.libcodec.json;

import io.libcodec.CodecException;
import io.libcodec.Encoder;

import java.nio.charset.StandardCharsets;

/**
 * JSON encoder implementation that encodes objects to UTF-16 byte arrays.
 */
public class JSONEncoderUTF16
        implements Encoder {
    private final JSONEncoder jsonEncoder;

    /**
     * Constructs a new JSON encoder for UTF-16 byte arrays.
     */
    public JSONEncoderUTF16() {
        this.jsonEncoder = new JSONEncoder();
    }

    @Override
    public String encode(Object object) throws CodecException {
        return jsonEncoder.encode(object);
    }

    /**
     * Encodes an object to a UTF-16 byte array.
     *
     * @param object the object to encode
     * @return the encoded UTF-16 byte array
     * @throws CodecException if encoding fails
     */
    public byte[] encodeToUTF16(Object object) throws CodecException {
        try {
            String json = jsonEncoder.encode(object);
            return json.getBytes(StandardCharsets.UTF_16);
        } catch (Exception e) {
            throw new CodecException("Failed to encode object to UTF-16 JSON", e);
        }
    }
}
