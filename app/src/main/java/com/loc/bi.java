package com.loc;

import java.net.URLConnection;

// compiled from: BaseNetManager.java
public final class bi {
    private static bi a;

    // compiled from: BaseNetManager.java
    public static interface a {
        URLConnection a();
    }

    public static bi a() {
        if (a == null) {
            a = new bi();
        }
        return a;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.loc.bo a(com.loc.bn r6, boolean r7) throws com.loc.j {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bi.a(com.loc.bn, boolean):com.loc.bo");
        /*
        if (r6 != 0) goto L_0x000c;
    L_0x0002:
        r0 = new com.loc.j;	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r1 = "requeust is null";
        r0.<init>(r1);	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        throw r0;	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
    L_0x000a:
        r0 = move-exception;
        throw r0;
    L_0x000c:
        r0 = r6.c();	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        if (r0 == 0) goto L_0x001e;
    L_0x0012:
        r0 = "";
        r1 = r6.c();	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r0 = r0.equals(r1);	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        if (r0 == 0) goto L_0x0032;
    L_0x001e:
        r0 = new com.loc.j;	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r1 = "request url is empty";
        r0.<init>(r1);	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        throw r0;	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
    L_0x0026:
        r0 = move-exception;
        r0.printStackTrace();
        r0 = new com.loc.j;
        r1 = "\u672a\u77e5\u7684\u9519\u8bef";
        r0.<init>(r1);
        throw r0;
    L_0x0032:
        r0 = r6.e;	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        if (r0 != 0) goto L_0x0072;
    L_0x0036:
        r0 = 0;
    L_0x0037:
        r2 = new com.loc.bl;	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r1 = r6.c;	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r3 = r6.d;	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r2.<init>(r1, r3, r0, r7);	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r0 = r6.d();	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        if (r0 == 0) goto L_0x0049;
    L_0x0046:
        r0 = r0.length;	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        if (r0 != 0) goto L_0x0075;
    L_0x0049:
        r0 = r6.c();	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r1 = r0;
    L_0x004e:
        r3 = r6.b();	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r0 = r6.d();	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        if (r0 == 0) goto L_0x005b;
    L_0x0058:
        r4 = r0.length;	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        if (r4 != 0) goto L_0x006d;
    L_0x005b:
        r4 = r6.b_();	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r4 = com.loc.bl.a(r4);	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r5 = android.text.TextUtils.isEmpty(r4);	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        if (r5 != 0) goto L_0x006d;
    L_0x0069:
        r0 = com.loc.t.a(r4);	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
    L_0x006d:
        r0 = r2.a(r1, r3, r0);	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        return r0;
    L_0x0072:
        r0 = r6.e;	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        goto L_0x0037;
    L_0x0075:
        r0 = r6.b_();	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        if (r0 != 0) goto L_0x0081;
    L_0x007b:
        r0 = r6.c();	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r1 = r0;
        goto L_0x004e;
    L_0x0081:
        r0 = com.loc.bl.a(r0);	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r1 = new java.lang.StringBuffer;	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r1.<init>();	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r3 = r6.c();	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r3 = r1.append(r3);	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r4 = "?";
        r3 = r3.append(r4);	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r3.append(r0);	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r0 = r1.toString();	 Catch:{ j -> 0x000a, Throwable -> 0x0026 }
        r1 = r0;
        goto L_0x004e;
        */
    }

    public static byte[] a(bn bnVar) throws j {
        try {
            bo a = a(bnVar, false);
            return a != null ? a.a : null;
        } catch (j e) {
            throw e;
        }
    }
}
