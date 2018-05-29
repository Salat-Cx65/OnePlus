package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzbdh implements OnCompleteListener<TResult> {
    private /* synthetic */ zzbdf zzaCW;
    private /* synthetic */ TaskCompletionSource zzaCX;

    zzbdh(zzbdf com_google_android_gms_internal_zzbdf, TaskCompletionSource taskCompletionSource) {
        this.zzaCW = com_google_android_gms_internal_zzbdf;
        this.zzaCX = taskCompletionSource;
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzaCW.zzaCU.remove(this.zzaCX);
    }
}
