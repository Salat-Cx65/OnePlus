package com.oneplus.lib.widget.listview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.android.volley.DefaultRetryPolicy;
import com.oneplus.commonctrl.R;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class OPListView extends ListView {
    static final String TAG = "OPListView";
    private boolean mAnimRunning;
    private ArrayList<ObjectAnimator> mAnimatorList;
    AnimatorUpdateListener mAnimatorUpdateListener;
    private DecelerateInterpolator mDecelerateInterpolator;
    AnimatorSet mDelAniSet;
    private boolean mDelAnimationFlag;
    private ArrayList<Integer> mDelOriViewTopList;
    private ArrayList<Integer> mDelPosList;
    private ArrayList<View> mDelViewList;
    private DeleteAnimationListener mDeleteAnimationListener;
    private boolean mDisableTouchEvent;
    private Drawable mDivider;
    private IOPDividerController mDividerController;
    private int mDividerHeight;
    private boolean mFooterDividersEnabled;
    private boolean mHeaderDividersEnabled;
    private boolean mInDeleteAnimation;
    private boolean mIsClipToPadding;
    private boolean mIsDisableAnimation;
    private ArrayList<View> mNowViewList;
    private int mOriBelowLeftCount;
    private int mOriCurDeleteCount;
    private int mOriCurLeftCount;
    private int mOriFirstPosition;
    private boolean mOriLastPage;
    private int mOriUpperDeleteCount;
    Rect mTempRect;

    public static interface DeleteAnimationListener {
        void onAnimationEnd();

        void onAnimationStart();

        void onAnimationUpdate();
    }

    public OPListView(Context context) {
        this(context, null);
    }

    public OPListView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842868);
    }

    public OPListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public OPListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mDividerHeight = 1;
        this.mIsDisableAnimation = true;
        this.mDelViewList = null;
        this.mDelPosList = null;
        this.mNowViewList = null;
        this.mDelOriViewTopList = null;
        this.mDelAniSet = null;
        this.mDecelerateInterpolator = new DecelerateInterpolator(1.2f);
        this.mAnimatorList = new ArrayList();
        this.mTempRect = new Rect();
        this.mHeaderDividersEnabled = true;
        this.mFooterDividersEnabled = true;
        this.mIsClipToPadding = true;
        this.mDividerController = null;
        this.mAnimatorUpdateListener = new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                OPListView.this.invalidate();
            }
        };
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OPListView, R.attr.OPListViewStyle, 0);
        Drawable d = a.getDrawable(R.styleable.OPListView_android_divider);
        Drawable bg = a.getDrawable(R.styleable.OPListView_android_background);
        if (d != null) {
            setDivider(d);
        }
        if (bg != null) {
            setBackground(bg);
        }
        this.mDividerHeight = getResources().getDimensionPixelSize(R.dimen.listview_divider_height);
        setOverScrollMode(0);
        super.setDivider(new ColorDrawable(17170445));
        setDividerHeight(this.mDividerHeight);
        setFooterDividersEnabled(false);
    }

    public void setHeaderDividersEnabled(boolean headerDividersEnabled) {
        this.mHeaderDividersEnabled = headerDividersEnabled;
    }

    public void setFooterDividersEnabled(boolean footerDividersEnabled) {
        this.mFooterDividersEnabled = footerDividersEnabled;
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Drawable overscrollHeader = getOverscrollHeader();
        Drawable overscrollFooter = getOverscrollFooter();
        boolean drawOverscrollHeader = overscrollHeader != null;
        boolean drawOverscrollFooter = overscrollFooter != null;
        boolean drawDividers = getDivider() != null;
        if (drawDividers || drawOverscrollHeader || drawOverscrollFooter) {
            Rect bounds = this.mTempRect;
            bounds.left = getPaddingLeft();
            bounds.right = (getRight() - getLeft()) - getPaddingRight();
            int count = getChildCount();
            int headerCount = getHeaderViewsCount();
            int footerLimit = getCount() - getFooterViewsCount();
            boolean headerDividers = this.mHeaderDividersEnabled;
            boolean footerDividers = this.mFooterDividersEnabled;
            int first = getFirstVisiblePosition();
            ListAdapter adapter = getAdapter();
            int effectivePaddingTop = 0;
            int effectivePaddingBottom = 0;
            if (isClipToPadding()) {
                effectivePaddingTop = getListPaddingTop();
                effectivePaddingBottom = getListPaddingBottom();
            }
            int listBottom = ((getBottom() - getTop()) - effectivePaddingBottom) + getScrollY();
            int scrollY;
            int i;
            int itemIndex;
            boolean isHeader;
            boolean isFooter;
            if (isStackFromBottom()) {
                scrollY = getScrollY();
                int start = drawOverscrollHeader ? 1 : 0;
                i = start;
                while (i < count) {
                    itemIndex = first + i;
                    isHeader = itemIndex < headerCount;
                    isFooter = itemIndex >= footerLimit;
                    if (headerDividers || !isHeader) {
                        if (footerDividers || !isFooter) {
                            int top = getChildAt(i).getTop();
                            if (drawDividers && shouldDrawDivider(i) && top > effectivePaddingTop) {
                                boolean isFirstItem = i == start;
                                int previousIndex = itemIndex - 1;
                                if (headerDividers || (!isHeader && previousIndex >= headerCount)) {
                                    if (isFirstItem || footerDividers || (!isFooter && previousIndex < footerLimit)) {
                                        bounds.top = top - getDividerHeight();
                                        bounds.bottom = top;
                                        drawDivider(canvas, bounds, i - 1);
                                    }
                                }
                            }
                        }
                    }
                    i++;
                }
                if (count > 0 && scrollY > 0 && drawDividers) {
                    bounds.top = listBottom;
                    bounds.bottom = getDividerHeight() + listBottom;
                    drawDivider(canvas, bounds, -1);
                }
            } else {
                scrollY = getScrollY();
                if (count > 0 && scrollY < 0 && drawDividers) {
                    bounds.bottom = 0;
                    bounds.top = -getDividerHeight();
                    drawDivider(canvas, bounds, -1);
                }
                i = 0;
                while (i < count) {
                    itemIndex = first + i;
                    isHeader = itemIndex < headerCount;
                    isFooter = itemIndex >= footerLimit;
                    if (headerDividers || !isHeader) {
                        if (footerDividers || !isFooter) {
                            View child = getChildAt(i);
                            int bottom = child.getBottom();
                            boolean isLastItem = i == count + -1;
                            if (drawDividers && shouldDrawDivider(i) && child.getHeight() > 0 && bottom < listBottom) {
                                if (!drawOverscrollFooter || !isLastItem) {
                                    int nextIndex = itemIndex + 1;
                                    if (headerDividers || (!isHeader && nextIndex >= headerCount)) {
                                        if (isLastItem || footerDividers || (!isFooter && nextIndex < footerLimit)) {
                                            int translationOffsetY = (int) child.getTranslationY();
                                            bounds.top = bottom + translationOffsetY;
                                            bounds.bottom = (getDividerHeight() + bottom) + translationOffsetY;
                                            drawDivider(canvas, bounds, i);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    i++;
                }
            }
        }
        if (this.mDelAnimationFlag) {
            this.mDelAnimationFlag = false;
            startDelDropAnimation();
        }
    }

    public Drawable getDivider() {
        return this.mDivider;
    }

    public void setDivider(Drawable divider) {
        this.mDivider = divider;
        requestLayout();
        invalidate();
    }

    public int getDividerHeight() {
        return this.mDividerHeight;
    }

    private boolean isClipToPadding() {
        return this.mIsClipToPadding;
    }

    public void setClipToPadding(boolean clipToPadding) {
        super.setClipToPadding(clipToPadding);
        this.mIsClipToPadding = clipToPadding;
    }

    void drawDivider(Canvas canvas, Rect bounds, int childIndex) {
        Drawable divider = getDivider();
        int dividerType = getDividerType(childIndex + getFirstVisiblePosition());
        if (this.mDividerController != null) {
            if (dividerType == 1) {
                bounds.left = 0;
                bounds.right = getWidth();
            } else if (dividerType == 2) {
                bounds.left = 100;
                bounds.right = getWidth() - 32;
            }
        }
        divider.setBounds(bounds);
        divider.draw(canvas);
    }

    public void setDividerController(IOPDividerController dividerController) {
        this.mDividerController = dividerController;
    }

    private int getDividerType(int position) {
        return this.mDividerController == null ? -1 : this.mDividerController.getDividerType(position);
    }

    private boolean shouldDrawDivider(int childIndex) {
        return this.mDividerController == null || (this.mDividerController != null && getDividerType(childIndex + getFirstVisiblePosition()) > 0);
    }

    private ObjectAnimator getAnimator(int index, View child, float startValue) {
        if (index >= this.mAnimatorList.size()) {
            PropertyValuesHolder y = PropertyValuesHolder.ofFloat("y", new float[]{startValue, (float) child.getTop()});
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(child, new PropertyValuesHolder[]{y});
            this.mAnimatorList.add(animator);
            return animator;
        }
        animator = this.mAnimatorList.get(index);
        animator.getValues()[0].setFloatValues(new float[]{startValue, (float) child.getTop()});
        animator.setTarget(child);
        return animator;
    }

    public void setDeleteAnimationListener(DeleteAnimationListener listener) {
        this.mDeleteAnimationListener = listener;
    }

    public void setDelPositionsList(ArrayList<Integer> deleteList) {
        if (deleteList == null) {
            this.mDisableTouchEvent = false;
            throw new InvalidParameterException("The input parameter d is null!");
        } else if (this.mAnimRunning) {
            this.mDisableTouchEvent = false;
        } else if (isDeleteAnimationEnabled()) {
            int listLength = deleteList.size();
            if (listLength == 0) {
                this.mDisableTouchEvent = false;
                return;
            }
            int i;
            this.mAnimRunning = true;
            if (this.mDeleteAnimationListener != null) {
                this.mDeleteAnimationListener.onAnimationStart();
            }
            this.mInDeleteAnimation = true;
            this.mOriFirstPosition = getFirstVisiblePosition();
            int childCount = getChildCount();
            if (this.mOriFirstPosition + childCount == getAdapter().getCount() + listLength) {
                this.mOriLastPage = true;
            } else {
                this.mOriLastPage = false;
            }
            this.mOriUpperDeleteCount = 0;
            this.mOriCurDeleteCount = 0;
            this.mOriCurLeftCount = 0;
            this.mOriBelowLeftCount = 0;
            if (this.mDelOriViewTopList == null) {
                this.mDelOriViewTopList = new ArrayList();
            } else {
                this.mDelOriViewTopList.clear();
            }
            if (this.mDelViewList == null) {
                this.mDelViewList = new ArrayList();
            } else {
                this.mDelViewList.clear();
            }
            if (this.mDelPosList == null) {
                this.mDelPosList = new ArrayList();
            } else {
                this.mDelPosList.clear();
            }
            int belowDeleteCount = 0;
            for (i = 0; i < listLength; i++) {
                int delPos = ((Integer) deleteList.get(i)).intValue();
                if (delPos < this.mOriFirstPosition) {
                    this.mOriUpperDeleteCount++;
                } else if (delPos < this.mOriFirstPosition + childCount) {
                    this.mDelPosList.add(Integer.valueOf(delPos));
                    this.mDelViewList.add(getChildAt(delPos - this.mOriFirstPosition));
                    this.mOriCurDeleteCount++;
                } else {
                    belowDeleteCount++;
                }
            }
            boolean isDel = false;
            if (this.mOriUpperDeleteCount > 0 || this.mDelPosList.size() > 0) {
                isDel = true;
            }
            if (isDel) {
                int size = this.mDelPosList.size();
                for (i = 0; i < childCount; i++) {
                    View child;
                    if (size > 0) {
                        if (!this.mDelPosList.contains(Integer.valueOf(this.mOriFirstPosition + i))) {
                            child = getChildAt(i);
                            if (child != null) {
                                this.mDelOriViewTopList.add(Integer.valueOf(child.getTop()));
                            }
                        }
                    } else {
                        child = getChildAt(i);
                        if (child != null) {
                            this.mDelOriViewTopList.add(Integer.valueOf(child.getTop()));
                        }
                    }
                }
                this.mOriCurLeftCount = getChildCount() - this.mOriCurDeleteCount;
                this.mOriBelowLeftCount = (((getAdapter().getCount() + listLength) - getLastVisiblePosition()) - 1) - belowDeleteCount;
                startDelGoneAnimation();
                return;
            }
            this.mAnimRunning = false;
            this.mInDeleteAnimation = false;
            this.mDisableTouchEvent = false;
            if (this.mDeleteAnimationListener != null) {
                this.mDeleteAnimationListener.onAnimationUpdate();
                this.mDeleteAnimationListener.onAnimationEnd();
            }
        } else {
            if (this.mDeleteAnimationListener != null) {
                this.mDeleteAnimationListener.onAnimationUpdate();
                this.mDeleteAnimationListener.onAnimationStart();
                this.mDeleteAnimationListener.onAnimationEnd();
            }
            this.mDisableTouchEvent = false;
        }
    }

    private void startDelGoneAnimation() {
        this.mAnimRunning = true;
        int size = this.mDelViewList.size();
        if (size == 0) {
            this.mDelAnimationFlag = true;
            if (this.mDeleteAnimationListener != null) {
                this.mDeleteAnimationListener.onAnimationUpdate();
            }
            this.mDisableTouchEvent = false;
            return;
        }
        this.mDelAniSet = new AnimatorSet();
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", new float[]{1.0f, 0.0f});
        for (int i = 0; i < size; i++) {
            ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder((View) this.mDelViewList.get(i), new PropertyValuesHolder[]{pvhAlpha});
            anim.setDuration((long) 200);
            anim.setInterpolator(this.mDecelerateInterpolator);
            anim.addUpdateListener(this.mAnimatorUpdateListener);
            this.mDelAniSet.playTogether(new Animator[]{anim});
        }
        this.mDelAniSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                int size = OPListView.this.mDelViewList.size();
                for (int i = 0; i < size; i++) {
                    OPListView.this.mDelViewList.get(i).setAlpha(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                }
                if (OPListView.this.getAdapter() == null || (OPListView.this.getAdapter().getCount() != 0 && (OPListView.this.getEmptyView() == null || !OPListView.this.getAdapter().isEmpty()))) {
                    OPListView.this.mDelAnimationFlag = true;
                    if (OPListView.this.mDeleteAnimationListener != null) {
                        OPListView.this.mDeleteAnimationListener.onAnimationUpdate();
                        return;
                    }
                    return;
                }
                OPListView.this.mAnimRunning = false;
                OPListView.this.mInDeleteAnimation = false;
                OPListView.this.mDisableTouchEvent = false;
                OPListView.this.mDelPosList.clear();
                OPListView.this.mDelOriViewTopList.clear();
                OPListView.this.mDelViewList.clear();
                if (OPListView.this.mDeleteAnimationListener != null) {
                    OPListView.this.mDeleteAnimationListener.onAnimationUpdate();
                    OPListView.this.mDeleteAnimationListener.onAnimationEnd();
                }
            }
        });
        this.mDelAniSet.start();
    }

    private void startDelDropAnimation() {
        this.mDelAniSet = new AnimatorSet();
        setDelViewLocation();
        for (int i = 0; i < this.mNowViewList.size(); i++) {
            ObjectAnimator anim = getAnimator(i, this.mNowViewList.get(i), (float) ((Integer) this.mDelOriViewTopList.get(i)).intValue());
            anim.setDuration((long) 200);
            anim.setInterpolator(this.mDecelerateInterpolator);
            anim.addUpdateListener(this.mAnimatorUpdateListener);
            this.mDelAniSet.playTogether(new Animator[]{anim});
        }
        this.mDelAniSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                OPListView.this.mAnimRunning = false;
                OPListView.this.mInDeleteAnimation = false;
                OPListView.this.mDisableTouchEvent = false;
                OPListView.this.mDelPosList.clear();
                OPListView.this.mDelOriViewTopList.clear();
                OPListView.this.mDelViewList.clear();
                OPListView.this.mNowViewList.clear();
                OPListView.this.invalidate();
                if (OPListView.this.mDeleteAnimationListener != null) {
                    OPListView.this.mDeleteAnimationListener.onAnimationEnd();
                }
            }
        });
        this.mDelAniSet.start();
    }

    private void setDelViewLocation() {
        int i;
        int diff;
        int nowFirstPosition = getFirstVisiblePosition();
        int nowCurChildCount = getChildCount();
        boolean nowLastPage = false;
        if (getLastVisiblePosition() == getAdapter().getCount() - 1) {
            nowLastPage = true;
        }
        boolean nowFirstPage = false;
        if (nowFirstPosition == 0) {
            nowFirstPage = true;
        }
        int top = getTop();
        int bottom = getBottom();
        int childCount = getChildCount();
        int height = 0;
        if (this.mNowViewList == null) {
            this.mNowViewList = new ArrayList();
        } else {
            this.mNowViewList.clear();
        }
        for (i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            this.mNowViewList.add(child);
            if (i == 0 && child != null) {
                height = child.getHeight();
            }
        }
        int childIndex;
        if (this.mOriLastPage) {
            if (this.mOriUpperDeleteCount == 0) {
                if (this.mOriCurDeleteCount != 0) {
                    Log.d(TAG, "DeleteAnimation Case 14 ");
                }
            } else if (this.mOriCurDeleteCount == 0) {
                if (this.mOriUpperDeleteCount >= this.mOriCurLeftCount) {
                    Log.d(TAG, "DeleteAnimation Case 12 ");
                    this.mDelOriViewTopList.clear();
                } else {
                    Log.d(TAG, "DeleteAnimation Case 13 ");
                    for (i = 0; i < this.mOriUpperDeleteCount; i++) {
                        this.mDelOriViewTopList.remove(0);
                    }
                }
            } else if (nowFirstPage) {
                Log.d(TAG, "DeleteAnimation Case 17 ");
            } else {
                if (this.mOriUpperDeleteCount >= this.mOriCurLeftCount) {
                    Log.d(TAG, "DeleteAnimation Case 15 ");
                } else {
                    Log.d(TAG, "DeleteAnimation Case 16 ");
                }
            }
            childIndex = 1;
            while (nowCurChildCount > this.mDelOriViewTopList.size()) {
                this.mDelOriViewTopList.add(0, Integer.valueOf((-height) * childIndex));
                childIndex++;
            }
        } else if (nowLastPage) {
            if (nowFirstPage) {
                if (this.mOriCurDeleteCount == 0) {
                    Log.d(TAG, "DeleteAnimation Case 11 ");
                } else {
                    if (this.mOriUpperDeleteCount >= this.mOriCurLeftCount) {
                        Log.d(TAG, "DeleteAnimation Case 7 ");
                    } else {
                        Log.d(TAG, "DeleteAnimation Case 8 ");
                    }
                }
            } else if (this.mOriUpperDeleteCount == 0) {
                Log.d(TAG, "DeleteAnimation Case 4 ");
            } else if (this.mOriCurDeleteCount == 0) {
                if (this.mOriUpperDeleteCount >= this.mOriCurLeftCount) {
                    Log.d(TAG, "DeleteAnimation Case 9 ");
                } else {
                    Log.d(TAG, "DeleteAnimation Case 10 ");
                }
            } else {
                if (this.mOriUpperDeleteCount >= this.mOriCurLeftCount) {
                    Log.d(TAG, "DeleteAnimation Case 5 ");
                } else {
                    Log.d(TAG, "DeleteAnimation Case 6 ");
                }
            }
            for (i = 0; i < this.mOriBelowLeftCount; i++) {
                this.mDelOriViewTopList.add(Integer.valueOf(((i + 1) * height) + bottom));
            }
            diff = this.mDelOriViewTopList.size() - nowCurChildCount;
            for (i = 0; i < diff; i++) {
                this.mDelOriViewTopList.remove(0);
            }
            childIndex = 1;
            while (nowCurChildCount > this.mDelOriViewTopList.size()) {
                this.mDelOriViewTopList.add(0, Integer.valueOf((-height) * childIndex));
                childIndex++;
            }
        } else if (this.mOriUpperDeleteCount == 0) {
            Log.d(TAG, "DeleteAnimation Case 1");
        } else {
            if (this.mOriUpperDeleteCount >= this.mOriCurLeftCount) {
                Log.d(TAG, "DeleteAnimation Case 3 ");
                this.mDelOriViewTopList.clear();
            } else {
                Log.d(TAG, "DeleteAnimation Case 2 ");
                for (i = 0; i < this.mOriUpperDeleteCount; i++) {
                    this.mDelOriViewTopList.remove(0);
                }
            }
        }
        diff = this.mNowViewList.size() - this.mDelOriViewTopList.size();
        for (i = 0; i < diff; i++) {
            this.mDelOriViewTopList.add(Integer.valueOf(((i + 1) * height) + bottom));
        }
        int invertCount = 0;
        for (i = childCount - 1; i >= 0; i--) {
            if (((View) this.mNowViewList.get(i)).getTop() == ((Integer) this.mDelOriViewTopList.get(i)).intValue()) {
                this.mNowViewList.remove(i);
                this.mDelOriViewTopList.remove(i);
            } else {
                if (((Integer) this.mDelOriViewTopList.get(i)).intValue() < ((View) this.mNowViewList.get(i)).getTop()) {
                    invertCount++;
                }
            }
        }
        if (invertCount > 1) {
            ArrayList<View> tmpViewList = (ArrayList) this.mNowViewList.clone();
            ArrayList<Integer> tmpOriTopList = (ArrayList) this.mDelOriViewTopList.clone();
            this.mNowViewList.clear();
            this.mDelOriViewTopList.clear();
            for (i = 0; i < tmpViewList.size(); i++) {
                int tmpPos;
                if (i < invertCount) {
                    tmpPos = (invertCount - i) - 1;
                } else {
                    tmpPos = i;
                }
                this.mNowViewList.add(tmpViewList.get(tmpPos));
                this.mDelOriViewTopList.add(tmpOriTopList.get(tmpPos));
            }
        }
    }

    public void setDeleteAnimationEnabled(boolean enabled) {
        this.mIsDisableAnimation = enabled;
    }

    public boolean isDeleteAnimationEnabled() {
        return this.mIsDisableAnimation;
    }
}
