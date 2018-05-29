package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzbr;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class zza implements ServiceConnection {
    private boolean zzazX;
    private final BlockingQueue<IBinder> zzazY;

    public zza() {
        this.zzazX = false;
        this.zzazY = new LinkedBlockingQueue();
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.zzazY.add(iBinder);
    }

    public final void onServiceDisconnected(ComponentName componentName) {
    }

    public final IBinder zza(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        zzbr.zzcG("BlockingServiceConnection.getServiceWithTimeout() called on main thread");
        if (this.zzazX) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.zzazX = true;
        IBinder iBinder = (IBinder) this.zzazY.poll(10000, timeUnit);
        if (iBinder != null) {
            return iBinder;
        }
        throw new TimeoutException("Timed out waiting for the service connection");
    }

    public final IBinder zzoT() throws InterruptedException {
        zzbr.zzcG("BlockingServiceConnection.getService() called on main thread");
        if (this.zzazX) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.zzazX = true;
        return (IBinder) this.zzazY.take();
    }
}
