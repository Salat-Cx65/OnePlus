package com.google.android.gms.internal;

import java.lang.ref.WeakReference;

final class zzbeh extends zzbex {
    private WeakReference<zzbeb> zzaDT;

    zzbeh(zzbeb com_google_android_gms_internal_zzbeb) {
        this.zzaDT = new WeakReference(com_google_android_gms_internal_zzbeb);
    }

    public final void zzpy() {
        zzbeb com_google_android_gms_internal_zzbeb = (zzbeb) this.zzaDT.get();
        if (com_google_android_gms_internal_zzbeb != null) {
            zzbeb.zza(com_google_android_gms_internal_zzbeb);
        }
    }
}
