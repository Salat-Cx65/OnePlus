package com.oneplus.lib.widget.button;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import com.google.android.gms.common.ConnectionResult;
import com.oneplus.commonctrl.R;
import com.oneplus.lib.design.widget.AppBarLayout;
import com.oneplus.lib.design.widget.CoordinatorLayout;
import com.oneplus.lib.design.widget.CoordinatorLayout.DefaultBehavior;
import com.oneplus.lib.design.widget.CoordinatorLayout.LayoutParams;
import com.oneplus.lib.design.widget.Utils;
import com.oneplus.lib.widget.button.OPFloatingActionButton.OnVisibilityChangedListener;
import java.util.List;
import net.oneplus.weather.provider.CitySearchProvider;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

@DefaultBehavior(Behavior.class)
public class OPFloatingActionButton extends ImageView {
    private static final int SIZE_MINI = 1;
    private static final int SIZE_NORMAL = 0;
    private ColorStateList mBackgroundTint;
    private Mode mBackgroundTintMode;
    private int mBorderWidth;
    private int mContentPadding;
    private final OPFloatingActionButtonImpl mImpl;
    private int mRippleColor;
    private final Rect mShadowPadding;
    private int mSize;
    private int mUserSetVisibility;

    public static abstract class OnVisibilityChangedListener {
        public void onShown(OPFloatingActionButton fab) {
        }

        public void onHidden(OPFloatingActionButton fab) {
        }
    }

    public static class Behavior extends com.oneplus.lib.design.widget.CoordinatorLayout.Behavior<OPFloatingActionButton> {
        private static final boolean AUTO_HIDE_DEFAULT = true;
        private boolean mAutoHideEnabled;
        private OnVisibilityChangedListener mInternalAutoHideListener;
        private Rect mTmpRect;

        public Behavior() {
            this.mAutoHideEnabled = true;
        }

        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OpFloatingActionButton_Behavior_Layout);
            this.mAutoHideEnabled = a.getBoolean(R.styleable.OpFloatingActionButton_Behavior_Layout_op_behavior_autoHide, AUTO_HIDE_DEFAULT);
            a.recycle();
        }

        public void setAutoHideEnabled(boolean autoHide) {
            this.mAutoHideEnabled = autoHide;
        }

        public boolean isAutoHideEnabled() {
            return this.mAutoHideEnabled;
        }

        public void onAttachedToLayoutParams(@NonNull LayoutParams lp) {
            if (lp.dodgeInsetEdges == 0) {
                lp.dodgeInsetEdges = 80;
            }
        }

        public boolean onDependentViewChanged(CoordinatorLayout parent, OPFloatingActionButton child, View dependency) {
            if (dependency instanceof AppBarLayout) {
                updateFabVisibilityForAppBarLayout(parent, (AppBarLayout) dependency, child);
            } else if (isBottomSheet(dependency)) {
                updateFabVisibilityForBottomSheet(dependency, child);
            }
            return false;
        }

        private static boolean isBottomSheet(@NonNull View view) {
            return false;
        }

        @VisibleForTesting
        void setInternalAutoHideListener(OnVisibilityChangedListener listener) {
            this.mInternalAutoHideListener = listener;
        }

        private boolean shouldUpdateVisibility(View dependency, OPFloatingActionButton child) {
            return (this.mAutoHideEnabled && ((LayoutParams) child.getLayoutParams()).getAnchorId() == dependency.getId() && child.getUserSetVisibility() == 0) ? AUTO_HIDE_DEFAULT : false;
        }

        private boolean updateFabVisibilityForAppBarLayout(CoordinatorLayout parent, AppBarLayout appBarLayout, OPFloatingActionButton child) {
            if (!shouldUpdateVisibility(appBarLayout, child)) {
                return false;
            }
            if (this.mTmpRect == null) {
                this.mTmpRect = new Rect();
            }
            Rect rect = this.mTmpRect;
            Utils.getDescendantRect(parent, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                child.hide(false);
            } else {
                child.show(false);
            }
            return AUTO_HIDE_DEFAULT;
        }

        private boolean updateFabVisibilityForBottomSheet(View bottomSheet, OPFloatingActionButton child) {
            if (!shouldUpdateVisibility(bottomSheet, child)) {
                return false;
            }
            if (bottomSheet.getTop() < (child.getHeight() / 2) + ((LayoutParams) child.getLayoutParams()).topMargin) {
                child.hide(false);
            } else {
                child.show(false);
            }
            return AUTO_HIDE_DEFAULT;
        }

        public boolean onLayoutChild(CoordinatorLayout parent, OPFloatingActionButton child, int layoutDirection) {
            List<View> dependencies = parent.getDependencies(child);
            int count = dependencies.size();
            for (int i = 0; i < count; i++) {
                View dependency = (View) dependencies.get(i);
                if (!(dependency instanceof AppBarLayout)) {
                    if (isBottomSheet(dependency) && updateFabVisibilityForBottomSheet(dependency, child)) {
                        break;
                    }
                } else if (updateFabVisibilityForAppBarLayout(parent, (AppBarLayout) dependency, child)) {
                    break;
                }
            }
            parent.onLayoutChild(child, layoutDirection);
            offsetIfNeeded(parent, child);
            return AUTO_HIDE_DEFAULT;
        }

        public boolean getInsetDodgeRect(@NonNull CoordinatorLayout parent, @NonNull OPFloatingActionButton child, @NonNull Rect rect) {
            Rect shadowPadding = child.mShadowPadding;
            rect.set(child.getLeft() + shadowPadding.left, child.getTop() + shadowPadding.top, child.getRight() - shadowPadding.right, child.getBottom() - shadowPadding.bottom);
            return AUTO_HIDE_DEFAULT;
        }

        private void offsetIfNeeded(CoordinatorLayout parent, OPFloatingActionButton fab) {
            Rect padding = fab.mShadowPadding;
            if (padding != null && padding.centerX() > 0 && padding.centerY() > 0) {
                LayoutParams lp = (LayoutParams) fab.getLayoutParams();
                int offsetTB = 0;
                int offsetLR = 0;
                if (fab.getRight() >= parent.getWidth() - lp.rightMargin) {
                    offsetLR = padding.right;
                } else if (fab.getLeft() <= lp.leftMargin) {
                    offsetLR = -padding.left;
                }
                if (fab.getBottom() >= parent.getHeight() - lp.bottomMargin) {
                    offsetTB = padding.bottom;
                } else if (fab.getTop() <= lp.topMargin) {
                    offsetTB = -padding.top;
                }
                if (offsetTB != 0) {
                    ViewCompat.offsetTopAndBottom(fab, offsetTB);
                }
                if (offsetLR != 0) {
                    ViewCompat.offsetLeftAndRight(fab, offsetLR);
                }
            }
        }
    }

    public OPFloatingActionButton(Context context) {
        this(context, null);
    }

    public OPFloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.OPFloatingActionButtonStyle);
    }

    public OPFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mUserSetVisibility = getVisibility();
        this.mShadowPadding = new Rect();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OPFloatingActionButton, defStyleAttr, R.style.OnePlus_Widget_Design_FloatingActionButton);
        Drawable background = a.getDrawable(R.styleable.OPFloatingActionButton_android_background);
        this.mBackgroundTint = a.getColorStateList(R.styleable.OPFloatingActionButton_op_backgroundTint);
        this.mBackgroundTintMode = parseTintMode(a.getInt(R.styleable.OPFloatingActionButton_op_backgroundTintMode, -1), null);
        this.mRippleColor = a.getColor(R.styleable.OPFloatingActionButton_op_rippleColor, 0);
        this.mSize = a.getInt(R.styleable.OPFloatingActionButton_op_fabSize, 0);
        this.mBorderWidth = a.getDimensionPixelSize(R.styleable.OPFloatingActionButton_op_borderWidth, 0);
        float op_elevation = a.getDimension(R.styleable.OPFloatingActionButton_op_elevation, AutoScrollHelper.RELATIVE_UNSPECIFIED);
        float op_pressedTranslationZ = a.getDimension(R.styleable.OPFloatingActionButton_op_pressedTranslationZ, AutoScrollHelper.RELATIVE_UNSPECIFIED);
        a.recycle();
        this.mImpl = new OPFloatingActionButtonImpl(this, new OPShadowViewDelegate() {
            public float getRadius() {
                return ((float) OPFloatingActionButton.this.getSizeDimension()) / 2.0f;
            }

            public void setShadowPadding(int left, int top, int right, int bottom) {
                OPFloatingActionButton.this.mShadowPadding.set(left, top, right, bottom);
                OPFloatingActionButton.this.setPadding(OPFloatingActionButton.this.mContentPadding + left, OPFloatingActionButton.this.mContentPadding + top, OPFloatingActionButton.this.mContentPadding + right, OPFloatingActionButton.this.mContentPadding + bottom);
            }

            public void setBackground(Drawable background) {
                super.setBackground(background);
            }
        });
        this.mContentPadding = (getSizeDimension() - ((int) getResources().getDimension(R.dimen.design_fab_content_size))) / 2;
        this.mImpl.setBackground(background, this.mBackgroundTint, this.mBackgroundTintMode, this.mRippleColor, this.mBorderWidth);
        this.mImpl.setElevation(op_elevation);
        this.mImpl.setPressedTranslationZ(op_pressedTranslationZ);
        setClickable(true);
    }

    public void setVisibility(int visibility) {
        internalSetVisibility(visibility, true);
    }

    final void internalSetVisibility(int visibility, boolean fromUser) {
        super.setVisibility(visibility);
        if (fromUser) {
            this.mUserSetVisibility = visibility;
        }
    }

    final int getUserSetVisibility() {
        return this.mUserSetVisibility;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int preferredSize = getSizeDimension();
        int d = Math.min(resolveAdjustedSize(preferredSize, widthMeasureSpec), resolveAdjustedSize(preferredSize, heightMeasureSpec));
        setMeasuredDimension((this.mShadowPadding.left + d) + this.mShadowPadding.right, (this.mShadowPadding.top + d) + this.mShadowPadding.bottom);
    }

    public void setRippleColor(int color) {
        if (this.mRippleColor != color) {
            this.mRippleColor = color;
            this.mImpl.setRippleColor(color);
        }
    }

    public ColorStateList getBackgroundTintList() {
        return this.mBackgroundTint;
    }

    public void setBackgroundTintList(ColorStateList tint) {
        if (this.mBackgroundTint != tint) {
            this.mBackgroundTint = tint;
            this.mImpl.setBackgroundTintList(tint);
        }
    }

    public Mode getBackgroundTintMode() {
        return this.mBackgroundTintMode;
    }

    public void setBackgroundTintMode(Mode tintMode) {
        if (this.mBackgroundTintMode != tintMode) {
            this.mBackgroundTintMode = tintMode;
            this.mImpl.setBackgroundTintMode(tintMode);
        }
    }

    public void setBackground(Drawable background) {
        if (this.mImpl != null) {
            this.mImpl.setBackground(background, this.mBackgroundTint, this.mBackgroundTintMode, this.mRippleColor, this.mBorderWidth);
        }
    }

    public void show() {
        show(true);
    }

    public void hide() {
        hide(true);
    }

    public void show(boolean fromUser) {
        this.mImpl.show(fromUser);
    }

    public void hide(boolean fromUser) {
        this.mImpl.hide(fromUser);
    }

    final int getSizeDimension() {
        switch (this.mSize) {
            case SIZE_MINI:
                return getResources().getDimensionPixelSize(R.dimen.design_fab_size_mini);
            default:
                return getResources().getDimensionPixelSize(R.dimen.design_fab_size_normal);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.mImpl.onDrawableStateChanged(getDrawableState());
    }

    @TargetApi(11)
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        this.mImpl.jumpDrawableToCurrentState();
    }

    private static int resolveAdjustedSize(int desiredSize, int measureSpec) {
        int result = desiredSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case CitySearchProvider.GET_SEARCH_RESULT_FAIL:
                return Math.min(desiredSize, specSize);
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return desiredSize;
            case CitySearchProvider.GET_SEARCH_RESULT_SUCC:
                return specSize;
            default:
                return result;
        }
    }

    static Mode parseTintMode(int value, Mode defaultMode) {
        switch (value) {
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return Mode.SRC_OVER;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return Mode.SRC_IN;
            case ConnectionResult.SERVICE_INVALID:
                return Mode.SRC_ATOP;
            case ConnectionResult.TIMEOUT:
                return Mode.MULTIPLY;
            case ConnectionResult.INTERRUPTED:
                return Mode.SCREEN;
            default:
                return defaultMode;
        }
    }
}
