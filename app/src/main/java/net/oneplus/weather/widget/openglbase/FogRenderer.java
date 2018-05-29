package net.oneplus.weather.widget.openglbase;

import javax.microedition.khronos.opengles.GL10;
import net.oneplus.weather.widget.anim.BaseAnimation;
import net.oneplus.weather.widget.anim.FogAnimation;
import net.oneplus.weather.widget.shap.Fog;
import net.oneplus.weather.widget.shap.IShap;
import net.oneplus.weather.widget.shap.Icosahedron;

public class FogRenderer extends BaseGLRenderer {
    IShap fog;
    Icosahedron icosahedron;
    BaseAnimation mWeatherAnimation;

    public FogRenderer(boolean day) {
        super(day);
        this.mWeatherAnimation = new FogAnimation();
        this.fog = new Fog();
        this.fog.setDay(day);
    }

    public void onSurfaceChangedLoaded(GL10 gl, int width, int height, float minX, float maxX, float minY, float maxY, float minZ, float maxZ) {
        BaseAnimation.setCenterXYZ((maxX + minX) / 2.0f, (maxY + minY) / 2.0f, (maxZ + minZ) / 2.0f);
        BaseAnimation.setRange(2.0f * maxX, maxY - minY, maxZ - minZ);
        this.fog.setDeadLine(minX * 10.0f, maxX * 10.0f, Float.MIN_VALUE, 10.0f * minY);
        this.fog.onCreate();
    }

    public void onDrawing(GL10 gl) {
        this.fog.draw(gl);
    }

    public void setAlpha(float alpha) {
        this.fog.setAlpha(alpha);
    }
}
