package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

final class zzbel extends Handler {
    private /* synthetic */ zzbej zzaEc;

    zzbel(zzbej com_google_android_gms_internal_zzbej, Looper looper) {
        this.zzaEc = com_google_android_gms_internal_zzbej;
        super(looper);
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                ((zzbek) message.obj).zzc(this.zzaEc);
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                throw ((RuntimeException) message.obj);
            default:
                Log.w("GACStateManager", new StringBuilder(31).append("Unknown message id: ").append(message.what).toString());
        }
    }
}
