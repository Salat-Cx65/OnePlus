package com.google.android.gms.internal;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.zzb;

public class zzbdi extends zzbcm {
    private zzben zzaAP;
    private final zzb<zzbcf<?>> zzaCY;

    private zzbdi(zzbff com_google_android_gms_internal_zzbff) {
        super(com_google_android_gms_internal_zzbff);
        this.zzaCY = new zzb();
        this.zzaEI.zza("ConnectionlessLifecycleHelper", (zzbfe) this);
    }

    public static void zza(Activity activity, zzben com_google_android_gms_internal_zzben, zzbcf<?> com_google_android_gms_internal_zzbcf_) {
        zzn(activity);
        zzbff zzn = zzn(activity);
        zzbdi com_google_android_gms_internal_zzbdi = (zzbdi) zzn.zza("ConnectionlessLifecycleHelper", zzbdi.class);
        if (com_google_android_gms_internal_zzbdi == null) {
            com_google_android_gms_internal_zzbdi = new zzbdi(zzn);
        }
        com_google_android_gms_internal_zzbdi.zzaAP = com_google_android_gms_internal_zzben;
        zzbr.zzb((Object) com_google_android_gms_internal_zzbcf_, (Object) "ApiKey cannot be null");
        com_google_android_gms_internal_zzbdi.zzaCY.add(com_google_android_gms_internal_zzbcf_);
        com_google_android_gms_internal_zzben.zza(com_google_android_gms_internal_zzbdi);
    }

    private final void zzpQ() {
        if (!this.zzaCY.isEmpty()) {
            this.zzaAP.zza(this);
        }
    }

    public final void onResume() {
        super.onResume();
        zzpQ();
    }

    public final void onStart() {
        super.onStart();
        zzpQ();
    }

    public final void onStop() {
        super.onStop();
        this.zzaAP.zzb(this);
    }

    protected final void zza(ConnectionResult connectionResult, int i) {
        this.zzaAP.zza(connectionResult, i);
    }

    final zzb<zzbcf<?>> zzpP() {
        return this.zzaCY;
    }

    protected final void zzpq() {
        this.zzaAP.zzpq();
    }
}
