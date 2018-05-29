package net.oneplus.weather.model;

public class HourForecastsWeatherData {
    private String hourText;
    private String temperature;
    private int weatherIconId;
    private int weatherId;

    public HourForecastsWeatherData(String hourText, int weatherId, int weatherIconId, String temperature) {
        this.hourText = hourText;
        this.weatherId = weatherId;
        this.weatherIconId = weatherIconId;
        this.temperature = temperature;
    }

    public String getHourText() {
        return this.hourText;
    }

    public void setHourText(String hourText) {
        this.hourText = hourText;
    }

    public int getWeatherId() {
        return this.weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public int getWeatherIconId() {
        return this.weatherIconId;
    }

    public void setWeatherIconId(int weatherIconId) {
        this.weatherIconId = weatherIconId;
    }

    public String getTemperature() {
        return this.temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
