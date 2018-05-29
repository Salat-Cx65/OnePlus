package net.oneplus.weather.widget.shap;

import javax.microedition.khronos.opengles.GL10;
import net.oneplus.weather.widget.anim.BaseAnimation;
import net.oneplus.weather.widget.anim.FogAnimation;

public class Fog extends BaseShape {
    private static final int COUNT = 400;
    private FogParticle[] mObjects;

    public Fog() {
        this.mObjects = new FogParticle[400];
    }

    public void setDeadLine() {
    }

    public void onCreate() {
        for (int i = 0; i < 400; i++) {
            float[] p = BaseAnimation.orginXYZ();
            this.mObjects[i] = new FogParticle(p[0], p[1], p[2]);
            float[][] c = FogAnimation.randomColor();
            this.mObjects[i].setDay(isDay());
            this.mObjects[i].setColor(c[0][0], c[0][1], c[0][2], getAlpha());
            this.mObjects[i].setNightColor(c[1][0], c[1][1], c[1][2], getAlpha());
            this.mObjects[i].setDeadLine(getDeadLineLeftX(), getDeadLineRightX(), getDeadLineUpY(), getDeadLineDownY());
        }
    }

    public void draw(GL10 gl) {
        for (int i = 0; i < 400; i++) {
            this.mObjects[i].setAlpha(getAlpha());
            this.mObjects[i].draw(gl);
            this.mObjects[i].move();
            if (this.mObjects[i].dead()) {
                float[] p = BaseAnimation.nextXYZ();
                this.mObjects[i].setXYZ(p[0], p[1], p[2]);
                this.mObjects[i].init(p[0], p[1], p[2]);
                this.mObjects[i].setDay(isDay());
                float[][] c = FogAnimation.randomColor();
                this.mObjects[i].setColor(c[0][0], c[0][1], c[0][2], getAlpha());
                this.mObjects[i].setNightColor(c[1][0], c[1][1], c[1][2], getAlpha());
            }
        }
    }
}
