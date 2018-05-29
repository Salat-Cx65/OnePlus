package android.support.v7.graphics.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.support.v7.appcompat.R;
import com.oneplus.sdk.utils.OpBoostFramework;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DrawerArrowDrawable extends Drawable {
    public static final int ARROW_DIRECTION_END = 3;
    public static final int ARROW_DIRECTION_LEFT = 0;
    public static final int ARROW_DIRECTION_RIGHT = 1;
    public static final int ARROW_DIRECTION_START = 2;
    private static final float ARROW_HEAD_ANGLE;
    private float mArrowHeadLength;
    private float mArrowShaftLength;
    private float mBarGap;
    private float mBarLength;
    private int mDirection;
    private float mMaxCutForBarSize;
    private final Paint mPaint;
    private final Path mPath;
    private float mProgress;
    private final int mSize;
    private boolean mSpin;
    private boolean mVerticalMirror;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface ArrowDirection {
    }

    static {
        ARROW_HEAD_ANGLE = (float) Math.toRadians(45.0d);
    }

    public DrawerArrowDrawable(Context context) {
        this.mPaint = new Paint();
        this.mPath = new Path();
        this.mVerticalMirror = false;
        this.mDirection = 2;
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeJoin(Join.MITER);
        this.mPaint.setStrokeCap(Cap.BUTT);
        this.mPaint.setAntiAlias(true);
        TypedArray a = context.getTheme().obtainStyledAttributes(null, R.styleable.DrawerArrowToggle, R.attr.drawerArrowStyle, R.style.Base_Widget_AppCompat_DrawerArrowToggle);
        setColor(a.getColor(R.styleable.DrawerArrowToggle_color, ARROW_DIRECTION_LEFT));
        setBarThickness(a.getDimension(R.styleable.DrawerArrowToggle_thickness, AutoScrollHelper.RELATIVE_UNSPECIFIED));
        setSpinEnabled(a.getBoolean(R.styleable.DrawerArrowToggle_spinBars, true));
        setGapSize((float) Math.round(a.getDimension(R.styleable.DrawerArrowToggle_gapBetweenBars, AutoScrollHelper.RELATIVE_UNSPECIFIED)));
        this.mSize = a.getDimensionPixelSize(R.styleable.DrawerArrowToggle_drawableSize, ARROW_DIRECTION_LEFT);
        this.mBarLength = (float) Math.round(a.getDimension(R.styleable.DrawerArrowToggle_barLength, AutoScrollHelper.RELATIVE_UNSPECIFIED));
        this.mArrowHeadLength = (float) Math.round(a.getDimension(R.styleable.DrawerArrowToggle_arrowHeadLength, AutoScrollHelper.RELATIVE_UNSPECIFIED));
        this.mArrowShaftLength = a.getDimension(R.styleable.DrawerArrowToggle_arrowShaftLength, AutoScrollHelper.RELATIVE_UNSPECIFIED);
        a.recycle();
    }

    public void setArrowHeadLength(float length) {
        if (this.mArrowHeadLength != length) {
            this.mArrowHeadLength = length;
            invalidateSelf();
        }
    }

    public float getArrowHeadLength() {
        return this.mArrowHeadLength;
    }

    public void setArrowShaftLength(float length) {
        if (this.mArrowShaftLength != length) {
            this.mArrowShaftLength = length;
            invalidateSelf();
        }
    }

    public float getArrowShaftLength() {
        return this.mArrowShaftLength;
    }

    public float getBarLength() {
        return this.mBarLength;
    }

    public void setBarLength(float length) {
        if (this.mBarLength != length) {
            this.mBarLength = length;
            invalidateSelf();
        }
    }

    public void setColor(@ColorInt int color) {
        if (color != this.mPaint.getColor()) {
            this.mPaint.setColor(color);
            invalidateSelf();
        }
    }

    @ColorInt
    public int getColor() {
        return this.mPaint.getColor();
    }

    public void setBarThickness(float width) {
        if (this.mPaint.getStrokeWidth() != width) {
            this.mPaint.setStrokeWidth(width);
            this.mMaxCutForBarSize = (float) (((double) (width / 2.0f)) * Math.cos((double) ARROW_HEAD_ANGLE));
            invalidateSelf();
        }
    }

    public float getBarThickness() {
        return this.mPaint.getStrokeWidth();
    }

    public float getGapSize() {
        return this.mBarGap;
    }

    public void setGapSize(float gap) {
        if (gap != this.mBarGap) {
            this.mBarGap = gap;
            invalidateSelf();
        }
    }

    public void setDirection(int direction) {
        if (direction != this.mDirection) {
            this.mDirection = direction;
            invalidateSelf();
        }
    }

    public boolean isSpinEnabled() {
        return this.mSpin;
    }

    public void setSpinEnabled(boolean enabled) {
        if (this.mSpin != enabled) {
            this.mSpin = enabled;
            invalidateSelf();
        }
    }

    public int getDirection() {
        return this.mDirection;
    }

    public void setVerticalMirror(boolean verticalMirror) {
        if (this.mVerticalMirror != verticalMirror) {
            this.mVerticalMirror = verticalMirror;
            invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        boolean flipToPointRight;
        Rect bounds = getBounds();
        switch (this.mDirection) {
            case ARROW_DIRECTION_LEFT:
                flipToPointRight = false;
                break;
            case ARROW_DIRECTION_RIGHT:
                flipToPointRight = true;
                break;
            case ARROW_DIRECTION_END:
                flipToPointRight = DrawableCompat.getLayoutDirection(this) == 0;
                break;
            default:
                flipToPointRight = DrawableCompat.getLayoutDirection(this) == 1;
                break;
        }
        float arrowHeadBarLength = lerp(this.mBarLength, (float) Math.sqrt((double) ((this.mArrowHeadLength * this.mArrowHeadLength) * 2.0f)), this.mProgress);
        float arrowShaftLength = lerp(this.mBarLength, this.mArrowShaftLength, this.mProgress);
        float arrowShaftCut = (float) Math.round(lerp(AutoScrollHelper.RELATIVE_UNSPECIFIED, this.mMaxCutForBarSize, this.mProgress));
        float rotation = lerp(AutoScrollHelper.RELATIVE_UNSPECIFIED, ARROW_HEAD_ANGLE, this.mProgress);
        if (flipToPointRight) {
            Object obj = null;
        } else {
            int i = -1020002304;
        }
        Object obj2;
        if (flipToPointRight) {
            obj2 = 1127481344;
        } else {
            obj2 = null;
        }
        float canvasRotate = lerp(r19, r18, this.mProgress);
        float arrowWidth = (float) Math.round(((double) arrowHeadBarLength) * Math.cos((double) rotation));
        float arrowHeight = (float) Math.round(((double) arrowHeadBarLength) * Math.sin((double) rotation));
        this.mPath.rewind();
        float topBottomBarOffset = lerp(this.mBarGap + this.mPaint.getStrokeWidth(), -this.mMaxCutForBarSize, this.mProgress);
        float arrowEdge = (-arrowShaftLength) / 2.0f;
        this.mPath.moveTo(arrowEdge + arrowShaftCut, AutoScrollHelper.RELATIVE_UNSPECIFIED);
        this.mPath.rLineTo(arrowShaftLength - (2.0f * arrowShaftCut), AutoScrollHelper.RELATIVE_UNSPECIFIED);
        this.mPath.moveTo(arrowEdge, topBottomBarOffset);
        this.mPath.rLineTo(arrowWidth, arrowHeight);
        this.mPath.moveTo(arrowEdge, -topBottomBarOffset);
        this.mPath.rLineTo(arrowWidth, -arrowHeight);
        this.mPath.close();
        canvas.save();
        float barThickness = this.mPaint.getStrokeWidth();
        canvas.translate((float) bounds.centerX(), ((float) ((((int) ((((float) bounds.height()) - (3.0f * barThickness)) - (this.mBarGap * 2.0f))) / 4) * 2)) + ((1.5f * barThickness) + this.mBarGap));
        if (this.mSpin) {
            canvas.rotate(((float) ((this.mVerticalMirror ^ flipToPointRight) != 0 ? -1 : ARROW_DIRECTION_RIGHT)) * canvasRotate);
        } else if (flipToPointRight) {
            canvas.rotate(180.0f);
        }
        canvas.drawPath(this.mPath, this.mPaint);
        canvas.restore();
    }

    public void setAlpha(int alpha) {
        if (alpha != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public int getIntrinsicHeight() {
        return this.mSize;
    }

    public int getIntrinsicWidth() {
        return this.mSize;
    }

    public int getOpacity() {
        return OpBoostFramework.REQUEST_FAILED_UNKNOWN_POLICY;
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getProgress() {
        return this.mProgress;
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float progress) {
        if (this.mProgress != progress) {
            this.mProgress = progress;
            invalidateSelf();
        }
    }

    public final Paint getPaint() {
        return this.mPaint;
    }

    private static float lerp(float a, float b, float t) {
        return ((b - a) * t) + a;
    }
}
