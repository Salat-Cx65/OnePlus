package com.loc;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import android.util.Log;
import com.loc.s.a;
import com.oneplus.custom.utils.BuildConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import net.oneplus.weather.R;
import net.oneplus.weather.util.StringUtils;
import org.json.JSONObject;

// compiled from: Utils.java
public final class t {
    static String a;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 80; i++) {
            stringBuilder.append("=");
        }
        a = stringBuilder.toString();
    }

    public static s a() throws j {
        return new a("collection", BuildConfig.VERSION_NAME, "AMap_collection_1.0").a(new String[]{"com.amap.api.collection"}).a();
    }

    public static String a(long j) {
        try {
            return new SimpleDateFormat("yyyyMMdd HH:mm:ss:SSS", Locale.CHINA).format(new Date(j));
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static String a(Throwable th) {
        Throwable cause;
        Throwable th2;
        String str = null;
        try {
            PrintWriter printWriter;
            Writer stringWriter = new StringWriter();
            try {
                printWriter = new PrintWriter(stringWriter);
                try {
                    th.printStackTrace(printWriter);
                    for (cause = th.getCause(); cause != null; cause = cause.getCause()) {
                        cause.printStackTrace(printWriter);
                    }
                    str = stringWriter.toString();
                    if (stringWriter != null) {
                        try {
                            stringWriter.close();
                        } catch (Throwable cause2) {
                            cause2.printStackTrace();
                        }
                    }
                } catch (Throwable th3) {
                    cause2 = th3;
                    cause2.printStackTrace();
                    if (stringWriter != null) {
                        stringWriter.close();
                    }
                    if (printWriter != null) {
                        printWriter.close();
                    }
                    return str;
                }
            } catch (Throwable cause22) {
                printWriter = null;
                th2 = cause22;
                if (stringWriter != null) {
                    stringWriter.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
                throw th2;
            }
            if (printWriter != null) {
                try {
                    printWriter.close();
                } catch (Throwable th4) {
                    cause22 = th4;
                    cause22.printStackTrace();
                    return str;
                }
            }
        } catch (Throwable cause222) {
            printWriter = null;
            stringWriter = null;
            th2 = cause222;
            if (stringWriter != null) {
                try {
                    stringWriter.close();
                } catch (Throwable cause2222) {
                    cause2222.printStackTrace();
                }
            }
            if (printWriter != null) {
                try {
                    printWriter.close();
                } catch (Throwable cause22222) {
                    cause22222.printStackTrace();
                }
            }
            throw th2;
        }
        return str;
    }

    public static String a(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            int i = 1;
            for (Entry entry : map.entrySet()) {
                Object obj;
                if (obj != null) {
                    obj = null;
                    stringBuffer.append((String) entry.getKey()).append("=").append((String) entry.getValue());
                } else {
                    stringBuffer.append("&").append((String) entry.getKey()).append("=").append((String) entry.getValue());
                }
            }
        } catch (Throwable th) {
            w.a(th, "Utils", "assembleParams");
        }
        return stringBuffer.toString();
    }

    public static String a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return StringUtils.EMPTY_STRING;
        }
        try {
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new String(bArr);
        }
    }

    public static void a(Context context, String str, String str2, JSONObject jSONObject) {
        Object obj = StringUtils.EMPTY_STRING;
        String e = k.e(context);
        String b = p.b(e);
        Object obj2 = StringUtils.EMPTY_STRING;
        String str3 = StringUtils.EMPTY_STRING;
        String str4 = StringUtils.EMPTY_STRING;
        String a = k.a(context);
        String str5 = StringUtils.EMPTY_STRING;
        try {
            if (jSONObject.has("info")) {
                obj = jSONObject.getString("info");
                str4 = new StringBuilder("\u8bf7\u5728\u9ad8\u5fb7\u5f00\u653e\u5e73\u53f0\u5b98\u7f51\u4e2d\u641c\u7d22\"").append(obj).append("\"\u76f8\u5173\u5185\u5bb9\u8fdb\u884c\u89e3\u51b3").toString();
            }
            if ("INVALID_USER_SCODE".equals(obj)) {
                if (jSONObject.has("sec_code")) {
                    String string = jSONObject.getString("sec_code");
                }
                if (jSONObject.has("sec_code_debug")) {
                    Object string2 = jSONObject.getString("sec_code_debug");
                } else {
                    str5 = str3;
                }
                if (b.equals(obj2) || b.equals(r1)) {
                    str4 = "\u8bf7\u5728\u9ad8\u5fb7\u5f00\u653e\u5e73\u53f0\u5b98\u7f51\u4e2d\u641c\u7d22\"\u8bf7\u6c42\u5185\u5bb9\u8fc7\u957f\u5bfc\u81f4\u4e1a\u52a1\u8c03\u7528\u5931\u8d25\"\u76f8\u5173\u5185\u5bb9\u8fdb\u884c\u89e3\u51b3";
                }
            } else if ("INVALID_USER_KEY".equals(obj)) {
                if (jSONObject.has("key")) {
                    str5 = jSONObject.getString("key");
                }
                if (str5.length() > 0 && !a.equals(str5)) {
                    str4 = "\u8bf7\u5728\u9ad8\u5fb7\u5f00\u653e\u5e73\u53f0\u5b98\u7f51\u4e0a\u53d1\u8d77\u6280\u672f\u54a8\u8be2\u5de5\u5355\u2014>\u8d26\u53f7\u4e0eKey\u95ee\u9898\uff0c\u54a8\u8be2INVALID_USER_KEY\u5982\u4f55\u89e3\u51b3";
                }
            }
        } catch (Throwable th) {
        }
        Log.i("authErrLog", a);
        Log.i("authErrLog", "                                   \u9274\u6743\u9519\u8bef\u4fe1\u606f                                  ");
        Log.i("authErrLog", a);
        e(new StringBuilder("SHA1Package:").append(e).toString());
        e(new StringBuilder("key:").append(a).toString());
        e(new StringBuilder("csid:").append(str).toString());
        e(new StringBuilder("gsid:").append(str2).toString());
        e(new StringBuilder("json:").append(jSONObject.toString()).toString());
        Log.i("authErrLog", "                                                                               ");
        Log.i("authErrLog", str4);
        Log.i("authErrLog", a);
    }

    public static void a(ByteArrayOutputStream byteArrayOutputStream, byte b, byte[] bArr) {
        try {
            byteArrayOutputStream.write(new byte[]{b});
            int i = b & 255;
            if (i < 255 && i > 0) {
                byteArrayOutputStream.write(bArr);
            } else if (i == 255) {
                byteArrayOutputStream.write(bArr, 0, MotionEventCompat.ACTION_MASK);
            }
        } catch (Throwable e) {
            w.a(e, "Utils", "writeField");
        }
    }

    public static void a(ByteArrayOutputStream byteArrayOutputStream, String str) {
        int i = MotionEventCompat.ACTION_MASK;
        if (TextUtils.isEmpty(str)) {
            try {
                byteArrayOutputStream.write(new byte[]{(byte) 0});
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int length = str.length();
        if (length <= 255) {
            i = length;
        }
        a(byteArrayOutputStream, (byte) i, a(str));
    }

    public static boolean a(JSONObject jSONObject, String str) {
        return jSONObject != null && jSONObject.has(str);
    }

    public static byte[] a(String str) {
        if (TextUtils.isEmpty(str)) {
            return new byte[0];
        }
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return str.getBytes();
        }
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        String b = o.b(a(str));
        String str2 = StringUtils.EMPTY_STRING;
        try {
            return ((char) ((b.length() % 26) + 65)) + b;
        } catch (Throwable th) {
            th.printStackTrace();
            return str2;
        }
    }

    public static String b(Map<String, String> map) {
        String toString;
        if (map != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Entry entry : map.entrySet()) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append((String) entry.getKey());
                stringBuilder.append("=");
                stringBuilder.append((String) entry.getValue());
            }
            toString = stringBuilder.toString();
        } else {
            toString = null;
        }
        return d(toString);
    }

    public static byte[] b() {
        int i = 0;
        try {
            String[] split = new StringBuffer("16,16,18,77,15,911,121,77,121,911,38,77,911,99,86,67,611,96,48,77,84,911,38,67,021,301,86,67,611,98,48,77,511,77,48,97,511,58,48,97,511,84,501,87,511,96,48,77,221,911,38,77,121,37,86,67,25,301,86,67,021,96,86,67,021,701,86,67,35,56,86,67,611,37,221,87").reverse().toString().split(",");
            byte[] bArr = new byte[split.length];
            for (int i2 = 0; i2 < split.length; i2++) {
                bArr[i2] = Byte.parseByte(split[i2]);
            }
            split = new StringBuffer(new String(o.b(new String(bArr)))).reverse().toString().split(",");
            byte[] bArr2 = new byte[split.length];
            while (i < split.length) {
                bArr2[i] = Byte.parseByte(split[i]);
                i++;
            }
            return bArr2;
        } catch (Throwable th) {
            w.a(th, "Utils", "getIV");
            return new byte[16];
        }
    }

    public static byte[] b(byte[] bArr) {
        try {
            return g(bArr);
        } catch (Throwable th) {
            w.a(th, "Utils", "gZip");
            return new byte[0];
        }
    }

    public static String c(String str) {
        return str.length() < 2 ? StringUtils.EMPTY_STRING : o.a(str.substring(1));
    }

    static PublicKey c() throws CertificateException, InvalidKeySpecException, NoSuchAlgorithmException, NullPointerException, IOException {
        Throwable th;
        PublicKey publicKey = null;
        try {
            InputStream byteArrayInputStream = new ByteArrayInputStream(o.b("MIICnjCCAgegAwIBAgIJAJ0Pdzos7ZfYMA0GCSqGSIb3DQEBBQUAMGgxCzAJBgNVBAYTAkNOMRMwEQYDVQQIDApTb21lLVN0YXRlMRAwDgYDVQQHDAdCZWlqaW5nMREwDwYDVQQKDAhBdXRvbmF2aTEfMB0GA1UEAwwWY29tLmF1dG9uYXZpLmFwaXNlcnZlcjAeFw0xMzA4MTUwNzU2NTVaFw0yMzA4MTMwNzU2NTVaMGgxCzAJBgNVBAYTAkNOMRMwEQYDVQQIDApTb21lLVN0YXRlMRAwDgYDVQQHDAdCZWlqaW5nMREwDwYDVQQKDAhBdXRvbmF2aTEfMB0GA1UEAwwWY29tLmF1dG9uYXZpLmFwaXNlcnZlcjCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA8eWAyHbFPoFPfdx5AD+D4nYFq4dbJ1p7SIKt19Oz1oivF/6H43v5Fo7s50pD1UF8+Qu4JoUQxlAgOt8OCyQ8DYdkaeB74XKb1wxkIYg/foUwN1CMHPZ9O9ehgna6K4EJXZxR7Y7XVZnbjHZIVn3VpPU/Rdr2v37LjTw+qrABJxMCAwEAAaNQME4wHQYDVR0OBBYEFOM/MLGP8xpVFuVd+3qZkw7uBvOTMB8GA1UdIwQYMBaAFOM/MLGP8xpVFuVd+3qZkw7uBvOTMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEFBQADgYEA4LY3g8aAD8JkxAOqUXDDyLuCCGOc2pTIhn0TwMNaVdH4hZlpTeC/wuRD5LJ0z3j+IQ0vLvuQA5uDjVyEOlBrvVIGwSem/1XGUo13DfzgAJ5k1161S5l+sFUo5TxpHOXr8Z5nqJMjieXmhnE/I99GFyHpQmw4cC6rhYUhdhtg+Zk="));
            try {
                CertificateFactory instance = CertificateFactory.getInstance("X.509");
                KeyFactory instance2 = KeyFactory.getInstance("RSA");
                Certificate generateCertificate = instance.generateCertificate(byteArrayInputStream);
            } catch (Throwable th2) {
                th = th2;
                th.printStackTrace();
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
                return publicKey;
            }
            if (generateCertificate == null || instance2 == null) {
                if (byteArrayInputStream != null) {
                    try {
                        byteArrayInputStream.close();
                    } catch (Throwable th3) {
                        th = th3;
                        th.printStackTrace();
                        return publicKey;
                    }
                }
                return publicKey;
            }
            publicKey = instance2.generatePublic(new X509EncodedKeySpec(generateCertificate.getPublicKey().getEncoded()));
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (Throwable th4) {
                    th = th4;
                    th.printStackTrace();
                    return publicKey;
                }
            }
            return publicKey;
        } catch (Throwable th5) {
            byteArrayInputStream = null;
            th = th5;
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (Throwable th52) {
                    th52.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static byte[] c(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable th;
        Throwable th2;
        String str;
        String str2;
        byte[] bArr2 = null;
        if (!(bArr == null || bArr.length == 0)) {
            ZipOutputStream zipOutputStream;
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
                    try {
                        zipOutputStream.putNextEntry(new ZipEntry("log"));
                        zipOutputStream.write(bArr);
                        zipOutputStream.closeEntry();
                        zipOutputStream.finish();
                        bArr2 = byteArrayOutputStream.toByteArray();
                        if (zipOutputStream != null) {
                            try {
                                zipOutputStream.close();
                            } catch (Throwable th3) {
                                w.a(th3, "Utils", "zip1");
                            }
                        }
                    } catch (Throwable th4) {
                        th3 = th4;
                        w.a(th3, "Utils", "zip");
                        if (zipOutputStream != null) {
                            zipOutputStream.close();
                        }
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                        return bArr2;
                    }
                } catch (Throwable th32) {
                    zipOutputStream = null;
                    th2 = th32;
                    if (zipOutputStream != null) {
                        zipOutputStream.close();
                    }
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    throw th2;
                }
            } catch (Throwable th322) {
                byteArrayOutputStream = null;
                zipOutputStream = null;
                th2 = th322;
                if (zipOutputStream != null) {
                    try {
                        zipOutputStream.close();
                    } catch (Throwable th3222) {
                        w.a(th3222, "Utils", "zip1");
                    }
                }
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable th32222) {
                        w.a(th32222, "Utils", "zip2");
                    }
                }
                throw th2;
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th5) {
                    th32222 = th5;
                    str = "Utils";
                    str2 = "zip2";
                    w.a(th32222, str, str2);
                    return bArr2;
                }
            }
        }
        return bArr2;
    }

    private static String d(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return StringUtils.EMPTY_STRING;
            }
            String[] split = str.split("&");
            Arrays.sort(split);
            StringBuffer stringBuffer = new StringBuffer();
            for (String str2 : split) {
                stringBuffer.append(str2);
                stringBuffer.append("&");
            }
            String toString = stringBuffer.toString();
            if (toString.length() > 1) {
                return (String) toString.subSequence(0, toString.length() - 1);
            }
            return str;
        } catch (Throwable th) {
            w.a(th, "Utils", "sortParams");
        }
    }

    static String d(byte[] bArr) {
        try {
            return f(bArr);
        } catch (Throwable th) {
            w.a(th, "Utils", "HexString");
            return null;
        }
    }

    static String e(byte[] bArr) {
        try {
            return f(bArr);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private static void e(String str) {
        int i = 0;
        while (str.length() >= 78) {
            String str2 = "authErrLog";
            Log.i(str2, new StringBuilder("|").append(str.substring(0, R.styleable.AppCompatTheme_listPreferredItemPaddingRight)).append("|").toString());
            str = str.substring(R.styleable.AppCompatTheme_listPreferredItemPaddingRight);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("|").append(str);
        while (i < 78 - str.length()) {
            stringBuilder.append(" ");
            i++;
        }
        stringBuilder.append("|");
        Log.i("authErrLog", stringBuilder.toString());
    }

    public static String f(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        if (bArr == null) {
            return null;
        }
        for (byte b : bArr) {
            String toHexString = Integer.toHexString(b & 255);
            if (toHexString.length() == 1) {
                toHexString = new StringBuilder("0").append(toHexString).toString();
            }
            stringBuilder.append(toHexString);
        }
        return stringBuilder.toString();
    }

    private static byte[] g(byte[] bArr) throws IOException, Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        GZIPOutputStream gZIPOutputStream;
        Throwable th;
        Throwable th2;
        byte[] bArr2 = null;
        if (bArr != null) {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                    try {
                        gZIPOutputStream.write(bArr);
                        gZIPOutputStream.finish();
                        bArr2 = byteArrayOutputStream.toByteArray();
                        if (gZIPOutputStream != null) {
                            gZIPOutputStream.close();
                        }
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        throw th;
                    }
                } catch (Throwable th4) {
                    th2 = th4;
                    gZIPOutputStream = null;
                    th = th2;
                    if (gZIPOutputStream != null) {
                        gZIPOutputStream.close();
                    }
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    throw th;
                }
            } catch (Throwable th42) {
                byteArrayOutputStream = null;
                th = th42;
                gZIPOutputStream = null;
                if (gZIPOutputStream != null) {
                    gZIPOutputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                throw th;
            }
        }
        return bArr2;
    }
}
