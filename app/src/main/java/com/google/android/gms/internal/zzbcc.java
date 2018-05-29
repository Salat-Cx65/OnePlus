package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzbcc<TResult> extends zzbby {
    private final zzbgc<zzb, TResult> zzaBy;
    private final zzbfy zzaBz;
    private final TaskCompletionSource<TResult> zzalG;

    public zzbcc(int i, zzbgc<zzb, TResult> com_google_android_gms_internal_zzbgc_com_google_android_gms_common_api_Api_zzb__TResult, TaskCompletionSource<TResult> taskCompletionSource, zzbfy com_google_android_gms_internal_zzbfy) {
        super(i);
        this.zzalG = taskCompletionSource;
        this.zzaBy = com_google_android_gms_internal_zzbgc_com_google_android_gms_common_api_Api_zzb__TResult;
        this.zzaBz = com_google_android_gms_internal_zzbfy;
    }

    public final void zza(@NonNull zzbdf com_google_android_gms_internal_zzbdf, boolean z) {
        com_google_android_gms_internal_zzbdf.zza(this.zzalG, z);
    }

    public final void zza(zzbep<?> com_google_android_gms_internal_zzbep_) throws DeadObjectException {
        try {
            this.zzaBy.zza(com_google_android_gms_internal_zzbep_.zzpH(), this.zzalG);
        } catch (DeadObjectException e) {
            throw e;
        } catch (RemoteException e2) {
            zzp(zzbby.zza(e2));
        }
    }

    public final void zzp(@NonNull Status status) {
        this.zzalG.trySetException(this.zzaBz.zzq(status));
    }
}
