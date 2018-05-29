package com.loc;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.autonavi.aps.amapapi.model.AMapLocationServer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

// compiled from: APSManager.java
public final class e {
    private boolean A;
    private String B;
    private String C;
    String a;
    b b;
    AMapLocation c;
    a d;
    Context e;
    bu f;
    boolean g;
    HashMap<Messenger, Long> h;
    db i;
    long j;
    long k;
    String l;
    AMapLocationClientOption m;
    ServerSocket n;
    boolean o;
    Socket p;
    boolean q;
    c r;
    private boolean s;
    private boolean t;
    private long u;
    private long v;
    private AMapLocationServer w;
    private long x;
    private int y;
    private h z;

    // compiled from: APSManager.java
    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            Throwable th;
            Messenger messenger = null;
            try {
                e eVar;
                Bundle data = message.getData();
                try {
                    messenger = message.replyTo;
                    if (!(data == null || data.isEmpty())) {
                        Object string = data.getString("c");
                        e eVar2 = e.this;
                        if (TextUtils.isEmpty(eVar2.l)) {
                            eVar2.l = cw.d(eVar2.e);
                        }
                        if (TextUtils.isEmpty(string) || !string.equals(eVar2.l)) {
                            string = null;
                        } else {
                            int i = 1;
                        }
                        if (string == null) {
                            if (message.what == 1) {
                                eVar = e.this;
                                AMapLocation a = e.a((int) ConnectionResult.DEVELOPER_ERROR, "invalid handlder scode!!!");
                                e.this.a(messenger, a, a.k(), 0);
                                return;
                            }
                            return;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cw.a(th, "APSServiceCore", "ActionHandler handlerMessage");
                    switch (message.what) {
                        case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                            e.this.a(data);
                            e.a(e.this, messenger, data);
                            break;
                        case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                            e.this.a(data);
                            e.b(e.this, messenger, data);
                            break;
                        case RainSurfaceView.RAIN_LEVEL_SHOWER:
                            if (data == null) {
                                return;
                            }
                            return;
                        case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                            if (data == null) {
                                return;
                            }
                            return;
                        case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                            e.this.a(data);
                            e.a(e.this);
                            break;
                        case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                            e.this.a(data);
                            eVar = e.this;
                            if (!cv.e()) {
                                eVar.f.d();
                            } else if (!de.d(eVar.e)) {
                                eVar.f.d();
                            }
                            eVar.d.sendEmptyMessageDelayed(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, (long) (cv.d() * 1000));
                            break;
                        case DetectedActivity.WALKING:
                            e.this.a(data);
                            e.b(e.this);
                            break;
                        case ConnectionResult.SERVICE_INVALID:
                            e.this.a(data);
                            e.a(e.this, messenger);
                            break;
                        case ConnectionResult.DEVELOPER_ERROR:
                            e.this.a(data);
                            e.this.a(messenger, data);
                            break;
                        case ConnectionResult.LICENSE_CHECK_FAILED:
                            e.this.b();
                            break;
                        case WeatherCircleView.ARC_DIN:
                            e.this.h.remove(messenger);
                            break;
                    }
                    super.handleMessage(message);
                }
                switch (message.what) {
                    case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                        e.this.a(data);
                        e.a(e.this, messenger, data);
                        break;
                    case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        e.this.a(data);
                        e.b(e.this, messenger, data);
                        break;
                    case RainSurfaceView.RAIN_LEVEL_SHOWER:
                        if (data == null && !data.isEmpty()) {
                            e.this.a(null);
                            eVar = e.this;
                            try {
                                if (!eVar.q) {
                                    eVar.r = new c();
                                    eVar.r.start();
                                    eVar.q = true;
                                }
                            } catch (Throwable th3) {
                                cw.a(th3, "APSServiceCore", "startSocket");
                            }
                        } else {
                            return;
                        }
                    case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                        if (data == null && !data.isEmpty()) {
                            e.this.a(null);
                            e.this.a();
                        } else {
                            return;
                        }
                    case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                        e.this.a(data);
                        e.a(e.this);
                        break;
                    case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                        e.this.a(data);
                        eVar = e.this;
                        try {
                            if (!cv.e()) {
                                eVar.f.d();
                            } else if (de.d(eVar.e)) {
                                eVar.f.d();
                            }
                            eVar.d.sendEmptyMessageDelayed(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, (long) (cv.d() * 1000));
                        } catch (Throwable th32) {
                            cw.a(th32, "APSServiceCore", "doOffFusion");
                        }
                        break;
                    case DetectedActivity.WALKING:
                        e.this.a(data);
                        e.b(e.this);
                        break;
                    case ConnectionResult.SERVICE_INVALID:
                        e.this.a(data);
                        e.a(e.this, messenger);
                        break;
                    case ConnectionResult.DEVELOPER_ERROR:
                        e.this.a(data);
                        e.this.a(messenger, data);
                        break;
                    case ConnectionResult.LICENSE_CHECK_FAILED:
                        e.this.b();
                        break;
                    case WeatherCircleView.ARC_DIN:
                        e.this.h.remove(messenger);
                        break;
                }
                super.handleMessage(message);
            } catch (Throwable th322) {
                cw.a(th322, "actionHandler", "handleMessage");
            }
        }
    }

    // compiled from: APSManager.java
    class b extends HandlerThread {
        public b(String str) {
            super(str);
        }

        protected final void onLooperPrepared() {
            try {
                e.this.z = new h(e.this.e);
            } catch (Throwable th) {
                cw.a(th, "AMapLocationManager", "init 2");
            }
            e.this.f = new bu();
            super.onLooperPrepared();
        }
    }

    // compiled from: APSManager.java
    class c extends Thread {
        c() {
        }

        public final void run() {
            try {
                if (!e.this.o) {
                    e.this.o = true;
                    e.this.n = new ServerSocket(43689);
                }
                while (e.this.o && e.this.n != null) {
                    e.this.p = e.this.n.accept();
                    e.a(e.this, e.this.p);
                }
            } catch (Throwable th) {
                cw.a(th, "APSServiceCore", "run");
            }
            super.run();
        }
    }

    public e(Context context) {
        this.s = false;
        this.t = false;
        this.a = null;
        this.b = null;
        this.u = 0;
        this.v = 0;
        this.w = null;
        this.c = null;
        this.x = 0;
        this.y = 0;
        this.d = null;
        this.e = null;
        this.z = null;
        this.f = null;
        this.g = false;
        this.h = new HashMap();
        this.i = null;
        this.j = 0;
        this.k = 0;
        this.l = null;
        this.A = true;
        this.B = StringUtils.EMPTY_STRING;
        this.m = new AMapLocationClientOption();
        this.n = null;
        this.o = false;
        this.p = null;
        this.q = false;
        this.r = null;
        this.C = "jsonp1";
        this.e = context;
    }

    private static AMapLocationServer a(int i, String str) {
        try {
            AMapLocationServer aMapLocationServer = new AMapLocationServer(StringUtils.EMPTY_STRING);
            aMapLocationServer.setErrorCode(i);
            aMapLocationServer.setLocationDetail(str);
            return aMapLocationServer;
        } catch (Throwable th) {
            cw.a(th, "APSServiceCore", "newInstanceAMapLoc");
            return null;
        }
    }

    private void a(Bundle bundle) {
        try {
            if (!this.s) {
                cw.a(this.e);
                if (bundle != null) {
                    this.m = cw.a(bundle.getBundle("optBundle"));
                }
                this.s = true;
                this.f.a(this.e);
                this.f.a();
                a(this.m);
                this.f.b();
            }
        } catch (Throwable th) {
            this.A = false;
            this.B = th.getMessage();
            cw.a(th, "APSServiceCore", "init");
        }
    }

    private void a(Messenger messenger) {
        try {
            if (cv.d(this.e)) {
                a(messenger, (int) LocationRequest.PRIORITY_HIGH_ACCURACY, null);
            }
            if (cv.a()) {
                this.d.sendEmptyMessage(RainSurfaceView.RAIN_LEVEL_RAINSTORM);
            }
            if (cv.c() && cv.d() > 2) {
                this.d.sendEmptyMessage(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER);
            }
        } catch (Throwable th) {
            cw.a(th, "APSServiceCore", "checkConfig");
        }
    }

    private static void a(Messenger messenger, int i, Bundle bundle) {
        if (messenger != null) {
            try {
                Message obtain = Message.obtain();
                obtain.setData(bundle);
                obtain.what = i;
                messenger.send(obtain);
            } catch (Throwable th) {
                cw.a(th, "APSServiceCore", "sendMessage");
            }
        }
    }

    private void a(Messenger messenger, AMapLocation aMapLocation, String str, int i) {
        Bundle bundle = new Bundle();
        bundle.setClassLoader(AMapLocation.class.getClassLoader());
        bundle.putParcelable("loc", aMapLocation);
        bundle.putString("nb", str);
        bundle.putInt("originalLocType", i);
        this.h.put(messenger, Long.valueOf(de.b()));
        a(messenger, 1, bundle);
    }

    private void a(AMapLocationClientOption aMapLocationClientOption) {
        try {
            if (this.f != null) {
                this.f.a(aMapLocationClientOption);
                this.g = aMapLocationClientOption.isKillProcess();
            }
        } catch (Throwable th) {
            cw.a(th, "APSServiceCore", "setExtra");
        }
    }

    static /* synthetic */ void a(e eVar) {
        try {
            if (eVar.y < cv.b()) {
                eVar.y++;
                eVar.f.d();
                eVar.d.sendEmptyMessageDelayed(RainSurfaceView.RAIN_LEVEL_RAINSTORM, 2000);
            }
        } catch (Throwable th) {
            cw.a(th, "APSServiceCore", "doGpsFusion");
        }
    }

    static /* synthetic */ void a(e eVar, Messenger messenger) {
        try {
            eVar.b(messenger);
            cv.f(eVar.e);
            try {
                bu buVar = eVar.f;
                Context context = eVar.e;
                buVar.g();
            } catch (Throwable th) {
            }
        } catch (Throwable th2) {
            cw.a(th2, "APSServiceCore", "doCallOtherSer");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(com.loc.e r3, android.os.Messenger r4, android.os.Bundle r5) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.e.a(com.loc.e, android.os.Messenger, android.os.Bundle):void");
        /*
        if (r5 == 0) goto L_0x0008;
    L_0x0002:
        r0 = r5.isEmpty();	 Catch:{ Throwable -> 0x0047 }
        if (r0 == 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r0 = r3.t;	 Catch:{ Throwable -> 0x0047 }
        if (r0 != 0) goto L_0x0008;
    L_0x000d:
        r0 = 1;
        r3.t = r0;	 Catch:{ Throwable -> 0x0047 }
        r3.b(r4);	 Catch:{ Throwable -> 0x0047 }
        r0 = r3.e;	 Catch:{ Throwable -> 0x0047 }
        com.loc.cv.f(r0);	 Catch:{ Throwable -> 0x0047 }
        r0 = r3.f;	 Catch:{ Throwable -> 0x0050 }
        r1 = r3.e;	 Catch:{ Throwable -> 0x0050 }
        r0.f();	 Catch:{ Throwable -> 0x0050 }
    L_0x001f:
        r3.a(r4);	 Catch:{ Throwable -> 0x0047 }
        r0 = r3.x;	 Catch:{ Throwable -> 0x0047 }
        r0 = com.loc.cv.a(r0);	 Catch:{ Throwable -> 0x0047 }
        if (r0 == 0) goto L_0x0043;
    L_0x002a:
        r0 = "1";
        r1 = "isCacheLoc";
        r1 = r5.getString(r1);	 Catch:{ Throwable -> 0x0047 }
        r0 = r0.equals(r1);	 Catch:{ Throwable -> 0x0047 }
        if (r0 == 0) goto L_0x0043;
    L_0x0038:
        r0 = com.loc.de.b();	 Catch:{ Throwable -> 0x0047 }
        r3.x = r0;	 Catch:{ Throwable -> 0x0047 }
        r0 = r3.f;	 Catch:{ Throwable -> 0x0047 }
        r0.d();	 Catch:{ Throwable -> 0x0047 }
    L_0x0043:
        r3.d();	 Catch:{ Throwable -> 0x0047 }
        goto L_0x0008;
    L_0x0047:
        r0 = move-exception;
        r1 = "APSServiceCore";
        r2 = "doInitAuth";
        com.loc.cw.a(r0, r1, r2);
        goto L_0x0008;
    L_0x0050:
        r0 = move-exception;
        goto L_0x001f;
        */
    }

    static /* synthetic */ void a(e eVar, Socket socket) {
        Throwable th;
        BufferedReader bufferedReader;
        String str;
        BufferedReader bufferedReader2;
        String str2 = null;
        if (socket != null) {
            BufferedReader bufferedReader3;
            try {
                int i = cw.e;
                try {
                    String str3;
                    bufferedReader3 = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    try {
                        eVar.a(bufferedReader3);
                        str2 = eVar.c();
                        cw.e = i;
                        try {
                            eVar.b(str3);
                            try {
                                bufferedReader3.close();
                                socket.close();
                                return;
                            } catch (Throwable th2) {
                                cw.a(th2, "APSServiceCore", "invoke part4");
                            }
                        } catch (Throwable th22) {
                            cw.a(th22, "APSServiceCore", "invoke part3");
                            try {
                                bufferedReader3.close();
                                socket.close();
                            } catch (Throwable th222) {
                                cw.a(th222, "APSServiceCore", "invoke part4");
                            }
                        }
                    } catch (Throwable th3) {
                        th222 = th3;
                        cw.e = i;
                        eVar.b(str2);
                        bufferedReader3.close();
                        socket.close();
                        throw th222;
                    }
                    if (str2 == null) {
                        try {
                            str3 = eVar.w == null ? eVar.C + "&&" + eVar.C + "({'package':'" + eVar.a + "','error_code':8,'error':'unknown error'})" : eVar.C + "&&" + eVar.C + "({'package':'" + eVar.a + "','error_code':0,'error':'','location':{'y':" + eVar.w.getLatitude() + ",'precision':" + eVar.w.getAccuracy() + ",'x':" + eVar.w.getLongitude() + "},'version_code':'3.4.0','version':'3.4.0'})";
                            if (de.d(eVar.e)) {
                                str3 = eVar.C + "&&" + eVar.C + "({'package':'" + eVar.a + "','error_code':36,'error':'app is background'})";
                            }
                        } catch (Throwable th32) {
                            th222 = th32;
                            cw.e = i;
                            eVar.b(str2);
                            bufferedReader3.close();
                            socket.close();
                            throw th222;
                        }
                    }
                    str3 = str2;
                } catch (Throwable th4) {
                    th222 = th4;
                    bufferedReader3 = null;
                    cw.e = i;
                    try {
                        eVar.b(str2);
                        bufferedReader3.close();
                        socket.close();
                    } catch (Throwable th5) {
                        cw.a(th5, "APSServiceCore", "invoke part3");
                        try {
                            bufferedReader3.close();
                            socket.close();
                        } catch (Throwable th52) {
                            cw.a(th52, "APSServiceCore", "invoke part4");
                        }
                    }
                    throw th222;
                }
            } catch (Throwable th2222) {
                cw.a(th2222, "APSServiceCore", "invoke part5");
            }
        }
    }

    private void a(BufferedReader bufferedReader) throws IOException {
        String readLine = bufferedReader.readLine();
        int i = 30000;
        if (readLine != null && readLine.length() > 0) {
            String[] split = readLine.split(" ");
            if (split != null && split.length > 1) {
                split = split[1].split("\\?");
                if (split != null && split.length > 1) {
                    String[] split2 = split[1].split("&");
                    if (split2 != null && split2.length > 0) {
                        int i2 = 30000;
                        for (i = 0; i < split2.length; i++) {
                            String[] split3 = split2[i].split("=");
                            if (split3 != null && split3.length > 1) {
                                if ("to".equals(split3[0])) {
                                    i2 = Integer.parseInt(split3[1]);
                                }
                                if ("callback".equals(split3[0])) {
                                    this.C = split3[1];
                                }
                            }
                        }
                        i = i2;
                    }
                }
            }
        }
        cw.e = i;
    }

    private void b(Messenger messenger) {
        try {
            bu buVar = this.f;
            bu.b(this.e);
            if (cv.q()) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("ngpsAble", cv.s());
                a(messenger, (int) DetectedActivity.WALKING, bundle);
                cv.r();
            }
        } catch (Throwable th) {
            cw.a(th, "APSServiceCore", "initAuth");
        }
    }

    static /* synthetic */ void b(e eVar) {
        try {
            if (cv.a(eVar.e, eVar.u)) {
                eVar.u = de.a();
                eVar.f.d();
            }
        } catch (Throwable th) {
            cw.a(th, "APSServiceCore", "doNGps");
        }
    }

    static /* synthetic */ void b(e eVar, Messenger messenger, Bundle bundle) {
        String str = null;
        int i = 0;
        try {
            if (eVar.h.containsKey(messenger)) {
                if (de.b() - ((Long) eVar.h.get(messenger)).longValue() < 800) {
                    return;
                }
            }
            if (bundle != null && !bundle.isEmpty()) {
                if (eVar.A) {
                    AMapLocationClientOption a = cw.a(bundle.getBundle("optBundle"));
                    eVar.a(a);
                    long b = de.b();
                    if (eVar.w == null || eVar.w.getErrorCode() != 0 || b - eVar.v >= 600) {
                        int i2;
                        da daVar = new da();
                        daVar.a(de.b());
                        try {
                            eVar.w = eVar.f.c();
                            if (eVar.w != null) {
                                i = eVar.w.getLocationType();
                            }
                            daVar.a(eVar.w);
                            b = 0;
                            if (eVar.w != null) {
                                b = eVar.w.getTime();
                            }
                            eVar.w = eVar.f.a(eVar.w, new String[0]);
                            eVar.w.setTime(b);
                            i2 = i;
                        } catch (Throwable th) {
                            db.a(null, 2081);
                            eVar.w = a((int) DetectedActivity.RUNNING, new StringBuilder("loc error : ").append(th.getMessage()).toString());
                            cw.a(th, "APSServiceCore", "run part2");
                            i2 = 0;
                        }
                        daVar.b(de.b());
                        if (eVar.w != null && eVar.w.getErrorCode() == 0) {
                            eVar.v = de.b();
                        }
                        if (eVar.w == null) {
                            eVar.w = a((int) DetectedActivity.RUNNING, "loc is null");
                        }
                        if (eVar.w != null) {
                            str = eVar.w.k();
                        }
                        AMapLocation aMapLocation = eVar.w;
                        try {
                            if (a.isLocationCacheEnable() && eVar.z != null) {
                                aMapLocation = eVar.z.b(eVar.w, str);
                            }
                        } catch (Throwable th2) {
                            cw.a(th2, "APSServiceCore", "fixLastLocation");
                        }
                        eVar.a(messenger, aMapLocation, str, i2);
                        db.a(eVar.e, daVar);
                        i = i2;
                    } else {
                        eVar.a(messenger, eVar.w, eVar.w.k(), (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM);
                    }
                    eVar.b(messenger);
                    if (cv.h(eVar.e)) {
                        eVar.a(messenger);
                    }
                    if (cv.a(eVar.x) && eVar.w != null) {
                        if (i == 2 || i == 4) {
                            eVar.x = de.b();
                            eVar.f.d();
                        }
                    }
                    eVar.d();
                    return;
                }
                eVar.w = a((int) ConnectionResult.SERVICE_INVALID, new StringBuilder("init error : ").append(eVar.B).toString());
                eVar.a(messenger, eVar.w, eVar.w.k(), 0);
            }
        } catch (Throwable th3) {
            cw.a(th3, "APSServiceCore", "doLocation");
        }
    }

    private void b(String str) throws UnsupportedEncodingException, IOException {
        PrintStream printStream = new PrintStream(this.p.getOutputStream(), true, "UTF-8");
        printStream.println("HTTP/1.0 200 OK");
        printStream.println(new StringBuilder("Content-Length:").append(str.getBytes("UTF-8").length).toString());
        printStream.println();
        printStream.println(str);
        printStream.close();
    }

    private String c() {
        long currentTimeMillis = System.currentTimeMillis();
        if ((this.w != null && currentTimeMillis - this.w.getTime() <= 5000) || de.d(this.e)) {
            return null;
        }
        try {
            this.w = this.f.c();
            return this.w.getErrorCode() != 0 ? this.C + "&&" + this.C + "({'package':'" + this.a + "','error_code':" + this.w.getErrorCode() + ",'error':'" + this.w.getErrorInfo() + "'})" : null;
        } catch (Throwable th) {
            cw.a(th, "APSServiceCore", "invoke part1");
            return null;
        }
    }

    private void d() {
        try {
            if (this.f != null) {
                this.f.j();
            }
        } catch (Throwable th) {
            cw.a(th, "APSServiceCore", "startColl");
        }
    }

    public final void a() {
        try {
            if (this.p != null) {
                this.p.close();
            }
        } catch (Throwable th) {
            cw.a(th, "APSServiceCore", "doStopScocket 1");
        }
        try {
            if (this.n != null) {
                this.n.close();
            }
        } catch (Throwable th2) {
            cw.a(th2, "APSServiceCore", "doStopScocket 2");
        }
        try {
            if (this.r != null) {
                this.r.interrupt();
            }
        } catch (Throwable th3) {
        }
        this.r = null;
        this.n = null;
        this.o = false;
        this.q = false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final void a(android.os.Messenger r11, android.os.Bundle r12) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.e.a(android.os.Messenger, android.os.Bundle):void");
        /*
        this = this;
        r1 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        if (r12 == 0) goto L_0x000a;
    L_0x0004:
        r0 = r12.isEmpty();	 Catch:{ Throwable -> 0x0093 }
        if (r0 == 0) goto L_0x000b;
    L_0x000a:
        return;
    L_0x000b:
        r0 = com.loc.cv.y();	 Catch:{ Throwable -> 0x0093 }
        if (r0 == 0) goto L_0x000a;
    L_0x0011:
        r0 = "lat";
        r2 = r12.getDouble(r0);	 Catch:{ Throwable -> 0x0093 }
        r0 = "lon";
        r4 = r12.getDouble(r0);	 Catch:{ Throwable -> 0x0093 }
        r0 = r10.c;	 Catch:{ Throwable -> 0x0093 }
        if (r0 == 0) goto L_0x009d;
    L_0x0021:
        r0 = 4;
        r0 = new double[r0];	 Catch:{ Throwable -> 0x0093 }
        r6 = 0;
        r0[r6] = r2;	 Catch:{ Throwable -> 0x0093 }
        r6 = 1;
        r0[r6] = r4;	 Catch:{ Throwable -> 0x0093 }
        r6 = 2;
        r7 = r10.c;	 Catch:{ Throwable -> 0x0093 }
        r8 = r7.getLatitude();	 Catch:{ Throwable -> 0x0093 }
        r0[r6] = r8;	 Catch:{ Throwable -> 0x0093 }
        r6 = 3;
        r7 = r10.c;	 Catch:{ Throwable -> 0x0093 }
        r8 = r7.getLongitude();	 Catch:{ Throwable -> 0x0093 }
        r0[r6] = r8;	 Catch:{ Throwable -> 0x0093 }
        r0 = com.loc.de.a(r0);	 Catch:{ Throwable -> 0x0093 }
        r6 = com.loc.cv.z();	 Catch:{ Throwable -> 0x0093 }
        r6 = r6 * 3;
        r6 = (float) r6;	 Catch:{ Throwable -> 0x0093 }
        r6 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1));
        if (r6 >= 0) goto L_0x0078;
    L_0x004b:
        r6 = new android.os.Bundle;	 Catch:{ Throwable -> 0x0093 }
        r6.<init>();	 Catch:{ Throwable -> 0x0093 }
        r7 = com.amap.api.location.AMapLocation.class;
        r7 = r7.getClassLoader();	 Catch:{ Throwable -> 0x0093 }
        r6.setClassLoader(r7);	 Catch:{ Throwable -> 0x0093 }
        r7 = "lMaxGeoDis";
        r8 = com.loc.cv.z();	 Catch:{ Throwable -> 0x0093 }
        r8 = r8 * 3;
        r6.putInt(r7, r8);	 Catch:{ Throwable -> 0x0093 }
        r7 = "lMinGeoDis";
        r8 = com.loc.cv.z();	 Catch:{ Throwable -> 0x0093 }
        r6.putInt(r7, r8);	 Catch:{ Throwable -> 0x0093 }
        r7 = "loc";
        r8 = r10.c;	 Catch:{ Throwable -> 0x0093 }
        r6.putParcelable(r7, r8);	 Catch:{ Throwable -> 0x0093 }
        r7 = 6;
        a(r11, r7, r6);	 Catch:{ Throwable -> 0x0093 }
    L_0x0078:
        r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1));
        if (r1 == 0) goto L_0x0085;
    L_0x007c:
        r1 = com.loc.cv.z();	 Catch:{ Throwable -> 0x0093 }
        r1 = (float) r1;	 Catch:{ Throwable -> 0x0093 }
        r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1));
        if (r0 <= 0) goto L_0x000a;
    L_0x0085:
        r0 = 0;
        r10.a(r0);	 Catch:{ Throwable -> 0x0093 }
        r0 = r10.f;	 Catch:{ Throwable -> 0x0093 }
        r0 = r0.a(r2, r4);	 Catch:{ Throwable -> 0x0093 }
        r10.c = r0;	 Catch:{ Throwable -> 0x0093 }
        goto L_0x000a;
    L_0x0093:
        r0 = move-exception;
        r1 = "APSServiceCore";
        r2 = "doLocationGeo";
        com.loc.cw.a(r0, r1, r2);
        goto L_0x000a;
    L_0x009d:
        r0 = r1;
        goto L_0x0078;
        */
    }

    public final void b() {
        try {
            this.h.clear();
            this.h = null;
            if (this.f != null) {
                bu buVar = this.f;
                bu.b(this.e);
            }
            if (this.d != null) {
                this.d.removeCallbacksAndMessages(null);
            }
            if (this.b != null) {
                if (VERSION.SDK_INT >= 18) {
                    try {
                        cz.a(this.b, HandlerThread.class, "quitSafely", new Object[0]);
                    } catch (Throwable th) {
                        this.b.quit();
                    }
                } else {
                    this.b.quit();
                }
            }
            this.b = null;
            this.d = null;
            if (this.z != null) {
                this.z.b();
                this.z = null;
            }
            a();
            this.s = false;
            this.t = false;
            this.f.e();
            db.a(this.e);
            if (!(this.i == null || this.j == 0 || this.k == 0)) {
                long b = de.b() - this.j;
                db.a(this.e, this.i.c(this.e), this.i.d(this.e), this.k, b);
                this.i.e(this.e);
            }
            z.a();
            if (this.g) {
                Process.killProcess(Process.myPid());
            }
        } catch (Throwable th2) {
            cw.a(th2, "APSServiceCore", "threadDestroy");
        }
    }
}
