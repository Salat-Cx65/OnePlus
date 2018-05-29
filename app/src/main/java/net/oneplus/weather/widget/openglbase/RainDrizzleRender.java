package net.oneplus.weather.widget.openglbase;

import android.content.Context;

public class RainDrizzleRender extends RainBaseRender {
    public RainDrizzleRender(Context context, boolean day) {
        super(context, day);
        this.mRain = new RainDrizzle();
        this.SPEED = -0.5f;
    }
}
