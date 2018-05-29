package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

final class zzh extends Handler {
    private /* synthetic */ zzd zzaHg;

    public zzh(zzd com_google_android_gms_common_internal_zzd, Looper looper) {
        this.zzaHg = com_google_android_gms_common_internal_zzd;
        super(looper);
    }

    private static void zza(Message message) {
        ((zzi) message.obj).unregister();
    }

    private static boolean zzb(Message message) {
        return message.what == 2 || message.what == 1 || message.what == 7;
    }

    public final void handleMessage(Message message) {
        PendingIntent pendingIntent = null;
        if (this.zzaHg.zzaHd.get() != message.arg1) {
            if (zzb(message)) {
                zza(message);
            }
        } else if ((message.what == 1 || message.what == 7 || message.what == 4 || message.what == 5) && !this.zzaHg.isConnecting()) {
            zza(message);
        } else if (message.what == 4) {
            zzd.zza(this.zzaHg, new ConnectionResult(message.arg2));
            if (!zzd.zzb(this.zzaHg) || zzd.zzc(this.zzaHg)) {
                zzd = zzd.zzd(this.zzaHg) != null ? zzd.zzd(this.zzaHg) : new ConnectionResult(8);
                this.zzaHg.zzaGS.zzf(zzd);
                this.zzaHg.onConnectionFailed(zzd);
                return;
            }
            zzd.zza(this.zzaHg, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, null);
        } else if (message.what == 5) {
            zzd = zzd.zzd(this.zzaHg) != null ? zzd.zzd(this.zzaHg) : new ConnectionResult(8);
            this.zzaHg.zzaGS.zzf(zzd);
            this.zzaHg.onConnectionFailed(zzd);
        } else if (message.what == 3) {
            if (message.obj instanceof PendingIntent) {
                pendingIntent = (PendingIntent) message.obj;
            }
            ConnectionResult connectionResult = new ConnectionResult(message.arg2, pendingIntent);
            this.zzaHg.zzaGS.zzf(connectionResult);
            this.zzaHg.onConnectionFailed(connectionResult);
        } else if (message.what == 6) {
            zzd.zza(this.zzaHg, (int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, null);
            if (zzd.zze(this.zzaHg) != null) {
                zzd.zze(this.zzaHg).onConnectionSuspended(message.arg2);
            }
            this.zzaHg.onConnectionSuspended(message.arg2);
            zzd.zza(this.zzaHg, (int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, 1, null);
        } else if (message.what == 2 && !this.zzaHg.isConnected()) {
            zza(message);
        } else if (zzb(message)) {
            ((zzi) message.obj).zzri();
        } else {
            Log.wtf("GmsClient", new StringBuilder(45).append("Don't know how to handle message: ").append(message.what).toString(), new Exception());
        }
    }
}
