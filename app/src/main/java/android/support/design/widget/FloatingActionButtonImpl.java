package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Interpolator;
import com.android.volley.DefaultRetryPolicy;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

@RequiresApi(14)
class FloatingActionButtonImpl {
    static final Interpolator ANIM_INTERPOLATOR;
    static final int ANIM_STATE_HIDING = 1;
    static final int ANIM_STATE_NONE = 0;
    static final int ANIM_STATE_SHOWING = 2;
    static final int[] EMPTY_STATE_SET;
    static final int[] ENABLED_STATE_SET;
    static final int[] FOCUSED_ENABLED_STATE_SET;
    static final long PRESSED_ANIM_DELAY = 100;
    static final long PRESSED_ANIM_DURATION = 100;
    static final int[] PRESSED_ENABLED_STATE_SET;
    static final int SHOW_HIDE_ANIM_DURATION = 200;
    int mAnimState;
    CircularBorderDrawable mBorderDrawable;
    Drawable mContentBackground;
    float mElevation;
    private OnPreDrawListener mPreDrawListener;
    float mPressedTranslationZ;
    Drawable mRippleDrawable;
    private float mRotation;
    ShadowDrawableWrapper mShadowDrawable;
    final ShadowViewDelegate mShadowViewDelegate;
    Drawable mShapeDrawable;
    private final StateListAnimator mStateListAnimator;
    private final Rect mTmpRect;
    final VisibilityAwareImageButton mView;

    class AnonymousClass_1 extends AnimatorListenerAdapter {
        private boolean mCancelled;
        final /* synthetic */ boolean val$fromUser;
        final /* synthetic */ InternalVisibilityChangedListener val$listener;

        AnonymousClass_1(boolean z, InternalVisibilityChangedListener internalVisibilityChangedListener) {
            this.val$fromUser = z;
            this.val$listener = internalVisibilityChangedListener;
        }

        public void onAnimationStart(Animator animation) {
            FloatingActionButtonImpl.this.mView.internalSetVisibility(ANIM_STATE_NONE, this.val$fromUser);
            this.mCancelled = false;
        }

        public void onAnimationCancel(Animator animation) {
            this.mCancelled = true;
        }

        public void onAnimationEnd(Animator animation) {
            FloatingActionButtonImpl.this.mAnimState = 0;
            if (!this.mCancelled) {
                FloatingActionButtonImpl.this.mView.internalSetVisibility(this.val$fromUser ? DetectedActivity.RUNNING : RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.val$fromUser);
                if (this.val$listener != null) {
                    this.val$listener.onHidden();
                }
            }
        }
    }

    class AnonymousClass_2 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$fromUser;
        final /* synthetic */ InternalVisibilityChangedListener val$listener;

        AnonymousClass_2(boolean z, InternalVisibilityChangedListener internalVisibilityChangedListener) {
            this.val$fromUser = z;
            this.val$listener = internalVisibilityChangedListener;
        }

        public void onAnimationStart(Animator animation) {
            FloatingActionButtonImpl.this.mView.internalSetVisibility(ANIM_STATE_NONE, this.val$fromUser);
        }

        public void onAnimationEnd(Animator animation) {
            FloatingActionButtonImpl.this.mAnimState = 0;
            if (this.val$listener != null) {
                this.val$listener.onShown();
            }
        }
    }

    static interface InternalVisibilityChangedListener {
        void onHidden();

        void onShown();
    }

    private abstract class ShadowAnimatorImpl extends AnimatorListenerAdapter implements AnimatorUpdateListener {
        private float mShadowSizeEnd;
        private float mShadowSizeStart;
        private boolean mValidValues;

        protected abstract float getTargetShadowSize();

        private ShadowAnimatorImpl() {
        }

        public void onAnimationUpdate(ValueAnimator animator) {
            if (!this.mValidValues) {
                this.mShadowSizeStart = FloatingActionButtonImpl.this.mShadowDrawable.getShadowSize();
                this.mShadowSizeEnd = getTargetShadowSize();
                this.mValidValues = true;
            }
            FloatingActionButtonImpl.this.mShadowDrawable.setShadowSize(this.mShadowSizeStart + ((this.mShadowSizeEnd - this.mShadowSizeStart) * animator.getAnimatedFraction()));
        }

        public void onAnimationEnd(Animator animator) {
            FloatingActionButtonImpl.this.mShadowDrawable.setShadowSize(this.mShadowSizeEnd);
            this.mValidValues = false;
        }
    }

    private class DisabledElevationAnimation extends ShadowAnimatorImpl {
        DisabledElevationAnimation() {
            super(null);
        }

        protected float getTargetShadowSize() {
            return AutoScrollHelper.RELATIVE_UNSPECIFIED;
        }
    }

    private class ElevateToTranslationZAnimation extends ShadowAnimatorImpl {
        ElevateToTranslationZAnimation() {
            super(null);
        }

        protected float getTargetShadowSize() {
            return FloatingActionButtonImpl.this.mElevation + FloatingActionButtonImpl.this.mPressedTranslationZ;
        }
    }

    private class ResetElevationAnimation extends ShadowAnimatorImpl {
        ResetElevationAnimation() {
            super(null);
        }

        protected float getTargetShadowSize() {
            return FloatingActionButtonImpl.this.mElevation;
        }
    }

    static {
        ANIM_INTERPOLATOR = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
        PRESSED_ENABLED_STATE_SET = new int[]{16842919, 16842910};
        FOCUSED_ENABLED_STATE_SET = new int[]{16842908, 16842910};
        ENABLED_STATE_SET = new int[]{16842910};
        EMPTY_STATE_SET = new int[0];
    }

    FloatingActionButtonImpl(VisibilityAwareImageButton view, ShadowViewDelegate shadowViewDelegate) {
        this.mAnimState = 0;
        this.mTmpRect = new Rect();
        this.mView = view;
        this.mShadowViewDelegate = shadowViewDelegate;
        this.mStateListAnimator = new StateListAnimator();
        this.mStateListAnimator.addState(PRESSED_ENABLED_STATE_SET, createAnimator(new ElevateToTranslationZAnimation()));
        this.mStateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, createAnimator(new ElevateToTranslationZAnimation()));
        this.mStateListAnimator.addState(ENABLED_STATE_SET, createAnimator(new ResetElevationAnimation()));
        this.mStateListAnimator.addState(EMPTY_STATE_SET, createAnimator(new DisabledElevationAnimation()));
        this.mRotation = this.mView.getRotation();
    }

    void setBackgroundDrawable(ColorStateList backgroundTint, Mode backgroundTintMode, int rippleColor, int borderWidth) {
        Drawable[] layers;
        this.mShapeDrawable = DrawableCompat.wrap(createShapeDrawable());
        DrawableCompat.setTintList(this.mShapeDrawable, backgroundTint);
        if (backgroundTintMode != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, backgroundTintMode);
        }
        this.mRippleDrawable = DrawableCompat.wrap(createShapeDrawable());
        DrawableCompat.setTintList(this.mRippleDrawable, createColorStateList(rippleColor));
        if (borderWidth > 0) {
            this.mBorderDrawable = createBorderDrawable(borderWidth, backgroundTint);
            layers = new Drawable[]{this.mBorderDrawable, this.mShapeDrawable, this.mRippleDrawable};
        } else {
            this.mBorderDrawable = null;
            layers = new Drawable[]{this.mShapeDrawable, this.mRippleDrawable};
        }
        this.mContentBackground = new LayerDrawable(layers);
        this.mShadowDrawable = new ShadowDrawableWrapper(this.mView.getContext(), this.mContentBackground, this.mShadowViewDelegate.getRadius(), this.mElevation, this.mElevation + this.mPressedTranslationZ);
        this.mShadowDrawable.setAddPaddingForCorners(false);
        this.mShadowViewDelegate.setBackgroundDrawable(this.mShadowDrawable);
    }

    void setBackgroundTintList(ColorStateList tint) {
        if (this.mShapeDrawable != null) {
            DrawableCompat.setTintList(this.mShapeDrawable, tint);
        }
        if (this.mBorderDrawable != null) {
            this.mBorderDrawable.setBorderTint(tint);
        }
    }

    void setBackgroundTintMode(Mode tintMode) {
        if (this.mShapeDrawable != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, tintMode);
        }
    }

    void setRippleColor(int rippleColor) {
        if (this.mRippleDrawable != null) {
            DrawableCompat.setTintList(this.mRippleDrawable, createColorStateList(rippleColor));
        }
    }

    final void setElevation(float elevation) {
        if (this.mElevation != elevation) {
            this.mElevation = elevation;
            onElevationsChanged(elevation, this.mPressedTranslationZ);
        }
    }

    float getElevation() {
        return this.mElevation;
    }

    final void setPressedTranslationZ(float translationZ) {
        if (this.mPressedTranslationZ != translationZ) {
            this.mPressedTranslationZ = translationZ;
            onElevationsChanged(this.mElevation, translationZ);
        }
    }

    void onElevationsChanged(float elevation, float pressedTranslationZ) {
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.setShadowSize(elevation, this.mPressedTranslationZ + elevation);
            updatePadding();
        }
    }

    void onDrawableStateChanged(int[] state) {
        this.mStateListAnimator.setState(state);
    }

    void jumpDrawableToCurrentState() {
        this.mStateListAnimator.jumpToCurrentState();
    }

    void hide(@Nullable InternalVisibilityChangedListener listener, boolean fromUser) {
        if (!isOrWillBeHidden()) {
            this.mView.animate().cancel();
            if (shouldAnimateVisibilityChange()) {
                this.mAnimState = 1;
                this.mView.animate().scaleX(AutoScrollHelper.RELATIVE_UNSPECIFIED).scaleY(AutoScrollHelper.RELATIVE_UNSPECIFIED).alpha(AutoScrollHelper.RELATIVE_UNSPECIFIED).setDuration(200).setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener(new AnonymousClass_1(fromUser, listener));
                return;
            }
            this.mView.internalSetVisibility(fromUser ? DetectedActivity.RUNNING : RainSurfaceView.RAIN_LEVEL_RAINSTORM, fromUser);
            if (listener != null) {
                listener.onHidden();
            }
        }
    }

    void show(@Nullable InternalVisibilityChangedListener listener, boolean fromUser) {
        if (!isOrWillBeShown()) {
            this.mView.animate().cancel();
            if (shouldAnimateVisibilityChange()) {
                this.mAnimState = 2;
                if (this.mView.getVisibility() != 0) {
                    this.mView.setAlpha(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                    this.mView.setScaleY(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                    this.mView.setScaleX(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                }
                this.mView.animate().scaleX(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT).scaleY(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT).alpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT).setDuration(200).setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setListener(new AnonymousClass_2(fromUser, listener));
                return;
            }
            this.mView.internalSetVisibility(ANIM_STATE_NONE, fromUser);
            this.mView.setAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            this.mView.setScaleY(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            this.mView.setScaleX(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            if (listener != null) {
                listener.onShown();
            }
        }
    }

    final Drawable getContentBackground() {
        return this.mContentBackground;
    }

    void onCompatShadowChanged() {
    }

    final void updatePadding() {
        Rect rect = this.mTmpRect;
        getPadding(rect);
        onPaddingUpdated(rect);
        this.mShadowViewDelegate.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    void getPadding(Rect rect) {
        this.mShadowDrawable.getPadding(rect);
    }

    void onPaddingUpdated(Rect padding) {
    }

    void onAttachedToWindow() {
        if (requirePreDrawListener()) {
            ensurePreDrawListener();
            this.mView.getViewTreeObserver().addOnPreDrawListener(this.mPreDrawListener);
        }
    }

    void onDetachedFromWindow() {
        if (this.mPreDrawListener != null) {
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this.mPreDrawListener);
            this.mPreDrawListener = null;
        }
    }

    boolean requirePreDrawListener() {
        return true;
    }

    CircularBorderDrawable createBorderDrawable(int borderWidth, ColorStateList backgroundTint) {
        Context context = this.mView.getContext();
        CircularBorderDrawable borderDrawable = newCircularDrawable();
        borderDrawable.setGradientColors(ContextCompat.getColor(context, R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(context, R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_outer_color));
        borderDrawable.setBorderWidth((float) borderWidth);
        borderDrawable.setBorderTint(backgroundTint);
        return borderDrawable;
    }

    CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawable();
    }

    void onPreDraw() {
        float rotation = this.mView.getRotation();
        if (this.mRotation != rotation) {
            this.mRotation = rotation;
            updateFromViewRotation();
        }
    }

    private void ensurePreDrawListener() {
        if (this.mPreDrawListener == null) {
            this.mPreDrawListener = new OnPreDrawListener() {
                public boolean onPreDraw() {
                    FloatingActionButtonImpl.this.onPreDraw();
                    return true;
                }
            };
        }
    }

    GradientDrawable createShapeDrawable() {
        GradientDrawable d = newGradientDrawableForShape();
        d.setShape(ANIM_STATE_HIDING);
        d.setColor(-1);
        return d;
    }

    GradientDrawable newGradientDrawableForShape() {
        return new GradientDrawable();
    }

    boolean isOrWillBeShown() {
        return this.mView.getVisibility() != 0 ? this.mAnimState == 2 : this.mAnimState != 1;
    }

    boolean isOrWillBeHidden() {
        return this.mView.getVisibility() == 0 ? this.mAnimState == 1 : this.mAnimState != 2;
    }

    private ValueAnimator createAnimator(@NonNull ShadowAnimatorImpl impl) {
        ValueAnimator animator = new ValueAnimator();
        animator.setInterpolator(ANIM_INTERPOLATOR);
        animator.setDuration(PRESSED_ANIM_DURATION);
        animator.addListener(impl);
        animator.addUpdateListener(impl);
        animator.setFloatValues(new float[]{0.0f, 1.0f});
        return animator;
    }

    private static ColorStateList createColorStateList(int selectedColor) {
        states = new int[3][];
        int[] colors = new int[3];
        states[0] = FOCUSED_ENABLED_STATE_SET;
        colors[0] = selectedColor;
        int i = 0 + 1;
        states[i] = PRESSED_ENABLED_STATE_SET;
        colors[i] = selectedColor;
        i++;
        states[i] = new int[0];
        colors[i] = 0;
        i++;
        return new ColorStateList(states, colors);
    }

    private boolean shouldAnimateVisibilityChange() {
        return ViewCompat.isLaidOut(this.mView) && !this.mView.isInEditMode();
    }

    private void updateFromViewRotation() {
        if (VERSION.SDK_INT == 19) {
            if (this.mRotation % 90.0f != 0.0f) {
                if (this.mView.getLayerType() != 1) {
                    this.mView.setLayerType(ANIM_STATE_HIDING, null);
                }
            } else if (this.mView.getLayerType() != 0) {
                this.mView.setLayerType(ANIM_STATE_NONE, null);
            }
        }
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.setRotation(-this.mRotation);
        }
        if (this.mBorderDrawable != null) {
            this.mBorderDrawable.setRotation(-this.mRotation);
        }
    }
}
