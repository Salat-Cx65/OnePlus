package net.oneplus.weather.api.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import net.oneplus.weather.api.WeatherRequest;
import net.oneplus.weather.api.helper.DateUtils;
import net.oneplus.weather.api.helper.LogUtils;
import net.oneplus.weather.api.helper.NumberUtils;
import net.oneplus.weather.api.helper.StringUtils;
import net.oneplus.weather.api.helper.WeatherUtils;
import net.oneplus.weather.api.impl.OppoChinaRequest;
import net.oneplus.weather.api.nodes.Alarm;
import net.oneplus.weather.api.nodes.AqiWeather;
import net.oneplus.weather.api.nodes.CurrentWeather;
import net.oneplus.weather.api.nodes.DailyForecastsWeather;
import net.oneplus.weather.api.nodes.HourForecastsWeather;
import net.oneplus.weather.api.nodes.OppoChinaAlarm;
import net.oneplus.weather.api.nodes.OppoChinaAqiWeather;
import net.oneplus.weather.api.nodes.OppoChinaCurrentWeather;
import net.oneplus.weather.api.nodes.OppoChinaDailyForecastsWeather;
import net.oneplus.weather.api.nodes.OppoChinaHourForecastsWeather;
import net.oneplus.weather.api.nodes.OppoWind;
import net.oneplus.weather.api.nodes.RootWeather;
import net.oneplus.weather.api.nodes.Sun;
import net.oneplus.weather.api.nodes.Temperature;
import net.oneplus.weather.api.nodes.Wind.Direction;
import net.oneplus.weather.api.parser.ResponseParser.WeatherBuilder;

public class OppoChinaResponseParser extends OppoResponseParser {
    private static final String TAG = "OppoChinaResponseParser";

    private static class RootWeatherBuilder implements WeatherBuilder {
        private int aqiValue;
        private String areaCode;
        private String areaName;
        private List<DailyForecastsHolder> dailyItemList;
        String date;
        private List<HourForecastsHolder> hourItemList;
        Date sunRise;
        Date sunSet;

        static class DailyForecastsHolder {
            String clothingValue;
            int currentRelativeHumidity;
            double currentTemperature;
            String currentUVIndexText;
            int currentWeatherId;
            Direction currentWindDirection;
            String currentWindPower;
            Date date;
            int dayWeatherId;
            double maxTemperature;
            double minTemperature;
            String mobileLink;
            int nightWeatherId;
            String sportsValue;
            String warnWeatherDetail;
            String warnWeatherTitle;
            String washcarValue;

            DailyForecastsHolder() {
                this.currentWeatherId = Integer.MIN_VALUE;
                this.currentTemperature = Double.NaN;
                this.currentRelativeHumidity = Integer.MIN_VALUE;
                this.currentUVIndexText = null;
                this.currentWindDirection = null;
                this.currentWindPower = null;
                this.warnWeatherTitle = null;
                this.warnWeatherDetail = null;
                this.dayWeatherId = Integer.MIN_VALUE;
                this.nightWeatherId = Integer.MIN_VALUE;
                this.date = null;
                this.minTemperature = Double.NaN;
                this.maxTemperature = Double.NaN;
                this.sportsValue = null;
                this.washcarValue = null;
                this.clothingValue = null;
                this.mobileLink = null;
            }
        }

        static class HourForecastsHolder {
            double temperature;
            Date time;
            int weatherId;

            HourForecastsHolder() {
                this.weatherId = Integer.MIN_VALUE;
                this.temperature = Double.NaN;
                this.time = null;
            }
        }

        private RootWeatherBuilder() {
            this.areaName = null;
            this.aqiValue = Integer.MIN_VALUE;
            this.dailyItemList = null;
            this.hourItemList = null;
            this.sunRise = null;
            this.sunSet = null;
        }

        public void add(DailyForecastsHolder item) {
            if (this.dailyItemList == null) {
                this.dailyItemList = new ArrayList();
            }
            this.dailyItemList.add(item);
        }

        public void add(HourForecastsHolder item) {
            if (this.hourItemList == null) {
                this.hourItemList = new ArrayList();
            }
            this.hourItemList.add(item);
        }

        public RootWeather build() throws BuilderException {
            if (StringUtils.isBlank(this.areaCode)) {
                throw new BuilderException("Valid area code empty.");
            }
            RootWeather rootWeather = new RootWeather(this.areaCode, this.areaName, OppoChinaRequest.DATA_SOURCE_NAME);
            if (this.date != null) {
                LogUtils.d(TAG, "Date: " + this.date, new Object[0]);
            }
            AqiWeather oppoChinaAqiWeather = new OppoChinaAqiWeather(this.areaCode, this.areaName, OppoChinaRequest.DATA_SOURCE_NAME, this.aqiValue);
            CurrentWeather currentWeather = getCurrentWeather(this.date, this.dailyItemList);
            List<DailyForecastsWeather> dailyForecastsWeather = null;
            if (this.dailyItemList != null && this.dailyItemList.size() > 0) {
                dailyForecastsWeather = new ArrayList();
                for (DailyForecastsHolder holder : this.dailyItemList) {
                    if (holder.date != null) {
                        Temperature minTemperature = new Temperature(this.areaCode, this.areaName, OppoChinaRequest.DATA_SOURCE_NAME, holder.minTemperature, WeatherUtils.centigradeToFahrenheit(holder.minTemperature));
                        Temperature maxTemperature = new Temperature(this.areaCode, this.areaName, OppoChinaRequest.DATA_SOURCE_NAME, holder.maxTemperature, WeatherUtils.centigradeToFahrenheit(holder.maxTemperature));
                        Sun sun = null;
                        if (DateUtils.isSameDay(System.currentTimeMillis(), holder.date.getTime(), TimeZone.getTimeZone("GMT+08:00"))) {
                            sun = new Sun(this.areaCode, this.areaName, OppoChinaRequest.DATA_SOURCE_NAME, this.sunRise, this.sunSet);
                        }
                        List<DailyForecastsWeather> list = dailyForecastsWeather;
                        list.add(new OppoChinaDailyForecastsWeather(this.areaCode, this.areaName, OppoChinaRequest.DATA_SOURCE_NAME, holder.dayWeatherId, holder.nightWeatherId, holder.date, minTemperature, maxTemperature, sun, holder.sportsValue, holder.washcarValue, holder.clothingValue, holder.mobileLink));
                    }
                }
            }
            List<HourForecastsWeather> hourForecastsWeather = null;
            if (this.hourItemList != null && this.hourItemList.size() > 0) {
                hourForecastsWeather = new ArrayList();
                for (HourForecastsHolder holder2 : this.hourItemList) {
                    if (holder2.time != null) {
                        if (holder2.weatherId != Integer.MIN_VALUE || holder2.temperature != Double.NaN) {
                            List<HourForecastsWeather> list2 = hourForecastsWeather;
                            list2.add(new OppoChinaHourForecastsWeather(this.areaCode, holder2.weatherId, holder2.time, new Temperature(this.areaCode, this.areaName, OppoChinaRequest.DATA_SOURCE_NAME, holder2.temperature, WeatherUtils.centigradeToFahrenheit(holder2.temperature))));
                        }
                    }
                }
            }
            List<Alarm> oppoAlarmList = getAlarmWeather(this.date, this.dailyItemList);
            rootWeather.setCurrentWeather(currentWeather);
            rootWeather.setAqiWeather(oppoChinaAqiWeather);
            if (dailyForecastsWeather == null || dailyForecastsWeather.size() <= 0) {
                rootWeather.setDailyForecastsWeather(null);
            } else {
                rootWeather.setDailyForecastsWeather(dailyForecastsWeather);
            }
            if (hourForecastsWeather == null || hourForecastsWeather.size() <= 0) {
                rootWeather.setHourForecastsWeather(null);
            } else {
                rootWeather.setHourForecastsWeather(hourForecastsWeather);
            }
            if (oppoAlarmList != null) {
                rootWeather.setWeatherAlarms(oppoAlarmList);
            }
            return rootWeather;
        }

        private CurrentWeather getCurrentWeather(String date, List<DailyForecastsHolder> list) {
            if (list == null || list.size() == 0) {
                return null;
            }
            for (DailyForecastsHolder holder : list) {
                if (DateUtils.isSameDay(System.currentTimeMillis(), holder.date.getTime(), TimeZone.getTimeZone("GMT+08:00"))) {
                    Date observationDate = DateUtils.parseOppoCurrentWeatherDate(date);
                    if (NumberUtils.isNaN(holder.currentTemperature)) {
                        holder.currentTemperature = (holder.maxTemperature + holder.minTemperature) / 2.0d;
                    }
                    Temperature temperature = new Temperature(this.areaCode, this.areaName, OppoChinaRequest.DATA_SOURCE_NAME, holder.currentTemperature, WeatherUtils.centigradeToFahrenheit(holder.currentTemperature));
                    return new OppoChinaCurrentWeather(this.areaCode, this.areaName, OppoChinaRequest.DATA_SOURCE_NAME, holder.currentWeatherId, observationDate, temperature, holder.currentRelativeHumidity, new OppoWind(this.areaCode, this.areaName, OppoChinaRequest.DATA_SOURCE_NAME, holder.currentWindDirection, holder.currentWindPower), Integer.MIN_VALUE, holder.currentUVIndexText);
                }
            }
            return null;
        }

        private List<Alarm> getAlarmWeather(String date, List<DailyForecastsHolder> list) {
            List<Alarm> list2 = null;
            if (!(list == null || list.size() == 0)) {
                for (DailyForecastsHolder holder : list) {
                    if (DateUtils.isSameDay(System.currentTimeMillis(), holder.date.getTime(), TimeZone.getTimeZone("GMT+08:00"))) {
                        list2 = new ArrayList();
                        OppoChinaAlarm alarm = OppoChinaAlarm.build(this.areaCode, this.areaName, DateUtils.parseOppoCurrentWeatherDate(date), holder.warnWeatherTitle, holder.warnWeatherDetail);
                        if (alarm != null) {
                            list2.add(alarm);
                        }
                    }
                }
            }
            return list2;
        }
    }

    public OppoChinaResponseParser(WeatherRequest request) {
        super(request);
    }

    public RootWeather parseCurrent(byte[] data) throws ParseException {
        return innerCommonParse(data);
    }

    public RootWeather parseHourForecasts(byte[] data) throws ParseException {
        return innerCommonParse(data);
    }

    public RootWeather parseDailyForecasts(byte[] data) throws ParseException {
        return innerCommonParse(data);
    }

    public RootWeather parseAqi(byte[] data) throws ParseException {
        return innerCommonParse(data);
    }

    public RootWeather parseLifeIndex(byte[] data) throws ParseException {
        return innerCommonParse(data);
    }

    public RootWeather parseAlarm(byte[] data) throws ParseException {
        return innerCommonParse(data);
    }

    private net.oneplus.weather.api.nodes.RootWeather innerCommonParse(byte[] r13_data) throws net.oneplus.weather.api.parser.ParseException {
        throw new UnsupportedOperationException("Method not decompiled: net.oneplus.weather.api.parser.OppoChinaResponseParser.innerCommonParse(byte[]):net.oneplus.weather.api.nodes.RootWeather");
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Try/catch wrap count limit reached in net.oneplus.weather.api.parser.OppoChinaResponseParser.innerCommonParse(byte[]):net.oneplus.weather.api.nodes.RootWeather
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:54)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:40)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:16)
	at jadx.core.ProcessClass.process(ProcessClass.java:22)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:209)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:133)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
	at java.lang.Thread.run(Unknown Source)
*/
        /*
        this = this;
        r11 = 3;
        r10 = 2;
        if (r13 != 0) goto L_0x000c;
    L_0x0004:
        r7 = new net.oneplus.weather.api.parser.ParseException;
        r8 = "The data to parser is null!";
        r7.<init>(r8);
        throw r7;
    L_0x000c:
        r0 = new net.oneplus.weather.api.parser.OppoChinaResponseParser$RootWeatherBuilder;
        r7 = 0;
        r0.<init>();
        r5 = 0;
        r5 = net.oneplus.weather.api.helper.IOUtils.getInputStreamFromByteArray(r13);	 Catch:{ Exception -> 0x039d }
        r6 = new java.util.zip.GZIPInputStream;	 Catch:{ Exception -> 0x039d }
        r6.<init>(r5);	 Catch:{ Exception -> 0x039d }
        r4 = r12.getXmlPullParser(r6);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r2 = r4.getEventType();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
    L_0x0024:
        r7 = 1;
        if (r2 == r7) goto L_0x0388;
    L_0x0027:
        if (r2 != r10) goto L_0x0382;
    L_0x0029:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "WeatherForecast";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x0382;
    L_0x0035:
        r2 = r4.next();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
    L_0x0039:
        if (r2 != r11) goto L_0x0047;
    L_0x003b:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "WeatherForecast";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 != 0) goto L_0x0382;
    L_0x0047:
        if (r2 != r10) goto L_0x0061;
    L_0x0049:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "city";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x0061;
    L_0x0055:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r0.areaName = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
    L_0x005c:
        r2 = r4.next();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0039;
    L_0x0061:
        if (r2 != r10) goto L_0x008a;
    L_0x0063:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "city_id";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x008a;
    L_0x006f:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r0.areaCode = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x005c;
    L_0x0077:
        r1 = move-exception;
        r5 = r6;
    L_0x0079:
        r7 = new net.oneplus.weather.api.parser.ParseException;	 Catch:{ all -> 0x0083 }
        r8 = r1.getMessage();	 Catch:{ all -> 0x0083 }
        r7.<init>(r8);	 Catch:{ all -> 0x0083 }
        throw r7;	 Catch:{ all -> 0x0083 }
    L_0x0083:
        r7 = move-exception;
    L_0x0084:
        if (r5 == 0) goto L_0x0089;
    L_0x0086:
        r5.close();	 Catch:{ IOException -> 0x0397 }
    L_0x0089:
        throw r7;
    L_0x008a:
        if (r2 != r10) goto L_0x00a2;
    L_0x008c:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "date_time";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x00a2;
    L_0x0098:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r0.date = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x005c;
    L_0x009f:
        r7 = move-exception;
        r5 = r6;
        goto L_0x0084;
    L_0x00a2:
        if (r2 != r10) goto L_0x028e;
    L_0x00a4:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "items";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x028e;
    L_0x00b0:
        r2 = r4.next();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3 = 0;
    L_0x00b5:
        if (r2 != r11) goto L_0x00c3;
    L_0x00b7:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "items";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 != 0) goto L_0x005c;
    L_0x00c3:
        if (r2 != r10) goto L_0x0288;
    L_0x00c5:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "item";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x0288;
    L_0x00d1:
        r2 = r4.next();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3 = new net.oneplus.weather.api.parser.OppoChinaResponseParser$RootWeatherBuilder$DailyForecastsHolder;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.<init>();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
    L_0x00da:
        if (r2 != r11) goto L_0x00e8;
    L_0x00dc:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "item";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 != 0) goto L_0x0285;
    L_0x00e8:
        if (r2 != r10) goto L_0x0105;
    L_0x00ea:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "date";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x0105;
    L_0x00f6:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r7 = net.oneplus.weather.api.helper.DateUtils.parseOppoforcastDate(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.date = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
    L_0x0100:
        r2 = r4.next();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x00da;
    L_0x0105:
        if (r2 != r10) goto L_0x011a;
    L_0x0107:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "warn_weather";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x011a;
    L_0x0113:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.warnWeatherTitle = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x011a:
        if (r2 != r10) goto L_0x012f;
    L_0x011c:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "detail_warn_weather";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x012f;
    L_0x0128:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.warnWeatherDetail = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x012f:
        if (r2 != r10) goto L_0x0148;
    L_0x0131:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "current_weather";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x0148;
    L_0x013d:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r7 = net.oneplus.weather.api.helper.WeatherUtils.oppoChinaWeatherTextToWeatherId(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.currentWeatherId = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x0148:
        if (r2 != r10) goto L_0x0161;
    L_0x014a:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "current_temp";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x0161;
    L_0x0156:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = net.oneplus.weather.api.helper.NumberUtils.valueToDouble(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.currentTemperature = r8;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x0161:
        if (r2 != r10) goto L_0x017a;
    L_0x0163:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "current_wind_direction";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x017a;
    L_0x016f:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r7 = net.oneplus.weather.api.nodes.Wind.getDirectionFromOppo(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.currentWindDirection = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x017a:
        if (r2 != r10) goto L_0x0190;
    L_0x017c:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "current_wind_power";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x0190;
    L_0x0188:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.currentWindPower = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x0190:
        if (r2 != r10) goto L_0x01aa;
    L_0x0192:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "current_humidity";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x01aa;
    L_0x019e:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r7 = net.oneplus.weather.api.helper.NumberUtils.valueToInt(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.currentRelativeHumidity = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x01aa:
        if (r2 != r10) goto L_0x01c0;
    L_0x01ac:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "current_uv_desc";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x01c0;
    L_0x01b8:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.currentUVIndexText = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x01c0:
        if (r2 != r10) goto L_0x01da;
    L_0x01c2:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "day_weather";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x01da;
    L_0x01ce:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r7 = net.oneplus.weather.api.helper.WeatherUtils.oppoChinaWeatherTextToWeatherId(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.dayWeatherId = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x01da:
        if (r2 != r10) goto L_0x01f4;
    L_0x01dc:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "day_temp";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x01f4;
    L_0x01e8:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = net.oneplus.weather.api.helper.NumberUtils.valueToDouble(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.maxTemperature = r8;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x01f4:
        if (r2 != r10) goto L_0x020e;
    L_0x01f6:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "night_weather";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x020e;
    L_0x0202:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r7 = net.oneplus.weather.api.helper.WeatherUtils.oppoChinaWeatherTextToWeatherId(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.nightWeatherId = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x020e:
        if (r2 != r10) goto L_0x0228;
    L_0x0210:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "night_temp";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x0228;
    L_0x021c:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = net.oneplus.weather.api.helper.NumberUtils.valueToDouble(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.minTemperature = r8;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x0228:
        if (r2 != r10) goto L_0x023e;
    L_0x022a:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "clothing_value";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x023e;
    L_0x0236:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.clothingValue = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x023e:
        if (r2 != r10) goto L_0x0254;
    L_0x0240:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "sports_value";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x0254;
    L_0x024c:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.sportsValue = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x0254:
        if (r2 != r10) goto L_0x026a;
    L_0x0256:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "washcar_value";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x026a;
    L_0x0262:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.washcarValue = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x026a:
        if (r2 != r10) goto L_0x0100;
    L_0x026c:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "avg_aqi";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x0100;
    L_0x0278:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r7 = net.oneplus.weather.api.helper.NumberUtils.valueToInt(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r0.aqiValue = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0100;
    L_0x0285:
        r0.add(r3);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
    L_0x0288:
        r2 = r4.next();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x00b5;
    L_0x028e:
        if (r2 != r10) goto L_0x032c;
    L_0x0290:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "weather_hours";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x032c;
    L_0x029c:
        r2 = r4.next();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3 = 0;
    L_0x02a1:
        if (r2 != r11) goto L_0x02af;
    L_0x02a3:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "weather_hours";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 != 0) goto L_0x005c;
    L_0x02af:
        if (r2 != r10) goto L_0x0326;
    L_0x02b1:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "item_hour";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x0326;
    L_0x02bd:
        r2 = r4.next();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3 = new net.oneplus.weather.api.parser.OppoChinaResponseParser$RootWeatherBuilder$HourForecastsHolder;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.<init>();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
    L_0x02c6:
        if (r2 != r11) goto L_0x02d4;
    L_0x02c8:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "item_hour";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 != 0) goto L_0x0323;
    L_0x02d4:
        if (r2 != r10) goto L_0x02f1;
    L_0x02d6:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "forcast_time";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x02f1;
    L_0x02e2:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r7 = net.oneplus.weather.api.helper.DateUtils.parseOppoCurrentWeatherDate(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.time = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
    L_0x02ec:
        r2 = r4.next();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x02c6;
    L_0x02f1:
        if (r2 != r10) goto L_0x030a;
    L_0x02f3:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "weather_phenomena";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x030a;
    L_0x02ff:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r7 = net.oneplus.weather.api.helper.WeatherUtils.oppoChinaWeatherTextToWeatherId(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.weatherId = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x02ec;
    L_0x030a:
        if (r2 != r10) goto L_0x02ec;
    L_0x030c:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "temprature";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x02ec;
    L_0x0318:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = net.oneplus.weather.api.helper.NumberUtils.valueToDouble(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r3.temperature = r8;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x02ec;
    L_0x0323:
        r0.add(r3);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
    L_0x0326:
        r2 = r4.next();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x02a1;
    L_0x032c:
        if (r2 != r10) goto L_0x005c;
    L_0x032e:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "extraData";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x005c;
    L_0x033a:
        r2 = r4.next();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
    L_0x033e:
        if (r2 != r11) goto L_0x034c;
    L_0x0340:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "extraData";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 != 0) goto L_0x005c;
    L_0x034c:
        if (r2 != r10) goto L_0x0369;
    L_0x034e:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "sunrise_time";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x0369;
    L_0x035a:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r7 = net.oneplus.weather.api.helper.DateUtils.parseOppoSunDate(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r0.sunRise = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
    L_0x0364:
        r2 = r4.next();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x033e;
    L_0x0369:
        if (r2 != r10) goto L_0x0364;
    L_0x036b:
        r7 = r4.getName();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r8 = "sunset_time";
        r7 = r7.equalsIgnoreCase(r8);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        if (r7 == 0) goto L_0x0364;
    L_0x0377:
        r7 = r4.nextText();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r7 = net.oneplus.weather.api.helper.DateUtils.parseOppoSunDate(r7);	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        r0.sunSet = r7;	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0364;
    L_0x0382:
        r2 = r4.next();	 Catch:{ Exception -> 0x0077, all -> 0x009f }
        goto L_0x0024;
    L_0x0388:
        if (r6 == 0) goto L_0x038d;
    L_0x038a:
        r6.close();	 Catch:{ IOException -> 0x0392 }
    L_0x038d:
        r7 = r0.build();
        return r7;
    L_0x0392:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x038d;
    L_0x0397:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0089;
    L_0x039d:
        r1 = move-exception;
        goto L_0x0079;
        */
    }
}
