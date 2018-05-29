package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationStatusCodes;

final class zzcew extends zzcek {
    private zzbcl<Status> zzbiX;

    public zzcew(zzbcl<Status> com_google_android_gms_internal_zzbcl_com_google_android_gms_common_api_Status) {
        this.zzbiX = com_google_android_gms_internal_zzbcl_com_google_android_gms_common_api_Status;
    }

    public final void zza(int i, PendingIntent pendingIntent) {
        Log.wtf("LocationClientImpl", "Unexpected call to onRemoveGeofencesByPendingIntentResult");
    }

    public final void zza(int i, String[] strArr) {
        if (this.zzbiX == null) {
            Log.wtf("LocationClientImpl", "onAddGeofenceResult called multiple times");
            return;
        }
        this.zzbiX.setResult(LocationStatusCodes.zzbj(LocationStatusCodes.zzbi(i)));
        this.zzbiX = null;
    }

    public final void zzb(int i, String[] strArr) {
        Log.wtf("LocationClientImpl", "Unexpected call to onRemoveGeofencesByRequestIdsResult");
    }
}
