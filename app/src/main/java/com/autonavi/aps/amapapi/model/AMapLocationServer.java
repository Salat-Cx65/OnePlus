package com.autonavi.aps.amapapi.model;

import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.loc.cw;
import com.loc.de;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.json.JSONObject;

public class AMapLocationServer extends AMapLocation {
    protected String c;
    boolean d;
    private String e;
    private String f;
    private int g;
    private String h;
    private String i;
    private JSONObject j;
    private String k;
    private String l;
    private long m;
    private String n;

    public AMapLocationServer(String str) {
        super(str);
        this.c = StringUtils.EMPTY_STRING;
        this.e = null;
        this.f = StringUtils.EMPTY_STRING;
        this.h = StringUtils.EMPTY_STRING;
        this.i = "new";
        this.j = null;
        this.k = StringUtils.EMPTY_STRING;
        this.d = true;
        this.l = StringUtils.EMPTY_STRING;
        this.m = 0;
        this.n = null;
    }

    public final String a() {
        return this.e;
    }

    public final void a(long j) {
        this.m = j;
    }

    public final void a(String str) {
        this.e = str;
    }

    public final void a(JSONObject jSONObject) {
        this.j = jSONObject;
    }

    public final void a(boolean z) {
        this.d = z;
    }

    public final String b() {
        return this.f;
    }

    public final void b(String str) {
        this.f = str;
    }

    public final void b(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                cw.a((AMapLocation) this, jSONObject);
                this.i = jSONObject.optString("type", this.i);
                this.h = jSONObject.optString("retype", this.h);
                String optString = jSONObject.optString("cens", this.l);
                if (!TextUtils.isEmpty(optString)) {
                    for (Object obj : optString.split("\\*")) {
                        if (!TextUtils.isEmpty(obj)) {
                            String[] split = obj.split(",");
                            setLongitude(Double.parseDouble(split[0]));
                            setLatitude(Double.parseDouble(split[1]));
                            setAccuracy((float) Integer.parseInt(split[2]));
                            break;
                        }
                    }
                    this.l = optString;
                }
                this.c = jSONObject.optString("desc", this.c);
                c(jSONObject.optString("coord", String.valueOf(this.g)));
                this.k = jSONObject.optString("mcell", this.k);
                this.d = jSONObject.optBoolean("isReversegeo", this.d);
                if (de.a(jSONObject, "poiid")) {
                    setBuildingId(jSONObject.optString("poiid"));
                }
                if (de.a(jSONObject, "pid")) {
                    setBuildingId(jSONObject.optString("pid"));
                }
                if (de.a(jSONObject, "floor")) {
                    setFloor(jSONObject.optString("floor"));
                }
                if (de.a(jSONObject, "flr")) {
                    setFloor(jSONObject.optString("flr"));
                }
            } catch (Throwable th) {
                cw.a(th, "AmapLoc", "AmapLoc");
            }
        }
    }

    public final int c() {
        return this.g;
    }

    public final void c(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (getProvider().equals(GeocodeSearch.GPS)) {
                this.g = 0;
                return;
            } else if (str.equals("0")) {
                this.g = 0;
                return;
            } else if (str.equals("1")) {
                this.g = 1;
                return;
            }
        }
        this.g = -1;
    }

    public final String d() {
        return this.h;
    }

    public final void d(String str) {
        this.h = str;
    }

    public final String e() {
        return this.i;
    }

    public final void e(String str) {
        this.i = str;
    }

    public final JSONObject f() {
        return this.j;
    }

    public final void f(String str) {
        this.c = str;
    }

    public final String g() {
        return this.k;
    }

    public final void g(String str) {
        this.n = str;
    }

    public final AMapLocationServer h() {
        Object obj = this.k;
        if (TextUtils.isEmpty(obj)) {
            return null;
        }
        String[] split = obj.split(",");
        if (split.length != 3) {
            return null;
        }
        AMapLocationServer aMapLocationServer = new AMapLocationServer(StringUtils.EMPTY_STRING);
        aMapLocationServer.setProvider(getProvider());
        aMapLocationServer.setLongitude(Double.parseDouble(split[0]));
        aMapLocationServer.setLatitude(Double.parseDouble(split[1]));
        aMapLocationServer.setAccuracy(Float.parseFloat(split[2]));
        aMapLocationServer.setCityCode(getCityCode());
        aMapLocationServer.setAdCode(getAdCode());
        aMapLocationServer.setCountry(getCountry());
        aMapLocationServer.setProvince(getProvince());
        aMapLocationServer.setCity(getCity());
        aMapLocationServer.setTime(getTime());
        aMapLocationServer.i = this.i;
        aMapLocationServer.c(String.valueOf(this.g));
        return de.a(aMapLocationServer) ? aMapLocationServer : null;
    }

    public final boolean i() {
        return this.d;
    }

    public final long j() {
        return this.m;
    }

    public final String k() {
        return this.n;
    }

    public JSONObject toJson(int i) {
        try {
            JSONObject toJson = super.toJson(i);
            switch (i) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    toJson.put("retype", this.h);
                    toJson.put("cens", this.l);
                    toJson.put("coord", this.g);
                    toJson.put("mcell", this.k);
                    toJson.put("desc", this.c);
                    toJson.put("address", getAddress());
                    if (this.j != null && de.a(toJson, "offpct")) {
                        toJson.put("offpct", this.j.getString("offpct"));
                    }
                    toJson.put("type", this.i);
                    toJson.put("isReversegeo", this.d);
                    return toJson;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    toJson.put("type", this.i);
                    toJson.put("isReversegeo", this.d);
                    return toJson;
                default:
                    return toJson;
            }
        } catch (Throwable th) {
            cw.a(th, "AmapLoc", "toStr");
            return null;
        }
    }

    public String toStr(int i) {
        JSONObject toJson;
        try {
            toJson = super.toJson(i);
            toJson.put("nb", this.n);
        } catch (Throwable th) {
            cw.a(th, "AMapLocation", "toStr part2");
            toJson = null;
        }
        return toJson == null ? null : toJson.toString();
    }
}
