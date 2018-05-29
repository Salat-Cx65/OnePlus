package net.oneplus.weather.api.nodes;

import android.content.Context;
import net.oneplus.weather.api.R;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class OppoChinaAqiWeather extends AqiWeather {
    private static String DEFAULT_INDEX_TEXT;
    public String mAqiType;
    private final int mAqiValue;
    public int mPM25_Value;

    static {
        DEFAULT_INDEX_TEXT = StringUtils.EMPTY_STRING;
    }

    public OppoChinaAqiWeather(String areaCode, String dataSource, int aqiValue) {
        this(areaCode, null, dataSource, aqiValue);
    }

    public OppoChinaAqiWeather(String areaCode, String areaName, String dataSource, int aqiValue) {
        this(areaCode, areaName, dataSource, aqiValue, Integer.MIN_VALUE, null);
    }

    public OppoChinaAqiWeather(String areaCode, String areaName, String dataSource, int aqiValue, int pm25Value, String aqi) {
        super(areaCode, areaName, dataSource);
        this.mAqiValue = aqiValue;
        this.mPM25_Value = pm25Value;
        this.mAqiType = aqi;
    }

    public String getWeatherName() {
        return "Oppo China Aqi Weather";
    }

    public int getAqiValue() {
        return this.mAqiValue;
    }

    public int getPM25_Value() {
        return this.mPM25_Value;
    }

    public String getAqiType() {
        return this.mAqiType;
    }

    public String getAqiTypeTransformSimlpe(Context context) {
        String str = DEFAULT_INDEX_TEXT;
        if (net.oneplus.weather.api.helper.StringUtils.isBlank(this.mAqiType)) {
            return context.getString(R.string.api_aqi_level_na);
        }
        String str2 = this.mAqiType;
        Object obj = -1;
        switch (str2.hashCode()) {
            case 20248:
                if (str2.equals("\u4f18")) {
                    obj = null;
                }
                break;
            case 33391:
                if (str2.equals("\u826f")) {
                    obj = 1;
                }
                break;
            case 620378987:
                if (str2.equals("\u4e2d\u5ea6\u6c61\u67d3")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
            case 632724954:
                if (str2.equals("\u4e25\u91cd\u6c61\u67d3")) {
                    obj = RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
                }
                break;
            case 1118424925:
                if (str2.equals("\u8f7b\u5ea6\u6c61\u67d3")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case 1136120779:
                if (str2.equals("\u91cd\u5ea6\u6c61\u67d3")) {
                    obj = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return context.getString(R.string.api_aqi_level_one);
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return context.getString(R.string.api_aqi_level_two);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return context.getString(R.string.api_aqi_level_three);
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return context.getString(R.string.api_aqi_level_four);
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return context.getString(R.string.api_aqi_level_five);
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return context.getString(R.string.api_aqi_level_six);
            default:
                return this.mAqiType;
        }
    }
}
