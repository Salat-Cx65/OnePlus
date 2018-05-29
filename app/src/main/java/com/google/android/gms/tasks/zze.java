package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zze<TResult> implements zzk<TResult> {
    private final Object mLock;
    private final Executor zzbEs;
    private OnCompleteListener<TResult> zzbLY;

    public zze(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.mLock = new Object();
        this.zzbEs = executor;
        this.zzbLY = onCompleteListener;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzbLY = null;
        }
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        synchronized (this.mLock) {
            if (this.zzbLY == null) {
                return;
            }
            this.zzbEs.execute(new zzf(this, task));
        }
    }
}
