package net.oneplus.weather.widget.openglbase;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.widget.ListPopupWindow;
import android.util.AttributeSet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.util.OrientationSensorUtil;
import net.oneplus.weather.util.OrientationSensorUtil.OrientationInfoListener;
import net.oneplus.weather.widget.AbsWeather;

public abstract class OPGLSurfaceView extends GLSurfaceView implements AbsWeather {
    private boolean mDay;
    protected OrientationInfoListener mListener;
    protected BaseGLRenderer mRenderer;

    protected abstract void onCreateOrientationInfoListener();

    public OPGLSurfaceView(Context context, AttributeSet attrs, boolean day) {
        super(context, attrs);
        onCreateOrientationInfoListener();
        this.mDay = day;
        setZOrderOnTop(true);
        getHolder().setFormat(ListPopupWindow.WRAP_CONTENT);
        setEGLConfigChooser(DetectedActivity.RUNNING, 8, 8, 8, ConnectionResult.API_UNAVAILABLE, 0);
    }

    public OPGLSurfaceView(Context context, boolean day) {
        this(context, null, day);
        onCreateOrientationInfoListener();
    }

    public void setDay(boolean day) {
        this.mDay = day;
        if (this.mRenderer != null) {
            this.mRenderer.setDay(day);
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

    protected void onDetachedFromWindow() {
        OrientationSensorUtil.removeOrientationInfoListener(this.mListener);
        super.onDetachedFromWindow();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        OrientationSensorUtil.addOrientationInfoListener(this.mListener);
    }
}
