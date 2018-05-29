package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class aib implements Cloneable {
    private static final aic zzcuY;
    private int mSize;
    private boolean zzcuZ;
    private int[] zzcva;
    private aic[] zzcvb;

    static {
        zzcuY = new aic();
    }

    aib() {
        this(10);
    }

    private aib(int i) {
        this.zzcuZ = false;
        int idealIntArraySize = idealIntArraySize(i);
        this.zzcva = new int[idealIntArraySize];
        this.zzcvb = new aic[idealIntArraySize];
        this.mSize = 0;
    }

    private static int idealIntArraySize(int i) {
        int i2 = i << 2;
        for (int i3 = RainSurfaceView.RAIN_LEVEL_RAINSTORM; i3 < 32; i3++) {
            if (i2 <= (1 << i3) - 12) {
                i2 = (1 << i3) - 12;
                break;
            }
        }
        return i2 / 4;
    }

    private final int zzcy(int i) {
        int i2 = 0;
        int i3 = this.mSize - 1;
        while (i2 <= i3) {
            int i4 = (i2 + i3) >>> 1;
            int i5 = this.zzcva[i4];
            if (i5 < i) {
                i2 = i4 + 1;
            } else if (i5 <= i) {
                return i4;
            } else {
                i3 = i4 - 1;
            }
        }
        return i2 ^ -1;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        int i = this.mSize;
        aib com_google_android_gms_internal_aib = new aib(i);
        System.arraycopy(this.zzcva, 0, com_google_android_gms_internal_aib.zzcva, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            if (this.zzcvb[i2] != null) {
                com_google_android_gms_internal_aib.zzcvb[i2] = (aic) this.zzcvb[i2].clone();
            }
        }
        com_google_android_gms_internal_aib.mSize = i;
        return com_google_android_gms_internal_aib;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aib)) {
            return false;
        }
        aib com_google_android_gms_internal_aib = (aib) obj;
        if (this.mSize != com_google_android_gms_internal_aib.mSize) {
            return false;
        }
        int i;
        int[] iArr = this.zzcva;
        int[] iArr2 = com_google_android_gms_internal_aib.zzcva;
        int i2 = this.mSize;
        for (i = 0; i < i2; i++) {
            if (iArr[i] != iArr2[i]) {
                Object obj2 = null;
                break;
            }
        }
        boolean z = true;
        if (z) {
            aic[] com_google_android_gms_internal_aicArr = this.zzcvb;
            aic[] com_google_android_gms_internal_aicArr2 = com_google_android_gms_internal_aib.zzcvb;
            i2 = this.mSize;
            for (i = 0; i < i2; i++) {
                if (!com_google_android_gms_internal_aicArr[i].equals(com_google_android_gms_internal_aicArr2[i])) {
                    obj2 = null;
                    break;
                }
            }
            z = true;
            if (z) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = ConnectionResult.SIGN_IN_FAILED;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = (((i * 31) + this.zzcva[i2]) * 31) + this.zzcvb[i2].hashCode();
        }
        return i;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    final int size() {
        return this.mSize;
    }

    final void zza(int i, aic com_google_android_gms_internal_aic) {
        int zzcy = zzcy(i);
        if (zzcy >= 0) {
            this.zzcvb[zzcy] = com_google_android_gms_internal_aic;
            return;
        }
        zzcy ^= -1;
        if (zzcy >= this.mSize || this.zzcvb[zzcy] != zzcuY) {
            if (this.mSize >= this.zzcva.length) {
                int idealIntArraySize = idealIntArraySize(this.mSize + 1);
                Object obj = new Object[idealIntArraySize];
                Object obj2 = new Object[idealIntArraySize];
                System.arraycopy(this.zzcva, 0, obj, 0, this.zzcva.length);
                System.arraycopy(this.zzcvb, 0, obj2, 0, this.zzcvb.length);
                this.zzcva = obj;
                this.zzcvb = obj2;
            }
            if (this.mSize - zzcy != 0) {
                System.arraycopy(this.zzcva, zzcy, this.zzcva, zzcy + 1, this.mSize - zzcy);
                System.arraycopy(this.zzcvb, zzcy, this.zzcvb, zzcy + 1, this.mSize - zzcy);
            }
            this.zzcva[zzcy] = i;
            this.zzcvb[zzcy] = com_google_android_gms_internal_aic;
            this.mSize++;
            return;
        }
        this.zzcva[zzcy] = i;
        this.zzcvb[zzcy] = com_google_android_gms_internal_aic;
    }

    final aic zzcw(int i) {
        int zzcy = zzcy(i);
        return (zzcy < 0 || this.zzcvb[zzcy] == zzcuY) ? null : this.zzcvb[zzcy];
    }

    final aic zzcx(int i) {
        return this.zzcvb[i];
    }
}
