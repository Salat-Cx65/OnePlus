package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

final class zzbcz implements zzbfc {
    private /* synthetic */ zzbcw zzaCz;

    private zzbcz(zzbcw com_google_android_gms_internal_zzbcw) {
        this.zzaCz = com_google_android_gms_internal_zzbcw;
    }

    public final void zzc(@NonNull ConnectionResult connectionResult) {
        zzbcw.zza(this.zzaCz).lock();
        zzbcw.zzb(this.zzaCz, connectionResult);
        zzbcw.zzb(this.zzaCz);
        zzbcw.zza(this.zzaCz).unlock();
    }

    public final void zze(int i, boolean z) {
        zzbcw.zza(this.zzaCz).lock();
        if (zzbcw.zzc(this.zzaCz)) {
            zzbcw.zza(this.zzaCz, false);
            zzbcw.zza(this.zzaCz, i, z);
            zzbcw.zza(this.zzaCz).unlock();
            return;
        }
        zzbcw.zza(this.zzaCz, true);
        zzbcw.zzf(this.zzaCz).onConnectionSuspended(i);
        zzbcw.zza(this.zzaCz).unlock();
    }

    public final void zzm(@Nullable Bundle bundle) {
        zzbcw.zza(this.zzaCz).lock();
        zzbcw.zzb(this.zzaCz, ConnectionResult.zzazZ);
        zzbcw.zzb(this.zzaCz);
        zzbcw.zza(this.zzaCz).unlock();
    }
}
