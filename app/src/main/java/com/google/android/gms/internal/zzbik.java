package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import com.google.android.gms.common.util.zzs;
import java.lang.reflect.InvocationTargetException;

public final class zzbik {
    private static Context zzaKl;
    private static Boolean zzaKm;

    public static synchronized boolean zzaN(Context context) {
        boolean booleanValue;
        synchronized (zzbik.class) {
            Context applicationContext = context.getApplicationContext();
            if (zzaKl == null || zzaKm == null || zzaKl != applicationContext) {
                zzaKm = null;
                if (zzs.isAtLeastO()) {
                    try {
                        zzaKm = (Boolean) PackageManager.class.getDeclaredMethod("isInstantApp", new Class[0]).invoke(applicationContext.getPackageManager(), new Object[0]);
                    } catch (NoSuchMethodException e) {
                        zzaKm = Boolean.valueOf(false);
                        zzaKl = applicationContext;
                        booleanValue = zzaKm.booleanValue();
                        return booleanValue;
                    } catch (InvocationTargetException e2) {
                        zzaKm = Boolean.valueOf(false);
                        zzaKl = applicationContext;
                        booleanValue = zzaKm.booleanValue();
                        return booleanValue;
                    } catch (IllegalAccessException e3) {
                        zzaKm = Boolean.valueOf(false);
                        zzaKl = applicationContext;
                        booleanValue = zzaKm.booleanValue();
                        return booleanValue;
                    }
                }
                try {
                    context.getClassLoader().loadClass("com.google.android.instantapps.supervisor.InstantAppsRuntime");
                    zzaKm = Boolean.valueOf(true);
                } catch (ClassNotFoundException e4) {
                    zzaKm = Boolean.valueOf(false);
                }
                zzaKl = applicationContext;
                booleanValue = zzaKm.booleanValue();
            } else {
                booleanValue = zzaKm.booleanValue();
            }
        }
        return booleanValue;
    }
}
