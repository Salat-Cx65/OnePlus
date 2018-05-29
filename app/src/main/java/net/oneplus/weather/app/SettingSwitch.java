package net.oneplus.weather.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class SettingSwitch extends LinearLayout implements OnTouchListener {
    private float downX;
    private OnChangedListener listener;
    private int mCheckedTxtColor;
    private int mHeight;
    private boolean mIsInit;
    private boolean mIsMoving;
    private TextView mLeftText;
    private boolean mMove2Right;
    private float mOffset;
    private long mOldtime;
    private long mOldtime1;
    private boolean mOnSlip;
    private Paint mPaint;
    private TextView mRightText;
    private float mSpeed;
    private boolean mStatus;
    private float mTempOffset;
    private float mTop;
    private int mUncheckedTxtColor;
    private int mWidth;
    private float nowX;

    public static interface OnChangedListener {
        void OnChanged(SettingSwitch settingSwitch, boolean z);
    }

    public SettingSwitch(Context context) {
        this(context, null);
    }

    public SettingSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mOnSlip = false;
        this.mStatus = true;
        this.mIsInit = true;
        this.mIsMoving = false;
        this.mMove2Right = false;
        this.mOldtime = 0;
        this.mOldtime1 = 0;
        this.mSpeed = 0.0f;
        this.mTop = 0.0f;
        this.mTempOffset = 0.0f;
        initView(context);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingSwitch);
            this.mLeftText.setText(a.getString(RainSurfaceView.RAIN_LEVEL_SHOWER));
            this.mRightText.setText(a.getString(RainSurfaceView.RAIN_LEVEL_DOWNPOUR));
            this.mCheckedTxtColor = a.getColor(1, getResources().getColor(R.color.setting_text_white));
            this.mUncheckedTxtColor = a.getColor(RainSurfaceView.RAIN_LEVEL_RAINSTORM, getResources().getColor(R.color.setting_text_gray));
            this.mStatus = a.getBoolean(0, true);
            a.recycle();
        }
        initData(context);
    }

    private void initData(Context context) {
        setTextColor();
        setBackgroundColor(0);
        LayoutParams lParams = new LayoutParams(156, 63);
        addView(this.mLeftText, lParams);
        addView(this.mRightText, lParams);
    }

    private void initView(Context context) {
        this.mPaint = new Paint();
        this.mLeftText = new TextView(context);
        this.mRightText = new TextView(context);
        this.mLeftText.setGravity(ConnectionResult.SIGN_IN_FAILED);
        this.mRightText.setGravity(ConnectionResult.SIGN_IN_FAILED);
        this.mLeftText.setTextSize(13.0f);
        this.mRightText.setTextSize(13.0f);
        setOrientation(0);
        setOnTouchListener(this);
    }

    public void setCheckedTextColor(int color) {
        this.mCheckedTxtColor = color;
        setTextColor();
    }

    public void setUncheckedTextColor(int color) {
        this.mUncheckedTxtColor = color;
        setTextColor();
    }

    public void setState1Text(CharSequence text) {
        this.mLeftText.setText(text);
    }

    public void setState2Text(CharSequence text) {
        this.mRightText.setText(text);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("ffmsg", "ondraw----1");
        if (this.mIsInit) {
            Log.i("ffmsg", "ondraw----2");
            this.mTop = (float) getPaddingTop();
            this.mHeight = (getHeight() - getPaddingBottom()) - getPaddingTop();
            this.mWidth = getWidth();
            Log.i("ffmsg", "height:" + this.mHeight + "             width:" + (this.mWidth / 2));
            if (!this.mStatus) {
                this.mOffset = (float) (this.mWidth / 2);
                this.mMove2Right = true;
            }
            setStatus(this.mStatus);
            this.mLeftText.setWidth(this.mWidth / 2);
            this.mLeftText.setHeight(this.mHeight);
            this.mRightText.setWidth(this.mWidth / 2);
            this.mRightText.setHeight(this.mHeight);
            this.mIsInit = false;
        } else {
            Log.i("ffmsg", "ondraw----3");
            if (this.mOnSlip) {
                this.mSpeed = 0.5f;
                this.mOldtime1 = 0;
            } else {
                long curTime = System.currentTimeMillis();
                int t = this.mOldtime1 == 0 ? 0 : (int) (curTime - this.mOldtime1);
                this.mOldtime1 = curTime;
                this.mSpeed += 0.008f * ((float) t);
                if (this.mMove2Right && this.mOffset < ((float) this.mWidth) / 2.0f) {
                    this.mOffset += this.mSpeed * ((float) (t + 1));
                } else if (!this.mMove2Right && this.mOffset > 0.0f) {
                    this.mOffset -= this.mSpeed * ((float) (t + 1));
                }
                if (this.mOffset == 0.0f) {
                    this.mSpeed = 0.5f;
                    this.mOldtime1 = 0;
                    this.mIsMoving = false;
                    setStatus(true);
                } else if (((float) this.mWidth) / 2.0f == this.mOffset) {
                    this.mSpeed = 0.5f;
                    this.mOldtime1 = 0;
                    this.mIsMoving = false;
                    setStatus(false);
                } else {
                    this.mIsMoving = true;
                }
            }
            if (this.mOffset < 0.0f) {
                this.mOffset = 0.0f;
            } else if (this.mOffset > ((float) this.mWidth) / 2.0f) {
                this.mOffset = ((float) this.mWidth) / 2.0f;
            }
        }
        this.mPaint.setColor(Color.argb(MotionEventCompat.ACTION_MASK, 237, 237, 237));
        canvas.drawRect(AutoScrollHelper.RELATIVE_UNSPECIFIED, this.mTop, (float) this.mWidth, this.mTop + ((float) this.mHeight), this.mPaint);
        this.mPaint.setColor(Color.argb(MotionEventCompat.ACTION_MASK, R.styleable.OneplusTheme_op_elevation, 163, 245));
        canvas.drawRect(0.0f + this.mOffset, this.mTop, this.mOffset + (((float) this.mWidth) / 2.0f), this.mTop + ((float) this.mHeight), this.mPaint);
        if (this.mIsMoving) {
            invalidate();
        }
    }

    private void setStatus(boolean state) {
        if (this.mStatus == (!state ? 1 : null) && this.listener != null) {
            this.listener.OnChanged(this, state);
        }
        this.mStatus = state;
        setTextColor();
    }

    private void setTextColor() {
        if (this.mStatus) {
            this.mLeftText.setTextColor(this.mCheckedTxtColor);
            this.mRightText.setTextColor(this.mUncheckedTxtColor);
            return;
        }
        this.mLeftText.setTextColor(this.mUncheckedTxtColor);
        this.mRightText.setTextColor(this.mCheckedTxtColor);
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                this.mOldtime = System.currentTimeMillis();
                if (event.getX() > ((float) this.mWidth) || event.getY() > ((float) getHeight())) {
                    return false;
                }
                this.mOnSlip = true;
                this.downX = event.getX();
                this.nowX = this.downX;
                this.mTempOffset = this.mOffset;
                break;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                if (this.mStatus && this.mOffset >= ((float) this.mWidth) / 4.0f) {
                    this.mMove2Right = true;
                } else if (!this.mStatus && this.mOffset < ((float) this.mWidth) / 4.0f) {
                    this.mMove2Right = false;
                }
                if (System.currentTimeMillis() - this.mOldtime < 200 && Math.abs(this.nowX - this.downX) < 5.0f) {
                    boolean z;
                    if (this.mMove2Right) {
                        z = false;
                    } else {
                        z = true;
                    }
                    this.mMove2Right = z;
                }
                this.mOnSlip = false;
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                this.nowX = event.getX();
                this.mOffset = (this.nowX - this.downX) + this.mTempOffset;
                break;
        }
        invalidate();
        return true;
    }

    public void setOnChangedListener(OnChangedListener listener) {
        this.listener = listener;
    }

    public void setChecked(boolean checked) {
        this.mMove2Right = !checked;
        if (this.mIsInit) {
            this.mStatus = checked;
        }
        invalidate();
    }
}
