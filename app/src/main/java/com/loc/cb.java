package com.loc;

import java.util.Locale;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

// compiled from: Beacon.java
public final class cb implements Comparable<cb> {
    public String a;
    public String b;
    public byte[] c;
    public String d;
    public String e;
    public int f;
    public int g;
    public String h;
    public long i;
    public int j;

    public cb(String str, String str2, byte[] bArr, String str3, int i, int i2, int i3, int i4, long j) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = 0;
        this.g = 0;
        this.h = null;
        this.i = 0;
        this.j = 0;
        this.a = str;
        this.b = str2;
        this.c = bArr;
        this.d = Integer.toHexString(i).trim().toUpperCase(Locale.CHINA);
        if (this.d.length() < 4) {
            this.d += "00000";
            this.d = this.d.substring(0, RainSurfaceView.RAIN_LEVEL_RAINSTORM);
        }
        this.e = Integer.toHexString(i2).trim().toUpperCase(Locale.CHINA);
        if (this.e.length() < 4) {
            this.e += "00000";
            this.e = this.e.substring(0, RainSurfaceView.RAIN_LEVEL_RAINSTORM);
        }
        this.f = i3;
        this.g = i4;
        this.i = j;
        this.h = str3;
    }

    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        cb cbVar = (cb) obj;
        return this.g < cbVar.g ? 1 : (this.g == cbVar.g || this.g <= cbVar.g) ? 0 : -1;
    }

    public final String toString() {
        return new StringBuilder("name = ").append(this.b).append(",uuid = ").append(this.a).append(",major = ").append(this.d).append(",minor = ").append(this.e).append(",TxPower = ").append(this.f).append(",rssi = ").append(this.g).append(",time = ").append(this.i).toString();
    }
}
