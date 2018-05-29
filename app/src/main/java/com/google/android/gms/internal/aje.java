package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationRequest;
import java.io.IOException;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class aje extends ahz<aje> implements Cloneable {
    private int zzcwY;
    private int zzcwZ;

    public aje() {
        this.zzcwY = -1;
        this.zzcwZ = 0;
        this.zzcuW = null;
        this.zzcvf = -1;
    }

    private aje zzMw() {
        try {
            return (aje) super.zzMd();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzMw();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aje)) {
            return false;
        }
        aje com_google_android_gms_internal_aje = (aje) obj;
        return this.zzcwY != com_google_android_gms_internal_aje.zzcwY ? false : this.zzcwZ != com_google_android_gms_internal_aje.zzcwZ ? false : (this.zzcuW == null || this.zzcuW.isEmpty()) ? com_google_android_gms_internal_aje.zzcuW == null || com_google_android_gms_internal_aje.zzcuW.isEmpty() : this.zzcuW.equals(com_google_android_gms_internal_aje.zzcuW);
    }

    public final int hashCode() {
        int hashCode = (((((getClass().getName().hashCode() + 527) * 31) + this.zzcwY) * 31) + this.zzcwZ) * 31;
        int hashCode2 = (this.zzcuW == null || this.zzcuW.isEmpty()) ? 0 : this.zzcuW.hashCode();
        return hashCode2 + hashCode;
    }

    public final /* synthetic */ ahz zzMd() throws CloneNotSupportedException {
        return (aje) clone();
    }

    public final /* synthetic */ aif zzMe() throws CloneNotSupportedException {
        return (aje) clone();
    }

    public final /* synthetic */ aif zza(ahw com_google_android_gms_internal_ahw) throws IOException {
        while (true) {
            int zzLQ = com_google_android_gms_internal_ahw.zzLQ();
            int position;
            int zzLS;
            switch (zzLQ) {
                case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                    return this;
                case DetectedActivity.RUNNING:
                    position = com_google_android_gms_internal_ahw.getPosition();
                    zzLS = com_google_android_gms_internal_ahw.zzLS();
                    switch (zzLS) {
                        case RainSurfaceView.RAIN_LEVEL_DEFAULT:
                        case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                        case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        case RainSurfaceView.RAIN_LEVEL_SHOWER:
                        case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                        case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                        case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                        case ConnectionResult.RESOLUTION_REQUIRED:
                        case DetectedActivity.WALKING:
                        case DetectedActivity.RUNNING:
                        case ConnectionResult.SERVICE_INVALID:
                        case ConnectionResult.DEVELOPER_ERROR:
                        case ConnectionResult.LICENSE_CHECK_FAILED:
                        case WeatherCircleView.ARC_DIN:
                        case ConnectionResult.CANCELED:
                        case ConnectionResult.TIMEOUT:
                        case ConnectionResult.INTERRUPTED:
                        case ConnectionResult.API_UNAVAILABLE:
                        case ConnectionResult.SIGN_IN_FAILED:
                            this.zzcwY = zzLS;
                            break;
                        default:
                            com_google_android_gms_internal_ahw.zzco(position);
                            zza(com_google_android_gms_internal_ahw, zzLQ);
                            break;
                    }
                    break;
                case ConnectionResult.API_UNAVAILABLE:
                    position = com_google_android_gms_internal_ahw.getPosition();
                    zzLS = com_google_android_gms_internal_ahw.zzLS();
                    switch (zzLS) {
                        case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                        case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        case RainSurfaceView.RAIN_LEVEL_SHOWER:
                        case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                        case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                        case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                        case ConnectionResult.RESOLUTION_REQUIRED:
                        case DetectedActivity.WALKING:
                        case DetectedActivity.RUNNING:
                        case ConnectionResult.SERVICE_INVALID:
                        case ConnectionResult.DEVELOPER_ERROR:
                        case ConnectionResult.LICENSE_CHECK_FAILED:
                        case WeatherCircleView.ARC_DIN:
                        case ConnectionResult.CANCELED:
                        case ConnectionResult.TIMEOUT:
                        case ConnectionResult.INTERRUPTED:
                        case ConnectionResult.API_UNAVAILABLE:
                        case LocationRequest.PRIORITY_HIGH_ACCURACY:
                            this.zzcwZ = zzLS;
                            break;
                        default:
                            com_google_android_gms_internal_ahw.zzco(position);
                            zza(com_google_android_gms_internal_ahw, zzLQ);
                            break;
                    }
                    break;
                default:
                    if (!super.zza(com_google_android_gms_internal_ahw, zzLQ)) {
                        return this;
                    }
            }
        }
    }

    public final void zza(ahx com_google_android_gms_internal_ahx) throws IOException {
        if (this.zzcwY != -1) {
            com_google_android_gms_internal_ahx.zzr(1, this.zzcwY);
        }
        if (this.zzcwZ != 0) {
            com_google_android_gms_internal_ahx.zzr(RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzcwZ);
        }
        super.zza(com_google_android_gms_internal_ahx);
    }

    protected final int zzn() {
        int zzn = super.zzn();
        if (this.zzcwY != -1) {
            zzn += ahx.zzs(1, this.zzcwY);
        }
        return this.zzcwZ != 0 ? zzn + ahx.zzs(RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzcwZ) : zzn;
    }
}
