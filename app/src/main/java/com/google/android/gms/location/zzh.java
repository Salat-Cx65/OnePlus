package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.internal.zzbfk;
import com.google.android.gms.internal.zzbgk;
import com.google.android.gms.internal.zzcev;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzh extends zzbgk<zzcev, LocationCallback> {
    zzh(FusedLocationProviderClient fusedLocationProviderClient, zzbfk com_google_android_gms_internal_zzbfk) {
        super(com_google_android_gms_internal_zzbfk);
    }

    protected final /* synthetic */ void zzc(zzb com_google_android_gms_common_api_Api_zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzcev) com_google_android_gms_common_api_Api_zzb).zzb(zzqE(), null);
    }
}
