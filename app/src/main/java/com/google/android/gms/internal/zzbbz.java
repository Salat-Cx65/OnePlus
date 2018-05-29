package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

abstract class zzbbz extends zzbby {
    protected final TaskCompletionSource<Void> zzalG;

    public zzbbz(int i, TaskCompletionSource<Void> taskCompletionSource) {
        super(i);
        this.zzalG = taskCompletionSource;
    }

    public void zza(@NonNull zzbdf com_google_android_gms_internal_zzbdf, boolean z) {
    }

    public final void zza(zzbep<?> com_google_android_gms_internal_zzbep_) throws DeadObjectException {
        try {
            zzb(com_google_android_gms_internal_zzbep_);
        } catch (RemoteException e) {
            zzp(zzbby.zza(e));
            throw e;
        } catch (RemoteException e2) {
            zzp(zzbby.zza(e2));
        }
    }

    protected abstract void zzb(zzbep<?> com_google_android_gms_internal_zzbep_) throws RemoteException;

    public void zzp(@NonNull Status status) {
        this.zzalG.trySetException(new ApiException(status));
    }
}
