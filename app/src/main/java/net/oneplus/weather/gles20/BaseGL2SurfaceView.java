package net.oneplus.weather.gles20;

import android.content.Context;
import android.opengl.GLSurfaceView;
import net.oneplus.weather.util.OrientationSensorUtil;
import net.oneplus.weather.util.OrientationSensorUtil.OrientationInfoListener;
import net.oneplus.weather.widget.AbsWeather;

public abstract class BaseGL2SurfaceView extends GLSurfaceView implements AbsWeather {
    protected OrientationInfoListener mListener;

    protected abstract void onCreateOrientationInfoListener();

    public BaseGL2SurfaceView(Context context) {
        super(context);
        onCreateOrientationInfoListener();
    }

    protected void onDetachedFromWindow() {
        OrientationSensorUtil.removeOrientationInfoListener(this.mListener);
        super.onDetachedFromWindow();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        OrientationSensorUtil.addOrientationInfoListener(this.mListener);
    }

    public void startAnimate() {
    }

    public void stopAnimate() {
    }

    public void onPageSelected(boolean isCurrent) {
    }

    public void setDay(boolean day) {
    }

    public void onViewPause() {
        OrientationSensorUtil.removeOrientationInfoListener(this.mListener);
    }

    public void onViewStart() {
        OrientationSensorUtil.addOrientationInfoListener(this.mListener);
    }
}
