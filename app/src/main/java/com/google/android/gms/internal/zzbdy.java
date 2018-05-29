package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

final class zzbdy implements ConnectionCallbacks, OnConnectionFailedListener {
    private /* synthetic */ zzbdp zzaDr;

    private zzbdy(zzbdp com_google_android_gms_internal_zzbdp) {
        this.zzaDr = com_google_android_gms_internal_zzbdp;
    }

    public final void onConnected(Bundle bundle) {
        zzbdp.zzf(this.zzaDr).zza(new zzbdw(this.zzaDr));
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zzbdp.zzc(this.zzaDr).lock();
        if (zzbdp.zzb(this.zzaDr, connectionResult)) {
            zzbdp.zzi(this.zzaDr);
            zzbdp.zzj(this.zzaDr);
        } else {
            zzbdp.zza(this.zzaDr, connectionResult);
        }
        zzbdp.zzc(this.zzaDr).unlock();
    }

    public final void onConnectionSuspended(int i) {
    }
}
