package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.util.zzk;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzs {
    private static final SimpleArrayMap<String, String> zzaHq;

    static {
        zzaHq = new SimpleArrayMap();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String zzaB(android.content.Context r3) {
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzs.zzaB(android.content.Context):java.lang.String");
        /*
        r0 = r3.getPackageName();
        r1 = com.google.android.gms.internal.zzbim.zzaP(r3);	 Catch:{ NameNotFoundException -> 0x0020, NullPointerException -> 0x0011 }
        r1 = r1.zzcN(r0);	 Catch:{ NameNotFoundException -> 0x0020, NullPointerException -> 0x0011 }
        r0 = r1.toString();	 Catch:{ NameNotFoundException -> 0x0020, NullPointerException -> 0x0011 }
    L_0x0010:
        return r0;
    L_0x0011:
        r1 = move-exception;
    L_0x0012:
        r1 = r3.getApplicationInfo();
        r1 = r1.name;
        r2 = android.text.TextUtils.isEmpty(r1);
        if (r2 != 0) goto L_0x0010;
    L_0x001e:
        r0 = r1;
        goto L_0x0010;
    L_0x0020:
        r1 = move-exception;
        goto L_0x0012;
        */
    }

    @Nullable
    public static String zzg(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return resources.getString(R.string.common_google_play_services_install_title);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return resources.getString(R.string.common_google_play_services_update_title);
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return resources.getString(R.string.common_google_play_services_enable_title);
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
            case ConnectionResult.RESOLUTION_REQUIRED:
            case ConnectionResult.SERVICE_UPDATING:
                return null;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                Log.e("GoogleApiAvailability", "An invalid account was specified when connecting. Please provide a valid account.");
                return zzz(context, "common_google_play_services_invalid_account_title");
            case DetectedActivity.WALKING:
                Log.e("GoogleApiAvailability", "Network error occurred. Please retry request later.");
                return zzz(context, "common_google_play_services_network_error_title");
            case DetectedActivity.RUNNING:
                Log.e("GoogleApiAvailability", "Internal error occurred. Please see logs for detailed information");
                return null;
            case ConnectionResult.SERVICE_INVALID:
                Log.e("GoogleApiAvailability", "Google Play services is invalid. Cannot recover.");
                return null;
            case ConnectionResult.DEVELOPER_ERROR:
                Log.e("GoogleApiAvailability", "Developer error occurred. Please see logs for detailed information");
                return null;
            case ConnectionResult.LICENSE_CHECK_FAILED:
                Log.e("GoogleApiAvailability", "The application is not licensed to the user.");
                return null;
            case ConnectionResult.API_UNAVAILABLE:
                Log.e("GoogleApiAvailability", "One of the API components you attempted to connect to is not available.");
                return null;
            case ConnectionResult.SIGN_IN_FAILED:
                Log.e("GoogleApiAvailability", "The specified account could not be signed in.");
                return zzz(context, "common_google_play_services_sign_in_failed_title");
            case ConnectionResult.RESTRICTED_PROFILE:
                Log.e("GoogleApiAvailability", "The current user profile is restricted and could not use authenticated features.");
                return zzz(context, "common_google_play_services_restricted_profile_title");
            default:
                Log.e("GoogleApiAvailability", new StringBuilder(33).append("Unexpected error code ").append(i).toString());
                return null;
        }
    }

    @NonNull
    public static String zzh(Context context, int i) {
        String zzz = i == 6 ? zzz(context, "common_google_play_services_resolution_required_title") : zzg(context, i);
        return zzz == null ? context.getResources().getString(R.string.common_google_play_services_notification_ticker) : zzz;
    }

    @NonNull
    public static String zzi(Context context, int i) {
        Resources resources = context.getResources();
        String zzaB = zzaB(context);
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return resources.getString(R.string.common_google_play_services_install_text, new Object[]{zzaB});
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                if (zzk.zzaH(context)) {
                    return resources.getString(R.string.common_google_play_services_wear_update_text);
                }
                return resources.getString(R.string.common_google_play_services_update_text, new Object[]{zzaB});
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return resources.getString(R.string.common_google_play_services_enable_text, new Object[]{zzaB});
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return zzl(context, "common_google_play_services_invalid_account_text", zzaB);
            case DetectedActivity.WALKING:
                return zzl(context, "common_google_play_services_network_error_text", zzaB);
            case ConnectionResult.SERVICE_INVALID:
                return resources.getString(R.string.common_google_play_services_unsupported_text, new Object[]{zzaB});
            case ConnectionResult.API_UNAVAILABLE:
                return zzl(context, "common_google_play_services_api_unavailable_text", zzaB);
            case ConnectionResult.SIGN_IN_FAILED:
                return zzl(context, "common_google_play_services_sign_in_failed_text", zzaB);
            case ConnectionResult.SERVICE_UPDATING:
                return resources.getString(R.string.common_google_play_services_updating_text, new Object[]{zzaB});
            case ConnectionResult.RESTRICTED_PROFILE:
                return zzl(context, "common_google_play_services_restricted_profile_text", zzaB);
            default:
                return resources.getString(R.string.common_google_play_services_unknown_issue, new Object[]{zzaB});
        }
    }

    @NonNull
    public static String zzj(Context context, int i) {
        return i == 6 ? zzl(context, "common_google_play_services_resolution_required_text", zzaB(context)) : zzi(context, i);
    }

    @NonNull
    public static String zzk(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return resources.getString(R.string.common_google_play_services_install_button);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return resources.getString(R.string.common_google_play_services_update_button);
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return resources.getString(R.string.common_google_play_services_enable_button);
            default:
                return resources.getString(17039370);
        }
    }

    private static String zzl(Context context, String str, String str2) {
        Resources resources = context.getResources();
        String zzz = zzz(context, str);
        if (zzz == null) {
            zzz = resources.getString(R.string.common_google_play_services_unknown_issue);
        }
        return String.format(resources.getConfiguration().locale, zzz, new Object[]{str2});
    }

    @Nullable
    private static String zzz(Context context, String str) {
        synchronized (zzaHq) {
            String str2 = (String) zzaHq.get(str);
            if (str2 != null) {
                return str2;
            }
            Resources remoteResource = GooglePlayServicesUtil.getRemoteResource(context);
            if (remoteResource == null) {
                return null;
            }
            int identifier = remoteResource.getIdentifier(str, "string", GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
            if (identifier == 0) {
                String str3 = "GoogleApiAvailability";
                String str4 = "Missing resource: ";
                str2 = String.valueOf(str);
                Log.w(str3, str2.length() != 0 ? str4.concat(str2) : new String(str4));
                return null;
            }
            Object string = remoteResource.getString(identifier);
            if (TextUtils.isEmpty(string)) {
                str3 = "GoogleApiAvailability";
                str4 = "Got empty resource: ";
                str2 = String.valueOf(str);
                Log.w(str3, str2.length() != 0 ? str4.concat(str2) : new String(str4));
                return null;
            }
            zzaHq.put(str, string);
            return string;
        }
    }
}
