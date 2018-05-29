package net.oneplus.weather.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import net.oneplus.weather.R;
import net.oneplus.weather.util.PreferenceUtils;

public class MockLocation extends Activity {
    public static final String GPS_FIRST_SWITCH_KEY = "gps_first";
    private Switch mSwitch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_activity);
        this.mSwitch = (Switch) findViewById(R.id.gps_first_switch);
        this.mSwitch.setChecked(PreferenceUtils.getBoolean(this, GPS_FIRST_SWITCH_KEY));
        this.mSwitch.setOnCheckedChangeListener(new MockLocation$$Lambda$0(this));
    }

    final /* synthetic */ void lambda$onCreate$0$MockLocation(CompoundButton buttonView, boolean isChecked) {
        PreferenceUtils.applyBoolean(this, GPS_FIRST_SWITCH_KEY, isChecked);
    }
}
