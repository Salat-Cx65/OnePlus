package net.oneplus.weather.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.R;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.AqiBar.Level;
import net.oneplus.weather.widget.AqiBar.OnAqiLevelChangeListener;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class AqiView extends LinearLayout implements OnAqiLevelChangeListener {
    private static final boolean DBG = false;
    private static final String TAG = "AqiView";
    private final AqiBar mAqiBar;
    private String mAqiType;
    private final ImageView mIcon;
    private final TextView mTextLevel;
    private final TextView mTextLevelValue;
    private final TextView mTextPMLevel;

    public AqiView(Context context) {
        this(context, null);
    }

    public AqiView(Context context, AttributeSet attrs) {
        this(context, attrs, 2130968628);
    }

    public AqiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AqiView, defStyleAttr, 0);
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(a.getResourceId(0, R.layout.aqi_view), this, true);
        int aqiValue = a.getInt(RainSurfaceView.RAIN_LEVEL_SHOWER, 0);
        this.mAqiType = a.getString(1);
        a.recycle();
        this.mTextLevel = (TextView) findViewById(R.id.level);
        this.mTextPMLevel = (TextView) findViewById(R.id.pm_type);
        this.mTextLevelValue = (TextView) findViewById(R.id.aqi_level_value);
        this.mIcon = (ImageView) findViewById(net.oneplus.weather.api.R.id.icon);
        Drawable d = this.mIcon.getBackground();
        if (d instanceof AnimationDrawable) {
            ((AnimationDrawable) d).start();
        }
        this.mAqiBar = (AqiBar) findViewById(R.id.aqiBar);
        this.mAqiBar.setOnAqiLevelChangeListener(this);
        this.mAqiBar.setAqiValue(aqiValue);
    }

    public void setAqiValue(int value) {
        if (value < 0) {
            this.mAqiBar.setAqiValue(0, DBG);
            this.mTextPMLevel.setVisibility(DetectedActivity.RUNNING);
            this.mTextLevelValue.setVisibility(DetectedActivity.RUNNING);
            return;
        }
        this.mAqiBar.setAqiValue(value);
    }

    public void setAqiType(String type) {
        if (TextUtils.isEmpty(type)) {
            this.mAqiType = getResources().getString(R.string.aqi_level_na);
        } else {
            this.mAqiType = type;
        }
        this.mTextLevel.setText(this.mAqiType);
    }

    public int getAqiValue() {
        return this.mAqiBar.getAqiValue();
    }

    public void onLevelChanged(Level level, int value) {
        if (TextUtils.isEmpty(this.mAqiType)) {
            this.mAqiType = getResources().getString(R.string.aqi_level_na);
        }
        this.mTextLevel.setText(this.mAqiType);
        this.mTextLevelValue.setVisibility(0);
        this.mTextPMLevel.setVisibility(0);
        this.mTextLevelValue.setText(value + StringUtils.EMPTY_STRING);
    }
}
