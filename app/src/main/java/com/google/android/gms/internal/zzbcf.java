package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.internal.zzbh;
import java.util.Arrays;

public final class zzbcf<O extends ApiOptions> {
    private final O zzaAL;
    private final boolean zzaBB;
    private final int zzaBC;
    private final Api<O> zzayY;

    private zzbcf(Api<O> api) {
        this.zzaBB = true;
        this.zzayY = api;
        this.zzaAL = null;
        this.zzaBC = System.identityHashCode(this);
    }

    private zzbcf(Api<O> api, O o) {
        this.zzaBB = false;
        this.zzayY = api;
        this.zzaAL = o;
        this.zzaBC = Arrays.hashCode(new Object[]{this.zzayY, this.zzaAL});
    }

    public static <O extends ApiOptions> zzbcf<O> zza(Api<O> api, O o) {
        return new zzbcf(api, o);
    }

    public static <O extends ApiOptions> zzbcf<O> zzb(Api<O> api) {
        return new zzbcf(api);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbcf)) {
            return false;
        }
        zzbcf com_google_android_gms_internal_zzbcf = (zzbcf) obj;
        return !this.zzaBB && !com_google_android_gms_internal_zzbcf.zzaBB && zzbh.equal(this.zzayY, com_google_android_gms_internal_zzbcf.zzayY) && zzbh.equal(this.zzaAL, com_google_android_gms_internal_zzbcf.zzaAL);
    }

    public final int hashCode() {
        return this.zzaBC;
    }

    public final String zzpp() {
        return this.zzayY.getName();
    }
}
