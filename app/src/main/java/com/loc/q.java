package com.loc;

import android.content.Context;
import android.net.Proxy;
import android.os.Build.VERSION;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.URI;
import java.util.List;

// compiled from: ProxyUtil.java
public final class q {
    private static String a() {
        String defaultHost;
        try {
            defaultHost = Proxy.getDefaultHost();
        } catch (Throwable th) {
            w.a(th, "ProxyUtil", "getDefHost");
            defaultHost = null;
        }
        return defaultHost == null ? "null" : defaultHost;
    }

    public static java.net.Proxy a(Context context) {
        try {
            return VERSION.SDK_INT >= 11 ? a(context, new URI("http://restapi.amap.com")) : b(context);
        } catch (Throwable th) {
            w.a(th, "ProxyUtil", "getProxy");
            return null;
        }
    }

    private static java.net.Proxy a(Context context, URI uri) {
        if (c(context)) {
            try {
                List select = ProxySelector.getDefault().select(uri);
                if (select == null || select.isEmpty()) {
                    return null;
                }
                java.net.Proxy proxy = (java.net.Proxy) select.get(0);
                return (proxy == null || proxy.type() == Type.DIRECT) ? null : proxy;
            } catch (Throwable th) {
                w.a(th, "ProxyUtil", "getProxySelectorCfg");
            }
        }
        return null;
    }

    private static int b() {
        try {
            return Proxy.getDefaultPort();
        } catch (Throwable th) {
            w.a(th, "ProxyUtil", "getDefPort");
            return -1;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.net.Proxy b(android.content.Context r12) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.q.b(android.content.Context):java.net.Proxy");
        /*
        r6 = 80;
        r10 = 0;
        r9 = 1;
        r7 = 0;
        r8 = -1;
        r0 = c(r12);
        if (r0 == 0) goto L_0x017b;
    L_0x000c:
        r0 = "content://telephony/carriers/preferapn";
        r1 = android.net.Uri.parse(r0);
        r0 = r12.getContentResolver();
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r2 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ SecurityException -> 0x00bc, Throwable -> 0x0137, all -> 0x0159 }
        if (r2 == 0) goto L_0x01ca;
    L_0x0020:
        r0 = r2.moveToFirst();	 Catch:{ SecurityException -> 0x0197, Throwable -> 0x0183 }
        if (r0 == 0) goto L_0x01ca;
    L_0x0026:
        r0 = "apn";
        r0 = r2.getColumnIndex(r0);	 Catch:{ SecurityException -> 0x0197, Throwable -> 0x0183 }
        r0 = r2.getString(r0);	 Catch:{ SecurityException -> 0x0197, Throwable -> 0x0183 }
        if (r0 == 0) goto L_0x0038;
    L_0x0032:
        r1 = java.util.Locale.US;	 Catch:{ SecurityException -> 0x0197, Throwable -> 0x0183 }
        r0 = r0.toLowerCase(r1);	 Catch:{ SecurityException -> 0x0197, Throwable -> 0x0183 }
    L_0x0038:
        if (r0 == 0) goto L_0x0083;
    L_0x003a:
        r1 = "ctwap";
        r1 = r0.contains(r1);	 Catch:{ SecurityException -> 0x0197, Throwable -> 0x0183 }
        if (r1 == 0) goto L_0x0083;
    L_0x0042:
        r3 = a();	 Catch:{ SecurityException -> 0x0197, Throwable -> 0x0183 }
        r0 = b();	 Catch:{ SecurityException -> 0x0197, Throwable -> 0x0183 }
        r1 = android.text.TextUtils.isEmpty(r3);	 Catch:{ SecurityException -> 0x019d, Throwable -> 0x0187 }
        if (r1 != 0) goto L_0x01ce;
    L_0x0050:
        r1 = "null";
        r1 = r3.equals(r1);	 Catch:{ SecurityException -> 0x019d, Throwable -> 0x0187 }
        if (r1 != 0) goto L_0x01ce;
    L_0x0058:
        r1 = r9;
    L_0x0059:
        if (r1 != 0) goto L_0x0061;
    L_0x005b:
        r1 = "QMTAuMC4wLjIwMA==";
        r3 = com.loc.t.c(r1);	 Catch:{ SecurityException -> 0x01a5, Throwable -> 0x018d }
    L_0x0061:
        if (r0 != r8) goto L_0x0064;
    L_0x0063:
        r0 = r6;
    L_0x0064:
        r1 = r0;
    L_0x0065:
        if (r2 == 0) goto L_0x006a;
    L_0x0067:
        r2.close();	 Catch:{ Throwable -> 0x00b0 }
    L_0x006a:
        if (r3 == 0) goto L_0x016d;
    L_0x006c:
        r0 = r3.length();	 Catch:{ Throwable -> 0x0170 }
        if (r0 <= 0) goto L_0x016d;
    L_0x0072:
        if (r1 == r8) goto L_0x016d;
    L_0x0074:
        r0 = r9;
    L_0x0075:
        if (r0 == 0) goto L_0x017b;
    L_0x0077:
        r0 = new java.net.Proxy;	 Catch:{ Throwable -> 0x0170 }
        r2 = java.net.Proxy.Type.HTTP;	 Catch:{ Throwable -> 0x0170 }
        r1 = java.net.InetSocketAddress.createUnresolved(r3, r1);	 Catch:{ Throwable -> 0x0170 }
        r0.<init>(r2, r1);	 Catch:{ Throwable -> 0x0170 }
    L_0x0082:
        return r0;
    L_0x0083:
        if (r0 == 0) goto L_0x01ca;
    L_0x0085:
        r1 = "wap";
        r0 = r0.contains(r1);	 Catch:{ SecurityException -> 0x0197, Throwable -> 0x0183 }
        if (r0 == 0) goto L_0x01ca;
    L_0x008d:
        r3 = a();	 Catch:{ SecurityException -> 0x0197, Throwable -> 0x0183 }
        r1 = b();	 Catch:{ SecurityException -> 0x0197, Throwable -> 0x0183 }
        r0 = android.text.TextUtils.isEmpty(r3);	 Catch:{ SecurityException -> 0x01ac, Throwable -> 0x0192 }
        if (r0 != 0) goto L_0x01c6;
    L_0x009b:
        r0 = "null";
        r0 = r3.equals(r0);	 Catch:{ SecurityException -> 0x01ac, Throwable -> 0x0192 }
        if (r0 != 0) goto L_0x01c6;
    L_0x00a3:
        r0 = r9;
    L_0x00a4:
        if (r0 != 0) goto L_0x00ac;
    L_0x00a6:
        r0 = "QMTAuMC4wLjE3Mg==";
        r3 = com.loc.t.c(r0);	 Catch:{ SecurityException -> 0x01b3, Throwable -> 0x0195 }
    L_0x00ac:
        if (r1 != r8) goto L_0x0065;
    L_0x00ae:
        r1 = r6;
        goto L_0x0065;
    L_0x00b0:
        r0 = move-exception;
        r2 = "ProxyUtil";
        r4 = "getHostProxy2";
        com.loc.w.a(r0, r2, r4);
        r0.printStackTrace();
        goto L_0x006a;
    L_0x00bc:
        r0 = move-exception;
        r1 = r7;
        r2 = r8;
        r3 = r7;
    L_0x00c0:
        r4 = "ProxyUtil";
        r5 = "getHostProxy";
        com.loc.w.a(r0, r4, r5);	 Catch:{ all -> 0x0180 }
        r0 = com.loc.n.o(r12);	 Catch:{ all -> 0x0180 }
        if (r0 == 0) goto L_0x01c3;
    L_0x00cd:
        r2 = java.util.Locale.US;	 Catch:{ all -> 0x0180 }
        r4 = r0.toLowerCase(r2);	 Catch:{ all -> 0x0180 }
        r2 = a();	 Catch:{ all -> 0x0180 }
        r0 = b();	 Catch:{ all -> 0x0180 }
        r5 = "ctwap";
        r5 = r4.indexOf(r5);	 Catch:{ all -> 0x0180 }
        if (r5 == r8) goto L_0x0106;
    L_0x00e3:
        r4 = android.text.TextUtils.isEmpty(r2);	 Catch:{ all -> 0x0180 }
        if (r4 != 0) goto L_0x01c0;
    L_0x00e9:
        r4 = "null";
        r4 = r2.equals(r4);	 Catch:{ all -> 0x0180 }
        if (r4 != 0) goto L_0x01c0;
    L_0x00f1:
        r3 = r2;
        r2 = r9;
    L_0x00f3:
        if (r2 != 0) goto L_0x00fb;
    L_0x00f5:
        r2 = "QMTAuMC4wLjIwMA==";
        r3 = com.loc.t.c(r2);	 Catch:{ all -> 0x0180 }
    L_0x00fb:
        if (r0 != r8) goto L_0x00fe;
    L_0x00fd:
        r0 = r6;
    L_0x00fe:
        if (r1 == 0) goto L_0x01b9;
    L_0x0100:
        r1.close();	 Catch:{ Throwable -> 0x0129 }
        r1 = r0;
        goto L_0x006a;
    L_0x0106:
        r5 = "wap";
        r4 = r4.indexOf(r5);	 Catch:{ all -> 0x0180 }
        if (r4 == r8) goto L_0x00fe;
    L_0x010e:
        r0 = android.text.TextUtils.isEmpty(r2);	 Catch:{ all -> 0x0180 }
        if (r0 != 0) goto L_0x01bc;
    L_0x0114:
        r0 = "null";
        r0 = r2.equals(r0);	 Catch:{ all -> 0x0180 }
        if (r0 != 0) goto L_0x01bc;
    L_0x011c:
        r0 = r2;
        r2 = r9;
    L_0x011e:
        if (r2 != 0) goto L_0x0126;
    L_0x0120:
        r0 = "QMTAuMC4wLjE3Mg==";
        r0 = com.loc.t.c(r0);	 Catch:{ all -> 0x0180 }
    L_0x0126:
        r3 = r0;
        r0 = r6;
        goto L_0x00fe;
    L_0x0129:
        r1 = move-exception;
        r2 = "ProxyUtil";
        r4 = "getHostProxy2";
        com.loc.w.a(r1, r2, r4);
        r1.printStackTrace();
        r1 = r0;
        goto L_0x006a;
    L_0x0137:
        r0 = move-exception;
        r2 = r7;
        r1 = r8;
        r3 = r7;
    L_0x013b:
        r4 = "ProxyUtil";
        r5 = "getHostProxy1";
        com.loc.w.a(r0, r4, r5);	 Catch:{ all -> 0x017e }
        r0.printStackTrace();	 Catch:{ all -> 0x017e }
        if (r2 == 0) goto L_0x006a;
    L_0x0147:
        r2.close();	 Catch:{ Throwable -> 0x014c }
        goto L_0x006a;
    L_0x014c:
        r0 = move-exception;
        r2 = "ProxyUtil";
        r4 = "getHostProxy2";
        com.loc.w.a(r0, r2, r4);
        r0.printStackTrace();
        goto L_0x006a;
    L_0x0159:
        r0 = move-exception;
        r2 = r7;
    L_0x015b:
        if (r2 == 0) goto L_0x0160;
    L_0x015d:
        r2.close();	 Catch:{ Throwable -> 0x0161 }
    L_0x0160:
        throw r0;
    L_0x0161:
        r1 = move-exception;
        r2 = "ProxyUtil";
        r3 = "getHostProxy2";
        com.loc.w.a(r1, r2, r3);
        r1.printStackTrace();
        goto L_0x0160;
    L_0x016d:
        r0 = r10;
        goto L_0x0075;
    L_0x0170:
        r0 = move-exception;
        r1 = "ProxyUtil";
        r2 = "getHostProxy2";
        com.loc.w.a(r0, r1, r2);
        r0.printStackTrace();
    L_0x017b:
        r0 = r7;
        goto L_0x0082;
    L_0x017e:
        r0 = move-exception;
        goto L_0x015b;
    L_0x0180:
        r0 = move-exception;
        r2 = r1;
        goto L_0x015b;
    L_0x0183:
        r0 = move-exception;
        r1 = r8;
        r3 = r7;
        goto L_0x013b;
    L_0x0187:
        r1 = move-exception;
        r3 = r7;
        r11 = r0;
        r0 = r1;
        r1 = r11;
        goto L_0x013b;
    L_0x018d:
        r1 = move-exception;
        r11 = r1;
        r1 = r0;
        r0 = r11;
        goto L_0x013b;
    L_0x0192:
        r0 = move-exception;
        r3 = r7;
        goto L_0x013b;
    L_0x0195:
        r0 = move-exception;
        goto L_0x013b;
    L_0x0197:
        r0 = move-exception;
        r1 = r2;
        r3 = r7;
        r2 = r8;
        goto L_0x00c0;
    L_0x019d:
        r1 = move-exception;
        r3 = r7;
        r11 = r2;
        r2 = r0;
        r0 = r1;
        r1 = r11;
        goto L_0x00c0;
    L_0x01a5:
        r1 = move-exception;
        r11 = r1;
        r1 = r2;
        r2 = r0;
        r0 = r11;
        goto L_0x00c0;
    L_0x01ac:
        r0 = move-exception;
        r3 = r7;
        r11 = r1;
        r1 = r2;
        r2 = r11;
        goto L_0x00c0;
    L_0x01b3:
        r0 = move-exception;
        r11 = r2;
        r2 = r1;
        r1 = r11;
        goto L_0x00c0;
    L_0x01b9:
        r1 = r0;
        goto L_0x006a;
    L_0x01bc:
        r2 = r10;
        r0 = r3;
        goto L_0x011e;
    L_0x01c0:
        r2 = r10;
        goto L_0x00f3;
    L_0x01c3:
        r0 = r2;
        goto L_0x00fe;
    L_0x01c6:
        r0 = r10;
        r3 = r7;
        goto L_0x00a4;
    L_0x01ca:
        r1 = r8;
        r3 = r7;
        goto L_0x0065;
    L_0x01ce:
        r1 = r10;
        r3 = r7;
        goto L_0x0059;
        */
    }

    private static boolean c(Context context) {
        return n.m(context) == 0;
    }
}
