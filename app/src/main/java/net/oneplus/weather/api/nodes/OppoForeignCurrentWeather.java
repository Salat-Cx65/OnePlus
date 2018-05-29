package net.oneplus.weather.api.nodes;

import android.content.Context;
import java.util.Date;
import net.oneplus.weather.api.helper.WeatherUtils;

public class OppoForeignCurrentWeather extends CurrentWeather {
    private final Date mObservationDate;
    private final int mRelativeHumidity;
    private final Temperature mTemperature;
    private final int mUVIndex;
    private final String mUVIndexText;
    private final int mWeatherId;
    private String mWeatherText;
    private final Wind mWind;

    public OppoForeignCurrentWeather(String areaCode, String dataSource, int weatherId, Date observationDate, Temperature temperature, int relativeHumidity, Wind wind, int UVIndex, String UVIndexText) {
        this(areaCode, null, dataSource, weatherId, observationDate, temperature, relativeHumidity, wind, UVIndex, UVIndexText);
    }

    public OppoForeignCurrentWeather(String areaCode, String areaName, String dataSource, int weatherId, Date observationDate, Temperature temperature, int relativeHumidity, Wind wind, int UVIndex, String UVIndexText) {
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
        return "Oppo Foreign Current Weather";
    }

    public int getWeatherId() {
        return this.mWeatherId;
    }

    public String getLocalTimeZone() {
        return null;
    }

    public Date getObservationDate() {
        return this.mObservationDate;
    }

    public String getWeatherText(Context context) {
        if (this.mWeatherText == null) {
            this.mWeatherText = WeatherUtils.getOppoForeignWeatherTextById(context, this.mWeatherId);
        }
        return this.mWeatherText;
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
}
