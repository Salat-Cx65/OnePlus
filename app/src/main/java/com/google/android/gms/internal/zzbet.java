package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzam;
import com.google.android.gms.common.internal.zzj;
import java.util.Set;

final class zzbet implements zzj, zzbfx {
    private final zzbcf<?> zzaAM;
    private final zze zzaCA;
    private zzam zzaDn;
    final /* synthetic */ zzben zzaEo;
    private boolean zzaEz;
    private Set<Scope> zzamg;

    public zzbet(zzben com_google_android_gms_internal_zzben, zze com_google_android_gms_common_api_Api_zze, zzbcf<?> com_google_android_gms_internal_zzbcf_) {
        this.zzaEo = com_google_android_gms_internal_zzben;
        this.zzaDn = null;
        this.zzamg = null;
        this.zzaEz = false;
        this.zzaCA = com_google_android_gms_common_api_Api_zze;
        this.zzaAM = com_google_android_gms_internal_zzbcf_;
    }

    @WorkerThread
    private final void zzqx() {
        if (this.zzaEz && this.zzaDn != null) {
            this.zzaCA.zza(this.zzaDn, this.zzamg);
        }
    }

    @WorkerThread
    public final void zzb(zzam com_google_android_gms_common_internal_zzam, Set<Scope> set) {
        if (com_google_android_gms_common_internal_zzam == null || set == null) {
            Log.wtf("GoogleApiManager", "Received null response from onSignInSuccess", new Exception());
            zzh(new ConnectionResult(4));
            return;
        }
        this.zzaDn = com_google_android_gms_common_internal_zzam;
        this.zzamg = set;
        zzqx();
    }

    public final void zzf(@NonNull ConnectionResult connectionResult) {
        this.zzaEo.mHandler.post(new zzbeu(this, connectionResult));
    }

    @WorkerThread
    public final void zzh(ConnectionResult connectionResult) {
        ((zzbep) this.zzaEo.zzaCD.get(this.zzaAM)).zzh(connectionResult);
    }
}
