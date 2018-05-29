package net.oneplus.weather.api.nodes;

import net.oneplus.weather.api.helper.Validate;

public abstract class AbstractWeather {
    private String mAreaCode;
    private String mAreaName;
    private String mDataSource;

    public abstract String getWeatherName();

    public AbstractWeather(String areaCode, String areaName, String dataSource) {
        Validate.notEmpty(areaCode, "AreaCode should not be empty!");
        Validate.notEmpty(dataSource, "Data source name should not be empty!");
        this.mAreaCode = areaCode;
        this.mAreaName = areaName;
        this.mDataSource = dataSource;
    }

    public String getAreaCode() {
        return this.mAreaCode;
    }

    public String getAreaName() {
        return this.mAreaName;
    }

    public String getDataSourceName() {
        return this.mDataSource;
    }

    public void setAreaCode(String areaCode) {
        this.mAreaCode = areaCode;
    }

    public void setAreaName(String areaName) {
        this.mAreaName = areaName;
    }

    public void setDataSource(String dataSource) {
        this.mDataSource = dataSource;
    }
}
