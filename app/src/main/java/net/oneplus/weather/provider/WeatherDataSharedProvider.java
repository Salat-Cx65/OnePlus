package net.oneplus.weather.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Locale;

import net.oneplus.weather.api.WeatherException;
import net.oneplus.weather.api.helper.WeatherUtils;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.model.CityData;
import net.oneplus.weather.provider.LocationProvider.OnLocationListener;
import net.oneplus.weather.util.DateTimeUtils;
import net.oneplus.weather.util.NetUtil;
import net.oneplus.weather.util.PermissionUtil;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.util.SystemSetting;
import net.oneplus.weather.util.WeatherClientProxy;
import net.oneplus.weather.util.WeatherClientProxy.CacheMode;
import net.oneplus.weather.util.WeatherClientProxy.OnResponseListener;
import net.oneplus.weather.util.WeatherResHelper;

public class WeatherDataSharedProvider extends ContentProvider {
    private static final String CONTENT_TYPE_ALL = "vnd.android.cursor.item/weatehr";
    public static final Uri CONTENT_URI;
    private static final String TAG;
    private static final int WEATHER_ALL = 1;
    public static final String WEATHER_AUTHORIY = "com.oneplus.weather.ContentProvider";
    private static final UriMatcher uriMatcher;
    private Handler h;

    class AnonymousClass_1 implements Runnable {
        final /* synthetic */ WeatherCursor val$cursor;
        final /* synthetic */ Object val$mLock;

        class AnonymousClass_2 implements OnResponseListener {
            final /* synthetic */ CityData val$mCity;

            AnonymousClass_2(CityData cityData) {
                this.val$mCity = cityData;
            }

            public void onNetworkResponse(RootWeather response) {
                if (response != null) {
                    AnonymousClass_1.this.val$cursor.addData(WeatherDataSharedProvider.parseList(WeatherDataSharedProvider.this.getContext(), this.val$mCity, response));
                }
                synchronized (AnonymousClass_1.this.val$mLock) {
                    AnonymousClass_1.this.val$mLock.notifyAll();
                }
            }

            public void onErrorResponse(WeatherException exception) {
                synchronized (AnonymousClass_1.this.val$mLock) {
                    AnonymousClass_1.this.val$mLock.notifyAll();
                }
            }

            public void onCacheResponse(RootWeather response) {
                if (response != null) {
                    AnonymousClass_1.this.val$cursor.addData(WeatherDataSharedProvider.parseList(WeatherDataSharedProvider.this.getContext(), this.val$mCity, response));
                }
                synchronized (AnonymousClass_1.this.val$mLock) {
                    AnonymousClass_1.this.val$mLock.notifyAll();
                }
            }
        }

        AnonymousClass_1(Object obj, WeatherCursor weatherCursor) {
            this.val$mLock = obj;
            this.val$cursor = weatherCursor;
        }

        public void run() {
            boolean nendChangeLanguage = (!NetUtil.isNetworkAvailable(WeatherDataSharedProvider.this.getContext()) || TextUtils.isEmpty(SystemSetting.getLocale(WeatherDataSharedProvider.this.getContext())) || Locale.getDefault().toString().equals(SystemSetting.getLocale(WeatherDataSharedProvider.this.getContext()))) ? false : true;
            Log.d(TAG, "nendChangeLanguage :" + nendChangeLanguage);
            if (nendChangeLanguage) {
                LocationProvider locationProvider = new LocationProvider(WeatherDataSharedProvider.this.getContext());
                locationProvider.setOnLocationListener(new OnLocationListener() {

                    class AnonymousClass_1 implements OnResponseListener {
                        final /* synthetic */ CityData val$mCity;

                        AnonymousClass_1(CityData cityData) {
                            this.val$mCity = cityData;
                        }

                        public void onNetworkResponse(RootWeather response) {
                            if (response != null) {
                                AnonymousClass_1.this.val$cursor.addData(WeatherDataSharedProvider.parseList(WeatherDataSharedProvider.this.getContext(), this.val$mCity, response));
                            }
                            synchronized (AnonymousClass_1.this.val$mLock) {
                                AnonymousClass_1.this.val$mLock.notifyAll();
                            }
                        }

                        public void onErrorResponse(WeatherException exception) {
                            synchronized (AnonymousClass_1.this.val$mLock) {
                                AnonymousClass_1.this.val$mLock.notifyAll();
                            }
                        }

                        public void onCacheResponse(RootWeather response) {
                            if (response != null) {
                                AnonymousClass_1.this.val$cursor.addData(WeatherDataSharedProvider.parseList(WeatherDataSharedProvider.this.getContext(), this.val$mCity, response));
                            }
                            synchronized (AnonymousClass_1.this.val$mLock) {
                                AnonymousClass_1.this.val$mLock.notifyAll();
                            }
                        }
                    }

                    public void onLocationChanged(CityData mCity) {
                        if (mCity == null || TextUtils.isEmpty(mCity.getLocationId()) || "0".equals(mCity.getLocationId())) {
                            synchronized (AnonymousClass_1.this.val$mLock) {
                                AnonymousClass_1.this.val$mLock.notifyAll();
                            }
                            return;
                        }
                        new WeatherClientProxy(WeatherDataSharedProvider.this.getContext()).setCacheMode(CacheMode.LOAD_CACHE_ELSE_NETWORK).requestWeatherInfo(mCity, new AnonymousClass_1(mCity));
                    }

                    public void onError(int error) {
                        Log.e("AmapErr", "Location ERR:" + error);
                        synchronized (AnonymousClass_1.this.val$mLock) {
                            AnonymousClass_1.this.val$mLock.notifyAll();
                        }
                    }
                });
                locationProvider.startLocation();
                return;
            }
            CityData mCity = SystemSetting.getLocationOrDefaultCity(WeatherDataSharedProvider.this.getContext());
            if (mCity.getLocationId() == null || mCity.getLocalName() == null || TextUtils.isEmpty(mCity.getLocationId()) || TextUtils.isEmpty(mCity.getLocalName())) {
                synchronized (this.val$mLock) {
                    this.val$mLock.notifyAll();
                }
                Log.d(TAG, "CityData LocationId is " + mCity.getLocationId() + "    CityData LocalName is " + mCity.getLocalName());
                return;
            }
            new WeatherClientProxy(WeatherDataSharedProvider.this.getContext()).setCacheMode(CacheMode.LOAD_CACHE_ELSE_NETWORK).requestWeatherInfo(mCity, new AnonymousClass_2(mCity));
        }
    }

    public WeatherDataSharedProvider() {
        this.h = new Handler(Looper.getMainLooper());
    }

    static {
        TAG = WeatherDataSharedProvider.class.getSimpleName();
        uriMatcher = new UriMatcher(-1);
        CONTENT_URI = Uri.parse("content://com.oneplus.weather.ContentProvider/data");
        uriMatcher.addURI(WEATHER_AUTHORIY, "data", WEATHER_ALL);
    }

    public String getType(Uri uri) {
        System.out.println("getType");
        switch (uriMatcher.match(uri)) {
            case WEATHER_ALL:
                return CONTENT_TYPE_ALL;
            default:
                return null;
        }
    }

    public int delete(Uri arg0, String arg1, String[] arg2) {
        return 0;
    }

    public Uri insert(Uri uri, ContentValues arg1) {
        return null;
    }

    public boolean onCreate() {
        return false;
    }

    private static ArrayList<String> parseList(Context context, CityData city, RootWeather response) {
        ArrayList<String> data = new ArrayList();
        if (!(city == null || response == null || context == null || response.getCurrentWeather() == null)) {
            String date = DateTimeUtils.dateToString(response.getCurrentWeather().getObservationDate(), DateTimeUtils.WC_FORMAT);
            if (date == null) {
                date = StringUtils.EMPTY_STRING;
            }
            boolean isChina = city.getProvider() == 4096;
            String name;
            if (SystemSetting.getTemperature(context)) {
                data.add(date);
                name = city.getLocalName();
                data.add(name);
                data.add(WeatherResHelper.weatherToResID(context, response.getCurrentWeatherId()) + StringUtils.EMPTY_STRING);
                data.add(response.getTodayCurrentTemp() + StringUtils.EMPTY_STRING);
                data.add(response.getTodayHighTemperature() + StringUtils.EMPTY_STRING);
                data.add(response.getTodayLowTemperature() + StringUtils.EMPTY_STRING);
                if (isChina) {
                    data.add(WeatherUtils.getOppoChinaWeatherTextById(context, response.getCurrentWeatherId()) + StringUtils.EMPTY_STRING);
                } else {
                    data.add(WeatherUtils.getWeatherTextByWeatherId(context, response.getCurrentWeatherId()) + StringUtils.EMPTY_STRING);
                }
                data.add(context.getResources().getString(R.string.c) + StringUtils.EMPTY_STRING);
                Log.i(TAG, "share data: " + response.getTodayCurrentTemp() + context.getResources().getString(R.string.c) + "     city:" + name);
            } else {
                data.add(date);
                name = city.getLocalName();
                data.add(name);
                data.add(WeatherResHelper.weatherToResID(context, response.getCurrentWeatherId()) + StringUtils.EMPTY_STRING);
                data.add(((int) SystemSetting.celsiusToFahrenheit((float) response.getTodayCurrentTemp())) + StringUtils.EMPTY_STRING);
                data.add(((int) SystemSetting.celsiusToFahrenheit((float) response.getTodayHighTemperature())) + StringUtils.EMPTY_STRING);
                data.add(((int) SystemSetting.celsiusToFahrenheit((float) response.getTodayLowTemperature())) + StringUtils.EMPTY_STRING);
                if (isChina) {
                    data.add(WeatherUtils.getOppoChinaWeatherTextById(context, response.getCurrentWeatherId()) + StringUtils.EMPTY_STRING);
                } else {
                    data.add(WeatherUtils.getWeatherTextByWeatherId(context, response.getCurrentWeatherId()) + StringUtils.EMPTY_STRING);
                }
                data.add(context.getResources().getString(R.string.f) + StringUtils.EMPTY_STRING);
                Log.i(TAG, "share data: " + ((int) SystemSetting.celsiusToFahrenheit((float) response.getTodayCurrentTemp())) + context.getResources().getString(R.string.f) + "    city:" + name);
            }
        }
        return data;
    }

    public Cursor query(Uri uri, String[] arg1, String arg2, String[] arg3, String arg4) {
        if (!PermissionUtil.hasGrantedPermissions(getContext(), "android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE")) {
            return null;
        }
        Object mLock = new Object();
        Cursor cursor = new WeatherCursor();
        this.h.post(new AnonymousClass_1(mLock, cursor));
        try {
            synchronized (mLock) {
                Log.i(TAG, "wait");
                mLock.wait();
            }
            return cursor;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return cursor;
        }
    }

    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        return 0;
    }
}
