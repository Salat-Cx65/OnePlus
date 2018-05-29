package com.oneplus.lib.widget.button;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.ListPopupWindow;
import com.oneplus.sdk.utils.OpBoostFramework;

class OPCircularBorderDrawable extends Drawable {
    private static final float DRAW_STROKE_WIDTH_MULTIPLE = 1.3333f;
    float mBorderWidth;
    private int mBottomInnerStrokeColor;
    private int mBottomOuterStrokeColor;
    private boolean mInvalidateShader;
    final Paint mPaint;
    final Rect mRect;
    final RectF mRectF;
    private ColorStateList mTint;
    private int mTintColor;
    private PorterDuffColorFilter mTintFilter;
    private Mode mTintMode;
    private int mTopInnerStrokeColor;
    private int mTopOuterStrokeColor;

    public OPCircularBorderDrawable() {
        this.mRect = new Rect();
        this.mRectF = new RectF();
        this.mInvalidateShader = true;
        this.mTintMode = Mode.SRC_IN;
        this.mPaint = new Paint(1);
        this.mPaint.setStyle(Style.STROKE);
    }

    void setGradientColors(int topOuterStrokeColor, int topInnerStrokeColor, int bottomOuterStrokeColor, int bottomInnerStrokeColor) {
        this.mTopOuterStrokeColor = topOuterStrokeColor;
        this.mTopInnerStrokeColor = topInnerStrokeColor;
        this.mBottomOuterStrokeColor = bottomOuterStrokeColor;
        this.mBottomInnerStrokeColor = bottomInnerStrokeColor;
    }

    void setBorderWidth(float width) {
        if (this.mBorderWidth != width) {
            this.mBorderWidth = width;
            this.mPaint.setStrokeWidth(1.3333f * width);
            this.mInvalidateShader = true;
            invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        boolean clearColorFilter;
        if (this.mTintFilter == null || this.mPaint.getColorFilter() != null) {
            clearColorFilter = false;
        } else {
            this.mPaint.setColorFilter(this.mTintFilter);
            clearColorFilter = true;
        }
        if (this.mInvalidateShader) {
            this.mPaint.setShader(createGradientShader());
            this.mInvalidateShader = false;
        }
        float halfBorderWidth = this.mPaint.getStrokeWidth() / 2.0f;
        RectF rectF = this.mRectF;
        copyBounds(this.mRect);
        rectF.set(this.mRect);
        rectF.left += halfBorderWidth;
        rectF.top += halfBorderWidth;
        rectF.right -= halfBorderWidth;
        rectF.bottom -= halfBorderWidth;
        canvas.drawOval(rectF, this.mPaint);
        if (clearColorFilter) {
            this.mPaint.setColorFilter(null);
        }
    }

    public boolean getPadding(Rect padding) {
        int op_borderWidth = Math.round(this.mBorderWidth);
        padding.set(op_borderWidth, op_borderWidth, op_borderWidth, op_borderWidth);
        return true;
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    void setTintColor(int tintColor) {
        this.mTintColor = tintColor;
        this.mInvalidateShader = true;
        invalidateSelf();
    }

    public void setTintList(ColorStateList tint) {
        this.mTint = tint;
        this.mTintFilter = updateTintFilter(tint, this.mTintMode);
        invalidateSelf();
    }

    public void setTintMode(Mode tintMode) {
        this.mTintMode = tintMode;
        this.mTintFilter = updateTintFilter(this.mTint, tintMode);
        invalidateSelf();
    }

    public void getOutline(Outline outline) {
        copyBounds(this.mRect);
        outline.setOval(this.mRect);
    }

    private PorterDuffColorFilter updateTintFilter(ColorStateList tint, Mode tintMode) {
        return (tint == null || tintMode == null) ? null : new PorterDuffColorFilter(tint.getColorForState(getState(), 0), tintMode);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public int getOpacity() {
        return this.mBorderWidth > 0.0f ? OpBoostFramework.REQUEST_FAILED_UNKNOWN_POLICY : ListPopupWindow.WRAP_CONTENT;
    }

    protected void onBoundsChange(Rect bounds) {
        this.mInvalidateShader = true;
    }

    private Shader createGradientShader() {
        Rect rect = this.mRect;
        copyBounds(rect);
        float borderRatio = this.mBorderWidth / ((float) rect.height());
        return new LinearGradient(0.0f, (float) rect.top, 0.0f, (float) rect.bottom, new int[]{compositeColors(this.mTopOuterStrokeColor, this.mTintColor), compositeColors(this.mTopInnerStrokeColor, this.mTintColor), compositeColors(setAlphaComponent(this.mTopInnerStrokeColor, 0), this.mTintColor), compositeColors(setAlphaComponent(this.mBottomInnerStrokeColor, 0), this.mTintColor), compositeColors(this.mBottomInnerStrokeColor, this.mTintColor), compositeColors(this.mBottomOuterStrokeColor, this.mTintColor)}, new float[]{0.0f, borderRatio, 0.5f, 0.5f, 1.0f - borderRatio, 1.0f}, TileMode.CLAMP);
    }

    private static int compositeColors(int foreground, int background) {
        int bgAlpha = Color.alpha(background);
        int fgAlpha = Color.alpha(foreground);
        int a = compositeAlpha(fgAlpha, bgAlpha);
        return Color.argb(a, compositeComponent(Color.red(foreground), fgAlpha, Color.red(background), bgAlpha, a), compositeComponent(Color.green(foreground), fgAlpha, Color.green(background), bgAlpha, a), compositeComponent(Color.blue(foreground), fgAlpha, Color.blue(background), bgAlpha, a));
    }

    private static int compositeAlpha(int foregroundAlpha, int backgroundAlpha) {
        return 255 - (((255 - backgroundAlpha) * (255 - foregroundAlpha)) / 255);
    }

    private static int compositeComponent(int fgC, int fgA, int bgC, int bgA, int a) {
        return a == 0 ? 0 : (((fgC * 255) * fgA) + ((bgC * bgA) * (255 - fgA))) / (a * 255);
    }

    public static int setAlphaComponent(int color, int alpha) {
        if (alpha >= 0 && alpha <= 255) {
            return (16777215 & color) | (alpha << 24);
        }
        throw new IllegalArgumentException("alpha must be between 0 and 255.");
    }
}
