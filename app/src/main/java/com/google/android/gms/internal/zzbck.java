package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbr;

public abstract class zzbck<R extends Result, A extends zzb> extends zzbcq<R> implements zzbcl<R> {
    private final zzc<A> zzaBO;
    private final Api<?> zzayY;

    @Deprecated
    protected zzbck(zzc<A> com_google_android_gms_common_api_Api_zzc_A, GoogleApiClient googleApiClient) {
        super((GoogleApiClient) zzbr.zzb((Object) googleApiClient, (Object) "GoogleApiClient must not be null"));
        this.zzaBO = (zzc) zzbr.zzu(com_google_android_gms_common_api_Api_zzc_A);
        this.zzayY = null;
    }

    protected zzbck(Api<?> api, GoogleApiClient googleApiClient) {
        super((GoogleApiClient) zzbr.zzb((Object) googleApiClient, (Object) "GoogleApiClient must not be null"));
        this.zzaBO = api.zzpb();
        this.zzayY = api;
    }

    private final void zzc(RemoteException remoteException) {
        zzr(new Status(8, remoteException.getLocalizedMessage(), null));
    }

    public /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((Result) obj);
    }

    protected abstract void zza(A a) throws RemoteException;

    public final void zzb(A a) throws DeadObjectException {
        try {
            zza(a);
        } catch (RemoteException e) {
            zzc(e);
            throw e;
        } catch (RemoteException e2) {
            zzc(e2);
        }
    }

    public final zzc<A> zzpb() {
        return this.zzaBO;
    }

    public final Api<?> zzpe() {
        return this.zzayY;
    }

    public final void zzr(Status status) {
        zzbr.zzb(!status.isSuccess(), (Object) "Failed result must not be success");
        setResult(zzb(status));
    }
}
