package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

public final class zzbca<A extends zzbck<? extends Result, zzb>> extends zzbby {
    private A zzaBv;

    public zzbca(int i, A a) {
        super(i);
        this.zzaBv = a;
    }

    public final void zza(@NonNull zzbdf com_google_android_gms_internal_zzbdf, boolean z) {
        com_google_android_gms_internal_zzbdf.zza(this.zzaBv, z);
    }

    public final void zza(zzbep<?> com_google_android_gms_internal_zzbep_) throws DeadObjectException {
        this.zzaBv.zzb(com_google_android_gms_internal_zzbep_.zzpH());
    }

    public final void zzp(@NonNull Status status) {
        this.zzaBv.zzr(status);
    }
}
