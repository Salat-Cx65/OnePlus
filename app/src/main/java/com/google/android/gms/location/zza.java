package com.google.android.gms.location;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.internal.zzcev;

final class zza extends com.google.android.gms.common.api.Api.zza<zzcev, NoOptions> {
    zza() {
    }

    public final /* synthetic */ zze zza(Context context, Looper looper, zzq com_google_android_gms_common_internal_zzq, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        return new zzcev(context, looper, connectionCallbacks, onConnectionFailedListener, ActivityRecognition.CLIENT_NAME);
    }
}
