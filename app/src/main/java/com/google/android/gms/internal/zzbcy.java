package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

final class zzbcy implements zzbfc {
    private /* synthetic */ zzbcw zzaCz;

    private zzbcy(zzbcw com_google_android_gms_internal_zzbcw) {
        this.zzaCz = com_google_android_gms_internal_zzbcw;
    }

    public final void zzc(@NonNull ConnectionResult connectionResult) {
        zzbcw.zza(this.zzaCz).lock();
        zzbcw.zza(this.zzaCz, connectionResult);
        zzbcw.zzb(this.zzaCz);
        zzbcw.zza(this.zzaCz).unlock();
    }

    public final void zze(int i, boolean z) {
        zzbcw.zza(this.zzaCz).lock();
        if (zzbcw.zzc(this.zzaCz) || zzbcw.zzd(this.zzaCz) == null || !zzbcw.zzd(this.zzaCz).isSuccess()) {
            zzbcw.zza(this.zzaCz, false);
            zzbcw.zza(this.zzaCz, i, z);
            zzbcw.zza(this.zzaCz).unlock();
            return;
        }
        zzbcw.zza(this.zzaCz, true);
        zzbcw.zze(this.zzaCz).onConnectionSuspended(i);
        zzbcw.zza(this.zzaCz).unlock();
    }

    public final void zzm(@Nullable Bundle bundle) {
        zzbcw.zza(this.zzaCz).lock();
        zzbcw.zza(this.zzaCz, bundle);
        zzbcw.zza(this.zzaCz, ConnectionResult.zzazZ);
        zzbcw.zzb(this.zzaCz);
        zzbcw.zza(this.zzaCz).unlock();
    }
}
