package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.location.LocationSettingsResult;

final class zzcey extends zzceo {
    private zzbcl<LocationSettingsResult> zzbiX;

    public zzcey(zzbcl<LocationSettingsResult> com_google_android_gms_internal_zzbcl_com_google_android_gms_location_LocationSettingsResult) {
        zzbr.zzb(com_google_android_gms_internal_zzbcl_com_google_android_gms_location_LocationSettingsResult != null, (Object) "listener can't be null.");
        this.zzbiX = com_google_android_gms_internal_zzbcl_com_google_android_gms_location_LocationSettingsResult;
    }

    public final void zza(LocationSettingsResult locationSettingsResult) throws RemoteException {
        this.zzbiX.setResult(locationSettingsResult);
        this.zzbiX = null;
    }
}
