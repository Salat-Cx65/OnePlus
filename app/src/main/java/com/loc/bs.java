package com.loc;

import android.content.Context;
import android.os.Build;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

// compiled from: StatisticsManager.java
public final class bs {
    private static boolean a;

    // compiled from: StatisticsManager.java
    static class AnonymousClass_1 implements Runnable {
        final /* synthetic */ Context a;
        final /* synthetic */ br b;

        AnonymousClass_1(Context context, br brVar) {
            this.a = context;
            this.b = brVar;
        }

        public final void run() {
            bt.a(this.a, x.g, this.b.a());
        }
    }

    // compiled from: StatisticsManager.java
    static class AnonymousClass_2 implements Runnable {
        final /* synthetic */ List a;
        final /* synthetic */ Context b;

        AnonymousClass_2(List list, Context context) {
            this.a = list;
            this.b = context;
        }

        public final void run() {
            byte[] toByteArray;
            Throwable th;
            byte[] bArr = new byte[0];
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    for (br brVar : this.a) {
                        byteArrayOutputStream.write(brVar.a());
                    }
                    toByteArray = byteArrayOutputStream.toByteArray();
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (Throwable th2) {
                            th2.printStackTrace();
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        w.a(th, "StatisticsEntity", "applyStaticsBatch");
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Throwable th4) {
                                th4.printStackTrace();
                            }
                        }
                        toByteArray = bArr;
                    } catch (Throwable th5) {
                        th4 = th5;
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Throwable th22) {
                                th22.printStackTrace();
                            }
                        }
                        throw th4;
                    }
                    bt.a(this.b, x.g, toByteArray);
                }
            } catch (Throwable th6) {
                th4 = th6;
                byteArrayOutputStream = null;
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                throw th4;
            }
            bt.a(this.b, x.g, toByteArray);
        }
    }

    static {
        a = true;
    }

    public static void a(Context context) {
        try {
            if (c(context)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date()));
                stringBuffer.append(" ");
                stringBuffer.append(UUID.randomUUID().toString());
                stringBuffer.append(" ");
                if (stringBuffer.length() == 53) {
                    Object a = t.a(stringBuffer.toString());
                    Object b = b(context);
                    Object a2 = bt.a(context, x.g);
                    byte[] bArr = new byte[(b.length + a2.length)];
                    System.arraycopy(b, 0, bArr, 0, b.length);
                    System.arraycopy(a2, 0, bArr, b.length, a2.length);
                    b = a(bArr);
                    byte[] bArr2 = new byte[(a.length + b.length)];
                    System.arraycopy(a, 0, bArr2, 0, a.length);
                    System.arraycopy(b, 0, bArr2, a.length, b.length);
                    bn yVar = new y(t.c(bArr2), "2");
                    bi.a();
                    bi.a(yVar);
                }
            }
        } catch (Throwable th) {
            w.a(th, "StatisticsManager", "updateStaticsData");
        }
    }

    public static synchronized void a(br brVar, Context context) {
        synchronized (bs.class) {
            z.b().submit(new AnonymousClass_1(context, brVar));
        }
    }

    public static synchronized void a(List<br> list, Context context) {
        synchronized (bs.class) {
            z.b().submit(new AnonymousClass_2(list, context));
        }
    }

    private static byte[] a(byte[] bArr) {
        try {
            return m.a(bArr);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private static byte[] b(Context context) {
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[0];
        try {
            t.a(byteArrayOutputStream, "1.2.13.6");
            t.a(byteArrayOutputStream, "Android");
            t.a(byteArrayOutputStream, n.q(context));
            t.a(byteArrayOutputStream, n.i(context));
            t.a(byteArrayOutputStream, n.f(context));
            t.a(byteArrayOutputStream, Build.MANUFACTURER);
            t.a(byteArrayOutputStream, Build.MODEL);
            t.a(byteArrayOutputStream, Build.DEVICE);
            t.a(byteArrayOutputStream, n.r(context));
            t.a(byteArrayOutputStream, k.c(context));
            t.a(byteArrayOutputStream, k.d(context));
            t.a(byteArrayOutputStream, k.f(context));
            byteArrayOutputStream.write(new byte[]{(byte) 0});
            bArr = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th = th2;
                th.printStackTrace();
                return bArr;
            }
        } catch (Throwable th3) {
            try {
                w.a(th3, "StatisticsManager", "getHeader");
            } catch (Throwable th4) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th32) {
                    th32.printStackTrace();
                }
            }
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th5) {
                th32 = th5;
                th32.printStackTrace();
                return bArr;
            }
        }
        return bArr;
    }

    private static boolean c(Context context) {
        try {
            if (n.m(context) != 1 || !a || bt.b(context, x.g) < 30) {
                return false;
            }
            long c = bt.c(context, "c.log");
            long time = new Date().getTime();
            if (time - c < 3600000) {
                return false;
            }
            bt.a(context, time, "c.log");
            a = false;
            return true;
        } catch (Throwable th) {
            w.a(th, "StatisticsManager", "isUpdate");
            return false;
        }
    }
}
