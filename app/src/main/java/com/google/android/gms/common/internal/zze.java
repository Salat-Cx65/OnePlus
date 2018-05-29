package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import com.google.android.gms.common.ConnectionResult;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

abstract class zze extends zzi<Boolean> {
    private int statusCode;
    private Bundle zzaHf;
    private /* synthetic */ zzd zzaHg;

    @BinderThread
    protected zze(zzd com_google_android_gms_common_internal_zzd, int i, Bundle bundle) {
        this.zzaHg = com_google_android_gms_common_internal_zzd;
        super(com_google_android_gms_common_internal_zzd, Boolean.valueOf(true));
        this.statusCode = i;
        this.zzaHf = bundle;
    }

    protected abstract void zzj(ConnectionResult connectionResult);

    protected abstract boolean zzrh();

    protected final /* synthetic */ void zzs(Object obj) {
        PendingIntent pendingIntent = null;
        if (((Boolean) obj) == null) {
            this.zzaHg.zza(1, null);
            return;
        }
        switch (this.statusCode) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                if (!zzrh()) {
                    this.zzaHg.zza(1, null);
                    zzj(new ConnectionResult(8, null));
                }
            case ConnectionResult.DEVELOPER_ERROR:
                this.zzaHg.zza(1, null);
                throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
            default:
                this.zzaHg.zza(1, null);
                if (this.zzaHf != null) {
                    pendingIntent = (PendingIntent) this.zzaHf.getParcelable("pendingIntent");
                }
                zzj(new ConnectionResult(this.statusCode, pendingIntent));
        }
    }
}
