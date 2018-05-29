package com.google.firebase.iid;

import android.os.Binder;
import android.os.Process;
import android.util.Log;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzf extends Binder {
    private final zzb zzcng;

    zzf(zzb com_google_firebase_iid_zzb) {
        this.zzcng = com_google_firebase_iid_zzb;
    }

    public final void zza(zzd com_google_firebase_iid_zzd) {
        if (Binder.getCallingUid() != Process.myUid()) {
            throw new SecurityException("Binding only allowed within app");
        }
        if (Log.isLoggable("EnhancedIntentService", RainSurfaceView.RAIN_LEVEL_DOWNPOUR)) {
            Log.d("EnhancedIntentService", "service received new intent via bind strategy");
        }
        if (this.zzcng.zzo(com_google_firebase_iid_zzd.intent)) {
            com_google_firebase_iid_zzd.finish();
            return;
        }
        if (Log.isLoggable("EnhancedIntentService", RainSurfaceView.RAIN_LEVEL_DOWNPOUR)) {
            Log.d("EnhancedIntentService", "intent being queued for bg execution");
        }
        this.zzcng.zzbrZ.execute(new zzg(this, com_google_firebase_iid_zzd));
    }
}
