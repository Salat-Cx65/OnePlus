package com.google.android.gms.internal;

import java.io.IOException;

public abstract class aif {
    protected volatile int zzcvf;

    public aif() {
        this.zzcvf = -1;
    }

    public static final <T extends aif> T zza(T t, byte[] bArr) throws aie {
        return zza(t, bArr, 0, bArr.length);
    }

    private static <T extends aif> T zza(T t, byte[] bArr, int i, int i2) throws aie {
        try {
            ahw zzb = ahw.zzb(bArr, 0, i2);
            t.zza(zzb);
            zzb.zzck(0);
            return t;
        } catch (aie e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
    }

    public static final byte[] zzd(aif com_google_android_gms_internal_aif) {
        byte[] bArr = new byte[com_google_android_gms_internal_aif.zzMl()];
        try {
            ahx zzc = ahx.zzc(bArr, 0, bArr.length);
            com_google_android_gms_internal_aif.zza(zzc);
            zzc.zzMc();
            return bArr;
        } catch (Throwable e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzMe();
    }

    public String toString() {
        return aig.zze(this);
    }

    public aif zzMe() throws CloneNotSupportedException {
        return (aif) super.clone();
    }

    public final int zzMk() {
        if (this.zzcvf < 0) {
            zzMl();
        }
        return this.zzcvf;
    }

    public final int zzMl() {
        int zzn = zzn();
        this.zzcvf = zzn;
        return zzn;
    }

    public abstract aif zza(ahw com_google_android_gms_internal_ahw) throws IOException;

    public void zza(ahx com_google_android_gms_internal_ahx) throws IOException {
    }

    protected int zzn() {
        return 0;
    }
}
