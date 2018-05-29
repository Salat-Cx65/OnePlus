package net.oneplus.weather.api.nodes;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.R;
import net.oneplus.weather.api.impl.SwaRequest;
import net.oneplus.weather.api.nodes.Wind.Direction;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import net.oneplus.weather.widget.shap.Stars;

public class SwaWind extends Wind {
    private final String mWindPower;

    public SwaWind(String areaCode, String direction, String power) {
        super(areaCode, SwaRequest.DATA_SOURCE_NAME, toSwaDirection(direction));
        this.mWindPower = power;
    }

    public String getWindPower(Context context) {
        String str = this.mWindPower;
        Object obj = -1;
        switch (str.hashCode()) {
            case R.styleable.AppCompatTheme_colorAccent:
                if (str.equals("0")) {
                    obj = null;
                }
                break;
            case R.styleable.AppCompatTheme_colorBackgroundFloating:
                if (str.equals("1")) {
                    obj = 1;
                }
                break;
            case Stars.CIRCLE_COUNT:
                if (str.equals("2")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case R.styleable.AppCompatTheme_colorControlActivated:
                if (str.equals("3")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
            case R.styleable.AppCompatTheme_colorControlHighlight:
                if (str.equals("4")) {
                    obj = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                }
                break;
            case R.styleable.AppCompatTheme_colorControlNormal:
                if (str.equals("5")) {
                    obj = RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
                }
                break;
            case R.styleable.AppCompatTheme_colorError:
                if (str.equals("6")) {
                    obj = ConnectionResult.RESOLUTION_REQUIRED;
                }
                break;
            case R.styleable.AppCompatTheme_colorPrimary:
                if (str.equals("7")) {
                    obj = DetectedActivity.WALKING;
                }
                break;
            case R.styleable.AppCompatTheme_colorPrimaryDark:
                if (str.equals("8")) {
                    obj = DetectedActivity.RUNNING;
                }
                break;
            case R.styleable.AppCompatTheme_colorSwitchThumbNormal:
                if (str.equals("9")) {
                    obj = ConnectionResult.SERVICE_INVALID;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_zero);
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_one);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_two);
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_three);
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_four);
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_five);
            case ConnectionResult.RESOLUTION_REQUIRED:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_six);
            case DetectedActivity.WALKING:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_seven);
            case DetectedActivity.RUNNING:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_eight);
            case ConnectionResult.SERVICE_INVALID:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_nine);
            default:
                return context.getString(net.oneplus.weather.api.R.string.api_wind_power_level_zero);
        }
    }

    private static Direction toSwaDirection(String dirction) {
        return Wind.getDirectionFromSwa(dirction);
    }
}
