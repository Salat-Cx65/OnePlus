package com.loc;

import android.content.Context;
import android.support.v7.widget.ListPopupWindow;

// compiled from: RollBackDynamic.java
public final class dc {
    static boolean a;
    static boolean b;
    static boolean c;
    static boolean d;
    static int e;
    static int f;
    static boolean g;
    static boolean h;

    static {
        a = false;
        b = false;
        c = false;
        d = false;
        e = 0;
        f = 0;
        g = true;
        h = false;
    }

    public static void a(Context context) {
        try {
            if (e(context) && !a) {
                dd.a(context, "loc", "startMark", dd.b(context, "loc", "startMark", 0) + 1);
                a = true;
            }
        } catch (Throwable th) {
            cw.a(th, "RollBackDynamic", "AddStartMark");
        }
    }

    private static void a(Context context, int i) {
        try {
            if (e(context)) {
                dd.a(context, "loc", "endMark", i);
                dd.a(context, "loc", "startMark", i);
            }
        } catch (Throwable th) {
            cw.a(th, "RollBackDynamic", "resetMark");
        }
    }

    public static void a(Context context, s sVar) {
        if (!d) {
            c = au.a(context, sVar);
            d = true;
            if (!c && cw.c()) {
                au.a(context, "loc");
                db.a("dexrollbackstatistics", "RollBack because of version error");
            }
        }
    }

    public static void a(Context context, String str, String str2) {
        try {
            au.a(context, str);
            db.a("dexrollbackstatistics", new StringBuilder("RollBack because of ").append(str2).toString());
        } catch (Throwable th) {
            cw.a(th, "RollBackDynamic", "rollBackDynamicFile");
        }
    }

    public static void b(Context context) {
        try {
            if (e(context) && !b) {
                dd.a(context, "loc", "endMark", dd.b(context, "loc", "endMark", 0) + 1);
                b = true;
            }
        } catch (Throwable th) {
            cw.a(th, "RollBackDynamic", "AddEndMark");
        }
    }

    public static boolean c(Context context) {
        try {
            if (!e(context)) {
                return false;
            }
            if (h) {
                return g;
            }
            if (e == 0) {
                e = dd.b(context, "loc", "startMark", 0);
            }
            if (f == 0) {
                f = dd.b(context, "loc", "endMark", 0);
            }
            if (!(a || b)) {
                if (e < f) {
                    a(context, 0);
                    g = true;
                }
                if (e - f > 0 && e > 99) {
                    a(context, 0);
                    g = true;
                }
                if (e - f > 0 && e < 99) {
                    a(context, (int) ListPopupWindow.WRAP_CONTENT);
                    g = false;
                }
                if (e - f > 0 && f < 0) {
                    a(context, "loc", "checkMark");
                    g = false;
                }
            }
            dd.a(context, "loc", "isload", g);
            h = true;
            return g;
        } catch (Throwable th) {
            cw.a(th, "RollBackDynamic", "checkMark");
        }
    }

    public static boolean d(Context context) {
        try {
            return !e(context) ? false : dd.b(context, "loc", "isload", false);
        } catch (Throwable th) {
            cw.a(th, "RollBackDynamic", "isLoad");
            return true;
        }
    }

    private static boolean e(Context context) {
        if (!d) {
            a(context, cw.b());
        }
        return c;
    }
}
