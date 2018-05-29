package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

final class zzbeg extends Handler {
    private /* synthetic */ zzbeb zzaDP;

    zzbeg(zzbeb com_google_android_gms_internal_zzbeb, Looper looper) {
        this.zzaDP = com_google_android_gms_internal_zzbeb;
        super(looper);
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                zzbeb.zzb(this.zzaDP);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                zzbeb.zza(this.zzaDP);
            default:
                Log.w("GoogleApiClientImpl", new StringBuilder(31).append("Unknown message id: ").append(message.what).toString());
        }
    }
}
