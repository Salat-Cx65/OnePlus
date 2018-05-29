package com.google.firebase.iid;

import android.util.Log;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

final class zzg implements Runnable {
    private /* synthetic */ zzd zzcnh;
    private /* synthetic */ zzf zzcni;

    zzg(zzf com_google_firebase_iid_zzf, zzd com_google_firebase_iid_zzd) {
        this.zzcni = com_google_firebase_iid_zzf;
        this.zzcnh = com_google_firebase_iid_zzd;
    }

    public final void run() {
        if (Log.isLoggable("EnhancedIntentService", RainSurfaceView.RAIN_LEVEL_DOWNPOUR)) {
            Log.d("EnhancedIntentService", "bg processing of the intent starting now");
        }
        this.zzcni.zzcng.handleIntent(this.zzcnh.intent);
        this.zzcnh.finish();
    }
}
