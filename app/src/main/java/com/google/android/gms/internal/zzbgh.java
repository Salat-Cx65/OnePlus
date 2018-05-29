package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class zzbgh {
    public static final Status zzaFl;
    private static final zzbcq<?>[] zzaFm;
    private final Map<zzc<?>, zze> zzaDH;
    final Set<zzbcq<?>> zzaFn;
    private final zzbgj zzaFo;

    static {
        zzaFl = new Status(8, "The connection to Google Play services was lost");
        zzaFm = new zzbcq[0];
    }

    public zzbgh(Map<zzc<?>, zze> map) {
        this.zzaFn = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
        this.zzaFo = new zzbgi(this);
        this.zzaDH = map;
    }

    public final void release() {
        for (zzbcq com_google_android_gms_internal_zzbcq : (zzbcq[]) this.zzaFn.toArray(zzaFm)) {
            com_google_android_gms_internal_zzbcq.zza(null);
            com_google_android_gms_internal_zzbcq.zzpm();
            if (com_google_android_gms_internal_zzbcq.zzpz()) {
                this.zzaFn.remove(com_google_android_gms_internal_zzbcq);
            }
        }
    }

    final void zzb(zzbcq<? extends Result> com_google_android_gms_internal_zzbcq__extends_com_google_android_gms_common_api_Result) {
        this.zzaFn.add(com_google_android_gms_internal_zzbcq__extends_com_google_android_gms_common_api_Result);
        com_google_android_gms_internal_zzbcq__extends_com_google_android_gms_common_api_Result.zza(this.zzaFo);
    }

    public final void zzqK() {
        for (zzbcq com_google_android_gms_internal_zzbcq : (zzbcq[]) this.zzaFn.toArray(zzaFm)) {
            com_google_android_gms_internal_zzbcq.zzs(zzaFl);
        }
    }
}
