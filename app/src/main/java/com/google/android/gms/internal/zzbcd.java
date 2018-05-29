package com.google.android.gms.internal;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzbcd extends zzbbz {
    private zzbfk<?> zzaBA;

    public zzbcd(zzbfk<?> com_google_android_gms_internal_zzbfk_, TaskCompletionSource<Void> taskCompletionSource) {
        super(4, taskCompletionSource);
        this.zzaBA = com_google_android_gms_internal_zzbfk_;
    }

    public final /* bridge */ /* synthetic */ void zza(@NonNull zzbdf com_google_android_gms_internal_zzbdf, boolean z) {
    }

    public final void zzb(zzbep<?> com_google_android_gms_internal_zzbep_) throws RemoteException {
        zzbfr com_google_android_gms_internal_zzbfr = (zzbfr) com_google_android_gms_internal_zzbep_.zzqq().remove(this.zzaBA);
        if (com_google_android_gms_internal_zzbfr != null) {
            com_google_android_gms_internal_zzbfr.zzaBx.zzc(com_google_android_gms_internal_zzbep_.zzpH(), this.zzalG);
            com_google_android_gms_internal_zzbfr.zzaBw.zzqF();
            return;
        }
        Log.wtf("UnregisterListenerTask", "Received call to unregister a listener without a matching registration call.", new Exception());
        this.zzalG.trySetException(new ApiException(Status.zzaBq));
    }

    public final /* bridge */ /* synthetic */ void zzp(@NonNull Status status) {
        super.zzp(status);
    }
}
