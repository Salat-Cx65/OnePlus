package net.oneplus.weather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;
import java.util.Collections;

import net.oneplus.weather.util.TemperatureUtil;
import net.oneplus.weather.util.UIUtil;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class WeatherTemperatureView extends View {
    public static final String DEFAULT_HTEMP_LINE_COLOR = "#FF00AAFF";
    public static final String DEFAULT_HTEMP_POINT_COLOR = "#FFFFFF";
    public static final String DEFAULT_LTEMP_LINE_COLOR = "#FF00AAFF";
    public static final String DEFAULT_LTEMP_POINT_COLOR = "#CED7DC";
    public static final String DEFAULT_PATH_AREA_ONE_COLOR = "#66FFFFFF";
    public static final String DEFAULT_PATH_AREA_TWO_COLOR = "#26000000";
    public static final String DEFAULT_VALUE_COLOR = "#DDDDDD";
    int[] dx;
    private Context mContext;
    private Paint mHTempLinePaint;
    private Paint mHTempPointPaint;
    private Paint mLTempLine2Paint;
    private Paint mLTempPoint2Paint;
    private ArrayList<Integer> mLowTemp;
    private Paint mPath1Paint;
    private Paint mPath2Paint;
    private int mRealCount;
    int mSpace;
    private Paint mTempValuePaint;
    private ArrayList<Integer> mTopTemp;
    private int maxTemp;
    private int minTemp;
    private int viewHeight;
    private int viewWidth;

    public WeatherTemperatureView(Context context) {
        super(context);
        this.dx = new int[9];
        this.mContext = context;
        init();
    }

    public WeatherTemperatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.dx = new int[9];
        this.mContext = context;
        init();
    }

    public WeatherTemperatureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.dx = new int[9];
        this.mContext = context;
        init();
    }

    private void init() {
        this.mTopTemp = new ArrayList(9);
        this.mTopTemp.add(Integer.valueOf(DetectedActivity.RUNNING));
        this.mTopTemp.add(Integer.valueOf(ConnectionResult.DEVELOPER_ERROR));
        this.mTopTemp.add(Integer.valueOf(ConnectionResult.LICENSE_CHECK_FAILED));
        this.mTopTemp.add(Integer.valueOf(WeatherCircleView.ARC_DIN));
        this.mTopTemp.add(Integer.valueOf(ConnectionResult.INTERRUPTED));
        this.mTopTemp.add(Integer.valueOf(ConnectionResult.LICENSE_CHECK_FAILED));
        this.mTopTemp.add(Integer.valueOf(ConnectionResult.SERVICE_INVALID));
        this.mTopTemp.add(Integer.valueOf(WeatherCircleView.ARC_DIN));
        this.mTopTemp.add(Integer.valueOf(ConnectionResult.DEVELOPER_ERROR));
        this.mLowTemp = new ArrayList(9);
        this.mLowTemp.add(Integer.valueOf(RainSurfaceView.RAIN_LEVEL_SHOWER));
        this.mLowTemp.add(Integer.valueOf(RainSurfaceView.RAIN_LEVEL_RAINSTORM));
        this.mLowTemp.add(Integer.valueOf(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER));
        this.mLowTemp.add(Integer.valueOf(ConnectionResult.RESOLUTION_REQUIRED));
        this.mLowTemp.add(Integer.valueOf(ConnectionResult.SERVICE_INVALID));
        this.mLowTemp.add(Integer.valueOf(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER));
        this.mLowTemp.add(Integer.valueOf(RainSurfaceView.RAIN_LEVEL_DOWNPOUR));
        this.mLowTemp.add(Integer.valueOf(RainSurfaceView.RAIN_LEVEL_RAINSTORM));
        this.mLowTemp.add(Integer.valueOf(RainSurfaceView.RAIN_LEVEL_SHOWER));
        this.mHTempPointPaint = new Paint();
        this.mHTempPointPaint.setAntiAlias(true);
        this.mHTempPointPaint.setColor(Color.parseColor(DEFAULT_HTEMP_POINT_COLOR));
        this.mLTempPoint2Paint = new Paint();
        this.mLTempPoint2Paint.setAntiAlias(true);
        this.mLTempPoint2Paint.setColor(Color.parseColor(DEFAULT_LTEMP_POINT_COLOR));
        this.mHTempLinePaint = new Paint();
        this.mHTempLinePaint.setAntiAlias(true);
        this.mHTempLinePaint.setStrokeWidth(5.0f);
        this.mHTempLinePaint.setColor(Color.parseColor(DEFAULT_LTEMP_LINE_COLOR));
        this.mLTempLine2Paint = new Paint();
        this.mHTempLinePaint.setColor(Color.parseColor(DEFAULT_LTEMP_LINE_COLOR));
        this.mLTempLine2Paint.setAntiAlias(true);
        this.mLTempLine2Paint.setStrokeWidth(5.0f);
        this.mTempValuePaint = new Paint();
        this.mTempValuePaint.setAntiAlias(true);
        this.mTempValuePaint.setColor(Color.parseColor(DEFAULT_VALUE_COLOR));
        this.mTempValuePaint.setTextSize((float) UIUtil.dip2px(this.mContext, 14.0f));
        this.mPath1Paint = new Paint();
        this.mPath1Paint.setAntiAlias(true);
        this.mPath1Paint.setColor(Color.parseColor(DEFAULT_PATH_AREA_ONE_COLOR));
        this.mPath2Paint = new Paint();
        this.mPath2Paint.setAntiAlias(true);
        this.mPath2Paint.setColor(Color.parseColor(DEFAULT_PATH_AREA_TWO_COLOR));
    }

    public void setPaint(String htpColor, String ltpColor, String htlColor, String ltlColor, String valueColor, String path1Color, String path2Color) {
        this.mHTempPointPaint.setColor(Color.parseColor(htpColor));
        this.mLTempPoint2Paint.setColor(Color.parseColor(ltpColor));
        this.mHTempLinePaint.setColor(Color.parseColor(htlColor));
        this.mLTempLine2Paint.setColor(Color.parseColor(ltlColor));
        this.mTempValuePaint.setColor(Color.parseColor(valueColor));
        this.mPath1Paint.setColor(Color.parseColor(path1Color));
        this.mPath2Paint.setColor(Color.parseColor(path2Color));
        postInvalidate();
    }

    public void setPaint(int htpColorId, int ltpColorId, int htlColorId, int ltlColorId, int valueColorId, int path1ColorId, int path2ColorId) {
        this.mHTempPointPaint.setColor(this.mContext.getResources().getColor(htpColorId));
        this.mLTempPoint2Paint.setColor(this.mContext.getResources().getColor(ltpColorId));
        this.mHTempLinePaint.setColor(this.mContext.getResources().getColor(htlColorId));
        this.mLTempLine2Paint.setColor(this.mContext.getResources().getColor(ltlColorId));
        this.mTempValuePaint.setColor(this.mContext.getResources().getColor(valueColorId));
        this.mPath1Paint.setColor(this.mContext.getResources().getColor(path1ColorId));
        this.mPath2Paint.setColor(this.mContext.getResources().getColor(path2ColorId));
        postInvalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
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
        return mode == 0 ? getPaddingLeft() + getPaddingRight() : Math.min(0, size);
    }

    public void initTemp(ArrayList<Integer> topTemp, ArrayList<Integer> lowTemp, int realCount) {
        if (topTemp != null && topTemp.size() > 0 && lowTemp != null && lowTemp.size() > 0) {
            this.mTopTemp.clear();
            this.mTopTemp.add(Integer.valueOf(((Integer) topTemp.get(0)).intValue() - 2));
            this.mTopTemp.addAll(topTemp);
            this.mTopTemp.add(Integer.valueOf(((Integer) topTemp.get(topTemp.size() - 1)).intValue() - 2));
            this.mLowTemp.clear();
            this.mLowTemp.add(Integer.valueOf(((Integer) lowTemp.get(0)).intValue() - 2));
            this.mLowTemp.addAll(lowTemp);
            this.mLowTemp.add(Integer.valueOf(((Integer) lowTemp.get(lowTemp.size() - 1)).intValue() - 2));
            this.mRealCount = realCount;
            postInvalidate();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.viewHeight = getHeight();
        this.viewWidth = getWidth();
        spaceHeightWidth();
        FontMetrics fontMetrics = this.mTempValuePaint.getFontMetrics();
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        int distance = R.styleable.AppCompatTheme_dialogTheme;
        if (fontHeight > 60.0f) {
            distance = R.styleable.AppCompatTheme_listChoiceBackgroundIndicator;
        }
        drawLinePoint(canvas, distance);
        drawPathArea(canvas, distance);
    }

    public void drawLinePoint(Canvas canvas, int distance) {
        int i;
        FontMetrics fontMetrics = this.mTempValuePaint.getFontMetrics();
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        String tempUnit = "\u00b0";
        for (i = 0; i < this.mTopTemp.size(); i++) {
            int _hTop = ((this.maxTemp - ((Integer) this.mTopTemp.get(i)).intValue()) * this.mSpace) + distance;
            if (i < this.mTopTemp.size() - 1) {
                canvas.drawLine((float) this.dx[i], (float) _hTop, (float) this.dx[i + 1], (float) (((this.maxTemp - ((Integer) this.mTopTemp.get(i + 1)).intValue()) * this.mSpace) + distance), this.mPath1Paint);
            }
            this.mTempValuePaint.setColor(Color.parseColor(DEFAULT_HTEMP_POINT_COLOR));
            int highTemp = ((Integer) this.mTopTemp.get(i)).intValue();
            String highTempString = TemperatureUtil.getHighTemperature(this.mContext, highTemp);
            if (i <= this.mRealCount) {
                canvas.drawText(highTempString, (float) (this.dx[i] - (getTempStringWidth(highTemp, tempUnit) / 2)), ((float) _hTop) - (fontHeight / 2.0f), this.mTempValuePaint);
            }
            canvas.drawCircle((float) this.dx[i], (float) _hTop, 10.0f, this.mHTempPointPaint);
        }
        for (i = this.mLowTemp.size() - 1; i >= 0; i--) {
            int _hLow = ((this.maxTemp - ((Integer) this.mLowTemp.get(i)).intValue()) * this.mSpace) + 60;
            if (i > 0) {
                canvas.drawLine((float) this.dx[i], (float) _hLow, (float) this.dx[i - 1], (float) (((this.maxTemp - ((Integer) this.mLowTemp.get(i - 1)).intValue()) * this.mSpace) + 60), this.mPath2Paint);
            }
            this.mTempValuePaint.setColor(Color.parseColor(DEFAULT_LTEMP_POINT_COLOR));
            int lowTemp = ((Integer) this.mLowTemp.get(i)).intValue();
            String lowTempString = TemperatureUtil.getLowTemperature(this.mContext, lowTemp);
            if (i <= this.mRealCount) {
                canvas.drawText(lowTempString, (float) (this.dx[i] - (getTempStringWidth(lowTemp, tempUnit) / 2)), ((float) _hLow) + fontHeight, this.mTempValuePaint);
            }
            canvas.drawCircle((float) this.dx[i], (float) _hLow, 10.0f, this.mLTempPoint2Paint);
        }
    }

    public int getTempStringWidth(int temp, String tempUnit) {
        String lowTempString = temp + tempUnit;
        Rect bounds = new Rect();
        this.mTempValuePaint.getTextBounds(lowTempString, 0, lowTempString.length(), bounds);
        return bounds.width();
    }

    public void drawPathArea(Canvas canvas, int distance) {
        int i;
        Path path = new Path();
        for (i = 0; i < this.mTopTemp.size(); i++) {
            int _hTop = ((this.maxTemp - ((Integer) this.mTopTemp.get(i)).intValue()) * this.mSpace) + distance;
            if (i == 0) {
                path.moveTo((float) this.dx[0], (float) _hTop);
            }
            path.lineTo((float) this.dx[i], (float) _hTop);
        }
        for (i = this.mLowTemp.size() - 1; i >= 0; i--) {
            path.lineTo((float) this.dx[i], (float) (((this.maxTemp - ((Integer) this.mLowTemp.get(i)).intValue()) * this.mSpace) + 60));
        }
        path.close();
        canvas.drawPath(path, this.mPath1Paint);
    }

    private void spaceHeightWidth() {
        this.minTemp = ((Integer) Collections.min(this.mLowTemp)).intValue();
        this.maxTemp = ((Integer) Collections.max(this.mTopTemp)).intValue();
        this.mSpace = (this.viewHeight - 120) / (this.maxTemp - this.minTemp);
        this.dx[0] = (-(this.viewWidth * 1)) / 12;
        this.dx[1] = (this.viewWidth * 1) / 12;
        this.dx[2] = (this.viewWidth * 3) / 12;
        this.dx[3] = (this.viewWidth * 5) / 12;
        this.dx[4] = (this.viewWidth * 7) / 12;
        this.dx[5] = (this.viewWidth * 9) / 12;
        this.dx[6] = (this.viewWidth * 11) / 12;
        this.dx[7] = (this.viewWidth * 13) / 12;
        this.dx[8] = (this.viewWidth * 15) / 12;
    }

    private int getMaxiTemperature(int[] topTemp) {
        return getMaxByBubbleSort(topTemp);
    }

    private int getMiniTemperature(int[] lowTemp) {
        return getMinByBubbleSort(lowTemp);
    }

    public static int getMaxByBubbleSort(int[] args) {
        int[] _array = args;
        for (int i = 0; i < _array.length; i++) {
            for (int j = i + 1; j < _array.length; j++) {
                if (_array[i] < _array[j]) {
                    int temp = _array[i];
                    _array[i] = _array[j];
                    _array[j] = temp;
                }
            }
        }
        return args[0];
    }

    public static int getMinByBubbleSort(int[] args) {
        for (int i = 0; i < args.length; i++) {
            for (int j = i + 1; j < args.length; j++) {
                if (args[i] > args[j]) {
                    int temp = args[i];
                    args[i] = args[j];
                    args[j] = temp;
                }
            }
        }
        return args[0];
    }
}
