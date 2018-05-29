package com.google.android.gms.internal;

final class zzbgb implements Runnable {
    private /* synthetic */ String zzO;
    private /* synthetic */ zzbfe zzaEM;
    private /* synthetic */ zzbga zzaFb;

    zzbgb(zzbga com_google_android_gms_internal_zzbga, zzbfe com_google_android_gms_internal_zzbfe, String str) {
        this.zzaFb = com_google_android_gms_internal_zzbga;
        this.zzaEM = com_google_android_gms_internal_zzbfe;
        this.zzO = str;
    }

    public final void run() {
        if (zzbga.zza(this.zzaFb) > 0) {
            this.zzaEM.onCreate(zzbga.zzb(this.zzaFb) != null ? zzbga.zzb(this.zzaFb).getBundle(this.zzO) : null);
        }
        if (zzbga.zza(this.zzaFb) >= 2) {
            this.zzaEM.onStart();
        }
        if (zzbga.zza(this.zzaFb) >= 3) {
            this.zzaEM.onResume();
        }
        if (zzbga.zza(this.zzaFb) >= 4) {
            this.zzaEM.onStop();
        }
        if (zzbga.zza(this.zzaFb) >= 5) {
            this.zzaEM.onDestroy();
        }
    }
}
