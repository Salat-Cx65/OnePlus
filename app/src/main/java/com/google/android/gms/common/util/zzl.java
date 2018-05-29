package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;

public final class zzl {
    private static IntentFilter zzaJQ;
    private static long zzaJR;
    private static float zzaJS;

    static {
        zzaJQ = new IntentFilter("android.intent.action.BATTERY_CHANGED");
        zzaJS = Float.NaN;
    }

    @TargetApi(20)
    public static int zzaK(Context context) {
        int i = 1;
        if (context == null || context.getApplicationContext() == null) {
            return -1;
        }
        Intent registerReceiver = context.getApplicationContext().registerReceiver(null, zzaJQ);
        int i2 = ((registerReceiver == null ? 0 : registerReceiver.getIntExtra("plugged", 0)) & 7) != 0 ? 1 : 0;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager == null) {
            return -1;
        }
        int i3 = (zzs.zzsc() ? powerManager.isInteractive() : powerManager.isScreenOn() ? 1 : 0) << 1;
        if (i2 == 0) {
            i = 0;
        }
        return i3 | i;
    }

    public static synchronized float zzaL(Context context) {
        float f;
        synchronized (zzl.class) {
            if (SystemClock.elapsedRealtime() - zzaJR >= 60000 || Float.isNaN(zzaJS)) {
                Intent registerReceiver = context.getApplicationContext().registerReceiver(null, zzaJQ);
                if (registerReceiver != null) {
                    zzaJS = ((float) registerReceiver.getIntExtra("level", -1)) / ((float) registerReceiver.getIntExtra("scale", -1));
                }
                zzaJR = SystemClock.elapsedRealtime();
                f = zzaJS;
            } else {
                f = zzaJS;
            }
        }
        return f;
    }
}
