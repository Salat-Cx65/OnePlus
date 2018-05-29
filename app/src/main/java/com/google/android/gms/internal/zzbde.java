package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collections;

final class zzbde implements OnCompleteListener<Void> {
    private /* synthetic */ zzbdb zzaCR;
    private zzbfu zzaCS;

    zzbde(zzbdb com_google_android_gms_internal_zzbdb, zzbfu com_google_android_gms_internal_zzbfu) {
        this.zzaCR = com_google_android_gms_internal_zzbdb;
        this.zzaCS = com_google_android_gms_internal_zzbfu;
    }

    final void cancel() {
        this.zzaCS.zzmD();
    }

    public final void onComplete(@NonNull Task<Void> task) {
        zzbdb.zza(this.zzaCR).lock();
        if (zzbdb.zzb(this.zzaCR)) {
            if (task.isSuccessful()) {
                zzbdb.zzb(this.zzaCR, new ArrayMap(zzbdb.zzm(this.zzaCR).size()));
                for (zzbda com_google_android_gms_internal_zzbda : zzbdb.zzm(this.zzaCR).values()) {
                    zzbdb.zzg(this.zzaCR).put(com_google_android_gms_internal_zzbda.zzpf(), ConnectionResult.zzazZ);
                }
            } else if (task.getException() instanceof zza) {
                zza com_google_android_gms_common_api_zza = (zza) task.getException();
                if (zzbdb.zze(this.zzaCR)) {
                    zzbdb.zzb(this.zzaCR, new ArrayMap(zzbdb.zzm(this.zzaCR).size()));
                    for (zzbda com_google_android_gms_internal_zzbda2 : zzbdb.zzm(this.zzaCR).values()) {
                        zzbcf zzpf = com_google_android_gms_internal_zzbda2.zzpf();
                        ConnectionResult zza = com_google_android_gms_common_api_zza.zza(com_google_android_gms_internal_zzbda2);
                        if (zzbdb.zza(this.zzaCR, com_google_android_gms_internal_zzbda2, zza)) {
                            zzbdb.zzg(this.zzaCR).put(zzpf, new ConnectionResult(16));
                        } else {
                            zzbdb.zzg(this.zzaCR).put(zzpf, zza);
                        }
                    }
                } else {
                    zzbdb.zzb(this.zzaCR, com_google_android_gms_common_api_zza.zzpd());
                }
            } else {
                Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                zzbdb.zzb(this.zzaCR, Collections.emptyMap());
            }
            if (this.zzaCR.isConnected()) {
                zzbdb.zzd(this.zzaCR).putAll(zzbdb.zzg(this.zzaCR));
                if (zzbdb.zzf(this.zzaCR) == null) {
                    zzbdb.zzi(this.zzaCR);
                    zzbdb.zzj(this.zzaCR);
                    zzbdb.zzl(this.zzaCR).signalAll();
                }
            }
            this.zzaCS.zzmD();
            zzbdb.zza(this.zzaCR).unlock();
            return;
        }
        this.zzaCS.zzmD();
        zzbdb.zza(this.zzaCR).unlock();
    }
}
