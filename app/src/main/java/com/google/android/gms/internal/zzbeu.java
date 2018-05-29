package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import java.util.Collections;

final class zzbeu implements Runnable {
    private /* synthetic */ zzbet zzaEA;
    private /* synthetic */ ConnectionResult zzaEy;

    zzbeu(zzbet com_google_android_gms_internal_zzbet, ConnectionResult connectionResult) {
        this.zzaEA = com_google_android_gms_internal_zzbet;
        this.zzaEy = connectionResult;
    }

    public final void run() {
        if (this.zzaEy.isSuccess()) {
            zzbet.zza(this.zzaEA, true);
            if (zzbet.zza(this.zzaEA).zzmt()) {
                zzbet.zzb(this.zzaEA);
                return;
            } else {
                zzbet.zza(this.zzaEA).zza(null, Collections.emptySet());
                return;
            }
        }
        ((zzbep) zzben.zzj(this.zzaEA.zzaEo).get(zzbet.zzc(this.zzaEA))).onConnectionFailed(this.zzaEy);
    }
}
