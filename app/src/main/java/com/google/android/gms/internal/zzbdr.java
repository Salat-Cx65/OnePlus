package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzj;
import java.lang.ref.WeakReference;

final class zzbdr implements zzj {
    private final boolean zzaCl;
    private final WeakReference<zzbdp> zzaDs;
    private final Api<?> zzayY;

    public zzbdr(zzbdp com_google_android_gms_internal_zzbdp, Api<?> api, boolean z) {
        this.zzaDs = new WeakReference(com_google_android_gms_internal_zzbdp);
        this.zzayY = api;
        this.zzaCl = z;
    }

    public final void zzf(@NonNull ConnectionResult connectionResult) {
        boolean z = false;
        zzbdp com_google_android_gms_internal_zzbdp = (zzbdp) this.zzaDs.get();
        if (com_google_android_gms_internal_zzbdp != null) {
            if (Looper.myLooper() == zzbdp.zzd(com_google_android_gms_internal_zzbdp).zzaCn.getLooper()) {
                z = true;
            }
            zzbr.zza(z, (Object) "onReportServiceBinding must be called on the GoogleApiClient handler thread");
            zzbdp.zzc(com_google_android_gms_internal_zzbdp).lock();
            if (zzbdp.zza(com_google_android_gms_internal_zzbdp, 0)) {
                if (!connectionResult.isSuccess()) {
                    zzbdp.zza(com_google_android_gms_internal_zzbdp, connectionResult, this.zzayY, this.zzaCl);
                }
                if (zzbdp.zzk(com_google_android_gms_internal_zzbdp)) {
                    zzbdp.zzj(com_google_android_gms_internal_zzbdp);
                }
                zzbdp.zzc(com_google_android_gms_internal_zzbdp).unlock();
                return;
            }
            zzbdp.zzc(com_google_android_gms_internal_zzbdp).unlock();
        }
    }
}
