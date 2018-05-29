package com.loc;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Looper;
import com.loc.be.b;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.json.JSONObject;

// compiled from: LogProcessor.java
public abstract class ad {
    static final List a;
    private s b;
    private int c;
    private bf d;
    private be e;

    // compiled from: LogProcessor.java
    class a implements bf {
        private an b;

        a(an anVar) {
            this.b = anVar;
        }

        public final void a(String str) {
            try {
                this.b.b(str, x.a(ad.this.b()));
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    static {
        a = Collections.synchronizedList(new ArrayList());
    }

    protected ad(int i) {
        this.c = i;
    }

    private static be a(Context context, String str) {
        try {
            File file = new File(x.a(context, str));
            return (file.exists() || file.mkdirs()) ? be.a(file, 20480) : null;
        } catch (Throwable e) {
            w.a(e, "LogProcessor", "initDiskLru");
            return null;
        } catch (Throwable e2) {
            w.a(e2, "LogProcessor", "initDiskLru");
            return null;
        }
    }

    private bf a(an anVar) {
        try {
            if (this.d == null) {
                this.d = new a(anVar);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return this.d;
    }

    private static String a(Throwable th) {
        try {
            return t.a(th);
        } catch (Throwable th2) {
            th2.printStackTrace();
            return null;
        }
    }

    public static List a() {
        return a;
    }

    private static void a(an anVar, String str, String str2, int i) {
        ao b = x.b(i);
        b.a(0);
        b.b(str);
        b.a(str2);
        anVar.a(b);
    }

    private void a(List<? extends ao> list, an anVar) {
        if (list != null && list.size() > 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ao aoVar = (ao) it.next();
                if (c(aoVar.b())) {
                    anVar.a(aoVar.b(), aoVar.getClass());
                } else {
                    aoVar.a((int) RainSurfaceView.RAIN_LEVEL_SHOWER);
                    anVar.b(aoVar);
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(android.content.Context r9, java.lang.String r10, java.lang.String r11, java.lang.String r12, com.loc.an r13) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.ad.a(android.content.Context, java.lang.String, java.lang.String, java.lang.String, com.loc.an):boolean");
        /*
        this = this;
        r2 = 0;
        r0 = 0;
        r1 = 0;
        r3 = 0;
        r4 = 0;
        r5 = com.loc.x.a(r9, r11);	 Catch:{ IOException -> 0x008a, Throwable -> 0x00a8, all -> 0x00c7 }
        r6 = new java.io.File;	 Catch:{ IOException -> 0x008a, Throwable -> 0x00a8, all -> 0x00c7 }
        r6.<init>(r5);	 Catch:{ IOException -> 0x008a, Throwable -> 0x00a8, all -> 0x00c7 }
        r5 = r6.exists();	 Catch:{ IOException -> 0x008a, Throwable -> 0x00a8, all -> 0x00c7 }
        if (r5 != 0) goto L_0x0030;
    L_0x0014:
        r5 = r6.mkdirs();	 Catch:{ IOException -> 0x008a, Throwable -> 0x00a8, all -> 0x00c7 }
        if (r5 != 0) goto L_0x0030;
    L_0x001a:
        if (r2 == 0) goto L_0x001f;
    L_0x001c:
        r1.close();	 Catch:{ Throwable -> 0x0103 }
    L_0x001f:
        if (r2 == 0) goto L_0x0024;
    L_0x0021:
        r4.close();	 Catch:{ Throwable -> 0x0109 }
    L_0x0024:
        if (r2 == 0) goto L_0x002f;
    L_0x0026:
        r1 = r2.c();
        if (r1 != 0) goto L_0x002f;
    L_0x002c:
        r3.close();	 Catch:{ Throwable -> 0x010f }
    L_0x002f:
        return r0;
    L_0x0030:
        r4 = 20480; // 0x5000 float:2.8699E-41 double:1.01185E-319;
        r4 = com.loc.be.a(r6, r4);	 Catch:{ IOException -> 0x008a, Throwable -> 0x00a8, all -> 0x00c7 }
        r3 = r8.a(r13);	 Catch:{ IOException -> 0x0154, Throwable -> 0x0144, all -> 0x0130 }
        r4.a(r3);	 Catch:{ IOException -> 0x0154, Throwable -> 0x0144, all -> 0x0130 }
        r3 = r4.a(r10);	 Catch:{ IOException -> 0x0154, Throwable -> 0x0144, all -> 0x0130 }
        if (r3 == 0) goto L_0x005e;
    L_0x0043:
        if (r2 == 0) goto L_0x0048;
    L_0x0045:
        r1.close();	 Catch:{ Throwable -> 0x0112 }
    L_0x0048:
        if (r3 == 0) goto L_0x004d;
    L_0x004a:
        r3.close();	 Catch:{ Throwable -> 0x0118 }
    L_0x004d:
        if (r4 == 0) goto L_0x002f;
    L_0x004f:
        r1 = r4.c();
        if (r1 != 0) goto L_0x002f;
    L_0x0055:
        r4.close();	 Catch:{ Throwable -> 0x0059 }
        goto L_0x002f;
    L_0x0059:
        r1 = move-exception;
    L_0x005a:
        r1.printStackTrace();
        goto L_0x002f;
    L_0x005e:
        r1 = com.loc.t.a(r12);	 Catch:{ IOException -> 0x0159, Throwable -> 0x0148, all -> 0x0133 }
        r5 = r4.b(r10);	 Catch:{ IOException -> 0x0159, Throwable -> 0x0148, all -> 0x0133 }
        r2 = r5.a();	 Catch:{ IOException -> 0x0159, Throwable -> 0x0148, all -> 0x0133 }
        r2.write(r1);	 Catch:{ IOException -> 0x0160, Throwable -> 0x014e, all -> 0x0138 }
        r5.b();	 Catch:{ IOException -> 0x0160, Throwable -> 0x014e, all -> 0x0138 }
        r4.d();	 Catch:{ IOException -> 0x0160, Throwable -> 0x014e, all -> 0x0138 }
        if (r2 == 0) goto L_0x0078;
    L_0x0075:
        r2.close();	 Catch:{ Throwable -> 0x011e }
    L_0x0078:
        if (r3 == 0) goto L_0x007d;
    L_0x007a:
        r3.close();	 Catch:{ Throwable -> 0x0124 }
    L_0x007d:
        if (r4 == 0) goto L_0x0088;
    L_0x007f:
        r0 = r4.c();
        if (r0 != 0) goto L_0x0088;
    L_0x0085:
        r4.close();	 Catch:{ Throwable -> 0x012a }
    L_0x0088:
        r0 = 1;
        goto L_0x002f;
    L_0x008a:
        r1 = move-exception;
        r3 = r2;
        r4 = r2;
    L_0x008d:
        r1.printStackTrace();	 Catch:{ all -> 0x013d }
        if (r4 == 0) goto L_0x0095;
    L_0x0092:
        r4.close();	 Catch:{ Throwable -> 0x00ef }
    L_0x0095:
        if (r2 == 0) goto L_0x009a;
    L_0x0097:
        r2.close();	 Catch:{ Throwable -> 0x00f4 }
    L_0x009a:
        if (r3 == 0) goto L_0x002f;
    L_0x009c:
        r1 = r3.c();
        if (r1 != 0) goto L_0x002f;
    L_0x00a2:
        r3.close();	 Catch:{ Throwable -> 0x00a6 }
        goto L_0x002f;
    L_0x00a6:
        r1 = move-exception;
        goto L_0x005a;
    L_0x00a8:
        r1 = move-exception;
        r4 = r2;
        r3 = r2;
    L_0x00ab:
        r1.printStackTrace();	 Catch:{ all -> 0x0142 }
        if (r3 == 0) goto L_0x00b3;
    L_0x00b0:
        r3.close();	 Catch:{ Throwable -> 0x00f9 }
    L_0x00b3:
        if (r2 == 0) goto L_0x00b8;
    L_0x00b5:
        r2.close();	 Catch:{ Throwable -> 0x00fe }
    L_0x00b8:
        if (r4 == 0) goto L_0x002f;
    L_0x00ba:
        r1 = r4.c();
        if (r1 != 0) goto L_0x002f;
    L_0x00c0:
        r4.close();	 Catch:{ Throwable -> 0x00c5 }
        goto L_0x002f;
    L_0x00c5:
        r1 = move-exception;
        goto L_0x005a;
    L_0x00c7:
        r0 = move-exception;
        r4 = r2;
        r3 = r2;
    L_0x00ca:
        if (r3 == 0) goto L_0x00cf;
    L_0x00cc:
        r3.close();	 Catch:{ Throwable -> 0x00e0 }
    L_0x00cf:
        if (r2 == 0) goto L_0x00d4;
    L_0x00d1:
        r2.close();	 Catch:{ Throwable -> 0x00e5 }
    L_0x00d4:
        if (r4 == 0) goto L_0x00df;
    L_0x00d6:
        r1 = r4.c();
        if (r1 != 0) goto L_0x00df;
    L_0x00dc:
        r4.close();	 Catch:{ Throwable -> 0x00ea }
    L_0x00df:
        throw r0;
    L_0x00e0:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00cf;
    L_0x00e5:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00d4;
    L_0x00ea:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00df;
    L_0x00ef:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0095;
    L_0x00f4:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x009a;
    L_0x00f9:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00b3;
    L_0x00fe:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00b8;
    L_0x0103:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x001f;
    L_0x0109:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0024;
    L_0x010f:
        r1 = move-exception;
        goto L_0x005a;
    L_0x0112:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0048;
    L_0x0118:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x004d;
    L_0x011e:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0078;
    L_0x0124:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x007d;
    L_0x012a:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0088;
    L_0x0130:
        r0 = move-exception;
        r3 = r2;
        goto L_0x00ca;
    L_0x0133:
        r0 = move-exception;
        r7 = r3;
        r3 = r2;
        r2 = r7;
        goto L_0x00ca;
    L_0x0138:
        r0 = move-exception;
        r7 = r3;
        r3 = r2;
        r2 = r7;
        goto L_0x00ca;
    L_0x013d:
        r0 = move-exception;
        r7 = r3;
        r3 = r4;
        r4 = r7;
        goto L_0x00ca;
    L_0x0142:
        r0 = move-exception;
        goto L_0x00ca;
    L_0x0144:
        r1 = move-exception;
        r3 = r2;
        goto L_0x00ab;
    L_0x0148:
        r1 = move-exception;
        r7 = r3;
        r3 = r2;
        r2 = r7;
        goto L_0x00ab;
    L_0x014e:
        r1 = move-exception;
        r7 = r3;
        r3 = r2;
        r2 = r7;
        goto L_0x00ab;
    L_0x0154:
        r1 = move-exception;
        r3 = r4;
        r4 = r2;
        goto L_0x008d;
    L_0x0159:
        r1 = move-exception;
        r7 = r3;
        r3 = r4;
        r4 = r2;
        r2 = r7;
        goto L_0x008d;
    L_0x0160:
        r1 = move-exception;
        r7 = r3;
        r3 = r4;
        r4 = r2;
        r2 = r7;
        goto L_0x008d;
        */
    }

    public static boolean a(String[] strArr, String str) {
        if (strArr == null || str == null) {
            return false;
        }
        try {
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                String str2 = strArr[i];
                str = str.trim();
                if (str.startsWith("at ") && str.contains(str2 + ".") && str.endsWith(")")) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    private static String b(String str) {
        try {
            return m.c(t.a(str));
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private static boolean b(String[] strArr, String str) {
        if (strArr == null || str == null) {
            return false;
        }
        try {
            String[] split = str.split("\n");
            int length = split.length;
            for (int i = 0; i < length; i++) {
                if (a(strArr, split[i].trim())) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    private boolean c(String str) {
        if (this.e == null) {
            return false;
        }
        try {
            return this.e.c(str);
        } catch (Throwable th) {
            w.a(th, "LogUpdateProcessor", new StringBuilder("deleteLogData-").append(str).toString());
            return false;
        }
    }

    private static int d(String str) {
        int i = 0;
        bn yVar = new y(t.c(t.a(str)));
        try {
            bi.a();
            byte[] a = bi.a(yVar);
            if (a == null) {
                return 0;
            }
            try {
                JSONObject jSONObject = new JSONObject(t.a(a));
                String str2 = "code";
                return jSONObject.has(str2) ? jSONObject.getInt(str2) : 0;
            } catch (Throwable e) {
                w.a(e, "LogProcessor", "processUpdate");
                i = 1;
                return i;
            }
        } catch (Throwable e2) {
            if (e2.e() != 27) {
                i = 1;
            }
            w.a(e2, "LogProcessor", "processUpdate");
            return i;
        }
    }

    private static List<s> d(Context context) {
        List<s> a;
        Throwable th;
        Object obj = null;
        try {
            Looper mainLooper = Looper.getMainLooper();
            synchronized (mainLooper) {
                try {
                    a = new ap(context, false).a();
                    try {
                    } catch (Throwable th2) {
                        th = th2;
                        r1 = a;
                        r0 = th;
                        throw r0;
                    }
                } catch (Throwable th3) {
                    r0 = th3;
                    try {
                        Throwable th4;
                        throw th4;
                    } catch (Throwable th42) {
                        List<s> list;
                        th = th42;
                        a = list;
                        Throwable th22 = th;
                        th22.printStackTrace();
                        return a;
                    }
                }
            }
        } catch (Throwable th422) {
            th = th422;
            a = null;
            th22 = th;
            th22.printStackTrace();
            return a;
        }
        return a;
    }

    private static String e(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("\"key\":\"").append(k.f(context)).append("\",\"platform\":\"android\",\"diu\":\"").append(n.q(context)).append("\",\"pkg\":\"").append(k.c(context)).append("\",\"model\":\"").append(Build.MODEL).append("\",\"appname\":\"").append(k.b(context)).append("\",\"appversion\":\"").append(k.d(context)).append("\",\"sysversion\":\"").append(VERSION.RELEASE).append("\",");
        } catch (Throwable th) {
            w.a(th, "CInfo", "getPublicJSONInfo");
        }
        return stringBuilder.toString();
    }

    private String e(String str) {
        InputStream a;
        String str2 = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            if (this.e == null) {
                if (null != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable e) {
                        w.a(e, "LogProcessor", "readLog1");
                    }
                }
                if (null != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                        Throwable e3 = e2;
                        String str3 = "LogProcessor";
                        String str4 = "readLog2";
                        w.a(e3, str3, str4);
                        return str2;
                    }
                }
                return str2;
            }
            b a2 = this.e.a(str);
            if (a2 == null) {
                if (null != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable e4) {
                        w.a(e4, "LogProcessor", "readLog1");
                    }
                }
                if (null != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e5) {
                        e3 = e5;
                        str3 = "LogProcessor";
                        str4 = "readLog2";
                        w.a(e3, str3, str4);
                        return str2;
                    }
                }
                return str2;
            }
            a = a2.a();
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = a.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    }
                    str2 = t.a(byteArrayOutputStream.toByteArray());
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (Throwable e32) {
                            w.a(e32, "LogProcessor", "readLog1");
                        }
                    }
                } catch (Throwable th) {
                    e32 = th;
                    try {
                        w.a(e32, "LogProcessor", "readLog");
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Throwable e322) {
                                w.a(e322, "LogProcessor", "readLog1");
                            }
                        }
                    } catch (Throwable th2) {
                        Throwable th3 = th2;
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Throwable e3222) {
                                w.a(e3222, "LogProcessor", "readLog1");
                            }
                        }
                        if (a != null) {
                            try {
                                a.close();
                            } catch (Throwable e32222) {
                                w.a(e32222, "LogProcessor", "readLog2");
                            }
                        }
                        throw th3;
                    }
                    if (a != null) {
                        try {
                            a.close();
                        } catch (IOException e6) {
                            e32222 = e6;
                            str3 = "LogProcessor";
                            str4 = "readLog2";
                            w.a(e32222, str3, str4);
                            return str2;
                        }
                    }
                    return str2;
                }
            } catch (Throwable e322222) {
                byteArrayOutputStream = null;
                th3 = e322222;
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (a != null) {
                    a.close();
                }
                throw th3;
            }
            if (a != null) {
                try {
                    a.close();
                } catch (IOException e7) {
                    e322222 = e7;
                    str3 = "LogProcessor";
                    str4 = "readLog2";
                    w.a(e322222, str3, str4);
                    return str2;
                }
            }
            return str2;
        } catch (Throwable e3222222) {
            byteArrayOutputStream = null;
            a = null;
            th3 = e3222222;
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            if (a != null) {
                a.close();
            }
            throw th3;
        }
    }

    private static String f(Context context) {
        try {
            String e = e(context);
            return StringUtils.EMPTY_STRING.equals(e) ? null : m.b(t.a(e));
        } catch (Throwable th) {
            w.a(th, "LogProcessor", "getPublicInfo");
            return null;
        }
    }

    protected String a(String str) {
        return p.c(str);
    }

    protected abstract String a(List<s> list);

    final void a(Context context, Throwable th, String str, String str2) {
        List<s> d = d(context);
        if (d != null && d.size() != 0) {
            String a = a(th);
            if (a != null && !StringUtils.EMPTY_STRING.equals(a)) {
                for (s sVar : d) {
                    if (b(sVar.f(), a)) {
                        a(sVar, context, th, a.replaceAll("\n", "<br/>"), str, str2);
                        return;
                    }
                }
                if (a.contains("com.amap.api.col")) {
                    try {
                        a(t.a(), context, th, a, str, str2);
                    } catch (j e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    protected final void a(s sVar) {
        this.b = sVar;
    }

    final void a(s sVar, Context context, String str, String str2, String str3, String str4) {
        this.b = sVar;
        String a = bt.a();
        String a2 = bt.a(context, sVar);
        k.a(context);
        if (str != null && !StringUtils.EMPTY_STRING.equals(str)) {
            int i = this.c;
            StringBuilder stringBuilder = new StringBuilder();
            if (str3 != null) {
                stringBuilder.append("class:").append(str3);
            }
            if (str4 != null) {
                stringBuilder.append(" method:").append(str4).append("$<br/>");
            }
            stringBuilder.append(str2);
            String a3 = a(str2);
            a = bt.a(a2, a, i, str, stringBuilder.toString());
            if (a != null && !StringUtils.EMPTY_STRING.equals(a)) {
                String b = b(a);
                String c = x.c(this.c);
                synchronized (Looper.getMainLooper()) {
                    an anVar = new an(context);
                    a(context, a3, c, b, anVar);
                    a(anVar, sVar.a(), a3, i);
                }
            }
        }
    }

    final void a(s sVar, Context context, Throwable th, String str, String str2, String str3) {
        a(sVar, context, th.toString(), str, str2, str3);
    }

    protected abstract boolean a(Context context);

    protected final int b() {
        return this.c;
    }

    final void b(Context context) {
        List d = d(context);
        if (d != null && d.size() != 0) {
            String a = a(d);
            if (a != null && !StringUtils.EMPTY_STRING.equals(a)) {
                String a2 = bt.a();
                String a3 = bt.a(context, this.b);
                k.a(context);
                int i = this.c;
                a2 = bt.a(a3, a2, i, "ANR", a);
                if (a2 != null && !StringUtils.EMPTY_STRING.equals(a2)) {
                    a3 = a(a);
                    String b = b(a2);
                    String c = x.c(this.c);
                    synchronized (Looper.getMainLooper()) {
                        an anVar = new an(context);
                        a(context, a3, c, b, anVar);
                        a(anVar, this.b.a(), a3, i);
                    }
                }
            }
        }
    }

    final void c() {
        try {
            if (this.e != null && !this.e.c()) {
                this.e.close();
            }
        } catch (Throwable e) {
            w.a(e, "LogProcessor", "closeDiskLru");
        } catch (Throwable e2) {
            w.a(e2, "LogProcessor", "closeDiskLru");
        }
    }

    final void c(Context context) {
        try {
            if (a(context)) {
                synchronized (Looper.getMainLooper()) {
                    try {
                        this.e = a(context, x.c(this.c));
                    } catch (Throwable th) {
                        w.a(th, "LogProcessor", "LogUpDateProcessor");
                    }
                    an anVar = new an(context);
                    try {
                        a(anVar.a((int) RainSurfaceView.RAIN_LEVEL_SHOWER, x.a(this.c)), anVar);
                    } catch (Throwable th2) {
                        w.a(th2, "LogProcessor", "processDeleteFail");
                    }
                    List<ao> a = anVar.a(0, x.a(this.c));
                    if (a == null || a.size() == 0) {
                        return;
                    }
                    Object obj;
                    String str;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("{\"pinfo\":\"").append(f(context)).append("\",\"els\":[");
                    int i = 1;
                    for (ao aoVar : a) {
                        Object obj2;
                        String e = e(aoVar.b());
                        if (e == null || StringUtils.EMPTY_STRING.equals(e)) {
                            obj2 = obj;
                        } else {
                            e = e + "||" + aoVar.c();
                            if (obj != null) {
                                obj2 = null;
                            } else {
                                stringBuilder.append(",");
                                obj2 = obj;
                            }
                            stringBuilder.append("{\"log\":\"").append(e).append("\"}");
                        }
                        obj = obj2;
                    }
                    if (obj != null) {
                        str = null;
                    } else {
                        stringBuilder.append("]}");
                        str = stringBuilder.toString();
                    }
                    if (str == null) {
                        return;
                    }
                    if (d(str) == 1) {
                        int i2 = this.c;
                        a((List) a, anVar);
                    }
                }
            }
        } catch (Throwable th22) {
            w.a(th22, "LogProcessor", "processUpdateLog");
        }
    }
}
