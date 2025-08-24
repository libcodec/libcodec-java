package io.libcodec.json;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static io.libcodec.json.JSONObject.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * JSONGenerator test class.
 * All tests in this class will run in both UTF-8 and UTF-16 encoding scenarios
 * to ensure the JSONGenerator's correctness and compatibility across different character encodings.
 */
public class JSONGeneratorTest {
    /**
     * Run test method which executes the same test logic in both UTF-8 and UTF-16 encoding environments.
     * This ensures consistent behavior of the generator across different character encodings.
     *
     * @param consumer The main test logic
     * @param after    The validation logic after testing
     */
    protected void run(Consumer<JSONGenerator> consumer, Consumer<JSONGenerator> after) {
        testUTF8(consumer, after);

        testUTF16(consumer, after);
    }

    protected void run(Consumer<JSONGenerator> consumer, String expected) {
        run(consumer, gen -> assertEquals(expected, gen.toString()));
    }

    private void testUTF16(Consumer<JSONGenerator> consumer, Consumer<JSONGenerator> after) {
        // Test UTF-16 implementation
        try (JSONGenerator generator = JSONGenerator.ofUTF16()) {
            run(generator, consumer, after);
        } catch (Throwable e) {
            fail("UTF-16 generator test failed: " + e.getMessage(), e);
        }
    }

    private void testUTF8(Consumer<JSONGenerator> consumer, Consumer<JSONGenerator> after) {
        // Test UTF-8 implementation
        try (JSONGenerator generator = JSONGenerator.ofUTF8()) {
            run(generator, consumer, after);
        } catch (Throwable e) {
            fail("UTF-8 generator test failed: " + e.getMessage(), e);
        }
    }

    protected void run(
            JSONGenerator generator,
            Consumer<JSONGenerator> r,
            Consumer<JSONGenerator> after
    ) {
        r.accept(generator);
        after.accept(generator);
    }

    @Test
    public void emptyObject() {
        run(
                gen -> gen.objectStart().objectEnd(),
                "{}"
        );
    }

    @Test
    public void nameValue() {
        run(
                gen ->
                        gen.objectStart()
                                .nameValue("name", "value")
                                .objectEnd(),
                "{\"name\":\"value\"}"
        );
        run(
                gen ->
                        gen.objectStart()
                                .nameValue("name", 123)
                                .objectEnd(),
                "{\"name\":123}"
        );
        run(
                gen ->
                        gen.objectStart()
                                .nameValue("name", 123L)
                                .objectEnd(),
                "{\"name\":123}"
        );
        run(
                gen ->
                        gen.objectStart()
                                .nameValue("name", 123.45F)
                                .objectEnd(),
                "{\"name\":123.45}"
        );
        run(
                gen ->
                        gen.objectStart()
                                .nameValue("name", 123.45D)
                                .objectEnd(),
                "{\"name\":123.45}"
        );
        run(
                gen ->
                        gen.objectStart()
                                .nameValue("name", true)
                                .objectEnd(),
                "{\"name\":true}"
        );
        run(
                gen ->
                        gen.objectStart()
                                .nameValue("name", false)
                                .objectEnd(),
                "{\"name\":false}"
        );
    }

    @Test
    public void object() {
        run(
                JSONGenerator::object,
                "{}"
        );
        run(
                gen -> gen.object("name", "value"),
                "{\"name\":\"value\"}"
        );
        run(
                gen -> gen.object("name", 123),
                "{\"name\":123}"
        );
        run(
                gen -> gen.object("name", 123L),
                "{\"name\":123}"
        );
        run(
                gen -> gen.object("name", true),
                "{\"name\":true}"
        );
        run(
                gen -> gen.object("name", false),
                "{\"name\":false}"
        );
        run(
                gen -> gen.object("name", 123.45f),
                "{\"name\":123.45}"
        );
        run(
                gen -> gen.object("name", 123.45D),
                "{\"name\":123.45}"
        );
        run(
                gen -> gen.object(
                        entry("str", "str"),
                        entry("v0", 1001),
                        entry("v1", 23456789),
                        entry("v1", true),
                        entry("v1", false)),
                "{\"str\":\"str\",\"v0\":1001,\"v1\":23456789,\"v1\":true,\"v1\":false}"
        );
    }
}
