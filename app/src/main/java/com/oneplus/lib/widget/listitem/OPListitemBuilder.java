package com.oneplus.lib.widget.listitem;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.oneplus.commonctrl.R;
import net.oneplus.weather.provider.CitySearchProvider;

public final class OPListitemBuilder {
    private static final boolean DEBUG = false;
    private static final String TAG = "OPListitem";
    private boolean mActionButtonEnabled;
    private Context mContext;
    private boolean mIconEnabled;
    private boolean mPrimaryTextEnabled;
    private boolean mSecondaryTextEnabled;
    private boolean mStampEnabled;

    private class OPListitemImpl extends OPListitem {
        private int mActionBtnSize;
        private ImageView mActionButton;
        private Context mContext;
        private ImageView mIcon;
        private int mIconSize;
        private int mMarginM1;
        private TextView mPrimaryText;
        private int mRemainHeight;
        private Resources mResources;
        private TextView mSecondaryText;
        private TextView mStamp;

        public OPListitemImpl(Context context) {
            super(context);
            this.mResources = null;
            this.mIcon = null;
            this.mPrimaryText = null;
            this.mSecondaryText = null;
            this.mStamp = null;
            this.mActionButton = null;
            this.mIconSize = -1;
            this.mActionBtnSize = -1;
            this.mMarginM1 = 0;
            this.mRemainHeight = 0;
            this.mContext = null;
            this.mContext = context;
            init();
        }

        private void init() {
            if (this.mContext != null) {
                this.mResources = this.mContext.getResources();
                this.mMarginM1 = this.mResources.getDimensionPixelOffset(R.dimen.margin_m1);
                if (OPListitemBuilder.this.mIconEnabled) {
                    this.mIcon = new ImageView(this.mContext);
                    this.mIconSize = this.mResources.getDimensionPixelOffset(R.dimen.listitem_icon_size);
                    this.mIcon.setLayoutParams(new LayoutParams(this.mIconSize, this.mIconSize));
                    addView(this.mIcon);
                }
                if (OPListitemBuilder.this.mPrimaryTextEnabled) {
                    this.mPrimaryText = new TextView(this.mContext, null, 0, R.style.listitem_primary_text_font);
                    this.mPrimaryText.setLayoutParams(new LayoutParams(-2, -2));
                    addView(this.mPrimaryText);
                }
                if (OPListitemBuilder.this.mSecondaryTextEnabled) {
                    this.mSecondaryText = new TextView(this.mContext, null, 0, R.style.listitem_secondary_text_font);
                    this.mSecondaryText.setLayoutParams(new LayoutParams(-2, -2));
                    addView(this.mSecondaryText);
                }
                if (OPListitemBuilder.this.mStampEnabled) {
                    this.mStamp = new TextView(this.mContext, null, 0, R.style.listitem_stamp_font);
                    this.mStamp.setLayoutParams(new LayoutParams(-2, -2));
                    addView(this.mStamp);
                }
                if (OPListitemBuilder.this.mActionButtonEnabled) {
                    this.mActionButton = new ImageView(this.mContext);
                    this.mActionBtnSize = this.mResources.getDimensionPixelOffset(R.dimen.listitem_actionbutton_size);
                    this.mActionButton.setLayoutParams(new LayoutParams(this.mActionBtnSize, this.mActionBtnSize));
                    addView(this.mActionButton);
                }
            }
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
            int itemWidth = MeasureSpec.getSize(widthMeasureSpec);
            int itemHeight = MeasureSpec.getSize(heightMeasureSpec);
            int remaindWidth = itemWidth;
            int remainHeight = itemHeight;
            if (this.mIcon != null) {
                this.mIcon.measure(MeasureSpec.makeMeasureSpec(this.mIconSize, CitySearchProvider.GET_SEARCH_RESULT_SUCC), MeasureSpec.makeMeasureSpec(this.mIconSize, CitySearchProvider.GET_SEARCH_RESULT_SUCC));
                remaindWidth = (remaindWidth - this.mIconSize) - this.mMarginM1;
            }
            if (this.mActionButton != null) {
                this.mActionButton.measure(MeasureSpec.makeMeasureSpec(this.mActionBtnSize, CitySearchProvider.GET_SEARCH_RESULT_SUCC), MeasureSpec.makeMeasureSpec(this.mActionBtnSize, CitySearchProvider.GET_SEARCH_RESULT_SUCC));
                remaindWidth = (remaindWidth - this.mActionBtnSize) - this.mMarginM1;
            }
            if (this.mStamp != null) {
                this.mStamp.measure(MeasureSpec.makeMeasureSpec(remaindWidth, CitySearchProvider.GET_SEARCH_RESULT_FAIL), MeasureSpec.makeMeasureSpec(itemHeight, CitySearchProvider.GET_SEARCH_RESULT_FAIL));
                remaindWidth = (remaindWidth - this.mStamp.getMeasuredWidth()) - this.mMarginM1;
            }
            if (this.mPrimaryText != null) {
                this.mPrimaryText.measure(MeasureSpec.makeMeasureSpec(remaindWidth - (this.mMarginM1 * 2), CitySearchProvider.GET_SEARCH_RESULT_FAIL), MeasureSpec.makeMeasureSpec(remainHeight, CitySearchProvider.GET_SEARCH_RESULT_FAIL));
                remainHeight -= this.mPrimaryText.getMeasuredHeight();
            }
            if (this.mSecondaryText != null) {
                if (this.mStamp != null) {
                    remaindWidth += this.mStamp.getMeasuredWidth();
                }
                this.mSecondaryText.measure(MeasureSpec.makeMeasureSpec(remaindWidth - (this.mMarginM1 * 2), CitySearchProvider.GET_SEARCH_RESULT_FAIL), MeasureSpec.makeMeasureSpec(remainHeight, CitySearchProvider.GET_SEARCH_RESULT_FAIL));
                remainHeight -= this.mSecondaryText.getMeasuredHeight();
            }
            this.mRemainHeight = remainHeight;
        }

        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            layoutLTR(l, t, r, b);
        }

        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
        }

        private void layoutLTR(int l, int t, int r, int b) {
            int itemheight = b - t;
            int currentLeft = l;
            if (this.mIcon != null) {
                int left = l + this.mMarginM1;
                int top = (itemheight - this.mIconSize) / 2;
                int right = left + this.mIconSize;
                this.mIcon.layout(left, top, right, top + this.mIconSize);
                currentLeft = right;
            }
            if (this.mActionButton != null) {
                right = r - this.mMarginM1;
                top = (itemheight - this.mActionBtnSize) / 2;
                this.mActionButton.layout(right - this.mActionBtnSize, top, right, top + this.mActionBtnSize);
            }
            if (this.mSecondaryText != null) {
                int bottom = itemheight - (this.mRemainHeight / 2);
                left = currentLeft + this.mMarginM1;
                this.mSecondaryText.layout(left, bottom - this.mSecondaryText.getMeasuredHeight(), left + this.mSecondaryText.getMeasuredWidth(), bottom);
            }
            if (this.mPrimaryText != null) {
                left = currentLeft + this.mMarginM1;
                top = this.mRemainHeight / 2;
                this.mPrimaryText.layout(left, top, left + this.mPrimaryText.getMeasuredWidth(), top + this.mPrimaryText.getMeasuredHeight());
            }
            if (this.mStamp != null) {
                right = r - this.mMarginM1;
                left = right - this.mStamp.getMeasuredWidth();
                if (this.mSecondaryText != null) {
                    top = (this.mRemainHeight / 2) + ((this.mPrimaryText.getMeasuredHeight() - this.mStamp.getMeasuredHeight()) / 2);
                } else {
                    top = (itemheight - this.mStamp.getMeasuredHeight()) / 2;
                }
                this.mStamp.layout(left, top, right, top + this.mStamp.getMeasuredHeight());
            }
        }

        public TextView getPrimaryText() {
            return this.mPrimaryText;
        }

        public TextView getSecondaryText() {
            return this.mSecondaryText;
        }

        public TextView getStamp() {
            return this.mStamp;
        }

        public ImageView getIcon() {
            return this.mIcon;
        }

        public ImageView getActionButton() {
            return this.mActionButton;
        }
    }

    public OPListitemBuilder(Context context) {
        this.mContext = null;
        this.mIconEnabled = false;
        this.mPrimaryTextEnabled = false;
        this.mSecondaryTextEnabled = false;
        this.mStampEnabled = false;
        this.mActionButtonEnabled = false;
        this.mContext = context;
    }

    public OPListitem build() {
        OPListitemImpl item = new OPListitemImpl(this.mContext);
        item.setLayoutParams(new AbsListView.LayoutParams(-1, 216));
        return item;
    }

    public OPListitemBuilder setIconEnabled(boolean enabled) {
        this.mIconEnabled = enabled;
        return this;
    }

    public OPListitemBuilder setPrimaryTextEnabled(boolean enabled) {
        this.mPrimaryTextEnabled = enabled;
        return this;
    }

    public OPListitemBuilder setSecondaryTextEnabled(boolean enabled) {
        this.mSecondaryTextEnabled = enabled;
        return this;
    }

    public OPListitemBuilder setStampEnabled(boolean enabled) {
        this.mStampEnabled = enabled;
        this.mActionButtonEnabled = !enabled ? true : DEBUG;
        return this;
    }

    public OPListitemBuilder setActionButtonEnabled(boolean enabled) {
        this.mActionButtonEnabled = enabled;
        this.mStampEnabled = !enabled ? true : DEBUG;
        return this;
    }

    public OPListitemBuilder reset() {
        this.mIconEnabled = false;
        this.mPrimaryTextEnabled = false;
        this.mSecondaryTextEnabled = false;
        this.mStampEnabled = false;
        this.mActionButtonEnabled = false;
        return this;
    }
}
