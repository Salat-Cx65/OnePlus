package net.oneplus.weather.widget.swipelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

public class SpringInterpolator implements Interpolator {
    public SpringInterpolator(Context context, AttributeSet attrs) {
    }

    public float getInterpolation(float input) {
        float inner = (1.55f * input) - 1.1f;
        return 1.2f - (inner * inner);
    }
}
