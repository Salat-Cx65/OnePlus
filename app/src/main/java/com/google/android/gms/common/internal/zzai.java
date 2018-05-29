package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.stats.zza;
import java.util.HashSet;
import java.util.Set;

final class zzai implements ServiceConnection {
    private int mState;
    private ComponentName zzaHQ;
    private final Set<ServiceConnection> zzaHV;
    private boolean zzaHW;
    private final zzag zzaHX;
    private /* synthetic */ zzah zzaHY;
    private IBinder zzaHl;

    public zzai(zzah com_google_android_gms_common_internal_zzah, zzag com_google_android_gms_common_internal_zzag) {
        this.zzaHY = com_google_android_gms_common_internal_zzah;
        this.zzaHX = com_google_android_gms_common_internal_zzag;
        this.zzaHV = new HashSet();
        this.mState = 2;
    }

    public final IBinder getBinder() {
        return this.zzaHl;
    }

    public final ComponentName getComponentName() {
        return this.zzaHQ;
    }

    public final int getState() {
        return this.mState;
    }

    public final boolean isBound() {
        return this.zzaHW;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (zzah.zza(this.zzaHY)) {
            zzah.zzb(this.zzaHY).removeMessages(1, this.zzaHX);
            this.zzaHl = iBinder;
            this.zzaHQ = componentName;
            for (ServiceConnection serviceConnection : this.zzaHV) {
                serviceConnection.onServiceConnected(componentName, iBinder);
            }
            this.mState = 1;
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (zzah.zza(this.zzaHY)) {
            zzah.zzb(this.zzaHY).removeMessages(1, this.zzaHX);
            this.zzaHl = null;
            this.zzaHQ = componentName;
            for (ServiceConnection serviceConnection : this.zzaHV) {
                serviceConnection.onServiceDisconnected(componentName);
            }
            this.mState = 2;
        }
    }

    public final void zza(ServiceConnection serviceConnection, String str) {
        zzah.zzd(this.zzaHY);
        zzah.zzc(this.zzaHY);
        this.zzaHX.zzrA();
        this.zzaHV.add(serviceConnection);
    }

    public final boolean zza(ServiceConnection serviceConnection) {
        return this.zzaHV.contains(serviceConnection);
    }

    public final void zzb(ServiceConnection serviceConnection, String str) {
        zzah.zzd(this.zzaHY);
        zzah.zzc(this.zzaHY);
        this.zzaHV.remove(serviceConnection);
    }

    public final void zzcB(String str) {
        this.mState = 3;
        zzah.zzd(this.zzaHY);
        this.zzaHW = zza.zza(zzah.zzc(this.zzaHY), str, this.zzaHX.zzrA(), this, 129);
        if (this.zzaHW) {
            zzah.zzb(this.zzaHY).sendMessageDelayed(zzah.zzb(this.zzaHY).obtainMessage(1, this.zzaHX), zzah.zze(this.zzaHY));
            return;
        }
        this.mState = 2;
        try {
            zzah.zzd(this.zzaHY);
            zzah.zzc(this.zzaHY).unbindService(this);
        } catch (IllegalArgumentException e) {
        }
    }

    public final void zzcC(String str) {
        zzah.zzb(this.zzaHY).removeMessages(1, this.zzaHX);
        zzah.zzd(this.zzaHY);
        zzah.zzc(this.zzaHY).unbindService(this);
        this.zzaHW = false;
        this.mState = 2;
    }

    public final boolean zzrB() {
        return this.zzaHV.isEmpty();
    }
}
