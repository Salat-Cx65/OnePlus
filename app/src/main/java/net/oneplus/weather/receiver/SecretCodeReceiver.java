package net.oneplus.weather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import net.oneplus.weather.app.MainActivity;
import net.oneplus.weather.app.MockLocation;
import net.oneplus.weather.app.SettingPreferenceActivity;
import net.oneplus.weather.app.citylist.CityListActivity;

public class SecretCodeReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SECRET_CODE")) {
            Intent activeintent = new Intent();
            activeintent.setAction("android.intent.action.MAIN");
            activeintent.addFlags(268435456);
            if (intent.getData().toString().contains("1288")) {
                activeintent.setClass(context, CityListActivity.class);
            } else if (intent.getData().toString().contains("3345678")) {
                activeintent.setClass(context, SettingPreferenceActivity.class);
            } else if (intent.getData().toString().contains("55688")) {
                activeintent.setClass(context, MainActivity.class);
            } else if (intent.getData().toString().contains("33344")) {
                activeintent.setClass(context, MockLocation.class);
            }
            context.startActivity(activeintent);
        }
    }
}
