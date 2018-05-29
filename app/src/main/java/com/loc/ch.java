package com.loc;

import android.content.Context;
import com.amap.api.location.AMapLocationClientOption;
import com.autonavi.aps.amapapi.model.AMapLocationServer;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.json.JSONObject;

// compiled from: OffLineManager.java
public final class ch {
    private String a;
    private Object b;
    private boolean c;
    private boolean d;
    private Context e;

    public ch() {
        this.a = "com.autonavi.aps.amapapi.offline.Off";
        this.b = null;
        this.c = false;
        this.d = false;
        this.e = null;
    }

    private AMapLocationServer a(String str, String str2, JSONObject jSONObject, String str3) {
        try {
            if (d()) {
                Object a;
                try {
                    a = cz.a(this.b, "getPureOfflineLocation", str, str2, jSONObject, str3);
                } catch (Throwable th) {
                    a = null;
                }
                String str4 = (String) a;
                AMapLocationServer aMapLocationServer = new AMapLocationServer(StringUtils.EMPTY_STRING);
                aMapLocationServer.b(new JSONObject(str4));
                return aMapLocationServer;
            }
        } catch (Throwable th2) {
        }
        return null;
    }

    private void a(String str) {
        try {
            if (d()) {
                try {
                    cz.a(this.b, str, new Object[0]);
                } catch (Throwable th) {
                }
                if (this.e != null) {
                }
            } else if (this.b != null) {
                cz.a(this.b, "stopOff", new Object[0]);
                this.d = false;
            }
        } catch (Throwable th2) {
        }
    }

    private boolean d() {
        return cv.f() && this.b != null && cv.w();
    }

    public final AMapLocationServer a(ck ckVar, String str, String str2, AMapLocationClientOption aMapLocationClientOption, String str3, AMapLocationServer aMapLocationServer) {
        if (aMapLocationServer != null && aMapLocationServer.getErrorCode() == 7) {
            return aMapLocationServer;
        }
        if (aMapLocationClientOption == null) {
            aMapLocationClientOption = new AMapLocationClientOption();
        }
        AMapLocationServer a = a(str, str2, cw.a(aMapLocationClientOption), str3);
        if (!de.a(a)) {
            return aMapLocationServer;
        }
        ckVar.a(str2);
        a.e("file");
        a.setLocationType(DetectedActivity.RUNNING);
        a.setLocationDetail(new StringBuilder("\u79bb\u7ebf\u5b9a\u4f4d\uff0c\u5728\u7ebf\u5b9a\u4f4d\u5931\u8d25\u539f\u56e0:").append(aMapLocationServer.getErrorInfo()).toString());
        a(a.toJson(1));
        return a;
    }

    public final void a() {
        try {
            if (d()) {
                cz.a(this.b, "getOffDlHist", new Object[0]);
            } else if (this.b != null) {
                cz.a(this.b, "stopOff", new Object[0]);
                this.d = false;
            }
        } catch (Throwable th) {
        }
    }

    public final void a(Context context) {
        try {
            this.e = context;
            if (cv.w() && this.b == null && !this.c) {
                s a = cw.a("OfflineLocation", "1.0.0");
                this.c = db.a(context, a);
                if (this.c) {
                    try {
                        this.b = au.a(context, a, this.a, null, new Class[]{Context.class}, new Object[]{context});
                        return;
                    } catch (Throwable th) {
                    }
                }
                this.c = true;
            }
        } catch (Throwable th2) {
            cw.a(th2, "OffLineLocManager", "initOffLocation");
        }
    }

    public final void a(AMapLocationClientOption aMapLocationClientOption, String str) {
        try {
            if (d() && !this.d) {
                if (aMapLocationClientOption == null) {
                    aMapLocationClientOption = new AMapLocationClientOption();
                }
                JSONObject a = cw.a(aMapLocationClientOption);
                cz.a(this.b, "startOff", a, str);
                this.d = true;
            }
        } catch (Throwable th) {
            cw.a(th, "OffLineLocManager", "getLocation:isOffLineLoc");
        }
    }

    public final void a(cf cfVar) {
        ArrayList a = cf.a();
        int e = cfVar.e();
        if (!a.isEmpty()) {
            switch (e) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    a("resetCdmaData");
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    ce ceVar = (ce) a.get(0);
                    if (ceVar.f <= 0 || ceVar.e <= 0) {
                        a("resetCdmaData");
                    } else if (d()) {
                        cz.a(this.b, "setCdmaLatLon", Integer.valueOf(ceVar.e), Integer.valueOf(ceVar.f));
                    }
                default:
                    a("resetCdmaData");
            }
        }
    }

    public final void a(JSONObject jSONObject) {
        try {
            if (d()) {
                cz.a(this.b, "setLastLoc", jSONObject);
            }
        } catch (Throwable th) {
        }
    }

    public final void b() {
        a("stopOff");
        a("destroy");
        c();
        this.d = false;
        this.b = null;
    }

    public final void c() {
        try {
            if (d()) {
                cz.a(this.b, "resetPureOfflineCache", new Object[0]);
            }
        } catch (Throwable th) {
        }
    }
}
