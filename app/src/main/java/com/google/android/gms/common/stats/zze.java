package com.google.android.gms.common.stats;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.util.zzl;
import com.google.android.gms.location.DetectedActivity;
import java.util.List;

public final class zze {
    private static zze zzaJC;
    private static Boolean zzaJD;

    static {
        zzaJC = new zze();
    }

    public static void zza(Context context, String str, int i, String str2, String str3, String str4, int i2, List<String> list) {
        zza(context, str, DetectedActivity.RUNNING, str2, str3, str4, i2, list, 0);
    }

    public static void zza(Context context, String str, int i, String str2, String str3, String str4, int i2, List<String> list, long j) {
        if (zzaJD == null) {
            zzaJD = Boolean.valueOf(false);
        }
        if (!zzaJD.booleanValue()) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            String str5 = "WakeLockTracker";
            String str6 = "missing wakeLock key. ";
            String valueOf = String.valueOf(str);
            Log.e(str5, valueOf.length() != 0 ? str6.concat(valueOf) : new String(str6));
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (7 == i || 8 == i || 10 == i || 11 == i) {
            List<String> list2;
            if (list == null || list.size() != 1) {
                list2 = list;
            } else {
                if (GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE.equals(list.get(0))) {
                    list = null;
                }
                list2 = list;
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            int zzaK = zzl.zzaK(context);
            String packageName = context.getPackageName();
            if (GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE.equals(packageName)) {
                packageName = null;
            }
            try {
                context.startService(new Intent().setComponent(zzb.zzaJh).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", new WakeLockEvent(currentTimeMillis, i, str2, i2, r8, str, elapsedRealtime, zzaK, str3, packageName, zzl.zzaL(context), j, str4)));
            } catch (Throwable e) {
                Log.wtf("WakeLockTracker", e);
            }
        }
    }

    public static zze zzrW() {
        return zzaJC;
    }
}
