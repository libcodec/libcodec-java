package io.libcodec;

/**
 * Generator interface for serializing objects
 */
public interface Generator {
    /**
     * Generates a string representation of an object
     * @param object the object to generate
     * @param context the codec context
     * @return the generated string
     * @throws CodecException if generation fails
     */
    String generate(Object object, CodecContext context) throws CodecException;
}
