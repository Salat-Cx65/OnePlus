package com.oneplus.lib.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.AutoScrollHelper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.volley.DefaultRetryPolicy;
import com.oneplus.commonctrl.R;
import com.oneplus.lib.util.DrawableUtils;
import net.oneplus.weather.provider.CitySearchProvider;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class OPAbsSeekBar extends OPProgressBar {
    private static final int NO_ALPHA = 255;
    private float mDisabledAlpha;
    private boolean mHasThumbTint;
    private boolean mHasThumbTintMode;
    private boolean mIsDragging;
    boolean mIsUserSeekable;
    private int mKeyProgressIncrement;
    private int mScaledTouchSlop;
    private boolean mSplitTrack;
    private final Rect mTempRect;
    private Drawable mThumb;
    private int mThumbOffset;
    private ColorStateList mThumbTintList;
    private Mode mThumbTintMode;
    private float mTouchDownX;
    float mTouchProgressOffset;

    public OPAbsSeekBar(Context context) {
        super(context);
        this.mTempRect = new Rect();
        this.mThumbTintList = null;
        this.mThumbTintMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbTintMode = false;
        this.mIsUserSeekable = true;
        this.mKeyProgressIncrement = 1;
    }

    public OPAbsSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTempRect = new Rect();
        this.mThumbTintList = null;
        this.mThumbTintMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbTintMode = false;
        this.mIsUserSeekable = true;
        this.mKeyProgressIncrement = 1;
    }

    public OPAbsSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public OPAbsSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mTempRect = new Rect();
        this.mThumbTintList = null;
        this.mThumbTintMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbTintMode = false;
        this.mIsUserSeekable = true;
        this.mKeyProgressIncrement = 1;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OPSeekBar, defStyleAttr, defStyleRes);
        setThumb(a.getDrawable(R.styleable.OPSeekBar_android_thumb));
        if (a.hasValue(R.styleable.OPSeekBar_android_thumbTintMode)) {
            this.mThumbTintMode = DrawableUtils.parseTintMode(a.getInt(R.styleable.OPSeekBar_android_thumbTintMode, -1), this.mThumbTintMode);
            this.mHasThumbTintMode = true;
        }
        if (a.hasValue(R.styleable.OPSeekBar_android_thumbTint)) {
            this.mThumbTintList = a.getColorStateList(R.styleable.OPSeekBar_android_thumbTint);
            this.mHasThumbTint = true;
        }
        this.mSplitTrack = a.getBoolean(R.styleable.OPSeekBar_android_splitTrack, false);
        setThumbOffset(a.getDimensionPixelOffset(R.styleable.OPSeekBar_android_thumbOffset, getThumbOffset()));
        boolean z = a.getBoolean(R.styleable.OPSeekBar_useDisabledAlpha, true);
        this.mDisabledAlpha = 1.0f;
        a.recycle();
        applyThumbTint();
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setThumb(Drawable thumb) {
        boolean needUpdate;
        if (this.mThumb == null || thumb == this.mThumb) {
            needUpdate = false;
        } else {
            this.mThumb.setCallback(null);
            needUpdate = true;
        }
        if (thumb != null) {
            thumb.setCallback(this);
            if (canResolveLayoutDirection()) {
                thumb.setLayoutDirection(getLayoutDirection());
            }
            this.mThumbOffset = thumb.getIntrinsicWidth() / 2;
            if (needUpdate) {
                if (!(thumb.getIntrinsicWidth() == this.mThumb.getIntrinsicWidth() && thumb.getIntrinsicHeight() == this.mThumb.getIntrinsicHeight())) {
                    requestLayout();
                }
            }
        }
        this.mThumb = thumb;
        applyThumbTint();
        invalidate();
        if (needUpdate) {
            updateThumbAndTrackPos(getWidth(), getHeight());
            if (thumb != null && thumb.isStateful()) {
                thumb.setState(getDrawableState());
            }
        }
    }

    public Drawable getThumb() {
        return this.mThumb;
    }

    public void setThumbTintList(ColorStateList tint) {
        this.mThumbTintList = tint;
        this.mHasThumbTint = true;
        applyThumbTint();
    }

    public ColorStateList getThumbTintList() {
        return this.mThumbTintList;
    }

    public void setThumbTintMode(Mode tintMode) {
        this.mThumbTintMode = tintMode;
        this.mHasThumbTintMode = true;
        applyThumbTint();
    }

    public Mode getThumbTintMode() {
        return this.mThumbTintMode;
    }

    private void applyThumbTint() {
        if (this.mThumb == null) {
            return;
        }
        if (this.mHasThumbTint || this.mHasThumbTintMode) {
            this.mThumb = this.mThumb.mutate();
            if (this.mHasThumbTint) {
                this.mThumb.setTintList(this.mThumbTintList);
            }
            if (this.mHasThumbTintMode) {
                this.mThumb.setTintMode(this.mThumbTintMode);
            }
            if (this.mThumb.isStateful()) {
                this.mThumb.setState(getDrawableState());
            }
        }
    }

    public int getThumbOffset() {
        return this.mThumbOffset;
    }

    public void setThumbOffset(int thumbOffset) {
        this.mThumbOffset = thumbOffset;
        invalidate();
    }

    public void setSplitTrack(boolean splitTrack) {
        this.mSplitTrack = splitTrack;
        invalidate();
    }

    public boolean getSplitTrack() {
        return this.mSplitTrack;
    }

    public void setKeyProgressIncrement(int increment) {
        if (increment < 0) {
            increment = -increment;
        }
        this.mKeyProgressIncrement = increment;
    }

    public int getKeyProgressIncrement() {
        return this.mKeyProgressIncrement;
    }

    public synchronized void setMax(int max) {
        super.setMax(max);
        if (this.mKeyProgressIncrement == 0 || getMax() / this.mKeyProgressIncrement > 20) {
            setKeyProgressIncrement(Math.max(1, Math.round(((float) getMax()) / 20.0f)));
        }
    }

    protected boolean verifyDrawable(Drawable who) {
        return who == this.mThumb || super.verifyDrawable(who);
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mThumb != null) {
            this.mThumb.jumpToCurrentState();
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable != null && this.mDisabledAlpha < 1.0f) {
            progressDrawable.setAlpha(isEnabled() ? NO_ALPHA : (int) (255.0f * this.mDisabledAlpha));
        }
        Drawable thumb = this.mThumb;
        if (thumb != null && thumb.isStateful()) {
            thumb.setState(getDrawableState());
        }
    }

    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (this.mThumb != null) {
            this.mThumb.setHotspot(x, y);
        }
    }

    void onProgressRefresh(float scale, boolean fromUser, int progress) {
        super.onProgressRefresh(scale, fromUser, progress);
        Drawable thumb = this.mThumb;
        if (thumb != null) {
            setThumbPos(getWidth(), thumb, scale, CitySearchProvider.GET_SEARCH_RESULT_FAIL);
            invalidate();
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateThumbAndTrackPos(w, h);
    }

    private void updateThumbAndTrackPos(int w, int h) {
        int trackOffset;
        int thumbOffset;
        int paddedHeight = (h - this.mPaddingTop) - this.mPaddingBottom;
        Drawable track = getCurrentDrawable();
        Drawable thumb = this.mThumb;
        int trackHeight = Math.min(this.mMaxHeight, paddedHeight);
        int thumbHeight = thumb == null ? 0 : thumb.getIntrinsicHeight();
        int offsetHeight;
        if (thumbHeight > trackHeight) {
            offsetHeight = (paddedHeight - thumbHeight) / 2;
            trackOffset = offsetHeight + ((thumbHeight - trackHeight) / 2);
            thumbOffset = offsetHeight + 0;
        } else {
            offsetHeight = (paddedHeight - trackHeight) / 2;
            trackOffset = offsetHeight + 0;
            thumbOffset = offsetHeight + ((trackHeight - thumbHeight) / 2);
        }
        if (track != null) {
            track.setBounds(0, trackOffset, (w - this.mPaddingRight) - this.mPaddingLeft, trackOffset + trackHeight);
        }
        if (thumb != null) {
            setThumbPos(w, thumb, getScale(), thumbOffset);
        }
    }

    private float getScale() {
        int max = getMax();
        return max > 0 ? ((float) getProgress()) / ((float) max) : AutoScrollHelper.RELATIVE_UNSPECIFIED;
    }

    private void setThumbPos(int w, Drawable thumb, float scale, int offset) {
        int top;
        int bottom;
        int left;
        int available = (w - this.mPaddingLeft) - this.mPaddingRight;
        int thumbWidth = thumb.getIntrinsicWidth();
        int thumbHeight = thumb.getIntrinsicHeight();
        available = (available - thumbWidth) + (this.mThumbOffset * 2);
        int thumbPos = (int) ((((float) available) * scale) + 0.5f);
        if (offset == Integer.MIN_VALUE) {
            Rect oldBounds = thumb.getBounds();
            top = oldBounds.top;
            bottom = oldBounds.bottom;
        } else {
            top = offset;
            bottom = offset + thumbHeight;
        }
        if (isLayoutRtl() && this.mMirrorForRtl) {
            left = available - thumbPos;
        } else {
            left = thumbPos;
        }
        int right = left + thumbWidth;
        Drawable background = getBackground();
        if (background != null) {
            int offsetX = this.mPaddingLeft - this.mThumbOffset;
            int offsetY = this.mPaddingTop;
            background.setHotspotBounds(left + offsetX, top + offsetY, right + offsetX, bottom + offsetY);
        }
        thumb.setBounds(left, top, right, bottom);
    }

    public void onResolveDrawables(int layoutDirection) {
        super.onResolveDrawables(layoutDirection);
        if (this.mThumb != null) {
            this.mThumb.setLayoutDirection(layoutDirection);
        }
    }

    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawThumb(canvas);
    }

    void drawTrack(Canvas canvas) {
        Drawable thumbDrawable = this.mThumb;
        if (thumbDrawable == null || !this.mSplitTrack) {
            super.drawTrack(canvas);
            return;
        }
        int i;
        Rect tempRect = this.mTempRect;
        int insetEnabled = (int) getResources().getDimension(R.dimen.seekbar_thumb_optical_inset);
        int insetDisabled = (int) getResources().getDimension(R.dimen.seekbar_thumb_optical_inset_disabled);
        thumbDrawable.copyBounds(tempRect);
        tempRect.offset(this.mPaddingLeft - this.mThumbOffset, this.mPaddingTop);
        int i2 = tempRect.left;
        if (isEnabled()) {
            i = insetEnabled;
        } else {
            i = insetDisabled;
        }
        tempRect.left = i + i2;
        i = tempRect.right;
        if (!isEnabled()) {
            insetEnabled = insetDisabled;
        }
        tempRect.right = i - insetEnabled;
        int saveCount = canvas.save();
        canvas.clipRect(tempRect, Op.DIFFERENCE);
        super.drawTrack(canvas);
        canvas.restoreToCount(saveCount);
    }

    void drawThumb(Canvas canvas) {
        if (this.mThumb != null) {
            canvas.save();
            canvas.translate((float) (this.mPaddingLeft - this.mThumbOffset), (float) this.mPaddingTop);
            this.mThumb.draw(canvas);
            canvas.restore();
        }
    }

    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int thumbHeight = 0;
        synchronized (this) {
            Drawable d = getCurrentDrawable();
            if (this.mThumb != null) {
                thumbHeight = this.mThumb.getIntrinsicHeight();
            }
            int dw = 0;
            int dh = 0;
            if (d != null) {
                dw = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, d.getIntrinsicWidth()));
                dh = Math.max(thumbHeight, Math.max(this.mMinHeight, Math.min(this.mMaxHeight, d.getIntrinsicHeight())));
            }
            setMeasuredDimension(resolveSizeAndState(dw + (this.mPaddingLeft + this.mPaddingRight), widthMeasureSpec, 0), resolveSizeAndState(dh + (this.mPaddingTop + this.mPaddingBottom), heightMeasureSpec, 0));
        }
    }

    public boolean isInScrollingContainer() {
        ViewParent p = getParent();
        while (p != null && (p instanceof ViewGroup)) {
            if (((ViewGroup) p).shouldDelayChildPressedState()) {
                return true;
            }
            p = p.getParent();
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.mIsUserSeekable || !isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                if (isInScrollingContainer()) {
                    this.mTouchDownX = event.getX();
                    return true;
                }
                setPressed(true);
                if (this.mThumb != null) {
                    invalidate(this.mThumb.getBounds());
                }
                onStartTrackingTouch();
                trackTouchEvent(event);
                attemptClaimDrag();
                return true;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                if (this.mIsDragging) {
                    trackTouchEvent(event);
                    onStopTrackingTouch();
                    setPressed(false);
                } else {
                    onStartTrackingTouch();
                    trackTouchEvent(event);
                    onStopTrackingTouch();
                }
                invalidate();
                return true;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                if (this.mIsDragging) {
                    trackTouchEvent(event);
                    return true;
                } else if (Math.abs(event.getX() - this.mTouchDownX) <= ((float) this.mScaledTouchSlop)) {
                    return true;
                } else {
                    setPressed(true);
                    if (this.mThumb != null) {
                        invalidate(this.mThumb.getBounds());
                    }
                    onStartTrackingTouch();
                    trackTouchEvent(event);
                    attemptClaimDrag();
                    return true;
                }
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                if (this.mIsDragging) {
                    onStopTrackingTouch();
                    setPressed(false);
                }
                invalidate();
                return true;
            default:
                return true;
        }
    }

    private void setHotspot(float x, float y) {
        Drawable bg = getBackground();
        if (bg != null) {
            bg.setHotspot(x, y);
        }
    }

    private void trackTouchEvent(MotionEvent event) {
        float scale;
        int width = getWidth();
        int available = (width - this.mPaddingLeft) - this.mPaddingRight;
        int x = (int) event.getX();
        float progress = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        if (isLayoutRtl() && this.mMirrorForRtl) {
            if (x > width - this.mPaddingRight) {
                scale = AutoScrollHelper.RELATIVE_UNSPECIFIED;
            } else if (x < this.mPaddingLeft) {
                scale = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
            } else {
                scale = ((float) ((available - x) + this.mPaddingLeft)) / ((float) available);
                progress = this.mTouchProgressOffset;
            }
        } else if (x < this.mPaddingLeft) {
            scale = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        } else if (x > width - this.mPaddingRight) {
            scale = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
        } else {
            scale = ((float) (x - this.mPaddingLeft)) / ((float) available);
            progress = this.mTouchProgressOffset;
        }
        progress += ((float) getMax()) * scale;
        setHotspot((float) x, (float) ((int) event.getY()));
        setProgress((int) progress, true);
    }

    private void attemptClaimDrag() {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }

    void onStartTrackingTouch() {
        this.mIsDragging = true;
    }

    void onStopTrackingTouch() {
        this.mIsDragging = false;
    }

    void onKeyChange() {
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isEnabled()) {
            int increment = this.mKeyProgressIncrement;
            switch (keyCode) {
                case net.oneplus.weather.R.styleable.Toolbar_titleMargin:
                    increment = -increment;
                    if (isLayoutRtl()) {
                        increment = -increment;
                    }
                    if (setProgress(getProgress() + increment, true)) {
                        onKeyChange();
                        return true;
                    }
                case net.oneplus.weather.R.styleable.Toolbar_titleMarginBottom:
                    if (isLayoutRtl()) {
                        increment = -increment;
                    }
                    if (setProgress(getProgress() + increment, true)) {
                        onKeyChange();
                        return true;
                    }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public CharSequence getAccessibilityClassName() {
        return OPAbsSeekBar.class.getName();
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        Drawable thumb = this.mThumb;
        if (thumb != null) {
            setThumbPos(getWidth(), thumb, getScale(), CitySearchProvider.GET_SEARCH_RESULT_FAIL);
            invalidate();
        }
    }
}
