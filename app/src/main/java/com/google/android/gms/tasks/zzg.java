package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzg<TResult> implements zzk<TResult> {
    private final Object mLock;
    private final Executor zzbEs;
    private OnFailureListener zzbMa;

    public zzg(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.mLock = new Object();
        this.zzbEs = executor;
        this.zzbMa = onFailureListener;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzbMa = null;
        }
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        if (!task.isSuccessful()) {
            synchronized (this.mLock) {
                if (this.zzbMa == null) {
                    return;
                }
                this.zzbEs.execute(new zzh(this, task));
            }
        }
    }
}
