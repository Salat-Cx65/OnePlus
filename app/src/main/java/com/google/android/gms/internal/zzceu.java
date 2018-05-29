package com.google.android.gms.internal;

import android.location.Location;
import com.google.android.gms.location.LocationListener;

final class zzceu implements zzbfl<LocationListener> {
    private /* synthetic */ Location zzbiV;

    zzceu(zzcet com_google_android_gms_internal_zzcet, Location location) {
        this.zzbiV = location;
    }

    public final void zzpR() {
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((LocationListener) obj).onLocationChanged(this.zzbiV);
    }
}
