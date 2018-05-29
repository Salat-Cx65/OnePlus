package net.oneplus.weather.provider;

import java.util.ArrayList;
import net.oneplus.weather.model.CommonCandidateCity;

public interface ICitySearchProvider {
    ArrayList<CommonCandidateCity> getCandidateCityList();
}
