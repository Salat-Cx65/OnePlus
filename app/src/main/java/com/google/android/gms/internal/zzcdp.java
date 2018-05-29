package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;

final class zzcdp extends zzcdy {
    private /* synthetic */ LocationCallback zzbiJ;

    zzcdp(zzcdn com_google_android_gms_internal_zzcdn, GoogleApiClient googleApiClient, LocationCallback locationCallback) {
        this.zzbiJ = locationCallback;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb com_google_android_gms_common_api_Api_zzb) throws RemoteException {
        ((zzcev) com_google_android_gms_common_api_Api_zzb).zzb(zzbfm.zza(this.zzbiJ, LocationCallback.class.getSimpleName()), new zzcdz(this));
    }
}
