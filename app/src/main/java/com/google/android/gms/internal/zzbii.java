package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbr;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzbii implements ThreadFactory {
    private final int mPriority;
    private final String zzaKi;
    private final AtomicInteger zzaKj;
    private final ThreadFactory zzaKk;

    public zzbii(String str) {
        this(str, 0);
    }

    private zzbii(String str, int i) {
        this.zzaKj = new AtomicInteger();
        this.zzaKk = Executors.defaultThreadFactory();
        this.zzaKi = (String) zzbr.zzb((Object) str, (Object) "Name must not be null");
        this.mPriority = 0;
    }

    public final Thread newThread(Runnable runnable) {
        Thread newThread = this.zzaKk.newThread(new zzbij(runnable, 0));
        String str = this.zzaKi;
        newThread.setName(new StringBuilder(String.valueOf(str).length() + 13).append(str).append("[").append(this.zzaKj.getAndIncrement()).append("]").toString());
        return newThread;
    }
}
