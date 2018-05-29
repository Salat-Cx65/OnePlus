package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

final class zzbgg extends Handler {
    private /* synthetic */ zzbge zzaFk;

    public zzbgg(zzbge com_google_android_gms_internal_zzbge, Looper looper) {
        this.zzaFk = com_google_android_gms_internal_zzbge;
        super(looper);
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                PendingResult pendingResult = (PendingResult) message.obj;
                synchronized (zzbge.zzf(this.zzaFk)) {
                    if (pendingResult == null) {
                        zzbge.zza(zzbge.zzg(this.zzaFk), new Status(13, "Transform returned null"));
                    } else if (pendingResult instanceof zzbft) {
                        zzbge.zza(zzbge.zzg(this.zzaFk), ((zzbft) pendingResult).getStatus());
                    } else {
                        zzbge.zzg(this.zzaFk).zza(pendingResult);
                    }
                }
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                RuntimeException runtimeException = (RuntimeException) message.obj;
                String str = "TransformedResultImpl";
                String str2 = "Runtime exception on the transformation worker thread: ";
                String valueOf = String.valueOf(runtimeException.getMessage());
                Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                throw runtimeException;
            default:
                Log.e("TransformedResultImpl", new StringBuilder(70).append("TransformationResultHandler received unknown message type: ").append(message.what).toString());
        }
    }
}
