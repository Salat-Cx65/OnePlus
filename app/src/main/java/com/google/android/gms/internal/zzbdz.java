package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;

abstract class zzbdz implements Runnable {
    private /* synthetic */ zzbdp zzaDr;

    private zzbdz(zzbdp com_google_android_gms_internal_zzbdp) {
        this.zzaDr = com_google_android_gms_internal_zzbdp;
    }

    @WorkerThread
    public void run() {
        zzbdp.zzc(this.zzaDr).lock();
        try {
            if (Thread.interrupted()) {
                zzbdp.zzc(this.zzaDr).unlock();
                return;
            }
            zzpT();
            zzbdp.zzc(this.zzaDr).unlock();
        } catch (RuntimeException e) {
            zzbdp.zzd(this.zzaDr).zza(e);
            zzbdp.zzc(this.zzaDr).unlock();
        }
    }

    @WorkerThread
    protected abstract void zzpT();
}
