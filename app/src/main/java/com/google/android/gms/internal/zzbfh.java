package com.google.android.gms.internal;

final class zzbfh implements Runnable {
    private /* synthetic */ String zzO;
    private /* synthetic */ zzbfe zzaEM;
    private /* synthetic */ zzbfg zzaEN;

    zzbfh(zzbfg com_google_android_gms_internal_zzbfg, zzbfe com_google_android_gms_internal_zzbfe, String str) {
        this.zzaEN = com_google_android_gms_internal_zzbfg;
        this.zzaEM = com_google_android_gms_internal_zzbfe;
        this.zzO = str;
    }

    public final void run() {
        if (zzbfg.zza(this.zzaEN) > 0) {
            this.zzaEM.onCreate(zzbfg.zzb(this.zzaEN) != null ? zzbfg.zzb(this.zzaEN).getBundle(this.zzO) : null);
        }
        if (zzbfg.zza(this.zzaEN) >= 2) {
            this.zzaEM.onStart();
        }
        if (zzbfg.zza(this.zzaEN) >= 3) {
            this.zzaEM.onResume();
        }
        if (zzbfg.zza(this.zzaEN) >= 4) {
            this.zzaEM.onStop();
        }
        if (zzbfg.zza(this.zzaEN) >= 5) {
            this.zzaEM.onDestroy();
        }
    }
}
