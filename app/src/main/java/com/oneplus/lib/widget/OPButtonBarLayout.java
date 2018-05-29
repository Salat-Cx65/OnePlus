package com.oneplus.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import com.google.android.gms.location.DetectedActivity;
import com.oneplus.commonctrl.R;
import net.oneplus.weather.provider.CitySearchProvider;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class OPButtonBarLayout extends LinearLayout {
    private boolean mAllowStacking;
    private int mLastWidthSize;

    public OPButtonBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mLastWidthSize = -1;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.OPButtonBarLayout);
        this.mAllowStacking = ta.getBoolean(R.styleable.OPButtonBarLayout_op_allowStacking, true);
        ta.recycle();
    }

    public void setAllowStacking(boolean allowStacking) {
        if (this.mAllowStacking != allowStacking) {
            this.mAllowStacking = allowStacking;
            if (!this.mAllowStacking && getOrientation() == 1) {
                setStacked(false);
            }
            requestLayout();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int initialWidthMeasureSpec;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (this.mAllowStacking) {
            if (widthSize > this.mLastWidthSize && isStacked()) {
                setStacked(false);
            }
            this.mLastWidthSize = widthSize;
        }
        boolean z = false;
        if (isStacked() || MeasureSpec.getMode(widthMeasureSpec) != 1073741824) {
            initialWidthMeasureSpec = widthMeasureSpec;
        } else {
            initialWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, CitySearchProvider.GET_SEARCH_RESULT_FAIL);
            z = true;
        }
        super.onMeasure(initialWidthMeasureSpec, heightMeasureSpec);
        if (this.mAllowStacking && !isStacked() && (getMeasuredWidthAndState() & -16777216) == 16777216) {
            setStacked(true);
            z = true;
        }
        if (z) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void setStacked(boolean stacked) {
        setOrientation(stacked ? 1 : 0);
        setGravity(stacked ? RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER : net.oneplus.weather.R.styleable.AppCompatTheme_panelMenuListTheme);
        if (stacked) {
            setPadding(0, 0, 0, getContext().getResources().getDimensionPixelSize(R.dimen.oneplus_contorl_layout_margin_bottom1));
        } else {
            setPadding(getContext().getResources().getDimensionPixelSize(R.dimen.oneplus_contorl_layout_margin_top1), getContext().getResources().getDimensionPixelSize(R.dimen.oneplus_contorl_layout_margin_right1), getContext().getResources().getDimensionPixelSize(R.dimen.oneplus_contorl_layout_margin_bottom1), getContext().getResources().getDimensionPixelSize(R.dimen.oneplus_contorl_layout_margin_left1));
        }
        View spacer = findViewById(R.id.spacer);
        if (spacer != null) {
            spacer.setVisibility(stacked ? DetectedActivity.RUNNING : RainSurfaceView.RAIN_LEVEL_RAINSTORM);
        }
        View spacer2 = findViewById(R.id.spacer2);
        if (spacer2 != null) {
            spacer2.setVisibility(stacked ? DetectedActivity.RUNNING : RainSurfaceView.RAIN_LEVEL_RAINSTORM);
        }
        View button1 = findViewById(16908313);
        if (button1 != null) {
            button1.setMinimumHeight(stacked ? getContext().getResources().getDimensionPixelSize(R.dimen.oneplus_contorl_list_item_height_one_line1) : (int) TypedValue.applyDimension(1, 36.0f, getContext().getResources().getDisplayMetrics()));
        }
        View button2 = findViewById(16908314);
        if (button2 != null) {
            button2.setMinimumHeight(stacked ? getContext().getResources().getDimensionPixelSize(R.dimen.oneplus_contorl_list_item_height_one_line1) : (int) TypedValue.applyDimension(1, 36.0f, getContext().getResources().getDisplayMetrics()));
        }
        View button3 = findViewById(16908315);
        if (button3 != null) {
            button3.setMinimumHeight(stacked ? getContext().getResources().getDimensionPixelSize(R.dimen.oneplus_contorl_list_item_height_one_line1) : (int) TypedValue.applyDimension(1, 36.0f, getContext().getResources().getDisplayMetrics()));
        }
        for (int i = getChildCount() - 2; i >= 0; i--) {
            bringChildToFront(getChildAt(i));
        }
    }

    private boolean isStacked() {
        return getOrientation() == 1;
    }
}
