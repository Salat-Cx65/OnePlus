package com.loc;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.gms.common.ConnectionResult;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

// compiled from: Encrypt.java
public final class cj {
    private static final char[] a;
    private static final byte[] b;
    private static final IvParameterSpec c;

    static {
        a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        b = new byte[]{(byte) 0, (byte) 1, (byte) 1, (byte) 2, (byte) 3, (byte) 5, (byte) 8, (byte) 13, (byte) 8, (byte) 7, (byte) 6, (byte) 5, (byte) 4, (byte) 3, (byte) 2, (byte) 1};
        c = new IvParameterSpec(b);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r4) {
        throw new UnsupportedOperationException("Method not decompiled: com.loc.cj.a(java.lang.String):java.lang.String");
        /*
        r0 = 0;
        if (r4 == 0) goto L_0x0009;
    L_0x0003:
        r1 = r4.length();	 Catch:{ Throwable -> 0x0028 }
        if (r1 != 0) goto L_0x000a;
    L_0x0009:
        return r0;
    L_0x000a:
        r1 = "SHA1";
        r1 = a(r1, r4);	 Catch:{ Throwable -> 0x0028 }
        r2 = "MD5";
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0028 }
        r3.<init>();	 Catch:{ Throwable -> 0x0028 }
        r1 = r3.append(r1);	 Catch:{ Throwable -> 0x0028 }
        r1 = r1.append(r4);	 Catch:{ Throwable -> 0x0028 }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x0028 }
        r0 = a(r2, r1);	 Catch:{ Throwable -> 0x0028 }
        goto L_0x0009;
    L_0x0028:
        r1 = move-exception;
        r2 = "Encrypt";
        r3 = "generatorKey";
        com.loc.cw.a(r1, r2, r3);
        goto L_0x0009;
        */
    }

    public static String a(String str, String str2) {
        if (str2 == null) {
            return null;
        }
        try {
            byte[] a = p.a(str2.getBytes("UTF-8"), str);
            int length = a.length;
            StringBuilder stringBuilder = new StringBuilder(length * 2);
            for (int i = 0; i < length; i++) {
                stringBuilder.append(a[(a[i] >> 4) & 15]);
                stringBuilder.append(a[a[i] & 15]);
            }
            return stringBuilder.toString();
        } catch (Throwable th) {
            cw.a(th, "Encrypt", "encode");
            return null;
        }
    }

    public static byte[] a(byte[] bArr) {
        int i = 0;
        try {
            Object obj = new Object[16];
            Object obj2 = new Object[(bArr.length - 16)];
            System.arraycopy(bArr, 0, obj, 0, ConnectionResult.API_UNAVAILABLE);
            System.arraycopy(bArr, ConnectionResult.API_UNAVAILABLE, obj2, 0, bArr.length - 16);
            Key secretKeySpec = new SecretKeySpec(obj, "AES");
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(RainSurfaceView.RAIN_LEVEL_SHOWER, secretKeySpec, new IvParameterSpec(t.b()));
            return instance.doFinal(obj2);
        } catch (Throwable th) {
            if (bArr != null) {
                i = bArr.length;
            }
            cw.a(th, "Encrypt", new StringBuilder("decryptRsponse length = ").append(i).toString());
            return null;
        }
    }

    public static synchronized byte[] a(byte[] bArr, String str) throws Exception {
        byte[] doFinal;
        int i = 0;
        synchronized (cj.class) {
            Key generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(o.b(str)));
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(1, generatePrivate);
            int length = bArr.length;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i2 = 0;
            while (length - i > 0) {
                doFinal = length - i > 245 ? instance.doFinal(bArr, i, 245) : instance.doFinal(bArr, i, length - i);
                byteArrayOutputStream.write(doFinal, 0, doFinal.length);
                i = i2 + 1;
                int i3 = i;
                i *= 245;
                i2 = i3;
            }
            doFinal = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        }
        return doFinal;
    }

    public static byte[] a(byte[] bArr, BigInteger bigInteger, BigInteger bigInteger2) {
        int i = 0;
        try {
            BigInteger modPow = new BigInteger(bArr).modPow(bigInteger, bigInteger2);
            byte[] bArr2 = new byte[16];
            BigInteger bigInteger3 = new BigInteger("256");
            while (modPow.bitCount() > 0 && i < 16) {
                BigInteger[] divideAndRemainder = modPow.divideAndRemainder(bigInteger3);
                modPow = divideAndRemainder[0];
                bArr2[i] = (byte) divideAndRemainder[1].intValue();
                i++;
            }
            return bArr2;
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) throws Exception {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(RainSurfaceView.RAIN_LEVEL_SHOWER, new SecretKeySpec(bArr, "AES"), c);
        return instance.doFinal(bArr2);
    }

    private static SecretKeySpec b(String str) {
        byte[] bArr = null;
        if (str == null) {
            str = StringUtils.EMPTY_STRING;
        }
        StringBuffer stringBuffer = new StringBuffer(16);
        stringBuffer.append(str);
        while (stringBuffer.length() < 16) {
            stringBuffer.append("0");
        }
        if (stringBuffer.length() > 16) {
            stringBuffer.setLength(ConnectionResult.API_UNAVAILABLE);
        }
        try {
            bArr = stringBuffer.toString().getBytes("UTF-8");
        } catch (Throwable th) {
            cw.a(th, "Encrypt", "createKey");
        }
        return new SecretKeySpec(bArr, "AES");
    }

    public static synchronized byte[] b(byte[] bArr, String str) throws Exception {
        byte[] doFinal;
        int i = 0;
        synchronized (cj.class) {
            Key generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(o.b(str)));
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(RainSurfaceView.RAIN_LEVEL_SHOWER, generatePrivate);
            int length = bArr.length;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i2 = 0;
            while (length - i > 0) {
                doFinal = length - i > 256 ? instance.doFinal(bArr, i, AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY) : instance.doFinal(bArr, i, length - i);
                byteArrayOutputStream.write(doFinal, 0, doFinal.length);
                i = i2 + 1;
                int i3 = i;
                i *= 256;
                i2 = i3;
            }
            doFinal = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        }
        return doFinal;
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) {
        try {
            Key secretKeySpec = new SecretKeySpec(bArr2, "AES");
            Cipher instance = Cipher.getInstance("AES/ECB/NoPadding");
            instance.init(RainSurfaceView.RAIN_LEVEL_SHOWER, secretKeySpec);
            return instance.doFinal(bArr);
        } catch (Throwable th) {
            return null;
        }
    }

    public static byte[] c(byte[] bArr, String str) {
        try {
            Key b = b(str);
            AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(t.b());
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(1, b, ivParameterSpec);
            return instance.doFinal(bArr);
        } catch (Throwable th) {
            cw.a(th, "Encrypt", "aesEncrypt");
            return null;
        }
    }

    public static byte[] d(byte[] bArr, String str) {
        try {
            Key b = b(str);
            AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(t.b());
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(RainSurfaceView.RAIN_LEVEL_SHOWER, b, ivParameterSpec);
            return instance.doFinal(bArr);
        } catch (Throwable th) {
            cw.a(th, "Encrypt", "aesDecrypt");
            return null;
        }
    }
}
