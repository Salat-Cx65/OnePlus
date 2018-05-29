package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.zzp;
import com.google.android.gms.internal.zzbim;
import net.oneplus.weather.api.WeatherRequest.Type;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzy {
    @TargetApi(19)
    public static boolean zzb(Context context, int i, String str) {
        return zzbim.zzaP(context).zzf(i, str);
    }

    public static boolean zzf(Context context, int i) {
        boolean z = false;
        if (!zzb(context, i, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE)) {
            return false;
        }
        try {
            z = zzp.zzax(context).zza(context.getPackageManager(), context.getPackageManager().getPackageInfo(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, Type.SUCCESS));
            return z;
        } catch (NameNotFoundException e) {
            if (!Log.isLoggable("UidVerifier", RainSurfaceView.RAIN_LEVEL_DOWNPOUR)) {
                return z;
            }
            Log.d("UidVerifier", "Package manager can't find google play services package, defaulting to false");
            return z;
        }
    }
}
