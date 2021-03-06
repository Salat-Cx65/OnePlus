package com.oneplus.lib.widget.recyclerview;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.widget.AutoScrollHelper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import com.android.volley.DefaultRetryPolicy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.oneplus.commonctrl.R;
import com.oneplus.lib.widget.recyclerview.ItemTouchHelper.Callback;
import com.oneplus.lib.widget.recyclerview.ItemTouchHelper.ViewDropHandler;
import com.oneplus.lib.widget.recyclerview.RecyclerView.ChildDrawingOrderCallback;
import com.oneplus.lib.widget.recyclerview.RecyclerView.ItemAnimator;
import com.oneplus.lib.widget.recyclerview.RecyclerView.ItemDecoration;
import com.oneplus.lib.widget.recyclerview.RecyclerView.LayoutManager;
import com.oneplus.lib.widget.recyclerview.RecyclerView.OnChildAttachStateChangeListener;
import com.oneplus.lib.widget.recyclerview.RecyclerView.OnItemTouchListener;
import com.oneplus.lib.widget.recyclerview.RecyclerView.State;
import com.oneplus.lib.widget.recyclerview.RecyclerView.ViewHolder;
import java.util.ArrayList;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class ItemTouchHelper extends ItemDecoration implements OnChildAttachStateChangeListener {
    private static final int ACTION_MODE_DRAG_MASK = 16711680;
    private static final int ACTION_MODE_IDLE_MASK = 255;
    private static final int ACTION_MODE_SWIPE_MASK = 65280;
    public static final int ACTION_STATE_DRAG = 2;
    public static final int ACTION_STATE_IDLE = 0;
    public static final int ACTION_STATE_SWIPE = 1;
    private static final int ACTIVE_POINTER_ID_NONE = -1;
    public static final int ANIMATION_TYPE_DRAG = 8;
    public static final int ANIMATION_TYPE_SWIPE_CANCEL = 4;
    public static final int ANIMATION_TYPE_SWIPE_SUCCESS = 2;
    private static final boolean DEBUG = false;
    private static final int DIRECTION_FLAG_COUNT = 8;
    public static final int DOWN = 2;
    public static final int END = 32;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int START = 16;
    private static final String TAG = "ItemTouchHelper";
    public static final int UP = 1;
    int mActionState;
    int mActivePointerId;
    Callback mCallback;
    private ChildDrawingOrderCallback mChildDrawingOrderCallback;
    private List<Integer> mDistances;
    private long mDragScrollStartTimeInMs;
    float mDx;
    float mDy;
    private GestureDetector mGestureDetector;
    float mInitialTouchX;
    float mInitialTouchY;
    private final OnItemTouchListener mOnItemTouchListener;
    private View mOverdrawChild;
    private int mOverdrawChildPosition;
    final List<View> mPendingCleanup;
    List<RecoverAnimation> mRecoverAnimations;
    private RecyclerView mRecyclerView;
    private final Runnable mScrollRunnable;
    ViewHolder mSelected;
    int mSelectedFlags;
    float mSelectedStartX;
    float mSelectedStartY;
    private int mSlop;
    private List<ViewHolder> mSwapTargets;
    private final float[] mTmpPosition;
    private Rect mTmpRect;
    private VelocityTracker mVelocityTracker;

    class AnonymousClass_4 implements Runnable {
        final /* synthetic */ RecoverAnimation val$anim;
        final /* synthetic */ int val$swipeDir;

        AnonymousClass_4(RecoverAnimation recoverAnimation, int i) {
            this.val$anim = recoverAnimation;
            this.val$swipeDir = i;
        }

        public void run() {
            if (ItemTouchHelper.this.mRecyclerView != null && ItemTouchHelper.this.mRecyclerView.isAttachedToWindow() && !this.val$anim.mOverridden && this.val$anim.mViewHolder.getAdapterPosition() != -1) {
                ItemAnimator animator = ItemTouchHelper.this.mRecyclerView.getItemAnimator();
                if ((animator == null || !animator.isRunning(null)) && !ItemTouchHelper.this.hasRunningRecoverAnim()) {
                    ItemTouchHelper.this.mCallback.onSwiped(this.val$anim.mViewHolder, this.val$swipeDir);
                } else {
                    ItemTouchHelper.this.mRecyclerView.post(this);
                }
            }
        }
    }

    public static abstract class Callback {
        private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
        public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
        public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
        private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 2000;
        static final int RELATIVE_DIR_FLAGS = 3158064;
        private static final Interpolator sDragScrollInterpolator;
        private static final Interpolator sDragViewScrollCapInterpolator;
        private static final ItemTouchUIUtil sUICallback;
        private int mCachedMaxScrollSpeed;

        static class ItemTouchUIUtilImplHoneycomb implements ItemTouchUIUtil {
            ItemTouchUIUtilImplHoneycomb() {
            }

            public void clearView(View view) {
                view.setTranslationX(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                view.setTranslationY(AutoScrollHelper.RELATIVE_UNSPECIFIED);
            }

            public void onSelected(View view) {
            }

            public void onDraw(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                view.setTranslationX(dX);
                view.setTranslationY(dY);
            }

            public void onDrawOver(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            }
        }

        static class ItemTouchUIUtilImplLollipop extends ItemTouchUIUtilImplHoneycomb {
            ItemTouchUIUtilImplLollipop() {
            }

            public void onDraw(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (isCurrentlyActive && view.getTag(R.id.item_touch_helper_previous_elevation) == null) {
                    Float originalElevation = Float.valueOf(view.getElevation());
                    view.setElevation(1.0f + findMaxElevation(recyclerView, view));
                    view.setTag(R.id.item_touch_helper_previous_elevation, originalElevation);
                }
                super.onDraw(c, recyclerView, view, dX, dY, actionState, isCurrentlyActive);
            }

            private float findMaxElevation(RecyclerView recyclerView, View itemView) {
                int childCount = recyclerView.getChildCount();
                float max = AutoScrollHelper.RELATIVE_UNSPECIFIED;
                for (int i = ACTION_STATE_IDLE; i < childCount; i++) {
                    View child = recyclerView.getChildAt(i);
                    if (child != itemView) {
                        float elevation = child.getElevation();
                        if (elevation > max) {
                            max = elevation;
                        }
                    }
                }
                return max;
            }

            public void clearView(View view) {
                Object tag = view.getTag(R.id.item_touch_helper_previous_elevation);
                if (tag != null && (tag instanceof Float)) {
                    view.setElevation(((Float) tag).floatValue());
                }
                view.setTag(R.id.item_touch_helper_previous_elevation, null);
                super.clearView(view);
            }
        }

        public abstract int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder);

        public abstract boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder viewHolder2);

        public abstract void onSwiped(ViewHolder viewHolder, int i);

        public Callback() {
            this.mCachedMaxScrollSpeed = -1;
        }

        static {
            sDragScrollInterpolator = new Interpolator() {
                public float getInterpolation(float t) {
                    return (((t * t) * t) * t) * t;
                }
            };
            sDragViewScrollCapInterpolator = new Interpolator() {
                public float getInterpolation(float t) {
                    t -= 1.0f;
                    return ((((t * t) * t) * t) * t) + 1.0f;
                }
            };
            sUICallback = new ItemTouchUIUtilImplLollipop();
        }

        public static ItemTouchUIUtil getDefaultUIUtil() {
            return sUICallback;
        }

        public static int convertToRelativeDirection(int flags, int layoutDirection) {
            int masked = flags & 789516;
            if (masked == 0) {
                return flags;
            }
            flags &= masked ^ -1;
            return layoutDirection == 0 ? flags | (masked << 2) : (flags | ((masked << 1) & -789517)) | (((masked << 1) & 789516) << 2);
        }

        public static int makeMovementFlags(int dragFlags, int swipeFlags) {
            return (makeFlag(ACTION_STATE_IDLE, swipeFlags | dragFlags) | makeFlag(UP, swipeFlags)) | makeFlag(DOWN, dragFlags);
        }

        public static int makeFlag(int actionState, int directions) {
            return directions << (actionState * 8);
        }

        public int convertToAbsoluteDirection(int flags, int layoutDirection) {
            int masked = flags & 3158064;
            if (masked == 0) {
                return flags;
            }
            flags &= masked ^ -1;
            return layoutDirection == 0 ? flags | (masked >> 2) : (flags | ((masked >> 1) & -3158065)) | (((masked >> 1) & 3158064) >> 2);
        }

        final int getAbsoluteMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
            return convertToAbsoluteDirection(getMovementFlags(recyclerView, viewHolder), recyclerView.getLayoutDirection());
        }

        private boolean hasDragFlag(RecyclerView recyclerView, ViewHolder viewHolder) {
            return (16711680 & getAbsoluteMovementFlags(recyclerView, viewHolder)) != 0 ? true : DEBUG;
        }

        private boolean hasSwipeFlag(RecyclerView recyclerView, ViewHolder viewHolder) {
            return (65280 & getAbsoluteMovementFlags(recyclerView, viewHolder)) != 0 ? true : DEBUG;
        }

        public boolean canDropOver(RecyclerView recyclerView, ViewHolder current, ViewHolder target) {
            return true;
        }

        public boolean isLongPressDragEnabled() {
            return true;
        }

        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        public int getBoundingBoxMargin() {
            return ACTION_STATE_IDLE;
        }

        public float getSwipeThreshold(ViewHolder viewHolder) {
            return 0.5f;
        }

        public float getMoveThreshold(ViewHolder viewHolder) {
            return 0.5f;
        }

        public ViewHolder chooseDropTarget(ViewHolder selected, List<ViewHolder> dropTargets, int curX, int curY) {
            int right = curX + selected.itemView.getWidth();
            int bottom = curY + selected.itemView.getHeight();
            ViewHolder winner = null;
            int winnerScore = ACTIVE_POINTER_ID_NONE;
            int dx = curX - selected.itemView.getLeft();
            int dy = curY - selected.itemView.getTop();
            int targetsSize = dropTargets.size();
            for (int i = ACTION_STATE_IDLE; i < targetsSize; i++) {
                int diff;
                int score;
                ViewHolder target = (ViewHolder) dropTargets.get(i);
                if (dx > 0) {
                    diff = target.itemView.getRight() - right;
                    if (diff < 0 && target.itemView.getRight() > selected.itemView.getRight()) {
                        score = Math.abs(diff);
                        if (score > winnerScore) {
                            winnerScore = score;
                            winner = target;
                        }
                    }
                }
                if (dx < 0) {
                    diff = target.itemView.getLeft() - curX;
                    if (diff > 0 && target.itemView.getLeft() < selected.itemView.getLeft()) {
                        score = Math.abs(diff);
                        if (score > winnerScore) {
                            winnerScore = score;
                            winner = target;
                        }
                    }
                }
                if (dy < 0) {
                    diff = target.itemView.getTop() - curY;
                    if (diff > 0 && target.itemView.getTop() < selected.itemView.getTop()) {
                        score = Math.abs(diff);
                        if (score > winnerScore) {
                            winnerScore = score;
                            winner = target;
                        }
                    }
                }
                if (dy > 0) {
                    diff = target.itemView.getBottom() - bottom;
                    if (diff < 0 && target.itemView.getBottom() > selected.itemView.getBottom()) {
                        score = Math.abs(diff);
                        if (score > winnerScore) {
                            winnerScore = score;
                            winner = target;
                        }
                    }
                }
            }
            return winner;
        }

        public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
            if (viewHolder != null) {
                sUICallback.onSelected(viewHolder.itemView);
            }
        }

        private int getMaxDragScroll(RecyclerView recyclerView) {
            if (this.mCachedMaxScrollSpeed == -1) {
                this.mCachedMaxScrollSpeed = recyclerView.getResources().getDimensionPixelSize(R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            return this.mCachedMaxScrollSpeed;
        }

        public void onMoved(RecyclerView recyclerView, ViewHolder viewHolder, int fromPos, ViewHolder target, int toPos, int x, int y) {
            LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof ViewDropHandler) {
                ((ViewDropHandler) layoutManager).prepareForDrop(viewHolder.itemView, target.itemView, x, y);
                return;
            }
            if (layoutManager.canScrollHorizontally()) {
                if (layoutManager.getDecoratedLeft(target.itemView) <= recyclerView.getPaddingLeft()) {
                    recyclerView.scrollToPosition(toPos);
                }
                if (layoutManager.getDecoratedRight(target.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                    recyclerView.scrollToPosition(toPos);
                }
            }
            if (layoutManager.canScrollVertically()) {
                if (layoutManager.getDecoratedTop(target.itemView) <= recyclerView.getPaddingTop()) {
                    recyclerView.scrollToPosition(toPos);
                }
                if (layoutManager.getDecoratedBottom(target.itemView) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                    recyclerView.scrollToPosition(toPos);
                }
            }
        }

        private void onDraw(Canvas c, RecyclerView parent, ViewHolder selected, List<RecoverAnimation> recoverAnimationList, int actionState, float dX, float dY) {
            int recoverAnimSize = recoverAnimationList.size();
            for (int i = ACTION_STATE_IDLE; i < recoverAnimSize; i++) {
                RecoverAnimation anim = (RecoverAnimation) recoverAnimationList.get(i);
                anim.update();
                int count = c.save();
                onChildDraw(c, parent, anim.mViewHolder, anim.mX, anim.mY, anim.mActionState, DEBUG);
                c.restoreToCount(count);
            }
            if (selected != null) {
                count = c.save();
                onChildDraw(c, parent, selected, dX, dY, actionState, true);
                c.restoreToCount(count);
            }
        }

        private void onDrawOver(Canvas c, RecyclerView parent, ViewHolder selected, List<RecoverAnimation> recoverAnimationList, int actionState, float dX, float dY) {
            int i;
            int recoverAnimSize = recoverAnimationList.size();
            for (i = ACTION_STATE_IDLE; i < recoverAnimSize; i++) {
                RecoverAnimation anim = (RecoverAnimation) recoverAnimationList.get(i);
                int count = c.save();
                onChildDrawOver(c, parent, anim.mViewHolder, anim.mX, anim.mY, anim.mActionState, DEBUG);
                c.restoreToCount(count);
            }
            if (selected != null) {
                count = c.save();
                onChildDrawOver(c, parent, selected, dX, dY, actionState, true);
                c.restoreToCount(count);
            }
            boolean hasRunningAnimation = DEBUG;
            for (i = recoverAnimSize - 1; i >= 0; i--) {
                anim = (RecoverAnimation) recoverAnimationList.get(i);
                if (anim.mEnded && !anim.mIsPendingCleanup) {
                    recoverAnimationList.remove(i);
                    anim.mViewHolder.setIsRecyclable(true);
                } else if (!anim.mEnded) {
                    hasRunningAnimation = true;
                }
            }
            if (hasRunningAnimation) {
                parent.invalidate();
            }
        }

        public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
            sUICallback.clearView(viewHolder.itemView);
        }

        public void onChildDraw(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            sUICallback.onDraw(c, recyclerView, viewHolder.itemView, dX, dY, actionState, isCurrentlyActive);
        }

        public void onChildDrawOver(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            sUICallback.onDrawOver(c, recyclerView, viewHolder.itemView, dX, dY, actionState, isCurrentlyActive);
        }

        public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
            ItemAnimator itemAnimator = recyclerView.getItemAnimator();
            return itemAnimator == null ? animationType == 8 ? 200 : 250 : animationType == 8 ? itemAnimator.getMoveDuration() : itemAnimator.getRemoveDuration();
        }

        public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int viewSize, int viewSizeOutOfBounds, int totalSize, long msSinceStartScroll) {
            int value = (int) (((float) ((int) (((float) (((int) Math.signum((float) viewSizeOutOfBounds)) * getMaxDragScroll(recyclerView))) * sDragViewScrollCapInterpolator.getInterpolation(Math.min(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, (1.0f * ((float) Math.abs(viewSizeOutOfBounds))) / ((float) viewSize)))))) * sDragScrollInterpolator.getInterpolation(msSinceStartScroll > 2000 ? DefaultRetryPolicy.DEFAULT_BACKOFF_MULT : ((float) msSinceStartScroll) / 2000.0f));
            if (value == 0) {
                return viewSizeOutOfBounds > 0 ? UP : ACTIVE_POINTER_ID_NONE;
            } else {
                return value;
            }
        }
    }

    private class ItemTouchHelperGestureListener extends SimpleOnGestureListener {
        private ItemTouchHelperGestureListener() {
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }

        public void onLongPress(MotionEvent e) {
            View child = ItemTouchHelper.this.findChildView(e);
            if (child != null) {
                ViewHolder vh = ItemTouchHelper.this.mRecyclerView.getChildViewHolder(child);
                if (vh != null && ItemTouchHelper.this.mCallback.hasDragFlag(ItemTouchHelper.this.mRecyclerView, vh) && e.getPointerId(ACTION_STATE_IDLE) == ItemTouchHelper.this.mActivePointerId) {
                    int index = e.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
                    float x = e.getX(index);
                    float y = e.getY(index);
                    ItemTouchHelper.this.mInitialTouchX = x;
                    ItemTouchHelper.this.mInitialTouchY = y;
                    ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                    ItemTouchHelper.this.mDy = 0.0f;
                    itemTouchHelper.mDx = 0.0f;
                    if (ItemTouchHelper.this.mCallback.isLongPressDragEnabled()) {
                        ItemTouchHelper.this.select(vh, DOWN);
                    }
                }
            }
        }
    }

    private class RecoverAnimation implements AnimatorListener {
        final int mActionState;
        private final int mAnimationType;
        private boolean mEnded;
        private float mFraction;
        public boolean mIsPendingCleanup;
        boolean mOverridden;
        final float mStartDx;
        final float mStartDy;
        final float mTargetX;
        final float mTargetY;
        private final ValueAnimator mValueAnimator;
        final ViewHolder mViewHolder;
        float mX;
        float mY;

        class AnonymousClass_1 implements AnimatorUpdateListener {
            final /* synthetic */ ItemTouchHelper val$this$0;

            AnonymousClass_1(ItemTouchHelper itemTouchHelper) {
                this.val$this$0 = itemTouchHelper;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                RecoverAnimation.this.setFraction(animation.getAnimatedFraction());
            }
        }

        public RecoverAnimation(ViewHolder viewHolder, int animationType, int actionState, float startDx, float startDy, float targetX, float targetY) {
            this.mOverridden = false;
            this.mEnded = false;
            this.mActionState = actionState;
            this.mAnimationType = animationType;
            this.mViewHolder = viewHolder;
            this.mStartDx = startDx;
            this.mStartDy = startDy;
            this.mTargetX = targetX;
            this.mTargetY = targetY;
            this.mValueAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            this.mValueAnimator.addUpdateListener(new AnonymousClass_1(ItemTouchHelper.this));
            this.mValueAnimator.setTarget(viewHolder.itemView);
            this.mValueAnimator.addListener(this);
            setFraction(AutoScrollHelper.RELATIVE_UNSPECIFIED);
        }

        public void setDuration(long duration) {
            this.mValueAnimator.setDuration(duration);
        }

        public void start() {
            this.mViewHolder.setIsRecyclable(DEBUG);
            this.mValueAnimator.start();
        }

        public void cancel() {
            this.mValueAnimator.cancel();
        }

        public void setFraction(float fraction) {
            this.mFraction = fraction;
        }

        public void update() {
            if (this.mStartDx == this.mTargetX) {
                this.mX = this.mViewHolder.itemView.getTranslationX();
            } else {
                this.mX = this.mStartDx + (this.mFraction * (this.mTargetX - this.mStartDx));
            }
            if (this.mStartDy == this.mTargetY) {
                this.mY = this.mViewHolder.itemView.getTranslationY();
            } else {
                this.mY = this.mStartDy + (this.mFraction * (this.mTargetY - this.mStartDy));
            }
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
            this.mEnded = true;
        }

        public void onAnimationCancel(Animator animation) {
            setFraction(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    public static interface ViewDropHandler {
        void prepareForDrop(View view, View view2, int i, int i2);
    }

    class AnonymousClass_3 extends RecoverAnimation {
        final /* synthetic */ ViewHolder val$prevSelected;
        final /* synthetic */ int val$swipeDir;

        AnonymousClass_3(ViewHolder viewHolder, int animationType, int actionState, float startDx, float startDy, float targetX, float targetY, int i, ViewHolder viewHolder2) {
            this.val$swipeDir = i;
            this.val$prevSelected = viewHolder2;
            super(viewHolder, animationType, actionState, startDx, startDy, targetX, targetY);
        }

        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            if (!this.mOverridden) {
                if (this.val$swipeDir <= 0) {
                    ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, this.val$prevSelected);
                } else {
                    ItemTouchHelper.this.mPendingCleanup.add(this.val$prevSelected.itemView);
                    this.mIsPendingCleanup = true;
                    if (this.val$swipeDir > 0) {
                        ItemTouchHelper.this.postDispatchSwipe(this, this.val$swipeDir);
                    }
                }
                if (ItemTouchHelper.this.mOverdrawChild == this.val$prevSelected.itemView) {
                    ItemTouchHelper.this.removeChildDrawingOrderCallbackIfNecessary(this.val$prevSelected.itemView);
                }
            }
        }
    }

    public static abstract class SimpleCallback extends Callback {
        private int mDefaultDragDirs;
        private int mDefaultSwipeDirs;

        public SimpleCallback(int dragDirs, int swipeDirs) {
            this.mDefaultSwipeDirs = swipeDirs;
            this.mDefaultDragDirs = dragDirs;
        }

        public void setDefaultSwipeDirs(int defaultSwipeDirs) {
            this.mDefaultSwipeDirs = defaultSwipeDirs;
        }

        public void setDefaultDragDirs(int defaultDragDirs) {
            this.mDefaultDragDirs = defaultDragDirs;
        }

        public int getSwipeDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
            return this.mDefaultSwipeDirs;
        }

        public int getDragDirs(RecyclerView recyclerView, ViewHolder viewHolder) {
            return this.mDefaultDragDirs;
        }

        public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
            return makeMovementFlags(getDragDirs(recyclerView, viewHolder), getSwipeDirs(recyclerView, viewHolder));
        }
    }

    public ItemTouchHelper(Callback callback) {
        this.mPendingCleanup = new ArrayList();
        this.mTmpPosition = new float[2];
        this.mSelected = null;
        this.mActivePointerId = -1;
        this.mActionState = 0;
        this.mRecoverAnimations = new ArrayList();
        this.mScrollRunnable = new Runnable() {
            public void run() {
                if (ItemTouchHelper.this.mSelected != null && ItemTouchHelper.this.scrollIfNecessary()) {
                    if (ItemTouchHelper.this.mSelected != null) {
                        ItemTouchHelper.this.moveIfNecessary(ItemTouchHelper.this.mSelected);
                    }
                    ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
                    ItemTouchHelper.this.mRecyclerView.postOnAnimation(this);
                }
            }
        };
        this.mChildDrawingOrderCallback = null;
        this.mOverdrawChild = null;
        this.mOverdrawChildPosition = -1;
        this.mOnItemTouchListener = new OnItemTouchListener() {
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent event) {
                ItemTouchHelper.this.mGestureDetector.onTouchEvent(event);
                int action = event.getActionMasked();
                if (action == 0) {
                    ItemTouchHelper.this.mActivePointerId = event.getPointerId(ACTION_STATE_IDLE);
                    ItemTouchHelper.this.mInitialTouchX = event.getX();
                    ItemTouchHelper.this.mInitialTouchY = event.getY();
                    ItemTouchHelper.this.obtainVelocityTracker();
                    if (ItemTouchHelper.this.mSelected == null) {
                        RecoverAnimation animation = ItemTouchHelper.this.findAnimation(event);
                        if (animation != null) {
                            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                            itemTouchHelper.mInitialTouchX -= animation.mX;
                            itemTouchHelper = ItemTouchHelper.this;
                            itemTouchHelper.mInitialTouchY -= animation.mY;
                            ItemTouchHelper.this.endRecoverAnimation(animation.mViewHolder, true);
                            if (ItemTouchHelper.this.mPendingCleanup.remove(animation.mViewHolder.itemView)) {
                                ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, animation.mViewHolder);
                            }
                            ItemTouchHelper.this.select(animation.mViewHolder, animation.mActionState);
                            ItemTouchHelper.this.updateDxDy(event, ItemTouchHelper.this.mSelectedFlags, ACTION_STATE_IDLE);
                        }
                    }
                } else if (action == 3 || action == 1) {
                    ItemTouchHelper.this.mActivePointerId = -1;
                    ItemTouchHelper.this.select(null, ACTION_STATE_IDLE);
                } else if (ItemTouchHelper.this.mActivePointerId != -1) {
                    int index = event.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
                    if (index >= 0) {
                        ItemTouchHelper.this.checkSelectForSwipe(action, event, index);
                    }
                }
                if (ItemTouchHelper.this.mVelocityTracker != null) {
                    ItemTouchHelper.this.mVelocityTracker.addMovement(event);
                }
                return ItemTouchHelper.this.mSelected != null;
            }

            public void onTouchEvent(RecyclerView recyclerView, MotionEvent event) {
                int newPointerIndex = ACTION_STATE_IDLE;
                ItemTouchHelper.this.mGestureDetector.onTouchEvent(event);
                if (ItemTouchHelper.this.mVelocityTracker != null) {
                    ItemTouchHelper.this.mVelocityTracker.addMovement(event);
                }
                if (ItemTouchHelper.this.mActivePointerId != -1) {
                    int action = event.getActionMasked();
                    int activePointerIndex = event.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
                    if (activePointerIndex >= 0) {
                        ItemTouchHelper.this.checkSelectForSwipe(action, event, activePointerIndex);
                    }
                    ViewHolder viewHolder = ItemTouchHelper.this.mSelected;
                    if (viewHolder != null) {
                        switch (action) {
                            case UP:
                            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                                if (ItemTouchHelper.this.mVelocityTracker != null) {
                                    ItemTouchHelper.this.mVelocityTracker.computeCurrentVelocity(GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, (float) ItemTouchHelper.this.mRecyclerView.getMaxFlingVelocity());
                                }
                                ItemTouchHelper.this.select(null, ACTION_STATE_IDLE);
                                ItemTouchHelper.this.mActivePointerId = -1;
                            case DOWN:
                                if (activePointerIndex >= 0) {
                                    ItemTouchHelper.this.updateDxDy(event, ItemTouchHelper.this.mSelectedFlags, activePointerIndex);
                                    ItemTouchHelper.this.moveIfNecessary(viewHolder);
                                    ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
                                    ItemTouchHelper.this.mScrollRunnable.run();
                                    ItemTouchHelper.this.mRecyclerView.invalidate();
                                }
                            case ConnectionResult.RESOLUTION_REQUIRED:
                                int pointerIndex = event.getActionIndex();
                                if (event.getPointerId(pointerIndex) == ItemTouchHelper.this.mActivePointerId) {
                                    if (ItemTouchHelper.this.mVelocityTracker != null) {
                                        ItemTouchHelper.this.mVelocityTracker.computeCurrentVelocity(GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, (float) ItemTouchHelper.this.mRecyclerView.getMaxFlingVelocity());
                                    }
                                    if (pointerIndex == 0) {
                                        newPointerIndex = UP;
                                    }
                                    ItemTouchHelper.this.mActivePointerId = event.getPointerId(newPointerIndex);
                                    ItemTouchHelper.this.updateDxDy(event, ItemTouchHelper.this.mSelectedFlags, pointerIndex);
                                }
                            default:
                                break;
                        }
                    }
                }
            }

            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                if (disallowIntercept) {
                    ItemTouchHelper.this.select(null, ACTION_STATE_IDLE);
                }
            }
        };
        this.mCallback = callback;
    }

    private static boolean hitTest(View child, float x, float y, float left, float top) {
        return (x < left || x > ((float) child.getWidth()) + left || y < top || y > ((float) child.getHeight()) + top) ? DEBUG : true;
    }

    public void attachToRecyclerView(RecyclerView recyclerView) {
        if (this.mRecyclerView != recyclerView) {
            if (this.mRecyclerView != null) {
                destroyCallbacks();
            }
            this.mRecyclerView = recyclerView;
            if (this.mRecyclerView != null) {
                setupCallbacks();
            }
        }
    }

    private void setupCallbacks() {
        this.mSlop = ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
        this.mRecyclerView.addItemDecoration(this);
        this.mRecyclerView.addOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.addOnChildAttachStateChangeListener(this);
        initGestureDetector();
    }

    private void destroyCallbacks() {
        this.mRecyclerView.removeItemDecoration(this);
        this.mRecyclerView.removeOnItemTouchListener(this.mOnItemTouchListener);
        this.mRecyclerView.removeOnChildAttachStateChangeListener(this);
        for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--) {
            this.mCallback.clearView(this.mRecyclerView, ((RecoverAnimation) this.mRecoverAnimations.get(ACTION_STATE_IDLE)).mViewHolder);
        }
        this.mRecoverAnimations.clear();
        this.mOverdrawChild = null;
        this.mOverdrawChildPosition = -1;
        releaseVelocityTracker();
    }

    private void initGestureDetector() {
        if (this.mGestureDetector == null) {
            this.mGestureDetector = new GestureDetector(this.mRecyclerView.getContext(), new ItemTouchHelperGestureListener());
        }
    }

    private void getSelectedDxDy(float[] outPosition) {
        if ((this.mSelectedFlags & 12) != 0) {
            outPosition[0] = (this.mSelectedStartX + this.mDx) - ((float) this.mSelected.itemView.getLeft());
        } else {
            outPosition[0] = this.mSelected.itemView.getTranslationX();
        }
        if ((this.mSelectedFlags & 3) != 0) {
            outPosition[1] = (this.mSelectedStartY + this.mDy) - ((float) this.mSelected.itemView.getTop());
        } else {
            outPosition[1] = this.mSelected.itemView.getTranslationY();
        }
    }

    public void onDrawOver(Canvas c, RecyclerView parent, State state) {
        float dx = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        float dy = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        if (this.mSelected != null) {
            getSelectedDxDy(this.mTmpPosition);
            dx = this.mTmpPosition[0];
            dy = this.mTmpPosition[1];
        }
        this.mCallback.onDrawOver(c, parent, this.mSelected, this.mRecoverAnimations, this.mActionState, dx, dy);
    }

    public void onDraw(Canvas c, RecyclerView parent, State state) {
        this.mOverdrawChildPosition = -1;
        float dx = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        float dy = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        if (this.mSelected != null) {
            getSelectedDxDy(this.mTmpPosition);
            dx = this.mTmpPosition[0];
            dy = this.mTmpPosition[1];
        }
        this.mCallback.onDraw(c, parent, this.mSelected, this.mRecoverAnimations, this.mActionState, dx, dy);
    }

    private void select(ViewHolder selected, int actionState) {
        if (selected != this.mSelected || actionState != this.mActionState) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            int prevActionState = this.mActionState;
            endRecoverAnimation(selected, true);
            this.mActionState = actionState;
            if (actionState == 2) {
                this.mOverdrawChild = selected.itemView;
                addChildDrawingOrderCallback();
            }
            int actionStateMask = (1 << ((actionState * 8) + 8)) - 1;
            boolean preventLayout = DEBUG;
            if (this.mSelected != null) {
                ViewHolder prevSelected = this.mSelected;
                if (prevSelected.itemView.getParent() != null) {
                    float targetTranslateX;
                    float targetTranslateY;
                    int swipeDir = prevActionState == 2 ? ACTION_STATE_IDLE : swipeIfNecessary(prevSelected);
                    releaseVelocityTracker();
                    switch (swipeDir) {
                        case UP:
                        case DOWN:
                            targetTranslateX = AutoScrollHelper.RELATIVE_UNSPECIFIED;
                            targetTranslateY = Math.signum(this.mDy) * ((float) this.mRecyclerView.getHeight());
                            break;
                        case LEFT:
                        case RIGHT:
                        case START:
                        case END:
                            targetTranslateY = AutoScrollHelper.RELATIVE_UNSPECIFIED;
                            targetTranslateX = Math.signum(this.mDx) * ((float) this.mRecyclerView.getWidth());
                            break;
                        default:
                            targetTranslateX = AutoScrollHelper.RELATIVE_UNSPECIFIED;
                            targetTranslateY = AutoScrollHelper.RELATIVE_UNSPECIFIED;
                            break;
                    }
                    int animationType = prevActionState == 2 ? RIGHT : swipeDir > 0 ? DOWN : LEFT;
                    getSelectedDxDy(this.mTmpPosition);
                    float currentTranslateX = this.mTmpPosition[0];
                    float currentTranslateY = this.mTmpPosition[1];
                    RecoverAnimation rv = new AnonymousClass_3(prevSelected, animationType, prevActionState, currentTranslateX, currentTranslateY, targetTranslateX, targetTranslateY, swipeDir, prevSelected);
                    rv.setDuration(this.mCallback.getAnimationDuration(this.mRecyclerView, animationType, targetTranslateX - currentTranslateX, targetTranslateY - currentTranslateY));
                    this.mRecoverAnimations.add(rv);
                    rv.start();
                    preventLayout = true;
                } else {
                    removeChildDrawingOrderCallbackIfNecessary(prevSelected.itemView);
                    this.mCallback.clearView(this.mRecyclerView, prevSelected);
                }
                this.mSelected = null;
            }
            if (selected != null) {
                this.mSelectedFlags = (this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, selected) & actionStateMask) >> (this.mActionState * 8);
                this.mSelectedStartX = (float) selected.itemView.getLeft();
                this.mSelectedStartY = (float) selected.itemView.getTop();
                this.mSelected = selected;
                if (actionState == 2) {
                    this.mSelected.itemView.performHapticFeedback(ACTION_STATE_IDLE);
                }
            }
            ViewParent rvParent = this.mRecyclerView.getParent();
            if (rvParent != null) {
                rvParent.requestDisallowInterceptTouchEvent(this.mSelected != null ? true : DEBUG);
            }
            if (!preventLayout) {
                this.mRecyclerView.getLayoutManager().requestSimpleAnimationsInNextLayout();
            }
            this.mCallback.onSelectedChanged(this.mSelected, this.mActionState);
            this.mRecyclerView.invalidate();
        }
    }

    private void postDispatchSwipe(RecoverAnimation anim, int swipeDir) {
        this.mRecyclerView.post(new AnonymousClass_4(anim, swipeDir));
    }

    private boolean hasRunningRecoverAnim() {
        int size = this.mRecoverAnimations.size();
        for (int i = ACTION_STATE_IDLE; i < size; i++) {
            if (!((RecoverAnimation) this.mRecoverAnimations.get(i)).mEnded) {
                return true;
            }
        }
        return DEBUG;
    }

    private boolean scrollIfNecessary() {
        if (this.mSelected == null) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            return DEBUG;
        }
        long scrollDuration;
        long now = System.currentTimeMillis();
        if (this.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
            scrollDuration = 0;
        } else {
            scrollDuration = now - this.mDragScrollStartTimeInMs;
        }
        LayoutManager lm = this.mRecyclerView.getLayoutManager();
        if (this.mTmpRect == null) {
            this.mTmpRect = new Rect();
        }
        int scrollX = ACTION_STATE_IDLE;
        int scrollY = ACTION_STATE_IDLE;
        lm.calculateItemDecorationsForChild(this.mSelected.itemView, this.mTmpRect);
        if (lm.canScrollHorizontally()) {
            int curX = (int) (this.mSelectedStartX + this.mDx);
            int leftDiff = (curX - this.mTmpRect.left) - this.mRecyclerView.getPaddingLeft();
            if (this.mDx < 0.0f && leftDiff < 0) {
                scrollX = leftDiff;
            } else if (this.mDx > 0.0f) {
                int rightDiff = ((this.mSelected.itemView.getWidth() + curX) + this.mTmpRect.right) - (this.mRecyclerView.getWidth() - this.mRecyclerView.getPaddingRight());
                if (rightDiff > 0) {
                    scrollX = rightDiff;
                }
            }
        }
        if (lm.canScrollVertically()) {
            int curY = (int) (this.mSelectedStartY + this.mDy);
            int topDiff = (curY - this.mTmpRect.top) - this.mRecyclerView.getPaddingTop();
            if (this.mDy < 0.0f && topDiff < 0) {
                scrollY = topDiff;
            } else if (this.mDy > 0.0f) {
                int bottomDiff = ((this.mSelected.itemView.getHeight() + curY) + this.mTmpRect.bottom) - (this.mRecyclerView.getHeight() - this.mRecyclerView.getPaddingBottom());
                if (bottomDiff > 0) {
                    scrollY = bottomDiff;
                }
            }
        }
        if (scrollX != 0) {
            scrollX = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getWidth(), scrollX, this.mRecyclerView.getWidth(), scrollDuration);
        }
        if (scrollY != 0) {
            scrollY = this.mCallback.interpolateOutOfBoundsScroll(this.mRecyclerView, this.mSelected.itemView.getHeight(), scrollY, this.mRecyclerView.getHeight(), scrollDuration);
        }
        if (scrollX == 0 && scrollY == 0) {
            this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
            return DEBUG;
        }
        if (this.mDragScrollStartTimeInMs == Long.MIN_VALUE) {
            this.mDragScrollStartTimeInMs = now;
        }
        this.mRecyclerView.scrollBy(scrollX, scrollY);
        return true;
    }

    private List<ViewHolder> findSwapTargets(ViewHolder viewHolder) {
        if (this.mSwapTargets == null) {
            this.mSwapTargets = new ArrayList();
            this.mDistances = new ArrayList();
        } else {
            this.mSwapTargets.clear();
            this.mDistances.clear();
        }
        int margin = this.mCallback.getBoundingBoxMargin();
        int left = Math.round(this.mSelectedStartX + this.mDx) - margin;
        int top = Math.round(this.mSelectedStartY + this.mDy) - margin;
        int right = (viewHolder.itemView.getWidth() + left) + (margin * 2);
        int bottom = (viewHolder.itemView.getHeight() + top) + (margin * 2);
        int centerX = (left + right) / 2;
        int centerY = (top + bottom) / 2;
        LayoutManager lm = this.mRecyclerView.getLayoutManager();
        int childCount = lm.getChildCount();
        for (int i = ACTION_STATE_IDLE; i < childCount; i++) {
            View other = lm.getChildAt(i);
            if (other != viewHolder.itemView && other.getBottom() >= top && other.getTop() <= bottom && other.getRight() >= left && other.getLeft() <= right) {
                ViewHolder otherVh = this.mRecyclerView.getChildViewHolder(other);
                if (this.mCallback.canDropOver(this.mRecyclerView, this.mSelected, otherVh)) {
                    int dx = Math.abs(centerX - ((other.getLeft() + other.getRight()) / 2));
                    int dy = Math.abs(centerY - ((other.getTop() + other.getBottom()) / 2));
                    int dist = (dx * dx) + (dy * dy);
                    int pos = ACTION_STATE_IDLE;
                    int cnt = this.mSwapTargets.size();
                    int j = ACTION_STATE_IDLE;
                    while (j < cnt && dist > ((Integer) this.mDistances.get(j)).intValue()) {
                        pos++;
                        j++;
                    }
                    this.mSwapTargets.add(pos, otherVh);
                    this.mDistances.add(pos, Integer.valueOf(dist));
                }
            }
        }
        return this.mSwapTargets;
    }

    private void moveIfNecessary(ViewHolder viewHolder) {
        if (!this.mRecyclerView.isLayoutRequested() && this.mActionState == 2) {
            float threshold = this.mCallback.getMoveThreshold(viewHolder);
            int x = (int) (this.mSelectedStartX + this.mDx);
            int y = (int) (this.mSelectedStartY + this.mDy);
            if (((float) Math.abs(y - viewHolder.itemView.getTop())) >= ((float) viewHolder.itemView.getHeight()) * threshold || ((float) Math.abs(x - viewHolder.itemView.getLeft())) >= ((float) viewHolder.itemView.getWidth()) * threshold) {
                List<ViewHolder> swapTargets = findSwapTargets(viewHolder);
                if (swapTargets.size() != 0) {
                    ViewHolder target = this.mCallback.chooseDropTarget(viewHolder, swapTargets, x, y);
                    if (target == null) {
                        this.mSwapTargets.clear();
                        this.mDistances.clear();
                        return;
                    }
                    int toPosition = target.getAdapterPosition();
                    int fromPosition = viewHolder.getAdapterPosition();
                    if (this.mCallback.onMove(this.mRecyclerView, viewHolder, target)) {
                        this.mCallback.onMoved(this.mRecyclerView, viewHolder, fromPosition, target, toPosition, x, y);
                    }
                }
            }
        }
    }

    public void onChildViewAttachedToWindow(View view) {
    }

    public void onChildViewDetachedFromWindow(View view) {
        removeChildDrawingOrderCallbackIfNecessary(view);
        ViewHolder holder = this.mRecyclerView.getChildViewHolder(view);
        if (holder != null) {
            if (this.mSelected == null || holder != this.mSelected) {
                endRecoverAnimation(holder, DEBUG);
                if (this.mPendingCleanup.remove(holder.itemView)) {
                    this.mCallback.clearView(this.mRecyclerView, holder);
                    return;
                }
                return;
            }
            select(null, ACTION_STATE_IDLE);
        }
    }

    private int endRecoverAnimation(ViewHolder viewHolder, boolean override) {
        for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--) {
            RecoverAnimation anim = (RecoverAnimation) this.mRecoverAnimations.get(i);
            if (anim.mViewHolder == viewHolder) {
                anim.mOverridden |= override;
                if (!anim.mEnded) {
                    anim.cancel();
                }
                this.mRecoverAnimations.remove(i);
                anim.mViewHolder.setIsRecyclable(true);
                return anim.mAnimationType;
            }
        }
        return ACTION_STATE_IDLE;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        outRect.setEmpty();
    }

    private void obtainVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
        }
        this.mVelocityTracker = VelocityTracker.obtain();
    }

    private void releaseVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private ViewHolder findSwipedView(MotionEvent motionEvent) {
        LayoutManager lm = this.mRecyclerView.getLayoutManager();
        if (this.mActivePointerId == -1) {
            return null;
        }
        int pointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        float dy = motionEvent.getY(pointerIndex) - this.mInitialTouchY;
        float absDx = Math.abs(motionEvent.getX(pointerIndex) - this.mInitialTouchX);
        float absDy = Math.abs(dy);
        if (absDx < ((float) this.mSlop) && absDy < ((float) this.mSlop)) {
            return null;
        }
        if (absDx > absDy && lm.canScrollHorizontally()) {
            return null;
        }
        if (absDy > absDx && lm.canScrollVertically()) {
            return null;
        }
        View child = findChildView(motionEvent);
        return child != null ? this.mRecyclerView.getChildViewHolder(child) : null;
    }

    private boolean checkSelectForSwipe(int action, MotionEvent motionEvent, int pointerIndex) {
        if (this.mSelected != null || action != 2 || this.mActionState == 2 || !this.mCallback.isItemViewSwipeEnabled()) {
            return DEBUG;
        }
        if (this.mRecyclerView.getScrollState() == 1) {
            return DEBUG;
        }
        ViewHolder vh = findSwipedView(motionEvent);
        if (vh == null) {
            return DEBUG;
        }
        int swipeFlags = (65280 & this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, vh)) >> 8;
        if (swipeFlags == 0) {
            return DEBUG;
        }
        float x = motionEvent.getX(pointerIndex);
        float dx = x - this.mInitialTouchX;
        float dy = motionEvent.getY(pointerIndex) - this.mInitialTouchY;
        float absDx = Math.abs(dx);
        float absDy = Math.abs(dy);
        if (absDx < ((float) this.mSlop) && absDy < ((float) this.mSlop)) {
            return DEBUG;
        }
        if (absDx > absDy) {
            if (dx < 0.0f && (swipeFlags & 4) == 0) {
                return DEBUG;
            }
            if (dx > 0.0f && (swipeFlags & 8) == 0) {
                return DEBUG;
            }
        } else if (dy < 0.0f && (swipeFlags & 1) == 0) {
            return DEBUG;
        } else {
            if (dy > 0.0f && (swipeFlags & 2) == 0) {
                return DEBUG;
            }
        }
        this.mDy = 0.0f;
        this.mDx = 0.0f;
        this.mActivePointerId = motionEvent.getPointerId(ACTION_STATE_IDLE);
        select(vh, UP);
        return true;
    }

    private View findChildView(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (this.mSelected != null) {
            View selectedView = this.mSelected.itemView;
            if (hitTest(selectedView, x, y, this.mSelectedStartX + this.mDx, this.mSelectedStartY + this.mDy)) {
                return selectedView;
            }
        }
        for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--) {
            RecoverAnimation anim = (RecoverAnimation) this.mRecoverAnimations.get(i);
            View view = anim.mViewHolder.itemView;
            if (hitTest(view, x, y, anim.mX, anim.mY)) {
                return view;
            }
        }
        return this.mRecyclerView.findChildViewUnder(x, y);
    }

    public void startDrag(ViewHolder viewHolder) {
        if (!this.mCallback.hasDragFlag(this.mRecyclerView, viewHolder)) {
            Log.e(TAG, "Start drag has been called but swiping is not enabled");
        } else if (viewHolder.itemView.getParent() != this.mRecyclerView) {
            Log.e(TAG, "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
        } else {
            obtainVelocityTracker();
            this.mDy = 0.0f;
            this.mDx = 0.0f;
            select(viewHolder, DOWN);
        }
    }

    public void startSwipe(ViewHolder viewHolder) {
        if (!this.mCallback.hasSwipeFlag(this.mRecyclerView, viewHolder)) {
            Log.e(TAG, "Start swipe has been called but dragging is not enabled");
        } else if (viewHolder.itemView.getParent() != this.mRecyclerView) {
            Log.e(TAG, "Start swipe has been called with a view holder which is not a child of the RecyclerView controlled by this ItemTouchHelper.");
        } else {
            obtainVelocityTracker();
            this.mDy = 0.0f;
            this.mDx = 0.0f;
            select(viewHolder, UP);
        }
    }

    private RecoverAnimation findAnimation(MotionEvent event) {
        if (this.mRecoverAnimations.isEmpty()) {
            return null;
        }
        View target = findChildView(event);
        for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--) {
            RecoverAnimation anim = (RecoverAnimation) this.mRecoverAnimations.get(i);
            if (anim.mViewHolder.itemView == target) {
                return anim;
            }
        }
        return null;
    }

    private void updateDxDy(MotionEvent ev, int directionFlags, int pointerIndex) {
        float x = ev.getX(pointerIndex);
        float y = ev.getY(pointerIndex);
        this.mDx = x - this.mInitialTouchX;
        this.mDy = y - this.mInitialTouchY;
        if ((directionFlags & 4) == 0) {
            this.mDx = Math.max(AutoScrollHelper.RELATIVE_UNSPECIFIED, this.mDx);
        }
        if ((directionFlags & 8) == 0) {
            this.mDx = Math.min(AutoScrollHelper.RELATIVE_UNSPECIFIED, this.mDx);
        }
        if ((directionFlags & 1) == 0) {
            this.mDy = Math.max(AutoScrollHelper.RELATIVE_UNSPECIFIED, this.mDy);
        }
        if ((directionFlags & 2) == 0) {
            this.mDy = Math.min(AutoScrollHelper.RELATIVE_UNSPECIFIED, this.mDy);
        }
    }

    private int swipeIfNecessary(ViewHolder viewHolder) {
        if (this.mActionState == 2) {
            return 0;
        }
        int originalMovementFlags = this.mCallback.getMovementFlags(this.mRecyclerView, viewHolder);
        int flags = (this.mCallback.convertToAbsoluteDirection(originalMovementFlags, this.mRecyclerView.getLayoutDirection()) & 65280) >> 8;
        if (flags == 0) {
            return 0;
        }
        int originalFlags = (originalMovementFlags & 65280) >> 8;
        int swipeDir;
        if (Math.abs(this.mDx) > Math.abs(this.mDy)) {
            swipeDir = checkHorizontalSwipe(viewHolder, flags);
            if (swipeDir > 0) {
                return (originalFlags & swipeDir) == 0 ? Callback.convertToRelativeDirection(swipeDir, this.mRecyclerView.getLayoutDirection()) : swipeDir;
            } else {
                swipeDir = checkVerticalSwipe(viewHolder, flags);
                if (swipeDir > 0) {
                    return swipeDir;
                }
            }
        }
        swipeDir = checkVerticalSwipe(viewHolder, flags);
        if (swipeDir > 0) {
            return swipeDir;
        }
        swipeDir = checkHorizontalSwipe(viewHolder, flags);
        if (swipeDir > 0) {
            return (originalFlags & swipeDir) == 0 ? Callback.convertToRelativeDirection(swipeDir, this.mRecyclerView.getLayoutDirection()) : swipeDir;
        }
        return 0;
    }

    private int checkHorizontalSwipe(ViewHolder viewHolder, int flags) {
        if ((flags & 12) != 0) {
            int dirFlag = this.mDx > 0.0f ? 8 : 4;
            if (this.mVelocityTracker != null && this.mActivePointerId > -1) {
                int velDirFlag;
                float xVelocity = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
                if (xVelocity > 0.0f) {
                    velDirFlag = 8;
                } else {
                    velDirFlag = 4;
                }
                if ((velDirFlag & flags) != 0 && dirFlag == velDirFlag && Math.abs(xVelocity) >= ((float) this.mRecyclerView.getMinFlingVelocity())) {
                    return velDirFlag;
                }
            }
            float threshold = ((float) this.mRecyclerView.getWidth()) * this.mCallback.getSwipeThreshold(viewHolder);
            if ((flags & dirFlag) != 0 && Math.abs(this.mDx) > threshold) {
                return dirFlag;
            }
        }
        return ACTION_STATE_IDLE;
    }

    private int checkVerticalSwipe(ViewHolder viewHolder, int flags) {
        if ((flags & 3) != 0) {
            int dirFlag = this.mDy > 0.0f ? 2 : 1;
            if (this.mVelocityTracker != null && this.mActivePointerId > -1) {
                int velDirFlag;
                float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
                if (yVelocity > 0.0f) {
                    velDirFlag = 2;
                } else {
                    velDirFlag = 1;
                }
                if ((velDirFlag & flags) != 0 && velDirFlag == dirFlag && Math.abs(yVelocity) >= ((float) this.mRecyclerView.getMinFlingVelocity())) {
                    return velDirFlag;
                }
            }
            float threshold = ((float) this.mRecyclerView.getHeight()) * this.mCallback.getSwipeThreshold(viewHolder);
            if ((flags & dirFlag) != 0 && Math.abs(this.mDy) > threshold) {
                return dirFlag;
            }
        }
        return ACTION_STATE_IDLE;
    }

    private void addChildDrawingOrderCallback() {
        if (VERSION.SDK_INT < 21) {
            if (this.mChildDrawingOrderCallback == null) {
                this.mChildDrawingOrderCallback = new ChildDrawingOrderCallback() {
                    public int onGetChildDrawingOrder(int childCount, int i) {
                        if (ItemTouchHelper.this.mOverdrawChild == null) {
                            return i;
                        }
                        int childPosition = ItemTouchHelper.this.mOverdrawChildPosition;
                        if (childPosition == -1) {
                            childPosition = ItemTouchHelper.this.mRecyclerView.indexOfChild(ItemTouchHelper.this.mOverdrawChild);
                            ItemTouchHelper.this.mOverdrawChildPosition = childPosition;
                        }
                        if (i == childCount - 1) {
                            return childPosition;
                        }
                        return i >= childPosition ? i + 1 : i;
                    }
                };
            }
            this.mRecyclerView.setChildDrawingOrderCallback(this.mChildDrawingOrderCallback);
        }
    }

    private void removeChildDrawingOrderCallbackIfNecessary(View view) {
        if (view == this.mOverdrawChild) {
            this.mOverdrawChild = null;
            if (this.mChildDrawingOrderCallback != null) {
                this.mRecyclerView.setChildDrawingOrderCallback(null);
            }
        }
    }
}
