package net.oneplus.weather.app.citylist;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import net.oneplus.weather.db.CityWeatherDB;

public class CityListHandler extends Handler {
    public static final int MESSAGE_DELETE_COMPLETE = -1;
    private Context mContext;

    public CityListHandler(Looper looper, Context context) {
        super(looper);
        this.mContext = context;
    }

    public void handleMessage(Message msg) {
        if (!hasMessages(msg.what)) {
            CityWeatherDB.getInstance(this.mContext).deleteCity(((Long) msg.obj).longValue());
        }
    }
}
