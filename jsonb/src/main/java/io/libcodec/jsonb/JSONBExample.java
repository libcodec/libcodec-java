package io.libcodec.jsonb;

import io.libcodec.Codec;
import io.libcodec.CodecContext;
import io.libcodec.CodecException;
import io.libcodec.Generator;
import io.libcodec.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Example demonstrating the usage of the JSONB codec.
 */
public class JSONBExample {
    public static void main(String[] args) {
        try {
            // Create a JSONB codec instance
            Codec codec = new JSONBCodec();

            // Create a codec context
            CodecContext context = new CodecContext(Codec.VERSION);

            // Get generator and parser
            Generator generator = codec.getGenerator();
            Parser parser = codec.getParser();

            // Create a sample object to generate
            Person person = new Person("John Doe", 30);

            // Generate the object to JSON
            System.out.print("Generated JSON: ");
            generator.write(person, context);

            // Using the utility class
            System.out.print("JSON via utility class: ");
            JSONB.toJson(person);

            // Test with a list
            List<Person> people = new ArrayList<>();
            people.add(new Person("Alice", 25));
            people.add(new Person("Bob", 35));

            System.out.print("Generated list: ");
            generator.write(people, context);
        } catch (CodecException e) {
            System.err.println("Codec error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * A simple POJO for demonstration purposes.
     */
    public static class Person {
        private String name;
        private int age;

        // Required for JSON-B
        public Person() {
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }
}
