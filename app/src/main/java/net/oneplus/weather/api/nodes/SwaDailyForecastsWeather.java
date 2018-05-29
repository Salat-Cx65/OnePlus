package net.oneplus.weather.api.nodes;

import android.content.Context;
import java.util.Date;
import net.oneplus.weather.api.helper.WeatherUtils;
import net.oneplus.weather.api.impl.SwaRequest;

public class SwaDailyForecastsWeather extends DailyForecastsWeather {
    private final Date mDate;
    private final int mDayWeatherId;
    private String mDayWeatherText;
    private final Temperature mMaxTemperature;
    private final Temperature mMinTemperature;
    private final int mNightWeatherId;
    private String mNightWeatherText;
    private final Sun mSun;

    public SwaDailyForecastsWeather(String areaCode, String areaName, int dayWeatherId, int nightWeatherId, Date date, Temperature maxTemperature, Temperature minTemperature, Sun sun) {
        super(areaCode, areaName, SwaRequest.DATA_SOURCE_NAME);
        this.mDayWeatherId = dayWeatherId;
        this.mNightWeatherId = nightWeatherId;
        this.mDate = date;
        this.mMaxTemperature = maxTemperature;
        this.mMinTemperature = minTemperature;
        this.mSun = sun;
    }

    public String getWeatherName() {
        return "Swa Daily Forecasts Weather";
    }

    public Date getDate() {
        return this.mDate;
    }

    public int getDayWeatherId() {
        return this.mDayWeatherId;
    }

    public String getMobileLink() {
        return null;
    }

    public String getDayWeatherText(Context context) {
        if (this.mDayWeatherText == null) {
            this.mDayWeatherText = WeatherUtils.getSwaWeatherTextById(context, this.mDayWeatherId);
        }
        return this.mDayWeatherText;
    }

    public int getNightWeatherId() {
        return this.mNightWeatherId;
    }

    public String getNightWeatherText(Context context) {
        if (this.mNightWeatherText == null) {
            this.mNightWeatherText = WeatherUtils.getSwaWeatherTextById(context, this.mNightWeatherId);
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
}
