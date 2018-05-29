package com.loc;

import android.content.Context;
import java.util.List;

// compiled from: SDKDBOperation.java
public final class ap {
    private af a;
    private Context b;

    public ap(Context context, boolean z) {
        this.b = context;
        this.a = a(this.b, z);
    }

    private static af a(Context context, boolean z) {
        try {
            return new af(context, af.a(am.class));
        } catch (Throwable th) {
            if (z) {
                th.printStackTrace();
                return null;
            }
            w.a(th, "SDKDB", "getDB");
            return null;
        }
    }

    public final List<s> a() {
        try {
            return this.a.a(s.g(), s.class, true);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public final void a(s sVar) {
        if (sVar != null) {
            try {
                if (this.a == null) {
                    this.a = a(this.b, false);
                }
                String a = s.a(sVar.a());
                List<s> b = this.a.b(a, s.class);
                if (b == null || b.size() == 0) {
                    this.a.a((Object) sVar);
                    return;
                }
                Object obj;
                for (s sVar2 : b) {
                    if (sVar2.equals(sVar)) {
                        obj = null;
                        break;
                    }
                }
                obj = 1;
                if (obj != null) {
                    this.a.a(a, (Object) sVar);
                }
            } catch (Throwable th) {
                w.a(th, "SDKDB", "insert");
                th.printStackTrace();
            }
        }
    }
}
