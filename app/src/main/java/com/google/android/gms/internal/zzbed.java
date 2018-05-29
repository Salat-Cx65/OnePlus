package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import java.util.concurrent.atomic.AtomicReference;

final class zzbed implements ConnectionCallbacks {
    private /* synthetic */ zzbeb zzaDP;
    private /* synthetic */ AtomicReference zzaDQ;
    private /* synthetic */ zzbfz zzaDR;

    zzbed(zzbeb com_google_android_gms_internal_zzbeb, AtomicReference atomicReference, zzbfz com_google_android_gms_internal_zzbfz) {
        this.zzaDP = com_google_android_gms_internal_zzbeb;
        this.zzaDQ = atomicReference;
        this.zzaDR = com_google_android_gms_internal_zzbfz;
    }

    public final void onConnected(Bundle bundle) {
        zzbeb.zza(this.zzaDP, (GoogleApiClient) this.zzaDQ.get(), this.zzaDR, true);
    }

    public final void onConnectionSuspended(int i) {
    }
}
