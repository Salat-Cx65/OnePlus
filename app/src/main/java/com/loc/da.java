package com.loc;

import com.autonavi.aps.amapapi.model.AMapLocationServer;

// compiled from: ReportEntity.java
public final class da {
    private long a;
    private long b;
    private AMapLocationServer c;

    public da() {
        this.a = 0;
        this.b = 0;
        this.c = null;
    }

    public final long a() {
        return this.a;
    }

    public final void a(long j) {
        this.a = j;
    }

    public final void a(AMapLocationServer aMapLocationServer) {
        this.c = aMapLocationServer;
    }

    public final long b() {
        return this.b;
    }

    public final void b(long j) {
        this.b = j;
    }

    public final AMapLocationServer c() {
        return this.c;
    }
}
