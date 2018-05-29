package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.zzaa;

final class zzcee extends zzcef {
    private /* synthetic */ zzaa zzbiP;

    zzcee(zzcec com_google_android_gms_internal_zzcec, GoogleApiClient googleApiClient, zzaa com_google_android_gms_location_zzaa) {
        this.zzbiP = com_google_android_gms_location_zzaa;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb com_google_android_gms_common_api_Api_zzb) throws RemoteException {
        ((zzcev) com_google_android_gms_common_api_Api_zzb).zza(this.zzbiP, (zzbcl) this);
    }
}
