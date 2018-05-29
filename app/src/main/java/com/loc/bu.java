package com.loc;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.widget.AutoScrollHelper;
import android.text.TextUtils;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationProtocol;
import com.autonavi.aps.amapapi.model.AMapLocationServer;
import com.google.android.gms.common.ConnectionResult;
import java.util.ArrayList;
import java.util.Locale;
import net.oneplus.weather.api.WeatherType;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

@SuppressLint({"NewApi"})
// compiled from: APS.java
public final class bu {
    static int G;
    private static boolean M;
    private static int O;
    StringBuilder A;
    boolean B;
    public boolean C;
    int D;
    ca E;
    boolean F;
    by H;
    String I;
    IntentFilter J;
    private int K;
    private String L;
    private String N;
    Context a;
    ConnectivityManager b;
    ci c;
    cf d;
    ch e;
    cg f;
    ck g;
    cm h;
    bv i;
    cc j;
    ArrayList<ScanResult> k;
    a l;
    AMapLocationClientOption m;
    AMapLocationServer n;
    long o;
    String p;
    cu q;
    boolean r;
    cr s;
    StringBuilder t;
    boolean u;
    boolean v;
    boolean w;
    boolean x;
    WifiInfo y;
    boolean z;

    // compiled from: APS.java
    class a extends BroadcastReceiver {
        a() {
        }

        public final void onReceive(Context context, Intent intent) {
            if (context != null && intent != null) {
                try {
                    String action = intent.getAction();
                    if (!TextUtils.isEmpty(action)) {
                        if (action.equals("android.net.wifi.SCAN_RESULTS")) {
                            if (bu.this.c != null) {
                                bu.this.c.d();
                            }
                        } else if (action.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                            if (bu.this.c != null) {
                                bu.this.c.e();
                            }
                        } else if (action.equals("android.intent.action.SCREEN_ON")) {
                            if (bu.this.f != null) {
                                bu.this.f.a(true);
                            }
                        } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                            if (bu.this.f != null) {
                                bu.this.f.a(false);
                                bu.this.f.c();
                            }
                        } else if (action.equals("android.net.conn.CONNECTIVITY_CHANGE") && bu.this.f != null) {
                            bu.this.f.d();
                        }
                    }
                } catch (Throwable th) {
                    cw.a(th, "APS", "onReceive");
                }
            }
        }
    }

    static {
        M = false;
        O = -1;
        G = -1;
    }

    public bu() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = new ArrayList();
        this.l = null;
        this.m = new AMapLocationClientOption();
        this.n = null;
        this.o = 0;
        this.K = 0;
        this.p = "00:00:00:00:00:00";
        this.q = null;
        this.r = false;
        this.L = null;
        this.s = null;
        this.t = new StringBuilder();
        this.u = true;
        this.v = true;
        this.w = true;
        this.x = false;
        this.y = null;
        this.z = true;
        this.N = null;
        this.A = null;
        this.B = false;
        this.C = false;
        this.D = 12;
        this.E = null;
        this.F = false;
        this.H = null;
        this.I = null;
        this.J = null;
    }

    private static AMapLocationServer a(int i, String str) {
        AMapLocationServer aMapLocationServer = new AMapLocationServer(StringUtils.EMPTY_STRING);
        aMapLocationServer.setErrorCode(i);
        aMapLocationServer.setLocationDetail(str);
        if (i == 15) {
            db.a(null, 2151);
        }
        return aMapLocationServer;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.autonavi.aps.amapapi.model.AMapLocationServer a(com.autonavi.aps.amapapi.model.AMapLocationServer r6, com.loc.bo r7) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bu.a(com.autonavi.aps.amapapi.model.AMapLocationServer, com.loc.bo):com.autonavi.aps.amapapi.model.AMapLocationServer");
        /*
        this = this;
        r0 = 0;
        if (r7 == 0) goto L_0x000c;
    L_0x0003:
        r1 = r7.a;	 Catch:{ Throwable -> 0x0059 }
        if (r1 == 0) goto L_0x000c;
    L_0x0007:
        r1 = r7.a;	 Catch:{ Throwable -> 0x0059 }
        r1 = r1.length;	 Catch:{ Throwable -> 0x0059 }
        if (r1 != 0) goto L_0x0033;
    L_0x000c:
        r0 = 4;
        r6.setErrorCode(r0);	 Catch:{ Throwable -> 0x0059 }
        r0 = r5.t;	 Catch:{ Throwable -> 0x0059 }
        r1 = "please check the network";
        r0.append(r1);	 Catch:{ Throwable -> 0x0059 }
        r0 = r5.A;	 Catch:{ Throwable -> 0x0059 }
        r0 = r0.toString();	 Catch:{ Throwable -> 0x0059 }
        r6.g(r0);	 Catch:{ Throwable -> 0x0059 }
        r0 = r5.t;	 Catch:{ Throwable -> 0x0059 }
        r0 = r0.toString();	 Catch:{ Throwable -> 0x0059 }
        r6.setLocationDetail(r0);	 Catch:{ Throwable -> 0x0059 }
        if (r7 == 0) goto L_0x0032;
    L_0x002b:
        r0 = r7.d;	 Catch:{ Throwable -> 0x0059 }
        r1 = 2041; // 0x7f9 float:2.86E-42 double:1.0084E-320;
        com.loc.db.a(r0, r1);	 Catch:{ Throwable -> 0x0059 }
    L_0x0032:
        return r6;
    L_0x0033:
        r1 = new com.loc.ct;	 Catch:{ Throwable -> 0x0059 }
        r1.<init>();	 Catch:{ Throwable -> 0x0059 }
        r2 = new java.lang.String;	 Catch:{ Throwable -> 0x0059 }
        r3 = r7.a;	 Catch:{ Throwable -> 0x0059 }
        r4 = "UTF-8";
        r2.<init>(r3, r4);	 Catch:{ Throwable -> 0x0059 }
        r3 = "\"status\":\"0\"";
        r3 = r2.contains(r3);	 Catch:{ Throwable -> 0x0059 }
        if (r3 == 0) goto L_0x0083;
    L_0x0049:
        r0 = r5.a;	 Catch:{ Throwable -> 0x0059 }
        r6 = r1.a(r2, r0, r7);	 Catch:{ Throwable -> 0x0059 }
        r0 = r5.A;	 Catch:{ Throwable -> 0x0059 }
        r0 = r0.toString();	 Catch:{ Throwable -> 0x0059 }
        r6.g(r0);	 Catch:{ Throwable -> 0x0059 }
        goto L_0x0032;
    L_0x0059:
        r0 = move-exception;
        r1 = "APS";
        r2 = "checkResponseEntity";
        com.loc.cw.a(r0, r1, r2);
        r1 = r5.t;
        r2 = new java.lang.StringBuilder;
        r3 = "check response exception ex is";
        r2.<init>(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        r1.append(r0);
        r0 = r5.t;
        r0 = r0.toString();
        r6.setLocationDetail(r0);
        goto L_0x0032;
    L_0x0083:
        r1 = "</body></html>";
        r1 = r2.contains(r1);	 Catch:{ Throwable -> 0x0059 }
        if (r1 == 0) goto L_0x00c3;
    L_0x008b:
        r0 = 5;
        r6.setErrorCode(r0);	 Catch:{ Throwable -> 0x0059 }
        r0 = r5.c;	 Catch:{ Throwable -> 0x0059 }
        if (r0 == 0) goto L_0x00b5;
    L_0x0093:
        r0 = r5.c;	 Catch:{ Throwable -> 0x0059 }
        r1 = r5.b;	 Catch:{ Throwable -> 0x0059 }
        r0 = r0.a(r1);	 Catch:{ Throwable -> 0x0059 }
        if (r0 == 0) goto L_0x00b5;
    L_0x009d:
        r0 = r5.t;	 Catch:{ Throwable -> 0x0059 }
        r1 = "make sure you are logged in to the network";
        r0.append(r1);	 Catch:{ Throwable -> 0x0059 }
        r0 = 0;
        r1 = 2051; // 0x803 float:2.874E-42 double:1.0133E-320;
        com.loc.db.a(r0, r1);	 Catch:{ Throwable -> 0x0059 }
    L_0x00aa:
        r0 = r5.t;	 Catch:{ Throwable -> 0x0059 }
        r0 = r0.toString();	 Catch:{ Throwable -> 0x0059 }
        r6.setLocationDetail(r0);	 Catch:{ Throwable -> 0x0059 }
        goto L_0x0032;
    L_0x00b5:
        r0 = r5.t;	 Catch:{ Throwable -> 0x0059 }
        r1 = "request may be intercepted";
        r0.append(r1);	 Catch:{ Throwable -> 0x0059 }
        r0 = 0;
        r1 = 2052; // 0x804 float:2.875E-42 double:1.014E-320;
        com.loc.db.a(r0, r1);	 Catch:{ Throwable -> 0x0059 }
        goto L_0x00aa;
    L_0x00c3:
        r6 = r0;
        goto L_0x0032;
        */
    }

    @SuppressLint({"NewApi"})
    private AMapLocationServer a(boolean z, boolean z2) {
        AMapLocationServer aMapLocationServer = new AMapLocationServer(StringUtils.EMPTY_STRING);
        ct ctVar = new ct();
        try {
            if (this.q == null) {
                this.q = new cu();
            }
            if (this.m == null) {
                this.m = new AMapLocationClientOption();
            }
            this.q.a(this.a, this.m.isNeedAddress(), this.m.isOffset(), this.d, this.c, this.b, this.j, this.p, this.f.f(), this.I);
            this.e.a(this.d);
            byte[] a = this.q.a();
            this.o = de.b();
            try {
                AMapLocationServer aMapLocationServer2;
                cs a2 = this.s.a(this.a, a, cw.a(), z2);
                cp.a(this.a).a(a2);
                bo a3 = this.s.a(a2);
                String str = StringUtils.EMPTY_STRING;
                if (a3 != null) {
                    cp.a(this.a).a();
                    aMapLocationServer.a((long) this.s.a());
                    if (!TextUtils.isEmpty(a3.c)) {
                        this.t.append(new StringBuilder(" #csid:").append(a3.c).toString());
                    }
                    str = a3.d;
                    aMapLocationServer.g(this.A.toString());
                }
                if (z) {
                    aMapLocationServer2 = aMapLocationServer;
                } else {
                    AMapLocationServer a4 = a(aMapLocationServer, a3);
                    if (a4 != null) {
                        return a4;
                    }
                    byte[] a5 = cj.a(a3.a);
                    if (a5 == null) {
                        aMapLocationServer.setErrorCode(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER);
                        this.t.append("decrypt response data error");
                        aMapLocationServer.setLocationDetail(this.t.toString());
                        db.a(str, 2053);
                        return aMapLocationServer;
                    }
                    a4 = ctVar.a(aMapLocationServer, a5);
                    if (de.a(a4)) {
                        if (this.j != null) {
                            cc ccVar = this.j;
                            String d = a4.d();
                            float accuracy = a4.getAccuracy();
                            try {
                                if (!"-1".equals(d) || accuracy > 5.0f) {
                                    ccVar.a();
                                } else {
                                    ccVar.b();
                                }
                            } catch (Throwable th) {
                                cw.a(th, "BeaconManager", "checkLocationType");
                            }
                        }
                        if (a4.getErrorCode() == 0 && a4.getLocationType() == 0) {
                            if ("-5".equals(a4.d()) || "1".equals(a4.d()) || "2".equals(a4.d()) || "14".equals(a4.d()) || "24".equals(a4.d()) || "-1".equals(a4.d())) {
                                a4.setLocationType(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER);
                            } else {
                                a4.setLocationType(ConnectionResult.RESOLUTION_REQUIRED);
                            }
                        }
                        a4.setOffset(this.v);
                        a4.a(this.u);
                        aMapLocationServer2 = a4;
                    } else {
                        this.L = a4.b();
                        if (TextUtils.isEmpty(this.L)) {
                            db.a(str, 2061);
                        } else {
                            db.a(str, 2062);
                        }
                        a4.setErrorCode(ConnectionResult.RESOLUTION_REQUIRED);
                        this.t.append(new StringBuilder("location faile retype:").append(a4.d()).append(" rdesc:").append(TextUtils.isEmpty(this.L) ? StringUtils.EMPTY_STRING : this.L).toString());
                        a4.g(this.A.toString());
                        a4.setLocationDetail(this.t.toString());
                        return a4;
                    }
                }
                aMapLocationServer2.e("new");
                aMapLocationServer2.setLocationDetail(this.t.toString());
                this.I = aMapLocationServer2.a();
                return aMapLocationServer2;
            } catch (Throwable th2) {
                cp.a(this.a).b();
                cw.a(th2, "APS", "getApsLoc req");
                db.a("/mobile/binary", th2);
                this.t.append("request error, please check the network");
                aMapLocationServer = a((int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.t.toString());
                aMapLocationServer.g(this.A.toString());
                return aMapLocationServer;
            }
        } catch (Throwable th22) {
            this.t.append(new StringBuilder("get parames error:").append(th22.getMessage()).toString());
            db.a(null, (int) WeatherType.OPPO_CHINA_WEATHER_SANDSTORM);
            aMapLocationServer = a((int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.t.toString());
            aMapLocationServer.g(this.A.toString());
            return aMapLocationServer;
        }
    }

    private StringBuilder a(StringBuilder stringBuilder) {
        if (stringBuilder == null) {
            stringBuilder = new StringBuilder(700);
        } else {
            stringBuilder.delete(0, stringBuilder.length());
        }
        stringBuilder.append(this.d.j());
        stringBuilder.append(this.c.i());
        return stringBuilder;
    }

    public static void b(Context context) {
        try {
            if (O == -1 || cv.h(context)) {
                O = 1;
                cv.a(context);
            }
        } catch (Throwable th) {
            cw.a(th, "APS", "initAuth");
        }
    }

    private void b(AMapLocationServer aMapLocationServer) {
        if (this.e != null) {
            if (aMapLocationServer != null) {
                this.n = aMapLocationServer;
                this.e.a(this.n.toJson(1));
            }
            this.e.c();
        }
    }

    private void k() {
        if (this.t.length() > 0) {
            this.t.delete(0, this.t.length());
        }
    }

    private void l() {
        try {
            if (this.l == null) {
                this.l = new a();
            }
            if (this.J == null) {
                this.J = new IntentFilter();
                this.J.addAction("android.net.wifi.WIFI_STATE_CHANGED");
                this.J.addAction("android.net.wifi.SCAN_RESULTS");
                this.J.addAction("android.intent.action.SCREEN_ON");
                this.J.addAction("android.intent.action.SCREEN_OFF");
                this.J.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            }
            this.a.registerReceiver(this.l, this.J);
        } catch (Throwable th) {
            cw.a(th, "APS", "initBroadcastListener");
        }
    }

    private String m() {
        String str = StringUtils.EMPTY_STRING;
        String str2 = "network";
        int e = this.d.e();
        ce c = this.d.c();
        String h;
        if (c == null && (this.k == null || this.k.isEmpty())) {
            h = this.d.h();
            String a = this.c.a();
            if (h != null || a != null) {
                this.D = 12;
                StringBuilder stringBuilder = this.t;
                if (h == null) {
                    h = a;
                }
                stringBuilder.append(h);
                return str;
            } else if (de.f(this.a)) {
                this.t.append("no cgi & no wifis");
                this.D = 13;
                return str;
            } else {
                this.t.append("locationpermission has not been granted");
                this.D = 12;
                return str;
            }
        }
        this.y = this.c.g();
        ci ciVar = this.c;
        this.z = ci.a(this.y);
        switch (e) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                int i = (!this.k.isEmpty() || this.z) ? 1 : 0;
                if (this.z && this.k.isEmpty()) {
                    this.D = 2;
                    this.t.append("no around wifi(s) & has access wifi");
                    db.a(null, (int) WeatherType.OPPO_CHINA_WEATHER_LIGHT_TO_MODERATE_RAIN);
                    i = 0;
                } else if (this.k.size() == 1) {
                    this.D = 2;
                    if (this.z) {
                        if (this.c.g().getBSSID().equals(((ScanResult) this.k.get(0)).BSSID)) {
                            this.t.append("same access wifi & around wifi 1");
                            db.a(null, (int) WeatherType.OPPO_CHINA_WEATHER_LIGHT_TO_MODERATE_RAIN);
                            i = 0;
                        }
                    } else {
                        this.t.append("no access wifi & around wifi 1");
                        db.a(null, (int) WeatherType.OPPO_CHINA_WEATHER_MODERATE_TO_HEAVY_RAIN);
                        i = 0;
                    }
                }
                h = String.format(Locale.US, "#%s#", new Object[]{str2});
                if (i != 0) {
                    h = h + "wifi";
                } else if (str2.equals("network")) {
                    h = StringUtils.EMPTY_STRING;
                    this.D = 2;
                    this.t.append("is network & no wifi");
                }
                if (!TextUtils.isEmpty(h)) {
                    if (!h.startsWith("#")) {
                        h = new StringBuilder("#").append(h).toString();
                    }
                    h = de.h() + h;
                }
                return h;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                if (c != null) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(c.a).append("#");
                    stringBuilder.append(c.b).append("#");
                    stringBuilder.append(c.c).append("#");
                    stringBuilder.append(c.d).append("#");
                    stringBuilder.append(str2).append("#");
                    h = (!this.k.isEmpty() || this.z) ? "cgiwifi" : "cgi";
                    stringBuilder.append(h);
                    h = stringBuilder.toString();
                    if (TextUtils.isEmpty(h)) {
                        if (h.startsWith("#")) {
                            h = new StringBuilder("#").append(h).toString();
                        }
                        h = de.h() + h;
                    }
                    return h;
                }
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                if (c != null) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(c.a).append("#");
                    stringBuilder.append(c.b).append("#");
                    stringBuilder.append(c.g).append("#");
                    stringBuilder.append(c.h).append("#");
                    stringBuilder.append(c.i).append("#");
                    stringBuilder.append(str2).append("#");
                    h = (!this.k.isEmpty() || this.z) ? "cgiwifi" : "cgi";
                    stringBuilder.append(h);
                    h = stringBuilder.toString();
                    if (TextUtils.isEmpty(h)) {
                        if (h.startsWith("#")) {
                            h = new StringBuilder("#").append(h).toString();
                        }
                        h = de.h() + h;
                    }
                    return h;
                }
            default:
                this.D = 11;
                this.t.append("get cgi failure");
                break;
        }
        h = str;
        if (TextUtils.isEmpty(h)) {
            if (h.startsWith("#")) {
                h = new StringBuilder("#").append(h).toString();
            }
            h = de.h() + h;
        }
        return h;
    }

    public final AMapLocationServer a(double d, double d2) {
        try {
            String a = this.s.a(new StringBuilder("output=json&radius=1000&extensions=all&location=").append(d2).append(",").append(d).toString().getBytes("UTF-8"), this.a, "http://restapi.amap.com/v3/geocode/regeo");
            ct ctVar = new ct();
            if (a.contains("\"status\":\"1\"")) {
                AMapLocationServer a2 = ct.a(a);
                a2.setLatitude(d);
                a2.setLongitude(d2);
                return a2;
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public final AMapLocationServer a(AMapLocationServer aMapLocationServer, String... strArr) {
        this.H.a(this.w);
        if (strArr == null || strArr.length == 0) {
            return this.H.a(aMapLocationServer);
        }
        if (strArr[0].equals("shake")) {
            return this.H.a(aMapLocationServer);
        }
        if (!strArr[0].equals("fusion")) {
            return aMapLocationServer;
        }
        by byVar = this.H;
        return aMapLocationServer;
    }

    public final AMapLocationServer a(boolean z) {
        if (this.a == null) {
            this.t.append("context is null");
            db.a(null, (int) WeatherType.OPPO_CHINA_WEATHER_HEAVY_STORM);
            return a(1, this.t.toString());
        } else if (this.c.h()) {
            return a((int) ConnectionResult.INTERRUPTED, "networkLocation has been mocked!");
        } else {
            a();
            if (TextUtils.isEmpty(this.N)) {
                return a(this.D, this.t.toString());
            }
            AMapLocationServer a = a(false, z);
            if (de.a(a)) {
                this.g.a(this.A.toString());
                this.g.a(this.d.c());
                b(a);
                return a;
            }
            String toString = this.A.toString();
            this.e.a(this.a);
            ch chVar = this.e;
            ck ckVar = this.g;
            String str = this.N;
            AMapLocationClientOption aMapLocationClientOption = this.m;
            Context context = this.a;
            return chVar.a(ckVar, str, toString, aMapLocationClientOption, m(), a);
        }
    }

    public final void a() {
        this.s = cr.a(this.a);
        if (this.s != null) {
            try {
                this.s.a(this.m.getHttpTimeOut(), this.m.getLocationProtocol().equals(AMapLocationProtocol.HTTPS));
            } catch (Throwable th) {
            }
        }
        if (this.b == null) {
            this.b = (ConnectivityManager) de.a(this.a, "connectivity");
        }
        if (this.q == null) {
            this.q = new cu();
        }
    }

    public final void a(Context context) {
        try {
            if (this.a == null) {
                this.H = new by();
                this.a = context.getApplicationContext();
                cv.e(this.a);
                de.b(this.a);
                if (this.c == null) {
                    this.c = new ci(this.a, (WifiManager) de.a(this.a, "wifi"));
                }
                if (this.d == null) {
                    this.d = new cf(this.a);
                }
                if (this.e == null) {
                    this.e = new ch();
                }
                if (this.f == null) {
                    this.f = new cg();
                }
                if (this.g == null) {
                    this.g = new ck();
                }
                if (this.h == null) {
                    this.h = new cm(context);
                }
            }
        } catch (Throwable th) {
            cw.a(th, "APS", "initBase");
        }
    }

    public final void a(AMapLocationClientOption aMapLocationClientOption) {
        boolean isNeedAddress;
        boolean isLocationCacheEnable;
        boolean z = true;
        this.m = aMapLocationClientOption;
        if (this.m == null) {
            this.m = new AMapLocationClientOption();
        }
        if (this.c != null) {
            ci ciVar = this.c;
            this.m.isWifiActiveScan();
            ciVar.a(this.m.isWifiScan(), this.m.isMockEnable());
        }
        if (this.s != null) {
            this.s.a(this.m.getHttpTimeOut(), this.m.getLocationProtocol().equals(AMapLocationProtocol.HTTPS));
        }
        if (this.g != null) {
            this.g.a(this.m);
        }
        try {
            isNeedAddress = this.m.isNeedAddress();
            try {
                boolean isOffset = this.m.isOffset();
                try {
                    isLocationCacheEnable = this.m.isLocationCacheEnable();
                    try {
                        this.x = this.m.isOnceLocationLatest();
                        this.F = this.m.isSensorEnable();
                        z = isNeedAddress;
                        isNeedAddress = isOffset;
                    } catch (Throwable th) {
                        z = isNeedAddress;
                        isNeedAddress = isOffset;
                    }
                } catch (Throwable th2) {
                    isLocationCacheEnable = true;
                    z = isNeedAddress;
                    isNeedAddress = isOffset;
                }
            } catch (Throwable th3) {
                isLocationCacheEnable = true;
                boolean z2 = isNeedAddress;
                isNeedAddress = true;
                z = z2;
            }
            if (!(isOffset == this.v && isNeedAddress == this.u && isLocationCacheEnable == this.w)) {
                try {
                    if (this.g != null) {
                        this.g.a();
                    }
                    b(null);
                    this.o = 0;
                    if (this.H != null) {
                        this.H.a();
                    }
                    z = isNeedAddress;
                    isNeedAddress = isOffset;
                } catch (Throwable th4) {
                    cw.a(th4, "APS", "cleanCache");
                }
                this.v = isNeedAddress;
                this.u = z;
                this.w = isLocationCacheEnable;
            }
        } catch (Throwable th5) {
            isLocationCacheEnable = true;
            isNeedAddress = true;
        }
        this.v = isNeedAddress;
        this.u = z;
        this.w = isLocationCacheEnable;
    }

    public final void a(AMapLocationServer aMapLocationServer) {
        if (de.a(aMapLocationServer)) {
            this.g.a(this.N, this.A, aMapLocationServer, this.a, true);
        }
    }

    public final void b() {
        if (this.j == null) {
            this.j = new cc(this.a);
        }
        if (this.E == null) {
            this.E = new ca(this.a);
        }
        if (this.i == null) {
            this.i = new bv(this.a);
        }
        this.e.a(this.a);
        l();
        this.c.b(false);
        this.d.a(false);
        this.g.a(this.a);
        this.h.a(this.a);
        this.i.b();
        try {
            if (this.a.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") == 0) {
                this.r = true;
            }
        } catch (Throwable th) {
        }
        this.C = true;
    }

    public final AMapLocationServer c() throws Throwable {
        Object obj = null;
        k();
        if (this.a == null) {
            this.t.append("context is null");
            return a(1, this.t.toString());
        }
        boolean z;
        Context context;
        Object obj2;
        SharedPreferences sharedPreferences;
        AMapLocationServer a;
        this.K++;
        if (this.K == 1) {
            String str;
            this.f.e();
            if (this.c != null) {
                this.c.a(this.r);
            }
            this.f.a();
            this.e.a();
            if (TextUtils.isEmpty(this.p) || this.p.equals("00:00:00:00:00:00")) {
                this.p = dd.a(this.a);
                str = null;
            } else {
                str = this.p;
            }
            this.p = str;
        }
        if (de.b() - this.o < 800) {
            if ((de.a(this.n) ? de.a() - this.n.getTime() : 0) <= 10000) {
                z = true;
                if (z || !de.a(this.n)) {
                    if (this.E != null) {
                        if (this.F) {
                            this.E.b();
                        } else {
                            this.E.a();
                        }
                    }
                    try {
                        this.d.a(false);
                    } catch (Throwable th) {
                        cw.a(th, "APS", "getLocation getCgiListParam");
                    }
                    this.c.b(true);
                    this.k = this.c.b();
                    this.N = m();
                    if (TextUtils.isEmpty(this.N)) {
                        this.e.a(this.m, this.N);
                        if ((TextUtils.isEmpty(this.p) || this.p.equals("00:00:00:00:00:00")) && this.c.g() != null) {
                            this.p = n.i(this.a);
                            context = this.a;
                            obj2 = this.p;
                            if (!M) {
                                if (!(context == null || TextUtils.isEmpty(obj2))) {
                                    sharedPreferences = context.getSharedPreferences("pref", 0);
                                    try {
                                        obj = o.a(obj2.getBytes("UTF-8"));
                                    } catch (Throwable th2) {
                                        cw.a(th2, "SPUtil", "setSmac");
                                    }
                                    if (!TextUtils.isEmpty(obj)) {
                                        sharedPreferences.edit().putString("smac", obj).commit();
                                    }
                                }
                                M = true;
                            }
                            if (TextUtils.isEmpty(this.p)) {
                                this.p = "00:00:00:00:00:00";
                            }
                        }
                        this.A = a(this.A);
                        if (this.c.h()) {
                            return a((int) ConnectionResult.INTERRUPTED, "networkLocation has been mocked!");
                        }
                        boolean z2 = this.o != 0 ? true : de.b() - this.o <= 20000;
                        a = this.g.a(this.d, z2, this.n, this.c, this.A, this.N, this.a);
                        if (de.a(a)) {
                            this.n = a(false, true);
                            if (de.a(this.n)) {
                                this.n.e("new");
                                this.g.a(this.A.toString());
                                this.g.a(this.d.c());
                                this.e.a(this.n.toJson(1));
                                this.e.c();
                            }
                        } else {
                            b(a);
                        }
                        this.g.a(this.N, this.A, this.n, this.a, true);
                        this.h.a(this.a, this.N, this.n);
                        if (!de.a(this.n)) {
                            ch chVar = this.e;
                            ck ckVar = this.g;
                            String str2 = this.N;
                            String toString = this.A.toString();
                            AMapLocationClientOption aMapLocationClientOption = this.m;
                            Context context2 = this.a;
                            this.n = chVar.a(ckVar, str2, toString, aMapLocationClientOption, m(), this.n);
                        }
                        this.A.delete(0, this.A.length());
                        if (this.F || this.E == null) {
                            this.n.setAltitude(0.0d);
                            this.n.setBearing(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                            this.n.setSpeed(AutoScrollHelper.RELATIVE_UNSPECIFIED);
                        } else {
                            this.n.setAltitude(this.E.c());
                            this.n.setBearing(this.E.d());
                            this.n.setSpeed((float) this.E.e());
                        }
                        return this.n;
                    }
                    this.n = this.i.e();
                    return this.n == null ? this.n : a(this.D, this.t.toString());
                } else {
                    if (this.w && cv.b(this.n.getTime())) {
                        this.n.setLocationType(RainSurfaceView.RAIN_LEVEL_SHOWER);
                    }
                    return this.n;
                }
            }
        }
        z = false;
        if (z) {
        }
        if (this.E != null) {
            if (this.F) {
                this.E.b();
            } else {
                this.E.a();
            }
        }
        this.d.a(false);
        try {
            this.c.b(true);
            this.k = this.c.b();
        } catch (Throwable th22) {
            cw.a(th22, "APS", "getLocation getScanResultsParam");
        }
        this.N = m();
        if (TextUtils.isEmpty(this.N)) {
            this.e.a(this.m, this.N);
            this.p = n.i(this.a);
            context = this.a;
            obj2 = this.p;
            if (M) {
                sharedPreferences = context.getSharedPreferences("pref", 0);
                obj = o.a(obj2.getBytes("UTF-8"));
                if (TextUtils.isEmpty(obj)) {
                    sharedPreferences.edit().putString("smac", obj).commit();
                }
                M = true;
            }
            if (TextUtils.isEmpty(this.p)) {
                this.p = "00:00:00:00:00:00";
            }
            this.A = a(this.A);
            if (this.c.h()) {
                return a((int) ConnectionResult.INTERRUPTED, "networkLocation has been mocked!");
            }
            if (this.o != 0) {
                if (de.b() - this.o <= 20000) {
                }
            }
            a = this.g.a(this.d, z2, this.n, this.c, this.A, this.N, this.a);
            if (de.a(a)) {
                this.n = a(false, true);
                if (de.a(this.n)) {
                    this.n.e("new");
                    this.g.a(this.A.toString());
                    this.g.a(this.d.c());
                    this.e.a(this.n.toJson(1));
                    this.e.c();
                }
            } else {
                b(a);
            }
            this.g.a(this.N, this.A, this.n, this.a, true);
            this.h.a(this.a, this.N, this.n);
            if (de.a(this.n)) {
                ch chVar2 = this.e;
                ck ckVar2 = this.g;
                String str22 = this.N;
                String toString2 = this.A.toString();
                AMapLocationClientOption aMapLocationClientOption2 = this.m;
                Context context22 = this.a;
                this.n = chVar2.a(ckVar2, str22, toString2, aMapLocationClientOption2, m(), this.n);
            }
            this.A.delete(0, this.A.length());
            if (this.F) {
            }
            this.n.setAltitude(0.0d);
            this.n.setBearing(AutoScrollHelper.RELATIVE_UNSPECIFIED);
            this.n.setSpeed(AutoScrollHelper.RELATIVE_UNSPECIFIED);
            return this.n;
        }
        this.n = this.i.e();
        if (this.n == null) {
        }
    }

    public final void d() {
        try {
            a(this.a);
            a(this.m);
            Context context = this.a;
            h();
            a(a(true, true));
        } catch (Throwable th) {
            cw.a(th, "APS", "doFusionLocation");
        }
    }

    @SuppressLint({"NewApi"})
    public final void e() {
        this.I = null;
        this.B = false;
        this.C = false;
        if (this.f != null) {
            this.f.b();
        }
        if (this.i != null) {
            this.i.a();
        }
        if (this.g != null) {
            this.g.b(this.a);
        }
        if (this.H != null) {
            this.H.a();
        }
        de.g();
        try {
            if (!(this.a == null || this.l == null)) {
                this.a.unregisterReceiver(this.l);
            }
            this.l = null;
        } catch (Throwable th) {
            cw.a(th, "APS", "destroy");
            this.l = null;
        }
        if (this.d != null) {
            this.d.g();
        }
        if (this.h != null) {
            this.h.a();
        }
        if (this.c != null) {
            this.c.j();
        }
        if (this.k != null) {
            this.k.clear();
        }
        if (this.E != null) {
            this.E.f();
        }
        this.n = null;
        this.a = null;
        if (this.e != null) {
            this.e.b();
        }
        this.A = null;
        if (this.j != null) {
            this.j.d();
        }
    }

    public final void f() {
        try {
            if (this.i != null) {
                this.i.c();
            }
        } catch (Throwable th) {
            cw.a(th, "APS", "bindAMapService");
        }
    }

    public final void g() {
        try {
            if (this.i != null) {
                this.i.d();
            }
        } catch (Throwable th) {
            cw.a(th, "APS", "bindOtherService");
        }
    }

    public final void h() {
        try {
            if (!this.B) {
                if (this.N != null) {
                    this.N = null;
                }
                if (this.A != null) {
                    this.A.delete(0, this.A.length());
                }
                if (this.x) {
                    l();
                }
                this.d.a(true);
                this.c.b(this.x);
                this.k = this.c.b();
                this.N = m();
                if (!TextUtils.isEmpty(this.N)) {
                    this.A = a(this.A);
                }
                this.B = true;
            }
        } catch (Throwable th) {
            cw.a(th, "APS", "initFirstLocateParam");
        }
    }

    public final AMapLocationServer i() {
        k();
        if (this.c.h()) {
            return a((int) ConnectionResult.INTERRUPTED, "networkLocation has been mocked!");
        }
        if (TextUtils.isEmpty(this.N)) {
            return a(this.D, this.t.toString());
        }
        AMapLocationServer a = this.g.a(this.a, this.N, this.A, true);
        if (!de.a(a)) {
            return a;
        }
        b(a);
        return a;
    }

    public final void j() {
        this.f.a(this.a, this.s, this.c, this.m, this.b);
    }
}
