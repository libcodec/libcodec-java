package io.libcodec.meta;

import io.libcodec.serde.AutoTypeBeforeHandler;
import io.libcodec.serde.Filter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Locale;

public class BeanInfo {
    public String typeKey;
    public String typeName;
    public Class builder;
    public Method buildMethod;
    public String builderWithPrefix;
    public Class[] seeAlso;
    public String[] seeAlsoNames;
    public Class seeAlsoDefault;
    public Constructor creatorConstructor;
    public Constructor markerConstructor;
    public Method createMethod;
    public String[] createParameterNames;

    public long readerFeatures;
    public long writerFeatures;

    public boolean writeEnumAsJavaBean;

    public String namingStrategy;
    public String[] ignores;
    public String[] orders;
    public String[] includes;

    public boolean mixIn;
    public boolean kotlin;

    public Class serializer;
    public Class deserializer;
    public Class<? extends Filter>[] serializeFilters;
    public String schema;
    public String format;
    public Locale locale;
    public boolean alphabetic = true;
    public String objectWriterFieldName;
    public String objectReaderFieldName;
    public Class<? extends AutoTypeBeforeHandler> autoTypeBeforeHandler;
    public String rootName;
    public boolean skipTransient = true;
}
