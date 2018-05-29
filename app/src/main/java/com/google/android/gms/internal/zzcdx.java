package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzcdx extends zzcdy {
    private /* synthetic */ PendingIntent zzbiD;

    zzcdx(zzcdn com_google_android_gms_internal_zzcdn, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        this.zzbiD = pendingIntent;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb com_google_android_gms_common_api_Api_zzb) throws RemoteException {
        ((zzcev) com_google_android_gms_common_api_Api_zzb).zza(this.zzbiD, new zzcdz(this));
    }
}
