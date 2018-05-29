package net.oneplus.weather.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.oneplus.lib.widget.recyclerview.ItemTouchHelper.Callback;
import java.util.ArrayList;
import java.util.List;

import net.oneplus.weather.api.WeatherException;
import net.oneplus.weather.api.nodes.CurrentWeather;
import net.oneplus.weather.api.nodes.DailyForecastsWeather;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.app.MainActivity.OnViewPagerScrollListener;
import net.oneplus.weather.db.CityWeatherDB;
import net.oneplus.weather.model.CityData;
import net.oneplus.weather.provider.LocationProvider;
import net.oneplus.weather.provider.LocationProvider.OnLocationListener;
import net.oneplus.weather.util.DateTimeUtils;
import net.oneplus.weather.util.GlobalConfig;
import net.oneplus.weather.util.NetUtil;
import net.oneplus.weather.util.PermissionUtil;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.util.SystemSetting;
import net.oneplus.weather.util.TemperatureUtil;
import net.oneplus.weather.util.UIUtil;
import net.oneplus.weather.util.WeatherClientProxy;
import net.oneplus.weather.util.WeatherClientProxy.CacheMode;
import net.oneplus.weather.util.WeatherClientProxy.OnResponseListener;
import net.oneplus.weather.util.WeatherResHelper;
import net.oneplus.weather.widget.AqiView;
import net.oneplus.weather.widget.HourForecastView;
import net.oneplus.weather.widget.RefreshWeatherUnitView;
import net.oneplus.weather.widget.RefreshWeatherUnitView.OnRefreshUnitListener;
import net.oneplus.weather.widget.WeatherScrollView;
import net.oneplus.weather.widget.WeatherScrollView.ScrollViewListener;
import net.oneplus.weather.widget.WeatherSingleInfoView;
import net.oneplus.weather.widget.WeatherTemperatureView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import net.oneplus.weather.widget.widget.WidgetHelper;

public class ContentWrapper implements OnViewPagerScrollListener, OnRefreshUnitListener {
    private static final int NO_TEMP_DATA_FLAG = -2000;
    public static final String TAG = "ContentWrapper";
    private int cacheWeatherID;
    private RefreshWeatherUnitView content;
    private int index;
    private LayoutInflater inflater;
    private CityData mCityData;
    private Context mContext;
    public int mCurrentTemp;
    private GestureDetector mGestureDetector;
    private boolean mHasLocation;
    private boolean mIsFling;
    private boolean mLoading;
    private LocationProvider mLocationProvider;
    private boolean mMoved;
    private int mNeedMoveOffset;
    private float mOffset;
    private OnLocationListener mOnLocationListener;
    private OnResponseListener mOnResponseListener;
    Handler mScrollHandler;
    private boolean mSuccess;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mToolbar_subTitle;
    private OnUIChangedListener mUIListener;
    private boolean mUp;
    private RootWeather mWeatherData;

    class AnonymousClass_3 implements OnTouchListener {
        final /* synthetic */ List val$data;

        AnonymousClass_3(List list) {
            this.val$data = list;
        }

        public boolean onTouch(View v, MotionEvent event) {
            boolean isMove = false;
            int position;
            String url;
            switch (event.getAction()) {
                case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                    ((ViewGroup) ContentWrapper.this.getChild(R.id.weather_scrollview)).requestDisallowInterceptTouchEvent(true);
                    break;
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    if (isMove) {
                        position = (int) Math.ceil((double) (((int) event.getRawX()) / (UIUtil.getScreenWidth(v.getContext()) / 6)));
                        if (position <= this.val$data.size() - 1) {
                            Log.e(TAG, "position > data.size()");
                        } else {
                            url = ((DailyForecastsWeather) this.val$data.get(position)).getMobileLink();
                            if (url != null || TextUtils.isEmpty(url)) {
                                Log.e(TAG, "url is null");
                            } else {
                                ContentWrapper.this.clickUrl(url, v.getContext());
                            }
                        }
                    } else {
                        ((ViewGroup) ContentWrapper.this.getChild(R.id.weather_scrollview)).requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    isMove = true;
                    if (isMove) {
                        position = (int) Math.ceil((double) (((int) event.getRawX()) / (UIUtil.getScreenWidth(v.getContext()) / 6)));
                        if (position <= this.val$data.size() - 1) {
                            url = ((DailyForecastsWeather) this.val$data.get(position)).getMobileLink();
                            if (url != null) {
                            }
                            Log.e(TAG, "url is null");
                        } else {
                            Log.e(TAG, "position > data.size()");
                        }
                    } else {
                        ((ViewGroup) ContentWrapper.this.getChild(R.id.weather_scrollview)).requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
            }
            return true;
        }
    }

    class AnonymousClass_7 implements Runnable {
        final /* synthetic */ int val$offset;

        AnonymousClass_7(int i) {
            this.val$offset = i;
        }

        public void run() {
            ((ScrollView) ContentWrapper.this.getChild(R.id.weather_scrollview)).smoothScrollTo(0, this.val$offset);
        }
    }

    public static interface OnUIChangedListener {
        void ChangePathMenuResource(int i, boolean z, boolean z2);

        void onChangedCurrentWeather();

        void onError();

        void onScrollViewChange(float f);

        void onWeatherDataUpdate(int i);
    }

    class ScrollViewGestureListener extends SimpleOnGestureListener {
        ScrollViewGestureListener() {
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (velocityY < -200.0f) {
                ContentWrapper.this.mIsFling = true;
            }
            return false;
        }
    }

    class AnonymousClass_1 implements OnResponseListener {
        final /* synthetic */ CityData val$city;

        AnonymousClass_1(CityData cityData) {
            this.val$city = cityData;
        }

        public void onCacheResponse(RootWeather response) {
            ContentWrapper.this.mSuccess = true;
            ContentWrapper.this.mLoading = false;
            ContentWrapper.this.mCityData.setWeathers(response);
            ContentWrapper.this.mWeatherData = response;
            ContentWrapper.this.refreshLocatindLayout(false);
            ContentWrapper.this.updateCurrentWeatherUI();
            if (ContentWrapper.this.mSwipeRefreshLayout != null && ContentWrapper.this.mSwipeRefreshLayout.isRefreshing()) {
                ContentWrapper.this.mSwipeRefreshLayout.setRefreshing(false);
            }
            if (!(response == null || ContentWrapper.this.mOnResponseListener == null)) {
                ContentWrapper.this.mOnResponseListener.onCacheResponse(response);
            }
            if (ContentWrapper.this.mUIListener != null) {
                ContentWrapper.this.mUIListener.onWeatherDataUpdate(ContentWrapper.this.index);
            }
            if (response != null && response.getCurrentWeather() != null) {
                ContentWrapper.this.cacheWeatherID = response.getCurrentWeather().getWeatherId();
            }
        }

        public void onNetworkResponse(RootWeather response) {
            ContentWrapper.this.mLoading = false;
            SystemSetting.setRefreshTime(ContentWrapper.this.mContext, this.val$city.getLocationId(), System.currentTimeMillis());
            ContentWrapper.this.mSuccess = true;
            ContentWrapper.this.mCityData.setWeathers(response);
            if (response != null) {
                ContentWrapper.this.cacheWeatherID = response.getCurrentWeatherId();
                ContentWrapper.this.mWeatherData = response;
            }
            ContentWrapper.this.refreshLocatindLayout(false);
            ContentWrapper.this.updateCurrentWeatherUI();
            CityWeatherDB.getInstance(ContentWrapper.this.mContext).updateLastRefreshTime(ContentWrapper.this.mCityData.getLocationId(), DateTimeUtils.longTimeToRefreshTime(ContentWrapper.this.mContext, System.currentTimeMillis()));
            System.out.println("LastRefreshTime:" + CityWeatherDB.getInstance(ContentWrapper.this.mContext).getLastRefreshTime(ContentWrapper.this.mCityData.getLocationId()));
            if (ContentWrapper.this.mSwipeRefreshLayout != null && ContentWrapper.this.mSwipeRefreshLayout.isRefreshing()) {
                ContentWrapper.this.mSwipeRefreshLayout.setRefreshing(false);
            }
            if (ContentWrapper.this.mOnResponseListener != null) {
                ContentWrapper.this.mOnResponseListener.onNetworkResponse(response);
            }
            if (ContentWrapper.this.mUIListener != null) {
                ContentWrapper.this.mUIListener.onWeatherDataUpdate(ContentWrapper.this.index);
            }
            if (ContentWrapper.this.mCityData.isDefault() || ContentWrapper.this.mCityData.isLocatedCity()) {
                SystemSetting.notifyWeatherDataChange(ContentWrapper.this.mContext.getApplicationContext());
                SystemSetting.setLocale(ContentWrapper.this.mContext);
            }
        }

        public void onErrorResponse(WeatherException exception) {
            ContentWrapper.this.mSuccess = false;
            ContentWrapper.this.mLoading = false;
            ContentWrapper.this.refreshLocatindLayout(false);
            ContentWrapper.this.updateRefreshLayout();
            ContentWrapper.this.updateCurrentWeatherUI();
            if (ContentWrapper.this.mSwipeRefreshLayout != null && ContentWrapper.this.mSwipeRefreshLayout.isRefreshing()) {
                ContentWrapper.this.mSwipeRefreshLayout.setRefreshing(false);
            }
            if (ContentWrapper.this.mOnResponseListener != null) {
                ContentWrapper.this.mOnResponseListener.onErrorResponse(exception);
            }
        }
    }

    class AnonymousClass_2 implements OnLocationListener {
        final /* synthetic */ CacheMode val$mode;

        AnonymousClass_2(CacheMode cacheMode) {
            this.val$mode = cacheMode;
        }

        public void onLocationChanged(CityData data) {
            Log.i("zhangyuan", "onLocationChanged name = " + data.getLocalName());
            if (ContentWrapper.this.mOnLocationListener != null) {
                ContentWrapper.this.mOnLocationListener.onLocationChanged(data);
            }
            if (SystemSetting.getLocationOrDefaultCity(ContentWrapper.this.mContext).isLocatedCity() || TextUtils.isEmpty(SystemSetting.getLocationOrDefaultCity(ContentWrapper.this.mContext).getLocationId())) {
                SystemSetting.setLocationOrDefaultCity(ContentWrapper.this.mContext, data);
            }
            ContentWrapper.this.mCityData.copy(data);
            CityWeatherDB.getInstance(ContentWrapper.this.mContext).addCurrentCity(ContentWrapper.this.mCityData);
            ContentWrapper.this.requestWeather(ContentWrapper.this.mCityData, this.val$mode);
        }

        public void onError(int error) {
            Log.i("zhangyuan", "onLocationChanged onError");
            if (ContentWrapper.this.mOnLocationListener != null) {
                ContentWrapper.this.mOnLocationListener.onError(error);
            }
            ContentWrapper.this.mHasLocation = false;
            ContentWrapper.this.requestWeather(ContentWrapper.this.mCityData, this.val$mode);
            if (ContentWrapper.this.mOnResponseListener != null) {
                ContentWrapper.this.mOnResponseListener.onErrorResponse(new WeatherException("location error"));
            }
            if (ContentWrapper.this.mUIListener != null) {
                ContentWrapper.this.mUIListener.onError();
            }
            ContentWrapper.this.changePathMenuResource(false, ContentWrapper.this.mLoading);
        }
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public void setOnUIChangedListener(OnUIChangedListener mUIListener) {
        this.mUIListener = mUIListener;
    }

    public ContentWrapper(Context context, CityData city, OnResponseListener l, TextView textView) {
        this.mMoved = false;
        this.mUp = false;
        this.mOffset = 0.0f;
        this.mIsFling = false;
        this.mHasLocation = false;
        this.mScrollHandler = new Handler();
        this.mContext = context;
        this.mCityData = city;
        setOnResponseListener(l);
        this.mGestureDetector = new GestureDetector(this.mContext, new ScrollViewGestureListener());
        this.mToolbar_subTitle = textView;
    }

    public void setOnResponseListener(OnResponseListener listener) {
        this.mOnResponseListener = listener;
    }

    public View getContent() {
        return this.content;
    }

    public CityData getCityData() {
        return this.mCityData;
    }

    public void setOnLocationListener(OnLocationListener l) {
        this.mOnLocationListener = l;
    }

    public RootWeather getCurrentWeather() {
        return this.mCityData.getWeathers();
    }

    public void setCurrentWeather(RootWeather currentWeather) {
        this.mCityData.setWeathers(currentWeather);
    }

    public boolean isSuccess() {
        return this.mSuccess;
    }

    public void setSuccess(boolean success) {
        this.mSuccess = success;
    }

    public boolean isLoading() {
        return this.mLoading;
    }

    public boolean isLocation() {
        return this.mCityData != null ? this.mCityData.isLocatedCity() : false;
    }

    public RootWeather getCityWeather() {
        return this.mCityData != null ? this.mCityData.getWeathers() : null;
    }

    public void requestWeather(CityData city, CacheMode mode) {
        if (city == null || TextUtils.isEmpty(city.getLocationId()) || city.getLocationId().equals("0")) {
            if (this.mSwipeRefreshLayout != null && this.mSwipeRefreshLayout.isRefreshing()) {
                this.mSwipeRefreshLayout.setRefreshing(false);
            }
            if (this.mUIListener != null) {
                this.mUIListener.onError();
                return;
            }
            return;
        }
        this.mLoading = true;
        new WeatherClientProxy(this.mContext).setCacheMode(mode).requestWeatherInfo(city, new AnonymousClass_1(city));
    }

    public void updateWeatherInfo(CacheMode mode) {
        refreshLocatindLayout(false);
        initWeatherScrollView();
        if (this.mCityData.isLocatedCity()) {
            loadCurrentPositionWeatherInfo(mode);
        } else {
            requestWeather(this.mCityData, mode);
        }
    }

    public void loadCurrentPositionWeatherInfo(CacheMode mode) {
        if (!this.mHasLocation || mode == CacheMode.LOAD_NO_CACHE) {
            if (this.mSwipeRefreshLayout == null || !(this.mSwipeRefreshLayout == null || this.mSwipeRefreshLayout.isRefreshing())) {
                requestWeather(this.mCityData, CacheMode.LOAD_CACHE_ONLY);
            }
            if (NetUtil.isNetworkAvailable(this.mContext)) {
                if (PermissionUtil.check((Activity) this.mContext, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1)) {
                    this.mHasLocation = true;
                    if (this.mLocationProvider != null) {
                        this.mLocationProvider.stopLocation();
                        this.mLocationProvider = null;
                    }
                    this.mLocationProvider = new LocationProvider(this.mContext);
                    this.mLocationProvider.setOnLocationListener(new AnonymousClass_2(mode));
                    this.mLocationProvider.startLocation();
                    return;
                }
            }
            changePathMenuResource(false, this.mLoading);
            return;
        }
        requestWeather(this.mCityData, mode);
        if (this.mUIListener != null) {
            this.mUIListener.onError();
        }
    }

    public View getChild(int id) {
        return this.content.findViewById(id);
    }

    private void setAlpha(int resId, float alpha) {
        getChild(resId).setAlpha(alpha);
    }

    private void setText(int resId, String temp) {
        ((TextView) getChild(resId)).setText(temp);
    }

    private void setTextColor(int resId, int color) {
        ((TextView) getChild(resId)).setTextColor(color);
    }

    private int getTextColor(int resId) {
        try {
            return ((TextView) getChild(resId)).getCurrentTextColor();
        } catch (Exception e) {
            Log.e(TAG, "get textcolor error");
            return this.mContext.getResources().getColor(R.color.white);
        }
    }

    public void setImage(int resId, int imageId) {
        ((ImageView) getChild(resId)).setImageResource(imageId);
    }

    public void setVisibility(int resId, int visibility) {
        getChild(resId).setVisibility(visibility);
    }

    private void setIndex(int resId, String title, String value) {
        ((WeatherSingleInfoView) getChild(resId)).setInfoType(title).setInfoLevel(value);
    }

    private void setIndex(int resId, String title, String value, int iconId) {
        ((WeatherSingleInfoView) getChild(resId)).setInfoType(title).setInfoLevel(value).setInfoIcon(iconId);
    }

    private void setIndexValue(int resId, String value) {
        ((WeatherSingleInfoView) getChild(resId)).setInfoLevel(value);
    }

    private void setAqiValue(int resId, int value, String type) {
        ((AqiView) getChild(resId)).setAqiValue(value);
        ((AqiView) getChild(resId)).setAqiType(type);
    }

    public void setTextAndAdjustMargin(int resId, String temp) {
        int length = temp.length();
        TextView tv = (TextView) getChild(resId);
        MarginLayoutParams mlp = (MarginLayoutParams) tv.getLayoutParams();
        if (length < 2) {
            mlp.setMargins(Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 170, 0, 0);
        } else if (length > 2) {
            mlp.setMargins(130, 170, 0, 0);
        }
        tv.setText(temp);
    }

    public int getWeatherInfoViewTopMargin(int height) {
        int heightDp = UIUtil.px2dip(this.mContext, (float) height);
        if (heightDp == 0) {
            heightDp = 151;
        }
        return (Build.PRODUCT.equals("A0001") && VERSION.RELEASE.equals("4.3")) ? -(heightDp + 20) : -(heightDp - 10);
    }

    public void updateCurrentWeatherUI() {
        if (this.mCityData != null) {
            Log.e(TAG, "updateCurrentWeatherUI: " + this.mCityData.getLocalName());
            changeTopViewTextColor(10);
            updateRefreshLayout();
            System.out.println("mWeatherInfoView.getHeight():" + UIUtil.px2dip(this.mContext, (float) getChild(2131296498).getHeight()));
            RootWeather data = this.mCityData.getWeathers();
            if (data != null) {
                if (data.isFromAccu()) {
                    setVisibility(2131296274, 0);
                } else {
                    setVisibility(2131296274, 4);
                }
                CurrentWeather current = data.getCurrentWeather();
                if (current != null) {
                    String humUnit;
                    float relativeHumidity;
                    boolean isChinaCity = data.isFromChina();
                    int curTemp = data.getTodayCurrentTemp();
                    int highTemp = data.getTodayHighTemperature();
                    int lowTemp = data.getTodayLowTemperature();
                    this.mCurrentTemp = curTemp;
                    setText(2131296351, TemperatureUtil.getLowTemperature(this.mContext, lowTemp) + (SystemSetting.getTemperature(this.mContext) ? "C" : "F"));
                    setText(2131296349, TemperatureUtil.getHighTemperature(this.mContext, highTemp).replace("\u00b0", StringUtils.EMPTY_STRING));
                    setText(2131296355, current.getWeatherText(this.mContext));
                    setText(2131296515, TemperatureUtil.getCurrentTemperature(this.mContext, curTemp));
                    setImage(2131296354, WeatherResHelper.getWeatherIconResID(WeatherResHelper.weatherToResID(this.mContext, current.getWeatherId())));
                    boolean percentOrGm = SystemSetting.getHumidity(this.mContext);
                    if (percentOrGm) {
                        humUnit = this.mContext.getString(R.string.percent);
                    } else {
                        humUnit = this.mContext.getString(R.string.gm);
                    }
                    if (percentOrGm) {
                        relativeHumidity = (float) current.getRelativeHumidity();
                    } else {
                        relativeHumidity = SystemSetting.kmToMp((float) current.getRelativeHumidity());
                    }
                    int humidity = (int) relativeHumidity;
                    String str = "--";
                    if (humidity < -2000) {
                        str = "--" + humUnit;
                    } else {
                        str = humidity + humUnit;
                    }
                    if (isChinaCity) {
                        getChild(2131296497).setVisibility(0);
                        setIndexValue(2131296551, str);
                        setIndex(2131296558, data.getCurrentWindDir(this.mContext), data.getCurrentWindPower(this.mContext));
                        setIndex(2131296558, data.getCurrentWindDir(this.mContext), data.getCurrentWindPower(this.mContext), WeatherResHelper.getWindIconId(this.mContext, current.getWind() != null ? current.getWind().getDirection() : null));
                        setIndexValue(2131296556, data.getUvTextTransformSimlpeValue(this.mContext));
                        setAqiValue(2131296305, data.getAqiPM25Value(), data.getAqiTypeTransformSimlpe(this.mContext));
                        setIndexValue(2131296550, TemperatureUtil.getLowTemperature(this.mContext, data.getTodayBodytemp()));
                        setIndexValue(2131296555, data.getTodayPressureTransformSimpleValue(this.mContext));
                        setIndexValue(2131296557, data.getTodayVisibilityTransformSimpleValue(this.mContext));
                    } else {
                        getChild(2131296497).setVisibility(DetectedActivity.RUNNING);
                    }
                }
                String timeZone = null;
                if (current != null) {
                    timeZone = current.getLocalTimeZone();
                }
                if (timeZone == null) {
                    timeZone = DateTimeUtils.CHINA_OFFSET;
                }
                updateForecastWeatherUI(data.getDailyForecastsWeather(), timeZone);
                updateHourForecastView(data, timeZone);
                if (this.mUIListener != null) {
                    this.mUIListener.onChangedCurrentWeather();
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateForecastWeatherUI(java.util.List<net.oneplus.weather.api.nodes.DailyForecastsWeather> r37_data, java.lang.String r38_timeZone) {
        throw new UnsupportedOperationException("Method not decompiled: net.oneplus.weather.app.ContentWrapper.updateForecastWeatherUI(java.util.List, java.lang.String):void");
        /*
        this = this;
        if (r37 == 0) goto L_0x039c;
    L_0x0002:
        r32 = r37.size();
        if (r32 <= 0) goto L_0x039c;
    L_0x0008:
        r23 = 6;
        r21 = new java.util.ArrayList;
        r0 = r21;
        r1 = r23;
        r0.<init>(r1);
        r22 = new java.util.ArrayList;
        r22.<init>(r23);
        r24 = net.oneplus.weather.util.DateTimeUtils.getTimeByTimeZone();
        r4 = r36.getAverageHighTemp(r37);
        r5 = r36.getAverageLowTemp(r37);
        r0 = r36;
        r0 = r0.mContext;
        r32 = r0;
        r0 = r32;
        r1 = r24;
        r3 = r38;
        r26 = net.oneplus.weather.util.DateTimeUtils.timeToDay(r0, r1, r3);
        r17 = r37.iterator();
    L_0x0038:
        r32 = r17.hasNext();
        if (r32 == 0) goto L_0x0066;
    L_0x003e:
        r8 = r17.next();
        r8 = (net.oneplus.weather.api.nodes.DailyForecastsWeather) r8;
        r0 = r36;
        r0 = r0.mContext;
        r32 = r0;
        r33 = r8.getDate();
        r34 = r33.getTime();
        r0 = r32;
        r1 = r34;
        r3 = r38;
        r31 = net.oneplus.weather.util.DateTimeUtils.timeToDay(r0, r1, r3);
        r0 = r31;
        r1 = r26;
        if (r0 >= r1) goto L_0x0038;
    L_0x0062:
        r17.remove();
        goto L_0x0038;
    L_0x0066:
        r32 = 2131296399; // 0x7f09008f float:1.8210714E38 double:1.053000332E-314;
        r0 = r36;
        r1 = r32;
        r13 = r0.getChild(r1);
        r13 = (android.widget.LinearLayout) r13;
        r13.removeAllViews();
        r32 = new net.oneplus.weather.app.ContentWrapper$3;
        r0 = r32;
        r1 = r36;
        r2 = r37;
        r0.<init>(r2);
        r0 = r32;
        r13.setOnTouchListener(r0);
        r15 = 0;
    L_0x0087:
        r0 = r23;
        if (r15 >= r0) goto L_0x02de;
    L_0x008b:
        r30 = 0;
        r0 = r36;
        r0 = r0.inflater;
        r32 = r0;
        r33 = 2131492917; // 0x7f0c0035 float:1.86093E38 double:1.0530974246E-314;
        r34 = 0;
        r9 = r32.inflate(r33, r34);
        r32 = r37.size();
        r0 = r32;
        if (r15 >= r0) goto L_0x026c;
    L_0x00a4:
        r0 = r37;
        r30 = r0.get(r15);
        r30 = (net.oneplus.weather.api.nodes.DailyForecastsWeather) r30;
        r6 = java.util.Calendar.getInstance();
        r32 = new java.lang.StringBuilder;
        r32.<init>();
        r33 = "GMT";
        r32 = r32.append(r33);
        r0 = r32;
        r1 = r38;
        r32 = r0.append(r1);
        r32 = r32.toString();
        r32 = java.util.TimeZone.getTimeZone(r32);
        r0 = r32;
        r6.setTimeZone(r0);
        r32 = r30.getDate();
        r28 = r32.getTime();
        r0 = r28;
        r6.setTimeInMillis(r0);
        r0 = r36;
        r0 = r0.mContext;
        r32 = r0;
        r33 = 7;
        r0 = r33;
        r33 = r6.get(r0);
        r10 = net.oneplus.weather.util.DateTimeUtils.getDayString(r32, r33);
        if (r15 != 0) goto L_0x0126;
    L_0x00f1:
        r0 = r36;
        r0 = r0.mContext;
        r32 = r0;
        r0 = r32;
        r1 = r28;
        r3 = r38;
        r32 = net.oneplus.weather.util.DateTimeUtils.longTimeToMMdd(r0, r1, r3);
        r0 = r36;
        r0 = r0.mContext;
        r33 = r0;
        r34 = 0;
        r0 = r33;
        r1 = r24;
        r3 = r34;
        r33 = net.oneplus.weather.util.DateTimeUtils.longTimeToMMdd(r0, r1, r3);
        r32 = r32.equals(r33);
        if (r32 == 0) goto L_0x0126;
    L_0x0119:
        r0 = r36;
        r0 = r0.mContext;
        r32 = r0;
        r33 = 2131689858; // 0x7f0f0182 float:1.9008743E38 double:1.0531947264E-314;
        r10 = r32.getString(r33);
    L_0x0126:
        r0 = r36;
        r0 = r0.mContext;
        r32 = r0;
        r0 = r32;
        r1 = r28;
        r3 = r38;
        r32 = net.oneplus.weather.util.DateTimeUtils.longTimeToMMdd(r0, r1, r3);
        r0 = r36;
        r0 = r0.mContext;
        r33 = r0;
        r34 = 0;
        r0 = r33;
        r1 = r24;
        r3 = r34;
        r33 = net.oneplus.weather.util.DateTimeUtils.longTimeToMMdd(r0, r1, r3);
        r32 = r32.equals(r33);
        if (r32 != 0) goto L_0x0160;
    L_0x014e:
        r0 = r36;
        r0 = r0.mContext;
        r32 = r0;
        r33 = 7;
        r0 = r33;
        r33 = r6.get(r0);
        r10 = net.oneplus.weather.util.DateTimeUtils.getDayString(r32, r33);
    L_0x0160:
        r0 = r36;
        r0 = r0.mContext;
        r32 = r0;
        r0 = r32;
        r1 = r28;
        r3 = r38;
        r11 = net.oneplus.weather.util.DateTimeUtils.longTimeToMMddTwo(r0, r1, r3);
        r32 = 2131296374; // 0x7f090076 float:1.8210663E38 double:1.0530003195E-314;
        r0 = r32;
        r32 = r9.findViewById(r0);
        r32 = (android.widget.TextView) r32;
        r0 = r32;
        r0.setText(r11);
        r32 = 2131296373; // 0x7f090075 float:1.821066E38 double:1.053000319E-314;
        r0 = r32;
        r32 = r9.findViewById(r0);
        r32 = (android.widget.TextView) r32;
        r0 = r32;
        r0.setText(r10);
        r0 = r36;
        r0 = r0.mContext;
        r32 = r0;
        r33 = r30.getDayWeatherId();
        r32 = net.oneplus.weather.util.WeatherResHelper.weatherToResID(r32, r33);
        r16 = net.oneplus.weather.util.WeatherResHelper.getWeatherIconResID(r32);
        r32 = 2131296398; // 0x7f09008e float:1.8210712E38 double:1.0530003314E-314;
        r0 = r32;
        r32 = r9.findViewById(r0);
        r32 = (android.widget.ImageView) r32;
        r0 = r32;
        r1 = r16;
        r0.setImageResource(r1);
        r32 = r30.getMaxTemperature();
        if (r32 == 0) goto L_0x01cb;
    L_0x01ba:
        r32 = r30.getMaxTemperature();
        r32 = r32.getCentigradeValue();
        r34 = -4566861128386215936; // 0xc09f400000000000 float:0.0 double:-2000.0;
        r32 = (r32 > r34 ? 1 : (r32 == r34 ? 0 : -1));
        if (r32 >= 0) goto L_0x0218;
    L_0x01cb:
        r32 = java.lang.Integer.valueOf(r4);
        r0 = r21;
        r1 = r32;
        r0.add(r1);
    L_0x01d6:
        r32 = r30.getMinTemperature();
        if (r32 == 0) goto L_0x01ed;
    L_0x01dc:
        r32 = r30.getMinTemperature();
        r32 = r32.getCentigradeValue();
        r34 = -4566861128386215936; // 0xc09f400000000000 float:0.0 double:-2000.0;
        r32 = (r32 > r34 ? 1 : (r32 == r34 ? 0 : -1));
        if (r32 >= 0) goto L_0x0242;
    L_0x01ed:
        r32 = java.lang.Integer.valueOf(r5);
        r0 = r22;
        r1 = r32;
        r0.add(r1);
    L_0x01f8:
        r20 = new android.widget.LinearLayout$LayoutParams;
        r32 = -1;
        r33 = -1;
        r0 = r20;
        r1 = r32;
        r2 = r33;
        r0.<init>(r1, r2);
        r32 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r32;
        r1 = r20;
        r1.weight = r0;
        r0 = r20;
        r13.addView(r9, r0);
        r15 = r15 + 1;
        goto L_0x0087;
    L_0x0218:
        r32 = r30.getMaxTemperature();
        r32 = r32.getCentigradeValue();
        r0 = r32;
        r0 = (int) r0;
        r32 = r0;
        r33 = r30.getMinTemperature();
        r34 = r33.getCentigradeValue();
        r0 = r34;
        r0 = (int) r0;
        r33 = r0;
        r32 = java.lang.Math.max(r32, r33);
        r32 = java.lang.Integer.valueOf(r32);
        r0 = r21;
        r1 = r32;
        r0.add(r1);
        goto L_0x01d6;
    L_0x0242:
        r32 = r30.getMaxTemperature();
        r32 = r32.getCentigradeValue();
        r0 = r32;
        r0 = (int) r0;
        r32 = r0;
        r33 = r30.getMinTemperature();
        r34 = r33.getCentigradeValue();
        r0 = r34;
        r0 = (int) r0;
        r33 = r0;
        r32 = java.lang.Math.min(r32, r33);
        r32 = java.lang.Integer.valueOf(r32);
        r0 = r22;
        r1 = r32;
        r0.add(r1);
        goto L_0x01f8;
    L_0x026c:
        r32 = 2131296374; // 0x7f090076 float:1.8210663E38 double:1.0530003195E-314;
        r0 = r32;
        r32 = r9.findViewById(r0);
        r32 = (android.widget.TextView) r32;
        r33 = 2131689780; // 0x7f0f0134 float:1.9008585E38 double:1.053194688E-314;
        r32.setText(r33);
        r32 = 2131296373; // 0x7f090075 float:1.821066E38 double:1.053000319E-314;
        r0 = r32;
        r32 = r9.findViewById(r0);
        r32 = (android.widget.TextView) r32;
        r33 = 2131689780; // 0x7f0f0134 float:1.9008585E38 double:1.053194688E-314;
        r32.setText(r33);
        r32 = 0;
        r16 = net.oneplus.weather.util.WeatherResHelper.getWeatherIconResID(r32);
        r32 = 2131296398; // 0x7f09008e float:1.8210712E38 double:1.0530003314E-314;
        r0 = r32;
        r32 = r9.findViewById(r0);
        r32 = (android.widget.ImageView) r32;
        r0 = r32;
        r1 = r16;
        r0.setImageResource(r1);
        r32 = r22.size();
        if (r32 <= 0) goto L_0x01f8;
    L_0x02ac:
        r32 = r21.size();
        if (r32 <= 0) goto L_0x01f8;
    L_0x02b2:
        r32 = r22.size();
        r32 = r32 + -1;
        r0 = r22;
        r1 = r32;
        r32 = r0.get(r1);
        r0 = r22;
        r1 = r32;
        r0.add(r1);
        r32 = r21.size();
        r32 = r32 + -1;
        r0 = r21;
        r1 = r32;
        r32 = r0.get(r1);
        r0 = r21;
        r1 = r32;
        r0.add(r1);
        goto L_0x01f8;
    L_0x02de:
        r32 = r37.size();
        r0 = r32;
        r1 = r23;
        if (r0 >= r1) goto L_0x0348;
    L_0x02e8:
        r7 = r37.size();
    L_0x02ec:
        r32 = java.util.Collections.max(r21);	 Catch:{ Exception -> 0x037f }
        r32 = (java.lang.Integer) r32;	 Catch:{ Exception -> 0x037f }
        r14 = r32.intValue();	 Catch:{ Exception -> 0x037f }
        r32 = java.util.Collections.min(r22);	 Catch:{ Exception -> 0x037f }
        r32 = (java.lang.Integer) r32;	 Catch:{ Exception -> 0x037f }
        r19 = r32.intValue();	 Catch:{ Exception -> 0x037f }
        r32 = r14 - r19;
        r33 = 45;
        r0 = r32;
        r1 = r33;
        if (r0 <= r1) goto L_0x0387;
    L_0x030a:
        r32 = r14 - r4;
        r33 = r5 - r19;
        r0 = r32;
        r1 = r33;
        if (r0 < r1) goto L_0x034b;
    L_0x0314:
        r27 = r21.size();	 Catch:{ Exception -> 0x037f }
        r18 = 0;
    L_0x031a:
        r0 = r18;
        r1 = r27;
        if (r0 >= r1) goto L_0x0387;
    L_0x0320:
        r0 = r21;
        r1 = r18;
        r32 = r0.get(r1);	 Catch:{ Exception -> 0x037f }
        r32 = (java.lang.Integer) r32;	 Catch:{ Exception -> 0x037f }
        r32 = r32.intValue();	 Catch:{ Exception -> 0x037f }
        r32 = r14 - r32;
        r33 = 5;
        r0 = r32;
        r1 = r33;
        if (r0 > r1) goto L_0x0345;
    L_0x0338:
        r32 = java.lang.Integer.valueOf(r4);	 Catch:{ Exception -> 0x037f }
        r0 = r21;
        r1 = r18;
        r2 = r32;
        r0.set(r1, r2);	 Catch:{ Exception -> 0x037f }
    L_0x0345:
        r18 = r18 + 1;
        goto L_0x031a;
    L_0x0348:
        r7 = r23;
        goto L_0x02ec;
    L_0x034b:
        r27 = r22.size();	 Catch:{ Exception -> 0x037f }
        r18 = 0;
    L_0x0351:
        r0 = r18;
        r1 = r27;
        if (r0 >= r1) goto L_0x0387;
    L_0x0357:
        r0 = r22;
        r1 = r18;
        r32 = r0.get(r1);	 Catch:{ Exception -> 0x037f }
        r32 = (java.lang.Integer) r32;	 Catch:{ Exception -> 0x037f }
        r32 = r32.intValue();	 Catch:{ Exception -> 0x037f }
        r32 = r32 - r19;
        r33 = 5;
        r0 = r32;
        r1 = r33;
        if (r0 > r1) goto L_0x037c;
    L_0x036f:
        r32 = java.lang.Integer.valueOf(r5);	 Catch:{ Exception -> 0x037f }
        r0 = r22;
        r1 = r18;
        r2 = r32;
        r0.set(r1, r2);	 Catch:{ Exception -> 0x037f }
    L_0x037c:
        r18 = r18 + 1;
        goto L_0x0351;
    L_0x037f:
        r12 = move-exception;
        r32 = "contentwrapper";
        r33 = "check temp error";
        android.util.Log.e(r32, r33);
    L_0x0387:
        r32 = r21.size();
        if (r32 <= 0) goto L_0x039c;
    L_0x038d:
        r32 = r22.size();
        if (r32 <= 0) goto L_0x039c;
    L_0x0393:
        r0 = r36;
        r1 = r21;
        r2 = r22;
        r0.updateTempView(r1, r2, r7);
    L_0x039c:
        return;
        */
    }

    private void clickUrl(String url, Context context) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, context.getString(R.string.browser_not_found), 0).show();
            e.printStackTrace();
        }
    }

    public int getAverageHighTemp(List<DailyForecastsWeather> data) {
        List<Integer> averageHighTemp = new ArrayList();
        for (int i = 0; i < data.size(); i++) {
            if (i < 7) {
                DailyForecastsWeather w = (DailyForecastsWeather) data.get(i);
                if (w == null || w.getMaxTemperature() == null) {
                    if (w == null || w.getMinTemperature() == null) {
                        averageHighTemp.add(Integer.valueOf(0));
                    } else {
                        averageHighTemp.add(Integer.valueOf((int) w.getMinTemperature().getCentigradeValue()));
                    }
                } else if (w.getMaxTemperature().getCentigradeValue() > -2000.0d) {
                    averageHighTemp.add(Integer.valueOf((int) w.getMaxTemperature().getCentigradeValue()));
                }
            }
        }
        int highTempSum = 0;
        for (int j = 0; j < averageHighTemp.size(); j++) {
            highTempSum += ((Integer) averageHighTemp.get(j)).intValue();
        }
        return averageHighTemp.size() == 0 ? 0 : highTempSum / averageHighTemp.size();
    }

    public int getAverageLowTemp(List<DailyForecastsWeather> data) {
        List<Integer> averageLowTemp = new ArrayList();
        for (int i = 0; i < data.size(); i++) {
            DailyForecastsWeather w = (DailyForecastsWeather) data.get(i);
            if (w == null || w.getMinTemperature() == null) {
                if (w == null || w.getMinTemperature() == null) {
                    averageLowTemp.add(Integer.valueOf(0));
                } else {
                    averageLowTemp.add(Integer.valueOf((int) w.getMaxTemperature().getCentigradeValue()));
                }
            } else if (w.getMinTemperature().getCentigradeValue() > -2000.0d) {
                averageLowTemp.add(Integer.valueOf((int) w.getMinTemperature().getCentigradeValue()));
            }
        }
        int lowTempSum = 0;
        for (int j = 0; j < averageLowTemp.size(); j++) {
            lowTempSum += ((Integer) averageLowTemp.get(j)).intValue();
        }
        return averageLowTemp.size() == 0 ? 0 : lowTempSum / averageLowTemp.size();
    }

    private void refreshLocatindLayout(boolean locating) {
        if (this.content == null) {
            this.inflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
            this.content = (RefreshWeatherUnitView) this.inflater.inflate(R.layout.weather_info_layout, null);
            this.content.setOnRefreshUnitListener(this);
        }
        updateRefreshLayout();
    }

    public void updateRefreshLayout() {
        if (this.mSwipeRefreshLayout == null) {
            this.mSwipeRefreshLayout = (SwipeRefreshLayout) getChild(R.id.swipeRefreshLayout);
            this.mSwipeRefreshLayout.setColorSchemeResources(17170450, 17170454, 17170456, 17170452);
            this.mSwipeRefreshLayout.setProgressViewOffset(true, 0, GlobalConfig.MESSAGE_ACCU_GET_LOCATION_SUCC);
            this.mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                public void onRefresh() {
                    ContentWrapper.this.mLoading = true;
                    new Handler().post(new Runnable() {
                        public void run() {
                            if (NetUtil.isNetworkAvailable(ContentWrapper.this.mContext)) {
                                if (ContentWrapper.this.mWeatherData != null) {
                                    ContentWrapper.this.mToolbar_subTitle.setTextColor(ContextCompat.getColor(ContentWrapper.this.mContext, ContentWrapper.this.getTitleColor()));
                                }
                                ContentWrapper.this.updateWeatherInfo(CacheMode.LOAD_NO_CACHE);
                                WidgetHelper.getInstance(ContentWrapper.this.mContext).updateAllWidget(true);
                                return;
                            }
                            if (ContentWrapper.this.mSwipeRefreshLayout != null) {
                                ContentWrapper.this.mSwipeRefreshLayout.setRefreshing(false);
                            }
                            ContentWrapper.this.mLoading = false;
                            Toast.makeText(ContentWrapper.this.mContext, ContentWrapper.this.mContext.getString(R.string.no_network), 1).show();
                        }
                    });
                }
            });
        }
    }

    private int getTitleColor() {
        return (this.mCityData == null || this.mCityData.getWeathers() == null || !needGrayColor(WeatherResHelper.weatherToResID(this.mContext, this.mCityData.getWeathers().getCurrentWeatherId()))) ? R.color.oneplus_contorl_text_color_disable_dark : R.color.oneplus_contorl_text_color_disable_light;
    }

    public void updateHourForecastView(RootWeather data, String timeZone) {
        if (data == null || data.getHourForecastsWeather() == null || data.getHourForecastsWeather().size() <= 0) {
            getChild(R.id.hourForecastView).setVisibility(DetectedActivity.RUNNING);
            getChild(R.id.hourForecastViewline1).setVisibility(DetectedActivity.RUNNING);
            getChild(R.id.hourForecastViewline2).setVisibility(DetectedActivity.RUNNING);
            return;
        }
        getChild(R.id.hourForecastView).setVisibility(0);
        getChild(R.id.hourForecastViewline1).setVisibility(0);
        getChild(R.id.hourForecastViewline2).setVisibility(0);
        ((HourForecastView) getChild(R.id.hourForecastView)).updateForecastData(data.getHourForecastsWeather(), data.getDailyForecastsWeather(), this.mCurrentTemp, timeZone);
    }

    public void updateTempView(ArrayList<Integer> mHighTemp, ArrayList<Integer> mLowTemp, int realCount) {
        ((WeatherTemperatureView) getChild(R.id.weather_temp_view)).initTemp(mHighTemp, mLowTemp, realCount);
    }

    public int getWeatherNightArcColor() {
        int weatherId = 0;
        if (this.mCityData.getWeathers() != null) {
            weatherId = WeatherResHelper.weatherToResID(this.mContext, this.mCityData.getWeathers().getCurrentWeatherId());
        }
        return Color.parseColor(this.mContext.getString(WeatherResHelper.getWeatherNightArcColorID(weatherId)));
    }

    public void initWeatherScrollView() {
        WeatherScrollView mWeatherScrollView = (WeatherScrollView) getChild(R.id.weather_scrollview);
        FrameLayout mBackground = (FrameLayout) mWeatherScrollView.findViewById(R.id.current_opweather_overlay);
        LayoutParams bgParams = (LayoutParams) mBackground.getLayoutParams();
        bgParams.height = UIUtil.getWindowHeight(this.mContext) - ((int) this.mContext.getResources().getDimension(R.dimen.dimen_top_info_view));
        mBackground.setLayoutParams(bgParams);
        mWeatherScrollView.setOverScrollMode(RainSurfaceView.RAIN_LEVEL_SHOWER);
        mWeatherScrollView.setOnTouchListener(new OnTouchListener() {
            float downY;
            float upY;

            {
                this.downY = 0.0f;
                this.upY = 0.0f;
            }

            public boolean onTouch(View v, MotionEvent event) {
                ContentWrapper.this.mGestureDetector.onTouchEvent(event);
                switch (event.getAction()) {
                    case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                        ContentWrapper.this.mUp = false;
                        ContentWrapper.this.mIsFling = false;
                        this.downY = event.getY();
                        break;
                    case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                        this.upY = event.getY();
                        ContentWrapper.this.mUp = true;
                        float moveInstance = this.upY - this.downY;
                        if (ContentWrapper.this.mIsFling || moveInstance >= 0.0f) {
                            ContentWrapper.this.mIsFling = false;
                        } else {
                            ContentWrapper.this.doScroll();
                        }
                        this.downY = 0.0f;
                        this.upY = 0.0f;
                        break;
                }
                return ContentWrapper.this.mGestureDetector.onTouchEvent(event);
            }
        });
        mWeatherScrollView.setScrollViewListener(new ScrollViewListener() {
            public void onScrollChanged(WeatherScrollView scrollView, int x, int y, int oldx, int oldy) {
                ContentWrapper.this.mOffset = (float) y;
                if (ContentWrapper.this.mOffset == 0.0f) {
                    ContentWrapper.this.mMoved = false;
                }
                ContentWrapper.this.changeTopColor();
            }
        });
    }

    public void changeTopColor() {
        int margin;
        View secondView = getChild(R.id.opweather_info);
        int moveToOffset = (int) secondView.getY();
        RootWeather data = this.mCityData.getWeathers();
        boolean isChinaCity = true;
        if (data != null) {
            isChinaCity = data.isFromChina();
        }
        if (isChinaCity) {
            changeTopViewTextColor(moveToOffset);
        } else {
            changeTopViewTextColor(GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE);
        }
        if (this.mOffset > 0.0f) {
            float dy = ((float) moveToOffset) - this.mOffset;
            if (dy < 0.0f) {
                dy = AutoScrollHelper.RELATIVE_UNSPECIFIED;
            }
            float alpha = dy / ((float) moveToOffset);
            if (this.mUIListener != null) {
                this.mUIListener.onScrollViewChange(alpha);
            }
        }
        int marginMove = (int) ((((float) secondView.getHeight()) * this.mOffset) / ((float) moveToOffset));
        if (marginMove > secondView.getHeight()) {
            margin = secondView.getHeight();
        } else {
            margin = marginMove;
        }
        getChild(R.id.current_opweather_overlay).scrollTo(0, margin * 2);
    }

    public void resetScrollView() {
        this.mOffset = 0.0f;
        ((ScrollView) this.content.findViewById(R.id.weather_scrollview)).scrollTo(0, 0);
        changeTopViewTextColor(ConnectionResult.DEVELOPER_ERROR);
    }

    public void changeTopViewTextColor(int moveToOffset) {
        int y = (int) getChild(R.id.opweather_info).getY();
        if (this.mCityData.getWeathers() == null) {
            changePathMenuResource(false, this.mLoading);
            return;
        }
        int weatherId = WeatherResHelper.weatherToResID(this.mContext, this.mCityData.getWeathers().getCurrentWeatherId());
        if (weatherId == 9999) {
            weatherId = WeatherResHelper.weatherToResID(this.mContext, this.cacheWeatherID);
        }
        if (!needGrayColor(weatherId)) {
            changePathMenuResource(false, this.mLoading);
        } else if (this.mOffset >= ((float) moveToOffset)) {
            changePathMenuResource(false, this.mLoading);
        } else {
            changePathMenuResource(true, this.mLoading);
        }
    }

    public void resetTopViewTextColor() {
        if (this.mCityData.getWeathers() != null) {
            int weatherId = WeatherResHelper.weatherToResID(this.mContext, this.mCityData.getWeathers().getCurrentWeatherId());
            if (weatherId == 9999) {
                weatherId = WeatherResHelper.weatherToResID(this.mContext, this.cacheWeatherID);
            }
            View secondView = getChild(R.id.opweather_info);
            RootWeather data = this.mCityData.getWeathers();
            boolean isChinaCity = true;
            if (data != null) {
                isChinaCity = data.isFromChina();
            }
            if (this.mOffset >= ((float) (isChinaCity ? (int) secondView.getY() : GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE))) {
                return;
            }
            if (needGrayColor(weatherId)) {
                changePathMenuResource(true, this.mLoading);
            } else {
                changePathMenuResource(false, this.mLoading);
            }
        }
    }

    public boolean needGrayColor(int weatherId) {
        RootWeather data = this.mCityData.getWeathers();
        boolean isDay = true;
        if (data != null) {
            try {
                isDay = this.mCityData.isDay(data);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return weatherId == 1003 && isDay;
    }

    public void doScroll() {
        View firstView = getChild(R.id.current_opweather_overlay);
        View secondView = getChild(R.id.opweather_info);
        int moveToOffset = (int) secondView.getY();
        this.mNeedMoveOffset = (firstView.getHeight() - secondView.getHeight()) / 5;
        if (this.mOffset <= ((float) this.mNeedMoveOffset) || this.mOffset >= ((float) (this.mNeedMoveOffset * 5))) {
            if (this.mOffset <= ((float) this.mNeedMoveOffset)) {
                startScroll(0);
                this.mMoved = false;
            }
        } else if (!this.mMoved && this.mUp) {
            startScroll(moveToOffset);
            this.mMoved = true;
        }
    }

    public void startScroll(int offset) {
        this.mScrollHandler.post(new AnonymousClass_7(offset));
    }

    public int getWeatherColor(Context context, int weatherId, boolean isDay) {
        return context.getResources().getColor(WeatherResHelper.getWeatherColorStringID(weatherId, isDay));
    }

    public boolean isDay() {
        if (this.mWeatherData == null) {
            return true;
        }
        try {
            return this.mCityData.isDay(this.mWeatherData);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void onScrolled(float a, int position) {
        if (this.index >= position - 1 && this.index <= position + 1) {
            changeTopColor();
            float f = a;
            if (this.index == position) {
                if (a < 1.0f && ((double) a) >= 0.5d) {
                    f = (2.0f * a) - 1.0f;
                } else if (((double) a) < 0.5d) {
                }
            } else if (this.index < position) {
                f = (1.0f - a) * 2.0f;
            } else if (this.index > position) {
                float a1 = a * 2.0f;
                if (a <= 0.5f) {
                    f = 1.0f - a1;
                }
            }
            resetTopViewTextColor();
        }
    }

    private void changePathMenuResource(boolean isBlack, boolean isLoading) {
        if (this.mUIListener != null) {
            this.mUIListener.ChangePathMenuResource(this.index, isBlack, isLoading);
        }
    }

    public void onSelected(int position) {
        if (this.index >= position - 1 && this.index <= position + 1) {
            resetScrollView();
            ((ScrollView) this.content.findViewById(R.id.weather_scrollview)).scrollTo(0, 0);
        }
    }

    public void refreshUnit() {
        updateCurrentWeatherUI();
    }
}
