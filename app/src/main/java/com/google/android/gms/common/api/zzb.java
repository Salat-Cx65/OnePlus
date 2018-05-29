package com.google.android.gms.common.api;

import com.google.android.gms.common.api.PendingResult.zza;

final class zzb implements zza {
    private /* synthetic */ Batch zzaAI;

    zzb(Batch batch) {
        this.zzaAI = batch;
    }

    public final void zzo(Status status) {
        synchronized (Batch.zza(this.zzaAI)) {
            if (this.zzaAI.isCanceled()) {
                return;
            }
            if (status.isCanceled()) {
                Batch.zza(this.zzaAI, true);
            } else if (!status.isSuccess()) {
                Batch.zzb(this.zzaAI, true);
            }
            Batch.zzb(this.zzaAI);
            if (Batch.zzc(this.zzaAI) == 0) {
                if (Batch.zzd(this.zzaAI)) {
                    Batch.zze(this.zzaAI);
                } else {
                    this.zzaAI.setResult(new BatchResult(Batch.zzf(this.zzaAI) ? new Status(13) : Status.zzaBo, Batch.zzg(this.zzaAI)));
                }
            }
        }
    }
}
