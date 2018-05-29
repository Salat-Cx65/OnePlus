package net.oneplus.weather.api;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import com.oneplus.lib.widget.recyclerview.ItemTouchHelper;
import java.util.Locale;
import net.oneplus.weather.api.WeatherResponse.CacheListener;
import net.oneplus.weather.api.WeatherResponse.NetworkListener;
import net.oneplus.weather.api.helper.Validate;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.api.parser.ResponseParser;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class WeatherRequest {
    private CacheListener mCacheListener;
    private CacheMode mCacheMode;
    private final String mKey;
    private Locale mLocale;
    private NetworkListener mNetworkListener;
    private final int mType;
    private boolean mUseHttpCache;

    public enum CacheMode {
        LOAD_DEFAULT,
        LOAD_CACHE_ELSE_NETWORK,
        LOAD_NO_CACHE,
        LOAD_CACHE_ONLY
    }

    public static interface Type {
        public static final int ALARM = 32;
        public static final int ALL = 15;
        public static final int AQI = 8;
        public static final int CURRENT = 1;
        public static final int DAILY_FORECASTS = 4;
        public static final int HOUR_FORECASTS = 2;
        public static final int LIFE_INDEX = 16;
        public static final int SUCCESS = 64;
    }

    public abstract String getDiskCacheKey(int i);

    public abstract String getMemCacheKey();

    public abstract String getRequestUrl(int i);

    public abstract ResponseParser getResponseParser();

    public static boolean contain(int request, int type) {
        return (request & type) == type;
    }

    private static boolean validRequestType(int type) {
        return contain(type, 1) || contain(type, RainSurfaceView.RAIN_LEVEL_SHOWER) || contain(type, RainSurfaceView.RAIN_LEVEL_RAINSTORM) || contain(type, DetectedActivity.RUNNING) || contain(type, ConnectionResult.API_UNAVAILABLE) || contain(type, ItemTouchHelper.END);
    }

    public WeatherRequest(String key) {
        this(15, key, null, null);
    }

    public WeatherRequest(int type, String key) {
        this(type, key, null, null);
    }

    public WeatherRequest(String key, NetworkListener networkListener, CacheListener cacheListener) {
        this(15, key, networkListener, cacheListener);
    }

    public WeatherRequest(int type, String key, NetworkListener networkListener, CacheListener cacheListener) {
        this.mCacheMode = CacheMode.LOAD_DEFAULT;
        this.mUseHttpCache = true;
        Validate.notEmpty(key, "Key should not be empty!");
        if (validRequestType(type)) {
            this.mKey = key;
            this.mType = type;
            this.mNetworkListener = networkListener;
            this.mCacheListener = cacheListener;
            return;
        }
        throw new IllegalArgumentException("Type should contain at least one of type AQI, LIFE_INDEX, CURRENT, HOUR_FORECASTS, ALARM or DAILY_FORECASTS.");
    }

    public WeatherRequest setLocale(Locale locale) {
        this.mLocale = locale;
        return this;
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    public Locale getLocale(Locale defaultLocale) {
        return this.mLocale == null ? defaultLocale : this.mLocale;
    }

    public String getRequestKey() {
        return this.mKey;
    }

    public int getRequestType() {
        return this.mType;
    }

    public void deliverNetworkResponse(RootWeather weather) {
        if (this.mNetworkListener != null) {
            this.mNetworkListener.onResponseSuccess(weather);
        }
    }

    public void deliverNetworkError(WeatherException e) {
        if (this.mNetworkListener != null) {
            this.mNetworkListener.onResponseError(e);
        }
    }

    public void deliverCacheResponse(RootWeather weather) {
        if (this.mCacheListener != null) {
            this.mCacheListener.onResponse(weather);
        }
    }

    public void setNetworkListener(NetworkListener listener) {
        this.mNetworkListener = listener;
    }

    public void setCacheListener(CacheListener listener) {
        this.mCacheListener = listener;
    }

    public NetworkListener getNetworkListener() {
        return this.mNetworkListener;
    }

    public CacheListener getCacheListener() {
        return this.mCacheListener;
    }

    public final WeatherRequest setCacheMode(CacheMode mode) {
        if (mode == null) {
            throw new NullPointerException("mode should not be null.");
        }
        this.mCacheMode = mode;
        return this;
    }

    public final CacheMode getCacheMode() {
        return this.mCacheMode;
    }

    public final boolean containRequest(int type) {
        return contain(this.mType, type);
    }

    public WeatherRequest setHttpCacheEnable(boolean enable) {
        this.mUseHttpCache = enable;
        return this;
    }

    public boolean getHttpCacheEnable() {
        return this.mUseHttpCache;
    }
}
