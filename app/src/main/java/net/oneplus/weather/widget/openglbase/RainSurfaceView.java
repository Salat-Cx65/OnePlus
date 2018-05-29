package net.oneplus.weather.widget.openglbase;

import android.content.Context;
import net.oneplus.weather.util.OrientationSensorUtil.OrientationInfoListener;

public class RainSurfaceView extends OPGLSurfaceView {
    public static final int RAIN_LEVEL_DEFAULT = -1;
    public static final int RAIN_LEVEL_DOWNPOUR = 3;
    public static final int RAIN_LEVEL_DRIZZLE = 0;
    public static final int RAIN_LEVEL_NORMAL_RAIN = 1;
    public static final int RAIN_LEVEL_RAINSTORM = 4;
    public static final int RAIN_LEVEL_SHOWER = 2;
    public static final int RAIN_LEVEL_THUNDERSHOWER = 5;
    private int rainLevel;

    public RainSurfaceView(Context context, int rainLevel, boolean day) {
        super(context, null, day);
        this.rainLevel = -1;
        this.rainLevel = rainLevel;
        init(context, day);
    }

    private void init(Context context, boolean isDay) {
        this.mRenderer = getRainRender(context, isDay);
        setRenderer(this.mRenderer);
        requestFocus();
        setFocusableInTouchMode(true);
    }

    private RainBaseRender getRainRender(Context context, boolean isDay) {
        switch (this.rainLevel) {
            case RAIN_LEVEL_DRIZZLE:
                return new RainDrizzleRender(context, isDay);
            case RAIN_LEVEL_NORMAL_RAIN:
                return new RainNormalRender(context, isDay);
            case RAIN_LEVEL_SHOWER:
                return new RainShowerRender(context, isDay);
            case RAIN_LEVEL_DOWNPOUR:
                return new RainDownpourRender(context, isDay);
            case RAIN_LEVEL_RAINSTORM:
                return new RainStormRender(context, isDay);
            case RAIN_LEVEL_THUNDERSHOWER:
                return new RainThunderShowerRender(context, isDay);
            default:
                return new RainStormRender(context, isDay);
        }
    }

    public void setZAngle(float lr) {
        this.mRenderer.setAngleZ(lr);
    }

    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        this.mRenderer.setAlpha(alpha);
    }

    public void setXAngle(float x) {
        float ang;
        if (x <= -180.0f || x >= 90.0f) {
            ang = 270.0f - x;
        } else {
            ang = (-x) - 90.0f;
        }
        this.mRenderer.setAngleX(ang);
    }

    public void startAnimate() {
        setRenderMode(RAIN_LEVEL_NORMAL_RAIN);
        this.mRenderer.setAnimEnable(true);
    }

    public void stopAnimate() {
        setRenderMode(RAIN_LEVEL_DRIZZLE);
        this.mRenderer.setAnimEnable(false);
    }

    public void setRainLevel(int level) {
        this.rainLevel = level;
    }

    public void onPageSelected(boolean isCurrent) {
    }

    protected void onCreateOrientationInfoListener() {
        this.mListener = new OrientationInfoListener() {
            public void onOrientationInfoChange(float x, float y, float z) {
                RainSurfaceView.this.setXAngle(y);
                RainSurfaceView.this.setZAngle(z);
            }
        };
    }
}
