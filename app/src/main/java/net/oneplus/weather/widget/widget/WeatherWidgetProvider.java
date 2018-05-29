package net.oneplus.weather.widget.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;
import net.oneplus.weather.util.PreferenceUtils;

public class WeatherWidgetProvider extends AppWidgetProvider {
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        int length = appWidgetIds.length;
        for (int i = 0; i < length; i++) {
            WidgetHelper.getInstance(context).updateWidgetById(appWidgetIds[i], false);
        }
    }

    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        for (int widgetId : appWidgetIds) {
            PreferenceUtils.remove(context, WidgetHelper.WIDGET_ID_PREFIX + widgetId);
        }
    }

    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}
