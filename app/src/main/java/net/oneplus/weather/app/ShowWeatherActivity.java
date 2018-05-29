package net.oneplus.weather.app;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.Toast;

import net.oneplus.weather.model.WeatherDescription;
import net.oneplus.weather.util.UIUtil;
import net.oneplus.weather.util.WeatherViewCreator;
import net.oneplus.weather.widget.AbsWeather;

public class ShowWeatherActivity extends BaseActivity {
    private long mStartTime;
    private View mWeather;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtil.setWindowStyle(this);
        int type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            finish();
        }
        int alert = 0;
        int color = 0;
        switch (type) {
            case WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER:
                alert = R.string.rain_alert;
                color = R.color.weather_thunder_shower_rain;
                break;
            case WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM:
                alert = R.string.sand_alert;
                color = R.color.weather_dust;
                break;
            case WeatherDescription.WEATHER_DESCRIPTION_FOG:
                alert = R.string.fog_alert;
                color = R.color.weather_fog;
                break;
            case WeatherDescription.WEATHER_DESCRIPTION_HAZE:
                alert = R.string.haze_alert;
                color = R.color.weather_haze;
                break;
        }
        this.mWeather = (View) WeatherViewCreator.getViewFromDescription(this, type, true);
        this.mStartTime = System.currentTimeMillis();
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new LayoutParams(-1, -1));
        frameLayout.setBackgroundResource(color);
        ((AbsWeather) this.mWeather).startAnimate();
        frameLayout.addView(this.mWeather);
        setContentView(frameLayout);
        this.mWeather.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (System.currentTimeMillis() - ShowWeatherActivity.this.mStartTime > 10000) {
                    ShowWeatherActivity.this.finish();
                }
            }
        });
        Toast.makeText(this, alert, 1).show();
    }

    protected void onPause() {
        super.onPause();
        if (this.mWeather != null) {
            ((AbsWeather) this.mWeather).onViewPause();
        }
        if (this.mWeather != null && (this.mWeather instanceof GLSurfaceView)) {
            ((GLSurfaceView) this.mWeather).onPause();
        }
        if (this.mWeather != null) {
            ((AbsWeather) this.mWeather).onViewPause();
        }
    }

    protected void onStart() {
        super.onStart();
        if (this.mWeather != null) {
            ((AbsWeather) this.mWeather).onViewStart();
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.mWeather != null && (this.mWeather instanceof GLSurfaceView)) {
            ((GLSurfaceView) this.mWeather).onResume();
        }
    }
}
