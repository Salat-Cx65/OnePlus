package net.oneplus.weather.api.nodes;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.Date;
import net.oneplus.weather.api.R;
import net.oneplus.weather.api.helper.WeatherUtils;
import net.oneplus.weather.util.DateTimeUtils;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class OppoChinaCurrentWeather extends CurrentWeather {
    private final Date mObservationDate;
    private final int mRelativeHumidity;
    private final Temperature mTemperature;
    private final int mUVIndex;
    private final String mUVIndexText;
    private final int mWeatherId;
    private String mWeatherText;
    private final Wind mWind;

    public OppoChinaCurrentWeather(String areaCode, String dataSource, int weatherId, Date observationDate, Temperature temperature, int relativeHumidity, Wind wind, int UVIndex, String UVIndexText) {
        this(areaCode, null, dataSource, weatherId, observationDate, temperature, relativeHumidity, wind, UVIndex, UVIndexText);
    }

    public OppoChinaCurrentWeather(String areaCode, String areaName, String dataSource, int weatherId, Date observationDate, Temperature temperature, int relativeHumidity, Wind wind, int UVIndex, String UVIndexText) {
        super(areaCode, areaName, dataSource);
        this.mWeatherId = weatherId;
        this.mObservationDate = observationDate;
        this.mTemperature = temperature;
        this.mRelativeHumidity = relativeHumidity;
        this.mWind = wind;
        this.mUVIndex = UVIndex;
        this.mUVIndexText = UVIndexText;
    }

    public String getWeatherName() {
        return "Oppo China Current Weather";
    }

    public Date getObservationDate() {
        return this.mObservationDate;
    }

    public int getWeatherId() {
        return this.mWeatherId;
    }

    public String getLocalTimeZone() {
        return DateTimeUtils.CHINA_OFFSET;
    }

    public String getWeatherText(Context context) {
        return WeatherUtils.getOppoChinaWeatherTextById(context, this.mWeatherId);
    }

    public Temperature getTemperature() {
        return this.mTemperature;
    }

    public int getRelativeHumidity() {
        return this.mRelativeHumidity;
    }

    public Wind getWind() {
        return this.mWind;
    }

    public int getUVIndex() {
        return this.mUVIndex;
    }

    public String getUVIndexText() {
        return this.mUVIndexText;
    }

    public String getMainMoblieLink() {
        return null;
    }

    public String getUVIndexInternationalText(Context context) {
        String simpleValue = getUVIndexText();
        String result = StringUtils.EMPTY_STRING;
        if (net.oneplus.weather.api.helper.StringUtils.isBlank(simpleValue)) {
            return result;
        }
        Object obj = -1;
        switch (simpleValue.hashCode()) {
            case 24369:
                if (simpleValue.equals("\u5f31")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case 24378:
                if (simpleValue.equals("\u5f3a")) {
                    obj = RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
                }
                break;
            case 651964:
                if (simpleValue.equals("\u4e2d\u7b49")) {
                    obj = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                }
                break;
            case 782505:
                if (simpleValue.equals("\u5f88\u5f31")) {
                    obj = null;
                }
                break;
            case 782514:
                if (simpleValue.equals("\u5f88\u5f3a")) {
                    obj = DetectedActivity.WALKING;
                }
                break;
            case 841777:
                if (simpleValue.equals("\u6700\u5f31")) {
                    obj = 1;
                }
                break;
            case 841786:
                if (simpleValue.equals("\u6700\u5f3a")) {
                    obj = DetectedActivity.RUNNING;
                }
                break;
            case 1163278:
                if (simpleValue.equals("\u8f83\u5f31")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
            case 1163287:
                if (simpleValue.equals("\u8f83\u5f3a")) {
                    obj = ConnectionResult.RESOLUTION_REQUIRED;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return context.getString(R.string.ultraviolet_index_level_one);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return context.getString(R.string.ultraviolet_index_level_two);
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return context.getString(R.string.ultraviolet_index_level_three);
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
            case ConnectionResult.RESOLUTION_REQUIRED:
                return context.getString(R.string.ultraviolet_index_level_four);
            case DetectedActivity.WALKING:
            case DetectedActivity.RUNNING:
                return context.getString(R.string.ultraviolet_index_level_five);
            default:
                return result;
        }
    }
}
