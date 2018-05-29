package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller.SessionInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.util.zzk;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.common.util.zzy;
import com.google.android.gms.internal.zzbim;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import net.oneplus.weather.api.WeatherRequest.Type;
import net.oneplus.weather.provider.CitySearchProvider;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class zzo {
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    private static boolean zzaAq;
    private static boolean zzaAr;
    private static boolean zzaAs;
    private static boolean zzaAt;
    static final AtomicBoolean zzaAu;
    private static final AtomicBoolean zzaAv;

    static {
        GOOGLE_PLAY_SERVICES_VERSION_CODE = 11010000;
        zzaAq = false;
        zzaAr = false;
        zzaAs = false;
        zzaAt = false;
        zzaAu = new AtomicBoolean();
        zzaAv = new AtomicBoolean();
    }

    zzo() {
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int i, Context context, int i2) {
        return zze.zzoU().getErrorResolutionPendingIntent(context, i, i2);
    }

    @Deprecated
    public static String getErrorString(int i) {
        return ConnectionResult.getStatusString(i);
    }

    @Deprecated
    public static String getOpenSourceSoftwareLicenseInfo(Context context) {
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(new Builder().scheme("android.resource").authority(GOOGLE_PLAY_SERVICES_PACKAGE).appendPath("raw").appendPath("oss_notice").build());
            String next = new Scanner(openInputStream).useDelimiter("\\A").next();
            if (openInputStream == null) {
                return next;
            }
            openInputStream.close();
            return next;
        } catch (NoSuchElementException e) {
            if (openInputStream != null) {
                openInputStream.close();
            }
            return null;
        } catch (Exception e2) {
            return null;
        } catch (Throwable th) {
            if (openInputStream != null) {
                openInputStream.close();
            }
        }
    }

    public static Context getRemoteContext(Context context) {
        try {
            return context.createPackageContext(GOOGLE_PLAY_SERVICES_PACKAGE, RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static Resources getRemoteResource(Context context) {
        try {
            return context.getPackageManager().getResourcesForApplication(GOOGLE_PLAY_SERVICES_PACKAGE);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            context.getResources().getString(R.string.common_google_play_services_unknown_issue);
        } catch (Throwable th) {
            Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
        }
        if (!(GOOGLE_PLAY_SERVICES_PACKAGE.equals(context.getPackageName()) || zzaAv.get())) {
            int zzaE = zzbg.zzaE(context);
            if (zzaE == 0) {
                throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            } else if (zzaE != GOOGLE_PLAY_SERVICES_VERSION_CODE) {
                int i = GOOGLE_PLAY_SERVICES_VERSION_CODE;
                String valueOf = String.valueOf("com.google.android.gms.version");
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 290).append("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected ").append(i).append(" but found ").append(zzaE).append(".  You must have the following declaration within the <application> element:     <meta-data android:name=\"").append(valueOf).append("\" android:value=\"@integer/google_play_services_version\" />").toString());
            }
        }
        int i2 = (zzk.zzaH(context) || zzk.zzaJ(context)) ? 0 : 1;
        PackageInfo packageInfo = null;
        if (i2 != 0) {
            try {
                packageInfo = packageManager.getPackageInfo(GOOGLE_PLAY_STORE_PACKAGE, 8256);
            } catch (NameNotFoundException e) {
                Log.w("GooglePlayServicesUtil", "Google Play Store is missing.");
                return ConnectionResult.SERVICE_INVALID;
            }
        }
        try {
            PackageInfo packageInfo2 = packageManager.getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, Type.SUCCESS);
            zzp.zzax(context);
            if (i2 != 0) {
                if (zzp.zza(packageInfo, zzj.zzaAm) == null) {
                    Log.w("GooglePlayServicesUtil", "Google Play Store signature invalid.");
                    return ConnectionResult.SERVICE_INVALID;
                }
                if (zzp.zza(packageInfo2, zzp.zza(packageInfo, zzj.zzaAm)) == null) {
                    Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                    return ConnectionResult.SERVICE_INVALID;
                }
            } else if (zzp.zza(packageInfo2, zzj.zzaAm) == null) {
                Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                return ConnectionResult.SERVICE_INVALID;
            }
            if (packageInfo2.versionCode / 1000 < GOOGLE_PLAY_SERVICES_VERSION_CODE / 1000) {
                Log.w("GooglePlayServicesUtil", new StringBuilder(77).append("Google Play services out of date.  Requires ").append(GOOGLE_PLAY_SERVICES_VERSION_CODE).append(" but found ").append(packageInfo2.versionCode).toString());
                return RainSurfaceView.RAIN_LEVEL_SHOWER;
            }
            ApplicationInfo applicationInfo = packageInfo2.applicationInfo;
            if (applicationInfo == null) {
                try {
                    applicationInfo = packageManager.getApplicationInfo(GOOGLE_PLAY_SERVICES_PACKAGE, GOOGLE_PLAY_SERVICES_VERSION_CODE);
                } catch (Throwable e2) {
                    Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.", e2);
                    return 1;
                }
            }
            return !applicationInfo.enabled ? RainSurfaceView.RAIN_LEVEL_DOWNPOUR : 0;
        } catch (NameNotFoundException e3) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 1;
        }
    }

    @Deprecated
    public static boolean isUserRecoverableError(int i) {
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
            case ConnectionResult.SERVICE_INVALID:
                return true;
            default:
                return false;
        }
    }

    @Deprecated
    public static void zzah(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        int isGooglePlayServicesAvailable = zze.zzoU().isGooglePlayServicesAvailable(context);
        if (isGooglePlayServicesAvailable != 0) {
            zze.zzoU();
            Intent zza = zze.zza(context, isGooglePlayServicesAvailable, "e");
            Log.e("GooglePlayServicesUtil", new StringBuilder(57).append("GooglePlayServices not available due to error ").append(isGooglePlayServicesAvailable).toString());
            if (zza == null) {
                throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
            }
            throw new GooglePlayServicesRepairableException(isGooglePlayServicesAvailable, "Google Play Services not available", zza);
        }
    }

    @Deprecated
    public static void zzat(Context context) {
        if (!zzaAu.getAndSet(true)) {
            try {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                if (notificationManager != null) {
                    notificationManager.cancel(10436);
                }
            } catch (SecurityException e) {
            }
        }
    }

    @Deprecated
    public static int zzau(Context context) {
        int i = GOOGLE_PLAY_SERVICES_VERSION_CODE;
        try {
            i = context.getPackageManager().getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, GOOGLE_PLAY_SERVICES_VERSION_CODE).versionCode;
            return i;
        } catch (NameNotFoundException e) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return i;
        }
    }

    public static boolean zzaw(Context context) {
        if (!zzaAt) {
            try {
                PackageInfo packageInfo = zzbim.zzaP(context).getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, Type.SUCCESS);
                if (packageInfo != null) {
                    zzp.zzax(context);
                    if (zzp.zza(packageInfo, zzj.zzaAm[1]) != null) {
                        zzaAs = true;
                        zzaAt = true;
                    }
                }
                zzaAs = false;
                zzaAt = true;
            } catch (Throwable e) {
                Log.w("GooglePlayServicesUtil", "Cannot find Google Play services package name.", e);
                zzaAt = true;
            }
        }
        return zzaAs || !"user".equals(Build.TYPE);
    }

    @TargetApi(19)
    @Deprecated
    public static boolean zzb(Context context, int i, String str) {
        return zzy.zzb(context, i, str);
    }

    @Deprecated
    public static boolean zze(Context context, int i) {
        return i == 18 ? true : i == 1 ? zzy(context, GOOGLE_PLAY_SERVICES_PACKAGE) : false;
    }

    @Deprecated
    public static boolean zzf(Context context, int i) {
        return zzy.zzf(context, i);
    }

    @TargetApi(21)
    static boolean zzy(Context context, String str) {
        boolean equals = str.equals(GOOGLE_PLAY_SERVICES_PACKAGE);
        if (zzs.zzsd()) {
            for (SessionInfo sessionInfo : context.getPackageManager().getPackageInstaller().getAllSessions()) {
                if (str.equals(sessionInfo.getAppPackageName())) {
                    return true;
                }
            }
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, CitySearchProvider.PROVIDER_YAHOO_WEATHER);
            if (equals) {
                return applicationInfo.enabled;
            }
            if (applicationInfo.enabled) {
                if (zzs.zzsa()) {
                    Bundle applicationRestrictions = ((UserManager) context.getSystemService("user")).getApplicationRestrictions(context.getPackageName());
                    if (applicationRestrictions != null && "true".equals(applicationRestrictions.getString("restricted_profile"))) {
                        int i = 1;
                        if (r0 == null) {
                            return true;
                        }
                    }
                }
                Object obj = null;
                if (obj == null) {
                    return true;
                }
            }
            return false;
        } catch (NameNotFoundException e) {
            return false;
        }
    }
}
