package net.oneplus.weather.provider;

public class WeatherProvider {
    public static final int DOMMY_BEGIN_FLAG = 1;
    public static final int DOMMY_END_FLAG = 1;
    public static final int GET_WEATHER_FAIL = Integer.MIN_VALUE;
    public static final int GET_WEATHER_SUCC = 1073741824;
    public static final int PROVIDER_ACCU_WEATHER = 2048;
    public static final int PROVIDER_OP_WEATHER_FOREIGN = 8192;
    public static final int PROVIDER_WEATHER_CHINA = 4096;
    private static final String TAG;
    public static final String WEATHER_FILE_CURRENT = "_current";
    public static final String WEATHER_FILE_FORECAST = "_forecast";
    public static final String WEATHER_FILE_LOCAL_WEATHER = "_local_weather";
    public static final int WEATHER_TYPE_CURRENT = 2097152;
    public static final int WEATHER_TYPE_FORECASTS = 4194304;

    static {
        TAG = WeatherProvider.class.getSimpleName();
    }
}
