package net.oneplus.weather.provider;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import net.oneplus.weather.model.CommonCandidateCity;
import net.oneplus.weather.provider.apihelper.AccuWeatherHelper;
import net.oneplus.weather.provider.apihelper.ICitySearchAPIHelper;
import net.oneplus.weather.provider.apihelper.WeatherChinaHelper;

public class CitySearchProvider implements ICitySearchProvider {
    public static final int DOMMY_BEGIN_FLAG = 1;
    public static final int DOMMY_END_FLAG = 1;
    public static final int GET_SEARCH_RESULT_FAIL = Integer.MIN_VALUE;
    public static final int GET_SEARCH_RESULT_SUCC = 1073741824;
    public static final int PROVIDER_ACCU_WEATHER = 2048;
    public static final int PROVIDER_WEATHER_CHINA = 4096;
    public static final int PROVIDER_YAHOO_WEATHER = 8192;
    public static final int SEARCH_CITY_BY_KEYWORD = 2097152;
    private final String TAG;
    private ArrayList<CommonCandidateCity> mCandidateCity;
    private ICitySearchAPIHelper mCitySearchHelper;
    private Context mContext;
    private Handler mProviderHandler;
    private Handler mUIHandler;

    public CitySearchProvider(Context context, Handler uiHandler) {
        this(context, 2048, uiHandler);
    }

    public CitySearchProvider(Context context, int provider, Handler uiHandler) {
        this.TAG = CitySearchProvider.class.getSimpleName();
        Log.d(this.TAG, "init CitySearchProvider");
        this.mContext = context;
        this.mUIHandler = uiHandler;
        this.mProviderHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1073745920) {
                    Log.d(CitySearchProvider.this.TAG, "get candidate city list");
                    CitySearchProvider.this.mCandidateCity = (ArrayList) msg.obj;
                    CitySearchProvider.this.mUIHandler.sendEmptyMessage(PROVIDER_WEATHER_CHINA);
                } else if (msg.what == 1073743872) {
                    CitySearchProvider.this.mCandidateCity = (ArrayList) msg.obj;
                    CitySearchProvider.this.mUIHandler.sendEmptyMessage(PROVIDER_ACCU_WEATHER);
                } else if ((msg.what & Integer.MIN_VALUE) != 0) {
                    Log.d(CitySearchProvider.this.TAG, "search city fail");
                    CitySearchProvider.this.mUIHandler.sendEmptyMessage(ConnectionResult.INTERRUPTED);
                }
            }
        };
        if (provider == 2048) {
            this.mCitySearchHelper = new AccuWeatherHelper(this.mProviderHandler);
        } else {
            this.mCitySearchHelper = new WeatherChinaHelper(this.mProviderHandler);
        }
    }

    public void searchCitiesByKeyword(String keyword, String locale) {
        this.mCandidateCity = null;
        try {
            this.mCitySearchHelper.searchCitiesByKeyword(this.mContext, new String(keyword.getBytes("UTF-8")), locale);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CommonCandidateCity> getCandidateCityList() {
        return this.mCandidateCity;
    }
}
