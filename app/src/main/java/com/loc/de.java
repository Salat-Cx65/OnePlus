package com.loc;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.location.Location;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.SparseArray;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.DPoint;
import com.autonavi.aps.amapapi.model.AMapLocationServer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import net.oneplus.weather.R;
import net.oneplus.weather.api.WeatherRequest.Type;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.json.JSONObject;

// compiled from: Utils.java
public final class de {
    static String a;
    private static int b;
    private static String[] c;
    private static Hashtable<String, Long> d;
    private static SparseArray<String> e;
    private static SimpleDateFormat f;
    private static String[] g;

    static {
        b = 0;
        c = null;
        d = new Hashtable();
        e = null;
        f = null;
        g = new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
        a = null;
    }

    public static double a(double d) {
        return ((double) ((long) (d * 1000000.0d))) / 1000000.0d;
    }

    public static float a(float f) {
        return (float) (((double) ((long) (((double) f) * 100.0d))) / 100.0d);
    }

    public static float a(AMapLocation aMapLocation, AMapLocation aMapLocation2) {
        return a(new double[]{aMapLocation.getLatitude(), aMapLocation.getLongitude(), aMapLocation2.getLatitude(), aMapLocation2.getLongitude()});
    }

    public static float a(DPoint dPoint, DPoint dPoint2) {
        return a(new double[]{dPoint.getLatitude(), dPoint.getLongitude(), dPoint2.getLatitude(), dPoint2.getLongitude()});
    }

    public static float a(double[] dArr) {
        if (dArr.length != 4) {
            return AutoScrollHelper.RELATIVE_UNSPECIFIED;
        }
        float[] fArr = new float[1];
        Location.distanceBetween(dArr[0], dArr[1], dArr[2], dArr[3], fArr);
        return fArr[0];
    }

    public static int a(int i) {
        return (i * 2) - 113;
    }

    public static int a(NetworkInfo networkInfo) {
        return (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) ? networkInfo.getType() : -1;
    }

    public static long a() {
        return System.currentTimeMillis();
    }

    public static Object a(Context context, String str) {
        if (context == null) {
            return null;
        }
        try {
            return context.getApplicationContext().getSystemService(str);
        } catch (Throwable th) {
            cw.a(th, "Utils", "getServ");
            return null;
        }
    }

    public static void a(Context context, int i) {
        if (context != null) {
            try {
                af afVar = new af(context, af.a(co.class), i());
                Object clVar = new cl();
                clVar.a(i);
                afVar.a(clVar, "_id=1");
            } catch (Throwable th) {
                cw.a(th, "Utils", "getDBConfigVersion");
            }
        }
    }

    public static boolean a(long j, long j2) {
        String str = "yyyyMMddHH";
        if (f == null) {
            try {
                f = new SimpleDateFormat(str, Locale.CHINA);
            } catch (Throwable th) {
                cw.a(th, "Utils", "isSameDay part1");
            }
        } else {
            f.applyPattern(str);
        }
        try {
            return f != null ? f.format(Long.valueOf(j)).equals(f.format(Long.valueOf(j2))) : false;
        } catch (Throwable th2) {
            cw.a(th2, "Utils", "isSameHour");
            return false;
        }
    }

    public static boolean a(Context context) {
        if (context == null) {
            return false;
        }
        try {
            return c() < 17 ? c(context, "android.provider.Settings$System") : c(context, "android.provider.Settings$Global");
        } catch (Throwable th) {
            return false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(android.database.sqlite.SQLiteDatabase r6, java.lang.String r7) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.de.a(android.database.sqlite.SQLiteDatabase, java.lang.String):boolean");
        /*
        r2 = 0;
        r1 = 1;
        r0 = 0;
        r3 = android.text.TextUtils.isEmpty(r7);
        if (r3 == 0) goto L_0x000a;
    L_0x0009:
        return r0;
    L_0x000a:
        r3 = "2.0.201501131131";
        r4 = ".";
        r5 = "";
        r3 = r3.replace(r4, r5);
        if (r6 == 0) goto L_0x0009;
    L_0x0016:
        r4 = r6.isOpen();	 Catch:{ Throwable -> 0x005e, all -> 0x0067 }
        if (r4 == 0) goto L_0x0009;
    L_0x001c:
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x005e, all -> 0x0067 }
        r4.<init>();	 Catch:{ Throwable -> 0x005e, all -> 0x0067 }
        r5 = "SELECT count(*) as c FROM sqlite_master WHERE type = 'table' AND name = '";
        r4.append(r5);	 Catch:{ Throwable -> 0x005e, all -> 0x0067 }
        r5 = r7.trim();	 Catch:{ Throwable -> 0x005e, all -> 0x0067 }
        r5 = r4.append(r5);	 Catch:{ Throwable -> 0x005e, all -> 0x0067 }
        r3 = r5.append(r3);	 Catch:{ Throwable -> 0x005e, all -> 0x0067 }
        r5 = "' ";
        r3.append(r5);	 Catch:{ Throwable -> 0x005e, all -> 0x0067 }
        r3 = r4.toString();	 Catch:{ Throwable -> 0x005e, all -> 0x0067 }
        r5 = 0;
        r2 = r6.rawQuery(r3, r5);	 Catch:{ Throwable -> 0x005e, all -> 0x0067 }
        if (r2 == 0) goto L_0x0050;
    L_0x0042:
        r3 = r2.moveToFirst();	 Catch:{ Throwable -> 0x006e, all -> 0x0067 }
        if (r3 == 0) goto L_0x0050;
    L_0x0048:
        r3 = 0;
        r3 = r2.getInt(r3);	 Catch:{ Throwable -> 0x006e, all -> 0x0067 }
        if (r3 <= 0) goto L_0x0050;
    L_0x004f:
        r0 = r1;
    L_0x0050:
        r3 = 0;
        r5 = r4.length();	 Catch:{ Throwable -> 0x006e, all -> 0x0067 }
        r4.delete(r3, r5);	 Catch:{ Throwable -> 0x006e, all -> 0x0067 }
        if (r2 == 0) goto L_0x0009;
    L_0x005a:
        r2.close();
        goto L_0x0009;
    L_0x005e:
        r0 = move-exception;
        r0 = r2;
    L_0x0060:
        if (r0 == 0) goto L_0x0071;
    L_0x0062:
        r0.close();
        r0 = r1;
        goto L_0x0009;
    L_0x0067:
        r0 = move-exception;
        if (r2 == 0) goto L_0x006d;
    L_0x006a:
        r2.close();
    L_0x006d:
        throw r0;
    L_0x006e:
        r0 = move-exception;
        r0 = r2;
        goto L_0x0060;
    L_0x0071:
        r0 = r1;
        goto L_0x0009;
        */
    }

    public static boolean a(Location location, int i) {
        Bundle extras = location.getExtras();
        return (extras != null ? extras.getInt("satellites") : 0) <= 0 ? true : i == 0 && location.getAltitude() == 0.0d && location.getBearing() == 0.0f && location.getSpeed() == 0.0f;
    }

    public static boolean a(AMapLocation aMapLocation) {
        return (aMapLocation != null && aMapLocation.getErrorCode() == 0) ? b(aMapLocation) : false;
    }

    public static boolean a(AMapLocationServer aMapLocationServer) {
        return (aMapLocationServer == null || aMapLocationServer.d().equals("8") || aMapLocationServer.d().equals("5") || aMapLocationServer.d().equals("6")) ? false : b((AMapLocation) aMapLocationServer);
    }

    public static boolean a(String str) {
        return (!TextUtils.isEmpty(str) && TextUtils.isDigitsOnly(str)) ? ",202,204,206,208,212,213,214,216,218,219,220,222,225,226,228,230,231,232,234,235,238,240,242,244,246,247,248,250,255,257,259,260,262,266,268,270,272,274,276,278,280,282,283,284,286,288,289,290,292,293,294,295,297,302,308,310,311,312,313,314,315,316,310,330,332,334,338,340,342,344,346,348,350,352,354,356,358,360,362,363,364,365,366,368,370,372,374,376,400,401,402,404,405,406,410,412,413,414,415,416,417,418,419,420,421,422,424,425,426,427,428,429,430,431,432,434,436,437,438,440,441,450,452,454,455,456,457,466,467,470,472,502,505,510,514,515,520,525,528,530,534,535,536,537,539,540,541,542,543,544,545,546,547,548,549,550,551,552,553,555,602,603,604,605,606,607,608,609,610,611,612,613,614,615,616,617,618,619,620,621,622,623,624,625,626,627,628,629,630,631,632,633,634,635,636,637,638,639,640,641,642,643,645,646,647,648,649,650,651,652,653,654,655,657,659,702,704,706,708,710,712,714,716,722,724,730,732,734,736,738,740,742,744,746,748,750,901,".contains(new StringBuilder(",").append(str).append(",").toString()) : false;
    }

    public static boolean a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        String[] split = str.split("#");
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < split.length) {
            if (split[i].contains(",nb") || split[i].contains(",access")) {
                arrayList.add(split[i]);
            }
            i++;
        }
        String[] split2 = str2.toString().split("#");
        i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i2 < split2.length) {
            if (split2[i2].contains(",nb") || split2[i2].contains(",access")) {
                i3++;
                if (arrayList.contains(split2[i2])) {
                    i++;
                }
            }
            i2++;
        }
        return ((double) (i * 2)) >= ((double) (arrayList.size() + i3)) * 0.618d;
    }

    public static boolean a(JSONObject jSONObject, String str) {
        return t.a(jSONObject, str);
    }

    public static byte[] a(int i, byte[] bArr) {
        if (bArr == null || bArr.length < 2) {
            bArr = new byte[2];
        }
        bArr[0] = (byte) (i & 255);
        bArr[1] = (byte) ((65280 & i) >> 8);
        return bArr;
    }

    public static byte[] a(long j) {
        byte[] bArr = new byte[8];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) ((int) ((j >> (i * 8)) & 255));
        }
        return bArr;
    }

    public static byte[] a(byte[] bArr) {
        try {
            return t.b(bArr);
        } catch (Throwable th) {
            cw.a(th, "Utils", "gz");
            return null;
        }
    }

    public static String[] a(TelephonyManager telephonyManager) {
        Object obj = null;
        if (telephonyManager != null) {
            obj = telephonyManager.getNetworkOperator();
        }
        String[] strArr = new String[]{"0", "0"};
        int i = TextUtils.isEmpty(obj) ? 0 : !TextUtils.isDigitsOnly(obj) ? 0 : obj.length() <= 4 ? 0 : 1;
        if (i != 0) {
            strArr[0] = obj.substring(0, RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
            char[] toCharArray = obj.substring(RainSurfaceView.RAIN_LEVEL_DOWNPOUR).toCharArray();
            i = 0;
            while (i < toCharArray.length && Character.isDigit(toCharArray[i])) {
                i++;
            }
            strArr[1] = obj.substring(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, i + 3);
        }
        try {
            int parseInt = Integer.parseInt(strArr[0]);
        } catch (Throwable th) {
            cw.a(th, "Utils", "getMccMnc");
            parseInt = 0;
        }
        if (parseInt == 0) {
            strArr[0] = "0";
        }
        if (strArr[0].equals("0") || strArr[1].equals("0")) {
            return (strArr[0].equals("0") && strArr[1].equals("0") && c != null) ? c : strArr;
        } else {
            c = strArr;
            return strArr;
        }
    }

    public static double b(double d) {
        return ((double) ((long) (d * 100.0d))) / 100.0d;
    }

    public static long b() {
        return SystemClock.elapsedRealtime();
    }

    public static String b(int i) {
        String str = "\u5176\u4ed6\u9519\u8bef";
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return "success";
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return "\u91cd\u8981\u53c2\u6570\u4e3a\u7a7a";
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return "WIFI\u4fe1\u606f\u4e0d\u8db3";
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return "\u8bf7\u6c42\u53c2\u6570\u83b7\u53d6\u51fa\u73b0\u5f02\u5e38";
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return "\u7f51\u7edc\u8fde\u63a5\u5f02\u5e38";
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return "\u89e3\u6790\u6570\u636e\u5f02\u5e38";
            case ConnectionResult.RESOLUTION_REQUIRED:
                return "\u5b9a\u4f4d\u7ed3\u679c\u9519\u8bef";
            case DetectedActivity.WALKING:
                return "KEY\u9519\u8bef";
            case DetectedActivity.RUNNING:
                return "\u5176\u4ed6\u9519\u8bef";
            case ConnectionResult.SERVICE_INVALID:
                return "\u521d\u59cb\u5316\u5f02\u5e38";
            case ConnectionResult.DEVELOPER_ERROR:
                return "\u5b9a\u4f4d\u670d\u52a1\u542f\u52a8\u5931\u8d25";
            case ConnectionResult.LICENSE_CHECK_FAILED:
                return "\u9519\u8bef\u7684\u57fa\u7ad9\u4fe1\u606f\uff0c\u8bf7\u68c0\u67e5\u662f\u5426\u63d2\u5165SIM\u5361";
            case WeatherCircleView.ARC_DIN:
                return "\u7f3a\u5c11\u5b9a\u4f4d\u6743\u9650";
            case ConnectionResult.CANCELED:
                return "\u7f51\u7edc\u5b9a\u4f4d\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u8bbe\u5907\u662f\u5426\u63d2\u5165sim\u5361\uff0c\u662f\u5426\u5f00\u542f\u79fb\u52a8\u7f51\u7edc\u6216\u5f00\u542f\u4e86wifi\u6a21\u5757";
            case ConnectionResult.TIMEOUT:
                return "GPS \u5b9a\u4f4d\u5931\u8d25\uff0c\u7531\u4e8e\u8bbe\u5907\u5f53\u524d GPS \u72b6\u6001\u5dee,\u5efa\u8bae\u6301\u8bbe\u5907\u5230\u76f8\u5bf9\u5f00\u9614\u7684\u9732\u5929\u573a\u6240\u518d\u6b21\u5c1d\u8bd5";
            case ConnectionResult.INTERRUPTED:
                return "\u5f53\u524d\u8fd4\u56de\u4f4d\u7f6e\u4e3a\u6a21\u62df\u8f6f\u4ef6\u8fd4\u56de\uff0c\u8bf7\u5173\u95ed\u6a21\u62df\u8f6f\u4ef6\uff0c\u6216\u8005\u5728option\u4e2d\u8bbe\u7f6e\u5141\u8bb8\u6a21\u62df";
            default:
                return str;
        }
    }

    public static String b(Context context) {
        CharSequence charSequence = null;
        if (!TextUtils.isEmpty(cw.f)) {
            return cw.f;
        }
        if (context == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(k.c(context), Type.SUCCESS);
        } catch (Throwable th) {
            cw.a(th, "Utils", "getAppName part");
            packageInfo = null;
        }
        try {
            if (TextUtils.isEmpty(cw.g)) {
                cw.g = null;
            }
        } catch (Throwable th2) {
            cw.a(th2, "Utils", "getAppName");
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (packageInfo != null) {
            if (packageInfo.applicationInfo != null) {
                charSequence = packageInfo.applicationInfo.loadLabel(context.getPackageManager());
            }
            if (charSequence != null) {
                stringBuilder.append(charSequence.toString());
            }
            if (!TextUtils.isEmpty(packageInfo.versionName)) {
                stringBuilder.append(packageInfo.versionName);
            }
        }
        Object c = k.c(context);
        if (!TextUtils.isEmpty(c)) {
            stringBuilder.append(",").append(c);
        }
        if (!TextUtils.isEmpty(cw.g)) {
            stringBuilder.append(",").append(cw.g);
        }
        String toString = stringBuilder.toString();
        cw.f = toString;
        return toString;
    }

    public static String b(TelephonyManager telephonyManager) {
        int i = 0;
        if (e == null) {
            SparseArray sparseArray = new SparseArray();
            e = sparseArray;
            sparseArray.append(0, "UNKWN");
            e.append(1, "GPRS");
            e.append(RainSurfaceView.RAIN_LEVEL_SHOWER, "EDGE");
            e.append(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, "UMTS");
            e.append(RainSurfaceView.RAIN_LEVEL_RAINSTORM, "CDMA");
            e.append(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, "EVDO_0");
            e.append(ConnectionResult.RESOLUTION_REQUIRED, "EVDO_A");
            e.append(DetectedActivity.WALKING, "1xRTT");
            e.append(DetectedActivity.RUNNING, "HSDPA");
            e.append(ConnectionResult.SERVICE_INVALID, "HSUPA");
            e.append(ConnectionResult.DEVELOPER_ERROR, "HSPA");
            e.append(ConnectionResult.LICENSE_CHECK_FAILED, "IDEN");
            e.append(WeatherCircleView.ARC_DIN, "EVDO_B");
            e.append(ConnectionResult.CANCELED, "LTE");
            e.append(ConnectionResult.TIMEOUT, "EHRPD");
            e.append(ConnectionResult.INTERRUPTED, "HSPAP");
        }
        if (telephonyManager != null) {
            i = telephonyManager.getNetworkType();
        }
        return (String) e.get(i, "UNKWN");
    }

    public static String b(byte[] bArr) {
        return t.f(bArr);
    }

    public static boolean b(long j, long j2) {
        String str = "yyyyMMdd";
        if (f == null) {
            try {
                f = new SimpleDateFormat(str, Locale.CHINA);
            } catch (Throwable th) {
                cw.a(th, "Utils", "isSameDay part1");
            }
        } else {
            f.applyPattern(str);
        }
        try {
            return f != null ? f.format(Long.valueOf(j)).equals(f.format(Long.valueOf(j2))) : false;
        } catch (Throwable th2) {
            cw.a(th2, "Utils", "isSameDay");
            return false;
        }
    }

    public static boolean b(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0) != null;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean b(AMapLocation aMapLocation) {
        double longitude = aMapLocation.getLongitude();
        double latitude = aMapLocation.getLatitude();
        return !(longitude == 0.0d && latitude == 0.0d) && longitude <= 180.0d && latitude <= 90.0d && longitude >= -180.0d && latitude >= -90.0d;
    }

    public static boolean b(String str) {
        return (TextUtils.isEmpty(str) || str.equals("00:00:00:00:00:00") || str.contains(" :")) ? false : true;
    }

    public static byte[] b(int i, byte[] bArr) {
        if (bArr == null || bArr.length < 4) {
            bArr = new byte[4];
        }
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) ((i >> (i2 * 8)) & 255);
        }
        return bArr;
    }

    public static double c(double d) {
        return ((double) ((long) (d * 1000000.0d))) / 1000000.0d;
    }

    public static int c() {
        if (b > 0) {
            return b;
        }
        String str = "android.os.Build$VERSION";
        try {
            return ((Integer) cz.a(str, "SDK_INT")).intValue();
        } catch (Throwable th) {
            try {
                return Integer.parseInt(cz.a(str, "SDK").toString());
            } catch (Throwable th2) {
                return 0;
            }
        }
    }

    public static NetworkInfo c(Context context) {
        try {
            return n.n(context);
        } catch (Throwable th) {
            cw.a(th, "Utils", "getNetWorkInfo");
            return null;
        }
    }

    public static String c(String str) {
        return g(str);
    }

    public static boolean c(long j, long j2) {
        Object obj = 1;
        if (!b(j, j2)) {
            return false;
        }
        boolean z;
        Calendar instance = Calendar.getInstance(Locale.CHINA);
        instance.setTimeInMillis(j);
        int i = instance.get(ConnectionResult.LICENSE_CHECK_FAILED);
        instance.setTimeInMillis(j2);
        int i2 = instance.get(ConnectionResult.LICENSE_CHECK_FAILED);
        if (i <= 12 ? i2 <= 12 : i2 > 12) {
            z = false;
        }
        return z;
    }

    private static boolean c(Context context, String str) throws Throwable {
        ContentResolver contentResolver = context.getContentResolver();
        String toString = ((String) cz.a(str, "AIRPLANE_MODE_ON")).toString();
        return ((Integer) cz.a(str, "getInt", new Object[]{contentResolver, toString}, new Class[]{ContentResolver.class, String.class})).intValue() == 1;
    }

    public static byte[] c(byte[] bArr) {
        byte[] bArr2 = new byte[(bArr.length + 1)];
        bArr2[0] = (byte) 0;
        for (int i = 1; i <= bArr.length; i++) {
            bArr2[i] = bArr[bArr.length - i];
        }
        return bArr2;
    }

    public static String d() {
        return Build.MODEL;
    }

    public static String d(String str) {
        if (str == null || str.length() == 0) {
            return StringUtils.EMPTY_STRING;
        }
        try {
            return new String(Base64.decode(str, 0), "UTF-8");
        } catch (Throwable th) {
            cw.a(th, "Utils", "base642Str");
            return null;
        }
    }

    public static boolean d(Context context) {
        try {
            for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
                if (runningAppProcessInfo.processName.equals(k.c(context))) {
                    return runningAppProcessInfo.importance != 100;
                }
            }
            return false;
        } catch (Throwable th) {
            cw.a(th, "Utils", "isApplicationBroughtToBackground");
            return true;
        }
    }

    public static byte[] d(byte[] bArr) {
        Object obj = new Object[16];
        System.arraycopy(bArr, ConnectionResult.DEVELOPER_ERROR, obj, 0, Math.min(R.styleable.Toolbar_titleMargins, bArr.length) - 10);
        return obj;
    }

    public static int e(Context context) {
        try {
            List b = new af(context, af.a(co.class), i()).b("_id=1", cl.class);
            if (b != null && b.size() > 0) {
                return ((cl) b.get(0)).a();
            }
        } catch (Throwable th) {
            cw.a(th, "Utils", "getDBConfigVersion");
        }
        return -1;
    }

    public static String e() {
        return VERSION.RELEASE;
    }

    public static byte[] e(String str) {
        return a(Integer.parseInt(str), null);
    }

    public static int f() {
        return new Random().nextInt(AccessibilityNodeInfoCompat.ACTION_CUT) - 32768;
    }

    public static boolean f(Context context) {
        int i;
        if (VERSION.SDK_INT < 23 || context.getApplicationInfo().targetSdkVersion < 23) {
            String[] strArr = g;
            int length = strArr.length;
            for (i = 0; i < length; i++) {
                if (context.checkCallingOrSelfPermission(strArr[i]) != 0) {
                    return false;
                }
            }
            return true;
        }
        Application application = (Application) context;
        String[] strArr2 = g;
        int length2 = strArr2.length;
        for (i = 0; i < length2; i++) {
            String str = strArr2[i];
            try {
                int b = cz.b(application.getBaseContext(), "checkSelfPermission", str);
            } catch (Throwable th) {
                b = 0;
            }
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    public static byte[] f(String str) {
        return b(Integer.parseInt(str), null);
    }

    private static String g(String str) {
        byte[] bArr = null;
        try {
            bArr = str.getBytes("UTF-8");
        } catch (Throwable th) {
            cw.a(th, "Utils", "str2Base64");
        }
        return Base64.encodeToString(bArr, 0);
    }

    public static void g() {
        d.clear();
    }

    public static String h() {
        try {
            return o.a("S128DF1572465B890OE3F7A13167KLEI".getBytes("UTF-8")).substring(ConnectionResult.RESTRICTED_PROFILE);
        } catch (Throwable th) {
            return StringUtils.EMPTY_STRING;
        }
    }

    public static String i() {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getAbsolutePath()).append(File.separator);
        stringBuilder.append("amap").append(File.separator);
        stringBuilder.append("openamaplocationsdk").append(File.separator);
        return stringBuilder.toString();
    }
}
