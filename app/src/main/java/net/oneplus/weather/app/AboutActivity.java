package net.oneplus.weather.app;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.DetectedActivity;
import com.oneplus.lib.widget.button.OPButton;
import net.oneplus.weather.R;
import net.oneplus.weather.app.EggDetector.OnEggListerner;
import net.oneplus.weather.model.WeatherDescription;
import net.oneplus.weather.util.GpsUtils;
import net.oneplus.weather.util.StringUtils;

public class AboutActivity extends BaseBarActivity implements OnEggListerner {
    private static final int SHOW_WEATHER_COUNT = 10;
    private static final int SHOW_WEATHER_SPAN = 400;
    private static final String TAG;
    private EggDetector mCaiDanDetector;
    private int mCurrentCount;
    private Handler mHandler;
    private ImageView mIcon;
    private long mLastClick;

    public AboutActivity() {
        this.mHandler = new Handler() {
            public void handleMessage(Message msg) {
                AboutActivity.this.mCurrentCount = 0;
            }
        };
    }

    static {
        TAG = AboutActivity.class.getSimpleName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        View findViewById = findViewById(16908290);
        View findViewById2 = findViewById(getResources().getIdentifier("action_bar_container", "id", "android"));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.company_info);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.weather_info);
        if (!GpsUtils.isH2OS()) {
            findViewById(R.id.about_origin_china).setVisibility(DetectedActivity.RUNNING);
        }
        setTitle(R.string.about);
        this.mCaiDanDetector = new EggDetector(this, 5);
        this.mCaiDanDetector.setOnEggListerner(this);
        ((OPButton) findViewById(R.id.about_user_agreement)).setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                AboutActivity.this.startActivity(new Intent(AboutActivity.this, CopyActivity.class));
                AboutActivity.this.overridePendingTransition(R.anim.citylist_translate_up, R.anim.alpha_out);
            }
        });
        String versionName = null;
        try {
            String[] versions = getPackageManager().getPackageInfo(getPackageName(), 0).versionName.split("\\.");
            versionName = StringUtils.EMPTY_STRING;
            if (versions.length >= 3) {
                for (int i = 0; i < 3; i++) {
                    if (i != 2) {
                        versionName = versionName + versions[i] + ".";
                    } else {
                        versionName = versionName + versions[i];
                    }
                }
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        ((TextView) findViewById(R.id.about_version)).setText(getString(R.string.current_version, new Object[]{versionName}));
    }

    public void onSuccess(String s) {
        Intent intent;
        if (s.equals("0110")) {
            intent = new Intent(this, ShowWeatherActivity.class);
            intent.putExtra("type", WeatherDescription.WEATHER_DESCRIPTION_HAZE);
            startActivity(intent);
        } else if (s.equals("1010")) {
            intent = new Intent(this, ShowWeatherActivity.class);
            intent.putExtra("type", WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER);
            startActivity(intent);
        } else if (s.equals("0010")) {
            intent = new Intent(this, ShowWeatherActivity.class);
            intent.putExtra("type", WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM);
            startActivity(intent);
        } else {
            String l = getString(R.string.psd_long);
            s = s.replaceAll("1", l).replaceAll("0", getString(R.string.psd_short));
            this.mIcon.clearAnimation();
            this.mIcon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.spring_from_bottom));
            Toast.makeText(this, getResources().getString(R.string.click_code_err, new Object[]{s}), 0).show();
        }
    }
}
