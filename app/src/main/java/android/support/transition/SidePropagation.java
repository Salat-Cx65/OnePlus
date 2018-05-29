package android.support.transition;

import android.graphics.Rect;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class SidePropagation extends VisibilityPropagation {
    private float mPropagationSpeed;
    private int mSide;

    public SidePropagation() {
        this.mPropagationSpeed = 3.0f;
        this.mSide = 80;
    }

    public void setSide(int side) {
        this.mSide = side;
    }

    public void setPropagationSpeed(float propagationSpeed) {
        if (propagationSpeed == 0.0f) {
            throw new IllegalArgumentException("propagationSpeed may not be 0");
        }
        this.mPropagationSpeed = propagationSpeed;
    }

    public long getStartDelay(ViewGroup sceneRoot, Transition transition, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null && endValues == null) {
            return 0;
        }
        TransitionValues positionValues;
        int epicenterX;
        int epicenterY;
        int directionMultiplier = 1;
        Rect epicenter = transition.getEpicenter();
        if (endValues == null || getViewVisibility(startValues) == 0) {
            positionValues = startValues;
            directionMultiplier = -1;
        } else {
            positionValues = endValues;
        }
        int viewCenterX = getViewX(positionValues);
        int viewCenterY = getViewY(positionValues);
        int[] loc = new int[2];
        sceneRoot.getLocationOnScreen(loc);
        int left = loc[0] + Math.round(sceneRoot.getTranslationX());
        int top = loc[1] + Math.round(sceneRoot.getTranslationY());
        int right = left + sceneRoot.getWidth();
        int bottom = top + sceneRoot.getHeight();
        if (epicenter != null) {
            epicenterX = epicenter.centerX();
            epicenterY = epicenter.centerY();
        } else {
            epicenterX = (left + right) / 2;
            epicenterY = (top + bottom) / 2;
        }
        float distanceFraction = ((float) distance(sceneRoot, viewCenterX, viewCenterY, epicenterX, epicenterY, left, top, right, bottom)) / ((float) getMaxDistance(sceneRoot));
        long duration = transition.getDuration();
        if (duration < 0) {
            duration = 300;
        }
        return (long) Math.round((((float) (((long) directionMultiplier) * duration)) / this.mPropagationSpeed) * distanceFraction);
    }

    private int distance(View sceneRoot, int viewX, int viewY, int epicenterX, int epicenterY, int left, int top, int right, int bottom) {
        int side;
        if (this.mSide == 8388611) {
            side = ViewCompat.getLayoutDirection(sceneRoot) == 1 ? RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER : RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
        } else if (this.mSide == 8388613) {
            side = ViewCompat.getLayoutDirection(sceneRoot) == 1 ? RainSurfaceView.RAIN_LEVEL_DOWNPOUR : RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
        } else {
            side = this.mSide;
        }
        switch (side) {
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return (right - viewX) + Math.abs(epicenterY - viewY);
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return (viewX - left) + Math.abs(epicenterY - viewY);
            case R.styleable.AppCompatTheme_colorAccent:
                return (bottom - viewY) + Math.abs(epicenterX - viewX);
            case R.styleable.AppCompatTheme_panelMenuListTheme:
                return (viewY - top) + Math.abs(epicenterX - viewX);
            default:
                return 0;
        }
    }

    private int getMaxDistance(ViewGroup sceneRoot) {
        switch (this.mSide) {
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
            case GravityCompat.START:
            case GravityCompat.END:
                return sceneRoot.getWidth();
            default:
                return sceneRoot.getHeight();
        }
    }
}
