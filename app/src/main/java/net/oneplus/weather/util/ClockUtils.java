package net.oneplus.weather.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class ClockUtils {
    private static ClockUtils mClockUtils;
    private AlarmManager mAlarmManager;
    private Context mContext;

    public static synchronized ClockUtils getInstance(Context mContext) {
        ClockUtils clockUtils;
        synchronized (ClockUtils.class) {
            if (mClockUtils == null) {
                mClockUtils = new ClockUtils(mContext);
            }
            clockUtils = mClockUtils;
        }
        return clockUtils;
    }

    public ClockUtils(Context mContext) {
        this.mContext = mContext;
        if (this.mAlarmManager == null) {
            this.mAlarmManager = (AlarmManager) mContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
        }
    }

    public void setClock(PendingIntent intent, long startTime) {
        if (intent != null && this.mAlarmManager != null) {
            cancleClock(intent);
            this.mAlarmManager.set(RainSurfaceView.RAIN_LEVEL_SHOWER, startTime, intent);
        }
    }

    public void cancleClock(PendingIntent intent) {
        if (this.mAlarmManager != null && intent != null) {
            this.mAlarmManager.cancel(intent);
        }
    }

    public boolean isClockActive() {
        return SystemSetting.isWeatherAlarmActive(this.mContext);
    }

    public void setClockActive(boolean isActive) {
        SystemSetting.setWeatherAlarmActive(this.mContext, isActive);
    }
}
