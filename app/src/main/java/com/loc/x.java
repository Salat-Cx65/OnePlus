package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

// compiled from: Log.java
public final class x {
    public static final String a;
    static final String b;
    static final String c;
    static final String d;
    public static final String e;
    public static final String f;
    public static final String g;
    public static final String h;

    // compiled from: Log.java
    static class AnonymousClass_1 implements Runnable {
        final /* synthetic */ Context a;
        final /* synthetic */ String b;
        final /* synthetic */ s c;
        final /* synthetic */ String d;

        AnonymousClass_1(Context context, String str, s sVar, String str2) {
            this.a = context;
            this.b = str;
            this.c = sVar;
            this.d = str2;
        }

        public final void run() {
            try {
                Context context = this.a;
                ad d = x.d(1);
                if (TextUtils.isEmpty(this.b)) {
                    d.a(this.c, this.a, new Throwable("gpsstatistics"), this.d, null, null);
                } else {
                    d.a(this.c, this.a, this.b, this.d, null, null);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    // compiled from: Log.java
    static class AnonymousClass_2 implements Runnable {
        final /* synthetic */ Context a;
        final /* synthetic */ int b;
        final /* synthetic */ Throwable c;
        final /* synthetic */ String d;
        final /* synthetic */ String e;

        AnonymousClass_2(Context context, int i, Throwable th, String str, String str2) {
            this.a = context;
            this.b = i;
            this.c = th;
            this.d = str;
            this.e = str2;
        }

        public final void run() {
            try {
                Context context = this.a;
                ad d = x.d(this.b);
                if (d != null) {
                    d.a(this.a, this.c, this.d, this.e);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    // compiled from: Log.java
    static class AnonymousClass_3 implements Runnable {
        final /* synthetic */ Context a;

        AnonymousClass_3(Context context) {
            this.a = context;
        }

        public final void run() {
            ad d;
            Throwable th;
            ad adVar;
            Throwable th2;
            ad adVar2 = null;
            try {
                Context context = this.a;
                ad d2 = x.d(0);
                try {
                    context = this.a;
                    d = x.d(1);
                    try {
                        Context context2 = this.a;
                        adVar2 = x.d(RainSurfaceView.RAIN_LEVEL_SHOWER);
                        try {
                            d2.c(this.a);
                            d.c(this.a);
                            adVar2.c(this.a);
                            bs.a(this.a);
                            bq.a(this.a);
                            List a = ad.a();
                            if (d2 != null) {
                                d2.c();
                            }
                            if (d != null) {
                                d.c();
                            }
                        } catch (RejectedExecutionException e) {
                        } catch (Throwable th3) {
                            th = th3;
                            adVar = d2;
                            d2 = d;
                            d = adVar2;
                            th2 = th;
                        }
                        if (a.size() > 0) {
                            Iterator it = a.iterator();
                            while (it.hasNext()) {
                                it.next();
                                try {
                                    Context context3 = this.a;
                                } catch (RejectedExecutionException e2) {
                                } catch (Throwable th32) {
                                    th = th32;
                                    adVar = d2;
                                    d2 = d;
                                    d = adVar2;
                                    th2 = th;
                                }
                            }
                        }
                    } catch (RejectedExecutionException e22) {
                    } catch (Throwable th322) {
                        th = th322;
                        adVar = d2;
                        d2 = d;
                        d = null;
                        th2 = th;
                        if (adVar != null) {
                            adVar.c();
                        }
                        if (d2 != null) {
                            d2.c();
                        }
                        if (d != null) {
                            d.c();
                        }
                        throw th2;
                    }
                } catch (RejectedExecutionException e3) {
                    d = null;
                    if (d2 != null) {
                        d2.c();
                    }
                    if (d != null) {
                        d.c();
                    }
                    if (adVar2 == null) {
                        return;
                    }
                    adVar2.c();
                } catch (Throwable th4) {
                    adVar = d2;
                    d2 = null;
                    th2 = th4;
                    d = null;
                    if (adVar != null) {
                        adVar.c();
                    }
                    if (d2 != null) {
                        d2.c();
                    }
                    if (d != null) {
                        d.c();
                    }
                    throw th2;
                }
                if (adVar2 != null) {
                    adVar2.c();
                }
            } catch (RejectedExecutionException e4) {
                d = null;
                d2 = null;
                if (d2 != null) {
                    d2.c();
                }
                if (d != null) {
                    d.c();
                }
                if (adVar2 == null) {
                    return;
                }
                adVar2.c();
            } catch (Throwable th42) {
                d2 = null;
                adVar = null;
                th = th42;
                d = null;
                th2 = th;
                if (adVar != null) {
                    adVar.c();
                }
                if (d2 != null) {
                    d2.c();
                }
                if (d != null) {
                    d.c();
                }
                throw th2;
            }
        }
    }

    static {
        a = "/a/";
        b = "b";
        c = "c";
        d = "d";
        g = "e";
        h = "f";
        e = "g";
        f = "h";
    }

    public static Class<? extends ao> a(int i) {
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return aj.class;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return al.class;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return ai.class;
            default:
                return null;
        }
    }

    public static String a(Context context, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getFilesDir().getAbsolutePath());
        stringBuilder.append(a);
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    static void a(Context context) {
        try {
            ad d = d(RainSurfaceView.RAIN_LEVEL_SHOWER);
            if (d != null) {
                d.b(context);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    static void a(Context context, s sVar, String str, String str2) {
        try {
            if (sVar.e()) {
                ExecutorService b = z.b();
                if (b != null && !b.isShutdown()) {
                    b.submit(new AnonymousClass_1(context, str2, sVar, str));
                }
            }
        } catch (RejectedExecutionException e) {
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    static void a(Context context, Throwable th, int i, String str, String str2) {
        try {
            ExecutorService b = z.b();
            if (b != null && !b.isShutdown()) {
                b.submit(new AnonymousClass_2(context, i, th, str, str2));
            }
        } catch (RejectedExecutionException e) {
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    public static ao b(int i) {
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return new aj();
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return new al();
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return new ai();
            default:
                return null;
        }
    }

    static void b(Context context) {
        try {
            ExecutorService b = z.b();
            if (b != null && !b.isShutdown()) {
                b.submit(new AnonymousClass_3(context));
            }
        } catch (Throwable th) {
            w.a(th, "Log", "processLog");
        }
    }

    public static String c(int i) {
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return c;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return b;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return d;
            default:
                return StringUtils.EMPTY_STRING;
        }
    }

    static ad d(int i) {
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return new ab(i);
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return new ac(i);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return new aa(i);
            default:
                return null;
        }
    }
}
