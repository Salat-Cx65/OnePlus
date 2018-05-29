package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbce;
import com.google.android.gms.internal.zzbfy;

public final class zzd {
    private zzbfy zzaAO;
    private Looper zzrP;

    public final zzd zza(Looper looper) {
        zzbr.zzb((Object) looper, (Object) "Looper must not be null.");
        this.zzrP = looper;
        return this;
    }

    public final zzd zza(zzbfy com_google_android_gms_internal_zzbfy) {
        zzbr.zzb((Object) com_google_android_gms_internal_zzbfy, (Object) "StatusExceptionMapper must not be null.");
        this.zzaAO = com_google_android_gms_internal_zzbfy;
        return this;
    }

    public final zza zzph() {
        if (this.zzaAO == null) {
            this.zzaAO = new zzbce();
        }
        if (this.zzrP == null) {
            if (Looper.myLooper() != null) {
                this.zzrP = Looper.myLooper();
            } else {
                this.zzrP = Looper.getMainLooper();
            }
        }
        return new zza(null, this.zzrP, null);
    }
}
