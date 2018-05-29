package com.loc;

import android.content.Context;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

// compiled from: LastLocationManager.java
public final class h {
    String a;
    cn b;
    cn c;
    cn d;
    af e;
    long f;
    boolean g;
    private Context h;

    public h(Context context) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = 0;
        this.g = false;
        this.h = context.getApplicationContext();
    }

    public final void a() {
        if (!this.g) {
            try {
                if (this.a == null) {
                    this.a = cj.a("MD5", n.q(this.h));
                }
                if (this.e == null) {
                    this.e = new af(this.h, af.a(co.class), de.i());
                }
            } catch (Throwable th) {
                cw.a(th, "LastLocationManager", "<init>:DBOperation");
            }
            this.g = true;
        }
    }

    public final boolean a(AMapLocation aMapLocation, String str) {
        if (this.h == null || aMapLocation == null || !de.a(aMapLocation) || aMapLocation.getLocationType() == 2 || aMapLocation.isMock() || aMapLocation.isFixLastLocation()) {
            return false;
        }
        cn cnVar = new cn();
        cnVar.a(aMapLocation);
        if (aMapLocation.getLocationType() == 1) {
            cnVar.a(null);
        } else {
            cnVar.a(str);
        }
        try {
            this.b = cnVar;
            this.c = cnVar;
            return (this.d == null || de.a(this.d.a(), cnVar.a()) > 500.0f) && de.b() - this.f > 30000;
        } catch (Throwable th) {
            cw.a(th, "LastLocationManager", "setLastFix");
            return false;
        }
    }

    public final AMapLocation b(AMapLocation aMapLocation, String str) {
        if (aMapLocation == null || aMapLocation.getErrorCode() == 0 || aMapLocation.getLocationType() == 1 || aMapLocation.getErrorCode() == 7) {
            return aMapLocation;
        }
        if (this.b == null) {
            this.b = d();
        }
        if (this.b == null || this.b.a() == null) {
            return aMapLocation;
        }
        if (TextUtils.isEmpty(str)) {
            long b = de.b() - this.b.d();
            if (b < 0 || b > 30000) {
                return aMapLocation;
            }
            aMapLocation = this.b.a();
            aMapLocation.setLocationType(RainSurfaceView.RAIN_LEVEL_RAINSTORM);
            aMapLocation.setFixLastLocation(true);
            return aMapLocation;
        } else if (!de.a(this.b.b(), str)) {
            return aMapLocation;
        } else {
            aMapLocation = this.b.a();
            aMapLocation.setLocationType(RainSurfaceView.RAIN_LEVEL_RAINSTORM);
            aMapLocation.setFixLastLocation(true);
            return aMapLocation;
        }
    }

    public final void b() {
        try {
            c();
            this.f = 0;
            this.g = false;
            this.b = null;
            this.c = null;
            this.d = null;
        } catch (Throwable th) {
            cw.a(th, "LastLocationManager", "destroy");
        }
    }

    public final void c() {
        String str = null;
        try {
            a();
            if (this.c != null && de.a(this.c.a()) && this.e != null && this.c != this.d && this.c.d() == 0) {
                Object toStr = this.c.a().toStr();
                Object b = this.c.b();
                this.d = this.c;
                if (TextUtils.isEmpty(toStr)) {
                    toStr = null;
                } else {
                    toStr = o.a(cj.c(toStr.getBytes("UTF-8"), this.a));
                    if (!TextUtils.isEmpty(b)) {
                        str = o.a(cj.c(b.getBytes("UTF-8"), this.a));
                    }
                }
                if (!TextUtils.isEmpty(toStr)) {
                    b = new cn();
                    b.b(toStr);
                    b.a(de.b());
                    b.a(str);
                    this.e.a(b, "_id=1");
                    this.f = de.b();
                }
            }
        } catch (Throwable th) {
            cw.a(th, "LastLocationManager", "saveLastFix");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final com.loc.cn d() {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.h.d():com.loc.cn");
        /*
        this = this;
        r1 = 0;
        r0 = r6.h;
        if (r0 != 0) goto L_0x0007;
    L_0x0005:
        r0 = r1;
    L_0x0006:
        return r0;
    L_0x0007:
        r6.a();
        r0 = r6.e;	 Catch:{ Throwable -> 0x0095 }
        if (r0 != 0) goto L_0x0010;
    L_0x000e:
        r0 = r1;
        goto L_0x0006;
    L_0x0010:
        r0 = r6.e;	 Catch:{ Throwable -> 0x0095 }
        r2 = "_id=1";
        r3 = com.loc.cn.class;
        r0 = r0.b(r2, r3);	 Catch:{ Throwable -> 0x0095 }
        if (r0 == 0) goto L_0x009c;
    L_0x001c:
        r2 = r0.size();	 Catch:{ Throwable -> 0x0095 }
        if (r2 <= 0) goto L_0x009c;
    L_0x0022:
        r2 = 0;
        r0 = r0.get(r2);	 Catch:{ Throwable -> 0x0095 }
        r0 = (com.loc.cn) r0;	 Catch:{ Throwable -> 0x0095 }
        r2 = r0.c();	 Catch:{ Throwable -> 0x008b }
        r2 = com.loc.o.b(r2);	 Catch:{ Throwable -> 0x008b }
        if (r2 == 0) goto L_0x009a;
    L_0x0033:
        r3 = r2.length;	 Catch:{ Throwable -> 0x008b }
        if (r3 <= 0) goto L_0x009a;
    L_0x0036:
        r3 = r6.a;	 Catch:{ Throwable -> 0x008b }
        r3 = com.loc.cj.d(r2, r3);	 Catch:{ Throwable -> 0x008b }
        if (r3 == 0) goto L_0x009a;
    L_0x003e:
        r2 = r3.length;	 Catch:{ Throwable -> 0x008b }
        if (r2 <= 0) goto L_0x009a;
    L_0x0041:
        r2 = new java.lang.String;	 Catch:{ Throwable -> 0x008b }
        r4 = "UTF-8";
        r2.<init>(r3, r4);	 Catch:{ Throwable -> 0x008b }
    L_0x0048:
        r3 = r0.b();	 Catch:{ Throwable -> 0x008b }
        r3 = com.loc.o.b(r3);	 Catch:{ Throwable -> 0x008b }
        if (r3 == 0) goto L_0x0067;
    L_0x0052:
        r4 = r3.length;	 Catch:{ Throwable -> 0x008b }
        if (r4 <= 0) goto L_0x0067;
    L_0x0055:
        r4 = r6.a;	 Catch:{ Throwable -> 0x008b }
        r3 = com.loc.cj.d(r3, r4);	 Catch:{ Throwable -> 0x008b }
        if (r3 == 0) goto L_0x0067;
    L_0x005d:
        r4 = r3.length;	 Catch:{ Throwable -> 0x008b }
        if (r4 <= 0) goto L_0x0067;
    L_0x0060:
        r1 = new java.lang.String;	 Catch:{ Throwable -> 0x008b }
        r4 = "UTF-8";
        r1.<init>(r3, r4);	 Catch:{ Throwable -> 0x008b }
    L_0x0067:
        r0.a(r1);	 Catch:{ Throwable -> 0x008b }
        r1 = r2;
    L_0x006b:
        r2 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Throwable -> 0x008b }
        if (r2 != 0) goto L_0x0006;
    L_0x0071:
        r2 = new com.amap.api.location.AMapLocation;	 Catch:{ Throwable -> 0x008b }
        r3 = "";
        r2.<init>(r3);	 Catch:{ Throwable -> 0x008b }
        r3 = new org.json.JSONObject;	 Catch:{ Throwable -> 0x008b }
        r3.<init>(r1);	 Catch:{ Throwable -> 0x008b }
        com.loc.cw.a(r2, r3);	 Catch:{ Throwable -> 0x008b }
        r1 = com.loc.de.b(r2);	 Catch:{ Throwable -> 0x008b }
        if (r1 == 0) goto L_0x0006;
    L_0x0086:
        r0.a(r2);	 Catch:{ Throwable -> 0x008b }
        goto L_0x0006;
    L_0x008b:
        r1 = move-exception;
    L_0x008c:
        r2 = "LastLocationManager";
        r3 = "readLastFix";
        com.loc.cw.a(r1, r2, r3);
        goto L_0x0006;
    L_0x0095:
        r0 = move-exception;
        r5 = r0;
        r0 = r1;
        r1 = r5;
        goto L_0x008c;
    L_0x009a:
        r2 = r1;
        goto L_0x0048;
    L_0x009c:
        r0 = r1;
        goto L_0x006b;
        */
    }
}
