package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

// compiled from: ClassLoaderFactory.java
public final class aw {
    private static final aw a;
    private final Map<String, av> b;

    static {
        a = new aw();
    }

    private aw() {
        this.b = new HashMap();
    }

    public static aw a() {
        return a;
    }

    final synchronized av a(Context context, s sVar) throws Exception {
        av avVar;
        Object obj;
        if (sVar != null) {
            if (!(TextUtils.isEmpty(sVar.b()) || TextUtils.isEmpty(sVar.a()))) {
                obj = 1;
                if (obj != null || context == null) {
                    throw new Exception("sdkInfo or context referance is null");
                }
                String a = sVar.a();
                avVar = (av) this.b.get(a);
                if (avVar == null) {
                    try {
                        av azVar = new az(context.getApplicationContext(), sVar);
                        try {
                            this.b.put(a, azVar);
                            ba.a(context, sVar);
                            avVar = azVar;
                        } catch (Throwable th) {
                            avVar = azVar;
                        }
                    } catch (Throwable th2) {
                    }
                }
            }
        }
        obj = null;
        if (obj != null) {
        }
        throw new Exception("sdkInfo or context referance is null");
        return avVar;
    }
}
