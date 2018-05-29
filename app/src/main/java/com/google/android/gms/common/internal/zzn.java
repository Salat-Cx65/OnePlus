package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.support.annotation.BinderThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzn extends zze {
    private /* synthetic */ zzd zzaHg;
    private IBinder zzaHk;

    @BinderThread
    public zzn(zzd com_google_android_gms_common_internal_zzd, int i, IBinder iBinder, Bundle bundle) {
        this.zzaHg = com_google_android_gms_common_internal_zzd;
        super(com_google_android_gms_common_internal_zzd, i, bundle);
        this.zzaHk = iBinder;
    }

    protected final void zzj(ConnectionResult connectionResult) {
        if (this.zzaHg.zzaGY != null) {
            this.zzaHg.zzaGY.onConnectionFailed(connectionResult);
        }
        this.zzaHg.onConnectionFailed(connectionResult);
    }

    protected final boolean zzrh() {
        try {
            String interfaceDescriptor = this.zzaHk.getInterfaceDescriptor();
            if (this.zzaHg.zzdb().equals(interfaceDescriptor)) {
                IInterface zzd = this.zzaHg.zzd(this.zzaHk);
                if (zzd == null) {
                    return false;
                }
                if (!this.zzaHg.zza((int) RainSurfaceView.RAIN_LEVEL_SHOWER, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, zzd) && !this.zzaHg.zza((int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, zzd)) {
                    return false;
                }
                this.zzaHg.zzaHb = null;
                Bundle zzoA = this.zzaHg.zzoA();
                if (this.zzaHg.zzaGX != null) {
                    this.zzaHg.zzaGX.onConnected(zzoA);
                }
                return true;
            }
            String valueOf = String.valueOf(this.zzaHg.zzdb());
            Log.e("GmsClient", new StringBuilder((String.valueOf(valueOf).length() + 34) + String.valueOf(interfaceDescriptor).length()).append("service descriptor mismatch: ").append(valueOf).append(" vs. ").append(interfaceDescriptor).toString());
            return false;
        } catch (RemoteException e) {
            Log.w("GmsClient", "service probably died");
            return false;
        }
    }
}
