package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuBuilder.ItemInvoker;
import android.support.v7.view.menu.MenuView.ItemView;
import android.support.v7.widget.ActionMenuView.ActionMenuChildView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ForwardingListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.provider.CitySearchProvider;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ActionMenuItemView extends AppCompatTextView implements ItemView, OnClickListener, ActionMenuChildView {
    private static final int MAX_ICON_SIZE = 32;
    private static final String TAG = "ActionMenuItemView";
    private boolean mAllowTextWithIcon;
    private boolean mExpandedFormat;
    private ForwardingListener mForwardingListener;
    private Drawable mIcon;
    MenuItemImpl mItemData;
    ItemInvoker mItemInvoker;
    private int mMaxIconSize;
    private int mMinWidth;
    PopupCallback mPopupCallback;
    private int mSavedPaddingLeft;
    private CharSequence mTitle;

    public static abstract class PopupCallback {
        public abstract ShowableListMenu getPopup();
    }

    private class ActionMenuItemForwardingListener extends ForwardingListener {
        public ActionMenuItemForwardingListener() {
            super(ActionMenuItemView.this);
        }

        public ShowableListMenu getPopup() {
            return ActionMenuItemView.this.mPopupCallback != null ? ActionMenuItemView.this.mPopupCallback.getPopup() : null;
        }

        protected boolean onForwardingStarted() {
            if (ActionMenuItemView.this.mItemInvoker == null || !ActionMenuItemView.this.mItemInvoker.invokeItem(ActionMenuItemView.this.mItemData)) {
                return false;
            }
            ShowableListMenu popup = getPopup();
            return popup != null && popup.isShowing();
        }
    }

    public ActionMenuItemView(Context context) {
        this(context, null);
    }

    public ActionMenuItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Resources res = context.getResources();
        this.mAllowTextWithIcon = shouldAllowTextWithIcon();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ActionMenuItemView, defStyle, 0);
        this.mMinWidth = a.getDimensionPixelSize(R.styleable.ActionMenuItemView_android_minWidth, 0);
        a.recycle();
        this.mMaxIconSize = (int) ((32.0f * res.getDisplayMetrics().density) + 0.5f);
        setOnClickListener(this);
        this.mSavedPaddingLeft = -1;
        setSaveEnabled(false);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mAllowTextWithIcon = shouldAllowTextWithIcon();
        updateTextButtonVisibility();
    }

    private boolean shouldAllowTextWithIcon() {
        Configuration config = getContext().getResources().getConfiguration();
        int widthDp = config.screenWidthDp;
        int heightDp = config.screenHeightDp;
        if (widthDp < 480) {
            if ((widthDp < 640 || heightDp < 480) && config.orientation != 2) {
                return false;
            }
        }
        return true;
    }

    public void setPadding(int l, int t, int r, int b) {
        this.mSavedPaddingLeft = l;
        super.setPadding(l, t, r, b);
    }

    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    public void initialize(MenuItemImpl itemData, int menuType) {
        this.mItemData = itemData;
        setIcon(itemData.getIcon());
        setTitle(itemData.getTitleForItemView(this));
        setId(itemData.getItemId());
        setVisibility(itemData.isVisible() ? 0 : DetectedActivity.RUNNING);
        setEnabled(itemData.isEnabled());
        if (itemData.hasSubMenu() && this.mForwardingListener == null) {
            this.mForwardingListener = new ActionMenuItemForwardingListener();
        }
    }

    public boolean onTouchEvent(MotionEvent e) {
        return (this.mItemData.hasSubMenu() && this.mForwardingListener != null && this.mForwardingListener.onTouch(this, e)) ? true : super.onTouchEvent(e);
    }

    public void onClick(View v) {
        if (this.mItemInvoker != null) {
            this.mItemInvoker.invokeItem(this.mItemData);
        }
    }

    public void setItemInvoker(ItemInvoker invoker) {
        this.mItemInvoker = invoker;
    }

    public void setPopupCallback(PopupCallback popupCallback) {
        this.mPopupCallback = popupCallback;
    }

    public boolean prefersCondensedTitle() {
        return true;
    }

    public void setCheckable(boolean checkable) {
    }

    public void setChecked(boolean checked) {
    }

    public void setExpandedFormat(boolean expandedFormat) {
        if (this.mExpandedFormat != expandedFormat) {
            this.mExpandedFormat = expandedFormat;
            if (this.mItemData != null) {
                this.mItemData.actionFormatChanged();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateTextButtonVisibility() {
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.view.menu.ActionMenuItemView.updateTextButtonVisibility():void");
        /*
        this = this;
        r3 = 1;
        r4 = 0;
        r5 = 0;
        r6 = r7.mTitle;
        r6 = android.text.TextUtils.isEmpty(r6);
        if (r6 != 0) goto L_0x004d;
    L_0x000b:
        r2 = r3;
    L_0x000c:
        r6 = r7.mIcon;
        if (r6 == 0) goto L_0x0020;
    L_0x0010:
        r6 = r7.mItemData;
        r6 = r6.showsTextAsAction();
        if (r6 == 0) goto L_0x0021;
    L_0x0018:
        r6 = r7.mAllowTextWithIcon;
        if (r6 != 0) goto L_0x0020;
    L_0x001c:
        r6 = r7.mExpandedFormat;
        if (r6 == 0) goto L_0x0021;
    L_0x0020:
        r4 = r3;
    L_0x0021:
        r2 = r2 & r4;
        if (r2 == 0) goto L_0x004f;
    L_0x0024:
        r3 = r7.mTitle;
    L_0x0026:
        r7.setText(r3);
        r3 = r7.mItemData;
        r0 = r3.getContentDescription();
        r3 = android.text.TextUtils.isEmpty(r0);
        if (r3 == 0) goto L_0x0058;
    L_0x0035:
        if (r2 == 0) goto L_0x0051;
    L_0x0037:
        r3 = r5;
    L_0x0038:
        r7.setContentDescription(r3);
    L_0x003b:
        r3 = r7.mItemData;
        r1 = r3.getTooltipText();
        r3 = android.text.TextUtils.isEmpty(r1);
        if (r3 == 0) goto L_0x0063;
    L_0x0047:
        if (r2 == 0) goto L_0x005c;
    L_0x0049:
        android.support.v7.widget.TooltipCompat.setTooltipText(r7, r5);
    L_0x004c:
        return;
    L_0x004d:
        r2 = r4;
        goto L_0x000c;
    L_0x004f:
        r3 = r5;
        goto L_0x0026;
    L_0x0051:
        r3 = r7.mItemData;
        r3 = r3.getTitle();
        goto L_0x0038;
    L_0x0058:
        r7.setContentDescription(r0);
        goto L_0x003b;
    L_0x005c:
        r3 = r7.mItemData;
        r5 = r3.getTitle();
        goto L_0x0049;
    L_0x0063:
        android.support.v7.widget.TooltipCompat.setTooltipText(r7, r1);
        goto L_0x004c;
        */
    }

    public void setIcon(Drawable icon) {
        this.mIcon = icon;
        if (icon != null) {
            float scale;
            int width = icon.getIntrinsicWidth();
            int height = icon.getIntrinsicHeight();
            if (width > this.mMaxIconSize) {
                scale = ((float) this.mMaxIconSize) / ((float) width);
                width = this.mMaxIconSize;
                height = (int) (((float) height) * scale);
            }
            if (height > this.mMaxIconSize) {
                scale = ((float) this.mMaxIconSize) / ((float) height);
                height = this.mMaxIconSize;
                width = (int) (((float) width) * scale);
            }
            icon.setBounds(0, 0, width, height);
        }
        setCompoundDrawables(icon, null, null, null);
        updateTextButtonVisibility();
    }

    public boolean hasText() {
        return !TextUtils.isEmpty(getText());
    }

    public void setShortcut(boolean showShortcut, char shortcutKey) {
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
        updateTextButtonVisibility();
    }

    public boolean showsIcon() {
        return true;
    }

    public boolean needsDividerBefore() {
        return hasText() && this.mItemData.getIcon() == null;
    }

    public boolean needsDividerAfter() {
        return hasText();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean textVisible = hasText();
        if (textVisible && this.mSavedPaddingLeft >= 0) {
            super.setPadding(this.mSavedPaddingLeft, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int oldMeasuredWidth = getMeasuredWidth();
        int targetWidth = widthMode == Integer.MIN_VALUE ? Math.min(widthSize, this.mMinWidth) : this.mMinWidth;
        if (widthMode != 1073741824 && this.mMinWidth > 0 && oldMeasuredWidth < targetWidth) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(targetWidth, CitySearchProvider.GET_SEARCH_RESULT_SUCC), heightMeasureSpec);
        }
        if (!textVisible && this.mIcon != null) {
            super.setPadding((getMeasuredWidth() - this.mIcon.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
    }

    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(null);
    }
}
