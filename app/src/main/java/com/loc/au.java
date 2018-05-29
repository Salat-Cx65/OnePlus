package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.lang.reflect.Constructor;

// compiled from: InstanceFactory.java
public final class au {

    // compiled from: InstanceFactory.java
    static class AnonymousClass_1 implements Runnable {
        final /* synthetic */ Context a;
        final /* synthetic */ String b;

        AnonymousClass_1(Context context, String str) {
            this.a = context;
            this.b = str;
        }

        public final void run() {
            try {
                ay.a(new af(this.a, bb.b()), this.a, this.b);
            } catch (Throwable th) {
                w.a(th, "InstanceFactory", "rollBack");
            }
        }
    }

    public static <T> T a(Context context, s sVar, String str, Class cls, Class[] clsArr, Object[] objArr) throws j {
        T a = a(b(context, sVar), str, clsArr, objArr);
        if (a == null) {
            a = a(cls, clsArr, objArr);
            if (a == null) {
                throw new j("\u83b7\u53d6\u5bf9\u8c61\u9519\u8bef");
            }
        }
        return a;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static <T> T a(com.loc.av r3, java.lang.String r4, java.lang.Class[] r5, java.lang.Object[] r6) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.au.a(com.loc.av, java.lang.String, java.lang.Class[], java.lang.Object[]):T");
        /*
        r0 = 1;
        if (r3 == 0) goto L_0x0022;
    L_0x0003:
        r1 = r3.a();	 Catch:{ Throwable -> 0x0024 }
        if (r1 == 0) goto L_0x0022;
    L_0x0009:
        r1 = r3.d;	 Catch:{ Throwable -> 0x0024 }
        if (r1 == 0) goto L_0x0022;
    L_0x000d:
        if (r0 == 0) goto L_0x002c;
    L_0x000f:
        r0 = r3.loadClass(r4);	 Catch:{ Throwable -> 0x0024 }
        if (r0 == 0) goto L_0x002c;
    L_0x0015:
        r0 = r0.getDeclaredConstructor(r5);	 Catch:{ Throwable -> 0x0024 }
        r1 = 1;
        r0.setAccessible(r1);	 Catch:{ Throwable -> 0x0024 }
        r0 = r0.newInstance(r6);	 Catch:{ Throwable -> 0x0024 }
    L_0x0021:
        return r0;
    L_0x0022:
        r0 = 0;
        goto L_0x000d;
    L_0x0024:
        r0 = move-exception;
        r1 = "IFactory";
        r2 = "getWrap";
        com.loc.w.a(r0, r1, r2);
    L_0x002c:
        r0 = 0;
        goto L_0x0021;
        */
    }

    private static <T> T a(Class cls, Class[] clsArr, Object[] objArr) {
        if (cls == null) {
            return null;
        }
        try {
            Constructor declaredConstructor = cls.getDeclaredConstructor(clsArr);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(objArr);
        } catch (Throwable th) {
            w.a(th, "IFactory", "gIns2()");
            return null;
        }
    }

    public static void a(Context context, at atVar, s sVar) {
        if (atVar != null) {
            try {
                if (!TextUtils.isEmpty(atVar.a()) && !TextUtils.isEmpty(atVar.b()) && !TextUtils.isEmpty(atVar.c)) {
                    new as(context, atVar, sVar).a();
                }
            } catch (Throwable th) {
                w.a(th, "IFactory", "dDownload()");
            }
        }
    }

    public static void a(Context context, String str) {
        try {
            z.b().submit(new AnonymousClass_1(context, str));
        } catch (Throwable th) {
            w.a(th, "InstanceFactory", "rollBack");
        }
    }

    public static boolean a(Context context, s sVar) {
        try {
            File file = new File(ay.b(context, sVar.a(), sVar.b()));
            if (file.exists()) {
                return true;
            }
            File parentFile = file.getParentFile();
            if (file.exists() || parentFile == null || !parentFile.exists()) {
                return false;
            }
            ay.c(context, sVar.a(), sVar.b());
            return false;
        } catch (Throwable th) {
            w.a(th, "IFactory", "isdowned");
            return false;
        }
    }

    private static av b(Context context, s sVar) {
        try {
            return a(context, sVar) ? aw.a().a(context, sVar) : null;
        } catch (Throwable th) {
            w.a(th, "IFactory", "gIns1");
            return null;
        }
    }
}
