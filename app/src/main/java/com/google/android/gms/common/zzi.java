package com.google.android.gms.common;

import java.lang.ref.WeakReference;

abstract class zzi extends zzg {
    private static final WeakReference<byte[]> zzaAl;
    private WeakReference<byte[]> zzaAk;

    static {
        zzaAl = new WeakReference(null);
    }

    zzi(byte[] bArr) {
        super(bArr);
        this.zzaAk = zzaAl;
    }

    final byte[] getBytes() {
        byte[] bArr;
        synchronized (this) {
            bArr = (byte[]) this.zzaAk.get();
            if (bArr == null) {
                bArr = zzoY();
                this.zzaAk = new WeakReference(bArr);
            }
        }
        return bArr;
    }

    protected abstract byte[] zzoY();
}
