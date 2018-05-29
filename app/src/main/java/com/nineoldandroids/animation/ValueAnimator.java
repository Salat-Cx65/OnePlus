package com.nineoldandroids.animation;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.AutoScrollHelper;
import android.util.AndroidRuntimeException;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.android.volley.DefaultRetryPolicy;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.oneplus.weather.util.StringUtils;

public class ValueAnimator extends Animator {
    static final int ANIMATION_FRAME = 1;
    static final int ANIMATION_START = 0;
    private static final long DEFAULT_FRAME_DELAY = 10;
    public static final int INFINITE = -1;
    public static final int RESTART = 1;
    public static final int REVERSE = 2;
    static final int RUNNING = 1;
    static final int SEEKED = 2;
    static final int STOPPED = 0;
    private static ThreadLocal<AnimationHandler> sAnimationHandler;
    private static final ThreadLocal<ArrayList<ValueAnimator>> sAnimations;
    private static final Interpolator sDefaultInterpolator;
    private static final ThreadLocal<ArrayList<ValueAnimator>> sDelayedAnims;
    private static final ThreadLocal<ArrayList<ValueAnimator>> sEndingAnims;
    private static final TypeEvaluator sFloatEvaluator;
    private static long sFrameDelay;
    private static final TypeEvaluator sIntEvaluator;
    private static final ThreadLocal<ArrayList<ValueAnimator>> sPendingAnimations;
    private static final ThreadLocal<ArrayList<ValueAnimator>> sReadyAnims;
    private float mCurrentFraction;
    private int mCurrentIteration;
    private long mDelayStartTime;
    private long mDuration;
    boolean mInitialized;
    private Interpolator mInterpolator;
    private boolean mPlayingBackwards;
    int mPlayingState;
    private int mRepeatCount;
    private int mRepeatMode;
    private boolean mRunning;
    long mSeekTime;
    private long mStartDelay;
    long mStartTime;
    private boolean mStarted;
    private boolean mStartedDelay;
    private ArrayList<AnimatorUpdateListener> mUpdateListeners;
    PropertyValuesHolder[] mValues;
    HashMap<String, PropertyValuesHolder> mValuesMap;

    private static class AnimationHandler extends Handler {
        private AnimationHandler() {
        }

        public void handleMessage(Message msg) {
            boolean callAgain = true;
            ArrayList<ValueAnimator> animations = (ArrayList) sAnimations.get();
            ArrayList<ValueAnimator> delayedAnims = (ArrayList) sDelayedAnims.get();
            int i;
            ValueAnimator anim;
            long currentTime;
            ArrayList<ValueAnimator> readyAnims;
            ArrayList<ValueAnimator> endingAnims;
            int numDelayedAnims;
            int numReadyAnims;
            int numAnims;
            switch (msg.what) {
                case ANIMATION_START:
                    ArrayList<ValueAnimator> pendingAnimations = (ArrayList) sPendingAnimations.get();
                    if (animations.size() > 0 || delayedAnims.size() > 0) {
                        callAgain = false;
                    }
                    while (pendingAnimations.size() > 0) {
                        ArrayList<ValueAnimator> pendingCopy = (ArrayList) pendingAnimations.clone();
                        pendingAnimations.clear();
                        int count = pendingCopy.size();
                        for (i = ANIMATION_START; i < count; i++) {
                            anim = (ValueAnimator) pendingCopy.get(i);
                            if (anim.mStartDelay == 0) {
                                anim.startAnimation();
                            } else {
                                delayedAnims.add(anim);
                            }
                        }
                    }
                    currentTime = AnimationUtils.currentAnimationTimeMillis();
                    readyAnims = (ArrayList) sReadyAnims.get();
                    endingAnims = (ArrayList) sEndingAnims.get();
                    numDelayedAnims = delayedAnims.size();
                    for (i = ANIMATION_START; i < numDelayedAnims; i++) {
                        anim = (ValueAnimator) delayedAnims.get(i);
                        if (anim.delayedAnimationFrame(currentTime)) {
                            readyAnims.add(anim);
                        }
                    }
                    numReadyAnims = readyAnims.size();
                    if (numReadyAnims > 0) {
                        for (i = ANIMATION_START; i < numReadyAnims; i++) {
                            anim = (ValueAnimator) readyAnims.get(i);
                            anim.startAnimation();
                            anim.mRunning = true;
                            delayedAnims.remove(anim);
                        }
                        readyAnims.clear();
                    }
                    numAnims = animations.size();
                    i = ANIMATION_START;
                    while (i < numAnims) {
                        anim = (ValueAnimator) animations.get(i);
                        if (anim.animationFrame(currentTime)) {
                            endingAnims.add(anim);
                        }
                        if (animations.size() != numAnims) {
                            i++;
                        } else {
                            numAnims--;
                            endingAnims.remove(anim);
                        }
                    }
                    if (endingAnims.size() > 0) {
                        for (i = ANIMATION_START; i < endingAnims.size(); i++) {
                            ((ValueAnimator) endingAnims.get(i)).endAnimation();
                        }
                        endingAnims.clear();
                    }
                    if (callAgain) {
                        if (animations.isEmpty() || !delayedAnims.isEmpty()) {
                            sendEmptyMessageDelayed(1, Math.max(0, sFrameDelay - (AnimationUtils.currentAnimationTimeMillis() - currentTime)));
                        }
                    }
                case RUNNING:
                    currentTime = AnimationUtils.currentAnimationTimeMillis();
                    readyAnims = (ArrayList) sReadyAnims.get();
                    endingAnims = (ArrayList) sEndingAnims.get();
                    numDelayedAnims = delayedAnims.size();
                    for (i = ANIMATION_START; i < numDelayedAnims; i++) {
                        anim = (ValueAnimator) delayedAnims.get(i);
                        if (anim.delayedAnimationFrame(currentTime)) {
                            readyAnims.add(anim);
                        }
                    }
                    numReadyAnims = readyAnims.size();
                    if (numReadyAnims > 0) {
                        for (i = ANIMATION_START; i < numReadyAnims; i++) {
                            anim = (ValueAnimator) readyAnims.get(i);
                            anim.startAnimation();
                            anim.mRunning = true;
                            delayedAnims.remove(anim);
                        }
                        readyAnims.clear();
                    }
                    numAnims = animations.size();
                    i = ANIMATION_START;
                    while (i < numAnims) {
                        anim = (ValueAnimator) animations.get(i);
                        if (anim.animationFrame(currentTime)) {
                            endingAnims.add(anim);
                        }
                        if (animations.size() != numAnims) {
                            numAnims--;
                            endingAnims.remove(anim);
                        } else {
                            i++;
                        }
                    }
                    if (endingAnims.size() > 0) {
                        for (i = ANIMATION_START; i < endingAnims.size(); i++) {
                            ((ValueAnimator) endingAnims.get(i)).endAnimation();
                        }
                        endingAnims.clear();
                    }
                    if (callAgain) {
                        if (animations.isEmpty()) {
                        }
                        sendEmptyMessageDelayed(1, Math.max(0, sFrameDelay - (AnimationUtils.currentAnimationTimeMillis() - currentTime)));
                    }
                default:
                    break;
            }
        }
    }

    public static interface AnimatorUpdateListener {
        void onAnimationUpdate(ValueAnimator valueAnimator);
    }

    static {
        sAnimationHandler = new ThreadLocal();
        sAnimations = new ThreadLocal<ArrayList<ValueAnimator>>() {
            protected ArrayList<ValueAnimator> initialValue() {
                return new ArrayList();
            }
        };
        sPendingAnimations = new ThreadLocal<ArrayList<ValueAnimator>>() {
            protected ArrayList<ValueAnimator> initialValue() {
                return new ArrayList();
            }
        };
        sDelayedAnims = new ThreadLocal<ArrayList<ValueAnimator>>() {
            protected ArrayList<ValueAnimator> initialValue() {
                return new ArrayList();
            }
        };
        sEndingAnims = new ThreadLocal<ArrayList<ValueAnimator>>() {
            protected ArrayList<ValueAnimator> initialValue() {
                return new ArrayList();
            }
        };
        sReadyAnims = new ThreadLocal<ArrayList<ValueAnimator>>() {
            protected ArrayList<ValueAnimator> initialValue() {
                return new ArrayList();
            }
        };
        sDefaultInterpolator = new AccelerateDecelerateInterpolator();
        sIntEvaluator = new IntEvaluator();
        sFloatEvaluator = new FloatEvaluator();
        sFrameDelay = 10;
    }

    public ValueAnimator() {
        this.mSeekTime = -1;
        this.mPlayingBackwards = false;
        this.mCurrentIteration = 0;
        this.mCurrentFraction = 0.0f;
        this.mStartedDelay = false;
        this.mPlayingState = 0;
        this.mRunning = false;
        this.mStarted = false;
        this.mInitialized = false;
        this.mDuration = 300;
        this.mStartDelay = 0;
        this.mRepeatCount = 0;
        this.mRepeatMode = 1;
        this.mInterpolator = sDefaultInterpolator;
        this.mUpdateListeners = null;
    }

    public static ValueAnimator ofInt(int... values) {
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(values);
        return anim;
    }

    public static ValueAnimator ofFloat(float... values) {
        ValueAnimator anim = new ValueAnimator();
        anim.setFloatValues(values);
        return anim;
    }

    public static ValueAnimator ofPropertyValuesHolder(PropertyValuesHolder... values) {
        ValueAnimator anim = new ValueAnimator();
        anim.setValues(values);
        return anim;
    }

    public static ValueAnimator ofObject(TypeEvaluator evaluator, Object... values) {
        ValueAnimator anim = new ValueAnimator();
        anim.setObjectValues(values);
        anim.setEvaluator(evaluator);
        return anim;
    }

    public void setIntValues(int... values) {
        if (values != null && values.length != 0) {
            if (this.mValues == null || this.mValues.length == 0) {
                setValues(PropertyValuesHolder.ofInt(StringUtils.EMPTY_STRING, values));
            } else {
                this.mValues[0].setIntValues(values);
            }
            this.mInitialized = false;
        }
    }

    public void setFloatValues(float... values) {
        if (values != null && values.length != 0) {
            if (this.mValues == null || this.mValues.length == 0) {
                setValues(PropertyValuesHolder.ofFloat(StringUtils.EMPTY_STRING, values));
            } else {
                this.mValues[0].setFloatValues(values);
            }
            this.mInitialized = false;
        }
    }

    public void setObjectValues(Object... values) {
        if (values != null && values.length != 0) {
            if (this.mValues == null || this.mValues.length == 0) {
                setValues(PropertyValuesHolder.ofObject(StringUtils.EMPTY_STRING, (TypeEvaluator) null, values));
            } else {
                this.mValues[0].setObjectValues(values);
            }
            this.mInitialized = false;
        }
    }

    public void setValues(PropertyValuesHolder... values) {
        int numValues = values.length;
        this.mValues = values;
        this.mValuesMap = new HashMap(numValues);
        for (int i = ANIMATION_START; i < numValues; i++) {
            PropertyValuesHolder valuesHolder = values[i];
            this.mValuesMap.put(valuesHolder.getPropertyName(), valuesHolder);
        }
        this.mInitialized = false;
    }

    public PropertyValuesHolder[] getValues() {
        return this.mValues;
    }

    void initAnimation() {
        if (!this.mInitialized) {
            int numValues = this.mValues.length;
            for (int i = ANIMATION_START; i < numValues; i++) {
                this.mValues[i].init();
            }
            this.mInitialized = true;
        }
    }

    public ValueAnimator setDuration(long duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + duration);
        }
        this.mDuration = duration;
        return this;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public void setCurrentPlayTime(long playTime) {
        initAnimation();
        long currentTime = AnimationUtils.currentAnimationTimeMillis();
        if (this.mPlayingState != 1) {
            this.mSeekTime = playTime;
            this.mPlayingState = 2;
        }
        this.mStartTime = currentTime - playTime;
        animationFrame(currentTime);
    }

    public long getCurrentPlayTime() {
        return (!this.mInitialized || this.mPlayingState == 0) ? 0 : AnimationUtils.currentAnimationTimeMillis() - this.mStartTime;
    }

    public long getStartDelay() {
        return this.mStartDelay;
    }

    public void setStartDelay(long startDelay) {
        this.mStartDelay = startDelay;
    }

    public static long getFrameDelay() {
        return sFrameDelay;
    }

    public static void setFrameDelay(long frameDelay) {
        sFrameDelay = frameDelay;
    }

    public Object getAnimatedValue() {
        return (this.mValues == null || this.mValues.length <= 0) ? null : this.mValues[0].getAnimatedValue();
    }

    public Object getAnimatedValue(String propertyName) {
        PropertyValuesHolder valuesHolder = (PropertyValuesHolder) this.mValuesMap.get(propertyName);
        return valuesHolder != null ? valuesHolder.getAnimatedValue() : null;
    }

    public void setRepeatCount(int value) {
        this.mRepeatCount = value;
    }

    public int getRepeatCount() {
        return this.mRepeatCount;
    }

    public void setRepeatMode(int value) {
        this.mRepeatMode = value;
    }

    public int getRepeatMode() {
        return this.mRepeatMode;
    }

    public void addUpdateListener(AnimatorUpdateListener listener) {
        if (this.mUpdateListeners == null) {
            this.mUpdateListeners = new ArrayList();
        }
        this.mUpdateListeners.add(listener);
    }

    public void removeAllUpdateListeners() {
        if (this.mUpdateListeners != null) {
            this.mUpdateListeners.clear();
            this.mUpdateListeners = null;
        }
    }

    public void removeUpdateListener(AnimatorUpdateListener listener) {
        if (this.mUpdateListeners != null) {
            this.mUpdateListeners.remove(listener);
            if (this.mUpdateListeners.size() == 0) {
                this.mUpdateListeners = null;
            }
        }
    }

    public void setInterpolator(Interpolator value) {
        if (value != null) {
            this.mInterpolator = value;
        } else {
            this.mInterpolator = new LinearInterpolator();
        }
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    public void setEvaluator(TypeEvaluator value) {
        if (value != null && this.mValues != null && this.mValues.length > 0) {
            this.mValues[0].setEvaluator(value);
        }
    }

    private void start(boolean playBackwards) {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        this.mPlayingBackwards = playBackwards;
        this.mCurrentIteration = 0;
        this.mPlayingState = 0;
        this.mStarted = true;
        this.mStartedDelay = false;
        ((ArrayList) sPendingAnimations.get()).add(this);
        if (this.mStartDelay == 0) {
            setCurrentPlayTime(getCurrentPlayTime());
            this.mPlayingState = 0;
            this.mRunning = true;
            if (this.mListeners != null) {
                ArrayList<AnimatorListener> tmpListeners = (ArrayList) this.mListeners.clone();
                int numListeners = tmpListeners.size();
                for (int i = ANIMATION_START; i < numListeners; i++) {
                    ((AnimatorListener) tmpListeners.get(i)).onAnimationStart(this);
                }
            }
        }
        AnimationHandler animationHandler = (AnimationHandler) sAnimationHandler.get();
        if (animationHandler == null) {
            animationHandler = new AnimationHandler();
            sAnimationHandler.set(animationHandler);
        }
        animationHandler.sendEmptyMessage(ANIMATION_START);
    }

    public void start() {
        start(false);
    }

    public void cancel() {
        if (this.mPlayingState != 0 || ((ArrayList) sPendingAnimations.get()).contains(this) || ((ArrayList) sDelayedAnims.get()).contains(this)) {
            if (this.mRunning && this.mListeners != null) {
                Iterator i$ = ((ArrayList) this.mListeners.clone()).iterator();
                while (i$.hasNext()) {
                    ((AnimatorListener) i$.next()).onAnimationCancel(this);
                }
            }
            endAnimation();
        }
    }

    public void end() {
        if (!((ArrayList) sAnimations.get()).contains(this) && !((ArrayList) sPendingAnimations.get()).contains(this)) {
            this.mStartedDelay = false;
            startAnimation();
        } else if (!this.mInitialized) {
            initAnimation();
        }
        if (this.mRepeatCount <= 0 || (this.mRepeatCount & 1) != 1) {
            animateValue(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        } else {
            animateValue(AutoScrollHelper.RELATIVE_UNSPECIFIED);
        }
        endAnimation();
    }

    public boolean isRunning() {
        return this.mPlayingState == 1 || this.mRunning;
    }

    public boolean isStarted() {
        return this.mStarted;
    }

    public void reverse() {
        this.mPlayingBackwards = !this.mPlayingBackwards;
        if (this.mPlayingState == 1) {
            long currentTime = AnimationUtils.currentAnimationTimeMillis();
            this.mStartTime = currentTime - (this.mDuration - (currentTime - this.mStartTime));
            return;
        }
        start(true);
    }

    private void endAnimation() {
        ((ArrayList) sAnimations.get()).remove(this);
        ((ArrayList) sPendingAnimations.get()).remove(this);
        ((ArrayList) sDelayedAnims.get()).remove(this);
        this.mPlayingState = 0;
        if (this.mRunning && this.mListeners != null) {
            ArrayList<AnimatorListener> tmpListeners = (ArrayList) this.mListeners.clone();
            int numListeners = tmpListeners.size();
            for (int i = ANIMATION_START; i < numListeners; i++) {
                ((AnimatorListener) tmpListeners.get(i)).onAnimationEnd(this);
            }
        }
        this.mRunning = false;
        this.mStarted = false;
    }

    private void startAnimation() {
        initAnimation();
        ((ArrayList) sAnimations.get()).add(this);
        if (this.mStartDelay > 0 && this.mListeners != null) {
            ArrayList<AnimatorListener> tmpListeners = (ArrayList) this.mListeners.clone();
            int numListeners = tmpListeners.size();
            for (int i = ANIMATION_START; i < numListeners; i++) {
                ((AnimatorListener) tmpListeners.get(i)).onAnimationStart(this);
            }
        }
    }

    private boolean delayedAnimationFrame(long currentTime) {
        if (this.mStartedDelay) {
            long deltaTime = currentTime - this.mDelayStartTime;
            if (deltaTime > this.mStartDelay) {
                this.mStartTime = currentTime - (deltaTime - this.mStartDelay);
                this.mPlayingState = 1;
                return true;
            }
        }
        this.mStartedDelay = true;
        this.mDelayStartTime = currentTime;
        return false;
    }

    boolean animationFrame(long currentTime) {
        boolean done = false;
        if (this.mPlayingState == 0) {
            this.mPlayingState = 1;
            if (this.mSeekTime < 0) {
                this.mStartTime = currentTime;
            } else {
                this.mStartTime = currentTime - this.mSeekTime;
                this.mSeekTime = -1;
            }
        }
        switch (this.mPlayingState) {
            case RUNNING:
            case SEEKED:
                float fraction;
                if (this.mDuration > 0) {
                    fraction = ((float) (currentTime - this.mStartTime)) / ((float) this.mDuration);
                } else {
                    fraction = 1.0f;
                }
                if (fraction >= 1.0f) {
                    if (this.mCurrentIteration < this.mRepeatCount || this.mRepeatCount == -1) {
                        if (this.mListeners != null) {
                            int numListeners = this.mListeners.size();
                            for (int i = ANIMATION_START; i < numListeners; i++) {
                                ((AnimatorListener) this.mListeners.get(i)).onAnimationRepeat(this);
                            }
                        }
                        if (this.mRepeatMode == 2) {
                            this.mPlayingBackwards = !this.mPlayingBackwards;
                        }
                        this.mCurrentIteration += (int) fraction;
                        fraction %= 1.0f;
                        this.mStartTime += this.mDuration;
                    } else {
                        done = true;
                        fraction = Math.min(fraction, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    }
                }
                if (this.mPlayingBackwards) {
                    fraction = 1.0f - fraction;
                }
                animateValue(fraction);
                break;
        }
        return done;
    }

    public float getAnimatedFraction() {
        return this.mCurrentFraction;
    }

    void animateValue(float fraction) {
        int i;
        fraction = this.mInterpolator.getInterpolation(fraction);
        this.mCurrentFraction = fraction;
        int numValues = this.mValues.length;
        for (i = ANIMATION_START; i < numValues; i++) {
            this.mValues[i].calculateValue(fraction);
        }
        if (this.mUpdateListeners != null) {
            int numListeners = this.mUpdateListeners.size();
            for (i = ANIMATION_START; i < numListeners; i++) {
                ((AnimatorUpdateListener) this.mUpdateListeners.get(i)).onAnimationUpdate(this);
            }
        }
    }

    public ValueAnimator clone() {
        int i;
        ValueAnimator anim = (ValueAnimator) super.clone();
        if (this.mUpdateListeners != null) {
            ArrayList<AnimatorUpdateListener> oldListeners = this.mUpdateListeners;
            anim.mUpdateListeners = new ArrayList();
            int numListeners = oldListeners.size();
            for (i = ANIMATION_START; i < numListeners; i++) {
                anim.mUpdateListeners.add(oldListeners.get(i));
            }
        }
        anim.mSeekTime = -1;
        anim.mPlayingBackwards = false;
        anim.mCurrentIteration = 0;
        anim.mInitialized = false;
        anim.mPlayingState = 0;
        anim.mStartedDelay = false;
        PropertyValuesHolder[] oldValues = this.mValues;
        if (oldValues != null) {
            int numValues = oldValues.length;
            anim.mValues = new PropertyValuesHolder[numValues];
            anim.mValuesMap = new HashMap(numValues);
            for (i = ANIMATION_START; i < numValues; i++) {
                PropertyValuesHolder newValuesHolder = oldValues[i].clone();
                anim.mValues[i] = newValuesHolder;
                anim.mValuesMap.put(newValuesHolder.getPropertyName(), newValuesHolder);
            }
        }
        return anim;
    }

    public static int getCurrentAnimationsCount() {
        return ((ArrayList) sAnimations.get()).size();
    }

    public static void clearAllAnimations() {
        ((ArrayList) sAnimations.get()).clear();
        ((ArrayList) sPendingAnimations.get()).clear();
        ((ArrayList) sDelayedAnims.get()).clear();
    }

    public String toString() {
        String returnVal = "ValueAnimator@" + Integer.toHexString(hashCode());
        if (this.mValues != null) {
            for (int i = ANIMATION_START; i < this.mValues.length; i++) {
                returnVal = returnVal + "\n    " + this.mValues[i].toString();
            }
        }
        return returnVal;
    }
}
