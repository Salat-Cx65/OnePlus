package net.oneplus.weather.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class WeatherScrollView extends ScrollView {
    private static final int ANIM_TIME = 200;
    private float MOVE_FACTOR;
    private boolean canPullDown;
    private boolean canPullUp;
    private View contentView;
    private boolean isMoved;
    ScrollViewListener mScrollViewListener;
    private Rect originalRect;
    private float startY;

    public static interface ScrollViewListener {
        void onScrollChanged(WeatherScrollView weatherScrollView, int i, int i2, int i3, int i4);
    }

    public WeatherScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.MOVE_FACTOR = 0.3f;
        this.originalRect = new Rect();
        this.canPullDown = false;
        this.canPullUp = false;
        this.isMoved = false;
    }

    public WeatherScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.MOVE_FACTOR = 0.3f;
        this.originalRect = new Rect();
        this.canPullDown = false;
        this.canPullUp = false;
        this.isMoved = false;
    }

    public WeatherScrollView(Context context) {
        super(context);
        this.MOVE_FACTOR = 0.3f;
        this.originalRect = new Rect();
        this.canPullDown = false;
        this.canPullUp = false;
        this.isMoved = false;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            this.contentView = getChildAt(0);
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.contentView != null) {
            this.originalRect.set(this.contentView.getLeft(), this.contentView.getTop(), this.contentView.getRight(), this.contentView.getBottom());
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean dispatchTouchEvent(android.view.MotionEvent r13_ev) {
        throw new UnsupportedOperationException("Method not decompiled: net.oneplus.weather.widget.WeatherScrollView.dispatchTouchEvent(android.view.MotionEvent):boolean");
        /*
        this = this;
        r6 = 1;
        r8 = 0;
        r5 = 0;
        r7 = r12.contentView;
        if (r7 != 0) goto L_0x000c;
    L_0x0007:
        r6 = super.dispatchTouchEvent(r13);
    L_0x000b:
        return r6;
    L_0x000c:
        r0 = r13.getAction();
        switch(r0) {
            case 0: goto L_0x0018;
            case 1: goto L_0x002b;
            case 2: goto L_0x0066;
            default: goto L_0x0013;
        };
    L_0x0013:
        r6 = super.dispatchTouchEvent(r13);
        goto L_0x000b;
    L_0x0018:
        r6 = r12.isCanPullDown();
        r12.canPullDown = r6;
        r6 = r12.isCanPullUp();
        r12.canPullUp = r6;
        r6 = r13.getY();
        r12.startY = r6;
        goto L_0x0013;
    L_0x002b:
        r6 = r12.isMoved;
        if (r6 == 0) goto L_0x0013;
    L_0x002f:
        r1 = new android.view.animation.TranslateAnimation;
        r6 = r12.contentView;
        r6 = r6.getTop();
        r6 = (float) r6;
        r7 = r12.originalRect;
        r7 = r7.top;
        r7 = (float) r7;
        r1.<init>(r8, r8, r6, r7);
        r6 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r1.setDuration(r6);
        r6 = r12.contentView;
        r6.startAnimation(r1);
        r6 = r12.contentView;
        r7 = r12.originalRect;
        r7 = r7.left;
        r8 = r12.originalRect;
        r8 = r8.top;
        r9 = r12.originalRect;
        r9 = r9.right;
        r10 = r12.originalRect;
        r10 = r10.bottom;
        r6.layout(r7, r8, r9, r10);
        r12.canPullDown = r5;
        r12.canPullUp = r5;
        r12.isMoved = r5;
        goto L_0x0013;
    L_0x0066:
        r7 = r12.canPullDown;
        if (r7 != 0) goto L_0x0081;
    L_0x006a:
        r7 = r12.canPullUp;
        if (r7 != 0) goto L_0x0081;
    L_0x006e:
        r6 = r13.getY();
        r12.startY = r6;
        r6 = r12.isCanPullDown();
        r12.canPullDown = r6;
        r6 = r12.isCanPullUp();
        r12.canPullUp = r6;
        goto L_0x0013;
    L_0x0081:
        r3 = r13.getY();
        r7 = r12.startY;
        r7 = r3 - r7;
        r2 = (int) r7;
        r7 = r12.canPullDown;
        if (r7 == 0) goto L_0x0090;
    L_0x008e:
        if (r2 > 0) goto L_0x009e;
    L_0x0090:
        r7 = r12.canPullUp;
        if (r7 == 0) goto L_0x0096;
    L_0x0094:
        if (r2 < 0) goto L_0x009e;
    L_0x0096:
        r7 = r12.canPullUp;
        if (r7 == 0) goto L_0x009f;
    L_0x009a:
        r7 = r12.canPullDown;
        if (r7 == 0) goto L_0x009f;
    L_0x009e:
        r5 = r6;
    L_0x009f:
        if (r5 == 0) goto L_0x0013;
    L_0x00a1:
        r7 = (float) r2;
        r8 = r12.MOVE_FACTOR;
        r7 = r7 * r8;
        r4 = (int) r7;
        r7 = r12.contentView;
        r8 = r12.originalRect;
        r8 = r8.left;
        r9 = r12.originalRect;
        r9 = r9.top;
        r9 = r9 + r4;
        r10 = r12.originalRect;
        r10 = r10.right;
        r11 = r12.originalRect;
        r11 = r11.bottom;
        r11 = r11 + r4;
        r7.layout(r8, r9, r10, r11);
        r12.isMoved = r6;
        goto L_0x0013;
        */
    }

    private boolean isCanPullDown() {
        return getScrollY() == 0 || this.contentView.getHeight() < getHeight() + getScrollY();
    }

    private boolean isCanPullUp() {
        return this.contentView.getHeight() <= getHeight() + getScrollY();
    }

    public void fling(int velocityY) {
        super.fling((velocityY * 3) / 4);
    }

    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (this.mScrollViewListener != null) {
            this.mScrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.mScrollViewListener = scrollViewListener;
    }
}
