package com.google.android.gms.dynamic;

import android.app.Activity;
import android.os.Bundle;

final class zzc implements zzi {
    private /* synthetic */ Activity val$activity;
    private /* synthetic */ Bundle zzaSA;
    private /* synthetic */ zza zzaSz;
    private /* synthetic */ Bundle zzxY;

    zzc(zza com_google_android_gms_dynamic_zza, Activity activity, Bundle bundle, Bundle bundle2) {
        this.zzaSz = com_google_android_gms_dynamic_zza;
        this.val$activity = activity;
        this.zzaSA = bundle;
        this.zzxY = bundle2;
    }

    public final int getState() {
        return 0;
    }

    public final void zzb(LifecycleDelegate lifecycleDelegate) {
        this.zzaSz.zzaSv.onInflate(this.val$activity, this.zzaSA, this.zzxY);
    }
}
