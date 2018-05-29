package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

final class zzbgf implements Runnable {
    private /* synthetic */ Result zzaFj;
    private /* synthetic */ zzbge zzaFk;

    zzbgf(zzbge com_google_android_gms_internal_zzbge, Result result) {
        this.zzaFk = com_google_android_gms_internal_zzbge;
        this.zzaFj = result;
    }

    @WorkerThread
    public final void run() {
        try {
            zzbcq.zzaBX.set(Boolean.valueOf(true));
            zzbge.zzd(this.zzaFk).sendMessage(zzbge.zzd(this.zzaFk).obtainMessage(0, zzbge.zzc(this.zzaFk).onSuccess(this.zzaFj)));
            zzbcq.zzaBX.set(Boolean.valueOf(false));
            zzbge.zza(this.zzaFk, this.zzaFj);
            GoogleApiClient googleApiClient = (GoogleApiClient) zzbge.zze(this.zzaFk).get();
            if (googleApiClient != null) {
                googleApiClient.zzb(this.zzaFk);
            }
        } catch (RuntimeException e) {
            try {
                zzbge.zzd(this.zzaFk).sendMessage(zzbge.zzd(this.zzaFk).obtainMessage(1, e));
                zzbcq.zzaBX.set(Boolean.valueOf(false));
                zzbge.zza(this.zzaFk, this.zzaFj);
                googleApiClient = (GoogleApiClient) zzbge.zze(this.zzaFk).get();
                if (googleApiClient != null) {
                    googleApiClient.zzb(this.zzaFk);
                }
            } catch (Throwable th) {
                Throwable th2 = th;
                zzbcq.zzaBX.set(Boolean.valueOf(false));
                zzbge.zza(this.zzaFk, this.zzaFj);
                googleApiClient = (GoogleApiClient) zzbge.zze(this.zzaFk).get();
                if (googleApiClient != null) {
                    googleApiClient.zzb(this.zzaFk);
                }
            }
        }
    }
}
