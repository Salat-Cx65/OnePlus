package com.google.android.gms.common.stats;

import android.content.ComponentName;
import com.google.android.gms.common.GooglePlayServicesUtil;

public final class zzb {
    private static int LOG_LEVEL_OFF;
    public static final ComponentName zzaJh;
    private static int zzaJi;
    private static int zzaJj;
    private static int zzaJk;
    private static int zzaJl;
    private static int zzaJm;
    private static int zzaJn;
    private static int zzaJo;

    static {
        zzaJh = new ComponentName(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, "com.google.android.gms.common.stats.GmsCoreStatsService");
        LOG_LEVEL_OFF = 0;
        zzaJi = 1;
        zzaJj = 2;
        zzaJk = 4;
        zzaJl = 8;
        zzaJm = 16;
        zzaJn = 32;
        zzaJo = 1;
    }
}
