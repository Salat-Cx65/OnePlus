package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.io.IOException;
import net.oneplus.weather.R;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class aja extends ahz<aja> implements Cloneable {
    private String version;
    private int zzbpf;
    private String zzcwz;

    public aja() {
        this.zzbpf = 0;
        this.zzcwz = StringUtils.EMPTY_STRING;
        this.version = StringUtils.EMPTY_STRING;
        this.zzcuW = null;
        this.zzcvf = -1;
    }

    private aja zzMr() {
        try {
            return (aja) super.zzMd();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzMr();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aja)) {
            return false;
        }
        aja com_google_android_gms_internal_aja = (aja) obj;
        if (this.zzbpf != com_google_android_gms_internal_aja.zzbpf) {
            return false;
        }
        if (this.zzcwz == null) {
            if (com_google_android_gms_internal_aja.zzcwz != null) {
                return false;
            }
        } else if (!this.zzcwz.equals(com_google_android_gms_internal_aja.zzcwz)) {
            return false;
        }
        if (this.version == null) {
            if (com_google_android_gms_internal_aja.version != null) {
                return false;
            }
        } else if (!this.version.equals(com_google_android_gms_internal_aja.version)) {
            return false;
        }
        return (this.zzcuW == null || this.zzcuW.isEmpty()) ? com_google_android_gms_internal_aja.zzcuW == null || com_google_android_gms_internal_aja.zzcuW.isEmpty() : this.zzcuW.equals(com_google_android_gms_internal_aja.zzcuW);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.version == null ? 0 : this.version.hashCode()) + (((this.zzcwz == null ? 0 : this.zzcwz.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + this.zzbpf) * 31)) * 31)) * 31;
        if (!(this.zzcuW == null || this.zzcuW.isEmpty())) {
            i = this.zzcuW.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ ahz zzMd() throws CloneNotSupportedException {
        return (aja) clone();
    }

    public final /* synthetic */ aif zzMe() throws CloneNotSupportedException {
        return (aja) clone();
    }

    public final /* synthetic */ aif zza(ahw com_google_android_gms_internal_ahw) throws IOException {
        while (true) {
            int zzLQ = com_google_android_gms_internal_ahw.zzLQ();
            switch (zzLQ) {
                case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                    return this;
                case DetectedActivity.RUNNING:
                    this.zzbpf = com_google_android_gms_internal_ahw.zzLS();
                    break;
                case ConnectionResult.SERVICE_UPDATING:
                    this.zzcwz = com_google_android_gms_internal_ahw.readString();
                    break;
                case R.styleable.Toolbar_titleMargins:
                    this.version = com_google_android_gms_internal_ahw.readString();
                    break;
                default:
                    if (!super.zza(com_google_android_gms_internal_ahw, zzLQ)) {
                        return this;
                    }
            }
        }
    }

    public final void zza(ahx com_google_android_gms_internal_ahx) throws IOException {
        if (this.zzbpf != 0) {
            com_google_android_gms_internal_ahx.zzr(1, this.zzbpf);
        }
        if (!(this.zzcwz == null || this.zzcwz.equals(StringUtils.EMPTY_STRING))) {
            com_google_android_gms_internal_ahx.zzl(RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzcwz);
        }
        if (!(this.version == null || this.version.equals(StringUtils.EMPTY_STRING))) {
            com_google_android_gms_internal_ahx.zzl(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.version);
        }
        super.zza(com_google_android_gms_internal_ahx);
    }

    protected final int zzn() {
        int zzn = super.zzn();
        if (this.zzbpf != 0) {
            zzn += ahx.zzs(1, this.zzbpf);
        }
        if (!(this.zzcwz == null || this.zzcwz.equals(StringUtils.EMPTY_STRING))) {
            zzn += ahx.zzm(RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzcwz);
        }
        return (this.version == null || this.version.equals(StringUtils.EMPTY_STRING)) ? zzn : zzn + ahx.zzm(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.version);
    }
}
