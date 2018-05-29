package com.google.android.gms.internal;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzk;

final class zzceq extends zzk {
    private final zzbfi<LocationCallback> zzaEW;

    zzceq(zzbfi<LocationCallback> com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationCallback) {
        this.zzaEW = com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationCallback;
    }

    public final void onLocationAvailability(LocationAvailability locationAvailability) {
        this.zzaEW.zza(new zzces(this, locationAvailability));
    }

    public final void onLocationResult(LocationResult locationResult) {
        this.zzaEW.zza(new zzcer(this, locationResult));
    }

    public final synchronized void release() {
        this.zzaEW.clear();
    }
}
