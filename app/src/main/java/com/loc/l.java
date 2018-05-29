package com.loc;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.json.JSONException;
import org.json.JSONObject;

// compiled from: AuthConfigManager.java
public final class l {
    public static int a;
    public static String b;

    // compiled from: AuthConfigManager.java
    public static class a {
        public String a;
        public int b;
        public JSONObject c;
        public JSONObject d;
        public JSONObject e;
        public JSONObject f;
        public JSONObject g;
        public JSONObject h;
        public JSONObject i;
        public JSONObject j;
        public JSONObject k;
        public JSONObject l;
        public JSONObject m;
        public JSONObject n;
        public JSONObject o;
        public JSONObject p;
        public JSONObject q;
        public JSONObject r;
        public JSONObject s;
        public a t;
        public d u;
        public c v;
        public b w;
        public b x;
        public b y;
        public b z;

        // compiled from: AuthConfigManager.java
        public static class a {
            public boolean a;
            public boolean b;
            public JSONObject c;
        }

        // compiled from: AuthConfigManager.java
        public static class b {
            public boolean a;
            public String b;
            public String c;
            public String d;
        }

        // compiled from: AuthConfigManager.java
        public static class c {
            public String a;
            public String b;
        }

        // compiled from: AuthConfigManager.java
        public static class d {
            public String a;
            public String b;
            public String c;
        }

        public a() {
            this.b = -1;
        }
    }

    // compiled from: AuthConfigManager.java
    static class b extends bj {
        private String f;
        private Map<String, String> g;
        private boolean h;

        b(Context context, s sVar, String str) {
            super(context, sVar);
            this.f = str;
            this.g = null;
            this.h = VERSION.SDK_INT != 19;
        }

        private Map<String, String> j() {
            Object q = n.q(this.a);
            if (!TextUtils.isEmpty(q)) {
                q = p.b(new StringBuilder(q).reverse().toString());
            }
            Map<String, String> hashMap = new HashMap();
            hashMap.put("authkey", this.f);
            hashMap.put("plattype", "android");
            hashMap.put("product", this.b.a());
            hashMap.put("version", this.b.b());
            hashMap.put("output", "json");
            hashMap.put("androidversion", VERSION.SDK_INT);
            hashMap.put("deviceId", q);
            if (!(this.g == null || this.g.isEmpty())) {
                hashMap.putAll(this.g);
            }
            if (VERSION.SDK_INT >= 21) {
                try {
                    ApplicationInfo applicationInfo = this.a.getApplicationInfo();
                    Field declaredField = Class.forName(ApplicationInfo.class.getName()).getDeclaredField("primaryCpuAbi");
                    declaredField.setAccessible(true);
                    String str = (String) declaredField.get(applicationInfo);
                } catch (Throwable th) {
                    w.a(th, "ConfigManager", "getcpu");
                }
                if (TextUtils.isEmpty(q)) {
                    q = Build.CPU_ABI;
                }
                hashMap.put("abitype", q);
                hashMap.put("ext", this.b.d());
                return hashMap;
            }
            q = null;
            if (TextUtils.isEmpty(q)) {
                q = Build.CPU_ABI;
            }
            hashMap.put("abitype", q);
            hashMap.put("ext", this.b.d());
            return hashMap;
        }

        public final boolean a() {
            return this.h;
        }

        public final byte[] a_() {
            return null;
        }

        public final Map<String, String> b() {
            return null;
        }

        public final String c() {
            return this.h ? "https://restapi.amap.com/v3/iasdkauth" : "http://restapi.amap.com/v3/iasdkauth";
        }

        public final byte[] e() {
            return t.a(t.a(j()));
        }

        protected final String f() {
            return "3.0";
        }
    }

    static {
        a = -1;
        b = StringUtils.EMPTY_STRING;
    }

    public static a a(Context context, s sVar, String str) {
        Object obj;
        String str2;
        bo boVar;
        byte[] bArr;
        JSONObject jSONObject;
        int i;
        String str3;
        a aVar;
        JSONObject jSONObject2;
        JSONObject jSONObject3;
        JSONObject jSONObject4;
        d dVar;
        c cVar;
        String a;
        b bVar;
        Object obj2 = null;
        a aVar2 = new a();
        try {
            Object a2;
            Object a3;
            bi biVar = new bi();
            bn bVar2 = new b(context, sVar, str);
            bo a4 = bi.a(bVar2, bVar2.a());
            if (a4 != null) {
                try {
                    obj = a4.a;
                } catch (j e) {
                    j e2 = e;
                    obj = null;
                    aVar2.a = e2.a();
                    z.a(sVar, "/v3/iasdkauth", e2);
                    str2 = r3;
                    boVar = a4;
                    if (bArr != null) {
                        if (TextUtils.isEmpty(str2)) {
                            str2 = t.a(bArr);
                        }
                        jSONObject = new JSONObject(str2);
                        if (jSONObject.has(NotificationCompat.CATEGORY_STATUS)) {
                            i = jSONObject.getInt(NotificationCompat.CATEGORY_STATUS);
                            if (i != 1) {
                                a = 1;
                            } else if (i == 0) {
                                str2 = "authcsid";
                                str3 = "authgsid";
                                if (boVar != null) {
                                    str2 = boVar.c;
                                    str3 = boVar.d;
                                }
                                t.a(context, str2, str3, jSONObject);
                                a = 0;
                                if (jSONObject.has("info")) {
                                    b = jSONObject.getString("info");
                                }
                                str2 = StringUtils.EMPTY_STRING;
                                if (jSONObject.has("infocode")) {
                                    str2 = jSONObject.getString("infocode");
                                }
                                z.a(sVar, "/v3/iasdkauth", b, str3, str2);
                                if (a == 0) {
                                    aVar2.a = b;
                                }
                            }
                            if (jSONObject.has("ver")) {
                                aVar2.b = jSONObject.getInt("ver");
                            }
                            if (t.a(jSONObject, "result")) {
                                aVar = new a();
                                aVar.a = false;
                                aVar.b = false;
                                aVar2.t = aVar;
                                jSONObject2 = jSONObject.getJSONObject("result");
                                if (t.a(jSONObject2, "11K")) {
                                    jSONObject3 = jSONObject2.getJSONObject("11K");
                                    aVar.a = a(jSONObject3.getString("able"), false);
                                    if (jSONObject3.has("off")) {
                                        aVar.c = jSONObject3.getJSONObject("off");
                                    }
                                }
                                if (t.a(jSONObject2, "11B")) {
                                    aVar2.h = jSONObject2.getJSONObject("11B");
                                }
                                if (t.a(jSONObject2, "11C")) {
                                    aVar2.k = jSONObject2.getJSONObject("11C");
                                }
                                if (t.a(jSONObject2, "11I")) {
                                    aVar2.l = jSONObject2.getJSONObject("11I");
                                }
                                if (t.a(jSONObject2, "11H")) {
                                    aVar2.m = jSONObject2.getJSONObject("11H");
                                }
                                if (t.a(jSONObject2, "11E")) {
                                    aVar2.n = jSONObject2.getJSONObject("11E");
                                }
                                if (t.a(jSONObject2, "11F")) {
                                    aVar2.o = jSONObject2.getJSONObject("11F");
                                }
                                if (t.a(jSONObject2, "13A")) {
                                    aVar2.q = jSONObject2.getJSONObject("13A");
                                }
                                if (t.a(jSONObject2, "13J")) {
                                    aVar2.i = jSONObject2.getJSONObject("13J");
                                }
                                if (t.a(jSONObject2, "11G")) {
                                    aVar2.p = jSONObject2.getJSONObject("11G");
                                }
                                if (t.a(jSONObject2, "001")) {
                                    jSONObject4 = jSONObject2.getJSONObject("001");
                                    dVar = new d();
                                    if (jSONObject4 != null) {
                                        obj = a(jSONObject4, "md5");
                                        a2 = a(jSONObject4, "url");
                                        a3 = a(jSONObject4, "sdkversion");
                                        dVar.a = a2;
                                        dVar.b = obj;
                                        dVar.c = a3;
                                    }
                                    aVar2.u = dVar;
                                }
                                if (t.a(jSONObject2, "002")) {
                                    jSONObject4 = jSONObject2.getJSONObject("002");
                                    cVar = new c();
                                    if (jSONObject4 != null) {
                                        a = a(jSONObject4, "md5");
                                        str2 = a(jSONObject4, "url");
                                        cVar.b = a;
                                        cVar.a = str2;
                                    }
                                    aVar2.v = cVar;
                                }
                                if (t.a(jSONObject2, "006")) {
                                    aVar2.r = jSONObject2.getJSONObject("006");
                                }
                                if (t.a(jSONObject2, "010")) {
                                    aVar2.s = jSONObject2.getJSONObject("010");
                                }
                                if (t.a(jSONObject2, "11Z")) {
                                    jSONObject4 = jSONObject2.getJSONObject("11Z");
                                    bVar = new b();
                                    a(jSONObject4, bVar);
                                    aVar2.w = bVar;
                                }
                                if (t.a(jSONObject2, "135")) {
                                    aVar2.j = jSONObject2.getJSONObject("135");
                                }
                                if (t.a(jSONObject2, "13S")) {
                                    aVar2.g = jSONObject2.getJSONObject("13S");
                                }
                                if (t.a(jSONObject2, "121")) {
                                    jSONObject4 = jSONObject2.getJSONObject("121");
                                    bVar = new b();
                                    a(jSONObject4, bVar);
                                    aVar2.x = bVar;
                                }
                                if (t.a(jSONObject2, "122")) {
                                    jSONObject4 = jSONObject2.getJSONObject("122");
                                    bVar = new b();
                                    a(jSONObject4, bVar);
                                    aVar2.y = bVar;
                                }
                                if (t.a(jSONObject2, "123")) {
                                    jSONObject4 = jSONObject2.getJSONObject("123");
                                    bVar = new b();
                                    a(jSONObject4, bVar);
                                    aVar2.z = bVar;
                                }
                                if (t.a(jSONObject2, "011")) {
                                    aVar2.c = jSONObject2.getJSONObject("011");
                                }
                                if (t.a(jSONObject2, "012")) {
                                    aVar2.d = jSONObject2.getJSONObject("012");
                                }
                                if (t.a(jSONObject2, "013")) {
                                    aVar2.e = jSONObject2.getJSONObject("013");
                                }
                                if (t.a(jSONObject2, "014")) {
                                    aVar2.f = jSONObject2.getJSONObject("014");
                                }
                            }
                        }
                    }
                    return aVar2;
                } catch (IllegalBlockSizeException e3) {
                    bArr = null;
                    str2 = r3;
                    boVar = a4;
                    if (bArr != null) {
                        if (TextUtils.isEmpty(str2)) {
                            str2 = t.a(bArr);
                        }
                        jSONObject = new JSONObject(str2);
                        if (jSONObject.has(NotificationCompat.CATEGORY_STATUS)) {
                            i = jSONObject.getInt(NotificationCompat.CATEGORY_STATUS);
                            if (i != 1) {
                                a = 1;
                            } else if (i == 0) {
                                str2 = "authcsid";
                                str3 = "authgsid";
                                if (boVar != null) {
                                    str2 = boVar.c;
                                    str3 = boVar.d;
                                }
                                t.a(context, str2, str3, jSONObject);
                                a = 0;
                                if (jSONObject.has("info")) {
                                    b = jSONObject.getString("info");
                                }
                                str2 = StringUtils.EMPTY_STRING;
                                if (jSONObject.has("infocode")) {
                                    str2 = jSONObject.getString("infocode");
                                }
                                z.a(sVar, "/v3/iasdkauth", b, str3, str2);
                                if (a == 0) {
                                    aVar2.a = b;
                                }
                            }
                            if (jSONObject.has("ver")) {
                                aVar2.b = jSONObject.getInt("ver");
                            }
                            if (t.a(jSONObject, "result")) {
                                aVar = new a();
                                aVar.a = false;
                                aVar.b = false;
                                aVar2.t = aVar;
                                jSONObject2 = jSONObject.getJSONObject("result");
                                if (t.a(jSONObject2, "11K")) {
                                    jSONObject3 = jSONObject2.getJSONObject("11K");
                                    aVar.a = a(jSONObject3.getString("able"), false);
                                    if (jSONObject3.has("off")) {
                                        aVar.c = jSONObject3.getJSONObject("off");
                                    }
                                }
                                if (t.a(jSONObject2, "11B")) {
                                    aVar2.h = jSONObject2.getJSONObject("11B");
                                }
                                if (t.a(jSONObject2, "11C")) {
                                    aVar2.k = jSONObject2.getJSONObject("11C");
                                }
                                if (t.a(jSONObject2, "11I")) {
                                    aVar2.l = jSONObject2.getJSONObject("11I");
                                }
                                if (t.a(jSONObject2, "11H")) {
                                    aVar2.m = jSONObject2.getJSONObject("11H");
                                }
                                if (t.a(jSONObject2, "11E")) {
                                    aVar2.n = jSONObject2.getJSONObject("11E");
                                }
                                if (t.a(jSONObject2, "11F")) {
                                    aVar2.o = jSONObject2.getJSONObject("11F");
                                }
                                if (t.a(jSONObject2, "13A")) {
                                    aVar2.q = jSONObject2.getJSONObject("13A");
                                }
                                if (t.a(jSONObject2, "13J")) {
                                    aVar2.i = jSONObject2.getJSONObject("13J");
                                }
                                if (t.a(jSONObject2, "11G")) {
                                    aVar2.p = jSONObject2.getJSONObject("11G");
                                }
                                if (t.a(jSONObject2, "001")) {
                                    jSONObject4 = jSONObject2.getJSONObject("001");
                                    dVar = new d();
                                    if (jSONObject4 != null) {
                                        obj = a(jSONObject4, "md5");
                                        a2 = a(jSONObject4, "url");
                                        a3 = a(jSONObject4, "sdkversion");
                                        dVar.a = a2;
                                        dVar.b = obj;
                                        dVar.c = a3;
                                    }
                                    aVar2.u = dVar;
                                }
                                if (t.a(jSONObject2, "002")) {
                                    jSONObject4 = jSONObject2.getJSONObject("002");
                                    cVar = new c();
                                    if (jSONObject4 != null) {
                                        a = a(jSONObject4, "md5");
                                        str2 = a(jSONObject4, "url");
                                        cVar.b = a;
                                        cVar.a = str2;
                                    }
                                    aVar2.v = cVar;
                                }
                                if (t.a(jSONObject2, "006")) {
                                    aVar2.r = jSONObject2.getJSONObject("006");
                                }
                                if (t.a(jSONObject2, "010")) {
                                    aVar2.s = jSONObject2.getJSONObject("010");
                                }
                                if (t.a(jSONObject2, "11Z")) {
                                    jSONObject4 = jSONObject2.getJSONObject("11Z");
                                    bVar = new b();
                                    a(jSONObject4, bVar);
                                    aVar2.w = bVar;
                                }
                                if (t.a(jSONObject2, "135")) {
                                    aVar2.j = jSONObject2.getJSONObject("135");
                                }
                                if (t.a(jSONObject2, "13S")) {
                                    aVar2.g = jSONObject2.getJSONObject("13S");
                                }
                                if (t.a(jSONObject2, "121")) {
                                    jSONObject4 = jSONObject2.getJSONObject("121");
                                    bVar = new b();
                                    a(jSONObject4, bVar);
                                    aVar2.x = bVar;
                                }
                                if (t.a(jSONObject2, "122")) {
                                    jSONObject4 = jSONObject2.getJSONObject("122");
                                    bVar = new b();
                                    a(jSONObject4, bVar);
                                    aVar2.y = bVar;
                                }
                                if (t.a(jSONObject2, "123")) {
                                    jSONObject4 = jSONObject2.getJSONObject("123");
                                    bVar = new b();
                                    a(jSONObject4, bVar);
                                    aVar2.z = bVar;
                                }
                                if (t.a(jSONObject2, "011")) {
                                    aVar2.c = jSONObject2.getJSONObject("011");
                                }
                                if (t.a(jSONObject2, "012")) {
                                    aVar2.d = jSONObject2.getJSONObject("012");
                                }
                                if (t.a(jSONObject2, "013")) {
                                    aVar2.e = jSONObject2.getJSONObject("013");
                                }
                                if (t.a(jSONObject2, "014")) {
                                    aVar2.f = jSONObject2.getJSONObject("014");
                                }
                            }
                        }
                    }
                    return aVar2;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    bArr = null;
                    w.a(th2, "ConfigManager", "loadConfig");
                    str2 = r3;
                    boVar = a4;
                    if (bArr != null) {
                        if (TextUtils.isEmpty(str2)) {
                            str2 = t.a(bArr);
                        }
                        jSONObject = new JSONObject(str2);
                        if (jSONObject.has(NotificationCompat.CATEGORY_STATUS)) {
                            i = jSONObject.getInt(NotificationCompat.CATEGORY_STATUS);
                            if (i != 1) {
                                a = 1;
                            } else if (i == 0) {
                                str2 = "authcsid";
                                str3 = "authgsid";
                                if (boVar != null) {
                                    str2 = boVar.c;
                                    str3 = boVar.d;
                                }
                                t.a(context, str2, str3, jSONObject);
                                a = 0;
                                if (jSONObject.has("info")) {
                                    b = jSONObject.getString("info");
                                }
                                str2 = StringUtils.EMPTY_STRING;
                                if (jSONObject.has("infocode")) {
                                    str2 = jSONObject.getString("infocode");
                                }
                                z.a(sVar, "/v3/iasdkauth", b, str3, str2);
                                if (a == 0) {
                                    aVar2.a = b;
                                }
                            }
                            if (jSONObject.has("ver")) {
                                aVar2.b = jSONObject.getInt("ver");
                            }
                            if (t.a(jSONObject, "result")) {
                                aVar = new a();
                                aVar.a = false;
                                aVar.b = false;
                                aVar2.t = aVar;
                                jSONObject2 = jSONObject.getJSONObject("result");
                                if (t.a(jSONObject2, "11K")) {
                                    jSONObject3 = jSONObject2.getJSONObject("11K");
                                    aVar.a = a(jSONObject3.getString("able"), false);
                                    if (jSONObject3.has("off")) {
                                        aVar.c = jSONObject3.getJSONObject("off");
                                    }
                                }
                                if (t.a(jSONObject2, "11B")) {
                                    aVar2.h = jSONObject2.getJSONObject("11B");
                                }
                                if (t.a(jSONObject2, "11C")) {
                                    aVar2.k = jSONObject2.getJSONObject("11C");
                                }
                                if (t.a(jSONObject2, "11I")) {
                                    aVar2.l = jSONObject2.getJSONObject("11I");
                                }
                                if (t.a(jSONObject2, "11H")) {
                                    aVar2.m = jSONObject2.getJSONObject("11H");
                                }
                                if (t.a(jSONObject2, "11E")) {
                                    aVar2.n = jSONObject2.getJSONObject("11E");
                                }
                                if (t.a(jSONObject2, "11F")) {
                                    aVar2.o = jSONObject2.getJSONObject("11F");
                                }
                                if (t.a(jSONObject2, "13A")) {
                                    aVar2.q = jSONObject2.getJSONObject("13A");
                                }
                                if (t.a(jSONObject2, "13J")) {
                                    aVar2.i = jSONObject2.getJSONObject("13J");
                                }
                                if (t.a(jSONObject2, "11G")) {
                                    aVar2.p = jSONObject2.getJSONObject("11G");
                                }
                                if (t.a(jSONObject2, "001")) {
                                    jSONObject4 = jSONObject2.getJSONObject("001");
                                    dVar = new d();
                                    if (jSONObject4 != null) {
                                        obj = a(jSONObject4, "md5");
                                        a2 = a(jSONObject4, "url");
                                        a3 = a(jSONObject4, "sdkversion");
                                        dVar.a = a2;
                                        dVar.b = obj;
                                        dVar.c = a3;
                                    }
                                    aVar2.u = dVar;
                                }
                                if (t.a(jSONObject2, "002")) {
                                    jSONObject4 = jSONObject2.getJSONObject("002");
                                    cVar = new c();
                                    if (jSONObject4 != null) {
                                        a = a(jSONObject4, "md5");
                                        str2 = a(jSONObject4, "url");
                                        cVar.b = a;
                                        cVar.a = str2;
                                    }
                                    aVar2.v = cVar;
                                }
                                if (t.a(jSONObject2, "006")) {
                                    aVar2.r = jSONObject2.getJSONObject("006");
                                }
                                if (t.a(jSONObject2, "010")) {
                                    aVar2.s = jSONObject2.getJSONObject("010");
                                }
                                if (t.a(jSONObject2, "11Z")) {
                                    jSONObject4 = jSONObject2.getJSONObject("11Z");
                                    bVar = new b();
                                    a(jSONObject4, bVar);
                                    aVar2.w = bVar;
                                }
                                if (t.a(jSONObject2, "135")) {
                                    aVar2.j = jSONObject2.getJSONObject("135");
                                }
                                if (t.a(jSONObject2, "13S")) {
                                    aVar2.g = jSONObject2.getJSONObject("13S");
                                }
                                if (t.a(jSONObject2, "121")) {
                                    jSONObject4 = jSONObject2.getJSONObject("121");
                                    bVar = new b();
                                    a(jSONObject4, bVar);
                                    aVar2.x = bVar;
                                }
                                if (t.a(jSONObject2, "122")) {
                                    jSONObject4 = jSONObject2.getJSONObject("122");
                                    bVar = new b();
                                    a(jSONObject4, bVar);
                                    aVar2.y = bVar;
                                }
                                if (t.a(jSONObject2, "123")) {
                                    jSONObject4 = jSONObject2.getJSONObject("123");
                                    bVar = new b();
                                    a(jSONObject4, bVar);
                                    aVar2.z = bVar;
                                }
                                if (t.a(jSONObject2, "011")) {
                                    aVar2.c = jSONObject2.getJSONObject("011");
                                }
                                if (t.a(jSONObject2, "012")) {
                                    aVar2.d = jSONObject2.getJSONObject("012");
                                }
                                if (t.a(jSONObject2, "013")) {
                                    aVar2.e = jSONObject2.getJSONObject("013");
                                }
                                if (t.a(jSONObject2, "014")) {
                                    aVar2.f = jSONObject2.getJSONObject("014");
                                }
                            }
                        }
                    }
                    return aVar2;
                }
            }
            obj = null;
            try {
                a3 = new Object[16];
                a2 = new Object[(bArr.length - 16)];
                System.arraycopy(bArr, 0, a3, 0, ConnectionResult.API_UNAVAILABLE);
                System.arraycopy(bArr, ConnectionResult.API_UNAVAILABLE, a2, 0, bArr.length - 16);
                Key secretKeySpec = new SecretKeySpec(a3, "AES");
                Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
                instance.init(RainSurfaceView.RAIN_LEVEL_SHOWER, secretKeySpec, new IvParameterSpec(t.b()));
                str2 = t.a(instance.doFinal(a2));
                boVar = a4;
            } catch (j e4) {
                e2 = e4;
                aVar2.a = e2.a();
                z.a(sVar, "/v3/iasdkauth", e2);
                str2 = r3;
                boVar = a4;
                if (bArr != null) {
                    if (TextUtils.isEmpty(str2)) {
                        str2 = t.a(bArr);
                    }
                    jSONObject = new JSONObject(str2);
                    if (jSONObject.has(NotificationCompat.CATEGORY_STATUS)) {
                        i = jSONObject.getInt(NotificationCompat.CATEGORY_STATUS);
                        if (i != 1) {
                            a = 1;
                        } else if (i == 0) {
                            str2 = "authcsid";
                            str3 = "authgsid";
                            if (boVar != null) {
                                str2 = boVar.c;
                                str3 = boVar.d;
                            }
                            t.a(context, str2, str3, jSONObject);
                            a = 0;
                            if (jSONObject.has("info")) {
                                b = jSONObject.getString("info");
                            }
                            str2 = StringUtils.EMPTY_STRING;
                            if (jSONObject.has("infocode")) {
                                str2 = jSONObject.getString("infocode");
                            }
                            z.a(sVar, "/v3/iasdkauth", b, str3, str2);
                            if (a == 0) {
                                aVar2.a = b;
                            }
                        }
                        if (jSONObject.has("ver")) {
                            aVar2.b = jSONObject.getInt("ver");
                        }
                        if (t.a(jSONObject, "result")) {
                            aVar = new a();
                            aVar.a = false;
                            aVar.b = false;
                            aVar2.t = aVar;
                            jSONObject2 = jSONObject.getJSONObject("result");
                            if (t.a(jSONObject2, "11K")) {
                                jSONObject3 = jSONObject2.getJSONObject("11K");
                                aVar.a = a(jSONObject3.getString("able"), false);
                                if (jSONObject3.has("off")) {
                                    aVar.c = jSONObject3.getJSONObject("off");
                                }
                            }
                            if (t.a(jSONObject2, "11B")) {
                                aVar2.h = jSONObject2.getJSONObject("11B");
                            }
                            if (t.a(jSONObject2, "11C")) {
                                aVar2.k = jSONObject2.getJSONObject("11C");
                            }
                            if (t.a(jSONObject2, "11I")) {
                                aVar2.l = jSONObject2.getJSONObject("11I");
                            }
                            if (t.a(jSONObject2, "11H")) {
                                aVar2.m = jSONObject2.getJSONObject("11H");
                            }
                            if (t.a(jSONObject2, "11E")) {
                                aVar2.n = jSONObject2.getJSONObject("11E");
                            }
                            if (t.a(jSONObject2, "11F")) {
                                aVar2.o = jSONObject2.getJSONObject("11F");
                            }
                            if (t.a(jSONObject2, "13A")) {
                                aVar2.q = jSONObject2.getJSONObject("13A");
                            }
                            if (t.a(jSONObject2, "13J")) {
                                aVar2.i = jSONObject2.getJSONObject("13J");
                            }
                            if (t.a(jSONObject2, "11G")) {
                                aVar2.p = jSONObject2.getJSONObject("11G");
                            }
                            if (t.a(jSONObject2, "001")) {
                                jSONObject4 = jSONObject2.getJSONObject("001");
                                dVar = new d();
                                if (jSONObject4 != null) {
                                    obj = a(jSONObject4, "md5");
                                    a2 = a(jSONObject4, "url");
                                    a3 = a(jSONObject4, "sdkversion");
                                    dVar.a = a2;
                                    dVar.b = obj;
                                    dVar.c = a3;
                                }
                                aVar2.u = dVar;
                            }
                            if (t.a(jSONObject2, "002")) {
                                jSONObject4 = jSONObject2.getJSONObject("002");
                                cVar = new c();
                                if (jSONObject4 != null) {
                                    a = a(jSONObject4, "md5");
                                    str2 = a(jSONObject4, "url");
                                    cVar.b = a;
                                    cVar.a = str2;
                                }
                                aVar2.v = cVar;
                            }
                            if (t.a(jSONObject2, "006")) {
                                aVar2.r = jSONObject2.getJSONObject("006");
                            }
                            if (t.a(jSONObject2, "010")) {
                                aVar2.s = jSONObject2.getJSONObject("010");
                            }
                            if (t.a(jSONObject2, "11Z")) {
                                jSONObject4 = jSONObject2.getJSONObject("11Z");
                                bVar = new b();
                                a(jSONObject4, bVar);
                                aVar2.w = bVar;
                            }
                            if (t.a(jSONObject2, "135")) {
                                aVar2.j = jSONObject2.getJSONObject("135");
                            }
                            if (t.a(jSONObject2, "13S")) {
                                aVar2.g = jSONObject2.getJSONObject("13S");
                            }
                            if (t.a(jSONObject2, "121")) {
                                jSONObject4 = jSONObject2.getJSONObject("121");
                                bVar = new b();
                                a(jSONObject4, bVar);
                                aVar2.x = bVar;
                            }
                            if (t.a(jSONObject2, "122")) {
                                jSONObject4 = jSONObject2.getJSONObject("122");
                                bVar = new b();
                                a(jSONObject4, bVar);
                                aVar2.y = bVar;
                            }
                            if (t.a(jSONObject2, "123")) {
                                jSONObject4 = jSONObject2.getJSONObject("123");
                                bVar = new b();
                                a(jSONObject4, bVar);
                                aVar2.z = bVar;
                            }
                            if (t.a(jSONObject2, "011")) {
                                aVar2.c = jSONObject2.getJSONObject("011");
                            }
                            if (t.a(jSONObject2, "012")) {
                                aVar2.d = jSONObject2.getJSONObject("012");
                            }
                            if (t.a(jSONObject2, "013")) {
                                aVar2.e = jSONObject2.getJSONObject("013");
                            }
                            if (t.a(jSONObject2, "014")) {
                                aVar2.f = jSONObject2.getJSONObject("014");
                            }
                        }
                    }
                }
                return aVar2;
            } catch (IllegalBlockSizeException e5) {
                str2 = r3;
                boVar = a4;
                if (bArr != null) {
                    if (TextUtils.isEmpty(str2)) {
                        str2 = t.a(bArr);
                    }
                    jSONObject = new JSONObject(str2);
                    if (jSONObject.has(NotificationCompat.CATEGORY_STATUS)) {
                        i = jSONObject.getInt(NotificationCompat.CATEGORY_STATUS);
                        if (i != 1) {
                            a = 1;
                        } else if (i == 0) {
                            str2 = "authcsid";
                            str3 = "authgsid";
                            if (boVar != null) {
                                str2 = boVar.c;
                                str3 = boVar.d;
                            }
                            t.a(context, str2, str3, jSONObject);
                            a = 0;
                            if (jSONObject.has("info")) {
                                b = jSONObject.getString("info");
                            }
                            str2 = StringUtils.EMPTY_STRING;
                            if (jSONObject.has("infocode")) {
                                str2 = jSONObject.getString("infocode");
                            }
                            z.a(sVar, "/v3/iasdkauth", b, str3, str2);
                            if (a == 0) {
                                aVar2.a = b;
                            }
                        }
                        if (jSONObject.has("ver")) {
                            aVar2.b = jSONObject.getInt("ver");
                        }
                        if (t.a(jSONObject, "result")) {
                            aVar = new a();
                            aVar.a = false;
                            aVar.b = false;
                            aVar2.t = aVar;
                            jSONObject2 = jSONObject.getJSONObject("result");
                            if (t.a(jSONObject2, "11K")) {
                                jSONObject3 = jSONObject2.getJSONObject("11K");
                                aVar.a = a(jSONObject3.getString("able"), false);
                                if (jSONObject3.has("off")) {
                                    aVar.c = jSONObject3.getJSONObject("off");
                                }
                            }
                            if (t.a(jSONObject2, "11B")) {
                                aVar2.h = jSONObject2.getJSONObject("11B");
                            }
                            if (t.a(jSONObject2, "11C")) {
                                aVar2.k = jSONObject2.getJSONObject("11C");
                            }
                            if (t.a(jSONObject2, "11I")) {
                                aVar2.l = jSONObject2.getJSONObject("11I");
                            }
                            if (t.a(jSONObject2, "11H")) {
                                aVar2.m = jSONObject2.getJSONObject("11H");
                            }
                            if (t.a(jSONObject2, "11E")) {
                                aVar2.n = jSONObject2.getJSONObject("11E");
                            }
                            if (t.a(jSONObject2, "11F")) {
                                aVar2.o = jSONObject2.getJSONObject("11F");
                            }
                            if (t.a(jSONObject2, "13A")) {
                                aVar2.q = jSONObject2.getJSONObject("13A");
                            }
                            if (t.a(jSONObject2, "13J")) {
                                aVar2.i = jSONObject2.getJSONObject("13J");
                            }
                            if (t.a(jSONObject2, "11G")) {
                                aVar2.p = jSONObject2.getJSONObject("11G");
                            }
                            if (t.a(jSONObject2, "001")) {
                                jSONObject4 = jSONObject2.getJSONObject("001");
                                dVar = new d();
                                if (jSONObject4 != null) {
                                    obj = a(jSONObject4, "md5");
                                    a2 = a(jSONObject4, "url");
                                    a3 = a(jSONObject4, "sdkversion");
                                    dVar.a = a2;
                                    dVar.b = obj;
                                    dVar.c = a3;
                                }
                                aVar2.u = dVar;
                            }
                            if (t.a(jSONObject2, "002")) {
                                jSONObject4 = jSONObject2.getJSONObject("002");
                                cVar = new c();
                                if (jSONObject4 != null) {
                                    a = a(jSONObject4, "md5");
                                    str2 = a(jSONObject4, "url");
                                    cVar.b = a;
                                    cVar.a = str2;
                                }
                                aVar2.v = cVar;
                            }
                            if (t.a(jSONObject2, "006")) {
                                aVar2.r = jSONObject2.getJSONObject("006");
                            }
                            if (t.a(jSONObject2, "010")) {
                                aVar2.s = jSONObject2.getJSONObject("010");
                            }
                            if (t.a(jSONObject2, "11Z")) {
                                jSONObject4 = jSONObject2.getJSONObject("11Z");
                                bVar = new b();
                                a(jSONObject4, bVar);
                                aVar2.w = bVar;
                            }
                            if (t.a(jSONObject2, "135")) {
                                aVar2.j = jSONObject2.getJSONObject("135");
                            }
                            if (t.a(jSONObject2, "13S")) {
                                aVar2.g = jSONObject2.getJSONObject("13S");
                            }
                            if (t.a(jSONObject2, "121")) {
                                jSONObject4 = jSONObject2.getJSONObject("121");
                                bVar = new b();
                                a(jSONObject4, bVar);
                                aVar2.x = bVar;
                            }
                            if (t.a(jSONObject2, "122")) {
                                jSONObject4 = jSONObject2.getJSONObject("122");
                                bVar = new b();
                                a(jSONObject4, bVar);
                                aVar2.y = bVar;
                            }
                            if (t.a(jSONObject2, "123")) {
                                jSONObject4 = jSONObject2.getJSONObject("123");
                                bVar = new b();
                                a(jSONObject4, bVar);
                                aVar2.z = bVar;
                            }
                            if (t.a(jSONObject2, "011")) {
                                aVar2.c = jSONObject2.getJSONObject("011");
                            }
                            if (t.a(jSONObject2, "012")) {
                                aVar2.d = jSONObject2.getJSONObject("012");
                            }
                            if (t.a(jSONObject2, "013")) {
                                aVar2.e = jSONObject2.getJSONObject("013");
                            }
                            if (t.a(jSONObject2, "014")) {
                                aVar2.f = jSONObject2.getJSONObject("014");
                            }
                        }
                    }
                }
                return aVar2;
            } catch (Throwable th3) {
                th2 = th3;
                w.a(th2, "ConfigManager", "loadConfig");
                str2 = r3;
                boVar = a4;
                if (bArr != null) {
                    if (TextUtils.isEmpty(str2)) {
                        str2 = t.a(bArr);
                    }
                    jSONObject = new JSONObject(str2);
                    if (jSONObject.has(NotificationCompat.CATEGORY_STATUS)) {
                        i = jSONObject.getInt(NotificationCompat.CATEGORY_STATUS);
                        if (i != 1) {
                            a = 1;
                        } else if (i == 0) {
                            str2 = "authcsid";
                            str3 = "authgsid";
                            if (boVar != null) {
                                str2 = boVar.c;
                                str3 = boVar.d;
                            }
                            t.a(context, str2, str3, jSONObject);
                            a = 0;
                            if (jSONObject.has("info")) {
                                b = jSONObject.getString("info");
                            }
                            str2 = StringUtils.EMPTY_STRING;
                            if (jSONObject.has("infocode")) {
                                str2 = jSONObject.getString("infocode");
                            }
                            z.a(sVar, "/v3/iasdkauth", b, str3, str2);
                            if (a == 0) {
                                aVar2.a = b;
                            }
                        }
                        if (jSONObject.has("ver")) {
                            aVar2.b = jSONObject.getInt("ver");
                        }
                        if (t.a(jSONObject, "result")) {
                            aVar = new a();
                            aVar.a = false;
                            aVar.b = false;
                            aVar2.t = aVar;
                            jSONObject2 = jSONObject.getJSONObject("result");
                            if (t.a(jSONObject2, "11K")) {
                                jSONObject3 = jSONObject2.getJSONObject("11K");
                                aVar.a = a(jSONObject3.getString("able"), false);
                                if (jSONObject3.has("off")) {
                                    aVar.c = jSONObject3.getJSONObject("off");
                                }
                            }
                            if (t.a(jSONObject2, "11B")) {
                                aVar2.h = jSONObject2.getJSONObject("11B");
                            }
                            if (t.a(jSONObject2, "11C")) {
                                aVar2.k = jSONObject2.getJSONObject("11C");
                            }
                            if (t.a(jSONObject2, "11I")) {
                                aVar2.l = jSONObject2.getJSONObject("11I");
                            }
                            if (t.a(jSONObject2, "11H")) {
                                aVar2.m = jSONObject2.getJSONObject("11H");
                            }
                            if (t.a(jSONObject2, "11E")) {
                                aVar2.n = jSONObject2.getJSONObject("11E");
                            }
                            if (t.a(jSONObject2, "11F")) {
                                aVar2.o = jSONObject2.getJSONObject("11F");
                            }
                            if (t.a(jSONObject2, "13A")) {
                                aVar2.q = jSONObject2.getJSONObject("13A");
                            }
                            if (t.a(jSONObject2, "13J")) {
                                aVar2.i = jSONObject2.getJSONObject("13J");
                            }
                            if (t.a(jSONObject2, "11G")) {
                                aVar2.p = jSONObject2.getJSONObject("11G");
                            }
                            if (t.a(jSONObject2, "001")) {
                                jSONObject4 = jSONObject2.getJSONObject("001");
                                dVar = new d();
                                if (jSONObject4 != null) {
                                    obj = a(jSONObject4, "md5");
                                    a2 = a(jSONObject4, "url");
                                    a3 = a(jSONObject4, "sdkversion");
                                    dVar.a = a2;
                                    dVar.b = obj;
                                    dVar.c = a3;
                                }
                                aVar2.u = dVar;
                            }
                            if (t.a(jSONObject2, "002")) {
                                jSONObject4 = jSONObject2.getJSONObject("002");
                                cVar = new c();
                                if (jSONObject4 != null) {
                                    a = a(jSONObject4, "md5");
                                    str2 = a(jSONObject4, "url");
                                    cVar.b = a;
                                    cVar.a = str2;
                                }
                                aVar2.v = cVar;
                            }
                            if (t.a(jSONObject2, "006")) {
                                aVar2.r = jSONObject2.getJSONObject("006");
                            }
                            if (t.a(jSONObject2, "010")) {
                                aVar2.s = jSONObject2.getJSONObject("010");
                            }
                            if (t.a(jSONObject2, "11Z")) {
                                jSONObject4 = jSONObject2.getJSONObject("11Z");
                                bVar = new b();
                                a(jSONObject4, bVar);
                                aVar2.w = bVar;
                            }
                            if (t.a(jSONObject2, "135")) {
                                aVar2.j = jSONObject2.getJSONObject("135");
                            }
                            if (t.a(jSONObject2, "13S")) {
                                aVar2.g = jSONObject2.getJSONObject("13S");
                            }
                            if (t.a(jSONObject2, "121")) {
                                jSONObject4 = jSONObject2.getJSONObject("121");
                                bVar = new b();
                                a(jSONObject4, bVar);
                                aVar2.x = bVar;
                            }
                            if (t.a(jSONObject2, "122")) {
                                jSONObject4 = jSONObject2.getJSONObject("122");
                                bVar = new b();
                                a(jSONObject4, bVar);
                                aVar2.y = bVar;
                            }
                            if (t.a(jSONObject2, "123")) {
                                jSONObject4 = jSONObject2.getJSONObject("123");
                                bVar = new b();
                                a(jSONObject4, bVar);
                                aVar2.z = bVar;
                            }
                            if (t.a(jSONObject2, "011")) {
                                aVar2.c = jSONObject2.getJSONObject("011");
                            }
                            if (t.a(jSONObject2, "012")) {
                                aVar2.d = jSONObject2.getJSONObject("012");
                            }
                            if (t.a(jSONObject2, "013")) {
                                aVar2.e = jSONObject2.getJSONObject("013");
                            }
                            if (t.a(jSONObject2, "014")) {
                                aVar2.f = jSONObject2.getJSONObject("014");
                            }
                        }
                    }
                }
                return aVar2;
            }
        } catch (j e22) {
            throw e22;
        } catch (IllegalBlockSizeException e6) {
            a4 = null;
            bArr = null;
            str2 = r3;
            boVar = a4;
            if (bArr != null) {
                if (TextUtils.isEmpty(str2)) {
                    str2 = t.a(bArr);
                }
                jSONObject = new JSONObject(str2);
                if (jSONObject.has(NotificationCompat.CATEGORY_STATUS)) {
                    i = jSONObject.getInt(NotificationCompat.CATEGORY_STATUS);
                    if (i != 1) {
                        a = 1;
                    } else if (i == 0) {
                        str2 = "authcsid";
                        str3 = "authgsid";
                        if (boVar != null) {
                            str2 = boVar.c;
                            str3 = boVar.d;
                        }
                        t.a(context, str2, str3, jSONObject);
                        a = 0;
                        if (jSONObject.has("info")) {
                            b = jSONObject.getString("info");
                        }
                        str2 = StringUtils.EMPTY_STRING;
                        if (jSONObject.has("infocode")) {
                            str2 = jSONObject.getString("infocode");
                        }
                        z.a(sVar, "/v3/iasdkauth", b, str3, str2);
                        if (a == 0) {
                            aVar2.a = b;
                        }
                    }
                    if (jSONObject.has("ver")) {
                        aVar2.b = jSONObject.getInt("ver");
                    }
                    if (t.a(jSONObject, "result")) {
                        aVar = new a();
                        aVar.a = false;
                        aVar.b = false;
                        aVar2.t = aVar;
                        jSONObject2 = jSONObject.getJSONObject("result");
                        if (t.a(jSONObject2, "11K")) {
                            jSONObject3 = jSONObject2.getJSONObject("11K");
                            aVar.a = a(jSONObject3.getString("able"), false);
                            if (jSONObject3.has("off")) {
                                aVar.c = jSONObject3.getJSONObject("off");
                            }
                        }
                        if (t.a(jSONObject2, "11B")) {
                            aVar2.h = jSONObject2.getJSONObject("11B");
                        }
                        if (t.a(jSONObject2, "11C")) {
                            aVar2.k = jSONObject2.getJSONObject("11C");
                        }
                        if (t.a(jSONObject2, "11I")) {
                            aVar2.l = jSONObject2.getJSONObject("11I");
                        }
                        if (t.a(jSONObject2, "11H")) {
                            aVar2.m = jSONObject2.getJSONObject("11H");
                        }
                        if (t.a(jSONObject2, "11E")) {
                            aVar2.n = jSONObject2.getJSONObject("11E");
                        }
                        if (t.a(jSONObject2, "11F")) {
                            aVar2.o = jSONObject2.getJSONObject("11F");
                        }
                        if (t.a(jSONObject2, "13A")) {
                            aVar2.q = jSONObject2.getJSONObject("13A");
                        }
                        if (t.a(jSONObject2, "13J")) {
                            aVar2.i = jSONObject2.getJSONObject("13J");
                        }
                        if (t.a(jSONObject2, "11G")) {
                            aVar2.p = jSONObject2.getJSONObject("11G");
                        }
                        if (t.a(jSONObject2, "001")) {
                            jSONObject4 = jSONObject2.getJSONObject("001");
                            dVar = new d();
                            if (jSONObject4 != null) {
                                obj = a(jSONObject4, "md5");
                                a2 = a(jSONObject4, "url");
                                a3 = a(jSONObject4, "sdkversion");
                                dVar.a = a2;
                                dVar.b = obj;
                                dVar.c = a3;
                            }
                            aVar2.u = dVar;
                        }
                        if (t.a(jSONObject2, "002")) {
                            jSONObject4 = jSONObject2.getJSONObject("002");
                            cVar = new c();
                            if (jSONObject4 != null) {
                                a = a(jSONObject4, "md5");
                                str2 = a(jSONObject4, "url");
                                cVar.b = a;
                                cVar.a = str2;
                            }
                            aVar2.v = cVar;
                        }
                        if (t.a(jSONObject2, "006")) {
                            aVar2.r = jSONObject2.getJSONObject("006");
                        }
                        if (t.a(jSONObject2, "010")) {
                            aVar2.s = jSONObject2.getJSONObject("010");
                        }
                        if (t.a(jSONObject2, "11Z")) {
                            jSONObject4 = jSONObject2.getJSONObject("11Z");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.w = bVar;
                        }
                        if (t.a(jSONObject2, "135")) {
                            aVar2.j = jSONObject2.getJSONObject("135");
                        }
                        if (t.a(jSONObject2, "13S")) {
                            aVar2.g = jSONObject2.getJSONObject("13S");
                        }
                        if (t.a(jSONObject2, "121")) {
                            jSONObject4 = jSONObject2.getJSONObject("121");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.x = bVar;
                        }
                        if (t.a(jSONObject2, "122")) {
                            jSONObject4 = jSONObject2.getJSONObject("122");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.y = bVar;
                        }
                        if (t.a(jSONObject2, "123")) {
                            jSONObject4 = jSONObject2.getJSONObject("123");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.z = bVar;
                        }
                        if (t.a(jSONObject2, "011")) {
                            aVar2.c = jSONObject2.getJSONObject("011");
                        }
                        if (t.a(jSONObject2, "012")) {
                            aVar2.d = jSONObject2.getJSONObject("012");
                        }
                        if (t.a(jSONObject2, "013")) {
                            aVar2.e = jSONObject2.getJSONObject("013");
                        }
                        if (t.a(jSONObject2, "014")) {
                            aVar2.f = jSONObject2.getJSONObject("014");
                        }
                    }
                }
            }
            return aVar2;
        } catch (j e7) {
            e22 = e7;
            a4 = null;
            obj = null;
        } catch (IllegalBlockSizeException e62) {
            a4 = null;
            bArr = null;
            str2 = r3;
            boVar = a4;
            if (bArr != null) {
                if (TextUtils.isEmpty(str2)) {
                    str2 = t.a(bArr);
                }
                jSONObject = new JSONObject(str2);
                if (jSONObject.has(NotificationCompat.CATEGORY_STATUS)) {
                    i = jSONObject.getInt(NotificationCompat.CATEGORY_STATUS);
                    if (i != 1) {
                        a = 1;
                    } else if (i == 0) {
                        str2 = "authcsid";
                        str3 = "authgsid";
                        if (boVar != null) {
                            str2 = boVar.c;
                            str3 = boVar.d;
                        }
                        t.a(context, str2, str3, jSONObject);
                        a = 0;
                        if (jSONObject.has("info")) {
                            b = jSONObject.getString("info");
                        }
                        str2 = StringUtils.EMPTY_STRING;
                        if (jSONObject.has("infocode")) {
                            str2 = jSONObject.getString("infocode");
                        }
                        z.a(sVar, "/v3/iasdkauth", b, str3, str2);
                        if (a == 0) {
                            aVar2.a = b;
                        }
                    }
                    if (jSONObject.has("ver")) {
                        aVar2.b = jSONObject.getInt("ver");
                    }
                    if (t.a(jSONObject, "result")) {
                        aVar = new a();
                        aVar.a = false;
                        aVar.b = false;
                        aVar2.t = aVar;
                        jSONObject2 = jSONObject.getJSONObject("result");
                        if (t.a(jSONObject2, "11K")) {
                            jSONObject3 = jSONObject2.getJSONObject("11K");
                            aVar.a = a(jSONObject3.getString("able"), false);
                            if (jSONObject3.has("off")) {
                                aVar.c = jSONObject3.getJSONObject("off");
                            }
                        }
                        if (t.a(jSONObject2, "11B")) {
                            aVar2.h = jSONObject2.getJSONObject("11B");
                        }
                        if (t.a(jSONObject2, "11C")) {
                            aVar2.k = jSONObject2.getJSONObject("11C");
                        }
                        if (t.a(jSONObject2, "11I")) {
                            aVar2.l = jSONObject2.getJSONObject("11I");
                        }
                        if (t.a(jSONObject2, "11H")) {
                            aVar2.m = jSONObject2.getJSONObject("11H");
                        }
                        if (t.a(jSONObject2, "11E")) {
                            aVar2.n = jSONObject2.getJSONObject("11E");
                        }
                        if (t.a(jSONObject2, "11F")) {
                            aVar2.o = jSONObject2.getJSONObject("11F");
                        }
                        if (t.a(jSONObject2, "13A")) {
                            aVar2.q = jSONObject2.getJSONObject("13A");
                        }
                        if (t.a(jSONObject2, "13J")) {
                            aVar2.i = jSONObject2.getJSONObject("13J");
                        }
                        if (t.a(jSONObject2, "11G")) {
                            aVar2.p = jSONObject2.getJSONObject("11G");
                        }
                        if (t.a(jSONObject2, "001")) {
                            jSONObject4 = jSONObject2.getJSONObject("001");
                            dVar = new d();
                            if (jSONObject4 != null) {
                                obj = a(jSONObject4, "md5");
                                a2 = a(jSONObject4, "url");
                                a3 = a(jSONObject4, "sdkversion");
                                dVar.a = a2;
                                dVar.b = obj;
                                dVar.c = a3;
                            }
                            aVar2.u = dVar;
                        }
                        if (t.a(jSONObject2, "002")) {
                            jSONObject4 = jSONObject2.getJSONObject("002");
                            cVar = new c();
                            if (jSONObject4 != null) {
                                a = a(jSONObject4, "md5");
                                str2 = a(jSONObject4, "url");
                                cVar.b = a;
                                cVar.a = str2;
                            }
                            aVar2.v = cVar;
                        }
                        if (t.a(jSONObject2, "006")) {
                            aVar2.r = jSONObject2.getJSONObject("006");
                        }
                        if (t.a(jSONObject2, "010")) {
                            aVar2.s = jSONObject2.getJSONObject("010");
                        }
                        if (t.a(jSONObject2, "11Z")) {
                            jSONObject4 = jSONObject2.getJSONObject("11Z");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.w = bVar;
                        }
                        if (t.a(jSONObject2, "135")) {
                            aVar2.j = jSONObject2.getJSONObject("135");
                        }
                        if (t.a(jSONObject2, "13S")) {
                            aVar2.g = jSONObject2.getJSONObject("13S");
                        }
                        if (t.a(jSONObject2, "121")) {
                            jSONObject4 = jSONObject2.getJSONObject("121");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.x = bVar;
                        }
                        if (t.a(jSONObject2, "122")) {
                            jSONObject4 = jSONObject2.getJSONObject("122");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.y = bVar;
                        }
                        if (t.a(jSONObject2, "123")) {
                            jSONObject4 = jSONObject2.getJSONObject("123");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.z = bVar;
                        }
                        if (t.a(jSONObject2, "011")) {
                            aVar2.c = jSONObject2.getJSONObject("011");
                        }
                        if (t.a(jSONObject2, "012")) {
                            aVar2.d = jSONObject2.getJSONObject("012");
                        }
                        if (t.a(jSONObject2, "013")) {
                            aVar2.e = jSONObject2.getJSONObject("013");
                        }
                        if (t.a(jSONObject2, "014")) {
                            aVar2.f = jSONObject2.getJSONObject("014");
                        }
                    }
                }
            }
            return aVar2;
        } catch (Throwable th4) {
            th2 = th4;
            a4 = null;
            bArr = null;
            w.a(th2, "ConfigManager", "loadConfig");
            str2 = r3;
            boVar = a4;
            if (bArr != null) {
                if (TextUtils.isEmpty(str2)) {
                    str2 = t.a(bArr);
                }
                jSONObject = new JSONObject(str2);
                if (jSONObject.has(NotificationCompat.CATEGORY_STATUS)) {
                    i = jSONObject.getInt(NotificationCompat.CATEGORY_STATUS);
                    if (i != 1) {
                        a = 1;
                    } else if (i == 0) {
                        str2 = "authcsid";
                        str3 = "authgsid";
                        if (boVar != null) {
                            str2 = boVar.c;
                            str3 = boVar.d;
                        }
                        t.a(context, str2, str3, jSONObject);
                        a = 0;
                        if (jSONObject.has("info")) {
                            b = jSONObject.getString("info");
                        }
                        str2 = StringUtils.EMPTY_STRING;
                        if (jSONObject.has("infocode")) {
                            str2 = jSONObject.getString("infocode");
                        }
                        z.a(sVar, "/v3/iasdkauth", b, str3, str2);
                        if (a == 0) {
                            aVar2.a = b;
                        }
                    }
                    if (jSONObject.has("ver")) {
                        aVar2.b = jSONObject.getInt("ver");
                    }
                    if (t.a(jSONObject, "result")) {
                        aVar = new a();
                        aVar.a = false;
                        aVar.b = false;
                        aVar2.t = aVar;
                        jSONObject2 = jSONObject.getJSONObject("result");
                        if (t.a(jSONObject2, "11K")) {
                            jSONObject3 = jSONObject2.getJSONObject("11K");
                            aVar.a = a(jSONObject3.getString("able"), false);
                            if (jSONObject3.has("off")) {
                                aVar.c = jSONObject3.getJSONObject("off");
                            }
                        }
                        if (t.a(jSONObject2, "11B")) {
                            aVar2.h = jSONObject2.getJSONObject("11B");
                        }
                        if (t.a(jSONObject2, "11C")) {
                            aVar2.k = jSONObject2.getJSONObject("11C");
                        }
                        if (t.a(jSONObject2, "11I")) {
                            aVar2.l = jSONObject2.getJSONObject("11I");
                        }
                        if (t.a(jSONObject2, "11H")) {
                            aVar2.m = jSONObject2.getJSONObject("11H");
                        }
                        if (t.a(jSONObject2, "11E")) {
                            aVar2.n = jSONObject2.getJSONObject("11E");
                        }
                        if (t.a(jSONObject2, "11F")) {
                            aVar2.o = jSONObject2.getJSONObject("11F");
                        }
                        if (t.a(jSONObject2, "13A")) {
                            aVar2.q = jSONObject2.getJSONObject("13A");
                        }
                        if (t.a(jSONObject2, "13J")) {
                            aVar2.i = jSONObject2.getJSONObject("13J");
                        }
                        if (t.a(jSONObject2, "11G")) {
                            aVar2.p = jSONObject2.getJSONObject("11G");
                        }
                        if (t.a(jSONObject2, "001")) {
                            jSONObject4 = jSONObject2.getJSONObject("001");
                            dVar = new d();
                            if (jSONObject4 != null) {
                                obj = a(jSONObject4, "md5");
                                a2 = a(jSONObject4, "url");
                                a3 = a(jSONObject4, "sdkversion");
                                dVar.a = a2;
                                dVar.b = obj;
                                dVar.c = a3;
                            }
                            aVar2.u = dVar;
                        }
                        if (t.a(jSONObject2, "002")) {
                            jSONObject4 = jSONObject2.getJSONObject("002");
                            cVar = new c();
                            if (jSONObject4 != null) {
                                a = a(jSONObject4, "md5");
                                str2 = a(jSONObject4, "url");
                                cVar.b = a;
                                cVar.a = str2;
                            }
                            aVar2.v = cVar;
                        }
                        if (t.a(jSONObject2, "006")) {
                            aVar2.r = jSONObject2.getJSONObject("006");
                        }
                        if (t.a(jSONObject2, "010")) {
                            aVar2.s = jSONObject2.getJSONObject("010");
                        }
                        if (t.a(jSONObject2, "11Z")) {
                            jSONObject4 = jSONObject2.getJSONObject("11Z");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.w = bVar;
                        }
                        if (t.a(jSONObject2, "135")) {
                            aVar2.j = jSONObject2.getJSONObject("135");
                        }
                        if (t.a(jSONObject2, "13S")) {
                            aVar2.g = jSONObject2.getJSONObject("13S");
                        }
                        if (t.a(jSONObject2, "121")) {
                            jSONObject4 = jSONObject2.getJSONObject("121");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.x = bVar;
                        }
                        if (t.a(jSONObject2, "122")) {
                            jSONObject4 = jSONObject2.getJSONObject("122");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.y = bVar;
                        }
                        if (t.a(jSONObject2, "123")) {
                            jSONObject4 = jSONObject2.getJSONObject("123");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.z = bVar;
                        }
                        if (t.a(jSONObject2, "011")) {
                            aVar2.c = jSONObject2.getJSONObject("011");
                        }
                        if (t.a(jSONObject2, "012")) {
                            aVar2.d = jSONObject2.getJSONObject("012");
                        }
                        if (t.a(jSONObject2, "013")) {
                            aVar2.e = jSONObject2.getJSONObject("013");
                        }
                        if (t.a(jSONObject2, "014")) {
                            aVar2.f = jSONObject2.getJSONObject("014");
                        }
                    }
                }
            }
            return aVar2;
        }
        if (bArr != null) {
            if (TextUtils.isEmpty(str2)) {
                str2 = t.a(bArr);
            }
            try {
                jSONObject = new JSONObject(str2);
                if (jSONObject.has(NotificationCompat.CATEGORY_STATUS)) {
                    i = jSONObject.getInt(NotificationCompat.CATEGORY_STATUS);
                    if (i != 1) {
                        a = 1;
                    } else if (i == 0) {
                        str2 = "authcsid";
                        str3 = "authgsid";
                        if (boVar != null) {
                            str2 = boVar.c;
                            str3 = boVar.d;
                        }
                        t.a(context, str2, str3, jSONObject);
                        a = 0;
                        if (jSONObject.has("info")) {
                            b = jSONObject.getString("info");
                        }
                        str2 = StringUtils.EMPTY_STRING;
                        if (jSONObject.has("infocode")) {
                            str2 = jSONObject.getString("infocode");
                        }
                        z.a(sVar, "/v3/iasdkauth", b, str3, str2);
                        if (a == 0) {
                            aVar2.a = b;
                        }
                    }
                    try {
                        if (jSONObject.has("ver")) {
                            aVar2.b = jSONObject.getInt("ver");
                        }
                    } catch (Throwable th22) {
                        w.a(th22, "AuthConfigManager", "loadConfigVer");
                    }
                    if (t.a(jSONObject, "result")) {
                        aVar = new a();
                        aVar.a = false;
                        aVar.b = false;
                        aVar2.t = aVar;
                        jSONObject2 = jSONObject.getJSONObject("result");
                        if (t.a(jSONObject2, "11K")) {
                            try {
                                jSONObject3 = jSONObject2.getJSONObject("11K");
                                aVar.a = a(jSONObject3.getString("able"), false);
                                if (jSONObject3.has("off")) {
                                    aVar.c = jSONObject3.getJSONObject("off");
                                }
                            } catch (Throwable th222) {
                                w.a(th222, "AuthConfigManager", "loadException");
                            }
                        }
                        if (t.a(jSONObject2, "11B")) {
                            aVar2.h = jSONObject2.getJSONObject("11B");
                        }
                        if (t.a(jSONObject2, "11C")) {
                            aVar2.k = jSONObject2.getJSONObject("11C");
                        }
                        if (t.a(jSONObject2, "11I")) {
                            aVar2.l = jSONObject2.getJSONObject("11I");
                        }
                        if (t.a(jSONObject2, "11H")) {
                            aVar2.m = jSONObject2.getJSONObject("11H");
                        }
                        if (t.a(jSONObject2, "11E")) {
                            aVar2.n = jSONObject2.getJSONObject("11E");
                        }
                        if (t.a(jSONObject2, "11F")) {
                            aVar2.o = jSONObject2.getJSONObject("11F");
                        }
                        if (t.a(jSONObject2, "13A")) {
                            aVar2.q = jSONObject2.getJSONObject("13A");
                        }
                        if (t.a(jSONObject2, "13J")) {
                            aVar2.i = jSONObject2.getJSONObject("13J");
                        }
                        if (t.a(jSONObject2, "11G")) {
                            aVar2.p = jSONObject2.getJSONObject("11G");
                        }
                        if (t.a(jSONObject2, "001")) {
                            jSONObject4 = jSONObject2.getJSONObject("001");
                            dVar = new d();
                            if (jSONObject4 != null) {
                                try {
                                    obj = a(jSONObject4, "md5");
                                    a2 = a(jSONObject4, "url");
                                    a3 = a(jSONObject4, "sdkversion");
                                    if (!(TextUtils.isEmpty(obj) || TextUtils.isEmpty(a2) || TextUtils.isEmpty(a3))) {
                                        dVar.a = a2;
                                        dVar.b = obj;
                                        dVar.c = a3;
                                    }
                                } catch (Throwable th2222) {
                                    w.a(th2222, "ConfigManager", "parseSDKUpdate");
                                }
                            }
                            aVar2.u = dVar;
                        }
                        if (t.a(jSONObject2, "002")) {
                            jSONObject4 = jSONObject2.getJSONObject("002");
                            cVar = new c();
                            if (jSONObject4 != null) {
                                try {
                                    a = a(jSONObject4, "md5");
                                    str2 = a(jSONObject4, "url");
                                    cVar.b = a;
                                    cVar.a = str2;
                                } catch (Throwable th22222) {
                                    w.a(th22222, "ConfigManager", "parseSDKCoordinate");
                                }
                            }
                            aVar2.v = cVar;
                        }
                        if (t.a(jSONObject2, "006")) {
                            aVar2.r = jSONObject2.getJSONObject("006");
                        }
                        if (t.a(jSONObject2, "010")) {
                            aVar2.s = jSONObject2.getJSONObject("010");
                        }
                        if (t.a(jSONObject2, "11Z")) {
                            jSONObject4 = jSONObject2.getJSONObject("11Z");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.w = bVar;
                        }
                        if (t.a(jSONObject2, "135")) {
                            aVar2.j = jSONObject2.getJSONObject("135");
                        }
                        if (t.a(jSONObject2, "13S")) {
                            aVar2.g = jSONObject2.getJSONObject("13S");
                        }
                        if (t.a(jSONObject2, "121")) {
                            jSONObject4 = jSONObject2.getJSONObject("121");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.x = bVar;
                        }
                        if (t.a(jSONObject2, "122")) {
                            jSONObject4 = jSONObject2.getJSONObject("122");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.y = bVar;
                        }
                        if (t.a(jSONObject2, "123")) {
                            jSONObject4 = jSONObject2.getJSONObject("123");
                            bVar = new b();
                            a(jSONObject4, bVar);
                            aVar2.z = bVar;
                        }
                        if (t.a(jSONObject2, "011")) {
                            aVar2.c = jSONObject2.getJSONObject("011");
                        }
                        if (t.a(jSONObject2, "012")) {
                            aVar2.d = jSONObject2.getJSONObject("012");
                        }
                        if (t.a(jSONObject2, "013")) {
                            aVar2.e = jSONObject2.getJSONObject("013");
                        }
                        if (t.a(jSONObject2, "014")) {
                            aVar2.f = jSONObject2.getJSONObject("014");
                        }
                    }
                }
            } catch (Throwable th222222) {
                w.a(th222222, "AuthConfigManager", "loadConfig");
            }
        }
        return aVar2;
    }

    private static String a(JSONObject jSONObject, String str) throws JSONException {
        if (jSONObject == null) {
            return StringUtils.EMPTY_STRING;
        }
        String str2 = StringUtils.EMPTY_STRING;
        return (!jSONObject.has(str) || jSONObject.getString(str).equals("[]")) ? str2 : jSONObject.optString(str);
    }

    @Deprecated
    public static void a(String str) {
        k.b(str);
    }

    private static void a(JSONObject jSONObject, b bVar) {
        if (bVar != null) {
            try {
                String a = a(jSONObject, "m");
                String a2 = a(jSONObject, "u");
                String a3 = a(jSONObject, "v");
                String a4 = a(jSONObject, "able");
                bVar.c = a;
                bVar.b = a2;
                bVar.d = a3;
                bVar.a = a(a4, false);
            } catch (Throwable th) {
                w.a(th, "ConfigManager", "parsePluginEntity");
            }
        }
    }

    public static boolean a(String str, boolean z) {
        try {
            String[] split = URLDecoder.decode(str).split("/");
            return split[split.length + -1].charAt(RainSurfaceView.RAIN_LEVEL_RAINSTORM) % 2 == 1;
        } catch (Throwable th) {
            return z;
        }
    }
}
