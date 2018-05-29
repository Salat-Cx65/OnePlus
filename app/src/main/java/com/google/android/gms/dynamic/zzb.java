package com.google.android.gms.dynamic;

import java.util.Iterator;

final class zzb implements zzo<T> {
    private /* synthetic */ zza zzaSz;

    zzb(zza com_google_android_gms_dynamic_zza) {
        this.zzaSz = com_google_android_gms_dynamic_zza;
    }

    public final void zza(T t) {
        this.zzaSz.zzaSv = t;
        Iterator it = this.zzaSz.zzaSx.iterator();
        while (it.hasNext()) {
            ((zzi) it.next()).zzb(this.zzaSz.zzaSv);
        }
        this.zzaSz.zzaSx.clear();
        this.zzaSz.zzaSw = null;
    }
}
