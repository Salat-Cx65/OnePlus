package com.google.android.gms.dynamic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

final class zze implements zzi {
    private /* synthetic */ FrameLayout zzaSB;
    private /* synthetic */ LayoutInflater zzaSC;
    private /* synthetic */ ViewGroup zzaSD;
    private /* synthetic */ zza zzaSz;
    private /* synthetic */ Bundle zzxY;

    zze(zza com_google_android_gms_dynamic_zza, FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.zzaSz = com_google_android_gms_dynamic_zza;
        this.zzaSB = frameLayout;
        this.zzaSC = layoutInflater;
        this.zzaSD = viewGroup;
        this.zzxY = bundle;
    }

    public final int getState() {
        return RainSurfaceView.RAIN_LEVEL_SHOWER;
    }

    public final void zzb(LifecycleDelegate lifecycleDelegate) {
        this.zzaSB.removeAllViews();
        this.zzaSB.addView(this.zzaSz.zzaSv.onCreateView(this.zzaSC, this.zzaSD, this.zzxY));
    }
}
