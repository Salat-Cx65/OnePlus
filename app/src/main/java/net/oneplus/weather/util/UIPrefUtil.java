package net.oneplus.weather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class UIPrefUtil {
    private static final String P_LAST_SHOW_DIALOG_DAY = "p.last.show.day";
    private static final String P_LAST_UPGRADE_VERSION = "p.last.upgrade.version";
    private static final String P_REMIND_TIMES = "p.remind.times";
    private static final String SHARED_PREFERENCES_NAME_UPDATE = "upgrade_info";
    private static SharedPreferences sPref;

    public static void clear(Context context) {
        getUpdateSharedPreferences(context).edit().clear().commit();
    }

    private static SharedPreferences getUpdateSharedPreferences(Context context) {
        if (sPref == null && context != null) {
            context = context.getApplicationContext();
            if (context != null) {
                sPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME_UPDATE, 0);
            }
        }
        return sPref;
    }

    private static void putIntValue(Context context, String key, int value) {
        getUpdateSharedPreferences(context).edit().putInt(key, value).commit();
    }

    private static int getIntValue(Context context, String key, int defValue) {
        return getUpdateSharedPreferences(context).getInt(key, defValue);
    }

    private static void putStringValue(Context context, String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            Editor editor = getUpdateSharedPreferences(context).edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    private static String getStringValue(Context context, String key, String defValue) {
        return getUpdateSharedPreferences(context).getString(key, defValue);
    }

    private static void removeValue(Context context, String key) {
        getUpdateSharedPreferences(context).edit().remove(key).commit();
    }

    public static void setLastUpgradeVersion(Context context, int version) {
        putIntValue(context, P_LAST_UPGRADE_VERSION, version);
    }

    public static int getLastUpgradeVersion(Context context) {
        return getIntValue(context, P_LAST_UPGRADE_VERSION, 0);
    }

    public static void removeLastUpgradeVersion(Context context) {
        removeValue(context, P_LAST_UPGRADE_VERSION);
    }

    public static void setRemindTimes(Context context, int times) {
        putIntValue(context, P_REMIND_TIMES, times);
    }

    public static int getRemindTimes(Context context) {
        return getIntValue(context, P_REMIND_TIMES, 0);
    }

    public static void removeRemindTimes(Context context) {
        removeValue(context, P_REMIND_TIMES);
    }
}
