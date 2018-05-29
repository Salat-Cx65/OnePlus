package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbr;

public final class zzbfi<L> {
    private volatile L mListener;
    private final zzbfj zzaEO;
    private final zzbfk<L> zzaEP;

    zzbfi(@NonNull Looper looper, @NonNull L l, @NonNull String str) {
        this.zzaEO = new zzbfj(this, looper);
        this.mListener = zzbr.zzb((Object) l, (Object) "Listener must not be null");
        this.zzaEP = new zzbfk(l, zzbr.zzcF(str));
    }

    public final void clear() {
        this.mListener = null;
    }

    public final void zza(zzbfl<? super L> com_google_android_gms_internal_zzbfl__super_L) {
        zzbr.zzb((Object) com_google_android_gms_internal_zzbfl__super_L, (Object) "Notifier must not be null");
        this.zzaEO.sendMessage(this.zzaEO.obtainMessage(1, com_google_android_gms_internal_zzbfl__super_L));
    }

    final void zzb(zzbfl<? super L> com_google_android_gms_internal_zzbfl__super_L) {
        Object obj = this.mListener;
        if (obj == null) {
            com_google_android_gms_internal_zzbfl__super_L.zzpR();
            return;
        }
        try {
            com_google_android_gms_internal_zzbfl__super_L.zzq(obj);
        } catch (RuntimeException e) {
            com_google_android_gms_internal_zzbfl__super_L.zzpR();
            throw e;
        }
    }

    public final boolean zzoa() {
        return this.mListener != null;
    }

    @NonNull
    public final zzbfk<L> zzqE() {
        return this.zzaEP;
    }
}
