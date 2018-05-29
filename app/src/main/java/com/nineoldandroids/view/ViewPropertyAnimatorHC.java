package com.nineoldandroids.view;

import android.support.v4.widget.AutoScrollHelper;
import android.view.View;
import android.view.animation.Interpolator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

class ViewPropertyAnimatorHC extends ViewPropertyAnimator {
    private static final int ALPHA = 512;
    private static final int NONE = 0;
    private static final int ROTATION = 16;
    private static final int ROTATION_X = 32;
    private static final int ROTATION_Y = 64;
    private static final int SCALE_X = 4;
    private static final int SCALE_Y = 8;
    private static final int TRANSFORM_MASK = 511;
    private static final int TRANSLATION_X = 1;
    private static final int TRANSLATION_Y = 2;
    private static final int X = 128;
    private static final int Y = 256;
    private Runnable mAnimationStarter;
    private AnimatorEventListener mAnimatorEventListener;
    private HashMap<Animator, PropertyBundle> mAnimatorMap;
    private long mDuration;
    private boolean mDurationSet;
    private Interpolator mInterpolator;
    private boolean mInterpolatorSet;
    private AnimatorListener mListener;
    ArrayList<NameValuesHolder> mPendingAnimations;
    private long mStartDelay;
    private boolean mStartDelaySet;
    private final WeakReference<View> mView;

    private static class NameValuesHolder {
        float mDeltaValue;
        float mFromValue;
        int mNameConstant;

        NameValuesHolder(int nameConstant, float fromValue, float deltaValue) {
            this.mNameConstant = nameConstant;
            this.mFromValue = fromValue;
            this.mDeltaValue = deltaValue;
        }
    }

    private static class PropertyBundle {
        ArrayList<NameValuesHolder> mNameValuesHolder;
        int mPropertyMask;

        PropertyBundle(int propertyMask, ArrayList<NameValuesHolder> nameValuesHolder) {
            this.mPropertyMask = propertyMask;
            this.mNameValuesHolder = nameValuesHolder;
        }

        boolean cancel(int propertyConstant) {
            if (!((this.mPropertyMask & propertyConstant) == 0 || this.mNameValuesHolder == null)) {
                int count = this.mNameValuesHolder.size();
                for (int i = NONE; i < count; i++) {
                    if (((NameValuesHolder) this.mNameValuesHolder.get(i)).mNameConstant == propertyConstant) {
                        this.mNameValuesHolder.remove(i);
                        this.mPropertyMask &= propertyConstant ^ -1;
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private class AnimatorEventListener implements AnimatorListener, AnimatorUpdateListener {
        private AnimatorEventListener() {
        }

        public void onAnimationStart(Animator animation) {
            if (ViewPropertyAnimatorHC.this.mListener != null) {
                ViewPropertyAnimatorHC.this.mListener.onAnimationStart(animation);
            }
        }

        public void onAnimationCancel(Animator animation) {
            if (ViewPropertyAnimatorHC.this.mListener != null) {
                ViewPropertyAnimatorHC.this.mListener.onAnimationCancel(animation);
            }
        }

        public void onAnimationRepeat(Animator animation) {
            if (ViewPropertyAnimatorHC.this.mListener != null) {
                ViewPropertyAnimatorHC.this.mListener.onAnimationRepeat(animation);
            }
        }

        public void onAnimationEnd(Animator animation) {
            if (ViewPropertyAnimatorHC.this.mListener != null) {
                ViewPropertyAnimatorHC.this.mListener.onAnimationEnd(animation);
            }
            ViewPropertyAnimatorHC.this.mAnimatorMap.remove(animation);
            if (ViewPropertyAnimatorHC.this.mAnimatorMap.isEmpty()) {
                ViewPropertyAnimatorHC.this.mListener = null;
            }
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            View v;
            float fraction = animation.getAnimatedFraction();
            PropertyBundle propertyBundle = (PropertyBundle) ViewPropertyAnimatorHC.this.mAnimatorMap.get(animation);
            if ((propertyBundle.mPropertyMask & 511) != 0) {
                v = (View) ViewPropertyAnimatorHC.this.mView.get();
                if (v != null) {
                    v.invalidate();
                }
            }
            ArrayList<NameValuesHolder> valueList = propertyBundle.mNameValuesHolder;
            if (valueList != null) {
                int count = valueList.size();
                for (int i = NONE; i < count; i++) {
                    NameValuesHolder values = (NameValuesHolder) valueList.get(i);
                    ViewPropertyAnimatorHC.this.setValue(values.mNameConstant, values.mFromValue + (values.mDeltaValue * fraction));
                }
            }
            v = (View) ViewPropertyAnimatorHC.this.mView.get();
            if (v != null) {
                v.invalidate();
            }
        }
    }

    ViewPropertyAnimatorHC(View view) {
        this.mDurationSet = false;
        this.mStartDelay = 0;
        this.mStartDelaySet = false;
        this.mInterpolatorSet = false;
        this.mListener = null;
        this.mAnimatorEventListener = new AnimatorEventListener();
        this.mPendingAnimations = new ArrayList();
        this.mAnimationStarter = new Runnable() {
            public void run() {
                ViewPropertyAnimatorHC.this.startAnimation();
            }
        };
        this.mAnimatorMap = new HashMap();
        this.mView = new WeakReference(view);
    }

    public ViewPropertyAnimator setDuration(long duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + duration);
        }
        this.mDurationSet = true;
        this.mDuration = duration;
        return this;
    }

    public long getDuration() {
        return this.mDurationSet ? this.mDuration : new ValueAnimator().getDuration();
    }

    public long getStartDelay() {
        return this.mStartDelaySet ? this.mStartDelay : 0;
    }

    public ViewPropertyAnimator setStartDelay(long startDelay) {
        if (startDelay < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + startDelay);
        }
        this.mStartDelaySet = true;
        this.mStartDelay = startDelay;
        return this;
    }

    public ViewPropertyAnimator setInterpolator(Interpolator interpolator) {
        this.mInterpolatorSet = true;
        this.mInterpolator = interpolator;
        return this;
    }

    public ViewPropertyAnimator setListener(AnimatorListener listener) {
        this.mListener = listener;
        return this;
    }

    public void start() {
        startAnimation();
    }

    public void cancel() {
        if (this.mAnimatorMap.size() > 0) {
            for (Animator runningAnim : ((HashMap) this.mAnimatorMap.clone()).keySet()) {
                runningAnim.cancel();
            }
        }
        this.mPendingAnimations.clear();
        View v = (View) this.mView.get();
        if (v != null) {
            v.removeCallbacks(this.mAnimationStarter);
        }
    }

    public ViewPropertyAnimator x(float value) {
        animateProperty(X, value);
        return this;
    }

    public ViewPropertyAnimator xBy(float value) {
        animatePropertyBy(X, value);
        return this;
    }

    public ViewPropertyAnimator y(float value) {
        animateProperty(Y, value);
        return this;
    }

    public ViewPropertyAnimator yBy(float value) {
        animatePropertyBy(Y, value);
        return this;
    }

    public ViewPropertyAnimator rotation(float value) {
        animateProperty(ROTATION, value);
        return this;
    }

    public ViewPropertyAnimator rotationBy(float value) {
        animatePropertyBy(ROTATION, value);
        return this;
    }

    public ViewPropertyAnimator rotationX(float value) {
        animateProperty(ROTATION_X, value);
        return this;
    }

    public ViewPropertyAnimator rotationXBy(float value) {
        animatePropertyBy(ROTATION_X, value);
        return this;
    }

    public ViewPropertyAnimator rotationY(float value) {
        animateProperty(ROTATION_Y, value);
        return this;
    }

    public ViewPropertyAnimator rotationYBy(float value) {
        animatePropertyBy(ROTATION_Y, value);
        return this;
    }

    public ViewPropertyAnimator translationX(float value) {
        animateProperty(TRANSLATION_X, value);
        return this;
    }

    public ViewPropertyAnimator translationXBy(float value) {
        animatePropertyBy(TRANSLATION_X, value);
        return this;
    }

    public ViewPropertyAnimator translationY(float value) {
        animateProperty(TRANSLATION_Y, value);
        return this;
    }

    public ViewPropertyAnimator translationYBy(float value) {
        animatePropertyBy(TRANSLATION_Y, value);
        return this;
    }

    public ViewPropertyAnimator scaleX(float value) {
        animateProperty(SCALE_X, value);
        return this;
    }

    public ViewPropertyAnimator scaleXBy(float value) {
        animatePropertyBy(SCALE_X, value);
        return this;
    }

    public ViewPropertyAnimator scaleY(float value) {
        animateProperty(SCALE_Y, value);
        return this;
    }

    public ViewPropertyAnimator scaleYBy(float value) {
        animatePropertyBy(SCALE_Y, value);
        return this;
    }

    public ViewPropertyAnimator alpha(float value) {
        animateProperty(ALPHA, value);
        return this;
    }

    public ViewPropertyAnimator alphaBy(float value) {
        animatePropertyBy(ALPHA, value);
        return this;
    }

    private void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f);
        ArrayList<NameValuesHolder> nameValueList = (ArrayList) this.mPendingAnimations.clone();
        this.mPendingAnimations.clear();
        int propertyMask = NONE;
        int propertyCount = nameValueList.size();
        for (int i = NONE; i < propertyCount; i++) {
            propertyMask |= ((NameValuesHolder) nameValueList.get(i)).mNameConstant;
        }
        this.mAnimatorMap.put(animator, new PropertyBundle(propertyMask, nameValueList));
        animator.addUpdateListener(this.mAnimatorEventListener);
        animator.addListener(this.mAnimatorEventListener);
        if (this.mStartDelaySet) {
            animator.setStartDelay(this.mStartDelay);
        }
        if (this.mDurationSet) {
            animator.setDuration(this.mDuration);
        }
        if (this.mInterpolatorSet) {
            animator.setInterpolator(this.mInterpolator);
        }
        animator.start();
    }

    private void animateProperty(int constantName, float toValue) {
        float fromValue = getValue(constantName);
        animatePropertyBy(constantName, fromValue, toValue - fromValue);
    }

    private void animatePropertyBy(int constantName, float byValue) {
        animatePropertyBy(constantName, getValue(constantName), byValue);
    }

    private void animatePropertyBy(int constantName, float startValue, float byValue) {
        if (this.mAnimatorMap.size() > 0) {
            Animator animatorToCancel = null;
            for (Animator runningAnim : this.mAnimatorMap.keySet()) {
                PropertyBundle bundle = (PropertyBundle) this.mAnimatorMap.get(runningAnim);
                if (bundle.cancel(constantName) && bundle.mPropertyMask == 0) {
                    animatorToCancel = runningAnim;
                    break;
                }
            }
            if (animatorToCancel != null) {
                animatorToCancel.cancel();
            }
        }
        this.mPendingAnimations.add(new NameValuesHolder(constantName, startValue, byValue));
        View v = (View) this.mView.get();
        if (v != null) {
            v.removeCallbacks(this.mAnimationStarter);
            v.post(this.mAnimationStarter);
        }
    }

    private void setValue(int propertyConstant, float value) {
        View v = (View) this.mView.get();
        if (v != null) {
            switch (propertyConstant) {
                case TRANSLATION_X:
                    v.setTranslationX(value);
                case TRANSLATION_Y:
                    v.setTranslationY(value);
                case SCALE_X:
                    v.setScaleX(value);
                case SCALE_Y:
                    v.setScaleY(value);
                case ROTATION:
                    v.setRotation(value);
                case ROTATION_X:
                    v.setRotationX(value);
                case ROTATION_Y:
                    v.setRotationY(value);
                case X:
                    v.setX(value);
                case Y:
                    v.setY(value);
                case ALPHA:
                    v.setAlpha(value);
                default:
                    break;
            }
        }
    }

    private float getValue(int propertyConstant) {
        View v = (View) this.mView.get();
        if (v != null) {
            switch (propertyConstant) {
                case TRANSLATION_X:
                    return v.getTranslationX();
                case TRANSLATION_Y:
                    return v.getTranslationY();
                case SCALE_X:
                    return v.getScaleX();
                case SCALE_Y:
                    return v.getScaleY();
                case ROTATION:
                    return v.getRotation();
                case ROTATION_X:
                    return v.getRotationX();
                case ROTATION_Y:
                    return v.getRotationY();
                case X:
                    return v.getX();
                case Y:
                    return v.getY();
                case ALPHA:
                    return v.getAlpha();
            }
        }
        return AutoScrollHelper.RELATIVE_UNSPECIFIED;
    }
}
