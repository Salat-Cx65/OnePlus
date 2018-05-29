package net.oneplus.weather.api.nodes;

import android.content.Context;
import java.util.Date;
import net.oneplus.weather.api.helper.WeatherUtils;
import net.oneplus.weather.api.impl.AccuRequest;

public class AccuCurrentWeather extends CurrentWeather {
    private String mLocalTimeZone;
    private final String mMainMoblieLink;
    private final Date mObservationDate;
    private final int mRelativeHumidity;
    private final Temperature mTemperature;
    private final int mUVIndex;
    private final String mUVIndexText;
    private final int mWeatherId;
    private String mWeatherText;
    private final Wind mWind;

    public AccuCurrentWeather(String areaCode, int weatherId, String localTimeZone, String weatherText, Date observationDate, Temperature temperature, int relativeHumidity, Wind wind, int UVIndex, String UVIndexText, String MainMoblieLink) {
        this(areaCode, null, weatherId, localTimeZone, weatherText, observationDate, temperature, relativeHumidity, wind, UVIndex, UVIndexText, MainMoblieLink);
    }

    public AccuCurrentWeather(String areaCode, String areaName, int weatherId, String localTimeZone, String weatherText, Date observationDate, Temperature temperature, int relativeHumidity, Wind wind, int UVIndex, String UVIndexText, String MainMoblieLink) {
        super(areaCode, areaName, AccuRequest.DATA_SOURCE_NAME);
        this.mWeatherId = weatherId;
        this.mLocalTimeZone = localTimeZone;
        this.mWeatherText = weatherText;
        this.mObservationDate = observationDate;
        this.mTemperature = temperature;
        this.mRelativeHumidity = relativeHumidity;
        this.mWind = wind;
        this.mUVIndex = UVIndex;
        this.mUVIndexText = UVIndexText;
        this.mMainMoblieLink = MainMoblieLink;
    }

    public String getWeatherName() {
        return "Accu Current Weather";
    }

    public Date getObservationDate() {
        return this.mObservationDate;
    }

    public int getWeatherId() {
        return this.mWeatherId;
    }

    public String getLocalTimeZone() {
        return this.mLocalTimeZone;
    }

    public String getWeatherText(Context context) {
        return WeatherUtils.getWeatherTextByWeatherId(context, this.mWeatherId);
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
        return this.mMainMoblieLink;
    }
}
