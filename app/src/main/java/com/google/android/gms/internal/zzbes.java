package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;

final class zzbes implements Runnable {
    private /* synthetic */ zzbep zzaEx;
    private /* synthetic */ ConnectionResult zzaEy;

    zzbes(zzbep com_google_android_gms_internal_zzbep, ConnectionResult connectionResult) {
        this.zzaEx = com_google_android_gms_internal_zzbep;
        this.zzaEy = connectionResult;
    }

    public final void run() {
        this.zzaEx.onConnectionFailed(this.zzaEy);
    }
}
