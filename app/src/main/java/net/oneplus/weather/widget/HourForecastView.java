package net.oneplus.weather.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.google.android.gms.common.ConnectionResult;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import net.oneplus.weather.R;
import net.oneplus.weather.api.helper.DateUtils;
import net.oneplus.weather.api.nodes.DailyForecastsWeather;
import net.oneplus.weather.api.nodes.HourForecastsWeather;
import net.oneplus.weather.api.nodes.Sun;
import net.oneplus.weather.api.nodes.Temperature;
import net.oneplus.weather.model.HourForecastsWeatherData;
import net.oneplus.weather.util.DateTimeUtils;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.util.SystemSetting;
import net.oneplus.weather.util.WeatherResHelper;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class HourForecastView extends FrameLayout {
    private HourForecastAdapter mAdapter;
    private Context mContext;
    private LinearLayoutManager mLayoutManager;
    private final RecyclerView mRecyclerView;
    private SpacesItemDecoration mSpacesItemDecoration;

    public class AsyncTaskHourLoad extends AsyncTask<Void, Void, List<HourForecastsWeatherData>> {
        private HourForecastAdapter adapter;
        private List<HourForecastsWeather> dataset;
        private int mCurrentTemp;
        private Sun mSun;

        public AsyncTaskHourLoad(HourForecastAdapter adapter, List<HourForecastsWeather> dataset, List<DailyForecastsWeather> dailyDate, int currentTemp, String timeZoneStr) {
            this.adapter = null;
            this.dataset = null;
            this.mSun = null;
            this.adapter = adapter;
            this.dataset = dataset;
            this.mCurrentTemp = currentTemp;
            DailyForecastsWeather today = DailyForecastsWeather.getTodayForecast(dailyDate, DateUtils.getTimeZone(timeZoneStr));
            if (today != null) {
                this.mSun = today.getRealSun(dailyDate, DateUtils.getTimeZone(timeZoneStr));
            }
        }

        protected List<HourForecastsWeatherData> doInBackground(Void... params) {
            List<HourForecastsWeatherData> result = new ArrayList();
            int size = this.dataset.size();
            for (int i = 0; i < size; i++) {
                int iconId;
                String tempText;
                HourForecastsWeather weather = (HourForecastsWeather) this.dataset.get(i);
                Date time = weather.getTime();
                String hourText = HourForecastView.this.getHourText(time, this.mSun);
                if (this.mSun != null && HourForecastView.this.checkSunTime(this.mSun.getSet(), time)) {
                    iconId = R.drawable.ic_sunset;
                    tempText = HourForecastView.this.mContext.getString(R.string.sunset);
                } else if (this.mSun == null || !HourForecastView.this.checkSunTime(this.mSun.getRise(), time)) {
                    iconId = WeatherResHelper.getWeatherIconResID(WeatherResHelper.weatherToResID(HourForecastView.this.mContext, weather.getWeatherId()));
                    Temperature temperature = weather.getTemperature();
                    if (temperature == null || temperature.getCentigradeValue() == Double.NaN) {
                        tempText = StringUtils.EMPTY_STRING;
                    } else {
                        int temp;
                        float f;
                        if (hourText.equals(HourForecastView.this.mContext.getString(R.string.now))) {
                            temp = this.mCurrentTemp;
                        } else {
                            temp = (int) Math.floor(temperature.getCentigradeValue());
                        }
                        boolean cOrf = SystemSetting.getTemperature(HourForecastView.this.mContext);
                        String tempUnit = cOrf ? "\u00b0" : "\u00b0";
                        if (cOrf) {
                            f = (float) temp;
                        } else {
                            f = SystemSetting.celsiusToFahrenheit((float) temp);
                        }
                        tempText = ((int) f) + tempUnit;
                    }
                } else {
                    iconId = R.drawable.ic_sunrise;
                    tempText = HourForecastView.this.mContext.getString(R.string.sunrise);
                }
                if (result != null) {
                    result.add(new HourForecastsWeatherData(hourText, weather.getWeatherId(), iconId, tempText));
                }
            }
            return result;
        }

        protected void onPostExecute(List<HourForecastsWeatherData> result) {
            super.onPostExecute(result);
            if (result != null && result.size() > 0 && this.adapter != null) {
                this.adapter.bindForecastData(result);
                this.adapter.notifyDataSetChanged();
            }
        }
    }

    public HourForecastView(Context context) {
        this(context, null);
    }

    public HourForecastView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HourForecastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.hour_forecast_layout, this, true);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.mRecyclerView.setHasFixedSize(true);
        this.mLayoutManager = createLayoutManager(context);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mAdapter = new HourForecastAdapter(context);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mSpacesItemDecoration = new SpacesItemDecoration(this.mLayoutManager.getOrientation(), context.getResources().getDimensionPixelSize(R.dimen.dimen_14), context.getResources().getDimensionPixelSize(R.dimen.dimen_23));
        this.mRecyclerView.addItemDecoration(this.mSpacesItemDecoration);
    }

    public void updateForecastData(List<HourForecastsWeather> dataset, List<DailyForecastsWeather> dailyDate, int currentTemp, String timeZone) {
        if (this.mAdapter != null) {
            new AsyncTaskHourLoad(this.mAdapter, dataset, dailyDate, currentTemp, timeZone).execute(new Void[0]);
        }
    }

    private LinearLayoutManager createLayoutManager(Context context) {
        LinearLayoutManager manager = new HourForecastLinearLayoutManager(context);
        manager.setOrientation(0);
        return manager;
    }

    private String getHourText(Date time, Sun mSun) {
        if (time == null) {
            return StringUtils.EMPTY_STRING;
        }
        if (mSun == null || !checkSunTime(mSun.getRise(), time)) {
            return (mSun == null || !checkSunTime(mSun.getSet(), time)) ? DateTimeUtils.DateTimeToHourMinute(time, null) : DateTimeUtils.DateTimeToHourMinute(mSun.getSet(), null);
        } else {
            return DateTimeUtils.DateTimeToHourMinute(mSun.getRise(), null);
        }
    }

    private boolean isNow(Date time) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(time);
        Calendar calendar2 = Calendar.getInstance();
        return calendar1.get(1) == calendar2.get(1) && calendar1.get(RainSurfaceView.RAIN_LEVEL_SHOWER) == calendar2.get(RainSurfaceView.RAIN_LEVEL_SHOWER) && calendar1.get(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER) == calendar2.get(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER) && calendar1.get(ConnectionResult.LICENSE_CHECK_FAILED) == calendar2.get(ConnectionResult.LICENSE_CHECK_FAILED);
    }

    private boolean checkSunTime(Date sunTime, Date hourTime) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(sunTime);
        Calendar calendar2 = Calendar.getInstance();
        if (hourTime != null) {
            calendar2.setTime(hourTime);
        }
        if (calendar1.get(1) == calendar2.get(1) && calendar1.get(RainSurfaceView.RAIN_LEVEL_SHOWER) == calendar2.get(RainSurfaceView.RAIN_LEVEL_SHOWER) && (calendar1.get(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER) == calendar2.get(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER) || calendar1.get(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER) - calendar2.get(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER) == -1)) {
            return calendar1.get(ConnectionResult.LICENSE_CHECK_FAILED) == calendar2.get(ConnectionResult.LICENSE_CHECK_FAILED);
        } else {
            return false;
        }
    }

    private boolean checkNow(Date time) {
        return DateTimeUtils.distanceOfHour(time) == 1;
    }
}
