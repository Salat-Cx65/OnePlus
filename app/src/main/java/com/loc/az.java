package com.loc;

import android.content.Context;
import android.text.TextUtils;
import com.loc.ay.a;
import dalvik.system.DexFile;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

// compiled from: DynamicClassLoader.java
final class az extends av {
    private PublicKey g;

    // compiled from: DynamicClassLoader.java
    class AnonymousClass_1 implements Runnable {
        final /* synthetic */ Context a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        AnonymousClass_1(Context context, String str, String str2) {
            this.a = context;
            this.b = str;
            this.c = str2;
        }

        public final void run() {
            try {
                az.this.a(this.a, this.b, this.c);
            } catch (Throwable th) {
                w.a(th, "dLoader", "run()");
            }
        }
    }

    az(Context context, s sVar) throws Exception {
        super(context, sVar);
        this.g = null;
        String b = ay.b(context, sVar.a(), sVar.b());
        String a = ay.a(context);
        if (TextUtils.isEmpty(b) || TextUtils.isEmpty(a)) {
            throw new Exception("dexPath or dexOutputDir is null.");
        }
        String str = a + File.separator + ay.a(new File(b).getName());
        if (this.c == null) {
            b();
            this.c = DexFile.loadDex(b, str, 0);
        }
        z.b().submit(new AnonymousClass_1(context, b, a));
    }

    private static void a(JarFile jarFile, JarEntry jarEntry) throws IOException {
        try {
            Closeable inputStream = jarFile.getInputStream(jarEntry);
            do {
            } while (inputStream.read(new byte[8192]) > 0);
            try {
                bd.a(inputStream);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        } catch (Throwable th2) {
            try {
                w.a(th2, "DyLoader", "loadJa");
                try {
                    bd.a(null);
                } catch (Throwable th22) {
                    th22.printStackTrace();
                }
            } catch (Throwable th3) {
                try {
                    bd.a(null);
                } catch (Throwable th4) {
                    th4.printStackTrace();
                }
            }
        }
    }

    private boolean a(File file) {
        Throwable th;
        Throwable th2;
        boolean z = false;
        JarFile jarFile = null;
        try {
            if (this.g == null) {
                this.g = bd.a();
            }
            JarFile jarFile2 = new JarFile(file);
            try {
                JarEntry jarEntry = jarFile2.getJarEntry("classes.dex");
                if (jarEntry == null) {
                    try {
                        jarFile2.close();
                    } catch (Throwable th3) {
                    }
                } else {
                    a(jarFile2, jarEntry);
                    Certificate[] certificates = jarEntry.getCertificates();
                    if (certificates == null) {
                        try {
                            jarFile2.close();
                        } catch (Throwable th4) {
                        }
                    } else {
                        z = a(certificates);
                        try {
                            jarFile2.close();
                        } catch (Throwable th5) {
                        }
                    }
                }
            } catch (Throwable th6) {
                th2 = th6;
                jarFile = jarFile2;
                if (jarFile != null) {
                    jarFile.close();
                }
                throw th2;
            }
        } catch (Throwable th7) {
            th = th7;
            try {
                w.a(th, "DyLoader", "verify");
                if (jarFile != null) {
                    try {
                        jarFile.close();
                    } catch (Throwable th8) {
                    }
                }
            } catch (Throwable th9) {
                th2 = th9;
                if (jarFile != null) {
                    try {
                        jarFile.close();
                    } catch (Throwable th10) {
                    }
                }
                throw th2;
            }
            return z;
        }
        return z;
    }

    private boolean a(Certificate[] certificateArr) {
        try {
            if (certificateArr.length > 0) {
                int length = certificateArr.length - 1;
                if (length >= 0) {
                    certificateArr[length].verify(this.g);
                    return true;
                }
            }
        } catch (Throwable e) {
            w.a(e, "DyLoader", "check");
        }
        return false;
    }

    final void a(Context context, String str, String str2) throws Exception {
        Object obj = null;
        new Date().getTime();
        try {
            af afVar = new af(context, bb.b());
            File file = new File(str);
            bc a = a.a(afVar, file.getName());
            if (a != null) {
                this.f = a.e();
            }
            s sVar = this.e;
            String absolutePath = file.getAbsolutePath();
            if (!(a(new File(absolutePath)) ? bd.a(afVar, ay.a(this.a, sVar.a(), sVar.b()), absolutePath, sVar) : false)) {
                this.d = false;
                ay.a(this.a, afVar, file.getName());
                Object a2 = ay.a(this.a, afVar, this.e);
                if (!TextUtils.isEmpty(a2)) {
                    this.f = a2;
                    ay.a(this.a, this.e);
                }
            }
            if (file.exists()) {
                if (new File(str2 + File.separator + ay.a(file.getName())).exists()) {
                    String a3 = ay.a(file.getName());
                    String str3 = this.f;
                    String a4 = ay.a(this.a, a3);
                    if (!bd.a(afVar, a3, a4, this.e)) {
                        if (a.a(afVar, a3) == null) {
                            if (!TextUtils.isEmpty(this.f)) {
                                afVar.a(new bc.a(a3, p.a(a4), this.e.a(), this.e.b(), str3).a("useod").a(), bc.b(a3));
                            }
                        }
                        if (obj == null) {
                            ay.a(this.a, this.e);
                        }
                    }
                    obj = 1;
                    if (obj == null) {
                        ay.a(this.a, this.e);
                    }
                }
                new Date().getTime();
            }
        } catch (Throwable th) {
            w.a(th, "dLoader", "verifyD()");
        }
    }

    protected final Class<?> findClass(String str) throws ClassNotFoundException {
        if (this.c == null) {
            throw new ClassNotFoundException(str);
        }
        Class<?> cls;
        Object obj = null;
        try {
            Map map = this.b;
            synchronized (map) {
                try {
                    cls = (Class) this.b.get(str);
                    try {
                    } catch (Throwable th) {
                        Throwable th2 = th;
                        Class<?> cls2 = cls;
                        Throwable th3 = th2;
                        throw th3;
                    }
                } catch (Throwable th4) {
                    th3 = th4;
                    throw th3;
                }
            }
        } catch (Throwable th32) {
            w.a(th32, "dLoader", "findCl");
            cls = cls2;
        }
        if (cls == null) {
            cls = this.c.loadClass(str, this);
            if (cls == null) {
                throw new ClassNotFoundException(str);
            }
            try {
                synchronized (this.b) {
                    this.b.put(str, cls);
                }
            } catch (Throwable th5) {
                w.a(th5, "dLoader", "findCl");
            }
        }
        return cls;
    }
}
