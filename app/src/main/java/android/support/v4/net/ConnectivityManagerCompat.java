package android.support.v4.net;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class ConnectivityManagerCompat {
    private static final ConnectivityManagerCompatImpl IMPL;
    public static final int RESTRICT_BACKGROUND_STATUS_DISABLED = 1;
    public static final int RESTRICT_BACKGROUND_STATUS_ENABLED = 3;
    public static final int RESTRICT_BACKGROUND_STATUS_WHITELISTED = 2;

    static interface ConnectivityManagerCompatImpl {
        int getRestrictBackgroundStatus(ConnectivityManager connectivityManager);

        boolean isActiveNetworkMetered(ConnectivityManager connectivityManager);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface RestrictBackgroundStatus {
    }

    static class ConnectivityManagerCompatBaseImpl implements ConnectivityManagerCompatImpl {
        ConnectivityManagerCompatBaseImpl() {
        }

        public boolean isActiveNetworkMetered(ConnectivityManager cm) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                return true;
            }
            switch (info.getType()) {
                case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                case RESTRICT_BACKGROUND_STATUS_WHITELISTED:
                case RESTRICT_BACKGROUND_STATUS_ENABLED:
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                case ConnectionResult.RESOLUTION_REQUIRED:
                    return true;
                case RESTRICT_BACKGROUND_STATUS_DISABLED:
                case DetectedActivity.WALKING:
                case ConnectionResult.SERVICE_INVALID:
                    return false;
                default:
                    return true;
            }
        }

        public int getRestrictBackgroundStatus(ConnectivityManager cm) {
            return RESTRICT_BACKGROUND_STATUS_ENABLED;
        }
    }

    @RequiresApi(16)
    static class ConnectivityManagerCompatApi16Impl extends ConnectivityManagerCompatBaseImpl {
        ConnectivityManagerCompatApi16Impl() {
        }

        public boolean isActiveNetworkMetered(ConnectivityManager cm) {
            return cm.isActiveNetworkMetered();
        }
    }

    @RequiresApi(24)
    static class ConnectivityManagerCompatApi24Impl extends ConnectivityManagerCompatApi16Impl {
        ConnectivityManagerCompatApi24Impl() {
        }

        public int getRestrictBackgroundStatus(ConnectivityManager cm) {
            return cm.getRestrictBackgroundStatus();
        }
    }

    static {
        if (VERSION.SDK_INT >= 24) {
            IMPL = new ConnectivityManagerCompatApi24Impl();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new ConnectivityManagerCompatApi16Impl();
        } else {
            IMPL = new ConnectivityManagerCompatBaseImpl();
        }
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static boolean isActiveNetworkMetered(ConnectivityManager cm) {
        return IMPL.isActiveNetworkMetered(cm);
    }

    public static NetworkInfo getNetworkInfoFromBroadcast(ConnectivityManager cm, Intent intent) {
        NetworkInfo info = (NetworkInfo) intent.getParcelableExtra("networkInfo");
        return info != null ? cm.getNetworkInfo(info.getType()) : null;
    }

    public static int getRestrictBackgroundStatus(ConnectivityManager cm) {
        return IMPL.getRestrictBackgroundStatus(cm);
    }

    private ConnectivityManagerCompat() {
    }
}
