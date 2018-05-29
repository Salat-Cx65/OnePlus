package com.google.android.gms.tasks;

final class zzd implements Runnable {
    private /* synthetic */ Task zzbLV;
    private /* synthetic */ zzc zzbLX;

    zzd(zzc com_google_android_gms_tasks_zzc, Task task) {
        this.zzbLX = com_google_android_gms_tasks_zzc;
        this.zzbLV = task;
    }

    public final void run() {
        try {
            Task task = (Task) zzc.zza(this.zzbLX).then(this.zzbLV);
            if (task == null) {
                this.zzbLX.onFailure(new NullPointerException("Continuation returned null"));
                return;
            }
            task.addOnSuccessListener(TaskExecutors.zzbMh, this.zzbLX);
            task.addOnFailureListener(TaskExecutors.zzbMh, this.zzbLX);
        } catch (Exception e) {
            if (e.getCause() instanceof Exception) {
                zzc.zzb(this.zzbLX).setException((Exception) e.getCause());
            } else {
                zzc.zzb(this.zzbLX).setException(e);
            }
        } catch (Exception e2) {
            zzc.zzb(this.zzbLX).setException(e2);
        }
    }
}
