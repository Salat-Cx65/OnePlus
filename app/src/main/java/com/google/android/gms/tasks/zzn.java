package com.google.android.gms.tasks;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbfe;
import com.google.android.gms.internal.zzbff;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

final class zzn<TResult> extends Task<TResult> {
    private final Object mLock;
    private final zzl<TResult> zzbMi;
    private boolean zzbMj;
    private TResult zzbMk;
    private Exception zzbMl;

    static class zza extends zzbfe {
        private final List<WeakReference<zzk<?>>> mListeners;

        private zza(zzbff com_google_android_gms_internal_zzbff) {
            super(com_google_android_gms_internal_zzbff);
            this.mListeners = new ArrayList();
            this.zzaEI.zza("TaskOnStopCallback", (zzbfe) this);
        }

        public static zza zzr(Activity activity) {
            zzbff zzn = zzn(activity);
            zza com_google_android_gms_tasks_zzn_zza = (zza) zzn.zza("TaskOnStopCallback", zza.class);
            return com_google_android_gms_tasks_zzn_zza == null ? new zza(zzn) : com_google_android_gms_tasks_zzn_zza;
        }

        @MainThread
        public final void onStop() {
            synchronized (this.mListeners) {
                for (WeakReference weakReference : this.mListeners) {
                    zzk com_google_android_gms_tasks_zzk = (zzk) weakReference.get();
                    if (com_google_android_gms_tasks_zzk != null) {
                        com_google_android_gms_tasks_zzk.cancel();
                    }
                }
                this.mListeners.clear();
            }
        }

        public final <T> void zzb(zzk<T> com_google_android_gms_tasks_zzk_T) {
            synchronized (this.mListeners) {
                this.mListeners.add(new WeakReference(com_google_android_gms_tasks_zzk_T));
            }
        }
    }

    zzn() {
        this.mLock = new Object();
        this.zzbMi = new zzl();
    }

    private final void zzDD() {
        zzbr.zza(this.zzbMj, (Object) "Task is not yet complete");
    }

    private final void zzDE() {
        zzbr.zza(!this.zzbMj, (Object) "Task is already complete");
    }

    private final void zzDF() {
        synchronized (this.mLock) {
            if (this.zzbMj) {
                this.zzbMi.zza((Task) this);
                return;
            }
        }
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Activity activity, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        zzk com_google_android_gms_tasks_zze = new zze(TaskExecutors.MAIN_THREAD, onCompleteListener);
        this.zzbMi.zza(com_google_android_gms_tasks_zze);
        zza.zzr(activity).zzb(com_google_android_gms_tasks_zze);
        zzDF();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull OnCompleteListener<TResult> onCompleteListener) {
        return addOnCompleteListener(TaskExecutors.MAIN_THREAD, (OnCompleteListener) onCompleteListener);
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzbMi.zza(new zze(executor, onCompleteListener));
        zzDF();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        zzk com_google_android_gms_tasks_zzg = new zzg(TaskExecutors.MAIN_THREAD, onFailureListener);
        this.zzbMi.zza(com_google_android_gms_tasks_zzg);
        zza.zzr(activity).zzb(com_google_android_gms_tasks_zzg);
        zzDF();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzbMi.zza(new zzg(executor, onFailureListener));
        zzDF();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zzk com_google_android_gms_tasks_zzi = new zzi(TaskExecutors.MAIN_THREAD, onSuccessListener);
        this.zzbMi.zza(com_google_android_gms_tasks_zzi);
        zza.zzr(activity).zzb(com_google_android_gms_tasks_zzi);
        zzDF();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        return addOnSuccessListener(TaskExecutors.MAIN_THREAD, (OnSuccessListener) onSuccessListener);
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzbMi.zza(new zzi(executor, onSuccessListener));
        zzDF();
        return this;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation) {
        Task com_google_android_gms_tasks_zzn = new zzn();
        this.zzbMi.zza(new zza(executor, continuation, com_google_android_gms_tasks_zzn));
        zzDF();
        return com_google_android_gms_tasks_zzn;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        Task com_google_android_gms_tasks_zzn = new zzn();
        this.zzbMi.zza(new zzc(executor, continuation, com_google_android_gms_tasks_zzn));
        zzDF();
        return com_google_android_gms_tasks_zzn;
    }

    @Nullable
    public final Exception getException() {
        Exception exception;
        synchronized (this.mLock) {
            exception = this.zzbMl;
        }
        return exception;
    }

    public final TResult getResult() {
        TResult tResult;
        synchronized (this.mLock) {
            zzDD();
            if (this.zzbMl != null) {
                throw new RuntimeExecutionException(this.zzbMl);
            }
            tResult = this.zzbMk;
        }
        return tResult;
    }

    public final <X extends Throwable> TResult getResult(@NonNull Class<X> cls) throws Throwable {
        TResult tResult;
        synchronized (this.mLock) {
            zzDD();
            if (cls.isInstance(this.zzbMl)) {
                throw ((Throwable) cls.cast(this.zzbMl));
            } else if (this.zzbMl != null) {
                throw new RuntimeExecutionException(this.zzbMl);
            } else {
                tResult = this.zzbMk;
            }
        }
        return tResult;
    }

    public final boolean isComplete() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzbMj;
        }
        return z;
    }

    public final boolean isSuccessful() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzbMj && this.zzbMl == null;
        }
        return z;
    }

    public final void setException(@NonNull Exception exception) {
        zzbr.zzb((Object) exception, (Object) "Exception must not be null");
        synchronized (this.mLock) {
            zzDE();
            this.zzbMj = true;
            this.zzbMl = exception;
        }
        this.zzbMi.zza((Task) this);
    }

    public final void setResult(TResult tResult) {
        synchronized (this.mLock) {
            zzDE();
            this.zzbMj = true;
            this.zzbMk = tResult;
        }
        this.zzbMi.zza((Task) this);
    }

    public final boolean trySetException(@NonNull Exception exception) {
        boolean z = true;
        zzbr.zzb((Object) exception, (Object) "Exception must not be null");
        synchronized (this.mLock) {
            if (this.zzbMj) {
                z = false;
            } else {
                this.zzbMj = true;
                this.zzbMl = exception;
                this.zzbMi.zza((Task) this);
            }
        }
        return z;
    }

    public final boolean trySetResult(TResult tResult) {
        boolean z = true;
        synchronized (this.mLock) {
            if (this.zzbMj) {
                z = false;
            } else {
                this.zzbMj = true;
                this.zzbMk = tResult;
                this.zzbMi.zza((Task) this);
            }
        }
        return z;
    }
}
