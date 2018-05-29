package net.oneplus.weather.widget;

import android.content.Context;
import android.util.AttributeSet;

public class SandStormView extends BaseWeatherView {
    public SandStormView(Context context) {
        this(context, null);
    }

    public SandStormView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SandStormView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void startAnimate() {
    }

    public void stopAnimate() {
    }

    public void onPageSelected(boolean isCurrent) {
    }

    private void init() {
        setBackgroundResource(R.drawable.bkg_sandstorm);
    }

    protected void onCreateOrientationInfoListener() {
    }
}
