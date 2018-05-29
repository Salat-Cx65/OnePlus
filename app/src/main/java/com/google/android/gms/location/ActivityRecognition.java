package com.google.android.gms.location;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.internal.zzbck;
import com.google.android.gms.internal.zzcdf;
import com.google.android.gms.internal.zzcev;

public class ActivityRecognition {
    public static final Api<NoOptions> API;
    public static final ActivityRecognitionApi ActivityRecognitionApi;
    public static final String CLIENT_NAME = "activity_recognition";
    private static final zzf<zzcev> zzajT;
    private static final com.google.android.gms.common.api.Api.zza<zzcev, NoOptions> zzajU;

    public static abstract class zza<R extends Result> extends zzbck<R, zzcev> {
        public zza(GoogleApiClient googleApiClient) {
            super(API, googleApiClient);
        }

        public final /* bridge */ /* synthetic */ void setResult(Object obj) {
            super.setResult((Result) obj);
        }
    }

    static {
        zzajT = new zzf();
        zzajU = new zza();
        API = new Api("ActivityRecognition.API", zzajU, zzajT);
        ActivityRecognitionApi = new zzcdf();
    }

    private ActivityRecognition() {
    }
}
