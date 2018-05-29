package com.google.android.gms.dynamic;

import net.oneplus.weather.widget.openglbase.RainSurfaceView;

final class zzg implements zzi {
    private /* synthetic */ zza zzaSz;

    zzg(zza com_google_android_gms_dynamic_zza) {
        this.zzaSz = com_google_android_gms_dynamic_zza;
    }

    public final int getState() {
        return RainSurfaceView.RAIN_LEVEL_RAINSTORM;
    }

    public final void zzb(LifecycleDelegate lifecycleDelegate) {
        this.zzaSz.zzaSv.onStart();
    }
}
