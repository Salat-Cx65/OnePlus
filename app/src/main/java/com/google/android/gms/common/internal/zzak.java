package com.google.android.gms.common.internal;

import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzak {
    private static int zzaIb;
    private static final String zzaIc;
    private final String zzaId;
    private final String zzaIe;

    static {
        zzaIb = 15;
        zzaIc = null;
    }

    public zzak(String str) {
        this(str, null);
    }

    public zzak(String str, String str2) {
        zzbr.zzb((Object) str, (Object) "log tag cannot be null");
        zzbr.zzb(str.length() <= 23, "tag \"%s\" is longer than the %d character maximum", str, Integer.valueOf(R.styleable.Toolbar_titleMarginEnd));
        this.zzaId = str;
        if (str2 == null || str2.length() <= 0) {
            this.zzaIe = null;
        } else {
            this.zzaIe = str2;
        }
    }

    private final boolean zzaB(int i) {
        return Log.isLoggable(this.zzaId, i);
    }

    private final String zzcE(String str) {
        return this.zzaIe == null ? str : this.zzaIe.concat(str);
    }

    public final void zzb(String str, String str2, Throwable th) {
        if (zzaB(RainSurfaceView.RAIN_LEVEL_RAINSTORM)) {
            Log.i(str, zzcE(str2), th);
        }
    }

    public final void zzc(String str, String str2, Throwable th) {
        if (zzaB(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER)) {
            Log.w(str, zzcE(str2), th);
        }
    }

    public final void zzd(String str, String str2, Throwable th) {
        if (zzaB(ConnectionResult.RESOLUTION_REQUIRED)) {
            Log.e(str, zzcE(str2), th);
        }
    }

    public final void zze(String str, String str2, Throwable th) {
        if (zzaB(DetectedActivity.WALKING)) {
            Log.e(str, zzcE(str2), th);
            Log.wtf(str, zzcE(str2), th);
        }
    }

    public final void zzx(String str, String str2) {
        if (zzaB(RainSurfaceView.RAIN_LEVEL_DOWNPOUR)) {
            Log.d(str, zzcE(str2));
        }
    }

    public final void zzy(String str, String str2) {
        if (zzaB(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER)) {
            Log.w(str, zzcE(str2));
        }
    }

    public final void zzz(String str, String str2) {
        if (zzaB(ConnectionResult.RESOLUTION_REQUIRED)) {
            Log.e(str, zzcE(str2));
        }
    }
}
