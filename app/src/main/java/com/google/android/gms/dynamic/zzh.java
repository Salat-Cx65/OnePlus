package com.google.android.gms.dynamic;

import net.oneplus.weather.widget.openglbase.RainSurfaceView;

final class zzh implements zzi {
    private /* synthetic */ zza zzaSz;

    zzh(zza com_google_android_gms_dynamic_zza) {
        this.zzaSz = com_google_android_gms_dynamic_zza;
    }

    public final int getState() {
        return RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
    }

    public final void zzb(LifecycleDelegate lifecycleDelegate) {
        this.zzaSz.zzaSv.onResume();
    }
}
