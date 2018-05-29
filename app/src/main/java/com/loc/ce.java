package com.loc;

import android.support.v4.os.EnvironmentCompat;
import java.util.Locale;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

// compiled from: Cgi.java
public final class ce {
    private static ce p;
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public int k;
    public short l;
    public long m;
    public boolean n;
    public boolean o;

    static {
        p = null;
    }

    public ce(int i, boolean z) {
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = -113;
        this.k = 0;
        this.l = (short) 0;
        this.m = 0;
        this.n = false;
        this.o = true;
        this.k = i;
        this.n = z;
    }

    public final int a() {
        return this.c;
    }

    public final boolean a(ce ceVar) {
        if (ceVar == null) {
            return false;
        }
        switch (ceVar.k) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return this.k == 1 && ceVar.c == this.c && ceVar.d == this.d && ceVar.b == this.b;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return this.k == 2 && ceVar.i == this.i && ceVar.h == this.h && ceVar.g == this.g;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return this.k == 3 && ceVar.c == this.c && ceVar.d == this.d && ceVar.b == this.b;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return this.k == 4 && ceVar.c == this.c && ceVar.d == this.d && ceVar.b == this.b;
            default:
                return false;
        }
    }

    public final int b() {
        return this.d;
    }

    public final int c() {
        return this.h;
    }

    public final int d() {
        return this.i;
    }

    public final int e() {
        return this.j;
    }

    public final String toString() {
        String str = EnvironmentCompat.MEDIA_UNKNOWN;
        switch (this.k) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return String.format(Locale.CHINA, "GSM lac=%d, cid=%d, mnc=%s, valid=%b, sig=%d, age=%d, reg=%b", new Object[]{Integer.valueOf(this.c), Integer.valueOf(this.d), Integer.valueOf(this.b), Boolean.valueOf(this.o), Integer.valueOf(this.j), Short.valueOf(this.l), Boolean.valueOf(this.n)});
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return String.format(Locale.CHINA, "CDMA bid=%d, nid=%d, sid=%d, valid=%b, sig=%d, age=%d, reg=%b", new Object[]{Integer.valueOf(this.i), Integer.valueOf(this.h), Integer.valueOf(this.g), Boolean.valueOf(this.o), Integer.valueOf(this.j), Short.valueOf(this.l), Boolean.valueOf(this.n)});
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return String.format(Locale.CHINA, "LTE lac=%d, cid=%d, mnc=%s, valid=%b, sig=%d, age=%d, reg=%b", new Object[]{Integer.valueOf(this.c), Integer.valueOf(this.d), Integer.valueOf(this.b), Boolean.valueOf(this.o), Integer.valueOf(this.j), Short.valueOf(this.l), Boolean.valueOf(this.n)});
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return String.format(Locale.CHINA, "WCDMA lac=%d, cid=%d, mnc=%s, valid=%b, sig=%d, age=%d, reg=%b", new Object[]{Integer.valueOf(this.c), Integer.valueOf(this.d), Integer.valueOf(this.b), Boolean.valueOf(this.o), Integer.valueOf(this.j), Short.valueOf(this.l), Boolean.valueOf(this.n)});
            default:
                return str;
        }
    }
}
