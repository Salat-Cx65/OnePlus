package com.loc;

import java.util.HashMap;
import java.util.Map;

// compiled from: LogInfo.java
public abstract class ao {
    @ah(a = "b2", b = 2)
    protected int a;
    @ah(a = "b1", b = 6)
    protected String b;
    @ah(a = "b3", b = 2)
    protected int c;
    @ah(a = "a1", b = 6)
    private String d;

    public ao() {
        this.a = -1;
        this.c = 1;
    }

    public static String c(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("b2=").append(i);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String c(String str) {
        Map hashMap = new HashMap();
        hashMap.put("b1", str);
        return af.a(hashMap);
    }

    public final int a() {
        return this.a;
    }

    public final void a(int i) {
        this.a = i;
    }

    public final void a(String str) {
        this.b = str;
    }

    public final String b() {
        return this.b;
    }

    public final void b(int i) {
        this.c = i;
    }

    public final void b(String str) {
        this.d = t.b(str);
    }

    public final int c() {
        return this.c;
    }
}
