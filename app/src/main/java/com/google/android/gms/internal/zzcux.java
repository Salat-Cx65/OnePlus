package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;

public final class zzcux implements Optional {
    public static final zzcux zzbCQ;
    private final boolean zzalj;
    private final String zzalk;
    private final boolean zzamc;
    private final String zzamd;
    private final boolean zzbCR;
    private final boolean zzbCS;
    private final Long zzbCT;
    private final Long zzbCU;

    static {
        zzcuy com_google_android_gms_internal_zzcuy = new zzcuy();
        zzbCQ = new zzcux(false, false, null, false, null, false, null, null);
    }

    private zzcux(boolean z, boolean z2, String str, boolean z3, String str2, boolean z4, Long l, Long l2) {
        this.zzbCR = false;
        this.zzalj = false;
        this.zzalk = null;
        this.zzamc = false;
        this.zzbCS = false;
        this.zzamd = null;
        this.zzbCT = null;
        this.zzbCU = null;
    }

    public final String getServerClientId() {
        return this.zzalk;
    }

    public final boolean isIdTokenRequested() {
        return this.zzalj;
    }

    public final boolean zzAp() {
        return this.zzbCR;
    }

    public final boolean zzAq() {
        return this.zzamc;
    }

    @Nullable
    public final String zzAr() {
        return this.zzamd;
    }

    public final boolean zzAs() {
        return this.zzbCS;
    }

    @Nullable
    public final Long zzAt() {
        return this.zzbCT;
    }

    @Nullable
    public final Long zzAu() {
        return this.zzbCU;
    }
}
