package com.oneplus.lib.util;

import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import com.google.android.gms.common.ConnectionResult;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class DrawableUtils {
    public static Mode parseTintMode(int value, Mode defaultMode) {
        switch (value) {
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return Mode.SRC_OVER;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return Mode.SRC_IN;
            case ConnectionResult.SERVICE_INVALID:
                return Mode.SRC_ATOP;
            case ConnectionResult.TIMEOUT:
                return Mode.MULTIPLY;
            case ConnectionResult.INTERRUPTED:
                return Mode.SCREEN;
            case ConnectionResult.API_UNAVAILABLE:
                return VERSION.SDK_INT >= 11 ? Mode.valueOf("ADD") : defaultMode;
            default:
                return defaultMode;
        }
    }
}
