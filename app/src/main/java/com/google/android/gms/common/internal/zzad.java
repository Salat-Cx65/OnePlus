package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzad implements Callback {
    private final Handler mHandler;
    private final Object mLock;
    private final zzae zzaHG;
    private final ArrayList<ConnectionCallbacks> zzaHH;
    private ArrayList<ConnectionCallbacks> zzaHI;
    private final ArrayList<OnConnectionFailedListener> zzaHJ;
    private volatile boolean zzaHK;
    private final AtomicInteger zzaHL;
    private boolean zzaHM;

    public zzad(Looper looper, zzae com_google_android_gms_common_internal_zzae) {
        this.zzaHH = new ArrayList();
        this.zzaHI = new ArrayList();
        this.zzaHJ = new ArrayList();
        this.zzaHK = false;
        this.zzaHL = new AtomicInteger(0);
        this.zzaHM = false;
        this.mLock = new Object();
        this.zzaHG = com_google_android_gms_common_internal_zzae;
        this.mHandler = new Handler(looper, this);
    }

    public final boolean handleMessage(Message message) {
        if (message.what == 1) {
            ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) message.obj;
            synchronized (this.mLock) {
                if (this.zzaHK && this.zzaHG.isConnected() && this.zzaHH.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(this.zzaHG.zzoA());
                }
            }
            return true;
        }
        Log.wtf("GmsClientEvents", new StringBuilder(45).append("Don't know how to handle message: ").append(message.what).toString(), new Exception());
        return false;
    }

    public final boolean isConnectionCallbacksRegistered(ConnectionCallbacks connectionCallbacks) {
        boolean contains;
        zzbr.zzu(connectionCallbacks);
        synchronized (this.mLock) {
            contains = this.zzaHH.contains(connectionCallbacks);
        }
        return contains;
    }

    public final boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener onConnectionFailedListener) {
        boolean contains;
        zzbr.zzu(onConnectionFailedListener);
        synchronized (this.mLock) {
            contains = this.zzaHJ.contains(onConnectionFailedListener);
        }
        return contains;
    }

    public final void registerConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
        zzbr.zzu(connectionCallbacks);
        synchronized (this.mLock) {
            if (this.zzaHH.contains(connectionCallbacks)) {
                String valueOf = String.valueOf(connectionCallbacks);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf(valueOf).length() + 62).append("registerConnectionCallbacks(): listener ").append(valueOf).append(" is already registered").toString());
            } else {
                this.zzaHH.add(connectionCallbacks);
            }
        }
        if (this.zzaHG.isConnected()) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, connectionCallbacks));
        }
    }

    public final void registerConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
        zzbr.zzu(onConnectionFailedListener);
        synchronized (this.mLock) {
            if (this.zzaHJ.contains(onConnectionFailedListener)) {
                String valueOf = String.valueOf(onConnectionFailedListener);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf(valueOf).length() + 67).append("registerConnectionFailedListener(): listener ").append(valueOf).append(" is already registered").toString());
            } else {
                this.zzaHJ.add(onConnectionFailedListener);
            }
        }
    }

    public final void unregisterConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
        zzbr.zzu(connectionCallbacks);
        synchronized (this.mLock) {
            if (!this.zzaHH.remove(connectionCallbacks)) {
                String valueOf = String.valueOf(connectionCallbacks);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf(valueOf).length() + 52).append("unregisterConnectionCallbacks(): listener ").append(valueOf).append(" not found").toString());
            } else if (this.zzaHM) {
                this.zzaHI.add(connectionCallbacks);
            }
        }
    }

    public final void unregisterConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
        zzbr.zzu(onConnectionFailedListener);
        synchronized (this.mLock) {
            if (!this.zzaHJ.remove(onConnectionFailedListener)) {
                String valueOf = String.valueOf(onConnectionFailedListener);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf(valueOf).length() + 57).append("unregisterConnectionFailedListener(): listener ").append(valueOf).append(" not found").toString());
            }
        }
    }

    public final void zzaA(int i) {
        int i2 = 0;
        zzbr.zza(Looper.myLooper() == this.mHandler.getLooper(), (Object) "onUnintentionalDisconnection must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        synchronized (this.mLock) {
            this.zzaHM = true;
            ArrayList arrayList = new ArrayList(this.zzaHH);
            int i3 = this.zzaHL.get();
            arrayList = arrayList;
            int size = arrayList.size();
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) obj;
                if (this.zzaHK && this.zzaHL.get() == i3) {
                    if (this.zzaHH.contains(connectionCallbacks)) {
                        connectionCallbacks.onConnectionSuspended(i);
                    }
                }
            }
            break;
            this.zzaHI.clear();
            this.zzaHM = false;
        }
    }

    public final void zzk(ConnectionResult connectionResult) {
        int i = 0;
        zzbr.zza(Looper.myLooper() == this.mHandler.getLooper(), (Object) "onConnectionFailure must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        synchronized (this.mLock) {
            ArrayList arrayList = new ArrayList(this.zzaHJ);
            int i2 = this.zzaHL.get();
            arrayList = arrayList;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                OnConnectionFailedListener onConnectionFailedListener = (OnConnectionFailedListener) obj;
                if (this.zzaHK && this.zzaHL.get() == i2) {
                    if (this.zzaHJ.contains(onConnectionFailedListener)) {
                        onConnectionFailedListener.onConnectionFailed(connectionResult);
                    }
                }
                return;
            }
        }
    }

    public final void zzn(Bundle bundle) {
        boolean z = true;
        int i = 0;
        zzbr.zza(Looper.myLooper() == this.mHandler.getLooper(), (Object) "onConnectionSuccess must only be called on the Handler thread");
        synchronized (this.mLock) {
            zzbr.zzae(!this.zzaHM);
            this.mHandler.removeMessages(1);
            this.zzaHM = true;
            if (this.zzaHI.size() != 0) {
                z = false;
            }
            zzbr.zzae(z);
            ArrayList arrayList = new ArrayList(this.zzaHH);
            int i2 = this.zzaHL.get();
            arrayList = arrayList;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) obj;
                if (this.zzaHK && this.zzaHG.isConnected() && this.zzaHL.get() == i2) {
                    if (!this.zzaHI.contains(connectionCallbacks)) {
                        connectionCallbacks.onConnected(bundle);
                    }
                }
            }
            break;
            this.zzaHI.clear();
            this.zzaHM = false;
        }
    }

    public final void zzry() {
        this.zzaHK = false;
        this.zzaHL.incrementAndGet();
    }

    public final void zzrz() {
        this.zzaHK = true;
    }
}
