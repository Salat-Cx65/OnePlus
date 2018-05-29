package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class aic implements Cloneable {
    private Object value;
    private aia<?, ?> zzcvc;
    private List<aii> zzcvd;

    aic() {
        this.zzcvd = new ArrayList();
    }

    private final byte[] toByteArray() throws IOException {
        byte[] bArr = new byte[zzn()];
        zza(ahx.zzJ(bArr));
        return bArr;
    }

    private aic zzMf() {
        aic com_google_android_gms_internal_aic = new aic();
        try {
            com_google_android_gms_internal_aic.zzcvc = this.zzcvc;
            if (this.zzcvd == null) {
                com_google_android_gms_internal_aic.zzcvd = null;
            } else {
                com_google_android_gms_internal_aic.zzcvd.addAll(this.zzcvd);
            }
            if (this.value != null) {
                if (this.value instanceof aif) {
                    com_google_android_gms_internal_aic.value = (aif) ((aif) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    com_google_android_gms_internal_aic.value = ((byte[]) this.value).clone();
                } else if (this.value instanceof byte[][]) {
                    byte[][] bArr = (byte[][]) this.value;
                    r4 = new Object[bArr.length];
                    com_google_android_gms_internal_aic.value = r4;
                    for (r2 = 0; r2 < bArr.length; r2++) {
                        r4[r2] = (byte[]) bArr[r2].clone();
                    }
                } else if (this.value instanceof boolean[]) {
                    com_google_android_gms_internal_aic.value = ((boolean[]) this.value).clone();
                } else if (this.value instanceof int[]) {
                    com_google_android_gms_internal_aic.value = ((int[]) this.value).clone();
                } else if (this.value instanceof long[]) {
                    com_google_android_gms_internal_aic.value = ((long[]) this.value).clone();
                } else if (this.value instanceof float[]) {
                    com_google_android_gms_internal_aic.value = ((float[]) this.value).clone();
                } else if (this.value instanceof double[]) {
                    com_google_android_gms_internal_aic.value = ((double[]) this.value).clone();
                } else if (this.value instanceof aif[]) {
                    aif[] com_google_android_gms_internal_aifArr = (aif[]) this.value;
                    r4 = new Object[com_google_android_gms_internal_aifArr.length];
                    com_google_android_gms_internal_aic.value = r4;
                    for (r2 = 0; r2 < com_google_android_gms_internal_aifArr.length; r2++) {
                        r4[r2] = (aif) com_google_android_gms_internal_aifArr[r2].clone();
                    }
                }
            }
            return com_google_android_gms_internal_aic;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzMf();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aic)) {
            return false;
        }
        aic com_google_android_gms_internal_aic = (aic) obj;
        if (this.value != null && com_google_android_gms_internal_aic.value != null) {
            return this.zzcvc == com_google_android_gms_internal_aic.zzcvc ? !this.zzcvc.zzcmA.isArray() ? this.value.equals(com_google_android_gms_internal_aic.value) : this.value instanceof byte[] ? Arrays.equals((byte[]) this.value, (byte[]) com_google_android_gms_internal_aic.value) : this.value instanceof int[] ? Arrays.equals((int[]) this.value, (int[]) com_google_android_gms_internal_aic.value) : this.value instanceof long[] ? Arrays.equals((long[]) this.value, (long[]) com_google_android_gms_internal_aic.value) : this.value instanceof float[] ? Arrays.equals((float[]) this.value, (float[]) com_google_android_gms_internal_aic.value) : this.value instanceof double[] ? Arrays.equals((double[]) this.value, (double[]) com_google_android_gms_internal_aic.value) : this.value instanceof boolean[] ? Arrays.equals((boolean[]) this.value, (boolean[]) com_google_android_gms_internal_aic.value) : Arrays.deepEquals((Object[]) this.value, (Object[]) com_google_android_gms_internal_aic.value) : false;
        } else {
            if (this.zzcvd != null && com_google_android_gms_internal_aic.zzcvd != null) {
                return this.zzcvd.equals(com_google_android_gms_internal_aic.zzcvd);
            }
            try {
                return Arrays.equals(toByteArray(), com_google_android_gms_internal_aic.toByteArray());
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public final int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    final void zza(ahx com_google_android_gms_internal_ahx) throws IOException {
        if (this.value != null) {
            this.zzcvc.zza(this.value, com_google_android_gms_internal_ahx);
            return;
        }
        for (aii com_google_android_gms_internal_aii : this.zzcvd) {
            com_google_android_gms_internal_ahx.zzct(com_google_android_gms_internal_aii.tag);
            com_google_android_gms_internal_ahx.zzL(com_google_android_gms_internal_aii.zzbww);
        }
    }

    final void zza(aii com_google_android_gms_internal_aii) {
        this.zzcvd.add(com_google_android_gms_internal_aii);
    }

    final <T> T zzb(aia<?, T> com_google_android_gms_internal_aia___T) {
        if (this.value == null) {
            this.zzcvc = com_google_android_gms_internal_aia___T;
            this.value = com_google_android_gms_internal_aia___T.zzY(this.zzcvd);
            this.zzcvd = null;
        } else if (!this.zzcvc.equals(com_google_android_gms_internal_aia___T)) {
            throw new IllegalStateException("Tried to getExtension with a different Extension.");
        }
        return this.value;
    }

    final int zzn() {
        if (this.value != null) {
            return this.zzcvc.zzav(this.value);
        }
        int i = 0;
        for (aii com_google_android_gms_internal_aii : this.zzcvd) {
            i = (com_google_android_gms_internal_aii.zzbww.length + (ahx.zzcu(com_google_android_gms_internal_aii.tag) + 0)) + i;
        }
        return i;
    }
}
