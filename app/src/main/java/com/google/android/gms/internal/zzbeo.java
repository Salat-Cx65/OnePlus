package com.google.android.gms.internal;

final class zzbeo implements zzbcj {
    private /* synthetic */ zzben zzaEo;

    zzbeo(zzben com_google_android_gms_internal_zzben) {
        this.zzaEo = com_google_android_gms_internal_zzben;
    }

    public final void zzac(boolean z) {
        this.zzaEo.mHandler.sendMessage(this.zzaEo.mHandler.obtainMessage(1, Boolean.valueOf(z)));
    }
}
