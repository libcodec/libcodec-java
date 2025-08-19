package io.libcodec.json;

import io.libcodec.CodecContext;
import io.libcodec.CodecException;
import io.libcodec.Generator;

/**
 * JSON generator implementation that generates objects to UTF-16 byte arrays.
 */
public class JSONGeneratorUTF16
        implements Generator {
    private final JSONGenerator jsonGenerator;

    /**
     * Constructs a new JSON generator for UTF-16 byte arrays.
     */
    public JSONGeneratorUTF16() {
        this.jsonGenerator = new JSONGenerator();
    }

    @Override
    public void generate(Object object, CodecContext context) throws CodecException {
        jsonGenerator.generate(object, context);
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
            // In a real implementation, this would generate JSON to a byte array
            // For now, we'll return an empty byte array as a placeholder
            return new byte[0];
        } catch (Exception e) {
            throw new CodecException("Failed to generate object to UTF-16 JSON", e);
        }
    }
}
