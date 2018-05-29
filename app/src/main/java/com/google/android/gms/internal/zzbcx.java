package com.google.android.gms.internal;

final class zzbcx implements Runnable {
    private /* synthetic */ zzbcw zzaCz;

    zzbcx(zzbcw com_google_android_gms_internal_zzbcw) {
        this.zzaCz = com_google_android_gms_internal_zzbcw;
    }

    public final void run() {
        zzbcw.zza(this.zzaCz).lock();
        zzbcw.zzb(this.zzaCz);
        zzbcw.zza(this.zzaCz).unlock();
    }
}
