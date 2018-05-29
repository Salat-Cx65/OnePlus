package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.tasks.TaskCompletionSource;

public abstract class zzbgk<A extends zzb, L> {
    private final zzbfk<L> zzaEP;

    protected zzbgk(zzbfk<L> com_google_android_gms_internal_zzbfk_L) {
        this.zzaEP = com_google_android_gms_internal_zzbfk_L;
    }

    protected abstract void zzc(A a, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException;

    public final zzbfk<L> zzqE() {
        return this.zzaEP;
    }
}
