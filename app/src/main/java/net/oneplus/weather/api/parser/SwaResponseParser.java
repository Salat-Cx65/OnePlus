package net.oneplus.weather.api.parser;

import com.google.android.gms.common.ConnectionResult;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import net.oneplus.weather.api.helper.DateUtils;
import net.oneplus.weather.api.helper.IOUtils;
import net.oneplus.weather.api.helper.LogUtils;
import net.oneplus.weather.api.helper.NumberUtils;
import net.oneplus.weather.api.helper.StringUtils;
import net.oneplus.weather.api.helper.WeatherUtils;
import net.oneplus.weather.api.impl.SwaRequest;
import net.oneplus.weather.api.nodes.Alarm;
import net.oneplus.weather.api.nodes.DailyForecastsWeather;
import net.oneplus.weather.api.nodes.HourForecastsWeather;
import net.oneplus.weather.api.nodes.LifeIndexWeather.LifeIndex;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.api.nodes.Sun;
import net.oneplus.weather.api.nodes.SwaAlarm;
import net.oneplus.weather.api.nodes.SwaAqiWeather;
import net.oneplus.weather.api.nodes.SwaCurrentWeather;
import net.oneplus.weather.api.nodes.SwaDailyForecastsWeather;
import net.oneplus.weather.api.nodes.SwaHourForecastsWeather;
import net.oneplus.weather.api.nodes.SwaLifeIndexWeather;
import net.oneplus.weather.api.nodes.SwaWind;
import net.oneplus.weather.api.nodes.Temperature;
import net.oneplus.weather.api.nodes.Wind;
import net.oneplus.weather.api.parser.ResponseParser.WeatherBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class SwaResponseParser implements ResponseParser {
    private static final String CONTENT_CHARSET = "utf-8";
    private static final String TAG = "SwaResponseParser";
    private final String mSearchKey;

    private static class CurrentWeatherBuilder implements WeatherBuilder {
        String mSearchKey;
        String relativeHumidity;
        String temperature;
        String time;
        String weather;
        String windDirection;
        String windPower;

        private CurrentWeatherBuilder() {
            this.mSearchKey = null;
            this.temperature = null;
            this.relativeHumidity = null;
            this.windPower = null;
            this.windDirection = null;
            this.weather = null;
            this.time = null;
        }

        public RootWeather build() throws BuilderException {
            if (StringUtils.isBlank(this.mSearchKey)) {
                throw new BuilderException("Valid area code empty.");
            }
            int iWeatherId = WeatherUtils.swaWeatherIdToWeatherId(this.weather);
            Date iObservationDate = DateUtils.parseSwaCurrentDate(this.time);
            double iCentigradeValue = NumberUtils.valueToDouble(this.temperature);
            double iFahrenheitValue = WeatherUtils.centigradeToFahrenheit(iCentigradeValue);
            Temperature iTemperature = null;
            Wind swaWind = new SwaWind(this.mSearchKey, this.windDirection, this.windPower);
            if (!(NumberUtils.isNaN(iCentigradeValue) || NumberUtils.isNaN(iFahrenheitValue))) {
                iTemperature = new Temperature(this.mSearchKey, SwaRequest.DATA_SOURCE_NAME, iCentigradeValue, iFahrenheitValue);
            }
            RootWeather result = new RootWeather(this.mSearchKey, SwaRequest.DATA_SOURCE_NAME);
            result.setCurrentWeather(new SwaCurrentWeather(this.mSearchKey, iWeatherId, iObservationDate, iTemperature, NumberUtils.valueToInt(this.relativeHumidity), swaWind, Integer.MIN_VALUE, net.oneplus.weather.util.StringUtils.EMPTY_STRING));
            return result;
        }
    }

    private static class DailyForecastsWeatherBuilder implements WeatherBuilder {
        private List<ItemHolder> itemList;
        String mCityNameCn;
        String mSearchKey;

        static class ItemHolder {
            String dayTemperature;
            String dayWeather;
            String nightTemperature;
            String nightWeather;
            String sunriseAndSunset;

            ItemHolder() {
                this.dayWeather = null;
                this.nightWeather = null;
                this.dayTemperature = null;
                this.nightTemperature = null;
                this.sunriseAndSunset = null;
            }
        }

        private DailyForecastsWeatherBuilder() {
            this.mSearchKey = null;
            this.mCityNameCn = null;
        }

        public void add(ItemHolder item) {
            if (this.itemList == null) {
                this.itemList = new ArrayList();
            }
            this.itemList.add(item);
        }

        public RootWeather build() throws BuilderException {
            if (StringUtils.isBlank(this.mSearchKey)) {
                throw new BuilderException("Valid area code empty.");
            } else if (this.itemList == null) {
                throw new BuilderException("Valid forecasts is empty.");
            } else {
                RootWeather rootWeather = new RootWeather(this.mSearchKey, this.mCityNameCn, SwaRequest.DATA_SOURCE_NAME);
                List<DailyForecastsWeather> list = new ArrayList();
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"), Locale.CHINA);
                for (ItemHolder holder : this.itemList) {
                    int iDayWeatherId = WeatherUtils.swaWeatherIdToWeatherId(holder.dayWeather);
                    int iNightWeatherId = WeatherUtils.swaWeatherIdToWeatherId(holder.nightWeather);
                    Temperature iDayTemperature = null;
                    Temperature iNightTemperature = null;
                    double iDayCentigradeValue = NumberUtils.valueToDouble(holder.dayTemperature);
                    double iDayFahrenheitValue = WeatherUtils.centigradeToFahrenheit(iDayCentigradeValue);
                    double iNightCentigradeValue = NumberUtils.valueToDouble(holder.nightTemperature);
                    double iNightFahrenheitValue = WeatherUtils.centigradeToFahrenheit(iDayCentigradeValue);
                    if (!NumberUtils.isNaN(iDayCentigradeValue)) {
                        iDayTemperature = new Temperature(this.mSearchKey, SwaRequest.DATA_SOURCE_NAME, iDayCentigradeValue, iDayFahrenheitValue);
                    }
                    if (!NumberUtils.isNaN(iNightCentigradeValue)) {
                        iNightTemperature = new Temperature(this.mSearchKey, SwaRequest.DATA_SOURCE_NAME, iNightCentigradeValue, iNightFahrenheitValue);
                    }
                    Sun iSun = null;
                    Date rise = SwaResponseParser.getSunDate(calendar.getTime(), holder.sunriseAndSunset, true);
                    Date set = SwaResponseParser.getSunDate(calendar.getTime(), holder.sunriseAndSunset, false);
                    if (rise != null && set != null) {
                        Sun sun = new Sun(this.mSearchKey, SwaRequest.DATA_SOURCE_NAME, rise, set);
                    }
                    list.add(new SwaDailyForecastsWeather(this.mSearchKey, this.mCityNameCn, iDayWeatherId, iNightWeatherId, calendar.getTime(), iDayTemperature, iNightTemperature, iSun));
                    calendar.add(ConnectionResult.RESOLUTION_REQUIRED, 1);
                }
                if (list.size() == 0) {
                    throw new BuilderException("Empty forecasts weather!");
                }
                rootWeather.setDailyForecastsWeather(list);
                return rootWeather;
            }
        }
    }

    private static class HourForecastsWeatherBuilder implements WeatherBuilder {
        private List<ItemHolder> itemList;
        String mSearchKey;

        static class ItemHolder {
            String forecastTime;
            String temperature;
            String weatherId;

            ItemHolder() {
                this.weatherId = null;
                this.temperature = null;
                this.forecastTime = null;
            }
        }

        private HourForecastsWeatherBuilder() {
            this.mSearchKey = null;
        }

        public void add(ItemHolder item) {
            if (this.itemList == null) {
                this.itemList = new ArrayList();
            }
            this.itemList.add(item);
        }

        public RootWeather build() throws BuilderException {
            if (StringUtils.isBlank(this.mSearchKey)) {
                throw new BuilderException("Valid area code empty.");
            } else if (this.itemList == null) {
                throw new BuilderException("Valid forecasts is empty.");
            } else {
                RootWeather result = new RootWeather(this.mSearchKey, null, SwaRequest.DATA_SOURCE_NAME);
                List<HourForecastsWeather> list = new ArrayList();
                for (ItemHolder holder : this.itemList) {
                    HourForecastsWeather forecastsWeather = SwaHourForecastsWeather.buildFromString(this.mSearchKey, holder.weatherId, holder.forecastTime, holder.temperature);
                    if (forecastsWeather != null) {
                        list.add(forecastsWeather);
                    }
                }
                if (list.size() == 0) {
                    throw new BuilderException("Empty forecasts weather!");
                }
                result.setHourForecastsWeather(list);
                return result;
            }
        }
    }

    private static class LifeIndexBuilder implements WeatherBuilder {
        private List<ItemHolder> itemList;
        String mSearchKey;

        static class ItemHolder {
            String indexCnAlias;
            String indexCnName;
            String indexLevel;
            String indexShortName;
            String indexText;

            ItemHolder() {
                this.indexShortName = null;
                this.indexCnName = null;
                this.indexCnAlias = null;
                this.indexLevel = null;
                this.indexText = null;
            }
        }

        private LifeIndexBuilder() {
            this.mSearchKey = null;
        }

        public void add(ItemHolder item) {
            if (this.itemList == null) {
                this.itemList = new ArrayList();
            }
            this.itemList.add(item);
        }

        public RootWeather build() throws BuilderException {
            if (StringUtils.isBlank(this.mSearchKey)) {
                throw new BuilderException("Valid area code empty.");
            } else if (this.itemList == null) {
                throw new BuilderException("Valid life index is empty.");
            } else {
                RootWeather result = new RootWeather(this.mSearchKey, SwaRequest.DATA_SOURCE_NAME);
                SwaLifeIndexWeather lifeIndexWeather = new SwaLifeIndexWeather(this.mSearchKey);
                for (ItemHolder holder : this.itemList) {
                    lifeIndexWeather.add(new LifeIndex(holder.indexShortName, holder.indexCnName, holder.indexCnAlias, holder.indexLevel, holder.indexText));
                }
                if (lifeIndexWeather.size() == 0) {
                    throw new BuilderException("Empty forecasts weather!");
                }
                result.setLifeIndexWeather(lifeIndexWeather);
                return result;
            }
        }
    }

    private static class WeatherAlarmBuilder implements WeatherBuilder {
        private List<ItemHolder> itemList;
        String mSearchKey;

        static class ItemHolder {
            String alarmAreaName;
            String contentText;
            String levelName;
            String levelNo;
            String publishTime;
            String typeName;
            String typeNo;

            ItemHolder() {
            }
        }

        private WeatherAlarmBuilder() {
            this.mSearchKey = null;
        }

        public void add(ItemHolder item) {
            if (this.itemList == null) {
                this.itemList = new ArrayList();
            }
            this.itemList.add(item);
        }

        public RootWeather build() throws BuilderException {
            if (StringUtils.isBlank(this.mSearchKey)) {
                throw new BuilderException("Valid area code empty.");
            }
            RootWeather result = new RootWeather(this.mSearchKey, SwaRequest.DATA_SOURCE_NAME);
            List<Alarm> list = new ArrayList();
            if (this.itemList != null) {
                for (ItemHolder holder : this.itemList) {
                    SwaAlarm alarm = SwaAlarm.build(this.mSearchKey, holder.alarmAreaName, holder.typeName, holder.typeNo, holder.levelName, holder.levelNo, holder.contentText, holder.publishTime);
                    if (alarm != null) {
                        list.add(alarm);
                    }
                }
            }
            result.setWeatherAlarms(list);
            return result;
        }
    }

    public SwaResponseParser(String key) {
        this.mSearchKey = key;
    }

    public RootWeather parseCurrent(byte[] data) throws ParseException {
        if (data == null) {
            throw new ParseException("The data to parser is null!");
        }
        CurrentWeatherBuilder builder = new CurrentWeatherBuilder();
        try {
            JSONObject current = new JSONObject(IOUtils.byteArrayToString(data, CONTENT_CHARSET)).getJSONObject("l");
            builder.mSearchKey = this.mSearchKey;
            builder.temperature = current.getString("l1");
            builder.relativeHumidity = current.getString("l2");
            builder.windPower = current.getString("l3");
            builder.windDirection = current.getString("l4");
            builder.weather = current.getString("l5");
            builder.time = current.getString("l7");
            return builder.build();
        } catch (Exception e) {
            LogUtils.e(TAG, "Can not parse data!", e);
            throw new ParseException(e.getMessage());
        }
    }

    public RootWeather parseHourForecasts(byte[] data) throws ParseException {
        if (data == null) {
            throw new ParseException("The data to parser is null!");
        }
        HourForecastsWeatherBuilder builder = new HourForecastsWeatherBuilder();
        try {
            JSONObject jsObject = new JSONObject(IOUtils.byteArrayToString(data, CONTENT_CHARSET));
            builder.mSearchKey = this.mSearchKey;
            JSONArray jsonArray = jsObject.getJSONArray("jh");
            for (int i = 0; i < jsonArray.length(); i++) {
                ItemHolder holder = new ItemHolder();
                JSONObject item = jsonArray.getJSONObject(i);
                holder.weatherId = item.getString("ja");
                holder.temperature = item.getString("jb");
                holder.forecastTime = item.getString("jf");
                builder.add(holder);
            }
            return builder.build();
        } catch (Exception e) {
            LogUtils.e(TAG, "Can not parse data!", e);
            throw new ParseException(e.getMessage());
        }
    }

    public RootWeather parseDailyForecasts(byte[] data) throws ParseException {
        if (data == null) {
            throw new ParseException("The data to parser is null!");
        }
        DailyForecastsWeatherBuilder builder = new DailyForecastsWeatherBuilder();
        try {
            JSONObject jsObject = new JSONObject(IOUtils.byteArrayToString(data, CONTENT_CHARSET));
            JSONObject city = jsObject.getJSONObject("c");
            JSONObject forecasts = jsObject.getJSONObject("f");
            builder.mSearchKey = this.mSearchKey;
            builder.mCityNameCn = city.getString("c3");
            JSONArray jsonArray = forecasts.getJSONArray("f1");
            for (int i = 0; i < jsonArray.length(); i++) {
                ItemHolder holder = new ItemHolder();
                JSONObject item = jsonArray.getJSONObject(i);
                holder.dayWeather = item.getString("fa");
                holder.nightWeather = item.getString("fb");
                holder.dayTemperature = item.getString("fc");
                holder.nightTemperature = item.getString("fd");
                holder.sunriseAndSunset = item.getString("fi");
                builder.add(holder);
            }
            return builder.build();
        } catch (Exception e) {
            LogUtils.e(TAG, "Can not parse data!", e);
            throw new ParseException(e.getMessage());
        }
    }

    public RootWeather parseAqi(byte[] data) throws ParseException {
        if (data == null) {
            throw new ParseException("The data to parser is null!");
        }
        try {
            JSONObject airIndex = new JSONObject(IOUtils.byteArrayToString(data, CONTENT_CHARSET)).getJSONObject("p");
            String pm2_5 = airIndex.getString("p1");
            String aqi = airIndex.getString("p2");
            String time = airIndex.getString("p9");
            RootWeather root = new RootWeather(this.mSearchKey, SwaRequest.DATA_SOURCE_NAME);
            root.setAqiWeather(SwaAqiWeather.newInstance(this.mSearchKey, time, pm2_5, aqi));
            return root;
        } catch (Exception e) {
            LogUtils.e(TAG, "Can not parse data!", e);
            throw new ParseException(e.getMessage());
        }
    }

    public RootWeather parseLifeIndex(byte[] data) throws ParseException {
        if (data == null) {
            throw new ParseException("The data to parser is null!");
        }
        LifeIndexBuilder builder = new LifeIndexBuilder();
        try {
            builder.mSearchKey = this.mSearchKey;
            JSONArray jsonArray = new JSONObject(IOUtils.byteArrayToString(data, CONTENT_CHARSET)).getJSONArray("i");
            for (int i = 0; i < jsonArray.length(); i++) {
                ItemHolder holder = new ItemHolder();
                JSONObject item = jsonArray.getJSONObject(i);
                holder.indexShortName = item.getString("i1");
                holder.indexCnName = item.getString("i2");
                holder.indexCnAlias = item.getString("i3");
                holder.indexLevel = item.getString("i4");
                holder.indexText = item.getString("i5");
                builder.add(holder);
            }
            return builder.build();
        } catch (Exception e) {
            LogUtils.e(TAG, "Can not parse data!", e);
            throw new ParseException(e.getMessage());
        }
    }

    public RootWeather parseAlarm(byte[] data) throws ParseException {
        if (data == null) {
            throw new ParseException("The data to parser is null!");
        }
        WeatherAlarmBuilder builder = new WeatherAlarmBuilder();
        try {
            builder.mSearchKey = this.mSearchKey;
            JSONArray jsonArray = new JSONObject(IOUtils.byteArrayToString(data, CONTENT_CHARSET)).getJSONArray("w");
            for (int i = 0; i < jsonArray.length(); i++) {
                ItemHolder holder = new ItemHolder();
                JSONObject item = jsonArray.getJSONObject(i);
                String string = StringUtils.isBlank(item.getString("w3")) ? StringUtils.isBlank(item.getString("w2")) ? null : item.getString("w2") : item.getString("w3");
                holder.alarmAreaName = string;
                if (!StringUtils.isBlank(holder.alarmAreaName)) {
                    holder.typeName = item.getString("w5");
                    holder.typeNo = item.getString("w4");
                    holder.levelName = item.getString("w7");
                    holder.levelNo = item.getString("w6");
                    holder.contentText = item.getString("w9");
                    holder.publishTime = item.getString("w8");
                    builder.add(holder);
                }
            }
            return builder.build();
        } catch (Exception e) {
            LogUtils.e(TAG, "Can not parse data!", e);
            throw new ParseException(e.getMessage());
        }
    }

    private static Date getSunDate(Date date, String str, boolean rise) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] array = str.split("\\|");
        if (array.length < 2) {
            return null;
        }
        String dateStr;
        if (rise) {
            dateStr = array[0].trim();
        } else {
            dateStr = array[1].trim();
        }
        return DateUtils.parseSwaDate(date, dateStr);
    }
}
