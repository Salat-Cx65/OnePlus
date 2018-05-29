package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

final class zzac implements zzg {
    private /* synthetic */ OnConnectionFailedListener zzaHF;

    zzac(OnConnectionFailedListener onConnectionFailedListener) {
        this.zzaHF = onConnectionFailedListener;
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzaHF.onConnectionFailed(connectionResult);
    }
}
