package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbr;

public final class zzcfn {
    public static Looper zzb(@Nullable Looper looper) {
        return looper != null ? looper : zzwc();
    }

    public static Looper zzwc() {
        zzbr.zza(Looper.myLooper() != null, (Object) "Can't create handler inside thread that has not called Looper.prepare()");
        return Looper.myLooper();
    }
}
