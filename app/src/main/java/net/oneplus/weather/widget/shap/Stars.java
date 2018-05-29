package net.oneplus.weather.widget.shap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.AutoScrollHelper;
import com.android.volley.DefaultRetryPolicy;
import net.oneplus.weather.util.UIUtil;
import net.oneplus.weather.widget.anim.StartAnimation;

public class Stars implements IViewShap {
    public static final int CIRCLE_COUNT = 50;
    private Circle[] mCircles;
    private int mWHeight;
    private int mWWidth;

    class Circle {
        public int CURRNET;
        public Paint P;
        public float R;
        public int STEP;
        public float X;
        public float Y;

        Circle() {
            this.STEP = 120;
        }

        public void next() {
            this.CURRNET = (this.CURRNET + 1) % this.STEP;
            if (this.CURRNET <= 60) {
                this.P.setAlpha((int) ((((float) this.CURRNET) / 60.0f) * 100.0f));
            } else {
                this.P.setAlpha((int) ((((float) (120 - this.CURRNET)) / 60.0f) * 100.0f));
            }
        }
    }

    public Stars() {
        this.mWHeight = 1920;
        this.mWWidth = 1080;
    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < this.mCircles.length; i++) {
            canvas.drawCircle(this.mCircles[i].X, this.mCircles[i].Y, this.mCircles[i].R, this.mCircles[i].P);
        }
    }

    public void setWindowSize(int w, int h) {
        this.mWWidth = w;
        this.mWHeight = h;
    }

    public void init(Context context, int w, int h) {
        setWindowSize(w, h);
        StartAnimation.setRange((float) this.mWWidth, (float) this.mWHeight, AutoScrollHelper.RELATIVE_UNSPECIFIED);
        this.mCircles = new Circle[50];
        for (int i = 0; i < this.mCircles.length; i++) {
            Paint paint = new Paint();
            paint.setColor(Color.rgb(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK));
            this.mCircles[i] = new Circle();
            float[] p = StartAnimation.orginXY();
            this.mCircles[i].X = p[0];
            this.mCircles[i].Y = p[1];
            this.mCircles[i].R = (float) UIUtil.dip2px(context, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            this.mCircles[i].P = paint;
            this.mCircles[i].CURRNET = (int) p[2];
        }
    }

    public void next() {
        for (int i = 0; i < this.mCircles.length; i++) {
            this.mCircles[i].next();
        }
    }
}
