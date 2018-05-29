package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;

public abstract class zzaf {
    private static final Object zzaHN;
    private static zzaf zzaHO;

    static {
        zzaHN = new Object();
    }

    public static zzaf zzaC(Context context) {
        synchronized (zzaHN) {
            if (zzaHO == null) {
                zzaHO = new zzah(context.getApplicationContext());
            }
        }
        return zzaHO;
    }

    public final void zza(String str, String str2, ServiceConnection serviceConnection, String str3) {
        zzb(new zzag(str, str2), serviceConnection, str3);
    }

    public final boolean zza(ComponentName componentName, ServiceConnection serviceConnection, String str) {
        return zza(new zzag(componentName), serviceConnection, str);
    }

    protected abstract boolean zza(zzag com_google_android_gms_common_internal_zzag, ServiceConnection serviceConnection, String str);

    public final void zzb(ComponentName componentName, ServiceConnection serviceConnection, String str) {
        zzb(new zzag(componentName), serviceConnection, str);
    }

    protected abstract void zzb(zzag com_google_android_gms_common_internal_zzag, ServiceConnection serviceConnection, String str);
}
