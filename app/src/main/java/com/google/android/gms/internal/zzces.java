package com.google.android.gms.internal;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;

final class zzces implements zzbfl<LocationCallback> {
    private /* synthetic */ LocationAvailability zzbiU;

    zzces(zzceq com_google_android_gms_internal_zzceq, LocationAvailability locationAvailability) {
        this.zzbiU = locationAvailability;
    }

    public final void zzpR() {
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((LocationCallback) obj).onLocationAvailability(this.zzbiU);
    }
}
