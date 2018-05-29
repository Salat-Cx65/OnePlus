package com.google.android.gms.internal;

import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

final class zzcdu extends zzcdy {
    private /* synthetic */ LocationRequest zzbiH;
    private /* synthetic */ LocationCallback zzbiJ;
    private /* synthetic */ Looper zzbiM;

    zzcdu(zzcdn com_google_android_gms_internal_zzcdn, GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationCallback locationCallback, Looper looper) {
        this.zzbiH = locationRequest;
        this.zzbiJ = locationCallback;
        this.zzbiM = looper;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb com_google_android_gms_common_api_Api_zzb) throws RemoteException {
        ((zzcev) com_google_android_gms_common_api_Api_zzb).zza(zzcez.zza(this.zzbiH), zzbfm.zzb(this.zzbiJ, zzcfn.zzb(this.zzbiM), LocationCallback.class.getSimpleName()), new zzcdz(this));
    }
}
