package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzbr;
import java.lang.ref.WeakReference;

public final class zzbge<R extends Result> extends TransformedResult<R> implements ResultCallback<R> {
    private final Object zzaBY;
    private final WeakReference<GoogleApiClient> zzaCa;
    private ResultTransform<? super R, ? extends Result> zzaFc;
    private zzbge<? extends Result> zzaFd;
    private volatile ResultCallbacks<? super R> zzaFe;
    private PendingResult<R> zzaFf;
    private Status zzaFg;
    private final zzbgg zzaFh;
    private boolean zzaFi;

    public zzbge(WeakReference<GoogleApiClient> weakReference) {
        this.zzaFc = null;
        this.zzaFd = null;
        this.zzaFe = null;
        this.zzaFf = null;
        this.zzaBY = new Object();
        this.zzaFg = null;
        this.zzaFi = false;
        zzbr.zzb((Object) weakReference, (Object) "GoogleApiClient reference must not be null");
        this.zzaCa = weakReference;
        GoogleApiClient googleApiClient = (GoogleApiClient) this.zzaCa.get();
        this.zzaFh = new zzbgg(this, googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
    }

    private static void zzc(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (Throwable e) {
                String valueOf = String.valueOf(result);
                Log.w("TransformedResultImpl", new StringBuilder(String.valueOf(valueOf).length() + 18).append("Unable to release ").append(valueOf).toString(), e);
            }
        }
    }

    private final void zzqH() {
        if (this.zzaFc != null || this.zzaFe != null) {
            GoogleApiClient googleApiClient = (GoogleApiClient) this.zzaCa.get();
            if (!(this.zzaFi || this.zzaFc == null || googleApiClient == null)) {
                googleApiClient.zza(this);
                this.zzaFi = true;
            }
            if (this.zzaFg != null) {
                zzw(this.zzaFg);
            } else if (this.zzaFf != null) {
                this.zzaFf.setResultCallback(this);
            }
        }
    }

    private final boolean zzqJ() {
        return (this.zzaFe == null || ((GoogleApiClient) this.zzaCa.get()) == null) ? false : true;
    }

    private final void zzv(Status status) {
        synchronized (this.zzaBY) {
            this.zzaFg = status;
            zzw(this.zzaFg);
        }
    }

    private final void zzw(Status status) {
        synchronized (this.zzaBY) {
            if (this.zzaFc != null) {
                Object onFailure = this.zzaFc.onFailure(status);
                zzbr.zzb(onFailure, (Object) "onFailure must not return null");
                this.zzaFd.zzv(onFailure);
            } else if (zzqJ()) {
                this.zzaFe.onFailure(status);
            }
        }
    }

    public final void andFinally(@NonNull ResultCallbacks<? super R> resultCallbacks) {
        boolean z = true;
        synchronized (this.zzaBY) {
            zzbr.zza(this.zzaFe == null, (Object) "Cannot call andFinally() twice.");
            if (this.zzaFc != null) {
                z = false;
            }
            zzbr.zza(z, (Object) "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zzaFe = resultCallbacks;
            zzqH();
        }
    }

    public final void onResult(R r) {
        synchronized (this.zzaBY) {
            if (!r.getStatus().isSuccess()) {
                zzv(r.getStatus());
                zzc((Result) r);
            } else if (this.zzaFc != null) {
                zzbfs.zzqh().submit(new zzbgf(this, r));
            } else if (zzqJ()) {
                this.zzaFe.onSuccess(r);
            }
        }
    }

    @NonNull
    public final <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult com_google_android_gms_internal_zzbge;
        boolean z = true;
        synchronized (this.zzaBY) {
            zzbr.zza(this.zzaFc == null, (Object) "Cannot call then() twice.");
            if (this.zzaFe != null) {
                z = false;
            }
            zzbr.zza(z, (Object) "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zzaFc = resultTransform;
            com_google_android_gms_internal_zzbge = new zzbge(this.zzaCa);
            this.zzaFd = com_google_android_gms_internal_zzbge;
            zzqH();
        }
        return com_google_android_gms_internal_zzbge;
    }

    public final void zza(PendingResult<?> pendingResult) {
        synchronized (this.zzaBY) {
            this.zzaFf = pendingResult;
            zzqH();
        }
    }

    final void zzqI() {
        this.zzaFe = null;
    }
}
