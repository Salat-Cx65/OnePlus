package com.google.android.gms.internal;

import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Iterator;
import java.util.Set;

public final class zzbch {
    private final ArrayMap<zzbcf<?>, ConnectionResult> zzaAD;
    private final TaskCompletionSource<Void> zzaBI;
    private int zzaBJ;
    private boolean zzaBK;

    public zzbch(Iterable<? extends GoogleApi<?>> iterable) {
        this.zzaBI = new TaskCompletionSource();
        this.zzaBK = false;
        this.zzaAD = new ArrayMap();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            this.zzaAD.put(r0.zzpf(), null);
        }
        this.zzaBJ = this.zzaAD.keySet().size();
    }

    public final Task<Void> getTask() {
        return this.zzaBI.getTask();
    }

    public final void zza(zzbcf<?> com_google_android_gms_internal_zzbcf_, ConnectionResult connectionResult) {
        this.zzaAD.put(com_google_android_gms_internal_zzbcf_, connectionResult);
        this.zzaBJ--;
        if (!connectionResult.isSuccess()) {
            this.zzaBK = true;
        }
        if (this.zzaBJ != 0) {
            return;
        }
        if (this.zzaBK) {
            this.zzaBI.setException(new zza(this.zzaAD));
            return;
        }
        this.zzaBI.setResult(null);
    }

    public final Set<zzbcf<?>> zzpr() {
        return this.zzaAD.keySet();
    }

    public final void zzps() {
        this.zzaBI.setResult(null);
    }
}
