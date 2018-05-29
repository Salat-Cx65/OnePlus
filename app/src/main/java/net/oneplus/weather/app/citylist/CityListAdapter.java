package net.oneplus.weather.app.citylist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import net.oneplus.weather.api.WeatherException;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.db.ChinaCityDB;
import net.oneplus.weather.db.CityWeatherDB;
import net.oneplus.weather.db.CityWeatherDB.CityListDBListener;
import net.oneplus.weather.db.CityWeatherDBHelper.CityListEntry;
import net.oneplus.weather.db.CityWeatherDBHelper.WeatherEntry;
import net.oneplus.weather.model.CityData;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.util.SystemSetting;
import net.oneplus.weather.util.WeatherClientProxy;
import net.oneplus.weather.util.WeatherClientProxy.CacheMode;
import net.oneplus.weather.util.WeatherClientProxy.OnResponseListener;
import net.oneplus.weather.util.WeatherResHelper;
import net.oneplus.weather.widget.WeatherTemperatureView;

public class CityListAdapter extends IgnorCursorAdapter implements AnimationListener {
    private static final int NO_TEMP_DATA_FLAG = -2000;
    private boolean isWidgeMode;
    private boolean mCanScroll;
    private CityListDBListener mCityListDBListener;
    private CityWeatherDB mCityWeatherDB;
    private Context mContext;
    private Map<String, Boolean> mLoadItems;
    private OnDefaulatChangeListener mOnDefaulatChangeListener;
    private Map<String, RootWeather> mWeatherMap;

    class AnonymousClass_2 implements OnClickListener {
        final /* synthetic */ ItemHolder val$itemHolder;
        final /* synthetic */ View val$view;

        AnonymousClass_2(View view, ItemHolder itemHolder) {
            this.val$view = view;
            this.val$itemHolder = itemHolder;
        }

        public void onClick(View v) {
            int position = -1;
            if (this.val$view.getTag() != null) {
                position = ((Integer) this.val$view.getTag()).intValue();
            }
            if (position != -1 && CityListAdapter.this.mOnDefaulatChangeListener != null && !CityListAdapter.this.isDefaultCity(position)) {
                CityListAdapter.this.mOnDefaulatChangeListener.onChanged(position);
                this.val$itemHolder.homeView.setImageResource(R.drawable.btn_home_enable);
            }
        }
    }

    public class ItemHolder {
        String cityDisplayName;
        TextView cityNameView;
        TextView cityTempType;
        TextView cityTempView;
        ImageView cityThemeView;
        ImageView currentLocationView;
        View homeBtnView;
        ImageView homeView;
        private WeakReference<WeatherWorkerClient> workerClient;

        public void setWorkerClient(WeatherWorkerClient client) {
            this.workerClient = new WeakReference(client);
        }

        public WeatherWorkerClient getWorkerClient() {
            return this.workerClient != null ? this.workerClient.get() : null;
        }
    }

    public static interface OnDefaulatChangeListener {
        void onChanged(int i);
    }

    private class WeatherWorkerClient {
        private final CityData mCityData;
        private final WeakReference<ItemHolder> mItemHolder;

        public WeatherWorkerClient(ItemHolder item, CityData cityData) {
            this.mItemHolder = new WeakReference(item);
            this.mCityData = cityData;
        }

        public void loadWeather() {
            new WeatherClientProxy(CityListAdapter.this.mContext).setCacheMode(CacheMode.LOAD_CACHE_ELSE_NETWORK).requestWeatherInfo(this.mCityData, new OnResponseListener() {
                public void onNetworkResponse(RootWeather response) {
                    WeatherWorkerClient.this.onResponse(response);
                }

                public void onErrorResponse(WeatherException error) {
                    WeatherWorkerClient.this.onResponse(null);
                }

                public void onCacheResponse(RootWeather response) {
                    WeatherWorkerClient.this.onResponse(response);
                }
            });
        }

        private void onResponse(RootWeather response) {
            if (response != null) {
                CityListAdapter.this.mWeatherMap.put(response.getAreaCode(), response);
            }
            ItemHolder holder = (ItemHolder) this.mItemHolder.get();
            if (holder != null && this == holder.getWorkerClient()) {
                CityListAdapter.this.updateView(holder, response, this.mCityData);
            }
            String locationId = this.mCityData.getLocationId();
            if (!TextUtils.isEmpty(locationId)) {
                CityListAdapter.this.mLoadItems.remove(locationId);
            }
        }
    }

    protected CityListAdapter(Context context, Cursor cursor, boolean autoRequery) {
        super(context, cursor, autoRequery);
        this.mCityListDBListener = new CityListDBListener() {
            public void onCityAdded(long newId) {
                CityListAdapter.this.requery();
            }

            public void onCityDeleted(long deletedId) {
            }

            public void onCityUpdated(long recordId) {
            }
        };
        this.mCanScroll = false;
        this.mContext = context;
        this.mLoadItems = new ConcurrentHashMap();
        this.mWeatherMap = new WeakHashMap();
        this.mCityWeatherDB = CityWeatherDB.getInstance(context);
        this.mCityWeatherDB.addDataChangeListener(this.mCityListDBListener);
    }

    public void onDestroy() {
        if (this.mCityWeatherDB != null) {
            this.mCityWeatherDB.removeDataChangeListener(this.mCityListDBListener);
            this.mCityWeatherDB = null;
        }
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(this.mContext).inflate(R.layout.citylist_item, parent, false);
    }

    private void setNewHolder(View view, ItemHolder holder) {
        holder.cityNameView = (TextView) view.findViewById(R.id.cityName);
        holder.cityTempView = (TextView) view.findViewById(R.id.cityTemp);
        holder.cityTempType = (TextView) view.findViewById(R.id.weather_type);
        holder.cityThemeView = (ImageView) view.findViewById(R.id.cityTheme);
        holder.currentLocationView = (ImageView) view.findViewById(R.id.current_location);
        holder.homeView = (ImageView) view.findViewById(R.id.img_city_home);
        holder.homeBtnView = view.findViewById(R.id.btn_city_home);
    }

    public void requery() {
        clearDelete();
        if (this.mCursor != null) {
            this.mCursor.requery();
        }
        notifyDataSetChanged();
    }

    private CityData getCityFromCoursor(Cursor cursor) {
        int provider = cursor.getInt(cursor.getColumnIndex(CityListEntry.COLUMN_1_PROVIDER));
        String cityName = cursor.getString(cursor.getColumnIndex(CityListEntry.COLUMN_2_NAME));
        String cityDisplayName = cursor.getString(cursor.getColumnIndex(CityListEntry.COLUMN_3_DISPLAY_NAME));
        String cityLocationId = cursor.getString(cursor.getColumnIndex(WeatherEntry.COLUMN_1_LOCATION_ID));
        CityData city = new CityData();
        city.setProvider(provider);
        city.setName(cityName);
        city.setLocalName(cityDisplayName);
        city.setLocationId(cityLocationId);
        return city;
    }

    public void bindView(View view, Context context, Cursor cursor) {
        if (view != null && cursor != null) {
            view.setTag(Integer.valueOf(cursor.getPosition()));
            ItemHolder itemHolder = new ItemHolder();
            setNewHolder(view, itemHolder);
            long cityId = cursor.getLong(0);
            int orderId = cursor.getInt(ConnectionResult.SERVICE_INVALID);
            CityData cityData = getCityFromCoursor(cursor);
            String cityLocationId = cityData.getLocationId();
            if (TextUtils.isEmpty(cityLocationId)) {
                updateView(itemHolder, cityData);
                return;
            }
            itemHolder.cityDisplayName = cityData.getLocalName();
            if (0 == cityId) {
                itemHolder.currentLocationView.setVisibility(0);
            } else {
                itemHolder.currentLocationView.setVisibility(DetectedActivity.RUNNING);
            }
            RootWeather weather = (RootWeather) this.mWeatherMap.get(cityLocationId);
            updateView(itemHolder, weather, cityData);
            if (weather == null && this.mLoadItems.get(cityLocationId) == null) {
                this.mLoadItems.put(cityLocationId, Boolean.valueOf(true));
                WeatherWorkerClient client = new WeatherWorkerClient(itemHolder, cityData);
                itemHolder.setWorkerClient(client);
                client.loadWeather();
            }
            if (this.mCanScroll && cursor.getPosition() == cursor.getCount() - 1) {
                view.clearAnimation();
                Animation animation = AnimationUtils.loadAnimation(this.mContext, R.anim.spring_from_bottom);
                animation.setAnimationListener(this);
                view.startAnimation(animation);
            }
            if (orderId == -1) {
                itemHolder.homeView.setImageResource(R.drawable.btn_home_enable);
            } else if (cityId == 0 && orderId == 0 && cursor.getPosition() == 0) {
                itemHolder.homeView.setImageResource(R.drawable.btn_home_enable);
            }
            if (isWidgeMode()) {
                itemHolder.homeBtnView.setVisibility(DetectedActivity.RUNNING);
                itemHolder.homeBtnView.setClickable(false);
                return;
            }
            itemHolder.homeBtnView.setOnClickListener(new AnonymousClass_2(view, itemHolder));
        }
    }

    public boolean isWidgeMode() {
        return this.isWidgeMode;
    }

    public void setWidgeMode(boolean widgeMode) {
        this.isWidgeMode = widgeMode;
    }

    public int getItemViewType(int position) {
        return (isocationCity(position) || isDefaultCity(position)) ? -1 : super.getItemViewType(position);
    }

    public int getCount() {
        return super.getCount();
    }

    private boolean isDay(RootWeather data, CityData cityData) {
        try {
            return cityData.isDay(data);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return true;
        }
    }

    private void updateView(ItemHolder holder, RootWeather data, CityData cityData) {
        TextView cityNameView = holder.cityNameView;
        TextView cityTempView = holder.cityTempView;
        TextView cityTempType = holder.cityTempType;
        ImageView cityThemeView = holder.cityThemeView;
        ImageView currentLocationView = holder.currentLocationView;
        cityNameView.setText(holder.cityDisplayName);
        cityNameView.setTextColor(this.mContext.getResources().getColor(R.color.white));
        cityThemeView.setImageResource(2131230842);
        cityTempView.setText("--");
        if (data != null) {
            float f;
            boolean cOrf = SystemSetting.getTemperature(this.mContext);
            String tempUnit = cOrf ? "\u00b0" : "\u00b0";
            int currentTemp = data.getTodayCurrentTemp();
            if (cOrf) {
                f = (float) currentTemp;
            } else {
                f = SystemSetting.celsiusToFahrenheit((float) currentTemp);
            }
            int curTemp = (int) f;
            String str = "--";
            if (curTemp < -2000) {
                str = "--" + tempUnit;
            } else {
                str = curTemp + tempUnit;
            }
            cityTempView.setText(str);
            cityTempType.setText(data.getCurrentWeatherText(this.mContext));
            if (cityData != null) {
                ChinaCityDB.openCityDB(this.mContext).getCityTimeZone(cityData.getLocationId());
            }
            boolean isDay = isDay(data, cityData);
            int descriptionId = WeatherResHelper.weatherToResID(this.mContext, data.getCurrentWeatherId());
            cityThemeView.setImageResource(WeatherResHelper.getWeatherListitemBkgResID(descriptionId, isDay));
            if (descriptionId == 1003 && isDay) {
                currentLocationView.setImageResource(2131231009);
                cityNameView.setTextColor(Color.parseColor("#757575"));
                return;
            }
            currentLocationView.setImageResource(2131231008);
            cityNameView.setTextColor(Color.parseColor(WeatherTemperatureView.DEFAULT_HTEMP_POINT_COLOR));
        }
    }

    private void updateView(ItemHolder holder, CityData city) {
        TextView cityNameView = holder.cityNameView;
        TextView cityTempView = holder.cityTempView;
        TextView cityTempType = holder.cityTempType;
        ImageView cityThemeView = holder.cityThemeView;
        ImageView imageView = holder.currentLocationView;
        cityNameView.setTextColor(this.mContext.getResources().getColor(R.color.white));
        cityTempType.setText(this.mContext.getString(R.string.default_weather));
        cityThemeView.setImageResource(R.drawable.bkg_sunny);
        cityTempView.setText("--");
        if (city == null) {
            cityNameView.setText(this.mContext.getString(R.string.current_location));
        } else {
            cityNameView.setText(city.getName());
        }
    }

    public void setCanScroll(boolean canScroll) {
        this.mCanScroll = canScroll;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        this.mCanScroll = false;
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void setOnDefaulatChangeListener(OnDefaulatChangeListener onDefaulatChangeListener) {
        this.mOnDefaulatChangeListener = onDefaulatChangeListener;
    }

    public boolean isDefaultCity(int position) {
        Cursor cursor = getCursor();
        String orderId = StringUtils.EMPTY_STRING;
        if (cursor.getCount() > position) {
            cursor.moveToPosition(position);
            orderId = cursor.getString(ConnectionResult.SERVICE_INVALID);
        }
        return "-1".equals(orderId);
    }

    public boolean isocationCity(int position) {
        Cursor cursor = getCursor();
        String orderId = StringUtils.EMPTY_STRING;
        if (cursor.getCount() > position) {
            cursor.moveToPosition(position);
            orderId = cursor.getString(0);
        }
        return "0".equals(orderId);
    }
}
