package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResult.zza;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.TimeUnit;

final class zzbm implements zza {
    private /* synthetic */ PendingResult zzaIl;
    private /* synthetic */ TaskCompletionSource zzaIm;
    private /* synthetic */ zzbp zzaIn;
    private /* synthetic */ zzbq zzaIo;

    zzbm(PendingResult pendingResult, TaskCompletionSource taskCompletionSource, zzbp com_google_android_gms_common_internal_zzbp, zzbq com_google_android_gms_common_internal_zzbq) {
        this.zzaIl = pendingResult;
        this.zzaIm = taskCompletionSource;
        this.zzaIn = com_google_android_gms_common_internal_zzbp;
        this.zzaIo = com_google_android_gms_common_internal_zzbq;
    }

    public final void zzo(Status status) {
        if (status.isSuccess()) {
            this.zzaIm.setResult(this.zzaIn.zzd(this.zzaIl.await(0, TimeUnit.MILLISECONDS)));
            return;
        }
        this.zzaIm.setException(this.zzaIo.zzy(status));
    }
}
