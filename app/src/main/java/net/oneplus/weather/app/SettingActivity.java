package net.oneplus.weather.app;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import java.text.DecimalFormat;

import net.oneplus.weather.app.SettingSwitch.OnChangedListener;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.util.SystemSetting;

public class SettingActivity extends BaseBarActivity implements OnChangedListener, OnCheckedChangeListener {
    private SettingSwitch swHumidity;
    private SettingSwitch swTemperature;
    private Switch swWarnTip;
    private SettingSwitch swWind;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        setBarTitle(R.string.title_settings);
        this.swTemperature = (SettingSwitch) findViewById(R.id.switchTemperature);
        this.swWind = (SettingSwitch) findViewById(R.id.switchWind);
        this.swHumidity = (SettingSwitch) findViewById(R.id.switchHumidity);
        this.swWarnTip = (Switch) findViewById(R.id.switchWarnTips);
        this.swTemperature.setChecked(SystemSetting.getTemperature(this));
        this.swWind.setChecked(SystemSetting.getWind(this));
        this.swHumidity.setChecked(SystemSetting.getHumidity(this));
        this.swWarnTip.setChecked(SystemSetting.isWeatherWarningEnabled(this));
        this.swTemperature.setOnChangedListener(this);
        this.swWind.setOnChangedListener(this);
        this.swHumidity.setOnChangedListener(this);
        this.swWarnTip.setOnCheckedChangeListener(this);
        findViewById(R.id.LayoutWarn).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SettingActivity.this.swWarnTip.setChecked(!SettingActivity.this.swWarnTip.isChecked());
            }
        });
        String versionName = null;
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        ((TextView) findViewById(R.id.textViewVersion)).setText(getString(R.string.current_version, new Object[]{versionName}));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        overridePendingTransition(R.anim.alpha_in, R.anim.citylist_translate_down);
        return true;
    }

    public void onClick(View view) {
        boolean z = true;
        boolean checkState;
        SettingSwitch settingSwitch;
        boolean z2;
        switch (view.getId()) {
            case R.id.LayoutHumidity:
                checkState = SystemSetting.getHumidity(this);
                settingSwitch = this.swHumidity;
                if (checkState) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                settingSwitch.setChecked(z2);
                if (checkState) {
                    z = false;
                }
                SystemSetting.setHumidity(this, z);
            case R.id.LayoutTemperature:
                checkState = SystemSetting.getTemperature(this);
                settingSwitch = this.swTemperature;
                if (checkState) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                settingSwitch.setChecked(z2);
                if (checkState) {
                    z = false;
                }
                SystemSetting.setTemperature(this, z);
            case R.id.LayoutWind:
                checkState = SystemSetting.getWind(this);
                settingSwitch = this.swWind;
                if (checkState) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                settingSwitch.setChecked(z2);
                if (checkState) {
                    z = false;
                }
                SystemSetting.setWind(this, z);
            case R.id.textView_about:
                startActivity(new Intent(this, AboutActivity.class));
                overridePendingTransition(R.anim.citylist_translate_up, R.anim.alpha_out);
            case R.id.textView_feedback:
                startActivity(new Intent(this, FeekbackActivity.class));
                overridePendingTransition(R.anim.citylist_translate_up, R.anim.alpha_out);
            default:
                break;
        }
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.alpha_in, R.anim.citylist_translate_down);
        super.onBackPressed();
    }

    public void OnChanged(SettingSwitch wiperSwitch, boolean checkState) {
        switch (wiperSwitch.getId()) {
            case R.id.switchHumidity:
                SystemSetting.setHumidity(this, checkState);
            case R.id.switchTemperature:
                SystemSetting.setTemperature(this, checkState);
            case R.id.switchWind:
                SystemSetting.setWind(this, checkState);
            default:
                break;
        }
    }

    public String formetFileSize(String size) {
        if (TextUtils.isEmpty(size)) {
            return "0K";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        long fileS = Long.parseLong(size);
        String str = StringUtils.EMPTY_STRING;
        if (fileS < 1024) {
            return df.format((double) fileS) + "B";
        }
        if (fileS < 1048576) {
            return df.format(((double) fileS) / 1024.0d) + "K";
        }
        return fileS < 1073741824 ? df.format(((double) fileS) / 1048576.0d) + "M" : df.format(((double) fileS) / 1.073741824E9d) + "G";
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SystemSetting.setWeatherWarningEnabled(this, isChecked);
    }
}
