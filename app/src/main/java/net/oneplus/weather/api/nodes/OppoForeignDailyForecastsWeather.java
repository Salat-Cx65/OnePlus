package net.oneplus.weather.api.nodes;

import android.content.Context;
import java.util.Date;
import net.oneplus.weather.api.helper.WeatherUtils;

public class OppoForeignDailyForecastsWeather extends DailyForecastsWeather {
    private final Date mDate;
    private final int mDayWeatherId;
    private String mDayWeatherText;
    private final Temperature mMaxTemperature;
    private final Temperature mMinTemperature;
    private final int mNightWeatherId;
    private String mNightWeatherText;
    private final Sun mSun;

    public OppoForeignDailyForecastsWeather(String areaCode, String dataSource, int dayWeatherId, int nightWeatherId, Date date, Temperature minTemperature, Temperature maxTemperature, Sun sun) {
        this(areaCode, null, dataSource, dayWeatherId, nightWeatherId, date, minTemperature, maxTemperature, sun);
    }

    public OppoForeignDailyForecastsWeather(String areaCode, String areaName, String dataSource, int dayWeatherId, int nightWeatherId, Date date, Temperature minTemperature, Temperature maxTemperature, Sun sun) {
        super(areaCode, areaName, dataSource);
        this.mDayWeatherId = dayWeatherId;
        this.mNightWeatherId = nightWeatherId;
        this.mDate = date;
        this.mMinTemperature = minTemperature;
        this.mMaxTemperature = maxTemperature;
        this.mSun = sun;
    }

    public String getWeatherName() {
        return "Oppo Foreign Daily Forecasts Weather";
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
            this.mDayWeatherText = WeatherUtils.getOppoForeignWeatherTextById(context, this.mDayWeatherId);
        }
        return this.mDayWeatherText;
    }

    public int getNightWeatherId() {
        return this.mNightWeatherId;
    }

    public String getNightWeatherText(Context context) {
        if (this.mNightWeatherText == null) {
            this.mNightWeatherText = WeatherUtils.getOppoForeignWeatherTextById(context, this.mNightWeatherId);
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
