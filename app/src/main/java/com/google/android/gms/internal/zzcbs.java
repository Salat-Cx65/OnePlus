package com.google.android.gms.internal;

import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import java.util.concurrent.Callable;

public final class zzcbs {
    public static <T> T zzb(Callable<T> callable) throws Exception {
        ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(ThreadPolicy.LAX);
        T call = callable.call();
        StrictMode.setThreadPolicy(threadPolicy);
        return call;
    }
}
