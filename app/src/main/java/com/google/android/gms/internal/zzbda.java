package com.google.android.gms.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.zzq;

public final class zzbda<O extends ApiOptions> extends GoogleApi<O> {
    private final zza<? extends zzcuw, zzcux> zzaBg;
    private final zze zzaCA;
    private final zzbcu zzaCB;
    private final zzq zzaCC;

    public zzbda(@NonNull Context context, Api<O> api, Looper looper, @NonNull zze com_google_android_gms_common_api_Api_zze, @NonNull zzbcu com_google_android_gms_internal_zzbcu, zzq com_google_android_gms_common_internal_zzq, zza<? extends zzcuw, zzcux> com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux) {
        super(context, api, looper);
        this.zzaCA = com_google_android_gms_common_api_Api_zze;
        this.zzaCB = com_google_android_gms_internal_zzbcu;
        this.zzaCC = com_google_android_gms_common_internal_zzq;
        this.zzaBg = com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux;
        this.zzaAP.zzb((GoogleApi) this);
    }

    public final zze zza(Looper looper, zzbep<O> com_google_android_gms_internal_zzbep_O) {
        this.zzaCB.zza(com_google_android_gms_internal_zzbep_O);
        return this.zzaCA;
    }

    public final zzbfv zza(Context context, Handler handler) {
        return new zzbfv(context, handler, this.zzaCC, this.zzaBg);
    }

    public final zze zzpH() {
        return this.zzaCA;
    }
}
