package net.oneplus.weather.provider.apihelper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import net.oneplus.weather.model.CandidateCity;
import net.oneplus.weather.model.LocationData;

public class AccuWeatherParser {
    private static final String TAG;

    static {
        TAG = AccuWeatherParser.class.getSimpleName();
    }

    public static LocationData parseLocation(String raw) {
        try {
            LocationData locationData = (LocationData) new Gson().fromJson(raw, LocationData.class);
            return locationData == null ? new LocationData() : locationData;
        } catch (Exception e) {
            e.printStackTrace();
            return new LocationData();
        }
    }

    public static ArrayList<CandidateCity> getSearchCityResult(String raw) {
        try {
            JsonArray jArray = new JsonParser().parse(raw).getAsJsonArray();
            if (jArray.size() == 0) {
                return null;
            }
            ArrayList<CandidateCity> candidateCityList = new ArrayList();
            for (int i = 0; i < jArray.size(); i++) {
                candidateCityList.add(new Gson().fromJson(jArray.get(i).getAsJsonObject(), CandidateCity.class));
            }
            return candidateCityList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
