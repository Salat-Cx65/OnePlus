package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

final class zzcdv extends zzcdy {
    private /* synthetic */ PendingIntent zzbiD;
    private /* synthetic */ LocationRequest zzbiH;

    zzcdv(zzcdn com_google_android_gms_internal_zzcdn, GoogleApiClient googleApiClient, LocationRequest locationRequest, PendingIntent pendingIntent) {
        this.zzbiH = locationRequest;
        this.zzbiD = pendingIntent;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb com_google_android_gms_common_api_Api_zzb) throws RemoteException {
        ((zzcev) com_google_android_gms_common_api_Api_zzb).zza(this.zzbiH, this.zzbiD, new zzcdz(this));
    }
}
