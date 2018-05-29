package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import java.io.IOException;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class ajd extends ahz<ajd> implements Cloneable {
    private static volatile ajd[] zzcwX;
    private String key;
    private String value;

    public ajd() {
        this.key = StringUtils.EMPTY_STRING;
        this.value = StringUtils.EMPTY_STRING;
        this.zzcuW = null;
        this.zzcvf = -1;
    }

    public static ajd[] zzMu() {
        if (zzcwX == null) {
            synchronized (aid.zzcve) {
                if (zzcwX == null) {
                    zzcwX = new ajd[0];
                }
            }
        }
        return zzcwX;
    }

    private ajd zzMv() {
        try {
            return (ajd) super.zzMd();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzMv();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ajd)) {
            return false;
        }
        ajd com_google_android_gms_internal_ajd = (ajd) obj;
        if (this.key == null) {
            if (com_google_android_gms_internal_ajd.key != null) {
                return false;
            }
        } else if (!this.key.equals(com_google_android_gms_internal_ajd.key)) {
            return false;
        }
        if (this.value == null) {
            if (com_google_android_gms_internal_ajd.value != null) {
                return false;
            }
        } else if (!this.value.equals(com_google_android_gms_internal_ajd.value)) {
            return false;
        }
        return (this.zzcuW == null || this.zzcuW.isEmpty()) ? com_google_android_gms_internal_ajd.zzcuW == null || com_google_android_gms_internal_ajd.zzcuW.isEmpty() : this.zzcuW.equals(com_google_android_gms_internal_ajd.zzcuW);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.value == null ? 0 : this.value.hashCode()) + (((this.key == null ? 0 : this.key.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31;
        if (!(this.zzcuW == null || this.zzcuW.isEmpty())) {
            i = this.zzcuW.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ ahz zzMd() throws CloneNotSupportedException {
        return (ajd) clone();
    }

    public final /* synthetic */ aif zzMe() throws CloneNotSupportedException {
        return (ajd) clone();
    }

    public final /* synthetic */ aif zza(ahw com_google_android_gms_internal_ahw) throws IOException {
        while (true) {
            int zzLQ = com_google_android_gms_internal_ahw.zzLQ();
            switch (zzLQ) {
                case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                    return this;
                case ConnectionResult.DEVELOPER_ERROR:
                    this.key = com_google_android_gms_internal_ahw.readString();
                    break;
                case ConnectionResult.SERVICE_UPDATING:
                    this.value = com_google_android_gms_internal_ahw.readString();
                    break;
                default:
                    if (!super.zza(com_google_android_gms_internal_ahw, zzLQ)) {
                        return this;
                    }
            }
        }
    }

    public final void zza(ahx com_google_android_gms_internal_ahx) throws IOException {
        if (!(this.key == null || this.key.equals(StringUtils.EMPTY_STRING))) {
            com_google_android_gms_internal_ahx.zzl(1, this.key);
        }
        if (!(this.value == null || this.value.equals(StringUtils.EMPTY_STRING))) {
            com_google_android_gms_internal_ahx.zzl(RainSurfaceView.RAIN_LEVEL_SHOWER, this.value);
        }
        super.zza(com_google_android_gms_internal_ahx);
    }

    protected final int zzn() {
        int zzn = super.zzn();
        if (!(this.key == null || this.key.equals(StringUtils.EMPTY_STRING))) {
            zzn += ahx.zzm(1, this.key);
        }
        return (this.value == null || this.value.equals(StringUtils.EMPTY_STRING)) ? zzn : zzn + ahx.zzm(RainSurfaceView.RAIN_LEVEL_SHOWER, this.value);
    }
}
