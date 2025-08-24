package io.libcodec.json.serde;

import io.libcodec.json.JSONGeneratorUTF8;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeanSerializerTest {
    record Person(int age) {}
    @Test
    public void test() {
        BeanSerializer serializer = new BeanSerializer(
                new PropertySerializer[] {PropertySerializerInt.of("name", (Object e) -> ((Person) e).age())}
        );
        assertEquals(1, serializer.getPropertiesCount());
        JSONGeneratorUTF8 generator = JSONGeneratorUTF8.ofUTF8();
        SerializeContext context = new SerializeContext();
        serializer.serialize(new Person(10), generator, context);
        assertEquals("{\"name\":10}", generator.toString());
    }
}
