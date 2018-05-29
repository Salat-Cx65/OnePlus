package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzc<TResult, TContinuationResult> implements OnFailureListener, OnSuccessListener<TContinuationResult>, zzk<TResult> {
    private final Executor zzbEs;
    private final Continuation<TResult, Task<TContinuationResult>> zzbLT;
    private final zzn<TContinuationResult> zzbLU;

    public zzc(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation, @NonNull zzn<TContinuationResult> com_google_android_gms_tasks_zzn_TContinuationResult) {
        this.zzbEs = executor;
        this.zzbLT = continuation;
        this.zzbLU = com_google_android_gms_tasks_zzn_TContinuationResult;
    }

    public final void cancel() {
        throw new UnsupportedOperationException();
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzbEs.execute(new zzd(this, task));
    }

    public final void onFailure(@NonNull Exception exception) {
        this.zzbLU.setException(exception);
    }

    public final void onSuccess(TContinuationResult tContinuationResult) {
        this.zzbLU.setResult(tContinuationResult);
    }
}
