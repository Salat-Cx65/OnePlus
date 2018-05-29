package net.oneplus.weather.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class WeatherSingleInfoView extends LinearLayout {
    private ImageView mInfoIcon;
    private TextView mInfoLevel;
    private TextView mInfoType;
    private View mView;

    public WeatherSingleInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public WeatherSingleInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.WeatherSingleInfoView);
        String weatherLevel = t.getString(1);
        String weatherType = t.getString(RainSurfaceView.RAIN_LEVEL_SHOWER);
        Drawable weathIcon = t.getDrawable(0);
        this.mView = LayoutInflater.from(context).inflate(R.layout.weather_single_info_view, this);
        this.mInfoIcon = (ImageView) this.mView.findViewById(R.id.single_info_icon);
        this.mInfoType = (TextView) this.mView.findViewById(R.id.single_info_type);
        this.mInfoLevel = (TextView) this.mView.findViewById(R.id.single_info_level);
        this.mInfoIcon.setBackground(weathIcon);
        this.mInfoType.setText(weatherType);
        this.mInfoLevel.setText(weatherLevel);
    }

    public WeatherSingleInfoView setInfoIcon(int id) {
        this.mInfoIcon.setBackgroundResource(id);
        return this;
    }

    public WeatherSingleInfoView setInfoType(int id) {
        this.mInfoType.setText(id);
        return this;
    }

    public WeatherSingleInfoView setInfoType(String text) {
        this.mInfoType.setText(text);
        return this;
    }

    public WeatherSingleInfoView setInfoLevel(int id) {
        this.mInfoLevel.setText(id);
        return this;
    }

    public WeatherSingleInfoView setInfoLevel(String level) {
        this.mInfoLevel.setText(level);
        return this;
    }
}
