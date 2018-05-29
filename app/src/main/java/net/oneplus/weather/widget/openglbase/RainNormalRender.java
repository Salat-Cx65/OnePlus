package net.oneplus.weather.widget.openglbase;

import android.content.Context;

public class RainNormalRender extends RainBaseRender {
    public RainNormalRender(Context context, boolean day) {
        super(context, day);
        this.mRain = new RainNormal();
        this.SPEED = -0.65f;
        this.z = (float) ((-Z_RANDOM_RANGE) + 2);
    }
}
