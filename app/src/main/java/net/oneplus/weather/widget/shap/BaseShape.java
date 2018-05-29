package net.oneplus.weather.widget.shap;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public abstract class BaseShape implements IShap {
    private float mAlpha;
    private float mB;
    private boolean mDay;
    private float mDeadDY;
    private float mDeadLX;
    private float mDeadRX;
    private float mDeadUY;
    private float mG;
    private float mNB;
    private float mNG;
    private float mNR;
    private float mR;
    private float mX;
    private float mXSpeed;
    private float mY;
    private float mYSpeed;
    private float mZ;
    private float mZSpeed;

    public BaseShape() {
        this.mDeadLX = Float.MIN_VALUE;
        this.mDeadRX = Float.MIN_VALUE;
        this.mDeadUY = Float.MIN_VALUE;
        this.mDeadDY = Float.MIN_VALUE;
        this.mR = 1.0f;
        this.mG = 1.0f;
        this.mB = 1.0f;
        this.mAlpha = 1.0f;
        this.mNR = 1.0f;
        this.mNG = 1.0f;
        this.mNB = 1.0f;
    }

    public void setDeadLine(float lx, float rx, float uy, float dy) {
        this.mDeadLX = lx;
        this.mDeadRX = rx;
        this.mDeadUY = uy;
        this.mDeadDY = dy;
    }

    public void setDeadLineLeftX(float lx) {
        this.mDeadLX = lx;
    }

    public void setDeadLineRightX(float rx) {
        this.mDeadRX = rx;
    }

    public void setDeadLineUpY(float uy) {
        this.mDeadUY = uy;
    }

    public void setDeadLineDownY(float dy) {
        this.mDeadDY = dy;
    }

    public float getDeadLineLeftX() {
        return this.mDeadLX;
    }

    public float getDeadLineRightX() {
        return this.mDeadRX;
    }

    public float getDeadLineUpY() {
        return this.mDeadUY;
    }

    public float getDeadLineDownY() {
        return this.mDeadDY;
    }

    public void setColor(float r, float g, float b, float alpha) {
        this.mR = r;
        this.mG = g;
        this.mB = b;
        this.mAlpha = alpha;
    }

    public void setNightColor(float r, float g, float b, float alpha) {
        this.mNR = r;
        this.mNG = g;
        this.mNB = b;
        this.mAlpha = alpha;
    }

    public void setAlpha(float alpha) {
        this.mAlpha = alpha;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public void drawColor(GL10 gl) {
        if (isDay()) {
            gl.glColor4f(this.mR, this.mG, this.mB, this.mAlpha);
        } else {
            gl.glColor4f(this.mNR, this.mNG, this.mNB, this.mAlpha);
        }
    }

    public boolean dead() {
        boolean z = false;
        if (this.mDeadLX != Float.MIN_VALUE && this.mX < this.mDeadLX) {
            z = true;
        }
        if (this.mDeadRX != Float.MIN_VALUE && this.mX > this.mDeadRX) {
            z = true;
        }
        if (this.mDeadUY != Float.MIN_VALUE && this.mY > this.mDeadUY) {
            z = true;
        }
        return (this.mDeadDY == Float.MIN_VALUE || this.mY >= this.mDeadDY) ? z : true;
    }

    public void setXYZ(float x, float y, float z) {
        this.mX = x;
        this.mY = y;
        this.mZ = z;
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public float getZ() {
        return this.mZ;
    }

    public void draw(GL10 gl) {
        drawColor(gl);
    }

    public void setSpeed(float x, float y, float z) {
        this.mXSpeed = x;
        this.mYSpeed = y;
        this.mZSpeed = z;
    }

    public float getXSpeed() {
        return this.mXSpeed;
    }

    public float getYSpeed() {
        return this.mYSpeed;
    }

    public float getZSpeed() {
        return this.mZSpeed;
    }

    public void init(float x, float y, float z) {
        setXYZ(x, y, z);
    }

    public void move() {
    }

    public static FloatBuffer floatToBuffer(float[] a) {
        ByteBuffer mbb = ByteBuffer.allocateDirect(a.length * 4);
        mbb.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = mbb.asFloatBuffer();
        buffer.put(a);
        buffer.position(0);
        return buffer;
    }

    public void onCreate() {
    }

    public void setDay(boolean day) {
        this.mDay = day;
    }

    public boolean isDay() {
        return this.mDay;
    }
}
