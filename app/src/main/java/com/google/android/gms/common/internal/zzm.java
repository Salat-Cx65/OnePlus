package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;

public final class zzm implements zzj {
    private /* synthetic */ zzd zzaHg;

    public zzm(zzd com_google_android_gms_common_internal_zzd) {
        this.zzaHg = com_google_android_gms_common_internal_zzd;
    }

    public final void zzf(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.isSuccess()) {
            this.zzaHg.zza(null, this.zzaHg.zzrf());
        } else if (this.zzaHg.zzaGY != null) {
            this.zzaHg.zzaGY.onConnectionFailed(connectionResult);
        }
    }
}
