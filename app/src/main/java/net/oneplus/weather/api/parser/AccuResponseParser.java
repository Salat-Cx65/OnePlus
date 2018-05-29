package net.oneplus.weather.api.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.oneplus.weather.api.helper.DateUtils;
import net.oneplus.weather.api.helper.IOUtils;
import net.oneplus.weather.api.helper.LogUtils;
import net.oneplus.weather.api.helper.NumberUtils;
import net.oneplus.weather.api.helper.StringUtils;
import net.oneplus.weather.api.helper.WeatherUtils;
import net.oneplus.weather.api.impl.AccuRequest;
import net.oneplus.weather.api.nodes.AccuAlarm;
import net.oneplus.weather.api.nodes.AccuCurrentWeather;
import net.oneplus.weather.api.nodes.AccuDailyForecastsWeather;
import net.oneplus.weather.api.nodes.AccuWind;
import net.oneplus.weather.api.nodes.Alarm;
import net.oneplus.weather.api.nodes.DailyForecastsWeather;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.api.nodes.Sun;
import net.oneplus.weather.api.nodes.Temperature;
import net.oneplus.weather.api.nodes.Wind;
import net.oneplus.weather.api.nodes.Wind.Direction;
import net.oneplus.weather.api.parser.ResponseParser.WeatherBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class AccuResponseParser implements ResponseParser {
    private static final String CONTENT_CHARSET = "utf-8";
    private static final String TAG = "AccuResponseParser";
    private final String mSearchKey;

    private static class CurrentWeatherBuilder implements WeatherBuilder {
        double mCentigradeValue;
        Direction mDirection;
        double mFahrenheitValue;
        String mLocalTimeZone;
        String mMainMoblieLink;
        Date mObservationDate;
        int mRelativeHumidity;
        String mSearchKey;
        double mSpeed;
        int mUVIndex;
        String mUVIndexText;
        int mWeatherId;
        String mWeatherText;

        private CurrentWeatherBuilder() {
            this.mSearchKey = null;
            this.mObservationDate = null;
            this.mWeatherId = 0;
            this.mLocalTimeZone = null;
            this.mWeatherText = null;
            this.mRelativeHumidity = Integer.MIN_VALUE;
            this.mSpeed = Double.NaN;
            this.mUVIndex = Integer.MIN_VALUE;
            this.mUVIndexText = null;
            this.mCentigradeValue = Double.NaN;
            this.mFahrenheitValue = Double.NaN;
            this.mMainMoblieLink = null;
        }

        public RootWeather build() throws BuilderException {
            if (StringUtils.isBlank(this.mSearchKey)) {
                throw new BuilderException("Valid area code empty.");
            } else if (this.mWeatherId == 0) {
                throw new BuilderException("Valid weather id unknown.");
            } else {
                Wind wind;
                Temperature temperature = null;
                if (!(NumberUtils.isNaN(this.mCentigradeValue) || NumberUtils.isNaN(this.mFahrenheitValue))) {
                    temperature = new Temperature(this.mSearchKey, AccuRequest.DATA_SOURCE_NAME, this.mCentigradeValue, this.mFahrenheitValue);
                }
                if (this.mDirection != null) {
                    wind = new AccuWind(this.mSearchKey, AccuRequest.DATA_SOURCE_NAME, this.mDirection, this.mSpeed);
                } else {
                    wind = null;
                }
                RootWeather result = new RootWeather(this.mSearchKey, AccuRequest.DATA_SOURCE_NAME);
                result.setCurrentWeather(new AccuCurrentWeather(this.mSearchKey, this.mWeatherId, this.mLocalTimeZone, this.mWeatherText, this.mObservationDate, temperature, this.mRelativeHumidity, wind, this.mUVIndex, this.mUVIndexText, this.mMainMoblieLink));
                return result;
            }
        }
    }

    private static class DailyForecastsWeatherBuilder implements WeatherBuilder {
        private List<ItemHolder> itemList;
        String mSearchKey;
        String mlink;

        static class ItemHolder {
            int dayWeatherId;
            String dayWeatherText;
            long epochDate;
            double maxCentigradeValue;
            double maxFahrenheitValue;
            double minCentigradeValue;
            double minFahrenheitValue;
            int nightWeatherId;
            String nightWeatherText;
            long rise;
            long set;
            String url;

            ItemHolder() {
                this.dayWeatherId = 0;
                this.nightWeatherId = 0;
            }
        }

        private DailyForecastsWeatherBuilder() {
            this.mSearchKey = null;
            this.mlink = null;
        }

        public void add(ItemHolder item) {
            if (this.itemList == null) {
                this.itemList = new ArrayList();
            }
            this.itemList.add(item);
        }

        public void addLink(String link) {
            this.mlink = link;
        }

        public RootWeather build() throws BuilderException {
            if (StringUtils.isBlank(this.mSearchKey)) {
                throw new BuilderException("Valid area code empty.");
            } else if (this.itemList == null) {
                throw new BuilderException("Valid forecasts is empty.");
            } else {
                RootWeather rootWeather = new RootWeather(this.mSearchKey, AccuRequest.DATA_SOURCE_NAME);
                List<DailyForecastsWeather> list = new ArrayList();
                for (ItemHolder holder : this.itemList) {
                    if (!NumberUtils.isNaN(holder.epochDate)) {
                        Temperature minTemperature = new Temperature(this.mSearchKey, AccuRequest.DATA_SOURCE_NAME, holder.minCentigradeValue, holder.minFahrenheitValue);
                        Temperature maxTemperature = new Temperature(this.mSearchKey, AccuRequest.DATA_SOURCE_NAME, holder.maxCentigradeValue, holder.maxFahrenheitValue);
                        Sun sun = null;
                        if (!(NumberUtils.isNaN(holder.rise) || NumberUtils.isNaN(holder.set))) {
                            sun = new Sun(this.mSearchKey, AccuRequest.DATA_SOURCE_NAME, holder.rise, holder.set);
                        }
                        List<DailyForecastsWeather> list2 = list;
                        list2.add(new AccuDailyForecastsWeather(this.mSearchKey, AccuRequest.DATA_SOURCE_NAME, holder.dayWeatherId, holder.dayWeatherText, holder.nightWeatherId, holder.nightWeatherText, DateUtils.epochDateToDate(holder.epochDate), minTemperature, maxTemperature, sun, holder.url));
                    }
                }
                if (list.size() == 0) {
                    throw new BuilderException("Empty forecasts weather!");
                }
                rootWeather.setDailyForecastsWeather(list);
                rootWeather.setFutureLink(this.mlink);
                return rootWeather;
            }
        }
    }

    private static class HourForecastsWeatherBuilder implements WeatherBuilder {
        private List<ItemHolder> itemList;
        String mSearchKey;

        static class ItemHolder {
            ItemHolder() {
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
            } else if (this.itemList != null) {
                return null;
            } else {
                throw new BuilderException("Valid forecasts is empty.");
            }
        }
    }

    private static class WeatherAlarmBuilder implements WeatherBuilder {
        private List<ItemHolder> itemList;
        String mSearchKey;

        static class ItemHolder {
            String areaName;
            String contentText;
            long endTime;
            String levelName;
            long startTime;
            String summary;

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
            RootWeather result = new RootWeather(this.mSearchKey, AccuRequest.DATA_SOURCE_NAME);
            List<Alarm> list = new ArrayList();
            if (this.itemList != null) {
                for (ItemHolder holder : this.itemList) {
                    AccuAlarm alarm = AccuAlarm.build(this.mSearchKey, holder.areaName, holder.summary, holder.levelName, holder.contentText, holder.startTime, holder.endTime);
                    if (alarm != null) {
                        list.add(alarm);
                    }
                }
            }
            result.setWeatherAlarms(list);
            return result;
        }
    }

    public AccuResponseParser(String key) {
        this.mSearchKey = key;
    }

    public RootWeather parseCurrent(byte[] data) throws ParseException {
        if (data == null) {
            throw new ParseException("The data to parser is null!");
        }
        CurrentWeatherBuilder builder = new CurrentWeatherBuilder();
        try {
            String jsonData = IOUtils.byteArrayToString(data, CONTENT_CHARSET);
            if (jsonData == null) {
                throw new ParseException("Data to parse is null!");
            }
            JSONObject jsonObject = new JSONArray(jsonData).getJSONObject(0);
            String localObservationDateTime = jsonObject.getString("LocalObservationDateTime");
            int length = localObservationDateTime.length();
            String localTimeZone = localObservationDateTime.substring(length - 6, length);
            long epochTime = (long) jsonObject.getInt("EpochTime");
            int weatherIcon = jsonObject.getInt("WeatherIcon");
            String weatherText = jsonObject.getString("WeatherText");
            JSONObject temperature = jsonObject.getJSONObject("Temperature");
            double metricValue = temperature.getJSONObject("Metric").getDouble("Value");
            double imperialValue = temperature.getJSONObject("Imperial").getDouble("Value");
            int relativeHumidity = jsonObject.getInt("RelativeHumidity");
            JSONObject wind = jsonObject.getJSONObject("Wind");
            JSONObject windDirection = wind.getJSONObject("Direction");
            JSONObject windSpeed = wind.getJSONObject("Speed");
            String windEnglish = windDirection.getString("English");
            double windSpeedValue = windSpeed.getJSONObject("Metric").getDouble("Value");
            int uVIndex = jsonObject.getInt("UVIndex");
            String uVIndexText = jsonObject.getString("UVIndexText");
            String mobileLink = jsonObject.getString("MobileLink") + StringUtils.getPartner(jsonObject.getString("MobileLink"));
            builder.mSearchKey = this.mSearchKey;
            builder.mObservationDate = DateUtils.epochDateToDate(epochTime);
            builder.mWeatherId = WeatherUtils.accuWeatherIconToWeatherId(weatherIcon);
            builder.mLocalTimeZone = localTimeZone;
            builder.mWeatherText = weatherText;
            builder.mRelativeHumidity = relativeHumidity;
            builder.mDirection = Wind.getDirectionFromAccu(windEnglish);
            builder.mSpeed = windSpeedValue;
            builder.mUVIndex = uVIndex;
            builder.mUVIndexText = uVIndexText;
            builder.mCentigradeValue = metricValue;
            builder.mFahrenheitValue = imperialValue;
            builder.mMainMoblieLink = mobileLink;
            return builder.build();
        } catch (Exception e) {
            LogUtils.e(TAG, "Can not parse data!", e);
            throw new ParseException(e.getMessage());
        }
    }

    public RootWeather parseHourForecasts(byte[] data) throws ParseException {
        throw new ParseException("Now we don't need hour forecasts data when use accu data source!");
    }

    public RootWeather parseDailyForecasts(byte[] data) throws ParseException {
        if (data == null) {
            throw new ParseException("The data to parser is null!");
        }
        DailyForecastsWeatherBuilder builder = new DailyForecastsWeatherBuilder();
        builder.mSearchKey = this.mSearchKey;
        try {
            String jsonData = IOUtils.byteArrayToString(data, CONTENT_CHARSET);
            if (jsonData == null) {
                throw new ParseException("Data to parse is null!");
            }
            JSONObject jSONObject = new JSONObject(jsonData);
            JSONArray dailyArray = jSONObject.getJSONArray("DailyForecasts");
            JSONObject headObject = jSONObject.getJSONObject("Headline");
            builder.addLink(headObject.getString("MobileLink") + StringUtils.getPartner(headObject.getString("MobileLink")));
            for (int i = 0; i < dailyArray.length(); i++) {
                ItemHolder holder = new ItemHolder();
                JSONObject item = dailyArray.getJSONObject(i);
                long epochDate = item.getLong("EpochDate");
                JSONObject sun = item.getJSONObject("Sun");
                String mobileLink = item.getString("MobileLink") + StringUtils.getPartner(item.getString("MobileLink"));
                long epochRise = sun.optLong("EpochRise", Long.MIN_VALUE);
                long epochSet = sun.optLong("EpochSet", Long.MIN_VALUE);
                JSONObject temperature = item.getJSONObject("Temperature");
                double minValue = temperature.getJSONObject("Minimum").getDouble("Value");
                double maxValue = temperature.getJSONObject("Maximum").getDouble("Value");
                JSONObject day = item.getJSONObject("Day");
                int dayId = day.getInt("Icon");
                String dayText = day.getString("IconPhrase");
                JSONObject night = item.getJSONObject("Night");
                int nightId = night.getInt("Icon");
                String nightText = night.getString("IconPhrase");
                holder.epochDate = epochDate;
                holder.maxCentigradeValue = maxValue;
                holder.maxFahrenheitValue = WeatherUtils.centigradeToFahrenheit(maxValue);
                holder.minCentigradeValue = minValue;
                holder.minFahrenheitValue = WeatherUtils.centigradeToFahrenheit(minValue);
                holder.rise = epochRise;
                holder.set = epochSet;
                holder.dayWeatherId = WeatherUtils.accuWeatherIconToWeatherId(dayId);
                holder.nightWeatherId = WeatherUtils.accuWeatherIconToWeatherId(nightId);
                holder.dayWeatherText = dayText;
                holder.nightWeatherText = nightText;
                holder.url = mobileLink;
                builder.add(holder);
            }
            return builder.build();
        } catch (Exception e) {
            LogUtils.e(TAG, "Can not parse data!", e);
            throw new ParseException(e.getMessage());
        }
    }

    public RootWeather parseAqi(byte[] data) throws ParseException {
        throw new ParseException("Now we don't need aqi data when use accu data source!");
    }

    public RootWeather parseLifeIndex(byte[] data) throws ParseException {
        throw new ParseException("Now we don't need life index data when use accu data source!");
    }

    public RootWeather parseAlarm(byte[] data) throws ParseException {
        if (data == null) {
            throw new ParseException("The data to parser is null!");
        }
        WeatherAlarmBuilder builder = new WeatherAlarmBuilder();
        builder.mSearchKey = this.mSearchKey;
        try {
            String jsonData = IOUtils.byteArrayToString(data, CONTENT_CHARSET);
            if (jsonData == null) {
                throw new ParseException("Data to parse is null!");
            }
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                JSONObject description = item.getJSONObject("Description");
                JSONArray area = item.getJSONArray("Area");
                for (int j = 0; j < area.length(); j++) {
                    ItemHolder holder = new ItemHolder();
                    JSONObject areaItem = area.getJSONObject(j);
                    holder.summary = description.getString("Localized");
                    holder.levelName = item.getString("Level");
                    holder.areaName = areaItem.getString("Name");
                    holder.contentText = areaItem.getString("Summary");
                    holder.startTime = areaItem.getLong("EpochStartTime");
                    holder.endTime = areaItem.getLong("EpochEndTime");
                    builder.add(holder);
                }
            }
            return builder.build();
        } catch (Exception e) {
            LogUtils.e(TAG, "Can not parse data!", e);
            throw new ParseException(e.getMessage());
        }
    }
}
