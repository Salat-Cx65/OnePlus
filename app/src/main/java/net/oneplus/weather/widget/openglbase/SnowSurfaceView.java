package net.oneplus.weather.widget.openglbase;

import android.content.Context;
import net.oneplus.weather.util.OrientationSensorUtil.OrientationInfoListener;

public class SnowSurfaceView extends OPGLSurfaceView {
    public SnowSurfaceView(Context context, boolean day) {
        super(context, day);
        this.mRenderer = new SnowRenderer(day);
        setRenderer(this.mRenderer);
        requestFocus();
        setFocusableInTouchMode(true);
        setRenderMode(1);
    }

    public void setZAngle(float lr) {
        this.mRenderer.setAngleZ(-lr);
    }

    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        this.mRenderer.setAlpha(alpha);
    }

    public void setXAngle(float x) {
        float angle;
        if (x <= -180.0f || x >= 90.0f) {
            angle = x - 270.0f;
        } else {
            angle = x + 90.0f;
        }
        this.mRenderer.setAngleX(angle);
    }

    public void startAnimate() {
        setRenderMode(1);
    }

    public void stopAnimate() {
        setRenderMode(0);
    }

    public void onPageSelected(boolean isCurrent) {
    }

    protected void onCreateOrientationInfoListener() {
        this.mListener = new OrientationInfoListener() {
            public void onOrientationInfoChange(float x, float y, float z) {
                SnowSurfaceView.this.setXAngle(y);
                SnowSurfaceView.this.setZAngle(z);
            }
        };
    }
}
