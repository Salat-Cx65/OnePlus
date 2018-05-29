package com.google.android.gms.internal;

import com.google.android.gms.common.api.PendingResult.zza;
import com.google.android.gms.common.api.Status;

final class zzbdg implements zza {
    private /* synthetic */ zzbcq zzaCV;
    private /* synthetic */ zzbdf zzaCW;

    zzbdg(zzbdf com_google_android_gms_internal_zzbdf, zzbcq com_google_android_gms_internal_zzbcq) {
        this.zzaCW = com_google_android_gms_internal_zzbdf;
        this.zzaCV = com_google_android_gms_internal_zzbcq;
    }

    public final void zzo(Status status) {
        this.zzaCW.zzaCT.remove(this.zzaCV);
    }
}
