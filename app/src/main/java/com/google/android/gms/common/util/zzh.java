package com.google.android.gms.common.util;

import android.content.Context;
import android.os.DropBoxManager;
import android.util.Log;
import com.google.android.gms.common.internal.zzbr;

public final class zzh {
    private static final String[] zzaJF;
    private static DropBoxManager zzaJG;
    private static boolean zzaJH;
    private static int zzaJI;
    private static int zzaJJ;

    static {
        zzaJF = new String[]{"android.", "com.android.", "dalvik.", "java.", "javax."};
        zzaJG = null;
        zzaJH = false;
        zzaJI = -1;
        zzaJJ = 0;
    }

    public static boolean zza(Context context, Throwable th) {
        return zza(context, th, 0);
    }

    private static boolean zza(Context context, Throwable th, int i) {
        try {
            zzbr.zzu(context);
            zzbr.zzu(th);
        } catch (Throwable e) {
            Log.e("CrashUtils", "Error adding exception to DropBox!", e);
        }
        return false;
    }
}
