package com.oneplus.custom.utils;

import android.util.Log;

public class OpCustomizeSettings {
    protected static final boolean DBG;
    protected static final String TAG = "OpCustomizeSettings";
    private static OpCustomizeSettings sOpCustomizeSettings;
    private static final String sProjectName;

    public enum CUSTOM_BACK_COVER_TYPE {
        NONE,
        LCH,
        MYH,
        YYB
    }

    public enum CUSTOM_TYPE {
        NONE,
        JCC,
        SW,
        AVG
    }

    protected static class MyLog {
        protected MyLog() {
        }

        protected static void v(String tag, String log) {
            if (DBG) {
                Log.v(tag, log);
            }
        }

        protected static void d(String tag, String log) {
            if (DBG) {
                Log.d(tag, log);
            }
        }

        protected static void i(String tag, String log) {
            if (DBG) {
                Log.i(tag, log);
            }
        }

        protected static void w(String tag, String log) {
            if (DBG) {
                Log.w(tag, log);
            }
        }

        protected static void e(String tag, String log) {
            if (DBG) {
                Log.e(tag, log);
            }
        }
    }

    static {
        DBG = "true".equals(SystemProperties.get("persist.sys.assert.panic"));
        sProjectName = SystemProperties.get("ro.boot.project_name");
    }

    public static CUSTOM_TYPE getCustomType() {
        return getInstance().getCustomization();
    }

    public static CUSTOM_BACK_COVER_TYPE getBackCoverType() {
        return getInstance().getCustomBackCoverType();
    }

    public static long getBackCoverColor() {
        return getInstance().getCustomBackCoverColor();
    }

    private static OpCustomizeSettings getInstance() {
        if (sOpCustomizeSettings == null) {
            MyLog.v(TAG, "sProjectName = " + sProjectName);
            if ("16859".equals(sProjectName) || "17801".equals(sProjectName)) {
                sOpCustomizeSettings = new OpCustomizeSettingsG1();
            } else if ("17819".equals(sProjectName)) {
                sOpCustomizeSettings = new OpCustomizeSettingsG2();
            } else {
                sOpCustomizeSettings = new OpCustomizeSettings();
            }
        }
        return sOpCustomizeSettings;
    }

    protected CUSTOM_TYPE getCustomization() {
        return CUSTOM_TYPE.NONE;
    }

    protected CUSTOM_BACK_COVER_TYPE getCustomBackCoverType() {
        return CUSTOM_BACK_COVER_TYPE.NONE;
    }

    protected long getCustomBackCoverColor() {
        return 0;
    }
}
