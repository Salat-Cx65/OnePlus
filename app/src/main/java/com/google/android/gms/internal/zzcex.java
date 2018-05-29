package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationStatusCodes;

final class zzcex extends zzcek {
    private zzbcl<Status> zzbiX;

    public zzcex(zzbcl<Status> com_google_android_gms_internal_zzbcl_com_google_android_gms_common_api_Status) {
        this.zzbiX = com_google_android_gms_internal_zzbcl_com_google_android_gms_common_api_Status;
    }

    private final void zzbk(int i) {
        if (this.zzbiX == null) {
            Log.wtf("LocationClientImpl", "onRemoveGeofencesResult called multiple times");
            return;
        }
        this.zzbiX.setResult(LocationStatusCodes.zzbj(LocationStatusCodes.zzbi(i)));
        this.zzbiX = null;
    }

    public final void zza(int i, PendingIntent pendingIntent) {
        zzbk(i);
    }

    public final void zza(int i, String[] strArr) {
        Log.wtf("LocationClientImpl", "Unexpected call to onAddGeofencesResult");
    }

    public final void zzb(int i, String[] strArr) {
        zzbk(i);
    }
}
