package net.oneplus.weather.widget.widget;

import com.google.android.gms.location.GeofenceStatusCodes;
import net.oneplus.weather.R;
import net.oneplus.weather.model.WeatherDescription;

public class WidgetTypeUtil {
    public static final String ACTION_APPWIDGET_REFRESH = "net.oneplus.weather.receiver.update";
    public static final String ACTION_APPWIDGET_REFRESH_PERMISSION = "net.oneplus.weather.permission.UPDATE";
    public static final String WIDGET_PKG_NAME = "net.oneplus.widget";

    public static int getWidgetWeatherTypeResID(int type, boolean isDay) {
        switch (type) {
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return isDay ? R.drawable.wight_sunny : R.drawable.wight_sunny_night;
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
            case WeatherDescription.WEATHER_DESCRIPTION_CLOUDY:
                return isDay ? R.drawable.wight_cloudy : R.drawable.wight_cloudy_night;
            case WeatherDescription.WEATHER_DESCRIPTION_OVERCAST:
                return isDay ? R.drawable.wight_overcast : R.drawable.wight_overcast_night;
            case WeatherDescription.WEATHER_DESCRIPTION_DRIZZLE:
                return isDay ? R.drawable.wight_drizzle : R.drawable.wight_drizzle_night;
            case WeatherDescription.WEATHER_DESCRIPTION_RAIN:
                return isDay ? R.drawable.wight_rain : R.drawable.wight_rain_night;
            case WeatherDescription.WEATHER_DESCRIPTION_SHOWER:
            case WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER:
                return isDay ? R.drawable.wight_shower : R.drawable.wight_shower_night;
            case WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR:
                return isDay ? R.drawable.wight_downpour : R.drawable.wight_downpour_night;
            case WeatherDescription.WEATHER_DESCRIPTION_RAINSTORM:
                return isDay ? R.drawable.wight_rainstorm : R.drawable.wight_rainstorm_night;
            case WidgetUpdateJob.UPDATE_JOBID:
                return isDay ? R.drawable.wight_sleet : R.drawable.wight_sleet_night;
            case WeatherDescription.WEATHER_DESCRIPTION_FLURRY:
            case WeatherDescription.WEATHER_DESCRIPTION_SNOW:
            case WeatherDescription.WEATHER_DESCRIPTION_SNOWSTORM:
            case WeatherDescription.WEATHER_DESCRIPTION_HAIL:
                return isDay ? R.drawable.wight_snow : R.drawable.wight_snow_night;
            case WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM:
                return isDay ? R.drawable.wight_sandstorm : R.drawable.wight_sandstorm_night;
            case WeatherDescription.WEATHER_DESCRIPTION_FOG:
                return isDay ? R.drawable.wight_fog : R.drawable.wight_fog_night;
            case WeatherDescription.WEATHER_DESCRIPTION_HURRICANE:
            case WeatherDescription.WEATHER_DESCRIPTION_HAZE:
                return isDay ? R.drawable.wight_haze : R.drawable.wight_haze_night;
            default:
                return -1;
        }
    }
}
