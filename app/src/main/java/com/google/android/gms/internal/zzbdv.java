package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.Api.zze;
import java.util.ArrayList;

final class zzbdv extends zzbdz {
    private /* synthetic */ zzbdp zzaDr;
    private final ArrayList<zze> zzaDx;

    public zzbdv(zzbdp com_google_android_gms_internal_zzbdp, ArrayList<zze> arrayList) {
        this.zzaDr = com_google_android_gms_internal_zzbdp;
        super(null);
        this.zzaDx = arrayList;
    }

    @WorkerThread
    public final void zzpT() {
        zzbdp.zzd(this.zzaDr).zzaCn.zzaDI = zzbdp.zzg(this.zzaDr);
        ArrayList arrayList = this.zzaDx;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((zze) obj).zza(zzbdp.zzh(this.zzaDr), zzbdp.zzd(this.zzaDr).zzaCn.zzaDI);
        }
    }
}
