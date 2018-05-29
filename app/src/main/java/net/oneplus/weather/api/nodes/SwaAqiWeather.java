package net.oneplus.weather.api.nodes;

import java.util.Date;
import net.oneplus.weather.api.helper.DateUtils;
import net.oneplus.weather.api.helper.NumberUtils;
import net.oneplus.weather.api.impl.SwaRequest;

public class SwaAqiWeather extends AqiWeather {
    private int mAqiValue;
    private Date mDate;
    private int mPm2_5;

    public SwaAqiWeather(String areaCode, String areaName, String dataSource) {
        super(areaCode, areaName, dataSource);
        this.mPm2_5 = Integer.MIN_VALUE;
        this.mAqiValue = Integer.MIN_VALUE;
    }

    public int getAqiValue() {
        return this.mAqiValue;
    }

    public Date getPublicDate() {
        return this.mDate;
    }

    public int getPm2_5Value() {
        return this.mPm2_5;
    }

    public static SwaAqiWeather newInstance(String areaCode, String time, String pm2_5, String aqi) {
        SwaAqiWeather aqiWeather = new SwaAqiWeather(areaCode, null, SwaRequest.DATA_SOURCE_NAME);
        aqiWeather.mDate = DateUtils.parseSwaAqiDate(time);
        aqiWeather.mAqiValue = NumberUtils.valueToInt(aqi);
        aqiWeather.mPm2_5 = NumberUtils.valueToInt(pm2_5);
        return (NumberUtils.isNaN(aqiWeather.mAqiValue) || NumberUtils.isNaN(aqiWeather.mPm2_5) || aqiWeather.mDate == null) ? null : aqiWeather;
    }
}
