package io.libcodec.json.serde;

import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;
import io.libcodec.json.util.IOUtils;

public class IntegerSerDe implements Serializer {
    public static final int MAX_SIZE = 13;

    public void serialize(Object object, JSONGeneratorUTF8 generator, SerializeContext context) {
        Integer integer = (Integer) object;

        int offset = generator.getOffset();
        byte[] array = generator.ensureCapacity(offset + MAX_SIZE);
        generator.setOffset(
                serialize(offset, array, integer)
        );
    }

    public void serialize(Object object, JSONGeneratorUTF16 generator, SerializeContext context) {
        Integer integer = (Integer) object;

        int offset = generator.getOffset();
        char[] array = generator.ensureCapacity(offset + MAX_SIZE);
        generator.setOffset(
                serialize(offset, array, integer)
        );
    }

    private static int serialize(int offset, byte[] array, Integer integer) {
        if (integer == null) {
            offset = IOUtils.writeNull(array, offset);
        } else {
            offset = IOUtils.writeInt32(array, offset, integer);
        }
        return offset;
    }

    private static int serialize(int offset, char[] array, Integer integer) {
        if (integer == null) {
            offset = IOUtils.writeNull(array, offset);
        } else {
            offset = IOUtils.writeInt32(array, offset, integer);
        }
        return offset;
    }
}
