package net.oneplus.weather.widget.openglbase;

import android.content.Context;

public class RainStormRender extends RainBaseRender {
    public RainStormRender(Context context, boolean day) {
        super(context, day);
        this.mRain = new RainStorm();
        this.SPEED = -1.7f;
        this.z = (float) ((-Z_RANDOM_RANGE) + 2);
    }
}
