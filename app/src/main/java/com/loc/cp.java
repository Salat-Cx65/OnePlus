package com.loc;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.net.Proxy;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.util.concurrent.ExecutorService;
import net.oneplus.weather.util.StringUtils;

// compiled from: DNSManager.java
public final class cp {
    private static cp c;
    cs a;
    volatile int b;
    private Object d;
    private Context e;
    private ExecutorService f;
    private boolean g;
    private boolean h;

    // compiled from: DNSManager.java
    class a implements Runnable {
        cs a;

        a(cs csVar) {
            this.a = null;
            this.a = csVar;
        }

        public final void run() {
            cp cpVar = cp.this;
            cp.this++;
            cp.this.b(this.a);
            cpVar = cp.this;
            cp.this--;
        }
    }

    static {
        c = null;
    }

    private cp() {
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = false;
        this.h = true;
        this.a = null;
        this.b = 0;
    }

    private cp(Context context) {
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = false;
        this.h = true;
        this.a = null;
        this.b = 0;
        this.e = context;
        Context context2 = this.e;
        try {
            s a = cw.a("HttpDNS", "1.0.0");
            if (db.a(context2, a)) {
                try {
                    this.d = au.a(context2, a, "com.autonavi.httpdns.HttpDnsManager", null, new Class[]{Context.class}, new Object[]{context2});
                } catch (Throwable th) {
                }
                db.a(context2, "HttpDns", this.d == null ? 0 : 1);
            }
        } catch (Throwable th2) {
            cw.a(th2, "DNSManager", "initHttpDns");
        }
    }

    public static cp a(Context context) {
        if (c == null) {
            c = new cp(context);
        }
        return c;
    }

    private boolean c() {
        return (this.d == null || e() || dd.b(this.e, "pref", "dns_faile_count_total", 0) >= 2) ? false : true;
    }

    private String d() {
        if (c()) {
            try {
                return (String) cz.a(this.d, "getIpByHostAsync", "apilocatesrc.amap.com");
            } catch (Throwable th) {
                db.a(this.e, "HttpDns");
            }
        }
        return null;
    }

    private boolean e() {
        int parseInt;
        String str = null;
        try {
            if (VERSION.SDK_INT >= 14) {
                boolean z = true;
            } else {
                Object obj = null;
            }
            if (z) {
                str = System.getProperty("http.proxyHost");
                String property = System.getProperty("http.proxyPort");
                if (property == null) {
                    property = "-1";
                }
                parseInt = Integer.parseInt(property);
            } else {
                str = Proxy.getHost(this.e);
                parseInt = Proxy.getPort(this.e);
            }
        } catch (Throwable th) {
            th.printStackTrace();
            parseInt = -1;
        }
        return (str == null || parseInt == -1) ? false : true;
    }

    public final void a() {
        if (this.g) {
            dd.a(this.e, "pref", "dns_faile_count_total", 0);
        }
    }

    public final void a(cs csVar) {
        try {
            this.g = false;
            if (csVar != null) {
                this.a = csVar;
                String c = csVar.c();
                if (!c.substring(0, c.indexOf(":")).equalsIgnoreCase("https") && !"http://abroad.apilocate.amap.com/mobile/binary".equals(c) && c()) {
                    CharSequence charSequence;
                    CharSequence d = d();
                    if (this.h && TextUtils.isEmpty(d)) {
                        this.h = false;
                        String a = dd.a(this.e, "ip", "last_ip", StringUtils.EMPTY_STRING);
                    } else {
                        charSequence = d;
                    }
                    if (!TextUtils.isEmpty(charSequence)) {
                        String str = "last_ip";
                        try {
                            Editor edit = this.e.getSharedPreferences("ip", 0).edit();
                            edit.putString(str, charSequence);
                            dd.a(edit);
                        } catch (Throwable th) {
                            cw.a(th, "SPUtil", "setPrefsInt");
                        }
                        csVar.g = "http://apilocatesrc.amap.com/mobile/binary".replace("apilocatesrc.amap.com", charSequence);
                        csVar.b().put("host", "apilocatesrc.amap.com");
                        this.g = true;
                    }
                }
            }
        } catch (Throwable th2) {
        }
    }

    public final void b() {
        try {
            if (this.b <= 5 && this.g) {
                if (this.f == null) {
                    this.f = z.b();
                }
                if (!this.f.isShutdown()) {
                    this.f.submit(new a(this.a));
                }
            }
        } catch (Throwable th) {
        }
    }

    final synchronized void b(cs csVar) {
        try {
            csVar.g = "http://apilocatesrc.amap.com/mobile/binary";
            long b = dd.b(this.e, "pref", "dns_faile_count_total", 0);
            if (b < 2) {
                bi.a();
                bi.a(csVar, false);
                b++;
                if (b >= 2) {
                    dc.a(this.e, "HttpDNS", "dns failed too much");
                }
                dd.a(this.e, "pref", "dns_faile_count_total", b);
            }
        } catch (Throwable th) {
            dd.a(this.e, "pref", "dns_faile_count_total", 0);
        }
    }
}
