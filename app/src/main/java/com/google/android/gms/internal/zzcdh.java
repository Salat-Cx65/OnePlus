package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzcdh extends zzcdi {
    private /* synthetic */ PendingIntent zzbiD;

    zzcdh(zzcdf com_google_android_gms_internal_zzcdf, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        this.zzbiD = pendingIntent;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb com_google_android_gms_common_api_Api_zzb) throws RemoteException {
        ((zzcev) com_google_android_gms_common_api_Api_zzb).zzc(this.zzbiD);
        setResult(Status.zzaBo);
    }
}
