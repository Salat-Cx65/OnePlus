package android.support.v4.media;

import android.support.annotation.RequiresApi;
import java.lang.reflect.Constructor;

@RequiresApi(21)
class ParceledListSliceAdapterApi21 {
    private static Constructor sConstructor;

    ParceledListSliceAdapterApi21() {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.ParceledListSliceAdapterApi21.<clinit>():void");
        /*
        r2 = "android.content.pm.ParceledListSlice";
        r1 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x0015, NoSuchMethodException -> 0x001a }
        r2 = 1;
        r2 = new java.lang.Class[r2];	 Catch:{ ClassNotFoundException -> 0x0015, NoSuchMethodException -> 0x001a }
        r3 = 0;
        r4 = java.util.List.class;
        r2[r3] = r4;	 Catch:{ ClassNotFoundException -> 0x0015, NoSuchMethodException -> 0x001a }
        r2 = r1.getConstructor(r2);	 Catch:{ ClassNotFoundException -> 0x0015, NoSuchMethodException -> 0x001a }
        sConstructor = r2;	 Catch:{ ClassNotFoundException -> 0x0015, NoSuchMethodException -> 0x001a }
    L_0x0014:
        return;
    L_0x0015:
        r0 = move-exception;
    L_0x0016:
        r0.printStackTrace();
        goto L_0x0014;
    L_0x001a:
        r0 = move-exception;
        goto L_0x0016;
        */
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.Object newInstance(java.util.List<android.media.browse.MediaBrowser.MediaItem> r5_itemList) {
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.ParceledListSliceAdapterApi21.newInstance(java.util.List):java.lang.Object");
        /*
        r1 = 0;
        r2 = sConstructor;	 Catch:{ InstantiationException -> 0x000e, IllegalAccessException -> 0x0013, InvocationTargetException -> 0x0015 }
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ InstantiationException -> 0x000e, IllegalAccessException -> 0x0013, InvocationTargetException -> 0x0015 }
        r4 = 0;
        r3[r4] = r5;	 Catch:{ InstantiationException -> 0x000e, IllegalAccessException -> 0x0013, InvocationTargetException -> 0x0015 }
        r1 = r2.newInstance(r3);	 Catch:{ InstantiationException -> 0x000e, IllegalAccessException -> 0x0013, InvocationTargetException -> 0x0015 }
    L_0x000d:
        return r1;
    L_0x000e:
        r0 = move-exception;
    L_0x000f:
        r0.printStackTrace();
        goto L_0x000d;
    L_0x0013:
        r0 = move-exception;
        goto L_0x000f;
    L_0x0015:
        r0 = move-exception;
        goto L_0x000f;
        */
    }
}
