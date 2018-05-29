package android.support.v4.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import android.support.v4.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

@RequiresApi(26)
@RestrictTo({Scope.LIBRARY_GROUP})
public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
    private static final String ABORT_CREATION_METHOD = "abortCreation";
    private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
    private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String FREEZE_METHOD = "freeze";
    private static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String TAG = "TypefaceCompatApi26Impl";
    private static final Method sAbortCreation;
    private static final Method sAddFontFromAssetManager;
    private static final Method sAddFontFromBuffer;
    private static final Method sCreateFromFamiliesWithDefault;
    private static final Class sFontFamily;
    private static final Constructor sFontFamilyCtor;
    private static final Method sFreeze;

    static {
        Class fontFamilyClass;
        Constructor fontFamilyCtor;
        Method addFontMethod;
        Method addFromBufferMethod;
        Method freezeMethod;
        Method abortCreationMethod;
        Method createFromFamiliesWithDefaultMethod;
        try {
            fontFamilyClass = Class.forName(FONT_FAMILY_CLASS);
            fontFamilyCtor = fontFamilyClass.getConstructor(new Class[0]);
            addFontMethod = fontFamilyClass.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, new Class[]{AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class});
            addFromBufferMethod = fontFamilyClass.getMethod(ADD_FONT_FROM_BUFFER_METHOD, new Class[]{ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE});
            freezeMethod = fontFamilyClass.getMethod(FREEZE_METHOD, new Class[0]);
            abortCreationMethod = fontFamilyClass.getMethod(ABORT_CREATION_METHOD, new Class[0]);
            Object familyArray = Array.newInstance(fontFamilyClass, 1);
            createFromFamiliesWithDefaultMethod = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, new Class[]{familyArray.getClass(), Integer.TYPE, Integer.TYPE});
            createFromFamiliesWithDefaultMethod.setAccessible(true);
        } catch (ClassNotFoundException e) {
            ReflectiveOperationException e2 = e;
            Log.e(TAG, "Unable to collect necessary methods for class " + e2.getClass().getName(), e2);
            fontFamilyClass = null;
            fontFamilyCtor = null;
            addFontMethod = null;
            addFromBufferMethod = null;
            freezeMethod = null;
            abortCreationMethod = null;
            createFromFamiliesWithDefaultMethod = null;
            sFontFamilyCtor = fontFamilyCtor;
            sFontFamily = fontFamilyClass;
            sAddFontFromAssetManager = addFontMethod;
            sAddFontFromBuffer = addFromBufferMethod;
            sFreeze = freezeMethod;
            sAbortCreation = abortCreationMethod;
            sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
        } catch (NoSuchMethodException e3) {
            e2 = e3;
            Log.e(TAG, "Unable to collect necessary methods for class " + e2.getClass().getName(), e2);
            fontFamilyClass = null;
            fontFamilyCtor = null;
            addFontMethod = null;
            addFromBufferMethod = null;
            freezeMethod = null;
            abortCreationMethod = null;
            createFromFamiliesWithDefaultMethod = null;
            sFontFamilyCtor = fontFamilyCtor;
            sFontFamily = fontFamilyClass;
            sAddFontFromAssetManager = addFontMethod;
            sAddFontFromBuffer = addFromBufferMethod;
            sFreeze = freezeMethod;
            sAbortCreation = abortCreationMethod;
            sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
        }
        sFontFamilyCtor = fontFamilyCtor;
        sFontFamily = fontFamilyClass;
        sAddFontFromAssetManager = addFontMethod;
        sAddFontFromBuffer = addFromBufferMethod;
        sFreeze = freezeMethod;
        sAbortCreation = abortCreationMethod;
        sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
    }

    private static boolean isFontFamilyPrivateAPIAvailable() {
        if (sAddFontFromAssetManager == null) {
            Log.w(TAG, "Unable to collect necessary private methods.Fallback to legacy implementation.");
        }
        return sAddFontFromAssetManager != null;
    }

    private static Object newFamily() {
        try {
            return sFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException e) {
            ReflectiveOperationException e2 = e;
            throw new RuntimeException(e2);
        } catch (InstantiationException e3) {
            e2 = e3;
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e4) {
            e2 = e4;
            throw new RuntimeException(e2);
        }
    }

    private static boolean addFontFromAssetManager(Context context, Object family, String fileName, int ttcIndex, int weight, int style) {
        try {
            return ((Boolean) sAddFontFromAssetManager.invoke(family, new Object[]{context.getAssets(), fileName, Integer.valueOf(0), Boolean.valueOf(false), Integer.valueOf(ttcIndex), Integer.valueOf(weight), Integer.valueOf(style), null})).booleanValue();
        } catch (IllegalAccessException e) {
            ReflectiveOperationException e2 = e;
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            e2 = e3;
            throw new RuntimeException(e2);
        }
    }

    private static boolean addFontFromBuffer(Object family, ByteBuffer buffer, int ttcIndex, int weight, int style) {
        try {
            return ((Boolean) sAddFontFromBuffer.invoke(family, new Object[]{buffer, Integer.valueOf(ttcIndex), null, Integer.valueOf(weight), Integer.valueOf(style)})).booleanValue();
        } catch (IllegalAccessException e) {
            ReflectiveOperationException e2 = e;
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            e2 = e3;
            throw new RuntimeException(e2);
        }
    }

    private static Typeface createFromFamiliesWithDefault(Object family) {
        try {
            Array.set(Array.newInstance(sFontFamily, 1), 0, family);
            return (Typeface) sCreateFromFamiliesWithDefault.invoke(null, new Object[]{familyArray, Integer.valueOf(RESOLVE_BY_FONT_TABLE), Integer.valueOf(RESOLVE_BY_FONT_TABLE)});
        } catch (IllegalAccessException e) {
            ReflectiveOperationException e2 = e;
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            e2 = e3;
            throw new RuntimeException(e2);
        }
    }

    private static boolean freeze(Object family) {
        try {
            return ((Boolean) sFreeze.invoke(family, new Object[0])).booleanValue();
        } catch (IllegalAccessException e) {
            ReflectiveOperationException e2 = e;
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            e2 = e3;
            throw new RuntimeException(e2);
        }
    }

    private static boolean abortCreation(Object family) {
        try {
            return ((Boolean) sAbortCreation.invoke(family, new Object[0])).booleanValue();
        } catch (IllegalAccessException e) {
            ReflectiveOperationException e2 = e;
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            e2 = e3;
            throw new RuntimeException(e2);
        }
    }

    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontFamilyFilesResourceEntry entry, Resources resources, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, entry, resources, style);
        }
        Object fontFamily = newFamily();
        FontFileResourceEntry[] entries = entry.getEntries();
        int length = entries.length;
        int i = 0;
        while (i < length) {
            int i2;
            FontFileResourceEntry fontFile = entries[i];
            String fileName = fontFile.getFileName();
            int weight = fontFile.getWeight();
            if (fontFile.isItalic()) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            if (addFontFromAssetManager(context, fontFamily, fileName, 0, weight, i2)) {
                i++;
            } else {
                abortCreation(fontFamily);
                return null;
            }
        }
        return !freeze(fontFamily) ? null : createFromFamiliesWithDefault(fontFamily);
    }

    public android.graphics.Typeface createFromFontInfo(android.content.Context r20_context, @android.support.annotation.Nullable android.os.CancellationSignal r21_cancellationSignal, @android.support.annotation.NonNull android.support.v4.provider.FontsContractCompat.FontInfo[] r22_fonts, int r23_style) {
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.TypefaceCompatApi26Impl.createFromFontInfo(android.content.Context, android.os.CancellationSignal, android.support.v4.provider.FontsContractCompat$FontInfo[], int):android.graphics.Typeface");
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Try/catch wrap count limit reached in android.support.v4.graphics.TypefaceCompatApi26Impl.createFromFontInfo(android.content.Context, android.os.CancellationSignal, android.support.v4.provider.FontsContractCompat$FontInfo[], int):android.graphics.Typeface
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:54)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:40)
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
        r0 = r22;
        r13 = r0.length;
        r14 = 1;
        if (r13 >= r14) goto L_0x0008;
    L_0x0006:
        r13 = 0;
    L_0x0007:
        return r13;
    L_0x0008:
        r13 = isFontFamilyPrivateAPIAvailable();
        if (r13 != 0) goto L_0x0073;
    L_0x000e:
        r0 = r19;
        r1 = r22;
        r2 = r23;
        r4 = r0.findBestInfo(r1, r2);
        r10 = r20.getContentResolver();
        r13 = r4.getUri();	 Catch:{ IOException -> 0x0053 }
        r14 = "r";
        r0 = r21;
        r9 = r10.openFileDescriptor(r13, r14, r0);	 Catch:{ IOException -> 0x0053 }
        r14 = 0;
        r13 = new android.graphics.Typeface$Builder;	 Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
        r15 = r9.getFileDescriptor();	 Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
        r13.<init>(r15);	 Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
        r15 = r4.getWeight();	 Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
        r13 = r13.setWeight(r15);	 Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
        r15 = r4.isItalic();	 Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
        r13 = r13.setItalic(r15);	 Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
        r13 = r13.build();	 Catch:{ Throwable -> 0x005a, all -> 0x00d5 }
        if (r9 == 0) goto L_0x0007;
    L_0x0048:
        if (r14 == 0) goto L_0x0056;
    L_0x004a:
        r9.close();	 Catch:{ Throwable -> 0x004e }
        goto L_0x0007;
    L_0x004e:
        r15 = move-exception;
        r14.addSuppressed(r15);	 Catch:{ IOException -> 0x0053 }
        goto L_0x0007;
    L_0x0053:
        r5 = move-exception;
        r13 = 0;
        goto L_0x0007;
    L_0x0056:
        r9.close();	 Catch:{ IOException -> 0x0053 }
        goto L_0x0007;
    L_0x005a:
        r13 = move-exception;
        throw r13;	 Catch:{ all -> 0x005c }
    L_0x005c:
        r14 = move-exception;
        r18 = r14;
        r14 = r13;
        r13 = r18;
    L_0x0062:
        if (r9 == 0) goto L_0x0069;
    L_0x0064:
        if (r14 == 0) goto L_0x006f;
    L_0x0066:
        r9.close();	 Catch:{ Throwable -> 0x006a }
    L_0x0069:
        throw r13;	 Catch:{ IOException -> 0x0053 }
    L_0x006a:
        r15 = move-exception;
        r14.addSuppressed(r15);	 Catch:{ IOException -> 0x0053 }
        goto L_0x0069;
    L_0x006f:
        r9.close();	 Catch:{ IOException -> 0x0053 }
        goto L_0x0069;
    L_0x0073:
        r0 = r20;
        r1 = r22;
        r2 = r21;
        r12 = android.support.v4.provider.FontsContractCompat.prepareFontData(r0, r1, r2);
        r8 = newFamily();
        r3 = 0;
        r0 = r22;
        r15 = r0.length;
        r13 = 0;
        r14 = r13;
    L_0x0087:
        if (r14 >= r15) goto L_0x00be;
    L_0x0089:
        r6 = r22[r14];
        r13 = r6.getUri();
        r7 = r12.get(r13);
        r7 = (java.nio.ByteBuffer) r7;
        if (r7 != 0) goto L_0x009b;
    L_0x0097:
        r13 = r14 + 1;
        r14 = r13;
        goto L_0x0087;
    L_0x009b:
        r16 = r6.getTtcIndex();
        r17 = r6.getWeight();
        r13 = r6.isItalic();
        if (r13 == 0) goto L_0x00ba;
    L_0x00a9:
        r13 = 1;
    L_0x00aa:
        r0 = r16;
        r1 = r17;
        r11 = addFontFromBuffer(r8, r7, r0, r1, r13);
        if (r11 != 0) goto L_0x00bc;
    L_0x00b4:
        abortCreation(r8);
        r13 = 0;
        goto L_0x0007;
    L_0x00ba:
        r13 = 0;
        goto L_0x00aa;
    L_0x00bc:
        r3 = 1;
        goto L_0x0097;
    L_0x00be:
        if (r3 != 0) goto L_0x00c6;
    L_0x00c0:
        abortCreation(r8);
        r13 = 0;
        goto L_0x0007;
    L_0x00c6:
        r13 = freeze(r8);
        if (r13 != 0) goto L_0x00cf;
    L_0x00cc:
        r13 = 0;
        goto L_0x0007;
    L_0x00cf:
        r13 = createFromFamiliesWithDefault(r8);
        goto L_0x0007;
    L_0x00d5:
        r13 = move-exception;
        goto L_0x0062;
        */
    }

    @Nullable
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, resources, id, path, style);
        }
        Object fontFamily = newFamily();
        if (addFontFromAssetManager(context, fontFamily, path, 0, RESOLVE_BY_FONT_TABLE, -1)) {
            return !freeze(fontFamily) ? null : createFromFamiliesWithDefault(fontFamily);
        } else {
            abortCreation(fontFamily);
            return null;
        }
    }
}
