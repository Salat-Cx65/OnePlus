package com.loc;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat.MessagingStyle;
import android.support.v4.widget.AutoScrollHelper;
import android.text.TextUtils;
import android.util.Log;
import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.fence.GeoFenceManagerBase;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import net.oneplus.weather.app.WeatherWarningActivity;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

@SuppressLint({"NewApi"})
// compiled from: GeoFenceManager.java
public class a implements GeoFenceManagerBase {
    db a;
    Context b;
    PendingIntent c;
    String d;
    GeoFenceListener e;
    volatile int f;
    ArrayList<GeoFence> g;
    c h;
    Object i;
    a j;
    b k;
    volatile boolean l;
    volatile boolean m;
    b n;
    c o;
    AMapLocationClient p;
    volatile AMapLocation q;
    long r;
    AMapLocationClientOption s;
    int t;
    AMapLocationListener u;
    Hashtable<PendingIntent, ArrayList<GeoFence>> v;

    // compiled from: GeoFenceManager.java
    class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                        a.this.b(message.getData());
                    case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        a.this.c(message.getData());
                    case RainSurfaceView.RAIN_LEVEL_SHOWER:
                        a.this.e(message.getData());
                    case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                        a.this.d(message.getData());
                    case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                        a.this.f(message.getData());
                    case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                        a.this.c();
                    case ConnectionResult.RESOLUTION_REQUIRED:
                        a.this.a(a.this.q);
                    case DetectedActivity.WALKING:
                        a.this.b();
                    case DetectedActivity.RUNNING:
                        a.this.h(message.getData());
                    case ConnectionResult.SERVICE_INVALID:
                        a.this.a(message.getData());
                    case ConnectionResult.DEVELOPER_ERROR:
                        a.this.a();
                    default:
                        break;
                }
            } catch (Throwable th) {
            }
        }
    }

    // compiled from: GeoFenceManager.java
    static class b extends HandlerThread {
        public b(String str) {
            super(str);
        }
    }

    // compiled from: GeoFenceManager.java
    class c extends Handler {
        public c(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            Bundle data = message.getData();
            switch (message.what) {
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                    a.this.g(data);
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                    try {
                        a.this.a((GeoFence) data.getParcelable("geoFence"));
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                    try {
                        a.this.b(data.getInt(GeoFence.BUNDLE_KEY_LOCERRORCODE));
                    } catch (Throwable th2) {
                        th2.printStackTrace();
                    }
                default:
                    break;
            }
        }
    }

    public a(Context context) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = 1;
        this.g = new ArrayList();
        this.h = null;
        this.i = new Object();
        this.j = null;
        this.k = null;
        this.l = false;
        this.m = false;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = 0;
        this.s = null;
        this.t = 0;
        this.u = new AMapLocationListener() {
            public final void onLocationChanged(AMapLocation aMapLocation) {
                int i = DetectedActivity.RUNNING;
                Object obj = 1;
                try {
                    Bundle bundle;
                    a.this.q = aMapLocation;
                    if (aMapLocation != null) {
                        i = aMapLocation.getErrorCode();
                        if (aMapLocation.getErrorCode() == 0) {
                            a.this.r = de.b();
                            a.this.a(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, null, 0);
                            if (obj == null) {
                                a.this.t = 0;
                                a.this.a(ConnectionResult.RESOLUTION_REQUIRED, null, 0);
                            }
                            bundle = new Bundle();
                            if (!a.this.l) {
                                a.this.a((int) DetectedActivity.WALKING);
                                bundle.putLong("interval", 2000);
                                a.this.a(DetectedActivity.RUNNING, bundle, 2000);
                            }
                            a aVar = a.this;
                            aVar.t++;
                            if (a.this.t >= 3) {
                                bundle.putInt(GeoFence.BUNDLE_KEY_LOCERRORCODE, i);
                                a.this.a((int) GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS, bundle);
                                return;
                            }
                            return;
                        }
                        a aVar2 = a.this;
                        a.a("\u5b9a\u4f4d\u5931\u8d25", aMapLocation.getErrorCode(), aMapLocation.getErrorInfo(), new StringBuilder("locationDetail:").append(aMapLocation.getLocationDetail()).toString());
                    }
                    obj = null;
                    if (obj == null) {
                        bundle = new Bundle();
                        if (a.this.l) {
                            a.this.a((int) DetectedActivity.WALKING);
                            bundle.putLong("interval", 2000);
                            a.this.a(DetectedActivity.RUNNING, bundle, 2000);
                        }
                        a aVar3 = a.this;
                        aVar3.t++;
                        if (a.this.t >= 3) {
                            bundle.putInt(GeoFence.BUNDLE_KEY_LOCERRORCODE, i);
                            a.this.a((int) GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS, bundle);
                            return;
                        }
                        return;
                    }
                    a.this.t = 0;
                    a.this.a(ConnectionResult.RESOLUTION_REQUIRED, null, 0);
                } catch (Throwable th) {
                }
            }
        };
        this.v = new Hashtable();
        try {
            this.b = context.getApplicationContext();
            d();
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManger", "<init>");
        }
    }

    static float a(DPoint dPoint, List<DPoint> list) {
        if (dPoint == null || list == null || list.isEmpty()) {
            return AutoScrollHelper.NO_MAX;
        }
        float f = Float.MAX_VALUE;
        for (DPoint dPoint2 : list) {
            f = Math.min(f, de.a(dPoint, dPoint2));
        }
        return f;
    }

    private int a(List<GeoFence> list) {
        try {
            if (this.g == null) {
                this.g = new ArrayList();
            }
            for (GeoFence geoFence : list) {
                b(geoFence);
            }
            return 0;
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "addGeoFenceList");
            a("\u6dfb\u52a0\u56f4\u680f\u5931\u8d25", DetectedActivity.RUNNING, th.getMessage(), new String[0]);
            return 8;
        }
    }

    private static Bundle a(GeoFence geoFence, String str, String str2, int i, int i2) {
        Bundle bundle = new Bundle();
        String str3 = GeoFence.BUNDLE_KEY_FENCEID;
        if (str == null) {
            str = StringUtils.EMPTY_STRING;
        }
        bundle.putString(str3, str);
        bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str2);
        bundle.putInt(GeoFence.BUNDLE_KEY_FENCESTATUS, i);
        bundle.putInt(GeoFence.BUNDLE_KEY_LOCERRORCODE, i2);
        bundle.putParcelable(GeoFence.BUNDLE_KEY_FENCE, geoFence);
        return bundle;
    }

    private GeoFence a(Bundle bundle, boolean z) {
        PendingIntent pendingIntent;
        long j;
        Object obj;
        Object obj2;
        float f = 1000.0f;
        GeoFence geoFence = new GeoFence();
        List arrayList = new ArrayList();
        DPoint dPoint = new DPoint();
        List parcelableArrayList;
        if (z) {
            geoFence.setType(1);
            parcelableArrayList = bundle.getParcelableArrayList("points");
            if (parcelableArrayList != null) {
                dPoint = b(parcelableArrayList);
            }
            geoFence.setMaxDis2Center(b(dPoint, parcelableArrayList));
            geoFence.setMinDis2Center(a(dPoint, parcelableArrayList));
        } else {
            geoFence.setType(0);
            dPoint = (DPoint) bundle.getParcelable("point");
            if (dPoint != null) {
                arrayList.add(dPoint);
            }
            float f2 = bundle.getFloat("radius", 1000.0f);
            if (f2 > 0.0f) {
                f = f2;
            }
            geoFence.setRadius(f);
            geoFence.setMinDis2Center(f);
            geoFence.setMaxDis2Center(f);
            parcelableArrayList = arrayList;
        }
        geoFence.setActivatesAction(this.f);
        geoFence.setCustomId(bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID));
        List arrayList2 = new ArrayList();
        arrayList2.add(r1);
        geoFence.setPointList(arrayList2);
        geoFence.setCenter(dPoint);
        try {
            String string = bundle.getString(GeoFence.BUNDLE_KEY_FENCEID);
            try {
                long j2 = bundle.getLong("expiration", -1);
                try {
                    long j3 = j2;
                    pendingIntent = (PendingIntent) bundle.getParcelable("pIntent");
                    String str = string;
                    j = j3;
                } catch (Throwable th) {
                    j3 = j2;
                    String str2 = string;
                    j = j3;
                    obj = obj2;
                    pendingIntent = null;
                    if (TextUtils.isEmpty(obj)) {
                        geoFence.setFenceId(c.a());
                    } else {
                        geoFence.setFenceId(obj);
                    }
                    geoFence.setPendingIntentAction(this.d);
                    geoFence.setExpiration(j);
                    if (pendingIntent == null) {
                        geoFence.setPendingIntent(this.c);
                    } else {
                        geoFence.setPendingIntent(pendingIntent);
                    }
                    if (this.a != null) {
                        this.a.a(this.b, (int) RainSurfaceView.RAIN_LEVEL_SHOWER);
                    }
                    return geoFence;
                }
            } catch (Throwable th2) {
                str2 = string;
                j = -1;
                obj = obj2;
                pendingIntent = null;
                if (TextUtils.isEmpty(obj)) {
                    geoFence.setFenceId(c.a());
                } else {
                    geoFence.setFenceId(obj);
                }
                geoFence.setPendingIntentAction(this.d);
                geoFence.setExpiration(j);
                if (pendingIntent == null) {
                    geoFence.setPendingIntent(pendingIntent);
                } else {
                    geoFence.setPendingIntent(this.c);
                }
                if (this.a != null) {
                    this.a.a(this.b, (int) RainSurfaceView.RAIN_LEVEL_SHOWER);
                }
                return geoFence;
            }
        } catch (Throwable th3) {
            j = -1;
            obj2 = null;
            obj = obj2;
            pendingIntent = null;
            if (TextUtils.isEmpty(obj)) {
                geoFence.setFenceId(obj);
            } else {
                geoFence.setFenceId(c.a());
            }
            geoFence.setPendingIntentAction(this.d);
            geoFence.setExpiration(j);
            if (pendingIntent == null) {
                geoFence.setPendingIntent(this.c);
            } else {
                geoFence.setPendingIntent(pendingIntent);
            }
            if (this.a != null) {
                this.a.a(this.b, (int) RainSurfaceView.RAIN_LEVEL_SHOWER);
            }
            return geoFence;
        }
        if (TextUtils.isEmpty(obj)) {
            geoFence.setFenceId(c.a());
        } else {
            geoFence.setFenceId(obj);
        }
        geoFence.setPendingIntentAction(this.d);
        geoFence.setExpiration(j);
        if (pendingIntent == null) {
            geoFence.setPendingIntent(pendingIntent);
        } else {
            geoFence.setPendingIntent(this.c);
        }
        if (this.a != null) {
            this.a.a(this.b, (int) RainSurfaceView.RAIN_LEVEL_SHOWER);
        }
        return geoFence;
    }

    static void a(String str, int i, String str2, String... strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("===========================================\n");
        stringBuffer.append(new StringBuilder("              ").append(str).append("                ").toString()).append("\n");
        stringBuffer.append("-------------------------------------------\n");
        stringBuffer.append(new StringBuilder("errorCode:").append(i).toString()).append("\n");
        stringBuffer.append(new StringBuilder("\u9519\u8bef\u4fe1\u606f:").append(str2).toString()).append("\n");
        if (strArr != null && strArr.length > 0) {
            for (String str3 : strArr) {
                stringBuffer.append(str3).append("\n");
            }
        }
        stringBuffer.append("===========================================\n");
        Log.i("fenceErrLog", stringBuffer.toString());
    }

    private static boolean a(double d, double d2, double d3, double d4, double d5, double d6) {
        return Math.abs(((d3 - d) * (d6 - d2)) - ((d5 - d) * (d4 - d2))) < 1.0E-9d && (d - d3) * (d - d5) <= 0.0d && (d2 - d4) * (d2 - d6) <= 0.0d;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(com.amap.api.fence.GeoFence r5, int r6) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.a.a(com.amap.api.fence.GeoFence, int):boolean");
        /*
        r3 = 2;
        r0 = 1;
        r1 = 0;
        r2 = r6 & 1;
        if (r2 != r0) goto L_0x000e;
    L_0x0007:
        r2 = r5.getStatus();	 Catch:{ Throwable -> 0x0026 }
        if (r2 != r0) goto L_0x000e;
    L_0x000d:
        r1 = r0;
    L_0x000e:
        r2 = r6 & 2;
        if (r2 != r3) goto L_0x0019;
    L_0x0012:
        r2 = r5.getStatus();	 Catch:{ Throwable -> 0x0026 }
        if (r2 != r3) goto L_0x0019;
    L_0x0018:
        r1 = r0;
    L_0x0019:
        r2 = r6 & 4;
        r3 = 4;
        if (r2 != r3) goto L_0x0032;
    L_0x001e:
        r2 = r5.getStatus();	 Catch:{ Throwable -> 0x0026 }
        r3 = 3;
        if (r2 != r3) goto L_0x0032;
    L_0x0025:
        return r0;
    L_0x0026:
        r0 = move-exception;
        r4 = r0;
        r0 = r1;
        r1 = r4;
        r2 = "Utils";
        r3 = "remindStatus";
        com.loc.cw.a(r1, r2, r3);
        goto L_0x0025;
    L_0x0032:
        r0 = r1;
        goto L_0x0025;
        */
    }

    private static boolean a(AMapLocation aMapLocation, GeoFence geoFence) {
        boolean z = false;
        try {
            if (!de.a(aMapLocation) || geoFence == null || geoFence.getPointList() == null || geoFence.getPointList().isEmpty()) {
                return false;
            }
            Object obj;
            switch (geoFence.getType()) {
                case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    DPoint center = geoFence.getCenter();
                    obj = null;
                    if (de.a(new double[]{center.getLatitude(), center.getLongitude(), aMapLocation.getLatitude(), aMapLocation.getLongitude()}) <= geoFence.getRadius()) {
                        obj = 1;
                    }
                    return obj != null;
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    for (List list : geoFence.getPointList()) {
                        boolean z2;
                        int i = 0;
                        double longitude = aMapLocation.getLongitude();
                        double latitude = aMapLocation.getLatitude();
                        double latitude2 = aMapLocation.getLatitude();
                        if (list.size() < 3) {
                            obj = null;
                        } else {
                            if (!((DPoint) list.get(0)).equals(list.get(list.size() - 1))) {
                                list.add(list.get(0));
                            }
                            int i2 = 0;
                            while (i2 < list.size() - 1) {
                                double longitude2 = ((DPoint) list.get(i2)).getLongitude();
                                double latitude3 = ((DPoint) list.get(i2)).getLatitude();
                                double longitude3 = ((DPoint) list.get(i2 + 1)).getLongitude();
                                double latitude4 = ((DPoint) list.get(i2 + 1)).getLatitude();
                                if (a(longitude, latitude, longitude2, latitude3, longitude3, latitude4)) {
                                    obj = 1;
                                } else {
                                    int i3;
                                    if (Math.abs(latitude4 - latitude3) >= 1.0E-9d) {
                                        if (a(longitude2, latitude3, longitude, latitude, 180.0d, latitude2)) {
                                            if (latitude3 > latitude4) {
                                                i3 = i + 1;
                                                i = i3;
                                                i2++;
                                            }
                                        } else if (!a(longitude3, latitude4, longitude, latitude, 180.0d, latitude2)) {
                                            Object obj2;
                                            double d = ((longitude3 - longitude2) * (latitude2 - latitude)) - ((latitude4 - latitude3) * (180.0d - longitude));
                                            if (d != 0.0d) {
                                                double d2 = (((latitude3 - latitude) * (180.0d - longitude)) - ((longitude2 - longitude) * (latitude2 - latitude))) / d;
                                                longitude2 = (((longitude3 - longitude2) * (latitude3 - latitude)) - ((longitude2 - longitude) * (latitude4 - latitude3))) / d;
                                                if (d2 >= 0.0d && d2 <= 1.0d && longitude2 >= 0.0d && longitude2 <= 1.0d) {
                                                    obj2 = 1;
                                                    if (obj2 != null) {
                                                        i3 = i + 1;
                                                        i = i3;
                                                        i2++;
                                                    }
                                                }
                                            }
                                            obj2 = null;
                                            if (obj2 != null) {
                                                i3 = i + 1;
                                                i = i3;
                                                i2++;
                                            }
                                        } else if (latitude4 > latitude3) {
                                            i3 = i + 1;
                                            i = i3;
                                            i2++;
                                        }
                                    }
                                    i3 = i;
                                    i = i3;
                                    i2++;
                                }
                            }
                            obj = i % 2 != 0 ? 1 : null;
                        }
                        if (obj != null) {
                            obj = 1;
                        } else {
                            z2 = z;
                        }
                        z = z2;
                    }
                    return z;
                default:
                    return false;
            }
        } catch (Throwable th) {
            cw.a(th, "Utils", "isInGeoFence");
            return false;
        }
    }

    static float b(DPoint dPoint, List<DPoint> list) {
        if (dPoint == null || list == null || list.isEmpty()) {
            return Float.MIN_VALUE;
        }
        float f = Float.MIN_VALUE;
        for (DPoint dPoint2 : list) {
            f = Math.max(f, de.a(dPoint, dPoint2));
        }
        return f;
    }

    private int b(GeoFence geoFence) {
        try {
            if (this.g == null) {
                this.g = new ArrayList();
            }
            if (this.g.contains(geoFence)) {
                return ConnectionResult.SIGN_IN_FAILED;
            }
            this.g.add(geoFence);
            return 0;
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "addGeoFence2List");
            a("\u6dfb\u52a0\u56f4\u680f\u5931\u8d25", DetectedActivity.RUNNING, th.getMessage(), new String[0]);
            return 8;
        }
    }

    private static DPoint b(List<DPoint> list) {
        DPoint dPoint;
        double d = 0.0d;
        DPoint dPoint2 = new DPoint();
        if (list != null) {
            try {
                double d2 = 0.0d;
                for (DPoint dPoint3 : list) {
                    d2 += dPoint3.getLatitude();
                    d += dPoint3.getLongitude();
                }
                dPoint3 = new DPoint(de.c(d2 / ((double) list.size())), de.c(d / ((double) list.size())));
            } catch (Throwable th) {
                cw.a(th, "GeoFenceUtil", "getPolygonCenter");
                return dPoint2;
            }
        }
        dPoint3 = dPoint2;
        return dPoint3;
    }

    private static boolean b(AMapLocation aMapLocation, GeoFence geoFence) {
        boolean z = true;
        Object obj = null;
        try {
            boolean z2;
            if (a(aMapLocation, geoFence)) {
                if (geoFence.getEnterTime() == -1) {
                    if (geoFence.getStatus() != 1) {
                        geoFence.setEnterTime(de.b());
                        geoFence.setStatus(1);
                        return true;
                    }
                } else if (geoFence.getStatus() != 3 && de.b() - geoFence.getEnterTime() > 600000) {
                    geoFence.setStatus(RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
                    return true;
                }
            } else if (geoFence.getStatus() != 2) {
                try {
                    geoFence.setStatus(RainSurfaceView.RAIN_LEVEL_SHOWER);
                    geoFence.setEnterTime(-1);
                    z2 = true;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    cw.a(th2, "Utils", "isFenceStatusChanged");
                    return z;
                }
            }
            return z2;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            z = false;
            th2 = th4;
            cw.a(th2, "Utils", "isFenceStatusChanged");
            return z;
        }
    }

    private static int c(int i) {
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
            case DetectedActivity.WALKING:
            case ConnectionResult.API_UNAVAILABLE:
            case ConnectionResult.SIGN_IN_FAILED:
                break;
            case 10000:
                i = 0;
                break;
            case 10001:
            case 10002:
            case 10007:
            case 10008:
            case 10009:
            case 10012:
            case 10013:
                i = DetectedActivity.WALKING;
                break;
            case 10003:
            case 10004:
            case 10005:
            case 10006:
            case 10010:
            case 10011:
            case 10014:
            case 10015:
            case 10016:
            case 10017:
                i = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                break;
            case 20000:
            case 20001:
            case 20002:
                i = 1;
                break;
            case 20003:
                i = 8;
                break;
            default:
                i = 8;
                break;
        }
        if (i != 0) {
            a("\u6dfb\u52a0\u56f4\u680f\u5931\u8d25", i, new StringBuilder("searchErrCode is ").append(i).toString(), new String[0]);
        }
        return i;
    }

    private void c(GeoFence geoFence) {
        PendingIntent pendingIntent = geoFence.getPendingIntent();
        Object arrayList = new ArrayList();
        if (this.v.isEmpty()) {
            arrayList.add(geoFence);
            this.g.add(geoFence);
        } else {
            ArrayList arrayList2 = (ArrayList) this.v.get(pendingIntent);
            if (arrayList2 == null) {
                arrayList = new ArrayList();
            } else {
                GeoFence geoFence2;
                Object obj = null;
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    GeoFence geoFence3 = (GeoFence) it.next();
                    if (!geoFence3.getFenceId().equals(geoFence.getFenceId())) {
                        geoFence3 = geoFence2;
                    }
                    geoFence2 = geoFence3;
                }
                if (geoFence2 != null) {
                    arrayList2.remove(geoFence2);
                    this.g.remove(geoFence2);
                }
            }
            arrayList.add(geoFence);
            this.g.add(geoFence);
        }
        this.v.put(pendingIntent, arrayList);
    }

    private void d() {
        if (!this.m) {
            try {
                if (Looper.myLooper() == null) {
                    this.h = new c(this.b.getMainLooper());
                } else {
                    this.h = new c();
                }
            } catch (Throwable th) {
                cw.a(th, "GeoFenceManger", "init 1");
            }
            try {
                this.k = new b("fenceActionThread");
                this.k.setPriority(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER);
                this.k.start();
                this.j = new a(this.k.getLooper());
            } catch (Throwable th2) {
                cw.a(th2, "GeoFenceManger", "init 2");
            }
            try {
                Context context = this.b;
                this.n = new b();
                this.o = new c();
                this.s = new AMapLocationClientOption();
                this.p = new AMapLocationClient(this.b);
                this.s.setLocationCacheEnable(false);
                this.p.setLocationListener(this.u);
                if (this.a == null) {
                    this.a = new db();
                }
            } catch (Throwable th22) {
                cw.a(th22, "GeoFenceManger", "init 3");
            }
            this.m = true;
            try {
                if (this.d != null && this.c == null) {
                    createPendingIntent(this.d);
                }
            } catch (Throwable th222) {
                cw.a(th222, "GeoFenceManger", "init 4");
            }
        }
    }

    private static boolean d(GeoFence geoFence) {
        return geoFence.getExpiration() != -1 && geoFence.getExpiration() <= de.b();
    }

    private boolean e() {
        return this.q != null && de.a(this.q) && de.b() - this.r < 10000;
    }

    final void a() {
        try {
            if (this.m) {
                try {
                    synchronized (this.i) {
                        if (this.j != null) {
                            this.j.removeCallbacksAndMessages(null);
                        }
                        this.j = null;
                    }
                } catch (Throwable th) {
                    cw.a(th, "GeoFenceManager", "destroyActionHandler");
                }
                if (this.p != null) {
                    this.p.stopLocation();
                    this.p.onDestroy();
                }
                this.p = null;
                if (this.k != null) {
                    if (VERSION.SDK_INT >= 18) {
                        this.k.quitSafely();
                    } else {
                        this.k.quit();
                    }
                }
                this.k = null;
                if (this.g != null) {
                    this.g.clear();
                    this.g = null;
                }
                this.n = null;
                if (this.c != null) {
                    this.c.cancel();
                }
                this.c = null;
                if (this.a != null) {
                    this.a.b(this.b);
                }
                this.m = false;
            }
        } catch (Throwable th2) {
        }
    }

    final void a(int i) {
        try {
            synchronized (this.i) {
                if (this.j != null) {
                    this.j.removeMessages(i);
                }
            }
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "removeActionHandlerMessage");
        }
    }

    final void a(int i, Bundle bundle) {
        try {
            if (this.h != null) {
                Message obtainMessage = this.h.obtainMessage();
                obtainMessage.what = i;
                obtainMessage.setData(bundle);
                this.h.sendMessage(obtainMessage);
            }
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "sendResultHandlerMessage");
        }
    }

    final void a(int i, Bundle bundle, long j) {
        try {
            synchronized (this.i) {
                if (this.j != null) {
                    Message obtainMessage = this.j.obtainMessage();
                    obtainMessage.what = i;
                    obtainMessage.setData(bundle);
                    this.j.sendMessageDelayed(obtainMessage, j);
                }
            }
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "sendActionHandlerMessage");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(android.app.PendingIntent r4) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.a.a(android.app.PendingIntent):void");
        /*
        this = this;
        if (r4 == 0) goto L_0x000a;
    L_0x0002:
        r0 = r3.v;	 Catch:{ Throwable -> 0x0027 }
        r0 = r0.containsKey(r4);	 Catch:{ Throwable -> 0x0027 }
        if (r0 != 0) goto L_0x000b;
    L_0x000a:
        return;
    L_0x000b:
        r0 = r3.v;	 Catch:{ Throwable -> 0x0027 }
        r0 = r0.get(r4);	 Catch:{ Throwable -> 0x0027 }
        r0 = (java.util.ArrayList) r0;	 Catch:{ Throwable -> 0x0027 }
        r2 = r0.iterator();	 Catch:{ Throwable -> 0x0027 }
    L_0x0017:
        r1 = r2.hasNext();	 Catch:{ Throwable -> 0x0027 }
        if (r1 == 0) goto L_0x0030;
    L_0x001d:
        r1 = r2.next();	 Catch:{ Throwable -> 0x0027 }
        r1 = (com.amap.api.fence.GeoFence) r1;	 Catch:{ Throwable -> 0x0027 }
        r0.remove(r1);	 Catch:{ Throwable -> 0x0027 }
        goto L_0x0017;
    L_0x0027:
        r0 = move-exception;
        r1 = "AMapLocationManager";
        r2 = "doRemoveGeoFenceAlert2";
        com.loc.cw.a(r0, r1, r2);
        goto L_0x000a;
    L_0x0030:
        r0 = r3.v;	 Catch:{ Throwable -> 0x0027 }
        r0.remove(r4);	 Catch:{ Throwable -> 0x0027 }
        goto L_0x000a;
        */
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(android.app.PendingIntent r4, java.lang.String r5) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.a.a(android.app.PendingIntent, java.lang.String):void");
        /*
        this = this;
        if (r4 == 0) goto L_0x000a;
    L_0x0002:
        r0 = r3.v;	 Catch:{ Throwable -> 0x004c }
        r0 = r0.containsKey(r4);	 Catch:{ Throwable -> 0x004c }
        if (r0 != 0) goto L_0x000b;
    L_0x000a:
        return;
    L_0x000b:
        r0 = android.text.TextUtils.isEmpty(r5);	 Catch:{ Throwable -> 0x004c }
        if (r0 != 0) goto L_0x000a;
    L_0x0011:
        r0 = r3.v;	 Catch:{ Throwable -> 0x004c }
        r0 = r0.isEmpty();	 Catch:{ Throwable -> 0x004c }
        if (r0 != 0) goto L_0x000a;
    L_0x0019:
        r0 = r3.v;	 Catch:{ Throwable -> 0x004c }
        r0 = r0.get(r4);	 Catch:{ Throwable -> 0x004c }
        r0 = (java.util.ArrayList) r0;	 Catch:{ Throwable -> 0x004c }
        r1 = r0.iterator();	 Catch:{ Throwable -> 0x004c }
    L_0x0025:
        if (r1 == 0) goto L_0x000a;
    L_0x0027:
        r0 = r1.hasNext();	 Catch:{ Throwable -> 0x004c }
        if (r0 == 0) goto L_0x000a;
    L_0x002d:
        r0 = r1.next();	 Catch:{ Throwable -> 0x004c }
        r0 = (com.amap.api.fence.GeoFence) r0;	 Catch:{ Throwable -> 0x004c }
        r2 = r0.getFenceId();	 Catch:{ Throwable -> 0x004c }
        r2 = r5.equals(r2);	 Catch:{ Throwable -> 0x004c }
        if (r2 != 0) goto L_0x0043;
    L_0x003d:
        r2 = d(r0);	 Catch:{ Throwable -> 0x004c }
        if (r2 == 0) goto L_0x0025;
    L_0x0043:
        r1.remove();	 Catch:{ Throwable -> 0x004c }
        r2 = r3.g;	 Catch:{ Throwable -> 0x004c }
        r2.remove(r0);	 Catch:{ Throwable -> 0x004c }
        goto L_0x0025;
    L_0x004c:
        r0 = move-exception;
        r1 = "GeoFenceManager";
        r2 = "doRemoveGeoFenceAlert";
        com.loc.cw.a(r0, r1, r2);
        goto L_0x000a;
        */
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final void a(android.os.Bundle r7) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.a.a(android.os.Bundle):void");
        /*
        this = this;
        r0 = 1;
        if (r7 == 0) goto L_0x0060;
    L_0x0003:
        r0 = "activatesAction";
        r1 = 1;
        r0 = r7.getInt(r0, r1);	 Catch:{ Throwable -> 0x0037 }
        r1 = r0;
    L_0x000b:
        r0 = r6.f;	 Catch:{ Throwable -> 0x0037 }
        if (r0 == r1) goto L_0x0051;
    L_0x000f:
        r0 = r6.g;	 Catch:{ Throwable -> 0x0037 }
        if (r0 == 0) goto L_0x0040;
    L_0x0013:
        r0 = r6.g;	 Catch:{ Throwable -> 0x0037 }
        r0 = r0.isEmpty();	 Catch:{ Throwable -> 0x0037 }
        if (r0 != 0) goto L_0x0040;
    L_0x001b:
        r0 = r6.g;	 Catch:{ Throwable -> 0x0037 }
        r2 = r0.iterator();	 Catch:{ Throwable -> 0x0037 }
    L_0x0021:
        r0 = r2.hasNext();	 Catch:{ Throwable -> 0x0037 }
        if (r0 == 0) goto L_0x0040;
    L_0x0027:
        r0 = r2.next();	 Catch:{ Throwable -> 0x0037 }
        r0 = (com.amap.api.fence.GeoFence) r0;	 Catch:{ Throwable -> 0x0037 }
        r3 = 0;
        r0.setStatus(r3);	 Catch:{ Throwable -> 0x0037 }
        r4 = -1;
        r0.setEnterTime(r4);	 Catch:{ Throwable -> 0x0037 }
        goto L_0x0021;
    L_0x0037:
        r0 = move-exception;
        r1 = "GeoFenceManager";
        r2 = "doSetActivatesAction";
        com.loc.cw.a(r0, r1, r2);
    L_0x003f:
        return;
    L_0x0040:
        r0 = r6.j;	 Catch:{ Throwable -> 0x0037 }
        if (r0 == 0) goto L_0x0051;
    L_0x0044:
        r0 = r6.e();	 Catch:{ Throwable -> 0x0037 }
        if (r0 == 0) goto L_0x0054;
    L_0x004a:
        r0 = 6;
        r2 = 0;
        r4 = 0;
        r6.a(r0, r2, r4);	 Catch:{ Throwable -> 0x0037 }
    L_0x0051:
        r6.f = r1;	 Catch:{ Throwable -> 0x0037 }
        goto L_0x003f;
    L_0x0054:
        r0 = 7;
        r6.a(r0);	 Catch:{ Throwable -> 0x0037 }
        r0 = 7;
        r2 = 0;
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r6.a(r0, r2, r4);	 Catch:{ Throwable -> 0x0037 }
        goto L_0x0051;
    L_0x0060:
        r1 = r0;
        goto L_0x000b;
        */
    }

    final void a(GeoFence geoFence) {
        try {
            if (this.b == null) {
                return;
            }
            if (this.c != null || geoFence.getPendingIntent() != null) {
                Intent intent = new Intent();
                intent.putExtras(a(geoFence, geoFence.getFenceId(), geoFence.getCustomId(), geoFence.getStatus(), 0));
                if (this.d != null) {
                    intent.setAction(this.d);
                }
                intent.setPackage(k.c(this.b));
                if (geoFence.getPendingIntent() != null) {
                    geoFence.getPendingIntent().send(this.b, 0, intent);
                } else {
                    this.c.send(this.b, 0, intent);
                }
            }
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "resultTriggerGeoFence");
        }
    }

    final void a(AMapLocation aMapLocation) {
        try {
            if (this.g != null && !this.g.isEmpty() && aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                Iterator it = this.g.iterator();
                while (it.hasNext()) {
                    GeoFence geoFence = (GeoFence) it.next();
                    if (!d(geoFence) && b(aMapLocation, geoFence) && a(geoFence, this.f)) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("geoFence", geoFence);
                        a((int) GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES, bundle);
                    }
                }
            }
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "doCheckFence");
        }
    }

    public void addDistrictGeoFence(String str, String str2) {
        try {
            d();
            Bundle bundle = new Bundle();
            bundle.putString("keyword", str);
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str2);
            a(RainSurfaceView.RAIN_LEVEL_RAINSTORM, bundle, 0);
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "addDistricetGeoFence");
        }
    }

    public void addKeywordGeoFence(String str, String str2, String str3, int i, String str4) {
        int i2 = MessagingStyle.MAXIMUM_RETAINED_MESSAGES;
        try {
            d();
            if (i <= 0) {
                Object obj = ConnectionResult.DEVELOPER_ERROR;
            } else {
                int i3 = i;
            }
            if (i3 <= 25) {
                i2 = i3;
            }
            Bundle bundle = new Bundle();
            bundle.putString("keyword", str);
            bundle.putString("poiType", str2);
            bundle.putString(WeatherWarningActivity.INTENT_PARA_CITY, str3);
            bundle.putInt("size", i2);
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str4);
            a(RainSurfaceView.RAIN_LEVEL_SHOWER, bundle, 0);
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "addKeywordGeoFence");
        }
    }

    public void addNearbyGeoFence(String str, String str2, DPoint dPoint, float f, int i, String str3) {
        int i2 = MessagingStyle.MAXIMUM_RETAINED_MESSAGES;
        try {
            d();
            if (f <= 0.0f || f > 50000.0f) {
                f = 3000.0f;
            }
            if (i <= 0) {
                Object obj = ConnectionResult.DEVELOPER_ERROR;
            } else {
                int i3 = i;
            }
            if (i3 <= 25) {
                i2 = i3;
            }
            Bundle bundle = new Bundle();
            bundle.putString("keyword", str);
            bundle.putString("poiType", str2);
            bundle.putParcelable("centerPoint", dPoint);
            bundle.putFloat("aroundRadius", f);
            bundle.putInt("size", i2);
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str3);
            a(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, bundle, 0);
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "addNearbyGeoFence");
        }
    }

    public void addPolygonGeoFence(List<DPoint> list, String str) {
        try {
            d();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("points", new ArrayList(list));
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str);
            a(1, bundle, 0);
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "addPolygonGeoFence");
        }
    }

    public void addRoundGeoFence(DPoint dPoint, float f, String str, String str2, long j, PendingIntent pendingIntent) {
        try {
            d();
            Bundle bundle = new Bundle();
            bundle.putParcelable("point", dPoint);
            bundle.putFloat("radius", f);
            bundle.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str);
            bundle.putString(GeoFence.BUNDLE_KEY_FENCEID, str2);
            bundle.putLong("expiration", j);
            bundle.putParcelable("pIntent", pendingIntent);
            a(0, bundle, 0);
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "addRoundGeoFence");
        }
    }

    final void b() {
        try {
            if (this.p != null) {
                try {
                    if (this.l) {
                        a((int) DetectedActivity.RUNNING);
                    }
                    if (this.p != null) {
                        this.p.stopLocation();
                    }
                    this.l = false;
                } catch (Throwable th) {
                }
                this.s.setOnceLocation(true);
                this.p.setLocationOption(this.s);
                this.p.startLocation();
            }
        } catch (Throwable th2) {
            cw.a(th2, "GeoFenceManager", "doStartOnceLocation");
        }
    }

    final void b(int i) {
        try {
            if (this.b != null && this.c != null) {
                Intent intent = new Intent();
                intent.putExtras(a(null, null, null, RainSurfaceView.RAIN_LEVEL_RAINSTORM, i));
                this.c.send(this.b, 0, intent);
            }
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "resultRemindLocationError");
        }
    }

    final void b(Bundle bundle) {
        try {
            String string;
            int i;
            Bundle bundle2;
            ArrayList arrayList = new ArrayList();
            String str = StringUtils.EMPTY_STRING;
            if (!(bundle == null || bundle.isEmpty())) {
                DPoint dPoint = (DPoint) bundle.getParcelable("point");
                CharSequence string2 = bundle.getString(GeoFence.BUNDLE_KEY_FENCEID);
                string = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                if (dPoint == null) {
                    str = string;
                } else if (dPoint.getLatitude() > 90.0d || dPoint.getLatitude() < -90.0d || dPoint.getLongitude() > 180.0d || dPoint.getLongitude() < -180.0d) {
                    a("\u6dfb\u52a0\u56f4\u680f\u5931\u8d25", 1, new StringBuilder("\u7ecf\u7eac\u5ea6\u9519\u8bef\uff0c\u4f20\u5165\u7684\u7eac\u5ea6\uff1a").append(dPoint.getLatitude()).append("\u4f20\u5165\u7684\u7ecf\u5ea6:").append(dPoint.getLongitude()).toString(), new String[0]);
                    i = 1;
                    bundle2 = new Bundle();
                    bundle2.putInt("errorCode", i);
                    bundle2.putParcelableArrayList("resultList", arrayList);
                    bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, string);
                    a((int) GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, bundle2);
                } else {
                    GeoFence a = a(bundle, false);
                    if (TextUtils.isEmpty(string2)) {
                        i = b(a);
                    } else {
                        c(a);
                        i = 0;
                    }
                    if (i == 0) {
                        arrayList.add(a);
                    }
                    bundle2 = new Bundle();
                    bundle2.putInt("errorCode", i);
                    bundle2.putParcelableArrayList("resultList", arrayList);
                    bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, string);
                    a((int) GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, bundle2);
                }
            }
            string = str;
            i = 1;
            bundle2 = new Bundle();
            bundle2.putInt("errorCode", i);
            bundle2.putParcelableArrayList("resultList", arrayList);
            bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, string);
            a((int) GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, bundle2);
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "doAddGeoFence_round");
        }
    }

    final void c() {
        try {
            if (de.a(this.q)) {
                AMapLocation aMapLocation = this.q;
                List<GeoFence> list = this.g;
                float f = AutoScrollHelper.NO_MAX;
                if (!(aMapLocation == null || aMapLocation.getErrorCode() != 0 || list == null || list.isEmpty())) {
                    DPoint dPoint = new DPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    float f2 = Float.MAX_VALUE;
                    for (GeoFence geoFence : list) {
                        float a = de.a(dPoint, geoFence.getCenter());
                        if (a > geoFence.getMinDis2Center() && a < geoFence.getMaxDis2Center()) {
                            f = AutoScrollHelper.RELATIVE_UNSPECIFIED;
                            break;
                        }
                        if (a > geoFence.getMaxDis2Center()) {
                            f2 = Math.min(f2, a - geoFence.getMaxDis2Center());
                        }
                        f2 = a < geoFence.getMinDis2Center() ? Math.min(f2, geoFence.getMinDis2Center() - a) : f2;
                    }
                    f = f2;
                }
                if (f < 1000.0f) {
                    a((int) DetectedActivity.WALKING);
                    Bundle bundle = new Bundle();
                    bundle.putLong("interval", 2000);
                    a(DetectedActivity.RUNNING, bundle, 1000);
                } else if (f < 5000.0f) {
                    a((int) DetectedActivity.WALKING);
                    a(DetectedActivity.WALKING, null, 10000);
                } else {
                    a((int) DetectedActivity.WALKING);
                    a(DetectedActivity.WALKING, null, (long) (((f - 4000.0f) / 100.0f) * 1000.0f));
                }
            }
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "doCheckLocationPolicy");
        }
    }

    final void c(Bundle bundle) {
        int i = 1;
        try {
            ArrayList arrayList = new ArrayList();
            String str = StringUtils.EMPTY_STRING;
            if (!(bundle == null || bundle.isEmpty())) {
                List parcelableArrayList = bundle.getParcelableArrayList("points");
                str = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                if (parcelableArrayList != null && parcelableArrayList.size() > 2) {
                    GeoFence a = a(bundle, true);
                    i = b(a);
                    if (i == 0) {
                        arrayList.add(a);
                    }
                }
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str);
            bundle2.putInt("errorCode", i);
            bundle2.putParcelableArrayList("resultList", arrayList);
            a((int) GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, bundle2);
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "doAddGeoFence_polygon");
        }
    }

    public PendingIntent createPendingIntent(String str) {
        try {
            d();
            Intent intent = new Intent();
            intent.setPackage(k.c(this.b));
            intent.setAction(str);
            this.c = PendingIntent.getBroadcast(this.b, 0, intent, 0);
            this.d = str;
            if (!(this.g == null || this.g.isEmpty())) {
                Iterator it = this.g.iterator();
                while (it.hasNext()) {
                    GeoFence geoFence = (GeoFence) it.next();
                    geoFence.setPendingIntent(this.c);
                    geoFence.setPendingIntentAction(this.d);
                }
            }
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "createPendingIntent");
        }
        return this.c;
    }

    final void d(Bundle bundle) {
        int i = 1;
        try {
            Bundle bundle2;
            String str = StringUtils.EMPTY_STRING;
            ArrayList arrayList = new ArrayList();
            if (!(bundle == null || bundle.isEmpty())) {
                Object string = bundle.getString("keyword");
                String string2 = bundle.getString("poiType");
                DPoint dPoint = (DPoint) bundle.getParcelable("centerPoint");
                float f = bundle.getFloat("aroundRadius", 3000.0f);
                int i2 = bundle.getInt("size", ConnectionResult.DEVELOPER_ERROR);
                String string3 = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                if (dPoint == null || TextUtils.isEmpty(string)) {
                    str = string3;
                } else if (dPoint.getLatitude() > 90.0d || dPoint.getLatitude() < -90.0d || dPoint.getLongitude() > 180.0d || dPoint.getLongitude() < -180.0d) {
                    a("\u6dfb\u52a0\u56f4\u680f\u5931\u8d25", 1, new StringBuilder("\u7ecf\u7eac\u5ea6\u9519\u8bef\uff0c\u4f20\u5165\u7684\u7eac\u5ea6\uff1a").append(dPoint.getLatitude()).append("\u4f20\u5165\u7684\u7ecf\u5ea6:").append(dPoint.getLongitude()).toString(), new String[0]);
                    str = string3;
                } else {
                    str = this.n.a(this.b, "http://restapi.amap.com/v3/place/around?", string, string2, String.valueOf(i2), String.valueOf(de.c(dPoint.getLatitude())), String.valueOf(de.c(dPoint.getLongitude())), String.valueOf(Float.valueOf(f).intValue()));
                    if (str != null) {
                        List arrayList2 = new ArrayList();
                        bundle2 = new Bundle();
                        bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, string3);
                        bundle2.putString("pendingIntentAction", this.d);
                        bundle2.putLong("expiration", -1);
                        bundle2.putInt("activatesAction", this.f);
                        bundle2.putFloat("geoRadius", 200.0f);
                        c cVar = this.o;
                        int b = c.b(str, arrayList2, bundle2);
                        if (b != 10000) {
                            b = c(b);
                        } else if (arrayList2.isEmpty()) {
                            i = 16;
                            str = string3;
                        } else {
                            b = a(arrayList2);
                            if (b == 0) {
                                arrayList.addAll(arrayList2);
                                i = b;
                                str = string3;
                            }
                        }
                        i = b;
                        str = string3;
                    } else {
                        i = 4;
                        str = string3;
                    }
                }
            }
            bundle2 = new Bundle();
            bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str);
            bundle2.putInt("errorCode", i);
            bundle2.putParcelableArrayList("resultList", arrayList);
            a((int) GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, bundle2);
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "doAddGeoFence_nearby");
        }
    }

    final void e(Bundle bundle) {
        try {
            int i;
            String str = StringUtils.EMPTY_STRING;
            ArrayList arrayList = new ArrayList();
            if (bundle == null || bundle.isEmpty()) {
                i = 1;
            } else {
                CharSequence string = bundle.getString("keyword");
                CharSequence string2 = bundle.getString("poiType");
                String string3 = bundle.getString(WeatherWarningActivity.INTENT_PARA_CITY);
                int i2 = bundle.getInt("size", ConnectionResult.DEVELOPER_ERROR);
                String string4 = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                Bundle bundle2 = new Bundle();
                bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, string4);
                bundle2.putString("pendingIntentAction", this.d);
                bundle2.putLong("expiration", -1);
                bundle2.putInt("activatesAction", this.f);
                bundle2.putFloat("geoRadius", 1000.0f);
                str = this.n.a(this.b, "http://restapi.amap.com/v3/place/text?", string, string2, string3, String.valueOf(i2));
                if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
                    str = string4;
                    i = 1;
                } else if (str != null) {
                    List arrayList2 = new ArrayList();
                    c cVar = this.o;
                    int a = c.a(str, arrayList2, bundle2);
                    if (a != 10000) {
                        a = c(a);
                    } else if (arrayList2.isEmpty()) {
                        i = 16;
                        str = string4;
                    } else {
                        a = a(arrayList2);
                        if (a == 0) {
                            arrayList.addAll(arrayList2);
                            i = a;
                            str = string4;
                        }
                    }
                    i = a;
                    str = string4;
                } else {
                    i = 4;
                    str = string4;
                }
            }
            Bundle bundle3 = new Bundle();
            bundle3.putString(GeoFence.BUNDLE_KEY_CUSTOMID, str);
            bundle3.putInt("errorCode", i);
            bundle3.putParcelableArrayList("resultList", arrayList);
            a((int) GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, bundle3);
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "doAddGeoFence_Keyword");
        }
    }

    final void f(Bundle bundle) {
        try {
            String string;
            int c;
            Bundle bundle2;
            ArrayList arrayList = new ArrayList();
            String str = StringUtils.EMPTY_STRING;
            if (!(bundle == null || bundle.isEmpty())) {
                str = bundle.getString("keyword");
                string = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                String a = this.n.a(this.b, "http://restapi.amap.com/v3/config/district?", str);
                if (TextUtils.isEmpty(str)) {
                    str = string;
                } else {
                    if (a != null) {
                        Bundle bundle3 = new Bundle();
                        bundle3.putString(GeoFence.BUNDLE_KEY_CUSTOMID, string);
                        bundle3.putString("pendingIntentAction", this.d);
                        bundle3.putLong("expiration", -1);
                        bundle3.putInt("activatesAction", this.f);
                        List arrayList2 = new ArrayList();
                        c = this.o.c(a, arrayList2, bundle3);
                        if (c != 10000) {
                            c = c(c);
                        } else if (arrayList2.isEmpty()) {
                            c = ConnectionResult.API_UNAVAILABLE;
                        } else {
                            c = a(arrayList2);
                            if (c == 0) {
                                arrayList.addAll(arrayList2);
                            }
                        }
                    } else {
                        c = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                    }
                    bundle2 = new Bundle();
                    bundle2.putInt("errorCode", c);
                    bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, string);
                    bundle2.putParcelableArrayList("resultList", arrayList);
                    a((int) GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, bundle2);
                }
            }
            String str2 = str;
            c = 1;
            string = str2;
            bundle2 = new Bundle();
            bundle2.putInt("errorCode", c);
            bundle2.putString(GeoFence.BUNDLE_KEY_CUSTOMID, string);
            bundle2.putParcelableArrayList("resultList", arrayList);
            a((int) GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, bundle2);
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "doAddGeoFence_district");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final void g(android.os.Bundle r5) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.a.g(android.os.Bundle):void");
        /*
        this = this;
        if (r5 == 0) goto L_0x004c;
    L_0x0002:
        r0 = r5.isEmpty();	 Catch:{ Throwable -> 0x0059 }
        if (r0 != 0) goto L_0x004c;
    L_0x0008:
        r0 = "errorCode";
        r1 = r5.getInt(r0);	 Catch:{ Throwable -> 0x0059 }
        r0 = "resultList";
        r2 = r5.getParcelableArrayList(r0);	 Catch:{ Throwable -> 0x0059 }
        r0 = "customId";
        r0 = r5.getString(r0);	 Catch:{ Throwable -> 0x0059 }
        if (r0 != 0) goto L_0x001e;
    L_0x001c:
        r0 = "";
    L_0x001e:
        r3 = r4.e;	 Catch:{ Throwable -> 0x0059 }
        if (r3 == 0) goto L_0x0027;
    L_0x0022:
        r3 = r4.e;	 Catch:{ Throwable -> 0x0059 }
        r3.onGeoFenceCreateFinished(r2, r1, r0);	 Catch:{ Throwable -> 0x0059 }
    L_0x0027:
        if (r1 != 0) goto L_0x004c;
    L_0x0029:
        r0 = r4.j;	 Catch:{ Throwable -> 0x0059 }
        if (r0 == 0) goto L_0x004c;
    L_0x002d:
        r0 = r4.e();	 Catch:{ Throwable -> 0x0059 }
        if (r0 == 0) goto L_0x004d;
    L_0x0033:
        r0 = new android.os.Bundle;	 Catch:{ Throwable -> 0x0059 }
        r0.<init>();	 Catch:{ Throwable -> 0x0059 }
        r1 = "loc";
        r2 = r4.q;	 Catch:{ Throwable -> 0x0059 }
        r0.putParcelable(r1, r2);	 Catch:{ Throwable -> 0x0059 }
        r0 = 6;
        r1 = 0;
        r2 = 0;
        r4.a(r0, r1, r2);	 Catch:{ Throwable -> 0x0059 }
        r0 = 5;
        r2 = 0;
        r4.a(r0, r5, r2);	 Catch:{ Throwable -> 0x0059 }
    L_0x004c:
        return;
    L_0x004d:
        r0 = 7;
        r4.a(r0);	 Catch:{ Throwable -> 0x0059 }
        r0 = 7;
        r1 = 0;
        r2 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4.a(r0, r1, r2);	 Catch:{ Throwable -> 0x0059 }
        goto L_0x004c;
    L_0x0059:
        r0 = move-exception;
        r1 = "GeoFenceManager";
        r2 = "resultAddGeoFenceFinished";
        com.loc.cw.a(r0, r1, r2);
        goto L_0x004c;
        */
    }

    public List<GeoFence> getAllGeoFence() {
        try {
            if (this.g == null) {
                this.g = new ArrayList();
            }
            return (ArrayList) this.g.clone();
        } catch (Throwable th) {
            return new ArrayList();
        }
    }

    final void h(Bundle bundle) {
        try {
            if (this.p != null) {
                long j = 2000;
                if (!(bundle == null || bundle.isEmpty())) {
                    j = bundle.getLong("interval");
                }
                this.s.setOnceLocation(false);
                this.s.setInterval(j);
                this.p.setLocationOption(this.s);
                if (!this.l) {
                    this.p.stopLocation();
                    this.p.startLocation();
                    this.l = true;
                }
            }
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "doStartContinueLocation");
        }
    }

    public void removeGeoFence() {
        a(ConnectionResult.DEVELOPER_ERROR, null, 0);
    }

    public boolean removeGeoFence(GeoFence geoFence) {
        boolean z = false;
        try {
            if (this.g != null) {
                d();
                z = this.g.remove(geoFence);
                if (z && this.g.size() == 0) {
                    a(ConnectionResult.DEVELOPER_ERROR, null, 0);
                }
            }
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "removeGeoFence(GeoFence)");
        }
        return z;
    }

    public void setActivateAction(int i) {
        try {
            d();
            if (i > 7 || i <= 0) {
                i = 1;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("activatesAction", i);
            a(ConnectionResult.SERVICE_INVALID, bundle, 0);
        } catch (Throwable th) {
            cw.a(th, "GeoFenceManager", "setActivateAction");
        }
    }

    public void setGeoFenceListener(GeoFenceListener geoFenceListener) {
        try {
            this.e = geoFenceListener;
        } catch (Throwable th) {
        }
    }
}
