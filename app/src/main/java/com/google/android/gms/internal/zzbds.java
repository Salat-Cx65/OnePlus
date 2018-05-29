package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.internal.zzj;
import java.util.Map;

final class zzbds extends zzbdz {
    final /* synthetic */ zzbdp zzaDr;
    private final Map<zze, zzbdr> zzaDt;

    public zzbds(zzbdp com_google_android_gms_internal_zzbdp, Map<zze, zzbdr> map) {
        this.zzaDr = com_google_android_gms_internal_zzbdp;
        super(null);
        this.zzaDt = map;
    }

    @WorkerThread
    public final void zzpT() {
        int i;
        Object obj = 1;
        int i2 = 0;
        int i3 = 1;
        int i4 = 0;
        for (zze com_google_android_gms_common_api_Api_zze : this.zzaDt.keySet()) {
            if (!com_google_android_gms_common_api_Api_zze.zzpc()) {
                i = 0;
                i3 = i4;
            } else if (!((zzbdr) this.zzaDt.get(com_google_android_gms_common_api_Api_zze)).zzaCl) {
                i = 1;
                break;
            } else {
                i = i3;
                i3 = 1;
            }
            i4 = i3;
            i3 = i;
        }
        int i5 = i4;
        i = 0;
        if (i5 != 0) {
            i2 = zzbdp.zzb(this.zzaDr).isGooglePlayServicesAvailable(zzbdp.zza(this.zzaDr));
        }
        if (i2 == 0 || (r0 == 0 && i3 == 0)) {
            if (zzbdp.zze(this.zzaDr)) {
                zzbdp.zzf(this.zzaDr).connect();
            }
            for (zze com_google_android_gms_common_api_Api_zze2 : this.zzaDt.keySet()) {
                zzj com_google_android_gms_common_internal_zzj = (zzj) this.zzaDt.get(com_google_android_gms_common_api_Api_zze2);
                if (!com_google_android_gms_common_api_Api_zze2.zzpc() || i2 == 0) {
                    com_google_android_gms_common_api_Api_zze2.zza(com_google_android_gms_common_internal_zzj);
                } else {
                    zzbdp.zzd(this.zzaDr).zza(new zzbdu(this, this.zzaDr, com_google_android_gms_common_internal_zzj));
                }
            }
            return;
        }
        zzbdp.zzd(this.zzaDr).zza(new zzbdt(this, this.zzaDr, new ConnectionResult(i2, null)));
    }
}
