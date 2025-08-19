package io.libcodec.json;

import io.libcodec.CodecContext;
import io.libcodec.CodecException;

/**
 * JSON generator implementation that generates objects to UTF-16 byte arrays.
 */
final class JSONGeneratorUTF16
        extends JSONGenerator {
    @Override
    public void generate(Object object, CodecContext context) throws CodecException {
        super.generate(object, context);
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
