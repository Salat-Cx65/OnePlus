package net.oneplus.weather.api;

import android.content.Context;
import net.oneplus.weather.api.cache.WeatherCache;
import net.oneplus.weather.api.helper.NetworkHelper;
import net.oneplus.weather.api.impl.WeatherRequestExecuter;

public class WeatherClient {
    private static final Object classLock;
    private static WeatherClient sInstance;
    private Context mContext;

    static {
        classLock = WeatherClient.class;
    }

    public static WeatherClient getInstance(Context context) {
        if (context == null) {
            throw new NullPointerException("Context should not be null!");
        }
        if (sInstance == null) {
            synchronized (classLock) {
                if (sInstance == null) {
                    sInstance = new WeatherClient(context);
                }
            }
        }
        return sInstance;
    }

    private WeatherClient(Context context) {
        this.mContext = context.getApplicationContext();
        WeatherCache.getInstance(this.mContext);
    }

    public void execute(WeatherRequest request) {
        setDefaultLocale(request);
        new WeatherRequestExecuter(this.mContext).execute(request);
    }

    private void setDefaultLocale(WeatherRequest request) {
        if (request.getLocale() == null) {
            request.setLocale(this.mContext.getResources().getConfiguration().locale);
        }
    }

    public void cancelAll() {
        NetworkHelper.getInstance(this.mContext).cancelAll();
    }
}
