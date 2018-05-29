package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import com.oneplus.lib.widget.recyclerview.ItemTouchHelper;
import java.io.IOException;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class aiz extends ahz<aiz> implements Cloneable {
    private String[] zzcwu;
    private String[] zzcwv;
    private int[] zzcww;
    private long[] zzcwx;
    private long[] zzcwy;

    public aiz() {
        this.zzcwu = aij.EMPTY_STRING_ARRAY;
        this.zzcwv = aij.EMPTY_STRING_ARRAY;
        this.zzcww = aij.zzcvm;
        this.zzcwx = aij.zzcvn;
        this.zzcwy = aij.zzcvn;
        this.zzcuW = null;
        this.zzcvf = -1;
    }

    private aiz zzMq() {
        try {
            aiz com_google_android_gms_internal_aiz = (aiz) super.zzMd();
            if (this.zzcwu != null && this.zzcwu.length > 0) {
                com_google_android_gms_internal_aiz.zzcwu = (String[]) this.zzcwu.clone();
            }
            if (this.zzcwv != null && this.zzcwv.length > 0) {
                com_google_android_gms_internal_aiz.zzcwv = (String[]) this.zzcwv.clone();
            }
            if (this.zzcww != null && this.zzcww.length > 0) {
                com_google_android_gms_internal_aiz.zzcww = (int[]) this.zzcww.clone();
            }
            if (this.zzcwx != null && this.zzcwx.length > 0) {
                com_google_android_gms_internal_aiz.zzcwx = (long[]) this.zzcwx.clone();
            }
            if (this.zzcwy != null && this.zzcwy.length > 0) {
                com_google_android_gms_internal_aiz.zzcwy = (long[]) this.zzcwy.clone();
            }
            return com_google_android_gms_internal_aiz;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzMq();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aiz)) {
            return false;
        }
        aiz com_google_android_gms_internal_aiz = (aiz) obj;
        return !aid.equals(this.zzcwu, com_google_android_gms_internal_aiz.zzcwu) ? false : !aid.equals(this.zzcwv, com_google_android_gms_internal_aiz.zzcwv) ? false : !aid.equals(this.zzcww, com_google_android_gms_internal_aiz.zzcww) ? false : !aid.equals(this.zzcwx, com_google_android_gms_internal_aiz.zzcwx) ? false : !aid.equals(this.zzcwy, com_google_android_gms_internal_aiz.zzcwy) ? false : (this.zzcuW == null || this.zzcuW.isEmpty()) ? com_google_android_gms_internal_aiz.zzcuW == null || com_google_android_gms_internal_aiz.zzcuW.isEmpty() : this.zzcuW.equals(com_google_android_gms_internal_aiz.zzcuW);
    }

    public final int hashCode() {
        int hashCode = (((((((((((getClass().getName().hashCode() + 527) * 31) + aid.hashCode(this.zzcwu)) * 31) + aid.hashCode(this.zzcwv)) * 31) + aid.hashCode(this.zzcww)) * 31) + aid.hashCode(this.zzcwx)) * 31) + aid.hashCode(this.zzcwy)) * 31;
        int hashCode2 = (this.zzcuW == null || this.zzcuW.isEmpty()) ? 0 : this.zzcuW.hashCode();
        return hashCode2 + hashCode;
    }

    public final /* synthetic */ ahz zzMd() throws CloneNotSupportedException {
        return (aiz) clone();
    }

    public final /* synthetic */ aif zzMe() throws CloneNotSupportedException {
        return (aiz) clone();
    }

    public final /* synthetic */ aif zza(ahw com_google_android_gms_internal_ahw) throws IOException {
        while (true) {
            int zzLQ = com_google_android_gms_internal_ahw.zzLQ();
            int zzb;
            Object obj;
            int zzcm;
            Object obj2;
            switch (zzLQ) {
                case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                    return this;
                case ConnectionResult.DEVELOPER_ERROR:
                    zzb = aij.zzb(com_google_android_gms_internal_ahw, ConnectionResult.DEVELOPER_ERROR);
                    zzLQ = this.zzcwu == null ? 0 : this.zzcwu.length;
                    obj = new Object[(zzb + zzLQ)];
                    if (zzLQ != 0) {
                        System.arraycopy(this.zzcwu, 0, obj, 0, zzLQ);
                    }
                    while (zzLQ < obj.length - 1) {
                        obj[zzLQ] = com_google_android_gms_internal_ahw.readString();
                        com_google_android_gms_internal_ahw.zzLQ();
                        zzLQ++;
                    }
                    obj[zzLQ] = com_google_android_gms_internal_ahw.readString();
                    this.zzcwu = obj;
                    break;
                case ConnectionResult.SERVICE_UPDATING:
                    zzb = aij.zzb(com_google_android_gms_internal_ahw, ConnectionResult.SERVICE_UPDATING);
                    zzLQ = this.zzcwv == null ? 0 : this.zzcwv.length;
                    obj = new Object[(zzb + zzLQ)];
                    if (zzLQ != 0) {
                        System.arraycopy(this.zzcwv, 0, obj, 0, zzLQ);
                    }
                    while (zzLQ < obj.length - 1) {
                        obj[zzLQ] = com_google_android_gms_internal_ahw.readString();
                        com_google_android_gms_internal_ahw.zzLQ();
                        zzLQ++;
                    }
                    obj[zzLQ] = com_google_android_gms_internal_ahw.readString();
                    this.zzcwv = obj;
                    break;
                case R.styleable.Toolbar_titleMarginStart:
                    zzb = aij.zzb(com_google_android_gms_internal_ahw, R.styleable.Toolbar_titleMarginStart);
                    zzLQ = this.zzcww == null ? 0 : this.zzcww.length;
                    obj = new Object[(zzb + zzLQ)];
                    if (zzLQ != 0) {
                        System.arraycopy(this.zzcww, 0, obj, 0, zzLQ);
                    }
                    while (zzLQ < obj.length - 1) {
                        obj[zzLQ] = com_google_android_gms_internal_ahw.zzLS();
                        com_google_android_gms_internal_ahw.zzLQ();
                        zzLQ++;
                    }
                    obj[zzLQ] = com_google_android_gms_internal_ahw.zzLS();
                    this.zzcww = obj;
                    break;
                case R.styleable.Toolbar_titleMargins:
                    zzcm = com_google_android_gms_internal_ahw.zzcm(com_google_android_gms_internal_ahw.zzLV());
                    zzb = com_google_android_gms_internal_ahw.getPosition();
                    zzLQ = 0;
                    while (com_google_android_gms_internal_ahw.zzMa() > 0) {
                        com_google_android_gms_internal_ahw.zzLS();
                        zzLQ++;
                    }
                    com_google_android_gms_internal_ahw.zzco(zzb);
                    zzb = this.zzcww == null ? 0 : this.zzcww.length;
                    obj2 = new Object[(zzLQ + zzb)];
                    if (zzb != 0) {
                        System.arraycopy(this.zzcww, 0, obj2, 0, zzb);
                    }
                    while (zzb < obj2.length) {
                        obj2[zzb] = com_google_android_gms_internal_ahw.zzLS();
                        zzb++;
                    }
                    this.zzcww = obj2;
                    com_google_android_gms_internal_ahw.zzcn(zzcm);
                    break;
                case ItemTouchHelper.END:
                    zzb = aij.zzb(com_google_android_gms_internal_ahw, ItemTouchHelper.END);
                    zzLQ = this.zzcwx == null ? 0 : this.zzcwx.length;
                    obj = new Object[(zzb + zzLQ)];
                    if (zzLQ != 0) {
                        System.arraycopy(this.zzcwx, 0, obj, 0, zzLQ);
                    }
                    while (zzLQ < obj.length - 1) {
                        obj[zzLQ] = com_google_android_gms_internal_ahw.zzLR();
                        com_google_android_gms_internal_ahw.zzLQ();
                        zzLQ++;
                    }
                    obj[zzLQ] = com_google_android_gms_internal_ahw.zzLR();
                    this.zzcwx = obj;
                    break;
                case R.styleable.OneplusTheme_op_buttonPanelSideLayout:
                    zzcm = com_google_android_gms_internal_ahw.zzcm(com_google_android_gms_internal_ahw.zzLV());
                    zzb = com_google_android_gms_internal_ahw.getPosition();
                    zzLQ = 0;
                    while (com_google_android_gms_internal_ahw.zzMa() > 0) {
                        com_google_android_gms_internal_ahw.zzLR();
                        zzLQ++;
                    }
                    com_google_android_gms_internal_ahw.zzco(zzb);
                    zzb = this.zzcwx == null ? 0 : this.zzcwx.length;
                    obj2 = new Object[(zzLQ + zzb)];
                    if (zzb != 0) {
                        System.arraycopy(this.zzcwx, 0, obj2, 0, zzb);
                    }
                    while (zzb < obj2.length) {
                        obj2[zzb] = com_google_android_gms_internal_ahw.zzLR();
                        zzb++;
                    }
                    this.zzcwx = obj2;
                    com_google_android_gms_internal_ahw.zzcn(zzcm);
                    break;
                case R.styleable.OneplusTheme_op_rippleColor:
                    zzb = aij.zzb(com_google_android_gms_internal_ahw, R.styleable.OneplusTheme_op_rippleColor);
                    zzLQ = this.zzcwy == null ? 0 : this.zzcwy.length;
                    obj = new Object[(zzb + zzLQ)];
                    if (zzLQ != 0) {
                        System.arraycopy(this.zzcwy, 0, obj, 0, zzLQ);
                    }
                    while (zzLQ < obj.length - 1) {
                        obj[zzLQ] = com_google_android_gms_internal_ahw.zzLR();
                        com_google_android_gms_internal_ahw.zzLQ();
                        zzLQ++;
                    }
                    obj[zzLQ] = com_google_android_gms_internal_ahw.zzLR();
                    this.zzcwy = obj;
                    break;
                case R.styleable.OneplusTheme_progressLayout:
                    zzcm = com_google_android_gms_internal_ahw.zzcm(com_google_android_gms_internal_ahw.zzLV());
                    zzb = com_google_android_gms_internal_ahw.getPosition();
                    zzLQ = 0;
                    while (com_google_android_gms_internal_ahw.zzMa() > 0) {
                        com_google_android_gms_internal_ahw.zzLR();
                        zzLQ++;
                    }
                    com_google_android_gms_internal_ahw.zzco(zzb);
                    zzb = this.zzcwy == null ? 0 : this.zzcwy.length;
                    obj2 = new Object[(zzLQ + zzb)];
                    if (zzb != 0) {
                        System.arraycopy(this.zzcwy, 0, obj2, 0, zzb);
                    }
                    while (zzb < obj2.length) {
                        obj2[zzb] = com_google_android_gms_internal_ahw.zzLR();
                        zzb++;
                    }
                    this.zzcwy = obj2;
                    com_google_android_gms_internal_ahw.zzcn(zzcm);
                    break;
                default:
                    if (!super.zza(com_google_android_gms_internal_ahw, zzLQ)) {
                        return this;
                    }
            }
        }
    }

    public final void zza(ahx com_google_android_gms_internal_ahx) throws IOException {
        int i;
        String str;
        int i2 = 0;
        if (this.zzcwu != null && this.zzcwu.length > 0) {
            for (i = 0; i < this.zzcwu.length; i++) {
                str = this.zzcwu[i];
                if (str != null) {
                    com_google_android_gms_internal_ahx.zzl(1, str);
                }
            }
        }
        if (this.zzcwv != null && this.zzcwv.length > 0) {
            for (i = 0; i < this.zzcwv.length; i++) {
                str = this.zzcwv[i];
                if (str != null) {
                    com_google_android_gms_internal_ahx.zzl(RainSurfaceView.RAIN_LEVEL_SHOWER, str);
                }
            }
        }
        if (this.zzcww != null && this.zzcww.length > 0) {
            for (i = 0; i < this.zzcww.length; i++) {
                com_google_android_gms_internal_ahx.zzr(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzcww[i]);
            }
        }
        if (this.zzcwx != null && this.zzcwx.length > 0) {
            for (i = 0; i < this.zzcwx.length; i++) {
                com_google_android_gms_internal_ahx.zzb((int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzcwx[i]);
            }
        }
        if (this.zzcwy != null && this.zzcwy.length > 0) {
            while (i2 < this.zzcwy.length) {
                com_google_android_gms_internal_ahx.zzb((int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.zzcwy[i2]);
                i2++;
            }
        }
        super.zza(com_google_android_gms_internal_ahx);
    }

    protected final int zzn() {
        int i;
        int i2;
        int i3;
        String str;
        int i4 = 0;
        int zzn = super.zzn();
        if (this.zzcwu == null || this.zzcwu.length <= 0) {
            i = zzn;
        } else {
            i2 = 0;
            i3 = 0;
            for (i = 0; i < this.zzcwu.length; i++) {
                str = this.zzcwu[i];
                if (str != null) {
                    i3++;
                    i2 += ahx.zzip(str);
                }
            }
            i = (zzn + i2) + (i3 * 1);
        }
        if (this.zzcwv != null && this.zzcwv.length > 0) {
            i3 = 0;
            zzn = 0;
            for (i2 = 0; i2 < this.zzcwv.length; i2++) {
                str = this.zzcwv[i2];
                if (str != null) {
                    zzn++;
                    i3 += ahx.zzip(str);
                }
            }
            i = (i + i3) + (zzn * 1);
        }
        if (this.zzcww != null && this.zzcww.length > 0) {
            i3 = 0;
            for (i2 = 0; i2 < this.zzcww.length; i2++) {
                i3 += ahx.zzcq(this.zzcww[i2]);
            }
            i = (i + i3) + (this.zzcww.length * 1);
        }
        if (this.zzcwx != null && this.zzcwx.length > 0) {
            i3 = 0;
            for (i2 = 0; i2 < this.zzcwx.length; i2++) {
                i3 += ahx.zzaP(this.zzcwx[i2]);
            }
            i = (i + i3) + (this.zzcwx.length * 1);
        }
        if (this.zzcwy == null || this.zzcwy.length <= 0) {
            return i;
        }
        i2 = 0;
        while (i4 < this.zzcwy.length) {
            i2 += ahx.zzaP(this.zzcwy[i4]);
            i4++;
        }
        return (i + i2) + (this.zzcwy.length * 1);
    }
}
