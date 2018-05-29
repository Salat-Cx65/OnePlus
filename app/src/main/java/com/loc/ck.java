package com.loc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.autonavi.aps.amapapi.model.AMapLocationServer;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.json.JSONObject;

// compiled from: Cache.java
public final class ck {
    Hashtable<String, ArrayList<a>> a;
    boolean b;
    long c;
    String d;
    ce e;
    boolean f;
    boolean g;
    private long h;
    private boolean i;
    private String j;
    private String k;
    private String l;
    private long m;

    // compiled from: Cache.java
    static class a {
        private AMapLocationServer a;
        private String b;

        protected a() {
            this.a = null;
            this.b = null;
        }

        public final AMapLocationServer a() {
            return this.a;
        }

        public final void a(AMapLocationServer aMapLocationServer) {
            this.a = aMapLocationServer;
        }

        public final void a(String str) {
            if (TextUtils.isEmpty(str)) {
                this.b = null;
            } else {
                this.b = str.replace("##", "#");
            }
        }

        public final String b() {
            return this.b;
        }
    }

    public ck() {
        this.a = new Hashtable();
        this.h = 0;
        this.i = false;
        this.j = "2.0.201501131131".replace(".", StringUtils.EMPTY_STRING);
        this.k = null;
        this.b = true;
        this.c = 0;
        this.d = null;
        this.e = null;
        this.l = null;
        this.m = 0;
        this.f = true;
        this.g = true;
    }

    private AMapLocationServer a(String str, StringBuilder stringBuilder) {
        a aVar;
        a a;
        if (str.contains("cgiwifi")) {
            a = a(stringBuilder, str);
            if (a != null) {
                aVar = a;
            }
            aVar = a;
        } else if (str.contains("wifi")) {
            a = a(stringBuilder, str);
            if (a != null) {
                aVar = a;
            }
            aVar = a;
        } else {
            aVar = (str.contains("cgi") && this.a.containsKey(str)) ? (a) ((ArrayList) this.a.get(str)).get(0) : null;
        }
        if (aVar != null && de.a(aVar.a())) {
            AMapLocationServer a2 = aVar.a();
            a2.e("mem");
            a2.g(aVar.b());
            if (cv.b(a2.getTime())) {
                if (de.a(a2)) {
                    this.c = 0;
                }
                a2.setLocationType(RainSurfaceView.RAIN_LEVEL_RAINSTORM);
                return a2;
            }
        }
        return null;
    }

    private a a(StringBuilder stringBuilder, String str) {
        if (this.a.isEmpty() || TextUtils.isEmpty(stringBuilder)) {
            return null;
        }
        if (!this.a.containsKey(str)) {
            return null;
        }
        a aVar;
        Hashtable hashtable = new Hashtable();
        Hashtable hashtable2 = new Hashtable();
        Hashtable hashtable3 = new Hashtable();
        ArrayList arrayList = (ArrayList) this.a.get(str);
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            aVar = (a) arrayList.get(size);
            if (!TextUtils.isEmpty(aVar.b())) {
                boolean z;
                Object obj = null;
                String b = aVar.b();
                if (TextUtils.isEmpty(b) || TextUtils.isEmpty(stringBuilder)) {
                    z = false;
                } else {
                    if (b.contains(",access")) {
                        if (stringBuilder.indexOf(",access") != -1) {
                            String[] split = b.split(",access");
                            Object substring = split[0].contains("#") ? split[0].substring(split[0].lastIndexOf("#") + 1) : split[0];
                            z = TextUtils.isEmpty(substring) ? false : stringBuilder.toString().contains(substring + ",access");
                        }
                    }
                    z = false;
                }
                if (z) {
                    if (de.a(aVar.b(), stringBuilder.toString())) {
                        break;
                    }
                    int i = 1;
                }
                a(aVar.b(), hashtable);
                a(stringBuilder.toString(), hashtable2);
                hashtable3.clear();
                for (String b2 : hashtable.keySet()) {
                    hashtable3.put(b2, StringUtils.EMPTY_STRING);
                }
                for (String b22 : hashtable2.keySet()) {
                    hashtable3.put(b22, StringUtils.EMPTY_STRING);
                }
                Set keySet = hashtable3.keySet();
                double[] dArr = new double[keySet.size()];
                double[] dArr2 = new double[keySet.size()];
                Iterator it = keySet.iterator();
                int i2 = 0;
                while (it != null && it.hasNext()) {
                    b22 = (String) it.next();
                    dArr[i2] = hashtable.containsKey(b22) ? 4607182418800017408L : 0;
                    dArr2[i2] = hashtable2.containsKey(b22) ? 4607182418800017408L : 0;
                    i2++;
                }
                keySet.clear();
                double[] a = a(dArr, dArr2);
                if (a[0] < 0.800000011920929d && a[1] < 0.618d) {
                    if (obj != null && a[0] >= 0.618d) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        aVar = null;
        hashtable.clear();
        hashtable2.clear();
        hashtable3.clear();
        return aVar;
    }

    private String a(String str, StringBuilder stringBuilder, Context context) {
        if (context == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            if (this.k == null) {
                this.k = cj.a("MD5", k.c(context));
            }
            if (str.contains("&")) {
                str = str.substring(0, str.indexOf("&"));
            }
            String substring = str.substring(str.lastIndexOf("#") + 1);
            if (substring.equals("cgi")) {
                jSONObject.put("cgi", str.substring(0, str.length() - 12));
            } else if (!(TextUtils.isEmpty(stringBuilder) || stringBuilder.indexOf("access") == -1)) {
                jSONObject.put("cgi", str.substring(0, str.length() - (substring.length() + 9)));
                String[] split = stringBuilder.toString().split(",access");
                jSONObject.put("mmac", split[0].contains("#") ? split[0].substring(split[0].lastIndexOf("#") + 1) : split[0]);
            }
            try {
                return o.a(cj.c(jSONObject.toString().getBytes("UTF-8"), this.k));
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        } catch (Throwable th) {
            return null;
        }
    }

    private void a(Context context, String str) throws Exception {
        SQLiteDatabase openOrCreateDatabase;
        Throwable th;
        SQLiteDatabase sQLiteDatabase;
        Cursor cursor = null;
        if (cv.u() && context != null) {
            Cursor cursor2;
            try {
                openOrCreateDatabase = context.openOrCreateDatabase("hmdb", 0, null);
                try {
                } catch (Throwable th2) {
                    th = th2;
                    cursor2 = cursor;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    openOrCreateDatabase.close();
                    throw th;
                }
                if (de.a(openOrCreateDatabase, "hist")) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("SELECT feature, nb, loc FROM ");
                    stringBuilder.append("hist").append(this.j);
                    stringBuilder.append(" WHERE time > ").append(de.a() - cv.t());
                    if (str != null) {
                        stringBuilder.append(" and feature = '").append(str + "'");
                    }
                    stringBuilder.append(" ORDER BY time ASC;");
                    try {
                        cursor = openOrCreateDatabase.rawQuery(stringBuilder.toString(), null);
                        cursor2 = cursor;
                    } catch (Throwable th22) {
                        th = th22;
                        cursor2 = cursor;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        openOrCreateDatabase.close();
                        throw th;
                    }
                    try {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        if (this.k == null) {
                            this.k = cj.a("MD5", k.c(context));
                        }
                        if (cursor2 != null && cursor2.moveToFirst()) {
                            do {
                                JSONObject jSONObject;
                                JSONObject jSONObject2;
                                String str2;
                                if (cursor2.getString(0).startsWith("{")) {
                                    jSONObject = new JSONObject(cursor2.getString(0));
                                    stringBuilder2.delete(0, stringBuilder2.length());
                                    if (!TextUtils.isEmpty(cursor2.getString(1))) {
                                        stringBuilder2.append(cursor2.getString(1));
                                    } else if (de.a(jSONObject, "mmac")) {
                                        stringBuilder2.append("#").append(jSONObject.getString("mmac"));
                                        stringBuilder2.append(",access");
                                    }
                                    jSONObject2 = new JSONObject(cursor2.getString(RainSurfaceView.RAIN_LEVEL_SHOWER));
                                    if (de.a(jSONObject2, "type")) {
                                        jSONObject2.put("type", "new");
                                    }
                                } else {
                                    jSONObject = new JSONObject(new String(cj.d(o.b(cursor2.getString(0)), this.k), "UTF-8"));
                                    stringBuilder2.delete(0, stringBuilder2.length());
                                    if (!TextUtils.isEmpty(cursor2.getString(1))) {
                                        stringBuilder2.append(new String(cj.d(o.b(cursor2.getString(1)), this.k), "UTF-8"));
                                    } else if (de.a(jSONObject, "mmac")) {
                                        stringBuilder2.append("#").append(jSONObject.getString("mmac"));
                                        stringBuilder2.append(",access");
                                    }
                                    jSONObject2 = new JSONObject(new String(cj.d(o.b(cursor2.getString(RainSurfaceView.RAIN_LEVEL_SHOWER)), this.k), "UTF-8"));
                                    if (de.a(jSONObject2, "type")) {
                                        jSONObject2.put("type", "new");
                                    }
                                }
                                AMapLocationServer aMapLocationServer = new AMapLocationServer(StringUtils.EMPTY_STRING);
                                aMapLocationServer.b(jSONObject2);
                                String str3;
                                if (de.a(jSONObject, "mmac") && de.a(jSONObject, "cgi")) {
                                    str3 = (jSONObject.getString("cgi") + "#") + "network#";
                                    str2 = jSONObject.getString("cgi").contains("#") ? str3 + "cgiwifi" : str3 + "wifi";
                                } else if (de.a(jSONObject, "cgi")) {
                                    str3 = (jSONObject.getString("cgi") + "#") + "network#";
                                    if (jSONObject.getString("cgi").contains("#")) {
                                        str2 = str3 + "cgi";
                                    }
                                }
                                a(str2, stringBuilder2, aMapLocationServer, context, false);
                            } while (cursor2.moveToNext());
                            stringBuilder2.delete(0, stringBuilder2.length());
                            stringBuilder.delete(0, stringBuilder.length());
                        }
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        if (openOrCreateDatabase != null && openOrCreateDatabase.isOpen()) {
                            openOrCreateDatabase.close();
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } else if (openOrCreateDatabase != null && openOrCreateDatabase.isOpen()) {
                    openOrCreateDatabase.close();
                }
            } catch (Throwable th4) {
                th = th4;
                cursor2 = null;
                openOrCreateDatabase = null;
                if (cursor2 != null) {
                    cursor2.close();
                }
                openOrCreateDatabase.close();
                throw th;
            }
        }
    }

    private void a(String str, AMapLocation aMapLocation, StringBuilder stringBuilder, Context context) throws Exception {
        SQLiteDatabase sQLiteDatabase = null;
        if (context != null) {
            if (this.k == null) {
                this.k = cj.a("MD5", k.c(context));
            }
            String a = a(str, stringBuilder, context);
            StringBuilder stringBuilder2 = new StringBuilder();
            try {
                sQLiteDatabase = context.openOrCreateDatabase("hmdb", 0, null);
                stringBuilder2.append("CREATE TABLE IF NOT EXISTS hist");
                stringBuilder2.append(this.j);
                stringBuilder2.append(" (feature VARCHAR PRIMARY KEY, nb VARCHAR, loc VARCHAR, time VARCHAR);");
                sQLiteDatabase.execSQL(stringBuilder2.toString());
                stringBuilder2.delete(0, stringBuilder2.length());
                stringBuilder2.append("REPLACE INTO ");
                stringBuilder2.append("hist").append(this.j);
                stringBuilder2.append(" VALUES (?, ?, ?, ?)");
                Object[] objArr = new Object[]{a, cj.c(stringBuilder.toString().getBytes("UTF-8"), this.k), cj.c(aMapLocation.toStr().getBytes("UTF-8"), this.k), Long.valueOf(aMapLocation.getTime())};
                for (int i = 1; i < objArr.length - 1; i++) {
                    objArr[i] = o.a((byte[]) objArr[i]);
                }
                sQLiteDatabase.execSQL(stringBuilder2.toString(), objArr);
                stringBuilder2.delete(0, stringBuilder2.length());
                stringBuilder2.delete(0, stringBuilder2.length());
                if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                    sQLiteDatabase.close();
                }
            } catch (Throwable th) {
                try {
                    cw.a(th, "DB", "updateHist");
                    stringBuilder2.delete(0, stringBuilder2.length());
                    if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                        sQLiteDatabase.close();
                    }
                } catch (Throwable th2) {
                    stringBuilder2.delete(0, stringBuilder2.length());
                    if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                        sQLiteDatabase.close();
                    }
                }
            }
        }
    }

    private static void a(String str, Hashtable<String, String> hashtable) {
        if (!TextUtils.isEmpty(str)) {
            hashtable.clear();
            for (Object obj : str.split("#")) {
                if (!TextUtils.isEmpty(obj) && !obj.contains("|")) {
                    hashtable.put(obj, StringUtils.EMPTY_STRING);
                }
            }
        }
    }

    private static double[] a(double[] dArr, double[] dArr2) {
        int i;
        double[] dArr3 = new double[3];
        double d = 0.0d;
        double d2 = 0.0d;
        double d3 = 0.0d;
        int i2 = 0;
        int i3 = 0;
        for (i = 0; i < dArr.length; i++) {
            d2 += dArr[i] * dArr[i];
            d3 += dArr2[i] * dArr2[i];
            d += dArr[i] * dArr2[i];
            if (dArr2[i] == 1.0d) {
                i2++;
                if (dArr[i] == 1.0d) {
                    i3++;
                }
            }
        }
        dArr3[0] = d / (Math.sqrt(d3) * Math.sqrt(d2));
        dArr3[1] = (1.0d * ((double) i3)) / ((double) i2);
        dArr3[2] = (double) i3;
        for (i = 0; i < dArr3.length - 1; i++) {
            if (dArr3[i] > 1.0d) {
                dArr3[i] = 1.0d;
            }
        }
        return dArr3;
    }

    private boolean b() {
        return this.h == 0 ? false : this.a.size() > 360 ? true : de.b() - this.h > 36000000;
    }

    private void c() {
        this.h = 0;
        if (!this.a.isEmpty()) {
            this.a.clear();
        }
        this.i = false;
    }

    public final AMapLocationServer a(Context context, String str, StringBuilder stringBuilder, boolean z) {
        if (TextUtils.isEmpty(str) || !cv.u()) {
            return null;
        }
        String str2 = str + "&" + this.f + "&" + this.g;
        if (str2.contains(GeocodeSearch.GPS) || !cv.u() || TextUtils.isEmpty(stringBuilder)) {
            return null;
        }
        if (b()) {
            c();
            return null;
        }
        if (z && !this.i) {
            try {
                String a = a(str2, stringBuilder, context);
                c();
                a(context, a);
            } catch (Throwable th) {
            }
        }
        return !this.a.isEmpty() ? a(str2, stringBuilder) : null;
    }

    public final AMapLocationServer a(cf cfVar, boolean z, AMapLocationServer aMapLocationServer, ci ciVar, StringBuilder stringBuilder, String str, Context context) {
        Object obj = (this.b && cv.u()) ? 1 : null;
        obj = obj == null ? null : (aMapLocationServer == null || cv.b(aMapLocationServer.getTime())) ? 1 : null;
        if (obj == null) {
            return null;
        }
        try {
            Object obj2;
            boolean z2;
            ce c = cfVar.c();
            if (!(c == null && this.e == null) && (this.e == null || !this.e.a(c))) {
                int i = 1;
            } else {
                Object obj3 = null;
            }
            if (aMapLocationServer != null) {
                obj = (aMapLocationServer.getAccuracy() <= 299.0f || ciVar.b().size() <= 5) ? null : 1;
                obj2 = obj;
            } else {
                obj2 = null;
            }
            if (aMapLocationServer == null || this.d == null || obj2 != null || r3 != null) {
                z2 = false;
            } else {
                z2 = de.a(this.d, stringBuilder.toString());
                if ((z2 || (this.c != 0 && de.b() - this.c < 3000)) && de.a(aMapLocationServer)) {
                    aMapLocationServer.e("mem");
                    aMapLocationServer.setLocationType(RainSurfaceView.RAIN_LEVEL_SHOWER);
                    return aMapLocationServer;
                }
            }
            if (z2) {
                this.c = 0;
            } else {
                this.c = de.b();
            }
            if (this.l == null || str.equals(this.l)) {
                if (this.l == null) {
                    this.m = de.a();
                    this.l = str;
                } else {
                    this.m = de.a();
                }
            } else if (de.a() - this.m < 3000) {
                str = this.l;
            } else {
                this.m = de.a();
                this.l = str;
            }
            aMapLocationServer = null;
            if (obj2 == null && !z) {
                aMapLocationServer = a(context, str, stringBuilder, false);
            }
            if ((!z && !de.a(aMapLocationServer)) || obj2 != null) {
                return null;
            }
            if (z) {
                return null;
            }
            this.c = 0;
            aMapLocationServer.setLocationType(RainSurfaceView.RAIN_LEVEL_RAINSTORM);
            return aMapLocationServer;
        } catch (Throwable th) {
            return null;
        }
    }

    public final void a() {
        this.c = 0;
        this.d = null;
    }

    public final void a(Context context) {
        if (!this.i) {
            try {
                c();
                a(context, null);
            } catch (Throwable th) {
                cw.a(th, "Cache", "loadDB");
            }
            this.i = true;
        }
    }

    public final void a(AMapLocationClientOption aMapLocationClientOption) {
        this.g = aMapLocationClientOption.isNeedAddress();
        this.f = aMapLocationClientOption.isOffset();
        this.b = aMapLocationClientOption.isLocationCacheEnable();
    }

    public final void a(ce ceVar) {
        this.e = ceVar;
    }

    public final void a(String str) {
        this.d = str;
    }

    public final void a(String str, StringBuilder stringBuilder, AMapLocationServer aMapLocationServer, Context context, boolean z) {
        int i = 0;
        if (de.a(aMapLocationServer)) {
            int i2;
            String str2 = str + "&" + aMapLocationServer.isOffset() + "&" + aMapLocationServer.i();
            if (TextUtils.isEmpty(str2) || !de.a(aMapLocationServer)) {
                i2 = 0;
            } else if (str2.startsWith("#")) {
                i2 = 0;
            } else if (str2.contains("network")) {
                boolean z2 = true;
            } else {
                i2 = 0;
            }
            if (i2 != 0 && !aMapLocationServer.e().equals("mem") && !aMapLocationServer.e().equals("file") && !aMapLocationServer.d().equals("-3")) {
                if (b()) {
                    c();
                }
                JSONObject f = aMapLocationServer.f();
                if (de.a(f, "offpct")) {
                    f.remove("offpct");
                    aMapLocationServer.a(f);
                }
                if (str2.contains("wifi")) {
                    if (!TextUtils.isEmpty(stringBuilder)) {
                        if (aMapLocationServer.getAccuracy() >= 300.0f) {
                            String[] split = stringBuilder.toString().split("#");
                            int length = split.length;
                            i2 = 0;
                            while (i < length) {
                                if (split[i].contains(",")) {
                                    i2++;
                                }
                                i++;
                            }
                            if (i2 >= 8) {
                                return;
                            }
                        } else if (aMapLocationServer.getAccuracy() <= 10.0f) {
                            return;
                        }
                        if (str2.contains("cgiwifi") && !TextUtils.isEmpty(aMapLocationServer.g())) {
                            String replace = str2.replace("cgiwifi", "cgi");
                            AMapLocationServer h = aMapLocationServer.h();
                            if (de.a(h)) {
                                a(replace, new StringBuilder(), h, context, true);
                            }
                        }
                    } else {
                        return;
                    }
                } else if (str2.contains("cgi")) {
                    if (stringBuilder.indexOf(",") != -1) {
                        return;
                    }
                    if (aMapLocationServer.d().equals("4")) {
                        return;
                    }
                }
                AMapLocationServer a = a(str2, stringBuilder);
                if (!de.a(a) || !a.toStr().equals(aMapLocationServer.toStr(RainSurfaceView.RAIN_LEVEL_DOWNPOUR))) {
                    this.h = de.b();
                    a aVar = new a();
                    aVar.a(aMapLocationServer);
                    aVar.a(TextUtils.isEmpty(stringBuilder) ? null : stringBuilder.toString());
                    if (this.a.containsKey(str2)) {
                        ((ArrayList) this.a.get(str2)).add(aVar);
                    } else {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(aVar);
                        this.a.put(str2, arrayList);
                    }
                    if (z) {
                        try {
                            a(str2, (AMapLocation) aMapLocationServer, stringBuilder, context);
                        } catch (Throwable th) {
                            cw.a(th, "Cache", "add");
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(android.content.Context r9) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.ck.b(android.content.Context):void");
        /*
        this = this;
        r1 = 0;
        if (r9 == 0) goto L_0x001e;
    L_0x0003:
        r0 = "hmdb";
        r2 = 0;
        r3 = 0;
        r1 = r9.openOrCreateDatabase(r0, r2, r3);	 Catch:{ Throwable -> 0x007e }
        r0 = "hist";
        r0 = com.loc.de.a(r1, r0);	 Catch:{ Throwable -> 0x007e }
        if (r0 != 0) goto L_0x0029;
    L_0x0013:
        if (r1 == 0) goto L_0x001e;
    L_0x0015:
        r0 = r1.isOpen();	 Catch:{ Throwable -> 0x007e }
        if (r0 == 0) goto L_0x001e;
    L_0x001b:
        r1.close();	 Catch:{ Throwable -> 0x007e }
    L_0x001e:
        r0 = 0;
        r8.i = r0;	 Catch:{ Throwable -> 0x005d }
        r0 = 0;
        r8.d = r0;	 Catch:{ Throwable -> 0x005d }
        r0 = 0;
        r8.m = r0;	 Catch:{ Throwable -> 0x005d }
    L_0x0028:
        return;
    L_0x0029:
        r0 = "time<?";
        r2 = 1;
        r2 = new java.lang.String[r2];	 Catch:{ Throwable -> 0x007e }
        r3 = 0;
        r4 = com.loc.de.a();	 Catch:{ Throwable -> 0x007e }
        r6 = 86400000; // 0x5265c00 float:7.82218E-36 double:4.2687272E-316;
        r4 = r4 - r6;
        r4 = java.lang.String.valueOf(r4);	 Catch:{ Throwable -> 0x007e }
        r2[r3] = r4;	 Catch:{ Throwable -> 0x007e }
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0066 }
        r4 = "hist";
        r3.<init>(r4);	 Catch:{ Throwable -> 0x0066 }
        r4 = r8.j;	 Catch:{ Throwable -> 0x0066 }
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x0066 }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x0066 }
        r1.delete(r3, r0, r2);	 Catch:{ Throwable -> 0x0066 }
    L_0x0051:
        if (r1 == 0) goto L_0x001e;
    L_0x0053:
        r0 = r1.isOpen();	 Catch:{ Throwable -> 0x005d }
        if (r0 == 0) goto L_0x001e;
    L_0x0059:
        r1.close();	 Catch:{ Throwable -> 0x005d }
        goto L_0x001e;
    L_0x005d:
        r0 = move-exception;
        r1 = "Cache";
        r2 = "destroy part";
        com.loc.cw.a(r0, r1, r2);
        goto L_0x0028;
    L_0x0066:
        r0 = move-exception;
        r2 = "DB";
        r3 = "clearHist";
        com.loc.cw.a(r0, r2, r3);	 Catch:{ Throwable -> 0x007e }
        r0 = r0.getMessage();	 Catch:{ Throwable -> 0x007e }
        r2 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Throwable -> 0x007e }
        if (r2 != 0) goto L_0x0051;
    L_0x0078:
        r2 = "no such table";
        r0.contains(r2);	 Catch:{ Throwable -> 0x007e }
        goto L_0x0051;
    L_0x007e:
        r0 = move-exception;
        r2 = "DB";
        r3 = "clearHist p2";
        com.loc.cw.a(r0, r2, r3);	 Catch:{ all -> 0x0092 }
        if (r1 == 0) goto L_0x001e;
    L_0x0088:
        r0 = r1.isOpen();	 Catch:{ Throwable -> 0x005d }
        if (r0 == 0) goto L_0x001e;
    L_0x008e:
        r1.close();	 Catch:{ Throwable -> 0x005d }
        goto L_0x001e;
    L_0x0092:
        r0 = move-exception;
        if (r1 == 0) goto L_0x009e;
    L_0x0095:
        r2 = r1.isOpen();	 Catch:{ Throwable -> 0x005d }
        if (r2 == 0) goto L_0x009e;
    L_0x009b:
        r1.close();	 Catch:{ Throwable -> 0x005d }
    L_0x009e:
        throw r0;	 Catch:{ Throwable -> 0x005d }
        */
    }
}
