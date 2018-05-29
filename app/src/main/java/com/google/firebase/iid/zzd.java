package com.google.firebase.iid;

import android.content.BroadcastReceiver.PendingResult;
import android.content.Intent;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

final class zzd {
    final Intent intent;
    private final PendingResult zzcnc;
    private boolean zzcnd;
    private final ScheduledFuture<?> zzcne;

    zzd(Intent intent, PendingResult pendingResult, ScheduledExecutorService scheduledExecutorService) {
        this.zzcnd = false;
        this.intent = intent;
        this.zzcnc = pendingResult;
        this.zzcne = scheduledExecutorService.schedule(new zze(this, intent), 9500, TimeUnit.MILLISECONDS);
    }

    final synchronized void finish() {
        if (!this.zzcnd) {
            this.zzcnc.finish();
            this.zzcne.cancel(false);
            this.zzcnd = true;
        }
    }
}
