package net.oneplus.weather.api.nodes;

import android.content.Context;
import java.util.Date;
import net.oneplus.weather.api.helper.WeatherUtils;

public class AccuDailyForecastsWeather extends DailyForecastsWeather {
    private final Date mDate;
    private final int mDayWeatherId;
    private String mDayWeatherText;
    private final Temperature mMaxTemperature;
    private final Temperature mMinTemperature;
    private final String mMobileLink;
    private final int mNightWeatherId;
    private String mNightWeatherText;
    private final Sun mSun;

    public AccuDailyForecastsWeather(String areaCode, String dataSource, int dayWeatherId, String dayWeatherText, int nightWeatherId, String nightWeatherText, Date date, Temperature minTemperature, Temperature maxTemperature, Sun sun, String mobileLink) {
        this(areaCode, null, dataSource, dayWeatherId, dayWeatherText, nightWeatherId, nightWeatherText, date, minTemperature, maxTemperature, sun, mobileLink);
    }

    public AccuDailyForecastsWeather(String areaCode, String areaName, String dataSource, int dayWeatherId, String dayWeatherText, int nightWeatherId, String nightWeatherText, Date date, Temperature minTemperature, Temperature maxTemperature, Sun sun, String mobileLink) {
        super(areaCode, areaName, dataSource);
        this.mDayWeatherId = dayWeatherId;
        this.mDayWeatherText = dayWeatherText;
        this.mNightWeatherId = nightWeatherId;
        this.mNightWeatherText = nightWeatherText;
        this.mDate = date;
        this.mMinTemperature = minTemperature;
        this.mMaxTemperature = maxTemperature;
        this.mSun = sun;
        this.mMobileLink = mobileLink;
    }

    public String getWeatherName() {
        return "Accu Daily Forecasts Weather";
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
            this.mDayWeatherText = WeatherUtils.getAccuWeatherTextById(context, this.mDayWeatherId);
        }
        return this.mDayWeatherText;
    }

    public int getNightWeatherId() {
        return this.mNightWeatherId;
    }

    public String getNightWeatherText(Context context) {
        if (this.mNightWeatherText == null) {
            this.mNightWeatherText = WeatherUtils.getAccuWeatherTextById(context, this.mNightWeatherId);
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
