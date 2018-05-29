package com.loc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.Settings.System;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import java.io.File;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import javax.xml.parsers.SAXParserFactory;
import net.oneplus.weather.db.CityWeatherDBHelper.CityListEntry;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// compiled from: DeviceInfo.java
public final class n {
    private static String a;
    private static boolean b;
    private static String c;
    private static String d;
    private static String e;
    private static String f;
    private static String g;

    // compiled from: DeviceInfo.java
    static class a extends DefaultHandler {
        a() {
        }

        public final void characters(char[] cArr, int i, int i2) throws SAXException {
            if (b) {
                a = new String(cArr, i, i2);
            }
        }

        public final void endElement(String str, String str2, String str3) throws SAXException {
            b = false;
        }

        public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            if (str2.equals("string") && "UTDID".equals(attributes.getValue(CityListEntry.COLUMN_2_NAME))) {
                b = true;
            }
        }
    }

    static {
        a = StringUtils.EMPTY_STRING;
        b = false;
        c = null;
        d = StringUtils.EMPTY_STRING;
        e = StringUtils.EMPTY_STRING;
        f = StringUtils.EMPTY_STRING;
        g = StringUtils.EMPTY_STRING;
    }

    public static String a() {
        return c;
    }

    public static String a(Context context) {
        try {
            return u(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return StringUtils.EMPTY_STRING;
        }
    }

    private static List<ScanResult> a(List<ScanResult> list) {
        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            for (int i2 = 1; i2 < size - i; i2++) {
                if (((ScanResult) list.get(i2 - 1)).level > ((ScanResult) list.get(i2)).level) {
                    ScanResult scanResult = (ScanResult) list.get(i2 - 1);
                    list.set(i2 - 1, list.get(i2));
                    list.set(i2, scanResult);
                }
            }
        }
        return list;
    }

    public static void a(String str) {
        c = str;
    }

    private static boolean a(Context context, String str) {
        return context != null && context.checkCallingOrSelfPermission(str) == 0;
    }

    public static String b(Context context) {
        String str = StringUtils.EMPTY_STRING;
        try {
            String str2 = StringUtils.EMPTY_STRING;
            String r = r(context);
            return (r == null || r.length() < 5) ? str2 : r.substring(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER);
        } catch (Throwable th) {
            th.printStackTrace();
            return str;
        }
    }

    public static void b() {
        try {
            if (VERSION.SDK_INT > 14) {
                TrafficStats.class.getDeclaredMethod("setThreadStatsTag", new Class[]{Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(40964)});
            }
        } catch (Throwable th) {
            w.a(th, "DeviceInfo", "setTraficTag");
        }
    }

    public static int c(Context context) {
        try {
            return x(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return -1;
        }
    }

    public static int d(Context context) {
        try {
            return v(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return -1;
        }
    }

    private static String d() {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    byte[] hardwareAddress;
                    Object[] objArr = null;
                    if (VERSION.SDK_INT >= 9) {
                        hardwareAddress = networkInterface.getHardwareAddress();
                    }
                    if (hardwareAddress == null) {
                        return StringUtils.EMPTY_STRING;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    for (byte b : hardwareAddress) {
                        String toUpperCase = Integer.toHexString(b & 255).toUpperCase();
                        if (toUpperCase.length() == 1) {
                            stringBuilder.append("0");
                        }
                        stringBuilder.append(toUpperCase).append(":");
                    }
                    if (stringBuilder.length() > 0) {
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    }
                    return stringBuilder.toString();
                }
            }
        } catch (Throwable e) {
            w.a(e, "DeviceInfo", "getMacAddr");
        }
        return StringUtils.EMPTY_STRING;
    }

    public static String e(Context context) {
        try {
            return t(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return StringUtils.EMPTY_STRING;
        }
    }

    public static String f(Context context) {
        try {
            if (a != null && !StringUtils.EMPTY_STRING.equals(a)) {
                return a;
            }
            if (a(context, "android.permission.WRITE_SETTINGS")) {
                a = System.getString(context.getContentResolver(), "mqBRboGZkQPcAkyk");
            }
            if (!(a == null || StringUtils.EMPTY_STRING.equals(a))) {
                return a;
            }
            try {
                if ("mounted".equals(Environment.getExternalStorageState())) {
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.UTSystemConfig/Global/Alvin2.xml");
                    if (file.exists()) {
                        SAXParserFactory.newInstance().newSAXParser().parse(file, new a());
                    }
                }
            } catch (Throwable th) {
            }
            return a == null ? StringUtils.EMPTY_STRING : a;
        } catch (Throwable th2) {
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.String g(android.content.Context r4) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.n.g(android.content.Context):java.lang.String");
        /*
        r1 = "";
        if (r4 == 0) goto L_0x000c;
    L_0x0004:
        r0 = "android.permission.ACCESS_WIFI_STATE";
        r0 = a(r4, r0);	 Catch:{ Throwable -> 0x0027 }
        if (r0 != 0) goto L_0x000d;
    L_0x000c:
        return r1;
    L_0x000d:
        r0 = "wifi";
        r0 = r4.getSystemService(r0);	 Catch:{ Throwable -> 0x0027 }
        r0 = (android.net.wifi.WifiManager) r0;	 Catch:{ Throwable -> 0x0027 }
        if (r0 == 0) goto L_0x000c;
    L_0x0017:
        r2 = r0.isWifiEnabled();	 Catch:{ Throwable -> 0x0027 }
        if (r2 == 0) goto L_0x002f;
    L_0x001d:
        r0 = r0.getConnectionInfo();	 Catch:{ Throwable -> 0x0027 }
        r0 = r0.getBSSID();	 Catch:{ Throwable -> 0x0027 }
    L_0x0025:
        r1 = r0;
        goto L_0x000c;
    L_0x0027:
        r0 = move-exception;
        r2 = "DeviceInfo";
        r3 = "getWifiMacs";
        com.loc.w.a(r0, r2, r3);
    L_0x002f:
        r0 = r1;
        goto L_0x0025;
        */
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.String h(android.content.Context r7) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.n.h(android.content.Context):java.lang.String");
        /*
        r2 = 0;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        if (r7 == 0) goto L_0x0010;
    L_0x0008:
        r0 = "android.permission.ACCESS_WIFI_STATE";
        r0 = a(r7, r0);	 Catch:{ Throwable -> 0x0060 }
        if (r0 != 0) goto L_0x0015;
    L_0x0010:
        r0 = r4.toString();	 Catch:{ Throwable -> 0x0060 }
    L_0x0014:
        return r0;
    L_0x0015:
        r0 = "wifi";
        r0 = r7.getSystemService(r0);	 Catch:{ Throwable -> 0x0060 }
        r0 = (android.net.wifi.WifiManager) r0;	 Catch:{ Throwable -> 0x0060 }
        if (r0 != 0) goto L_0x0022;
    L_0x001f:
        r0 = "";
        goto L_0x0014;
    L_0x0022:
        r1 = r0.isWifiEnabled();	 Catch:{ Throwable -> 0x0060 }
        if (r1 == 0) goto L_0x0068;
    L_0x0028:
        r0 = r0.getScanResults();	 Catch:{ Throwable -> 0x0060 }
        if (r0 == 0) goto L_0x0034;
    L_0x002e:
        r1 = r0.size();	 Catch:{ Throwable -> 0x0060 }
        if (r1 != 0) goto L_0x0039;
    L_0x0034:
        r0 = r4.toString();	 Catch:{ Throwable -> 0x0060 }
        goto L_0x0014;
    L_0x0039:
        r5 = a(r0);	 Catch:{ Throwable -> 0x0060 }
        r1 = 1;
        r3 = r2;
    L_0x003f:
        r0 = r5.size();	 Catch:{ Throwable -> 0x0060 }
        if (r3 >= r0) goto L_0x0068;
    L_0x0045:
        r0 = 7;
        if (r3 >= r0) goto L_0x0068;
    L_0x0048:
        r0 = r5.get(r3);	 Catch:{ Throwable -> 0x0060 }
        r0 = (android.net.wifi.ScanResult) r0;	 Catch:{ Throwable -> 0x0060 }
        if (r1 == 0) goto L_0x005a;
    L_0x0050:
        r1 = r2;
    L_0x0051:
        r0 = r0.BSSID;	 Catch:{ Throwable -> 0x0060 }
        r4.append(r0);	 Catch:{ Throwable -> 0x0060 }
        r0 = r3 + 1;
        r3 = r0;
        goto L_0x003f;
    L_0x005a:
        r6 = ";";
        r4.append(r6);	 Catch:{ Throwable -> 0x0060 }
        goto L_0x0051;
    L_0x0060:
        r0 = move-exception;
        r1 = "DeviceInfo";
        r2 = "getWifiMacs";
        com.loc.w.a(r0, r1, r2);
    L_0x0068:
        r0 = r4.toString();
        goto L_0x0014;
        */
    }

    public static String i(Context context) {
        try {
            if (d != null && !StringUtils.EMPTY_STRING.equals(d)) {
                return d;
            }
            if (!a(context, "android.permission.ACCESS_WIFI_STATE")) {
                return d;
            }
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager == null) {
                return StringUtils.EMPTY_STRING;
            }
            d = wifiManager.getConnectionInfo().getMacAddress();
            if ("02:00:00:00:00:00".equals(d) || "00:00:00:00:00:00".equals(d)) {
                d = d();
            }
            return d;
        } catch (Throwable th) {
            w.a(th, "DeviceInfo", "getDeviceMac");
        }
    }

    static String[] j(Context context) {
        try {
            if (a(context, "android.permission.READ_PHONE_STATE") && a(context, "android.permission.ACCESS_COARSE_LOCATION")) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager == null) {
                    return new String[]{StringUtils.EMPTY_STRING, StringUtils.EMPTY_STRING};
                }
                CellLocation cellLocation = telephonyManager.getCellLocation();
                int cid;
                int lac;
                if (cellLocation instanceof GsmCellLocation) {
                    GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                    cid = gsmCellLocation.getCid();
                    lac = gsmCellLocation.getLac();
                    return new String[]{lac + "||" + cid, "gsm"};
                }
                if (cellLocation instanceof CdmaCellLocation) {
                    CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                    cid = cdmaCellLocation.getSystemId();
                    lac = cdmaCellLocation.getNetworkId();
                    int baseStationId = cdmaCellLocation.getBaseStationId();
                    return new String[]{cid + "||" + lac + "||" + baseStationId, "cdma"};
                }
                return new String[]{StringUtils.EMPTY_STRING, StringUtils.EMPTY_STRING};
            }
            return new String[]{StringUtils.EMPTY_STRING, StringUtils.EMPTY_STRING};
        } catch (Throwable th) {
            w.a(th, "DeviceInfo", "cellInfo");
        }
    }

    static String k(Context context) {
        String str = StringUtils.EMPTY_STRING;
        try {
            if (!a(context, "android.permission.READ_PHONE_STATE")) {
                return str;
            }
            TelephonyManager y = y(context);
            if (y == null) {
                return StringUtils.EMPTY_STRING;
            }
            String networkOperator = y.getNetworkOperator();
            return (TextUtils.isEmpty(networkOperator) || networkOperator.length() < 3) ? str : networkOperator.substring(RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
        } catch (Throwable th) {
            w.a(th, "DeviceInfo", "getMNC");
            return str;
        }
    }

    public static int l(Context context) {
        try {
            return x(context);
        } catch (Throwable th) {
            w.a(th, "DeviceInfo", "getNetWorkType");
            return -1;
        }
    }

    public static int m(Context context) {
        try {
            return v(context);
        } catch (Throwable th) {
            w.a(th, "DeviceInfo", "getActiveNetWorkType");
            return -1;
        }
    }

    public static NetworkInfo n(Context context) {
        if (!a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return null;
        }
        ConnectivityManager w = w(context);
        return w != null ? w.getActiveNetworkInfo() : null;
    }

    static String o(Context context) {
        try {
            NetworkInfo n = n(context);
            return n == null ? null : n.getExtraInfo();
        } catch (Throwable th) {
            w.a(th, "DeviceInfo", "getNetworkExtraInfo");
            return null;
        }
    }

    static String p(Context context) {
        try {
            if (e != null && !StringUtils.EMPTY_STRING.equals(e)) {
                return e;
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager == null) {
                return StringUtils.EMPTY_STRING;
            }
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            e = i2 > i ? i + "*" + i2 : i2 + "*" + i;
            return e;
        } catch (Throwable th) {
            w.a(th, "DeviceInfo", "getReslution");
        }
    }

    public static String q(Context context) {
        try {
            if (f != null && !StringUtils.EMPTY_STRING.equals(f)) {
                return f;
            }
            if (!a(context, "android.permission.READ_PHONE_STATE")) {
                return f;
            }
            TelephonyManager y = y(context);
            if (y == null) {
                return StringUtils.EMPTY_STRING;
            }
            String deviceId = y.getDeviceId();
            f = deviceId;
            if (deviceId == null) {
                f = StringUtils.EMPTY_STRING;
            }
            return f;
        } catch (Throwable th) {
            w.a(th, "DeviceInfo", "getDeviceID");
        }
    }

    public static String r(Context context) {
        String str = StringUtils.EMPTY_STRING;
        try {
            return t(context);
        } catch (Throwable th) {
            w.a(th, "DeviceInfo", "getSubscriberId");
            return str;
        }
    }

    static String s(Context context) {
        try {
            return u(context);
        } catch (Throwable th) {
            w.a(th, "DeviceInfo", "getNetworkOperatorName");
            return StringUtils.EMPTY_STRING;
        }
    }

    private static String t(Context context) {
        if (g != null && !StringUtils.EMPTY_STRING.equals(g)) {
            return g;
        }
        if (!a(context, "android.permission.READ_PHONE_STATE")) {
            return g;
        }
        TelephonyManager y = y(context);
        if (y == null) {
            return StringUtils.EMPTY_STRING;
        }
        String subscriberId = y.getSubscriberId();
        g = subscriberId;
        if (subscriberId == null) {
            g = StringUtils.EMPTY_STRING;
        }
        return g;
    }

    private static String u(Context context) {
        if (!a(context, "android.permission.READ_PHONE_STATE")) {
            return null;
        }
        TelephonyManager y = y(context);
        if (y == null) {
            return StringUtils.EMPTY_STRING;
        }
        Object simOperatorName = y.getSimOperatorName();
        return TextUtils.isEmpty(simOperatorName) ? y.getNetworkOperatorName() : simOperatorName;
    }

    private static int v(Context context) {
        if (context == null || !a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return -1;
        }
        ConnectivityManager w = w(context);
        if (w == null) {
            return -1;
        }
        NetworkInfo activeNetworkInfo = w.getActiveNetworkInfo();
        return activeNetworkInfo != null ? activeNetworkInfo.getType() : -1;
    }

    private static ConnectivityManager w(Context context) {
        return (ConnectivityManager) context.getSystemService("connectivity");
    }

    private static int x(Context context) {
        if (!a(context, "android.permission.READ_PHONE_STATE")) {
            return -1;
        }
        TelephonyManager y = y(context);
        return y != null ? y.getNetworkType() : -1;
    }

    private static TelephonyManager y(Context context) {
        return (TelephonyManager) context.getSystemService("phone");
    }
}
