package net.oneplus.weather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.util.AttributeSet;
import com.google.android.gms.location.LocationRequest;
import java.util.ArrayList;
import java.util.Random;
import net.oneplus.weather.R;
import net.oneplus.weather.util.OrientationSensorUtil.OrientationInfoListener;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import net.oneplus.weather.widget.shap.Stars;

public class SnowView extends BaseWeatherView {
    private static final int BACKGROUND_COLOR;
    private boolean mAnimate;
    private float mDeltaAngleY;
    private float mDeltaAngleZ;
    private float mInitAngleY;
    private float mInitAngleZ;
    private ArrayList<SnowDrop> mSnowDropList;

    private class SnowDrop {
        private int alpha;
        private float alphaReduceRate;
        private int drawnCount;
        private int originalYVelocity;
        private Paint paint;
        private float rotateAngleY;
        private float rotateAngleZ;
        private int snowRadius;
        private int snowRadiusDelta;
        private int updateCondition;
        private float x;
        private int xNoiseVelocity;
        private float y;
        private int yNoiseVelocity;
        private int yVelocity;

        public SnowDrop() {
            this.x = -1.0f;
            this.xNoiseVelocity = 0;
            this.y = -1.0f;
            this.originalYVelocity = 0;
            this.yVelocity = 0;
            this.yNoiseVelocity = 0;
            this.alpha = 255;
            this.alphaReduceRate = 0.0f;
            this.snowRadius = 0;
            this.snowRadiusDelta = 0;
            this.drawnCount = 0;
            this.updateCondition = 0;
            this.rotateAngleY = 0.0f;
            this.rotateAngleZ = 0.0f;
            this.paint = new Paint();
            init();
        }

        public boolean draw(Canvas canvas) {
            Random r = new Random();
            if (((float) canvas.getHeight()) <= this.y || this.alpha <= 0 || this.snowRadius < 20 || this.snowRadius > 90) {
                this.x = -1.0f;
                this.y = ((float) r.nextInt(canvas.getHeight())) / 3.0f;
                this.alpha = 255;
                this.drawnCount = 0;
                return false;
            }
            if (this.x == -1.0f) {
                this.x = (float) r.nextInt(canvas.getWidth());
            }
            if (this.y == -1.0f) {
                this.y = (float) r.nextInt(canvas.getHeight());
            }
            this.paint.setAlpha(this.alpha);
            canvas.drawCircle(this.x, this.y, (float) this.snowRadius, this.paint);
            this.y += (float) this.yVelocity;
            this.x += (float) this.xNoiseVelocity;
            if (this.drawnCount % this.updateCondition == 0) {
                this.xNoiseVelocity = -this.xNoiseVelocity;
            }
            if (this.rotateAngleZ > 0.0f) {
                this.xNoiseVelocity = -Math.abs(this.xNoiseVelocity);
            } else {
                this.xNoiseVelocity = Math.abs(this.xNoiseVelocity);
            }
            if (this.drawnCount % 5 == 0) {
                this.snowRadius += this.snowRadiusDelta;
            }
            this.alpha = (int) (((float) this.alpha) - this.alphaReduceRate);
            this.drawnCount++;
            return true;
        }

        public void recycle() {
            reset();
            init();
        }

        public void updateRoatationInfo(float x, float y, float z) {
            this.rotateAngleY = y;
            if (this.rotateAngleY != 0.0f) {
                if (this.rotateAngleY > 0.0f) {
                    this.snowRadiusDelta = 1;
                } else {
                    this.snowRadiusDelta = -1;
                }
                this.yVelocity = (int) ((((float) this.yVelocity) * (1.0f - (Math.abs(this.rotateAngleY) / 50.0f))) + (((float) this.yNoiseVelocity) * (Math.abs(this.rotateAngleY) / 50.0f)));
            } else {
                this.yVelocity = this.originalYVelocity;
                this.snowRadiusDelta = 0;
            }
            this.rotateAngleZ = z;
        }

        private void init() {
            Random r = new Random();
            this.snowRadius = r.nextInt(Stars.CIRCLE_COUNT) + 30;
            this.originalYVelocity = r.nextInt(RainSurfaceView.RAIN_LEVEL_RAINSTORM) + 1;
            this.yVelocity = (int) (((float) this.originalYVelocity) * (1.0f - (this.rotateAngleY / 50.0f)));
            this.xNoiseVelocity = r.nextInt(RainSurfaceView.RAIN_LEVEL_SHOWER) + 1;
            this.yNoiseVelocity = r.nextInt(RainSurfaceView.RAIN_LEVEL_SHOWER) + 1;
            if (r.nextBoolean()) {
                this.yNoiseVelocity = -this.yNoiseVelocity;
            }
            this.alpha = r.nextInt(AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS) + 128;
            this.alphaReduceRate = 0.0f;
            this.updateCondition = r.nextInt(300) + 60;
            int temp = r.nextInt(RainSurfaceView.RAIN_LEVEL_RAINSTORM);
            if (temp == 0) {
                this.paint.setColor(Color.rgb(0, 134, 216));
            } else if (temp == 1) {
                this.paint.setColor(Color.rgb(239, LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, R.styleable.AppCompatTheme_listDividerAlertDialog));
            } else if (temp == 2) {
                this.paint.setColor(Color.rgb(242, 249, 254));
            } else if (temp == 3) {
                this.paint.setColor(Color.rgb(189, 228, 248));
            }
            if (this.alpha > 192) {
                this.alphaReduceRate = 1.0f;
            }
        }

        private void reset() {
            this.x = -1.0f;
            this.y = -1.0f;
            this.snowRadius = 0;
            this.xNoiseVelocity = 0;
            this.yVelocity = 0;
            this.drawnCount = 0;
            this.updateCondition = 0;
            this.alpha = 255;
            this.alphaReduceRate = 0.0f;
            this.snowRadiusDelta = 0;
            this.originalYVelocity = 0;
            this.rotateAngleY = 0.0f;
            this.rotateAngleZ = 0.0f;
            this.yNoiseVelocity = 0;
        }
    }

    static {
        BACKGROUND_COLOR = Color.rgb(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK);
    }

    public SnowView(Context context) {
        this(context, null);
    }

    public SnowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SnowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mSnowDropList = null;
        this.mAnimate = false;
        this.mInitAngleZ = Float.MAX_VALUE;
        this.mDeltaAngleZ = 0.0f;
        this.mInitAngleY = Float.MAX_VALUE;
        this.mDeltaAngleY = 0.0f;
        init();
    }

    private void init() {
        setBackgroundColor(BACKGROUND_COLOR);
        this.mSnowDropList = new ArrayList();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mAnimate) {
            while (this.mSnowDropList.size() < 25) {
                this.mSnowDropList.add(new SnowDrop());
            }
            float rotateAngleZ = AutoScrollHelper.RELATIVE_UNSPECIFIED;
            if (Math.abs(this.mDeltaAngleZ) > 10.0f) {
                if (this.mDeltaAngleZ > 10.0f) {
                    rotateAngleZ = Math.min(this.mDeltaAngleZ - 10.0f, 30.0f);
                } else if (this.mDeltaAngleZ < -10.0f) {
                    rotateAngleZ = Math.max(this.mDeltaAngleZ + 10.0f, -30.0f);
                }
            }
            float rotateAngleY = AutoScrollHelper.RELATIVE_UNSPECIFIED;
            if (Math.abs(this.mDeltaAngleY) > 20.0f) {
                if (this.mDeltaAngleY > 20.0f) {
                    rotateAngleY = Math.min(this.mDeltaAngleY - 20.0f, 50.0f);
                } else if (this.mDeltaAngleY < -20.0f) {
                    rotateAngleY = Math.max(this.mDeltaAngleY + 20.0f, -50.0f);
                }
            }
            for (int i = 0; i < this.mSnowDropList.size(); i++) {
                SnowDrop snow = (SnowDrop) this.mSnowDropList.get(i);
                snow.updateRoatationInfo(AutoScrollHelper.RELATIVE_UNSPECIFIED, rotateAngleY, rotateAngleZ);
                if (!snow.draw(canvas)) {
                    ((SnowDrop) this.mSnowDropList.get(i)).recycle();
                }
            }
            invalidate();
        }
    }

    public void startAnimate() {
        this.mAnimate = true;
        invalidate();
    }

    public void stopAnimate() {
        this.mAnimate = false;
        this.mInitAngleZ = Float.MAX_VALUE;
        this.mInitAngleY = Float.MAX_VALUE;
    }

    public void onPageSelected(boolean isCurrent) {
    }

    protected void onCreateOrientationInfoListener() {
        this.mListener = new OrientationInfoListener() {
            public void onOrientationInfoChange(float x, float y, float z) {
                if (SnowView.this.mInitAngleZ == Float.MAX_VALUE) {
                    SnowView.this.mInitAngleZ = z;
                } else {
                    SnowView.this.mDeltaAngleZ = SnowView.this.mInitAngleZ - z;
                }
                if (SnowView.this.mInitAngleY == Float.MAX_VALUE) {
                    SnowView.this.mInitAngleY = y;
                } else {
                    SnowView.this.mDeltaAngleY = SnowView.this.mInitAngleY - y;
                }
            }
        };
    }
}
