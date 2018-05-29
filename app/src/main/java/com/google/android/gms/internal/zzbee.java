package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;

final class zzbee implements OnConnectionFailedListener {
    private /* synthetic */ zzbfz zzaDR;

    zzbee(zzbeb com_google_android_gms_internal_zzbeb, zzbfz com_google_android_gms_internal_zzbfz) {
        this.zzaDR = com_google_android_gms_internal_zzbfz;
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzaDR.setResult(new Status(8));
    }
}
