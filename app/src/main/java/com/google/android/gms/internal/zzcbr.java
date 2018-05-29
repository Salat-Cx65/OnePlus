package com.google.android.gms.internal;

public final class zzcbr {
    private static zzcbr zzaXL;
    private final zzcbm zzaXM;
    private final zzcbn zzaXN;

    static {
        zzcbr com_google_android_gms_internal_zzcbr = new zzcbr();
        synchronized (zzcbr.class) {
            zzaXL = com_google_android_gms_internal_zzcbr;
        }
    }

    private zzcbr() {
        this.zzaXM = new zzcbm();
        this.zzaXN = new zzcbn();
    }

    private static zzcbr zztZ() {
        zzcbr com_google_android_gms_internal_zzcbr;
        synchronized (zzcbr.class) {
            com_google_android_gms_internal_zzcbr = zzaXL;
        }
        return com_google_android_gms_internal_zzcbr;
    }

    public static zzcbm zzua() {
        return zztZ().zzaXM;
    }

    public static zzcbn zzub() {
        return zztZ().zzaXN;
    }
}
