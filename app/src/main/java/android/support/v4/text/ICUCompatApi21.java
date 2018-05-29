package android.support.v4.text;

import android.support.annotation.RequiresApi;
import java.lang.reflect.Method;
import java.util.Locale;

@RequiresApi(21)
class ICUCompatApi21 {
    private static final String TAG = "ICUCompatApi21";
    private static Method sAddLikelySubtagsMethod;

    ICUCompatApi21() {
    }

    static {
        try {
            sAddLikelySubtagsMethod = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", new Class[]{Locale.class});
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String maximizeAndGetScript(java.util.Locale r4_locale) {
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.text.ICUCompatApi21.maximizeAndGetScript(java.util.Locale):java.lang.String");
        /*
        r2 = 1;
        r0 = new java.lang.Object[r2];	 Catch:{ InvocationTargetException -> 0x0014, IllegalAccessException -> 0x001f }
        r2 = 0;
        r0[r2] = r4;	 Catch:{ InvocationTargetException -> 0x0014, IllegalAccessException -> 0x001f }
        r2 = sAddLikelySubtagsMethod;	 Catch:{ InvocationTargetException -> 0x0014, IllegalAccessException -> 0x001f }
        r3 = 0;
        r2 = r2.invoke(r3, r0);	 Catch:{ InvocationTargetException -> 0x0014, IllegalAccessException -> 0x001f }
        r2 = (java.util.Locale) r2;	 Catch:{ InvocationTargetException -> 0x0014, IllegalAccessException -> 0x001f }
        r2 = r2.getScript();	 Catch:{ InvocationTargetException -> 0x0014, IllegalAccessException -> 0x001f }
    L_0x0013:
        return r2;
    L_0x0014:
        r1 = move-exception;
        r2 = "ICUCompatApi21";
        android.util.Log.w(r2, r1);
    L_0x001a:
        r2 = r4.getScript();
        goto L_0x0013;
    L_0x001f:
        r1 = move-exception;
        r2 = "ICUCompatApi21";
        android.util.Log.w(r2, r1);
        goto L_0x001a;
        */
    }
}
