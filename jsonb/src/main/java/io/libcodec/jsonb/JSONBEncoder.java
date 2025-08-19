package io.libcodec.jsonb;

import io.libcodec.CodecContext;
import io.libcodec.CodecException;
import io.libcodec.Generator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

/**
 * JSON-B Generator implementation.
 */
public class JSONBEncoder
        implements Generator {
    @Override
    public String generate(Object object, CodecContext context) throws CodecException {
        try {
            return generateObject(object);
        } catch (Exception e) {
            throw new CodecException("Failed to generate data to JSON-B", e);
        }
    }

    private String generateObject(Object object) throws Exception {
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
                sb.append(generateObject(array[i]));
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
                sb.append(generateObject(list.get(i)));
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
                sb.append(generateObject(entry.getValue()));
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
                sb.append(generateObject(value));
                first = false;
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
