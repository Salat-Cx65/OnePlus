package com.google.android.gms.internal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbem {
    private static final ExecutorService zzaEd;

    static {
        zzaEd = Executors.newFixedThreadPool(RainSurfaceView.RAIN_LEVEL_SHOWER, new zzbii("GAC_Executor"));
    }

    public static ExecutorService zzqh() {
        return zzaEd;
    }
}
