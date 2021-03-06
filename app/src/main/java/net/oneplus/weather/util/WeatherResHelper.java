package net.oneplus.weather.util;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.oneplus.weather.api.WeatherType;
import net.oneplus.weather.api.nodes.Wind.Direction;
import net.oneplus.weather.db.CityWeatherDBHelper.WeatherEntry;
import net.oneplus.weather.model.WeatherDescription;
import net.oneplus.weather.widget.widget.WidgetUpdateJob;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherResHelper {
    public static final Map<String, Integer> WeatherTypes;

    static {
        WeatherTypes = new ConcurrentHashMap();
    }

    public static int getWeatherIconResID(int description) {
        switch (description) {
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return R.drawable.ic_sunny_intervals;
            case WeatherDescription.WEATHER_DESCRIPTION_CLOUDY:
                return R.drawable.ic_cloudy;
            case WeatherDescription.WEATHER_DESCRIPTION_OVERCAST:
                return R.drawable.ic_overcast;
            case WeatherDescription.WEATHER_DESCRIPTION_DRIZZLE:
                return R.drawable.ic_drizzle;
            case WeatherDescription.WEATHER_DESCRIPTION_RAIN:
                return R.drawable.ic_rain;
            case WeatherDescription.WEATHER_DESCRIPTION_SHOWER:
                return R.drawable.ic_shower;
            case WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR:
                return R.drawable.ic_downpour;
            case WeatherDescription.WEATHER_DESCRIPTION_RAINSTORM:
                return R.drawable.ic_rainstorm;
            case WidgetUpdateJob.UPDATE_JOBID:
                return R.drawable.ic_sleet;
            case WeatherDescription.WEATHER_DESCRIPTION_FLURRY:
                return R.drawable.ic_flurry;
            case WeatherDescription.WEATHER_DESCRIPTION_SNOW:
                return R.drawable.ic_snow;
            case WeatherDescription.WEATHER_DESCRIPTION_SNOWSTORM:
                return R.drawable.ic_snowstorm;
            case WeatherDescription.WEATHER_DESCRIPTION_HAIL:
                return R.drawable.ic_hail;
            case WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER:
                return R.drawable.ic_thundershower;
            case WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM:
                return R.drawable.ic_sandstorm;
            case WeatherDescription.WEATHER_DESCRIPTION_FOG:
                return R.drawable.ic_fog;
            case WeatherDescription.WEATHER_DESCRIPTION_HURRICANE:
                return R.drawable.ic_hurricane;
            case WeatherDescription.WEATHER_DESCRIPTION_HAZE:
                return R.drawable.ic_haze;
            default:
                return R.drawable.ic_sunny;
        }
    }

    public static int getWindIconId(Context context, Direction winDir) {
        if (winDir == null) {
            return R.drawable.north_wind_icon;
        }
        if (Direction.NA == winDir) {
            return R.drawable.no_sustained_wind_icon;
        }
        if (Direction.NE == winDir) {
            return R.drawable.north_east_wind_icon;
        }
        if (Direction.E == winDir) {
            return R.drawable.east_wind_icon;
        }
        if (Direction.SE == winDir) {
            return R.drawable.south_east_wind_icon;
        }
        if (Direction.S == winDir) {
            return R.drawable.south_wind_icon;
        }
        if (Direction.SW == winDir) {
            return R.drawable.south_west_wind_icon;
        }
        if (Direction.W == winDir) {
            return R.drawable.west_wind_icon;
        }
        if (Direction.NW == winDir) {
            return R.drawable.north_west_wind_icon;
        }
        return Direction.N == winDir ? R.drawable.north_wind_icon : R.drawable.north_wind_icon;
    }

    public static synchronized Map<String, Integer> getWeatherTypes(Context context) {
        Map<String, Integer> map;
        synchronized (WeatherResHelper.class) {
            if (WeatherTypes.size() == 0) {
                try {
                    JSONArray jsonArray = new JSONObject(TextResourceReader.readTextFileFromResource(context, R.raw.weather_type)).getJSONArray("weathers");
                    if (jsonArray != null) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject j = jsonArray.getJSONObject(i);
                            String weather = j.getString(WeatherEntry.TABLE_NAME);
                            int value = j.getInt("value");
                            if (!TextUtils.isEmpty(weather)) {
                                WeatherTypes.put(weather, Integer.valueOf(value));
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            map = WeatherTypes;
        }
        return map;
    }

    public static int weatherToResID(Context context, int weather) {
        switch (weather) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
            case WeatherDescription.WEATHER_DESCRIPTION_FLURRY:
            case WeatherDescription.WEATHER_DESCRIPTION_HAIL:
            case WeatherDescription.WEATHER_DESCRIPTION_HURRICANE:
            case WeatherType.ACCU_WEATHER_HOT:
            case WeatherType.ACCU_WEATHER_COLD:
            case WeatherType.ACCU_WEATHER_WINDY:
            case WeatherType.ACCU_WEATHER_CLEAR:
            case WeatherType.ACCU_WEATHER_MOSTLY_CLEAR:
            case WeatherType.OPPO_CHINA_WEATHER_SUNNY:
            case WeatherType.OPPO_FOREIGN_WEATHER_SUNNY:
            case WeatherType.SWA_WEATHER_SUNNY:
                return GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES;
            case WeatherDescription.WEATHER_DESCRIPTION_CLOUDY:
            case WeatherDescription.WEATHER_DESCRIPTION_DRIZZLE:
            case WeatherDescription.WEATHER_DESCRIPTION_RAIN:
            case WidgetUpdateJob.UPDATE_JOBID:
            case WeatherDescription.WEATHER_DESCRIPTION_SNOWSTORM:
            case WeatherType.ACCU_WEATHER_MOSTLY_CLOUDY_W_SNOW:
            case WeatherType.ACCU_WEATHER_PARTLY_CLOUDY:
            case WeatherType.ACCU_WEATHER_INTERMITTENT_CLOUDS_NIGHT:
            case WeatherType.ACCU_WEATHER_MOSTLY_CLOUDY_NIGHT:
            case WeatherType.ACCU_WEATHER_PARTLY_CLOUDY_W_SHOWERS:
            case WeatherType.ACCU_WEATHER_MOSTLY_CLOUDY_W_SHOWERS_NIGHT:
            case WeatherType.ACCU_WEATHER_PARTLY_CLOUDY_W_T_STORMS:
            case WeatherType.ACCU_WEATHER_MOSTLY_CLOUDY_W_T_STORMS_NIGHT:
            case WeatherType.ACCU_WEATHER_MOSTLY_CLOUDY_W_FLURRIES_NIGHT:
            case WeatherType.ACCU_WEATHER_MOSTLY_CLOUDY_W_SNOW_NIGHT:
            case WeatherType.OPPO_CHINA_WEATHER_CLOUDY:
            case WeatherType.OPPO_FOREIGN_WEATHER_CLOUDY:
            case WeatherType.SWA_WEATHER_CLOUDY:
                return WeatherDescription.WEATHER_DESCRIPTION_CLOUDY;
            case WeatherDescription.WEATHER_DESCRIPTION_OVERCAST:
            case WeatherType.ACCU_WEATHER_HAZY_MOONLIGHT:
            case WeatherType.OPPO_CHINA_WEATHER_HAZE:
            case WeatherType.OPPO_FOREIGN_WEATHER_HAZE:
            case WeatherType.SWA_WEATHER_HAZE:
                return WeatherDescription.WEATHER_DESCRIPTION_HAZE;
            case WeatherDescription.WEATHER_DESCRIPTION_SHOWER:
            case WeatherType.OPPO_CHINA_WEATHER_OVERCAST:
            case WeatherType.OPPO_FOREIGN_WEATHER_OVERCAST:
            case WeatherType.SWA_WEATHER_OVERCAST:
                return WeatherDescription.WEATHER_DESCRIPTION_OVERCAST;
            case WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR:
            case WeatherType.OPPO_CHINA_WEATHER_FOGGY:
            case WeatherType.OPPO_FOREIGN_WEATHER_FOGGY:
            case WeatherType.SWA_WEATHER_FOGGY:
                return WeatherDescription.WEATHER_DESCRIPTION_FOG;
            case WeatherDescription.WEATHER_DESCRIPTION_RAINSTORM:
            case WeatherType.OPPO_CHINA_WEATHER_SHOWER:
            case WeatherType.OPPO_FOREIGN_WEATHER_SHOWER:
            case WeatherType.SWA_WEATHER_SHOWER:
                return WeatherDescription.WEATHER_DESCRIPTION_SHOWER;
            case WeatherDescription.WEATHER_DESCRIPTION_SNOW:
            case WeatherType.OPPO_CHINA_WEATHER_THUNDERSHOWER:
            case WeatherType.OPPO_CHINA_WEATHER_THUNDERSHOWER_WITH_HAIL:
            case WeatherType.OPPO_FOREIGN_WEATHER_THUNDERSHOWER:
            case WeatherType.OPPO_FOREIGN_WEATHER_THUNDERSHOWER_WITH_HAIL:
            case WeatherType.SWA_WEATHER_THUNDERSHOWER:
            case WeatherType.SWA_WEATHER_THUNDERSHOWER_WITH_HAIL:
                return WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER;
            case WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER:
            case WeatherType.ACCU_WEATHER_ICE:
            case WeatherType.ACCU_WEATHER_SLEET:
            case WeatherType.ACCU_WEATHER_FREEZING_RAIN:
            case WeatherType.ACCU_WEATHER_RAIN_AND_SNOW:
            case WeatherType.OPPO_CHINA_WEATHER_HEAVY_RAIN:
            case WeatherType.OPPO_CHINA_WEATHER_HEAVY_RAIN_TO_STORM:
            case WeatherType.OPPO_FOREIGN_WEATHER_HEAVY_RAIN:
            case WeatherType.OPPO_FOREIGN_WEATHER_HEAVY_RAIN_TO_STORM:
            case WeatherType.SWA_WEATHER_HEAVY_RAIN:
            case WeatherType.SWA_WEATHER_HEAVY_RAIN_TO_STORM:
                return WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR;
            case WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM:
            case WeatherDescription.WEATHER_DESCRIPTION_FOG:
                return 1011;
            case WeatherDescription.WEATHER_DESCRIPTION_HAZE:
            case WeatherType.OPPO_CHINA_WEATHER_MODERATE_SNOW:
            case WeatherType.OPPO_CHINA_WEATHER_MODERATE_TO_HEAVY_SNOW:
            case WeatherType.OPPO_FOREIGN_WEATHER_MODERATE_SNOW:
            case WeatherType.OPPO_FOREIGN_WEATHER_MODERATE_TO_HEAVY_SNOW:
            case WeatherType.SWA_WEATHER_MODERATE_SNOW:
            case WeatherType.SWA_WEATHER_MODERATE_TO_HEAVY_SNOW:
                return WeatherDescription.WEATHER_DESCRIPTION_SNOW;
            case WeatherType.OPPO_CHINA_WEATHER_SLEET:
            case WeatherType.OPPO_CHINA_WEATHER_MODERATE_RAIN:
            case WeatherType.OPPO_CHINA_WEATHER_ICE_RAIN:
            case WeatherType.OPPO_CHINA_WEATHER_MODERATE_TO_HEAVY_RAIN:
            case WeatherType.OPPO_FOREIGN_WEATHER_SLEET:
            case WeatherType.OPPO_FOREIGN_WEATHER_MODERATE_RAIN:
            case WeatherType.OPPO_FOREIGN_WEATHER_ICE_RAIN:
            case WeatherType.OPPO_FOREIGN_WEATHER_MODERATE_TO_HEAVY_RAIN:
            case WeatherType.SWA_WEATHER_SLEET:
            case WeatherType.SWA_WEATHER_MODERATE_RAIN:
            case WeatherType.SWA_WEATHER_ICE_RAIN:
            case WeatherType.SWA_WEATHER_MODERATE_TO_HEAVY_RAIN:
                return WeatherDescription.WEATHER_DESCRIPTION_RAIN;
            case WeatherType.OPPO_CHINA_WEATHER_LIGHT_RAIN:
            case WeatherType.OPPO_CHINA_WEATHER_LIGHT_TO_MODERATE_RAIN:
            case WeatherType.OPPO_FOREIGN_WEATHER_LIGHT_RAIN:
            case WeatherType.OPPO_FOREIGN_WEATHER_LIGHT_TO_MODERATE_RAIN:
            case WeatherType.SWA_WEATHER_LIGHT_RAIN:
            case WeatherType.SWA_WEATHER_LIGHT_TO_MODERATE_RAIN:
                return WeatherDescription.WEATHER_DESCRIPTION_DRIZZLE;
            case WeatherType.OPPO_CHINA_WEATHER_STORM:
            case WeatherType.OPPO_CHINA_WEATHER_HEAVY_STORM:
            case WeatherType.OPPO_CHINA_WEATHER_SEVERE_STORM:
            case WeatherType.OPPO_CHINA_WEATHER_STORM_TO_HEAVY_STORM:
            case WeatherType.OPPO_CHINA_WEATHER_HEAVY_TO_SEVERE_STORM:
            case WeatherType.OPPO_FOREIGN_WEATHER_STORM:
            case WeatherType.OPPO_FOREIGN_WEATHER_HEAVY_STORM:
            case WeatherType.OPPO_FOREIGN_WEATHER_SEVERE_STORM:
            case WeatherType.OPPO_FOREIGN_WEATHER_STORM_TO_HEAVY_STORM:
            case WeatherType.OPPO_FOREIGN_WEATHER_HEAVY_TO_SEVERE_STORM:
            case WeatherType.SWA_WEATHER_STORM:
            case WeatherType.SWA_WEATHER_HEAVY_STORM:
            case WeatherType.SWA_WEATHER_SEVERE_STORM:
            case WeatherType.SWA_WEATHER_STORM_TO_HEAVY_STORM:
            case WeatherType.SWA_WEATHER_HEAVY_TO_SEVERE_STORM:
                return WeatherDescription.WEATHER_DESCRIPTION_RAINSTORM;
            case WeatherType.OPPO_CHINA_WEATHER_SNOW_FLURRY:
            case WeatherType.OPPO_CHINA_WEATHER_HEAVY_SNOW:
            case WeatherType.OPPO_CHINA_WEATHER_SNOWSTORM:
            case WeatherType.OPPO_CHINA_WEATHER_HEAVY_SNOW_TO_SNOWSTORM:
            case WeatherType.OPPO_FOREIGN_WEATHER_SNOW_FLURRY:
            case WeatherType.OPPO_FOREIGN_WEATHER_HEAVY_SNOW:
            case WeatherType.OPPO_FOREIGN_WEATHER_SNOWSTORM:
            case WeatherType.OPPO_FOREIGN_WEATHER_HEAVY_SNOW_TO_SNOWSTORM:
            case WeatherType.SWA_WEATHER_SNOW_FLURRY:
            case WeatherType.SWA_WEATHER_HEAVY_SNOW:
            case WeatherType.SWA_WEATHER_SNOWSTORM:
            case WeatherType.SWA_WEATHER_HEAVY_SNOW_TO_SNOWSTORM:
                return WeatherDescription.WEATHER_DESCRIPTION_SNOWSTORM;
            case WeatherType.OPPO_CHINA_WEATHER_LIGHT_SNOW:
            case WeatherType.OPPO_CHINA_WEATHER_LIGHT_TO_MODERATE_SNOW:
            case WeatherType.OPPO_FOREIGN_WEATHER_LIGHT_SNOW:
            case WeatherType.OPPO_FOREIGN_WEATHER_LIGHT_TO_MODERATE_SNOW:
            case WeatherType.SWA_WEATHER_LIGHT_SNOW:
            case WeatherType.SWA_WEATHER_LIGHT_TO_MODERATE_SNOW:
                return 1011;
            case WeatherType.OPPO_CHINA_WEATHER_DUSTSTORM:
            case WeatherType.OPPO_CHINA_WEATHER_DUST:
            case WeatherType.OPPO_CHINA_WEATHER_SAND:
            case WeatherType.OPPO_CHINA_WEATHER_SANDSTORM:
            case WeatherType.OPPO_FOREIGN_WEATHER_DUSTSTORM:
            case WeatherType.OPPO_FOREIGN_WEATHER_DUST:
            case WeatherType.OPPO_FOREIGN_WEATHER_SAND:
            case WeatherType.OPPO_FOREIGN_WEATHER_SANDSTORM:
            case WeatherType.SWA_WEATHER_DUSTSTORM:
            case WeatherType.SWA_WEATHER_DUST:
            case WeatherType.SWA_WEATHER_SAND:
            case WeatherType.SWA_WEATHER_SANDSTORM:
                return WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM;
            default:
                return WeatherDescription.WEATHER_DESCRIPTION_UNKNOWN;
        }
    }

    public static int getWeatherListitemBkgResID(int description, boolean isDay) {
        if (isDay) {
            switch (description) {
                case WeatherDescription.WEATHER_DESCRIPTION_CLOUDY:
                    return R.drawable.bkg_cloudy;
                case WeatherDescription.WEATHER_DESCRIPTION_OVERCAST:
                    return R.drawable.bkg_overcast;
                case WeatherDescription.WEATHER_DESCRIPTION_DRIZZLE:
                    return R.drawable.bkg_drizzle;
                case WeatherDescription.WEATHER_DESCRIPTION_RAIN:
                    return R.drawable.bkg_rain;
                case WeatherDescription.WEATHER_DESCRIPTION_SHOWER:
                    return R.drawable.bkg_shower;
                case WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR:
                    return R.drawable.bkg_downpour;
                case WeatherDescription.WEATHER_DESCRIPTION_RAINSTORM:
                    return R.drawable.bkg_rainstorm;
                case WidgetUpdateJob.UPDATE_JOBID:
                    return R.drawable.bkg_sleet;
                case WeatherDescription.WEATHER_DESCRIPTION_FLURRY:
                    return 2131230821;
                case WeatherDescription.WEATHER_DESCRIPTION_SNOW:
                    return R.drawable.bkg_snow;
                case WeatherDescription.WEATHER_DESCRIPTION_SNOWSTORM:
                    return 2131230841;
                case WeatherDescription.WEATHER_DESCRIPTION_HAIL:
                    return 2131230824;
                case WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER:
                    return R.drawable.bkg_thundershower;
                case WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM:
                    return R.drawable.bkg_sandstorm;
                case WeatherDescription.WEATHER_DESCRIPTION_FOG:
                    return R.drawable.bkg_fog;
                case WeatherDescription.WEATHER_DESCRIPTION_HAZE:
                    return R.drawable.bkg_haze;
                default:
                    return R.drawable.bkg_sunny;
            }
        }
        switch (description) {
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return 2131230843;
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return 2131230843;
            case WeatherDescription.WEATHER_DESCRIPTION_CLOUDY:
                return R.drawable.bkg_cloudy_night;
            case WeatherDescription.WEATHER_DESCRIPTION_OVERCAST:
                return R.drawable.bkg_overcast_night;
            case WeatherDescription.WEATHER_DESCRIPTION_DRIZZLE:
                return R.drawable.bkg_drizzle_night;
            case WeatherDescription.WEATHER_DESCRIPTION_RAIN:
                return R.drawable.bkg_rain_night;
            case WeatherDescription.WEATHER_DESCRIPTION_SHOWER:
                return R.drawable.bkg_shower_night;
            case WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR:
                return R.drawable.bkg_downpour_night;
            case WeatherDescription.WEATHER_DESCRIPTION_RAINSTORM:
                return R.drawable.bkg_rainstorm_night;
            case WidgetUpdateJob.UPDATE_JOBID:
                return R.drawable.bkg_sleet_night;
            case WeatherDescription.WEATHER_DESCRIPTION_FLURRY:
                return 2131230821;
            case WeatherDescription.WEATHER_DESCRIPTION_SNOW:
                return R.drawable.bkg_snow_night;
            case WeatherDescription.WEATHER_DESCRIPTION_SNOWSTORM:
                return 2131230841;
            case WeatherDescription.WEATHER_DESCRIPTION_HAIL:
                return 2131230824;
            case WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER:
                return R.drawable.bkg_thundershower_night;
            case WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM:
                return R.drawable.bkg_sandstorm_night;
            case WeatherDescription.WEATHER_DESCRIPTION_FOG:
                return R.drawable.bkg_fog_night;
            case WeatherDescription.WEATHER_DESCRIPTION_HAZE:
                return R.drawable.bkg_haze_night;
            default:
                return R.drawable.bkg_sunny;
        }
    }

    public static int getWeatherColorStringID(int description, boolean idDay) {
        if (idDay) {
            switch (description) {
                case WeatherDescription.WEATHER_DESCRIPTION_OVERCAST:
                    return R.color.weather_overcast;
                case WeatherDescription.WEATHER_DESCRIPTION_DRIZZLE:
                    return R.color.weather_drizzle_rain;
                case WeatherDescription.WEATHER_DESCRIPTION_RAIN:
                    return R.color.weather_rain;
                case WeatherDescription.WEATHER_DESCRIPTION_SHOWER:
                    return R.color.weather_shower_rain;
                case WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR:
                    return R.color.weather_downpour_rain;
                case WeatherDescription.WEATHER_DESCRIPTION_RAINSTORM:
                    return R.color.weather_storm_rain;
                case WidgetUpdateJob.UPDATE_JOBID:
                case WeatherDescription.WEATHER_DESCRIPTION_FLURRY:
                case WeatherDescription.WEATHER_DESCRIPTION_SNOW:
                case WeatherDescription.WEATHER_DESCRIPTION_SNOWSTORM:
                    return R.color.weather_snow;
                case WeatherDescription.WEATHER_DESCRIPTION_HAIL:
                    return R.color.weather_hail;
                case WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER:
                    return R.color.weather_thunder_shower_rain;
                case WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM:
                    return R.color.weather_dust;
                case WeatherDescription.WEATHER_DESCRIPTION_FOG:
                    return R.color.weather_fog;
                case WeatherDescription.WEATHER_DESCRIPTION_HURRICANE:
                    return R.color.weather_wind;
                case WeatherDescription.WEATHER_DESCRIPTION_HAZE:
                    return R.color.weather_haze;
                default:
                    return R.color.weather_sun;
            }
        }
        switch (description) {
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
            case WeatherDescription.WEATHER_DESCRIPTION_CLOUDY:
                return 2131100549;
            case WeatherDescription.WEATHER_DESCRIPTION_OVERCAST:
                return R.color.weather_overcast_night;
            case WeatherDescription.WEATHER_DESCRIPTION_DRIZZLE:
                return R.color.weather_drizzle_rain_night;
            case WeatherDescription.WEATHER_DESCRIPTION_RAIN:
                return R.color.weather_rain_night;
            case WeatherDescription.WEATHER_DESCRIPTION_SHOWER:
                return R.color.weather_shower_rain_night;
            case WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR:
                return 2131100518;
            case WeatherDescription.WEATHER_DESCRIPTION_RAINSTORM:
            case WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER:
                return 2131100518;
            case WidgetUpdateJob.UPDATE_JOBID:
            case WeatherDescription.WEATHER_DESCRIPTION_FLURRY:
            case WeatherDescription.WEATHER_DESCRIPTION_SNOW:
            case WeatherDescription.WEATHER_DESCRIPTION_SNOWSTORM:
                return R.color.weather_snow_night;
            case WeatherDescription.WEATHER_DESCRIPTION_HAIL:
                return R.color.weather_hail_night;
            case WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM:
                return R.color.weather_dust_night;
            case WeatherDescription.WEATHER_DESCRIPTION_FOG:
                return R.color.weather_fog_night;
            case WeatherDescription.WEATHER_DESCRIPTION_HURRICANE:
                return R.color.weather_wind_night;
            case WeatherDescription.WEATHER_DESCRIPTION_HAZE:
                return R.color.weather_haze_night;
            default:
                return 2131100549;
        }
    }

    public static int getWeatherNightArcColorID(int description) {
        switch (description) {
            case WeatherDescription.WEATHER_DESCRIPTION_CLOUDY:
                return R.color.weather_night_arc_cloudy;
            case WeatherDescription.WEATHER_DESCRIPTION_OVERCAST:
                return R.color.weather_night_arc_overcast;
            case WeatherDescription.WEATHER_DESCRIPTION_DRIZZLE:
            case WeatherDescription.WEATHER_DESCRIPTION_RAIN:
            case WeatherDescription.WEATHER_DESCRIPTION_SHOWER:
            case WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR:
            case WeatherDescription.WEATHER_DESCRIPTION_RAINSTORM:
            case WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER:
                return R.color.weather_night_arc_rain;
            case WidgetUpdateJob.UPDATE_JOBID:
            case WeatherDescription.WEATHER_DESCRIPTION_FLURRY:
            case WeatherDescription.WEATHER_DESCRIPTION_SNOW:
            case WeatherDescription.WEATHER_DESCRIPTION_SNOWSTORM:
                return R.color.weather_night_arc_snow;
            case WeatherDescription.WEATHER_DESCRIPTION_HAIL:
                return R.color.weather_night_arc_hail;
            case WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM:
                return R.color.weather_night_arc_dust;
            case WeatherDescription.WEATHER_DESCRIPTION_FOG:
            case WeatherDescription.WEATHER_DESCRIPTION_HAZE:
                return R.color.weather_night_arc_fog;
            case WeatherDescription.WEATHER_DESCRIPTION_HURRICANE:
                return R.color.weather_night_arc_wind;
            default:
                return R.color.weather_night_arc_sun;
        }
    }

    public static int getWeatherTopBarBg(int description, boolean isDay) {
        if (isDay) {
            switch (description) {
                case WeatherDescription.WEATHER_DESCRIPTION_OVERCAST:
                    return R.drawable.top_bar_overcast_bg;
                case WeatherDescription.WEATHER_DESCRIPTION_DRIZZLE:
                    return R.drawable.top_bar_weather_drizzle_rain;
                case WeatherDescription.WEATHER_DESCRIPTION_RAIN:
                    return R.drawable.top_bar_weather_rain;
                case WeatherDescription.WEATHER_DESCRIPTION_SHOWER:
                    return R.drawable.top_bar_weather_shower_rain;
                case WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR:
                    return R.drawable.top_bar_weather_downpour_rain;
                case WeatherDescription.WEATHER_DESCRIPTION_RAINSTORM:
                    return R.drawable.top_bar_weather_storm_rain;
                case WidgetUpdateJob.UPDATE_JOBID:
                case WeatherDescription.WEATHER_DESCRIPTION_FLURRY:
                case WeatherDescription.WEATHER_DESCRIPTION_SNOW:
                case WeatherDescription.WEATHER_DESCRIPTION_SNOWSTORM:
                    return R.drawable.top_bar_snow_bg;
                case WeatherDescription.WEATHER_DESCRIPTION_HAIL:
                    return 2131231167;
                case WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER:
                    return R.drawable.top_bar_weather_thunder_shower_rain;
                case WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM:
                    return R.drawable.top_bar_sandstorm_bg;
                case WeatherDescription.WEATHER_DESCRIPTION_FOG:
                    return R.drawable.top_bar_fog_bg;
                case WeatherDescription.WEATHER_DESCRIPTION_HAZE:
                    return R.drawable.top_bar_haze_bg;
                default:
                    return R.drawable.top_bar_sun_bg;
            }
        }
        switch (description) {
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return 2131231177;
            case WeatherDescription.WEATHER_DESCRIPTION_CLOUDY:
                return R.drawable.top_bar_cloudy_night_bg;
            case WeatherDescription.WEATHER_DESCRIPTION_OVERCAST:
                return R.drawable.top_bar_overcast_night_bg;
            case WeatherDescription.WEATHER_DESCRIPTION_DRIZZLE:
            case WeatherDescription.WEATHER_DESCRIPTION_RAIN:
            case WeatherDescription.WEATHER_DESCRIPTION_SHOWER:
            case WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR:
            case WeatherDescription.WEATHER_DESCRIPTION_RAINSTORM:
            case WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER:
                return R.drawable.top_bar_weather_rain_night;
            case WidgetUpdateJob.UPDATE_JOBID:
            case WeatherDescription.WEATHER_DESCRIPTION_FLURRY:
            case WeatherDescription.WEATHER_DESCRIPTION_SNOW:
            case WeatherDescription.WEATHER_DESCRIPTION_SNOWSTORM:
                return R.drawable.top_bar_snow_night_bg;
            case WeatherDescription.WEATHER_DESCRIPTION_HAIL:
                return 2131231167;
            case WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM:
                return R.drawable.top_bar_sandstorm_night_bg;
            case WeatherDescription.WEATHER_DESCRIPTION_FOG:
                return R.drawable.top_bar_fog_night_bg;
            case WeatherDescription.WEATHER_DESCRIPTION_HURRICANE:
                return 2131231177;
            case WeatherDescription.WEATHER_DESCRIPTION_HAZE:
                return R.drawable.top_bar_haze_night;
            default:
                return 2131231177;
        }
    }

    public static int getCloseIcon(Context context, String closeValue) {
        System.out.println("closeValue:" + closeValue);
        if (TextUtils.isEmpty(closeValue)) {
            return R.drawable.ic_clothes_level;
        }
        if (closeValue.equals(context.getString(R.string.clothes_colder))) {
            return R.drawable.ic_clothes_cold;
        }
        if (closeValue.equals(context.getString(R.string.clothes_more_colder))) {
            return R.drawable.ic_clothes_more_cold;
        }
        if (closeValue.equals(context.getString(R.string.clothes_iceness))) {
            return R.drawable.ic_clothes_iceness;
        }
        if (closeValue.equals(context.getString(R.string.clothes_more_comfortable))) {
            return R.drawable.ic_clothes_more_comfortable;
        }
        if (closeValue.equals(context.getString(R.string.clothes_comfortable))) {
            return R.drawable.ic_clothes_comfortable;
        }
        if (closeValue.equals(context.getString(R.string.clothes_hot))) {
            return R.drawable.ic_clothes_hot;
        }
        return closeValue.equals(context.getString(R.string.clothes_very_hot)) ? R.drawable.ic_clothes_very_hot : R.drawable.ic_clothes_level;
    }
}
