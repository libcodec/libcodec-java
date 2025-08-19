package io.libcodec.jsonb;

import io.libcodec.Codec;
import io.libcodec.CodecException;
import io.libcodec.Decoder;
import io.libcodec.Encoder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON-B Codec implementation.
 */
public class JSONBCodec
        implements Codec {
    private final JSONBEncoder encoder;
    private final JSONBDecoder decoder;

    public JSONBCodec() {
        this.encoder = new JSONBEncoder();
        this.decoder = new JSONBDecoder();
    }

    @Override
    public Encoder getEncoder() {
        return encoder;
    }

    @Override
    public Decoder getDecoder() {
        return decoder;
    }

    /**
     * JSON-B Encoder implementation.
     */
    private static class JSONBEncoder
            implements Encoder {
        @Override
        public String encode(Object object) throws CodecException {
            try {
                return encodeObject(object);
            } catch (Exception e) {
                throw new CodecException("Failed to encode data to JSON-B", e);
            }
        }

        private String encodeObject(Object object) throws Exception {
            if (object == null) {
                return "null";
            }

            Class<?> clazz = object.getClass();

            // Handle primitive types and strings
            if (clazz.isPrimitive() || object instanceof String || object instanceof Number || object instanceof Boolean) {
                if (object instanceof String) {
                    return "\"" + object.toString().replace("\"", "\\\"") + "\"";
                } else {
                    return object.toString();
                }
            }

            // Handle arrays
            if (clazz.isArray()) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                Object[] array = (Object[]) object;
                for (int i = 0; i < array.length; i++) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(encodeObject(array[i]));
                }
                sb.append("]");
                return sb.toString();
            }

            // Handle collections
            if (object instanceof List) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                List<?> list = (List<?>) object;
                for (int i = 0; i < list.size(); i++) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(encodeObject(list.get(i)));
                }
                sb.append("]");
                return sb.toString();
            }

            // Handle maps
            if (object instanceof Map) {
                StringBuilder sb = new StringBuilder();
                sb.append("{");
                Map<?, ?> map = (Map<?, ?>) object;
                boolean first = true;
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    if (!first) {
                        sb.append(",");
                    }
                    sb.append("\"").append(entry.getKey().toString().replace("\"", "\\\"")).append("\":");
                    sb.append(encodeObject(entry.getValue()));
                    first = false;
                }
                sb.append("}");
                return sb.toString();
            }

            // Handle custom objects
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            boolean first = true;
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                Object value = field.get(object);
                if (value != null) {
                    if (!first) {
                        sb.append(",");
                    }
                    sb.append("\"").append(field.getName()).append("\":");
                    sb.append(encodeObject(value));
                    first = false;
                }
            }
            sb.append("}");
            return sb.toString();
        }
    }

    /**
     * JSON-B Decoder implementation.
     */
    private static class JSONBDecoder
            implements Decoder {
        @Override
        public <T> T decode(String data, Class<T> type) throws CodecException {
            try {
                return (T) decodeObject(data, type);
            } catch (Exception e) {
                throw new CodecException("Failed to decode JSON-B data", e);
            }
        }

        private Object decodeObject(String data, Class<?> type) throws Exception {
            if (data == null || data.equals("null")) {
                return null;
            }

            data = data.trim();

            // Handle strings
            if (data.startsWith("\"") && data.endsWith("\"")) {
                return data.substring(1, data.length() - 1).replace("\\\"", "\"");
            }

            // Handle numbers
            if (data.matches("-?\\d+(\\.\\d+)?")) {
                if (data.contains(".")) {
                    return Double.parseDouble(data);
                } else {
                    return Long.parseLong(data);
                }
            }

            // Handle booleans
            if (data.equals("true") || data.equals("false")) {
                return Boolean.parseBoolean(data);
            }

            // Handle arrays
            if (data.startsWith("[") && data.endsWith("]")) {
                return decodeArray(data);
            }

            // Handle objects
            if (data.startsWith("{") && data.endsWith("}")) {
                return decodeMap(data);
            }

            throw new IllegalArgumentException("Unsupported JSON format: " + data);
        }

        private Object decodeArray(String data) throws Exception {
            String content = data.substring(1, data.length() - 1).trim();
            if (content.isEmpty()) {
                return new ArrayList<>();
            }

            List<Object> list = new ArrayList<>();
            String[] elements = splitJsonArray(content);
            for (String element : elements) {
                list.add(decodeObject(element.trim(), Object.class));
            }
            return list;
        }

        private Object decodeMap(String data) throws Exception {
            String content = data.substring(1, data.length() - 1).trim();
            if (content.isEmpty()) {
                return new HashMap<>();
            }

            Map<String, Object> map = new HashMap<>();
            String[] pairs = splitJsonObject(content);
            for (String pair : pairs) {
                int colonIndex = pair.indexOf(':');
                if (colonIndex != -1) {
                    String key = pair.substring(0, colonIndex).trim();
                    String value = pair.substring(colonIndex + 1).trim();
                    // Remove quotes from key
                    if (key.startsWith("\"") && key.endsWith("\"")) {
                        key = key.substring(1, key.length() - 1);
                    }
                    map.put(key, decodeObject(value, Object.class));
                }
            }
            return map;
        }

        private String[] splitJsonArray(String content) {
            List<String> elements = new ArrayList<>();
            int braceCount = 0;
            int bracketCount = 0;
            int start = 0;
            for (int i = 0; i < content.length(); i++) {
                char c = content.charAt(i);
                if (c == '{') {
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                } else if (c == '[') {
                    bracketCount++;
                } else if (c == ']') {
                    bracketCount--;
                } else if (c == ',' && braceCount == 0 && bracketCount == 0) {
                    elements.add(content.substring(start, i));
                    start = i + 1;
                }
            }
            elements.add(content.substring(start));
            return elements.toArray(new String[0]);
        }

        private String[] splitJsonObject(String content) {
            List<String> pairs = new ArrayList<>();
            int braceCount = 0;
            int bracketCount = 0;
            int start = 0;
            for (int i = 0; i < content.length(); i++) {
                char c = content.charAt(i);
                if (c == '{') {
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                } else if (c == '[') {
                    bracketCount++;
                } else if (c == ']') {
                    bracketCount--;
                } else if (c == ',' && braceCount == 0 && bracketCount == 0) {
                    pairs.add(content.substring(start, i));
                    start = i + 1;
                }
            }
            pairs.add(content.substring(start));
            return pairs.toArray(new String[0]);
        }
    }
}
