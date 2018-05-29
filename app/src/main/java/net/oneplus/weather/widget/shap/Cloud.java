package net.oneplus.weather.widget.shap;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Cloud {
    private static final float MAX_SPEED = 1.0f;
    private static final int MAX_TIME = 30;
    private final float OFFSET_RATIO;
    private float mAcceleration;
    private boolean mAnim;
    private boolean mDay;
    private float mDensity;
    private int mHeight;
    private boolean mInit;
    private int mLevel;
    private float mMaxHRadio;
    private float mMaxRadiusScale;
    private float mMaxY;
    private float mMoveSpeedX;
    private float mMoveSpeedY;
    private Paint mNightPaint;
    private long mOldTime;
    private Paint mPaint;
    private float mRXRotate;
    private float mRYRotate;
    private float mRadius;
    private float mRadiusNoise;
    private float mRadiusNoiseRate;
    private float mRadiusScale;
    private int mRadiusStep;
    private float mScaleZ;
    private int mWidth;
    private float mX;
    private float mXRotate;
    private float mY;
    private float mYRotate;
    private int mZ;

    public Cloud(int x, int y, int z, int radius, int color) {
        this.mMaxRadiusScale = 1.1f;
        this.OFFSET_RATIO = 0.5f;
        this.mX = 0.0f;
        this.mY = 0.0f;
        this.mZ = 0;
        this.mRadiusScale = 1.0f;
        this.mRadiusStep = 120;
        this.mRadiusNoise = 0.003f;
        this.mRadiusNoiseRate = 1.0f;
        this.mXRotate = 0.0f;
        this.mYRotate = 0.0f;
        this.mRadius = 0.0f;
        this.mAnim = true;
        this.mMaxHRadio = 0.4f;
        this.mOldTime = 0;
        this.mAcceleration = 0.001f;
        this.mInit = false;
        this.mDay = true;
        this.mDensity = 1.0f;
        this.mPaint = new Paint();
        this.mNightPaint = new Paint();
        this.mMaxY = 0.0f;
        this.mX = (float) x;
        this.mY = (float) y;
        this.mRadius = (float) radius;
        this.mZ = z;
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(color);
        this.mNightPaint.setAntiAlias(true);
        this.mScaleZ = z == 0 ? 1.0f : ((float) (2500 - z)) / 2500.0f;
        this.mRadiusNoise = 1.0f;
        this.mRadiusScale = 1.0f;
    }

    public Cloud setMaxY(float maxY) {
        this.mMaxY = maxY;
        return this;
    }

    public Cloud setNightColor(int color) {
        this.mNightPaint.setColor(color);
        return this;
    }

    public Cloud setDensity(float density) {
        this.mDensity = density;
        return this;
    }

    public Cloud setNightAlpha(int alpha) {
        this.mNightPaint.setAlpha(alpha);
        return this;
    }

    public void setDay(boolean day) {
        this.mDay = day;
    }

    public boolean isDay() {
        return this.mDay;
    }

    public Cloud setHeightRadio(float rate) {
        this.mMaxHRadio = rate;
        return this;
    }

    public Cloud setRadiusScale(float scale) {
        this.mMaxRadiusScale = scale;
        return this;
    }

    public Cloud setLevel(int l) {
        this.mLevel = l;
        return this;
    }

    public Cloud setStep(int step) {
        this.mRadiusStep = step;
        return this;
    }

    public Cloud setAlpha(int a) {
        this.mPaint.setAlpha(a);
        return this;
    }

    public Cloud setHeight(int height) {
        this.mHeight = height;
        return this;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public Cloud setAnimation(boolean anim) {
        this.mAnim = anim;
        return this;
    }

    public boolean draw(Canvas canvas) {
        if (!this.mInit) {
            this.mRXRotate = this.mXRotate;
            this.mRYRotate = this.mYRotate;
            this.mInit = true;
        }
        if (this.mAnim) {
            int t;
            long curTime = System.currentTimeMillis();
            if (this.mOldTime == 0) {
                t = 0;
            } else {
                t = (int) (curTime - this.mOldTime);
            }
            this.mOldTime = curTime;
            if (t > 30) {
                t = MAX_TIME;
            }
            float disX = Math.abs(this.mRXRotate - this.mXRotate);
            float disY = Math.abs(this.mRYRotate - this.mYRotate);
            if (this.mMoveSpeedX < 1.0f) {
                this.mMoveSpeedX += this.mAcceleration * ((float) t);
            } else {
                this.mMoveSpeedX -= this.mAcceleration * ((float) t);
            }
            if (this.mMoveSpeedX < 0.0f) {
                this.mMoveSpeedX = this.mAcceleration;
            }
            if (disX < 150.0f * this.mDensity) {
                this.mMoveSpeedX = Math.min(this.mMoveSpeedX, (1.0f * disX) / (150.0f * this.mDensity));
            }
            float moveOffsetX = (this.mMoveSpeedX * ((float) t)) * this.mDensity;
            if (disX > moveOffsetX) {
                if (this.mRXRotate > this.mXRotate) {
                    this.mRXRotate -= moveOffsetX;
                } else {
                    this.mRXRotate += moveOffsetX;
                }
            }
            if (this.mMoveSpeedY < 1.0f) {
                this.mMoveSpeedY += this.mAcceleration * ((float) t);
            } else {
                this.mMoveSpeedY -= this.mAcceleration * ((float) t);
            }
            if (this.mMoveSpeedY < 0.0f) {
                this.mMoveSpeedY = this.mAcceleration;
            }
            if (disY < 150.0f * this.mDensity) {
                this.mMoveSpeedY = Math.min(this.mMoveSpeedY, (1.0f * disY) / (150.0f * this.mDensity));
            }
            float moveOffsetY = (this.mMoveSpeedY * ((float) t)) * this.mDensity;
            if (disY > moveOffsetY) {
                if (this.mRYRotate > this.mYRotate) {
                    this.mRYRotate -= moveOffsetY;
                } else {
                    this.mRYRotate += moveOffsetY;
                }
            }
            if (this.mRadiusScale >= this.mMaxRadiusScale) {
                this.mRadiusNoise = (-(this.mMaxRadiusScale - 1.0f)) / ((float) this.mRadiusStep);
                this.mRadiusNoiseRate = 0.01f;
            } else if (this.mRadiusScale <= 1.0f) {
                this.mRadiusNoise = (this.mMaxRadiusScale - 1.0f) / ((float) this.mRadiusStep);
                this.mRadiusNoiseRate = 0.01f;
            }
            if (this.mRadiusNoiseRate >= 1.0f) {
                this.mRadiusNoiseRate = 1.0f;
            } else {
                this.mRadiusNoiseRate += 0.05f;
            }
            this.mRadiusScale += this.mRadiusNoise * this.mRadiusNoiseRate;
        }
        Paint p = this.mPaint;
        if (!isDay()) {
            p = this.mNightPaint;
        }
        canvas.drawCircle(this.mX + this.mRXRotate, this.mY + this.mRYRotate, getRadius(), p);
        return true;
    }

    public float getRadius() {
        return this.mRadius * this.mRadiusScale;
    }

    public void updateRoatationInfo(float x, float y, float z) {
        this.mYRotate = (800.0f * (y / 90.0f)) + ((float) (((double) ((z / 90.0f) * (((float) (this.mWidth / 2)) - this.mX))) * 0.5d));
        this.mYRotate *= this.mDensity * 0.5f;
        this.mXRotate = ((float) ((((double) (500.0f * (z / 90.0f))) * 0.5d) * ((double) this.mDensity))) * 0.5f;
        this.mYRotate = (float) (((double) this.mYRotate) * (((double) this.mLevel) * 0.1d));
        this.mYRotate += ((float) ((10 - this.mLevel) * 4)) * this.mDensity;
        this.mXRotate += ((float) ((10 - this.mLevel) * 4)) * this.mDensity;
        float radius = getRadius();
        if (this.mMaxY > 0.0f && (this.mY + this.mYRotate) + radius > this.mMaxY) {
            this.mYRotate = (this.mMaxY - this.mY) - radius;
        }
    }
}
