package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.R;
import android.support.design.widget.SwipeDismissBehavior.OnDismissListener;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import com.google.android.gms.location.DetectedActivity;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class BaseTransientBottomBar<B extends BaseTransientBottomBar<B>> {
    static final int ANIMATION_DURATION = 250;
    static final int ANIMATION_FADE_DURATION = 180;
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    static final int MSG_DISMISS = 1;
    static final int MSG_SHOW = 0;
    private static final boolean USE_OFFSET_API;
    static final Handler sHandler;
    private final AccessibilityManager mAccessibilityManager;
    private List<BaseCallback<B>> mCallbacks;
    private final ContentViewCallback mContentViewCallback;
    private final Context mContext;
    private int mDuration;
    final Callback mManagerCallback;
    private final ViewGroup mTargetParent;
    final SnackbarBaseLayout mView;

    class AnonymousClass_10 extends AnimatorListenerAdapter {
        final /* synthetic */ int val$event;

        AnonymousClass_10(int i) {
            this.val$event = i;
        }

        public void onAnimationStart(Animator animator) {
            BaseTransientBottomBar.this.mContentViewCallback.animateContentOut(LENGTH_LONG, ANIMATION_FADE_DURATION);
        }

        public void onAnimationEnd(Animator animator) {
            BaseTransientBottomBar.this.onViewHidden(this.val$event);
        }
    }

    class AnonymousClass_12 implements AnimationListener {
        final /* synthetic */ int val$event;

        AnonymousClass_12(int i) {
            this.val$event = i;
        }

        public void onAnimationEnd(Animation animation) {
            BaseTransientBottomBar.this.onViewHidden(this.val$event);
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    class AnonymousClass_8 implements AnimatorUpdateListener {
        private int mPreviousAnimatedIntValue;
        final /* synthetic */ int val$viewHeight;

        AnonymousClass_8(int i) {
            this.val$viewHeight = i;
            this.mPreviousAnimatedIntValue = this.val$viewHeight;
        }

        public void onAnimationUpdate(ValueAnimator animator) {
            int currentAnimatedIntValue = ((Integer) animator.getAnimatedValue()).intValue();
            if (USE_OFFSET_API) {
                ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.mView, currentAnimatedIntValue - this.mPreviousAnimatedIntValue);
            } else {
                BaseTransientBottomBar.this.mView.setTranslationY((float) currentAnimatedIntValue);
            }
            this.mPreviousAnimatedIntValue = currentAnimatedIntValue;
        }
    }

    public static abstract class BaseCallback<B> {
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_TIMEOUT = 2;

        @RestrictTo({Scope.LIBRARY_GROUP})
        @Retention(RetentionPolicy.SOURCE)
        public static @interface DismissEvent {
        }

        public void onDismissed(B b, int event) {
        }

        public void onShown(B b) {
        }
    }

    public static interface ContentViewCallback {
        void animateContentIn(int i, int i2);

        void animateContentOut(int i, int i2);
    }

    @IntRange(from = 1)
    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface Duration {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    static interface OnAttachStateChangeListener {
        void onViewAttachedToWindow(View view);

        void onViewDetachedFromWindow(View view);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    static interface OnLayoutChangeListener {
        void onLayoutChange(View view, int i, int i2, int i3, int i4);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    static class SnackbarBaseLayout extends FrameLayout {
        private OnAttachStateChangeListener mOnAttachStateChangeListener;
        private OnLayoutChangeListener mOnLayoutChangeListener;

        SnackbarBaseLayout(Context context) {
            this(context, null);
        }

        SnackbarBaseLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SnackbarLayout);
            if (a.hasValue(R.styleable.SnackbarLayout_elevation)) {
                ViewCompat.setElevation(this, (float) a.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, LENGTH_LONG));
            }
            a.recycle();
            setClickable(true);
        }

        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            if (this.mOnLayoutChangeListener != null) {
                this.mOnLayoutChangeListener.onLayoutChange(this, l, t, r, b);
            }
        }

        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            if (this.mOnAttachStateChangeListener != null) {
                this.mOnAttachStateChangeListener.onViewAttachedToWindow(this);
            }
            ViewCompat.requestApplyInsets(this);
        }

        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (this.mOnAttachStateChangeListener != null) {
                this.mOnAttachStateChangeListener.onViewDetachedFromWindow(this);
            }
        }

        void setOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
            this.mOnLayoutChangeListener = onLayoutChangeListener;
        }

        void setOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
            this.mOnAttachStateChangeListener = listener;
        }
    }

    final class Behavior extends SwipeDismissBehavior<SnackbarBaseLayout> {
        Behavior() {
        }

        public boolean canSwipeDismissView(View child) {
            return child instanceof SnackbarBaseLayout;
        }

        public boolean onInterceptTouchEvent(CoordinatorLayout parent, SnackbarBaseLayout child, MotionEvent event) {
            switch (event.getActionMasked()) {
                case LENGTH_LONG:
                    if (parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY())) {
                        SnackbarManager.getInstance().pauseTimeout(BaseTransientBottomBar.this.mManagerCallback);
                    }
                    break;
                case MSG_DISMISS:
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    SnackbarManager.getInstance().restoreTimeoutIfPaused(BaseTransientBottomBar.this.mManagerCallback);
                    break;
            }
            return super.onInterceptTouchEvent(parent, child, event);
        }
    }

    static {
        boolean z = VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 19;
        USE_OFFSET_API = z;
        sHandler = new Handler(Looper.getMainLooper(), new Callback() {
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case LENGTH_LONG:
                        ((BaseTransientBottomBar) message.obj).showView();
                        return true;
                    case MSG_DISMISS:
                        ((BaseTransientBottomBar) message.obj).hideView(message.arg1);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    protected BaseTransientBottomBar(@NonNull ViewGroup parent, @NonNull View content, @NonNull ContentViewCallback contentViewCallback) {
        this.mManagerCallback = new Callback() {
            public void show() {
                sHandler.sendMessage(sHandler.obtainMessage(LENGTH_LONG, BaseTransientBottomBar.this));
            }

            public void dismiss(int event) {
                sHandler.sendMessage(sHandler.obtainMessage(MSG_DISMISS, event, LENGTH_LONG, BaseTransientBottomBar.this));
            }
        };
        if (parent == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null parent");
        } else if (content == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null content");
        } else if (contentViewCallback == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null callback");
        } else {
            this.mTargetParent = parent;
            this.mContentViewCallback = contentViewCallback;
            this.mContext = parent.getContext();
            ThemeUtils.checkAppCompatTheme(this.mContext);
            this.mView = (SnackbarBaseLayout) LayoutInflater.from(this.mContext).inflate(R.layout.design_layout_snackbar, this.mTargetParent, false);
            this.mView.addView(content);
            ViewCompat.setAccessibilityLiveRegion(this.mView, MSG_DISMISS);
            ViewCompat.setImportantForAccessibility(this.mView, MSG_DISMISS);
            ViewCompat.setFitsSystemWindows(this.mView, true);
            ViewCompat.setOnApplyWindowInsetsListener(this.mView, new OnApplyWindowInsetsListener() {
                public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                    v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), insets.getSystemWindowInsetBottom());
                    return insets;
                }
            });
            this.mAccessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        }
    }

    @NonNull
    public B setDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

    public int getDuration() {
        return this.mDuration;
    }

    @NonNull
    public Context getContext() {
        return this.mContext;
    }

    @NonNull
    public View getView() {
        return this.mView;
    }

    public void show() {
        SnackbarManager.getInstance().show(this.mDuration, this.mManagerCallback);
    }

    public void dismiss() {
        dispatchDismiss(RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
    }

    void dispatchDismiss(int event) {
        SnackbarManager.getInstance().dismiss(this.mManagerCallback, event);
    }

    @NonNull
    public B addCallback(@NonNull BaseCallback<B> callback) {
        if (callback != null) {
            if (this.mCallbacks == null) {
                this.mCallbacks = new ArrayList();
            }
            this.mCallbacks.add(callback);
        }
        return this;
    }

    @NonNull
    public B removeCallback(@NonNull BaseCallback<B> callback) {
        if (!(callback == null || this.mCallbacks == null)) {
            this.mCallbacks.remove(callback);
        }
        return this;
    }

    public boolean isShown() {
        return SnackbarManager.getInstance().isCurrent(this.mManagerCallback);
    }

    public boolean isShownOrQueued() {
        return SnackbarManager.getInstance().isCurrentOrNext(this.mManagerCallback);
    }

    final void showView() {
        if (this.mView.getParent() == null) {
            LayoutParams lp = this.mView.getLayoutParams();
            if (lp instanceof CoordinatorLayout.LayoutParams) {
                CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams) lp;
                Behavior behavior = new Behavior();
                behavior.setStartAlphaSwipeDistance(0.1f);
                behavior.setEndAlphaSwipeDistance(0.6f);
                behavior.setSwipeDirection(LENGTH_LONG);
                behavior.setListener(new OnDismissListener() {
                    public void onDismiss(View view) {
                        view.setVisibility(DetectedActivity.RUNNING);
                        BaseTransientBottomBar.this.dispatchDismiss(LENGTH_LONG);
                    }

                    public void onDragStateChanged(int state) {
                        switch (state) {
                            case LENGTH_LONG:
                                SnackbarManager.getInstance().restoreTimeoutIfPaused(BaseTransientBottomBar.this.mManagerCallback);
                            case MSG_DISMISS:
                            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                                SnackbarManager.getInstance().pauseTimeout(BaseTransientBottomBar.this.mManagerCallback);
                            default:
                                break;
                        }
                    }
                });
                clp.setBehavior(behavior);
                clp.insetEdge = 80;
            }
            this.mTargetParent.addView(this.mView);
        }
        this.mView.setOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View v) {
            }

            public void onViewDetachedFromWindow(View v) {
                if (BaseTransientBottomBar.this.isShownOrQueued()) {
                    sHandler.post(new Runnable() {
                        public void run() {
                            BaseTransientBottomBar.this.onViewHidden(RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
                        }
                    });
                }
            }
        });
        if (!ViewCompat.isLaidOut(this.mView)) {
            this.mView.setOnLayoutChangeListener(new OnLayoutChangeListener() {
                public void onLayoutChange(View view, int left, int top, int right, int bottom) {
                    BaseTransientBottomBar.this.mView.setOnLayoutChangeListener(null);
                    if (BaseTransientBottomBar.this.shouldAnimate()) {
                        BaseTransientBottomBar.this.animateViewIn();
                    } else {
                        BaseTransientBottomBar.this.onViewShown();
                    }
                }
            });
        } else if (shouldAnimate()) {
            animateViewIn();
        } else {
            onViewShown();
        }
    }

    void animateViewIn() {
        if (VERSION.SDK_INT >= 12) {
            int viewHeight = this.mView.getHeight();
            if (USE_OFFSET_API) {
                ViewCompat.offsetTopAndBottom(this.mView, viewHeight);
            } else {
                this.mView.setTranslationY((float) viewHeight);
            }
            ValueAnimator animator = new ValueAnimator();
            animator.setIntValues(new int[]{viewHeight, 0});
            animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            animator.setDuration(250);
            animator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animator) {
                    BaseTransientBottomBar.this.mContentViewCallback.animateContentIn(net.oneplus.weather.R.styleable.AppCompatTheme_listChoiceBackgroundIndicator, ANIMATION_FADE_DURATION);
                }

                public void onAnimationEnd(Animator animator) {
                    BaseTransientBottomBar.this.onViewShown();
                }
            });
            animator.addUpdateListener(new AnonymousClass_8(viewHeight));
            animator.start();
            return;
        }
        Animation anim = AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.design_snackbar_in);
        anim.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        anim.setDuration(250);
        anim.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                BaseTransientBottomBar.this.onViewShown();
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.mView.startAnimation(anim);
    }

    private void animateViewOut(int event) {
        if (VERSION.SDK_INT >= 12) {
            ValueAnimator animator = new ValueAnimator();
            animator.setIntValues(new int[]{0, this.mView.getHeight()});
            animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            animator.setDuration(250);
            animator.addListener(new AnonymousClass_10(event));
            animator.addUpdateListener(new AnimatorUpdateListener() {
                private int mPreviousAnimatedIntValue;

                {
                    this.mPreviousAnimatedIntValue = 0;
                }

                public void onAnimationUpdate(ValueAnimator animator) {
                    int currentAnimatedIntValue = ((Integer) animator.getAnimatedValue()).intValue();
                    if (USE_OFFSET_API) {
                        ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.mView, currentAnimatedIntValue - this.mPreviousAnimatedIntValue);
                    } else {
                        BaseTransientBottomBar.this.mView.setTranslationY((float) currentAnimatedIntValue);
                    }
                    this.mPreviousAnimatedIntValue = currentAnimatedIntValue;
                }
            });
            animator.start();
            return;
        }
        Animation anim = AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.design_snackbar_out);
        anim.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        anim.setDuration(250);
        anim.setAnimationListener(new AnonymousClass_12(event));
        this.mView.startAnimation(anim);
    }

    final void hideView(int event) {
        if (shouldAnimate() && this.mView.getVisibility() == 0) {
            animateViewOut(event);
        } else {
            onViewHidden(event);
        }
    }

    void onViewShown() {
        SnackbarManager.getInstance().onShown(this.mManagerCallback);
        if (this.mCallbacks != null) {
            for (int i = this.mCallbacks.size() - 1; i >= 0; i--) {
                ((BaseCallback) this.mCallbacks.get(i)).onShown(this);
            }
        }
    }

    void onViewHidden(int event) {
        SnackbarManager.getInstance().onDismissed(this.mManagerCallback);
        if (this.mCallbacks != null) {
            for (int i = this.mCallbacks.size() - 1; i >= 0; i--) {
                ((BaseCallback) this.mCallbacks.get(i)).onDismissed(this, event);
            }
        }
        if (VERSION.SDK_INT < 11) {
            this.mView.setVisibility(DetectedActivity.RUNNING);
        }
        ViewParent parent = this.mView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this.mView);
        }
    }

    boolean shouldAnimate() {
        return !this.mAccessibilityManager.isEnabled();
    }
}
