package com.google.android.gms.internal;

import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResult.zza;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzap;
import com.google.android.gms.common.internal.zzbr;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class zzbcq<R extends Result> extends PendingResult<R> {
    static final ThreadLocal<Boolean> zzaBX;
    private Status mStatus;
    private boolean zzJ;
    private final Object zzaBY;
    private zzbcs<R> zzaBZ;
    private R zzaBl;
    private WeakReference<GoogleApiClient> zzaCa;
    private final ArrayList<zza> zzaCb;
    private ResultCallback<? super R> zzaCc;
    private final AtomicReference<zzbgj> zzaCd;
    private zzbct zzaCe;
    private volatile boolean zzaCf;
    private boolean zzaCg;
    private zzap zzaCh;
    private volatile zzbge<R> zzaCi;
    private boolean zzaCj;
    private final CountDownLatch zztM;

    static {
        zzaBX = new zzbcr();
    }

    @Deprecated
    zzbcq() {
        this.zzaBY = new Object();
        this.zztM = new CountDownLatch(1);
        this.zzaCb = new ArrayList();
        this.zzaCd = new AtomicReference();
        this.zzaCj = false;
        this.zzaBZ = new zzbcs(Looper.getMainLooper());
        this.zzaCa = new WeakReference(null);
    }

    @Deprecated
    protected zzbcq(Looper looper) {
        this.zzaBY = new Object();
        this.zztM = new CountDownLatch(1);
        this.zzaCb = new ArrayList();
        this.zzaCd = new AtomicReference();
        this.zzaCj = false;
        this.zzaBZ = new zzbcs(looper);
        this.zzaCa = new WeakReference(null);
    }

    protected zzbcq(GoogleApiClient googleApiClient) {
        this.zzaBY = new Object();
        this.zztM = new CountDownLatch(1);
        this.zzaCb = new ArrayList();
        this.zzaCd = new AtomicReference();
        this.zzaCj = false;
        this.zzaBZ = new zzbcs(googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
        this.zzaCa = new WeakReference(googleApiClient);
    }

    private final R get() {
        R r;
        boolean z = true;
        synchronized (this.zzaBY) {
            if (this.zzaCf) {
                z = false;
            }
            zzbr.zza(z, (Object) "Result has already been consumed.");
            zzbr.zza(isReady(), (Object) "Result is not ready.");
            r = this.zzaBl;
            this.zzaBl = null;
            this.zzaCc = null;
            this.zzaCf = true;
        }
        zzbgj com_google_android_gms_internal_zzbgj = (zzbgj) this.zzaCd.getAndSet(null);
        if (com_google_android_gms_internal_zzbgj != null) {
            com_google_android_gms_internal_zzbgj.zzc(this);
        }
        return r;
    }

    private final void zzb(R r) {
        this.zzaBl = r;
        this.zzaCh = null;
        this.zztM.countDown();
        this.mStatus = this.zzaBl.getStatus();
        if (this.zzJ) {
            this.zzaCc = null;
        } else if (this.zzaCc != null) {
            this.zzaBZ.removeMessages(RainSurfaceView.RAIN_LEVEL_SHOWER);
            this.zzaBZ.zza(this.zzaCc, get());
        } else if (this.zzaBl instanceof Releasable) {
            this.zzaCe = new zzbct();
        }
        ArrayList arrayList = this.zzaCb;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((zza) obj).zzo(this.mStatus);
        }
        this.zzaCb.clear();
    }

    public static void zzc(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (Throwable e) {
                String valueOf = String.valueOf(result);
                Log.w("BasePendingResult", new StringBuilder(String.valueOf(valueOf).length() + 18).append("Unable to release ").append(valueOf).toString(), e);
            }
        }
    }

    public final R await() {
        boolean z = true;
        zzbr.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "await must not be called on the UI thread");
        zzbr.zza(!this.zzaCf, (Object) "Result has already been consumed");
        if (this.zzaCi != null) {
            z = false;
        }
        zzbr.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            this.zztM.await();
        } catch (InterruptedException e) {
            zzs(Status.zzaBp);
        }
        zzbr.zza(isReady(), (Object) "Result is not ready.");
        return get();
    }

    public final R await(long j, TimeUnit timeUnit) {
        boolean z = true;
        boolean z2 = j <= 0 || Looper.myLooper() != Looper.getMainLooper();
        zzbr.zza(z2, (Object) "await must not be called on the UI thread when time is greater than zero.");
        zzbr.zza(!this.zzaCf, (Object) "Result has already been consumed.");
        if (this.zzaCi != null) {
            z = false;
        }
        zzbr.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            if (!this.zztM.await(j, timeUnit)) {
                zzs(Status.zzaBr);
            }
        } catch (InterruptedException e) {
            zzs(Status.zzaBp);
        }
        zzbr.zza(isReady(), (Object) "Result is not ready.");
        return get();
    }

    public void cancel() {
        synchronized (this.zzaBY) {
            if (this.zzJ || this.zzaCf) {
                return;
            }
            if (this.zzaCh != null) {
                try {
                    this.zzaCh.cancel();
                } catch (RemoteException e) {
                }
            }
            zzc(this.zzaBl);
            this.zzJ = true;
            zzb(zzb(Status.zzaBs));
        }
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.zzaBY) {
            z = this.zzJ;
        }
        return z;
    }

    public final boolean isReady() {
        return this.zztM.getCount() == 0;
    }

    public final void setResult(R r) {
        boolean z = true;
        synchronized (this.zzaBY) {
            if (this.zzaCg || this.zzJ) {
                zzc(r);
                return;
            }
            if (isReady()) {
            }
            zzbr.zza(!isReady(), (Object) "Results have already been set");
            if (this.zzaCf) {
                z = false;
            }
            zzbr.zza(z, (Object) "Result has already been consumed");
            zzb((Result) r);
        }
    }

    public final void setResultCallback(ResultCallback<? super R> resultCallback) {
        boolean z = true;
        synchronized (this.zzaBY) {
            if (resultCallback == null) {
                this.zzaCc = null;
                return;
            }
            zzbr.zza(!this.zzaCf, (Object) "Result has already been consumed.");
            if (this.zzaCi != null) {
                z = false;
            }
            zzbr.zza(z, (Object) "Cannot set callbacks if then() has been called.");
            if (isCanceled()) {
                return;
            }
            if (isReady()) {
                this.zzaBZ.zza(resultCallback, get());
            } else {
                this.zzaCc = resultCallback;
            }
        }
    }

    public final void setResultCallback(ResultCallback<? super R> resultCallback, long j, TimeUnit timeUnit) {
        boolean z = true;
        synchronized (this.zzaBY) {
            if (resultCallback == null) {
                this.zzaCc = null;
                return;
            }
            zzbr.zza(!this.zzaCf, (Object) "Result has already been consumed.");
            if (this.zzaCi != null) {
                z = false;
            }
            zzbr.zza(z, (Object) "Cannot set callbacks if then() has been called.");
            if (isCanceled()) {
                return;
            }
            if (isReady()) {
                this.zzaBZ.zza(resultCallback, get());
            } else {
                this.zzaCc = resultCallback;
                zzbcs com_google_android_gms_internal_zzbcs = this.zzaBZ;
                com_google_android_gms_internal_zzbcs.sendMessageDelayed(com_google_android_gms_internal_zzbcs.obtainMessage(RainSurfaceView.RAIN_LEVEL_SHOWER, this), timeUnit.toMillis(j));
            }
        }
    }

    public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult<S> then;
        boolean z = true;
        zzbr.zza(!this.zzaCf, (Object) "Result has already been consumed.");
        synchronized (this.zzaBY) {
            zzbr.zza(this.zzaCi == null, (Object) "Cannot call then() twice.");
            zzbr.zza(this.zzaCc == null, (Object) "Cannot call then() if callbacks are set.");
            if (this.zzJ) {
                z = false;
            }
            zzbr.zza(z, (Object) "Cannot call then() if result was canceled.");
            this.zzaCj = true;
            this.zzaCi = new zzbge(this.zzaCa);
            then = this.zzaCi.then(resultTransform);
            if (isReady()) {
                this.zzaBZ.zza(this.zzaCi, get());
            } else {
                this.zzaCc = this.zzaCi;
            }
        }
        return then;
    }

    public final void zza(zza com_google_android_gms_common_api_PendingResult_zza) {
        zzbr.zzb(com_google_android_gms_common_api_PendingResult_zza != null, (Object) "Callback cannot be null.");
        synchronized (this.zzaBY) {
            if (isReady()) {
                com_google_android_gms_common_api_PendingResult_zza.zzo(this.mStatus);
            } else {
                this.zzaCb.add(com_google_android_gms_common_api_PendingResult_zza);
            }
        }
    }

    protected final void zza(zzap com_google_android_gms_common_internal_zzap) {
        synchronized (this.zzaBY) {
            this.zzaCh = com_google_android_gms_common_internal_zzap;
        }
    }

    public final void zza(zzbgj com_google_android_gms_internal_zzbgj) {
        this.zzaCd.set(com_google_android_gms_internal_zzbgj);
    }

    @NonNull
    protected abstract R zzb(Status status);

    public final void zzpA() {
        boolean z = this.zzaCj || ((Boolean) zzaBX.get()).booleanValue();
        this.zzaCj = z;
    }

    public final Integer zzpm() {
        return null;
    }

    public final boolean zzpz() {
        boolean isCanceled;
        synchronized (this.zzaBY) {
            if (((GoogleApiClient) this.zzaCa.get()) == null || !this.zzaCj) {
                cancel();
            }
            isCanceled = isCanceled();
        }
        return isCanceled;
    }

    public final void zzs(Status status) {
        synchronized (this.zzaBY) {
            if (!isReady()) {
                setResult(zzb(status));
                this.zzaCg = true;
            }
        }
    }
}
