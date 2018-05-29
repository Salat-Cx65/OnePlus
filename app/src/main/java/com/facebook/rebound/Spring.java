package com.facebook.rebound;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

public class Spring {
    private static int ID = 0;
    private static final double MAX_DELTA_TIME_SEC = 0.064d;
    private static final double SOLVER_TIMESTEP_SEC = 0.001d;
    private final PhysicsState mCurrentState;
    private double mDisplacementFromRestThreshold;
    private double mEndValue;
    private final String mId;
    private CopyOnWriteArraySet<SpringListener> mListeners;
    private boolean mOvershootClampingEnabled;
    private final PhysicsState mPreviousState;
    private double mRestSpeedThreshold;
    private SpringConfig mSpringConfig;
    private final BaseSpringSystem mSpringSystem;
    private double mStartValue;
    private final PhysicsState mTempState;
    private double mTimeAccumulator;
    private boolean mWasAtRest;

    private static class PhysicsState {
        double position;
        double velocity;

        private PhysicsState() {
        }
    }

    static {
        ID = 0;
    }

    Spring(BaseSpringSystem springSystem) {
        this.mCurrentState = new PhysicsState();
        this.mPreviousState = new PhysicsState();
        this.mTempState = new PhysicsState();
        this.mWasAtRest = true;
        this.mRestSpeedThreshold = 0.005d;
        this.mDisplacementFromRestThreshold = 0.005d;
        this.mListeners = new CopyOnWriteArraySet();
        this.mTimeAccumulator = 0.0d;
        if (springSystem == null) {
            throw new IllegalArgumentException("Spring cannot be created outside of a BaseSpringSystem");
        }
        this.mSpringSystem = springSystem;
        StringBuilder append = new StringBuilder().append("spring:");
        int i = ID;
        ID = i + 1;
        this.mId = append.append(i).toString();
        setSpringConfig(SpringConfig.defaultConfig);
    }

    public void destroy() {
        this.mListeners.clear();
        this.mSpringSystem.deregisterSpring(this);
    }

    public String getId() {
        return this.mId;
    }

    public Spring setSpringConfig(SpringConfig springConfig) {
        if (springConfig == null) {
            throw new IllegalArgumentException("springConfig is required");
        }
        this.mSpringConfig = springConfig;
        return this;
    }

    public SpringConfig getSpringConfig() {
        return this.mSpringConfig;
    }

    public Spring setCurrentValue(double currentValue) {
        return setCurrentValue(currentValue, true);
    }

    public Spring setCurrentValue(double currentValue, boolean setAtRest) {
        this.mStartValue = currentValue;
        this.mCurrentState.position = currentValue;
        this.mSpringSystem.activateSpring(getId());
        Iterator i$ = this.mListeners.iterator();
        while (i$.hasNext()) {
            ((SpringListener) i$.next()).onSpringUpdate(this);
        }
        if (setAtRest) {
            setAtRest();
        }
        return this;
    }

    public double getStartValue() {
        return this.mStartValue;
    }

    public double getCurrentValue() {
        return this.mCurrentState.position;
    }

    public double getCurrentDisplacementDistance() {
        return getDisplacementDistanceForState(this.mCurrentState);
    }

    private double getDisplacementDistanceForState(PhysicsState state) {
        return Math.abs(this.mEndValue - state.position);
    }

    public Spring setEndValue(double endValue) {
        if (!(this.mEndValue == endValue && isAtRest())) {
            this.mStartValue = getCurrentValue();
            this.mEndValue = endValue;
            this.mSpringSystem.activateSpring(getId());
            Iterator i$ = this.mListeners.iterator();
            while (i$.hasNext()) {
                ((SpringListener) i$.next()).onSpringEndStateChange(this);
            }
        }
        return this;
    }

    public double getEndValue() {
        return this.mEndValue;
    }

    public Spring setVelocity(double velocity) {
        if (velocity != this.mCurrentState.velocity) {
            this.mCurrentState.velocity = velocity;
            this.mSpringSystem.activateSpring(getId());
        }
        return this;
    }

    public double getVelocity() {
        return this.mCurrentState.velocity;
    }

    public Spring setRestSpeedThreshold(double restSpeedThreshold) {
        this.mRestSpeedThreshold = restSpeedThreshold;
        return this;
    }

    public double getRestSpeedThreshold() {
        return this.mRestSpeedThreshold;
    }

    public Spring setRestDisplacementThreshold(double displacementFromRestThreshold) {
        this.mDisplacementFromRestThreshold = displacementFromRestThreshold;
        return this;
    }

    public double getRestDisplacementThreshold() {
        return this.mDisplacementFromRestThreshold;
    }

    public Spring setOvershootClampingEnabled(boolean overshootClampingEnabled) {
        this.mOvershootClampingEnabled = overshootClampingEnabled;
        return this;
    }

    public boolean isOvershootClampingEnabled() {
        return this.mOvershootClampingEnabled;
    }

    public boolean isOvershooting() {
        if (this.mSpringConfig.tension > 0.0d) {
            if ((this.mStartValue < this.mEndValue && getCurrentValue() > this.mEndValue) || (this.mStartValue > this.mEndValue && getCurrentValue() < this.mEndValue)) {
                return true;
            }
        }
        return false;
    }

    void advance(double realDeltaTime) {
        boolean isAtRest = isAtRest();
        if (!isAtRest || !this.mWasAtRest) {
            double adjustedDeltaTime = realDeltaTime;
            if (realDeltaTime > 0.064d) {
                adjustedDeltaTime = MAX_DELTA_TIME_SEC;
            }
            this.mTimeAccumulator += adjustedDeltaTime;
            double tension = this.mSpringConfig.tension;
            double friction = this.mSpringConfig.friction;
            double position = this.mCurrentState.position;
            double velocity = this.mCurrentState.velocity;
            double tempPosition = this.mTempState.position;
            double tempVelocity = this.mTempState.velocity;
            while (this.mTimeAccumulator >= 0.001d) {
                this.mTimeAccumulator -= 0.001d;
                if (this.mTimeAccumulator < 0.001d) {
                    this.mPreviousState.position = position;
                    this.mPreviousState.velocity = velocity;
                }
                double aVelocity = velocity;
                double aAcceleration = ((this.mEndValue - tempPosition) * tension) - (friction * velocity);
                tempVelocity = velocity + ((0.001d * aAcceleration) * 0.5d);
                double bVelocity = tempVelocity;
                double bAcceleration = ((this.mEndValue - (position + ((0.001d * aVelocity) * 0.5d))) * tension) - (friction * tempVelocity);
                tempVelocity = velocity + ((0.001d * bAcceleration) * 0.5d);
                double cVelocity = tempVelocity;
                double cAcceleration = ((this.mEndValue - (position + ((0.001d * bVelocity) * 0.5d))) * tension) - (friction * tempVelocity);
                tempPosition = position + (0.001d * cVelocity);
                tempVelocity = velocity + (0.001d * cAcceleration);
                position += 0.001d * (0.16666666666666666d * (((2.0d * (bVelocity + cVelocity)) + aVelocity) + tempVelocity));
                velocity += 0.001d * (0.16666666666666666d * (((2.0d * (bAcceleration + cAcceleration)) + aAcceleration) + (((this.mEndValue - tempPosition) * tension) - (friction * tempVelocity))));
            }
            this.mTempState.position = tempPosition;
            this.mTempState.velocity = tempVelocity;
            this.mCurrentState.position = position;
            this.mCurrentState.velocity = velocity;
            if (this.mTimeAccumulator > 0.0d) {
                interpolate(this.mTimeAccumulator / 0.001d);
            }
            if (isAtRest() || (this.mOvershootClampingEnabled && isOvershooting())) {
                if (tension > 0.0d) {
                    this.mStartValue = this.mEndValue;
                    this.mCurrentState.position = this.mEndValue;
                } else {
                    this.mEndValue = this.mCurrentState.position;
                    this.mStartValue = this.mEndValue;
                }
                setVelocity(0.0d);
                isAtRest = true;
            }
            boolean notifyActivate = false;
            if (this.mWasAtRest) {
                this.mWasAtRest = false;
                notifyActivate = true;
            }
            boolean notifyAtRest = false;
            if (isAtRest) {
                this.mWasAtRest = true;
                notifyAtRest = true;
            }
            Iterator i$ = this.mListeners.iterator();
            while (i$.hasNext()) {
                SpringListener listener = (SpringListener) i$.next();
                if (notifyActivate) {
                    listener.onSpringActivate(this);
                }
                listener.onSpringUpdate(this);
                if (notifyAtRest) {
                    listener.onSpringAtRest(this);
                }
            }
        }
    }

    public boolean systemShouldAdvance() {
        return (isAtRest() && wasAtRest()) ? false : true;
    }

    public boolean wasAtRest() {
        return this.mWasAtRest;
    }

    public boolean isAtRest() {
        return Math.abs(this.mCurrentState.velocity) <= this.mRestSpeedThreshold && (getDisplacementDistanceForState(this.mCurrentState) <= this.mDisplacementFromRestThreshold || this.mSpringConfig.tension == 0.0d);
    }

    public Spring setAtRest() {
        this.mEndValue = this.mCurrentState.position;
        this.mTempState.position = this.mCurrentState.position;
        this.mCurrentState.velocity = 0.0d;
        return this;
    }

    private void interpolate(double alpha) {
        this.mCurrentState.position = (this.mCurrentState.position * alpha) + (this.mPreviousState.position * (1.0d - alpha));
        this.mCurrentState.velocity = (this.mCurrentState.velocity * alpha) + (this.mPreviousState.velocity * (1.0d - alpha));
    }

    public Spring addListener(SpringListener newListener) {
        if (newListener == null) {
            throw new IllegalArgumentException("newListener is required");
        }
        this.mListeners.add(newListener);
        return this;
    }

    public Spring removeListener(SpringListener listenerToRemove) {
        if (listenerToRemove == null) {
            throw new IllegalArgumentException("listenerToRemove is required");
        }
        this.mListeners.remove(listenerToRemove);
        return this;
    }

    public Spring removeAllListeners() {
        this.mListeners.clear();
        return this;
    }

    public boolean currentValueIsApproximately(double value) {
        return Math.abs(getCurrentValue() - value) <= getRestDisplacementThreshold();
    }
}
