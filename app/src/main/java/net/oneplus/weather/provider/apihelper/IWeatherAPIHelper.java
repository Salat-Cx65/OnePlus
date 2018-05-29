package net.oneplus.weather.provider.apihelper;

import android.content.Context;
import net.oneplus.weather.model.CityData;

public interface IWeatherAPIHelper {
    void getWeatherAPIResponse(Context context, CityData cityData, int i);
}
