package net.oneplus.weather.api.nodes;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.R;
import net.oneplus.weather.api.helper.StringUtils;
import net.oneplus.weather.api.nodes.Wind.Direction;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class OppoWind extends Wind {
    private final String mWindPower;

    public OppoWind(String areaCode, String dataSource, Direction direction, String windPower) {
        super(areaCode, dataSource, direction);
        this.mWindPower = windPower;
    }

    public OppoWind(String areaCode, String areaName, String dataSource, Direction direction, String windPower) {
        super(areaCode, areaName, dataSource, direction);
        this.mWindPower = windPower;
    }

    public String getWindPower() {
        return this.mWindPower;
    }

    public String getWindPower(Context context) {
        return toInternationalWind(context);
    }

    private String toInternationalWind(Context context) {
        if (StringUtils.isBlank(this.mWindPower)) {
            return net.oneplus.weather.util.StringUtils.EMPTY_STRING;
        }
        String str = this.mWindPower;
        Object obj = -1;
        switch (str.hashCode()) {
            case 33911:
                if (str.equals("0\u7ea7")) {
                    obj = null;
                }
                break;
            case 33942:
                if (str.equals("1\u7ea7")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case 33973:
                if (str.equals("2\u7ea7")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
            case 34004:
                if (str.equals("3\u7ea7")) {
                    obj = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                }
                break;
            case 34035:
                if (str.equals("4\u7ea7")) {
                    obj = RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
                }
                break;
            case 34066:
                if (str.equals("5\u7ea7")) {
                    obj = ConnectionResult.RESOLUTION_REQUIRED;
                }
                break;
            case 34097:
                if (str.equals("6\u7ea7")) {
                    obj = DetectedActivity.WALKING;
                }
                break;
            case 34128:
                if (str.equals("7\u7ea7")) {
                    obj = DetectedActivity.RUNNING;
                }
                break;
            case 34159:
                if (str.equals("8\u7ea7")) {
                    obj = ConnectionResult.SERVICE_INVALID;
                }
                break;
            case 34190:
                if (str.equals("9\u7ea7")) {
                    obj = ConnectionResult.DEVELOPER_ERROR;
                }
                break;
            case 81000:
                if (str.equals("10\u7ea7")) {
                    obj = ConnectionResult.LICENSE_CHECK_FAILED;
                }
                break;
            case 81031:
                if (str.equals("11\u7ea7")) {
                    obj = WeatherCircleView.ARC_DIN;
                }
                break;
            case 81062:
                if (str.equals("12\u7ea7")) {
                    obj = ConnectionResult.CANCELED;
                }
                break;
            case 798432:
                if (str.equals("\u5fae\u98ce")) {
                    obj = 1;
                }
                break;
            case 1596621:
                if (str.equals("3-4\u7ea7")) {
                    obj = ConnectionResult.TIMEOUT;
                }
                break;
            case 1626443:
                if (str.equals("4-5\u7ea7")) {
                    obj = ConnectionResult.INTERRUPTED;
                }
                break;
            case 1656265:
                if (str.equals("5-6\u7ea7")) {
                    obj = ConnectionResult.API_UNAVAILABLE;
                }
                break;
            case 1686087:
                if (str.equals("6-7\u7ea7")) {
                    obj = ConnectionResult.SIGN_IN_FAILED;
                }
                break;
            case 1715909:
                if (str.equals("7-8\u7ea7")) {
                    obj = ConnectionResult.SERVICE_UPDATING;
                }
                break;
            case 1745731:
                if (str.equals("8-9\u7ea7")) {
                    obj = ConnectionResult.SERVICE_MISSING_PERMISSION;
                }
                break;
            case 54062292:
                if (str.equals("9-10\u7ea7")) {
                    obj = ConnectionResult.RESTRICTED_PROFILE;
                }
                break;
            case 1448579033:
                if (str.equals("10-11\u7ea7")) {
                    obj = R.styleable.Toolbar_titleMargin;
                }
                break;
            case 1449502585:
                if (str.equals("11-12\u7ea7")) {
                    obj = R.styleable.Toolbar_titleMarginBottom;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_zero);
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_one);
            case ConnectionResult.RESOLUTION_REQUIRED:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_two);
            case DetectedActivity.WALKING:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_three);
            case DetectedActivity.RUNNING:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_four);
            case ConnectionResult.SERVICE_INVALID:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_five);
            case ConnectionResult.DEVELOPER_ERROR:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_six);
            case ConnectionResult.LICENSE_CHECK_FAILED:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_seven);
            case WeatherCircleView.ARC_DIN:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_eight);
            case ConnectionResult.CANCELED:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_nine);
            case ConnectionResult.TIMEOUT:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_one);
            case ConnectionResult.INTERRUPTED:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_two);
            case ConnectionResult.API_UNAVAILABLE:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_three);
            case ConnectionResult.SIGN_IN_FAILED:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_four);
            case ConnectionResult.SERVICE_UPDATING:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_five);
            case ConnectionResult.SERVICE_MISSING_PERMISSION:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_six);
            case ConnectionResult.RESTRICTED_PROFILE:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_seven);
            case R.styleable.Toolbar_titleMargin:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_eight);
            case R.styleable.Toolbar_titleMarginBottom:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_nine);
            default:
                return this.mWindPower;
        }
    }
}
