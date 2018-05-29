package com.google.android.gms.tasks;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

public final class TaskExecutors {
    public static final Executor MAIN_THREAD;
    static final Executor zzbMh;

    static final class zza implements Executor {
        private final Handler mHandler;

        public zza() {
            this.mHandler = new Handler(Looper.getMainLooper());
        }

        public final void execute(@NonNull Runnable runnable) {
            this.mHandler.post(runnable);
        }
    }

    static {
        MAIN_THREAD = new zza();
        zzbMh = new zzm();
    }

    private TaskExecutors() {
    }
}
