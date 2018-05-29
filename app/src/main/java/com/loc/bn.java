package com.loc;

import java.net.Proxy;
import java.util.Map;

// compiled from: Request.java
public abstract class bn {
    int c;
    int d;
    Proxy e;

    public bn() {
        this.c = 20000;
        this.d = 20000;
        this.e = null;
    }

    public final void a(int i) {
        this.c = i;
    }

    public final void a(Proxy proxy) {
        this.e = proxy;
    }

    public abstract Map<String, String> b();

    public final void b(int i) {
        this.d = i;
    }

    public abstract Map<String, String> b_();

    public abstract String c();

    public byte[] d() {
        return null;
    }
}
