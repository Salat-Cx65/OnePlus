package com.google.android.gms.internal;

import java.io.IOException;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class ahw {
    private final byte[] buffer;
    private int zzcuM;
    private int zzcuN;
    private int zzcuO;
    private int zzcuP;
    private int zzcuQ;
    private int zzcuR;
    private int zzcuS;
    private int zzcuT;
    private int zzcuU;

    private ahw(byte[] bArr, int i, int i2) {
        this.zzcuR = Integer.MAX_VALUE;
        this.zzcuT = 64;
        this.zzcuU = 67108864;
        this.buffer = bArr;
        this.zzcuM = i;
        this.zzcuN = i + i2;
        this.zzcuP = i;
    }

    public static ahw zzI(byte[] bArr) {
        return zzb(bArr, 0, bArr.length);
    }

    private final void zzLZ() {
        this.zzcuN += this.zzcuO;
        int i = this.zzcuN;
        if (i > this.zzcuR) {
            this.zzcuO = i - this.zzcuR;
            this.zzcuN -= this.zzcuO;
            return;
        }
        this.zzcuO = 0;
    }

    private final byte zzMb() throws IOException {
        if (this.zzcuP == this.zzcuN) {
            throw aie.zzMg();
        }
        byte[] bArr = this.buffer;
        int i = this.zzcuP;
        this.zzcuP = i + 1;
        return bArr[i];
    }

    public static ahw zzb(byte[] bArr, int i, int i2) {
        return new ahw(bArr, 0, i2);
    }

    private final void zzcp(int i) throws IOException {
        if (i < 0) {
            throw aie.zzMh();
        } else if (this.zzcuP + i > this.zzcuR) {
            zzcp(this.zzcuR - this.zzcuP);
            throw aie.zzMg();
        } else if (i <= this.zzcuN - this.zzcuP) {
            this.zzcuP += i;
        } else {
            throw aie.zzMg();
        }
    }

    public final int getPosition() {
        return this.zzcuP - this.zzcuM;
    }

    public final byte[] readBytes() throws IOException {
        int zzLV = zzLV();
        if (zzLV < 0) {
            throw aie.zzMh();
        } else if (zzLV == 0) {
            return aij.zzcvs;
        } else {
            if (zzLV > this.zzcuN - this.zzcuP) {
                throw aie.zzMg();
            }
            Object obj = new Object[zzLV];
            System.arraycopy(this.buffer, this.zzcuP, obj, 0, zzLV);
            this.zzcuP = zzLV + this.zzcuP;
            return obj;
        }
    }

    public final String readString() throws IOException {
        int zzLV = zzLV();
        if (zzLV < 0) {
            throw aie.zzMh();
        } else if (zzLV > this.zzcuN - this.zzcuP) {
            throw aie.zzMg();
        } else {
            String str = new String(this.buffer, this.zzcuP, zzLV, aid.UTF_8);
            this.zzcuP = zzLV + this.zzcuP;
            return str;
        }
    }

    public final int zzLQ() throws IOException {
        if (this.zzcuP == this.zzcuN) {
            this.zzcuQ = 0;
            return 0;
        }
        this.zzcuQ = zzLV();
        if (this.zzcuQ != 0) {
            return this.zzcuQ;
        }
        throw new aie("Protocol message contained an invalid tag (zero).");
    }

    public final long zzLR() throws IOException {
        return zzLW();
    }

    public final int zzLS() throws IOException {
        return zzLV();
    }

    public final boolean zzLT() throws IOException {
        return zzLV() != 0;
    }

    public final long zzLU() throws IOException {
        long zzLW = zzLW();
        return (-(zzLW & 1)) ^ (zzLW >>> 1);
    }

    public final int zzLV() throws IOException {
        byte zzMb = zzMb();
        if (zzMb >= null) {
            return zzMb;
        }
        int i = zzMb & 127;
        byte zzMb2 = zzMb();
        if (zzMb2 >= null) {
            return i | (zzMb2 << 7);
        }
        i |= (zzMb2 & 127) << 7;
        zzMb2 = zzMb();
        if (zzMb2 >= null) {
            return i | (zzMb2 << 14);
        }
        i |= (zzMb2 & 127) << 14;
        zzMb2 = zzMb();
        if (zzMb2 >= null) {
            return i | (zzMb2 << 21);
        }
        i |= (zzMb2 & 127) << 21;
        zzMb2 = zzMb();
        i |= zzMb2 << 28;
        if (zzMb2 >= null) {
            return i;
        }
        for (int i2 = 0; i2 < 5; i2++) {
            if (zzMb() >= null) {
                return i;
            }
        }
        throw aie.zzMi();
    }

    public final long zzLW() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzMb = zzMb();
            j |= ((long) (zzMb & 127)) << i;
            if ((zzMb & 128) == 0) {
                return j;
            }
        }
        throw aie.zzMi();
    }

    public final int zzLX() throws IOException {
        return (((zzMb() & 255) | ((zzMb() & 255) << 8)) | ((zzMb() & 255) << 16)) | ((zzMb() & 255) << 24);
    }

    public final long zzLY() throws IOException {
        byte zzMb = zzMb();
        byte zzMb2 = zzMb();
        return ((((((((((long) zzMb2) & 255) << 8) | (((long) zzMb) & 255)) | ((((long) zzMb()) & 255) << 16)) | ((((long) zzMb()) & 255) << 24)) | ((((long) zzMb()) & 255) << 32)) | ((((long) zzMb()) & 255) << 40)) | ((((long) zzMb()) & 255) << 48)) | ((((long) zzMb()) & 255) << 56);
    }

    public final int zzMa() {
        if (this.zzcuR == Integer.MAX_VALUE) {
            return -1;
        }
        return this.zzcuR - this.zzcuP;
    }

    public final void zza(aif com_google_android_gms_internal_aif, int i) throws IOException {
        if (this.zzcuS >= this.zzcuT) {
            throw aie.zzMj();
        }
        this.zzcuS++;
        com_google_android_gms_internal_aif.zza(this);
        zzck((i << 3) | 4);
        this.zzcuS--;
    }

    public final void zzb(aif com_google_android_gms_internal_aif) throws IOException {
        int zzLV = zzLV();
        if (this.zzcuS >= this.zzcuT) {
            throw aie.zzMj();
        }
        zzLV = zzcm(zzLV);
        this.zzcuS++;
        com_google_android_gms_internal_aif.zza(this);
        zzck(0);
        this.zzcuS--;
        zzcn(zzLV);
    }

    public final void zzck(int i) throws aie {
        if (this.zzcuQ != i) {
            throw new aie("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final boolean zzcl(int i) throws IOException {
        switch (i & 7) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                zzLV();
                return true;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                zzLY();
                return true;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                zzcp(zzLV());
                return true;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                int zzLQ;
                do {
                    zzLQ = zzLQ();
                    if (zzLQ != 0) {
                    }
                    zzck(((i >>> 3) << 3) | 4);
                    return true;
                } while (zzcl(zzLQ));
                zzck(((i >>> 3) << 3) | 4);
                return true;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return false;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                zzLX();
                return true;
            default:
                throw new aie("Protocol message tag had invalid wire type.");
        }
    }

    public final int zzcm(int i) throws aie {
        if (i < 0) {
            throw aie.zzMh();
        }
        int i2 = this.zzcuP + i;
        int i3 = this.zzcuR;
        if (i2 > i3) {
            throw aie.zzMg();
        }
        this.zzcuR = i2;
        zzLZ();
        return i3;
    }

    public final void zzcn(int i) {
        this.zzcuR = i;
        zzLZ();
    }

    public final void zzco(int i) {
        zzq(i, this.zzcuQ);
    }

    public final byte[] zzp(int i, int i2) {
        if (i2 == 0) {
            return aij.zzcvs;
        }
        Object obj = new Object[i2];
        System.arraycopy(this.buffer, this.zzcuM + i, obj, 0, i2);
        return obj;
    }

    final void zzq(int i, int i2) {
        if (i > this.zzcuP - this.zzcuM) {
            throw new IllegalArgumentException(new StringBuilder(50).append("Position ").append(i).append(" is beyond current ").append(this.zzcuP - this.zzcuM).toString());
        } else if (i < 0) {
            throw new IllegalArgumentException(new StringBuilder(24).append("Bad position ").append(i).toString());
        } else {
            this.zzcuP = this.zzcuM + i;
            this.zzcuQ = i2;
        }
    }
}
