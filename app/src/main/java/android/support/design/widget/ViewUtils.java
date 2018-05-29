package android.support.design.widget;

import android.graphics.PorterDuff.Mode;
import com.google.android.gms.common.ConnectionResult;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

class ViewUtils {
    ViewUtils() {
    }

    static Mode parseTintMode(int value, Mode defaultMode) {
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
            default:
                return defaultMode;
        }
    }
}
