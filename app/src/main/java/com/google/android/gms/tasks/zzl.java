package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.ArrayDeque;
import java.util.Queue;

final class zzl<TResult> {
    private final Object mLock;
    private Queue<zzk<TResult>> zzbMe;
    private boolean zzbMf;

    zzl() {
        this.mLock = new Object();
    }

    public final void zza(@NonNull Task<TResult> task) {
        synchronized (this.mLock) {
            if (this.zzbMe == null || this.zzbMf) {
                return;
            }
            this.zzbMf = true;
            while (true) {
                synchronized (this.mLock) {
                    zzk com_google_android_gms_tasks_zzk = (zzk) this.zzbMe.poll();
                    if (com_google_android_gms_tasks_zzk == null) {
                        this.zzbMf = false;
                        return;
                    }
                    com_google_android_gms_tasks_zzk.onComplete(task);
                }
            }
        }
    }

    public final void zza(@NonNull zzk<TResult> com_google_android_gms_tasks_zzk_TResult) {
        synchronized (this.mLock) {
            if (this.zzbMe == null) {
                this.zzbMe = new ArrayDeque();
            }
            this.zzbMe.add(com_google_android_gms_tasks_zzk_TResult);
        }
    }
}
