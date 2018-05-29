package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collections;

final class zzbdd implements OnCompleteListener<Void> {
    private /* synthetic */ zzbdb zzaCR;

    private zzbdd(zzbdb com_google_android_gms_internal_zzbdb) {
        this.zzaCR = com_google_android_gms_internal_zzbdb;
    }

    public final void onComplete(@NonNull Task<Void> task) {
        zzbdb.zza(this.zzaCR).lock();
        if (zzbdb.zzb(this.zzaCR)) {
            if (task.isSuccessful()) {
                zzbdb.zza(this.zzaCR, new ArrayMap(zzbdb.zzc(this.zzaCR).size()));
                for (zzbda com_google_android_gms_internal_zzbda : zzbdb.zzc(this.zzaCR).values()) {
                    zzbdb.zzd(this.zzaCR).put(com_google_android_gms_internal_zzbda.zzpf(), ConnectionResult.zzazZ);
                }
            } else if (task.getException() instanceof zza) {
                zza com_google_android_gms_common_api_zza = (zza) task.getException();
                if (zzbdb.zze(this.zzaCR)) {
                    zzbdb.zza(this.zzaCR, new ArrayMap(zzbdb.zzc(this.zzaCR).size()));
                    for (zzbda com_google_android_gms_internal_zzbda2 : zzbdb.zzc(this.zzaCR).values()) {
                        zzbcf zzpf = com_google_android_gms_internal_zzbda2.zzpf();
                        ConnectionResult zza = com_google_android_gms_common_api_zza.zza(com_google_android_gms_internal_zzbda2);
                        if (zzbdb.zza(this.zzaCR, com_google_android_gms_internal_zzbda2, zza)) {
                            zzbdb.zzd(this.zzaCR).put(zzpf, new ConnectionResult(16));
                        } else {
                            zzbdb.zzd(this.zzaCR).put(zzpf, zza);
                        }
                    }
                } else {
                    zzbdb.zza(this.zzaCR, com_google_android_gms_common_api_zza.zzpd());
                }
                zzbdb.zza(this.zzaCR, zzbdb.zzf(this.zzaCR));
            } else {
                Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                zzbdb.zza(this.zzaCR, Collections.emptyMap());
                zzbdb.zza(this.zzaCR, new ConnectionResult(8));
            }
            if (zzbdb.zzg(this.zzaCR) != null) {
                zzbdb.zzd(this.zzaCR).putAll(zzbdb.zzg(this.zzaCR));
                zzbdb.zza(this.zzaCR, zzbdb.zzf(this.zzaCR));
            }
            if (zzbdb.zzh(this.zzaCR) == null) {
                zzbdb.zzi(this.zzaCR);
                zzbdb.zzj(this.zzaCR);
            } else {
                zzbdb.zza(this.zzaCR, false);
                zzbdb.zzk(this.zzaCR).zzc(zzbdb.zzh(this.zzaCR));
            }
            zzbdb.zzl(this.zzaCR).signalAll();
            zzbdb.zza(this.zzaCR).unlock();
            return;
        }
        zzbdb.zza(this.zzaCR).unlock();
    }
}
