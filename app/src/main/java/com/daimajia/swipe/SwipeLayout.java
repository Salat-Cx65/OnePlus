package com.daimajia.swipe;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import com.android.volley.DefaultRetryPolicy;
import com.daimajia.swipe.SwipeLayout.DragEdge;
import com.daimajia.swipe.SwipeLayout.ShowMode;
import com.daimajia.swipe.SwipeLayout.SwipeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class SwipeLayout extends FrameLayout {
    private GestureDetector gestureDetector;
    private DoubleClickListener mDoubleClickListener;
    private int mDragDistance;
    private DragEdge mDragEdge;
    private ViewDragHelper mDragHelper;
    private Callback mDragHelperCallback;
    private int mEventCounter;
    private List<OnLayout> mOnLayoutListeners;
    private Map<View, ArrayList<OnRevealListener>> mRevealListeners;
    private Map<View, Boolean> mShowEntirely;
    private ShowMode mShowMode;
    private List<SwipeDenier> mSwipeDeniers;
    private boolean mSwipeEnabled;
    private List<SwipeListener> mSwipeListeners;
    private boolean mTouchConsumedByChild;
    private float sX;
    private float sY;

    static /* synthetic */ class AnonymousClass_2 {
        static final /* synthetic */ int[] $SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge;

        static {
            $SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge = new int[DragEdge.values().length];
            try {
                $SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[DragEdge.Top.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[DragEdge.Bottom.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[DragEdge.Left.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[DragEdge.Right.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static interface DoubleClickListener {
        void onDoubleClick(SwipeLayout swipeLayout, boolean z);
    }

    public enum DragEdge {
        Left,
        Right,
        Top,
        Bottom
    }

    public static interface OnLayout {
        void onLayout(SwipeLayout swipeLayout);
    }

    public static interface OnRevealListener {
        void onReveal(View view, DragEdge dragEdge, float f, int i);
    }

    public enum ShowMode {
        LayDown,
        PullOut
    }

    public enum Status {
        Middle,
        Open,
        Close
    }

    public static interface SwipeDenier {
        boolean shouldDenySwipe(MotionEvent motionEvent);
    }

    class SwipeDetector extends SimpleOnGestureListener {
        SwipeDetector() {
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }

        public boolean onSingleTapUp(MotionEvent e) {
            if (SwipeLayout.this.mDoubleClickListener == null) {
                SwipeLayout.this.performAdapterViewItemClick(e);
            }
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (SwipeLayout.this.mDoubleClickListener != null) {
                SwipeLayout.this.performAdapterViewItemClick(e);
            }
            return true;
        }

        public void onLongPress(MotionEvent e) {
            SwipeLayout.this.performLongClick();
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (SwipeLayout.this.mDoubleClickListener != null) {
                View target;
                View bottom = SwipeLayout.this.getBottomView();
                View surface = SwipeLayout.this.getSurfaceView();
                if (e.getX() <= ((float) bottom.getLeft()) || e.getX() >= ((float) bottom.getRight()) || e.getY() <= ((float) bottom.getTop()) || e.getY() >= ((float) bottom.getBottom())) {
                    target = surface;
                } else {
                    target = bottom;
                }
                SwipeLayout.this.mDoubleClickListener.onDoubleClick(SwipeLayout.this, target == surface);
            }
            return true;
        }
    }

    public static interface SwipeListener {
        void onClose(SwipeLayout swipeLayout);

        void onHandRelease(SwipeLayout swipeLayout, float f, float f2);

        void onOpen(SwipeLayout swipeLayout);

        void onStartClose(SwipeLayout swipeLayout);

        void onStartOpen(SwipeLayout swipeLayout);

        void onUpdate(SwipeLayout swipeLayout, int i, int i2);
    }

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mDragDistance = 0;
        this.mSwipeListeners = new ArrayList();
        this.mSwipeDeniers = new ArrayList();
        this.mRevealListeners = new HashMap();
        this.mShowEntirely = new HashMap();
        this.mSwipeEnabled = true;
        this.mDragHelperCallback = new Callback() {
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (child == SwipeLayout.this.getSurfaceView()) {
                    switch (AnonymousClass_2.$SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[SwipeLayout.this.mDragEdge.ordinal()]) {
                        case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        case RainSurfaceView.RAIN_LEVEL_SHOWER:
                            return SwipeLayout.this.getPaddingLeft();
                        case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                            if (left < SwipeLayout.this.getPaddingLeft()) {
                                return SwipeLayout.this.getPaddingLeft();
                            }
                            return left > SwipeLayout.this.getPaddingLeft() + SwipeLayout.this.mDragDistance ? SwipeLayout.this.getPaddingLeft() + SwipeLayout.this.mDragDistance : left;
                        case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                            if (left > SwipeLayout.this.getPaddingLeft()) {
                                return SwipeLayout.this.getPaddingLeft();
                            }
                            return left < SwipeLayout.this.getPaddingLeft() - SwipeLayout.this.mDragDistance ? SwipeLayout.this.getPaddingLeft() - SwipeLayout.this.mDragDistance : left;
                        default:
                            return left;
                    }
                } else if (child != SwipeLayout.this.getBottomView()) {
                    return left;
                } else {
                    switch (AnonymousClass_2.$SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[SwipeLayout.this.mDragEdge.ordinal()]) {
                        case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        case RainSurfaceView.RAIN_LEVEL_SHOWER:
                            return SwipeLayout.this.getPaddingLeft();
                        case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                            return (SwipeLayout.this.mShowMode != ShowMode.PullOut || left <= SwipeLayout.this.getPaddingLeft()) ? left : SwipeLayout.this.getPaddingLeft();
                        case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                            return (SwipeLayout.this.mShowMode != ShowMode.PullOut || left >= SwipeLayout.this.getMeasuredWidth() - SwipeLayout.this.mDragDistance) ? left : SwipeLayout.this.getMeasuredWidth() - SwipeLayout.this.mDragDistance;
                        default:
                            return left;
                    }
                }
            }

            public int clampViewPositionVertical(View child, int top, int dy) {
                if (child == SwipeLayout.this.getSurfaceView()) {
                    switch (AnonymousClass_2.$SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[SwipeLayout.this.mDragEdge.ordinal()]) {
                        case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                            if (top < SwipeLayout.this.getPaddingTop()) {
                                return SwipeLayout.this.getPaddingTop();
                            }
                            return top > SwipeLayout.this.getPaddingTop() + SwipeLayout.this.mDragDistance ? SwipeLayout.this.getPaddingTop() + SwipeLayout.this.mDragDistance : top;
                        case RainSurfaceView.RAIN_LEVEL_SHOWER:
                            if (top < SwipeLayout.this.getPaddingTop() - SwipeLayout.this.mDragDistance) {
                                return SwipeLayout.this.getPaddingTop() - SwipeLayout.this.mDragDistance;
                            }
                            return top > SwipeLayout.this.getPaddingTop() ? SwipeLayout.this.getPaddingTop() : top;
                        case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                        case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                            return SwipeLayout.this.getPaddingTop();
                        default:
                            return top;
                    }
                }
                switch (AnonymousClass_2.$SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[SwipeLayout.this.mDragEdge.ordinal()]) {
                    case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        if (SwipeLayout.this.mShowMode == ShowMode.PullOut) {
                            return top > SwipeLayout.this.getPaddingTop() ? SwipeLayout.this.getPaddingTop() : top;
                        } else {
                            if (SwipeLayout.this.getSurfaceView().getTop() + dy < SwipeLayout.this.getPaddingTop()) {
                                return SwipeLayout.this.getPaddingTop();
                            }
                            return SwipeLayout.this.getSurfaceView().getTop() + dy > SwipeLayout.this.getPaddingTop() + SwipeLayout.this.mDragDistance ? SwipeLayout.this.getPaddingTop() + SwipeLayout.this.mDragDistance : top;
                        }
                    case RainSurfaceView.RAIN_LEVEL_SHOWER:
                        if (SwipeLayout.this.mShowMode == ShowMode.PullOut) {
                            return top < SwipeLayout.this.getMeasuredHeight() - SwipeLayout.this.mDragDistance ? SwipeLayout.this.getMeasuredHeight() - SwipeLayout.this.mDragDistance : top;
                        } else {
                            if (SwipeLayout.this.getSurfaceView().getTop() + dy >= SwipeLayout.this.getPaddingTop()) {
                                return SwipeLayout.this.getPaddingTop();
                            }
                            return SwipeLayout.this.getSurfaceView().getTop() + dy <= SwipeLayout.this.getPaddingTop() - SwipeLayout.this.mDragDistance ? SwipeLayout.this.getPaddingTop() - SwipeLayout.this.mDragDistance : top;
                        }
                    case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                        return SwipeLayout.this.getPaddingTop();
                    default:
                        return top;
                }
            }

            public boolean tryCaptureView(View child, int pointerId) {
                return child == SwipeLayout.this.getSurfaceView() || child == SwipeLayout.this.getBottomView();
            }

            public int getViewHorizontalDragRange(View child) {
                return SwipeLayout.this.mDragDistance;
            }

            public int getViewVerticalDragRange(View child) {
                return SwipeLayout.this.mDragDistance;
            }

            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                for (SwipeListener l : SwipeLayout.this.mSwipeListeners) {
                    l.onHandRelease(SwipeLayout.this, xvel, yvel);
                }
                if (releasedChild == SwipeLayout.this.getSurfaceView()) {
                    SwipeLayout.this.processSurfaceRelease(xvel, yvel);
                } else if (releasedChild == SwipeLayout.this.getBottomView()) {
                    if (SwipeLayout.this.getShowMode() == ShowMode.PullOut) {
                        SwipeLayout.this.processBottomPullOutRelease(xvel, yvel);
                    } else if (SwipeLayout.this.getShowMode() == ShowMode.LayDown) {
                        SwipeLayout.this.processBottomLayDownMode(xvel, yvel);
                    }
                }
                SwipeLayout.this.invalidate();
            }

            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                int evLeft = SwipeLayout.this.getSurfaceView().getLeft();
                int evRight = SwipeLayout.this.getSurfaceView().getRight();
                int evTop = SwipeLayout.this.getSurfaceView().getTop();
                int evBottom = SwipeLayout.this.getSurfaceView().getBottom();
                if (changedView == SwipeLayout.this.getSurfaceView()) {
                    if (SwipeLayout.this.mShowMode == ShowMode.PullOut) {
                        if (SwipeLayout.this.mDragEdge == DragEdge.Left || SwipeLayout.this.mDragEdge == DragEdge.Right) {
                            SwipeLayout.this.getBottomView().offsetLeftAndRight(dx);
                        } else {
                            SwipeLayout.this.getBottomView().offsetTopAndBottom(dy);
                        }
                    }
                } else if (changedView == SwipeLayout.this.getBottomView()) {
                    if (SwipeLayout.this.mShowMode == ShowMode.PullOut) {
                        SwipeLayout.this.getSurfaceView().offsetLeftAndRight(dx);
                        SwipeLayout.this.getSurfaceView().offsetTopAndBottom(dy);
                    } else {
                        Rect rect = SwipeLayout.this.computeBottomLayDown(SwipeLayout.this.mDragEdge);
                        SwipeLayout.this.getBottomView().layout(rect.left, rect.top, rect.right, rect.bottom);
                        int newLeft = SwipeLayout.this.getSurfaceView().getLeft() + dx;
                        int newTop = SwipeLayout.this.getSurfaceView().getTop() + dy;
                        if (SwipeLayout.this.mDragEdge == DragEdge.Left && newLeft < SwipeLayout.this.getPaddingLeft()) {
                            newLeft = SwipeLayout.this.getPaddingLeft();
                        } else if (SwipeLayout.this.mDragEdge == DragEdge.Right && newLeft > SwipeLayout.this.getPaddingLeft()) {
                            newLeft = SwipeLayout.this.getPaddingLeft();
                        } else if (SwipeLayout.this.mDragEdge == DragEdge.Top && newTop < SwipeLayout.this.getPaddingTop()) {
                            newTop = SwipeLayout.this.getPaddingTop();
                        } else if (SwipeLayout.this.mDragEdge == DragEdge.Bottom && newTop > SwipeLayout.this.getPaddingTop()) {
                            newTop = SwipeLayout.this.getPaddingTop();
                        }
                        SwipeLayout.this.getSurfaceView().layout(newLeft, newTop, SwipeLayout.this.getMeasuredWidth() + newLeft, SwipeLayout.this.getMeasuredHeight() + newTop);
                    }
                }
                SwipeLayout.this.dispatchRevealEvent(evLeft, evTop, evRight, evBottom);
                SwipeLayout.this.dispatchSwipeEvent(evLeft, evTop, dx, dy);
                SwipeLayout.this.invalidate();
            }
        };
        this.mEventCounter = 0;
        this.mTouchConsumedByChild = false;
        this.sX = -1.0f;
        this.sY = -1.0f;
        this.gestureDetector = new GestureDetector(getContext(), new SwipeDetector());
        this.mDragHelper = ViewDragHelper.create(this, this.mDragHelperCallback);
        this.mDragEdge = DragEdge.values()[1];
        this.mShowMode = ShowMode.values()[1];
    }

    public void addSwipeListener(SwipeListener l) {
        this.mSwipeListeners.add(l);
    }

    public void removeSwipeListener(SwipeListener l) {
        this.mSwipeListeners.remove(l);
    }

    public void addSwipeDenier(SwipeDenier denier) {
        this.mSwipeDeniers.add(denier);
    }

    public void removeSwipeDenier(SwipeDenier denier) {
        this.mSwipeDeniers.remove(denier);
    }

    public void removeAllSwipeDeniers() {
        this.mSwipeDeniers.clear();
    }

    public void addRevealListener(int childId, OnRevealListener l) {
        View child = findViewById(childId);
        if (child == null) {
            throw new IllegalArgumentException("Child does not belong to SwipeListener.");
        }
        if (!this.mShowEntirely.containsKey(child)) {
            this.mShowEntirely.put(child, Boolean.valueOf(false));
        }
        if (this.mRevealListeners.get(child) == null) {
            this.mRevealListeners.put(child, new ArrayList());
        }
        ((ArrayList) this.mRevealListeners.get(child)).add(l);
    }

    public void addRevealListener(int[] childIds, OnRevealListener l) {
        for (int i : childIds) {
            addRevealListener(i, l);
        }
    }

    public void removeRevealListener(int childId, OnRevealListener l) {
        View child = findViewById(childId);
        if (child != null) {
            this.mShowEntirely.remove(child);
            if (this.mRevealListeners.containsKey(child)) {
                ((ArrayList) this.mRevealListeners.get(child)).remove(l);
            }
        }
    }

    public void removeAllRevealListeners(int childId) {
        View child = findViewById(childId);
        if (child != null) {
            this.mRevealListeners.remove(child);
            this.mShowEntirely.remove(child);
        }
    }

    protected boolean isViewTotallyFirstShowed(View child, Rect relativePosition, DragEdge edge, int surfaceLeft, int surfaceTop, int surfaceRight, int surfaceBottom) {
        if (((Boolean) this.mShowEntirely.get(child)).booleanValue()) {
            return false;
        }
        int childLeft = relativePosition.left;
        int childRight = relativePosition.right;
        int childTop = relativePosition.top;
        int childBottom = relativePosition.bottom;
        if (getShowMode() == ShowMode.LayDown) {
            if (edge != DragEdge.Right || surfaceRight > childLeft) {
                if (edge != DragEdge.Left || surfaceLeft < childRight) {
                    if ((edge != DragEdge.Top || surfaceTop < childBottom) && (edge != DragEdge.Bottom || surfaceBottom > childTop)) {
                        return false;
                    }
                }
            }
            return true;
        } else if (getShowMode() != ShowMode.PullOut) {
            return false;
        } else {
            if (edge != DragEdge.Right || childRight > getWidth()) {
                if (edge != DragEdge.Left || childLeft < getPaddingLeft()) {
                    if ((edge != DragEdge.Top || childTop < getPaddingTop()) && (edge != DragEdge.Bottom || childBottom > getHeight())) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    protected boolean isViewShowing(View child, Rect relativePosition, DragEdge availableEdge, int surfaceLeft, int surfaceTop, int surfaceRight, int surfaceBottom) {
        int childLeft = relativePosition.left;
        int childRight = relativePosition.right;
        int childTop = relativePosition.top;
        int childBottom = relativePosition.bottom;
        if (getShowMode() == ShowMode.LayDown) {
            switch (AnonymousClass_2.$SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[availableEdge.ordinal()]) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    if (surfaceTop >= childTop && surfaceTop < childBottom) {
                        return true;
                    }
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    if (surfaceBottom > childTop && surfaceBottom <= childBottom) {
                        return true;
                    }
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    if (surfaceLeft < childRight && surfaceLeft >= childLeft) {
                        return true;
                    }
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    if (surfaceRight > childLeft && surfaceRight <= childRight) {
                        return true;
                    }
            }
        } else if (getShowMode() == ShowMode.PullOut) {
            switch (AnonymousClass_2.$SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[availableEdge.ordinal()]) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    if (childTop < getPaddingTop() && childBottom >= getPaddingTop()) {
                        return true;
                    }
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    if (childTop < getHeight() && childTop >= getPaddingTop()) {
                        return true;
                    }
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    if (childRight >= getPaddingLeft() && childLeft < getPaddingLeft()) {
                        return true;
                    }
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    if (childLeft <= getWidth() && childRight > getWidth()) {
                        return true;
                    }
                default:
                    break;
            }
        }
        return false;
    }

    protected Rect getRelativePosition(View child) {
        View t = child;
        Rect r = new Rect(t.getLeft(), t.getTop(), 0, 0);
        while (t.getParent() != null && t != getRootView()) {
            t = t.getParent();
            if (t == this) {
                break;
            }
            r.left += t.getLeft();
            r.top += t.getTop();
        }
        r.right = r.left + child.getMeasuredWidth();
        r.bottom = r.top + child.getMeasuredHeight();
        return r;
    }

    protected void dispatchSwipeEvent(int surfaceLeft, int surfaceTop, int dx, int dy) {
        DragEdge edge = getDragEdge();
        boolean open = true;
        if (edge == DragEdge.Left) {
            if (dx < 0) {
                open = false;
            }
        } else if (edge == DragEdge.Right) {
            if (dx > 0) {
                open = false;
            }
        } else if (edge == DragEdge.Top) {
            if (dy < 0) {
                open = false;
            }
        } else if (edge == DragEdge.Bottom && dy > 0) {
            open = false;
        }
        dispatchSwipeEvent(surfaceLeft, surfaceTop, open);
    }

    protected void dispatchSwipeEvent(int surfaceLeft, int surfaceTop, boolean open) {
        safeBottomView();
        Status status = getOpenStatus();
        if (!this.mSwipeListeners.isEmpty()) {
            this.mEventCounter++;
            for (SwipeListener l : this.mSwipeListeners) {
                if (this.mEventCounter == 1) {
                    if (open) {
                        l.onStartOpen(this);
                    } else {
                        l.onStartClose(this);
                    }
                }
                l.onUpdate(this, surfaceLeft - getPaddingLeft(), surfaceTop - getPaddingTop());
            }
            if (status == Status.Close) {
                for (SwipeListener l2 : this.mSwipeListeners) {
                    l2.onClose(this);
                }
                this.mEventCounter = 0;
            }
            if (status == Status.Open) {
                getBottomView().setEnabled(true);
                for (SwipeListener l22 : this.mSwipeListeners) {
                    l22.onOpen(this);
                }
                this.mEventCounter = 0;
            }
        }
    }

    private void safeBottomView() {
        Status status = getOpenStatus();
        ViewGroup bottom = getBottomView();
        if (status == Status.Close) {
            if (bottom.getVisibility() != 4) {
                bottom.setVisibility(RainSurfaceView.RAIN_LEVEL_RAINSTORM);
            }
        } else if (bottom.getVisibility() != 0) {
            bottom.setVisibility(0);
        }
    }

    protected void dispatchRevealEvent(int surfaceLeft, int surfaceTop, int surfaceRight, int surfaceBottom) {
        if (!this.mRevealListeners.isEmpty()) {
            for (Entry<View, ArrayList<OnRevealListener>> entry : this.mRevealListeners.entrySet()) {
                Iterator i$;
                View child = (View) entry.getKey();
                Rect rect = getRelativePosition(child);
                if (isViewShowing(child, rect, this.mDragEdge, surfaceLeft, surfaceTop, surfaceRight, surfaceBottom)) {
                    this.mShowEntirely.put(child, Boolean.valueOf(false));
                    int distance = 0;
                    float fraction = AutoScrollHelper.RELATIVE_UNSPECIFIED;
                    if (getShowMode() == ShowMode.LayDown) {
                        switch (AnonymousClass_2.$SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[this.mDragEdge.ordinal()]) {
                            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                                distance = rect.top - surfaceTop;
                                fraction = ((float) distance) / ((float) child.getHeight());
                                break;
                            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                                distance = rect.bottom - surfaceBottom;
                                fraction = ((float) distance) / ((float) child.getHeight());
                                break;
                            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                                distance = rect.left - surfaceLeft;
                                fraction = ((float) distance) / ((float) child.getWidth());
                                break;
                            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                                distance = rect.right - surfaceRight;
                                fraction = ((float) distance) / ((float) child.getWidth());
                                break;
                        }
                    } else if (getShowMode() == ShowMode.PullOut) {
                        switch (AnonymousClass_2.$SwitchMap$com$daimajia$swipe$SwipeLayout$DragEdge[this.mDragEdge.ordinal()]) {
                            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                                distance = rect.bottom - getPaddingTop();
                                fraction = ((float) distance) / ((float) child.getHeight());
                                break;
                            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                                distance = rect.top - getHeight();
                                fraction = ((float) distance) / ((float) child.getHeight());
                                break;
                            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                                distance = rect.right - getPaddingLeft();
                                fraction = ((float) distance) / ((float) child.getWidth());
                                break;
                            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                                distance = rect.left - getWidth();
                                fraction = ((float) distance) / ((float) child.getWidth());
                                break;
                            default:
                                break;
                        }
                    }
                    i$ = ((ArrayList) entry.getValue()).iterator();
                    while (i$.hasNext()) {
                        ((OnRevealListener) i$.next()).onReveal(child, this.mDragEdge, Math.abs(fraction), distance);
                        if (Math.abs(fraction) == 1.0f) {
                            this.mShowEntirely.put(child, Boolean.valueOf(true));
                        }
                    }
                }
                if (isViewTotallyFirstShowed(child, rect, this.mDragEdge, surfaceLeft, surfaceTop, surfaceRight, surfaceBottom)) {
                    this.mShowEntirely.put(child, Boolean.valueOf(true));
                    i$ = ((ArrayList) entry.getValue()).iterator();
                    while (i$.hasNext()) {
                        OnRevealListener l = (OnRevealListener) i$.next();
                        if (this.mDragEdge == DragEdge.Left || this.mDragEdge == DragEdge.Right) {
                            l.onReveal(child, this.mDragEdge, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, child.getWidth());
                        } else {
                            l.onReveal(child, this.mDragEdge, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, child.getHeight());
                        }
                    }
                }
            }
        }
    }

    public void computeScroll() {
        super.computeScroll();
        if (this.mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void addOnLayoutListener(OnLayout l) {
        if (this.mOnLayoutListeners == null) {
            this.mOnLayoutListeners = new ArrayList();
        }
        this.mOnLayoutListeners.add(l);
    }

    public void removeOnLayoutListener(OnLayout l) {
        if (this.mOnLayoutListeners != null) {
            this.mOnLayoutListeners.remove(l);
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() != 2) {
            throw new IllegalStateException("You need 2  views in SwipeLayout");
        } else if ((getChildAt(0) instanceof ViewGroup) && (getChildAt(1) instanceof ViewGroup)) {
            if (this.mShowMode == ShowMode.PullOut) {
                layoutPullOut();
            } else if (this.mShowMode == ShowMode.LayDown) {
                layoutLayDown();
            }
            safeBottomView();
            if (this.mOnLayoutListeners != null) {
                for (int i = 0; i < this.mOnLayoutListeners.size(); i++) {
                    ((OnLayout) this.mOnLayoutListeners.get(i)).onLayout(this);
                }
            }
        } else {
            throw new IllegalArgumentException("The 2 children in SwipeLayout must be an instance of ViewGroup");
        }
    }

    void layoutPullOut() {
        Rect rect = computeSurfaceLayoutArea(false);
        getSurfaceView().layout(rect.left, rect.top, rect.right, rect.bottom);
        rect = computeBottomLayoutAreaViaSurface(ShowMode.PullOut, rect);
        getBottomView().layout(rect.left, rect.top, rect.right, rect.bottom);
        bringChildToFront(getSurfaceView());
    }

    void layoutLayDown() {
        Rect rect = computeSurfaceLayoutArea(false);
        getSurfaceView().layout(rect.left, rect.top, rect.right, rect.bottom);
        rect = computeBottomLayoutAreaViaSurface(ShowMode.LayDown, rect);
        getBottomView().layout(rect.left, rect.top, rect.right, rect.bottom);
        bringChildToFront(getSurfaceView());
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mDragEdge == DragEdge.Left || this.mDragEdge == DragEdge.Right) {
            this.mDragDistance = getBottomView().getMeasuredWidth();
        } else {
            this.mDragDistance = getBottomView().getMeasuredHeight();
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean z = true;
        if (!isEnabled() || !isEnabledInAdapterView()) {
            return true;
        }
        if (!isSwipeEnabled()) {
            return false;
        }
        for (SwipeDenier denier : this.mSwipeDeniers) {
            if (denier != null && denier.shouldDenySwipe(ev)) {
                return false;
            }
        }
        switch (ev.getActionMasked()) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                Status status = getOpenStatus();
                if (status == Status.Close) {
                    if (childNeedHandleTouchEvent(getSurfaceView(), ev) == null) {
                        z = false;
                    }
                    this.mTouchConsumedByChild = z;
                } else if (status == Status.Open) {
                    if (childNeedHandleTouchEvent(getBottomView(), ev) == null) {
                        z = false;
                    }
                    this.mTouchConsumedByChild = z;
                }
                break;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                this.mTouchConsumedByChild = false;
                break;
        }
        return !this.mTouchConsumedByChild ? this.mDragHelper.shouldInterceptTouchEvent(ev) : false;
    }

    private View childNeedHandleTouchEvent(ViewGroup v, MotionEvent event) {
        if (v == null) {
            return null;
        }
        if (v.onTouchEvent(event)) {
            return v;
        }
        for (int i = v.getChildCount() - 1; i >= 0; i--) {
            View child = v.getChildAt(i);
            if (child instanceof ViewGroup) {
                View grandChild = childNeedHandleTouchEvent((ViewGroup) child, event);
                if (grandChild != null) {
                    return grandChild;
                }
            } else if (childNeedHandleTouchEvent(v.getChildAt(i), event)) {
                return v.getChildAt(i);
            }
        }
        return null;
    }

    private boolean childNeedHandleTouchEvent(View v, MotionEvent event) {
        if (v == null) {
            return false;
        }
        int[] loc = new int[2];
        v.getLocationOnScreen(loc);
        int left = loc[0];
        int top = loc[1];
        return (event.getRawX() <= ((float) left) || event.getRawX() >= ((float) (v.getWidth() + left)) || event.getRawY() <= ((float) top) || event.getRawY() >= ((float) (v.getHeight() + top))) ? false : v.onTouchEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabledInAdapterView() || !isEnabled()) {
            return true;
        }
        if (!isSwipeEnabled()) {
            return super.onTouchEvent(event);
        }
        int action = event.getActionMasked();
        ViewParent parent = getParent();
        this.gestureDetector.onTouchEvent(event);
        Status status = getOpenStatus();
        ViewGroup touching = null;
        if (status == Status.Close) {
            touching = getSurfaceView();
        } else if (status == Status.Open) {
            touching = getBottomView();
        }
        switch (action) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                this.mDragHelper.processTouchEvent(event);
                parent.requestDisallowInterceptTouchEvent(true);
                this.sX = event.getRawX();
                this.sY = event.getRawY();
                if (touching != null) {
                    touching.setPressed(true);
                }
                return true;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                this.sX = -1.0f;
                this.sY = -1.0f;
                if (touching != null) {
                    touching.setPressed(false);
                }
                parent.requestDisallowInterceptTouchEvent(true);
                this.mDragHelper.processTouchEvent(event);
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                if (this.sX == -1.0f || this.sY == -1.0f) {
                    event.setAction(0);
                    this.mDragHelper.processTouchEvent(event);
                    parent.requestDisallowInterceptTouchEvent(true);
                    this.sX = event.getRawX();
                    this.sY = event.getRawY();
                    return true;
                }
                boolean suitable;
                float distanceX = event.getRawX() - this.sX;
                float distanceY = event.getRawY() - this.sY;
                float angle = (float) Math.toDegrees(Math.atan((double) Math.abs(distanceY / distanceX)));
                boolean z = false;
                if (this.mDragEdge == DragEdge.Right) {
                    suitable = (status == Status.Open && distanceX > 0.0f) || (status == Status.Close && distanceX < 0.0f);
                    suitable = suitable || status == Status.Middle;
                    if (angle > 30.0f || !suitable) {
                        z = true;
                    }
                }
                if (this.mDragEdge == DragEdge.Left) {
                    suitable = (status == Status.Open && distanceX < 0.0f) || (status == Status.Close && distanceX > 0.0f);
                    suitable = suitable || status == Status.Middle;
                    if (angle > 30.0f || !suitable) {
                        z = true;
                    }
                }
                if (this.mDragEdge == DragEdge.Top) {
                    suitable = (status == Status.Open && distanceY < 0.0f) || (status == Status.Close && distanceY > 0.0f);
                    suitable = suitable || status == Status.Middle;
                    if (angle < 60.0f || !suitable) {
                        z = true;
                    }
                }
                if (this.mDragEdge == DragEdge.Bottom) {
                    suitable = (status == Status.Open && distanceY > 0.0f) || (status == Status.Close && distanceY < 0.0f);
                    suitable = suitable || status == Status.Middle;
                    if (angle < 60.0f || !suitable) {
                        z = true;
                    }
                }
                if (z) {
                    parent.requestDisallowInterceptTouchEvent(false);
                    return false;
                }
                if (touching != null) {
                    touching.setPressed(false);
                }
                parent.requestDisallowInterceptTouchEvent(true);
                this.mDragHelper.processTouchEvent(event);
                break;
            default:
                parent.requestDisallowInterceptTouchEvent(true);
                this.mDragHelper.processTouchEvent(event);
                break;
        }
        return true;
    }

    private boolean isEnabledInAdapterView() {
        AdapterView adapterView = getAdapterView();
        if (adapterView == null) {
            return true;
        }
        Adapter adapter = adapterView.getAdapter();
        if (adapter == null) {
            return true;
        }
        int p = adapterView.getPositionForView(this);
        if (adapter instanceof BaseAdapter) {
            return ((BaseAdapter) adapter).isEnabled(p);
        }
        return adapter instanceof ListAdapter ? ((ListAdapter) adapter).isEnabled(p) : true;
    }

    public void setSwipeEnabled(boolean enabled) {
        this.mSwipeEnabled = enabled;
    }

    public boolean isSwipeEnabled() {
        return this.mSwipeEnabled;
    }

    private boolean insideAdapterView() {
        return getAdapterView() != null;
    }

    private AdapterView getAdapterView() {
        for (ViewParent t = getParent(); t != null; t = t.getParent()) {
            if (t instanceof AdapterView) {
                return (AdapterView) t;
            }
        }
        return null;
    }

    private void performAdapterViewItemClick(MotionEvent e) {
        ViewParent t = getParent();
        while (t != null) {
            if (t instanceof AdapterView) {
                AdapterView view = (AdapterView) t;
                int p = view.getPositionForView(this);
                if (p != -1 && view.performItemClick(view.getChildAt(p), p, view.getAdapter().getItemId(p))) {
                    return;
                }
            } else if ((t instanceof View) && ((View) t).performClick()) {
                return;
            }
            t = t.getParent();
        }
    }

    public void setDragEdge(DragEdge dragEdge) {
        this.mDragEdge = dragEdge;
        requestLayout();
    }

    public void setDragDistance(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("Drag distance can not be < 0");
        }
        this.mDragDistance = dp2px((float) max);
        requestLayout();
    }

    public void setShowMode(ShowMode mode) {
        this.mShowMode = mode;
        requestLayout();
    }

    public DragEdge getDragEdge() {
        return this.mDragEdge;
    }

    public int getDragDistance() {
        return this.mDragDistance;
    }

    public ShowMode getShowMode() {
        return this.mShowMode;
    }

    public ViewGroup getSurfaceView() {
        return (ViewGroup) getChildAt(1);
    }

    public ViewGroup getBottomView() {
        return (ViewGroup) getChildAt(0);
    }

    public Status getOpenStatus() {
        int surfaceLeft = getSurfaceView().getLeft();
        int surfaceTop = getSurfaceView().getTop();
        if (surfaceLeft == getPaddingLeft() && surfaceTop == getPaddingTop()) {
            return Status.Close;
        }
        return (surfaceLeft == getPaddingLeft() - this.mDragDistance || surfaceLeft == getPaddingLeft() + this.mDragDistance || surfaceTop == getPaddingTop() - this.mDragDistance || surfaceTop == getPaddingTop() + this.mDragDistance) ? Status.Open : Status.Middle;
    }

    private void processSurfaceRelease(float xvel, float yvel) {
        if (xvel == 0.0f && getOpenStatus() == Status.Middle) {
            close();
        }
        if (this.mDragEdge == DragEdge.Left || this.mDragEdge == DragEdge.Right) {
            if (xvel > 0.0f) {
                if (this.mDragEdge == DragEdge.Left) {
                    open();
                } else {
                    close();
                }
            }
            if (xvel >= 0.0f) {
                return;
            }
            if (this.mDragEdge == DragEdge.Left) {
                close();
                return;
            } else {
                open();
                return;
            }
        }
        if (yvel > 0.0f) {
            if (this.mDragEdge == DragEdge.Top) {
                open();
            } else {
                close();
            }
        }
        if (yvel >= 0.0f) {
            return;
        }
        if (this.mDragEdge == DragEdge.Top) {
            close();
        } else {
            open();
        }
    }

    private void processBottomPullOutRelease(float xvel, float yvel) {
        if (xvel == 0.0f && getOpenStatus() == Status.Middle) {
            close();
        }
        if (this.mDragEdge == DragEdge.Left || this.mDragEdge == DragEdge.Right) {
            if (xvel > 0.0f) {
                if (this.mDragEdge == DragEdge.Left) {
                    open();
                } else {
                    close();
                }
            }
            if (xvel >= 0.0f) {
                return;
            }
            if (this.mDragEdge == DragEdge.Left) {
                close();
                return;
            } else {
                open();
                return;
            }
        }
        if (yvel > 0.0f) {
            if (this.mDragEdge == DragEdge.Top) {
                open();
            } else {
                close();
            }
        }
        if (yvel >= 0.0f) {
            return;
        }
        if (this.mDragEdge == DragEdge.Top) {
            close();
        } else {
            open();
        }
    }

    private void processBottomLayDownMode(float xvel, float yvel) {
        if (xvel == 0.0f && getOpenStatus() == Status.Middle) {
            close();
        }
        int l = getPaddingLeft();
        int t = getPaddingTop();
        if (xvel < 0.0f && this.mDragEdge == DragEdge.Right) {
            l -= this.mDragDistance;
        }
        if (xvel > 0.0f && this.mDragEdge == DragEdge.Left) {
            l += this.mDragDistance;
        }
        if (yvel > 0.0f && this.mDragEdge == DragEdge.Top) {
            t += this.mDragDistance;
        }
        if (yvel < 0.0f && this.mDragEdge == DragEdge.Bottom) {
            t -= this.mDragDistance;
        }
        this.mDragHelper.smoothSlideViewTo(getSurfaceView(), l, t);
        invalidate();
    }

    public void open() {
        open(true, true);
    }

    public void open(boolean smooth) {
        open(smooth, true);
    }

    public void open(boolean smooth, boolean notify) {
        ViewGroup surface = getSurfaceView();
        ViewGroup bottom = getBottomView();
        Rect rect = computeSurfaceLayoutArea(true);
        if (smooth) {
            this.mDragHelper.smoothSlideViewTo(getSurfaceView(), rect.left, rect.top);
        } else {
            int dx = rect.left - surface.getLeft();
            int dy = rect.top - surface.getTop();
            surface.layout(rect.left, rect.top, rect.right, rect.bottom);
            if (getShowMode() == ShowMode.PullOut) {
                Rect bRect = computeBottomLayoutAreaViaSurface(ShowMode.PullOut, rect);
                bottom.layout(bRect.left, bRect.top, bRect.right, bRect.bottom);
            }
            if (notify) {
                dispatchRevealEvent(rect.left, rect.top, rect.right, rect.bottom);
                dispatchSwipeEvent(rect.left, rect.top, dx, dy);
            } else {
                safeBottomView();
            }
        }
        invalidate();
    }

    public void close() {
        close(true, true);
    }

    public void close(boolean smooth) {
        close(smooth, true);
    }

    public void close(boolean smooth, boolean notify) {
        ViewGroup surface = getSurfaceView();
        if (smooth) {
            this.mDragHelper.smoothSlideViewTo(getSurfaceView(), getPaddingLeft(), getPaddingTop());
        } else {
            Rect rect = computeSurfaceLayoutArea(false);
            int dx = rect.left - surface.getLeft();
            int dy = rect.top - surface.getTop();
            surface.layout(rect.left, rect.top, rect.right, rect.bottom);
            if (notify) {
                dispatchRevealEvent(rect.left, rect.top, rect.right, rect.bottom);
                dispatchSwipeEvent(rect.left, rect.top, dx, dy);
            } else {
                safeBottomView();
            }
        }
        invalidate();
    }

    public void toggle() {
        toggle(true);
    }

    public void toggle(boolean smooth) {
        if (getOpenStatus() == Status.Open) {
            close(smooth);
        } else if (getOpenStatus() == Status.Close) {
            open(smooth);
        }
    }

    private Rect computeSurfaceLayoutArea(boolean open) {
        int l = getPaddingLeft();
        int t = getPaddingTop();
        if (open) {
            if (this.mDragEdge == DragEdge.Left) {
                l = getPaddingLeft() + this.mDragDistance;
            } else if (this.mDragEdge == DragEdge.Right) {
                l = getPaddingLeft() - this.mDragDistance;
            } else {
                t = this.mDragEdge == DragEdge.Top ? getPaddingTop() + this.mDragDistance : getPaddingTop() - this.mDragDistance;
            }
        }
        return new Rect(l, t, getMeasuredWidth() + l, getMeasuredHeight() + t);
    }

    private Rect computeBottomLayoutAreaViaSurface(ShowMode mode, Rect surfaceArea) {
        Rect rect = surfaceArea;
        int bl = rect.left;
        int bt = rect.top;
        int br = rect.right;
        int bb = rect.bottom;
        if (mode == ShowMode.PullOut) {
            if (this.mDragEdge == DragEdge.Left) {
                bl = rect.left - this.mDragDistance;
            } else if (this.mDragEdge == DragEdge.Right) {
                bl = rect.right;
            } else {
                bt = this.mDragEdge == DragEdge.Top ? rect.top - this.mDragDistance : rect.bottom;
            }
            if (this.mDragEdge == DragEdge.Left || this.mDragEdge == DragEdge.Right) {
                bb = rect.bottom;
                br = bl + getBottomView().getMeasuredWidth();
            } else {
                bb = bt + getBottomView().getMeasuredHeight();
                br = rect.right;
            }
        } else if (mode == ShowMode.LayDown) {
            if (this.mDragEdge == DragEdge.Left) {
                br = bl + this.mDragDistance;
            } else if (this.mDragEdge == DragEdge.Right) {
                bl = br - this.mDragDistance;
            } else if (this.mDragEdge == DragEdge.Top) {
                bb = bt + this.mDragDistance;
            } else {
                bt = bb - this.mDragDistance;
            }
        }
        return new Rect(bl, bt, br, bb);
    }

    private Rect computeBottomLayDown(DragEdge dragEdge) {
        int br;
        int bb;
        int bl = getPaddingLeft();
        int bt = getPaddingTop();
        if (dragEdge == DragEdge.Right) {
            bl = getMeasuredWidth() - this.mDragDistance;
        } else if (dragEdge == DragEdge.Bottom) {
            bt = getMeasuredHeight() - this.mDragDistance;
        }
        if (dragEdge == DragEdge.Left || dragEdge == DragEdge.Right) {
            br = bl + this.mDragDistance;
            bb = bt + getMeasuredHeight();
        } else {
            br = bl + getMeasuredWidth();
            bb = bt + this.mDragDistance;
        }
        return new Rect(bl, bt, br, bb);
    }

    public void setOnDoubleClickListener(DoubleClickListener doubleClickListener) {
        this.mDoubleClickListener = doubleClickListener;
    }

    private int dp2px(float dp) {
        return (int) ((getContext().getResources().getDisplayMetrics().density * dp) + 0.5f);
    }
}
