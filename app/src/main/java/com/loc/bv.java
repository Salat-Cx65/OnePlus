package com.loc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import com.amap.api.location.DPoint;
import com.autonavi.aps.amapapi.model.AMapLocationServer;
import com.google.android.gms.location.DetectedActivity;
import com.loc.i.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.json.JSONObject;

// compiled from: ConnectionServiceManager.java
public class bv {
    public boolean a;
    boolean b;
    private String c;
    private Context d;
    private boolean e;
    private i f;
    private ServiceConnection g;
    private ServiceConnection h;
    private ServiceConnection i;
    private Intent j;
    private String k;
    private String l;
    private String m;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q;
    private boolean r;
    private String s;
    private String t;
    private String u;
    private String v;

    public bv(Context context) {
        this.c = null;
        this.d = null;
        this.e = true;
        this.a = false;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = new Intent();
        this.k = "com.autonavi.minimap";
        this.l = "com.amap.api.service.AMapService";
        this.m = "com.autonavi.minimap.LBSConnectionService";
        this.n = false;
        this.o = false;
        this.p = false;
        this.q = false;
        this.r = false;
        this.s = "invaid type";
        this.t = "empty appkey";
        this.u = "refused";
        this.v = "failed";
        this.b = false;
        this.d = context;
        try {
            this.c = o.a(cj.a(k.f(context).getBytes("UTF-8"), "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDCEYwdO3V2ANrhApjqyk7X8FH5AEaWly58kP9IDAhMqwtIbmcJrUK9oO9Afh3KZnOlDtjiowy733YqpLRO7WBvdbW/c4Dz/d3dy/m+6HMqxaak+GQQRHw/VPdKciaZ3eIZp4MWOyIQwiFSQvPTAo/Na8hV4SgBZHB3lGFw0yu+BmG+h32eIE6p4Y8EDCn+G+yzekX+taMrWTQIysledrygZSGPv1ukbdFDnH/xZEI0dCr9pZT+AZQl3o9a2aMyuRrHM0oupXKKiYl69Y8fKh1Tyd752rF6LrR5uOb9aOfXt18hb+3YL5P9rQ+ZRYbyHYFaxzBPA2jLq0KUQ+Dmg7YhAgMBAAECggEAL9pj0lF3BUHwtssNKdf42QZJMD0BKuDcdZrLV9ifs0f54EJY5enzKw8j76MpdV8N5QVkNX4/BZR0bs9uJogh31oHFs5EXeWbb7V8P7bRrxpNnSAijGBWwscQsyqymf48YlcL28949ujnjoEz3jQjgWOyYnrCgpVhphrQbCGmB5TcZnTFvHfozt/0tzuMj5na5lRnkD0kYXgr0x/SRZcPoCybSpc3t/B/9MAAboGaV/QQkTotr7VOuJfaPRjvg8rzyPzavo3evxsjXj7vDXbN4w0cbk/Uqn2JtvPQ8HoysmF2HdYvILZibvJmWH1hA58b4sn5s6AqFRjMOL7rHdD+gQKBgQD+IzoofmZK5tTxgO9sWsG71IUeshQP9fe159jKCehk1RfuIqqbRP0UcxJiw4eNjHs4zU0HeRL3iF5XfUs0FQanO/pp6YL1xgVdfQlDdTdk6KFHJ0sUJapnJn1S2k7IKfRKE1+rkofSXMYUTsgHF1fDp+gxy4yUMY+h9O+JlKVKOwKBgQDDfaDIblaSm+B0lyG//wFPynAeGd0Q8wcMZbQQ/LWMJZhMZ7fyUZ+A6eL/jB53a2tgnaw2rXBpMe1qu8uSpym2plU0fkgLAnVugS5+KRhOkUHyorcbpVZbs5azf7GlTydR5dI1PHF3Bncemoa6IsEvumHWgQbVyTTz/O9mlFafUwKBgQCvDebms8KUf5JY1F6XfaCLWGVl8nZdVCmQFKbA7Lg2lI5KS3jHQWsupeEZRORffU/3nXsc1apZ9YY+r6CYvI77rRXd1KqPzxos/o7d96TzjkZhc9CEjTlmmh2jb5rqx/Ns/xFcZq/GGH+cx3ODZvHeZQ9NFY+9GLJ+dfB2DX0ZtwKBgQC+9/lZ8telbpqMqpqwqRaJ8LMn5JIdHZu0E6IcuhFLr+ogMW3zTKMpVtGGXEXi2M/TWRPDchiO2tQX4Q5T2/KW19QCbJ5KCwPWiGF3owN4tNOciDGh0xkSidRc0xAh8bnyejSoBry8zlcNUVztdkgMLOGonvCjZWPSOTNQnPYluwKBgCV+WVftpTk3l+OfAJTaXEPNYdh7+WQjzxZKjUaDzx80Ts7hRo2U+EQT7FBjQQNqmmDnWtujo5p1YmJC0FT3n1CVa7g901pb3b0RcOziYWAoJi0/+kLyeo6XBhuLeZ7h90S70GGh1o0V/j/9N1jb5DCL4xKkvdYePPTSTku0BM+n"));
        } catch (Throwable th) {
            cw.a(th, "ConnectionServiceManager", "ConnectionServiceManager");
        }
    }

    private AMapLocationServer a(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        byte[] b;
        JSONObject jSONObject;
        AMapLocationServer aMapLocationServer;
        String string;
        if (bundle.containsKey("key")) {
            try {
                b = cj.b(o.b(bundle.getString("key")), "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDCEYwdO3V2ANrhApjqyk7X8FH5AEaWly58kP9IDAhMqwtIbmcJrUK9oO9Afh3KZnOlDtjiowy733YqpLRO7WBvdbW/c4Dz/d3dy/m+6HMqxaak+GQQRHw/VPdKciaZ3eIZp4MWOyIQwiFSQvPTAo/Na8hV4SgBZHB3lGFw0yu+BmG+h32eIE6p4Y8EDCn+G+yzekX+taMrWTQIysledrygZSGPv1ukbdFDnH/xZEI0dCr9pZT+AZQl3o9a2aMyuRrHM0oupXKKiYl69Y8fKh1Tyd752rF6LrR5uOb9aOfXt18hb+3YL5P9rQ+ZRYbyHYFaxzBPA2jLq0KUQ+Dmg7YhAgMBAAECggEAL9pj0lF3BUHwtssNKdf42QZJMD0BKuDcdZrLV9ifs0f54EJY5enzKw8j76MpdV8N5QVkNX4/BZR0bs9uJogh31oHFs5EXeWbb7V8P7bRrxpNnSAijGBWwscQsyqymf48YlcL28949ujnjoEz3jQjgWOyYnrCgpVhphrQbCGmB5TcZnTFvHfozt/0tzuMj5na5lRnkD0kYXgr0x/SRZcPoCybSpc3t/B/9MAAboGaV/QQkTotr7VOuJfaPRjvg8rzyPzavo3evxsjXj7vDXbN4w0cbk/Uqn2JtvPQ8HoysmF2HdYvILZibvJmWH1hA58b4sn5s6AqFRjMOL7rHdD+gQKBgQD+IzoofmZK5tTxgO9sWsG71IUeshQP9fe159jKCehk1RfuIqqbRP0UcxJiw4eNjHs4zU0HeRL3iF5XfUs0FQanO/pp6YL1xgVdfQlDdTdk6KFHJ0sUJapnJn1S2k7IKfRKE1+rkofSXMYUTsgHF1fDp+gxy4yUMY+h9O+JlKVKOwKBgQDDfaDIblaSm+B0lyG//wFPynAeGd0Q8wcMZbQQ/LWMJZhMZ7fyUZ+A6eL/jB53a2tgnaw2rXBpMe1qu8uSpym2plU0fkgLAnVugS5+KRhOkUHyorcbpVZbs5azf7GlTydR5dI1PHF3Bncemoa6IsEvumHWgQbVyTTz/O9mlFafUwKBgQCvDebms8KUf5JY1F6XfaCLWGVl8nZdVCmQFKbA7Lg2lI5KS3jHQWsupeEZRORffU/3nXsc1apZ9YY+r6CYvI77rRXd1KqPzxos/o7d96TzjkZhc9CEjTlmmh2jb5rqx/Ns/xFcZq/GGH+cx3ODZvHeZQ9NFY+9GLJ+dfB2DX0ZtwKBgQC+9/lZ8telbpqMqpqwqRaJ8LMn5JIdHZu0E6IcuhFLr+ogMW3zTKMpVtGGXEXi2M/TWRPDchiO2tQX4Q5T2/KW19QCbJ5KCwPWiGF3owN4tNOciDGh0xkSidRc0xAh8bnyejSoBry8zlcNUVztdkgMLOGonvCjZWPSOTNQnPYluwKBgCV+WVftpTk3l+OfAJTaXEPNYdh7+WQjzxZKjUaDzx80Ts7hRo2U+EQT7FBjQQNqmmDnWtujo5p1YmJC0FT3n1CVa7g901pb3b0RcOziYWAoJi0/+kLyeo6XBhuLeZ7h90S70GGh1o0V/j/9N1jb5DCL4xKkvdYePPTSTku0BM+n");
            } catch (Throwable th) {
                cw.a(th, "ConnectionServiceManager", "parseData part");
            }
            if (bundle.containsKey("result")) {
                return null;
            }
            try {
                jSONObject = new JSONObject(new String(cj.a(b, o.b(bundle.getString("result"))), "utf-8"));
                if (jSONObject.has("error")) {
                    aMapLocationServer = new AMapLocationServer(StringUtils.EMPTY_STRING);
                    aMapLocationServer.b(jSONObject);
                    aMapLocationServer.setProvider("lbs");
                    aMapLocationServer.setLocationType(DetectedActivity.WALKING);
                    if ("WGS84".equals(aMapLocationServer.e()) && cw.a(aMapLocationServer.getLatitude(), aMapLocationServer.getLongitude())) {
                        DPoint a = cx.a(this.d, aMapLocationServer.getLongitude(), aMapLocationServer.getLatitude());
                        aMapLocationServer.setLatitude(a.getLatitude());
                        aMapLocationServer.setLongitude(a.getLongitude());
                    }
                    return aMapLocationServer;
                }
                string = jSONObject.getString("error");
                if (this.s.equals(string)) {
                    this.e = false;
                }
                if (this.t.equals(string)) {
                    this.e = false;
                }
                if (this.u.equals(string)) {
                    this.e = false;
                }
                this.v.equals(string);
                return null;
            } catch (Throwable th2) {
                cw.a(th2, bv.class.getName(), "parseData");
                return null;
            }
        }
        b = null;
        if (bundle.containsKey("result")) {
            return null;
        }
        jSONObject = new JSONObject(new String(cj.a(b, o.b(bundle.getString("result"))), "utf-8"));
        if (jSONObject.has("error")) {
            aMapLocationServer = new AMapLocationServer(StringUtils.EMPTY_STRING);
            aMapLocationServer.b(jSONObject);
            aMapLocationServer.setProvider("lbs");
            aMapLocationServer.setLocationType(DetectedActivity.WALKING);
            DPoint a2 = cx.a(this.d, aMapLocationServer.getLongitude(), aMapLocationServer.getLatitude());
            aMapLocationServer.setLatitude(a2.getLatitude());
            aMapLocationServer.setLongitude(a2.getLongitude());
            return aMapLocationServer;
        }
        string = jSONObject.getString("error");
        if (this.s.equals(string)) {
            this.e = false;
        }
        if (this.t.equals(string)) {
            this.e = false;
        }
        if (this.u.equals(string)) {
            this.e = false;
        }
        this.v.equals(string);
        return null;
    }

    private void f() {
        if (cv.c(this.d)) {
            Intent intent = new Intent();
            intent.putExtra("appkey", this.c);
            intent.setComponent(new ComponentName(this.k, this.m));
            try {
                this.o = this.d.bindService(intent, this.h, 1);
            } catch (Throwable th) {
            }
            if (!this.o) {
                ArrayList n = cv.n();
                if (n != null) {
                    Iterator it = n.iterator();
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        if (!str.equals(this.m)) {
                            intent.setComponent(new ComponentName(this.k, str));
                            try {
                                this.o = this.d.bindService(intent, this.h, 1);
                            } catch (Throwable th2) {
                            }
                            if (this.n) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private AMapLocationServer g() {
        try {
            if (!this.e || !this.n) {
                return null;
            }
            Bundle bundle = new Bundle();
            bundle.putString("type", "corse");
            bundle.putString("appkey", this.c);
            if (this.f == null) {
                return null;
            }
            this.f.a(bundle);
            return bundle.size() > 0 ? a(bundle) : null;
        } catch (Throwable th) {
            cw.a(th, "ConnectionServiceManager", "sendCommand");
            return null;
        }
    }

    public final void a() {
        try {
            if (this.n) {
                this.d.unbindService(this.g);
            }
            if (this.o) {
                this.d.unbindService(this.h);
            }
            if (this.p) {
                this.d.unbindService(this.i);
            }
        } catch (Throwable th) {
            cw.a(th, "ConnectionServiceManager", "unbindService");
        }
        this.f = null;
        this.d = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.e = true;
        this.a = false;
        this.n = false;
        this.o = false;
        this.p = false;
        this.b = false;
        this.r = false;
        this.q = false;
    }

    public final void b() {
        try {
            if (this.g == null) {
                this.g = new ServiceConnection() {
                    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        bv.this = true;
                        bv.this.f = a.a(iBinder);
                    }

                    public final void onServiceDisconnected(ComponentName componentName) {
                        bv.this = false;
                        bv.this.f = null;
                    }
                };
            }
            if (this.h == null) {
                this.h = new ServiceConnection() {
                    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    }

                    public final void onServiceDisconnected(ComponentName componentName) {
                    }
                };
            }
            if (this.i == null) {
                this.i = new ServiceConnection() {
                    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    }

                    public final void onServiceDisconnected(ComponentName componentName) {
                    }
                };
            }
        } catch (Throwable th) {
            cw.a(th, "ConnectionServiceManager", "init");
        }
    }

    public final void c() {
        if (!this.q) {
            try {
                if (cv.b(this.d)) {
                    this.j.putExtra("appkey", this.c);
                    this.j.setComponent(new ComponentName(this.k, this.l));
                    try {
                        this.n = this.d.bindService(this.j, this.g, 1);
                    } catch (Throwable th) {
                    }
                    if (!this.n) {
                        ArrayList m = cv.m();
                        if (m != null) {
                            Iterator it = m.iterator();
                            while (it.hasNext()) {
                                String str = (String) it.next();
                                if (!str.equals(this.l)) {
                                    this.j.setComponent(new ComponentName(this.k, str));
                                    try {
                                        this.n = this.d.bindService(this.j, this.g, 1);
                                    } catch (Throwable th2) {
                                    }
                                    if (this.n) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                f();
                d();
            } catch (Throwable th3) {
            }
            this.q = true;
        }
    }

    public final void d() {
        if (!this.r && !this.q) {
            try {
                if (cv.g(this.d)) {
                    List<cy> x = cv.x();
                    if (x != null && x.size() > 0) {
                        for (cy cyVar : x) {
                            if (cyVar != null) {
                                try {
                                    if (cyVar.a()) {
                                        Intent intent = new Intent();
                                        intent.setComponent(new ComponentName(cyVar.b(), cyVar.c()));
                                        if (!TextUtils.isEmpty(cyVar.e())) {
                                            intent.setAction(cyVar.e());
                                        }
                                        List d = cyVar.d();
                                        if (d != null && d.size() > 0) {
                                            for (int i = 0; i < d.size(); i++) {
                                                Iterator it = ((Map) d.get(i)).entrySet().iterator();
                                                if (it.hasNext()) {
                                                    Entry entry = (Entry) it.next();
                                                    intent.putExtra(((String) entry.getKey()).toString(), ((String) entry.getValue()).toString());
                                                }
                                            }
                                        }
                                        if (cyVar.f()) {
                                            this.d.startService(intent);
                                        }
                                        if (this.d.bindService(intent, this.i, 1)) {
                                            this.p = true;
                                        }
                                    }
                                } catch (Throwable th) {
                                    cw.a(th, "ConnectionServiceManager", "bindOtherService 1");
                                }
                            }
                        }
                    }
                    this.r = true;
                }
            } catch (Throwable th2) {
                cw.a(th2, "ConnectionServiceManager", "bindOtherService");
            }
        }
    }

    public final AMapLocationServer e() {
        c();
        for (int i = RainSurfaceView.RAIN_LEVEL_RAINSTORM; i > 0 && !this.a; i--) {
            SystemClock.sleep(500);
        }
        if (this.a) {
            AMapLocationServer g = g();
            if (g != null) {
                return g;
            }
        }
        return null;
    }
}
