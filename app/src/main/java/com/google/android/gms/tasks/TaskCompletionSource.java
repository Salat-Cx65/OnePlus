package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

public class TaskCompletionSource<TResult> {
    private final zzn<TResult> zzbMg;

    public TaskCompletionSource() {
        this.zzbMg = new zzn();
    }

    @NonNull
    public Task<TResult> getTask() {
        return this.zzbMg;
    }

    public void setException(@NonNull Exception exception) {
        this.zzbMg.setException(exception);
    }

    public void setResult(TResult tResult) {
        this.zzbMg.setResult(tResult);
    }

    public boolean trySetException(@NonNull Exception exception) {
        return this.zzbMg.trySetException(exception);
    }

    public boolean trySetResult(TResult tResult) {
        return this.zzbMg.trySetResult(tResult);
    }
}
