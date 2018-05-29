package com.loc;

import android.content.Context;
import android.util.SparseArray;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.services.core.AMapException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.oneplus.weather.api.WeatherType;
import net.oneplus.weather.db.CityWeatherDBHelper.WeatherEntry;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.json.JSONArray;
import org.json.JSONObject;

// compiled from: ReportUtil.java
public final class db {
    private static List<br> h;
    private static JSONArray i;
    public SparseArray<Long> a;
    public int b;
    public long c;
    String[] d;
    public int e;
    public long f;
    boolean g;
    private LinkedList<bx> j;

    // compiled from: ReportUtil.java
    static /* synthetic */ class AnonymousClass_1 {
        static final /* synthetic */ int[] a;

        static {
            a = new int[AMapLocationMode.values().length];
            try {
                a[AMapLocationMode.Battery_Saving.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[AMapLocationMode.Device_Sensors.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[AMapLocationMode.Hight_Accuracy.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static {
        h = new ArrayList();
        i = null;
    }

    public db() {
        this.a = new SparseArray();
        this.b = -1;
        this.c = 0;
        this.d = new String[]{"ol", "cl", "gl", "ha", "bs", "ds"};
        this.e = -1;
        this.f = -1;
        this.j = new LinkedList();
        this.g = false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(android.content.Context r3) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.db.a(android.content.Context):void");
        /*
        if (r3 == 0) goto L_0x0008;
    L_0x0002:
        r0 = com.loc.cv.o();	 Catch:{ Throwable -> 0x002b }
        if (r0 != 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r0 = h;	 Catch:{ Throwable -> 0x002b }
        if (r0 == 0) goto L_0x0027;
    L_0x000d:
        r0 = h;	 Catch:{ Throwable -> 0x002b }
        r0 = r0.size();	 Catch:{ Throwable -> 0x002b }
        if (r0 <= 0) goto L_0x0027;
    L_0x0015:
        r0 = new java.util.ArrayList;	 Catch:{ Throwable -> 0x002b }
        r0.<init>();	 Catch:{ Throwable -> 0x002b }
        r1 = h;	 Catch:{ Throwable -> 0x002b }
        r0.addAll(r1);	 Catch:{ Throwable -> 0x002b }
        com.loc.bs.a(r0, r3);	 Catch:{ Throwable -> 0x002b }
        r0 = h;	 Catch:{ Throwable -> 0x002b }
        r0.clear();	 Catch:{ Throwable -> 0x002b }
    L_0x0027:
        f(r3);	 Catch:{ Throwable -> 0x002b }
        goto L_0x0008;
    L_0x002b:
        r0 = move-exception;
        r1 = "ReportUtil";
        r2 = "destroy";
        com.loc.cw.a(r0, r1, r2);
        goto L_0x0008;
        */
    }

    public static void a(Context context, int i, int i2, long j, long j2) {
        if (i != -1 && i2 != -1) {
            try {
                String str = "O012";
                if (context != null) {
                    try {
                        if (cv.o()) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("param_int_first", i);
                            jSONObject.put("param_int_second", i2);
                            jSONObject.put("param_long_first", j);
                            jSONObject.put("param_long_second", j2);
                            a(context, str, jSONObject);
                        }
                    } catch (Throwable th) {
                        cw.a(th, "ReportUtil", "applyStatisticsEx");
                    }
                }
            } catch (Throwable th2) {
                cw.a(th2, "ReportUtil", "reportServiceAliveTime");
            }
        }
    }

    public static void a(Context context, int i, AMapLocation aMapLocation) {
        Object obj = null;
        Object obj2 = 1;
        try {
            String str = "net";
            int i2 = aMapLocation.getErrorCode() == 0 ? 1 : 0;
            int i3;
            switch (i) {
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                case DetectedActivity.WALKING:
                case DetectedActivity.RUNNING:
                    str = "cache";
                    i3 = 1;
                    break;
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                case ConnectionResult.RESOLUTION_REQUIRED:
                    str = "net";
                    i3 = 1;
                    break;
            }
            if (aMapLocation.getErrorCode() != 0) {
                switch (aMapLocation.getErrorCode()) {
                    case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    case ConnectionResult.RESOLUTION_REQUIRED:
                    case ConnectionResult.LICENSE_CHECK_FAILED:
                        str = "net";
                        if (obj2 != null) {
                            a(context, "O005", i2, str);
                        }
                }
            }
            obj2 = obj;
            if (obj2 != null) {
                a(context, "O005", i2, str);
            }
        } catch (Throwable th) {
            cw.a(th, "ReportUtil", "reportBatting");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(android.content.Context r3, long r4) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.db.a(android.content.Context, long):void");
        /*
        if (r3 == 0) goto L_0x0008;
    L_0x0002:
        r0 = com.loc.cv.o();	 Catch:{ Throwable -> 0x0018 }
        if (r0 != 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r0 = java.lang.Long.valueOf(r4);	 Catch:{ Throwable -> 0x0018 }
        r0 = r0.intValue();	 Catch:{ Throwable -> 0x0018 }
        r1 = "O004";
        r2 = 0;
        a(r3, r1, r0, r2);	 Catch:{ Throwable -> 0x0018 }
        goto L_0x0008;
    L_0x0018:
        r0 = move-exception;
        r1 = "ReportUtil";
        r2 = "reportGPSLocUseTime";
        com.loc.cw.a(r0, r1, r2);
        goto L_0x0008;
        */
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(android.content.Context r10, com.loc.da r11) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.db.a(android.content.Context, com.loc.da):void");
        /*
        r2 = 1;
        r1 = 0;
        if (r10 == 0) goto L_0x000a;
    L_0x0004:
        r0 = com.loc.cv.o();	 Catch:{ Throwable -> 0x0056 }
        if (r0 != 0) goto L_0x000b;
    L_0x000a:
        return;
    L_0x000b:
        r4 = r11.c();	 Catch:{ Throwable -> 0x0056 }
        r6 = r11.a();	 Catch:{ Throwable -> 0x0056 }
        r8 = r11.b();	 Catch:{ Throwable -> 0x0056 }
        r6 = r8 - r6;
        r0 = java.lang.Long.valueOf(r6);	 Catch:{ Throwable -> 0x0056 }
        r5 = r0.intValue();	 Catch:{ Throwable -> 0x0056 }
        r0 = "net";
        if (r4 == 0) goto L_0x0068;
    L_0x0025:
        r6 = r4.j();	 Catch:{ Throwable -> 0x0056 }
        r3 = java.lang.Long.valueOf(r6);	 Catch:{ Throwable -> 0x0056 }
        r3 = r3.intValue();	 Catch:{ Throwable -> 0x0056 }
        r4 = r4.getLocationType();	 Catch:{ Throwable -> 0x0056 }
        switch(r4) {
            case 1: goto L_0x005f;
            case 2: goto L_0x0061;
            case 3: goto L_0x0038;
            case 4: goto L_0x0061;
            case 5: goto L_0x0065;
            case 6: goto L_0x0065;
            default: goto L_0x0038;
        };	 Catch:{ Throwable -> 0x0056 }
    L_0x0038:
        if (r2 == 0) goto L_0x000a;
    L_0x003a:
        if (r1 != 0) goto L_0x0050;
    L_0x003c:
        r1 = new org.json.JSONObject;	 Catch:{ Throwable -> 0x0056 }
        r1.<init>();	 Catch:{ Throwable -> 0x0056 }
        r2 = "param_int_first";
        r1.put(r2, r3);	 Catch:{ Throwable -> 0x0056 }
        r2 = "param_int_second";
        r1.put(r2, r5);	 Catch:{ Throwable -> 0x0056 }
        r2 = "O003";
        a(r10, r2, r1);	 Catch:{ Throwable -> 0x0056 }
    L_0x0050:
        r1 = "O002";
        a(r10, r1, r5, r0);	 Catch:{ Throwable -> 0x0056 }
        goto L_0x000a;
    L_0x0056:
        r0 = move-exception;
        r1 = "ReportUtil";
        r2 = "reportLBSLocUseTime";
        com.loc.cw.a(r0, r1, r2);
        goto L_0x000a;
    L_0x005f:
        r2 = r1;
        goto L_0x0038;
    L_0x0061:
        r0 = "cache";
        r1 = r2;
        goto L_0x0038;
    L_0x0065:
        r0 = "net";
        goto L_0x0038;
    L_0x0068:
        r3 = r1;
        goto L_0x0038;
        */
    }

    public static void a(Context context, String str) {
        try {
            a(context, "O010", 0, str);
        } catch (Throwable th) {
            cw.a(th, "ReportUtil", "reportDex_dexFunction");
        }
    }

    public static void a(Context context, String str, int i) {
        try {
            a(context, "O009", i, str);
        } catch (Throwable th) {
            cw.a(th, "ReportUtil", "reportDex_dexLoadClass");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(android.content.Context r3, java.lang.String r4, int r5, java.lang.String r6) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.db.a(android.content.Context, java.lang.String, int, java.lang.String):void");
        /*
        if (r3 == 0) goto L_0x0008;
    L_0x0002:
        r0 = com.loc.cv.o();	 Catch:{ Throwable -> 0x0027 }
        if (r0 != 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r0 = new org.json.JSONObject;	 Catch:{ Throwable -> 0x0027 }
        r0.<init>();	 Catch:{ Throwable -> 0x0027 }
        r1 = android.text.TextUtils.isEmpty(r6);	 Catch:{ Throwable -> 0x0027 }
        if (r1 != 0) goto L_0x0019;
    L_0x0014:
        r1 = "param_string_first";
        r0.put(r1, r6);	 Catch:{ Throwable -> 0x0027 }
    L_0x0019:
        r1 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        if (r5 == r1) goto L_0x0023;
    L_0x001e:
        r1 = "param_int_first";
        r0.put(r1, r5);	 Catch:{ Throwable -> 0x0027 }
    L_0x0023:
        a(r3, r4, r0);	 Catch:{ Throwable -> 0x0027 }
        goto L_0x0008;
    L_0x0027:
        r0 = move-exception;
        r1 = "ReportUtil";
        r2 = "applyStatisticsEx";
        com.loc.cw.a(r0, r1, r2);
        goto L_0x0008;
        */
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(android.content.Context r3, java.lang.String r4, org.json.JSONObject r5) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.db.a(android.content.Context, java.lang.String, org.json.JSONObject):void");
        /*
        if (r3 == 0) goto L_0x0008;
    L_0x0002:
        r0 = com.loc.cv.o();	 Catch:{ Throwable -> 0x003b }
        if (r0 != 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r0 = new com.loc.br;	 Catch:{ Throwable -> 0x003b }
        r1 = "loc";
        r2 = "3.4.0";
        r0.<init>(r3, r1, r2, r4);	 Catch:{ Throwable -> 0x003b }
        r1 = r5.toString();	 Catch:{ Throwable -> 0x003b }
        r0.a(r1);	 Catch:{ Throwable -> 0x003b }
        r1 = h;	 Catch:{ Throwable -> 0x003b }
        r1.add(r0);	 Catch:{ Throwable -> 0x003b }
        r0 = h;	 Catch:{ Throwable -> 0x003b }
        r0 = r0.size();	 Catch:{ Throwable -> 0x003b }
        r1 = 100;
        if (r0 < r1) goto L_0x0008;
    L_0x0028:
        r0 = new java.util.ArrayList;	 Catch:{ Throwable -> 0x003b }
        r0.<init>();	 Catch:{ Throwable -> 0x003b }
        r1 = h;	 Catch:{ Throwable -> 0x003b }
        r0.addAll(r1);	 Catch:{ Throwable -> 0x003b }
        com.loc.bs.a(r0, r3);	 Catch:{ Throwable -> 0x003b }
        r0 = h;	 Catch:{ Throwable -> 0x003b }
        r0.clear();	 Catch:{ Throwable -> 0x003b }
        goto L_0x0008;
    L_0x003b:
        r0 = move-exception;
        r1 = "ReportUtil";
        r2 = "applyStatistics";
        com.loc.cw.a(r0, r1, r2);
        goto L_0x0008;
        */
    }

    public static void a(String str, int i) {
        String valueOf = String.valueOf(i);
        String str2 = StringUtils.EMPTY_STRING;
        switch (i) {
            case WeatherType.OPPO_CHINA_WEATHER_HEAVY_STORM:
                str2 = "ContextIsNull";
                break;
            case WeatherType.OPPO_CHINA_WEATHER_LIGHT_TO_MODERATE_RAIN:
                str2 = "OnlyMainWifi";
                break;
            case WeatherType.OPPO_CHINA_WEATHER_MODERATE_TO_HEAVY_RAIN:
                str2 = "OnlyOneWifiButNotMain";
                break;
            case WeatherType.OPPO_CHINA_WEATHER_SANDSTORM:
                str2 = "CreateApsReqException";
                break;
            case 2041:
                str2 = "ResponseResultIsNull";
                break;
            case 2051:
                str2 = "NeedLoginNetWork\t";
                break;
            case 2052:
                str2 = "MaybeIntercepted";
                break;
            case 2053:
                str2 = "DecryptResponseException";
                break;
            case 2054:
                str2 = "ParserDataException";
                break;
            case 2061:
                str2 = "ServerRetypeError";
                break;
            case 2062:
                str2 = "ServerLocFail";
                break;
            case 2081:
                str2 = "LocalLocException";
                break;
            case 2091:
                str2 = "InitException";
                break;
            case AMapException.CODE_AMAP_NEARBY_KEY_NOT_BIND:
                str2 = "BindAPSServiceException";
                break;
            case 2102:
                str2 = "AuthClientScodeFail";
                break;
            case 2111:
                str2 = "ErrorCgiInfo";
                break;
            case 2121:
                str2 = "NotLocPermission";
                break;
            case 2131:
                str2 = "NoCgiOrWifiInfo";
                break;
            case 2141:
                str2 = "NoEnoughStatellites";
                break;
            case 2151:
                str2 = "MaybeMockNetLoc";
                break;
            case 2152:
                str2 = "MaybeMockGPSLoc";
                break;
        }
        a(str, valueOf, str2);
    }

    public static void a(String str, String str2) {
        try {
            z.b(cw.b(), str2, str);
        } catch (Throwable th) {
            cw.a(th, "ReportUtil", "reportLog");
        }
    }

    public static void a(String str, String str2, String str3) {
        try {
            z.a(cw.b(), "/mobile/binary", str3, str, str2);
        } catch (Throwable th) {
        }
    }

    public static void a(String str, Throwable th) {
        try {
            if (th instanceof j) {
                z.a(cw.b(), str, (j) th);
            }
        } catch (Throwable th2) {
        }
    }

    public static boolean a(Context context, s sVar) {
        try {
            return au.a(context, sVar);
        } catch (Throwable th) {
            return false;
        }
    }

    public static void b(Context context, int i, AMapLocation aMapLocation) {
        int i2;
        int i3 = 1;
        Object obj = null;
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                i2 = 1;
                i3 = 0;
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
            case DetectedActivity.RUNNING:
                i2 = 1;
                break;
            default:
                i3 = 0;
                break;
        }
        if (i2 != 0) {
            try {
                if (i == null) {
                    i = new JSONArray();
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("lon", aMapLocation.getLongitude());
                jSONObject.put("lat", aMapLocation.getLatitude());
                jSONObject.put("type", i3);
                jSONObject.put(WeatherEntry.COLUMN_2_TIMESTAMP, de.a());
                JSONArray put = i.put(jSONObject);
                i = put;
                if (put.length() >= cv.p()) {
                    f(context);
                }
            } catch (Throwable th) {
                cw.a(th, "ReportUtil", "recordOfflineLocLog");
            }
        }
    }

    private static void f(Context context) {
        try {
            if (i != null && i.length() > 0) {
                bq.a(new bp(context, cw.b(), i.toString()), context);
                i = null;
            }
        } catch (Throwable th) {
            cw.a(th, "ReportUtil", "writeOfflineLocLog");
        }
    }

    public final void a() {
        this.g = false;
        if (this.j != null) {
            this.j.clear();
        }
    }

    public final void a(Context context, int i) {
        try {
            if (this.b != i) {
                if (!(this.b == -1 || this.b == i)) {
                    long b = de.b() - this.c;
                    this.a.append(this.b, Long.valueOf(((Long) this.a.get(this.b, Long.valueOf(0))).longValue() + b));
                }
                this.c = de.b() - dd.b(context, "pref", this.d[i], 0);
                this.b = i;
            }
        } catch (Throwable th) {
            cw.a(th, "ReportUtil", "setLocationType");
        }
    }

    public final void a(Context context, AMapLocationClientOption aMapLocationClientOption) {
        try {
            int i;
            switch (AnonymousClass_1.a[aMapLocationClientOption.getLocationMode().ordinal()]) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i = 4;
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    i = 5;
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    i = 3;
                    break;
                default:
                    i = -1;
                    break;
            }
            if (this.e != i) {
                if (!(this.e == -1 || this.e == i)) {
                    this.a.append(this.e, Long.valueOf((de.b() - this.f) + ((Long) this.a.get(this.e, Long.valueOf(0))).longValue()));
                }
                this.f = de.b() - dd.b(context, "pref", this.d[i], 0);
                this.e = i;
            }
        } catch (Throwable th) {
            cw.a(th, "ReportUtil", "setLocationMode");
        }
    }

    public final void a(AMapLocation aMapLocation, AMapLocation aMapLocation2) {
        bx bxVar;
        if (aMapLocation.equals(aMapLocation2)) {
            bxVar = new bx(aMapLocation, 0);
            if (!this.j.contains(bxVar)) {
                if (!this.g && this.j.size() >= 5) {
                    this.j.removeFirst();
                }
                this.j.add(bxVar);
            }
        } else {
            if (!this.g) {
                this.g = true;
            }
            bxVar = new bx(aMapLocation, 1);
            if (!this.j.contains(bxVar)) {
                this.j.add(bxVar);
            }
        }
        if (this.j.size() >= 10) {
            LinkedList linkedList = (LinkedList) this.j.clone();
            this.j.clear();
            this.g = false;
            if (linkedList != null && !linkedList.isEmpty()) {
                StringBuffer stringBuffer = new StringBuffer();
                Iterator it = linkedList.iterator();
                while (it.hasNext()) {
                    stringBuffer.append(((bx) it.next()).toString());
                    stringBuffer.append("#");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                a("gpsstatistics", stringBuffer.toString());
            }
        }
    }

    public final void b(Context context) {
        try {
            long b = de.b() - this.c;
            if (this.b != -1) {
                this.a.append(this.b, Long.valueOf(((Long) this.a.get(this.b, Long.valueOf(0))).longValue() + b));
            }
            b = de.b() - this.f;
            if (this.e != -1) {
                this.a.append(this.e, Long.valueOf(((Long) this.a.get(this.e, Long.valueOf(0))).longValue() + b));
            }
            int i = 0;
            while (i < 6) {
                b = ((Long) this.a.get(i, Long.valueOf(0))).longValue();
                if (b > 0 && b > dd.b(context, "pref", this.d[i], 0)) {
                    dd.a(context, "pref", this.d[i], b);
                }
                i++;
            }
        } catch (Throwable th) {
            cw.a(th, "ReportUtil", "saveLocationTypeAndMode");
        }
    }

    public final int c(Context context) {
        try {
            long b = dd.b(context, "pref", this.d[2], 0);
            long b2 = dd.b(context, "pref", this.d[0], 0);
            long b3 = dd.b(context, "pref", this.d[1], 0);
            if (b == 0 && b2 == 0 && b3 == 0) {
                return -1;
            }
            b2 -= b;
            b3 -= b;
            return b > b2 ? b > b3 ? 2 : 1 : b2 > b3 ? 0 : 1;
        } catch (Throwable th) {
            return -1;
        }
    }

    public final int d(Context context) {
        try {
            long b = dd.b(context, "pref", this.d[3], 0);
            long b2 = dd.b(context, "pref", this.d[4], 0);
            long b3 = dd.b(context, "pref", this.d[5], 0);
            return (b == 0 && b2 == 0 && b3 == 0) ? -1 : b > b2 ? b > b3 ? 3 : 5 : b2 > b3 ? 4 : 5;
        } catch (Throwable th) {
            return -1;
        }
    }

    public final void e(Context context) {
        int i = 0;
        while (i < this.d.length) {
            try {
                dd.a(context, "pref", this.d[i], 0);
                i++;
            } catch (Throwable th) {
            }
        }
    }
}
