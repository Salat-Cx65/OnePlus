package com.loc;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.loc.l.a.b;
import com.loc.l.a.c;
import com.loc.l.a.d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.oneplus.weather.R;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainDownpour;
import org.json.JSONArray;
import org.json.JSONObject;

// compiled from: AuthUtil.java
public final class cv {
    private static int A;
    private static boolean B;
    private static boolean C;
    private static boolean D;
    private static int E;
    private static long F;
    private static ArrayList<String> G;
    private static boolean H;
    private static int I;
    private static long J;
    private static ArrayList<String> K;
    private static String L;
    private static String M;
    private static boolean N;
    private static boolean O;
    private static int P;
    private static int Q;
    private static boolean R;
    private static long S;
    private static int T;
    private static boolean U;
    private static boolean V;
    private static boolean W;
    private static boolean X;
    private static List<cy> Y;
    private static boolean Z;
    static boolean a;
    private static long aa;
    private static int ab;
    private static int ac;
    private static List<String> ad;
    private static boolean ae;
    private static int af;
    private static boolean ag;
    static boolean b;
    static int c;
    static int d;
    static long e;
    static long f;
    private static String g;
    private static String h;
    private static String i;
    private static String j;
    private static String k;
    private static String l;
    private static boolean m;
    private static long n;
    private static long o;
    private static long p;
    private static boolean q;
    private static int r;
    private static boolean s;
    private static int t;
    private static boolean u;
    private static int v;
    private static int w;
    private static int x;
    private static boolean y;
    private static int z;

    // compiled from: AuthUtil.java
    static class a {
        boolean a;
        String b;
        boolean c;
        int d;

        a() {
            this.a = false;
            this.b = "0";
            this.c = false;
            this.d = 5;
        }
    }

    static {
        g = "\u63d0\u793a\u4fe1\u606f";
        h = "\u786e\u8ba4";
        i = "\u53d6\u6d88";
        j = StringUtils.EMPTY_STRING;
        k = StringUtils.EMPTY_STRING;
        l = StringUtils.EMPTY_STRING;
        m = false;
        n = 0;
        o = 0;
        p = 5000;
        q = false;
        r = 0;
        s = false;
        t = 0;
        u = false;
        a = true;
        v = 3600000;
        w = 0;
        x = 0;
        y = true;
        z = 1000;
        A = 200;
        B = false;
        C = true;
        D = true;
        E = -1;
        F = 0;
        G = new ArrayList();
        H = true;
        I = -1;
        J = 0;
        K = new ArrayList();
        N = false;
        O = false;
        P = 3000;
        Q = 3000;
        R = true;
        S = 300000;
        T = -1;
        U = false;
        V = false;
        W = false;
        X = false;
        b = false;
        Y = new ArrayList();
        Z = false;
        aa = 0;
        ab = 0;
        ac = 0;
        ad = new ArrayList();
        ae = true;
        af = 80;
        c = 1800000;
        d = 3600000;
        ag = false;
        e = 0;
        f = 0;
    }

    public static boolean A() {
        return ag;
    }

    private static a a(JSONObject jSONObject, String str) {
        a aVar;
        Throwable th;
        if (jSONObject != null) {
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject(str);
                if (jSONObject2 != null) {
                    aVar = new a();
                    try {
                        aVar.a = l.a(jSONObject2.optString("b"), false);
                        aVar.b = jSONObject2.optString("t");
                        aVar.c = l.a(jSONObject2.optString("st"), false);
                        aVar.d = jSONObject2.optInt("i", 0);
                        return aVar;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                aVar = null;
                th = th4;
                cw.a(th, "AuthUtil", "getLocateObj");
                return aVar;
            }
        }
        return null;
    }

    public static boolean a() {
        return q;
    }

    public static boolean a(long j) {
        long b = de.b();
        return m && b - o <= n && b - j >= p;
    }

    public static boolean a(Context context) {
        boolean a;
        C = true;
        try {
            com.loc.l.a a2 = l.a(context, cw.b(), cw.c(context));
            if (a2 != null) {
                T = a2.b;
                a = a(context, a2);
            } else {
                a = false;
            }
            try {
                de.a(context, T);
            } catch (Throwable th) {
                Throwable th2 = th;
                cw.a(th2, "AuthUtil", "getConfig");
                f = de.b();
                e = de.b();
                return a;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            a = false;
            th2 = th4;
            cw.a(th2, "AuthUtil", "getConfig");
            f = de.b();
            e = de.b();
            return a;
        }
        f = de.b();
        e = de.b();
        return a;
    }

    public static boolean a(Context context, long j) {
        if (!O) {
            return false;
        }
        long a = de.a();
        if (a - j < ((long) P)) {
            return false;
        }
        if (Q == -1) {
            return true;
        }
        if (de.c(a, dd.b(context, "pref", "ngpsTime", 0))) {
            int b = dd.b(context, "pref", "ngpsCount", 0);
            if (b >= Q) {
                return false;
            }
            dd.a(context, "pref", "ngpsCount", b + 1);
            return true;
        }
        try {
            Editor edit = context.getSharedPreferences("pref", 0).edit();
            edit.putLong("ngpsTime", a);
            edit.putInt("ngpsCount", 0);
            dd.a(edit);
        } catch (Throwable th) {
            cw.a(th, "AuthUtil", "resetPrefsNGPS");
        }
        dd.a(context, "pref", "ngpsCount", 1);
        return true;
    }

    private static boolean a(Context context, b bVar, String str, String str2) {
        boolean z = false;
        if (bVar != null) {
            try {
                z = bVar.a;
                Object obj = bVar.b;
                Object obj2 = bVar.c;
                CharSequence charSequence = bVar.d;
                if (!(!z || TextUtils.isEmpty(obj2) || TextUtils.isEmpty(obj) || TextUtils.isEmpty(charSequence))) {
                    au.a(context, new at(obj, obj2), cw.a(str, str2));
                }
            } catch (Throwable th) {
                cw.a(th, "AuthUtil", "downLoadPluginDex");
            }
        }
        return z;
    }

    private static boolean a(Context context, com.loc.l.a aVar) {
        boolean a;
        int i;
        try {
            JSONArray optJSONArray;
            JSONObject jSONObject = aVar.h;
            if (jSONObject != null) {
                a = l.a(jSONObject.optString("callamapflag"), true);
                D = a;
                if (a) {
                    E = jSONObject.optInt("count", E);
                    F = jSONObject.optLong("sysTime", F);
                    optJSONArray = jSONObject.optJSONArray("sn");
                    if (optJSONArray != null && optJSONArray.length() > 0) {
                        for (i = 0; i < optJSONArray.length(); i++) {
                            G.add(optJSONArray.getString(i));
                        }
                    }
                    if (!(E == -1 || F == 0)) {
                        if (!de.b(F, dd.b(context, "pref", "nowtime", 0))) {
                            i(context);
                        }
                    }
                }
            }
        } catch (Throwable th) {
            cw.a(th, "AuthUtil", "loadConfigData_callAMapSer");
        }
        try {
            jSONObject = aVar.k;
            if (jSONObject != null) {
                a = l.a(jSONObject.optString("amappushflag"), false);
                H = a;
                if (a) {
                    I = jSONObject.optInt("count", I);
                    J = jSONObject.optLong("sysTime", J);
                    optJSONArray = jSONObject.optJSONArray("sn");
                    if (optJSONArray != null && optJSONArray.length() > 0) {
                        for (i = 0; i < optJSONArray.length(); i++) {
                            K.add(optJSONArray.getString(i));
                        }
                    }
                    if (!(I == -1 || J == 0)) {
                        if (!de.b(J, dd.b(context, "pref", "pushSerTime", 0))) {
                            j(context);
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            cw.a(th2, "AuthUtil", "loadConfigData_callAMapPush");
        }
        try {
            jSONObject = aVar.l;
            if (jSONObject != null) {
                int optInt;
                N = l.a(jSONObject.optString("f"), false);
                w = jSONObject.optInt("mco", 0);
                x = jSONObject.optInt("co", 0);
                optInt = jSONObject.optInt("it", 3600) * 1000;
                v = optInt;
                if (optInt < 3600000) {
                    v = 3600000;
                }
                g = jSONObject.optString("a", "\u63d0\u793a\u4fe1\u606f");
                h = jSONObject.optString("o", "\u786e\u8ba4");
                i = jSONObject.optString("c", "\u53d6\u6d88");
                j = jSONObject.optString("i", StringUtils.EMPTY_STRING);
                k = jSONObject.optString("u", StringUtils.EMPTY_STRING);
                l = jSONObject.optString("t", StringUtils.EMPTY_STRING);
                if (((TextUtils.isEmpty(j) || "null".equals(j)) && (TextUtils.isEmpty(k) || "null".equals(k))) || x > w) {
                    N = false;
                }
            }
        } catch (Throwable th22) {
            cw.a(th22, "AuthUtil", "loadConfigData_openAMap");
        }
        try {
            s b = cw.b();
            d dVar = aVar.u;
            c cVar;
            com.loc.l.a.a aVar2;
            JSONObject jSONObject2;
            a a2;
            b bVar;
            boolean a3;
            JSONObject jSONObject3;
            JSONArray optJSONArray2;
            JSONObject optJSONObject;
            cy cyVar;
            JSONArray optJSONArray3;
            List arrayList;
            JSONObject optJSONObject2;
            Map hashMap;
            CharSequence optString;
            if (dVar != null) {
                Object obj = dVar.b;
                Object obj2 = dVar.a;
                CharSequence charSequence = dVar.c;
                if (TextUtils.isEmpty(obj) || TextUtils.isEmpty(obj2) || TextUtils.isEmpty(charSequence)) {
                    au.a(context, null, b);
                    try {
                        cVar = aVar.v;
                        if (cVar != null) {
                            L = cVar.a;
                            M = cVar.b;
                            if (!(TextUtils.isEmpty(L) || TextUtils.isEmpty(M))) {
                                new r(context, "loc", L, M).a();
                            }
                        }
                    } catch (Throwable th222) {
                        cw.a(th222, "AuthUtil", "loadConfigData_groupOffset");
                    }
                    aVar2 = aVar.t;
                    if (aVar2 != null) {
                        y = aVar2.a;
                        dd.a(context, "pref", "exception", y);
                        jSONObject = aVar2.c;
                        z = jSONObject.optInt("fn", z);
                        optInt = jSONObject.optInt("mpn", A);
                        A = optInt;
                        if (optInt > 500) {
                            A = 500;
                        }
                        if (A < 30) {
                            A = 30;
                        }
                        B = l.a(jSONObject.optString("igu"), false);
                        bq.a(z, B);
                        dd.a(context, "pref", "fn", z);
                        dd.a(context, "pref", "mpn", A);
                        dd.a(context, "pref", "igu", B);
                    }
                    jSONObject2 = aVar.m;
                    if (jSONObject2 != null && l.a(jSONObject2.optString("able"), false)) {
                        a2 = a(jSONObject2, "fs");
                        if (a2 != null) {
                            q = a2.c;
                            try {
                                r = Integer.parseInt(a2.b);
                            } catch (Throwable th2222) {
                                cw.a(th2222, "AuthUtil", "loadconfig part2");
                            }
                        }
                        a2 = a(jSONObject2, "us");
                        if (a2 != null) {
                            s = a2.c;
                            u = a2.a;
                            try {
                                t = Integer.parseInt(a2.b);
                            } catch (Throwable th22222) {
                                cw.a(th22222, "AuthUtil", "loadconfig part1");
                            }
                            if (t < 2) {
                                s = false;
                            }
                        }
                        a2 = a(jSONObject2, "rs");
                        if (a2 != null) {
                            a = a2.c;
                            m = a;
                            if (a) {
                                o = de.b();
                                p = (long) (a2.d * 1000);
                            }
                            try {
                                n = (long) (Integer.parseInt(a2.b) * 1000);
                            } catch (Throwable th222222) {
                                cw.a(th222222, "AuthUtil", "loadconfig part");
                            }
                        }
                    }
                    jSONObject = aVar.o;
                    if (jSONObject != null) {
                        a = l.a(jSONObject.optString("able"), false);
                        O = a;
                        if (a) {
                            optInt = jSONObject.optInt("c", 0);
                            if (optInt == 0) {
                                P = 3000;
                            } else {
                                P = optInt * 1000;
                            }
                            Q = jSONObject.getInt("t") / 2;
                        }
                    }
                    try {
                        jSONObject = aVar.p;
                        if (jSONObject != null) {
                            a = l.a(jSONObject.optString("able"), true);
                            R = a;
                            if (a) {
                                S = (long) (jSONObject.optInt("c", 300) * 1000);
                            }
                            dd.a(context, "pref", "ca", R);
                            dd.a(context, "pref", "ct", S);
                        }
                    } catch (Throwable th2222222) {
                        cw.a(th2222222, "AuthUtil", "loadConfigData_cacheAble");
                    }
                    bVar = aVar.w;
                    if (bVar != null) {
                        U = a(context, bVar, "Collection", "1.0.0");
                    }
                    bVar = aVar.y;
                    if (bVar != null) {
                        a3 = a(context, bVar, "HttpDNS", "1.0.0");
                        W = a3;
                        if (!a3 && db.a(context, cw.a("HttpDNS", "1.0.0"))) {
                            dc.a(context, "HttpDNS", "config|get dnsDex able is false");
                        }
                    }
                    try {
                        jSONObject3 = aVar.j;
                        if (jSONObject3 != null) {
                            Z = l.a(jSONObject3.optString("able"), false);
                            aa = jSONObject3.optLong("sysTime", de.a());
                            ab = jSONObject3.optInt("n", 1);
                            ac = jSONObject3.optInt("nh", 1);
                            if (Z) {
                                if (ab == -1 || ab >= ac) {
                                    optJSONArray2 = jSONObject3.optJSONArray("l");
                                    for (i = 0; i < optJSONArray2.length(); i++) {
                                        try {
                                            optJSONObject = optJSONArray2.optJSONObject(i);
                                            cyVar = new cy();
                                            a = l.a(optJSONObject.optString("able", "false"), false);
                                            cyVar.a = a;
                                            if (!a) {
                                                cyVar.b = optJSONObject.optString("pn", StringUtils.EMPTY_STRING);
                                                cyVar.c = optJSONObject.optString("cn", StringUtils.EMPTY_STRING);
                                                cyVar.e = optJSONObject.optString("a", StringUtils.EMPTY_STRING);
                                                optJSONArray3 = optJSONObject.optJSONArray("b");
                                                if (optJSONArray3 != null) {
                                                    arrayList = new ArrayList();
                                                    for (optInt = 0; optInt < optJSONArray3.length(); optInt++) {
                                                        optJSONObject2 = optJSONArray3.optJSONObject(optInt);
                                                        hashMap = new HashMap();
                                                        try {
                                                            hashMap.put(optJSONObject2.optString("k"), optJSONObject2.optString("v"));
                                                            arrayList.add(hashMap);
                                                        } catch (Throwable th3) {
                                                        }
                                                    }
                                                    cyVar.d = arrayList;
                                                }
                                                cyVar.f = l.a(optJSONObject.optString("is", "false"), false);
                                                Y.add(cyVar);
                                            }
                                        } catch (Throwable th4) {
                                        }
                                    }
                                    optJSONArray = jSONObject3.optJSONArray("sl");
                                    if (optJSONArray != null) {
                                        for (i = 0; i < optJSONArray.length(); i++) {
                                            optString = optJSONArray.optJSONObject(i).optString("pan");
                                            if (TextUtils.isEmpty(optString)) {
                                                ad.add(optString);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Throwable th22222222) {
                        cw.a(th22222222, "AuthUtil", "loadConfigData_otherServiceList");
                    }
                    try {
                        jSONObject = aVar.i;
                        if (jSONObject != null) {
                            a = l.a(jSONObject.optString("able"), true);
                            ae = a;
                            if (a) {
                                af = jSONObject.optInt("c", af);
                            }
                        }
                    } catch (Throwable th222222222) {
                        cw.a(th222222222, "AuthUtil", "loadConfigData_gpsGeoAble");
                    }
                    jSONObject2 = aVar.g;
                    if (jSONObject2 != null) {
                        return true;
                    }
                    try {
                        c = (jSONObject2.optInt("ht", RainDownpour.Z_RANDOM_RANGE) * 60) * 1000;
                        d = (jSONObject2.optInt("at", R.styleable.AppCompatTheme_dialogTheme) * 60) * 1000;
                    } catch (Throwable th2222222222) {
                        cw.a(th2222222222, "AuthUtil", "requestSdkAuthInterval");
                    }
                    a3 = l.a(jSONObject2.optString("ofl"), true);
                    a = a3;
                    bw.a = a3;
                    if (a) {
                        try {
                            bVar = aVar.x;
                            if (bVar != null) {
                                V = a(context, bVar, "OfflineLocation", "1.0.0");
                                dd.a(context, "pref", "oAble", V);
                            }
                        } catch (Throwable th22222222222) {
                            cw.a(th22222222222, "AuthUtil", "loadConfigData_OfflineDex");
                        }
                    }
                    ag = l.a(jSONObject2.optString("ila"), ag);
                    try {
                        a3 = l.a(jSONObject2.optString("icbd"), b);
                        b = a3;
                        if (a3) {
                            return true;
                        }
                        au.a(context, "loc");
                        return true;
                    } catch (Throwable th222222222222) {
                        cw.a(th222222222222, "AuthUtil", "loadConfigData_CallBackDex");
                        return true;
                    }
                }
                au.a(context, new at(obj2, obj), b);
                cVar = aVar.v;
                if (cVar != null) {
                    L = cVar.a;
                    M = cVar.b;
                    new r(context, "loc", L, M).a();
                }
                aVar2 = aVar.t;
                if (aVar2 != null) {
                    y = aVar2.a;
                    dd.a(context, "pref", "exception", y);
                    jSONObject = aVar2.c;
                    z = jSONObject.optInt("fn", z);
                    optInt = jSONObject.optInt("mpn", A);
                    A = optInt;
                    if (optInt > 500) {
                        A = 500;
                    }
                    if (A < 30) {
                        A = 30;
                    }
                    B = l.a(jSONObject.optString("igu"), false);
                    bq.a(z, B);
                    dd.a(context, "pref", "fn", z);
                    dd.a(context, "pref", "mpn", A);
                    dd.a(context, "pref", "igu", B);
                }
                jSONObject2 = aVar.m;
                a2 = a(jSONObject2, "fs");
                if (a2 != null) {
                    q = a2.c;
                    r = Integer.parseInt(a2.b);
                }
                a2 = a(jSONObject2, "us");
                if (a2 != null) {
                    s = a2.c;
                    u = a2.a;
                    t = Integer.parseInt(a2.b);
                    if (t < 2) {
                        s = false;
                    }
                }
                a2 = a(jSONObject2, "rs");
                if (a2 != null) {
                    a = a2.c;
                    m = a;
                    if (a) {
                        o = de.b();
                        p = (long) (a2.d * 1000);
                    }
                    n = (long) (Integer.parseInt(a2.b) * 1000);
                }
                try {
                    jSONObject = aVar.o;
                    if (jSONObject != null) {
                        a = l.a(jSONObject.optString("able"), false);
                        O = a;
                        if (a) {
                            optInt = jSONObject.optInt("c", 0);
                            if (optInt == 0) {
                                P = optInt * 1000;
                            } else {
                                P = 3000;
                            }
                            Q = jSONObject.getInt("t") / 2;
                        }
                    }
                } catch (Throwable th2222222222222) {
                    cw.a(th2222222222222, "AuthUtil", "loadConfigData_ngps");
                }
                jSONObject = aVar.p;
                if (jSONObject != null) {
                    a = l.a(jSONObject.optString("able"), true);
                    R = a;
                    if (a) {
                        S = (long) (jSONObject.optInt("c", 300) * 1000);
                    }
                    dd.a(context, "pref", "ca", R);
                    dd.a(context, "pref", "ct", S);
                }
                bVar = aVar.w;
                if (bVar != null) {
                    U = a(context, bVar, "Collection", "1.0.0");
                }
                try {
                    bVar = aVar.y;
                    if (bVar != null) {
                        a3 = a(context, bVar, "HttpDNS", "1.0.0");
                        W = a3;
                        dc.a(context, "HttpDNS", "config|get dnsDex able is false");
                    }
                } catch (Throwable th22222222222222) {
                    cw.a(th22222222222222, "AuthUtil", "loadConfigData_dnsDex");
                }
                jSONObject3 = aVar.j;
                if (jSONObject3 != null) {
                    Z = l.a(jSONObject3.optString("able"), false);
                    aa = jSONObject3.optLong("sysTime", de.a());
                    ab = jSONObject3.optInt("n", 1);
                    ac = jSONObject3.optInt("nh", 1);
                    if (Z) {
                        optJSONArray2 = jSONObject3.optJSONArray("l");
                        for (i = 0; i < optJSONArray2.length(); i++) {
                            optJSONObject = optJSONArray2.optJSONObject(i);
                            cyVar = new cy();
                            a = l.a(optJSONObject.optString("able", "false"), false);
                            cyVar.a = a;
                            if (!a) {
                                cyVar.b = optJSONObject.optString("pn", StringUtils.EMPTY_STRING);
                                cyVar.c = optJSONObject.optString("cn", StringUtils.EMPTY_STRING);
                                cyVar.e = optJSONObject.optString("a", StringUtils.EMPTY_STRING);
                                optJSONArray3 = optJSONObject.optJSONArray("b");
                                if (optJSONArray3 != null) {
                                    arrayList = new ArrayList();
                                    for (optInt = 0; optInt < optJSONArray3.length(); optInt++) {
                                        optJSONObject2 = optJSONArray3.optJSONObject(optInt);
                                        hashMap = new HashMap();
                                        hashMap.put(optJSONObject2.optString("k"), optJSONObject2.optString("v"));
                                        arrayList.add(hashMap);
                                    }
                                    cyVar.d = arrayList;
                                }
                                cyVar.f = l.a(optJSONObject.optString("is", "false"), false);
                                Y.add(cyVar);
                            }
                        }
                        optJSONArray = jSONObject3.optJSONArray("sl");
                        if (optJSONArray != null) {
                            for (i = 0; i < optJSONArray.length(); i++) {
                                optString = optJSONArray.optJSONObject(i).optString("pan");
                                if (TextUtils.isEmpty(optString)) {
                                    ad.add(optString);
                                }
                            }
                        }
                    }
                }
                jSONObject = aVar.i;
                if (jSONObject != null) {
                    a = l.a(jSONObject.optString("able"), true);
                    ae = a;
                    if (a) {
                        af = jSONObject.optInt("c", af);
                    }
                }
                try {
                    jSONObject2 = aVar.g;
                    if (jSONObject2 != null) {
                        return true;
                    }
                    c = (jSONObject2.optInt("ht", RainDownpour.Z_RANDOM_RANGE) * 60) * 1000;
                    d = (jSONObject2.optInt("at", R.styleable.AppCompatTheme_dialogTheme) * 60) * 1000;
                    try {
                        a3 = l.a(jSONObject2.optString("ofl"), true);
                        a = a3;
                        bw.a = a3;
                        if (a) {
                            bVar = aVar.x;
                            if (bVar != null) {
                                V = a(context, bVar, "OfflineLocation", "1.0.0");
                                dd.a(context, "pref", "oAble", V);
                            }
                        }
                    } catch (Throwable th222222222222222) {
                        cw.a(th222222222222222, "AuthUtil", "loadConfigData_offlineLoc");
                    }
                    ag = l.a(jSONObject2.optString("ila"), ag);
                    a3 = l.a(jSONObject2.optString("icbd"), b);
                    b = a3;
                    if (a3) {
                        return true;
                    }
                    au.a(context, "loc");
                    return true;
                } catch (Throwable th2222222222222222) {
                    cw.a(th2222222222222222, "AuthUtil", "loadConfigData_hotUpdate");
                    return true;
                }
            }
            au.a(context, null, b);
            cVar = aVar.v;
            if (cVar != null) {
                L = cVar.a;
                M = cVar.b;
                new r(context, "loc", L, M).a();
            }
            try {
                aVar2 = aVar.t;
                if (aVar2 != null) {
                    y = aVar2.a;
                    dd.a(context, "pref", "exception", y);
                    jSONObject = aVar2.c;
                    z = jSONObject.optInt("fn", z);
                    optInt = jSONObject.optInt("mpn", A);
                    A = optInt;
                    if (optInt > 500) {
                        A = 500;
                    }
                    if (A < 30) {
                        A = 30;
                    }
                    B = l.a(jSONObject.optString("igu"), false);
                    bq.a(z, B);
                    dd.a(context, "pref", "fn", z);
                    dd.a(context, "pref", "mpn", A);
                    dd.a(context, "pref", "igu", B);
                }
            } catch (Throwable th22222222222222222) {
                cw.a(th22222222222222222, "AuthUtil", "loadConfigData_uploadException");
            }
            try {
                jSONObject2 = aVar.m;
                a2 = a(jSONObject2, "fs");
                if (a2 != null) {
                    q = a2.c;
                    r = Integer.parseInt(a2.b);
                }
                a2 = a(jSONObject2, "us");
                if (a2 != null) {
                    s = a2.c;
                    u = a2.a;
                    t = Integer.parseInt(a2.b);
                    if (t < 2) {
                        s = false;
                    }
                }
                a2 = a(jSONObject2, "rs");
                if (a2 != null) {
                    a = a2.c;
                    m = a;
                    if (a) {
                        o = de.b();
                        p = (long) (a2.d * 1000);
                    }
                    n = (long) (Integer.parseInt(a2.b) * 1000);
                }
            } catch (Throwable th222222222222222222) {
                cw.a(th222222222222222222, "AuthUtil", "loadConfigData_locate");
            }
            jSONObject = aVar.o;
            if (jSONObject != null) {
                a = l.a(jSONObject.optString("able"), false);
                O = a;
                if (a) {
                    optInt = jSONObject.optInt("c", 0);
                    if (optInt == 0) {
                        P = 3000;
                    } else {
                        P = optInt * 1000;
                    }
                    Q = jSONObject.getInt("t") / 2;
                }
            }
            jSONObject = aVar.p;
            if (jSONObject != null) {
                a = l.a(jSONObject.optString("able"), true);
                R = a;
                if (a) {
                    S = (long) (jSONObject.optInt("c", 300) * 1000);
                }
                dd.a(context, "pref", "ca", R);
                dd.a(context, "pref", "ct", S);
            }
            try {
                bVar = aVar.w;
                if (bVar != null) {
                    U = a(context, bVar, "Collection", "1.0.0");
                }
            } catch (Throwable th2222222222222222222) {
                cw.a(th2222222222222222222, "AuthUtil", "loadConfigData_CollectorDex");
            }
            bVar = aVar.y;
            if (bVar != null) {
                a3 = a(context, bVar, "HttpDNS", "1.0.0");
                W = a3;
                dc.a(context, "HttpDNS", "config|get dnsDex able is false");
            }
            jSONObject3 = aVar.j;
            if (jSONObject3 != null) {
                Z = l.a(jSONObject3.optString("able"), false);
                aa = jSONObject3.optLong("sysTime", de.a());
                ab = jSONObject3.optInt("n", 1);
                ac = jSONObject3.optInt("nh", 1);
                if (Z) {
                    optJSONArray2 = jSONObject3.optJSONArray("l");
                    for (i = 0; i < optJSONArray2.length(); i++) {
                        optJSONObject = optJSONArray2.optJSONObject(i);
                        cyVar = new cy();
                        a = l.a(optJSONObject.optString("able", "false"), false);
                        cyVar.a = a;
                        if (!a) {
                            cyVar.b = optJSONObject.optString("pn", StringUtils.EMPTY_STRING);
                            cyVar.c = optJSONObject.optString("cn", StringUtils.EMPTY_STRING);
                            cyVar.e = optJSONObject.optString("a", StringUtils.EMPTY_STRING);
                            optJSONArray3 = optJSONObject.optJSONArray("b");
                            if (optJSONArray3 != null) {
                                arrayList = new ArrayList();
                                for (optInt = 0; optInt < optJSONArray3.length(); optInt++) {
                                    optJSONObject2 = optJSONArray3.optJSONObject(optInt);
                                    hashMap = new HashMap();
                                    hashMap.put(optJSONObject2.optString("k"), optJSONObject2.optString("v"));
                                    arrayList.add(hashMap);
                                }
                                cyVar.d = arrayList;
                            }
                            cyVar.f = l.a(optJSONObject.optString("is", "false"), false);
                            Y.add(cyVar);
                        }
                    }
                    optJSONArray = jSONObject3.optJSONArray("sl");
                    if (optJSONArray != null) {
                        for (i = 0; i < optJSONArray.length(); i++) {
                            optString = optJSONArray.optJSONObject(i).optString("pan");
                            if (TextUtils.isEmpty(optString)) {
                                ad.add(optString);
                            }
                        }
                    }
                }
            }
            jSONObject = aVar.i;
            if (jSONObject != null) {
                a = l.a(jSONObject.optString("able"), true);
                ae = a;
                if (a) {
                    af = jSONObject.optInt("c", af);
                }
            }
            jSONObject2 = aVar.g;
            if (jSONObject2 != null) {
                return true;
            }
            c = (jSONObject2.optInt("ht", RainDownpour.Z_RANDOM_RANGE) * 60) * 1000;
            d = (jSONObject2.optInt("at", R.styleable.AppCompatTheme_dialogTheme) * 60) * 1000;
            a3 = l.a(jSONObject2.optString("ofl"), true);
            a = a3;
            bw.a = a3;
            if (a) {
                bVar = aVar.x;
                if (bVar != null) {
                    V = a(context, bVar, "OfflineLocation", "1.0.0");
                    dd.a(context, "pref", "oAble", V);
                }
            }
            try {
                ag = l.a(jSONObject2.optString("ila"), ag);
            } catch (Throwable th22222222222222222222) {
                cw.a(th22222222222222222222, "AuthUtil", "loadConfigData_indoor");
            }
            a3 = l.a(jSONObject2.optString("icbd"), b);
            b = a3;
            if (a3) {
                return true;
            }
            au.a(context, "loc");
            return true;
        } catch (Throwable th222222222222222222222) {
            cw.a(th222222222222222222222, "AuthUtil", "loadConfigData_sdkUpdate");
        }
    }

    public static int b() {
        return r;
    }

    public static boolean b(long j) {
        if (!R) {
            return false;
        }
        return S < 0 || de.a() - j < S;
    }

    public static boolean b(Context context) {
        if (!D) {
            return false;
        }
        if (E == -1 || F == 0) {
            return true;
        }
        if (de.b(F, dd.b(context, "pref", "nowtime", 0))) {
            int b = dd.b(context, "pref", "count", 0);
            if (b >= E) {
                return false;
            }
            dd.a(context, "pref", "count", b + 1);
            return true;
        }
        i(context);
        dd.a(context, "pref", "count", 1);
        return true;
    }

    public static boolean c() {
        return s;
    }

    public static boolean c(Context context) {
        if (!H) {
            return false;
        }
        if (I == -1 || J == 0) {
            return true;
        }
        if (de.b(J, dd.b(context, "pref", "pushSerTime", 0))) {
            int b = dd.b(context, "pref", "pushCount", 0);
            if (b >= I) {
                return false;
            }
            dd.a(context, "pref", "pushCount", b + 1);
            return true;
        }
        j(context);
        dd.a(context, "pref", "pushCount", 1);
        return true;
    }

    public static int d() {
        return t;
    }

    public static boolean d(Context context) {
        if (!N || x <= 0 || w <= 0 || x > w) {
            return false;
        }
        long b = dd.b(context, "abcd", "lct", 0);
        long b2 = dd.b(context, "abcd", "lst", 0);
        long b3 = de.b();
        if (b3 < b) {
            dd.a(context, "abcd", "lct", b3);
            return false;
        }
        if (b3 - b > 86400000) {
            dd.a(context, "abcd", "lct", b3);
            dd.a(context, "abcd", "t", 0);
        }
        if (b3 - b2 < ((long) v)) {
            return false;
        }
        int b4 = dd.b(context, "abcd", "t", 0) + 1;
        if (b4 > w) {
            return false;
        }
        dd.a(context, "abcd", "lst", b3);
        dd.a(context, "abcd", "t", b4);
        return true;
    }

    public static void e(Context context) {
        try {
            y = dd.b(context, "pref", "exception", true);
            f(context);
        } catch (Throwable th) {
            cw.a(th, "AuthUtil", "loadLastAbleState p1");
        }
        try {
            V = dd.b(context, "pref", "oAble", false);
        } catch (Throwable th2) {
            cw.a(th2, "AuthUtil", "loadLastAbleState p2");
        }
        try {
            z = dd.b(context, "pref", "fn", z);
            A = dd.b(context, "pref", "mpn", A);
            B = dd.b(context, "pref", "igu", B);
            bq.a(z, B);
        } catch (Throwable th3) {
        }
        try {
            R = dd.b(context, "pref", "ca", R);
            S = dd.b(context, "pref", "ct", S);
        } catch (Throwable th4) {
        }
    }

    public static boolean e() {
        return u;
    }

    public static void f(Context context) {
        try {
            s b = cw.b();
            b.a(y);
            z.a(context, b);
        } catch (Throwable th) {
        }
    }

    public static boolean f() {
        bw.a = a;
        return a;
    }

    public static String g() {
        return g;
    }

    public static boolean g(Context context) {
        if (!Z || ab == 0 || ac == 0 || aa == 0 || (ab != -1 && ab < ac)) {
            return false;
        }
        if (ad != null && ad.size() > 0) {
            for (String str : ad) {
                if (de.b(context, str)) {
                    return false;
                }
            }
        }
        if (ab == -1 && ac == -1) {
            return true;
        }
        long b = dd.b(context, "pref", "ots", 0);
        long b2 = dd.b(context, "pref", "otsh", 0);
        int b3 = dd.b(context, "pref", "otn", 0);
        int b4 = dd.b(context, "pref", "otnh", 0);
        if (ab != -1) {
            if (!de.b(aa, b)) {
                try {
                    Editor edit = context.getSharedPreferences("pref", 0).edit();
                    edit.putLong("ots", aa);
                    edit.putLong("otsh", aa);
                    edit.putInt("otn", 0);
                    edit.putInt("otnh", 0);
                    dd.a(edit);
                } catch (Throwable th) {
                    cw.a(th, "AuthUtil", "resetPrefsBind");
                }
                dd.a(context, "pref", "otn", 1);
                dd.a(context, "pref", "otnh", 1);
                return true;
            } else if (b3 < ab) {
                if (ac == -1) {
                    dd.a(context, "pref", "otn", b3 + 1);
                    dd.a(context, "pref", "otnh", 0);
                    return true;
                } else if (!de.a(aa, b2)) {
                    dd.a(context, "pref", "otsh", aa);
                    dd.a(context, "pref", "otn", b3 + 1);
                    dd.a(context, "pref", "otnh", 1);
                    return true;
                } else if (b4 < ac) {
                    int i = b4 + 1;
                    dd.a(context, "pref", "otn", b3 + 1);
                    dd.a(context, "pref", "otnh", i);
                    return true;
                }
            }
        }
        if (ab == -1) {
            dd.a(context, "pref", "otn", 0);
            if (ac == -1) {
                dd.a(context, "pref", "otnh", 0);
                return true;
            } else if (!de.a(aa, b2)) {
                dd.a(context, "pref", "otsh", aa);
                dd.a(context, "pref", "otnh", 1);
                return true;
            } else if (b4 < ac) {
                dd.a(context, "pref", "otnh", b4 + 1);
                return true;
            }
        }
        return false;
    }

    public static String h() {
        return h;
    }

    public static boolean h(Context context) {
        if (context == null) {
            return false;
        }
        try {
            if (de.b() - e >= ((long) c)) {
                e = de.b();
                if (de.e(context) > T) {
                    return true;
                }
            }
            return de.b() - f >= ((long) d);
        } catch (Throwable th) {
            cw.a(th, "APS", "isConfigNeedUpdate");
            return false;
        }
    }

    public static String i() {
        return i;
    }

    private static void i(Context context) {
        try {
            Editor edit = context.getSharedPreferences("pref", 0).edit();
            edit.putLong("nowtime", F);
            edit.putInt("count", 0);
            dd.a(edit);
        } catch (Throwable th) {
            cw.a(th, "AuthUtil", "resetPrefsBind");
        }
    }

    public static String j() {
        return j;
    }

    private static void j(Context context) {
        try {
            Editor edit = context.getSharedPreferences("pref", 0).edit();
            edit.putLong("pushSerTime", J);
            edit.putInt("pushCount", 0);
            dd.a(edit);
        } catch (Throwable th) {
            cw.a(th, "AuthUtil", "resetPrefsBind");
        }
    }

    public static String k() {
        return k;
    }

    public static String l() {
        return l;
    }

    public static ArrayList<String> m() {
        return G;
    }

    public static ArrayList<String> n() {
        return K;
    }

    public static boolean o() {
        return y;
    }

    public static int p() {
        return A;
    }

    public static boolean q() {
        return C;
    }

    public static void r() {
        C = false;
    }

    public static boolean s() {
        return O;
    }

    public static long t() {
        return S;
    }

    public static boolean u() {
        return R;
    }

    public static boolean v() {
        return U;
    }

    public static boolean w() {
        return V;
    }

    public static List<cy> x() {
        return Y;
    }

    public static boolean y() {
        return ae;
    }

    public static int z() {
        return af;
    }
}
