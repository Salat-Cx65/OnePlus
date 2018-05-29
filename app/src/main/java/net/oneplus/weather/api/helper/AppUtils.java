package net.oneplus.weather.api.helper;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtils {
    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            return 0;
        }
    }
}
