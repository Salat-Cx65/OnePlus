package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.os.EnvironmentCompat;
import android.util.Log;
import com.google.android.gms.common.stats.zza;
import java.util.HashMap;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

final class zzah extends zzaf implements Callback {
    private final Context mApplicationContext;
    private final Handler mHandler;
    private final HashMap<zzag, zzai> zzaHR;
    private final zza zzaHS;
    private final long zzaHT;
    private final long zzaHU;

    zzah(Context context) {
        this.zzaHR = new HashMap();
        this.mApplicationContext = context.getApplicationContext();
        this.mHandler = new Handler(context.getMainLooper(), this);
        this.zzaHS = zza.zzrT();
        this.zzaHT = 5000;
        this.zzaHU = 300000;
    }

    public final boolean handleMessage(Message message) {
        zzag com_google_android_gms_common_internal_zzag;
        zzai com_google_android_gms_common_internal_zzai;
        switch (message.what) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                synchronized (this.zzaHR) {
                    com_google_android_gms_common_internal_zzag = (zzag) message.obj;
                    com_google_android_gms_common_internal_zzai = (zzai) this.zzaHR.get(com_google_android_gms_common_internal_zzag);
                    if (com_google_android_gms_common_internal_zzai != null && com_google_android_gms_common_internal_zzai.zzrB()) {
                        if (com_google_android_gms_common_internal_zzai.isBound()) {
                            com_google_android_gms_common_internal_zzai.zzcC("GmsClientSupervisor");
                        }
                        this.zzaHR.remove(com_google_android_gms_common_internal_zzag);
                    }
                }
                return true;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                synchronized (this.zzaHR) {
                    com_google_android_gms_common_internal_zzag = (zzag) message.obj;
                    com_google_android_gms_common_internal_zzai = (zzai) this.zzaHR.get(com_google_android_gms_common_internal_zzag);
                    if (com_google_android_gms_common_internal_zzai != null && com_google_android_gms_common_internal_zzai.getState() == 3) {
                        String valueOf = String.valueOf(com_google_android_gms_common_internal_zzag);
                        Log.wtf("GmsClientSupervisor", new StringBuilder(String.valueOf(valueOf).length() + 47).append("Timeout waiting for ServiceConnection callback ").append(valueOf).toString(), new Exception());
                        ComponentName componentName = com_google_android_gms_common_internal_zzai.getComponentName();
                        if (componentName == null) {
                            componentName = com_google_android_gms_common_internal_zzag.getComponentName();
                        }
                        com_google_android_gms_common_internal_zzai.onServiceDisconnected(componentName == null ? new ComponentName(com_google_android_gms_common_internal_zzag.getPackage(), EnvironmentCompat.MEDIA_UNKNOWN) : componentName);
                    }
                }
                return true;
            default:
                return false;
        }
    }

    protected final boolean zza(zzag com_google_android_gms_common_internal_zzag, ServiceConnection serviceConnection, String str) {
        boolean isBound;
        zzbr.zzb((Object) serviceConnection, (Object) "ServiceConnection must not be null");
        synchronized (this.zzaHR) {
            zzai com_google_android_gms_common_internal_zzai = (zzai) this.zzaHR.get(com_google_android_gms_common_internal_zzag);
            if (com_google_android_gms_common_internal_zzai == null) {
                com_google_android_gms_common_internal_zzai = new zzai(this, com_google_android_gms_common_internal_zzag);
                com_google_android_gms_common_internal_zzai.zza(serviceConnection, str);
                com_google_android_gms_common_internal_zzai.zzcB(str);
                this.zzaHR.put(com_google_android_gms_common_internal_zzag, com_google_android_gms_common_internal_zzai);
            } else {
                this.mHandler.removeMessages(0, com_google_android_gms_common_internal_zzag);
                if (com_google_android_gms_common_internal_zzai.zza(serviceConnection)) {
                    String valueOf = String.valueOf(com_google_android_gms_common_internal_zzag);
                    throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 81).append("Trying to bind a GmsServiceConnection that was already connected before.  config=").append(valueOf).toString());
                }
                com_google_android_gms_common_internal_zzai.zza(serviceConnection, str);
                switch (com_google_android_gms_common_internal_zzai.getState()) {
                    case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        serviceConnection.onServiceConnected(com_google_android_gms_common_internal_zzai.getComponentName(), com_google_android_gms_common_internal_zzai.getBinder());
                        break;
                    case RainSurfaceView.RAIN_LEVEL_SHOWER:
                        com_google_android_gms_common_internal_zzai.zzcB(str);
                        break;
                    default:
                        break;
                }
            }
            isBound = com_google_android_gms_common_internal_zzai.isBound();
        }
        return isBound;
    }

    protected final void zzb(zzag com_google_android_gms_common_internal_zzag, ServiceConnection serviceConnection, String str) {
        zzbr.zzb((Object) serviceConnection, (Object) "ServiceConnection must not be null");
        synchronized (this.zzaHR) {
            zzai com_google_android_gms_common_internal_zzai = (zzai) this.zzaHR.get(com_google_android_gms_common_internal_zzag);
            String valueOf;
            if (com_google_android_gms_common_internal_zzai == null) {
                valueOf = String.valueOf(com_google_android_gms_common_internal_zzag);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 50).append("Nonexistent connection status for service config: ").append(valueOf).toString());
            } else if (com_google_android_gms_common_internal_zzai.zza(serviceConnection)) {
                com_google_android_gms_common_internal_zzai.zzb(serviceConnection, str);
                if (com_google_android_gms_common_internal_zzai.zzrB()) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, com_google_android_gms_common_internal_zzag), this.zzaHT);
                }
            } else {
                valueOf = String.valueOf(com_google_android_gms_common_internal_zzag);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 76).append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=").append(valueOf).toString());
            }
        }
    }
}
