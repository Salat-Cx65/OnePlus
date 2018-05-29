package net.oneplus.weather.api.impl;

import android.content.Context;
import android.os.AsyncTask;
import net.oneplus.weather.api.RequestExecuter;
import net.oneplus.weather.api.WeatherRequest;
import net.oneplus.weather.api.cache.Cache;
import net.oneplus.weather.api.cache.WeatherCache;

public abstract class AbstractExecuter implements RequestExecuter {

    private class CacheAsyncTask extends AsyncTask<byte[], Void, Void> {
        private final Context mContext;
        private final String mUrl;

        public CacheAsyncTask(Context context, String url) {
            this.mContext = context;
            this.mUrl = url;
        }

        protected Void doInBackground(byte[]... params) {
            byte[] data = params[0];
            Cache cache = WeatherCache.getInstance(this.mContext);
            cache.putToDisk(this.mUrl, data);
            cache.flush();
            return null;
        }
    }

    public abstract void execute(WeatherRequest weatherRequest);

    protected void addToDiskCache(Context context, String key, byte[] value) {
        new CacheAsyncTask(context, key).execute(new Object[]{value});
    }

    protected void removeWeatherTimeDiskCache(Context context, String key, byte[] value) {
        new CacheAsyncTask(context, key).execute(new Object[]{value});
    }
}
