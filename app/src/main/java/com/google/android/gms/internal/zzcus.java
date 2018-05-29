package com.google.android.gms.internal;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.Scope;

public final class zzcus {
    public static final Api<zzcux> API;
    private static Api<zzcuv> zzaMg;
    private static zzf<zzcvg> zzajT;
    public static final zza<zzcvg, zzcux> zzajU;
    private static Scope zzalX;
    private static Scope zzalY;
    private static zzf<zzcvg> zzbCN;
    private static zza<zzcvg, zzcuv> zzbCO;

    static {
        zzajT = new zzf();
        zzbCN = new zzf();
        zzajU = new zzcut();
        zzbCO = new zzcuu();
        zzalX = new Scope(Scopes.PROFILE);
        zzalY = new Scope(Scopes.EMAIL);
        API = new Api("SignIn.API", zzajU, zzajT);
        zzaMg = new Api("SignIn.INTERNAL_API", zzbCO, zzbCN);
    }
}
