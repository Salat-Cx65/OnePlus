package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.google.android.gms.common.ConnectionResult;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import net.oneplus.weather.provider.CitySearchProvider;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class LinearLayoutCompat extends ViewGroup {
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface DividerMode {
    }

    public static class LayoutParams extends MarginLayoutParams {
        public int gravity;
        public float weight;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.gravity = -1;
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.LinearLayoutCompat_Layout);
            this.weight = a.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, AutoScrollHelper.RELATIVE_UNSPECIFIED);
            this.gravity = a.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.gravity = -1;
            this.weight = 0.0f;
        }

        public LayoutParams(int width, int height, float weight) {
            super(width, height);
            this.gravity = -1;
            this.weight = weight;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams p) {
            super(p);
            this.gravity = -1;
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
            this.gravity = -1;
        }

        public LayoutParams(android.support.v7.widget.LinearLayoutCompat.LayoutParams source) {
            super(source);
            this.gravity = -1;
            this.weight = source.weight;
            this.gravity = source.gravity;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public static @interface OrientationMode {
    }

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.LinearLayoutCompat, defStyleAttr, SHOW_DIVIDER_NONE);
        int index = a.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (index >= 0) {
            setOrientation(index);
        }
        index = a.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (index >= 0) {
            setGravity(index);
        }
        boolean baselineAligned = a.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!baselineAligned) {
            setBaselineAligned(baselineAligned);
        }
        this.mWeightSum = a.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = a.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = a.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(a.getDrawable(R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = a.getInt(R.styleable.LinearLayoutCompat_showDividers, SHOW_DIVIDER_NONE);
        this.mDividerPadding = a.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, SHOW_DIVIDER_NONE);
        a.recycle();
    }

    public void setShowDividers(int showDividers) {
        if (showDividers != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = showDividers;
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public void setDividerDrawable(Drawable divider) {
        boolean z = false;
        if (divider != this.mDivider) {
            this.mDivider = divider;
            if (divider != null) {
                this.mDividerWidth = divider.getIntrinsicWidth();
                this.mDividerHeight = divider.getIntrinsicHeight();
            } else {
                this.mDividerWidth = 0;
                this.mDividerHeight = 0;
            }
            if (divider == null) {
                z = true;
            }
            setWillNotDraw(z);
            requestLayout();
        }
    }

    public void setDividerPadding(int padding) {
        this.mDividerPadding = padding;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    protected void onDraw(Canvas canvas) {
        if (this.mDivider != null) {
            if (this.mOrientation == 1) {
                drawDividersVertical(canvas);
            } else {
                drawDividersHorizontal(canvas);
            }
        }
    }

    void drawDividersVertical(Canvas canvas) {
        int count = getVirtualChildCount();
        int i = SHOW_DIVIDER_NONE;
        while (i < count) {
            View child = getVirtualChildAt(i);
            if (child != null && child.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                drawHorizontalDivider(canvas, (child.getTop() - ((LayoutParams) child.getLayoutParams()).topMargin) - this.mDividerHeight);
            }
            i++;
        }
        if (hasDividerBeforeChildAt(count)) {
            int bottom;
            child = getVirtualChildAt(count - 1);
            if (child == null) {
                bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            } else {
                bottom = child.getBottom() + ((LayoutParams) child.getLayoutParams()).bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    void drawDividersHorizontal(Canvas canvas) {
        int position;
        int count = getVirtualChildCount();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int i = SHOW_DIVIDER_NONE;
        while (i < count) {
            LayoutParams lp;
            View child = getVirtualChildAt(i);
            if (child != null && child.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                lp = (LayoutParams) child.getLayoutParams();
                if (isLayoutRtl) {
                    position = child.getRight() + lp.rightMargin;
                } else {
                    position = (child.getLeft() - lp.leftMargin) - this.mDividerWidth;
                }
                drawVerticalDivider(canvas, position);
            }
            i++;
        }
        if (hasDividerBeforeChildAt(count)) {
            child = getVirtualChildAt(count - 1);
            if (child != null) {
                lp = (LayoutParams) child.getLayoutParams();
                if (isLayoutRtl) {
                    position = (child.getLeft() - lp.leftMargin) - this.mDividerWidth;
                } else {
                    position = child.getRight() + lp.rightMargin;
                }
            } else if (isLayoutRtl) {
                position = getPaddingLeft();
            } else {
                position = (getWidth() - getPaddingRight()) - this.mDividerWidth;
            }
            drawVerticalDivider(canvas, position);
        }
    }

    void drawHorizontalDivider(Canvas canvas, int top) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, top, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + top);
        this.mDivider.draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int left) {
        this.mDivider.setBounds(left, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + left, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public void setBaselineAligned(boolean baselineAligned) {
        this.mBaselineAligned = baselineAligned;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    public void setMeasureWithLargestChildEnabled(boolean enabled) {
        this.mUseLargestChild = enabled;
    }

    public int getBaseline() {
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        if (getChildCount() <= this.mBaselineAlignedChildIndex) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View child = getChildAt(this.mBaselineAlignedChildIndex);
        int childBaseline = child.getBaseline();
        if (childBaseline != -1) {
            int childTop = this.mBaselineChildTop;
            if (this.mOrientation == 1) {
                int majorGravity = this.mGravity & 112;
                if (majorGravity != 48) {
                    switch (majorGravity) {
                        case ConnectionResult.API_UNAVAILABLE:
                            childTop += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
                            break;
                        case net.oneplus.weather.R.styleable.AppCompatTheme_panelMenuListTheme:
                            childTop = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
                            break;
                    }
                }
            }
            return (((LayoutParams) child.getLayoutParams()).topMargin + childTop) + childBaseline;
        } else if (this.mBaselineAlignedChildIndex == 0) {
            return -1;
        } else {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i < 0 || i >= getChildCount()) {
            throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
        }
        this.mBaselineAlignedChildIndex = i;
    }

    View getVirtualChildAt(int index) {
        return getChildAt(index);
    }

    int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    public void setWeightSum(float weightSum) {
        this.mWeightSum = Math.max(AutoScrollHelper.RELATIVE_UNSPECIFIED, weightSum);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mOrientation == 1) {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        }
    }

    protected boolean hasDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            return (this.mShowDividers & 1) != 0;
        } else {
            if (childIndex == getChildCount()) {
                return (this.mShowDividers & 4) != 0;
            } else {
                if ((this.mShowDividers & 2) == 0) {
                    return false;
                }
                boolean hasVisibleViewBefore = false;
                for (int i = childIndex - 1; i >= 0; i--) {
                    if (getChildAt(i).getVisibility() != 8) {
                        hasVisibleViewBefore = true;
                        break;
                    }
                }
                return hasVisibleViewBefore;
            }
        }
    }

    void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
        LayoutParams lp;
        int childHeight;
        boolean matchWidthLocally;
        this.mTotalLength = 0;
        int maxWidth = SHOW_DIVIDER_NONE;
        int childState = SHOW_DIVIDER_NONE;
        int alternativeMaxWidth = SHOW_DIVIDER_NONE;
        int weightedMaxWidth = SHOW_DIVIDER_NONE;
        boolean allFillParent = true;
        float totalWeight = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        int count = getVirtualChildCount();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        boolean matchWidth = false;
        boolean skippedMeasure = false;
        int baselineChildIndex = this.mBaselineAlignedChildIndex;
        boolean useLargestChild = this.mUseLargestChild;
        int largestChildHeight = CitySearchProvider.GET_SEARCH_RESULT_FAIL;
        int i = SHOW_DIVIDER_NONE;
        while (i < count) {
            int totalLength;
            int margin;
            int measuredWidth;
            View child = getVirtualChildAt(i);
            if (child == null) {
                this.mTotalLength += measureNullChild(i);
            } else if (child.getVisibility() == 8) {
                i += getChildrenSkipCount(child, i);
            } else {
                if (hasDividerBeforeChildAt(i)) {
                    this.mTotalLength += this.mDividerHeight;
                }
                lp = (LayoutParams) child.getLayoutParams();
                totalWeight += lp.weight;
                if (heightMode == 1073741824 && lp.height == 0 && lp.weight > 0.0f) {
                    totalLength = this.mTotalLength;
                    this.mTotalLength = Math.max(totalLength, (lp.topMargin + totalLength) + lp.bottomMargin);
                    skippedMeasure = true;
                } else {
                    int oldHeight = CitySearchProvider.GET_SEARCH_RESULT_FAIL;
                    if (lp.height == 0 && lp.weight > 0.0f) {
                        oldHeight = SHOW_DIVIDER_NONE;
                        lp.height = -2;
                    }
                    measureChildBeforeLayout(child, i, widthMeasureSpec, SHOW_DIVIDER_NONE, heightMeasureSpec, totalWeight == 0.0f ? this.mTotalLength : SHOW_DIVIDER_NONE);
                    if (oldHeight != Integer.MIN_VALUE) {
                        lp.height = oldHeight;
                    }
                    childHeight = child.getMeasuredHeight();
                    totalLength = this.mTotalLength;
                    this.mTotalLength = Math.max(totalLength, (((totalLength + childHeight) + lp.topMargin) + lp.bottomMargin) + getNextLocationOffset(child));
                    if (useLargestChild) {
                        largestChildHeight = Math.max(childHeight, largestChildHeight);
                    }
                }
                if (baselineChildIndex >= 0 && baselineChildIndex == i + 1) {
                    this.mBaselineChildTop = this.mTotalLength;
                }
                if (i >= baselineChildIndex || lp.weight <= 0.0f) {
                    matchWidthLocally = false;
                    if (widthMode != 1073741824 && lp.width == -1) {
                        matchWidth = true;
                        matchWidthLocally = true;
                    }
                    margin = lp.leftMargin + lp.rightMargin;
                    measuredWidth = child.getMeasuredWidth() + margin;
                    maxWidth = Math.max(maxWidth, measuredWidth);
                    childState = View.combineMeasuredStates(childState, child.getMeasuredState());
                    allFillParent = allFillParent && lp.width == -1;
                    if (lp.weight > 0.0f) {
                        if (!matchWidthLocally) {
                            margin = measuredWidth;
                        }
                        weightedMaxWidth = Math.max(weightedMaxWidth, margin);
                    } else {
                        if (!matchWidthLocally) {
                            margin = measuredWidth;
                        }
                        alternativeMaxWidth = Math.max(alternativeMaxWidth, margin);
                    }
                    i += getChildrenSkipCount(child, i);
                } else {
                    throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                }
            }
            i++;
        }
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(count)) {
            this.mTotalLength += this.mDividerHeight;
        }
        if (useLargestChild) {
            if (heightMode == Integer.MIN_VALUE || heightMode == 0) {
                this.mTotalLength = 0;
                i = SHOW_DIVIDER_NONE;
                while (i < count) {
                    child = getVirtualChildAt(i);
                    if (child == null) {
                        this.mTotalLength += measureNullChild(i);
                    } else if (child.getVisibility() == 8) {
                        i += getChildrenSkipCount(child, i);
                    } else {
                        lp = (LayoutParams) child.getLayoutParams();
                        totalLength = this.mTotalLength;
                        this.mTotalLength = Math.max(totalLength, (((totalLength + largestChildHeight) + lp.topMargin) + lp.bottomMargin) + getNextLocationOffset(child));
                    }
                    i++;
                }
            }
        }
        this.mTotalLength += getPaddingTop() + getPaddingBottom();
        int heightSizeAndState = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumHeight()), heightMeasureSpec, SHOW_DIVIDER_NONE);
        int delta = (heightSizeAndState & 16777215) - this.mTotalLength;
        if (skippedMeasure || (delta != 0 && totalWeight > 0.0f)) {
            float weightSum;
            if (this.mWeightSum > 0.0f) {
                weightSum = this.mWeightSum;
            } else {
                weightSum = totalWeight;
            }
            this.mTotalLength = 0;
            for (i = SHOW_DIVIDER_NONE; i < count; i++) {
                child = getVirtualChildAt(i);
                if (child.getVisibility() != 8) {
                    lp = (LayoutParams) child.getLayoutParams();
                    float childExtra = lp.weight;
                    if (childExtra > 0.0f) {
                        int share = (int) ((((float) delta) * childExtra) / weightSum);
                        weightSum -= childExtra;
                        delta -= share;
                        int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, ((getPaddingLeft() + getPaddingRight()) + lp.leftMargin) + lp.rightMargin, lp.width);
                        if (lp.height == 0 && heightMode == 1073741824) {
                            if (share <= 0) {
                                share = SHOW_DIVIDER_NONE;
                            }
                            child.measure(childWidthMeasureSpec, MeasureSpec.makeMeasureSpec(share, CitySearchProvider.GET_SEARCH_RESULT_SUCC));
                        } else {
                            childHeight = child.getMeasuredHeight() + share;
                            if (childHeight < 0) {
                                childHeight = SHOW_DIVIDER_NONE;
                            }
                            child.measure(childWidthMeasureSpec, MeasureSpec.makeMeasureSpec(childHeight, CitySearchProvider.GET_SEARCH_RESULT_SUCC));
                        }
                        childState = View.combineMeasuredStates(childState, child.getMeasuredState() & -256);
                    }
                    margin = lp.leftMargin + lp.rightMargin;
                    measuredWidth = child.getMeasuredWidth() + margin;
                    maxWidth = Math.max(maxWidth, measuredWidth);
                    matchWidthLocally = widthMode != 1073741824 && lp.width == -1;
                    if (!matchWidthLocally) {
                        margin = measuredWidth;
                    }
                    alternativeMaxWidth = Math.max(alternativeMaxWidth, margin);
                    allFillParent = allFillParent && lp.width == -1;
                    totalLength = this.mTotalLength;
                    this.mTotalLength = Math.max(totalLength, (((child.getMeasuredHeight() + totalLength) + lp.topMargin) + lp.bottomMargin) + getNextLocationOffset(child));
                }
            }
            this.mTotalLength += getPaddingTop() + getPaddingBottom();
        } else {
            alternativeMaxWidth = Math.max(alternativeMaxWidth, weightedMaxWidth);
            if (useLargestChild && heightMode != 1073741824) {
                for (i = SHOW_DIVIDER_NONE; i < count; i++) {
                    child = getVirtualChildAt(i);
                    if (child != null && child.getVisibility() != 8 && ((LayoutParams) child.getLayoutParams()).weight > 0.0f) {
                        child.measure(MeasureSpec.makeMeasureSpec(child.getMeasuredWidth(), CitySearchProvider.GET_SEARCH_RESULT_SUCC), MeasureSpec.makeMeasureSpec(largestChildHeight, CitySearchProvider.GET_SEARCH_RESULT_SUCC));
                    }
                }
            }
        }
        if (!(allFillParent || widthMode == 1073741824)) {
            maxWidth = alternativeMaxWidth;
        }
        setMeasuredDimension(View.resolveSizeAndState(Math.max(maxWidth + (getPaddingLeft() + getPaddingRight()), getSuggestedMinimumWidth()), widthMeasureSpec, childState), heightSizeAndState);
        if (matchWidth) {
            forceUniformWidth(count, heightMeasureSpec);
        }
    }

    private void forceUniformWidth(int count, int heightMeasureSpec) {
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), CitySearchProvider.GET_SEARCH_RESULT_SUCC);
        for (int i = SHOW_DIVIDER_NONE; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.width == -1) {
                    int oldHeight = lp.height;
                    lp.height = child.getMeasuredHeight();
                    measureChildWithMargins(child, uniformMeasureSpec, SHOW_DIVIDER_NONE, heightMeasureSpec, 0);
                    lp.height = oldHeight;
                }
            }
        }
    }

    void measureHorizontal(int widthMeasureSpec, int heightMeasureSpec) {
        int childBaseline;
        this.mTotalLength = 0;
        int maxHeight = SHOW_DIVIDER_NONE;
        int childState = SHOW_DIVIDER_NONE;
        int alternativeMaxHeight = SHOW_DIVIDER_NONE;
        int weightedMaxHeight = SHOW_DIVIDER_NONE;
        boolean allFillParent = true;
        float totalWeight = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        int count = getVirtualChildCount();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        boolean matchHeight = false;
        boolean skippedMeasure = false;
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] maxAscent = this.mMaxAscent;
        int[] maxDescent = this.mMaxDescent;
        maxAscent[3] = -1;
        maxAscent[2] = -1;
        maxAscent[1] = -1;
        maxAscent[0] = -1;
        maxDescent[3] = -1;
        maxDescent[2] = -1;
        maxDescent[1] = -1;
        maxDescent[0] = -1;
        boolean baselineAligned = this.mBaselineAligned;
        boolean useLargestChild = this.mUseLargestChild;
        boolean isExactly = widthMode == 1073741824;
        int largestChildWidth = CitySearchProvider.GET_SEARCH_RESULT_FAIL;
        int i = SHOW_DIVIDER_NONE;
        while (i < count) {
            LayoutParams lp;
            int totalLength;
            int childWidth;
            boolean matchHeightLocally;
            int margin;
            int childHeight;
            int i2;
            View child = getVirtualChildAt(i);
            if (child == null) {
                this.mTotalLength += measureNullChild(i);
            } else if (child.getVisibility() == 8) {
                i += getChildrenSkipCount(child, i);
            } else {
                if (hasDividerBeforeChildAt(i)) {
                    this.mTotalLength += this.mDividerWidth;
                }
                lp = (LayoutParams) child.getLayoutParams();
                totalWeight += lp.weight;
                if (widthMode == 1073741824 && lp.width == 0 && lp.weight > 0.0f) {
                    if (isExactly) {
                        this.mTotalLength += lp.leftMargin + lp.rightMargin;
                    } else {
                        totalLength = this.mTotalLength;
                        this.mTotalLength = Math.max(totalLength, (lp.leftMargin + totalLength) + lp.rightMargin);
                    }
                    if (baselineAligned) {
                        int freeSpec = MeasureSpec.makeMeasureSpec(SHOW_DIVIDER_NONE, SHOW_DIVIDER_NONE);
                        child.measure(freeSpec, freeSpec);
                    } else {
                        skippedMeasure = true;
                    }
                } else {
                    int oldWidth = CitySearchProvider.GET_SEARCH_RESULT_FAIL;
                    if (lp.width == 0 && lp.weight > 0.0f) {
                        oldWidth = SHOW_DIVIDER_NONE;
                        lp.width = -2;
                    }
                    measureChildBeforeLayout(child, i, widthMeasureSpec, totalWeight == 0.0f ? this.mTotalLength : SHOW_DIVIDER_NONE, heightMeasureSpec, SHOW_DIVIDER_NONE);
                    if (oldWidth != Integer.MIN_VALUE) {
                        lp.width = oldWidth;
                    }
                    childWidth = child.getMeasuredWidth();
                    if (isExactly) {
                        this.mTotalLength += ((lp.leftMargin + childWidth) + lp.rightMargin) + getNextLocationOffset(child);
                    } else {
                        totalLength = this.mTotalLength;
                        this.mTotalLength = Math.max(totalLength, (((totalLength + childWidth) + lp.leftMargin) + lp.rightMargin) + getNextLocationOffset(child));
                    }
                    if (useLargestChild) {
                        largestChildWidth = Math.max(childWidth, largestChildWidth);
                    }
                }
                matchHeightLocally = false;
                if (heightMode != 1073741824 && lp.height == -1) {
                    matchHeight = true;
                    matchHeightLocally = true;
                }
                margin = lp.topMargin + lp.bottomMargin;
                childHeight = child.getMeasuredHeight() + margin;
                childState = View.combineMeasuredStates(childState, child.getMeasuredState());
                if (baselineAligned) {
                    childBaseline = child.getBaseline();
                    if (childBaseline != -1) {
                        if (lp.gravity < 0) {
                            i2 = this.mGravity;
                        } else {
                            i2 = lp.gravity;
                        }
                        int index = (((i2 & 112) >> 4) & -2) >> 1;
                        maxAscent[index] = Math.max(maxAscent[index], childBaseline);
                        maxDescent[index] = Math.max(maxDescent[index], childHeight - childBaseline);
                    }
                }
                maxHeight = Math.max(maxHeight, childHeight);
                allFillParent = allFillParent && lp.height == -1;
                if (lp.weight > 0.0f) {
                    if (!matchHeightLocally) {
                        margin = childHeight;
                    }
                    weightedMaxHeight = Math.max(weightedMaxHeight, margin);
                } else {
                    if (!matchHeightLocally) {
                        margin = childHeight;
                    }
                    alternativeMaxHeight = Math.max(alternativeMaxHeight, margin);
                }
                i += getChildrenSkipCount(child, i);
            }
            i++;
        }
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(count)) {
            this.mTotalLength += this.mDividerWidth;
        }
        if (!(maxAscent[1] == -1 && maxAscent[0] == -1 && maxAscent[2] == -1 && maxAscent[3] == -1)) {
            maxHeight = Math.max(maxHeight, Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2]))) + Math.max(maxDescent[3], Math.max(maxDescent[0], Math.max(maxDescent[1], maxDescent[2]))));
        }
        if (useLargestChild) {
            if (widthMode == Integer.MIN_VALUE || widthMode == 0) {
                this.mTotalLength = 0;
                i = SHOW_DIVIDER_NONE;
                while (i < count) {
                    child = getVirtualChildAt(i);
                    if (child == null) {
                        this.mTotalLength += measureNullChild(i);
                    } else if (child.getVisibility() == 8) {
                        i += getChildrenSkipCount(child, i);
                    } else {
                        lp = (LayoutParams) child.getLayoutParams();
                        if (isExactly) {
                            this.mTotalLength += ((lp.leftMargin + largestChildWidth) + lp.rightMargin) + getNextLocationOffset(child);
                        } else {
                            totalLength = this.mTotalLength;
                            this.mTotalLength = Math.max(totalLength, (((totalLength + largestChildWidth) + lp.leftMargin) + lp.rightMargin) + getNextLocationOffset(child));
                        }
                    }
                    i++;
                }
            }
        }
        this.mTotalLength += getPaddingLeft() + getPaddingRight();
        int widthSizeAndState = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumWidth()), widthMeasureSpec, SHOW_DIVIDER_NONE);
        int delta = (widthSizeAndState & 16777215) - this.mTotalLength;
        if (skippedMeasure || (delta != 0 && totalWeight > 0.0f)) {
            float weightSum;
            if (this.mWeightSum > 0.0f) {
                weightSum = this.mWeightSum;
            } else {
                weightSum = totalWeight;
            }
            maxAscent[3] = -1;
            maxAscent[2] = -1;
            maxAscent[1] = -1;
            maxAscent[0] = -1;
            maxDescent[3] = -1;
            maxDescent[2] = -1;
            maxDescent[1] = -1;
            maxDescent[0] = -1;
            maxHeight = -1;
            this.mTotalLength = 0;
            for (i = SHOW_DIVIDER_NONE; i < count; i++) {
                child = getVirtualChildAt(i);
                if (child != null && child.getVisibility() != 8) {
                    lp = (LayoutParams) child.getLayoutParams();
                    float childExtra = lp.weight;
                    if (childExtra > 0.0f) {
                        int share = (int) ((((float) delta) * childExtra) / weightSum);
                        weightSum -= childExtra;
                        delta -= share;
                        int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, ((getPaddingTop() + getPaddingBottom()) + lp.topMargin) + lp.bottomMargin, lp.height);
                        if (lp.width == 0 && widthMode == 1073741824) {
                            if (share <= 0) {
                                share = SHOW_DIVIDER_NONE;
                            }
                            child.measure(MeasureSpec.makeMeasureSpec(share, CitySearchProvider.GET_SEARCH_RESULT_SUCC), childHeightMeasureSpec);
                        } else {
                            childWidth = child.getMeasuredWidth() + share;
                            if (childWidth < 0) {
                                childWidth = SHOW_DIVIDER_NONE;
                            }
                            child.measure(MeasureSpec.makeMeasureSpec(childWidth, CitySearchProvider.GET_SEARCH_RESULT_SUCC), childHeightMeasureSpec);
                        }
                        childState = View.combineMeasuredStates(childState, child.getMeasuredState() & -16777216);
                    }
                    if (isExactly) {
                        this.mTotalLength += ((child.getMeasuredWidth() + lp.leftMargin) + lp.rightMargin) + getNextLocationOffset(child);
                    } else {
                        totalLength = this.mTotalLength;
                        this.mTotalLength = Math.max(totalLength, (((child.getMeasuredWidth() + totalLength) + lp.leftMargin) + lp.rightMargin) + getNextLocationOffset(child));
                    }
                    matchHeightLocally = heightMode != 1073741824 && lp.height == -1;
                    margin = lp.topMargin + lp.bottomMargin;
                    childHeight = child.getMeasuredHeight() + margin;
                    maxHeight = Math.max(maxHeight, childHeight);
                    if (!matchHeightLocally) {
                        margin = childHeight;
                    }
                    alternativeMaxHeight = Math.max(alternativeMaxHeight, margin);
                    allFillParent = allFillParent && lp.height == -1;
                    if (baselineAligned) {
                        childBaseline = child.getBaseline();
                        if (childBaseline != -1) {
                            if (lp.gravity < 0) {
                                i2 = this.mGravity;
                            } else {
                                i2 = lp.gravity;
                            }
                            index = (((i2 & 112) >> 4) & -2) >> 1;
                            maxAscent[index] = Math.max(maxAscent[index], childBaseline);
                            maxDescent[index] = Math.max(maxDescent[index], childHeight - childBaseline);
                        }
                    }
                }
            }
            this.mTotalLength += getPaddingLeft() + getPaddingRight();
            if (!(maxAscent[1] == -1 && maxAscent[0] == -1 && maxAscent[2] == -1 && maxAscent[3] == -1)) {
                maxHeight = Math.max(maxHeight, Math.max(maxAscent[3], Math.max(maxAscent[0], Math.max(maxAscent[1], maxAscent[2]))) + Math.max(maxDescent[3], Math.max(maxDescent[0], Math.max(maxDescent[1], maxDescent[2]))));
            }
        } else {
            alternativeMaxHeight = Math.max(alternativeMaxHeight, weightedMaxHeight);
            if (useLargestChild && widthMode != 1073741824) {
                for (i = SHOW_DIVIDER_NONE; i < count; i++) {
                    child = getVirtualChildAt(i);
                    if (child != null && child.getVisibility() != 8 && ((LayoutParams) child.getLayoutParams()).weight > 0.0f) {
                        child.measure(MeasureSpec.makeMeasureSpec(largestChildWidth, CitySearchProvider.GET_SEARCH_RESULT_SUCC), MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), CitySearchProvider.GET_SEARCH_RESULT_SUCC));
                    }
                }
            }
        }
        if (!(allFillParent || heightMode == 1073741824)) {
            maxHeight = alternativeMaxHeight;
        }
        setMeasuredDimension((-16777216 & childState) | widthSizeAndState, View.resolveSizeAndState(Math.max(maxHeight + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), heightMeasureSpec, childState << 16));
        if (matchHeight) {
            forceUniformHeight(count, widthMeasureSpec);
        }
    }

    private void forceUniformHeight(int count, int widthMeasureSpec) {
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), CitySearchProvider.GET_SEARCH_RESULT_SUCC);
        for (int i = SHOW_DIVIDER_NONE; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.height == -1) {
                    int oldWidth = lp.width;
                    lp.width = child.getMeasuredWidth();
                    measureChildWithMargins(child, widthMeasureSpec, SHOW_DIVIDER_NONE, uniformMeasureSpec, 0);
                    lp.width = oldWidth;
                }
            }
        }
    }

    int getChildrenSkipCount(View child, int index) {
        return SHOW_DIVIDER_NONE;
    }

    int measureNullChild(int childIndex) {
        return SHOW_DIVIDER_NONE;
    }

    void measureChildBeforeLayout(View child, int childIndex, int widthMeasureSpec, int totalWidth, int heightMeasureSpec, int totalHeight) {
        measureChildWithMargins(child, widthMeasureSpec, totalWidth, heightMeasureSpec, totalHeight);
    }

    int getLocationOffset(View child) {
        return SHOW_DIVIDER_NONE;
    }

    int getNextLocationOffset(View child) {
        return SHOW_DIVIDER_NONE;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.mOrientation == 1) {
            layoutVertical(l, t, r, b);
        } else {
            layoutHorizontal(l, t, r, b);
        }
    }

    void layoutVertical(int left, int top, int right, int bottom) {
        int childTop;
        int paddingLeft = getPaddingLeft();
        int width = right - left;
        int childRight = width - getPaddingRight();
        int childSpace = (width - paddingLeft) - getPaddingRight();
        int count = getVirtualChildCount();
        int minorGravity = this.mGravity & 8388615;
        switch (this.mGravity & 112) {
            case ConnectionResult.API_UNAVAILABLE:
                childTop = getPaddingTop() + (((bottom - top) - this.mTotalLength) / 2);
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_panelMenuListTheme:
                childTop = ((getPaddingTop() + bottom) - top) - this.mTotalLength;
                break;
            default:
                childTop = getPaddingTop();
                break;
        }
        int i = SHOW_DIVIDER_NONE;
        while (i < count) {
            View child = getVirtualChildAt(i);
            if (child == null) {
                childTop += measureNullChild(i);
            } else if (child.getVisibility() != 8) {
                int childLeft;
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int gravity = lp.gravity;
                if (gravity < 0) {
                    gravity = minorGravity;
                }
                switch (GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this)) & 7) {
                    case VERTICAL:
                        childLeft = ((((childSpace - childWidth) / 2) + paddingLeft) + lp.leftMargin) - lp.rightMargin;
                        break;
                    case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                        childLeft = (childRight - childWidth) - lp.rightMargin;
                        break;
                    default:
                        childLeft = paddingLeft + lp.leftMargin;
                        break;
                }
                if (hasDividerBeforeChildAt(i)) {
                    childTop += this.mDividerHeight;
                }
                childTop += lp.topMargin;
                setChildFrame(child, childLeft, childTop + getLocationOffset(child), childWidth, childHeight);
                childTop += (lp.bottomMargin + childHeight) + getNextLocationOffset(child);
                i += getChildrenSkipCount(child, i);
            }
            i++;
        }
    }

    void layoutHorizontal(int left, int top, int right, int bottom) {
        int childLeft;
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop = getPaddingTop();
        int height = bottom - top;
        int childBottom = height - getPaddingBottom();
        int childSpace = (height - paddingTop) - getPaddingBottom();
        int count = getVirtualChildCount();
        int majorGravity = this.mGravity & 8388615;
        int minorGravity = this.mGravity & 112;
        boolean baselineAligned = this.mBaselineAligned;
        int[] maxAscent = this.mMaxAscent;
        int[] maxDescent = this.mMaxDescent;
        switch (GravityCompat.getAbsoluteGravity(majorGravity, ViewCompat.getLayoutDirection(this))) {
            case VERTICAL:
                childLeft = getPaddingLeft() + (((right - left) - this.mTotalLength) / 2);
                break;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                childLeft = ((getPaddingLeft() + right) - left) - this.mTotalLength;
                break;
            default:
                childLeft = getPaddingLeft();
                break;
        }
        int start = SHOW_DIVIDER_NONE;
        int dir = VERTICAL;
        if (isLayoutRtl) {
            start = count - 1;
            dir = -1;
        }
        int i = SHOW_DIVIDER_NONE;
        while (i < count) {
            int childIndex = start + (dir * i);
            View child = getVirtualChildAt(childIndex);
            if (child == null) {
                childLeft += measureNullChild(childIndex);
            } else if (child.getVisibility() != 8) {
                int childTop;
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                int childBaseline = -1;
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (baselineAligned && lp.height != -1) {
                    childBaseline = child.getBaseline();
                }
                int gravity = lp.gravity;
                if (gravity < 0) {
                    gravity = minorGravity;
                }
                switch (gravity & 112) {
                    case ConnectionResult.API_UNAVAILABLE:
                        childTop = ((((childSpace - childHeight) / 2) + paddingTop) + lp.topMargin) - lp.bottomMargin;
                        break;
                    case net.oneplus.weather.R.styleable.AppCompatTheme_colorAccent:
                        childTop = paddingTop + lp.topMargin;
                        if (childBaseline != -1) {
                            childTop += maxAscent[1] - childBaseline;
                        }
                        break;
                    case net.oneplus.weather.R.styleable.AppCompatTheme_panelMenuListTheme:
                        childTop = (childBottom - childHeight) - lp.bottomMargin;
                        if (childBaseline != -1) {
                            childTop -= maxDescent[2] - (child.getMeasuredHeight() - childBaseline);
                        }
                        break;
                    default:
                        childTop = paddingTop;
                        break;
                }
                if (hasDividerBeforeChildAt(childIndex)) {
                    childLeft += this.mDividerWidth;
                }
                childLeft += lp.leftMargin;
                setChildFrame(child, childLeft + getLocationOffset(child), childTop, childWidth, childHeight);
                childLeft += (lp.rightMargin + childWidth) + getNextLocationOffset(child);
                i += getChildrenSkipCount(child, childIndex);
            }
            i++;
        }
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }

    public void setOrientation(int orientation) {
        if (this.mOrientation != orientation) {
            this.mOrientation = orientation;
            requestLayout();
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setGravity(int gravity) {
        if (this.mGravity != gravity) {
            if ((8388615 & gravity) == 0) {
                gravity |= 8388611;
            }
            if ((gravity & 112) == 0) {
                gravity |= 48;
            }
            this.mGravity = gravity;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public void setHorizontalGravity(int horizontalGravity) {
        int gravity = horizontalGravity & 8388615;
        if ((this.mGravity & 8388615) != gravity) {
            this.mGravity = (this.mGravity & -8388616) | gravity;
            requestLayout();
        }
    }

    public void setVerticalGravity(int verticalGravity) {
        int gravity = verticalGravity & 112;
        if ((this.mGravity & 112) != gravity) {
            this.mGravity = (this.mGravity & -113) | gravity;
            requestLayout();
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -2);
        }
        return this.mOrientation == 1 ? new LayoutParams(-1, -2) : null;
    }

    protected LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        if (VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityEvent(event);
            event.setClassName(LinearLayoutCompat.class.getName());
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        if (VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityNodeInfo(info);
            info.setClassName(LinearLayoutCompat.class.getName());
        }
    }
}
