package com.google.android.gms.internal;

import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzcdr extends zzcdy {
    private /* synthetic */ Location zzbiL;

    zzcdr(zzcdn com_google_android_gms_internal_zzcdn, GoogleApiClient googleApiClient, Location location) {
        this.zzbiL = location;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb com_google_android_gms_common_api_Api_zzb) throws RemoteException {
        ((zzcev) com_google_android_gms_common_api_Api_zzb).zzc(this.zzbiL);
        setResult(Status.zzaBo);
    }
}
