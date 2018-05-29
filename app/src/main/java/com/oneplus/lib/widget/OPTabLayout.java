package com.oneplus.lib.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.support.v7.widget.ListPopupWindow;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import com.oneplus.commonctrl.R;
import com.oneplus.lib.widget.OPTabLayout.Tab;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import net.oneplus.weather.provider.CitySearchProvider;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class OPTabLayout extends HorizontalScrollView {
    private static final int ANIMATION_DURATION = 300;
    private static final int DEFAULT_HEIGHT = 48;
    private static final int FIXED_WRAP_GUTTER_MIN = 16;
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_FILL = 0;
    public static final int MODE_FIXED = 1;
    public static final int MODE_SCROLLABLE = 0;
    private static final int MOTION_NON_ADJACENT_OFFSET = 24;
    private static final int TAB_MIN_WIDTH_MARGIN = 56;
    private Interpolator fast_out_slow_in_interpolator;
    private int mContentInsetStart;
    private ValueAnimator mIndicatorAnimator;
    private int mMode;
    private OnTabSelectedListener mOnTabSelectedListener;
    private final int mRequestedTabMaxWidth;
    private ValueAnimator mScrollAnimator;
    private Tab mSelectedTab;
    private final int mTabBackgroundResId;
    private OnClickListener mTabClickListener;
    private int mTabGravity;
    private int mTabHorizontalSpacing;
    private int mTabMaxWidth;
    private final int mTabMinWidth;
    private int mTabPaddingBottom;
    private int mTabPaddingEnd;
    private int mTabPaddingStart;
    private int mTabPaddingTop;
    private final SlidingTabStrip mTabStrip;
    private int mTabTextAppearance;
    private ColorStateList mTabTextColors;
    private final ArrayList<Tab> mTabs;

    @Retention(RetentionPolicy.SOURCE)
    public static @interface Mode {
    }

    public static interface OnTabSelectedListener {
        void onTabReselected(Tab tab);

        void onTabSelected(Tab tab);

        void onTabUnselected(Tab tab);
    }

    private class SlidingTabStrip extends LinearLayout {
        private int mIndicatorLeft;
        private int mIndicatorRight;
        private int mSelectedIndicatorHeight;
        private final Paint mSelectedIndicatorPaint;
        private int mSelectedPosition;
        private float mSelectionOffset;

        class AnonymousClass_1 implements AnimatorUpdateListener {
            final /* synthetic */ int val$startLeft;
            final /* synthetic */ int val$startRight;
            final /* synthetic */ int val$targetLeft;
            final /* synthetic */ int val$targetRight;

            AnonymousClass_1(int i, int i2, int i3, int i4) {
                this.val$startLeft = i;
                this.val$targetLeft = i2;
                this.val$startRight = i3;
                this.val$targetRight = i4;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                SlidingTabStrip.this.setIndicatorPosition(OPAnimationUtils.lerp(this.val$startLeft, this.val$targetLeft, fraction), OPAnimationUtils.lerp(this.val$startRight, this.val$targetRight, fraction));
            }
        }

        class AnonymousClass_2 extends AnimatorListenerAdapter {
            final /* synthetic */ int val$position;

            AnonymousClass_2(int i) {
                this.val$position = i;
            }

            public void onAnimationEnd(Animator animation) {
                SlidingTabStrip.this.mSelectedPosition = this.val$position;
                SlidingTabStrip.this.mSelectionOffset = AutoScrollHelper.RELATIVE_UNSPECIFIED;
            }

            public void onAnimationCancel(Animator animation) {
                SlidingTabStrip.this.mSelectedPosition = this.val$position;
                SlidingTabStrip.this.mSelectionOffset = AutoScrollHelper.RELATIVE_UNSPECIFIED;
            }
        }

        SlidingTabStrip(Context context) {
            super(context);
            this.mSelectedPosition = -1;
            this.mIndicatorLeft = -1;
            this.mIndicatorRight = -1;
            setWillNotDraw(false);
            this.mSelectedIndicatorPaint = new Paint();
        }

        void setSelectedIndicatorColor(int color) {
            if (this.mSelectedIndicatorPaint.getColor() != color) {
                this.mSelectedIndicatorPaint.setColor(color);
                postInvalidateOnAnimation();
            }
        }

        void setSelectedIndicatorHeight(int height) {
            if (this.mSelectedIndicatorHeight != height) {
                this.mSelectedIndicatorHeight = height;
                postInvalidateOnAnimation();
            }
        }

        boolean childrenNeedLayout() {
            int z = getChildCount();
            for (int i = MODE_SCROLLABLE; i < z; i++) {
                if (getChildAt(i).getWidth() <= 0) {
                    return true;
                }
            }
            return false;
        }

        void setIndicatorPositionFromTabPosition(int position, float positionOffset) {
            this.mSelectedPosition = position;
            this.mSelectionOffset = positionOffset;
            updateIndicatorPosition();
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (MeasureSpec.getMode(widthMeasureSpec) == 1073741824 && OPTabLayout.this.mMode == 1 && OPTabLayout.this.mTabGravity == 1) {
                int i;
                int count = getChildCount();
                int unspecifiedSpec = MeasureSpec.makeMeasureSpec(MODE_SCROLLABLE, MODE_SCROLLABLE);
                int largestTabWidth = MODE_SCROLLABLE;
                int z = count;
                for (i = MODE_SCROLLABLE; i < z; i++) {
                    View child = getChildAt(i);
                    child.measure(unspecifiedSpec, heightMeasureSpec);
                    largestTabWidth = Math.max(largestTabWidth, child.getMeasuredWidth());
                }
                if (largestTabWidth > 0) {
                    if (largestTabWidth * count <= getMeasuredWidth() - (OPTabLayout.this.dpToPx(FIXED_WRAP_GUTTER_MIN) * 2)) {
                        for (i = MODE_SCROLLABLE; i < count; i++) {
                            LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
                            lp.width = largestTabWidth;
                            lp.weight = 0.0f;
                        }
                    } else {
                        OPTabLayout.this.mTabGravity = MODE_SCROLLABLE;
                        OPTabLayout.this.updateTabViewsLayoutParams();
                    }
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }
            }
        }

        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            updateIndicatorPosition();
        }

        private void updateIndicatorPosition() {
            int right;
            int left;
            View selectedTitle = getChildAt(this.mSelectedPosition);
            if (selectedTitle == null || selectedTitle.getWidth() <= 0) {
                right = -1;
                left = -1;
            } else {
                left = selectedTitle.getLeft();
                right = selectedTitle.getRight();
                if (this.mSelectionOffset > 0.0f && this.mSelectedPosition < getChildCount() - 1) {
                    View nextTitle = getChildAt(this.mSelectedPosition + 1);
                    left = (int) ((this.mSelectionOffset * ((float) nextTitle.getLeft())) + ((1.0f - this.mSelectionOffset) * ((float) left)));
                    right = (int) ((this.mSelectionOffset * ((float) nextTitle.getRight())) + ((1.0f - this.mSelectionOffset) * ((float) right)));
                }
            }
            setIndicatorPosition(left, right);
        }

        private void setIndicatorPosition(int left, int right) {
            if (left != this.mIndicatorLeft || right != this.mIndicatorRight) {
                this.mIndicatorLeft = left;
                this.mIndicatorRight = right;
                postInvalidateOnAnimation();
            }
        }

        void animateIndicatorToPosition(int position, int duration) {
            int startLeft;
            int startRight;
            boolean isRtl = getLayoutDirection() == 1;
            View targetView = getChildAt(position);
            int targetLeft = targetView.getLeft();
            int targetRight = targetView.getRight();
            if (Math.abs(position - this.mSelectedPosition) <= 1) {
                startLeft = this.mIndicatorLeft;
                startRight = this.mIndicatorRight;
            } else {
                int offset = OPTabLayout.this.dpToPx(MOTION_NON_ADJACENT_OFFSET);
                if (position < this.mSelectedPosition) {
                    if (isRtl) {
                        startRight = targetLeft - offset;
                        startLeft = startRight;
                    } else {
                        startRight = targetRight + offset;
                        startLeft = startRight;
                    }
                } else if (isRtl) {
                    startRight = targetRight + offset;
                    startLeft = startRight;
                } else {
                    startRight = targetLeft - offset;
                    startLeft = startRight;
                }
            }
            if (startLeft != targetLeft || startRight != targetRight) {
                ValueAnimator animator = OPTabLayout.this.mIndicatorAnimator = new ValueAnimator();
                animator.setInterpolator(OPTabLayout.this.fast_out_slow_in_interpolator);
                animator.setDuration((long) duration);
                animator.setFloatValues(new float[]{0.0f, 1.0f});
                animator.addUpdateListener(new AnonymousClass_1(startLeft, targetLeft, startRight, targetRight));
                animator.addListener(new AnonymousClass_2(position));
                animator.start();
            }
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (this.mIndicatorLeft >= 0 && this.mIndicatorRight > this.mIndicatorLeft) {
                canvas.drawRect((float) this.mIndicatorLeft, (float) (getHeight() - this.mSelectedIndicatorHeight), (float) this.mIndicatorRight, (float) getHeight(), this.mSelectedIndicatorPaint);
            }
        }
    }

    public static final class Tab {
        public static final int INVALID_POSITION = -1;
        private CharSequence mContentDesc;
        private View mCustomView;
        private Drawable mIcon;
        private final OPTabLayout mParent;
        private int mPosition;
        private Object mTag;
        private CharSequence mText;

        Tab(OPTabLayout parent) {
            this.mPosition = -1;
            this.mParent = parent;
        }

        public Object getTag() {
            return this.mTag;
        }

        public com.oneplus.lib.widget.OPTabLayout.Tab setTag(Object tag) {
            this.mTag = tag;
            return this;
        }

        public View getCustomView() {
            return this.mCustomView;
        }

        public com.oneplus.lib.widget.OPTabLayout.Tab setCustomView(View view) {
            this.mCustomView = view;
            if (this.mPosition >= 0) {
                this.mParent.updateTab(this.mPosition);
            }
            return this;
        }

        public com.oneplus.lib.widget.OPTabLayout.Tab setCustomView(int layoutResId) {
            return setCustomView(LayoutInflater.from(this.mParent.getContext()).inflate(layoutResId, null));
        }

        public Drawable getIcon() {
            return this.mIcon;
        }

        public int getPosition() {
            return this.mPosition;
        }

        void setPosition(int position) {
            this.mPosition = position;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public com.oneplus.lib.widget.OPTabLayout.Tab setIcon(Drawable icon) {
            this.mIcon = icon;
            if (this.mPosition >= 0) {
                this.mParent.updateTab(this.mPosition);
            }
            return this;
        }

        public com.oneplus.lib.widget.OPTabLayout.Tab setIcon(int resId) {
            return setIcon(this.mParent.getContext().getDrawable(resId));
        }

        public com.oneplus.lib.widget.OPTabLayout.Tab setText(CharSequence text) {
            this.mText = text;
            if (this.mPosition >= 0) {
                this.mParent.updateTab(this.mPosition);
            }
            return this;
        }

        public com.oneplus.lib.widget.OPTabLayout.Tab setText(int resId) {
            return setText(this.mParent.getResources().getText(resId));
        }

        public void select() {
            this.mParent.selectTab(this);
        }

        public boolean isSelected() {
            return this.mParent.getSelectedTabPosition() == this.mPosition;
        }

        public com.oneplus.lib.widget.OPTabLayout.Tab setContentDescription(int resId) {
            return setContentDescription(this.mParent.getResources().getText(resId));
        }

        public com.oneplus.lib.widget.OPTabLayout.Tab setContentDescription(CharSequence contentDesc) {
            this.mContentDesc = contentDesc;
            if (this.mPosition >= 0) {
                this.mParent.updateTab(this.mPosition);
            }
            return this;
        }

        public CharSequence getContentDescription() {
            return this.mContentDesc;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public static @interface TabGravity {
    }

    class TabView extends LinearLayout implements OnLongClickListener {
        private ImageView mCustomIconView;
        private TextView mCustomTextView;
        private View mCustomView;
        private ImageView mIconView;
        private final Tab mTab;
        private TextView mTextView;

        public TabView(Context context, Tab tab) {
            super(context);
            this.mTab = tab;
            if (OPTabLayout.this.mTabBackgroundResId != 0) {
                setBackgroundDrawable(context.getDrawable(OPTabLayout.this.mTabBackgroundResId));
            }
            setPaddingRelative(OPTabLayout.this.mTabPaddingStart, OPTabLayout.this.mTabPaddingTop, OPTabLayout.this.mTabPaddingEnd, OPTabLayout.this.mTabPaddingBottom);
            setGravity(ConnectionResult.SIGN_IN_FAILED);
            update();
        }

        public void setSelected(boolean selected) {
            boolean changed = isSelected() != selected;
            super.setSelected(selected);
            if (changed && selected) {
                sendAccessibilityEvent(RainSurfaceView.RAIN_LEVEL_RAINSTORM);
                if (this.mTextView != null) {
                    this.mTextView.setSelected(selected);
                }
                if (this.mIconView != null) {
                    this.mIconView.setSelected(selected);
                }
            }
        }

        @TargetApi(14)
        public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(event);
            event.setClassName(android.app.ActionBar.Tab.class.getName());
        }

        @TargetApi(14)
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(info);
            info.setClassName(android.app.ActionBar.Tab.class.getName());
        }

        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int measuredWidth = getMeasuredWidth();
            if (measuredWidth < OPTabLayout.this.mTabMinWidth || measuredWidth > OPTabLayout.this.mTabMaxWidth) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(OPMathUtils.constrain(measuredWidth, OPTabLayout.this.mTabMinWidth, OPTabLayout.this.mTabMaxWidth), CitySearchProvider.GET_SEARCH_RESULT_SUCC), heightMeasureSpec);
            }
        }

        final void update() {
            Tab tab = this.mTab;
            View custom = tab.getCustomView();
            if (custom != null) {
                TabView customParent = custom.getParent();
                if (customParent != this) {
                    if (customParent != null) {
                        customParent.removeView(custom);
                    }
                    addView(custom);
                }
                this.mCustomView = custom;
                if (this.mTextView != null) {
                    this.mTextView.setVisibility(DetectedActivity.RUNNING);
                }
                if (this.mIconView != null) {
                    this.mIconView.setVisibility(DetectedActivity.RUNNING);
                    this.mIconView.setImageDrawable(null);
                }
                this.mCustomTextView = (TextView) custom.findViewById(16908308);
                this.mCustomIconView = (ImageView) custom.findViewById(16908294);
            } else {
                if (this.mCustomView != null) {
                    removeView(this.mCustomView);
                    this.mCustomView = null;
                }
                this.mCustomTextView = null;
                this.mCustomIconView = null;
            }
            if (this.mCustomView == null) {
                if (this.mIconView == null) {
                    ImageView iconView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.op_layout_tab_icon, this, false);
                    addView(iconView, MODE_SCROLLABLE);
                    this.mIconView = iconView;
                }
                if (this.mTextView == null) {
                    TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.op_layout_tab_text, this, false);
                    addView(textView);
                    this.mTextView = textView;
                }
                this.mTextView.setTextAppearance(getContext(), OPTabLayout.this.mTabTextAppearance);
                if (OPTabLayout.this.mTabTextColors != null) {
                    this.mTextView.setTextColor(OPTabLayout.this.mTabTextColors);
                }
                updateTextAndIcon(tab, this.mTextView, this.mIconView);
            } else if (this.mCustomTextView != null || this.mCustomIconView != null) {
                updateTextAndIcon(tab, this.mCustomTextView, this.mCustomIconView);
            }
        }

        private void updateTextAndIcon(Tab tab, TextView textView, ImageView iconView) {
            boolean hasText;
            Drawable icon = tab.getIcon();
            CharSequence text = tab.getText();
            if (iconView != null) {
                if (icon != null) {
                    iconView.setImageDrawable(icon);
                    iconView.setVisibility(MODE_SCROLLABLE);
                    setVisibility(MODE_SCROLLABLE);
                } else {
                    iconView.setVisibility(DetectedActivity.RUNNING);
                    iconView.setImageDrawable(null);
                }
                iconView.setContentDescription(tab.getContentDescription());
            }
            if (TextUtils.isEmpty(text)) {
                hasText = false;
            } else {
                hasText = true;
            }
            if (textView != null) {
                if (hasText) {
                    textView.setText(text);
                    textView.setContentDescription(tab.getContentDescription());
                    textView.setVisibility(MODE_SCROLLABLE);
                    setVisibility(MODE_SCROLLABLE);
                } else {
                    textView.setVisibility(DetectedActivity.RUNNING);
                    textView.setText(null);
                }
            }
            if (hasText || TextUtils.isEmpty(tab.getContentDescription())) {
                setOnLongClickListener(null);
                setLongClickable(false);
                return;
            }
            setOnLongClickListener(this);
        }

        public boolean onLongClick(View v) {
            int[] screenPos = new int[2];
            getLocationOnScreen(screenPos);
            Context context = getContext();
            int width = getWidth();
            int height = getHeight();
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            Toast cheatSheet = Toast.makeText(context, this.mTab.getContentDescription(), MODE_SCROLLABLE);
            cheatSheet.setGravity(net.oneplus.weather.R.styleable.AppCompatTheme_colorBackgroundFloating, (screenPos[0] + (width / 2)) - (screenWidth / 2), height);
            cheatSheet.show();
            return true;
        }

        public Tab getTab() {
            return this.mTab;
        }
    }

    public OPTabLayout(Context context) {
        this(context, null);
    }

    public OPTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.OPTabLayoutStyle);
    }

    public OPTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mTabs = new ArrayList();
        this.mTabMaxWidth = Integer.MAX_VALUE;
        this.fast_out_slow_in_interpolator = AnimationUtils.loadInterpolator(context, AndroidResources.FAST_OUT_SLOW_IN);
        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);
        this.mTabStrip = new SlidingTabStrip(context);
        addView(this.mTabStrip, ListPopupWindow.WRAP_CONTENT, -1);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OPTabLayout, defStyleAttr, R.style.Oneplus_Widget_Design_OPTabLayout);
        this.mTabStrip.setSelectedIndicatorHeight(a.getDimensionPixelSize(R.styleable.OPTabLayout_op_tabIndicatorHeight, MODE_SCROLLABLE));
        this.mTabStrip.setSelectedIndicatorColor(a.getColor(R.styleable.OPTabLayout_op_tabIndicatorColor, MODE_SCROLLABLE));
        this.mTabTextAppearance = a.getResourceId(R.styleable.OPTabLayout_op_tabTextAppearance, R.style.Oneplus_TextAppearance_Design_Tab);
        int dimensionPixelSize = a.getDimensionPixelSize(R.styleable.OPTabLayout_op_tabPadding, MODE_SCROLLABLE);
        this.mTabPaddingBottom = dimensionPixelSize;
        this.mTabPaddingEnd = dimensionPixelSize;
        this.mTabPaddingTop = dimensionPixelSize;
        this.mTabPaddingStart = dimensionPixelSize;
        this.mTabPaddingStart = a.getDimensionPixelSize(R.styleable.OPTabLayout_op_tabPaddingStart, this.mTabPaddingStart);
        this.mTabPaddingTop = a.getDimensionPixelSize(R.styleable.OPTabLayout_op_tabPaddingTop, this.mTabPaddingTop);
        this.mTabPaddingEnd = a.getDimensionPixelSize(R.styleable.OPTabLayout_op_tabPaddingEnd, this.mTabPaddingEnd);
        this.mTabPaddingBottom = a.getDimensionPixelSize(R.styleable.OPTabLayout_op_tabPaddingBottom, this.mTabPaddingBottom);
        this.mTabTextColors = loadTextColorFromTextAppearance(this.mTabTextAppearance);
        if (a.hasValue(R.styleable.OPTabLayout_op_tabTextColor)) {
            this.mTabTextColors = a.getColorStateList(R.styleable.OPTabLayout_op_tabTextColor);
        }
        if (a.hasValue(R.styleable.OPTabLayout_op_tabSelectedTextColor)) {
            this.mTabTextColors = createColorStateList(this.mTabTextColors.getDefaultColor(), a.getColor(R.styleable.OPTabLayout_op_tabSelectedTextColor, MODE_SCROLLABLE));
        }
        this.mTabMinWidth = a.getDimensionPixelSize(R.styleable.OPTabLayout_op_tabMinWidth, MODE_SCROLLABLE);
        this.mRequestedTabMaxWidth = a.getDimensionPixelSize(R.styleable.OPTabLayout_op_tabMaxWidth, MODE_SCROLLABLE);
        this.mTabBackgroundResId = a.getResourceId(R.styleable.OPTabLayout_op_tabBackground, MODE_SCROLLABLE);
        this.mContentInsetStart = a.getDimensionPixelSize(R.styleable.OPTabLayout_op_tabContentStart, MODE_SCROLLABLE);
        this.mTabHorizontalSpacing = a.getDimensionPixelSize(R.styleable.OPTabLayout_op_horizontalSpacing, MODE_SCROLLABLE);
        this.mMode = a.getInt(R.styleable.OPTabLayout_op_tabMode, MODE_FIXED);
        this.mTabGravity = a.getInt(R.styleable.OPTabLayout_op_tabGravity, MODE_SCROLLABLE);
        a.recycle();
        applyModeAndGravity();
    }

    public void setSelectedTabIndicatorColor(int color) {
        this.mTabStrip.setSelectedIndicatorColor(color);
    }

    public void setSelectedTabIndicatorHeight(int height) {
        this.mTabStrip.setSelectedIndicatorHeight(height);
    }

    public void setScrollPosition(int position, float positionOffset, boolean updateSelectedText) {
        if ((this.mIndicatorAnimator == null || !this.mIndicatorAnimator.isRunning()) && position >= 0 && position < this.mTabStrip.getChildCount()) {
            this.mTabStrip.setIndicatorPositionFromTabPosition(position, positionOffset);
            scrollTo(calculateScrollXForTab(position, positionOffset), MODE_SCROLLABLE);
            if (updateSelectedText) {
                setSelectedTabView(Math.round(((float) position) + positionOffset));
            }
        }
    }

    public void addTab(Tab tab) {
        addTab(tab, this.mTabs.isEmpty());
    }

    public void addTab(Tab tab, int position) {
        addTab(tab, position, this.mTabs.isEmpty());
    }

    public void addTab(Tab tab, boolean setSelected) {
        if (tab.mParent != this) {
            throw new IllegalArgumentException("Tab belongs to a different OPTabLayout.");
        }
        addTabView(tab, setSelected);
        configureTab(tab, this.mTabs.size());
        if (setSelected) {
            tab.select();
        }
    }

    public void addTab(Tab tab, int position, boolean setSelected) {
        if (tab.mParent != this) {
            throw new IllegalArgumentException("Tab belongs to a different OPTabLayout.");
        }
        addTabView(tab, position, setSelected);
        configureTab(tab, position);
        if (setSelected) {
            tab.select();
        }
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.mOnTabSelectedListener = onTabSelectedListener;
    }

    public Tab newTab() {
        return new Tab(this);
    }

    public int getTabCount() {
        return this.mTabs.size();
    }

    public Tab getTabAt(int index) {
        return (Tab) this.mTabs.get(index);
    }

    public int getSelectedTabPosition() {
        return this.mSelectedTab != null ? this.mSelectedTab.getPosition() : -1;
    }

    public void removeTab(Tab tab) {
        if (tab.mParent != this) {
            throw new IllegalArgumentException("Tab does not belong to this OPTabLayout.");
        }
        removeTabAt(tab.getPosition());
    }

    public void removeTabAt(int position) {
        int selectedTabPosition;
        if (this.mSelectedTab != null) {
            selectedTabPosition = this.mSelectedTab.getPosition();
        } else {
            selectedTabPosition = 0;
        }
        removeTabViewAt(position);
        Tab removedTab = (Tab) this.mTabs.remove(position);
        if (removedTab != null) {
            removedTab.setPosition(-1);
        }
        int newTabCount = this.mTabs.size();
        for (int i = position; i < newTabCount; i++) {
            ((Tab) this.mTabs.get(i)).setPosition(i);
        }
        if (selectedTabPosition == position) {
            selectTab(this.mTabs.isEmpty() ? null : (Tab) this.mTabs.get(Math.max(MODE_SCROLLABLE, position - 1)));
        }
    }

    public void removeAllTabs() {
        this.mTabStrip.removeAllViews();
        Iterator<Tab> i = this.mTabs.iterator();
        while (i.hasNext()) {
            ((Tab) i.next()).setPosition(-1);
            i.remove();
        }
        this.mSelectedTab = null;
    }

    public void setTabMode(int mode) {
        if (mode != this.mMode) {
            this.mMode = mode;
            applyModeAndGravity();
        }
    }

    public int getTabMode() {
        return this.mMode;
    }

    public void setTabGravity(int gravity) {
        if (this.mTabGravity != gravity) {
            this.mTabGravity = gravity;
            applyModeAndGravity();
        }
    }

    public int getTabGravity() {
        return this.mTabGravity;
    }

    public void setTabTextColors(ColorStateList textColor) {
        if (this.mTabTextColors != textColor) {
            this.mTabTextColors = textColor;
            updateAllTabs();
        }
    }

    public ColorStateList getTabTextColors() {
        return this.mTabTextColors;
    }

    public void setTabTextColors(int normalColor, int selectedColor) {
        setTabTextColors(createColorStateList(normalColor, selectedColor));
    }

    private void updateAllTabs() {
        int z = this.mTabStrip.getChildCount();
        for (int i = MODE_SCROLLABLE; i < z; i++) {
            updateTab(i);
        }
    }

    private TabView createTabView(Tab tab) {
        TabView tabView = new TabView(getContext(), tab);
        tabView.setFocusable(true);
        if (this.mTabClickListener == null) {
            this.mTabClickListener = new OnClickListener() {
                public void onClick(View view) {
                    ((TabView) view).getTab().select();
                }
            };
        }
        tabView.setOnClickListener(this.mTabClickListener);
        return tabView;
    }

    private void configureTab(Tab tab, int position) {
        tab.setPosition(position);
        this.mTabs.add(position, tab);
        int count = this.mTabs.size();
        for (int i = position + 1; i < count; i++) {
            ((Tab) this.mTabs.get(i)).setPosition(i);
        }
    }

    private void updateTab(int position) {
        TabView view = (TabView) this.mTabStrip.getChildAt(position);
        if (view != null) {
            view.update();
        }
    }

    private void addTabView(Tab tab, boolean setSelected) {
        TabView tabView = createTabView(tab);
        this.mTabStrip.addView(tabView, createLayoutParamsForTabs());
        updateTabViewsMargin();
        if (setSelected) {
            tabView.setSelected(true);
        }
    }

    private void addTabView(Tab tab, int position, boolean setSelected) {
        TabView tabView = createTabView(tab);
        this.mTabStrip.addView(tabView, position, createLayoutParamsForTabs());
        updateTabViewsMargin();
        if (setSelected) {
            tabView.setSelected(true);
        }
    }

    private void updateTabViewsMargin() {
        if (this.mTabStrip.getChildCount() > 0) {
            ((LayoutParams) this.mTabStrip.getChildAt(MODE_SCROLLABLE).getLayoutParams()).setMarginStart(MODE_SCROLLABLE);
        }
    }

    private LayoutParams createLayoutParamsForTabs() {
        LayoutParams lp = new LayoutParams(-2, -1);
        lp.setMarginStart(this.mTabHorizontalSpacing);
        updateTabViewLayoutParams(lp);
        return lp;
    }

    private void updateTabViewLayoutParams(LayoutParams lp) {
        if (this.mMode == 1 && this.mTabGravity == 0) {
            lp.width = 0;
            lp.weight = 1.0f;
            return;
        }
        lp.width = -2;
        lp.weight = 0.0f;
    }

    private int dpToPx(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * ((float) dps));
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int idealHeight = (getContext().getResources().getDimensionPixelSize(R.dimen.tab_layout_default_height_material) + getPaddingTop()) + getPaddingBottom();
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case CitySearchProvider.GET_SEARCH_RESULT_FAIL:
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.min(idealHeight, MeasureSpec.getSize(heightMeasureSpec)), CitySearchProvider.GET_SEARCH_RESULT_SUCC);
                break;
            case MODE_SCROLLABLE:
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(idealHeight, CitySearchProvider.GET_SEARCH_RESULT_SUCC);
                break;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mMode == 1 && getChildCount() == 1) {
            View child = getChildAt(MODE_SCROLLABLE);
            int width = getMeasuredWidth();
            if (child.getMeasuredWidth() > width) {
                child.measure(MeasureSpec.makeMeasureSpec(width, CitySearchProvider.GET_SEARCH_RESULT_SUCC), getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), child.getLayoutParams().height));
            }
        }
        int maxTabWidth = this.mRequestedTabMaxWidth;
        int defaultTabMaxWidth = getMeasuredWidth() - dpToPx(TAB_MIN_WIDTH_MARGIN);
        if (maxTabWidth == 0 || maxTabWidth > defaultTabMaxWidth) {
            maxTabWidth = defaultTabMaxWidth;
        }
        if (this.mTabMaxWidth != maxTabWidth) {
            this.mTabMaxWidth = maxTabWidth;
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void removeTabViewAt(int position) {
        this.mTabStrip.removeViewAt(position);
        updateTabViewsMargin();
        requestLayout();
    }

    private void animateToTab(int newPosition) {
        if (newPosition != -1) {
            if (getWindowToken() == null || !isLaidOut() || this.mTabStrip.childrenNeedLayout()) {
                setScrollPosition(newPosition, AutoScrollHelper.RELATIVE_UNSPECIFIED, true);
                return;
            }
            if (getScrollX() != calculateScrollXForTab(newPosition, AutoScrollHelper.RELATIVE_UNSPECIFIED)) {
                if (this.mScrollAnimator == null) {
                    this.mScrollAnimator = new ValueAnimator();
                    this.mScrollAnimator.setInterpolator(this.fast_out_slow_in_interpolator);
                    this.mScrollAnimator.setDuration(300);
                    this.mScrollAnimator.addUpdateListener(new AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            OPTabLayout.this.scrollTo(((Integer) animation.getAnimatedValue()).intValue(), MODE_SCROLLABLE);
                        }
                    });
                }
                this.mScrollAnimator.setIntValues(new int[]{startScrollX, targetScrollX});
                this.mScrollAnimator.start();
            }
            this.mTabStrip.animateIndicatorToPosition(newPosition, ANIMATION_DURATION);
        }
    }

    private void setSelectedTabView(int position) {
        int tabCount = this.mTabStrip.getChildCount();
        if (position < tabCount && !this.mTabStrip.getChildAt(position).isSelected()) {
            int i = MODE_SCROLLABLE;
            while (i < tabCount) {
                this.mTabStrip.getChildAt(i).setSelected(i == position);
                i++;
            }
        }
    }

    void selectTab(Tab tab) {
        selectTab(tab, true);
    }

    void selectTab(Tab tab, boolean updateIndicator) {
        if (this.mSelectedTab != tab) {
            int newPosition;
            if (tab != null) {
                newPosition = tab.getPosition();
            } else {
                newPosition = -1;
            }
            setSelectedTabView(newPosition);
            if (updateIndicator) {
                if ((this.mSelectedTab == null || this.mSelectedTab.getPosition() == -1) && newPosition != -1) {
                    setScrollPosition(newPosition, AutoScrollHelper.RELATIVE_UNSPECIFIED, true);
                } else {
                    animateToTab(newPosition);
                }
            }
            if (!(this.mSelectedTab == null || this.mOnTabSelectedListener == null)) {
                this.mOnTabSelectedListener.onTabUnselected(this.mSelectedTab);
            }
            this.mSelectedTab = tab;
            if (this.mSelectedTab != null && this.mOnTabSelectedListener != null) {
                this.mOnTabSelectedListener.onTabSelected(this.mSelectedTab);
            }
        } else if (this.mSelectedTab != null) {
            if (this.mOnTabSelectedListener != null) {
                this.mOnTabSelectedListener.onTabReselected(this.mSelectedTab);
            }
            animateToTab(tab.getPosition());
        }
    }

    private int calculateScrollXForTab(int position, float positionOffset) {
        int nextWidth = MODE_SCROLLABLE;
        if (this.mMode != 0) {
            return MODE_SCROLLABLE;
        }
        int selectedWidth;
        View selectedChild = this.mTabStrip.getChildAt(position);
        View nextChild = position + 1 < this.mTabStrip.getChildCount() ? this.mTabStrip.getChildAt(position + 1) : null;
        if (selectedChild != null) {
            selectedWidth = selectedChild.getWidth();
        } else {
            selectedWidth = 0;
        }
        if (nextChild != null) {
            nextWidth = nextChild.getWidth();
        }
        return ((selectedChild.getLeft() + ((int) ((((float) (selectedWidth + nextWidth)) * positionOffset) * 0.5f))) + (selectedChild.getWidth() / 2)) - (getWidth() / 2);
    }

    private void applyModeAndGravity() {
        int paddingStart = MODE_SCROLLABLE;
        if (this.mMode == 0) {
            paddingStart = Math.max(MODE_SCROLLABLE, this.mContentInsetStart - this.mTabPaddingStart);
        }
        this.mTabStrip.setPaddingRelative(paddingStart, MODE_SCROLLABLE, MODE_SCROLLABLE, MODE_SCROLLABLE);
        switch (this.mMode) {
            case MODE_SCROLLABLE:
                this.mTabStrip.setGravity(GravityCompat.START);
                break;
            case MODE_FIXED:
                this.mTabStrip.setGravity(MODE_FIXED);
                break;
        }
        updateTabViewsLayoutParams();
    }

    private void updateTabViewsLayoutParams() {
        for (int i = MODE_SCROLLABLE; i < this.mTabStrip.getChildCount(); i++) {
            View child = this.mTabStrip.getChildAt(i);
            updateTabViewLayoutParams((LayoutParams) child.getLayoutParams());
            child.requestLayout();
        }
    }

    private static ColorStateList createColorStateList(int defaultColor, int selectedColor) {
        states = new int[2][];
        int[] colors = new int[]{SELECTED_STATE_SET, selectedColor};
        int i = 0 + 1;
        states[i] = EMPTY_STATE_SET;
        colors[i] = defaultColor;
        i++;
        return new ColorStateList(states, colors);
    }

    private ColorStateList loadTextColorFromTextAppearance(int textAppearanceResId) {
        TypedArray a = getContext().obtainStyledAttributes(textAppearanceResId, R.styleable.TextAppearance);
        ColorStateList colorStateList = a.getColorStateList(R.styleable.TextAppearance_android_textColor);
        a.recycle();
        return colorStateList;
    }
}
