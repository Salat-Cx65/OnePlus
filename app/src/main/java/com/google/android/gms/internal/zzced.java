package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.GeofencingRequest;

final class zzced extends zzcef {
    private /* synthetic */ PendingIntent zzaVP;
    private /* synthetic */ GeofencingRequest zzbiO;

    zzced(zzcec com_google_android_gms_internal_zzcec, GoogleApiClient googleApiClient, GeofencingRequest geofencingRequest, PendingIntent pendingIntent) {
        this.zzbiO = geofencingRequest;
        this.zzaVP = pendingIntent;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb com_google_android_gms_common_api_Api_zzb) throws RemoteException {
        ((zzcev) com_google_android_gms_common_api_Api_zzb).zza(this.zzbiO, this.zzaVP, (zzbcl) this);
    }
}
