package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.common.ConnectionResult;

public final class zzl implements ServiceConnection {
    private /* synthetic */ zzd zzaHg;
    private final int zzaHj;

    public zzl(zzd com_google_android_gms_common_internal_zzd, int i) {
        this.zzaHg = com_google_android_gms_common_internal_zzd;
        this.zzaHj = i;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (iBinder == null) {
            zzd.zza(this.zzaHg, (int) ConnectionResult.API_UNAVAILABLE);
            return;
        }
        synchronized (zzd.zza(this.zzaHg)) {
            zzay com_google_android_gms_common_internal_zzay;
            zzd com_google_android_gms_common_internal_zzd = this.zzaHg;
            if (iBinder == null) {
                com_google_android_gms_common_internal_zzay = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                com_google_android_gms_common_internal_zzay = (queryLocalInterface == null || !(queryLocalInterface instanceof zzay)) ? new zzba(iBinder) : (zzay) queryLocalInterface;
            }
            zzd.zza(com_google_android_gms_common_internal_zzd, com_google_android_gms_common_internal_zzay);
        }
        this.zzaHg.zza(0, null, this.zzaHj);
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (zzd.zza(this.zzaHg)) {
            zzd.zza(this.zzaHg, null);
        }
        this.zzaHg.mHandler.sendMessage(this.zzaHg.mHandler.obtainMessage(ConnectionResult.RESOLUTION_REQUIRED, this.zzaHj, 1));
    }
}
