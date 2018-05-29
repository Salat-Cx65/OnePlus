package com.loc;

import android.content.Context;
import android.net.ConnectivityManager;
import com.amap.api.location.AMapLocationClientOption;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

// compiled from: CollectionManager.java
public final class cg {
    public String a;
    private Object b;
    private boolean c;
    private Timer d;
    private TimerTask e;
    private int f;
    private boolean g;
    private cr h;
    private ci i;
    private ConnectivityManager j;
    private long k;
    private Context l;
    private JSONObject m;

    // compiled from: CollectionManager.java
    class a implements Runnable {
        a() {
        }

        public final void run() {
            try {
                cz.a(cg.this.b, "destroy", new Object[0]);
                cg.this.b = null;
            } catch (Throwable th) {
                cw.a(th, "CollectionManager", "stop3rdCM");
            }
        }
    }

    public cg() {
        this.b = null;
        this.c = false;
        this.a = "com.data.carrier_v4.CollectorManager";
        this.d = null;
        this.e = null;
        this.f = 0;
        this.g = true;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = 0;
        this.l = null;
        this.m = null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(com.loc.cg r9, int r10) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cg.a(com.loc.cg, int):void");
        /*
        r1 = 674234367; // 0x282fffff float:9.769962E-15 double:3.33116038E-315;
        r0 = 70254591; // 0x42fffff float:2.0688699E-36 double:3.471038E-316;
        r8 = 3;
        r2 = r9.j();
        if (r2 == 0) goto L_0x0013;
    L_0x000d:
        r2 = com.loc.cv.v();
        if (r2 != 0) goto L_0x0014;
    L_0x0013:
        return;
    L_0x0014:
        switch(r10) {
            case 0: goto L_0x009a;
            case 1: goto L_0x0018;
            case 2: goto L_0x009d;
            default: goto L_0x0017;
        };
    L_0x0017:
        r1 = r0;
    L_0x0018:
        r0 = r9.b;	 Catch:{ Throwable -> 0x00b1 }
        r2 = "callBackWrapData";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x00b1 }
        r4 = 0;
        r5 = new org.json.JSONObject;	 Catch:{ Throwable -> 0x00b1 }
        r5.<init>();	 Catch:{ Throwable -> 0x00b1 }
        r6 = "e";
        r7 = 1;
        r5.put(r6, r7);	 Catch:{ Throwable -> 0x00b1 }
        r6 = "d";
        r5.put(r6, r1);	 Catch:{ Throwable -> 0x00b1 }
        r6 = "u";
        r7 = 1;
        r5.put(r6, r7);	 Catch:{ Throwable -> 0x00b1 }
        r5 = r5.toString();	 Catch:{ Throwable -> 0x00b1 }
        r3[r4] = r5;	 Catch:{ Throwable -> 0x00b1 }
        com.loc.cz.a(r0, r2, r3);	 Catch:{ Throwable -> 0x00b1 }
    L_0x003f:
        r0 = r9.b;	 Catch:{ Throwable -> 0x00b6 }
        r2 = "getReportDate";
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x00b6 }
        r0 = com.loc.cz.a(r0, r2, r3);	 Catch:{ Throwable -> 0x00b6 }
    L_0x004a:
        if (r0 == 0) goto L_0x007d;
    L_0x004c:
        r0 = (byte[]) r0;	 Catch:{ Throwable -> 0x0090 }
        r0 = (byte[]) r0;	 Catch:{ Throwable -> 0x0090 }
        r2 = r9.h;	 Catch:{ Throwable -> 0x0090 }
        r3 = r9.l;	 Catch:{ Throwable -> 0x0090 }
        r4 = "http://cgicol.amap.com/collection/writedata?ver=v1.0_ali&";
        r0 = r2.b(r0, r3, r4);	 Catch:{ Throwable -> 0x0090 }
        r2 = r9.j();	 Catch:{ Throwable -> 0x0090 }
        if (r2 == 0) goto L_0x007d;
    L_0x0060:
        r2 = r9.b;	 Catch:{ Throwable -> 0x00b9 }
        r3 = "setUploadResult";
        r4 = 2;
        r4 = new java.lang.Object[r4];	 Catch:{ Throwable -> 0x00b9 }
        r5 = 0;
        r4[r5] = r0;	 Catch:{ Throwable -> 0x00b9 }
        r0 = 1;
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ Throwable -> 0x00b9 }
        r4[r0] = r1;	 Catch:{ Throwable -> 0x00b9 }
        r0 = com.loc.cz.a(r2, r3, r4);	 Catch:{ Throwable -> 0x00b9 }
        r0 = (java.lang.Integer) r0;	 Catch:{ Throwable -> 0x00b9 }
        r0 = r0.intValue();	 Catch:{ Throwable -> 0x00b9 }
        r9.f = r0;	 Catch:{ Throwable -> 0x00b9 }
    L_0x007d:
        r9.e();	 Catch:{ Throwable -> 0x0090 }
        r0 = r9.j();	 Catch:{ Throwable -> 0x0090 }
        if (r0 == 0) goto L_0x00be;
    L_0x0086:
        r0 = r9.k();	 Catch:{ Throwable -> 0x0090 }
        if (r0 != 0) goto L_0x00be;
    L_0x008c:
        r9.i();	 Catch:{ Throwable -> 0x0090 }
        goto L_0x0013;
    L_0x0090:
        r0 = move-exception;
        r1 = "CollectionManager";
        r2 = "up";
        com.loc.cw.a(r0, r1, r2);
        goto L_0x0013;
    L_0x009a:
        r1 = r0;
        goto L_0x0018;
    L_0x009d:
        r0 = r9.i;	 Catch:{ Throwable -> 0x0090 }
        if (r0 == 0) goto L_0x00ab;
    L_0x00a1:
        r0 = r9.i;	 Catch:{ Throwable -> 0x0090 }
        r2 = r9.j;	 Catch:{ Throwable -> 0x0090 }
        r0 = r0.a(r2);	 Catch:{ Throwable -> 0x0090 }
        if (r0 == 0) goto L_0x0018;
    L_0x00ab:
        r0 = 2083520511; // 0x7c2fffff float:3.6553767E36 double:1.029395907E-314;
        r1 = r0;
        goto L_0x0018;
    L_0x00b1:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ Throwable -> 0x0090 }
        goto L_0x003f;
    L_0x00b6:
        r0 = move-exception;
        r0 = 0;
        goto L_0x004a;
    L_0x00b9:
        r0 = move-exception;
        r0 = 3;
        r9.f = r0;	 Catch:{ Throwable -> 0x0090 }
        goto L_0x007d;
    L_0x00be:
        r0 = r9.f;	 Catch:{ Throwable -> 0x0090 }
        if (r0 < r8) goto L_0x0013;
    L_0x00c2:
        r9.i();	 Catch:{ Throwable -> 0x0090 }
        goto L_0x0013;
        */
    }

    private boolean g() {
        try {
            return !j() ? false : h();
        } catch (Throwable th) {
            cw.a(th, "CollectionManager", "collStarted");
            return false;
        }
    }

    private boolean h() {
        try {
            return ((Boolean) cz.a(this.b, "isStarted", new Object[0])).booleanValue();
        } catch (Throwable th) {
            return false;
        }
    }

    private void i() {
        if (this.e != null) {
            this.e.cancel();
            this.e = null;
        }
        if (this.d != null) {
            this.d.cancel();
            this.d.purge();
            this.d = null;
        }
    }

    private boolean j() {
        return this.b != null;
    }

    private int k() {
        try {
            return ((Integer) cz.a(this.b, "getLeftUploadNum", new Object[0])).intValue();
        } catch (Throwable th) {
            return 0;
        }
    }

    public final void a() {
        if (j()) {
            try {
                cz.a(this.b, "getColUpHist", new Object[0]);
            } catch (Throwable th) {
            }
        }
    }

    public final void a(Context context, cr crVar, ci ciVar, AMapLocationClientOption aMapLocationClientOption, ConnectivityManager connectivityManager) {
        Object obj = 1;
        if (cv.v()) {
            JSONObject a = cw.a(aMapLocationClientOption);
            try {
                if (this.b == null) {
                    this.l = context;
                    this.h = crVar;
                    this.i = ciVar;
                    this.m = a;
                    this.j = connectivityManager;
                    if (!this.c) {
                        this.k = de.b();
                        s a2 = cw.a("Collection", "1.0.0");
                        this.c = db.a(context, a2);
                        if (this.c) {
                            try {
                                this.b = au.a(context, a2, this.a, null, new Class[]{Context.class}, new Object[]{context});
                            } catch (Throwable th) {
                            }
                        } else {
                            this.c = true;
                        }
                    }
                }
            } catch (Throwable th2) {
                cw.a(th2, "CollectionManager", "initCollection");
            }
            if (!this.g || g() || !j()) {
                return;
            }
            if (cv.v()) {
                if (de.a(this.m, "coll")) {
                    try {
                        Object obj2;
                        if (this.m.getString("coll").equals("0")) {
                            obj2 = null;
                        } else {
                            int i = 1;
                        }
                        obj = obj2;
                    } catch (Throwable th22) {
                        cw.a(th22, "CollectionManager", "start3rdCM");
                    }
                }
                if (obj == null) {
                    b();
                    return;
                } else if (!g()) {
                    try {
                        e();
                        try {
                            cz.a(this.b, "start", new Object[0]);
                        } catch (Throwable th3) {
                        }
                        if (this.l != null) {
                            return;
                        }
                        return;
                    } catch (Throwable th222) {
                        cw.a(th222, "CollectionManager", "start3rdCM");
                    }
                } else {
                    return;
                }
            }
            b();
        }
    }

    public final void a(boolean z) {
        this.g = z;
    }

    public final void b() {
        if (j() && g()) {
            try {
                cz.a(this.b, "destroy", new Object[0]);
            } catch (Throwable th) {
                cw.a(th, "CollectionManager", "stop3rdCM");
            }
            this.b = null;
            i();
        }
    }

    public final void c() {
        if (j() && g()) {
            z.b().submit(new a());
            i();
        }
    }

    public final void d() {
        if (!this.g || this.i == null || !this.i.a(this.j) || !j()) {
            return;
        }
        if (cv.v()) {
            if (this.e == null) {
                this.e = new TimerTask() {
                    final /* synthetic */ int a;

                    {
                        this.a = 2;
                    }

                    public final void run() {
                        try {
                            Thread.currentThread().setPriority(1);
                            long b = de.b() - cg.this.k;
                            if (cg.this.j() && cg.this.k() == 0) {
                                cg.this.i();
                            }
                            if (b >= 10000) {
                                if (cg.this.i == null || !cg.this.i.a(cg.this.j)) {
                                    cg.this.i();
                                } else {
                                    cg.a(cg.this, this.a);
                                }
                            }
                        } catch (Throwable th) {
                            cw.a(th, "CollectionManager", "timerTaskU run");
                        }
                    }
                };
            }
            if (this.d == null) {
                this.d = new Timer("T-U", false);
                this.d.schedule(this.e, 2000, 2000);
                return;
            }
            return;
        }
        i();
    }

    public final void e() {
        if (!j() || !j() || k() <= 0) {
        }
    }

    public final String f() {
        try {
            if (this.b != null) {
                return (String) cz.a(this.b, "getInnerString", "version");
            }
        } catch (Throwable th) {
            cw.a(th, "CollectionManager", "getCollVer");
        }
        return null;
    }
}
