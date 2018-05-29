package net.oneplus.weather.app;

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.oneplus.weather.api.WeatherException;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.app.ContentWrapper.OnUIChangedListener;
import net.oneplus.weather.app.MainActivity.OnViewPagerScrollListener;
import net.oneplus.weather.db.ChinaCityDB;
import net.oneplus.weather.db.CityWeatherDB;
import net.oneplus.weather.model.CityData;
import net.oneplus.weather.model.WeatherDescription;
import net.oneplus.weather.util.DateTimeUtils;
import net.oneplus.weather.util.SystemSetting;
import net.oneplus.weather.util.WeatherClientProxy;
import net.oneplus.weather.util.WeatherClientProxy.CacheMode;
import net.oneplus.weather.util.WeatherClientProxy.OnResponseListener;
import net.oneplus.weather.util.WeatherResHelper;

public class MainPagerAdapter extends PagerAdapter {
    public static final int DEFAULT_WEATHER_INDEX = 0;
    private List<CityData> mCitys;
    private Map<Integer, WeakReference<ContentWrapper>> mContentWrapper;
    private Context mContext;
    private OnUIChangedListener mOnUIChangedListener;
    private List<OnViewPagerScrollListener> mOnViewPagerScrollListener;
    private TextView mTextView;

    class AnonymousClass_1 implements OnResponseListener {
        final /* synthetic */ CityData val$city;

        AnonymousClass_1(CityData cityData) {
            this.val$city = cityData;
        }

        public void onNetworkResponse(RootWeather response) {
            if (response != null) {
                CityWeatherDB.getInstance(MainPagerAdapter.this.mContext).updateLastRefreshTime(this.val$city.getLocationId(), DateTimeUtils.longTimeToRefreshTime(MainPagerAdapter.this.mContext, System.currentTimeMillis()));
            }
        }

        public void onErrorResponse(WeatherException errorCode) {
        }

        public void onCacheResponse(RootWeather response) {
        }
    }

    public MainPagerAdapter(Context context, List<OnViewPagerScrollListener> l, TextView textView) {
        this.mContext = context;
        this.mCitys = new ArrayList();
        updateCityList(context);
        this.mOnViewPagerScrollListener = l;
        this.mContentWrapper = new HashMap();
        this.mTextView = textView;
    }

    public void loadWeather(int position) {
        loadWeather(position, false);
    }

    public void loadWeather(int position, boolean force) {
        if (this.mContentWrapper.size() > position) {
            WeakReference<ContentWrapper> wp = (WeakReference) this.mContentWrapper.get(Integer.valueOf(position));
            if (wp != null) {
                ContentWrapper cw = (ContentWrapper) wp.get();
                if (cw == null) {
                    return;
                }
                if (!cw.isSuccess() && !cw.isLoading()) {
                    cw.updateWeatherInfo(CacheMode.LOAD_NO_CACHE);
                } else if (!cw.isLoading()) {
                    if (position == 0 && force) {
                        cw.updateWeatherInfo(CacheMode.LOAD_NO_CACHE);
                    } else if (this.mCitys.size() > position) {
                        String cityId = ((CityData) this.mCitys.get(position)).getLocationId();
                        long rTime = SystemSetting.getRefreshTime(this.mContext, cityId);
                        if (force || DateTimeUtils.checkNeedRefresh(rTime) || WeatherClientProxy.needPullWeather(this.mContext, cityId, cw.getCityWeather())) {
                            cw.updateWeatherInfo(CacheMode.LOAD_NO_CACHE);
                        }
                    }
                }
            }
        }
    }

    public void setOnUIChangedListener(OnUIChangedListener l) {
        this.mOnUIChangedListener = l;
    }

    public int getCount() {
        return this.mCitys.size();
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((ContentWrapper) arg1).getContent();
    }

    public Object instantiateItem(ViewGroup container, int position) {
        CityData city = (CityData) this.mCitys.get(position);
        WeakReference<ContentWrapper> wr = (WeakReference) this.mContentWrapper.get(Integer.valueOf(position));
        ContentWrapper wrapper = null;
        if (wr != null) {
            wrapper = wr.get();
            if (wrapper == null) {
                this.mContentWrapper.remove(Integer.valueOf(position));
            }
        }
        if (wrapper == null) {
            wrapper = new ContentWrapper(this.mContext, city, new AnonymousClass_1(city), this.mTextView);
            wrapper.setOnUIChangedListener(this.mOnUIChangedListener);
            this.mContentWrapper.put(Integer.valueOf(position), new WeakReference(wrapper));
            wrapper.updateWeatherInfo(CacheMode.LOAD_DEFAULT);
        } else {
            container.removeView(wrapper.getContent());
            wrapper.updateWeatherInfo(CacheMode.LOAD_CACHE_ELSE_NETWORK);
        }
        this.mOnViewPagerScrollListener.add(wrapper);
        wrapper.setIndex(position);
        container.addView(wrapper.getContent(), new LayoutParams(-1, -1));
        return wrapper;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((ContentWrapper) object).getContent());
        this.mOnViewPagerScrollListener.remove(object);
    }

    public int getItemPosition(Object object) {
        return ListPopupWindow.WRAP_CONTENT;
    }

    public boolean contains(CityData city) {
        for (CityData c : this.mCitys) {
            if (c.getId() == city.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteCity(long id) {
        if (id < 0) {
            return false;
        }
        for (CityData c : this.mCitys) {
            if (c.getId() == id) {
                this.mCitys.remove(c);
                return true;
            }
        }
        return false;
    }

    public boolean updateCity(CityData city) {
        if (city == null) {
            return false;
        }
        for (CityData c : this.mCitys) {
            if (c.getId() == city.getId()) {
                c.copy(city);
                return true;
            }
        }
        return false;
    }

    public void updateCityList(Context context) {
        if (this.mCitys != null) {
            this.mCitys.clear();
        }
        if (this.mContentWrapper != null) {
            this.mContentWrapper.clear();
        }
        if (this.mOnViewPagerScrollListener != null) {
            this.mOnViewPagerScrollListener.clear();
        }
        boolean hasLocation = false;
        for (ContentValues values : CityWeatherDB.getInstance(this.mContext).getAllCityList()) {
            CityData city = CityData.parse(values);
            if (city.isLocatedCity()) {
                hasLocation = true;
            }
            this.mCitys.add(city);
            ChinaCityDB.openCityDB(context);
        }
        if (!hasLocation) {
            CityData cityData = new CityData();
            cityData.setLocatedCity(true);
            cityData.setDefault(true);
            cityData.setLocationId("0");
            cityData.setLocalName(this.mContext.getString(R.string.current_location));
            cityData.setName(this.mContext.getString(R.string.current_location));
            this.mCitys.add(0, cityData);
            CityWeatherDB.getInstance(this.mContext).addCurrentCity(cityData);
        }
    }

    public CityData getLocatedCityData() {
        return (this.mCitys == null || this.mCitys.size() == 0) ? null : (CityData) this.mCitys.get(0);
    }

    public int getWeatherDescriptionId(int position) {
        if (this.mCitys.size() <= position) {
            return WeatherDescription.WEATHER_DESCRIPTION_UNKNOWN;
        }
        RootWeather cWeather = ((CityData) this.mCitys.get(position)).getWeathers();
        return (cWeather == null || cWeather.getCurrentWeather() == null || cWeather.getTodayForecast() == null) ? WeatherDescription.WEATHER_DESCRIPTION_UNKNOWN : WeatherResHelper.weatherToResID(this.mContext, cWeather.getCurrentWeatherId());
    }

    public CityData getCityAtPosition(int position) {
        return (this.mCitys.size() <= position || position <= -1) ? null : (CityData) this.mCitys.get(position);
    }

    public ContentWrapper getContentWrap(int position) {
        if (this.mContentWrapper != null && this.mContentWrapper.size() > position) {
            WeakReference<ContentWrapper> wr = (WeakReference) this.mContentWrapper.get(Integer.valueOf(position));
            if (wr != null) {
                ContentWrapper cw = (ContentWrapper) wr.get();
                if (cw != null) {
                    return cw;
                }
                this.mContentWrapper.remove(wr);
                return cw;
            }
        }
        return null;
    }

    public RootWeather getWeatherDataAtPosition(int position) {
        return (this.mCitys.size() <= position || position == -1) ? null : ((CityData) this.mCitys.get(position)).getWeathers();
    }
}
