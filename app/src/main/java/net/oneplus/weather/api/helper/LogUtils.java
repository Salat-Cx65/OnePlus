package net.oneplus.weather.api.helper;

import android.util.Log;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class LogUtils {
    public static final boolean DEBUG = false;
    public static final boolean LOGGABLE;
    public static final String TAG = "OpWeatherApi";

    static {
        LOGGABLE = Log.isLoggable(TAG, RainSurfaceView.RAIN_LEVEL_SHOWER);
    }

    private LogUtils() {
    }

    public static void v(String message, Object... args) {
        if (LOGGABLE) {
            String str = TAG;
            if (args != null) {
                message = String.format(message, args);
            }
            Log.v(str, message);
        }
    }

    public static void v(String tag, String message, Object... args) {
        if (LOGGABLE) {
            String str = "OpWeatherApi/" + tag;
            if (args != null) {
                message = String.format(message, args);
            }
            Log.v(str, message);
        }
    }

    public static void d(String message, Object... args) {
        if (LOGGABLE) {
            String str = TAG;
            if (args != null) {
                message = String.format(message, args);
            }
            Log.d(str, message);
        }
    }

    public static void d(String tag, String message, Object... args) {
        if (LOGGABLE) {
            String str = "OpWeatherApi/" + tag;
            if (args != null) {
                message = String.format(message, args);
            }
            Log.d(str, message);
        }
    }

    public static void i(String message, Object... args) {
        if (LOGGABLE) {
            String str = TAG;
            if (args != null) {
                message = String.format(message, args);
            }
            Log.i(str, message);
        }
    }

    public static void i(String tag, String message, Object... args) {
        if (LOGGABLE) {
            String str = "OpWeatherApi/" + tag;
            if (args != null) {
                message = String.format(message, args);
            }
            Log.i(str, message);
        }
    }

    public static void w(String message, Object... args) {
        if (LOGGABLE) {
            String str = TAG;
            if (args != null) {
                message = String.format(message, args);
            }
            Log.w(str, message);
        }
    }

    public static void w(String tag, String message, Object... args) {
        if (LOGGABLE) {
            String str = "OpWeatherApi/" + tag;
            if (args != null) {
                message = String.format(message, args);
            }
            Log.w(str, message);
        }
    }

    public static void e(String message, Object... args) {
        if (LOGGABLE) {
            String str = TAG;
            if (args != null) {
                message = String.format(message, args);
            }
            Log.e(str, message);
        }
    }

    public static void e(String tag, String message, Object... args) {
        if (LOGGABLE) {
            String str = "OpWeatherApi/" + tag;
            if (args != null) {
                message = String.format(message, args);
            }
            Log.e(str, message);
        }
    }

    public static void e(String message, Exception e) {
        if (LOGGABLE) {
            Log.e(TAG, message, e);
        }
    }

    public static void e(String tag, String message, Exception e) {
        if (LOGGABLE) {
            Log.e("OpWeatherApi/" + tag, message, e);
        }
    }

    public static void wtf(String message, Object... args) {
        if (LOGGABLE) {
            String str = TAG;
            if (args != null) {
                message = String.format(message, args);
            }
            Log.wtf(str, message);
        }
    }

    public static void wtf(String tag, String message, Object... args) {
        if (LOGGABLE) {
            String str = "OpWeatherApi/" + tag;
            if (args != null) {
                message = String.format(message, args);
            }
            Log.wtf(str, message);
        }
    }
}
