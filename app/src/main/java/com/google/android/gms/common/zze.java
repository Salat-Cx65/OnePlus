package com.google.android.gms.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzaj;
import com.google.android.gms.common.util.zzk;
import com.google.android.gms.internal.zzbim;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class zze {
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final zze zzaAe;

    static {
        GOOGLE_PLAY_SERVICES_VERSION_CODE = zzo.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        zzaAe = new zze();
    }

    zze() {
    }

    @Nullable
    public static Intent zza(Context context, int i, @Nullable String str) {
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return (context == null || !zzk.zzaH(context)) ? zzaj.zzw(GOOGLE_PLAY_SERVICES_PACKAGE, zzx(context, str)) : zzaj.zzrC();
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return zzaj.zzcD(GOOGLE_PLAY_SERVICES_PACKAGE);
            default:
                return null;
        }
    }

    public static void zzas(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zzo.zzah(context);
    }

    public static void zzat(Context context) {
        zzo.zzat(context);
    }

    public static int zzau(Context context) {
        return zzo.zzau(context);
    }

    public static boolean zze(Context context, int i) {
        return zzo.zze(context, i);
    }

    public static zze zzoU() {
        return zzaAe;
    }

    private static String zzx(@Nullable Context context, @Nullable String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("gcore_");
        stringBuilder.append(GOOGLE_PLAY_SERVICES_VERSION_CODE);
        stringBuilder.append("-");
        if (!TextUtils.isEmpty(str)) {
            stringBuilder.append(str);
        }
        stringBuilder.append("-");
        if (context != null) {
            stringBuilder.append(context.getPackageName());
        }
        stringBuilder.append("-");
        if (context != null) {
            try {
                stringBuilder.append(zzbim.zzaP(context).getPackageInfo(context.getPackageName(), 0).versionCode);
            } catch (NameNotFoundException e) {
            }
        }
        return stringBuilder.toString();
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context context, int i, int i2) {
        return zza(context, i, i2, null);
    }

    public String getErrorString(int i) {
        return zzo.getErrorString(i);
    }

    @Nullable
    @Deprecated
    public String getOpenSourceSoftwareLicenseInfo(Context context) {
        return zzo.getOpenSourceSoftwareLicenseInfo(context);
    }

    public int isGooglePlayServicesAvailable(Context context) {
        int isGooglePlayServicesAvailable = zzo.isGooglePlayServicesAvailable(context);
        return zzo.zze(context, isGooglePlayServicesAvailable) ? ConnectionResult.SERVICE_UPDATING : isGooglePlayServicesAvailable;
    }

    public boolean isUserResolvableError(int i) {
        return zzo.isUserRecoverableError(i);
    }

    @Nullable
    public final PendingIntent zza(Context context, int i, int i2, @Nullable String str) {
        Intent zza = zza(context, i, str);
        return zza == null ? null : PendingIntent.getActivity(context, i2, zza, 268435456);
    }

    @Nullable
    @Deprecated
    public final Intent zzak(int i) {
        return zza(null, i, null);
    }
}
