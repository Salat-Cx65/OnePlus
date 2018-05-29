package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.safeparcel.zza;

public abstract class DowngradeableSafeParcel extends zza implements ReflectedParcelable {
    private static final Object zzaHs;
    private static ClassLoader zzaHt;
    private static Integer zzaHu;
    private boolean zzaHv;

    static {
        zzaHs = new Object();
        zzaHt = null;
        zzaHu = null;
    }

    public DowngradeableSafeParcel() {
        this.zzaHv = false;
    }

    protected static boolean zzcA(String str) {
        zzru();
        return true;
    }

    private static ClassLoader zzru() {
        synchronized (zzaHs) {
        }
        return null;
    }

    protected static Integer zzrv() {
        synchronized (zzaHs) {
        }
        return null;
    }
}
