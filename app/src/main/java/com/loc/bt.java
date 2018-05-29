package com.loc;

import android.content.Context;
import com.google.android.gms.location.LocationRequest;
import com.loc.be.a;
import com.loc.be.b;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

// compiled from: Utils.java
public class bt {
    public static String a() {
        return t.a(new Date().getTime());
    }

    public static String a(Context context, s sVar) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("\"sim\":\"").append(n.e(context)).append("\",\"sdkversion\":\"").append(sVar.c()).append("\",\"product\":\"").append(sVar.a()).append("\",\"ed\":\"").append(sVar.d()).append("\",\"nt\":\"").append(n.c(context)).append("\",\"np\":\"").append(n.a(context)).append("\",\"mnc\":\"").append(n.b(context)).append("\",\"ant\":\"").append(n.d(context)).append("\"");
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String a(String str, String str2, int i, String str3, String str4) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str).append(",\"timestamp\":\"");
        stringBuffer.append(str2);
        stringBuffer.append("\",\"et\":\"");
        stringBuffer.append(i);
        stringBuffer.append("\",\"classname\":\"");
        stringBuffer.append(str3);
        stringBuffer.append("\",");
        stringBuffer.append("\"detail\":\"");
        stringBuffer.append(str4);
        stringBuffer.append("\"");
        return stringBuffer.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(android.content.Context r5, long r6, java.lang.String r8) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.bt.a(android.content.Context, long, java.lang.String):void");
        /*
        r0 = com.loc.x.a(r5, r8);
        r3 = new java.io.File;
        r3.<init>(r0);
        r0 = r3.getParentFile();
        r0 = r0.exists();
        if (r0 != 0) goto L_0x001a;
    L_0x0013:
        r0 = r3.getParentFile();
        r0.mkdirs();
    L_0x001a:
        r2 = 0;
        r1 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x0031, IOException -> 0x0041, all -> 0x004e }
        r1.<init>(r3);	 Catch:{ FileNotFoundException -> 0x0031, IOException -> 0x0041, all -> 0x004e }
        r0 = java.lang.String.valueOf(r6);	 Catch:{ FileNotFoundException -> 0x0061, IOException -> 0x005f }
        r0 = com.loc.t.a(r0);	 Catch:{ FileNotFoundException -> 0x0061, IOException -> 0x005f }
        r1.write(r0);	 Catch:{ FileNotFoundException -> 0x0061, IOException -> 0x005f }
        if (r1 == 0) goto L_0x0030;
    L_0x002d:
        r1.close();	 Catch:{ Throwable -> 0x005b }
    L_0x0030:
        return;
    L_0x0031:
        r0 = move-exception;
        r1 = r2;
    L_0x0033:
        r0.printStackTrace();	 Catch:{ all -> 0x005d }
        if (r1 == 0) goto L_0x0030;
    L_0x0038:
        r1.close();	 Catch:{ Throwable -> 0x003c }
        goto L_0x0030;
    L_0x003c:
        r0 = move-exception;
    L_0x003d:
        r0.printStackTrace();
        goto L_0x0030;
    L_0x0041:
        r0 = move-exception;
        r1 = r2;
    L_0x0043:
        r0.printStackTrace();	 Catch:{ all -> 0x005d }
        if (r1 == 0) goto L_0x0030;
    L_0x0048:
        r1.close();	 Catch:{ Throwable -> 0x004c }
        goto L_0x0030;
    L_0x004c:
        r0 = move-exception;
        goto L_0x003d;
    L_0x004e:
        r0 = move-exception;
        r1 = r2;
    L_0x0050:
        if (r1 == 0) goto L_0x0055;
    L_0x0052:
        r1.close();	 Catch:{ Throwable -> 0x0056 }
    L_0x0055:
        throw r0;
    L_0x0056:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0055;
    L_0x005b:
        r0 = move-exception;
        goto L_0x003d;
    L_0x005d:
        r0 = move-exception;
        goto L_0x0050;
    L_0x005f:
        r0 = move-exception;
        goto L_0x0043;
    L_0x0061:
        r0 = move-exception;
        goto L_0x0033;
        */
    }

    public static void a(Context context, String str, byte[] bArr) {
        be a;
        Throwable th;
        OutputStream outputStream = null;
        if (bArr != null && bArr.length != 0) {
            synchronized (bt.class) {
                Random random = new Random();
                try {
                    a = be.a(new File(x.a(context, str)), 307200);
                    try {
                        a b = a.b(Integer.toString(random.nextInt(LocationRequest.PRIORITY_HIGH_ACCURACY)) + Long.toString(System.nanoTime()));
                        outputStream = b.a();
                        outputStream.write(bArr);
                        b.b();
                        a.d();
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (Throwable th2) {
                                th2.printStackTrace();
                            }
                        }
                        if (a != null) {
                            try {
                                a.close();
                            } catch (Throwable th22) {
                                th22.printStackTrace();
                            }
                        }
                    } catch (Throwable th3) {
                        th22 = th3;
                        w.a(th22, "Statistics.Utils", "writeToDiskLruCache");
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (a != null) {
                            a.close();
                        }
                    }
                } catch (Throwable th4) {
                    th22 = th4;
                    a = null;
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (Throwable th5) {
                            th5.printStackTrace();
                        }
                    }
                    if (a != null) {
                        try {
                            a.close();
                        } catch (Throwable th52) {
                            th52.printStackTrace();
                        }
                    }
                    throw th22;
                }
            }
        }
    }

    public static byte[] a(Context context, String str) {
        int i = 0;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[0];
        String a = x.a(context, str);
        be beVar = null;
        try {
            beVar = be.a(new File(a), 307200);
            File file = new File(a);
            if (file.exists()) {
                String[] list = file.list();
                int length = list.length;
                while (i < length) {
                    String str2 = list[i];
                    if (str2.contains(".0")) {
                        byteArrayOutputStream.write(a(beVar, str2.split("\\.")[0], true));
                    }
                    i++;
                }
            }
            bArr = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (beVar != null) {
                try {
                    beVar.close();
                } catch (Throwable th) {
                    th = th;
                    th.printStackTrace();
                    return bArr;
                }
            }
            return bArr;
        } catch (Throwable th2) {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            if (beVar != null) {
                try {
                    beVar.close();
                } catch (Throwable th3) {
                    Throwable th32;
                    th32.printStackTrace();
                }
            }
        }
    }

    static byte[] a(be beVar, String str, boolean z) {
        InputStream inputStream = null;
        InputStream inputStream2 = null;
        byte[] bArr = new byte[0];
        try {
            b a = beVar.a(str);
            if (a == null) {
                if (null != null) {
                    try {
                        inputStream2.close();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
                if (a != null) {
                    try {
                        a.close();
                    } catch (Throwable th2) {
                        th = th2;
                        th.printStackTrace();
                        return bArr;
                    }
                }
                return bArr;
            }
            try {
                inputStream = a.a();
            } catch (Throwable th3) {
                th = th3;
                w.a(th, "Utils", "readSingleLog");
                if (inputStream != null) {
                    inputStream.close();
                }
                if (a != null) {
                    a.close();
                }
                return bArr;
            }
            if (inputStream == null) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable th4) {
                        th4.printStackTrace();
                    }
                }
                if (a != null) {
                    try {
                        a.close();
                    } catch (Throwable th5) {
                        th4 = th5;
                        th4.printStackTrace();
                        return bArr;
                    }
                }
                return bArr;
            }
            bArr = new byte[inputStream.available()];
            inputStream.read(bArr);
            if (z) {
                beVar.c(str);
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable th42) {
                    th42.printStackTrace();
                }
            }
            if (a != null) {
                try {
                    a.close();
                } catch (Throwable th6) {
                    th42 = th6;
                    th42.printStackTrace();
                    return bArr;
                }
            }
            return bArr;
        } catch (Throwable th7) {
            th = th7;
            a = null;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable th422) {
                    th422.printStackTrace();
                }
            }
            if (a != null) {
                try {
                    a.close();
                } catch (Throwable th4222) {
                    th4222.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static int b(Context context, String str) {
        try {
            File file = new File(x.a(context, str));
            return !file.exists() ? 0 : file.list().length;
        } catch (Throwable th) {
            w.a(th, "Statistics.Utils", "getFileNum");
            return 0;
        }
    }

    public static long c(Context context, String str) {
        FileInputStream fileInputStream;
        Throwable e;
        Throwable th;
        long j = 0;
        File file = new File(x.a(context, str));
        if (file.exists()) {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    byte[] bArr = new byte[fileInputStream.available()];
                    fileInputStream.read(bArr);
                    j = Long.parseLong(t.a(bArr));
                } catch (FileNotFoundException e2) {
                    e = e2;
                    w.a(e, "StatisticsManager", "getUpdateTime");
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    return j;
                } catch (IOException e3) {
                    e = e3;
                    w.a(e, "StatisticsManager", "getUpdateTime");
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    return j;
                } catch (Throwable th2) {
                    e = th2;
                    w.a(e, "StatisticsManager", "getUpdateTime");
                    if (file.exists()) {
                        file.delete();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    return j;
                }
            } catch (FileNotFoundException e4) {
                e = e4;
                fileInputStream = null;
                w.a(e, "StatisticsManager", "getUpdateTime");
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th3) {
                        e = th3;
                        e.printStackTrace();
                        return j;
                    }
                }
                return j;
            } catch (IOException e5) {
                e = e5;
                fileInputStream = null;
                w.a(e, "StatisticsManager", "getUpdateTime");
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th4) {
                        e = th4;
                        e.printStackTrace();
                        return j;
                    }
                }
                return j;
            } catch (Throwable th5) {
                th = th5;
                fileInputStream = null;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th6) {
                        th6.printStackTrace();
                    }
                }
                throw th;
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Throwable th7) {
                    e = th7;
                    e.printStackTrace();
                    return j;
                }
            }
        }
        return j;
    }
}
