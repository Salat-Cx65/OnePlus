package com.google.android.gms.location;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbck;
import com.google.android.gms.internal.zzcdn;
import com.google.android.gms.internal.zzcec;
import com.google.android.gms.internal.zzcev;
import com.google.android.gms.internal.zzcfg;

public class LocationServices {
    public static final Api<NoOptions> API;
    public static final FusedLocationProviderApi FusedLocationApi;
    public static final GeofencingApi GeofencingApi;
    public static final SettingsApi SettingsApi;
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
        zzajU = new zzs();
        API = new Api("LocationServices.API", zzajU, zzajT);
        FusedLocationApi = new zzcdn();
        GeofencingApi = new zzcec();
        SettingsApi = new zzcfg();
    }

    private LocationServices() {
    }

    public static FusedLocationProviderClient getFusedLocationProviderClient(@NonNull Activity activity) {
        return new FusedLocationProviderClient(activity);
    }

    public static FusedLocationProviderClient getFusedLocationProviderClient(@NonNull Context context) {
        return new FusedLocationProviderClient(context);
    }

    public static GeofencingClient getGeofencingClient(@NonNull Activity activity) {
        return new GeofencingClient(activity);
    }

    public static GeofencingClient getGeofencingClient(@NonNull Context context) {
        return new GeofencingClient(context);
    }

    public static SettingsClient getSettingsClient(@NonNull Activity activity) {
        return new SettingsClient(activity);
    }

    public static SettingsClient getSettingsClient(@NonNull Context context) {
        return new SettingsClient(context);
    }

    public static zzcev zzg(GoogleApiClient googleApiClient) {
        boolean z = true;
        zzbr.zzb(googleApiClient != null, (Object) "GoogleApiClient parameter is required.");
        zzcev com_google_android_gms_internal_zzcev = (zzcev) googleApiClient.zza(zzajT);
        if (com_google_android_gms_internal_zzcev == null) {
            z = false;
        }
        zzbr.zza(z, (Object) "GoogleApiClient is not configured to use the LocationServices.API Api. Pass thisinto GoogleApiClient.Builder#addApi() to use this feature.");
        return com_google_android_gms_internal_zzcev;
    }
}
