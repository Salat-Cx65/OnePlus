package net.oneplus.weather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.widget.AutoScrollHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.AqiBar.Level;
import net.oneplus.weather.widget.shap.Stars;

public class AqiBar extends View {
    private static final int COLOR_LEVEL_FIVE = -1;
    private static final int COLOR_LEVEL_FOUR = -1;
    private static final int COLOR_LEVEL_ONE = -1;
    private static final int COLOR_LEVEL_SIX = -1;
    private static final int COLOR_LEVEL_THREE = -1;
    private static final int COLOR_LEVEL_TWO = -1;
    private static final int[] COLOR_LINEAR_DATAS;
    private static final boolean DBG = false;
    private static final int MAX_VALUE = 500;
    private static final int MIN_HEIGHT = 5;
    private static final int MIN_WIDTH = 5;
    private static final String TAG = "AqiView";
    private boolean hasAqiValue;
    private OnAqiLevelChangeListener mAqiLevelChangeListener;
    private int mAqiValue;
    private Paint mBackgroundPaint;
    private Paint mForegroundPaint;
    private Shader mLinearGradient;
    private Path path;

    static /* synthetic */ class AnonymousClass_1 {
        static final /* synthetic */ int[] $SwitchMap$net$oneplus$weather$widget$AqiBar$Level;

        static {
            $SwitchMap$net$oneplus$weather$widget$AqiBar$Level = new int[Level.values().length];
            try {
                $SwitchMap$net$oneplus$weather$widget$AqiBar$Level[Level.ONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$oneplus$weather$widget$AqiBar$Level[Level.TWO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$oneplus$weather$widget$AqiBar$Level[Level.THREE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$oneplus$weather$widget$AqiBar$Level[Level.FOUR.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$oneplus$weather$widget$AqiBar$Level[Level.FIVE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$oneplus$weather$widget$AqiBar$Level[Level.SIX.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public enum Level {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX
    }

    public static interface OnAqiLevelChangeListener {
        void onLevelChanged(Level level, int i);
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR;
        int aqiValue;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.aqiValue = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.aqiValue);
        }

        static {
            CREATOR = new Creator<SavedState>() {
                public SavedState createFromParcel(Parcel in) {
                    return new SavedState(null);
                }

                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            };
        }
    }

    static {
        COLOR_LINEAR_DATAS = new int[]{-15598962, -205299, -29184, -55552, -11659130, -9762048};
    }

    public AqiBar(Context context) {
        this(context, null);
    }

    public AqiBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AqiBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBackgroundPaint = new Paint(1);
        this.mForegroundPaint = new Paint(1);
        this.mLinearGradient = null;
        this.path = new Path();
        this.hasAqiValue = true;
        if (isInEditMode()) {
            setAqiValue(Stars.CIRCLE_COUNT);
        } else {
            setAqiValue(0);
        }
    }

    private void initPaints(int barWidth, int barHeight, int aqiWidth, float offset) {
        this.mLinearGradient = new LinearGradient(offset, 0.0f, ((float) barWidth) - offset, (float) barHeight, COLOR_LINEAR_DATAS, null, TileMode.REPEAT);
        this.mBackgroundPaint.setShader(this.mLinearGradient);
        this.mBackgroundPaint.setStyle(Style.FILL);
        this.mForegroundPaint.setStyle(Style.FILL);
        if (!this.path.isEmpty()) {
            this.path.reset();
        }
        if (((float) aqiWidth) > ((float) barWidth) - (2.0f * offset)) {
            aqiWidth = (int) (((float) barWidth) - (2.0f * offset));
        }
        this.path.moveTo((float) aqiWidth, (float) barHeight);
        this.path.lineTo(((float) aqiWidth) + (2.0f * offset), (float) barHeight);
        this.path.lineTo(((float) aqiWidth) + offset, AutoScrollHelper.RELATIVE_UNSPECIFIED);
        this.path.close();
    }

    public static Level getAqiLevel(int value) {
        Level localLevel = Level.ONE;
        if (value >= 0 && value < 51) {
            return Level.ONE;
        }
        if (value >= 51 && value < 101) {
            return Level.TWO;
        }
        if (value >= 101 && value < 151) {
            return Level.THREE;
        }
        if (value >= 151 && value < 201) {
            return Level.FOUR;
        }
        if (value < 201 || value >= 301) {
            return value >= 301 ? Level.SIX : localLevel;
        } else {
            return Level.FIVE;
        }
    }

    public void setOnAqiLevelChangeListener(OnAqiLevelChangeListener listener) {
        this.mAqiLevelChangeListener = listener;
    }

    public void setAqiValue(int value) {
        setAqiValue(value, true);
    }

    public void setAqiValue(int value, boolean hasValue) {
        if (value < 0) {
            throw new IllegalArgumentException("Value should not less than zero.");
        }
        this.hasAqiValue = hasValue;
        this.mAqiValue = value;
        this.mForegroundPaint.setColor(getAqiColor(value));
        if (this.mAqiLevelChangeListener != null) {
            this.mAqiLevelChangeListener.onLevelChanged(getAqiLevel(), value);
        }
        invalidate();
    }

    public int getAqiValue() {
        return this.mAqiValue;
    }

    public Level getAqiLevel() {
        return getAqiLevel(getAqiValue());
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int barWidth = (getWidth() - getPaddingLeft()) - getPaddingRight();
        int barHeight = (getHeight() - getPaddingTop()) - getPaddingBottom();
        int aqiWidth = Math.round((((float) this.mAqiValue) * ((float) barWidth)) / 500.0f);
        if (aqiWidth < 0) {
            aqiWidth = 0;
        }
        if (aqiWidth > barWidth) {
            aqiWidth = barWidth;
        }
        float offset = getResources().getDimension(R.dimen.dimen_3);
        initPaints(barWidth, barHeight, aqiWidth, offset);
        canvas.save();
        canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
        canvas.drawRect(offset, AutoScrollHelper.RELATIVE_UNSPECIFIED, ((float) barWidth) - offset, (float) (barHeight / 2), this.mBackgroundPaint);
        if (this.hasAqiValue) {
            canvas.drawPath(this.path, this.mForegroundPaint);
        }
        canvas.restore();
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.aqiValue = this.mAqiValue;
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setAqiValue(ss.aqiValue);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float density = getContext().getResources().getDisplayMetrics().density;
        int dw = Math.round(5.0f * density);
        setMeasuredDimension(resolveSizeAndState(dw + (getPaddingLeft() + getPaddingRight()), widthMeasureSpec, 0), resolveSizeAndState(Math.round(5.0f * density) + (getPaddingTop() + getPaddingBottom()), heightMeasureSpec, 0));
    }

    private int getAqiColor(int value) {
        return getAqiColor(getAqiLevel(value));
    }

    private int getAqiColor(Level level) {
        switch (AnonymousClass_1.$SwitchMap$net$oneplus$weather$widget$AqiBar$Level[level.ordinal()]) {
        }
        return COLOR_LEVEL_TWO;
    }
}
