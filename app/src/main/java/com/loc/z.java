package com.loc;

import android.content.Context;
import android.os.Looper;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import net.oneplus.weather.util.StringUtils;

// compiled from: SDKLogHandler.java
public final class z extends w implements UncaughtExceptionHandler {
    private static ExecutorService e;
    private static Set<Integer> f;
    private static final ThreadFactory g;
    private Context d;

    // compiled from: SDKLogHandler.java
    class AnonymousClass_1 implements Runnable {
        final /* synthetic */ Context a;
        final /* synthetic */ s b;
        final /* synthetic */ boolean c;

        AnonymousClass_1(Context context, s sVar, boolean z) {
            this.a = context;
            this.b = sVar;
            this.c = z;
        }

        public final void run() {
            try {
                synchronized (Looper.getMainLooper()) {
                    new ap(this.a, true).a(this.b);
                }
                if (this.c) {
                    synchronized (Looper.getMainLooper()) {
                        aq aqVar = new aq(this.a);
                        ar arVar = new ar();
                        arVar.c(true);
                        arVar.a(true);
                        arVar.b(true);
                        aqVar.a(arVar);
                    }
                    x.a(z.this);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    // compiled from: SDKLogHandler.java
    private static class a implements bm {
        private Context a;

        a(Context context) {
            this.a = context;
        }

        public final void a() {
            try {
                x.b(this.a);
            } catch (Throwable th) {
                w.a(th, "LogNetListener", "onNetCompleted");
            }
        }
    }

    static {
        f = Collections.synchronizedSet(new HashSet());
        g = new ThreadFactory() {
            private final AtomicInteger a;

            {
                this.a = new AtomicInteger(1);
            }

            public final Thread newThread(Runnable runnable) {
                return new Thread(runnable, new StringBuilder("pama#").append(this.a.getAndIncrement()).toString());
            }
        };
    }

    private z(Context context) {
        this.d = context;
        bl.a(new a(context));
        try {
            this.b = Thread.getDefaultUncaughtExceptionHandler();
            if (this.b == null) {
                Thread.setDefaultUncaughtExceptionHandler(this);
                this.c = true;
                return;
            }
            String toString = this.b.toString();
            if (toString.indexOf("com.amap.api") == -1 && toString.indexOf("com.loc") == -1) {
                Thread.setDefaultUncaughtExceptionHandler(this);
                this.c = true;
                return;
            }
            this.c = false;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static synchronized z a(Context context, s sVar) throws j {
        z zVar;
        synchronized (z.class) {
            if (sVar == null) {
                throw new j("sdk info is null");
            } else if (sVar.a() == null || StringUtils.EMPTY_STRING.equals(sVar.a())) {
                throw new j("sdk name is invalid");
            } else {
                try {
                    if (f.add(Integer.valueOf(sVar.hashCode()))) {
                        if (w.a == null) {
                            w.a = new z(context);
                        } else {
                            w.a.c = false;
                        }
                        w.a.a(context, sVar, w.a.c);
                        zVar = (z) w.a;
                    } else {
                        zVar = (z) w.a;
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }
        return zVar;
    }

    public static synchronized void a() {
        synchronized (z.class) {
            try {
                if (e != null) {
                    e.shutdown();
                }
                be.a();
            } catch (Throwable th) {
                th.printStackTrace();
            }
            try {
                if (!(w.a == null || Thread.getDefaultUncaughtExceptionHandler() != w.a || w.a.b == null)) {
                    Thread.setDefaultUncaughtExceptionHandler(w.a.b);
                }
                w.a = null;
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    public static void a(s sVar, String str, j jVar) {
        if (jVar != null) {
            a(sVar, str, jVar.c(), jVar.d(), jVar.b());
        }
    }

    public static void a(s sVar, String str, String str2, String str3, String str4) {
        if (w.a != null) {
            StringBuilder stringBuilder = new StringBuilder("path:");
            stringBuilder.append(str).append(",type:").append(str2).append(",gsid:").append(str3).append(",code:").append(str4);
            w.a.a(sVar, stringBuilder.toString(), "networkError");
        }
    }

    public static synchronized ExecutorService b() {
        ExecutorService executorService;
        synchronized (z.class) {
            try {
                if (e == null || e.isShutdown()) {
                    e = Executors.newSingleThreadExecutor(g);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            executorService = e;
        }
        return executorService;
    }

    public static void b(s sVar, String str, String str2) {
        if (w.a != null) {
            w.a.a(sVar, str, str2);
        }
    }

    public static void b(Throwable th, String str, String str2) {
        if (w.a != null) {
            w.a.a(th, 1, str, str2);
        }
    }

    protected final void a(Context context, s sVar, boolean z) {
        try {
            ExecutorService b = b();
            if (b != null && !b.isShutdown()) {
                b.submit(new AnonymousClass_1(context, sVar, z));
            }
        } catch (RejectedExecutionException e) {
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    protected final void a(s sVar, String str, String str2) {
        x.a(this.d, sVar, str, str2);
    }

    protected final void a(Throwable th, int i, String str, String str2) {
        x.a(this.d, th, i, str, str2);
    }

    public final void uncaughtException(Thread thread, Throwable th) {
        if (th != null) {
            a(th, 0, null, null);
            if (this.b != null) {
                try {
                    Thread.setDefaultUncaughtExceptionHandler(this.b);
                } catch (Throwable th2) {
                }
                this.b.uncaughtException(thread, th);
            }
        }
    }
}
