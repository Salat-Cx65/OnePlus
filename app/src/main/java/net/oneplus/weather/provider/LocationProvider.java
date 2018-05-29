package net.oneplus.weather.provider;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import java.util.Date;
import java.util.Locale;
import net.oneplus.weather.app.MainActivity;
import net.oneplus.weather.app.MockLocation;
import net.oneplus.weather.db.ChinaCityDB;
import net.oneplus.weather.db.CityWeatherDB;
import net.oneplus.weather.location.GMSLocationHelper;
import net.oneplus.weather.model.CityData;
import net.oneplus.weather.model.LocationData;
import net.oneplus.weather.model.OpCity;
import net.oneplus.weather.provider.apihelper.AccuWeatherHelper;
import net.oneplus.weather.util.GlobalConfig;
import net.oneplus.weather.util.GpsUtils;
import net.oneplus.weather.util.PreferenceUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class LocationProvider {
    private static final String TAG;
    private boolean isLocating;
    private Handler judgeIsChinaHandler;
    private Handler locationHandler;
    private AccuWeatherHelper locationHelper;
    private Context mContext;
    public GMSLocationHelper mGMSLocationHelper;
    private OnLocationListener mListener;
    public AMapLocationClient mLocationClient;

    class AnonymousClass_2 extends Handler {
        final /* synthetic */ AMapLocation val$location;

        AnonymousClass_2(AMapLocation aMapLocation) {
            this.val$location = aMapLocation;
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConfig.MESSAGE_ACCU_GET_COUNTRY_CHINA:
                    String cityName = (String) msg.obj;
                    Log.d(TAG, "cityName is :" + cityName);
                    OpCity cc = ChinaCityDB.openCityDB(LocationProvider.this.mContext).getChinaCityByPinyin(LocationProvider.this.mContext, cityName);
                    if (cc != null) {
                        CityData city = new CityData();
                        city.setName(this.val$location.getCity());
                        city.setLocalName(LocationProvider.this.getName(cc));
                        city.setLatitude(this.val$location.getLatitude());
                        city.setLongitude(this.val$location.getLongitude());
                        city.setProvider(CitySearchProvider.PROVIDER_WEATHER_CHINA);
                        city.setLocationId(cc.getAreaId());
                        city.setLocatedCity(true);
                        if (LocationProvider.this.mListener != null) {
                            LocationProvider.this.mListener.onLocationChanged(city);
                        }
                    } else if (LocationProvider.this.mListener != null) {
                        LocationProvider.this.mListener.onError(RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
                    }
                default:
                    break;
            }
        }
    }

    public static interface OnLocationListener {
        void onError(int i);

        void onLocationChanged(CityData cityData);
    }

    class AnonymousClass_3 implements OnGeocodeSearchListener {
        final /* synthetic */ AMapLocation val$amapLocation;

        AnonymousClass_3(AMapLocation aMapLocation) {
            this.val$amapLocation = aMapLocation;
        }

        public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            if (!(regeocodeResult == null || regeocodeResult.getRegeocodeAddress() == null)) {
                RegeocodeAddress s = regeocodeResult.getRegeocodeAddress();
                Log.d(TAG, "city name = " + s.getCity());
                Log.d(TAG, "ad code = " + s.getAdCode());
                Log.d(TAG, "country = " + s.getProvince());
                this.val$amapLocation.setCity(s.getCity());
                this.val$amapLocation.setAdCode(s.getAdCode());
                if (!TextUtils.isEmpty(this.val$amapLocation.getCity())) {
                    this.val$amapLocation.setCountry("\u4e2d\u56fd");
                }
            }
            LocationProvider.this.notifyLocationChangedForAmap(this.val$amapLocation);
        }

        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
            Log.d(TAG, "onGeocodeSearched");
        }
    }

    final /* bridge */ /* synthetic */ void bridge$lambda$0$LocationProvider(Location location) {
        notifyLocationChangedForOversea(location);
    }

    static {
        TAG = LocationProvider.class.getSimpleName();
    }

    public LocationProvider(Context context) {
        this.isLocating = false;
        this.mContext = context;
        this.locationHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GlobalConfig.MESSAGE_ACCU_GET_LOCATION_SUCC:
                        LocationData ld = (LocationData) msg.obj;
                        CityData city = new CityData();
                        city.setName(ld.getEnglishName());
                        city.setLocalName(ld.getLocalizedName());
                        city.setLatitude(ld.getGeoPosition().getLatitude());
                        city.setLongitude(ld.getGeoPosition().getLongitude());
                        city.setLocationId(ld.getKey());
                        city.setProvider(CitySearchProvider.PROVIDER_ACCU_WEATHER);
                        city.setLocatedCity(true);
                        if (TextUtils.isEmpty(city.getLocalName())) {
                            city.setLocalName(city.getName());
                        }
                        if (LocationProvider.this.mListener != null) {
                            LocationProvider.this.mListener.onLocationChanged(city);
                        }
                    default:
                        break;
                }
            }
        };
        this.locationHelper = new AccuWeatherHelper(this.locationHandler);
        initLocation();
    }

    public void setOnLocationListener(OnLocationListener l) {
        this.mListener = l;
        this.locationHelper.setOnLocationListener(this.mListener);
    }

    public void initLocation() {
        this.isLocating = true;
        if (GpsUtils.isH2OS()) {
            this.mLocationClient = new AMapLocationClient(this.mContext);
            this.mLocationClient.setLocationListener(new LocationProvider$$Lambda$0(this));
            AMapLocationClientOption option = new AMapLocationClientOption();
            option.setLocationMode(AMapLocationMode.Hight_Accuracy);
            option.setMockEnable(true);
            option.setOnceLocation(true);
            option.setNeedAddress(true);
            option.setGpsFirst(PreferenceUtils.getBoolean(this.mContext, MockLocation.GPS_FIRST_SWITCH_KEY));
            this.mLocationClient.setLocationOption(option);
            return;
        }
        this.mGMSLocationHelper = new GMSLocationHelper(this.mContext);
        this.mGMSLocationHelper.setOneplusLocationListener(new LocationProvider$$Lambda$1(this));
    }

    public void startLocation() {
        if (GpsUtils.isH2OS()) {
            this.mLocationClient.startLocation();
        } else {
            this.mGMSLocationHelper.startLocation();
        }
    }

    private void notifyLocationChangedForAmap(AMapLocation location) {
        String city = location.getCity();
        String code = location.getAdCode();
        String country = location.getCountry();
        String province = location.getProvince();
        boolean isGMSLocation = false;
        if (location.getExtras() != null) {
            isGMSLocation = location.getExtras().getBoolean(GMSLocationHelper.KEY_LOCATION);
        }
        Log.d(TAG, "location.getLatitude(): " + location.getLatitude());
        Log.d(TAG, "location.getLongitude(): " + location.getLongitude());
        Log.d(TAG, "isisGMSLocation : " + isGMSLocation);
        if (!isGMSLocation && TextUtils.isEmpty(city) && TextUtils.isEmpty(code) && TextUtils.isEmpty(country)) {
            Log.d(TAG, "location is null");
            this.judgeIsChinaHandler = new AnonymousClass_2(location);
            AccuWeatherHelper locationHelper = new AccuWeatherHelper(this.judgeIsChinaHandler);
            CityData tmpCity = new CityData();
            tmpCity.setLatitude(location.getLatitude());
            tmpCity.setLongitude(location.getLongitude());
            tmpCity.setLocationDataRequestedTimestamp(new Date().getTime());
            locationHelper.getAccWeatherinfo(this.mContext, tmpCity);
            locationHelper.setOnLocationListener(this.mListener);
        } else if (country == null || !country.equals("\u4e2d\u56fd") || province.equals("\u53f0\u6e7e\u7701")) {
            Log.d(TAG, "fetchAccuLocationData");
            fetchAccuLocationData(location.getLatitude(), location.getLongitude());
        } else {
            Log.d(TAG, "country is china" + location.getProvince());
            OpCity cc = ChinaCityDB.openCityDB(this.mContext).getChinaCity(this.mContext, location.getAdCode(), location.getCity());
            if (cc != null) {
                CityData cityData = new CityData();
                cityData.setName(location.getCity());
                cityData.setLocalName(getName(cc));
                cityData.setLatitude(location.getLatitude());
                cityData.setLongitude(location.getLongitude());
                cityData.setProvider(CitySearchProvider.PROVIDER_WEATHER_CHINA);
                cityData.setLocationId(cc.getAreaId());
                cityData.setLocatedCity(true);
                if (this.mListener != null) {
                    this.mListener.onLocationChanged(cityData);
                }
            } else if (this.mListener != null) {
                this.mListener.onError(RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
            }
        }
    }

    private void notifyLocationChangedForOversea(Location location) {
        if (location != null) {
            String provider = location.getProvider();
            Object obj = -1;
            switch (provider.hashCode()) {
                case -915215485:
                    if (provider.equals(GMSLocationHelper.CACHE_PROVIDER)) {
                        obj = null;
                    }
                    break;
            }
            switch (obj) {
                case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                    CityData cityData = CityWeatherDB.getInstance(this.mContext).getLocationCity();
                    if (cityData != null && !TextUtils.isEmpty(cityData.getLocationId()) && !"0".equals(cityData.getLocationId()) && this.mListener != null) {
                        this.mListener.onLocationChanged(cityData);
                        return;
                    }
                    return;
                default:
                    boolean success = false;
                    if (location.getExtras() != null) {
                        success = location.getExtras().getBoolean(GMSLocationHelper.KEY_LOCATION_SUCCESS);
                    }
                    Log.d(TAG, "fetchAccuLocationData");
                    if (success) {
                        fetchAccuLocationData(location.getLatitude(), location.getLongitude());
                        return;
                    } else if (this.mListener != null) {
                        this.mListener.onError(RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
                        return;
                    } else {
                        return;
                    }
            }
        }
        throw new IllegalArgumentException("location can't be empty");
    }

    private String getName(OpCity city) {
        Locale locale = this.mContext.getResources().getConfiguration().locale;
        Log.d(TAG, "locale: " + locale.toString());
        if (locale.toString().contains("Hans") || locale.toString().equals("zh_CN")) {
            return city.getNameChs();
        }
        return (locale.toString().contains("Hant") || locale.toString().equals("zh_TW")) ? city.getNameCht() : city.getNameEn();
    }

    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation == null || amapLocation.getErrorCode() != 0) {
            if (amapLocation != null) {
                Log.e("AmapErr", "Location ERR:" + amapLocation.getErrorCode() + "LocationDetail: " + amapLocation.getLocationDetail() + "errorInfo: " + amapLocation.getErrorInfo());
            }
            if (this.mListener != null) {
                this.mListener.onError(amapLocation != null ? amapLocation.getErrorCode() : RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
            }
        } else if (MainActivity.MOCK_TEST_FLAG) {
            GeocodeSearch gs = new GeocodeSearch(this.mContext);
            gs.setOnGeocodeSearchListener(new AnonymousClass_3(amapLocation));
            gs.getFromLocationAsyn(new RegeocodeQuery(new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude()), 15.0f, GeocodeSearch.GPS));
        } else {
            Log.d(TAG, "get location start");
            notifyLocationChangedForAmap(amapLocation);
            this.isLocating = false;
        }
    }

    public void stopLocation() {
        if (this.mLocationClient != null) {
            this.mLocationClient.onDestroy();
        }
        if (this.mGMSLocationHelper != null) {
            this.mGMSLocationHelper.stopLocation();
        }
        this.mLocationClient = null;
        this.mListener = null;
    }

    private void fetchAccuLocationData(double latitude, double longitude) {
        CityData tmpCity = new CityData();
        tmpCity.setLatitude(latitude);
        tmpCity.setLongitude(longitude);
        tmpCity.setLocationDataRequestedTimestamp(new Date().getTime());
        this.locationHelper.getAccWeatherLocationData(this.mContext, tmpCity);
    }
}
