package net.oneplus.weather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class RefreshWeatherUnitView extends RelativeLayout {
    public OnRefreshUnitListener mOnRefreshUnitListener;

    public static interface OnRefreshUnitListener {
        void refreshUnit();
    }

    public RefreshWeatherUnitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RefreshWeatherUnitView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public RefreshWeatherUnitView(Context context) {
        this(context, null);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void invalidate() {
        super.invalidate();
        if (this.mOnRefreshUnitListener != null) {
            this.mOnRefreshUnitListener.refreshUnit();
        }
    }

    public void setOnRefreshUnitListener(OnRefreshUnitListener l) {
        this.mOnRefreshUnitListener = l;
    }
}
