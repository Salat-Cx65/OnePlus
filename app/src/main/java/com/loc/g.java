package com.loc;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.AutoScrollHelper;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.DPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.Iterator;
import java.util.List;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

// compiled from: GPSLocation.java
public final class g {
    Handler a;
    LocationManager b;
    AMapLocationClientOption c;
    long d;
    boolean e;
    by f;
    db g;
    int h;
    int i;
    long j;
    LocationListener k;
    int l;
    GpsStatus m;
    public AMapLocation n;
    private Context o;
    private long p;
    private int q;
    private Listener r;

    public g(Context context, Handler handler) {
        this.p = 0;
        this.d = 0;
        this.e = false;
        this.q = 0;
        this.f = null;
        this.g = null;
        this.h = 240;
        this.i = 80;
        this.j = 0;
        this.k = new LocationListener() {
            public final void onLocationChanged(Location location) {
                int i = 0;
                if (g.this != null) {
                    g.this.removeMessages(DetectedActivity.RUNNING);
                }
                if (location != null) {
                    try {
                        AMapLocation aMapLocation = new AMapLocation(location);
                        aMapLocation.setLocationType(1);
                        Bundle extras = location.getExtras();
                        if (extras != null) {
                            i = extras.getInt("satellites");
                        }
                        if (!g.this.e) {
                            db.a(g.this.o, de.b() - g.this.p);
                            g.this.e = true;
                        }
                        if (de.a(location, g.this.l)) {
                            aMapLocation.setMock(true);
                            if (!g.this.c.isMockEnable()) {
                                db.a(null, 2152);
                                aMapLocation.setErrorCode(ConnectionResult.INTERRUPTED);
                                aMapLocation.setLocationDetail("GPSLocation has been mocked!");
                                aMapLocation.setLatitude(0.0d);
                                aMapLocation.setLongitude(0.0d);
                            }
                        }
                        aMapLocation.setSatellites(i);
                        g.a(g.this, aMapLocation);
                        g gVar = g.this;
                        try {
                            if (gVar.l >= 4) {
                                aMapLocation.setGpsAccuracyStatus(1);
                            } else if (gVar.l == 0) {
                                aMapLocation.setGpsAccuracyStatus(-1);
                            } else {
                                aMapLocation.setGpsAccuracyStatus(0);
                            }
                        } catch (Throwable th) {
                        }
                        AMapLocation b = g.b(g.this, aMapLocation);
                        g.c(g.this, b);
                        g gVar2 = g.this;
                        if (de.a(b) && g.this != null && gVar2.c.isNeedAddress()) {
                            long b2 = de.b();
                            if (gVar2.c.getInterval() <= 8000 || b2 - gVar2.j > gVar2.c.getInterval() - 8000) {
                                extras = new Bundle();
                                extras.putDouble("lat", b.getLatitude());
                                extras.putDouble("lon", b.getLongitude());
                                Message obtain = Message.obtain();
                                obtain.setData(extras);
                                obtain.what = 5;
                                if (gVar2.n == null) {
                                    g.this.sendMessage(obtain);
                                } else if (de.a(b, gVar2.n) > ((float) gVar2.i)) {
                                    g.this.sendMessage(obtain);
                                }
                            }
                        }
                        gVar2 = g.this;
                        AMapLocation aMapLocation2 = g.this.n;
                        if (aMapLocation2 != null && gVar2.c.isNeedAddress() && de.a(b, aMapLocation2) < ((float) gVar2.h)) {
                            cw.a(b, aMapLocation2);
                        }
                        gVar2 = g.this;
                        if ((b.getErrorCode() != 15 || AMapLocationMode.Device_Sensors.equals(gVar2.c.getLocationMode())) && de.b() - gVar2.j >= gVar2.c.getInterval() - 200) {
                            gVar2.j = de.b();
                            if (g.this != null) {
                                Message obtain2 = Message.obtain();
                                obtain2.obj = b;
                                obtain2.what = 2;
                                g.this.sendMessage(obtain2);
                            }
                        }
                        g.d(g.this, b);
                    } catch (Throwable th2) {
                        cw.a(th2, "GPSLocation", "onLocationChanged");
                    }
                }
            }

            public final void onProviderDisabled(String str) {
                try {
                    if (GeocodeSearch.GPS.equals(str)) {
                        g.this.d = 0;
                    }
                } catch (Throwable th) {
                    cw.a(th, "GPSLocation", "onProviderDisabled");
                }
            }

            public final void onProviderEnabled(String str) {
            }

            public final void onStatusChanged(String str, int i, Bundle bundle) {
                if (i == 0) {
                    try {
                        g.this.d = 0;
                    } catch (Throwable th) {
                        cw.a(th, "GPSLocation", "onStatusChanged");
                    }
                }
            }
        };
        this.l = 0;
        this.m = null;
        this.r = new Listener() {
            public final void onGpsStatusChanged(int i) {
                try {
                    g.this.m = g.this.b.getGpsStatus(g.this.m);
                    switch (i) {
                        case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                            Iterator it = g.this.m.getSatellites().iterator();
                            int i2 = 0;
                            int maxSatellites = g.this.m.getMaxSatellites();
                            while (it.hasNext() && i2 < maxSatellites) {
                                i2 = ((GpsSatellite) it.next()).usedInFix() ? i2 + 1 : i2;
                            }
                            g.this.l = i2;
                        default:
                            break;
                    }
                } catch (Throwable th) {
                    cw.a(th, "GPSLocation", "onGpsStatusChanged");
                }
            }
        };
        this.n = null;
        this.o = context;
        this.a = handler;
        this.b = (LocationManager) this.o.getSystemService("location");
        this.f = new by();
        this.g = new db();
    }

    private void a(int i, int i2, String str, long j) {
        if (this.a != null && this.c.getLocationMode() == AMapLocationMode.Device_Sensors) {
            Message obtain = Message.obtain();
            AMapLocation aMapLocation = new AMapLocation(StringUtils.EMPTY_STRING);
            aMapLocation.setProvider(GeocodeSearch.GPS);
            aMapLocation.setErrorCode(i2);
            aMapLocation.setLocationDetail(str);
            aMapLocation.setLocationType(1);
            obtain.obj = aMapLocation;
            obtain.what = i;
            this.a.sendMessageDelayed(obtain, j);
        }
    }

    static /* synthetic */ void a(g gVar, AMapLocation aMapLocation) {
        try {
            if (cw.a(aMapLocation.getLatitude(), aMapLocation.getLongitude()) && gVar.c.isOffset()) {
                DPoint a = cx.a(gVar.o, new DPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
                aMapLocation.setLatitude(a.getLatitude());
                aMapLocation.setLongitude(a.getLongitude());
            }
        } catch (Throwable th) {
        }
    }

    static /* synthetic */ AMapLocation b(g gVar, AMapLocation aMapLocation) {
        if (!de.a(aMapLocation) || gVar.q < 3) {
            return aMapLocation;
        }
        if (aMapLocation.getAccuracy() < 0.0f || aMapLocation.getAccuracy() == Float.MAX_VALUE) {
            aMapLocation.setAccuracy(AutoScrollHelper.RELATIVE_UNSPECIFIED);
        }
        if (aMapLocation.getSpeed() < 0.0f || aMapLocation.getSpeed() == Float.MAX_VALUE) {
            aMapLocation.setSpeed(AutoScrollHelper.RELATIVE_UNSPECIFIED);
        }
        AMapLocation a = gVar.f.a(aMapLocation);
        gVar.g.a(aMapLocation, a);
        return a;
    }

    static /* synthetic */ void c(g gVar, AMapLocation aMapLocation) {
        if (de.a(aMapLocation)) {
            gVar.d = de.b();
            gVar.q++;
        }
    }

    static /* synthetic */ void d(g gVar, AMapLocation aMapLocation) {
        try {
            if (aMapLocation.getErrorCode() == 0 && !cw.k && !dd.b(gVar.o, "pref", "colde", false)) {
                cw.k = true;
                dd.a(gVar.o, "pref", "colde", true);
            }
        } catch (Throwable th) {
        }
    }

    public final void a() {
        if (this.b != null) {
            if (this.k != null) {
                this.b.removeUpdates(this.k);
            }
            if (this.r != null) {
                this.b.removeGpsStatusListener(this.r);
            }
            if (this.a != null) {
                this.a.removeMessages(DetectedActivity.RUNNING);
            }
            this.l = 0;
            this.p = 0;
            this.j = 0;
            this.d = 0;
            this.q = 0;
            this.f.a();
            this.g.a();
        }
    }

    final void b() {
        try {
            Looper myLooper = Looper.myLooper();
            if (myLooper == null) {
                myLooper = this.o.getMainLooper();
            }
            this.p = de.b();
            try {
                this.b.sendExtraCommand(GeocodeSearch.GPS, "force_xtra_injection", null);
            } catch (SecurityException e) {
                a(RainSurfaceView.RAIN_LEVEL_SHOWER, WeatherCircleView.ARC_DIN, e.getMessage(), 0);
            } catch (Throwable th) {
            }
            List allProviders = this.b.getAllProviders();
            boolean contains = (allProviders == null || allProviders.size() == 0) ? false : allProviders.contains(GeocodeSearch.GPS);
            if (contains) {
                this.b.requestLocationUpdates(GeocodeSearch.GPS, 900, AutoScrollHelper.RELATIVE_UNSPECIFIED, this.k, myLooper);
                this.b.addGpsStatusListener(this.r);
                a(DetectedActivity.RUNNING, ConnectionResult.TIMEOUT, "no enough satellites", this.c.getHttpTimeOut());
                return;
            }
            a(DetectedActivity.RUNNING, ConnectionResult.TIMEOUT, "no gps provider", 0);
        } catch (SecurityException e2) {
            a(RainSurfaceView.RAIN_LEVEL_SHOWER, WeatherCircleView.ARC_DIN, e2.getMessage(), 0);
        } catch (Throwable th2) {
            cw.a(th2, "GPSLocation", "requestLocationUpdates part2");
        }
    }

    public final boolean c() {
        return de.b() - this.d <= 10000;
    }
}
