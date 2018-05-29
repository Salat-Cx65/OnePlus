package com.google.android.gms.tasks;

final class zzh implements Runnable {
    private /* synthetic */ Task zzbLV;
    private /* synthetic */ zzg zzbMb;

    zzh(zzg com_google_android_gms_tasks_zzg, Task task) {
        this.zzbMb = com_google_android_gms_tasks_zzg;
        this.zzbLV = task;
    }

    public final void run() {
        synchronized (zzg.zza(this.zzbMb)) {
            if (zzg.zzb(this.zzbMb) != null) {
                zzg.zzb(this.zzbMb).onFailure(this.zzbLV.getException());
            }
        }
    }
}
