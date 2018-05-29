package com.google.protobuf;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

final class zze {
    private static final Logger logger;
    private static final Unsafe zzcuB;
    private static final Class<?> zzcuC;
    private static final boolean zzcuD;
    private static final boolean zzcuE;
    private static final boolean zzcuF;
    private static final zzd zzcuG;
    private static final boolean zzcuH;
    private static final boolean zzcuI;
    private static final long zzcuJ;
    private static final boolean zzcuK;
    private static final boolean zzcuu;
    private static final long zzcuv;

    static abstract class zzd {
        Unsafe zzcuL;

        zzd(Unsafe unsafe) {
            this.zzcuL = unsafe;
        }
    }

    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }
    }

    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }
    }

    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.zze.<clinit>():void");
        /*
        r3 = 0;
        r1 = 1;
        r2 = 0;
        r0 = com.google.protobuf.zze.class;
        r0 = r0.getName();
        r0 = java.util.logging.Logger.getLogger(r0);
        logger = r0;
        r0 = zzLL();
        zzcuB = r0;
        r0 = "libcore.io.Memory";
        r0 = zzio(r0);
        zzcuC = r0;
        r0 = "org.robolectric.Robolectric";
        r0 = zzio(r0);
        if (r0 == 0) goto L_0x0087;
    L_0x0025:
        r0 = r1;
    L_0x0026:
        zzcuD = r0;
        r0 = java.lang.Long.TYPE;
        r0 = zzg(r0);
        zzcuE = r0;
        r0 = java.lang.Integer.TYPE;
        r0 = zzg(r0);
        zzcuF = r0;
        r0 = zzcuB;
        if (r0 != 0) goto L_0x0089;
    L_0x003c:
        r0 = r3;
    L_0x003d:
        zzcuG = r0;
        r0 = zzLO();
        zzcuH = r0;
        r0 = zzLM();
        zzcuu = r0;
        r0 = zzLN();
        zzcuI = r0;
        r0 = zzcuu;
        if (r0 == 0) goto L_0x00b1;
    L_0x0055:
        r0 = zzcuG;
        r3 = byte[].class;
        r0 = r0.zzcuL;
        r0 = r0.arrayBaseOffset(r3);
    L_0x005f:
        r4 = (long) r0;
        zzcuv = r4;
        r0 = zzLP();
        if (r0 == 0) goto L_0x00b3;
    L_0x0068:
        r0 = java.nio.Buffer.class;
        r3 = "effectiveDirectAddress";
        r0 = zza(r0, r3);
        if (r0 == 0) goto L_0x00b3;
    L_0x0072:
        if (r0 == 0) goto L_0x0078;
    L_0x0074:
        r3 = zzcuG;
        if (r3 != 0) goto L_0x00bc;
    L_0x0078:
        r4 = -1;
    L_0x007a:
        zzcuJ = r4;
        r0 = java.nio.ByteOrder.nativeOrder();
        r3 = java.nio.ByteOrder.BIG_ENDIAN;
        if (r0 != r3) goto L_0x00c5;
    L_0x0084:
        zzcuK = r1;
        return;
    L_0x0087:
        r0 = r2;
        goto L_0x0026;
    L_0x0089:
        r0 = zzLP();
        if (r0 == 0) goto L_0x00a9;
    L_0x008f:
        r0 = zzcuE;
        if (r0 == 0) goto L_0x009b;
    L_0x0093:
        r0 = new com.google.protobuf.zze$zzb;
        r3 = zzcuB;
        r0.<init>(r3);
        goto L_0x003d;
    L_0x009b:
        r0 = zzcuF;
        if (r0 == 0) goto L_0x00a7;
    L_0x009f:
        r0 = new com.google.protobuf.zze$zza;
        r3 = zzcuB;
        r0.<init>(r3);
        goto L_0x003d;
    L_0x00a7:
        r0 = r3;
        goto L_0x003d;
    L_0x00a9:
        r0 = new com.google.protobuf.zze$zzc;
        r3 = zzcuB;
        r0.<init>(r3);
        goto L_0x003d;
    L_0x00b1:
        r0 = -1;
        goto L_0x005f;
    L_0x00b3:
        r0 = java.nio.Buffer.class;
        r3 = "address";
        r0 = zza(r0, r3);
        goto L_0x0072;
    L_0x00bc:
        r3 = zzcuG;
        r3 = r3.zzcuL;
        r4 = r3.objectFieldOffset(r0);
        goto L_0x007a;
    L_0x00c5:
        r1 = r2;
        goto L_0x0084;
        */
    }

    private zze() {
    }

    static boolean zzLJ() {
        return zzcuu;
    }

    static long zzLK() {
        return zzcuv;
    }

    private static Unsafe zzLL() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzf());
        } catch (Throwable th) {
            return null;
        }
    }

    private static boolean zzLM() {
        if (zzcuB == null) {
            return false;
        }
        try {
            Class cls = zzcuB.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod("arrayBaseOffset", new Class[]{Class.class});
            cls.getMethod("getInt", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putInt", new Class[]{Object.class, Long.TYPE, Integer.TYPE});
            cls.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putLong", new Class[]{Object.class, Long.TYPE, Long.TYPE});
            cls.getMethod("getObject", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putObject", new Class[]{Object.class, Long.TYPE, Object.class});
            if (zzLP()) {
                return true;
            }
            cls.getMethod("getByte", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putByte", new Class[]{Object.class, Long.TYPE, Byte.TYPE});
            cls.getMethod("getBoolean", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putBoolean", new Class[]{Object.class, Long.TYPE, Boolean.TYPE});
            cls.getMethod("getFloat", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putFloat", new Class[]{Object.class, Long.TYPE, Float.TYPE});
            cls.getMethod("getDouble", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putDouble", new Class[]{Object.class, Long.TYPE, Double.TYPE});
            return true;
        } catch (Throwable th) {
            String valueOf = String.valueOf(th);
            logger.logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", new StringBuilder(String.valueOf(valueOf).length() + 71).append("platform method missing - proto runtime falling back to safer methods: ").append(valueOf).toString());
            return false;
        }
    }

    private static boolean zzLN() {
        boolean z = false;
        if (zzcuB == null) {
            return false;
        }
        try {
            zzcuB.getClass().getMethod("copyMemory", new Class[]{Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE});
            z = true;
            return z;
        } catch (Throwable th) {
            logger.logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "supportsUnsafeCopyMemory", "copyMemory is missing from platform - proto runtime falling back to safer methods.");
            return z;
        }
    }

    private static boolean zzLO() {
        if (zzcuB == null) {
            return false;
        }
        try {
            Class cls = zzcuB.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            if (zzLP()) {
                return true;
            }
            cls.getMethod("getByte", new Class[]{Long.TYPE});
            cls.getMethod("putByte", new Class[]{Long.TYPE, Byte.TYPE});
            cls.getMethod("getInt", new Class[]{Long.TYPE});
            cls.getMethod("putInt", new Class[]{Long.TYPE, Integer.TYPE});
            cls.getMethod("getLong", new Class[]{Long.TYPE});
            cls.getMethod("putLong", new Class[]{Long.TYPE, Long.TYPE});
            cls.getMethod("copyMemory", new Class[]{Long.TYPE, Long.TYPE, Long.TYPE});
            return true;
        } catch (Throwable th) {
            String valueOf = String.valueOf(th);
            logger.logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", new StringBuilder(String.valueOf(valueOf).length() + 71).append("platform method missing - proto runtime falling back to safer methods: ").append(valueOf).toString());
            return false;
        }
    }

    private static boolean zzLP() {
        return (zzcuC == null || zzcuD) ? false : true;
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable th) {
            return null;
        }
    }

    private static boolean zzg(Class<?> cls) {
        boolean z = false;
        if (!zzLP()) {
            return false;
        }
        try {
            Class cls2 = zzcuC;
            cls2.getMethod("peekLong", new Class[]{cls, Boolean.TYPE});
            cls2.getMethod("pokeLong", new Class[]{cls, Long.TYPE, Boolean.TYPE});
            cls2.getMethod("pokeInt", new Class[]{cls, Integer.TYPE, Boolean.TYPE});
            cls2.getMethod("peekInt", new Class[]{cls, Boolean.TYPE});
            cls2.getMethod("pokeByte", new Class[]{cls, Byte.TYPE});
            cls2.getMethod("peekByte", new Class[]{cls});
            cls2.getMethod("pokeByteArray", new Class[]{cls, byte[].class, Integer.TYPE, Integer.TYPE});
            cls2.getMethod("peekByteArray", new Class[]{cls, byte[].class, Integer.TYPE, Integer.TYPE});
            z = true;
            return z;
        } catch (Throwable th) {
            return z;
        }
    }

    private static <T> Class<T> zzio(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable th) {
            return null;
        }
    }
}
