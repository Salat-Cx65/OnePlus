package com.google.android.gms.internal;

import android.support.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

final class zzbco implements Runnable {
    private final zzbcn zzaBT;
    final /* synthetic */ zzbcm zzaBU;

    zzbco(zzbcm com_google_android_gms_internal_zzbcm, zzbcn com_google_android_gms_internal_zzbcn) {
        this.zzaBU = com_google_android_gms_internal_zzbcm;
        this.zzaBT = com_google_android_gms_internal_zzbcn;
    }

    @MainThread
    public final void run() {
        if (this.zzaBU.mStarted) {
            ConnectionResult zzpx = this.zzaBT.zzpx();
            if (zzpx.hasResolution()) {
                this.zzaBU.zzaEI.startActivityForResult(GoogleApiActivity.zza(this.zzaBU.getActivity(), zzpx.getResolution(), this.zzaBT.zzpw(), false), 1);
            } else if (this.zzaBU.zzaBf.isUserResolvableError(zzpx.getErrorCode())) {
                this.zzaBU.zzaBf.zza(this.zzaBU.getActivity(), this.zzaBU.zzaEI, zzpx.getErrorCode(), RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzaBU);
            } else if (zzpx.getErrorCode() == 18) {
                GoogleApiAvailability.zza(this.zzaBU.getActivity().getApplicationContext(), new zzbcp(this, GoogleApiAvailability.zza(this.zzaBU.getActivity(), this.zzaBU)));
            } else {
                this.zzaBU.zza(zzpx, this.zzaBT.zzpw());
            }
        }
    }
}
