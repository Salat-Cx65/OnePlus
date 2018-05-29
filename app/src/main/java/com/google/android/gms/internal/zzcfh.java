package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices.zza;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

final class zzcfh extends zza<LocationSettingsResult> {
    private /* synthetic */ LocationSettingsRequest zzbjk;
    private /* synthetic */ String zzbjl;

    zzcfh(zzcfg com_google_android_gms_internal_zzcfg, GoogleApiClient googleApiClient, LocationSettingsRequest locationSettingsRequest, String str) {
        this.zzbjk = locationSettingsRequest;
        this.zzbjl = null;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb com_google_android_gms_common_api_Api_zzb) throws RemoteException {
        ((zzcev) com_google_android_gms_common_api_Api_zzb).zza(this.zzbjk, (zzbcl) this, this.zzbjl);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new LocationSettingsResult(status);
    }
}
