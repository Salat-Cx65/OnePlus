package com.loc;

import android.content.Context;
import dalvik.system.DexFile;
import java.util.HashMap;
import java.util.Map;

// compiled from: BaseClassLoader.java
abstract class av extends ClassLoader {
    protected final Context a;
    protected final Map<String, Class<?>> b;
    protected DexFile c;
    volatile boolean d;
    protected s e;
    protected String f;

    public av(Context context, s sVar) {
        super(context.getClassLoader());
        this.b = new HashMap();
        this.c = null;
        this.d = true;
        this.a = context;
        this.e = sVar;
    }

    public final boolean a() {
        return this.c != null;
    }

    protected final void b() {
        try {
            synchronized (this.b) {
                this.b.clear();
            }
            if (this.c != null) {
                this.c.close();
            }
        } catch (Throwable th) {
            w.a(th, "BaseClassLoader", "releaseDexFile()");
        }
    }
}
