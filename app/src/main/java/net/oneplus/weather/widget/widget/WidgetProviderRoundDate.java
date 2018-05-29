package net.oneplus.weather.widget.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import net.oneplus.weather.R;
import net.oneplus.weather.api.WeatherException;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.app.MainActivity;
import net.oneplus.weather.model.CityData;
import net.oneplus.weather.util.DateTimeUtils;
import net.oneplus.weather.util.SystemSetting;
import net.oneplus.weather.util.WeatherClientProxy;
import net.oneplus.weather.util.WeatherClientProxy.CacheMode;
import net.oneplus.weather.util.WeatherClientProxy.OnResponseListener;
import net.oneplus.weather.util.WeatherResHelper;

public class WidgetProviderRoundDate extends AppWidgetProvider {
    public static final String CLOCK_TICK_ACTION = "net.oneplus.weather.widget.CLOCK_TICK";
    private static final int REQUEST_CODE = 1000112;
    private static CityData mCityData;
    private final String APP_AUTO_UPDATE;

    class AnonymousClass_1 implements OnResponseListener {
        final /* synthetic */ Context val$context;

        AnonymousClass_1(Context context) {
            this.val$context = context;
        }

        public void onNetworkResponse(RootWeather response) {
            Log.e("WidgetProviderRound", "refreshData onNetworkResponse");
        }

        public void onErrorResponse(WeatherException exception) {
            Log.e("WidgetProviderRound", "refreshData error" + exception.toString());
        }

        public void onCacheResponse(RootWeather response) {
            if (response != null) {
                mCityData.setWeathers(response);
                WidgetProviderRoundDate.this.updateAppWidgetUi(this.val$context);
            }
        }
    }

    public WidgetProviderRoundDate() {
        this.APP_AUTO_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(WidgetTypeUtil.ACTION_APPWIDGET_REFRESH) || intent.getAction().equals(CLOCK_TICK_ACTION) || intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")) {
            refreshData(context);
        }
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, new RemoteViews(context.getPackageName(), 2131492994));
        }
    }

    public void onEnabled(Context context) {
        super.onEnabled(context);
        new Properties().setProperty("date", "start");
        refreshData(context);
        registerAlarm(context);
    }

    public void onDisabled(Context context) {
        new Properties().setProperty("date", "stop");
        unregisterAlarm(context);
        super.onDisabled(context);
    }

    private void refreshData(Context context) {
        mCityData = SystemSetting.getLocationOrDefaultCity(context);
        if (mCityData != null && !TextUtils.isEmpty(mCityData.getLocationId()) && !mCityData.getLocationId().equals("0")) {
            new WeatherClientProxy(context).setCacheMode(CacheMode.LOAD_CACHE_ONLY).requestWeatherInfo(mCityData, new AnonymousClass_1(context));
        }
    }

    private void updateAppWidgetUi(Context context) {
        AppWidgetManager appWidgetManger = AppWidgetManager.getInstance(context);
        for (int appWidgetId : appWidgetManger.getAppWidgetIds(new ComponentName(context, WidgetProviderRoundDate.class))) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), 2131492995);
            updateDate(context, rv);
            if (mCityData != null && mCityData.getWeathers() != null && mCityData.getWeathers() != null) {
                updateWeather(context, rv);
            }
            updateAppWidget(context, appWidgetManger, appWidgetId, rv);
        }
    }

    private void registerAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(ConnectionResult.TIMEOUT, 0);
        calendar.add(ConnectionResult.CANCELED, R.styleable.AppCompatTheme_dialogTheme);
        calendar.set(ConnectionResult.CANCELED, 0);
        alarmManager.setRepeating(1, calendar.getTimeInMillis(), DateTimeUtils.MINUTE, createClockTickIntent(context));
        context.getApplicationContext().getPackageManager().setComponentEnabledSetting(new ComponentName(context, WidgetProviderRoundDate.class), 1, 1);
    }

    private void unregisterAlarm(Context context) {
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(createClockTickIntent(context));
        context.getApplicationContext().getPackageManager().setComponentEnabledSetting(new ComponentName(context, WidgetProviderRoundDate.class), 0, 1);
    }

    private PendingIntent createClockTickIntent(Context context) {
        return PendingIntent.getBroadcast(context, 0, new Intent(CLOCK_TICK_ACTION), 0);
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgeManger, int appWidgetId, RemoteViews rv) {
        Intent intentClick = new Intent(context, MainActivity.class);
        intentClick.setFlags(268435456);
        rv.setOnClickPendingIntent(R.id.root, PendingIntent.getActivity(context, REQUEST_CODE, intentClick, 134217728));
        appWidgeManger.updateAppWidget(appWidgetId, rv);
    }

    private void updateDate(Context context, RemoteViews rv) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfTime = new SimpleDateFormat(context.getString(R.string.widget_time_format), Locale.getDefault());
        SimpleDateFormat sdfdate = new SimpleDateFormat(context.getString(R.string.widget_date_format), Locale.getDefault());
        String time = sdfTime.format(calendar.getTime());
        String date = sdfdate.format(calendar.getTime());
        String week = calendar.getDisplayName(DetectedActivity.WALKING, 1, Locale.getDefault());
        getDateView(context, rv, time);
        rv.setTextViewText(R.id.widget_round_tv_date, date);
        rv.setTextViewText(R.id.widget_round_tv_week, week);
    }

    private void getDateView(Context context, RemoteViews rv, String time) {
        int i = 0;
        while (i < time.length() && i < 5) {
            if (i != 2) {
                rv.setImageViewResource(context.getResources().getIdentifier("number_iv_" + i, "id", context.getPackageName()), context.getResources().getIdentifier("widget_number_" + time.charAt(i), "drawable", context.getPackageName()));
            }
            i++;
        }
    }

    private void updateWeather(Context context, RemoteViews rv) {
        RootWeather weather = mCityData.getWeathers();
        String cityname = mCityData.getLocalName();
        String weatherName = weather.getCurrentWeatherText(context);
        int currentTemper = weather.getTodayCurrentTemp();
        rv.setTextViewText(R.id.widget_round_tv_city, cityname);
        rv.setTextViewText(R.id.widget_round_tv_type, weatherName);
        rv.setTextViewText(R.id.widget_round_tv_temper, String.valueOf(currentTemper) + "\u00b0");
        int weatherResID = -1;
        try {
            weatherResID = WidgetTypeUtil.getWidgetWeatherTypeResID(WeatherResHelper.weatherToResID(context, weather.getCurrentWeatherId()), mCityData.isDay(weather));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        rv.setImageViewResource(R.id.weather_type_background, weatherResID);
    }
}
