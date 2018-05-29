package com.loc;

import android.content.Context;
import java.util.Map;
import net.oneplus.weather.util.StringUtils;

// compiled from: LocationRequest.java
public final class cs extends bj {
    Map<String, String> f;
    String g;
    byte[] h;
    byte[] i;
    boolean j;
    String k;
    Map<String, String> l;
    boolean m;

    public cs(Context context, s sVar) {
        super(context, sVar);
        this.f = null;
        this.g = StringUtils.EMPTY_STRING;
        this.h = null;
        this.i = null;
        this.j = false;
        this.k = null;
        this.l = null;
        this.m = false;
    }

    public final byte[] a_() {
        return this.h;
    }

    public final Map<String, String> b() {
        return this.f;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(byte[] r4) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cs.b(byte[]):void");
        /*
        this = this;
        r2 = 0;
        r1 = new java.io.ByteArrayOutputStream;	 Catch:{ Throwable -> 0x0021, all -> 0x0031 }
        r1.<init>();	 Catch:{ Throwable -> 0x0021, all -> 0x0031 }
        if (r4 == 0) goto L_0x0012;
    L_0x0008:
        r0 = a(r4);	 Catch:{ Throwable -> 0x0040 }
        r1.write(r0);	 Catch:{ IOException -> 0x0042 }
        r1.write(r4);	 Catch:{ IOException -> 0x0042 }
    L_0x0012:
        r0 = r1.toByteArray();	 Catch:{ Throwable -> 0x0040 }
        r3.i = r0;	 Catch:{ Throwable -> 0x0040 }
        r1.close();	 Catch:{ IOException -> 0x001c }
    L_0x001b:
        return;
    L_0x001c:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x001b;
    L_0x0021:
        r0 = move-exception;
        r1 = r2;
    L_0x0023:
        r0.printStackTrace();	 Catch:{ all -> 0x003e }
        if (r1 == 0) goto L_0x001b;
    L_0x0028:
        r1.close();	 Catch:{ IOException -> 0x002c }
        goto L_0x001b;
    L_0x002c:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x001b;
    L_0x0031:
        r0 = move-exception;
        r1 = r2;
    L_0x0033:
        if (r1 == 0) goto L_0x0038;
    L_0x0035:
        r1.close();	 Catch:{ IOException -> 0x0039 }
    L_0x0038:
        throw r0;
    L_0x0039:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0038;
    L_0x003e:
        r0 = move-exception;
        goto L_0x0033;
    L_0x0040:
        r0 = move-exception;
        goto L_0x0023;
    L_0x0042:
        r0 = move-exception;
        goto L_0x0012;
        */
    }

    public final Map<String, String> b_() {
        return this.l;
    }

    public final String c() {
        return this.g;
    }

    public final byte[] e() {
        return this.i;
    }

    public final boolean g() {
        return this.j;
    }

    public final String h() {
        return this.k;
    }

    protected final boolean i() {
        return this.m;
    }
}
