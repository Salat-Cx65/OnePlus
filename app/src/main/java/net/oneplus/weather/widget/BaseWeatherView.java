package net.oneplus.weather.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import net.oneplus.weather.util.OrientationSensorUtil;
import net.oneplus.weather.util.OrientationSensorUtil.OrientationInfoListener;

public abstract class BaseWeatherView extends View implements AbsWeather {
    private boolean mDay;
    private int mDayBackgroundColor;
    protected OrientationInfoListener mListener;
    private int mNightBackgroundColor;

    protected abstract void onCreateOrientationInfoListener();

    public BaseWeatherView(Context context, boolean day) {
        super(context, null);
        onCreateOrientationInfoListener();
        this.mDay = day;
    }

    public BaseWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreateOrientationInfoListener();
    }

    public BaseWeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onCreateOrientationInfoListener();
    }

    public void setDayBackgroundColor(int color) {
        this.mDayBackgroundColor = color;
    }

    public void setNightBackgroundColor(int color) {
        this.mNightBackgroundColor = color;
    }

    protected void onDetachedFromWindow() {
        OrientationSensorUtil.removeOrientationInfoListener(this.mListener);
        super.onDetachedFromWindow();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        OrientationSensorUtil.addOrientationInfoListener(this.mListener);
    }

    public void setDay(boolean day) {
        if (isDay() != day) {
            this.mDay = day;
        }
    }

    public boolean isDay() {
        return this.mDay;
    }

    public void onViewPause() {
        OrientationSensorUtil.removeOrientationInfoListener(this.mListener);
    }

    public void onViewStart() {
        OrientationSensorUtil.addOrientationInfoListener(this.mListener);
    }
}
