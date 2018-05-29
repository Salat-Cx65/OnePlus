package com.google.android.gms.internal;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

final class zzcer implements zzbfl<LocationCallback> {
    private /* synthetic */ LocationResult zzbiT;

    zzcer(zzceq com_google_android_gms_internal_zzceq, LocationResult locationResult) {
        this.zzbiT = locationResult;
    }

    public final void zzpR() {
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((LocationCallback) obj).onLocationResult(this.zzbiT);
    }
}
