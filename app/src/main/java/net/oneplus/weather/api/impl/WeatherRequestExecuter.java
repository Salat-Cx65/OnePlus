package net.oneplus.weather.api.impl;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import com.oneplus.lib.widget.recyclerview.ItemTouchHelper;
import net.oneplus.weather.api.WeatherException;
import net.oneplus.weather.api.WeatherRequest;
import net.oneplus.weather.api.WeatherRequest.CacheMode;
import net.oneplus.weather.api.WeatherResponse;
import net.oneplus.weather.api.cache.WeatherCache;
import net.oneplus.weather.api.helper.LogUtils;
import net.oneplus.weather.api.helper.NetworkHelper;
import net.oneplus.weather.api.helper.NetworkHelper.ResponseListener;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.api.parser.ParseException;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class WeatherRequestExecuter extends AbstractExecuter {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final String TAG = "WeatherRequestExecuter";
    private final Context mContext;

    private class CacheBox {
        boolean error;
        private int mRequestedType;
        RootWeather mResult;

        private CacheBox() {
            this.error = false;
            this.mRequestedType = 0;
        }

        void addResponse(RootWeather weather, int type) {
            this.mRequestedType |= type;
            if (weather != null) {
                if (this.mResult == null) {
                    this.mResult = weather;
                } else if (weather != null) {
                    switch (type) {
                        case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                            this.mResult.setCurrentWeather(weather.getCurrentWeather());
                        case RainSurfaceView.RAIN_LEVEL_SHOWER:
                            this.mResult.setHourForecastsWeather(weather.getHourForecastsWeather());
                        case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                            this.mResult.setDailyForecastsWeather(weather.getDailyForecastsWeather());
                            this.mResult.setFutureLink(weather.getFutureLink());
                        case DetectedActivity.RUNNING:
                            this.mResult.setAqiWeather(weather.getAqiWeather());
                        case ConnectionResult.API_UNAVAILABLE:
                            this.mResult.setLifeIndexWeather(weather.getLifeIndexWeather());
                        case ItemTouchHelper.END:
                            this.mResult.setWeatherAlarms(weather.getWeatherAlarms());
                        default:
                            break;
                    }
                }
            }
        }

        boolean isRequested(int type) {
            boolean hasData = $assertionsDisabled;
            switch (type) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    if (this.mResult == null || this.mResult.getCurrentWeather() == null) {
                        hasData = false;
                    } else {
                        hasData = true;
                    }
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    if (this.mResult == null || this.mResult.getHourForecastsWeather() == null) {
                        hasData = false;
                    } else {
                        hasData = true;
                    }
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    if (this.mResult == null || this.mResult.getDailyForecastsWeather() == null) {
                        hasData = false;
                    } else {
                        hasData = true;
                    }
                    break;
                case DetectedActivity.RUNNING:
                    if (this.mResult == null || this.mResult.getAqiWeather() == null) {
                        hasData = false;
                    } else {
                        hasData = true;
                    }
                    break;
                case ConnectionResult.API_UNAVAILABLE:
                    if (this.mResult == null || this.mResult.getLifeIndexWeather() == null) {
                        hasData = false;
                    } else {
                        hasData = true;
                    }
                    break;
                case ItemTouchHelper.END:
                    hasData = (this.mResult == null || this.mResult.getWeatherAlarms() == null) ? false : true;
                    break;
            }
            return ((this.mRequestedType & type) == type || hasData) ? true : $assertionsDisabled;
        }

        RootWeather getResult() {
            return this.mResult;
        }
    }

    private class CacheParserWorkerTask extends AsyncTask<String, Void, Void> {
        private final CacheBox mCacheBox;
        private final WeatherRequest mRequest;
        private final int mRequestType;

        public CacheParserWorkerTask(int type, WeatherRequest request, CacheBox cacheBox) {
            this.mRequestType = type;
            this.mRequest = request;
            this.mCacheBox = cacheBox;
        }

        protected Void doInBackground(String... key) {
            byte[] data = WeatherCache.getInstance(WeatherRequestExecuter.this.mContext).getFromDiskCache(key[0]);
            try {
                RootWeather rootWeather;
                if (this.mRequestType == 8) {
                    rootWeather = this.mRequest.getResponseParser().parseAqi(data);
                } else if (this.mRequestType == 1) {
                    rootWeather = this.mRequest.getResponseParser().parseCurrent(data);
                } else if (this.mRequestType == 16) {
                    rootWeather = this.mRequest.getResponseParser().parseLifeIndex(data);
                } else if (this.mRequestType == 2) {
                    rootWeather = this.mRequest.getResponseParser().parseHourForecasts(data);
                } else if (this.mRequestType == 4) {
                    rootWeather = this.mRequest.getResponseParser().parseDailyForecasts(data);
                } else if (this.mRequestType == 32) {
                    rootWeather = this.mRequest.getResponseParser().parseAlarm(data);
                } else {
                    throw new WeatherException("Unsupport request type!");
                }
                this.mCacheBox.addResponse(rootWeather, this.mRequestType);
            } catch (WeatherException e) {
                if (e instanceof ParseException) {
                    this.mCacheBox.addResponse(null, this.mRequestType);
                } else {
                    this.mCacheBox.error = true;
                }
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            WeatherRequestExecuter.this.requestOrDeliverCache(this.mRequest, this.mCacheBox);
        }
    }

    private class CacheTask extends AsyncTask<String, Void, Void> {
        private final WeatherRequest mRequest;
        private final int mRequestType;
        private final WeatherResponse mResponse;

        public CacheTask(int type, WeatherRequest request, WeatherResponse response) {
            this.mRequestType = type;
            this.mRequest = request;
            this.mResponse = response;
        }

        protected Void doInBackground(String... key) {
            byte[] data = WeatherCache.getInstance(WeatherRequestExecuter.this.mContext).getFromDiskCache(key[0]);
            try {
                RootWeather rootWeather;
                if (this.mRequestType == 8) {
                    rootWeather = this.mRequest.getResponseParser().parseAqi(data);
                } else if (this.mRequestType == 1) {
                    rootWeather = this.mRequest.getResponseParser().parseCurrent(data);
                } else if (this.mRequestType == 16) {
                    rootWeather = this.mRequest.getResponseParser().parseLifeIndex(data);
                } else if (this.mRequestType == 2) {
                    rootWeather = this.mRequest.getResponseParser().parseHourForecasts(data);
                } else if (this.mRequestType == 4) {
                    rootWeather = this.mRequest.getResponseParser().parseDailyForecasts(data);
                } else if (this.mRequestType == 32) {
                    rootWeather = this.mRequest.getResponseParser().parseAlarm(data);
                } else {
                    throw new WeatherException("Unsupport request type!");
                }
                if (rootWeather != null) {
                    rootWeather.setRequestIsSuccess($assertionsDisabled);
                }
                this.mResponse.addResponse(rootWeather, this.mRequestType);
            } catch (WeatherException e) {
                if (e instanceof ParseException) {
                    this.mResponse.addResponse(null, this.mRequestType);
                } else {
                    this.mResponse.setError(new WeatherException(e.getMessage()));
                }
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            WeatherRequestExecuter.this.requestOrDeliverNetwork(this.mRequest, this.mResponse);
        }
    }

    private class NetworkParserWorkerTask extends AsyncTask<byte[], Void, Void> {
        private final WeatherRequest mRequest;
        private final int mRequestType;
        private final WeatherResponse mResponse;

        public NetworkParserWorkerTask(int type, WeatherRequest request, WeatherResponse response) {
            this.mRequestType = type;
            this.mRequest = request;
            this.mResponse = response;
        }

        protected Void doInBackground(byte[]... params) {
            byte[] data = params[0];
            try {
                RootWeather rootWeather;
                if (this.mRequestType == 8) {
                    rootWeather = this.mRequest.getResponseParser().parseAqi(data);
                } else if (this.mRequestType == 1) {
                    rootWeather = this.mRequest.getResponseParser().parseCurrent(data);
                } else if (this.mRequestType == 16) {
                    rootWeather = this.mRequest.getResponseParser().parseLifeIndex(data);
                } else if (this.mRequestType == 2) {
                    rootWeather = this.mRequest.getResponseParser().parseHourForecasts(data);
                } else if (this.mRequestType == 4) {
                    rootWeather = this.mRequest.getResponseParser().parseDailyForecasts(data);
                } else if (this.mRequestType == 32) {
                    rootWeather = this.mRequest.getResponseParser().parseAlarm(data);
                } else {
                    throw new WeatherException("Unsupport request type!");
                }
                if (rootWeather != null) {
                    rootWeather.setRequestIsSuccess(true);
                }
                this.mResponse.addResponse(rootWeather, this.mRequestType);
            } catch (WeatherException e) {
                if (e instanceof ParseException) {
                    this.mResponse.addResponse(null, this.mRequestType);
                } else {
                    this.mResponse.setError(new WeatherException(e.getMessage()));
                }
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            WeatherRequestExecuter.this.requestOrDeliverNetwork(this.mRequest, this.mResponse);
        }
    }

    class AnonymousClass_1 implements ResponseListener {
        final /* synthetic */ WeatherRequest val$request;
        final /* synthetic */ WeatherResponse val$response;
        final /* synthetic */ int val$type;

        AnonymousClass_1(WeatherRequest weatherRequest, int i, WeatherResponse weatherResponse) {
            this.val$request = weatherRequest;
            this.val$type = i;
            this.val$response = weatherResponse;
        }

        public void onResponse(byte[] data, String charset) {
            WeatherRequestExecuter.this.addToDiskCache(WeatherRequestExecuter.this.mContext, this.val$request.getDiskCacheKey(this.val$type), data);
            new NetworkParserWorkerTask(this.val$type, this.val$request, this.val$response).execute(new Object[]{data});
        }

        public void onError(WeatherException e) {
            Log.e(TAG, "onError: " + e.toString());
            RootWeather weather = WeatherCache.getInstance(WeatherRequestExecuter.this.mContext).getFromMemCache(this.val$request.getMemCacheKey());
            if (weather == null || !WeatherResponse.containRequestedData(this.val$request.getRequestType(), weather)) {
                String key = this.val$request.getDiskCacheKey(this.val$type);
                new CacheTask(this.val$type, this.val$request, this.val$response).execute(new String[]{key});
                return;
            }
            RootWeather emptyWeather = new RootWeather(weather.getAreaCode(), weather.getDataSourceName());
            WeatherRequestExecuter.this.setRootWeather(emptyWeather, weather, this.val$type);
            emptyWeather.setRequestIsSuccess($assertionsDisabled);
            this.val$response.addResponse(emptyWeather, this.val$type);
            WeatherRequestExecuter.this.requestOrDeliverNetwork(this.val$request, this.val$response);
        }
    }

    static {
        $assertionsDisabled = !WeatherRequestExecuter.class.desiredAssertionStatus() ? true : $assertionsDisabled;
    }

    public WeatherRequestExecuter(Context context) {
        this.mContext = context;
    }

    public void execute(WeatherRequest request) {
        if (!$assertionsDisabled && request == null) {
            throw new AssertionError();
        } else if (request.getCacheMode() == CacheMode.LOAD_DEFAULT) {
            fetchCache(request);
            fetchNetwork(request);
        } else if (request.getCacheMode() == CacheMode.LOAD_NO_CACHE) {
            fetchNetwork(request);
        } else {
            fetchCache(request);
        }
    }

    private void fetchNetwork(WeatherRequest request) {
        requestOrDeliverNetwork(request, new WeatherResponse());
    }

    private void fetchCache(WeatherRequest request) {
        RootWeather weather = WeatherCache.getInstance(this.mContext).getFromMemCache(request.getMemCacheKey());
        if (weather == null || !WeatherResponse.containRequestedData(request.getRequestType(), weather)) {
            fetchDiskCache(request);
            return;
        }
        request.deliverCacheResponse(weather);
        LogUtils.d(TAG, "\u547d\u4e2d\u5185\u5b58\u7f13\u5b58\uff0c\u533a\u57dfid\uff1a" + (weather.getAreaCode() != null ? weather.getAreaCode() : "null") + "\uff0c\u533a\u57df\u540d\u79f0\uff1a" + (weather.getAreaName() != null ? weather.getAreaName() : "null"), new Object[0]);
    }

    private void fetchDiskCache(WeatherRequest request) {
        requestOrDeliverCache(request, new CacheBox());
    }

    private void requestOrDeliverNetwork(WeatherRequest request, WeatherResponse response) {
        if (!response.isSuccess()) {
            WeatherResponse.deliverResponse(this.mContext, request, response);
        } else if (request.containRequest(1) && !response.isRequested(1)) {
            requestNetworkData(1, request, response);
        } else if (request.containRequest(DetectedActivity.RUNNING) && !response.isRequested(DetectedActivity.RUNNING)) {
            requestNetworkData(DetectedActivity.RUNNING, request, response);
        } else if (request.containRequest(ConnectionResult.API_UNAVAILABLE) && !response.isRequested(ConnectionResult.API_UNAVAILABLE)) {
            requestNetworkData(ConnectionResult.API_UNAVAILABLE, request, response);
        } else if (request.containRequest(RainSurfaceView.RAIN_LEVEL_SHOWER) && !response.isRequested(RainSurfaceView.RAIN_LEVEL_SHOWER)) {
            requestNetworkData(RainSurfaceView.RAIN_LEVEL_SHOWER, request, response);
        } else if (request.containRequest(RainSurfaceView.RAIN_LEVEL_RAINSTORM) && !response.isRequested(RainSurfaceView.RAIN_LEVEL_RAINSTORM)) {
            requestNetworkData(RainSurfaceView.RAIN_LEVEL_RAINSTORM, request, response);
        } else if (!request.containRequest(ItemTouchHelper.END) || response.isRequested(ItemTouchHelper.END)) {
            WeatherResponse.deliverResponse(this.mContext, request, response);
        } else {
            requestNetworkData(ItemTouchHelper.END, request, response);
        }
    }

    private void requestNetworkData(int type, WeatherRequest request, WeatherResponse response) {
        String url = request.getRequestUrl(type);
        Log.d(TAG, "Request url: " + url);
        NetworkHelper.getInstance(this.mContext).get(url, new AnonymousClass_1(request, type, response), request.getHttpCacheEnable());
    }

    public RootWeather setRootWeather(RootWeather targetWeather, RootWeather weather, int type) {
        if (weather != null) {
            switch (type) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    targetWeather.setCurrentWeather(weather.getCurrentWeather());
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    targetWeather.setHourForecastsWeather(weather.getHourForecastsWeather());
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    targetWeather.setDailyForecastsWeather(weather.getDailyForecastsWeather());
                    targetWeather.setFutureLink(weather.getFutureLink());
                    break;
                case DetectedActivity.RUNNING:
                    targetWeather.setAqiWeather(weather.getAqiWeather());
                    break;
                case ConnectionResult.API_UNAVAILABLE:
                    targetWeather.setLifeIndexWeather(weather.getLifeIndexWeather());
                    break;
                case ItemTouchHelper.END:
                    targetWeather.setWeatherAlarms(weather.getWeatherAlarms());
                    break;
            }
        }
        return targetWeather;
    }

    private void requestOrDeliverCache(WeatherRequest request, CacheBox box) {
        if (box.error) {
            request.deliverCacheResponse(null);
        } else if (request.containRequest(1) && !box.isRequested(1)) {
            requestCacheData(1, request, box);
        } else if (request.containRequest(DetectedActivity.RUNNING) && !box.isRequested(DetectedActivity.RUNNING)) {
            requestCacheData(DetectedActivity.RUNNING, request, box);
        } else if (request.containRequest(ConnectionResult.API_UNAVAILABLE) && !box.isRequested(ConnectionResult.API_UNAVAILABLE)) {
            requestCacheData(ConnectionResult.API_UNAVAILABLE, request, box);
        } else if (request.containRequest(RainSurfaceView.RAIN_LEVEL_SHOWER) && !box.isRequested(RainSurfaceView.RAIN_LEVEL_SHOWER)) {
            requestCacheData(RainSurfaceView.RAIN_LEVEL_SHOWER, request, box);
        } else if (request.containRequest(RainSurfaceView.RAIN_LEVEL_RAINSTORM) && !box.isRequested(RainSurfaceView.RAIN_LEVEL_RAINSTORM)) {
            requestCacheData(RainSurfaceView.RAIN_LEVEL_RAINSTORM, request, box);
        } else if (!request.containRequest(ItemTouchHelper.END) || box.isRequested(ItemTouchHelper.END)) {
            RootWeather result = box.getResult();
            if (result != null) {
                result.writeMemoryCache(request, WeatherCache.getInstance(this.mContext));
                LogUtils.d(TAG, "\u547d\u4e2d\u78c1\u76d8\u7f13\u5b58\uff0c\u533a\u57dfid\uff1a" + (result.getAreaCode() != null ? result.getAreaCode() : "null") + "\uff0c\u533a\u57df\u540d\u79f0\uff1a" + (result.getAreaName() != null ? result.getAreaName() : "null"), new Object[0]);
            } else {
                LogUtils.d(TAG, "\u7f13\u5b58\u4e2d\u4e0d\u5b58\u5728\uff0c\u5730\u533aid\uff1a" + request.getRequestKey(), new Object[0]);
                if (request.getCacheMode() == CacheMode.LOAD_CACHE_ELSE_NETWORK) {
                    fetchNetwork(request);
                }
            }
            request.deliverCacheResponse(result);
        } else {
            requestCacheData(ItemTouchHelper.END, request, box);
        }
    }

    private void requestCacheData(int type, WeatherRequest request, CacheBox box) {
        LogUtils.d(TAG, "Cache key: " + request.getDiskCacheKey(type), new Object[0]);
        new CacheParserWorkerTask(type, request, box).execute(new String[]{key});
    }
}
