package net.oneplus.weather.widget.swipelistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.List;

public class SwipeListView extends ListView {
    public static final boolean DEBUG = false;
    public static final int SWIPE_ACTION_CHOICE = 2;
    public static final int SWIPE_ACTION_DISMISS = 1;
    public static final int SWIPE_ACTION_NONE = 3;
    public static final int SWIPE_ACTION_REVEAL = 0;
    public static final String SWIPE_DEFAULT_BACK_VIEW = "swipelist_backview";
    public static final String SWIPE_DEFAULT_FRONT_VIEW = "swipelist_frontview";
    public static final int SWIPE_MODE_BOTH = 1;
    public static final int SWIPE_MODE_DEFAULT = -1;
    public static final int SWIPE_MODE_LEFT = 3;
    public static final int SWIPE_MODE_NONE = 0;
    public static final int SWIPE_MODE_RIGHT = 2;
    public static final String TAG = "SwipeListView";
    private static final int TOUCH_STATE_REST = 0;
    private static final int TOUCH_STATE_SCROLLING_X = 1;
    private static final int TOUCH_STATE_SCROLLING_Y = 2;
    private float lastMotionX;
    private float lastMotionY;
    int swipeBackView;
    int swipeFrontView;
    private SwipeListViewListener swipeListViewListener;
    private SwipeListViewTouchListener touchListener;
    private int touchSlop;
    private int touchState;

    public SwipeListView(Context context, int swipeBackView, int swipeFrontView) {
        super(context);
        this.touchState = 0;
        this.swipeFrontView = 0;
        this.swipeBackView = 0;
        this.swipeFrontView = swipeFrontView;
        this.swipeBackView = swipeBackView;
        init(null);
    }

    public SwipeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.touchState = 0;
        this.swipeFrontView = 0;
        this.swipeBackView = 0;
        init(attrs);
    }

    public SwipeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.touchState = 0;
        this.swipeFrontView = 0;
        this.swipeBackView = 0;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        int swipeMode = TOUCH_STATE_SCROLLING_X;
        boolean swipeOpenOnLongPress = true;
        boolean swipeCloseAllItemsWhenMoveList = true;
        long swipeAnimationTime = 0;
        float swipeOffsetLeft = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        float swipeOffsetRight = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        int swipeDrawableChecked = TOUCH_STATE_REST;
        int swipeDrawableUnchecked = TOUCH_STATE_REST;
        int swipeActionLeft = TOUCH_STATE_REST;
        int swipeActionRight = TOUCH_STATE_REST;
        if (attrs != null) {
            TypedArray styled = getContext().obtainStyledAttributes(attrs, R.styleable.SwipeListView);
            swipeMode = styled.getInt(8, 1);
            swipeActionLeft = styled.getInt(0, 0);
            swipeActionRight = styled.getInt(1, 0);
            swipeOffsetLeft = styled.getDimension(9, 0.0f);
            swipeOffsetRight = styled.getDimension(10, 0.0f);
            swipeOpenOnLongPress = styled.getBoolean(11, true);
            swipeAnimationTime = (long) styled.getInteger(2, 0);
            swipeCloseAllItemsWhenMoveList = styled.getBoolean(4, true);
            swipeDrawableChecked = styled.getResourceId(5, 0);
            swipeDrawableUnchecked = styled.getResourceId(6, 0);
            this.swipeFrontView = styled.getResourceId(7, 0);
            this.swipeBackView = styled.getResourceId(3, 0);
            styled.recycle();
        }
        if (this.swipeFrontView == 0 || this.swipeBackView == 0) {
            this.swipeFrontView = getContext().getResources().getIdentifier(SWIPE_DEFAULT_FRONT_VIEW, "id", getContext().getPackageName());
            this.swipeBackView = getContext().getResources().getIdentifier(SWIPE_DEFAULT_BACK_VIEW, "id", getContext().getPackageName());
            if (this.swipeFrontView == 0 || this.swipeBackView == 0) {
                Object[] objArr = new Object[2];
                objArr[0] = SWIPE_DEFAULT_FRONT_VIEW;
                objArr[1] = SWIPE_DEFAULT_BACK_VIEW;
                throw new RuntimeException(String.format("You forgot the attributes swipeFrontView or swipeBackView. You can add this attributes or use '%s' and '%s' identifiers", objArr));
            }
        }
        this.touchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(getContext()));
        this.touchListener = new SwipeListViewTouchListener(this, this.swipeFrontView, this.swipeBackView);
        if (swipeAnimationTime > 0) {
            this.touchListener.setAnimationTime(swipeAnimationTime);
        }
        this.touchListener.setRightOffset(swipeOffsetRight);
        this.touchListener.setLeftOffset(swipeOffsetLeft);
        this.touchListener.setSwipeActionLeft(swipeActionLeft);
        this.touchListener.setSwipeActionRight(swipeActionRight);
        this.touchListener.setSwipeMode(swipeMode);
        this.touchListener.setSwipeClosesAllItemsWhenListMoves(swipeCloseAllItemsWhenMoveList);
        this.touchListener.setSwipeOpenOnLongPress(swipeOpenOnLongPress);
        this.touchListener.setSwipeDrawableChecked(swipeDrawableChecked);
        this.touchListener.setSwipeDrawableUnchecked(swipeDrawableUnchecked);
        setOnTouchListener(this.touchListener);
        setOnScrollListener(this.touchListener.makeScrollListener());
    }

    public void recycle(View convertView, int position) {
        this.touchListener.reloadChoiceStateInView(convertView.findViewById(this.swipeFrontView), position);
        this.touchListener.reloadSwipeStateInView(convertView.findViewById(this.swipeFrontView), position);
        for (int j = TOUCH_STATE_REST; j < ((ViewGroup) convertView).getChildCount(); j++) {
            ((ViewGroup) convertView).getChildAt(j).setPressed(DEBUG);
        }
    }

    public boolean isChecked(int position) {
        return this.touchListener.isChecked(position);
    }

    public List<Integer> getPositionsSelected() {
        return this.touchListener.getPositionsSelected();
    }

    public int getCountSelected() {
        return this.touchListener.getCountSelected();
    }

    public void unselectedChoiceStates() {
        this.touchListener.unselectedChoiceStates();
    }

    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        this.touchListener.resetItems();
        if (adapter != null) {
            adapter.registerDataSetObserver(new DataSetObserver() {
                public void onChanged() {
                    super.onChanged();
                    SwipeListView.this.onListChanged();
                    SwipeListView.this.touchListener.resetItems();
                }
            });
        }
    }

    public void dismiss(int position) {
        int height = this.touchListener.dismiss(position);
        if (height > 0) {
            this.touchListener.handlerPendingDismisses(height);
            return;
        }
        onDismiss(new int[]{position});
        this.touchListener.resetPendingDismisses();
    }

    public void dismissSelected() {
        List<Integer> list = this.touchListener.getPositionsSelected();
        int[] dismissPositions = new int[list.size()];
        int height = TOUCH_STATE_REST;
        for (int i = TOUCH_STATE_REST; i < list.size(); i++) {
            int position = ((Integer) list.get(i)).intValue();
            dismissPositions[i] = position;
            int auxHeight = this.touchListener.dismiss(position);
            if (auxHeight > 0) {
                height = auxHeight;
            }
        }
        if (height > 0) {
            this.touchListener.handlerPendingDismisses(height);
        } else {
            onDismiss(dismissPositions);
            this.touchListener.resetPendingDismisses();
        }
        this.touchListener.returnOldActions();
    }

    public void openAnimate(int position) {
        this.touchListener.openAnimate(position);
    }

    public void closeAnimate(int position) {
        this.touchListener.closeAnimate(position);
    }

    protected void onDismiss(int[] reverseSortedPositions) {
        if (this.swipeListViewListener != null) {
            this.swipeListViewListener.onDismiss(reverseSortedPositions);
        }
    }

    protected void onStartOpen(int position, int action, boolean right) {
        if (this.swipeListViewListener != null && position != -1) {
            this.swipeListViewListener.onStartOpen(position, action, right);
        }
    }

    protected void onStartClose(int position, boolean right) {
        if (this.swipeListViewListener != null && position != -1) {
            this.swipeListViewListener.onStartClose(position, right);
        }
    }

    protected void onClickFrontView(int position) {
        if (this.swipeListViewListener != null && position != -1) {
            this.swipeListViewListener.onClickFrontView(position);
        }
    }

    protected void onClickBackView(int position) {
        if (this.swipeListViewListener != null && position != -1) {
            this.swipeListViewListener.onClickBackView(position);
        }
    }

    protected void onOpened(int position, boolean toRight) {
        if (this.swipeListViewListener != null && position != -1) {
            this.swipeListViewListener.onOpened(position, toRight);
        }
    }

    protected void onClosed(int position, boolean fromRight) {
        if (this.swipeListViewListener != null && position != -1) {
            this.swipeListViewListener.onClosed(position, fromRight);
        }
    }

    protected void onChoiceChanged(int position, boolean selected) {
        if (this.swipeListViewListener != null && position != -1) {
            this.swipeListViewListener.onChoiceChanged(position, selected);
        }
    }

    protected void onChoiceStarted() {
        if (this.swipeListViewListener != null) {
            this.swipeListViewListener.onChoiceStarted();
        }
    }

    protected void onChoiceEnded() {
        if (this.swipeListViewListener != null) {
            this.swipeListViewListener.onChoiceEnded();
        }
    }

    protected void onFirstListItem() {
        if (this.swipeListViewListener != null) {
            this.swipeListViewListener.onFirstListItem();
        }
    }

    protected void onLastListItem() {
        if (this.swipeListViewListener != null) {
            this.swipeListViewListener.onLastListItem();
        }
    }

    protected void onListChanged() {
        if (this.swipeListViewListener != null) {
            this.swipeListViewListener.onListChanged();
        }
    }

    protected void onMove(int position, float x) {
        if (this.swipeListViewListener != null && position != -1) {
            this.swipeListViewListener.onMove(position, x);
        }
    }

    protected int changeSwipeMode(int position) {
        return (this.swipeListViewListener == null || position == -1) ? SWIPE_MODE_DEFAULT : this.swipeListViewListener.onChangeSwipeMode(position);
    }

    public void setSwipeListViewListener(SwipeListViewListener swipeListViewListener) {
        this.swipeListViewListener = swipeListViewListener;
    }

    public void resetScrolling() {
        this.touchState = 0;
    }

    public void setOffsetRight(float offsetRight) {
        this.touchListener.setRightOffset(offsetRight);
    }

    public void setOffsetLeft(float offsetLeft) {
        this.touchListener.setLeftOffset(offsetLeft);
    }

    public void setSwipeCloseAllItemsWhenMoveList(boolean swipeCloseAllItemsWhenMoveList) {
        this.touchListener.setSwipeClosesAllItemsWhenListMoves(swipeCloseAllItemsWhenMoveList);
    }

    public void setSwipeOpenOnLongPress(boolean swipeOpenOnLongPress) {
        this.touchListener.setSwipeOpenOnLongPress(swipeOpenOnLongPress);
    }

    public void setSwipeMode(int swipeMode) {
        this.touchListener.setSwipeMode(swipeMode);
    }

    public int getSwipeActionLeft() {
        return this.touchListener.getSwipeActionLeft();
    }

    public void setSwipeActionLeft(int swipeActionLeft) {
        this.touchListener.setSwipeActionLeft(swipeActionLeft);
    }

    public int getSwipeActionRight() {
        return this.touchListener.getSwipeActionRight();
    }

    public void setSwipeActionRight(int swipeActionRight) {
        this.touchListener.setSwipeActionRight(swipeActionRight);
    }

    public void setAnimationTime(long animationTime) {
        this.touchListener.setAnimationTime(animationTime);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        float x = ev.getX();
        float y = ev.getY();
        if (!this.touchListener.isSwipeEnabled()) {
            this.touchListener.onTouch(this, ev);
        }
        if (isEnabled() && this.touchListener.isSwipeEnabled()) {
            if (this.touchState == 1) {
                return this.touchListener.onTouch(this, ev);
            }
            switch (action) {
                case TOUCH_STATE_REST:
                    super.onInterceptTouchEvent(ev);
                    this.touchListener.onTouch(this, ev);
                    this.touchState = 0;
                    this.lastMotionX = x;
                    this.lastMotionY = y;
                    return false;
                case TOUCH_STATE_SCROLLING_X:
                    this.touchListener.onTouch(this, ev);
                    return this.touchState == 2;
                case TOUCH_STATE_SCROLLING_Y:
                    checkInMoving(x, y);
                    return this.touchState == 2;
                case SWIPE_MODE_LEFT:
                    this.touchState = 0;
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void checkInMoving(float x, float y) {
        boolean xMoved;
        boolean yMoved = DEBUG;
        int xDiff = (int) Math.abs(x - this.lastMotionX);
        int yDiff = (int) Math.abs(y - this.lastMotionY);
        int touchSlop = this.touchSlop;
        if (xDiff > touchSlop) {
            xMoved = true;
        } else {
            xMoved = false;
        }
        if (yDiff > touchSlop) {
            yMoved = true;
        }
        if (xMoved) {
            this.touchState = 1;
            this.lastMotionX = x;
            this.lastMotionY = y;
        }
        if (yMoved) {
            this.touchState = 2;
            this.lastMotionX = x;
            this.lastMotionY = y;
        }
    }

    public void closeOpenedItems() {
        this.touchListener.closeOpenedItems();
    }
}
