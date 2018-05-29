package net.oneplus.weather.widget.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import net.oneplus.weather.util.WeatherLog;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class WidgetReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action)) {
            int i;
            switch (action.hashCode()) {
                case 1540861290:
                    if (action.equals(WidgetHelper.ACTION_REFRESH)) {
                        boolean z = false;
                    }
                    i = -1;
                    break;
                default:
                    i = -1;
                    break;
            }
            switch (i) {
                case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                    int id = intent.getIntExtra(WidgetHelper.WIDGET_ID, -1);
                    boolean isForce = intent.getBooleanExtra(WidgetHelper.NEED_REFRESH, false);
                    if (id != -1) {
                        WidgetHelper.getInstance(context).updateWidgetById(id, isForce);
                    } else {
                        WeatherLog.e("id is -1");
                    }
                default:
                    break;
            }
        }
    }
}
