package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.internal.zzbfi;
import com.google.android.gms.internal.zzbfq;
import com.google.android.gms.internal.zzcev;
import com.google.android.gms.internal.zzcez;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzg extends zzbfq<zzcev, LocationCallback> {
    private /* synthetic */ zzcez zzbhH;
    private /* synthetic */ zzbfi zzbhI;

    zzg(FusedLocationProviderClient fusedLocationProviderClient, zzbfi com_google_android_gms_internal_zzbfi, zzcez com_google_android_gms_internal_zzcez, zzbfi com_google_android_gms_internal_zzbfi2) {
        this.zzbhH = com_google_android_gms_internal_zzcez;
        this.zzbhI = com_google_android_gms_internal_zzbfi2;
        super(com_google_android_gms_internal_zzbfi);
    }

    protected final /* synthetic */ void zzb(zzb com_google_android_gms_common_api_Api_zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzcev) com_google_android_gms_common_api_Api_zzb).zza(this.zzbhH, this.zzbhI, new zza(taskCompletionSource));
    }
}
