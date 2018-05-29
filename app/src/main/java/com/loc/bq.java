package com.loc;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.loc.be.a;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.json.JSONObject;

// compiled from: OfflineLocManager.java
public class bq {
    static int a;
    static boolean b;

    // compiled from: OfflineLocManager.java
    static class AnonymousClass_1 implements Runnable {
        final /* synthetic */ bp a;
        final /* synthetic */ Context b;

        AnonymousClass_1(bp bpVar, Context context) {
            this.a = bpVar;
            this.b = context;
        }

        public final void run() {
            Throwable th;
            OutputStream outputStream = null;
            synchronized (bq.class) {
                try {
                    byte[] a = this.a.a();
                    be a2 = be.a(new File(x.a(this.b, x.h)), 2097152);
                    try {
                        a2.a(a);
                        a b = a2.b(Long.toString(System.currentTimeMillis()));
                        outputStream = b.a();
                        outputStream.write(a);
                        b.b();
                        a2.d();
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (Throwable th2) {
                                th2.printStackTrace();
                            }
                        }
                        if (a2 != null) {
                            try {
                                a2.close();
                            } catch (Throwable th22) {
                                th22.printStackTrace();
                            }
                        }
                    } catch (Throwable th3) {
                        th22 = th3;
                        w.a(th22, "OfflineLocManager", "applyOfflineLocEntity");
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (a2 != null) {
                            a2.close();
                        }
                    }
                } catch (Throwable th4) {
                    th22 = th4;
                    a2 = null;
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (Throwable th5) {
                            th5.printStackTrace();
                        }
                    }
                    if (a2 != null) {
                        try {
                            a2.close();
                        } catch (Throwable th52) {
                            th52.printStackTrace();
                        }
                    }
                    throw th22;
                }
            }
        }
    }

    static {
        a = 1000;
        b = false;
    }

    private static String a(be beVar) {
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        try {
            File b = beVar.b();
            if (b != null && b.exists()) {
                String[] list = b.list();
                int length = list.length;
                for (int i = 0; i < length; i++) {
                    String str = list[i];
                    if (str.contains(".0")) {
                        str = t.a(bt.a(beVar, str.split("\\.")[0], false));
                        if (obj != null) {
                            obj = null;
                        } else {
                            stringBuilder.append(",");
                        }
                        stringBuilder.append("{\"log\":\"").append(str).append("\"}");
                    }
                }
            }
        } catch (Throwable th) {
            w.a(th, "StatisticsManager", "getContent");
        }
        return stringBuilder.toString();
    }

    public static synchronized void a(int i, boolean z) {
        synchronized (bq.class) {
            a = i;
            b = z;
        }
    }

    public static void a(Context context) {
        Throwable th;
        be beVar = null;
        try {
            be a;
            if (c(context)) {
                a = be.a(new File(x.a(context, x.h)), 2097152);
                try {
                    Object a2 = a(a);
                    if (TextUtils.isEmpty(a2)) {
                        a2 = null;
                    } else {
                        String b = m.b(t.a(d(context)));
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("{\"pinfo\":\"").append(b).append("\",\"els\":[");
                        stringBuilder.append(a2);
                        stringBuilder.append("]}");
                        a2 = stringBuilder.toString();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        w.a(th, "OfflineLocManager", "updateOfflineLocData");
                    } catch (Throwable th3) {
                        th = th3;
                        if (a != null) {
                            try {
                                if (!a.c()) {
                                    a.close();
                                }
                            } catch (Throwable th4) {
                                th4.printStackTrace();
                            }
                        }
                        throw th;
                    }
                    if (a == null) {
                        try {
                            if (!a.c()) {
                                a.close();
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            th.printStackTrace();
                        }
                    }
                }
                if (!TextUtils.isEmpty(r0)) {
                    bn yVar = new y(t.c(t.a(r0)), "6");
                    bi.a();
                    JSONObject jSONObject = new JSONObject(new String(bi.a(yVar)));
                    if (jSONObject.has("code") && jSONObject.getInt("code") == 1) {
                        if (a != null) {
                            try {
                                a.e();
                            } catch (Throwable th6) {
                                w.a(th6, "StatisticsManager", "getContent");
                            }
                        }
                        a = null;
                    }
                    if (a != null) {
                        try {
                            if (!a.c()) {
                                try {
                                    a.close();
                                } catch (Throwable th7) {
                                    th6 = th7;
                                }
                            }
                        } catch (Throwable th8) {
                            th6 = th8;
                            th6.printStackTrace();
                        }
                    }
                } else if (a != null) {
                    try {
                        if (!a.c()) {
                            try {
                                a.close();
                            } catch (Throwable th9) {
                                th6 = th9;
                            }
                        }
                    } catch (Throwable th10) {
                        th6 = th10;
                        th6.printStackTrace();
                    }
                }
            } else if (null != null) {
                try {
                    if (!beVar.c()) {
                        try {
                            beVar.close();
                        } catch (Throwable th11) {
                            th6 = th11;
                        }
                    }
                } catch (Throwable th12) {
                    th6 = th12;
                    th6.printStackTrace();
                }
            }
        } catch (Throwable th13) {
            th6 = th13;
            a = null;
            if (a != null) {
                if (a.c()) {
                    a.close();
                }
            }
            throw th6;
        }
    }

    public static synchronized void a(bp bpVar, Context context) {
        synchronized (bq.class) {
            z.b().submit(new AnonymousClass_1(bpVar, context));
        }
    }

    private static long b(Context context) {
        Throwable th;
        File file = new File(x.a(context, "f.log"));
        if (!file.exists()) {
            return 0;
        }
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                byte[] bArr = new byte[fileInputStream2.available()];
                fileInputStream2.read(bArr);
                long parseLong = Long.parseLong(t.a(bArr));
                if (fileInputStream2 == null) {
                    return parseLong;
                }
                try {
                    fileInputStream2.close();
                    return parseLong;
                } catch (Throwable th2) {
                    th2.printStackTrace();
                    return parseLong;
                }
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            try {
                w.a(th, "OfflineLocManager", "getUpdateTime");
                if (file.exists()) {
                    file.delete();
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th5) {
                        th5.printStackTrace();
                    }
                }
                return System.currentTimeMillis();
            } catch (Throwable th6) {
                th5 = th6;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th7) {
                        th7.printStackTrace();
                    }
                }
                throw th5;
            }
        }
    }

    private static synchronized boolean c(Context context) {
        Throwable th;
        Throwable th2;
        boolean z = false;
        synchronized (bq.class) {
            try {
                if (n.m(context) == 1 || b) {
                    if (System.currentTimeMillis() - b(context) >= 14400000) {
                        FileOutputStream fileOutputStream;
                        long currentTimeMillis = System.currentTimeMillis();
                        FileOutputStream fileOutputStream2 = null;
                        try {
                            File file = new File(x.a(context, "f.log"));
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                            }
                            fileOutputStream = new FileOutputStream(file);
                        } catch (Throwable th3) {
                            th = th3;
                            try {
                                w.a(th, "OfflineLocManager", "updateLogUpdateTime");
                                if (fileOutputStream2 != null) {
                                    try {
                                        fileOutputStream2.close();
                                    } catch (Throwable th4) {
                                        th4.printStackTrace();
                                    }
                                }
                            } catch (Throwable th5) {
                                th2 = th5;
                                if (fileOutputStream2 != null) {
                                    try {
                                        fileOutputStream2.close();
                                    } catch (Throwable th42) {
                                        th42.printStackTrace();
                                    }
                                }
                                throw th2;
                            }
                            z = true;
                            return z;
                        }
                        try {
                            fileOutputStream.write(t.a(String.valueOf(currentTimeMillis)));
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (Throwable th422) {
                                    th422.printStackTrace();
                                }
                            }
                        } catch (Throwable th6) {
                            th2 = th6;
                            fileOutputStream2 = fileOutputStream;
                            if (fileOutputStream2 != null) {
                                fileOutputStream2.close();
                            }
                            throw th2;
                        }
                        z = true;
                    }
                }
            } catch (Throwable th22) {
                w.a(th22, "StatisticsManager", "isUpdate");
            }
        }
        return z;
    }

    private static String d(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("\"key\":\"").append(k.f(context)).append("\",\"platform\":\"android\",\"diu\":\"").append(n.q(context)).append("\",\"mac\":\"").append(n.i(context)).append("\",\"tid\":\"").append(n.f(context)).append("\",\"umidt\":\"").append(n.a()).append("\",\"manufacture\":\"").append(Build.MANUFACTURER).append("\",\"device\":\"").append(Build.DEVICE).append("\",\"sim\":\"").append(n.r(context)).append("\",\"pkg\":\"").append(k.c(context)).append("\",\"model\":\"").append(Build.MODEL).append("\",\"appversion\":\"").append(k.d(context)).append("\",\"appname\":\"").append(k.b(context)).append("\"");
        } catch (Throwable th) {
            w.a(th, "CInfo", "getPublicJSONInfo");
        }
        return stringBuilder.toString();
    }
}
