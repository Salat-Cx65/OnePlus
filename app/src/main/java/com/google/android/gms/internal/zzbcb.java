package com.google.android.gms.internal;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzbcb extends zzbbz {
    private zzbfq<zzb, ?> zzaBw;
    private zzbgk<zzb, ?> zzaBx;

    public zzbcb(zzbfr com_google_android_gms_internal_zzbfr, TaskCompletionSource<Void> taskCompletionSource) {
        super(3, taskCompletionSource);
        this.zzaBw = com_google_android_gms_internal_zzbfr.zzaBw;
        this.zzaBx = com_google_android_gms_internal_zzbfr.zzaBx;
    }

    public final /* bridge */ /* synthetic */ void zza(@NonNull zzbdf com_google_android_gms_internal_zzbdf, boolean z) {
    }

    public final void zzb(zzbep<?> com_google_android_gms_internal_zzbep_) throws RemoteException {
        this.zzaBw.zzb(com_google_android_gms_internal_zzbep_.zzpH(), this.zzalG);
        if (this.zzaBw.zzqE() != null) {
            com_google_android_gms_internal_zzbep_.zzqq().put(this.zzaBw.zzqE(), new zzbfr(this.zzaBw, this.zzaBx));
        }
    }

    public final /* bridge */ /* synthetic */ void zzp(@NonNull Status status) {
        super.zzp(status);
    }
}
