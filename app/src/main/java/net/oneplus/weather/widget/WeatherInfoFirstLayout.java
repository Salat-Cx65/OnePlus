package net.oneplus.weather.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import net.oneplus.weather.R;

public class WeatherInfoFirstLayout extends RelativeLayout {
    public WeatherInfoFirstLayout(Context context) {
        super(context);
    }

    @SuppressLint({"NewApi"})
    public WeatherInfoFirstLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public WeatherInfoFirstLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeatherInfoFirstLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addView(View view) {
        if (view.getId() == 2131296498) {
            ((LayoutParams) view.getLayoutParams()).topMargin = -view.getHeight();
        }
        super.addView(view);
    }

    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View childView = findViewById(R.id.opweather_info);
        ((LayoutParams) childView.getLayoutParams()).topMargin = -childView.getHeight();
    }
}
