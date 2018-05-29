package com.oneplus.lib.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public abstract class AppUtils {
    public static final String SP_KEY_PREV_VER = "prev_install_versionCode";
    private static final String TAG;

    static {
        TAG = AppUtils.class.getSimpleName();
    }

    public static void setCurrentVersion(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            Log.d(TAG, "Get app version code failed! " + e.toString());
        }
        PreferenceUtils.applyInt(context, SP_KEY_PREV_VER, versionCode);
    }

    public static int getPrevVersion(Context context) {
        return PreferenceUtils.getInt(context, SP_KEY_PREV_VER, -1);
    }

    public static int getCurrentVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            Log.d(TAG, "Get app version code failed! " + e.toString());
            return -1;
        }
    }

    public static boolean versionCodeChanged(Context context) {
        return getPrevVersion(context) != getCurrentVersion(context);
    }
}
