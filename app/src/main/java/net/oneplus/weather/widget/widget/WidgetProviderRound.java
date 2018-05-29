package net.oneplus.weather.widget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import com.google.android.gms.location.DetectedActivity;
import java.util.Properties;

import net.oneplus.weather.api.WeatherException;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.app.MainActivity;
import net.oneplus.weather.db.CityWeatherDBHelper.WeatherEntry;
import net.oneplus.weather.model.CityData;
import net.oneplus.weather.util.SystemSetting;
import net.oneplus.weather.util.WeatherClientProxy;
import net.oneplus.weather.util.WeatherClientProxy.CacheMode;
import net.oneplus.weather.util.WeatherClientProxy.OnResponseListener;
import net.oneplus.weather.util.WeatherResHelper;

public class WidgetProviderRound extends AppWidgetProvider {
    private static final int REQUEST_CODE = 1000111;
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
                WidgetProviderRound.this.updateAppWidgetUi(this.val$context);
            }
        }
    }

    public WidgetProviderRound() {
        this.APP_AUTO_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(WidgetTypeUtil.ACTION_APPWIDGET_REFRESH) || intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")) {
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
        new Properties().setProperty(WeatherEntry.COLUMN_3_TEMPERATURE, "start");
        refreshData(context);
    }

    public void onDisabled(Context context) {
        new Properties().setProperty(WeatherEntry.COLUMN_3_TEMPERATURE, "stop");
        super.onDisabled(context);
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgeManger, int appWidgetId, RemoteViews rv) {
        Intent intentClick = new Intent(context, MainActivity.class);
        intentClick.setFlags(268435456);
        rv.setOnClickPendingIntent(R.id.root, PendingIntent.getActivity(context, REQUEST_CODE, intentClick, 134217728));
        appWidgeManger.updateAppWidget(appWidgetId, rv);
    }

    private void refreshData(Context context) {
        mCityData = SystemSetting.getLocationOrDefaultCity(context);
        if (mCityData != null && !TextUtils.isEmpty(mCityData.getLocationId()) && !mCityData.getLocationId().equals("0")) {
            new WeatherClientProxy(context).setCacheMode(CacheMode.LOAD_CACHE_ONLY).requestWeatherInfo(mCityData, new AnonymousClass_1(context));
        }
    }

    private void updateAppWidgetUi(Context context) {
        if (mCityData != null && mCityData.getWeathers() != null && mCityData.getWeathers() != null) {
            AppWidgetManager appWidgetManger = AppWidgetManager.getInstance(context);
            for (int appWidgetId : appWidgetManger.getAppWidgetIds(new ComponentName(context, WidgetProviderRound.class))) {
                RemoteViews rv = new RemoteViews(context.getPackageName(), 2131492994);
                RootWeather weather = mCityData.getWeathers();
                String name = mCityData.getLocalName();
                String weatherName = weather.getCurrentWeatherText(context);
                int currentTemper = weather.getTodayCurrentTemp();
                String lhTemper = weather.getTodayHighTemperature() + "\u00b0/" + weather.getTodayLowTemperature() + "\u00b0";
                rv.setTextViewText(2131296624, name);
                rv.setTextViewText(2131296626, weatherName);
                getDateView(context, rv, String.valueOf(currentTemper));
                rv.setTextViewText(2131296625, lhTemper);
                int weatherResID = -1;
                try {
                    weatherResID = WidgetTypeUtil.getWidgetWeatherTypeResID(WeatherResHelper.weatherToResID(context, weather.getCurrentWeatherId()), mCityData.isDay(weather));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                rv.setImageViewResource(2131296640, weatherResID);
                updateAppWidget(context, appWidgetManger, appWidgetId, rv);
            }
        }
    }

    private void getDateView(Context context, RemoteViews rv, String temper) {
        for (int i = 0; i < 3; i++) {
            int viewId = context.getResources().getIdentifier("number_iv_" + i, "id", context.getPackageName());
            if (i < temper.length()) {
                String s = String.valueOf(temper.charAt(i));
                if ("-".equals(s)) {
                    s = "subzero";
                }
                int resId = context.getResources().getIdentifier("widget_number_temper_" + s, "drawable", context.getPackageName());
                rv.setViewVisibility(viewId, 0);
                rv.setImageViewResource(viewId, resId);
            } else {
                rv.setViewVisibility(viewId, DetectedActivity.RUNNING);
            }
            if (temper.length() < 2) {
                rv.setViewPadding(R.id.widget_round_ll_temper, 0, 0, R.styleable.AppCompatTheme_buttonStyleSmall, 0);
            }
        }
    }
}
