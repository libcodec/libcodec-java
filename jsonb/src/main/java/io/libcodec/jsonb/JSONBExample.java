package io.libcodec.jsonb;

import io.libcodec.Codec;
import io.libcodec.CodecException;
import io.libcodec.Decoder;
import io.libcodec.Encoder;

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

            // Get encoder and decoder
            Encoder encoder = codec.getEncoder();
            Decoder decoder = codec.getDecoder();

            // Create a sample object to encode
            Person person = new Person("John Doe", 30);

            // Encode the object to JSON
            String json = encoder.encode(person);
            System.out.println("Encoded JSON: " + json);

            // Decode the JSON back to an object (will be a Map in this simple implementation)
            Object decodedObject = decoder.decode(json, Person.class);
            System.out.println("Decoded object type: " + decodedObject.getClass().getName());
            System.out.println("Decoded object: " + decodedObject);

            // If it's a map, we can access the properties
            if (decodedObject instanceof Map) {
                Map<String, Object> decodedMap = (Map<String, Object>) decodedObject;
                System.out.println("Decoded Person Name: " + decodedMap.get("name"));
                System.out.println("Decoded Person Age: " + decodedMap.get("age"));
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

            String json3 = encoder.encode(people);
            System.out.println("Encoded list: " + json3);

            Object decodedPeople = decoder.decode(json3, List.class);
            System.out.println("Decoded list: " + decodedPeople);
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
