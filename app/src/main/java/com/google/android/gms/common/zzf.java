package com.google.android.gms.common;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.internal.zzbb;
import com.google.android.gms.common.internal.zzbc;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;

final class zzf {
    private static zzbb zzaAf;
    private static final Object zzaAg;
    private static Context zzaAh;

    static {
        zzaAg = new Object();
    }

    static boolean zza(String str, zzg com_google_android_gms_common_zzg) {
        return zza(str, com_google_android_gms_common_zzg, false);
    }

    private static boolean zza(String str, zzg com_google_android_gms_common_zzg, boolean z) {
        if (!zzoV()) {
            return false;
        }
        zzbr.zzu(zzaAh);
        try {
            return zzaAf.zza(new zzm(str, com_google_android_gms_common_zzg, z), zzn.zzw(zzaAh.getPackageManager()));
        } catch (Throwable e) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            return false;
        }
    }

    static synchronized void zzav(Context context) {
        synchronized (zzf.class) {
            if (zzaAh != null) {
                Log.w("GoogleCertificates", "GoogleCertificates has been initialized already");
            } else if (context != null) {
                zzaAh = context.getApplicationContext();
            }
        }
    }

    static boolean zzb(String str, zzg com_google_android_gms_common_zzg) {
        return zza(str, com_google_android_gms_common_zzg, true);
    }

    private static boolean zzoV() {
        boolean z = true;
        if (zzaAf == null) {
            zzbr.zzu(zzaAh);
            synchronized (zzaAg) {
                if (zzaAf == null) {
                    try {
                        zzaAf = zzbc.zzJ(DynamiteModule.zza(zzaAh, DynamiteModule.zzaST, "com.google.android.gms.googlecertificates").zzcW("com.google.android.gms.common.GoogleCertificatesImpl"));
                    } catch (Throwable e) {
                        Log.e("GoogleCertificates", "Failed to load com.google.android.gms.googlecertificates", e);
                        z = false;
                    }
                }
            }
        }
        return z;
    }
}
