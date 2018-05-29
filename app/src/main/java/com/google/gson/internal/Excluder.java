package com.google.gson.internal;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Excluder implements TypeAdapterFactory, Cloneable {
    public static final Excluder DEFAULT;
    private static final double IGNORE_VERSIONS = -1.0d;
    private List<ExclusionStrategy> deserializationStrategies;
    private int modifiers;
    private boolean requireExpose;
    private List<ExclusionStrategy> serializationStrategies;
    private boolean serializeInnerClasses;
    private double version;

    class AnonymousClass_1 extends TypeAdapter<T> {
        private TypeAdapter<T> delegate;
        final /* synthetic */ Gson val$gson;
        final /* synthetic */ boolean val$skipDeserialize;
        final /* synthetic */ boolean val$skipSerialize;
        final /* synthetic */ TypeToken val$type;

        AnonymousClass_1(boolean z, boolean z2, Gson gson, TypeToken typeToken) {
            this.val$skipDeserialize = z;
            this.val$skipSerialize = z2;
            this.val$gson = gson;
            this.val$type = typeToken;
        }

        public T read(JsonReader in) throws IOException {
            if (!this.val$skipDeserialize) {
                return delegate().read(in);
            }
            in.skipValue();
            return null;
        }

        public void write(JsonWriter out, T value) throws IOException {
            if (this.val$skipSerialize) {
                out.nullValue();
            } else {
                delegate().write(out, value);
            }
        }

        private TypeAdapter<T> delegate() {
            TypeAdapter<T> d = this.delegate;
            if (d != null) {
                return d;
            }
            d = this.val$gson.getDelegateAdapter(Excluder.this, this.val$type);
            this.delegate = d;
            return d;
        }
    }

    public Excluder() {
        this.version = -1.0d;
        this.modifiers = 136;
        this.serializeInnerClasses = true;
        this.serializationStrategies = Collections.emptyList();
        this.deserializationStrategies = Collections.emptyList();
    }

    static {
        DEFAULT = new Excluder();
    }

    protected Excluder clone() {
        try {
            return (Excluder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Excluder withVersion(double ignoreVersionsAfter) {
        Excluder result = clone();
        result.version = ignoreVersionsAfter;
        return result;
    }

    public com.google.gson.internal.Excluder withModifiers(int... r7_modifiers) {
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.Excluder.withModifiers(int[]):com.google.gson.internal.Excluder");
        /* JADX: method processing error */
/*
        Error: java.lang.IndexOutOfBoundsException: bitIndex < 0: -1
	at java.util.BitSet.get(Unknown Source)
	at jadx.core.dex.visitors.CodeShrinker$ArgsInfo.usedArgAssign(CodeShrinker.java:137)
	at jadx.core.dex.visitors.CodeShrinker$ArgsInfo.access$300(CodeShrinker.java:42)
	at jadx.core.dex.visitors.CodeShrinker.canMoveBetweenBlocks(CodeShrinker.java:271)
	at jadx.core.dex.visitors.CodeShrinker.shrinkBlock(CodeShrinker.java:227)
	at jadx.core.dex.visitors.CodeShrinker.shrinkMethod(CodeShrinker.java:38)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkArrayForEach(LoopRegionVisitor.java:194)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkForIndexedLoop(LoopRegionVisitor.java:118)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.processLoopRegion(LoopRegionVisitor.java:64)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.enterRegion(LoopRegionVisitor.java:52)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:44)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:46)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseAll(DepthRegionTraversal.java:22)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.visit(LoopRegionVisitor.java:46)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:16)
	at jadx.core.ProcessClass.process(ProcessClass.java:22)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:209)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:133)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
	at java.lang.Thread.run(Unknown Source)
*/
        /*
        this = this;
        r4 = r6.clone();
        r5 = 0;
        r4.modifiers = r5;
        r0 = r7;
        r2 = r0.length;
        r1 = 0;
    L_0x000a:
        if (r1 >= r2) goto L_0x0016;
    L_0x000c:
        r3 = r0[r1];
        r5 = r4.modifiers;
        r5 = r5 | r3;
        r4.modifiers = r5;
        r1 = r1 + 1;
        goto L_0x000a;
    L_0x0016:
        return r4;
        */
    }

    public Excluder disableInnerClassSerialization() {
        Excluder result = clone();
        result.serializeInnerClasses = false;
        return result;
    }

    public Excluder excludeFieldsWithoutExposeAnnotation() {
        Excluder result = clone();
        result.requireExpose = true;
        return result;
    }

    public Excluder withExclusionStrategy(ExclusionStrategy exclusionStrategy, boolean serialization, boolean deserialization) {
        Excluder result = clone();
        if (serialization) {
            result.serializationStrategies = new ArrayList(this.serializationStrategies);
            result.serializationStrategies.add(exclusionStrategy);
        }
        if (deserialization) {
            result.deserializationStrategies = new ArrayList(this.deserializationStrategies);
            result.deserializationStrategies.add(exclusionStrategy);
        }
        return result;
    }

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<?> rawType = type.getRawType();
        boolean skipSerialize = excludeClass(rawType, true);
        boolean skipDeserialize = excludeClass(rawType, false);
        return (skipSerialize || skipDeserialize) ? new AnonymousClass_1(skipDeserialize, skipSerialize, gson, type) : null;
    }

    public boolean excludeField(Field field, boolean serialize) {
        if ((this.modifiers & field.getModifiers()) != 0) {
            return true;
        }
        if (this.version != -1.0d && !isValidVersion((Since) field.getAnnotation(Since.class), (Until) field.getAnnotation(Until.class))) {
            return true;
        }
        if (field.isSynthetic()) {
            return true;
        }
        if (this.requireExpose) {
            Expose annotation = (Expose) field.getAnnotation(Expose.class);
            if (annotation != null) {
                if (serialize) {
                }
            }
            return true;
        }
        if (!this.serializeInnerClasses && isInnerClass(field.getType())) {
            return true;
        }
        if (isAnonymousOrLocal(field.getType())) {
            return true;
        }
        List<ExclusionStrategy> list = serialize ? this.serializationStrategies : this.deserializationStrategies;
        if (!list.isEmpty()) {
            FieldAttributes fieldAttributes = new FieldAttributes(field);
            for (ExclusionStrategy exclusionStrategy : list) {
                if (exclusionStrategy.shouldSkipField(fieldAttributes)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean excludeClass(Class<?> clazz, boolean serialize) {
        if (this.version != -1.0d && !isValidVersion((Since) clazz.getAnnotation(Since.class), (Until) clazz.getAnnotation(Until.class))) {
            return true;
        }
        if (!this.serializeInnerClasses && isInnerClass(clazz)) {
            return true;
        }
        if (isAnonymousOrLocal(clazz)) {
            return true;
        }
        for (ExclusionStrategy exclusionStrategy : serialize ? this.serializationStrategies : this.deserializationStrategies) {
            if (exclusionStrategy.shouldSkipClass(clazz)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAnonymousOrLocal(Class<?> clazz) {
        return !Enum.class.isAssignableFrom(clazz) && (clazz.isAnonymousClass() || clazz.isLocalClass());
    }

    private boolean isInnerClass(Class<?> clazz) {
        return clazz.isMemberClass() && !isStatic(clazz);
    }

    private boolean isStatic(Class<?> clazz) {
        return (clazz.getModifiers() & 8) != 0;
    }

    private boolean isValidVersion(Since since, Until until) {
        return isValidSince(since) && isValidUntil(until);
    }

    private boolean isValidSince(Since annotation) {
        return annotation == null || annotation.value() <= this.version;
    }

    private boolean isValidUntil(Until annotation) {
        return annotation == null || annotation.value() > this.version;
    }
}
