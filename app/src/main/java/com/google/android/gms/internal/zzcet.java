package com.google.android.gms.internal;

import android.location.Location;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.zzn;

final class zzcet extends zzn {
    private final zzbfi<LocationListener> zzaEW;

    zzcet(zzbfi<LocationListener> com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationListener) {
        this.zzaEW = com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationListener;
    }

    public final synchronized void onLocationChanged(Location location) {
        this.zzaEW.zza(new zzceu(this, location));
    }

    public final synchronized void release() {
        this.zzaEW.clear();
    }
}
