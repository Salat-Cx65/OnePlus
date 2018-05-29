package com.google.android.gms.common.api;

import android.support.annotation.NonNull;

public class Response<T extends Result> {
    private T zzaBl;

    protected Response(@NonNull T t) {
        this.zzaBl = t;
    }

    @NonNull
    protected T getResult() {
        return this.zzaBl;
    }

    public void setResult(@NonNull T t) {
        this.zzaBl = t;
    }
}
