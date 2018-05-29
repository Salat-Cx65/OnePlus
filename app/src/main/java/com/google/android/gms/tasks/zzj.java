package com.google.android.gms.tasks;

final class zzj implements Runnable {
    private /* synthetic */ Task zzbLV;
    private /* synthetic */ zzi zzbMd;

    zzj(zzi com_google_android_gms_tasks_zzi, Task task) {
        this.zzbMd = com_google_android_gms_tasks_zzi;
        this.zzbLV = task;
    }

    public final void run() {
        synchronized (zzi.zza(this.zzbMd)) {
            if (zzi.zzb(this.zzbMd) != null) {
                zzi.zzb(this.zzbMd).onSuccess(this.zzbLV.getResult());
            }
        }
    }
}
