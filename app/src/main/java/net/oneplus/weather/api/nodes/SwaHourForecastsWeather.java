package net.oneplus.weather.api.nodes;

import android.content.Context;
import java.util.Date;
import net.oneplus.weather.api.helper.DateUtils;
import net.oneplus.weather.api.helper.NumberUtils;
import net.oneplus.weather.api.helper.WeatherUtils;
import net.oneplus.weather.api.impl.SwaRequest;

public class SwaHourForecastsWeather extends HourForecastsWeather {
    private final Temperature mTemperature;
    private final Date mTime;
    private final int mWeatherId;
    private String mWeatherText;

    public SwaHourForecastsWeather(String areaCode, int weatherId, Date time, Temperature temperature) {
        this(areaCode, null, SwaRequest.DATA_SOURCE_NAME, weatherId, time, temperature);
    }

    public SwaHourForecastsWeather(String areaCode, String areaName, String dataSource, int weatherId, Date time, Temperature temperature) {
        super(areaCode, areaName, dataSource);
        this.mWeatherId = weatherId;
        this.mTime = time;
        this.mTemperature = temperature;
    }

    public String getWeatherName() {
        return "Huafeng Hour Forecasts Weather";
    }

    public Date getTime() {
        return this.mTime;
    }

    public int getWeatherId() {
        return this.mWeatherId;
    }

    public String getWeatherText(Context context) {
        if (this.mWeatherText == null) {
            this.mWeatherText = WeatherUtils.getSwaWeatherTextById(context, this.mWeatherId);
        }
        return this.mWeatherText;
    }

    public Temperature getTemperature() {
        return this.mTemperature;
    }

    public static final SwaHourForecastsWeather buildFromString(String areaCode, String weatherId, String time, String temperature) {
        int iWeatherId = WeatherUtils.swaWeatherIdToWeatherId(weatherId);
        Temperature iTemperature = null;
        double iCentigradeValue = NumberUtils.valueToDouble(temperature);
        double iFahrenheitValue = WeatherUtils.centigradeToFahrenheit(iCentigradeValue);
        if (!NumberUtils.isNaN(iCentigradeValue)) {
            iTemperature = new Temperature(areaCode, SwaRequest.DATA_SOURCE_NAME, iCentigradeValue, iFahrenheitValue);
        }
        return new SwaHourForecastsWeather(areaCode, iWeatherId, DateUtils.parseSwaAqiDate(time), iTemperature);
    }
}
