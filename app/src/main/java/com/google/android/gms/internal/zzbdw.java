package com.google.android.gms.internal;

import android.support.annotation.BinderThread;
import java.lang.ref.WeakReference;

final class zzbdw extends zzcvb {
    private final WeakReference<zzbdp> zzaDs;

    zzbdw(zzbdp com_google_android_gms_internal_zzbdp) {
        this.zzaDs = new WeakReference(com_google_android_gms_internal_zzbdp);
    }

    @BinderThread
    public final void zzb(zzcvj com_google_android_gms_internal_zzcvj) {
        zzbdp com_google_android_gms_internal_zzbdp = (zzbdp) this.zzaDs.get();
        if (com_google_android_gms_internal_zzbdp != null) {
            com_google_android_gms_internal_zzbdp.zzaDb.zza(new zzbdx(this, com_google_android_gms_internal_zzbdp, com_google_android_gms_internal_zzbdp, com_google_android_gms_internal_zzcvj));
        }
    }
}
