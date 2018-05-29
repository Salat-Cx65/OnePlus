package net.oneplus.weather.api.impl;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import com.oneplus.lib.widget.recyclerview.ItemTouchHelper;
import net.oneplus.weather.api.CommonConfig;
import net.oneplus.weather.api.WeatherRequest;
import net.oneplus.weather.api.WeatherResponse.CacheListener;
import net.oneplus.weather.api.WeatherResponse.NetworkListener;
import net.oneplus.weather.api.parser.ResponseParser;
import net.oneplus.weather.api.parser.SwaResponseParser;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class SwaRequest extends WeatherRequest {
    public static final String DATA_SOURCE_NAME = "HuaFeng";

    public SwaRequest(String key) {
        super(key);
    }

    public SwaRequest(int type, String key) {
        super(type, key);
    }

    public SwaRequest(String key, NetworkListener networkListener, CacheListener cacheListener) {
        super(key, networkListener, cacheListener);
    }

    public SwaRequest(int type, String key, NetworkListener networkListener, CacheListener cacheListener) {
        super(type, key, networkListener, cacheListener);
    }

    public String getRequestUrl(int type) {
        switch (type) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return CommonConfig.getSwaCurrentUrl(getRequestKey());
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return CommonConfig.getSwaHourForecastsUrl(getRequestKey());
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return CommonConfig.getSwaDailyForecastsUrl(getRequestKey());
            case DetectedActivity.RUNNING:
                return CommonConfig.getSwaAqiUrl(getRequestKey());
            case ConnectionResult.API_UNAVAILABLE:
                return CommonConfig.getSwaIndexUrl(getRequestKey());
            case ItemTouchHelper.END:
                return CommonConfig.getSwaAlertsUrl(getRequestKey());
            default:
                return null;
        }
    }

    public ResponseParser getResponseParser() {
        return new SwaResponseParser(getRequestKey());
    }

    public String getMemCacheKey() {
        return "HuaFeng#" + getRequestKey();
    }

    public String getDiskCacheKey(int type) {
        return "HuaFeng#" + getRequestKey() + "." + type;
    }
}
