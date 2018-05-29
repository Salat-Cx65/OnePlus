package com.google.android.gms.tasks;

final class zzb implements Runnable {
    private /* synthetic */ Task zzbLV;
    private /* synthetic */ zza zzbLW;

    zzb(zza com_google_android_gms_tasks_zza, Task task) {
        this.zzbLW = com_google_android_gms_tasks_zza;
        this.zzbLV = task;
    }

    public final void run() {
        try {
            zza.zzb(this.zzbLW).setResult(zza.zza(this.zzbLW).then(this.zzbLV));
        } catch (Exception e) {
            if (e.getCause() instanceof Exception) {
                zza.zzb(this.zzbLW).setException((Exception) e.getCause());
            } else {
                zza.zzb(this.zzbLW).setException(e);
            }
        } catch (Exception e2) {
            zza.zzb(this.zzbLW).setException(e2);
        }
    }
}
