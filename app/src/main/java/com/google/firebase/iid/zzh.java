package com.google.firebase.iid;

import android.content.BroadcastReceiver.PendingResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.google.android.gms.common.stats.zza;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzh implements ServiceConnection {
    private final Intent zzcnj;
    private final ScheduledExecutorService zzcnk;
    private final Queue<zzd> zzcnl;
    private zzf zzcnm;
    private boolean zzcnn;
    private final Context zzqG;

    public zzh(Context context, String str) {
        this(context, str, new ScheduledThreadPoolExecutor(0));
    }

    @VisibleForTesting
    private zzh(Context context, String str, ScheduledExecutorService scheduledExecutorService) {
        this.zzcnl = new LinkedList();
        this.zzcnn = false;
        this.zzqG = context.getApplicationContext();
        this.zzcnj = new Intent(str).setPackage(this.zzqG.getPackageName());
        this.zzcnk = scheduledExecutorService;
    }

    private final synchronized void zzKd() {
        if (Log.isLoggable("EnhancedIntentService", RainSurfaceView.RAIN_LEVEL_DOWNPOUR)) {
            Log.d("EnhancedIntentService", "flush queue called");
        }
        while (!this.zzcnl.isEmpty()) {
            if (Log.isLoggable("EnhancedIntentService", RainSurfaceView.RAIN_LEVEL_DOWNPOUR)) {
                Log.d("EnhancedIntentService", "found intent to be delivered");
            }
            if (this.zzcnm == null || !this.zzcnm.isBinderAlive()) {
                if (Log.isLoggable("EnhancedIntentService", RainSurfaceView.RAIN_LEVEL_DOWNPOUR)) {
                    Log.d("EnhancedIntentService", new StringBuilder(39).append("binder is dead. start connection? ").append(!this.zzcnn).toString());
                }
                if (!this.zzcnn) {
                    this.zzcnn = true;
                    try {
                        if (!zza.zzrT().zza(this.zzqG, this.zzcnj, this, R.styleable.AppCompatTheme_editTextBackground)) {
                            Log.e("EnhancedIntentService", "binding to the service failed");
                            while (!this.zzcnl.isEmpty()) {
                                ((zzd) this.zzcnl.poll()).finish();
                            }
                        }
                    } catch (Throwable e) {
                        Log.e("EnhancedIntentService", "Exception while binding the service", e);
                    }
                }
            } else {
                if (Log.isLoggable("EnhancedIntentService", RainSurfaceView.RAIN_LEVEL_DOWNPOUR)) {
                    Log.d("EnhancedIntentService", "binder is alive, sending the intent.");
                }
                this.zzcnm.zza((zzd) this.zzcnl.poll());
            }
        }
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this) {
            this.zzcnn = false;
            this.zzcnm = (zzf) iBinder;
            if (Log.isLoggable("EnhancedIntentService", RainSurfaceView.RAIN_LEVEL_DOWNPOUR)) {
                String valueOf = String.valueOf(componentName);
                Log.d("EnhancedIntentService", new StringBuilder(String.valueOf(valueOf).length() + 20).append("onServiceConnected: ").append(valueOf).toString());
            }
            zzKd();
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        if (Log.isLoggable("EnhancedIntentService", RainSurfaceView.RAIN_LEVEL_DOWNPOUR)) {
            String valueOf = String.valueOf(componentName);
            Log.d("EnhancedIntentService", new StringBuilder(String.valueOf(valueOf).length() + 23).append("onServiceDisconnected: ").append(valueOf).toString());
        }
        zzKd();
    }

    public final synchronized void zza(Intent intent, PendingResult pendingResult) {
        if (Log.isLoggable("EnhancedIntentService", RainSurfaceView.RAIN_LEVEL_DOWNPOUR)) {
            Log.d("EnhancedIntentService", "new intent queued in the bind-strategy delivery");
        }
        this.zzcnl.add(new zzd(intent, pendingResult, this.zzcnk));
        zzKd();
    }
}
