package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import java.io.IOException;
import java.util.Arrays;
import net.oneplus.weather.R;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class ajb extends ahz<ajb> implements Cloneable {
    private byte[] zzcwA;
    private String zzcwB;
    private byte[][] zzcwC;
    private boolean zzcwD;

    public ajb() {
        this.zzcwA = aij.zzcvs;
        this.zzcwB = StringUtils.EMPTY_STRING;
        this.zzcwC = aij.zzcvr;
        this.zzcwD = false;
        this.zzcuW = null;
        this.zzcvf = -1;
    }

    private ajb zzMs() {
        try {
            ajb com_google_android_gms_internal_ajb = (ajb) super.zzMd();
            if (this.zzcwC != null && this.zzcwC.length > 0) {
                com_google_android_gms_internal_ajb.zzcwC = (byte[][]) this.zzcwC.clone();
            }
            return com_google_android_gms_internal_ajb;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzMs();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ajb)) {
            return false;
        }
        ajb com_google_android_gms_internal_ajb = (ajb) obj;
        if (!Arrays.equals(this.zzcwA, com_google_android_gms_internal_ajb.zzcwA)) {
            return false;
        }
        if (this.zzcwB == null) {
            if (com_google_android_gms_internal_ajb.zzcwB != null) {
                return false;
            }
        } else if (!this.zzcwB.equals(com_google_android_gms_internal_ajb.zzcwB)) {
            return false;
        }
        return !aid.zza(this.zzcwC, com_google_android_gms_internal_ajb.zzcwC) ? false : this.zzcwD != com_google_android_gms_internal_ajb.zzcwD ? false : (this.zzcuW == null || this.zzcuW.isEmpty()) ? com_google_android_gms_internal_ajb.zzcuW == null || com_google_android_gms_internal_ajb.zzcuW.isEmpty() : this.zzcuW.equals(com_google_android_gms_internal_ajb.zzcuW);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzcwD ? 1231 : 1237) + (((((this.zzcwB == null ? 0 : this.zzcwB.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + Arrays.hashCode(this.zzcwA)) * 31)) * 31) + aid.zzc(this.zzcwC)) * 31)) * 31;
        if (!(this.zzcuW == null || this.zzcuW.isEmpty())) {
            i = this.zzcuW.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ ahz zzMd() throws CloneNotSupportedException {
        return (ajb) clone();
    }

    public final /* synthetic */ aif zzMe() throws CloneNotSupportedException {
        return (ajb) clone();
    }

    public final /* synthetic */ aif zza(ahw com_google_android_gms_internal_ahw) throws IOException {
        while (true) {
            int zzLQ = com_google_android_gms_internal_ahw.zzLQ();
            switch (zzLQ) {
                case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                    return this;
                case ConnectionResult.DEVELOPER_ERROR:
                    this.zzcwA = com_google_android_gms_internal_ahw.readBytes();
                    break;
                case ConnectionResult.SERVICE_UPDATING:
                    int zzb = aij.zzb(com_google_android_gms_internal_ahw, ConnectionResult.SERVICE_UPDATING);
                    zzLQ = this.zzcwC == null ? 0 : this.zzcwC.length;
                    Object obj = new Object[(zzb + zzLQ)];
                    if (zzLQ != 0) {
                        System.arraycopy(this.zzcwC, 0, obj, 0, zzLQ);
                    }
                    while (zzLQ < obj.length - 1) {
                        obj[zzLQ] = com_google_android_gms_internal_ahw.readBytes();
                        com_google_android_gms_internal_ahw.zzLQ();
                        zzLQ++;
                    }
                    obj[zzLQ] = com_google_android_gms_internal_ahw.readBytes();
                    this.zzcwC = obj;
                    break;
                case R.styleable.Toolbar_titleMarginStart:
                    this.zzcwD = com_google_android_gms_internal_ahw.zzLT();
                    break;
                case R.styleable.OneplusTheme_op_buttonPanelSideLayout:
                    this.zzcwB = com_google_android_gms_internal_ahw.readString();
                    break;
                default:
                    if (!super.zza(com_google_android_gms_internal_ahw, zzLQ)) {
                        return this;
                    }
            }
        }
    }

    public final void zza(ahx com_google_android_gms_internal_ahx) throws IOException {
        if (!Arrays.equals(this.zzcwA, aij.zzcvs)) {
            com_google_android_gms_internal_ahx.zzb(1, this.zzcwA);
        }
        if (this.zzcwC != null && this.zzcwC.length > 0) {
            for (int i = 0; i < this.zzcwC.length; i++) {
                byte[] bArr = this.zzcwC[i];
                if (bArr != null) {
                    com_google_android_gms_internal_ahx.zzb((int) RainSurfaceView.RAIN_LEVEL_SHOWER, bArr);
                }
            }
        }
        if (this.zzcwD) {
            com_google_android_gms_internal_ahx.zzk(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzcwD);
        }
        if (!(this.zzcwB == null || this.zzcwB.equals(StringUtils.EMPTY_STRING))) {
            com_google_android_gms_internal_ahx.zzl(RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzcwB);
        }
        super.zza(com_google_android_gms_internal_ahx);
    }

    protected final int zzn() {
        int i = 0;
        int zzn = super.zzn();
        if (!Arrays.equals(this.zzcwA, aij.zzcvs)) {
            zzn += ahx.zzc(1, this.zzcwA);
        }
        if (this.zzcwC != null && this.zzcwC.length > 0) {
            int i2 = 0;
            int i3 = 0;
            while (i < this.zzcwC.length) {
                byte[] bArr = this.zzcwC[i];
                if (bArr != null) {
                    i3++;
                    i2 += ahx.zzK(bArr);
                }
                i++;
            }
            zzn = (zzn + i2) + (i3 * 1);
        }
        if (this.zzcwD) {
            zzn += ahx.zzcs(RainSurfaceView.RAIN_LEVEL_DOWNPOUR) + 1;
        }
        return (this.zzcwB == null || this.zzcwB.equals(StringUtils.EMPTY_STRING)) ? zzn : zzn + ahx.zzm(RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzcwB);
    }
}
