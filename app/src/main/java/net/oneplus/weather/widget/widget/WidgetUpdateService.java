package net.oneplus.weather.widget.widget;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import net.oneplus.weather.util.WeatherLog;

public class WidgetUpdateService extends IntentService {
    public WidgetUpdateService() {
        super("widgetupdateservice");
    }

    protected void onHandleIntent(@Nullable Intent intent) {
        WeatherLog.d("update use WidgetUpdateService");
        if (intent == null) {
            WeatherLog.e("intent is null");
            return;
        }
        int id = intent.getIntExtra(WidgetHelper.WIDGET_ID, -1);
        boolean needRefresh = intent.getBooleanExtra(WidgetHelper.NEED_REFRESH, false);
        if (id != -1) {
            WidgetHelper.getInstance(this).updateWidgetById(id, needRefresh);
        }
    }
}
