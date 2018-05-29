package net.oneplus.weather.api.nodes;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import java.util.Date;
import net.oneplus.weather.api.R;
import net.oneplus.weather.api.helper.WeatherUtils;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class OppoChinaDailyForecastsWeather extends DailyForecastsWeather {
    private static String DEFAULT_INDEX_TEXT;
    private final Temperature mBodytemp;
    private final String mClothingValue;
    private final Date mDate;
    private final int mDayWeatherId;
    private String mDayWeatherText;
    private final Temperature mMaxTemperature;
    private final Temperature mMinTemperature;
    private final String mMobileLink;
    private final int mNightWeatherId;
    private String mNightWeatherText;
    private final int mPressure;
    private final String mSportsValue;
    private final Sun mSun;
    private final int mVisibility;
    private final String mWashcarValue;

    static {
        DEFAULT_INDEX_TEXT = StringUtils.EMPTY_STRING;
    }

    public OppoChinaDailyForecastsWeather(String areaCode, String dataSource, int dayWeatherId, int nightWeatherId, Date date, Temperature minTemperature, Temperature maxTemperature, Sun sun, String sportsValue, String washcarValue, String clothingValue, String mobileLink) {
        this(areaCode, null, dataSource, dayWeatherId, nightWeatherId, date, minTemperature, maxTemperature, sun, sportsValue, washcarValue, clothingValue, mobileLink);
    }

    public OppoChinaDailyForecastsWeather(String areaCode, String areaName, String dataSource, int dayWeatherId, int nightWeatherId, Date date, Temperature minTemperature, Temperature maxTemperature, Sun sun, String sportsValue, String washcarValue, String clothingValue, String mobileLink) {
        this(areaCode, areaName, dataSource, dayWeatherId, nightWeatherId, date, minTemperature, maxTemperature, sun, sportsValue, washcarValue, clothingValue, null, Integer.MIN_VALUE, Integer.MIN_VALUE, mobileLink);
    }

    public OppoChinaDailyForecastsWeather(String areaCode, String areaName, String dataSource, int dayWeatherId, int nightWeatherId, Date date, Temperature minTemperature, Temperature maxTemperature, Sun sun, Temperature bodytemp, int pressure, int visibility, String mobileLink) {
        this(areaCode, areaName, dataSource, dayWeatherId, nightWeatherId, date, minTemperature, maxTemperature, sun, null, null, null, bodytemp, pressure, visibility, mobileLink);
    }

    public OppoChinaDailyForecastsWeather(String areaCode, String areaName, String dataSource, int dayWeatherId, int nightWeatherId, Date date, Temperature minTemperature, Temperature maxTemperature, Sun sun, String sportsValue, String washcarValue, String clothingValue, Temperature bodytemp, int pressure, int visibility, String mobileLink) {
        super(areaCode, areaName, dataSource);
        this.mDayWeatherId = dayWeatherId;
        this.mNightWeatherId = nightWeatherId;
        this.mDate = date;
        this.mMinTemperature = minTemperature;
        this.mMaxTemperature = maxTemperature;
        this.mSun = sun;
        this.mSportsValue = sportsValue;
        this.mWashcarValue = washcarValue;
        this.mClothingValue = clothingValue;
        this.mBodytemp = bodytemp;
        this.mPressure = pressure;
        this.mVisibility = visibility;
        this.mMobileLink = mobileLink;
    }

    public String getWeatherName() {
        return "Oppo China Daily Forecasts Weather";
    }

    public Date getDate() {
        return this.mDate;
    }

    public int getDayWeatherId() {
        return this.mDayWeatherId;
    }

    public String getMobileLink() {
        return this.mMobileLink;
    }

    public String getDayWeatherText(Context context) {
        if (this.mDayWeatherText == null) {
            this.mDayWeatherText = WeatherUtils.getOppoChinaWeatherTextById(context, this.mDayWeatherId);
        }
        return this.mDayWeatherText;
    }

    public int getNightWeatherId() {
        return this.mNightWeatherId;
    }

    public String getNightWeatherText(Context context) {
        if (this.mNightWeatherText == null) {
            this.mNightWeatherText = WeatherUtils.getOppoChinaWeatherTextById(context, this.mNightWeatherId);
        }
        return this.mNightWeatherText;
    }

    public Temperature getMinTemperature() {
        return this.mMinTemperature;
    }

    public Temperature getMaxTemperature() {
        return this.mMaxTemperature;
    }

    public Sun getSun() {
        return this.mSun;
    }

    public Temperature getBodytemp() {
        return this.mBodytemp;
    }

    public int getPressure() {
        return this.mPressure;
    }

    public String getPressureTransformSimlpeValue(Context mContext) {
        return this.mPressure + StringUtils.EMPTY_STRING + mContext.getString(R.string.api_pressure);
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public String getVisibilityTransformSimlpeValue(Context mContext) {
        try {
            return this.mVisibility >= 1000 ? (this.mVisibility / 1000) + mContext.getString(R.string.api_km) : this.mVisibility + mContext.getString(R.string.api_m);
        } catch (IllegalArgumentException e) {
            return this.mVisibility + mContext.getString(R.string.api_m);
        }
    }

    public String getSportsValue() {
        return this.mSportsValue;
    }

    public String getWashcarValue() {
        return this.mWashcarValue;
    }

    public String getClothingValue() {
        return this.mClothingValue;
    }

    public String getSportsTransformSimlpeValue(Context context) {
        String simpleValue = toSimple(this.mSportsValue);
        String result = DEFAULT_INDEX_TEXT;
        if (net.oneplus.weather.api.helper.StringUtils.isBlank(simpleValue)) {
            return result;
        }
        Object obj = -1;
        switch (simpleValue.hashCode()) {
            case 642863:
                if (simpleValue.equals("\u4e0d\u5b9c")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
            case 1166298:
                if (simpleValue.equals("\u9002\u5b9c")) {
                    obj = null;
                }
                break;
            case 35949042:
                if (simpleValue.equals("\u8f83\u4e0d\u5b9c")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case 36472477:
                if (simpleValue.equals("\u8f83\u9002\u5b9c")) {
                    obj = 1;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return context.getString(R.string.motion_index_level_one);
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return context.getString(R.string.motion_index_level_two);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return context.getString(R.string.motion_index_level_three);
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return context.getString(R.string.motion_index_level_four);
            default:
                return result;
        }
    }

    public String getCarwashTransformSimlpeValue(Context context) {
        String simpleValue = toSimple(this.mWashcarValue);
        String result = DEFAULT_INDEX_TEXT;
        if (net.oneplus.weather.api.helper.StringUtils.isBlank(simpleValue)) {
            return result;
        }
        Object obj = -1;
        switch (simpleValue.hashCode()) {
            case 642863:
                if (simpleValue.equals("\u4e0d\u5b9c")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
            case 1166298:
                if (simpleValue.equals("\u9002\u5b9c")) {
                    obj = null;
                }
                break;
            case 35949042:
                if (simpleValue.equals("\u8f83\u4e0d\u5b9c")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case 36472477:
                if (simpleValue.equals("\u8f83\u9002\u5b9c")) {
                    obj = 1;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return context.getString(R.string.carwash_index_level_one);
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return context.getString(R.string.carwash_index_level_two);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return context.getString(R.string.carwash_index_level_three);
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return context.getString(R.string.carwash_index_level_four);
            default:
                return result;
        }
    }

    public String getClothingTransformSimlpeValue(Context context) {
        String simpleValue = toSimple(this.mClothingValue);
        String result = DEFAULT_INDEX_TEXT;
        if (net.oneplus.weather.api.helper.StringUtils.isBlank(simpleValue)) {
            return result;
        }
        Object obj = -1;
        switch (simpleValue.hashCode()) {
            case 20919:
                if (simpleValue.equals("\u51b7")) {
                    obj = 1;
                }
                break;
            case 28909:
                if (simpleValue.equals("\u70ed")) {
                    obj = RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
                }
                break;
            case 749605:
                if (simpleValue.equals("\u5bd2\u51b7")) {
                    obj = null;
                }
                break;
            case 922143:
                if (simpleValue.equals("\u708e\u70ed")) {
                    obj = ConnectionResult.RESOLUTION_REQUIRED;
                }
                break;
            case 1069104:
                if (simpleValue.equals("\u8212\u9002")) {
                    obj = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                }
                break;
            case 1159828:
                if (simpleValue.equals("\u8f83\u51b7")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case 36375283:
                if (simpleValue.equals("\u8f83\u8212\u9002")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return context.getString(R.string.dress_index_earmuff);
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return context.getString(R.string.dress_index_scarf);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return context.getString(R.string.dress_index_sweater);
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return context.getString(R.string.dress_index_jacket);
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return context.getString(R.string.dress_index_fleece);
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return context.getString(R.string.dress_index_short_sleeve);
            case ConnectionResult.RESOLUTION_REQUIRED:
                return context.getString(R.string.dress_index_waistcoat);
            default:
                return result;
        }
    }

    public static String toSimple(String value) {
        if (net.oneplus.weather.api.helper.StringUtils.isBlank(value)) {
            return DEFAULT_INDEX_TEXT;
        }
        String[] sp = value.split("\u3002");
        return sp.length == 0 ? DEFAULT_INDEX_TEXT : sp[0];
    }
}
