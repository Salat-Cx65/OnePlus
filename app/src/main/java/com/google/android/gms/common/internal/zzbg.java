package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import com.google.android.gms.internal.zzbim;

public final class zzbg {
    private static boolean zzRm;
    private static String zzaIh;
    private static int zzaIi;
    private static Object zzuI;

    static {
        zzuI = new Object();
    }

    public static String zzaD(Context context) {
        zzaF(context);
        return zzaIh;
    }

    public static int zzaE(Context context) {
        zzaF(context);
        return zzaIi;
    }

    private static void zzaF(Context context) {
        synchronized (zzuI) {
            if (zzRm) {
                return;
            }
            zzRm = true;
            try {
                Bundle bundle = zzbim.zzaP(context).getApplicationInfo(context.getPackageName(), AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS).metaData;
                if (bundle == null) {
                    return;
                }
                zzaIh = bundle.getString("com.google.app.id");
                zzaIi = bundle.getInt("com.google.android.gms.version");
            } catch (Throwable e) {
                Log.wtf("MetadataValueReader", "This should never happen.", e);
            }
        }
    }
}
