package com.google.android.gms.tasks;

final class zzf implements Runnable {
    private /* synthetic */ Task zzbLV;
    private /* synthetic */ zze zzbLZ;

    zzf(zze com_google_android_gms_tasks_zze, Task task) {
        this.zzbLZ = com_google_android_gms_tasks_zze;
        this.zzbLV = task;
    }

    public final void run() {
        synchronized (zze.zza(this.zzbLZ)) {
            if (zze.zzb(this.zzbLZ) != null) {
                zze.zzb(this.zzbLZ).onComplete(this.zzbLV);
            }
        }
    }
}
