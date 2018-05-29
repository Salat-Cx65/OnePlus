package com.google.android.gms.internal;

final class zzq implements Runnable {
    private /* synthetic */ String zzO;
    private /* synthetic */ long zzP;
    private /* synthetic */ zzp zzQ;

    zzq(zzp com_google_android_gms_internal_zzp, String str, long j) {
        this.zzQ = com_google_android_gms_internal_zzp;
        this.zzO = str;
        this.zzP = j;
    }

    public final void run() {
        zzp.zzb(this.zzQ).zza(this.zzO, this.zzP);
        zzp.zzb(this.zzQ).zzc(toString());
    }
}
