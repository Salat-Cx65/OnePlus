package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzf;

public final class zzbha {
    public static final Api<NoOptions> API;
    public static final zzbhc zzaIA;
    public static final zzf<zzbhi> zzajT;
    private static final zza<zzbhi, NoOptions> zzajU;

    static {
        zzajT = new zzf();
        zzajU = new zzbhb();
        API = new Api("Common.API", zzajU, zzajT);
        zzaIA = new zzbhd();
    }
}
