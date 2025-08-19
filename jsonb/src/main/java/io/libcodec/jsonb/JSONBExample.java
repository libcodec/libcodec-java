package io.libcodec.jsonb;

import io.libcodec.Codec;
import io.libcodec.CodecContext;
import io.libcodec.CodecException;
import io.libcodec.Generator;
import io.libcodec.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            String json = generator.generate(person, context);
            System.out.println("Generated JSON: " + json);

            // Parse the JSON back to an object (will be a Map in this simple implementation)
            Object parsedObject = parser.parse(json, Person.class);
            System.out.println("Parsed object type: " + parsedObject.getClass().getName());
            System.out.println("Parsed object: " + parsedObject);

            // If it's a map, we can access the properties
            if (parsedObject instanceof Map) {
                Map<String, Object> parsedMap = (Map<String, Object>) parsedObject;
                System.out.println("Parsed Person Name: " + parsedMap.get("name"));
                System.out.println("Parsed Person Age: " + parsedMap.get("age"));
            }

            // Using the utility class
            String json2 = JSONB.toJson(person);
            System.out.println("JSON via utility class: " + json2);

            Object person2 = JSONB.fromJson(json2, Person.class);
            System.out.println("Person via utility class: " + person2);

            // Test with a list
            List<Person> people = new ArrayList<>();
            people.add(new Person("Alice", 25));
            people.add(new Person("Bob", 35));

            String json3 = generator.generate(people, context);
            System.out.println("Generated list: " + json3);

            Object parsedPeople = parser.parse(json3, List.class);
            System.out.println("Parsed list: " + parsedPeople);
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
