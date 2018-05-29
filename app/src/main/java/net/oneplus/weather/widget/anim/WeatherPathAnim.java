package net.oneplus.weather.widget.anim;

import android.support.v4.widget.AutoScrollHelper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.view.ViewPropertyAnimator;
import java.util.ArrayList;
import java.util.List;

import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class WeatherPathAnim {
    private boolean isOpen;
    private int mChildCount;
    private ViewGroup mMenuGroup;
    private List<ViewPropertyAnimator> viewAnimators;

    private class AnimListener implements AnimatorListener {
        private AnimatorListener mListener;
        private View target;

        public AnimListener(View _target, AnimatorListener l) {
            this.target = _target;
            this.mListener = l;
        }

        public void onAnimationStart(Animator animation) {
            if (this.mListener != null) {
                this.mListener.onAnimationStart(animation);
            }
        }

        public void onAnimationEnd(Animator animation) {
            if (!WeatherPathAnim.this.isOpen) {
                this.target.setVisibility(RainSurfaceView.RAIN_LEVEL_RAINSTORM);
            }
            if (this.mListener != null) {
                this.mListener.onAnimationEnd(animation);
            }
        }

        public void onAnimationCancel(Animator animation) {
            if (this.mListener != null) {
                this.mListener.onAnimationCancel(animation);
            }
        }

        public void onAnimationRepeat(Animator animation) {
            if (this.mListener != null) {
                this.mListener.onAnimationRepeat(animation);
            }
        }
    }

    public WeatherPathAnim(ViewGroup menuGroup, int poscode) {
        this.isOpen = false;
        this.viewAnimators = new ArrayList();
        this.mMenuGroup = menuGroup;
        this.mChildCount = this.mMenuGroup.getChildCount();
        for (int i = 0; i < this.mChildCount; i++) {
            this.viewAnimators.add(ViewPropertyAnimator.animate(this.mMenuGroup.getChildAt(i)));
        }
    }

    public void startAnimationsOpen(int durationMillis) {
        this.isOpen = true;
        for (int i = 1; i < this.mMenuGroup.getChildCount(); i++) {
            View inoutimagebutton = this.mMenuGroup.getChildAt(i);
            ViewPropertyAnimator viewPropertyAnimator = (ViewPropertyAnimator) this.viewAnimators.get(i);
            viewPropertyAnimator.setListener(null);
            inoutimagebutton.setVisibility(0);
            viewPropertyAnimator.x((float) inoutimagebutton.getLeft()).y((float) inoutimagebutton.getTop());
            viewPropertyAnimator.rotation(-360.0f);
            viewPropertyAnimator.setInterpolator(new OvershootInterpolator(2.0f));
        }
    }

    public void startAnimationsClose(int durationMillis, AnimatorListener l) {
        this.isOpen = false;
        for (int i = 1; i < this.mMenuGroup.getChildCount(); i++) {
            View inoutimagebutton = this.mMenuGroup.getChildAt(i);
            ViewPropertyAnimator viewPropertyAnimator = (ViewPropertyAnimator) this.viewAnimators.get(i);
            viewPropertyAnimator.x((float) (inoutimagebutton.getLeft() == 0 ? R.styleable.AppCompatTheme_dialogTheme : R.styleable.AppCompatTheme_dialogTheme)).y((float) this.mMenuGroup.getChildAt(0).getTop());
            viewPropertyAnimator.rotation(AutoScrollHelper.RELATIVE_UNSPECIFIED);
            viewPropertyAnimator.setListener(new AnimListener(inoutimagebutton, l));
            viewPropertyAnimator.setInterpolator(new AccelerateInterpolator());
        }
    }

    public static Animation getRotateAnimation(float fromDegrees, float toDegrees, int durationMillis) {
        RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees, 1, 0.5f, 1, 0.5f);
        rotate.setDuration((long) durationMillis);
        rotate.setFillAfter(true);
        return rotate;
    }
}
