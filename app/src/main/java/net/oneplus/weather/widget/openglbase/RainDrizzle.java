package net.oneplus.weather.widget.openglbase;

public class RainDrizzle extends Rain {
    public RainDrizzle() {
        this.numLines = 150;
        this.HEIGHT_CHANGE_RANGE = 1.5f;
        this.WIDTH_CHANGE_RANGE = 4.0f;
        this.RECT_WIDTH_CHANGE_RANGE = 1.5f;
        init();
    }
}
