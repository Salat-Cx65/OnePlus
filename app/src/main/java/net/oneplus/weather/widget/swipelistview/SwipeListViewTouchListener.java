package net.oneplus.weather.widget.swipelistview;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import com.android.volley.DefaultRetryPolicy;
import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.OrigamiValueConverter;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class SwipeListViewTouchListener implements OnTouchListener {
    private static final int DISPLACE_CHOICE = 80;
    private long animationTime;
    private View backView;
    private List<Boolean> checked;
    private long configShortAnimationTime;
    private int dismissAnimationRefCount;
    private int downPosition;
    private float downX;
    private View frontView;
    private float leftOffset;
    private boolean listViewMoving;
    private Spring mSpring;
    private SpringConfig mSpringConfig;
    private final BaseSpringSystem mSpringSystem;
    private int maxFlingVelocity;
    private int minFlingVelocity;
    private int oldSwipeActionLeft;
    private int oldSwipeActionRight;
    private List<Boolean> opened;
    private List<Boolean> openedRight;
    private View parentView;
    private boolean paused;
    private List<PendingDismissData> pendingDismisses;
    private Rect rect;
    private float rightOffset;
    private int slop;
    private int swipeActionLeft;
    private int swipeActionRight;
    private int swipeBackView;
    private boolean swipeClosesAllItemsWhenListMoves;
    private int swipeCurrentAction;
    private int swipeDrawableChecked;
    private int swipeDrawableUnchecked;
    private int swipeFrontView;
    private SwipeListView swipeListView;
    private int swipeMode;
    private boolean swipeOpenOnLongPress;
    private boolean swiping;
    private boolean swipingRight;
    private VelocityTracker velocityTracker;
    private int viewWidth;

    class AnonymousClass_12 implements Runnable {
        final /* synthetic */ int val$originalHeight;

        AnonymousClass_12(int i) {
            this.val$originalHeight = i;
        }

        public void run() {
            SwipeListViewTouchListener.this.removePendingDismisses(this.val$originalHeight);
        }
    }

    class AnonymousClass_2 implements OnLongClickListener {
        final /* synthetic */ int val$childPosition;

        AnonymousClass_2(int i) {
            this.val$childPosition = i;
        }

        public boolean onLongClick(View v) {
            if (!SwipeListViewTouchListener.this.swipeOpenOnLongPress) {
                SwipeListViewTouchListener.this.swapChoiceState(this.val$childPosition);
            } else if (SwipeListViewTouchListener.this.downPosition >= 0) {
                SwipeListViewTouchListener.this.openAnimate(this.val$childPosition);
            }
            return false;
        }
    }

    class PendingDismissData implements Comparable<PendingDismissData> {
        public int position;
        public View view;

        public PendingDismissData(int position, View view) {
            this.position = position;
            this.view = view;
        }

        public int compareTo(PendingDismissData other) {
            return other.position - this.position;
        }
    }

    class AnonymousClass_11 implements AnimatorUpdateListener {
        final /* synthetic */ View val$dismissView;
        final /* synthetic */ LayoutParams val$lp;

        AnonymousClass_11(LayoutParams layoutParams, View view) {
            this.val$lp = layoutParams;
            this.val$dismissView = view;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.val$lp.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
            this.val$dismissView.setLayoutParams(this.val$lp);
        }
    }

    class AnonymousClass_10 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$dismissView;

        AnonymousClass_10(View view) {
            this.val$dismissView = view;
        }

        public void onAnimationEnd(Animator animation) {
            SwipeListViewTouchListener.enableDisableViewGroup((ViewGroup) this.val$dismissView, true);
        }
    }

    class AnonymousClass_5 extends AnimatorListenerAdapter {
        final /* synthetic */ int val$position;
        final /* synthetic */ boolean val$swap;
        final /* synthetic */ View val$view;

        AnonymousClass_5(boolean z, View view, int i) {
            this.val$swap = z;
            this.val$view = view;
            this.val$position = i;
        }

        public void onAnimationEnd(Animator animation) {
            if (this.val$swap) {
                SwipeListViewTouchListener.this.closeOpenedItems();
                SwipeListViewTouchListener.this.performDismiss(this.val$view, this.val$position, true);
            }
            SwipeListViewTouchListener.this.swipeListView.resetScrolling();
            SwipeListViewTouchListener.this.resetCell();
        }
    }

    class AnonymousClass_6 extends SimpleSpringListener {
        final /* synthetic */ float val$currentX;
        final /* synthetic */ boolean val$swap;
        final /* synthetic */ float val$toX;
        final /* synthetic */ View val$view;

        AnonymousClass_6(float f, float f2, View view, boolean z) {
            this.val$currentX = f;
            this.val$toX = f2;
            this.val$view = view;
            this.val$swap = z;
        }

        public void onSpringUpdate(Spring spring) {
            float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0.0d, 1.0d, (double) this.val$currentX, (double) this.val$toX);
            SwipeListViewTouchListener.this.swipeListView.resetScrolling();
            this.val$view.setTranslationX(mappedValue);
        }

        public void onSpringAtRest(Spring spring) {
            if (this.val$swap) {
                SwipeListViewTouchListener.this.closeOpenedItems();
            }
            SwipeListViewTouchListener.this.resetCell();
        }
    }

    class AnonymousClass_7 extends AnimatorListenerAdapter {
        final /* synthetic */ int val$position;
        final /* synthetic */ boolean val$swap;
        final /* synthetic */ boolean val$swapRight;

        AnonymousClass_7(boolean z, int i, boolean z2) {
            this.val$swap = z;
            this.val$position = i;
            this.val$swapRight = z2;
        }

        public void onAnimationEnd(Animator animation) {
            SwipeListViewTouchListener.this.swipeListView.resetScrolling();
            if (this.val$swap) {
                boolean aux = !((Boolean) SwipeListViewTouchListener.this.opened.get(this.val$position)).booleanValue();
                SwipeListViewTouchListener.this.opened.set(this.val$position, Boolean.valueOf(aux));
                if (aux) {
                    SwipeListViewTouchListener.this.swipeListView.onOpened(this.val$position, this.val$swapRight);
                    SwipeListViewTouchListener.this.openedRight.set(this.val$position, Boolean.valueOf(this.val$swapRight));
                } else {
                    SwipeListViewTouchListener.this.swipeListView.onClosed(this.val$position, ((Boolean) SwipeListViewTouchListener.this.openedRight.get(this.val$position)).booleanValue());
                }
            }
            SwipeListViewTouchListener.this.swipeListView.resetScrolling();
            SwipeListViewTouchListener.this.resetCell();
        }
    }

    class AnonymousClass_9 extends AnimatorListenerAdapter {
        final /* synthetic */ int val$originalHeight;

        AnonymousClass_9(int i) {
            this.val$originalHeight = i;
        }

        public void onAnimationEnd(Animator animation) {
            SwipeListViewTouchListener.access$906(SwipeListViewTouchListener.this);
            if (SwipeListViewTouchListener.this.dismissAnimationRefCount == 0) {
                SwipeListViewTouchListener.this.removePendingDismisses(this.val$originalHeight);
            }
        }
    }

    static /* synthetic */ int access$906(SwipeListViewTouchListener x0) {
        int i = x0.dismissAnimationRefCount - 1;
        x0.dismissAnimationRefCount = i;
        return i;
    }

    public SwipeListViewTouchListener(SwipeListView swipeListView, int swipeFrontView, int swipeBackView) {
        this.mSpringSystem = SpringSystem.create();
        this.swipeMode = 1;
        this.swipeOpenOnLongPress = true;
        this.swipeClosesAllItemsWhenListMoves = true;
        this.swipeFrontView = 0;
        this.swipeBackView = 0;
        this.rect = new Rect();
        this.leftOffset = 0.0f;
        this.rightOffset = 0.0f;
        this.swipeDrawableChecked = 0;
        this.swipeDrawableUnchecked = 0;
        this.viewWidth = 1;
        this.pendingDismisses = new ArrayList();
        this.dismissAnimationRefCount = 0;
        this.swipeCurrentAction = 3;
        this.swipeActionLeft = 0;
        this.swipeActionRight = 0;
        this.opened = new ArrayList();
        this.openedRight = new ArrayList();
        this.checked = new ArrayList();
        this.swipeFrontView = swipeFrontView;
        this.swipeBackView = swipeBackView;
        ViewConfiguration vc = ViewConfiguration.get(swipeListView.getContext());
        this.slop = vc.getScaledTouchSlop();
        this.minFlingVelocity = vc.getScaledMinimumFlingVelocity();
        this.maxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        this.configShortAnimationTime = (long) swipeListView.getContext().getResources().getInteger(17694720);
        this.animationTime = this.configShortAnimationTime;
        this.swipeListView = swipeListView;
        this.mSpring = this.mSpringSystem.createSpring();
        this.mSpringConfig = SpringConfig.defaultConfig;
        this.mSpringConfig.tension = OrigamiValueConverter.tensionFromOrigamiValue(50.0d);
        this.mSpringConfig.friction = OrigamiValueConverter.frictionFromOrigamiValue(7.0d);
        this.mSpring.setSpringConfig(this.mSpringConfig);
    }

    private void setParentView(View parentView) {
        this.parentView = parentView;
    }

    private void setFrontView(View frontView, int childPosition) {
        this.frontView = frontView;
        frontView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SwipeListViewTouchListener.this.swipeListView.onClickFrontView(SwipeListViewTouchListener.this.downPosition);
            }
        });
        frontView.setOnLongClickListener(new AnonymousClass_2(childPosition));
    }

    private void setBackView(View backView) {
        this.backView = backView;
        backView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SwipeListViewTouchListener.this.swipeListView.onClickBackView(SwipeListViewTouchListener.this.downPosition);
            }
        });
    }

    public boolean isListViewMoving() {
        return this.listViewMoving;
    }

    public void setAnimationTime(long animationTime) {
        if (animationTime > 0) {
            this.animationTime = animationTime;
        } else {
            this.animationTime = this.configShortAnimationTime;
        }
    }

    public void setRightOffset(float rightOffset) {
        this.rightOffset = rightOffset;
    }

    public void setLeftOffset(float leftOffset) {
        this.leftOffset = leftOffset;
    }

    public void setSwipeClosesAllItemsWhenListMoves(boolean swipeClosesAllItemsWhenListMoves) {
        this.swipeClosesAllItemsWhenListMoves = swipeClosesAllItemsWhenListMoves;
    }

    public void setSwipeOpenOnLongPress(boolean swipeOpenOnLongPress) {
        this.swipeOpenOnLongPress = swipeOpenOnLongPress;
    }

    public void setSwipeMode(int swipeMode) {
        this.swipeMode = swipeMode;
    }

    protected boolean isSwipeEnabled() {
        return this.swipeMode != 0;
    }

    public int getSwipeActionLeft() {
        return this.swipeActionLeft;
    }

    public void setSwipeActionLeft(int swipeActionLeft) {
        this.swipeActionLeft = swipeActionLeft;
    }

    public int getSwipeActionRight() {
        return this.swipeActionRight;
    }

    public void setSwipeActionRight(int swipeActionRight) {
        this.swipeActionRight = swipeActionRight;
    }

    protected void setSwipeDrawableChecked(int swipeDrawableChecked) {
        this.swipeDrawableChecked = swipeDrawableChecked;
    }

    protected void setSwipeDrawableUnchecked(int swipeDrawableUnchecked) {
        this.swipeDrawableUnchecked = swipeDrawableUnchecked;
    }

    public void resetItems() {
        if (this.swipeListView.getAdapter() != null) {
            int count = this.swipeListView.getAdapter().getCount();
            for (int i = this.opened.size(); i <= count; i++) {
                this.opened.add(Boolean.valueOf(false));
                this.openedRight.add(Boolean.valueOf(false));
                this.checked.add(Boolean.valueOf(false));
            }
        }
    }

    protected void openAnimate(int position) {
        View child = this.swipeListView.getChildAt(position - this.swipeListView.getFirstVisiblePosition()).findViewById(this.swipeFrontView);
        if (child != null) {
            openAnimate(child, position);
        }
    }

    protected void closeAnimate(int position) {
        if (this.swipeListView != null) {
            View childContainer = this.swipeListView.getChildAt(position - this.swipeListView.getFirstVisiblePosition());
            if (childContainer != null) {
                View child = childContainer.findViewById(this.swipeFrontView);
                if (child != null) {
                    closeAnimate(child, position);
                }
            }
        }
    }

    private void swapChoiceState(int position) {
        boolean z;
        boolean z2 = true;
        int lastCount = getCountSelected();
        boolean lastChecked = ((Boolean) this.checked.get(position)).booleanValue();
        List list = this.checked;
        if (lastChecked) {
            z = false;
        } else {
            z = true;
        }
        list.set(position, Boolean.valueOf(z));
        int count = lastChecked ? lastCount - 1 : lastCount + 1;
        if (lastCount == 0 && count == 1) {
            this.swipeListView.onChoiceStarted();
            closeOpenedItems();
            setActionsTo(RainSurfaceView.RAIN_LEVEL_SHOWER);
        }
        if (lastCount == 1 && count == 0) {
            this.swipeListView.onChoiceEnded();
            returnOldActions();
        }
        if (VERSION.SDK_INT >= 11) {
            SwipeListView swipeListView = this.swipeListView;
            if (lastChecked) {
                z = false;
            } else {
                z = true;
            }
            swipeListView.setItemChecked(position, z);
        }
        SwipeListView swipeListView2 = this.swipeListView;
        if (lastChecked) {
            z2 = false;
        }
        swipeListView2.onChoiceChanged(position, z2);
        reloadChoiceStateInView(this.frontView, position);
    }

    protected void unselectedChoiceStates() {
        int start = this.swipeListView.getFirstVisiblePosition();
        int end = this.swipeListView.getLastVisiblePosition();
        int i = 0;
        while (i < this.checked.size()) {
            if (((Boolean) this.checked.get(i)).booleanValue() && i >= start && i <= end) {
                reloadChoiceStateInView(this.swipeListView.getChildAt(i - start).findViewById(this.swipeFrontView), i);
            }
            this.checked.set(i, Boolean.valueOf(false));
            i++;
        }
        this.swipeListView.onChoiceEnded();
        returnOldActions();
    }

    protected int dismiss(int position) {
        this.opened.remove(position);
        this.checked.remove(position);
        int start = this.swipeListView.getFirstVisiblePosition();
        int end = this.swipeListView.getLastVisiblePosition();
        View view = this.swipeListView.getChildAt(position - start);
        this.dismissAnimationRefCount++;
        if (position < start || position > end) {
            this.pendingDismisses.add(new PendingDismissData(position, null));
            return 0;
        }
        performDismiss(view, position, false);
        return view.getHeight();
    }

    protected void reloadChoiceStateInView(View frontView, int position) {
        if (isChecked(position)) {
            if (this.swipeDrawableChecked > 0) {
                frontView.setBackgroundResource(this.swipeDrawableChecked);
            }
        } else if (this.swipeDrawableUnchecked > 0) {
            frontView.setBackgroundResource(this.swipeDrawableUnchecked);
        }
    }

    protected void reloadSwipeStateInView(View frontView, int position) {
        if (!((Boolean) this.opened.get(position)).booleanValue()) {
            ViewHelper.setTranslationX(frontView, AutoScrollHelper.RELATIVE_UNSPECIFIED);
        } else if (((Boolean) this.openedRight.get(position)).booleanValue()) {
            ViewHelper.setTranslationX(frontView, (float) this.swipeListView.getWidth());
        } else {
            ViewHelper.setTranslationX(frontView, (float) (-this.swipeListView.getWidth()));
        }
    }

    protected boolean isChecked(int position) {
        return position < this.checked.size() && ((Boolean) this.checked.get(position)).booleanValue();
    }

    protected int getCountSelected() {
        int count = 0;
        for (int i = 0; i < this.checked.size(); i++) {
            if (((Boolean) this.checked.get(i)).booleanValue()) {
                count++;
            }
        }
        return count;
    }

    protected List<Integer> getPositionsSelected() {
        List<Integer> list = new ArrayList();
        for (int i = 0; i < this.checked.size(); i++) {
            if (((Boolean) this.checked.get(i)).booleanValue()) {
                list.add(Integer.valueOf(i));
            }
        }
        return list;
    }

    private void openAnimate(View view, int position) {
        if (!((Boolean) this.opened.get(position)).booleanValue()) {
            generateRevealAnimate(view, true, false, position);
        }
    }

    private void closeAnimate(View view, int position) {
        if (((Boolean) this.opened.get(position)).booleanValue()) {
            generateRevealAnimate(view, true, false, position);
        }
    }

    private void generateAnimate(View view, boolean swap, boolean swapRight, int position) {
        if (this.swipeCurrentAction == 0) {
            generateRevealAnimate(view, swap, swapRight, position);
        }
        if (this.swipeCurrentAction == 1) {
            generateDismissAnimate(this.parentView, swap, swapRight, position);
        }
        if (this.swipeCurrentAction == 2) {
            generateChoiceAnimate(view, position);
        }
    }

    private void generateChoiceAnimate(View view, int position) {
        ViewPropertyAnimator.animate(view).translationX(AutoScrollHelper.RELATIVE_UNSPECIFIED).setDuration(this.animationTime).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                SwipeListViewTouchListener.this.swipeListView.resetScrolling();
                SwipeListViewTouchListener.this.resetCell();
            }
        });
    }

    private void generateDismissAnimate(View view, boolean swap, boolean swapRight, int position) {
        int moveTo = 0;
        if (((Boolean) this.opened.get(position)).booleanValue()) {
            if (!swap) {
                moveTo = ((Boolean) this.openedRight.get(position)).booleanValue() ? (int) (((float) this.viewWidth) - this.rightOffset) : (int) (((float) (-this.viewWidth)) + this.leftOffset);
            }
        } else if (swap) {
            moveTo = swapRight ? (int) (((float) this.viewWidth) - this.rightOffset) : (int) (((float) (-this.viewWidth)) + this.leftOffset);
        }
        int alpha = 1;
        if (swap) {
            this.dismissAnimationRefCount++;
            alpha = 0;
        }
        ViewPropertyAnimator.animate(view).translationX((float) moveTo).alpha((float) alpha).setDuration(this.animationTime).setListener(new AnonymousClass_5(swap, view, position));
    }

    private void generateSpringbackAnimate(View view, boolean swap, boolean swapRight, int position) {
        int moveTo = 0;
        if (((Boolean) this.opened.get(position)).booleanValue()) {
            if (!swap) {
                moveTo = ((Boolean) this.openedRight.get(position)).booleanValue() ? (int) (((float) this.viewWidth) - this.rightOffset) : (int) (((float) (-this.viewWidth)) + this.leftOffset);
            }
        } else if (swap) {
            moveTo = swapRight ? (int) (((float) this.viewWidth) - this.rightOffset) : (int) (((float) (-this.viewWidth)) + this.leftOffset);
        }
        if (swap) {
            this.dismissAnimationRefCount++;
        }
        float currentX = view.getTranslationX();
        float toX = (float) moveTo;
        this.mSpring.removeAllListeners();
        this.mSpring.addListener(new AnonymousClass_6(currentX, toX, view, swap));
        this.mSpring.setCurrentValue(0.0d);
        this.mSpring.setEndValue(1.0d);
    }

    private void generateRevealAnimate(View view, boolean swap, boolean swapRight, int position) {
        int moveTo = 0;
        if (((Boolean) this.opened.get(position)).booleanValue()) {
            if (!swap) {
                moveTo = ((Boolean) this.openedRight.get(position)).booleanValue() ? (int) (((float) this.viewWidth) - this.rightOffset) : (int) (((float) (-this.viewWidth)) + this.leftOffset);
            }
        } else if (swap) {
            moveTo = swapRight ? (int) (((float) this.viewWidth) - this.rightOffset) : (int) (((float) (-this.viewWidth)) + this.leftOffset);
        }
        ViewPropertyAnimator.animate(view).translationX((float) moveTo).setDuration(this.animationTime).setListener(new AnonymousClass_7(swap, position, swapRight));
    }

    private void resetCell() {
        if (this.downPosition != -1 && this.swipeCurrentAction == 2) {
            this.backView.setVisibility(0);
        }
    }

    public void setEnabled(boolean enabled) {
        this.paused = !enabled;
    }

    public OnScrollListener makeScrollListener() {
        return new OnScrollListener() {
            private boolean isFirstItem;
            private boolean isLastItem;

            {
                this.isFirstItem = false;
                this.isLastItem = false;
            }

            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                SwipeListViewTouchListener.this.setEnabled(scrollState != 1);
                if (SwipeListViewTouchListener.this.swipeClosesAllItemsWhenListMoves && scrollState == 1) {
                    SwipeListViewTouchListener.this.closeOpenedItems();
                }
                if (scrollState == 1) {
                    SwipeListViewTouchListener.this.listViewMoving = true;
                    SwipeListViewTouchListener.this.setEnabled(false);
                }
                if (scrollState != 2 && scrollState != 1) {
                    SwipeListViewTouchListener.this.listViewMoving = false;
                    SwipeListViewTouchListener.this.downPosition = -1;
                    SwipeListViewTouchListener.this.swipeListView.resetScrolling();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            SwipeListViewTouchListener.this.setEnabled(true);
                        }
                    }, 500);
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean onLastItemList = false;
                if (this.isFirstItem) {
                    if (firstVisibleItem == 1) {
                        this.isFirstItem = false;
                    }
                } else {
                    boolean onFirstItemList;
                    if (firstVisibleItem == 0) {
                        onFirstItemList = true;
                    } else {
                        onFirstItemList = false;
                    }
                    if (onFirstItemList) {
                        this.isFirstItem = true;
                        SwipeListViewTouchListener.this.swipeListView.onFirstListItem();
                    }
                }
                if (this.isLastItem) {
                    boolean onBeforeLastItemList;
                    if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
                        onBeforeLastItemList = true;
                    } else {
                        onBeforeLastItemList = false;
                    }
                    if (onBeforeLastItemList) {
                        this.isLastItem = false;
                        return;
                    }
                    return;
                }
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    onLastItemList = true;
                }
                if (onLastItemList) {
                    this.isLastItem = true;
                    SwipeListViewTouchListener.this.swipeListView.onLastListItem();
                }
            }
        };
    }

    void closeOpenedItems() {
        if (this.opened != null) {
            int start = this.swipeListView.getFirstVisiblePosition();
            int end = this.swipeListView.getLastVisiblePosition();
            for (int i = start; i <= end; i++) {
                if (((Boolean) this.opened.get(i)).booleanValue()) {
                    closeAnimate(this.swipeListView.getChildAt(i - start).findViewById(this.swipeFrontView), i);
                }
            }
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.viewWidth < 2) {
            this.viewWidth = this.swipeListView.getWidth();
        }
        float deltaX;
        float abs;
        float abs2;
        switch (MotionEventCompat.getActionMasked(motionEvent)) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                if (this.paused && this.downPosition != -1) {
                    return false;
                }
                this.swipeCurrentAction = 3;
                int childCount = this.swipeListView.getChildCount();
                int[] listViewCoords = new int[2];
                this.swipeListView.getLocationOnScreen(listViewCoords);
                int x = ((int) motionEvent.getRawX()) - listViewCoords[0];
                int y = ((int) motionEvent.getRawY()) - listViewCoords[1];
                for (int i = 0; i < childCount; i++) {
                    View child = this.swipeListView.getChildAt(i);
                    child.getHitRect(this.rect);
                    int childPosition = this.swipeListView.getPositionForView(child);
                    if (this.swipeListView.getAdapter().isEnabled(childPosition) && this.rect.contains(x, y)) {
                        setParentView(child);
                        setFrontView(child.findViewById(this.swipeFrontView), childPosition);
                        this.downX = motionEvent.getRawX();
                        this.downPosition = childPosition;
                        this.frontView.setClickable(!((Boolean) this.opened.get(this.downPosition)).booleanValue() ? 1 : null);
                        this.frontView.setLongClickable(false);
                        this.velocityTracker = VelocityTracker.obtain();
                        this.velocityTracker.addMovement(motionEvent);
                        if (this.swipeBackView > 0) {
                            setBackView(child.findViewById(this.swipeBackView));
                        }
                        if (this.swipeListView.getAdapter().getItemViewType(childPosition) < 0 && this.mSpring != null) {
                            this.mSpring.setAtRest();
                            this.mSpring.removeAllListeners();
                        }
                        view.onTouchEvent(motionEvent);
                        return true;
                    }
                }
                view.onTouchEvent(motionEvent);
                return true;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                if (!(this.velocityTracker == null || !this.swiping || this.downPosition == -1)) {
                    deltaX = motionEvent.getRawX() - this.downX;
                    this.velocityTracker.addMovement(motionEvent);
                    this.velocityTracker.computeCurrentVelocity(GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE);
                    abs = Math.abs(this.velocityTracker.getXVelocity());
                    if (!((Boolean) this.opened.get(this.downPosition)).booleanValue()) {
                        if (this.swipeMode == 3 && this.velocityTracker.getXVelocity() > 0.0f) {
                        }
                        if (this.swipeMode == 2 && this.velocityTracker.getXVelocity() < 0.0f) {
                        }
                    }
                    abs2 = Math.abs(this.velocityTracker.getYVelocity());
                    boolean swap = false;
                    boolean swapRight = false;
                    if (((double) Math.abs(deltaX)) > ((double) this.viewWidth) * 0.5d) {
                        swap = true;
                        swapRight = deltaX > 0.0f;
                    }
                    if (this.swipeListView.getAdapter().getItemViewType(this.downPosition) < 0) {
                        generateSpringbackAnimate(this.parentView, false, swapRight, this.downPosition);
                    } else {
                        generateAnimate(this.frontView, swap, swapRight, this.downPosition);
                    }
                    if (this.swipeCurrentAction == 2) {
                        swapChoiceState(this.downPosition);
                    }
                    this.velocityTracker.recycle();
                    this.velocityTracker = null;
                    this.downX = 0.0f;
                    this.swiping = false;
                    this.frontView.setClickable(((Boolean) this.opened.get(this.downPosition)).booleanValue());
                    this.frontView.setLongClickable(((Boolean) this.opened.get(this.downPosition)).booleanValue());
                    this.frontView = null;
                    this.backView = null;
                    this.downPosition = -1;
                }
                return false;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                if (!(this.velocityTracker == null || this.paused || this.downPosition == -1)) {
                    this.velocityTracker.addMovement(motionEvent);
                    this.velocityTracker.computeCurrentVelocity(GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE);
                    abs = Math.abs(this.velocityTracker.getXVelocity());
                    abs2 = Math.abs(this.velocityTracker.getYVelocity());
                    deltaX = motionEvent.getRawX() - this.downX;
                    float deltaMode = Math.abs(deltaX);
                    int swipeMode = this.swipeMode;
                    int changeSwipeMode = this.swipeListView.changeSwipeMode(this.downPosition);
                    if (changeSwipeMode >= 0) {
                        swipeMode = changeSwipeMode;
                    }
                    if (swipeMode == 0) {
                        deltaMode = AutoScrollHelper.RELATIVE_UNSPECIFIED;
                    } else if (swipeMode != 1) {
                        if (((Boolean) this.opened.get(this.downPosition)).booleanValue()) {
                            if (swipeMode == 3 && deltaX < 0.0f) {
                                deltaMode = AutoScrollHelper.RELATIVE_UNSPECIFIED;
                            } else if (swipeMode == 2 && deltaX > 0.0f) {
                                deltaMode = AutoScrollHelper.RELATIVE_UNSPECIFIED;
                            }
                        } else if (swipeMode == 3 && deltaX > 0.0f) {
                            deltaMode = AutoScrollHelper.RELATIVE_UNSPECIFIED;
                        } else if (swipeMode == 2 && deltaX < 0.0f) {
                            deltaMode = AutoScrollHelper.RELATIVE_UNSPECIFIED;
                        }
                    }
                    if (deltaMode > ((float) this.slop) && this.swipeCurrentAction == 3 && abs2 < abs) {
                        this.swiping = true;
                        this.swipingRight = deltaX > 0.0f ? 1 : null;
                        if (((Boolean) this.opened.get(this.downPosition)).booleanValue()) {
                            this.swipeListView.onStartClose(this.downPosition, this.swipingRight);
                            this.swipeCurrentAction = 0;
                        } else {
                            if (this.swipingRight && this.swipeActionRight == 1) {
                                this.swipeCurrentAction = 1;
                            } else if (!this.swipingRight && this.swipeActionLeft == 1) {
                                this.swipeCurrentAction = 1;
                            } else if (this.swipingRight && this.swipeActionRight == 2) {
                                this.swipeCurrentAction = 2;
                            } else if (this.swipingRight || this.swipeActionLeft != 2) {
                                this.swipeCurrentAction = 0;
                            } else {
                                this.swipeCurrentAction = 2;
                            }
                            this.swipeListView.onStartOpen(this.downPosition, this.swipeCurrentAction, this.swipingRight);
                        }
                        this.swipeListView.requestDisallowInterceptTouchEvent(true);
                        MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                        cancelEvent.setAction((MotionEventCompat.getActionIndex(motionEvent) << 8) | 3);
                        this.swipeListView.onTouchEvent(cancelEvent);
                        if (this.swipeCurrentAction == 2) {
                            this.backView.setVisibility(DetectedActivity.RUNNING);
                        }
                    }
                    if (this.swiping && this.downPosition != -1) {
                        if (((Boolean) this.opened.get(this.downPosition)).booleanValue()) {
                            float f;
                            if (((Boolean) this.openedRight.get(this.downPosition)).booleanValue()) {
                                f = ((float) this.viewWidth) - this.rightOffset;
                            } else {
                                f = ((float) (-this.viewWidth)) + this.leftOffset;
                            }
                            deltaX += f;
                        }
                        move(0.8f * deltaX);
                        return true;
                    }
                }
                return false;
            default:
                return false;
        }
    }

    private void setActionsTo(int action) {
        this.oldSwipeActionRight = this.swipeActionRight;
        this.oldSwipeActionLeft = this.swipeActionLeft;
        this.swipeActionRight = action;
        this.swipeActionLeft = action;
    }

    protected void returnOldActions() {
        this.swipeActionRight = this.oldSwipeActionRight;
        this.swipeActionLeft = this.oldSwipeActionLeft;
    }

    public void move(float deltaX) {
        boolean z;
        this.swipeListView.onMove(this.downPosition, deltaX);
        float posX = ViewHelper.getX(this.frontView);
        if (((Boolean) this.opened.get(this.downPosition)).booleanValue()) {
            posX += ((Boolean) this.openedRight.get(this.downPosition)).booleanValue() ? ((float) (-this.viewWidth)) + this.rightOffset : ((float) this.viewWidth) - this.leftOffset;
        }
        if (posX > 0.0f && !this.swipingRight) {
            if (this.swipingRight) {
                z = false;
            } else {
                z = true;
            }
            this.swipingRight = z;
            this.swipeCurrentAction = this.swipeActionRight;
            if (this.swipeCurrentAction == 2) {
                this.backView.setVisibility(DetectedActivity.RUNNING);
            } else {
                this.backView.setVisibility(0);
            }
        }
        if (posX < 0.0f && this.swipingRight) {
            if (this.swipingRight) {
                z = false;
            } else {
                z = true;
            }
            this.swipingRight = z;
            this.swipeCurrentAction = this.swipeActionLeft;
            if (this.swipeCurrentAction == 2) {
                this.backView.setVisibility(DetectedActivity.RUNNING);
            } else {
                this.backView.setVisibility(0);
            }
        }
        if (this.swipeCurrentAction == 1) {
            if (this.swipeListView.getAdapter().getItemViewType(this.downPosition) < 0) {
                ViewHelper.setTranslationX(this.parentView, convertXWithDamp(deltaX));
            } else {
                ViewHelper.setTranslationX(this.parentView, deltaX);
            }
            if (this.swipeListView.getAdapter().getItemViewType(this.downPosition) >= 0) {
                ViewHelper.setAlpha(this.parentView, Math.max(AutoScrollHelper.RELATIVE_UNSPECIFIED, Math.min(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, 1.0f - ((1.3f * Math.abs(deltaX)) / ((float) this.viewWidth)))));
            }
        } else if (this.swipeCurrentAction == 2) {
            if (!this.swipingRight || deltaX <= 0.0f || posX >= 80.0f) {
                if (this.swipingRight || deltaX >= 0.0f || posX <= -80.0f) {
                    if ((!this.swipingRight || deltaX >= 80.0f) && (this.swipingRight || deltaX <= -80.0f)) {
                        return;
                    }
                }
            }
            ViewHelper.setTranslationX(this.frontView, deltaX);
        } else {
            ViewHelper.setTranslationX(this.frontView, deltaX);
        }
    }

    private float convertXWithDamp(float deltaX) {
        float value = (float) Math.pow((double) Math.abs(deltaX), 0.75d);
        return deltaX >= 0.0f ? value : -value;
    }

    protected void performDismiss(View dismissView, int dismissPosition, boolean doPendingDismiss) {
        enableDisableViewGroup((ViewGroup) dismissView, false);
        LayoutParams lp = dismissView.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(dismissView.getHeight(), 1).setDuration(this.animationTime);
        if (doPendingDismiss) {
            animator.addListener(new AnonymousClass_9(originalHeight));
        }
        animator.addListener(new AnonymousClass_10(dismissView));
        animator.addUpdateListener(new AnonymousClass_11(lp, dismissView));
        this.pendingDismisses.add(new PendingDismissData(dismissPosition, dismissView));
        animator.start();
    }

    protected void resetPendingDismisses() {
        this.pendingDismisses.clear();
    }

    protected void handlerPendingDismisses(int originalHeight) {
        new Handler().postDelayed(new AnonymousClass_12(originalHeight), this.animationTime + 100);
    }

    private void removePendingDismisses(int originalHeight) {
        Collections.sort(this.pendingDismisses);
        int[] dismissPositions = new int[this.pendingDismisses.size()];
        for (int i = this.pendingDismisses.size() - 1; i >= 0; i--) {
            dismissPositions[i] = ((PendingDismissData) this.pendingDismisses.get(i)).position;
        }
        this.swipeListView.onDismiss(dismissPositions);
        for (PendingDismissData pendingDismiss : this.pendingDismisses) {
            if (pendingDismiss.view != null) {
                ViewHelper.setAlpha(pendingDismiss.view, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                ViewHelper.setTranslationX(pendingDismiss.view, AutoScrollHelper.RELATIVE_UNSPECIFIED);
                LayoutParams lp = pendingDismiss.view.getLayoutParams();
                lp.height = originalHeight;
                pendingDismiss.view.setLayoutParams(lp);
            }
        }
        resetPendingDismisses();
    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }
}
