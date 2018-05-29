package net.oneplus.weather.app;

import android.content.Intent;
import android.os.Bundle;
import com.oneplus.lib.preference.Preference;
import com.oneplus.lib.preference.Preference.OnPreferenceChangeListener;
import com.oneplus.lib.preference.Preference.OnPreferenceClickListener;
import com.oneplus.lib.widget.preference.OPListPreference;
import com.oneplus.lib.widget.preference.OPPreferenceActivity;
import com.oneplus.lib.widget.preference.OPSwitchPreference;
import net.oneplus.weather.R;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.util.SystemSetting;
import net.oneplus.weather.widget.widget.WidgetHelper;

public class SettingPreferenceActivity extends OPPreferenceActivity implements OnPreferenceChangeListener {
    private static final String PREFS_NAME_ABOUT = "settings_preference_about";
    private static final String PREFS_NAME_HUMIDITY = "settings_preference_humidity";
    private static final String PREFS_NAME_TEMPERATURE = "settings_preference_temperature";
    private static final String PREFS_NAME_WARN = "settings_preference_warn";
    private OPListPreference mHumidityPreference;
    private OPListPreference mTemperaturePreference;
    private OPSwitchPreference mWarnPreference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle(R.string.title_settings);
        addPreferencesFromResource(R.xml.setting_preference);
        initPreferences();
    }

    private void initPreferences() {
        this.mTemperaturePreference = (OPListPreference) findPreference(PREFS_NAME_TEMPERATURE);
        this.mHumidityPreference = (OPListPreference) findPreference(PREFS_NAME_HUMIDITY);
        this.mWarnPreference = (OPSwitchPreference) findPreference(PREFS_NAME_WARN);
        findPreference(PREFS_NAME_ABOUT).setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                SettingPreferenceActivity.this.startActivity(new Intent(SettingPreferenceActivity.this, AboutActivity.class));
                SettingPreferenceActivity.this.overridePendingTransition(R.anim.citylist_translate_up, R.anim.alpha_out);
                return true;
            }
        });
        this.mTemperaturePreference.setOnPreferenceChangeListener(this);
        this.mHumidityPreference.setOnPreferenceChangeListener(this);
        this.mWarnPreference.setOnPreferenceChangeListener(this);
        this.mTemperaturePreference.setSummary(getTemperatureValues(SystemSetting.getTemperature(this)));
        this.mHumidityPreference.setSummary(getHumidityValues(SystemSetting.getHumidity(this)));
        this.mWarnPreference.setChecked(SystemSetting.isWeatherWarningEnabled(this));
        this.mTemperaturePreference.setValue(SystemSetting.getTemperature(this) + StringUtils.EMPTY_STRING);
        this.mHumidityPreference.setValue(SystemSetting.getHumidity(this) + StringUtils.EMPTY_STRING);
        this.mTemperaturePreference.setDialogTitle(getString(R.string.rtl_flag) + getString(R.string.humidity));
        this.mTemperaturePreference.setEntries(new CharSequence[]{getString(R.string.rtl_flag) + getString(R.string.celsius), getString(R.string.rtl_flag) + getString(R.string.fahrenheit)});
        this.mHumidityPreference.setDialogTitle(getString(R.string.rtl_flag) + getString(R.string.humidity));
        this.mHumidityPreference.setEntries(new CharSequence[]{getString(R.string.rtl_flag) + getString(R.string.relative_humidity), getString(R.string.rtl_flag) + getString(R.string.absolute_humidity)});
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (newValue == null) {
            return false;
        }
        boolean isChecked = Boolean.parseBoolean(newValue.toString());
        if (preference.getKey().equals(PREFS_NAME_TEMPERATURE)) {
            SystemSetting.notifyWeatherDataChange(this);
            SystemSetting.setTemperature(this, isChecked);
            this.mTemperaturePreference.setSummary(getTemperatureValues(isChecked));
            WidgetHelper.getInstance(this).updateAllWidget(false);
        } else if (preference.getKey().equals(PREFS_NAME_HUMIDITY)) {
            SystemSetting.setHumidity(this, isChecked);
            this.mHumidityPreference.setSummary(getHumidityValues(isChecked));
        } else if (preference.getKey().equals(PREFS_NAME_WARN)) {
            SystemSetting.setWeatherWarningEnabled(this, isChecked);
        }
        return true;
    }

    private String getTemperatureValues(boolean isCheck) {
        return isCheck ? getString(R.string.celsius) : getString(R.string.fahrenheit);
    }

    private String getHumidityValues(boolean isCheck) {
        return isCheck ? "%" + getString(R.string.relative_humidity) : getString(R.string.absolute_humidity);
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
