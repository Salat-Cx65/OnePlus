package com.loc;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;

// compiled from: SDKCoordinatorDownload.java
public final class r extends Thread implements com.loc.bk.a {
    private static String h;
    private static String i;
    private bk a;
    private a b;
    private RandomAccessFile c;
    private String d;
    private String e;
    private String f;
    private Context g;

    // compiled from: SDKCoordinatorDownload.java
    private static class a extends bn {
        private String a;

        a(String str) {
            this.a = str;
        }

        public final Map<String, String> b() {
            return null;
        }

        public final Map<String, String> b_() {
            return null;
        }

        public final String c() {
            return this.a;
        }
    }

    static {
        h = "sodownload";
        i = "sofail";
    }

    public r(Context context, String str, String str2, String str3) {
        this.g = context;
        this.f = str3;
        this.d = a(context, str + "temp.so");
        this.e = a(context, "libwgs2gcj.so");
        this.b = new a(str2);
        this.a = new bk(this.b);
    }

    public static String a(Context context, String str) {
        return context.getFilesDir().getAbsolutePath() + File.separator + "libso" + File.separator + str;
    }

    private void e() {
        File file = new File(this.d);
        if (file.exists()) {
            file.delete();
        }
    }

    public final void a() {
        if (this.b != null && !TextUtils.isEmpty(this.b.c()) && this.b.c().contains("libJni_wgs2gcj.so") && this.b.c().contains(Build.CPU_ABI) && !new File(this.e).exists()) {
            start();
        }
    }

    public final void a(byte[] bArr, long j) {
        try {
            if (this.c == null) {
                File file = new File(this.d);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                try {
                    this.c = new RandomAccessFile(file, "rw");
                } catch (Throwable e) {
                    w.a(e, "SDKCoordinatorDownload", "onDownload");
                    e();
                }
            }
            if (this.c != null) {
                try {
                    this.c.seek(j);
                    this.c.write(bArr);
                } catch (Throwable e2) {
                    e();
                    w.a(e2, "SDKCoordinatorDownload", "onDownload");
                }
            }
        } catch (Throwable e22) {
            e();
            w.a(e22, "SDKCoordinatorDownload", "onDownload");
        }
    }

    public final void b() {
        e();
    }

    public final void c() {
        try {
            if (this.c != null) {
                this.c.close();
            }
            String a = p.a(this.d);
            if (a == null || !a.equalsIgnoreCase(this.f)) {
                e();
            } else if (new File(this.e).exists()) {
                e();
            } else {
                new File(this.d).renameTo(new File(this.e));
            }
        } catch (Throwable th) {
            e();
            file = new File(this.e);
            File file;
            if (file.exists()) {
                file.delete();
            }
            w.a(th, "SDKCoordinatorDownload", "onFinish");
        }
    }

    public final void d() {
        try {
            if (this.c != null) {
                this.c.close();
            }
            e();
            File file = new File(a(this.g, "tempfile"));
            if (!file.exists()) {
                try {
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdir();
                    }
                    file.createNewFile();
                } catch (Throwable th) {
                    w.a(th, "SDKCoordinatorDownload", "onException");
                }
            }
        } catch (Throwable th2) {
            w.a(th2, "SDKCoordinatorDownload", "onException");
        }
    }

    public final void run() {
        try {
            File file = new File(a(this.g, "tempfile"));
            if (file.exists()) {
                file.delete();
            }
            this.a.a(this);
        } catch (Throwable th) {
            w.a(th, "SDKCoordinatorDownload", "run");
            e();
        }
    }
}
