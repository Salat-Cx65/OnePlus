package net.oneplus.weather.api;

import android.content.Context;

import net.oneplus.weather.api.cache.WeatherCache;
import net.oneplus.weather.api.nodes.RootWeather;

public class WeatherResponse {
    private WeatherException mError;
    private int mRequestedType;
    private RootWeather mResult;

    public interface CacheListener {
        void onResponse(RootWeather rootWeather);
    }

    public static interface NetworkListener {
        void onResponseError(WeatherException weatherException);

        void onResponseSuccess(RootWeather rootWeather);
    }

    public static void deliverResponse(Context context, WeatherRequest request, WeatherResponse response) {
        if (response.isSuccess()) {
            RootWeather weather = response.getResult();
            if (weather == null) {
                request.deliverNetworkError(new WeatherException("Weather response is null!"));
                return;
            } else if (containRequestedData(request.getRequestType(), weather)) {
                weather.writeMemoryCache(request, WeatherCache.getInstance(context));
                request.deliverNetworkResponse(weather);
                return;
            } else {
                request.deliverNetworkError(new WeatherException("Response not contained the request data!"));
                return;
            }
        }
        request.deliverNetworkError(response.getError());
    }

    public static boolean containRequestedData(int type, RootWeather weather) {
        if (WeatherRequest.contain(type, WeatherRequest.Type.AQI) && weather.getAqiWeather() != null) {
            return true;
        }
        if (WeatherRequest.contain(type, WeatherRequest.Type.CURRENT) && weather.getCurrentWeather() != null) {
            return true;
        }
        if (WeatherRequest.contain(type, WeatherRequest.Type.DAILY_FORECASTS) && weather.getDailyForecastsWeather()
                != null) {
            return true;
        }
        if (WeatherRequest.contain(type, WeatherRequest.Type.HOUR_FORECASTS) && weather.getHourForecastsWeather() !=
                null) {
            return true;
        }
        if (!WeatherRequest.contain(type, WeatherRequest.Type.LIFE_INDEX) || weather.getLifeIndexWeather() == null) {
            return WeatherRequest.contain(type, WeatherRequest.Type.LIFE_INDEX) && weather.getWeatherAlarms() != null;
        } else {
            return true;
        }
    }

    public void addResponse(RootWeather weather, int type) {
        if (mResult == null) {
            mResult = weather;
        } else if (weather != null) {
            switch (type) {
                case WeatherRequest.Type.CURRENT:
                    mResult.setCurrentWeather(weather.getCurrentWeather());
                    break;
                case WeatherRequest.Type.HOUR_FORECASTS:
                    mResult.setHourForecastsWeather(weather.getHourForecastsWeather());
                    break;
                case WeatherRequest.Type.DAILY_FORECASTS:
                    mResult.setDailyForecastsWeather(weather.getDailyForecastsWeather());
                    mResult.setFutureLink(weather.getFutureLink());
                    break;
                case WeatherRequest.Type.AQI:
                    mResult.setAqiWeather(weather.getAqiWeather());
                    break;
                case WeatherRequest.Type.LIFE_INDEX:
                    mResult.setLifeIndexWeather(weather.getLifeIndexWeather());
                    break;
                case WeatherRequest.Type.ALARM:
                    mResult.setWeatherAlarms(weather.getWeatherAlarms());
                    break;
                default:
                    break;
            }
        }
        mRequestedType |= type;
    }

    public boolean isSuccess() {
        return getError() == null;
    }

    public RootWeather getResult() {
        return mResult;
    }

    public WeatherException getError() {
        return mError;
    }

    public boolean isRequested(int type) {
        boolean hasData = false;
        switch (type) {
            case WeatherRequest.Type.CURRENT:
                if (mResult == null || mResult.getCurrentWeather() == null) {
                    hasData = false;
                } else {
                    hasData = true;
                }
                break;
            case WeatherRequest.Type.HOUR_FORECASTS:
                if (mResult == null || mResult.getHourForecastsWeather() == null) {
                    hasData = false;
                } else {
                    hasData = true;
                }
                break;
            case WeatherRequest.Type.DAILY_FORECASTS:
                if (mResult == null || mResult.getDailyForecastsWeather() == null) {
                    hasData = false;
                } else {
                    hasData = true;
                }
                break;
            case WeatherRequest.Type.AQI:
                if (mResult == null || mResult.getAqiWeather() == null) {
                    hasData = false;
                } else {
                    hasData = true;
                }
                break;
            case WeatherRequest.Type.LIFE_INDEX:
                if (mResult == null || mResult.getLifeIndexWeather() == null) {
                    hasData = false;
                } else {
                    hasData = true;
                }
                break;
            case WeatherRequest.Type.ALARM:
                hasData = (mResult == null || mResult.getWeatherAlarms() == null) ? false : true;
                break;
        }
        return (mRequestedType & type) == type || hasData;
    }

    public void setError(WeatherException error) {
        mError = error;
        mResult = null;
        mRequestedType = 0;
    }
}
