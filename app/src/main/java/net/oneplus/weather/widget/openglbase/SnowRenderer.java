package net.oneplus.weather.widget.openglbase;

import javax.microedition.khronos.opengles.GL10;
import net.oneplus.weather.widget.anim.BaseAnimation;
import net.oneplus.weather.widget.anim.SnowAnimation;
import net.oneplus.weather.widget.shap.IShap;
import net.oneplus.weather.widget.shap.Icosahedron;
import net.oneplus.weather.widget.shap.Snow;

public class SnowRenderer extends BaseGLRenderer {
    Icosahedron icosahedron;
    BaseAnimation mWeatherAnimation;
    IShap snow;

    public SnowRenderer(boolean day) {
        super(day);
        this.mWeatherAnimation = new SnowAnimation();
        this.snow = new Snow();
        this.snow.setDay(day);
    }

    public void onSurfaceChangedLoaded(GL10 gl, int width, int height, float minX, float maxX, float minY, float maxY, float minZ, float maxZ) {
        BaseAnimation.setCenterXYZ((maxX + minX) / 2.0f, (maxY + minY) / 2.0f, (maxZ + minZ) / 2.0f);
        BaseAnimation.setRange(2.0f * maxX, maxY - minY, maxZ - minZ);
        this.snow.setDeadLine(minX * 10.0f, maxX * 10.0f, Float.MIN_VALUE, 10.0f * minY);
        this.snow.onCreate();
    }

    public void onDrawing(GL10 gl) {
        this.snow.draw(gl);
    }

    public void setAlpha(float alpha) {
        this.snow.setAlpha(alpha);
    }

    public void setDay(boolean day) {
        super.setDay(day);
        this.snow.setDay(day);
    }
}
