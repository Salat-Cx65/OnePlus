package com.google.android.gms.internal;

import java.io.IOException;

public abstract class ahz<M extends ahz<M>> extends aif {
    protected aib zzcuW;

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzMd();
    }

    public M zzMd() throws CloneNotSupportedException {
        ahz com_google_android_gms_internal_ahz = (ahz) super.zzMe();
        aid.zza(this, com_google_android_gms_internal_ahz);
        return com_google_android_gms_internal_ahz;
    }

    public /* synthetic */ aif zzMe() throws CloneNotSupportedException {
        return (ahz) clone();
    }

    public final <T> T zza(aia<M, T> com_google_android_gms_internal_aia_M__T) {
        if (this.zzcuW == null) {
            return null;
        }
        aic zzcw = this.zzcuW.zzcw(com_google_android_gms_internal_aia_M__T.tag >>> 3);
        return zzcw != null ? zzcw.zzb(com_google_android_gms_internal_aia_M__T) : null;
    }

    public void zza(ahx com_google_android_gms_internal_ahx) throws IOException {
        if (this.zzcuW != null) {
            for (int i = 0; i < this.zzcuW.size(); i++) {
                this.zzcuW.zzcx(i).zza(com_google_android_gms_internal_ahx);
            }
        }
    }

    protected final boolean zza(ahw com_google_android_gms_internal_ahw, int i) throws IOException {
        int position = com_google_android_gms_internal_ahw.getPosition();
        if (!com_google_android_gms_internal_ahw.zzcl(i)) {
            return false;
        }
        int i2 = i >>> 3;
        aii com_google_android_gms_internal_aii = new aii(i, com_google_android_gms_internal_ahw.zzp(position, com_google_android_gms_internal_ahw.getPosition() - position));
        aic com_google_android_gms_internal_aic = null;
        if (this.zzcuW == null) {
            this.zzcuW = new aib();
        } else {
            com_google_android_gms_internal_aic = this.zzcuW.zzcw(i2);
        }
        if (com_google_android_gms_internal_aic == null) {
            com_google_android_gms_internal_aic = new aic();
            this.zzcuW.zza(i2, com_google_android_gms_internal_aic);
        }
        com_google_android_gms_internal_aic.zza(com_google_android_gms_internal_aii);
        return true;
    }

    protected int zzn() {
        int i = 0;
        if (this.zzcuW == null) {
            return 0;
        }
        int i2 = 0;
        while (i < this.zzcuW.size()) {
            i2 += this.zzcuW.zzcx(i).zzn();
            i++;
        }
        return i2;
    }
}
