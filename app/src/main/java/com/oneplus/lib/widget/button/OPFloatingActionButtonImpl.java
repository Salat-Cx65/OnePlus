package com.oneplus.lib.widget.button;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.v4.widget.AutoScrollHelper;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.android.volley.DefaultRetryPolicy;
import com.google.android.gms.location.DetectedActivity;
import com.oneplus.commonctrl.R;

class OPFloatingActionButtonImpl {
    static final int[] EMPTY_STATE_SET;
    static final int[] FOCUSED_ENABLED_STATE_SET;
    static final int[] PRESSED_ENABLED_STATE_SET;
    static final int SHOW_HIDE_ANIM_DURATION = 200;
    private Drawable mBorderDrawable;
    private Interpolator mInterpolator;
    private boolean mIsHiding;
    private RippleDrawable mRippleDrawable;
    final OPShadowViewDelegate mShadowViewDelegate;
    private Drawable mShapeDrawable;
    final OPFloatingActionButton mView;

    class AnonymousClass_1 implements AnimatorListener {
        final /* synthetic */ boolean val$fromUser;

        AnonymousClass_1(boolean z) {
            this.val$fromUser = z;
        }

        public void onAnimationStart(Animator animation) {
            OPFloatingActionButtonImpl.this.mIsHiding = true;
            OPFloatingActionButtonImpl.this.mView.internalSetVisibility(0, this.val$fromUser);
        }

        public void onAnimationEnd(Animator animation) {
            OPFloatingActionButtonImpl.this.mIsHiding = false;
            OPFloatingActionButtonImpl.this.mView.internalSetVisibility(DetectedActivity.RUNNING, this.val$fromUser);
        }

        public void onAnimationCancel(Animator animation) {
            OPFloatingActionButtonImpl.this.mIsHiding = false;
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    class AnonymousClass_2 implements AnimatorListener {
        final /* synthetic */ boolean val$fromUser;

        AnonymousClass_2(boolean z) {
            this.val$fromUser = z;
        }

        public void onAnimationStart(Animator animation) {
            OPFloatingActionButtonImpl.this.mView.internalSetVisibility(0, this.val$fromUser);
        }

        public void onAnimationEnd(Animator animation) {
        }

        public void onAnimationCancel(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    static {
        PRESSED_ENABLED_STATE_SET = new int[]{16842919, 16842910};
        FOCUSED_ENABLED_STATE_SET = new int[]{16842908, 16842910};
        EMPTY_STATE_SET = new int[0];
    }

    OPFloatingActionButtonImpl(OPFloatingActionButton view, OPShadowViewDelegate shadowViewDelegate) {
        this.mView = view;
        this.mShadowViewDelegate = shadowViewDelegate;
        if (!view.isInEditMode()) {
            this.mInterpolator = AnimationUtils.loadInterpolator(this.mView.getContext(), AndroidResources.FAST_OUT_SLOW_IN);
        }
    }

    Drawable createBorderDrawable(int op_borderWidth, ColorStateList op_backgroundTint) {
        Resources resources = this.mView.getResources();
        OPCircularBorderDrawable borderDrawable = new OPCircularBorderDrawable();
        borderDrawable.setGradientColors(resources.getColor(R.color.design_fab_stroke_top_outer_color), resources.getColor(R.color.design_fab_stroke_top_inner_color), resources.getColor(R.color.design_fab_stroke_end_inner_color), resources.getColor(R.color.design_fab_stroke_end_outer_color));
        borderDrawable.setBorderWidth((float) op_borderWidth);
        borderDrawable.setTintColor(op_backgroundTint.getDefaultColor());
        return borderDrawable;
    }

    void setBackground(Drawable originalBackground, ColorStateList op_backgroundTint, Mode op_backgroundTintMode, int op_rippleColor, int op_borderWidth) {
        Drawable rippleContent;
        this.mShapeDrawable = originalBackground.mutate();
        this.mShapeDrawable.setTintList(op_backgroundTint);
        if (op_backgroundTintMode != null) {
            this.mShapeDrawable.setTintMode(op_backgroundTintMode);
        }
        if (op_borderWidth > 0) {
            this.mBorderDrawable = createBorderDrawable(op_borderWidth, op_backgroundTint);
            rippleContent = new LayerDrawable(new Drawable[]{this.mBorderDrawable, this.mShapeDrawable});
        } else {
            this.mBorderDrawable = null;
            rippleContent = this.mShapeDrawable;
        }
        this.mRippleDrawable = new RippleDrawable(ColorStateList.valueOf(op_rippleColor), rippleContent, null);
        this.mShadowViewDelegate.setBackground(this.mRippleDrawable);
        this.mShadowViewDelegate.setShadowPadding(0, 0, 0, 0);
    }

    void setBackgroundTintList(ColorStateList tint) {
        this.mShapeDrawable.setTintList(tint);
        if (this.mBorderDrawable != null) {
            this.mBorderDrawable.setTintList(tint);
        }
    }

    void setBackgroundTintMode(Mode tintMode) {
        this.mShapeDrawable.setTintMode(tintMode);
    }

    void setRippleColor(int op_rippleColor) {
        this.mRippleDrawable.setColor(ColorStateList.valueOf(op_rippleColor));
    }

    public void setElevation(float op_elevation) {
        this.mView.setElevation(op_elevation);
    }

    void setPressedTranslationZ(float translationZ) {
        StateListAnimator stateListAnimator = new StateListAnimator();
        stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, setupAnimator(ObjectAnimator.ofFloat(this.mView, "translationZ", new float[]{translationZ})));
        stateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, setupAnimator(ObjectAnimator.ofFloat(this.mView, "translationZ", new float[]{translationZ})));
        stateListAnimator.addState(EMPTY_STATE_SET, setupAnimator(ObjectAnimator.ofFloat(this.mView, "translationZ", new float[]{0.0f})));
        this.mView.setStateListAnimator(stateListAnimator);
    }

    void onDrawableStateChanged(int[] state) {
    }

    void jumpDrawableToCurrentState() {
    }

    private Animator setupAnimator(Animator animator) {
        animator.setInterpolator(this.mInterpolator);
        return animator;
    }

    void hide(boolean fromUser) {
        if (!this.mIsHiding && this.mView.getVisibility() == 0) {
            if (!this.mView.isLaidOut() || this.mView.isInEditMode()) {
                this.mView.internalSetVisibility(DetectedActivity.RUNNING, fromUser);
            } else {
                this.mView.animate().scaleX(AutoScrollHelper.RELATIVE_UNSPECIFIED).scaleY(AutoScrollHelper.RELATIVE_UNSPECIFIED).alpha(AutoScrollHelper.RELATIVE_UNSPECIFIED).setDuration(200).setInterpolator(new FastOutSlowInInterpolator()).setListener(new AnonymousClass_1(fromUser));
            }
        }
    }

    void show(boolean fromUser) {
        if (this.mView.getVisibility() == 0) {
            return;
        }
        if (!this.mView.isLaidOut() || this.mView.isInEditMode()) {
            this.mView.internalSetVisibility(0, fromUser);
            this.mView.setAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            this.mView.setScaleY(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            this.mView.setScaleX(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            return;
        }
        this.mView.setAlpha(AutoScrollHelper.RELATIVE_UNSPECIFIED);
        this.mView.setScaleY(AutoScrollHelper.RELATIVE_UNSPECIFIED);
        this.mView.setScaleX(AutoScrollHelper.RELATIVE_UNSPECIFIED);
        this.mView.animate().scaleX(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT).scaleY(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT).alpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT).setDuration(200).setInterpolator(new FastOutSlowInInterpolator()).setListener(new AnonymousClass_2(fromUser));
    }
}
