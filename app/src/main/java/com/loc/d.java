package com.loc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.APSService;
import com.amap.api.location.DPoint;
import com.amap.api.location.LocationManagerBase;
import com.amap.api.location.UmidtokenInfo;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.autonavi.aps.amapapi.model.AMapLocationServer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.LocationRequest;
import java.util.ArrayList;
import java.util.Iterator;
import net.oneplus.weather.api.WeatherType;
import net.oneplus.weather.api.helper.OppoHttpHeaderParser;
import net.oneplus.weather.model.WeatherDescription;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

// compiled from: AMapLocationManager.java
public class d implements LocationManagerBase {
    private int A;
    private boolean B;
    private ServiceConnection C;
    AMapLocationClientOption a;
    public c b;
    g c;
    ArrayList<AMapLocationListener> d;
    a e;
    boolean f;
    public boolean g;
    public boolean h;
    h i;
    Messenger j;
    Messenger k;
    Intent l;
    int m;
    b n;
    boolean o;
    AMapLocationMode p;
    Object q;
    db r;
    e s;
    String t;
    boolean u;
    a v;
    String w;
    private Context x;
    private boolean y;
    private boolean z;

    // compiled from: AMapLocationManager.java
    static /* synthetic */ class AnonymousClass_4 {
        static final /* synthetic */ int[] a;

        static {
            a = new int[AMapLocationMode.values().length];
            try {
                a[AMapLocationMode.Battery_Saving.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[AMapLocationMode.Device_Sensors.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[AMapLocationMode.Hight_Accuracy.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    // compiled from: AMapLocationManager.java
    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                    try {
                        d.a(d.this, (AMapLocationListener) message.obj);
                    } catch (Throwable th) {
                        cw.a(th, "AMapLocationManage$MHandlerr", "handleMessage SET_LISTENER");
                    }
                case WeatherDescription.WEATHER_DESCRIPTION_CLOUDY:
                    try {
                        d.this.f();
                    } catch (Throwable th2) {
                        cw.a(th2, "AMapLocationManager$MHandler", "handleMessage START_LOCATION");
                    }
                case WeatherDescription.WEATHER_DESCRIPTION_OVERCAST:
                    try {
                        d.this.g();
                    } catch (Throwable th22) {
                        cw.a(th22, "AMapLocationManager$MHandler", "handleMessage STOP_LOCATION");
                    }
                case WeatherDescription.WEATHER_DESCRIPTION_DRIZZLE:
                    try {
                        d.b(d.this, (AMapLocationListener) message.obj);
                    } catch (Throwable th222) {
                        cw.a(th222, "AMapLocationManager$MHandler", "handleMessage REMOVE_LISTENER");
                    }
                case WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR:
                    try {
                        d.h(d.this);
                    } catch (Throwable th2222) {
                        cw.a(th2222, "AMapLocationManager$ActionHandler", "handleMessage START_SOCKET");
                    }
                case WeatherDescription.WEATHER_DESCRIPTION_RAINSTORM:
                    try {
                        d.i(d.this);
                    } catch (Throwable th22222) {
                        cw.a(th22222, "AMapLocationManager$ActionHandler", "handleMessage STOP_SOCKET");
                    }
                case WeatherDescription.WEATHER_DESCRIPTION_FLURRY:
                    try {
                        d.this.a();
                    } catch (Throwable th222222) {
                        cw.a(th222222, "AMapLocationManager$MHandler", "handleMessage DESTROY");
                    }
                case WeatherDescription.WEATHER_DESCRIPTION_HAIL:
                    try {
                        d.this.i.c();
                    } catch (Throwable th2222222) {
                        cw.a(th2222222, "AMapLocationManager$ActionHandler", "handleMessage ACTION_SAVE_LAST_LOCATION");
                    }
                case WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER:
                    try {
                        g gVar = d.this.c;
                        gVar.c = d.this;
                        if (gVar.c == null) {
                            gVar.c = new AMapLocationClientOption();
                        }
                        gVar.b();
                    } catch (Throwable th22222222) {
                        cw.a(th22222222, "AMapLocationManager$ActionHandler", "handleMessage START_GPS_LOCATION");
                    }
                case WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM:
                    try {
                        if (d.this.c.c()) {
                            d.this.a(WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM, null, 1000);
                        } else {
                            d.f(d.this);
                        }
                    } catch (Throwable th222222222) {
                        cw.a(th222222222, "AMapLocationManager$ActionHandler", "handleMessage START_LBS_LOCATION");
                    }
                case WeatherDescription.WEATHER_DESCRIPTION_FOG:
                    try {
                        d.this.c.a();
                    } catch (Throwable th2222222222) {
                        cw.a(th2222222222, "AMapLocationManager$ActionHandler", "handleMessage STOP_GPS_LOCATION");
                    }
                case WeatherDescription.WEATHER_DESCRIPTION_HURRICANE:
                    try {
                        d.this = (AMapLocationClientOption) message.obj;
                        if (d.this != null) {
                            d.g(d.this);
                        }
                    } catch (Throwable th22222222222) {
                        cw.a(th22222222222, "AMapLocationManager$ActionHandler", "handleMessage SET_OPTION");
                    }
                default:
                    break;
            }
        }
    }

    // compiled from: AMapLocationManager.java
    static class b extends HandlerThread {
        d a;

        public b(String str, d dVar) {
            super(str);
            this.a = null;
            this.a = dVar;
        }

        protected final void onLooperPrepared() {
            try {
                this.a.i.a();
                this.a.k = new Messenger(this.a.b);
                this.a.a(this.a.b());
                super.onLooperPrepared();
            } catch (Throwable th) {
            }
        }
    }

    // compiled from: AMapLocationManager.java
    public class c extends Handler {
        public c(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            if (!d.this.o || cw.c()) {
                switch (message.what) {
                    case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        try {
                            d.a(d.this, message.getData());
                        } catch (Throwable th) {
                            cw.a(th, "AMapLocationManager$ActionHandler", "handleMessage RESULT_LBS_LOCATIONSUCCESS");
                        }
                    case RainSurfaceView.RAIN_LEVEL_SHOWER:
                        try {
                            d.a(d.this, message);
                        } catch (Throwable th2) {
                            cw.a(th2, "AMapLocationManager$ActionHandler", "handleMessage RESULT_GPS_LOCATIONSUCCESS");
                        }
                    case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                        break;
                    case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                        try {
                            d.this.a((int) ConnectionResult.DEVELOPER_ERROR, message.getData());
                        } catch (Throwable th22) {
                            cw.a(th22, "AMapLocationManager$ActionHandler", "handleMessage RESULT_GPS_LOCATIONCHANGE");
                        }
                    case ConnectionResult.RESOLUTION_REQUIRED:
                        try {
                            Bundle data = message.getData();
                            if (d.this.c != null) {
                                g gVar = d.this.c;
                                if (data != null) {
                                    try {
                                        data.setClassLoader(AMapLocation.class.getClassLoader());
                                        gVar.h = data.getInt("lMaxGeoDis");
                                        gVar.i = data.getInt("lMinGeoDis");
                                        AMapLocation aMapLocation = (AMapLocation) data.getParcelable("loc");
                                        if (!TextUtils.isEmpty(aMapLocation.getAdCode())) {
                                            gVar.n = aMapLocation;
                                        }
                                    } catch (Throwable th222) {
                                        cw.a(th222, "GPSLocation", "setLastGeoLocation");
                                    }
                                }
                            }
                        } catch (Throwable th2222) {
                            cw.a(th2222, "AMapLocationManager$ActionHandler", "handleMessage RESULT_GPS_GEO_SUCCESS");
                        }
                    case DetectedActivity.WALKING:
                        try {
                            d.this.B = message.getData().getBoolean("ngpsAble");
                        } catch (Throwable th22222) {
                            cw.a(th22222, "AMapLocationManager$ActionHandler", "handleMessage RESULT_NGPS_ABLE");
                        }
                    case DetectedActivity.RUNNING:
                        db.a(null, 2141);
                        d.a(d.this, message);
                    case LocationRequest.PRIORITY_HIGH_ACCURACY:
                        try {
                            d.a(d.this);
                        } catch (Throwable th222222) {
                            cw.a(th222222, "AMapLocationManager$ActionHandler", "handleMessage RESULT_FASTSKY");
                        }
                    default:
                        break;
                }
            }
        }
    }

    public d(Context context, Intent intent) {
        this.a = new AMapLocationClientOption();
        this.c = null;
        this.y = false;
        this.z = false;
        this.d = new ArrayList();
        this.f = false;
        this.g = true;
        this.h = true;
        this.j = null;
        this.k = null;
        this.l = null;
        this.m = 0;
        this.A = 0;
        this.B = true;
        this.n = null;
        this.o = false;
        this.p = AMapLocationMode.Hight_Accuracy;
        this.q = new Object();
        this.r = null;
        this.s = null;
        this.t = null;
        this.C = new ServiceConnection() {
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                try {
                    d.this.j = new Messenger(iBinder);
                    d.this.y = true;
                } catch (Throwable th) {
                    cw.a(th, "AMapLocationManager", "onServiceConnected");
                }
            }

            public final void onServiceDisconnected(ComponentName componentName) {
                d.this.j = null;
                d.this.y = false;
            }
        };
        this.u = false;
        this.v = null;
        this.w = null;
        this.x = context;
        this.l = intent;
        if (cw.c()) {
            try {
                dc.a(this.x, cw.b());
            } catch (Throwable th) {
            }
        }
        try {
            if (Looper.myLooper() == null) {
                this.b = new c(this.x.getMainLooper());
            } else {
                this.b = new c();
            }
        } catch (Throwable th2) {
            cw.a(th2, "AMapLocationManager", "init 1");
        }
        try {
            this.i = new h(this.x);
        } catch (Throwable th22) {
            cw.a(th22, "AMapLocationManager", "init 2");
        }
        this.n = new b("amapLocManagerThread", this);
        this.n.setPriority(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER);
        this.n.start();
        this.v = a(this.n.getLooper());
        try {
            this.c = new g(this.x, this.b);
        } catch (Throwable th222) {
            cw.a(th222, "AMapLocationManager", "init 3");
        }
        if (this.r == null) {
            this.r = new db();
        }
    }

    private AMapLocationServer a(bu buVar) {
        if (this.a.isLocationCacheEnable()) {
            try {
                return buVar.i();
            } catch (Throwable th) {
                cw.a(th, "AMapLocationManager", "doFirstCacheLoc");
            }
        }
        return null;
    }

    private a a(Looper looper) {
        a aVar;
        synchronized (this.q) {
            this.v = new a(looper);
            aVar = this.v;
        }
        return aVar;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(int r4, android.os.Bundle r5) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.d.a(int, android.os.Bundle):void");
        /*
        this = this;
        if (r5 != 0) goto L_0x0007;
    L_0x0002:
        r5 = new android.os.Bundle;	 Catch:{ Throwable -> 0x0035 }
        r5.<init>();	 Catch:{ Throwable -> 0x0035 }
    L_0x0007:
        r0 = r3.t;	 Catch:{ Throwable -> 0x0035 }
        r0 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Throwable -> 0x0035 }
        if (r0 == 0) goto L_0x0017;
    L_0x000f:
        r0 = r3.x;	 Catch:{ Throwable -> 0x0035 }
        r0 = com.loc.cw.d(r0);	 Catch:{ Throwable -> 0x0035 }
        r3.t = r0;	 Catch:{ Throwable -> 0x0035 }
    L_0x0017:
        r0 = "c";
        r1 = r3.t;	 Catch:{ Throwable -> 0x0035 }
        r5.putString(r0, r1);	 Catch:{ Throwable -> 0x0035 }
        r0 = android.os.Message.obtain();	 Catch:{ Throwable -> 0x0035 }
        r0.what = r4;	 Catch:{ Throwable -> 0x0035 }
        r0.setData(r5);	 Catch:{ Throwable -> 0x0035 }
        r1 = r3.k;	 Catch:{ Throwable -> 0x0035 }
        r0.replyTo = r1;	 Catch:{ Throwable -> 0x0035 }
        r1 = r3.j;	 Catch:{ Throwable -> 0x0035 }
        if (r1 == 0) goto L_0x0034;
    L_0x002f:
        r1 = r3.j;	 Catch:{ Throwable -> 0x0035 }
        r1.send(r0);	 Catch:{ Throwable -> 0x0035 }
    L_0x0034:
        return;
    L_0x0035:
        r0 = move-exception;
        r1 = "AMapLocationManager";
        r2 = "sendLocMessage";
        com.loc.cw.a(r0, r1, r2);
        goto L_0x0034;
        */
    }

    private void a(int i, Object obj, long j) {
        synchronized (this.q) {
            if (this.v != null) {
                Message obtain = Message.obtain();
                obtain.what = i;
                obtain.obj = obj;
                this.v.sendMessageDelayed(obtain, j);
            }
        }
    }

    private void a(AMapLocation aMapLocation, Throwable th) {
        try {
            if (!cw.c() || aMapLocation != null) {
                if (aMapLocation == null) {
                    aMapLocation = new AMapLocation(StringUtils.EMPTY_STRING);
                    aMapLocation.setErrorCode(DetectedActivity.RUNNING);
                    aMapLocation.setLocationDetail("amapLocation is null");
                }
                if (!GeocodeSearch.GPS.equals(aMapLocation.getProvider())) {
                    aMapLocation.setProvider("lbs");
                }
                try {
                    if (this.z) {
                        aMapLocation.setTime(aMapLocation.getTime());
                        if (this.i.a(aMapLocation, this.w)) {
                            a(WeatherDescription.WEATHER_DESCRIPTION_HAIL, null, 0);
                        }
                        db.a(this.x, this.A, aMapLocation);
                        db.b(this.x, this.A, aMapLocation);
                        try {
                            if (GeocodeSearch.GPS.equals(aMapLocation.getProvider()) || !this.c.c()) {
                                aMapLocation.setAltitude(de.b(aMapLocation.getAltitude()));
                                aMapLocation.setBearing(de.a(aMapLocation.getBearing()));
                                aMapLocation.setSpeed(de.a(aMapLocation.getSpeed()));
                                Iterator it = this.d.iterator();
                                while (it.hasNext()) {
                                    try {
                                        ((AMapLocationListener) it.next()).onLocationChanged(aMapLocation);
                                    } catch (Throwable th2) {
                                    }
                                }
                            }
                        } catch (Throwable th3) {
                        }
                    }
                } catch (Throwable th4) {
                    cw.a(th4, "AMapLocationManager", "handlerLocation part2");
                }
                if (!this.o || cw.c()) {
                    dc.b(this.x);
                    if (this.a.isOnceLocation()) {
                        g();
                    }
                }
            } else if (th != null) {
                dc.a(this.x, "loc", th.getMessage());
            } else {
                dc.a(this.x, "loc", "amaplocation is null");
            }
        } catch (Throwable th42) {
            cw.a(th42, "AMapLocationManager", "handlerLocation part3");
        }
    }

    static /* synthetic */ void a(d dVar) {
        Object obj = 1;
        Object obj2 = null;
        try {
            int i;
            if (dVar.x.checkCallingOrSelfPermission("android.permission.SYSTEM_ALERT_WINDOW") == 0) {
                i = 1;
            } else if (dVar.x instanceof Activity) {
                i = 1;
                obj = null;
            } else {
                obj = null;
            }
            if (obj2 != null) {
                Builder builder = new Builder(dVar.x);
                builder.setMessage(cv.g());
                if (!(StringUtils.EMPTY_STRING.equals(cv.h()) || cv.h() == null)) {
                    builder.setPositiveButton(cv.h(), new OnClickListener() {
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            d.this.e();
                            dialogInterface.cancel();
                        }
                    });
                }
                builder.setNegativeButton(cv.i(), new OnClickListener() {
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog create = builder.create();
                if (obj != null) {
                    create.getWindow().setType(WeatherType.OPPO_CHINA_WEATHER_SHOWER);
                }
                create.setCanceledOnTouchOutside(false);
                create.show();
                return;
            }
            dVar.e();
        } catch (Throwable th) {
            dVar.e();
            cw.a(th, "AMapLocationManager", "showDialog");
        }
    }

    static /* synthetic */ void a(d dVar, Bundle bundle) {
        AMapLocation aMapLocation;
        Throwable th = null;
        if (bundle != null) {
            try {
                bundle.setClassLoader(AMapLocation.class.getClassLoader());
                aMapLocation = (AMapLocation) bundle.getParcelable("loc");
                dVar.A = bundle.getInt("originalLocType", 0);
                dVar.w = bundle.getString("nb", null);
                if (!(aMapLocation == null || aMapLocation.getErrorCode() != 0 || TextUtils.isEmpty(aMapLocation.getAdCode()) || dVar.c == null)) {
                    dVar.c.n = aMapLocation;
                }
            } catch (Throwable th2) {
                cw.a(th2, "AMapLocationManager", "doLbsLocationSuccess");
                Throwable th3 = th2;
                aMapLocation = null;
                th = th3;
            }
        } else {
            aMapLocation = null;
        }
        dVar.a(aMapLocation, th);
    }

    static /* synthetic */ void a(d dVar, Message message) {
        try {
            AMapLocation aMapLocation = (AMapLocation) message.obj;
            if (aMapLocation != null) {
                dVar.A = aMapLocation.getLocationType();
            }
            if (dVar.h && dVar.j != null) {
                Bundle bundle = new Bundle();
                bundle.putBundle("optBundle", cw.b(dVar.a));
                dVar.a(0, bundle);
                dVar.h = false;
            }
            dVar.a(aMapLocation, null);
            if (dVar.B) {
                dVar.a((int) DetectedActivity.WALKING, null);
            }
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "doGpsLocationSuccess");
        }
    }

    static /* synthetic */ void a(d dVar, AMapLocationListener aMapLocationListener) {
        if (aMapLocationListener == null) {
            throw new IllegalArgumentException("listener\u53c2\u6570\u4e0d\u80fd\u4e3anull");
        }
        if (dVar.d == null) {
            dVar.d = new ArrayList();
        }
        if (!dVar.d.contains(aMapLocationListener)) {
            dVar.d.add(aMapLocationListener);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.autonavi.aps.amapapi.model.AMapLocationServer b(com.loc.bu r9) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.d.b(com.loc.bu):com.autonavi.aps.amapapi.model.AMapLocationServer");
        /*
        this = this;
        r1 = 0;
        r3 = 1;
        r4 = 0;
        r5 = new com.loc.da;	 Catch:{ Throwable -> 0x00c7 }
        r5.<init>();	 Catch:{ Throwable -> 0x00c7 }
        r6 = com.loc.de.b();	 Catch:{ Throwable -> 0x00c7 }
        r5.a(r6);	 Catch:{ Throwable -> 0x00c7 }
        r0 = com.amap.api.location.AMapLocationClientOption.getAPIKEY();	 Catch:{ Throwable -> 0x00bd }
        r2 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Throwable -> 0x00bd }
        if (r2 != 0) goto L_0x001c;
    L_0x0019:
        com.loc.l.a(r0);	 Catch:{ Throwable -> 0x00bd }
    L_0x001c:
        r0 = com.amap.api.location.UmidtokenInfo.getUmidtoken();	 Catch:{ Throwable -> 0x00d8 }
        r2 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Throwable -> 0x00d8 }
        if (r2 != 0) goto L_0x0029;
    L_0x0026:
        com.loc.n.a(r0);	 Catch:{ Throwable -> 0x00d8 }
    L_0x0029:
        r0 = r8.x;	 Catch:{ Throwable -> 0x00e9 }
        r9.a(r0);	 Catch:{ Throwable -> 0x00e9 }
        r0 = r8.a;	 Catch:{ Throwable -> 0x00e9 }
        r9.a(r0);	 Catch:{ Throwable -> 0x00e9 }
        r0 = r8.x;	 Catch:{ Throwable -> 0x00e9 }
        r9.h();	 Catch:{ Throwable -> 0x00e9 }
    L_0x0038:
        r2 = r8.a(r9);	 Catch:{ Throwable -> 0x00c7 }
        if (r2 != 0) goto L_0x013f;
    L_0x003e:
        r0 = r8.x;	 Catch:{ Throwable -> 0x00f3 }
        r0 = com.loc.cw.b(r0);	 Catch:{ Throwable -> 0x00f3 }
        r0 = com.loc.de.a(r0);	 Catch:{ Throwable -> 0x00f3 }
        if (r0 == 0) goto L_0x004e;
    L_0x004a:
        r0 = "http://abroad.apilocate.amap.com/mobile/binary";
        com.loc.cw.a = r0;	 Catch:{ Throwable -> 0x00f3 }
    L_0x004e:
        r0 = 0;
        r2 = r9.a(r0);	 Catch:{ Throwable -> 0x00ff }
    L_0x0053:
        r6 = com.loc.de.b();	 Catch:{ Throwable -> 0x00fd }
        r5.b(r6);	 Catch:{ Throwable -> 0x00fd }
        r5.a(r2);	 Catch:{ Throwable -> 0x00fd }
        if (r2 == 0) goto L_0x013c;
    L_0x005f:
        r0 = r2.k();	 Catch:{ Throwable -> 0x00fd }
    L_0x0063:
        r4 = r8.a;	 Catch:{ Throwable -> 0x0109 }
        r4 = r4.isLocationCacheEnable();	 Catch:{ Throwable -> 0x0109 }
        if (r4 == 0) goto L_0x0111;
    L_0x006b:
        r4 = r8.i;	 Catch:{ Throwable -> 0x0109 }
        if (r4 == 0) goto L_0x0111;
    L_0x006f:
        r4 = r8.i;	 Catch:{ Throwable -> 0x0109 }
        r0 = r4.b(r2, r0);	 Catch:{ Throwable -> 0x0109 }
    L_0x0075:
        r4 = new android.os.Bundle;	 Catch:{ Throwable -> 0x011c }
        r4.<init>();	 Catch:{ Throwable -> 0x011c }
        if (r2 == 0) goto L_0x0114;
    L_0x007c:
        r6 = "originalLocType";
        r7 = r2.getLocationType();	 Catch:{ Throwable -> 0x011c }
        r4.putInt(r6, r7);	 Catch:{ Throwable -> 0x011c }
        r6 = "loc";
        r4.putParcelable(r6, r0);	 Catch:{ Throwable -> 0x011c }
        r0 = "nb";
        r6 = r2.k();	 Catch:{ Throwable -> 0x011c }
        r4.putString(r0, r6);	 Catch:{ Throwable -> 0x011c }
    L_0x0093:
        r0 = android.os.Message.obtain();	 Catch:{ Throwable -> 0x011c }
        r0.setData(r4);	 Catch:{ Throwable -> 0x011c }
        r4 = 1;
        r0.what = r4;	 Catch:{ Throwable -> 0x011c }
        r4 = r8.b;	 Catch:{ Throwable -> 0x011c }
        r4.sendMessage(r0);	 Catch:{ Throwable -> 0x011c }
    L_0x00a2:
        r0 = r8.x;	 Catch:{ Throwable -> 0x00fd }
        com.loc.db.a(r0, r5);	 Catch:{ Throwable -> 0x00fd }
        if (r3 == 0) goto L_0x00b7;
    L_0x00a9:
        r0 = 1;
        r1 = r9.a(r0);	 Catch:{ Throwable -> 0x0126 }
    L_0x00ae:
        r0 = r1.getErrorCode();	 Catch:{ Throwable -> 0x00fd }
        if (r0 != 0) goto L_0x00b7;
    L_0x00b4:
        r9.a(r1);	 Catch:{ Throwable -> 0x012f }
    L_0x00b7:
        if (r9 == 0) goto L_0x00bc;
    L_0x00b9:
        r9.e();	 Catch:{ Throwable -> 0x0138 }
    L_0x00bc:
        return r2;
    L_0x00bd:
        r0 = move-exception;
        r2 = "AMapLocationManager";
        r6 = "apsLocation setAuthKey";
        com.loc.cw.a(r0, r2, r6);	 Catch:{ Throwable -> 0x00c7 }
        goto L_0x001c;
    L_0x00c7:
        r0 = move-exception;
        r2 = r1;
    L_0x00c9:
        r1 = "AMapLocationManager";
        r3 = "apsLocation";
        com.loc.cw.a(r0, r1, r3);	 Catch:{ all -> 0x00e2 }
        if (r9 == 0) goto L_0x00bc;
    L_0x00d2:
        r9.e();	 Catch:{ Throwable -> 0x00d6 }
        goto L_0x00bc;
    L_0x00d6:
        r0 = move-exception;
        goto L_0x00bc;
    L_0x00d8:
        r0 = move-exception;
        r2 = "AMapLocationManager";
        r6 = "apsLocation setUmidToken";
        com.loc.cw.a(r0, r2, r6);	 Catch:{ Throwable -> 0x00c7 }
        goto L_0x0029;
    L_0x00e2:
        r0 = move-exception;
        if (r9 == 0) goto L_0x00e8;
    L_0x00e5:
        r9.e();	 Catch:{ Throwable -> 0x013a }
    L_0x00e8:
        throw r0;
    L_0x00e9:
        r0 = move-exception;
        r2 = "AMapLocationManager";
        r6 = "initApsBase";
        com.loc.cw.a(r0, r2, r6);	 Catch:{ Throwable -> 0x00c7 }
        goto L_0x0038;
    L_0x00f3:
        r0 = move-exception;
        r4 = "AMapLocationManager";
        r6 = "checkUrl";
        com.loc.cw.a(r0, r4, r6);	 Catch:{ Throwable -> 0x00fd }
        goto L_0x004e;
    L_0x00fd:
        r0 = move-exception;
        goto L_0x00c9;
    L_0x00ff:
        r0 = move-exception;
        r4 = "AMapLocationManager";
        r6 = "apsLocation:doFirstNetLocate";
        com.loc.cw.a(r0, r4, r6);	 Catch:{ Throwable -> 0x00fd }
        goto L_0x0053;
    L_0x0109:
        r0 = move-exception;
        r4 = "AMapLocationManager";
        r6 = "fixLastLocation";
        com.loc.cw.a(r0, r4, r6);	 Catch:{ Throwable -> 0x00fd }
    L_0x0111:
        r0 = r2;
        goto L_0x0075;
    L_0x0114:
        r0 = "originalLocType";
        r6 = 0;
        r4.putInt(r0, r6);	 Catch:{ Throwable -> 0x011c }
        goto L_0x0093;
    L_0x011c:
        r0 = move-exception;
        r4 = "AMapLocationManager";
        r6 = "apsLocation:callback";
        com.loc.cw.a(r0, r4, r6);	 Catch:{ Throwable -> 0x00fd }
        goto L_0x00a2;
    L_0x0126:
        r0 = move-exception;
        r3 = "AMapLocationManager";
        r4 = "apsLocation:doFirstNetLocate 2";
        com.loc.cw.a(r0, r3, r4);	 Catch:{ Throwable -> 0x00fd }
        goto L_0x00ae;
    L_0x012f:
        r0 = move-exception;
        r1 = "AMapLocationManager";
        r3 = "apsLocation:doFirstAddCache";
        com.loc.cw.a(r0, r1, r3);	 Catch:{ Throwable -> 0x00fd }
        goto L_0x00b7;
    L_0x0138:
        r0 = move-exception;
        goto L_0x00bc;
    L_0x013a:
        r1 = move-exception;
        goto L_0x00e8;
    L_0x013c:
        r0 = r1;
        goto L_0x0063;
    L_0x013f:
        r3 = r4;
        goto L_0x0053;
        */
    }

    static /* synthetic */ void b(d dVar, AMapLocationListener aMapLocationListener) {
        if (!dVar.d.isEmpty() && dVar.d.contains(aMapLocationListener)) {
            dVar.d.remove(aMapLocationListener);
        }
        if (dVar.d.isEmpty()) {
            dVar.g();
        }
    }

    private void c() {
        synchronized (this.q) {
            if (this.v != null) {
                this.v.removeMessages(WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM);
            }
        }
    }

    private boolean d() {
        int i = 0;
        do {
            try {
                if (this.j != null) {
                    break;
                }
                Thread.sleep(100);
                i++;
            } catch (Throwable th) {
                cw.a(th, "AMapLocationManager", "checkAPSManager");
                return false;
            }
        } while (i < 50);
        if (this.j != null) {
            return true;
        }
        Message obtain = Message.obtain();
        Bundle bundle = new Bundle();
        Parcelable aMapLocation = new AMapLocation(StringUtils.EMPTY_STRING);
        aMapLocation.setErrorCode(ConnectionResult.DEVELOPER_ERROR);
        aMapLocation.setLocationDetail("\u8bf7\u68c0\u67e5\u914d\u7f6e\u6587\u4ef6\u662f\u5426\u914d\u7f6e\u670d\u52a1\uff0c\u5e76\u4e14manifest\u4e2dservice\u6807\u7b7e\u662f\u5426\u914d\u7f6e\u5728application\u6807\u7b7e\u5185");
        bundle.putParcelable("loc", aMapLocation);
        obtain.setData(bundle);
        obtain.what = 1;
        this.b.sendMessage(obtain);
        return false;
    }

    private void e() {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.autonavi.minimap", cv.l()));
            intent.setFlags(268435456);
            intent.setData(Uri.parse(cv.j()));
            this.x.startActivity(intent);
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "callAMap part1");
            try {
                intent = new Intent("android.intent.action.VIEW", Uri.parse(cv.k()));
                intent.setFlags(268435456);
                this.x.startActivity(intent);
            } catch (Throwable th2) {
                cw.a(th2, "AMapLocationManager", "callAMap part2");
            }
        }
    }

    private void f() {
        long j = 0;
        if (this.a == null) {
            this.a = new AMapLocationClientOption();
        }
        if (!this.z) {
            this.z = true;
            switch (AnonymousClass_4.a[this.a.getLocationMode().ordinal()]) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    a(WeatherDescription.WEATHER_DESCRIPTION_FOG, null, 0);
                    a(WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM, null, 0);
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    c();
                    a(WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER, null, 0);
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    a(WeatherDescription.WEATHER_DESCRIPTION_THUNDERSHOWER, null, 0);
                    if (this.a.isGpsFirst() && this.a.isOnceLocation()) {
                        j = OppoHttpHeaderParser.CACHE_TIME;
                    }
                    a(WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM, null, j);
                default:
                    break;
            }
        }
    }

    static /* synthetic */ void f(d dVar) {
        try {
            if (dVar.g) {
                dVar.g = false;
                AMapLocationServer b = dVar.b(new bu());
                if (dVar.d()) {
                    Bundle bundle = new Bundle();
                    String str = "0";
                    if (b != null) {
                        if (b.getLocationType() == 2 || b.getLocationType() == 4) {
                            str = "1";
                        }
                    }
                    bundle.putBundle("optBundle", cw.b(dVar.a));
                    bundle.putString("isCacheLoc", str);
                    dVar.a(0, bundle);
                }
            } else if (dVar.d()) {
                Bundle bundle2 = new Bundle();
                bundle2.putBundle("optBundle", cw.b(dVar.a));
                if (!dVar.c.c()) {
                    dVar.a(1, bundle2);
                }
            }
            try {
                if (!dVar.a.isOnceLocation()) {
                    dVar.h();
                }
            } catch (Throwable th) {
            }
        } catch (Throwable th2) {
            try {
                cw.a(th2, "AMapLocationManager", "doLBSLocation");
                try {
                    if (!dVar.a.isOnceLocation()) {
                        dVar.h();
                    }
                } catch (Throwable th3) {
                }
            } catch (Throwable th4) {
                try {
                    if (!dVar.a.isOnceLocation()) {
                        dVar.h();
                    }
                } catch (Throwable th5) {
                }
            }
        }
    }

    private void g() {
        try {
            this.c.a();
            c();
            this.z = false;
            this.m = 0;
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "stopLocation");
        }
    }

    static /* synthetic */ void g(d dVar) {
        g gVar = dVar.c;
        gVar.c = dVar.a;
        if (gVar.c == null) {
            gVar.c = new AMapLocationClientOption();
        }
        if (!(gVar.c.getLocationMode() == AMapLocationMode.Device_Sensors || gVar.a == null)) {
            gVar.a.removeMessages(DetectedActivity.RUNNING);
        }
        if (dVar.z && !dVar.a.getLocationMode().equals(dVar.p)) {
            dVar.g();
            dVar.f();
        }
        dVar.p = dVar.a.getLocationMode();
        if (dVar.r != null) {
            if (dVar.a.isOnceLocation()) {
                dVar.r.a(dVar.x, 0);
            } else {
                dVar.r.a(dVar.x, 1);
            }
            dVar.r.a(dVar.x, dVar.a);
        }
    }

    private void h() {
        long j = 1000;
        if (this.a.getLocationMode() != AMapLocationMode.Device_Sensors) {
            if (this.a.getInterval() >= 1000) {
                j = this.a.getInterval();
            }
            a(WeatherDescription.WEATHER_DESCRIPTION_SANDSTORM, null, j);
        }
    }

    static /* synthetic */ void h(d dVar) {
        try {
            if (dVar.j != null) {
                dVar.m = 0;
                Bundle bundle = new Bundle();
                bundle.putBundle("optBundle", cw.b(dVar.a));
                dVar.a((int) RainSurfaceView.RAIN_LEVEL_SHOWER, bundle);
                return;
            }
            dVar.m++;
            if (dVar.m < 10) {
                dVar.a(WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR, null, 50);
            }
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "startAssistantLocationImpl");
        }
    }

    static /* synthetic */ void i(d dVar) {
        try {
            Bundle bundle = new Bundle();
            bundle.putBundle("optBundle", cw.b(dVar.a));
            dVar.a((int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, bundle);
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "stopAssistantLocationImpl");
        }
    }

    final void a() {
        a((int) WeatherCircleView.ARC_DIN, null);
        this.g = true;
        this.h = true;
        this.y = false;
        g();
        if (this.r != null) {
            this.r.b(this.x);
        }
        db.a(this.x);
        if (this.e != null) {
            this.e.removeGeoFence();
        }
        if (this.s != null) {
            this.s.d.sendEmptyMessage(ConnectionResult.LICENSE_CHECK_FAILED);
        } else if (this.C != null) {
            this.x.unbindService(this.C);
        }
        if (this.d != null) {
            this.d.clear();
            this.d = null;
        }
        this.C = null;
        synchronized (this.q) {
            if (this.v != null) {
                this.v.removeCallbacksAndMessages(null);
            }
            this.v = null;
        }
        if (this.n != null) {
            if (VERSION.SDK_INT >= 18) {
                try {
                    cz.a(this.n, HandlerThread.class, "quitSafely", new Object[0]);
                } catch (Throwable th) {
                    this.n.quit();
                }
            } else {
                this.n.quit();
            }
        }
        this.n = null;
        if (this.b != null) {
            this.b.removeCallbacksAndMessages(null);
        }
        if (this.i != null) {
            this.i.b();
            this.i = null;
        }
    }

    final void a(Intent intent) {
        try {
            this.x.bindService(intent, this.C, 1);
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "startServiceImpl");
        }
    }

    public void addGeoFenceAlert(String str, double d, double d2, float f, long j, PendingIntent pendingIntent) {
        try {
            if (this.e == null) {
                this.e = new a(this.x);
                this.e.setActivateAction(DetectedActivity.WALKING);
            }
            this.e.addRoundGeoFence(new DPoint(d, d2), f, null, str, j, pendingIntent);
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "addGeoFenceAlert");
        }
    }

    final Intent b() {
        if (this.l == null) {
            this.l = new Intent(this.x, APSService.class);
        }
        String str = StringUtils.EMPTY_STRING;
        try {
            str = !TextUtils.isEmpty(AMapLocationClientOption.getAPIKEY()) ? AMapLocationClientOption.getAPIKEY() : k.f(this.x);
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "startServiceImpl p2");
        }
        this.l.putExtra("a", str);
        this.l.putExtra("b", k.c(this.x));
        this.l.putExtra("d", UmidtokenInfo.getUmidtoken());
        return this.l;
    }

    public AMapLocation getLastKnownLocation() {
        try {
            if (this.i == null) {
                return null;
            }
            h hVar = this.i;
            if (hVar.b == null) {
                hVar.b = hVar.d();
            }
            return (hVar.b != null && de.a(hVar.b.a())) ? hVar.b.a() : null;
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "getLastKnownLocation");
            return null;
        }
    }

    public boolean isStarted() {
        return this.y;
    }

    public void onDestroy() {
        try {
            a(WeatherDescription.WEATHER_DESCRIPTION_FLURRY, null, 0);
            this.o = true;
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "onDestroy");
        }
    }

    public void removeGeoFenceAlert(PendingIntent pendingIntent) {
        try {
            if (this.e != null) {
                this.e.a(pendingIntent);
            }
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "removeGeoFenceAlert 2");
        }
    }

    public void removeGeoFenceAlert(PendingIntent pendingIntent, String str) {
        try {
            if (this.e != null) {
                this.e.a(pendingIntent, str);
            }
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "removeGeoFenceAlert 1");
        }
    }

    public void setLocationListener(AMapLocationListener aMapLocationListener) {
        try {
            a(GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS, aMapLocationListener, 0);
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "setLocationListener");
        }
    }

    public void setLocationOption(AMapLocationClientOption aMapLocationClientOption) {
        try {
            a(WeatherDescription.WEATHER_DESCRIPTION_HURRICANE, aMapLocationClientOption.clone(), 0);
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "setLocationOption");
        }
    }

    public void startAssistantLocation() {
        try {
            a(WeatherDescription.WEATHER_DESCRIPTION_DOWNPOUR, null, 0);
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "startAssistantLocation");
        }
    }

    public void startLocation() {
        try {
            a(WeatherDescription.WEATHER_DESCRIPTION_CLOUDY, null, 0);
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "startLocation");
        }
    }

    public void stopAssistantLocation() {
        try {
            a(WeatherDescription.WEATHER_DESCRIPTION_RAINSTORM, null, 0);
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "stopAssistantLocation");
        }
    }

    public void stopLocation() {
        try {
            a(WeatherDescription.WEATHER_DESCRIPTION_OVERCAST, null, 0);
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "stopLocation");
        }
    }

    public void unRegisterLocationListener(AMapLocationListener aMapLocationListener) {
        try {
            a(WeatherDescription.WEATHER_DESCRIPTION_DRIZZLE, aMapLocationListener, 0);
        } catch (Throwable th) {
            cw.a(th, "AMapLocationManager", "unRegisterLocationListener");
        }
    }
}
