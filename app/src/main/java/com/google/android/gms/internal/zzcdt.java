package com.google.android.gms.internal;

import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

final class zzcdt extends zzcdy {
    private /* synthetic */ LocationRequest zzbiH;
    private /* synthetic */ LocationListener zzbiI;
    private /* synthetic */ Looper zzbiM;

    zzcdt(zzcdn com_google_android_gms_internal_zzcdn, GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        this.zzbiH = locationRequest;
        this.zzbiI = locationListener;
        this.zzbiM = looper;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb com_google_android_gms_common_api_Api_zzb) throws RemoteException {
        ((zzcev) com_google_android_gms_common_api_Api_zzb).zza(this.zzbiH, zzbfm.zzb(this.zzbiI, zzcfn.zzb(this.zzbiM), LocationListener.class.getSimpleName()), new zzcdz(this));
    }
}
