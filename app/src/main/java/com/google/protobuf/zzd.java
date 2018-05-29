package com.google.protobuf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzd {
    private static volatile boolean zzcux;
    private static final Class<?> zzcuy;
    static final zzd zzcuz;
    private final Map<Object, Object> zzcuA;

    static {
        zzcux = false;
        zzcuy = zzLI();
        zzcuz = new zzd(true);
    }

    zzd() {
        this.zzcuA = new HashMap();
    }

    zzd(boolean z) {
        this.zzcuA = Collections.emptyMap();
    }

    private static Class<?> zzLI() {
        try {
            return Class.forName("com.google.protobuf.Extension");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
