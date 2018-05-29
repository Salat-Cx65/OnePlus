package com.loc;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import net.oneplus.weather.R;
import net.oneplus.weather.util.StringUtils;

// compiled from: ClientInfo.java
public final class m {

    // compiled from: ClientInfo.java
    private static class a {
        String a;
        String b;
        String c;
        String d;
        String e;
        String f;
        String g;
        String h;
        String i;
        String j;
        String k;
        String l;
        String m;
        String n;
        String o;
        String p;
        String q;
        String r;
        String s;
        String t;
        String u;
        String v;
        String w;
        String x;
        String y;

        private a() {
        }
    }

    public static String a() {
        try {
            String valueOf = String.valueOf(System.currentTimeMillis());
            try {
                String str = "1";
                if (!k.a()) {
                    str = "0";
                }
                int length = valueOf.length();
                return valueOf.substring(0, length - 2) + str + valueOf.substring(length - 1);
            } catch (Throwable th) {
                Throwable th2 = th;
                str = valueOf;
                Throwable th3 = th2;
                w.a(th3, "CInfo", "getTS");
                return str;
            }
        } catch (Throwable th4) {
            th2 = th4;
            str = null;
            th3 = th2;
            w.a(th3, "CInfo", "getTS");
            return str;
        }
    }

    public static String a(Context context, String str, String str2) {
        try {
            return p.b(k.e(context) + ":" + str.substring(0, str.length() - 3) + ":" + str2);
        } catch (Throwable th) {
            w.a(th, "CInfo", "Scode");
            return null;
        }
    }

    private static void a(ByteArrayOutputStream byteArrayOutputStream, String str) {
        if (TextUtils.isEmpty(str)) {
            t.a(byteArrayOutputStream, (byte) 0, new byte[0]);
        } else {
            t.a(byteArrayOutputStream, str.getBytes().length > 255 ? (byte) -1 : (byte) str.getBytes().length, t.a(str));
        }
    }

    public static byte[] a(Context context, boolean z) {
        try {
            a aVar = new a();
            aVar.a = n.q(context);
            aVar.b = n.i(context);
            String f = n.f(context);
            if (f == null) {
                f = StringUtils.EMPTY_STRING;
            }
            aVar.c = f;
            aVar.d = k.c(context);
            aVar.e = Build.MODEL;
            aVar.f = Build.MANUFACTURER;
            aVar.g = Build.DEVICE;
            aVar.h = k.b(context);
            aVar.i = k.d(context);
            aVar.j = String.valueOf(VERSION.SDK_INT);
            aVar.k = n.r(context);
            aVar.l = n.p(context);
            aVar.m = n.m(context);
            aVar.n = n.l(context);
            aVar.o = n.s(context);
            aVar.p = n.k(context);
            if (z) {
                aVar.q = StringUtils.EMPTY_STRING;
            } else {
                aVar.q = n.h(context);
            }
            if (z) {
                aVar.r = StringUtils.EMPTY_STRING;
            } else {
                aVar.r = n.g(context);
            }
            if (z) {
                aVar.s = StringUtils.EMPTY_STRING;
                aVar.t = StringUtils.EMPTY_STRING;
            } else {
                String[] j = n.j(context);
                aVar.s = j[0];
                aVar.t = j[1];
            }
            aVar.w = n.a();
            return a(aVar);
        } catch (Throwable th) {
            w.a(th, "CInfo", "getGZipXInfo");
            return null;
        }
    }

    private static byte[] a(a aVar) {
        Throwable th;
        byte[] bArr = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                a(byteArrayOutputStream, aVar.a);
                a(byteArrayOutputStream, aVar.b);
                a(byteArrayOutputStream, aVar.c);
                a(byteArrayOutputStream, aVar.d);
                a(byteArrayOutputStream, aVar.e);
                a(byteArrayOutputStream, aVar.f);
                a(byteArrayOutputStream, aVar.g);
                a(byteArrayOutputStream, aVar.h);
                a(byteArrayOutputStream, aVar.i);
                a(byteArrayOutputStream, aVar.j);
                a(byteArrayOutputStream, aVar.k);
                a(byteArrayOutputStream, aVar.l);
                a(byteArrayOutputStream, aVar.m);
                a(byteArrayOutputStream, aVar.n);
                a(byteArrayOutputStream, aVar.o);
                a(byteArrayOutputStream, aVar.p);
                a(byteArrayOutputStream, aVar.q);
                a(byteArrayOutputStream, aVar.r);
                a(byteArrayOutputStream, aVar.s);
                a(byteArrayOutputStream, aVar.t);
                a(byteArrayOutputStream, aVar.u);
                a(byteArrayOutputStream, aVar.v);
                a(byteArrayOutputStream, aVar.w);
                a(byteArrayOutputStream, aVar.x);
                a(byteArrayOutputStream, aVar.y);
                byte[] b = t.b(byteArrayOutputStream.toByteArray());
                Key c = t.c();
                if (b.length > 117) {
                    byte[] bArr2 = new byte[117];
                    System.arraycopy(b, 0, bArr2, 0, R.styleable.AppCompatTheme_windowMinWidthMinor);
                    Object a = o.a(bArr2, c);
                    Object obj = new Object[((b.length + 128) - 117)];
                    System.arraycopy(a, 0, obj, 0, AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
                    System.arraycopy(b, R.styleable.AppCompatTheme_windowMinWidthMinor, obj, AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS, b.length - 117);
                    Object obj2 = obj;
                } else {
                    bArr = o.a(b, c);
                }
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th2) {
                    th2.printStackTrace();
                }
            } catch (Throwable th3) {
                th2 = th3;
                w.a(th2, "CInfo", "InitXInfo");
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                return bArr;
            }
        } catch (Throwable th22) {
            byteArrayOutputStream = null;
            th = th22;
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th222) {
                    th222.printStackTrace();
                }
            }
            throw th;
        }
        return bArr;
    }

    public static byte[] a(byte[] bArr) throws CertificateException, InvalidKeySpecException, NoSuchAlgorithmException, NullPointerException, IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator instance = KeyGenerator.getInstance("AES");
        if (instance == null) {
            return null;
        }
        instance.init(AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY);
        byte[] encoded = instance.generateKey().getEncoded();
        Key c = t.c();
        if (c == null) {
            return null;
        }
        Object a = o.a(encoded, c);
        Object a2 = o.a(encoded, bArr);
        Object obj = new Object[(a.length + a2.length)];
        System.arraycopy(a, 0, obj, 0, a.length);
        System.arraycopy(a2, 0, obj, a.length, a2.length);
        return obj;
    }

    public static String b(byte[] bArr) {
        try {
            return d(bArr);
        } catch (Throwable th) {
            w.a(th, "CInfo", "AESData");
            return StringUtils.EMPTY_STRING;
        }
    }

    public static String c(byte[] bArr) {
        try {
            return d(bArr);
        } catch (Throwable th) {
            th.printStackTrace();
            return StringUtils.EMPTY_STRING;
        }
    }

    private static String d(byte[] bArr) throws InvalidKeyException, IOException, InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, CertificateException {
        byte[] b = t.b(a(bArr));
        return b != null ? o.a(b) : StringUtils.EMPTY_STRING;
    }
}
