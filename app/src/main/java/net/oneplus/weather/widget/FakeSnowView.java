package net.oneplus.weather.widget;

import android.content.Context;
import android.util.AttributeSet;

public class FakeSnowView extends BaseWeatherView {
    private int mDescription;

    public FakeSnowView(Context context) {
        this(context, null);
    }

    public FakeSnowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FakeSnowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDescription = -1;
        init();
    }

    public void startAnimate() {
    }

    public void stopAnimate() {
    }

    public void onPageSelected(boolean isCurrent) {
    }

    public void setDescription(int description) {
        this.mDescription = description;
        init();
    }

    private void init() {
    }

    protected void onCreateOrientationInfoListener() {
    }
}
