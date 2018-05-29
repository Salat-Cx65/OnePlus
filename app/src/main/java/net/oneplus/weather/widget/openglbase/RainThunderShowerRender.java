package net.oneplus.weather.widget.openglbase;

import android.content.Context;

public class RainThunderShowerRender extends RainBaseRender {
    public RainThunderShowerRender(Context context, boolean day) {
        super(context, day);
        this.mRain = new RainThunderShower();
        this.SPEED = -1.5f;
        this.z = (float) ((-Z_RANDOM_RANGE) + 2);
    }
}
