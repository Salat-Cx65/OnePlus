package net.oneplus.weather.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationRequest;
import java.util.List;
import net.oneplus.weather.R;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.util.DateTimeUtils;
import net.oneplus.weather.util.SystemSetting;
import net.oneplus.weather.util.UIUtil;
import net.oneplus.weather.util.WeatherResHelper;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class WeatherCircleView extends LinearLayout {
    private static final float ALL_ARC = 360.0f;
    public static final int ARC_DIN = 12;
    public static final int DIN_ANGEL = 15;
    private static final float END_ARC = 360.0f;
    public static final int START_ANGEL_0 = 0;
    public static final int START_ANGEL_180 = 180;
    public static final int START_ANGEL_270 = 270;
    public static final int START_ANGEL_90 = 90;
    private static final float START_ARC = 135.0f;
    public int START_0_ANGEL;
    public int START_10_ANGEL;
    public int START_11_ANGEL;
    public int START_12_ANGEL;
    public int START_13_ANGEL;
    public int START_14_ANGEL;
    public int START_15_ANGEL;
    public int START_16_ANGEL;
    public int START_17_ANGEL;
    public int START_18_ANGEL;
    public int START_19_ANGEL;
    public int START_1_ANGEL;
    public int START_20_ANGEL;
    public int START_21_ANGEL;
    public int START_22_ANGEL;
    public int START_23_ANGEL;
    public int START_24_ANGEL;
    public int START_2_ANGEL;
    public int START_3_ANGEL;
    public int START_4_ANGEL;
    public int START_5_ANGEL;
    public int START_6_ANGEL;
    public int START_7_ANGEL;
    public int START_8_ANGEL;
    public int START_9_ANGEL;
    private double angle;
    private Paint drawArc;
    private Paint drawArcDay;
    private Paint drawArcNight;
    private Paint drawArcShaper;
    private Paint drawBigDot;
    private Paint drawCircle;
    private Paint drawSmallDot;
    private int fromColor;
    private boolean isNeedShaper;
    private Paint mCenterTempPaint;
    private Paint mCenterTypePaint;
    private float mCircleradius;
    private Context mContext;
    private CenterPoint mCpoint;
    private int mCurDotRadius;
    private int mCurrentIconId;
    private String mCurrentTemp;
    private double mCurrentThumbradius;
    private float mCurrentThumbradiusX;
    private float mCurrentThumbradiusY;
    private String mCurrentType;
    private int mCurrentWeatherId;
    private int mDayEndAngle;
    private int mDayStartAngle;
    private int mDegrees;
    private int mDotColor;
    private float mDotPosition;
    private int mDotRadius;
    private int mDotTouchArea;
    public Handler mHandler;
    private boolean mIsDayMode;
    private double mLastThumbradius;
    private int mNightEndAngle;
    private int mNightStartAngle;
    private List<RootWeather> mOpWeatherHours;
    private int mPadding;
    private Paint mSunPaint;
    private Bitmap mSunRiseBitmap;
    private long mSunRiseTime;
    private float mSunRiseX;
    private float mSunRiseY;
    private Bitmap mSunSetBitmap;
    private long mSunSetTime;
    private float mSunSetX;
    private float mSunSetY;
    private float mThumbX;
    private float mThumbY;
    private Paint mTimePaint;
    private String mTimeZone;
    private RectF rectF;
    private double thumbradius;
    private int toColor;
    private int view;
    private float[] xy;

    public static class CenterPoint {
        int h;
        int w;
        int x;
        int y;
    }

    static /* synthetic */ double access$008(WeatherCircleView x0) {
        double d = x0.mLastThumbradius;
        x0.mLastThumbradius = 1.0d + d;
        return d;
    }

    static /* synthetic */ double access$010(WeatherCircleView x0) {
        double d = x0.mLastThumbradius;
        x0.mLastThumbradius = d - 1.0d;
        return d;
    }

    public WeatherCircleView(Context context) {
        super(context);
        this.START_0_ANGEL = 90;
        this.START_1_ANGEL = this.START_0_ANGEL + 15;
        this.START_2_ANGEL = this.START_1_ANGEL + 15;
        this.START_3_ANGEL = this.START_2_ANGEL + 15;
        this.START_4_ANGEL = this.START_3_ANGEL + 15;
        this.START_5_ANGEL = this.START_4_ANGEL + 15;
        this.START_6_ANGEL = this.START_5_ANGEL + 15;
        this.START_7_ANGEL = this.START_6_ANGEL + 15;
        this.START_8_ANGEL = this.START_7_ANGEL + 15;
        this.START_9_ANGEL = this.START_8_ANGEL + 15;
        this.START_10_ANGEL = this.START_9_ANGEL + 15;
        this.START_11_ANGEL = this.START_10_ANGEL + 15;
        this.START_12_ANGEL = this.START_11_ANGEL + 15;
        this.START_13_ANGEL = this.START_12_ANGEL + 15;
        this.START_14_ANGEL = this.START_13_ANGEL + 15;
        this.START_15_ANGEL = this.START_14_ANGEL + 15;
        this.START_16_ANGEL = this.START_15_ANGEL + 15;
        this.START_17_ANGEL = this.START_16_ANGEL + 15;
        this.START_18_ANGEL = this.START_17_ANGEL + 15;
        this.START_19_ANGEL = this.START_18_ANGEL + 15;
        this.START_20_ANGEL = this.START_19_ANGEL + 15;
        this.START_21_ANGEL = this.START_20_ANGEL + 15;
        this.START_22_ANGEL = this.START_21_ANGEL + 15;
        this.START_23_ANGEL = this.START_22_ANGEL + 15;
        this.START_24_ANGEL = this.START_23_ANGEL + 15;
        this.mCircleradius = 300.0f;
        this.mPadding = 75;
        this.mDotPosition = 0.0f;
        this.mDotRadius = 50;
        this.mCurDotRadius = 30;
        this.mDotTouchArea = 100;
        this.thumbradius = 270.0d;
        this.view = 425;
        this.mDegrees = 10;
        this.mCurrentTemp = "--\u00b0";
        this.mCurrentType = "Sunny";
        this.mCurrentWeatherId = 0;
        this.mIsDayMode = true;
        this.mTimeZone = null;
        this.mHandler = new Handler() {
        };
        this.mContext = context;
        initPaint();
    }

    public WeatherCircleView(Context context, AttributeSet set) {
        super(context, set);
        this.START_0_ANGEL = 90;
        this.START_1_ANGEL = this.START_0_ANGEL + 15;
        this.START_2_ANGEL = this.START_1_ANGEL + 15;
        this.START_3_ANGEL = this.START_2_ANGEL + 15;
        this.START_4_ANGEL = this.START_3_ANGEL + 15;
        this.START_5_ANGEL = this.START_4_ANGEL + 15;
        this.START_6_ANGEL = this.START_5_ANGEL + 15;
        this.START_7_ANGEL = this.START_6_ANGEL + 15;
        this.START_8_ANGEL = this.START_7_ANGEL + 15;
        this.START_9_ANGEL = this.START_8_ANGEL + 15;
        this.START_10_ANGEL = this.START_9_ANGEL + 15;
        this.START_11_ANGEL = this.START_10_ANGEL + 15;
        this.START_12_ANGEL = this.START_11_ANGEL + 15;
        this.START_13_ANGEL = this.START_12_ANGEL + 15;
        this.START_14_ANGEL = this.START_13_ANGEL + 15;
        this.START_15_ANGEL = this.START_14_ANGEL + 15;
        this.START_16_ANGEL = this.START_15_ANGEL + 15;
        this.START_17_ANGEL = this.START_16_ANGEL + 15;
        this.START_18_ANGEL = this.START_17_ANGEL + 15;
        this.START_19_ANGEL = this.START_18_ANGEL + 15;
        this.START_20_ANGEL = this.START_19_ANGEL + 15;
        this.START_21_ANGEL = this.START_20_ANGEL + 15;
        this.START_22_ANGEL = this.START_21_ANGEL + 15;
        this.START_23_ANGEL = this.START_22_ANGEL + 15;
        this.START_24_ANGEL = this.START_23_ANGEL + 15;
        this.mCircleradius = 300.0f;
        this.mPadding = 75;
        this.mDotPosition = 0.0f;
        this.mDotRadius = 50;
        this.mCurDotRadius = 30;
        this.mDotTouchArea = 100;
        this.thumbradius = 270.0d;
        this.view = 425;
        this.mDegrees = 10;
        this.mCurrentTemp = "--\u00b0";
        this.mCurrentType = "Sunny";
        this.mCurrentWeatherId = 0;
        this.mIsDayMode = true;
        this.mTimeZone = null;
        this.mHandler = new Handler() {
        };
        this.mContext = context;
        initPaint();
    }

    @SuppressLint({"NewApi"})
    public WeatherCircleView(Context context, AttributeSet set, int defStyle) {
        super(context, set, defStyle);
        this.START_0_ANGEL = 90;
        this.START_1_ANGEL = this.START_0_ANGEL + 15;
        this.START_2_ANGEL = this.START_1_ANGEL + 15;
        this.START_3_ANGEL = this.START_2_ANGEL + 15;
        this.START_4_ANGEL = this.START_3_ANGEL + 15;
        this.START_5_ANGEL = this.START_4_ANGEL + 15;
        this.START_6_ANGEL = this.START_5_ANGEL + 15;
        this.START_7_ANGEL = this.START_6_ANGEL + 15;
        this.START_8_ANGEL = this.START_7_ANGEL + 15;
        this.START_9_ANGEL = this.START_8_ANGEL + 15;
        this.START_10_ANGEL = this.START_9_ANGEL + 15;
        this.START_11_ANGEL = this.START_10_ANGEL + 15;
        this.START_12_ANGEL = this.START_11_ANGEL + 15;
        this.START_13_ANGEL = this.START_12_ANGEL + 15;
        this.START_14_ANGEL = this.START_13_ANGEL + 15;
        this.START_15_ANGEL = this.START_14_ANGEL + 15;
        this.START_16_ANGEL = this.START_15_ANGEL + 15;
        this.START_17_ANGEL = this.START_16_ANGEL + 15;
        this.START_18_ANGEL = this.START_17_ANGEL + 15;
        this.START_19_ANGEL = this.START_18_ANGEL + 15;
        this.START_20_ANGEL = this.START_19_ANGEL + 15;
        this.START_21_ANGEL = this.START_20_ANGEL + 15;
        this.START_22_ANGEL = this.START_21_ANGEL + 15;
        this.START_23_ANGEL = this.START_22_ANGEL + 15;
        this.START_24_ANGEL = this.START_23_ANGEL + 15;
        this.mCircleradius = 300.0f;
        this.mPadding = 75;
        this.mDotPosition = 0.0f;
        this.mDotRadius = 50;
        this.mCurDotRadius = 30;
        this.mDotTouchArea = 100;
        this.thumbradius = 270.0d;
        this.view = 425;
        this.mDegrees = 10;
        this.mCurrentTemp = "--\u00b0";
        this.mCurrentType = "Sunny";
        this.mCurrentWeatherId = 0;
        this.mIsDayMode = true;
        this.mTimeZone = null;
        this.mHandler = new Handler() {
        };
        this.mContext = context;
        initPaint();
    }

    private void initPaint() {
        this.mCurrentIconId = 2131230998;
        this.mCurrentType = this.mContext.getString(R.string.weather_description_sunny);
        this.mCircleradius = (float) UIUtil.dip2px(this.mContext, 116.0f);
        this.mCpoint = new CenterPoint();
        setBackgroundColor(START_ANGEL_0);
        setGravity(ConnectionResult.SIGN_IN_FAILED);
        this.mTimePaint = new Paint();
        this.mTimePaint.setAntiAlias(true);
        this.mTimePaint.setColor(-1);
        this.mTimePaint.setTextSize((float) UIUtil.dip2px(this.mContext, 11.0f));
        this.mSunPaint = new Paint();
        this.mSunPaint.setAntiAlias(true);
        this.mSunPaint.setColor(-1);
        this.mSunPaint.setTextSize((float) UIUtil.dip2px(this.mContext, 11.0f));
        this.mCenterTempPaint = new Paint();
        this.mCenterTempPaint.setAntiAlias(true);
        this.mCenterTempPaint.setColor(-1);
        this.mCenterTempPaint.setTextSize((float) UIUtil.dip2px(this.mContext, 76.0f));
        this.mCenterTypePaint = new Paint();
        this.mCenterTypePaint.setAntiAlias(true);
        this.mCenterTypePaint.setColor(-1);
        this.mCenterTypePaint.setTextSize((float) UIUtil.dip2px(this.mContext, 16.0f));
        this.drawArc = new Paint();
        this.drawArc.setAntiAlias(true);
        this.drawArcNight = new Paint();
        this.drawArcNight.setAntiAlias(true);
        this.drawArcDay = new Paint();
        this.drawArcDay.setAntiAlias(true);
        this.drawArcShaper = new Paint();
        this.drawArcShaper.setAntiAlias(true);
        this.drawCircle = new Paint();
        this.drawCircle.setAntiAlias(true);
        this.drawBigDot = new Paint();
        this.drawBigDot.setAntiAlias(true);
        this.drawSmallDot = new Paint();
        this.drawSmallDot.setAntiAlias(true);
        this.mSunRiseBitmap = BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.sunrise);
        this.mSunSetBitmap = BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.sunset);
        setNightArcColor(Color.parseColor("#ff2563bb"));
        setNightArcStrokeWidth(UIUtil.dip2px(this.mContext, 2.0f));
        setDayArcColor(Color.parseColor("#fffcf7df"));
        setDayArcStrokeWidth(UIUtil.dip2px(this.mContext, 2.0f));
        setSunRiseSetTime(1423347225880L, 1423386963638L, null);
        setCenterCirclePadding(R.styleable.AppCompatTheme_listPreferredItemHeightLarge);
        setDotCircleRadius(UIUtil.dip2px(this.mContext, 10.0f));
        setDotColorBackground(-1);
        setDotPosition(90.0f);
    }

    public void setChildView(View view) {
    }

    public void setChildView(int id) {
    }

    public void setDataObj(Object obj) {
    }

    public void setArcShaperColor(int fromColor, int toColor, boolean isNeed) {
        this.fromColor = fromColor;
        this.toColor = toColor;
        this.isNeedShaper = isNeed;
        this.xy = goQuadrant(135.0d);
    }

    public void setDotCircleRadius(int radius) {
        if (this.mDotRadius >= this.mPadding) {
            throw new RuntimeException("The radius must be less than padding param");
        }
        this.mDotRadius = radius;
    }

    public void setArcStrokeWidth(int px, boolean isNeed) {
        if (isNeed) {
            this.drawArc.setStyle(Style.STROKE);
            this.drawArc.setStrokeWidth((float) px);
            this.drawArcShaper.setStyle(Style.STROKE);
            this.drawArcShaper.setStrokeWidth((float) (px + 4));
        }
    }

    public void setDotColorBackground(int color) {
        this.mDotColor = color;
        this.drawBigDot.setColor(color);
        this.drawSmallDot.setColor(color);
    }

    public void setDotPosition(float position) {
        if (position > 100.0f) {
            position = 100.0f;
        }
        if (position < 0.0f) {
            position = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        }
        this.mDotPosition = position;
    }

    private float[] goQuadrant(double thumbradius) {
        a = new float[2];
        float arcradius = this.mCircleradius + ((float) this.mPadding);
        a[0] = ((float) Math.sin(Math.toRadians(thumbradius))) * arcradius;
        a[1] = ((float) Math.cos(Math.toRadians(thumbradius))) * arcradius;
        return a;
    }

    public void setNightArcColor(int color) {
        this.drawArcNight.setColor(color);
    }

    public void setSunRiseSetTime(long sunRiseTime, long sunSetTime, String timeZone) {
        this.mSunRiseTime = sunRiseTime;
        this.mSunSetTime = sunSetTime;
        this.mTimeZone = timeZone;
        double d = (double) get24HrAngelByTime(DateTimeUtils.longTimeToHour(System.currentTimeMillis(), timeZone));
        this.mCurrentThumbradius = d;
        this.thumbradius = d;
        int surRise = DateTimeUtils.longTimeToHour(sunRiseTime, timeZone);
        int surSet = DateTimeUtils.longTimeToHour(sunSetTime, timeZone);
        System.out.println("setSunRiseSetTime--sunrise:" + getSunAngelByTime(surRise));
        System.out.println("setSunRiseSetTime--sunset:" + getSunAngelByTime(surSet));
        setDayAngel(getSunAngelByTime(surRise), getSunAngelByTime(surSet));
    }

    public void setDayMode(boolean isDayMode) {
        this.mIsDayMode = isDayMode;
    }

    public boolean getDayMode() {
        return this.mIsDayMode;
    }

    public int get24HrTimeByAngel(int angle) {
        if (this.mIsDayMode) {
            if (angle > 0 && angle < 15) {
                return START_ANGEL_0;
            }
            if (angle >= 15 && angle < 30) {
                return 1;
            }
            if (angle >= 30 && angle < 45) {
                return RainSurfaceView.RAIN_LEVEL_SHOWER;
            }
            if (angle >= 45 && angle < 60) {
                return RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
            }
            if (angle >= 60 && angle < 75) {
                return RainSurfaceView.RAIN_LEVEL_RAINSTORM;
            }
            if (angle >= 75 && angle < 90) {
                return RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
            }
            if (angle >= 90 && angle < 105) {
                return ConnectionResult.RESOLUTION_REQUIRED;
            }
            if (angle >= 105 && angle < 120) {
                return DetectedActivity.WALKING;
            }
            if (angle >= 120 && angle < 135) {
                return DetectedActivity.RUNNING;
            }
            if (angle >= 135 && angle < 150) {
                return ConnectionResult.SERVICE_INVALID;
            }
            if (angle >= 150 && angle < 165) {
                return ConnectionResult.DEVELOPER_ERROR;
            }
            if (angle >= 165 && angle < 180) {
                return ConnectionResult.LICENSE_CHECK_FAILED;
            }
            if (angle >= 180 && angle < 195) {
                return ARC_DIN;
            }
            if (angle >= 195 && angle < 210) {
                return ConnectionResult.CANCELED;
            }
            if (angle >= 210 && angle < 225) {
                return ConnectionResult.TIMEOUT;
            }
            if (angle >= 225 && angle < 240) {
                return DIN_ANGEL;
            }
            if (angle >= 240 && angle < 255) {
                return ConnectionResult.API_UNAVAILABLE;
            }
            if (angle >= 255 && angle < 270) {
                return ConnectionResult.SIGN_IN_FAILED;
            }
            if (angle >= 270 && angle < 285) {
                return ConnectionResult.SERVICE_UPDATING;
            }
            if (angle >= 285 && angle < 300) {
                return ConnectionResult.SERVICE_MISSING_PERMISSION;
            }
            if (angle >= 300 && angle < 315) {
                return ConnectionResult.RESTRICTED_PROFILE;
            }
            if (angle >= 315 && angle < 330) {
                return R.styleable.Toolbar_titleMargin;
            }
            if (angle < 330 || angle >= 345) {
                return angle >= 345 ? R.styleable.Toolbar_titleMarginEnd : START_ANGEL_0;
            } else {
                return R.styleable.Toolbar_titleMarginBottom;
            }
        } else if (angle > 180 && angle < 195) {
            return START_ANGEL_0;
        } else {
            if (angle >= 195 && angle < 210) {
                return 1;
            }
            if (angle >= 210 && angle < 225) {
                return RainSurfaceView.RAIN_LEVEL_SHOWER;
            }
            if (angle >= 225 && angle < 240) {
                return RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
            }
            if (angle >= 240 && angle < 255) {
                return RainSurfaceView.RAIN_LEVEL_RAINSTORM;
            }
            if (angle >= 255 && angle < 270) {
                return RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
            }
            if (angle >= 270 && angle < 285) {
                return ConnectionResult.RESOLUTION_REQUIRED;
            }
            if (angle >= 285 && angle < 300) {
                return DetectedActivity.WALKING;
            }
            if (angle >= 300 && angle < 315) {
                return DetectedActivity.RUNNING;
            }
            if (angle >= 315 && angle < 330) {
                return ConnectionResult.SERVICE_INVALID;
            }
            if (angle >= 330 && angle < 345) {
                return ConnectionResult.DEVELOPER_ERROR;
            }
            if (angle >= 345) {
                return ConnectionResult.LICENSE_CHECK_FAILED;
            }
            if (angle >= 0 && angle < 15) {
                return ARC_DIN;
            }
            if (angle >= 15 && angle < 30) {
                return ConnectionResult.CANCELED;
            }
            if (angle >= 30 && angle < 45) {
                return ConnectionResult.TIMEOUT;
            }
            if (angle >= 45 && angle < 60) {
                return DIN_ANGEL;
            }
            if (angle >= 60 && angle < 75) {
                return ConnectionResult.API_UNAVAILABLE;
            }
            if (angle >= 75 && angle < 90) {
                return ConnectionResult.SIGN_IN_FAILED;
            }
            if (angle >= 90 && angle < 105) {
                return ConnectionResult.SERVICE_UPDATING;
            }
            if (angle >= 105 && angle < 120) {
                return ConnectionResult.SERVICE_MISSING_PERMISSION;
            }
            if (angle >= 120 && angle < 135) {
                return ConnectionResult.RESTRICTED_PROFILE;
            }
            if (angle >= 135 && angle < 150) {
                return R.styleable.Toolbar_titleMargin;
            }
            if (angle < 150 || angle >= 165) {
                return (angle < 165 || angle >= 180) ? START_ANGEL_0 : R.styleable.Toolbar_titleMarginEnd;
            } else {
                return R.styleable.Toolbar_titleMarginBottom;
            }
        }
    }

    public int get24HrAngelByTime(int time) {
        if (this.mIsDayMode) {
            switch (time) {
                case START_ANGEL_0:
                    return START_ANGEL_0;
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    return 15;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    return 30;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    return 45;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    return 60;
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    return R.styleable.AppCompatTheme_listPreferredItemHeightLarge;
                case ConnectionResult.RESOLUTION_REQUIRED:
                    return START_ANGEL_90;
                case DetectedActivity.WALKING:
                    return LocationRequest.PRIORITY_NO_POWER;
                case DetectedActivity.RUNNING:
                    return 120;
                case ConnectionResult.SERVICE_INVALID:
                    return 135;
                case ConnectionResult.DEVELOPER_ERROR:
                    return 150;
                case ConnectionResult.LICENSE_CHECK_FAILED:
                    return 165;
                case ARC_DIN:
                    return START_ANGEL_180;
                case ConnectionResult.CANCELED:
                    return 195;
                case ConnectionResult.TIMEOUT:
                    return 210;
                case DIN_ANGEL:
                    return 225;
                case ConnectionResult.API_UNAVAILABLE:
                    return 240;
                case ConnectionResult.SIGN_IN_FAILED:
                    return MotionEventCompat.ACTION_MASK;
                case ConnectionResult.SERVICE_UPDATING:
                    return START_ANGEL_270;
                case ConnectionResult.SERVICE_MISSING_PERMISSION:
                    return 285;
                case ConnectionResult.RESTRICTED_PROFILE:
                    return 300;
                case R.styleable.Toolbar_titleMargin:
                    return 315;
                case R.styleable.Toolbar_titleMarginBottom:
                    return 330;
                case R.styleable.Toolbar_titleMarginEnd:
                    return 345;
                default:
                    return this.START_6_ANGEL;
            }
        }
        switch (time) {
            case START_ANGEL_0:
                return START_ANGEL_180;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return 195;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return 210;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return 225;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return 240;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return MotionEventCompat.ACTION_MASK;
            case ConnectionResult.RESOLUTION_REQUIRED:
                return START_ANGEL_270;
            case DetectedActivity.WALKING:
                return 285;
            case DetectedActivity.RUNNING:
                return 300;
            case ConnectionResult.SERVICE_INVALID:
                return 315;
            case ConnectionResult.DEVELOPER_ERROR:
                return 330;
            case ConnectionResult.LICENSE_CHECK_FAILED:
                return 345;
            case ARC_DIN:
                return START_ANGEL_0;
            case ConnectionResult.CANCELED:
                return 15;
            case ConnectionResult.TIMEOUT:
                return 30;
            case DIN_ANGEL:
                return 45;
            case ConnectionResult.API_UNAVAILABLE:
                return 60;
            case ConnectionResult.SIGN_IN_FAILED:
                return R.styleable.AppCompatTheme_listPreferredItemHeightLarge;
            case ConnectionResult.SERVICE_UPDATING:
                return START_ANGEL_90;
            case ConnectionResult.SERVICE_MISSING_PERMISSION:
                return LocationRequest.PRIORITY_NO_POWER;
            case ConnectionResult.RESTRICTED_PROFILE:
                return 120;
            case R.styleable.Toolbar_titleMargin:
                return 135;
            case R.styleable.Toolbar_titleMarginBottom:
                return 150;
            case R.styleable.Toolbar_titleMarginEnd:
                return 165;
            default:
                return this.START_6_ANGEL;
        }
    }

    private void refreshStartSunAngel() {
        if (this.mIsDayMode) {
            this.START_0_ANGEL = 90;
            this.START_1_ANGEL = this.START_0_ANGEL + 15;
            this.START_2_ANGEL = this.START_1_ANGEL + 15;
            this.START_3_ANGEL = this.START_2_ANGEL + 15;
            this.START_4_ANGEL = this.START_3_ANGEL + 15;
            this.START_5_ANGEL = this.START_4_ANGEL + 15;
            this.START_6_ANGEL = this.START_5_ANGEL + 15;
            this.START_7_ANGEL = this.START_6_ANGEL + 15;
            this.START_8_ANGEL = this.START_7_ANGEL + 15;
            this.START_9_ANGEL = this.START_8_ANGEL + 15;
            this.START_10_ANGEL = this.START_9_ANGEL + 15;
            this.START_11_ANGEL = this.START_10_ANGEL + 15;
            this.START_12_ANGEL = this.START_11_ANGEL + 15;
            this.START_13_ANGEL = this.START_12_ANGEL + 15;
            this.START_14_ANGEL = this.START_13_ANGEL + 15;
            this.START_15_ANGEL = this.START_14_ANGEL + 15;
            this.START_16_ANGEL = this.START_15_ANGEL + 15;
            this.START_17_ANGEL = this.START_16_ANGEL + 15;
            this.START_18_ANGEL = this.START_17_ANGEL + 15;
            this.START_19_ANGEL = this.START_18_ANGEL + 15;
            this.START_20_ANGEL = this.START_19_ANGEL + 15;
            this.START_21_ANGEL = this.START_20_ANGEL + 15;
            this.START_22_ANGEL = this.START_21_ANGEL + 15;
            this.START_23_ANGEL = this.START_22_ANGEL + 15;
            this.START_24_ANGEL = this.START_23_ANGEL + 15;
            return;
        }
        this.START_0_ANGEL = 270;
        this.START_1_ANGEL = this.START_0_ANGEL + 15;
        this.START_2_ANGEL = this.START_1_ANGEL + 15;
        this.START_3_ANGEL = this.START_2_ANGEL + 15;
        this.START_4_ANGEL = this.START_3_ANGEL + 15;
        this.START_5_ANGEL = this.START_4_ANGEL + 15;
        this.START_6_ANGEL = this.START_5_ANGEL + 15;
        this.START_7_ANGEL = 15;
        this.START_8_ANGEL = this.START_7_ANGEL + 15;
        this.START_9_ANGEL = this.START_8_ANGEL + 15;
        this.START_10_ANGEL = this.START_9_ANGEL + 15;
        this.START_11_ANGEL = this.START_10_ANGEL + 15;
        this.START_12_ANGEL = this.START_11_ANGEL + 15;
        this.START_13_ANGEL = this.START_12_ANGEL + 15;
        this.START_14_ANGEL = this.START_13_ANGEL + 15;
        this.START_15_ANGEL = this.START_14_ANGEL + 15;
        this.START_16_ANGEL = this.START_15_ANGEL + 15;
        this.START_17_ANGEL = this.START_16_ANGEL + 15;
        this.START_18_ANGEL = this.START_17_ANGEL + 15;
        this.START_19_ANGEL = this.START_18_ANGEL + 15;
        this.START_20_ANGEL = this.START_19_ANGEL + 15;
        this.START_21_ANGEL = this.START_20_ANGEL + 15;
        this.START_22_ANGEL = this.START_21_ANGEL + 15;
        this.START_23_ANGEL = this.START_22_ANGEL + 15;
        this.START_24_ANGEL = this.START_23_ANGEL + 15;
    }

    public int getSunAngelByTime(int time) {
        refreshStartSunAngel();
        switch (time) {
            case START_ANGEL_0:
                return this.START_0_ANGEL;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return this.START_1_ANGEL;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return this.START_2_ANGEL;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return this.START_3_ANGEL;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return this.START_4_ANGEL;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return this.START_5_ANGEL;
            case ConnectionResult.RESOLUTION_REQUIRED:
                return this.START_6_ANGEL;
            case DetectedActivity.WALKING:
                return this.START_7_ANGEL;
            case DetectedActivity.RUNNING:
                return this.START_8_ANGEL;
            case ConnectionResult.SERVICE_INVALID:
                return this.START_9_ANGEL;
            case ConnectionResult.DEVELOPER_ERROR:
                return this.START_10_ANGEL;
            case ConnectionResult.LICENSE_CHECK_FAILED:
                return this.START_11_ANGEL;
            case ARC_DIN:
                return this.START_12_ANGEL;
            case ConnectionResult.CANCELED:
                return this.START_13_ANGEL;
            case ConnectionResult.TIMEOUT:
                return this.START_14_ANGEL;
            case DIN_ANGEL:
                return this.START_15_ANGEL;
            case ConnectionResult.API_UNAVAILABLE:
                return this.START_16_ANGEL;
            case ConnectionResult.SIGN_IN_FAILED:
                return this.START_17_ANGEL;
            case ConnectionResult.SERVICE_UPDATING:
                return this.START_18_ANGEL;
            case ConnectionResult.SERVICE_MISSING_PERMISSION:
                return this.START_19_ANGEL;
            case ConnectionResult.RESTRICTED_PROFILE:
                return this.START_20_ANGEL;
            case R.styleable.Toolbar_titleMargin:
                return this.START_21_ANGEL;
            case R.styleable.Toolbar_titleMarginBottom:
                return this.START_22_ANGEL;
            case R.styleable.Toolbar_titleMarginEnd:
                return this.START_23_ANGEL;
            case R.styleable.Toolbar_titleMarginStart:
                return this.START_0_ANGEL;
            default:
                return this.START_6_ANGEL;
        }
    }

    public void setNightAngel(int startAngel, int endAngel) {
        int startAngle = startAngel + 6;
        if (startAngle >= 360) {
            startAngle -= 360;
        }
        this.mNightStartAngle = startAngle;
        this.mNightEndAngle = endAngel - 6;
        setDayAngel();
    }

    public void setNightAngel() {
        this.mNightStartAngle = this.mDayEndAngle + 12;
        this.mNightEndAngle = (this.mDayStartAngle + 360) - 12;
    }

    public void setNightArcStrokeWidth(int px) {
        this.drawArcNight.setStyle(Style.STROKE);
        this.drawArcNight.setStrokeWidth((float) px);
    }

    public void setDayAngel(int startAngel, int endAngel) {
        int startAngle = startAngel + 6;
        if (startAngle >= 360) {
            startAngle -= 360;
        }
        this.mDayStartAngle = startAngle;
        if (this.mIsDayMode) {
            this.mDayEndAngle = endAngel - 6;
        } else if (this.mDayStartAngle > 300) {
            this.mDayEndAngle = (endAngel + 360) - 6;
        } else if (this.mDayStartAngle < 90) {
            this.mDayEndAngle = endAngel - 6;
        }
        setNightAngel();
    }

    public void setDayAngel() {
        this.mDayStartAngle = this.mNightEndAngle + 12;
        if (this.mNightStartAngle - 12 >= 0) {
            this.mDayEndAngle = this.mNightEndAngle - 12;
        } else {
            this.mDayEndAngle = (this.mNightStartAngle + 360) - 12;
        }
    }

    public void setDayArcColor(int color) {
        this.drawArcDay.setColor(color);
    }

    public void setDayArcStrokeWidth(int px) {
        this.drawArcDay.setStyle(Style.STROKE);
        this.drawArcDay.setStrokeWidth((float) px);
    }

    public void setArcColorBackground(int color) {
        this.drawArc.setColor(color);
    }

    public void setCenterCircleColorBackground(int color) {
        this.drawCircle.setColor(color);
    }

    public void setCenterPoint(CenterPoint point) {
        this.mCpoint = point;
    }

    public void setCenterCircleRadius(float radius) {
        this.mCircleradius = radius;
    }

    public void setCenterCirclePadding(int padding) {
        this.mPadding = padding;
    }

    public void initRect() {
        this.rectF = new RectF();
        this.rectF.top = ((float) this.mCpoint.y) - (this.mCircleradius + ((float) this.mPadding));
        this.rectF.left = ((float) this.mCpoint.x) - (this.mCircleradius + ((float) this.mPadding));
        this.rectF.right = (((float) this.mCpoint.x) + this.mCircleradius) + ((float) this.mPadding);
        this.rectF.bottom = (((float) this.mCpoint.y) + this.mCircleradius) + ((float) this.mPadding);
    }

    private void moveToPosition(float x, float y) {
        this.angle = buildingradius((double) x, (double) y);
        postionAngle(this.angle);
        if (this.angle > 360.0d) {
            this.angle = 360.0d;
        }
        if (this.angle < 0.0d) {
            this.angle = 0.0d;
        }
        postionAngle(this.angle);
        this.thumbradius = this.angle;
        getPointXY(this.thumbradius);
        invalidate();
    }

    public void smoothMoveTo(float x, float y) {
        this.angle = buildingradius((double) x, (double) y);
        postionAngle(this.angle);
        this.thumbradius = this.angle;
        if (this.thumbradius > this.mLastThumbradius) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    WeatherCircleView.access$008(WeatherCircleView.this);
                    WeatherCircleView.this.getPointXY(WeatherCircleView.this.mLastThumbradius);
                    if (WeatherCircleView.this.mLastThumbradius == WeatherCircleView.this.thumbradius) {
                        WeatherCircleView.this.mHandler.removeCallbacks(this);
                    }
                    WeatherCircleView.this.mHandler.post(this);
                }
            });
        } else if (this.thumbradius < this.mLastThumbradius) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    WeatherCircleView.access$010(WeatherCircleView.this);
                    WeatherCircleView.this.getPointXY(WeatherCircleView.this.mLastThumbradius);
                    if (WeatherCircleView.this.mLastThumbradius == WeatherCircleView.this.thumbradius) {
                        WeatherCircleView.this.mHandler.removeCallbacks(this);
                    }
                    WeatherCircleView.this.mHandler.post(this);
                }
            });
        }
    }

    private void getPoint(double thumbradius, int i) {
        if (i == 0) {
            thumbradius += 0.0d;
        }
        float[] xy = goQuadrant(thumbradius);
        if (thumbradius >= 0.0d && thumbradius < 90.0d) {
            this.mThumbX = ((float) this.mCpoint.x) + Math.abs(xy[1]);
            this.mThumbY = ((float) this.mCpoint.y) + Math.abs(xy[0]);
        }
        if (thumbradius >= 90.0d && thumbradius < 180.0d) {
            this.mThumbX = ((float) this.mCpoint.x) - Math.abs(xy[1]);
            this.mThumbY = ((float) this.mCpoint.y) + Math.abs(xy[0]);
        }
        if (thumbradius >= 180.0d && thumbradius < 270.0d) {
            this.mThumbX = ((float) this.mCpoint.x) - Math.abs(xy[1]);
            this.mThumbY = ((float) this.mCpoint.y) - Math.abs(xy[0]);
        }
        if (thumbradius >= 270.0d && thumbradius < 360.0d) {
            this.mThumbX = ((float) this.mCpoint.x) + Math.abs(xy[1]);
            this.mThumbY = ((float) this.mCpoint.y) - Math.abs(xy[0]);
        }
        invalidate();
    }

    private void getNightPoint(double endAngel) {
        float[] xy = goQuadrant(endAngel);
        if (endAngel >= 0.0d && endAngel < 90.0d) {
            this.mSunRiseX = ((float) this.mCpoint.x) + Math.abs(xy[1]);
            this.mSunRiseY = ((float) this.mCpoint.y) + Math.abs(xy[0]);
        }
        if (endAngel >= 90.0d && endAngel < 180.0d) {
            this.mSunRiseX = ((float) this.mCpoint.x) - Math.abs(xy[1]);
            this.mSunRiseY = ((float) this.mCpoint.y) + Math.abs(xy[0]);
        }
        if (endAngel >= 180.0d && endAngel < 270.0d) {
            this.mSunRiseX = ((float) this.mCpoint.x) - Math.abs(xy[1]);
            this.mSunRiseY = ((float) this.mCpoint.y) - Math.abs(xy[0]);
        }
        if (endAngel >= 270.0d && endAngel < 360.0d) {
            this.mSunRiseX = ((float) this.mCpoint.x) + Math.abs(xy[1]);
            this.mSunRiseY = ((float) this.mCpoint.y) - Math.abs(xy[0]);
        }
    }

    private void getDayPoint(double endAngel) {
        float[] xy = goQuadrant(endAngel);
        if (endAngel >= 0.0d && endAngel < 90.0d) {
            this.mSunSetX = ((float) this.mCpoint.x) + Math.abs(xy[1]);
            this.mSunSetY = ((float) this.mCpoint.y) + Math.abs(xy[0]);
        }
        if (endAngel >= 90.0d && endAngel < 180.0d) {
            this.mSunSetX = ((float) this.mCpoint.x) - Math.abs(xy[1]);
            this.mSunSetY = ((float) this.mCpoint.y) + Math.abs(xy[0]);
        }
        if (endAngel >= 180.0d && endAngel < 270.0d) {
            this.mSunSetX = ((float) this.mCpoint.x) - Math.abs(xy[1]);
            this.mSunSetY = ((float) this.mCpoint.y) - Math.abs(xy[0]);
        }
        if (endAngel >= 270.0d && endAngel < 360.0d) {
            this.mSunSetX = ((float) this.mCpoint.x) + Math.abs(xy[1]);
            this.mSunSetY = ((float) this.mCpoint.y) - Math.abs(xy[0]);
        }
    }

    private void postionAngle(double angle) {
        this.mDotPosition = (float) ((angle - 0.0d) / 3.5999999046325684d);
    }

    private double buildingradius(double x, double y) {
        double angle = (Math.atan2(Math.abs(x - ((double) this.mCpoint.x)), Math.abs(y - ((double) this.mCpoint.y))) * 180.0d) / 3.141592653589793d;
        switch (getQuadrant(x, y)) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                angle += 180.0d;
                return angle;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                angle = 180.0d - angle;
                return angle;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return angle;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                angle = 360.0d - angle;
                return angle;
            default:
                return angle;
        }
    }

    private int getQuadrant(double x, double y) {
        if (((double) this.mCpoint.x) - x >= 0.0d && ((double) this.mCpoint.y) - y >= 0.0d) {
            return RainSurfaceView.RAIN_LEVEL_SHOWER;
        }
        if (((double) this.mCpoint.x) - x <= 0.0d && ((double) this.mCpoint.y) - y >= 0.0d) {
            return 1;
        }
        if (((double) this.mCpoint.x) - x < 0.0d || ((double) this.mCpoint.y) - y > 0.0d) {
            return (((double) this.mCpoint.x) - x > 0.0d || ((double) this.mCpoint.y) - y > 0.0d) ? START_ANGEL_0 : RainSurfaceView.RAIN_LEVEL_RAINSTORM;
        } else {
            return RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
        }
    }

    private boolean isTouchThumb(float x, float y) {
        return inside(x, y);
    }

    private boolean inside(float x, float y) {
        return new Rect((int) ((this.mThumbX - ((float) this.mDotRadius)) - ((float) this.mDotTouchArea)), (int) ((this.mThumbY - ((float) this.mDotRadius)) - ((float) this.mDotTouchArea)), (int) ((this.mThumbX + ((float) this.mDotRadius)) + ((float) this.mDotTouchArea)), (int) ((this.mThumbY + ((float) this.mDotRadius)) + ((float) this.mDotTouchArea))).contains((int) x, (int) y);
    }

    public boolean isOnTheCircle(float x, float y) {
        return (Math.abs(x - ((float) this.mCpoint.x)) >= 200.0f && Math.abs(x - ((float) this.mCpoint.x)) <= 400.0f) || (Math.abs(y - ((float) this.mCpoint.y)) >= 200.0f && Math.abs(y - ((float) this.mCpoint.y)) <= 400.0f);
    }

    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        initRect();
        doDrawTouchMove(canvas);
    }

    public void doDrawAutoMove(Canvas canvas) {
        int sunriseAngle;
        int sunsetAngle;
        getPoint(this.thumbradius, 1);
        int nightEndAngle = this.mNightEndAngle + 6;
        if (nightEndAngle >= 360) {
            sunriseAngle = nightEndAngle - 360;
        } else {
            sunriseAngle = nightEndAngle;
        }
        getNightPoint((double) sunriseAngle);
        int dayEndAngle = this.mDayEndAngle + 6;
        if (dayEndAngle >= 360) {
            sunsetAngle = dayEndAngle - 360;
        } else {
            sunsetAngle = dayEndAngle;
        }
        getDayPoint((double) sunsetAngle);
        canvas.drawArc(this.rectF, (float) this.mNightStartAngle, (float) (this.mNightEndAngle - this.mNightStartAngle), false, this.drawArcNight);
        canvas.drawArc(this.rectF, (float) this.mDayStartAngle, (float) (this.mDayEndAngle - this.mDayStartAngle), false, this.drawArcDay);
        canvas.drawBitmap(this.mSunRiseBitmap, this.mSunRiseX - ((float) (this.mSunRiseBitmap.getWidth() / 2)), this.mSunRiseY - ((float) (this.mSunRiseBitmap.getHeight() / 2)), this.drawArcDay);
        String sunRiseTime = DateTimeUtils.longTimeToHourMinute(this.mSunRiseTime, this.mTimeZone);
        if (!TextUtils.isEmpty(sunRiseTime)) {
            canvas.drawText(sunRiseTime, (this.mSunRiseX - ((float) (this.mSunRiseBitmap.getWidth() / 2))) - ((float) UIUtil.dip2px(this.mContext, 29.0f)), (this.mSunRiseY - ((float) (this.mSunRiseBitmap.getHeight() / 2))) + ((float) UIUtil.dip2px(this.mContext, 14.0f)), this.mTimePaint);
        }
        if (!(this.mSunSetBitmap == null || this.mSunSetBitmap.isRecycled())) {
            canvas.drawBitmap(this.mSunSetBitmap, this.mSunSetX - ((float) (this.mSunSetBitmap.getWidth() / 2)), this.mSunSetY - ((float) (this.mSunSetBitmap.getHeight() / 2)), this.drawArcDay);
            String sunSetTime = DateTimeUtils.longTimeToHourMinute(this.mSunSetTime, this.mTimeZone);
            if (!TextUtils.isEmpty(sunSetTime)) {
                canvas.drawText(sunSetTime, (this.mSunSetX - ((float) (this.mSunSetBitmap.getWidth() / 2))) + ((float) UIUtil.dip2px(this.mContext, 20.0f)), (this.mSunSetY - ((float) (this.mSunSetBitmap.getHeight() / 2))) + ((float) UIUtil.dip2px(this.mContext, 13.0f)), this.mTimePaint);
            }
        }
        canvas.drawCircle(this.mThumbX, this.mThumbY, (float) this.mDotRadius, this.drawBigDot);
        startMove();
    }

    public void doDrawTouchMove(Canvas canvas) {
        int sunriseAngle;
        int sunsetAngle;
        float sunRiseTimeX;
        double mThumbradiusChange;
        double mCurrentThumbradiusChange;
        getPointXY(this.thumbradius);
        int nightEndAngle = this.mNightEndAngle + 6;
        if (nightEndAngle >= 360) {
            sunriseAngle = nightEndAngle - 360;
        } else {
            sunriseAngle = nightEndAngle;
        }
        getNightPoint((double) sunriseAngle);
        int dayEndAngle = this.mDayEndAngle + 6;
        if (dayEndAngle >= 360) {
            sunsetAngle = dayEndAngle - 360;
        } else {
            sunsetAngle = dayEndAngle;
        }
        getDayPoint((double) sunsetAngle);
        canvas.drawArc(this.rectF, (float) this.mNightStartAngle, (float) (this.mNightEndAngle - this.mNightStartAngle), false, this.drawArcNight);
        canvas.drawArc(this.rectF, (float) this.mDayStartAngle, (float) (this.mDayEndAngle - this.mDayStartAngle), false, this.drawArcDay);
        canvas.drawBitmap(this.mSunRiseBitmap, this.mSunRiseX - ((float) (this.mSunRiseBitmap.getWidth() / 2)), this.mSunRiseY - ((float) (this.mSunRiseBitmap.getHeight() / 2)), this.drawArcDay);
        String sunRiseTime = DateTimeUtils.longTimeToHourMinute(this.mSunRiseTime, this.mTimeZone);
        if (this.mIsDayMode) {
            sunRiseTimeX = (this.mSunRiseX - ((float) (this.mSunRiseBitmap.getWidth() / 2))) + ((float) UIUtil.dip2px(this.mContext, 25.0f));
        } else {
            sunRiseTimeX = (this.mSunRiseX - ((float) (this.mSunRiseBitmap.getWidth() / 2))) - ((float) UIUtil.dip2px(this.mContext, 34.0f));
        }
        if (!TextUtils.isEmpty(sunRiseTime)) {
            canvas.drawText(sunRiseTime, sunRiseTimeX, (this.mSunRiseY - ((float) (this.mSunRiseBitmap.getHeight() / 2))) + ((float) UIUtil.dip2px(this.mContext, 14.0f)), this.mSunPaint);
        }
        if (!(this.mSunSetBitmap == null || this.mSunSetBitmap.isRecycled())) {
            float sunSetTimeX;
            canvas.drawBitmap(this.mSunSetBitmap, this.mSunSetX - ((float) (this.mSunSetBitmap.getWidth() / 2)), this.mSunSetY - ((float) (this.mSunSetBitmap.getHeight() / 2)), this.drawArcDay);
            String sunSetTime = DateTimeUtils.longTimeToHourMinute(this.mSunSetTime, this.mTimeZone);
            if (this.mIsDayMode) {
                sunSetTimeX = (this.mSunSetX - ((float) (this.mSunRiseBitmap.getWidth() / 2))) - ((float) UIUtil.dip2px(this.mContext, 34.0f));
            } else {
                sunSetTimeX = (this.mSunSetX - ((float) (this.mSunRiseBitmap.getWidth() / 2))) + ((float) UIUtil.dip2px(this.mContext, 25.0f));
            }
            if (!TextUtils.isEmpty(sunSetTime)) {
                canvas.drawText(sunSetTime, sunSetTimeX, (this.mSunSetY - ((float) (this.mSunSetBitmap.getHeight() / 2))) + ((float) UIUtil.dip2px(this.mContext, 14.0f)), this.mSunPaint);
            }
        }
        getCurrentPoinitXY(this.mCurrentThumbradius);
        if (this.mIsDayMode) {
            mThumbradiusChange = this.thumbradius + 90.0d;
            mCurrentThumbradiusChange = this.mCurrentThumbradius + 90.0d;
        } else if (this.mDayStartAngle >= 270) {
            mThumbradiusChange = (this.thumbradius + 90.0d) + 360.0d;
            mCurrentThumbradiusChange = (this.mCurrentThumbradius + 90.0d) + 360.0d;
        } else {
            mThumbradiusChange = (this.thumbradius + 90.0d) % 360.0d;
            mCurrentThumbradiusChange = (this.mCurrentThumbradius + 90.0d) % 360.0d;
        }
        int colorID = WeatherResHelper.getWeatherNightArcColorID(this.mCurrentWeatherId);
        System.out.println("mThumbradiusChange:" + mThumbradiusChange);
        System.out.println("mCurrentThumbradius:" + mCurrentThumbradiusChange);
        System.out.println("mThumbradiusChange--mDayStartAngle:" + this.mDayStartAngle);
        System.out.println("mThumbradiusChange--mDayEndAngle:" + this.mDayEndAngle);
        System.out.println("mThumbradiusChange--mNightStartAngle:" + this.mNightStartAngle);
        System.out.println("mThumbradiusChange--mNightEndAngle:" + this.mNightEndAngle);
        if (this.mIsDayMode) {
            if ((mThumbradiusChange < ((double) (this.mNightStartAngle - 6)) || mThumbradiusChange > ((double) this.mNightEndAngle)) && mThumbradiusChange >= ((double) (this.mDayStartAngle - 6))) {
                this.drawBigDot.setColor(-1);
                this.mTimePaint.setColor(Color.parseColor(this.mContext.getString(colorID)));
            } else {
                this.drawBigDot.setColor(Color.parseColor(this.mContext.getString(colorID)));
                this.mTimePaint.setColor(-1);
            }
        } else if ((mThumbradiusChange < ((double) (this.mDayStartAngle - 6)) || mThumbradiusChange >= ((double) (this.mNightStartAngle - 6))) && mThumbradiusChange < ((double) (this.mNightEndAngle + 6))) {
            this.drawBigDot.setColor(Color.parseColor(this.mContext.getString(colorID)));
            this.mTimePaint.setColor(-1);
        } else {
            this.drawBigDot.setColor(-1);
            this.mTimePaint.setColor(Color.parseColor(this.mContext.getString(colorID)));
        }
        if (this.mIsDayMode) {
            if ((mCurrentThumbradiusChange < ((double) (this.mNightStartAngle - 6)) || mCurrentThumbradiusChange > ((double) this.mNightEndAngle)) && mCurrentThumbradiusChange >= ((double) (this.mDayStartAngle - 6))) {
                this.drawSmallDot.setColor(-1);
            } else {
                this.drawSmallDot.setColor(Color.parseColor(this.mContext.getString(colorID)));
            }
        } else if ((mCurrentThumbradiusChange < ((double) (this.mDayStartAngle - 6)) || mCurrentThumbradiusChange >= ((double) this.mDayEndAngle)) && mCurrentThumbradiusChange < ((double) (this.mNightEndAngle + 6))) {
            this.drawSmallDot.setColor(Color.parseColor(this.mContext.getString(colorID)));
        } else {
            this.drawSmallDot.setColor(-1);
        }
        canvas.drawCircle(this.mCurrentThumbradiusX, this.mCurrentThumbradiusY, (float) (this.mDotRadius - 20), this.drawSmallDot);
        Bitmap decodeResource = BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.ic_cloudy);
        canvas.drawCircle(this.mThumbX, this.mThumbY, (float) this.mDotRadius, this.drawBigDot);
        String timeString = String.valueOf(get24HrTimeByAngel((int) this.thumbradius));
        System.out.println("timeString:" + timeString);
        canvas.drawText(timeString, (this.mThumbX - (((float) getStringWidth(timeString, this.mTimePaint)) / 2.0f)) - 2.0f, this.mThumbY + ((float) UIUtil.dip2px(this.mContext, 4.0f)), this.mTimePaint);
        FontMetrics fontMetrics = this.mCenterTempPaint.getFontMetrics();
        float fontHeight = this.mCenterTempPaint.ascent() + this.mCenterTempPaint.descent();
        float fontWidth = (float) getStringWidth(this.mCurrentTemp, this.mCenterTempPaint);
        canvas.drawText(this.mCurrentTemp, (((float) this.mCpoint.x) - (fontWidth / 2.0f)) + ((float) UIUtil.dip2px(this.mContext, 5.0f)), ((float) this.mCpoint.y) - ((3.0f * fontHeight) / 4.0f), this.mCenterTempPaint);
        Bitmap weatherIcon = getCurrentWeatherIcon();
        float weatherIconX = (((float) this.mCpoint.x) - (fontWidth / 2.0f)) + ((float) UIUtil.dip2px(this.mContext, 4.0f));
        float weatherIconY = ((float) this.mCpoint.y) + fontHeight;
        if (weatherIcon != null && !weatherIcon.isRecycled()) {
            canvas.drawBitmap(weatherIcon, weatherIconX, weatherIconY, this.mCenterTempPaint);
            String currentType = getCurrentType();
            float currentTypeFontHeight = this.mCenterTypePaint.ascent() + this.mCenterTypePaint.descent();
            float measureText = this.mCenterTypePaint.measureText(currentType);
            canvas.drawText(currentType, (((float) weatherIcon.getWidth()) + weatherIconX) + 20.0f, (((float) this.mCpoint.y) + (3.0f * currentTypeFontHeight)) + 2.0f, this.mCenterTypePaint);
        }
    }

    public int getStringWidth(String str, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(str, START_ANGEL_0, str.length(), bounds);
        return bounds.width();
    }

    public void setCurrentWeatherIconId(int iconId) {
        this.mCurrentIconId = iconId;
    }

    public int getCurrentWeahterIconId() {
        return this.mCurrentIconId;
    }

    public Bitmap getCurrentWeatherIcon() {
        return BitmapFactory.decodeResource(this.mContext.getResources(), this.mCurrentIconId);
    }

    public void setCurrentTypeId(int weatherId) {
        this.mCurrentWeatherId = weatherId;
    }

    public int getCurrentTypeId() {
        return this.mCurrentWeatherId;
    }

    public void setCurrentType(String weatherType) {
        this.mCurrentType = weatherType;
    }

    public String getCurrentType() {
        return this.mCurrentType;
    }

    public void setCurrentTemp(int temp) {
        float f;
        boolean cOrf = SystemSetting.getTemperature(this.mContext);
        String tempUnit = cOrf ? "\u00b0" : "\u00b0";
        if (cOrf) {
            f = (float) temp;
        } else {
            f = SystemSetting.celsiusToFahrenheit((float) temp);
        }
        int currentTemp = (int) f;
        if (currentTemp < -2000) {
            this.mCurrentTemp = "--" + tempUnit;
        } else {
            this.mCurrentTemp = currentTemp + tempUnit;
        }
        invalidate();
    }

    public String getCurrentTemp() {
        return this.mCurrentTemp;
    }

    private void getPointXY(double thumbradius) {
        float[] xy = goQuadrant(thumbradius);
        if (thumbradius >= 0.0d && thumbradius < 90.0d) {
            this.mThumbX = ((float) this.mCpoint.x) - Math.abs(xy[0]);
            this.mThumbY = ((float) this.mCpoint.y) + Math.abs(xy[1]);
        }
        if (thumbradius >= 90.0d && thumbradius < 180.0d) {
            this.mThumbX = ((float) this.mCpoint.x) - Math.abs(xy[0]);
            this.mThumbY = ((float) this.mCpoint.y) - Math.abs(xy[1]);
        }
        if (thumbradius >= 180.0d && thumbradius < 270.0d) {
            this.mThumbX = ((float) this.mCpoint.x) + Math.abs(xy[0]);
            this.mThumbY = ((float) this.mCpoint.y) - Math.abs(xy[1]);
        }
        if (thumbradius >= 270.0d && thumbradius < 360.0d) {
            this.mThumbX = ((float) this.mCpoint.x) + Math.abs(xy[0]);
            this.mThumbY = ((float) this.mCpoint.y) + Math.abs(xy[1]);
        }
    }

    private void getCurrentPoinitXY(double curThumbradius) {
        float[] xy = goQuadrant(curThumbradius);
        if (curThumbradius >= 0.0d && curThumbradius < 90.0d) {
            this.mCurrentThumbradiusX = ((float) this.mCpoint.x) - Math.abs(xy[0]);
            this.mCurrentThumbradiusY = ((float) this.mCpoint.y) + Math.abs(xy[1]);
        }
        if (curThumbradius >= 90.0d && curThumbradius < 180.0d) {
            this.mCurrentThumbradiusX = ((float) this.mCpoint.x) - Math.abs(xy[0]);
            this.mCurrentThumbradiusY = ((float) this.mCpoint.y) - Math.abs(xy[1]);
        }
        if (curThumbradius >= 180.0d && curThumbradius < 270.0d) {
            this.mCurrentThumbradiusX = ((float) this.mCpoint.x) + Math.abs(xy[0]);
            this.mCurrentThumbradiusY = ((float) this.mCpoint.y) - Math.abs(xy[1]);
        }
        if (curThumbradius >= 270.0d && curThumbradius < 360.0d) {
            this.mCurrentThumbradiusX = ((float) this.mCpoint.x) + Math.abs(xy[0]);
            this.mCurrentThumbradiusY = ((float) this.mCpoint.y) + Math.abs(xy[1]);
        }
    }

    public void setTodayHoursWeather(List<RootWeather> opWeatherHour) {
        this.mOpWeatherHours = opWeatherHour;
    }

    public void startMove() {
        this.thumbradius += 2.0d;
        if (this.thumbradius >= 360.0d) {
            this.thumbradius = 0.0d;
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
        this.mCpoint.x = width / 2;
        this.mCpoint.y = height / 2;
        this.mCircleradius = (float) ((Math.min(this.mCpoint.x, this.mCpoint.y) * 2) / 3);
    }

    private int measureHeight(int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == 1073741824) {
            return specSize;
        }
        int result = getPaddingBottom() + getPaddingTop();
        return specMode == Integer.MIN_VALUE ? Math.min(result, specSize) : result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == 1073741824) {
            return size;
        }
        return mode == 0 ? getPaddingLeft() + getPaddingRight() : Math.min(START_ANGEL_0, size);
    }
}
