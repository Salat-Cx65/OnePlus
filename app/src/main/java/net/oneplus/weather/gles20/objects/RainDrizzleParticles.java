package net.oneplus.weather.gles20.objects;

import android.content.Context;
import net.oneplus.weather.gles20.util.Geometry.Vector;

public class RainDrizzleParticles extends RainParticles {
    public RainDrizzleParticles(Vector direction, int color1, int color2, Context mContext) {
        super(direction, color1, color2, mContext);
        this.numLines = 100;
        this.numRect = 0;
        this.LINE_HEIGHT_START_OFFSET = 0.53333336f;
        this.LINE_WIDTH_START_OFFSET = 0.2f;
        this.HEIGHT_CHANGE_RANGE = 0.08f;
        this.WIDTH_CHANGE_RANGE = 3.5f;
        this.RECT_HEIGHT_START_OFFSET = 1.5f;
        this.RECT_WIDTH_START_OFFSET = 0.01f;
        this.RAIN_SPEED = 0.045f;
        this.MAX_RAIN_DROPS_COUNT = 150;
        this.numOfGroup = 5;
        init(direction);
    }
}
