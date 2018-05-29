package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.support.design.R;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.math.MathUtils;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class BottomSheetBehavior<V extends View> extends Behavior<V> {
    private static final float HIDE_FRICTION = 0.1f;
    private static final float HIDE_THRESHOLD = 0.5f;
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    int mActivePointerId;
    private BottomSheetCallback mCallback;
    private final Callback mDragCallback;
    boolean mHideable;
    private boolean mIgnoreEvents;
    private int mInitialY;
    private int mLastNestedScrollDy;
    int mMaxOffset;
    private float mMaximumVelocity;
    int mMinOffset;
    private boolean mNestedScrolled;
    WeakReference<View> mNestedScrollingChildRef;
    int mParentHeight;
    private int mPeekHeight;
    private boolean mPeekHeightAuto;
    private int mPeekHeightMin;
    private boolean mSkipCollapsed;
    int mState;
    boolean mTouchingScrollingChild;
    private VelocityTracker mVelocityTracker;
    ViewDragHelper mViewDragHelper;
    WeakReference<V> mViewRef;

    class AnonymousClass_1 implements Runnable {
        final /* synthetic */ View val$child;
        final /* synthetic */ int val$state;

        AnonymousClass_1(View view, int i) {
            this.val$child = view;
            this.val$state = i;
        }

        public void run() {
            BottomSheetBehavior.this.startSettlingAnimation(this.val$child, this.val$state);
        }
    }

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(@NonNull View view, float f);

        public abstract void onStateChanged(@NonNull View view, int i);
    }

    private class SettleRunnable implements Runnable {
        private final int mTargetState;
        private final View mView;

        SettleRunnable(View view, int targetState) {
            this.mView = view;
            this.mTargetState = targetState;
        }

        public void run() {
            if (BottomSheetBehavior.this.mViewDragHelper == null || !BottomSheetBehavior.this.mViewDragHelper.continueSettling(true)) {
                BottomSheetBehavior.this.setStateInternal(this.mTargetState);
            } else {
                ViewCompat.postOnAnimation(this.mView, this);
            }
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface State {
    }

    protected static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR;
        final int state;

        public SavedState(Parcel source) {
            this(source, null);
        }

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            this.state = source.readInt();
        }

        public SavedState(Parcelable superState, int state) {
            super(superState);
            this.state = state;
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.state);
        }

        static {
            CREATOR = new ClassLoaderCreator<SavedState>() {
                public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                    return new SavedState(in, loader);
                }

                public SavedState createFromParcel(Parcel in) {
                    return new SavedState(in, null);
                }

                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            };
        }
    }

    public BottomSheetBehavior() {
        this.mState = 4;
        this.mDragCallback = new Callback() {
            public boolean tryCaptureView(View child, int pointerId) {
                Object obj = STATE_DRAGGING;
                if (BottomSheetBehavior.this.mState == 1 || BottomSheetBehavior.this.mTouchingScrollingChild) {
                    return false;
                }
                boolean z;
                if (BottomSheetBehavior.this.mState == 3 && BottomSheetBehavior.this.mActivePointerId == pointerId) {
                    View scroll = (View) BottomSheetBehavior.this.mNestedScrollingChildRef.get();
                    if (scroll != null && scroll.canScrollVertically(PEEK_HEIGHT_AUTO)) {
                        return false;
                    }
                }
                if (BottomSheetBehavior.this.mViewRef == null || BottomSheetBehavior.this.mViewRef.get() != child) {
                    z = false;
                }
                return z;
            }

            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                BottomSheetBehavior.this.dispatchOnSlide(top);
            }

            public void onViewDragStateChanged(int state) {
                if (state == 1) {
                    BottomSheetBehavior.this.setStateInternal(STATE_DRAGGING);
                }
            }

            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                int top;
                int targetState;
                if (yvel < 0.0f) {
                    top = BottomSheetBehavior.this.mMinOffset;
                    targetState = STATE_EXPANDED;
                } else if (BottomSheetBehavior.this.mHideable && BottomSheetBehavior.this.shouldHide(releasedChild, yvel)) {
                    top = BottomSheetBehavior.this.mParentHeight;
                    targetState = STATE_HIDDEN;
                } else if (yvel == 0.0f) {
                    int currentTop = releasedChild.getTop();
                    if (Math.abs(currentTop - BottomSheetBehavior.this.mMinOffset) < Math.abs(currentTop - BottomSheetBehavior.this.mMaxOffset)) {
                        top = BottomSheetBehavior.this.mMinOffset;
                        targetState = STATE_EXPANDED;
                    } else {
                        top = BottomSheetBehavior.this.mMaxOffset;
                        targetState = STATE_COLLAPSED;
                    }
                } else {
                    top = BottomSheetBehavior.this.mMaxOffset;
                    targetState = STATE_COLLAPSED;
                }
                if (BottomSheetBehavior.this.mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top)) {
                    BottomSheetBehavior.this.setStateInternal(STATE_SETTLING);
                    ViewCompat.postOnAnimation(releasedChild, new SettleRunnable(releasedChild, targetState));
                    return;
                }
                BottomSheetBehavior.this.setStateInternal(targetState);
            }

            public int clampViewPositionVertical(View child, int top, int dy) {
                return MathUtils.clamp(top, BottomSheetBehavior.this.mMinOffset, BottomSheetBehavior.this.mHideable ? BottomSheetBehavior.this.mParentHeight : BottomSheetBehavior.this.mMaxOffset);
            }

            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return child.getLeft();
            }

            public int getViewVerticalDragRange(View child) {
                return BottomSheetBehavior.this.mHideable ? BottomSheetBehavior.this.mParentHeight - BottomSheetBehavior.this.mMinOffset : BottomSheetBehavior.this.mMaxOffset - BottomSheetBehavior.this.mMinOffset;
            }
        };
    }

    public BottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mState = 4;
        this.mDragCallback = new Callback() {
            public boolean tryCaptureView(View child, int pointerId) {
                Object obj = STATE_DRAGGING;
                if (BottomSheetBehavior.this.mState == 1 || BottomSheetBehavior.this.mTouchingScrollingChild) {
                    return false;
                }
                boolean z;
                if (BottomSheetBehavior.this.mState == 3 && BottomSheetBehavior.this.mActivePointerId == pointerId) {
                    View scroll = (View) BottomSheetBehavior.this.mNestedScrollingChildRef.get();
                    if (scroll != null && scroll.canScrollVertically(PEEK_HEIGHT_AUTO)) {
                        return false;
                    }
                }
                if (BottomSheetBehavior.this.mViewRef == null || BottomSheetBehavior.this.mViewRef.get() != child) {
                    z = false;
                }
                return z;
            }

            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                BottomSheetBehavior.this.dispatchOnSlide(top);
            }

            public void onViewDragStateChanged(int state) {
                if (state == 1) {
                    BottomSheetBehavior.this.setStateInternal(STATE_DRAGGING);
                }
            }

            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                int top;
                int targetState;
                if (yvel < 0.0f) {
                    top = BottomSheetBehavior.this.mMinOffset;
                    targetState = STATE_EXPANDED;
                } else if (BottomSheetBehavior.this.mHideable && BottomSheetBehavior.this.shouldHide(releasedChild, yvel)) {
                    top = BottomSheetBehavior.this.mParentHeight;
                    targetState = STATE_HIDDEN;
                } else if (yvel == 0.0f) {
                    int currentTop = releasedChild.getTop();
                    if (Math.abs(currentTop - BottomSheetBehavior.this.mMinOffset) < Math.abs(currentTop - BottomSheetBehavior.this.mMaxOffset)) {
                        top = BottomSheetBehavior.this.mMinOffset;
                        targetState = STATE_EXPANDED;
                    } else {
                        top = BottomSheetBehavior.this.mMaxOffset;
                        targetState = STATE_COLLAPSED;
                    }
                } else {
                    top = BottomSheetBehavior.this.mMaxOffset;
                    targetState = STATE_COLLAPSED;
                }
                if (BottomSheetBehavior.this.mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top)) {
                    BottomSheetBehavior.this.setStateInternal(STATE_SETTLING);
                    ViewCompat.postOnAnimation(releasedChild, new SettleRunnable(releasedChild, targetState));
                    return;
                }
                BottomSheetBehavior.this.setStateInternal(targetState);
            }

            public int clampViewPositionVertical(View child, int top, int dy) {
                return MathUtils.clamp(top, BottomSheetBehavior.this.mMinOffset, BottomSheetBehavior.this.mHideable ? BottomSheetBehavior.this.mParentHeight : BottomSheetBehavior.this.mMaxOffset);
            }

            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return child.getLeft();
            }

            public int getViewVerticalDragRange(View child) {
                return BottomSheetBehavior.this.mHideable ? BottomSheetBehavior.this.mParentHeight - BottomSheetBehavior.this.mMinOffset : BottomSheetBehavior.this.mMaxOffset - BottomSheetBehavior.this.mMinOffset;
            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomSheetBehavior_Layout);
        TypedValue value = a.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (value == null || value.data != -1) {
            setPeekHeight(a.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, PEEK_HEIGHT_AUTO));
        } else {
            setPeekHeight(value.data);
        }
        setHideable(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setSkipCollapsed(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        a.recycle();
        this.mMaximumVelocity = (float) ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    public Parcelable onSaveInstanceState(CoordinatorLayout parent, V child) {
        return new SavedState(super.onSaveInstanceState(parent, child), this.mState);
    }

    public void onRestoreInstanceState(CoordinatorLayout parent, V child, Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(parent, child, ss.getSuperState());
        if (ss.state == 1 || ss.state == 2) {
            this.mState = 4;
        } else {
            this.mState = ss.state;
        }
    }

    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        int peekHeight;
        if (ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
            ViewCompat.setFitsSystemWindows(child, true);
        }
        int savedTop = child.getTop();
        parent.onLayoutChild(child, layoutDirection);
        this.mParentHeight = parent.getHeight();
        if (this.mPeekHeightAuto) {
            if (this.mPeekHeightMin == 0) {
                this.mPeekHeightMin = parent.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            }
            peekHeight = Math.max(this.mPeekHeightMin, this.mParentHeight - ((parent.getWidth() * 9) / 16));
        } else {
            peekHeight = this.mPeekHeight;
        }
        this.mMinOffset = Math.max(0, this.mParentHeight - child.getHeight());
        this.mMaxOffset = Math.max(this.mParentHeight - peekHeight, this.mMinOffset);
        if (this.mState == 3) {
            ViewCompat.offsetTopAndBottom(child, this.mMinOffset);
        } else if (this.mHideable && this.mState == 5) {
            ViewCompat.offsetTopAndBottom(child, this.mParentHeight);
        } else if (this.mState == 4) {
            ViewCompat.offsetTopAndBottom(child, this.mMaxOffset);
        } else if (this.mState == 1 || this.mState == 2) {
            ViewCompat.offsetTopAndBottom(child, savedTop - child.getTop());
        }
        if (this.mViewDragHelper == null) {
            this.mViewDragHelper = ViewDragHelper.create(parent, this.mDragCallback);
        }
        this.mViewRef = new WeakReference(child);
        this.mNestedScrollingChildRef = new WeakReference(findScrollingChild(child));
        return true;
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        boolean z = true;
        if (child.isShown()) {
            View scroll;
            int action = event.getActionMasked();
            if (action == 0) {
                reset();
            }
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(event);
            switch (action) {
                case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                    int initialX = (int) event.getX();
                    this.mInitialY = (int) event.getY();
                    scroll = this.mNestedScrollingChildRef != null ? (View) this.mNestedScrollingChildRef.get() : null;
                    if (scroll != null && parent.isPointInChildBounds(scroll, initialX, this.mInitialY)) {
                        this.mActivePointerId = event.getPointerId(event.getActionIndex());
                        this.mTouchingScrollingChild = true;
                    }
                    boolean z2 = this.mActivePointerId == -1 && !parent.isPointInChildBounds(child, initialX, this.mInitialY);
                    this.mIgnoreEvents = z2;
                    break;
                case STATE_DRAGGING:
                case STATE_EXPANDED:
                    this.mTouchingScrollingChild = false;
                    this.mActivePointerId = -1;
                    if (this.mIgnoreEvents) {
                        this.mIgnoreEvents = false;
                        return false;
                    }
            }
            if (!this.mIgnoreEvents && this.mViewDragHelper.shouldInterceptTouchEvent(event)) {
                return true;
            }
            scroll = (View) this.mNestedScrollingChildRef.get();
            if (action != 2 || scroll == null || this.mIgnoreEvents || this.mState == 1 || parent.isPointInChildBounds(scroll, (int) event.getX(), (int) event.getY()) || Math.abs(((float) this.mInitialY) - event.getY()) <= ((float) this.mViewDragHelper.getTouchSlop())) {
                z = false;
            }
            return z;
        }
        this.mIgnoreEvents = true;
        return false;
    }

    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        if (!child.isShown()) {
            return false;
        }
        int action = event.getActionMasked();
        if (this.mState == 1 && action == 0) {
            return true;
        }
        this.mViewDragHelper.processTouchEvent(event);
        if (action == 0) {
            reset();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(event);
        if (action == 2 && !this.mIgnoreEvents && Math.abs(((float) this.mInitialY) - event.getY()) > ((float) this.mViewDragHelper.getTouchSlop())) {
            this.mViewDragHelper.captureChildView(child, event.getPointerId(event.getActionIndex()));
        }
        return !this.mIgnoreEvents;
    }

    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View directTargetChild, View target, int nestedScrollAxes) {
        this.mLastNestedScrollDy = 0;
        this.mNestedScrolled = false;
        return (nestedScrollAxes & 2) != 0;
    }

    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed) {
        if (target == ((View) this.mNestedScrollingChildRef.get())) {
            int currentTop = child.getTop();
            int newTop = currentTop - dy;
            if (dy > 0) {
                if (newTop < this.mMinOffset) {
                    consumed[1] = currentTop - this.mMinOffset;
                    ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                    setStateInternal(STATE_EXPANDED);
                } else {
                    consumed[1] = dy;
                    ViewCompat.offsetTopAndBottom(child, -dy);
                    setStateInternal(STATE_DRAGGING);
                }
            } else if (dy < 0 && !target.canScrollVertically(PEEK_HEIGHT_AUTO)) {
                if (newTop <= this.mMaxOffset || this.mHideable) {
                    consumed[1] = dy;
                    ViewCompat.offsetTopAndBottom(child, -dy);
                    setStateInternal(STATE_DRAGGING);
                } else {
                    consumed[1] = currentTop - this.mMaxOffset;
                    ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                    setStateInternal(STATE_COLLAPSED);
                }
            }
            dispatchOnSlide(child.getTop());
            this.mLastNestedScrollDy = dy;
            this.mNestedScrolled = true;
        }
    }

    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target) {
        if (child.getTop() == this.mMinOffset) {
            setStateInternal(STATE_EXPANDED);
        } else if (this.mNestedScrollingChildRef != null && target == this.mNestedScrollingChildRef.get() && this.mNestedScrolled) {
            int top;
            int targetState;
            if (this.mLastNestedScrollDy > 0) {
                top = this.mMinOffset;
                targetState = STATE_EXPANDED;
            } else if (this.mHideable && shouldHide(child, getYVelocity())) {
                top = this.mParentHeight;
                targetState = STATE_HIDDEN;
            } else if (this.mLastNestedScrollDy == 0) {
                int currentTop = child.getTop();
                if (Math.abs(currentTop - this.mMinOffset) < Math.abs(currentTop - this.mMaxOffset)) {
                    top = this.mMinOffset;
                    targetState = STATE_EXPANDED;
                } else {
                    top = this.mMaxOffset;
                    targetState = STATE_COLLAPSED;
                }
            } else {
                top = this.mMaxOffset;
                targetState = STATE_COLLAPSED;
            }
            if (this.mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
                setStateInternal(STATE_SETTLING);
                ViewCompat.postOnAnimation(child, new SettleRunnable(child, targetState));
            } else {
                setStateInternal(targetState);
            }
            this.mNestedScrolled = false;
        }
    }

    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY) {
        return target == this.mNestedScrollingChildRef.get() && (this.mState != 3 || super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY));
    }

    public final void setPeekHeight(int peekHeight) {
        boolean layout = false;
        if (peekHeight == -1) {
            if (!this.mPeekHeightAuto) {
                this.mPeekHeightAuto = true;
                layout = true;
            }
        } else if (this.mPeekHeightAuto || this.mPeekHeight != peekHeight) {
            this.mPeekHeightAuto = false;
            this.mPeekHeight = Math.max(0, peekHeight);
            this.mMaxOffset = this.mParentHeight - peekHeight;
            layout = true;
        }
        if (layout && this.mState == 4 && this.mViewRef != null) {
            View view = (View) this.mViewRef.get();
            if (view != null) {
                view.requestLayout();
            }
        }
    }

    public final int getPeekHeight() {
        return this.mPeekHeightAuto ? PEEK_HEIGHT_AUTO : this.mPeekHeight;
    }

    public void setHideable(boolean hideable) {
        this.mHideable = hideable;
    }

    public boolean isHideable() {
        return this.mHideable;
    }

    public void setSkipCollapsed(boolean skipCollapsed) {
        this.mSkipCollapsed = skipCollapsed;
    }

    public boolean getSkipCollapsed() {
        return this.mSkipCollapsed;
    }

    public void setBottomSheetCallback(BottomSheetCallback callback) {
        this.mCallback = callback;
    }

    public final void setState(int state) {
        if (state != this.mState) {
            if (this.mViewRef != null) {
                View child = (View) this.mViewRef.get();
                if (child != null) {
                    ViewParent parent = child.getParent();
                    if (parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(child)) {
                        child.post(new AnonymousClass_1(child, state));
                    } else {
                        startSettlingAnimation(child, state);
                    }
                }
            } else if (state == 4 || state == 3 || (this.mHideable && state == 5)) {
                this.mState = state;
            }
        }
    }

    public final int getState() {
        return this.mState;
    }

    void setStateInternal(int state) {
        if (this.mState != state) {
            this.mState = state;
            View bottomSheet = (View) this.mViewRef.get();
            if (bottomSheet != null && this.mCallback != null) {
                this.mCallback.onStateChanged(bottomSheet, state);
            }
        }
    }

    private void reset() {
        this.mActivePointerId = -1;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    boolean shouldHide(View child, float yvel) {
        if (this.mSkipCollapsed) {
            return true;
        }
        if (child.getTop() < this.mMaxOffset) {
            return false;
        }
        return Math.abs((((float) child.getTop()) + (0.1f * yvel)) - ((float) this.mMaxOffset)) / ((float) this.mPeekHeight) > 0.5f;
    }

    @VisibleForTesting
    View findScrollingChild(View view) {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View scrollingChild = findScrollingChild(group.getChildAt(i));
                if (scrollingChild != null) {
                    return scrollingChild;
                }
            }
        }
        return null;
    }

    private float getYVelocity() {
        this.mVelocityTracker.computeCurrentVelocity(GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, this.mMaximumVelocity);
        return this.mVelocityTracker.getYVelocity(this.mActivePointerId);
    }

    void startSettlingAnimation(View child, int state) {
        int top;
        if (state == 4) {
            top = this.mMaxOffset;
        } else if (state == 3) {
            top = this.mMinOffset;
        } else if (this.mHideable && state == 5) {
            top = this.mParentHeight;
        } else {
            throw new IllegalArgumentException("Illegal state argument: " + state);
        }
        if (this.mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
            setStateInternal(STATE_SETTLING);
            ViewCompat.postOnAnimation(child, new SettleRunnable(child, state));
            return;
        }
        setStateInternal(state);
    }

    void dispatchOnSlide(int top) {
        View bottomSheet = (View) this.mViewRef.get();
        if (bottomSheet != null && this.mCallback != null) {
            if (top > this.mMaxOffset) {
                this.mCallback.onSlide(bottomSheet, ((float) (this.mMaxOffset - top)) / ((float) (this.mParentHeight - this.mMaxOffset)));
            } else {
                this.mCallback.onSlide(bottomSheet, ((float) (this.mMaxOffset - top)) / ((float) (this.mMaxOffset - this.mMinOffset)));
            }
        }
    }

    @VisibleForTesting
    int getPeekHeightMin() {
        return this.mPeekHeightMin;
    }

    public static <V extends View> BottomSheetBehavior<V> from(V view) {
        LayoutParams params = view.getLayoutParams();
        if (params instanceof CoordinatorLayout.LayoutParams) {
            Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
            if (behavior instanceof BottomSheetBehavior) {
                return (BottomSheetBehavior) behavior;
            }
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
    }
}
