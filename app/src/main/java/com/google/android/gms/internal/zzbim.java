package com.google.android.gms.internal;

import android.content.Context;

public final class zzbim {
    private static zzbim zzaKo;
    private zzbil zzaKn;

    static {
        zzaKo = new zzbim();
    }

    public zzbim() {
        this.zzaKn = null;
    }

    private final synchronized zzbil zzaO(Context context) {
        if (this.zzaKn == null) {
            if (context.getApplicationContext() != null) {
                context = context.getApplicationContext();
            }
            this.zzaKn = new zzbil(context);
        }
        return this.zzaKn;
    }

    public static zzbil zzaP(Context context) {
        return zzaKo.zzaO(context);
    }
}
