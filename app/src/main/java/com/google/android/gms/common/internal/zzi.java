package com.google.android.gms.common.internal;

import android.util.Log;

public abstract class zzi<TListener> {
    private TListener mListener;
    private /* synthetic */ zzd zzaHg;
    private boolean zzaHh;

    public zzi(zzd com_google_android_gms_common_internal_zzd, TListener tListener) {
        this.zzaHg = com_google_android_gms_common_internal_zzd;
        this.mListener = tListener;
        this.zzaHh = false;
    }

    public final void removeListener() {
        synchronized (this) {
            this.mListener = null;
        }
    }

    public final void unregister() {
        removeListener();
        synchronized (zzd.zzf(this.zzaHg)) {
            zzd.zzf(this.zzaHg).remove(this);
        }
    }

    public final void zzri() {
        synchronized (this) {
            Object obj = this.mListener;
            if (this.zzaHh) {
                String valueOf = String.valueOf(this);
                Log.w("GmsClient", new StringBuilder(String.valueOf(valueOf).length() + 47).append("Callback proxy ").append(valueOf).append(" being reused. This is not safe.").toString());
            }
        }
        if (obj != null) {
            try {
                zzs(obj);
            } catch (RuntimeException e) {
                throw e;
            }
        }
        synchronized (this) {
            this.zzaHh = true;
        }
        unregister();
    }

    protected abstract void zzs(TListener tListener);
}
