package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;

final class zzcdw extends zzcdy {
    private /* synthetic */ LocationListener zzbiI;

    zzcdw(zzcdn com_google_android_gms_internal_zzcdn, GoogleApiClient googleApiClient, LocationListener locationListener) {
        this.zzbiI = locationListener;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb com_google_android_gms_common_api_Api_zzb) throws RemoteException {
        ((zzcev) com_google_android_gms_common_api_Api_zzb).zza(zzbfm.zza(this.zzbiI, LocationListener.class.getSimpleName()), new zzcdz(this));
    }
}
