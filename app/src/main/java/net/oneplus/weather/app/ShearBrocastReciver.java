package net.oneplus.weather.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import net.oneplus.weather.api.WeatherException;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.model.CityData;
import net.oneplus.weather.provider.LocationProvider;
import net.oneplus.weather.provider.LocationProvider.OnLocationListener;
import net.oneplus.weather.util.NetUtil;
import net.oneplus.weather.util.WeatherClientProxy;
import net.oneplus.weather.util.WeatherClientProxy.CacheMode;
import net.oneplus.weather.util.WeatherClientProxy.OnResponseListener;
import net.oneplus.weather.util.WeatherResHelper;

public class ShearBrocastReciver extends BroadcastReceiver {
    private static String ACTION_LOCATION;
    private static String ACTION_WEATHER;
    private static String TAG;
    private CityData mCityData;

    class AnonymousClass_1 implements OnLocationListener {
        final /* synthetic */ Context val$context;
        final /* synthetic */ CacheMode val$mode;

        AnonymousClass_1(Context context, CacheMode cacheMode) {
            this.val$context = context;
            this.val$mode = cacheMode;
        }

        public void onLocationChanged(CityData data) {
            ShearBrocastReciver.this.mCityData = data;
            ShearBrocastReciver.this.requestWeather(this.val$context, ShearBrocastReciver.this.mCityData, this.val$mode);
        }

        public void onError(int error) {
            if (ShearBrocastReciver.this.mCityData != null) {
                ShearBrocastReciver.this.requestWeather(this.val$context, ShearBrocastReciver.this.mCityData, this.val$mode);
            }
        }
    }

    class AnonymousClass_2 implements OnResponseListener {
        final /* synthetic */ Context val$context;

        AnonymousClass_2(Context context) {
            this.val$context = context;
        }

        public void onNetworkResponse(RootWeather response) {
            ShearBrocastReciver.sendWeatherInfoBrocast(this.val$context, ShearBrocastReciver.this.mCityData.getLocalName(), response);
        }

        public void onErrorResponse(WeatherException errorCode) {
        }

        public void onCacheResponse(RootWeather response) {
            ShearBrocastReciver.sendWeatherInfoBrocast(this.val$context, ShearBrocastReciver.this.mCityData.getLocalName(), response);
        }
    }

    static {
        TAG = "ShearBrocastReciver";
        ACTION_LOCATION = "net.oneplus.weather.GET_LOCATION_WEATHER";
        ACTION_WEATHER = "net.oneplus.weather.RECEIVE_LOCATION_WEATHER";
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_LOCATION)) {
            getLocation(context, CacheMode.LOAD_NO_CACHE);
        }
    }

    private void getLocation(Context context, CacheMode mode) {
        if (NetUtil.isNetworkAvailable(context)) {
            LocationProvider locationProvider = new LocationProvider(context);
            locationProvider.setOnLocationListener(new AnonymousClass_1(context, mode));
            locationProvider.startLocation();
            return;
        }
        requestWeather(context, this.mCityData, mode);
    }

    public void requestWeather(Context context, CityData city, CacheMode mode) {
        if (city != null && !TextUtils.isEmpty(city.getLocationId()) && !city.getLocationId().equals("0")) {
            new WeatherClientProxy(context).setCacheMode(mode).requestWeatherInfo(city, new AnonymousClass_2(context));
        }
    }

    public static void sendWeatherInfoBrocast(Context context, String name, RootWeather weather) {
        if (weather != null && weather.getCurrentWeather() != null) {
            Intent updateIntent = new Intent(ACTION_WEATHER);
            Bundle bundle = new Bundle();
            bundle.putString("CityName", name);
            bundle.putString("CurrentWeatherName", weather.getCurrentWeatherText(context));
            bundle.putInt("CurrentWeatherTypeId", WeatherResHelper.weatherToResID(context, weather.getCurrentWeatherId()));
            bundle.putInt("CurrentTemp", weather.getTodayCurrentTemp());
            bundle.putInt("NightTemp", weather.getTodayLowTemperature());
            bundle.putInt("DayTemp", weather.getTodayHighTemperature());
            updateIntent.putExtra("OpWeatherInfo", bundle);
            context.sendBroadcast(updateIntent);
        }
    }
}
