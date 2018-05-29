package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.android.gms.common.util.zzs;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public final class zzbci implements ActivityLifecycleCallbacks, ComponentCallbacks2 {
    private static final zzbci zzaBL;
    private final ArrayList<zzbcj> mListeners;
    private final AtomicBoolean zzaBM;
    private final AtomicBoolean zzaBN;
    private boolean zzafM;

    static {
        zzaBL = new zzbci();
    }

    private zzbci() {
        this.zzaBM = new AtomicBoolean();
        this.zzaBN = new AtomicBoolean();
        this.mListeners = new ArrayList();
        this.zzafM = false;
    }

    public static void zza(Application application) {
        synchronized (zzaBL) {
            if (!zzaBL.zzafM) {
                application.registerActivityLifecycleCallbacks(zzaBL);
                application.registerComponentCallbacks(zzaBL);
                zzaBL.zzafM = true;
            }
        }
    }

    private final void zzac(boolean z) {
        synchronized (zzaBL) {
            ArrayList arrayList = this.mListeners;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((zzbcj) obj).zzac(z);
            }
        }
    }

    public static zzbci zzpt() {
        return zzaBL;
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        boolean compareAndSet = this.zzaBM.compareAndSet(true, false);
        this.zzaBN.set(true);
        if (compareAndSet) {
            zzac(false);
        }
    }

    public final void onActivityDestroyed(Activity activity) {
    }

    public final void onActivityPaused(Activity activity) {
    }

    public final void onActivityResumed(Activity activity) {
        boolean compareAndSet = this.zzaBM.compareAndSet(true, false);
        this.zzaBN.set(true);
        if (compareAndSet) {
            zzac(false);
        }
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void onLowMemory() {
    }

    public final void onTrimMemory(int i) {
        if (i == 20 && this.zzaBM.compareAndSet(false, true)) {
            this.zzaBN.set(true);
            zzac(true);
        }
    }

    public final void zza(zzbcj com_google_android_gms_internal_zzbcj) {
        synchronized (zzaBL) {
            this.mListeners.add(com_google_android_gms_internal_zzbcj);
        }
    }

    @TargetApi(16)
    public final boolean zzab(boolean z) {
        if (!this.zzaBN.get()) {
            if (!zzs.zzrY()) {
                return true;
            }
            RunningAppProcessInfo runningAppProcessInfo = new RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(runningAppProcessInfo);
            if (!this.zzaBN.getAndSet(true) && runningAppProcessInfo.importance > 100) {
                this.zzaBM.set(true);
            }
        }
        return this.zzaBM.get();
    }

    public final boolean zzpu() {
        return this.zzaBM.get();
    }
}
