package com.google.android.gms.internal;

import java.nio.charset.Charset;
import java.util.Arrays;

public final class aid {
    private static Charset ISO_8859_1;
    protected static final Charset UTF_8;
    public static final Object zzcve;

    static {
        UTF_8 = Charset.forName("UTF-8");
        ISO_8859_1 = Charset.forName("ISO-8859-1");
        zzcve = new Object();
    }

    public static boolean equals(float[] fArr, float[] fArr2) {
        return (fArr == null || fArr.length == 0) ? fArr2 == null || fArr2.length == 0 : Arrays.equals(fArr, fArr2);
    }

    public static boolean equals(int[] iArr, int[] iArr2) {
        return (iArr == null || iArr.length == 0) ? iArr2 == null || iArr2.length == 0 : Arrays.equals(iArr, iArr2);
    }

    public static boolean equals(long[] jArr, long[] jArr2) {
        return (jArr == null || jArr.length == 0) ? jArr2 == null || jArr2.length == 0 : Arrays.equals(jArr, jArr2);
    }

    public static boolean equals(Object[] objArr, Object[] objArr2) {
        if (objArr == null) {
            boolean z = false;
        } else {
            int length = objArr.length;
        }
        int length2 = objArr2 == null ? 0 : objArr2.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            if (r6 >= z || objArr[r6] != null) {
                boolean z2;
                boolean z3;
                int i3 = i;
                while (i3 < length2 && objArr2[i3] == null) {
                    i3++;
                }
                if (r6 >= z) {
                    int i4 = 1;
                } else {
                    z2 = false;
                }
                if (i3 >= length2) {
                    i = 1;
                } else {
                    z3 = false;
                }
                if (z2 && z3) {
                    return true;
                }
                if (z2 != z3 || !objArr[r6].equals(objArr2[i3])) {
                    return false;
                }
                i = i3 + 1;
                i2 = r6 + 1;
            } else {
                i2 = r6 + 1;
            }
        }
    }

    public static boolean equals(boolean[] zArr, boolean[] zArr2) {
        return (zArr == null || zArr.length == 0) ? zArr2 == null || zArr2.length == 0 : Arrays.equals(zArr, zArr2);
    }

    public static int hashCode(float[] fArr) {
        return (fArr == null || fArr.length == 0) ? 0 : Arrays.hashCode(fArr);
    }

    public static int hashCode(int[] iArr) {
        return (iArr == null || iArr.length == 0) ? 0 : Arrays.hashCode(iArr);
    }

    public static int hashCode(long[] jArr) {
        return (jArr == null || jArr.length == 0) ? 0 : Arrays.hashCode(jArr);
    }

    public static int hashCode(Object[] objArr) {
        int i = 0;
        int length = objArr == null ? 0 : objArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            Object obj = objArr[i2];
            if (obj != null) {
                i = (i * 31) + obj.hashCode();
            }
        }
        return i;
    }

    public static int hashCode(boolean[] zArr) {
        return (zArr == null || zArr.length == 0) ? 0 : Arrays.hashCode(zArr);
    }

    public static void zza(ahz com_google_android_gms_internal_ahz, ahz com_google_android_gms_internal_ahz2) {
        if (com_google_android_gms_internal_ahz.zzcuW != null) {
            com_google_android_gms_internal_ahz2.zzcuW = (aib) com_google_android_gms_internal_ahz.zzcuW.clone();
        }
    }

    public static boolean zza(byte[][] bArr, byte[][] bArr2) {
        if (bArr == null) {
            boolean z = false;
        } else {
            int length = bArr.length;
        }
        int length2 = bArr2 == null ? 0 : bArr2.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            if (r6 >= z || bArr[r6] != null) {
                boolean z2;
                boolean z3;
                int i3 = i;
                while (i3 < length2 && bArr2[i3] == null) {
                    i3++;
                }
                if (r6 >= z) {
                    int i4 = 1;
                } else {
                    z2 = false;
                }
                if (i3 >= length2) {
                    i = 1;
                } else {
                    z3 = false;
                }
                if (z2 && z3) {
                    return true;
                }
                if (z2 != z3 || !Arrays.equals(bArr[r6], bArr2[i3])) {
                    return false;
                }
                i = i3 + 1;
                i2 = r6 + 1;
            } else {
                i2 = r6 + 1;
            }
        }
    }

    public static int zzc(byte[][] bArr) {
        int i = 0;
        int length = bArr == null ? 0 : bArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            byte[] bArr2 = bArr[i2];
            if (bArr2 != null) {
                i = (i * 31) + Arrays.hashCode(bArr2);
            }
        }
        return i;
    }
}
