package net.oneplus.weather.widget.openglbase;

import android.content.Context;
import android.opengl.GLU;
import android.support.v4.widget.AutoScrollHelper;
import com.android.volley.DefaultRetryPolicy;
import javax.microedition.khronos.opengles.GL10;

public class RainBaseRender extends BaseGLRenderer {
    protected float SPEED;
    protected Rain mRain;

    public RainBaseRender(Context context, boolean day) {
        super(day);
        this.SPEED = -0.8f;
        this.mRain = new Rain();
        this.mRain.setDay(day);
        this.z = (float) (((-Z_RANDOM_RANGE) * 2) / 3);
    }

    public void onDrawFrame(GL10 gl) {
        if (this.animEnable) {
            gl.glClear(16640);
            gl.glLoadIdentity();
            gl.glTranslatef(AutoScrollHelper.RELATIVE_UNSPECIFIED, AutoScrollHelper.RELATIVE_UNSPECIFIED, this.z);
            GLU.gluLookAt(gl, AutoScrollHelper.RELATIVE_UNSPECIFIED, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, 0.0f);
            gl.glRotatef(this.mAngleX, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, AutoScrollHelper.RELATIVE_UNSPECIFIED, AutoScrollHelper.RELATIVE_UNSPECIFIED);
            gl.glRotatef(this.mAngleY, AutoScrollHelper.RELATIVE_UNSPECIFIED, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, AutoScrollHelper.RELATIVE_UNSPECIFIED);
            gl.glRotatef(this.mAngleZ, AutoScrollHelper.RELATIVE_UNSPECIFIED, AutoScrollHelper.RELATIVE_UNSPECIFIED, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            this.mRain.draw(gl, AutoScrollHelper.RELATIVE_UNSPECIFIED, this.SPEED);
            gl.glLoadIdentity();
        }
    }

    public void onDrawing(GL10 gl) {
        gl.glTranslatef(AutoScrollHelper.RELATIVE_UNSPECIFIED, AutoScrollHelper.RELATIVE_UNSPECIFIED, this.z);
        GLU.gluLookAt(gl, AutoScrollHelper.RELATIVE_UNSPECIFIED, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, 0.0f);
        this.mRain.draw(gl, AutoScrollHelper.RELATIVE_UNSPECIFIED, this.SPEED);
    }

    public void setAlpha(float alpha) {
        this.mRain.setAlpha(alpha);
    }

    public void setDay(boolean day) {
        super.setDay(day);
        this.mRain.setDay(day);
    }
}
