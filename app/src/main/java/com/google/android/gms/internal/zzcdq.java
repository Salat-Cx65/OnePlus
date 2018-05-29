package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzcdq extends zzcdy {
    private /* synthetic */ boolean zzbiK;

    zzcdq(zzcdn com_google_android_gms_internal_zzcdn, GoogleApiClient googleApiClient, boolean z) {
        this.zzbiK = z;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb com_google_android_gms_common_api_Api_zzb) throws RemoteException {
        ((zzcev) com_google_android_gms_common_api_Api_zzb).zzai(this.zzbiK);
        setResult(Status.zzaBo);
    }
}
