package io.libcodec.json;

import io.libcodec.CodecContext;
import io.libcodec.CodecException;
import io.libcodec.Generator;

/**
 * JSON generator implementation.
 */
public abstract class JSONGenerator
        extends Generator
        implements AutoCloseable {
    static final byte PRETTY_NON = 0, PRETTY_TAB = 1, PRETTY_2_SPACE = 2, PRETTY_4_SPACE = 4;
    protected byte pretty;
    protected int level;
    protected final int maxArraySize;
    protected boolean startObject;
    protected final int maxLevel;
    protected long features;
    boolean unquote;
    boolean browserSecure;
    boolean escapeNoneAscii;
    char quote = '"';
    public final boolean useSingleQuote = false;

    public JSONGenerator(long features) {
        this.maxArraySize = 67108864;
        this.maxLevel = 1024;
        this.features = features;
    }

    public static JSONGenerator of(Feature... features) {
        long featuresValue = Feature.valueOf(features);
        if (Feature.OptimizedForAscii.isEnabled(featuresValue)) {
            return new JSONGeneratorUTF8(featuresValue);
        }
        return new JSONGeneratorUTF16(featuresValue);
    }

    /**
     * Creates a new UTF-8 JSON generator instance.
     *
     * @return a new UTF-8 JSON generator
     */
    public static JSONGenerator ofUTF8(Feature... features) {
        return new JSONGeneratorUTF8(
                Feature.valueOf(features));
    }

    /**
     * Creates a new UTF-16 JSON generator instance.
     *
     * @return a new UTF-16 JSON generator
     */
    public static JSONGenerator ofUTF16(Feature... features) {
        return new JSONGeneratorUTF16(
                Feature.valueOf(features));
    }

    @Override
    public void write(Object object, CodecContext context) throws CodecException {
        try {
            // Assuming there's a JSON implementation available
            // This is a simplified implementation
            // In a real implementation, this would generate JSON to a destination
            System.out.println("{}"); // Placeholder implementation
        } catch (Exception e) {
            throw new CodecException("Failed to generate JSON from object", e);
        }
    }

    public JSONGenerator object() {
        if (++level > maxLevel) {
            overflowLevel();
        }
        writeRaw('{', '}');
        return this;
    }

    public JSONGenerator object(String name, String value) {
        return objectStart()
                .nameValue(name, value).
                objectEnd();
    }

    public JSONGenerator object(String name, int value) {
        return objectStart()
                .nameValue(name, value).
                objectEnd();
    }

    public JSONGenerator object(String name, long value) {
        return objectStart()
                .nameValue(name, value).
                objectEnd();
    }

    public JSONGenerator object(String name, boolean value) {
        return objectStart()
                .nameValue(name, value).
                objectEnd();
    }

    public JSONGenerator object(String name, float value) {
        return objectStart()
                .nameValue(name, value).
                objectEnd();
    }

    public JSONGenerator object(String name, double value) {
        return objectStart()
                .nameValue(name, value).
                objectEnd();
    }

    public JSONGenerator object(JSONObject.Entry entry) {
        return objectStart()
                .nameValue(entry).
                objectEnd();
    }

    public JSONGenerator object(JSONObject.Entry... entries) {
        objectStart();
        for (JSONObject.Entry entry : entries) {
            nameValue(entry);
        }
        objectEnd();
        return this;
    }

    public abstract JSONGenerator objectStart();
    public abstract JSONGenerator objectEnd();

    public JSONGenerator writeName(String name) {
        if (startObject) {
            startObject = false;
        } else {
            writeComma();
        }

        if (unquote) {
            writeRaw(name);
        } else {
            writeString(name);
        }
        return this;
    }

    public JSONGenerator nameValue(JSONObject.Entry entry) {
        JSONGenerator gen = writeName(entry.name()).writeColon();
        switch (entry) {
            case JSONObject.EntryInt e -> gen.writeInt(e.valueInt());
            case JSONObject.EntryLong e -> gen.writeLong(e.valueLong());
            case JSONObject.EntryBoolean e -> gen.writeBool(e.valueBoolean());
            case JSONObject.EntryString e -> gen.writeString(e.valueString());
            default -> throw new JSONException("not support");
        }
        return this;
    }

    public JSONGenerator nameValue(String name, String value) {
        return nameValue(JSONObject.entry(name, value));
    }

    public JSONGenerator nameValue(String name, int value) {
        return nameValue(JSONObject.entry(name, value));
    }

    public JSONGenerator nameValue(String name, long value) {
        return nameValue(JSONObject.entry(name, value));
    }

    public JSONGenerator nameValue(String name, boolean value) {
        return nameValue(JSONObject.entry(name, value));
    }

    public JSONGenerator nameValue(String name, float value) {
        return nameValue(JSONObject.entry(name, value));
    }

    public JSONGenerator nameValue(String name, double value) {
        return nameValue(JSONObject.entry(name, value));
    }

    /**
     * Writes a raw string without any escaping or formatting.
     *
     * @param str the string to write
     */
    public abstract void writeRaw(String str);

    public void writeRaw(char c0, char c1) {
        throw new JSONException("UnsupportedOperation");
    }

    /**
     * Writes a comma separator.
     */
    public abstract JSONGenerator writeComma();

    /**
     * Writes a colon separator.
     */
    public abstract JSONGenerator writeColon();

    /**
     * Writes a string value.
     * @param str the string to write, can be null
     */
    public abstract JSONGenerator writeString(String str);

    /**
     * Writes an int value.
     *
     * @param value the int value to write
     */
    public abstract JSONGenerator writeInt(int value);


    /**
     * Writes a boolean value.
     *
     * @param value the boolean value to write
     */
    public abstract JSONGenerator writeBool(boolean value);


    /**
     * Writes a long value.
     * @param i the long value to write
     */
    public abstract JSONGenerator writeLong(long i);

    /**
     * Writes a float value.
     * @param value the float value to write
     */
    public abstract JSONGenerator writeFloat(float value);

    /**
     * Writes a double value.
     * @param value the double value to write
     */
    public abstract JSONGenerator writeDouble(double value);

    protected static boolean isWriteAsString(long value, long features) {
        return (features & (MASK_WRITE_NON_STRING_VALUE_AS_STRING | MASK_WRITE_LONG_AS_STRING)) != 0
                || ((features & MASK_BROWSER_COMPATIBLE) != 0 && !isJavaScriptSupport(value));
    }

    static final long LONG_JAVASCRIPT_LOW = -9007199254740991L;
    static final long LONG_JAVASCRIPT_HIGH = 9007199254740991L;

    static boolean isJavaScriptSupport(long i) {
        return i >= LONG_JAVASCRIPT_LOW && i <= LONG_JAVASCRIPT_HIGH;
    }

    protected final int newCapacity(int minCapacity, int oldCapacity) {
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        if (newCapacity > maxArraySize) {
            if (minCapacity < maxArraySize) {
                newCapacity = maxArraySize;
            } else {
                throw new CodecException("Maximum array size exceeded. Try enabling LargeObject feature instead. "
                        + "Requested size: " + minCapacity + ", max size: " + maxArraySize);
            }
        }
        return newCapacity;
    }

    /**
     * Writes a null string value.
     * The serialization format depends on the context features:
     * <ul>
     *   <li>If {@link Feature#NullAsDefaultValue} or {@link Feature#WriteNullStringAsEmpty} is enabled, an empty string is written</li>
     *   <li>Otherwise, a null value is written</li>
     * </ul>
     */
    public void writeStringNull() {
        String raw;
        if ((features & (MASK_NULL_AS_DEFAULT_VALUE | MASK_WRITE_NULL_STRING_AS_EMPTY)) != 0) {
            raw = (features & MASK_USE_SINGLE_QUOTES) != 0 ? "''" : "\"\"";
        } else {
            raw = "null";
        }
        writeRaw(raw);
    }

    protected final void overflowLevel() {
        throw new JSONException("level too large : " + level);
    }

    public void close() {
    }

    protected static final long MASK_WRITE_MAP_NULL_VALUE = 1 << 4;
    protected static final long MASK_BROWSER_COMPATIBLE = 1 << 5;
    protected static final long MASK_NULL_AS_DEFAULT_VALUE = 1 << 6;
    protected static final long MASK_WRITE_BOOLEAN_AS_NUMBER = 1 << 7;
    protected static final long MASK_WRITE_NON_STRING_VALUE_AS_STRING = 1L << 8;
    protected static final long MASK_WRITE_CLASS_NAME = 1 << 9;
    protected static final long MASK_NOT_WRITE_DEFAULT_VALUE = 1 << 12;
    protected static final long MASK_WRITE_ENUMS_USING_NAME = 1 << 13;
    protected static final long MASK_WRITE_ENUM_USING_TO_STRING = 1 << 14;
    protected static final long MASK_PRETTY_FORMAT = 1 << 16;
    protected static final long MASK_REFERENCE_DETECTION = 1 << 17;
    protected static final long MASK_USE_SINGLE_QUOTES = 1 << 20;
    protected static final long MASK_WRITE_NULL_LIST_AS_EMPTY = 1 << 22;
    protected static final long MASK_WRITE_NULL_STRING_AS_EMPTY = 1 << 23;
    protected static final long MASK_WRITE_NULL_NUMBER_AS_ZERO = 1 << 24;
    protected static final long MASK_WRITE_NULL_BOOLEAN_AS_FALSE = 1 << 25;
    protected static final long MASK_NOT_WRITE_EMPTY_ARRAY = 1 << 26;
    protected static final long MASK_ESCAPE_NONE_ASCII = 1L << 30;
    protected static final long MASK_IGNORE_NON_FIELD_GETTER = 1L << 32;
    protected static final long MASK_WRITE_LONG_AS_STRING = 1L << 34;
    protected static final long MASK_BROWSER_SECURE = 1L << 35;
    protected static final long MASK_NOT_WRITE_NUMBER_CLASS_NAME = 1L << 40;

    /**
     * Feature is used to control the behavior of JSON writing and serialization in FASTJSON2.
     * Each feature represents a specific configuration option that can be enabled or disabled
     * to customize how Java objects are serialized to JSON format.
     *
     * <p>Features can be enabled in several ways:
     * <ul>
     *   <li>Using factory methods like {@link #of(Feature...)}</li>
     *   <li>Using {@link Context#config(Feature...)} method</li>
     *   <li>Using {@link JSONFactory#getDefaultWriterFeatures()} for global configuration</li>
     * </ul>
     *
     *
     * <p>Example usage:
     * <pre>
     * // Enable PrettyFormat feature for this writer only
     * try (JSONWriter writer = JSONWriter.of(JSONWriter.Feature.PrettyFormat)) {
     *     writer.writeAny(object);
     *     String json = writer.toString();
     * }
     *
     * // Enable multiple features
     * try (JSONWriter writer = JSONWriter.of(
     *         JSONWriter.Feature.PrettyFormat,
     *         JSONWriter.Feature.WriteMapNullValue)) {
     *     writer.writeAny(object);
     *     String json = writer.toString();
     * }
     *
     * // Using context configuration
     * JSONWriter.Context context = new JSONWriter.Context();
     * context.config(JSONWriter.Feature.PrettyFormat);
     * try (JSONWriter writer = JSONWriter.of(context)) {
     *     writer.writeAny(object);
     *     String json = writer.toString();
     * }
     * </pre>
     *
     *
     * <p>Features are implemented as bitmask flags for efficient storage and checking.
     * Each feature has a unique mask value that is used internally to determine
     * whether the feature is enabled in a given configuration.</p>
     *
     * @since 1.0.0
     */
    public enum Feature {
        /**
         * Feature that determines whether to use field-based serialization instead of getter-based serialization.
         * When enabled, fields are directly accessed rather than using getter methods.
         * This can improve performance but may bypass validation logic in getters.
         *
         * <p>By default, this feature is disabled, meaning that getter-based serialization is used.</p>
         *
         * @since 2.0.0
         */
        FieldBased(1),

        /**
         * Feature that determines whether to ignore non-serializable classes during serialization.
         * When enabled, classes that do not implement {@link java.io.Serializable} will be ignored
         * rather than causing an exception to be thrown.
         *
         * <p>By default, this feature is disabled, meaning that non-serializable classes are not ignored.</p>
         *
         * @since 2.0.0
         */
        IgnoreNoneSerializable(1 << 1),

        /**
         * Feature that determines whether to throw an exception when encountering non-serializable classes
         * during serialization.
         * When enabled, an exception will be thrown if a class does not implement {@link java.io.Serializable}.
         *
         * <p>By default, this feature is disabled, meaning that no exception is thrown for non-serializable classes.</p>
         *
         * @since 2.0.0
         */
        ErrorOnNoneSerializable(1 << 2),

        /**
         * Feature that determines whether to serialize Java beans as JSON arrays instead of JSON objects.
         * When enabled, bean properties will be serialized as array elements in the order they are defined,
         * rather than as key-value pairs in an object.
         *
         * <p>By default, this feature is disabled, meaning that beans are serialized as JSON objects.</p>
         *
         * @since 2.0.0
         */
        BeanToArray(1 << 3),

        /**
         * Feature that determines whether to write null values during serialization.
         * When enabled, null values will be included in the output JSON.
         *
         * <p>By default, this feature is disabled, meaning that null values are omitted from the output.</p>
         *
         * @since 2.0.0
         */
        WriteNulls(MASK_WRITE_MAP_NULL_VALUE),

        /**
         * Feature that determines whether to write null values for map entries during serialization.
         * When enabled, null values in maps will be included in the output JSON.
         *
         * <p>By default, this feature is disabled, meaning that null map values are omitted from the output.</p>
         *
         * @since 2.0.0
         */
        WriteMapNullValue(MASK_WRITE_MAP_NULL_VALUE),

        /**
         * Feature that enables browser-compatible JSON output.
         * When enabled, the output will be formatted to be compatible with browser JavaScript engines.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.0
         */
        BrowserCompatible(MASK_BROWSER_COMPATIBLE),

        /**
         * Feature that determines whether to write default values instead of null values during serialization.
         * When enabled, default values (0 for numbers, false for booleans, empty string for strings) will be
         * written instead of null values.
         *
         * <p>By default, this feature is disabled, meaning that null values are handled according to other features.</p>
         *
         * @since 2.0.0
         */
        NullAsDefaultValue(MASK_NULL_AS_DEFAULT_VALUE),

        /**
         * Feature that determines whether to write boolean values as numbers during serialization.
         * When enabled, boolean values will be serialized as 1 (for true) and 0 (for false) instead of
         * true and false literals.
         *
         * <p>By default, this feature is disabled, meaning that boolean values are written as true/false.</p>
         *
         * @since 2.0.0
         */
        WriteBooleanAsNumber(MASK_WRITE_BOOLEAN_AS_NUMBER),

        /**
         * Feature that determines whether to write non-string values as strings during serialization.
         * When enabled, numeric and other non-string values will be converted to their string representation.
         *
         * <p>By default, this feature is disabled, meaning that values are written in their native JSON types.</p>
         *
         * @since 2.0.0
         */
        WriteNonStringValueAsString(MASK_WRITE_NON_STRING_VALUE_AS_STRING),

        /**
         * Feature that determines whether to write class names during serialization.
         * When enabled, class names will be included in the output JSON, typically using a special "@type" field.
         *
         * <p>By default, this feature is disabled, meaning that class names are not included in the output.</p>
         *
         * @since 2.0.0
         */
        WriteClassName(MASK_WRITE_CLASS_NAME),

        /**
         * Feature that determines whether to write the root class name during serialization.
         * When enabled, the class name of the root object will be included in the output JSON.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.0
         */
        NotWriteRootClassName(1 << 10),

        /**
         * Feature that determines whether to write class names for HashMap and ArrayList during serialization.
         * When enabled, class names for these common collection types will be omitted from the output.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.0
         */
        NotWriteHashMapArrayListClassName(1 << 11),

        /**
         * Feature that determines whether to write default values during serialization.
         * When enabled, fields with default values (0 for numbers, false for booleans, etc.) will be omitted.
         *
         * <p>By default, this feature is disabled, meaning that all field values are written regardless of whether
         * they are default values.</p>
         *
         * @since 2.0.0
         */
        NotWriteDefaultValue(MASK_NOT_WRITE_DEFAULT_VALUE),

        /**
         * Feature that determines whether to write enum values using their name during serialization.
         * When enabled, enum values will be serialized as their name string rather than their ordinal value.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.0
         */
        WriteEnumsUsingName(MASK_WRITE_ENUMS_USING_NAME),

        /**
         * Feature that determines whether to write enum values using their toString() representation during serialization.
         * When enabled, enum values will be serialized using their toString() method rather than their name or ordinal.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.0
         */
        WriteEnumUsingToString(MASK_WRITE_ENUM_USING_TO_STRING),

        /**
         * Feature that determines whether to ignore errors when calling getter methods during serialization.
         * When enabled, exceptions thrown by getter methods will be ignored rather than propagated.
         *
         * <p>By default, this feature is disabled, meaning that getter method exceptions are propagated.</p>
         *
         * @since 2.0.0
         */
        IgnoreErrorGetter(1 << 15),

        /**
         * Feature that enables pretty-printed JSON output with formatting and indentation.
         * When enabled, the output JSON will be formatted with line breaks and indentation for readability.
         *
         * <p>By default, this feature is disabled, meaning that JSON is output in compact form.</p>
         *
         * @since 2.0.0
         */
        PrettyFormat(MASK_PRETTY_FORMAT),

        /**
         * Feature that enables reference detection during serialization.
         * When enabled, circular references and repeated objects will be detected and handled using
         * reference markers to avoid infinite loops and duplicate serialization.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.0
         */
        ReferenceDetection(MASK_REFERENCE_DETECTION),

        /**
         * Feature that determines whether to write field names as symbols during serialization.
         * When enabled, field names will be written as symbol references rather than string literals.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.0
         */
        WriteNameAsSymbol(1 << 18),

        /**
         * Feature that determines whether to write BigDecimal values in plain format during serialization.
         * When enabled, BigDecimal values will be written without exponential notation when possible.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.0
         */
        WriteBigDecimalAsPlain(1 << 19),

        /**
         * Feature that determines whether to use single quotes instead of double quotes for strings.
         * When enabled, string values will be enclosed in single quotes rather than double quotes.
         *
         * <p>By default, this feature is disabled, meaning that double quotes are used.</p>
         *
         * @since 2.0.0
         */
        UseSingleQuotes(MASK_USE_SINGLE_QUOTES),

        /**
         * The serialized Map will first be sorted according to Key,
         * and is used in some scenarios where serialized content needs to be signed.
         * SortedMap and derived classes do not need to do this.
         * This Feature does not work for LinkedHashMap.
         * @deprecated Use {@link Feature#SortMapEntriesByKeys} instead.
         * @since 2.0.0
         */
        MapSortField(1 << 21),

        /**
         * Feature that determines whether to write null lists as empty arrays during serialization.
         * When enabled, null collection values will be serialized as empty JSON arrays ([]) rather than null.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.0
         */
        WriteNullListAsEmpty(MASK_WRITE_NULL_LIST_AS_EMPTY),

        /**
         * Feature that determines whether to write null strings as empty strings during serialization.
         * When enabled, null string values will be serialized as empty strings ("") rather than null.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 1.1
         */
        WriteNullStringAsEmpty(MASK_WRITE_NULL_STRING_AS_EMPTY),

        /**
         * Feature that determines whether to write null numbers as zero during serialization.
         * When enabled, null numeric values will be serialized as 0 rather than null.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 1.1
         */
        WriteNullNumberAsZero(MASK_WRITE_NULL_NUMBER_AS_ZERO),

        /**
         * Feature that determines whether to write null booleans as false during serialization.
         * When enabled, null boolean values will be serialized as false rather than null.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 1.1
         */
        WriteNullBooleanAsFalse(MASK_WRITE_NULL_BOOLEAN_AS_FALSE),

        /**
         * Feature that determines whether to avoid writing empty arrays during serialization.
         * When enabled, empty arrays will be omitted from the output rather than written as [].
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @deprecated use IgnoreEmpty
         * @since 2.0.7
         */
        NotWriteEmptyArray(MASK_NOT_WRITE_EMPTY_ARRAY),

        /**
         * Feature that determines whether to ignore empty values during serialization.
         * When enabled, empty collections, empty strings, and other empty values will be omitted from the output.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.51
         */
        IgnoreEmpty(MASK_NOT_WRITE_EMPTY_ARRAY),

        /**
         * Feature that determines whether to write non-string keys as strings during serialization.
         * When enabled, map keys that are not strings will be converted to their string representation.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.0
         */
        WriteNonStringKeyAsString(1 << 27),

        /**
         * Feature that determines whether to write key-value pairs as Java beans during serialization.
         * When enabled, key-value pairs will be serialized using Java bean conventions.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.11
         */
        WritePairAsJavaBean(1L << 28),

        /**
         * Feature that enables optimization for ASCII characters during serialization.
         * When enabled, the serializer will use optimized paths for ASCII-only content.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.12
         */
        OptimizedForAscii(1L << 29),

        /**
         * Feature that specifies that all characters beyond 7-bit ASCII range (i.e. code points of 128 and above)
         * need to be output using format-specific escapes (for JSON, backslash escapes),
         * if format uses escaping mechanisms (which is generally true for textual formats but not for binary formats).
         * Feature is disabled by default.
         *
         * @since 2.0.12
         */
        EscapeNoneAscii(MASK_ESCAPE_NONE_ASCII),

        /**
         * Feature that determines whether to write byte arrays as Base64-encoded strings during serialization.
         * When enabled, byte array values will be serialized as Base64-encoded strings rather than arrays of numbers.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.13
         */
        WriteByteArrayAsBase64(1L << 31),

        /**
         * Feature that determines whether to ignore non-field getter methods during serialization.
         * When enabled, only getter methods that correspond to actual fields will be considered.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.13
         */
        IgnoreNonFieldGetter(MASK_IGNORE_NON_FIELD_GETTER),

        /**
         * Feature that enables support for large objects during serialization.
         * When enabled, the serializer will use configurations appropriate for very large object graphs.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.16
         */
        LargeObject(1L << 33),

        /**
         * Feature that determines whether to write long values as strings during serialization.
         * When enabled, long numeric values will be serialized as strings rather than numbers to avoid precision loss
         * in JavaScript and other environments with limited integer precision.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.17
         */
        WriteLongAsString(MASK_WRITE_LONG_AS_STRING),

        /**
         * Feature that enables browser security measures during serialization.
         * When enabled, the output will be formatted to be secure when used in browser environments.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.20
         */
        BrowserSecure(MASK_BROWSER_SECURE),

        /**
         * Feature that determines whether to write enum values using their ordinal value during serialization.
         * When enabled, enum values will be serialized as their ordinal (position) rather than their name.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.20
         */
        WriteEnumUsingOrdinal(1L << 36),

        /**
         * Feature that determines whether to write the class name of Throwable objects during serialization.
         * When enabled, the class name of exception and error objects will be included in the output.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.30
         */
        WriteThrowableClassName(1L << 37),

        /**
         * Feature that determines whether to write field names without quotes during serialization.
         * When enabled, field names in JSON objects will not be enclosed in quotes.
         *
         * <p>By default, this feature is disabled, meaning that field names are quoted.</p>
         *
         * @since 2.0.33
         */
        UnquoteFieldName(1L << 38),

        /**
         * Feature that determines whether to write class names for Set collections during serialization.
         * When enabled, class names for Set collections will be omitted from the output.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.34
         */
        NotWriteSetClassName(1L << 39),

        /**
         * Feature that determines whether to write class names for Number objects during serialization.
         * When enabled, class names for Number objects will be omitted from the output.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.34
         */
        NotWriteNumberClassName(MASK_NOT_WRITE_NUMBER_CLASS_NAME),

        /**
         * The serialized Map will first be sorted according to Key,
         * and is used in some scenarios where serialized content needs to be signed.
         * SortedMap and derived classes do not need to do this.
         *
         * @since 2.0.48
         */
        SortMapEntriesByKeys(1L << 41),

        /**
         * JSON formatting support using 2 spaces for indentation.
         * When enabled, pretty-printed JSON will use 2 spaces for each indentation level.
         *
         * <p>This feature requires {@link PrettyFormat} to also be enabled.</p>
         *
         * @since 2.0.54
         */
        PrettyFormatWith2Space(1L << 42),

        /**
         * JSON formatting support using 4 spaces for indentation.
         * When enabled, pretty-printed JSON will use 4 spaces for each indentation level.
         *
         * <p>This feature requires {@link PrettyFormat} to also be enabled.</p>
         *
         * @since 2.0.54
         */
        PrettyFormatWith4Space(1L << 43),

        /**
         * Feature that determines whether to write java.util.Date objects as milliseconds since epoch.
         * When enabled, Date objects will be serialized as numeric timestamps rather than formatted strings.
         *
         * <p>By default, this feature is disabled.</p>
         *
         * @since 2.0.0
         */
        WriterUtilDateAsMillis(1L << 44);

        public final long mask;

        Feature(long mask) {
            this.mask = mask;
        }

        /**
         * Checks if this feature is enabled in the specified features bitmask.
         *
         * @param features the features bitmask to check
         * @return true if this feature is enabled, false otherwise
         */
        public boolean isEnabled(long features) {
            return (features & mask) != 0;
        }

        public static long valueOf(Feature... features) {
            long value = 0;
            for (Feature feature : features) {
                value |= feature.mask;
            }
            return value;
        }

        public static long config(long features, Feature feature, boolean state) {
            if (state) {
                return features | feature.mask;
            } else {
                return features & ~feature.mask;
            }
        }
    }
}
