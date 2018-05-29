package com.google.android.gms.internal;

abstract class zzbek {
    private final zzbei zzaEb;

    protected zzbek(zzbei com_google_android_gms_internal_zzbei) {
        this.zzaEb = com_google_android_gms_internal_zzbei;
    }

    public final void zzc(zzbej com_google_android_gms_internal_zzbej) {
        zzbej.zza(com_google_android_gms_internal_zzbej).lock();
        if (zzbej.zzb(com_google_android_gms_internal_zzbej) != this.zzaEb) {
            zzbej.zza(com_google_android_gms_internal_zzbej).unlock();
            return;
        }
        zzpT();
        zzbej.zza(com_google_android_gms_internal_zzbej).unlock();
    }

    protected abstract void zzpT();
}
