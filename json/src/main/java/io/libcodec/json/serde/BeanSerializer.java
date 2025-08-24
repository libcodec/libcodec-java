package io.libcodec.json.serde;

import io.libcodec.json.JSONGeneratorUTF16;
import io.libcodec.json.JSONGeneratorUTF8;

public class BeanSerializer implements Serializer {
    private final PropertySerializer[] properties;
    private final SerializerUTF8 serializerUTF8;
    private final SerializerUTF16 serializerUTF16;

    public BeanSerializer(PropertySerializer[] properties) {
        this.properties = properties;
        serializerUTF8 = createSerializerUTF8(properties);
        serializerUTF16 = createSerializerUTF16(properties);
    }

    @Override
    public void serialize(Object object, JSONGeneratorUTF8 generator, SerializeContext context) {
        if (object == null) {
            generator.writeNull();
        }

        context.setObject(object);
        generator.objectStart();
        serializerUTF8.serialize(object, generator, context);
        generator.objectEnd();
    }

    @Override
    public void serialize(Object object, JSONGeneratorUTF16 generator, SerializeContext context) {
        if (object == null) {
            generator.writeNull();
        }

        context.setObject(object);
        generator.objectStart();
        serializerUTF16.serialize(object, generator, context);
        generator.objectEnd();
    }

    private static SerializerUTF8 createSerializerUTF8(PropertySerializer[] properties) {
        return switch (properties.length) {
            case 1 -> (Object object, JSONGeneratorUTF8 generator, SerializeContext context)
                    -> properties[0].serialize(object, generator, context);
            case 2 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
            };
            case 3 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
            };
            case 4 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
            };
            case 5 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
            };
            case 6 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
            };
            case 7 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
            };
            case 8 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
            };
            case 9 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
            };
            case 10 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
                properties[9].serialize(object, generator, context);
            };
            case 11 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
                properties[9].serialize(object, generator, context);
                properties[10].serialize(object, generator, context);
            };
            case 12 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
                properties[9].serialize(object, generator, context);
                properties[10].serialize(object, generator, context);
                properties[11].serialize(object, generator, context);
            };
            case 13 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
                properties[9].serialize(object, generator, context);
                properties[10].serialize(object, generator, context);
                properties[11].serialize(object, generator, context);
                properties[12].serialize(object, generator, context);
            };
            case 14 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
                properties[9].serialize(object, generator, context);
                properties[10].serialize(object, generator, context);
                properties[11].serialize(object, generator, context);
                properties[12].serialize(object, generator, context);
                properties[13].serialize(object, generator, context);
            };
            case 15 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
                properties[9].serialize(object, generator, context);
                properties[10].serialize(object, generator, context);
                properties[11].serialize(object, generator, context);
                properties[12].serialize(object, generator, context);
                properties[13].serialize(object, generator, context);
                properties[14].serialize(object, generator, context);
            };
            case 16 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
                properties[9].serialize(object, generator, context);
                properties[10].serialize(object, generator, context);
                properties[11].serialize(object, generator, context);
                properties[12].serialize(object, generator, context);
                properties[13].serialize(object, generator, context);
                properties[14].serialize(object, generator, context);
                properties[15].serialize(object, generator, context);
            };
            default -> (Object object, JSONGeneratorUTF8 generator, SerializeContext context)
                    -> {
                for (PropertySerializer property : properties) {
                    property.serialize(object, generator, context);
                }
            };
        };
    }

    private static SerializerUTF16 createSerializerUTF16(PropertySerializer[] properties) {
        return switch (properties.length) {
            case 1 -> (object, generator, context)
                    -> properties[0].serialize(object, generator, context);
            case 2 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
            };
            case 3 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
            };
            case 4 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
            };
            case 5 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
            };
            case 6 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
            };
            case 7 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
            };
            case 8 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
            };
            case 9 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
            };
            case 10 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
                properties[9].serialize(object, generator, context);
            };
            case 11 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
                properties[9].serialize(object, generator, context);
                properties[10].serialize(object, generator, context);
            };
            case 12 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
                properties[9].serialize(object, generator, context);
                properties[10].serialize(object, generator, context);
                properties[11].serialize(object, generator, context);
            };
            case 13 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
                properties[9].serialize(object, generator, context);
                properties[10].serialize(object, generator, context);
                properties[11].serialize(object, generator, context);
                properties[12].serialize(object, generator, context);
            };
            case 14 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
                properties[9].serialize(object, generator, context);
                properties[10].serialize(object, generator, context);
                properties[11].serialize(object, generator, context);
                properties[12].serialize(object, generator, context);
                properties[13].serialize(object, generator, context);
            };
            case 15 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
                properties[9].serialize(object, generator, context);
                properties[10].serialize(object, generator, context);
                properties[11].serialize(object, generator, context);
                properties[12].serialize(object, generator, context);
                properties[13].serialize(object, generator, context);
                properties[14].serialize(object, generator, context);
            };
            case 16 -> (object, generator, context) -> {
                properties[0].serialize(object, generator, context);
                properties[1].serialize(object, generator, context);
                properties[2].serialize(object, generator, context);
                properties[3].serialize(object, generator, context);
                properties[4].serialize(object, generator, context);
                properties[5].serialize(object, generator, context);
                properties[6].serialize(object, generator, context);
                properties[7].serialize(object, generator, context);
                properties[8].serialize(object, generator, context);
                properties[9].serialize(object, generator, context);
                properties[10].serialize(object, generator, context);
                properties[11].serialize(object, generator, context);
                properties[12].serialize(object, generator, context);
                properties[13].serialize(object, generator, context);
                properties[14].serialize(object, generator, context);
                properties[15].serialize(object, generator, context);
            };
            default -> (object, generator, context) -> {
                for (PropertySerializer property : properties) {
                    property.serialize(object, generator, context);
                }
            };
        };
    }
}
