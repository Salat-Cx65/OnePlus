package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbr;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public final class zzbfm {
    private final Set<zzbfi<?>> zzauD;

    public zzbfm() {
        this.zzauD = Collections.newSetFromMap(new WeakHashMap());
    }

    public static <L> zzbfk<L> zza(@NonNull L l, @NonNull String str) {
        zzbr.zzb((Object) l, (Object) "Listener must not be null");
        zzbr.zzb((Object) str, (Object) "Listener type must not be null");
        zzbr.zzh(str, "Listener type must not be empty");
        return new zzbfk(l, str);
    }

    public static <L> zzbfi<L> zzb(@NonNull L l, @NonNull Looper looper, @NonNull String str) {
        zzbr.zzb((Object) l, (Object) "Listener must not be null");
        zzbr.zzb((Object) looper, (Object) "Looper must not be null");
        zzbr.zzb((Object) str, (Object) "Listener type must not be null");
        return new zzbfi(looper, l, str);
    }

    public final void release() {
        for (zzbfi com_google_android_gms_internal_zzbfi : this.zzauD) {
            com_google_android_gms_internal_zzbfi.clear();
        }
        this.zzauD.clear();
    }

    public final <L> zzbfi<L> zza(@NonNull L l, @NonNull Looper looper, @NonNull String str) {
        zzbfi<L> zzb = zzb(l, looper, str);
        this.zzauD.add(zzb);
        return zzb;
    }
}
