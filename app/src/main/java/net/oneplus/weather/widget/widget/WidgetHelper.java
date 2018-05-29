package net.oneplus.weather.widget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import net.oneplus.weather.R;
import net.oneplus.weather.api.WeatherException;
import net.oneplus.weather.api.nodes.CurrentWeather;
import net.oneplus.weather.api.nodes.DailyForecastsWeather;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.app.citylist.CityListActivity;
import net.oneplus.weather.db.CityWeatherDB;
import net.oneplus.weather.db.CityWeatherDBHelper.CityListEntry;
import net.oneplus.weather.db.CityWeatherDBHelper.WeatherEntry;
import net.oneplus.weather.model.CityData;
import net.oneplus.weather.util.DateTimeUtils;
import net.oneplus.weather.util.PreferenceUtils;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.util.TemperatureUtil;
import net.oneplus.weather.util.WeatherClientProxy;
import net.oneplus.weather.util.WeatherClientProxy.CacheMode;
import net.oneplus.weather.util.WeatherClientProxy.OnResponseListener;
import net.oneplus.weather.util.WeatherLog;
import net.oneplus.weather.util.WeatherResHelper;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class WidgetHelper extends ContextWrapper {
    public static final String ACTION_REFRESH = "net.oneplus.weather.widget.REFRESH";
    public static final String NEED_REFRESH = "need_refresh";
    public static final String TAG = "WidgetHelper";
    public static final String WIDGET_ID = "widget_id";
    public static final String WIDGET_ID_PREFIX = "widget_";
    private static volatile AppWidgetManager sAppWidgetManager;
    private static volatile WidgetHelper sWidgetHelper;
    private final Handler mHandler;
    private RemoteViews mRemoteViews;

    class AnonymousClass_1 implements OnResponseListener {
        final /* synthetic */ CityData val$finalCityData;
        final /* synthetic */ int val$finalWidgetId;

        class AnonymousClass_1 implements Runnable {
            final /* synthetic */ RootWeather val$response;

            AnonymousClass_1(RootWeather rootWeather) {
                this.val$response = rootWeather;
            }

            public void run() {
                WidgetHelper.this.updateWeatherWidget(this.val$response, AnonymousClass_1.this.val$finalCityData, AnonymousClass_1.this.val$finalWidgetId);
            }
        }

        class AnonymousClass_2 implements Runnable {
            final /* synthetic */ RootWeather val$response;

            AnonymousClass_2(RootWeather rootWeather) {
                this.val$response = rootWeather;
            }

            public void run() {
                WidgetHelper.this.updateWeatherWidget(this.val$response, AnonymousClass_1.this.val$finalCityData, AnonymousClass_1.this.val$finalWidgetId);
            }
        }

        AnonymousClass_1(CityData cityData, int i) {
            this.val$finalCityData = cityData;
            this.val$finalWidgetId = i;
        }

        public void onCacheResponse(RootWeather response) {
            WidgetHelper.this.mHandler.postDelayed(new AnonymousClass_1(response), 500);
        }

        public void onNetworkResponse(RootWeather response) {
            WidgetHelper.this.mHandler.postDelayed(new AnonymousClass_2(response), 500);
        }

        public void onErrorResponse(WeatherException exception) {
            WidgetHelper.this.setWidgetFail(this.val$finalWidgetId);
            WeatherLog.e("update error" + exception);
        }
    }

    private WidgetHelper(Context base) {
        super(base);
        this.mHandler = new Handler();
        this.mRemoteViews = new RemoteViews(getPackageName(), 2131492996);
    }

    public static WidgetHelper getInstance(Context context) {
        if (sWidgetHelper == null) {
            synchronized (WidgetHelper.class) {
                if (sWidgetHelper == null) {
                    sWidgetHelper = new WidgetHelper(context);
                    sAppWidgetManager = AppWidgetManager.getInstance(context);
                }
            }
        }
        return sWidgetHelper;
    }

    public void updateAllWidget(boolean isForce) {
        for (int id : sAppWidgetManager.getAppWidgetIds(new ComponentName(this, WeatherWidgetProvider.class))) {
            updateWidgetById(id, isForce);
        }
    }

    public void updateWidgetById(int widgetId, boolean isForce) {
        if (widgetId == -1) {
            throw new IllegalArgumentException("citydata can't be empty");
        }
        updateDataThenWidget(null, widgetId, isForce);
    }

    private void updateDataThenWidget(CityData cityData, int widgetId, boolean isForce) {
        if (cityData == null && widgetId != -1) {
            CityWeatherDB instance = CityWeatherDB.getInstance(this);
            int locationId = PreferenceUtils.getInt(this, WIDGET_ID_PREFIX + widgetId, -1);
            if (locationId == -1) {
                WeatherLog.e("locationId is null ");
                setWidgetFailException(widgetId);
                return;
            }
            WeatherLog.d("locationId is :" + locationId);
            cityData = getCityByID(this, locationId);
            if (cityData == null) {
                setWidgetFailException(widgetId);
                return;
            }
        }
        if (cityData != null && widgetId == -1) {
            String locationId2 = cityData.getLocationId();
            if (TextUtils.isEmpty(locationId2)) {
                WeatherLog.e("locationId is null ");
                setWidgetFailException(widgetId);
                return;
            }
            widgetId = PreferenceUtils.getInt(this, WIDGET_ID_PREFIX + locationId2, -1);
        }
        if (cityData == null || widgetId == -1) {
            WeatherLog.e("cityData or widgetId is null ");
            return;
        }
        this.mRemoteViews.setViewVisibility(R.id.weather_widget, DetectedActivity.RUNNING);
        this.mRemoteViews.setViewVisibility(R.id.widget_refreshing_group, 0);
        this.mRemoteViews.setTextViewText(R.id.widget_refreshing_city, cityData.getLocalName());
        this.mRemoteViews.setImageViewResource(R.id.widget_refreshing_bar, R.drawable.btn_refresh);
        this.mRemoteViews.setTextViewText(R.id.widget_refreshing_text, getString(R.string.widget_refreshing));
        Log.d(TAG, "updateDataThenWidget getConfiguration: " + getResources().getConfiguration().toString());
        sAppWidgetManager.updateAppWidget(widgetId, this.mRemoteViews);
        new WeatherClientProxy(this).setCacheMode(isForce ? CacheMode.LOAD_NO_CACHE : CacheMode.LOAD_CACHE_ELSE_NETWORK).requestWeatherInfo(cityData, new AnonymousClass_1(cityData, widgetId));
    }

    private CityData getCityFromCoursor(Cursor cursor, int index) {
        if (cursor == null || cursor.getCount() < index) {
            return null;
        }
        cursor.moveToPosition(index);
        int provider = cursor.getInt(cursor.getColumnIndex(CityListEntry.COLUMN_1_PROVIDER));
        String cityName = cursor.getString(cursor.getColumnIndex(CityListEntry.COLUMN_2_NAME));
        String cityDisplayName = cursor.getString(cursor.getColumnIndex(CityListEntry.COLUMN_3_DISPLAY_NAME));
        String cityLocationId = cursor.getString(cursor.getColumnIndex(WeatherEntry.COLUMN_1_LOCATION_ID));
        CityData city = new CityData();
        city.setProvider(provider);
        city.setName(cityName);
        city.setLocalName(cityDisplayName);
        city.setLocationId(cityLocationId);
        return city;
    }

    private void updateWeatherWidget(RootWeather response, CityData cityData, int widgetId) {
        if (response == null || cityData == null) {
            setWidgetFailException(widgetId);
            return;
        }
        String timeZone = DateTimeUtils.CHINA_OFFSET;
        if (response.getCurrentWeather() != null) {
            CurrentWeather currentWeather = response.getCurrentWeather();
            this.mRemoteViews.setViewVisibility(R.id.weather_widget, 0);
            this.mRemoteViews.setViewVisibility(R.id.widget_refreshing_group, DetectedActivity.RUNNING);
            timeZone = currentWeather.getLocalTimeZone();
            this.mRemoteViews.setTextViewText(R.id.widget_city_name, cityData.getLocalName());
            RemoteViews remoteViews = this.mRemoteViews;
            Object[] objArr = new Object[1];
            objArr[0] = DateTimeUtils.dateToHourMinute(response.getDate(), null);
            remoteViews.setTextViewText(R.id.widget_refresh_time, getString(2131689866, objArr));
            this.mRemoteViews.setTextViewText(R.id.widget_weather_des, currentWeather.getWeatherText(this));
            this.mRemoteViews.setTextViewText(R.id.widget_weather_temp, TemperatureUtil.getCurrentTemperature(this, response.getTodayCurrentTemp()));
            int highTemp = response.getTodayHighTemperature();
            int lowTemp = response.getTodayLowTemperature();
            Log.d(TAG, "highTemp:" + highTemp);
            Log.d(TAG, "lowTemp:" + lowTemp);
            String hTemp = TemperatureUtil.getHighTemperature(this, highTemp);
            this.mRemoteViews.setTextViewText(R.id.widget_high_low_temp, hTemp.replace("\u00b0", StringUtils.EMPTY_STRING) + " / " + TemperatureUtil.getHighTemperature(this, lowTemp));
            try {
                this.mRemoteViews.setImageViewResource(R.id.widget_bkg, WeatherTypeUtil.getWidgetWeatherTypeResID(WeatherResHelper.weatherToResID(this, response.getCurrentWeatherId()), cityData.isDay(response)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (response.getDailyForecastsWeather() != null && response.getDailyForecastsWeather().size() >= 3) {
            List<DailyForecastsWeather> dailyForecastsWeathers = new ArrayList();
            dailyForecastsWeathers.addAll(response.getDailyForecastsWeather());
            int realCurrentdate = DateTimeUtils.timeToDay(this, System.currentTimeMillis(), timeZone);
            Iterator<DailyForecastsWeather> iterator = dailyForecastsWeathers.iterator();
            while (iterator.hasNext()) {
                if (DateTimeUtils.timeToDay(this, ((DailyForecastsWeather) iterator.next()).getDate().getTime(), timeZone) <= realCurrentdate) {
                    iterator.remove();
                }
            }
            int loopCount = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
            if (dailyForecastsWeathers.size() < 3) {
                loopCount = dailyForecastsWeathers.size();
            }
            for (int i = 0; i < loopCount; i++) {
                DailyForecastsWeather weather = (DailyForecastsWeather) dailyForecastsWeathers.get(i);
                Calendar c = Calendar.getInstance();
                c.setTimeZone(TimeZone.getTimeZone("GMT" + timeZone));
                c.setTimeInMillis(weather.getDate().getTime());
                String day = DateTimeUtils.getDayString(this, c.get(7));
                if (i == 0) {
                    this.mRemoteViews.setTextViewText(2131296359, day);
                    this.mRemoteViews.setImageViewResource(2131296361, WeatherResHelper.getWeatherIconResID(WeatherResHelper.weatherToResID(this, weather.getDayWeatherId())));
                    this.mRemoteViews.setTextViewText(2131296362, TemperatureUtil.getHighTemperature(this, (int) weather.getMaxTemperature().getCentigradeValue()).replace("\u00b0", StringUtils.EMPTY_STRING) + " / " + TemperatureUtil.getHighTemperature(this, (int) weather.getMinTemperature().getCentigradeValue()));
                }
                if (i == 1) {
                    this.mRemoteViews.setTextViewText(2131296363, day);
                    this.mRemoteViews.setImageViewResource(2131296365, WeatherResHelper.getWeatherIconResID(WeatherResHelper.weatherToResID(this, weather.getDayWeatherId())));
                    this.mRemoteViews.setTextViewText(2131296366, TemperatureUtil.getHighTemperature(this, (int) weather.getMaxTemperature().getCentigradeValue()).replace("\u00b0", StringUtils.EMPTY_STRING) + " / " + TemperatureUtil.getHighTemperature(this, (int) weather.getMinTemperature().getCentigradeValue()));
                }
                if (i == 2) {
                    this.mRemoteViews.setTextViewText(2131296367, day);
                    this.mRemoteViews.setImageViewResource(2131296369, WeatherResHelper.getWeatherIconResID(WeatherResHelper.weatherToResID(this, weather.getDayWeatherId())));
                    this.mRemoteViews.setTextViewText(2131296370, TemperatureUtil.getHighTemperature(this, (int) weather.getMaxTemperature().getCentigradeValue()).replace("\u00b0", StringUtils.EMPTY_STRING) + " / " + TemperatureUtil.getHighTemperature(this, (int) weather.getMinTemperature().getCentigradeValue()));
                }
            }
        }
        this.mRemoteViews.setOnClickPendingIntent(R.id.widget_refresh, getRefreshPendingIntent(this, widgetId));
        this.mRemoteViews.setOnClickPendingIntent(R.id.weather_widget, getClickPendingIntent(this, widgetId));
        sAppWidgetManager.updateAppWidget(widgetId, this.mRemoteViews);
    }

    private PendingIntent getClickPendingIntent(Context context, int widgetId) {
        Intent intentClick = getPackageManager().getLaunchIntentForPackage(getPackageName());
        intentClick.putExtra(WIDGET_ID, widgetId);
        intentClick.setFlags(268468224);
        return PendingIntent.getActivity(context, widgetId, intentClick, 134217728);
    }

    private PendingIntent getRefreshPendingIntent(Context context, int widgetId) {
        Intent refreshIntent = new Intent(context, WidgetReceiver.class);
        refreshIntent.putExtra(NEED_REFRESH, true);
        refreshIntent.putExtra(WIDGET_ID, widgetId);
        refreshIntent.setAction(ACTION_REFRESH);
        return PendingIntent.getBroadcast(context, widgetId, refreshIntent, 134217728);
    }

    private void setWidgetFail(int widgetId) {
        this.mRemoteViews.setViewVisibility(R.id.weather_widget, DetectedActivity.RUNNING);
        this.mRemoteViews.setViewVisibility(R.id.widget_refreshing_group, 0);
        this.mRemoteViews.setTextViewText(R.id.widget_refreshing_text, getString(R.string.widget_refresh_fail));
        this.mRemoteViews.setOnClickPendingIntent(R.id.widget_refreshing_bar, getRefreshPendingIntent(this, widgetId));
        sAppWidgetManager.updateAppWidget(widgetId, this.mRemoteViews);
    }

    private void setWidgetFailException(int widgetId) {
        this.mRemoteViews.setViewVisibility(R.id.weather_widget, DetectedActivity.RUNNING);
        this.mRemoteViews.setViewVisibility(R.id.widget_refreshing_group, 0);
        this.mRemoteViews.setImageViewResource(R.id.widget_bkg, R.drawable.widget_bkg_sunny);
        this.mRemoteViews.setImageViewResource(R.id.widget_refreshing_bar, R.drawable.ic_add_city);
        this.mRemoteViews.setTextViewText(R.id.widget_refreshing_text, getString(R.string.widget_refresh_fail_add));
        this.mRemoteViews.setTextViewText(R.id.widget_refreshing_city, getString(R.string.app_name));
        Intent chooseIntent = new Intent(this, CityListActivity.class);
        chooseIntent.putExtra(NEED_REFRESH, true);
        chooseIntent.putExtra("appWidgetId", widgetId);
        chooseIntent.setFlags(268468224);
        this.mRemoteViews.setOnClickPendingIntent(R.id.widget_refreshing_bar, PendingIntent.getActivity(this, widgetId, chooseIntent, 134217728));
        sAppWidgetManager.updateAppWidget(widgetId, this.mRemoteViews);
    }

    public void setCityByID(Context context, CityData cityData) {
        if (cityData == null) {
            throw new IllegalArgumentException("cityData can't empty");
        }
        String locationId = cityData.getLocationId();
        PreferenceUtils.applyString(context, locationId + "city_name", cityData.getName());
        PreferenceUtils.applyString(context, locationId + "city_localname", cityData.getLocalName());
        PreferenceUtils.applyInt(context, locationId + "city_provider", cityData.getProvider());
        PreferenceUtils.applyString(context, locationId + "city_locationid", cityData.getLocationId());
    }

    public CityData getCityByID(Context context, int locationId) {
        CityData city = new CityData();
        city.setName(PreferenceUtils.getString(context, locationId + "city_name", getString(R.string.current_location)));
        city.setLocalName(PreferenceUtils.getString(context, locationId + "city_localname", getString(R.string.current_location)));
        city.setProvider(PreferenceUtils.getInt(context, locationId + "city_provider", -1));
        city.setLocationId(PreferenceUtils.getString(context, locationId + "city_locationid", "-1"));
        return city;
    }
}
