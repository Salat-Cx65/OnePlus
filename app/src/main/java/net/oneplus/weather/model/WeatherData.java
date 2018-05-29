package net.oneplus.weather.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import net.oneplus.weather.util.StringUtils;

public class WeatherData implements Parcelable {
    public static final Creator<WeatherData> CREATOR;
    private int mCurrentRealFeelTemp;
    private int mCurrentTemp;
    private int mHighTemp;
    private int mHumidity;
    private String mLocationId;
    private int mLowTemp;
    private long mSunriseTime;
    private long mSunsetTime;
    private long mTimestamp;
    private int mWeatherDescriptionId;

    public WeatherData() {
        this.mLocationId = StringUtils.EMPTY_STRING;
        this.mTimestamp = 0;
        this.mHighTemp = 0;
        this.mLowTemp = 0;
        this.mCurrentTemp = 0;
        this.mCurrentRealFeelTemp = 0;
        this.mHumidity = 0;
        this.mSunriseTime = 0;
        this.mSunsetTime = 0;
        this.mWeatherDescriptionId = 0;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public void setTimestamp(long timestamp) {
        this.mTimestamp = timestamp;
    }

    public int getHighTemp() {
        return this.mHighTemp;
    }

    public void setHighTemp(int highTemp) {
        this.mHighTemp = highTemp;
    }

    public int getLowTemp() {
        return this.mLowTemp;
    }

    public void setLowTemp(int lowTemp) {
        this.mLowTemp = lowTemp;
    }

    public int getCurrentTemp() {
        return this.mCurrentTemp;
    }

    public void setCurrentTemp(int currentTemp) {
        this.mCurrentTemp = currentTemp;
    }

    public int getCurrentRealFeelTemp() {
        return this.mCurrentRealFeelTemp;
    }

    public void setCurrentRealFeelTemp(int currentRealFeelTemp) {
        this.mCurrentRealFeelTemp = currentRealFeelTemp;
    }

    public int getHumidity() {
        return this.mHumidity;
    }

    public void setHumidity(int humidity) {
        this.mHumidity = humidity;
    }

    public long getSunsetTime() {
        return this.mSunsetTime;
    }

    public void setSunsetTime(long time) {
        this.mSunsetTime = time;
    }

    public long getSunriseTime() {
        return this.mSunriseTime;
    }

    public void setSunriseTime(long time) {
        this.mSunriseTime = time;
    }

    public int getWeatherDescriptionId() {
        return this.mWeatherDescriptionId;
    }

    public void setWeatherDescriptionId(int id) {
        this.mWeatherDescriptionId = id;
    }

    public String getLocationId() {
        return this.mLocationId;
    }

    public void setLocationId(String locId) {
        this.mLocationId = locId;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mLocationId);
        dest.writeInt(this.mHighTemp);
        dest.writeInt(this.mLowTemp);
        dest.writeInt(this.mCurrentTemp);
        dest.writeInt(this.mCurrentRealFeelTemp);
        dest.writeInt(this.mHumidity);
        dest.writeInt(this.mWeatherDescriptionId);
        dest.writeLong(this.mTimestamp);
        dest.writeLong(this.mSunriseTime);
        dest.writeLong(this.mSunsetTime);
    }

    static {
        CREATOR = new Creator<WeatherData>() {
            public WeatherData createFromParcel(Parcel source) {
                WeatherData weatherDate = new WeatherData();
                weatherDate.mLocationId = source.readString();
                weatherDate.mHighTemp = source.readInt();
                weatherDate.mLowTemp = source.readInt();
                weatherDate.mCurrentTemp = source.readInt();
                weatherDate.mCurrentRealFeelTemp = source.readInt();
                weatherDate.mHumidity = source.readInt();
                weatherDate.mWeatherDescriptionId = source.readInt();
                weatherDate.mTimestamp = source.readLong();
                weatherDate.mSunriseTime = source.readLong();
                weatherDate.mSunsetTime = source.readLong();
                return weatherDate;
            }

            public WeatherData[] newArray(int size) {
                return new WeatherData[size];
            }
        };
    }
}
