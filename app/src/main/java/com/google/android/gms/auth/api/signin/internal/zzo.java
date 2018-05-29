package com.google.android.gms.auth.api.signin.internal;

public final class zzo {
    private static int zzamu;
    private int zzamv;

    static {
        zzamu = 31;
    }

    public zzo() {
        this.zzamv = 1;
    }

    public final zzo zzP(boolean z) {
        this.zzamv = (z ? 1 : 0) + (this.zzamv * zzamu);
        return this;
    }

    public final int zzmH() {
        return this.zzamv;
    }

    public final zzo zzo(Object obj) {
        this.zzamv = (obj == null ? 0 : obj.hashCode()) + (this.zzamv * zzamu);
        return this;
    }
}
