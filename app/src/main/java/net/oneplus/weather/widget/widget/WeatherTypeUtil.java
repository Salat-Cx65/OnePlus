package net.oneplus.weather.widget.widget;

import com.google.android.gms.location.GeofenceStatusCodes;
import net.oneplus.weather.R;
import net.oneplus.weather.model.WeatherDescription;

public class WeatherTypeUtil {
    public static int getWidgetWeatherTypeResID(int type, boolean isDay) {
        switch (type) {
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return isDay ? R.drawable.widget_bkg_sunny : R.drawable.widget_bkg_sunny_night;
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
            case WeatherDescription.WEATHER_DESCRIPTION_CLOUDY:
                return isDay ? R.drawable.widget_bkg_cloudy : R.drawable.widget_bkg_cloudy_night;
            case WeatherDescription.WEATHER_DESCRIPTION_OVERCAST:
                return isDay ? R.drawable.widget_bkg_overcast : R.drawable.widget_bkg_overcast_night;
            case WeatherDescription.WEATHER_DESCRIPTION_DRIZZLE:
                return isDay ? R.drawable.widget_bkg_drizzle : R.drawable.widget_bkg_drizzle_night;
            case WeatherDescription.WEATHER_DESCRIPTION_RAIN:
                return isDay ? R.drawable.widget_bkg_rain : R.drawable.widget_bkg_rain_night;
            case WeatherDescription.WEATHER_DESCRIPTION_SHOWER:
            case WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER:
                return isDay ? R.drawable.widget_bkg_shower : R.drawable.widget_bkg_shower_night;
            case WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR:
                return isDay ? R.drawable.widget_bkg_downpour : R.drawable.widget_bkg_downpour_night;
            case WeatherDescription.WEATHER_DESCRIPTION_RAINSTORM:
                return isDay ? R.drawable.widget_bkg_rainstorm : R.drawable.widget_bkg_rainstorm_night;
            case WidgetUpdateJob.UPDATE_JOBID:
                return isDay ? R.drawable.widget_bkg_sleet : R.drawable.widget_bkg_sleet_night;
            case WeatherDescription.WEATHER_DESCRIPTION_FLURRY:
            case WeatherDescription.WEATHER_DESCRIPTION_SNOW:
            case WeatherDescription.WEATHER_DESCRIPTION_SNOWSTORM:
            case WeatherDescription.WEATHER_DESCRIPTION_HAIL:
                return isDay ? R.drawable.widget_bkg_snow : R.drawable.widget_bkg_snow_night;
            case WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM:
                return isDay ? R.drawable.widget_bkg_sandstorm : R.drawable.widget_bkg_sandstorm_night;
            case WeatherDescription.WEATHER_DESCRIPTION_FOG:
                return isDay ? R.drawable.widget_bkg_fog : R.drawable.widget_bkg_fog_night;
            case WeatherDescription.WEATHER_DESCRIPTION_HURRICANE:
            case WeatherDescription.WEATHER_DESCRIPTION_HAZE:
                return isDay ? R.drawable.widget_bkg_haze : R.drawable.widget_bkg_haze_night;
            default:
                return -1;
        }
    }
}
