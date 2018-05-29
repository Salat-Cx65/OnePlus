package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.io.IOException;
import java.util.Arrays;
import net.oneplus.weather.R;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import net.oneplus.weather.widget.shap.Stars;

public final class ajc extends ahz<ajc> implements Cloneable {
    private String tag;
    private boolean zzcfX;
    private aje zzcpG;
    public long zzcwE;
    public long zzcwF;
    private long zzcwG;
    public int zzcwH;
    private ajd[] zzcwI;
    private byte[] zzcwJ;
    private aja zzcwK;
    public byte[] zzcwL;
    private String zzcwM;
    private String zzcwN;
    private aiz zzcwO;
    private String zzcwP;
    public long zzcwQ;
    private ajb zzcwR;
    public byte[] zzcwS;
    private String zzcwT;
    private int zzcwU;
    private int[] zzcwV;
    private long zzcwW;
    public int zzrE;

    public ajc() {
        this.zzcwE = 0;
        this.zzcwF = 0;
        this.zzcwG = 0;
        this.tag = StringUtils.EMPTY_STRING;
        this.zzcwH = 0;
        this.zzrE = 0;
        this.zzcfX = false;
        this.zzcwI = ajd.zzMu();
        this.zzcwJ = aij.zzcvs;
        this.zzcwK = null;
        this.zzcwL = aij.zzcvs;
        this.zzcwM = StringUtils.EMPTY_STRING;
        this.zzcwN = StringUtils.EMPTY_STRING;
        this.zzcwO = null;
        this.zzcwP = StringUtils.EMPTY_STRING;
        this.zzcwQ = 180000;
        this.zzcwR = null;
        this.zzcwS = aij.zzcvs;
        this.zzcwT = StringUtils.EMPTY_STRING;
        this.zzcwU = 0;
        this.zzcwV = aij.zzcvm;
        this.zzcwW = 0;
        this.zzcpG = null;
        this.zzcuW = null;
        this.zzcvf = -1;
    }

    private final ajc zzMt() {
        try {
            ajc com_google_android_gms_internal_ajc = (ajc) super.zzMd();
            if (this.zzcwI != null && this.zzcwI.length > 0) {
                com_google_android_gms_internal_ajc.zzcwI = new ajd[this.zzcwI.length];
                for (int i = 0; i < this.zzcwI.length; i++) {
                    if (this.zzcwI[i] != null) {
                        com_google_android_gms_internal_ajc.zzcwI[i] = (ajd) this.zzcwI[i].clone();
                    }
                }
            }
            if (this.zzcwK != null) {
                com_google_android_gms_internal_ajc.zzcwK = (aja) this.zzcwK.clone();
            }
            if (this.zzcwO != null) {
                com_google_android_gms_internal_ajc.zzcwO = (aiz) this.zzcwO.clone();
            }
            if (this.zzcwR != null) {
                com_google_android_gms_internal_ajc.zzcwR = (ajb) this.zzcwR.clone();
            }
            if (this.zzcwV != null && this.zzcwV.length > 0) {
                com_google_android_gms_internal_ajc.zzcwV = (int[]) this.zzcwV.clone();
            }
            if (this.zzcpG != null) {
                com_google_android_gms_internal_ajc.zzcpG = (aje) this.zzcpG.clone();
            }
            return com_google_android_gms_internal_ajc;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzMt();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ajc)) {
            return false;
        }
        ajc com_google_android_gms_internal_ajc = (ajc) obj;
        if (this.zzcwE != com_google_android_gms_internal_ajc.zzcwE) {
            return false;
        }
        if (this.zzcwF != com_google_android_gms_internal_ajc.zzcwF) {
            return false;
        }
        if (this.zzcwG != com_google_android_gms_internal_ajc.zzcwG) {
            return false;
        }
        if (this.tag == null) {
            if (com_google_android_gms_internal_ajc.tag != null) {
                return false;
            }
        } else if (!this.tag.equals(com_google_android_gms_internal_ajc.tag)) {
            return false;
        }
        if (this.zzcwH != com_google_android_gms_internal_ajc.zzcwH) {
            return false;
        }
        if (this.zzrE != com_google_android_gms_internal_ajc.zzrE) {
            return false;
        }
        if (this.zzcfX != com_google_android_gms_internal_ajc.zzcfX) {
            return false;
        }
        if (!aid.equals(this.zzcwI, com_google_android_gms_internal_ajc.zzcwI)) {
            return false;
        }
        if (!Arrays.equals(this.zzcwJ, com_google_android_gms_internal_ajc.zzcwJ)) {
            return false;
        }
        if (this.zzcwK == null) {
            if (com_google_android_gms_internal_ajc.zzcwK != null) {
                return false;
            }
        } else if (!this.zzcwK.equals(com_google_android_gms_internal_ajc.zzcwK)) {
            return false;
        }
        if (!Arrays.equals(this.zzcwL, com_google_android_gms_internal_ajc.zzcwL)) {
            return false;
        }
        if (this.zzcwM == null) {
            if (com_google_android_gms_internal_ajc.zzcwM != null) {
                return false;
            }
        } else if (!this.zzcwM.equals(com_google_android_gms_internal_ajc.zzcwM)) {
            return false;
        }
        if (this.zzcwN == null) {
            if (com_google_android_gms_internal_ajc.zzcwN != null) {
                return false;
            }
        } else if (!this.zzcwN.equals(com_google_android_gms_internal_ajc.zzcwN)) {
            return false;
        }
        if (this.zzcwO == null) {
            if (com_google_android_gms_internal_ajc.zzcwO != null) {
                return false;
            }
        } else if (!this.zzcwO.equals(com_google_android_gms_internal_ajc.zzcwO)) {
            return false;
        }
        if (this.zzcwP == null) {
            if (com_google_android_gms_internal_ajc.zzcwP != null) {
                return false;
            }
        } else if (!this.zzcwP.equals(com_google_android_gms_internal_ajc.zzcwP)) {
            return false;
        }
        if (this.zzcwQ != com_google_android_gms_internal_ajc.zzcwQ) {
            return false;
        }
        if (this.zzcwR == null) {
            if (com_google_android_gms_internal_ajc.zzcwR != null) {
                return false;
            }
        } else if (!this.zzcwR.equals(com_google_android_gms_internal_ajc.zzcwR)) {
            return false;
        }
        if (!Arrays.equals(this.zzcwS, com_google_android_gms_internal_ajc.zzcwS)) {
            return false;
        }
        if (this.zzcwT == null) {
            if (com_google_android_gms_internal_ajc.zzcwT != null) {
                return false;
            }
        } else if (!this.zzcwT.equals(com_google_android_gms_internal_ajc.zzcwT)) {
            return false;
        }
        if (this.zzcwU != com_google_android_gms_internal_ajc.zzcwU) {
            return false;
        }
        if (!aid.equals(this.zzcwV, com_google_android_gms_internal_ajc.zzcwV)) {
            return false;
        }
        if (this.zzcwW != com_google_android_gms_internal_ajc.zzcwW) {
            return false;
        }
        if (this.zzcpG == null) {
            if (com_google_android_gms_internal_ajc.zzcpG != null) {
                return false;
            }
        } else if (!this.zzcpG.equals(com_google_android_gms_internal_ajc.zzcpG)) {
            return false;
        }
        return (this.zzcuW == null || this.zzcuW.isEmpty()) ? com_google_android_gms_internal_ajc.zzcuW == null || com_google_android_gms_internal_ajc.zzcuW.isEmpty() : this.zzcuW.equals(com_google_android_gms_internal_ajc.zzcuW);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzcpG == null ? 0 : this.zzcpG.hashCode()) + (((((((((this.zzcwT == null ? 0 : this.zzcwT.hashCode()) + (((((this.zzcwR == null ? 0 : this.zzcwR.hashCode()) + (((((this.zzcwP == null ? 0 : this.zzcwP.hashCode()) + (((this.zzcwO == null ? 0 : this.zzcwO.hashCode()) + (((this.zzcwN == null ? 0 : this.zzcwN.hashCode()) + (((this.zzcwM == null ? 0 : this.zzcwM.hashCode()) + (((((this.zzcwK == null ? 0 : this.zzcwK.hashCode()) + (((((((this.zzcfX ? 1231 : 1237) + (((((((this.tag == null ? 0 : this.tag.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + ((int) (this.zzcwE ^ (this.zzcwE >>> 32)))) * 31) + ((int) (this.zzcwF ^ (this.zzcwF >>> 32)))) * 31) + ((int) (this.zzcwG ^ (this.zzcwG >>> 32)))) * 31)) * 31) + this.zzcwH) * 31) + this.zzrE) * 31)) * 31) + aid.hashCode(this.zzcwI)) * 31) + Arrays.hashCode(this.zzcwJ)) * 31)) * 31) + Arrays.hashCode(this.zzcwL)) * 31)) * 31)) * 31)) * 31)) * 31) + ((int) (this.zzcwQ ^ (this.zzcwQ >>> 32)))) * 31)) * 31) + Arrays.hashCode(this.zzcwS)) * 31)) * 31) + this.zzcwU) * 31) + aid.hashCode(this.zzcwV)) * 31) + ((int) (this.zzcwW ^ (this.zzcwW >>> 32)))) * 31)) * 31;
        if (!(this.zzcuW == null || this.zzcuW.isEmpty())) {
            i = this.zzcuW.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ ahz zzMd() throws CloneNotSupportedException {
        return (ajc) clone();
    }

    public final /* synthetic */ aif zzMe() throws CloneNotSupportedException {
        return (ajc) clone();
    }

    public final /* synthetic */ aif zza(ahw com_google_android_gms_internal_ahw) throws IOException {
        while (true) {
            int zzLQ = com_google_android_gms_internal_ahw.zzLQ();
            int zzb;
            Object obj;
            int zzLS;
            switch (zzLQ) {
                case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                    return this;
                case DetectedActivity.RUNNING:
                    this.zzcwE = com_google_android_gms_internal_ahw.zzLR();
                    break;
                case ConnectionResult.SERVICE_UPDATING:
                    this.tag = com_google_android_gms_internal_ahw.readString();
                    break;
                case R.styleable.Toolbar_titleMargins:
                    zzb = aij.zzb(com_google_android_gms_internal_ahw, R.styleable.Toolbar_titleMargins);
                    zzLQ = this.zzcwI == null ? 0 : this.zzcwI.length;
                    obj = new Object[(zzb + zzLQ)];
                    if (zzLQ != 0) {
                        System.arraycopy(this.zzcwI, 0, obj, 0, zzLQ);
                    }
                    while (zzLQ < obj.length - 1) {
                        obj[zzLQ] = new ajd();
                        com_google_android_gms_internal_ahw.zzb(obj[zzLQ]);
                        com_google_android_gms_internal_ahw.zzLQ();
                        zzLQ++;
                    }
                    obj[zzLQ] = new ajd();
                    com_google_android_gms_internal_ahw.zzb(obj[zzLQ]);
                    this.zzcwI = obj;
                    break;
                case R.styleable.OneplusTheme_op_buttonPanelSideLayout:
                    this.zzcwJ = com_google_android_gms_internal_ahw.readBytes();
                    break;
                case Stars.CIRCLE_COUNT:
                    this.zzcwL = com_google_android_gms_internal_ahw.readBytes();
                    break;
                case R.styleable.AppCompatTheme_controlBackground:
                    if (this.zzcwO == null) {
                        this.zzcwO = new aiz();
                    }
                    com_google_android_gms_internal_ahw.zzb(this.zzcwO);
                    break;
                case R.styleable.AppCompatTheme_editTextColor:
                    this.zzcwM = com_google_android_gms_internal_ahw.readString();
                    break;
                case R.styleable.AppCompatTheme_listPreferredItemHeight:
                    if (this.zzcwK == null) {
                        this.zzcwK = new aja();
                    }
                    com_google_android_gms_internal_ahw.zzb(this.zzcwK);
                    break;
                case R.styleable.AppCompatTheme_panelMenuListTheme:
                    this.zzcfX = com_google_android_gms_internal_ahw.zzLT();
                    break;
                case R.styleable.AppCompatTheme_searchViewStyle:
                    this.zzcwH = com_google_android_gms_internal_ahw.zzLS();
                    break;
                case R.styleable.AppCompatTheme_textAppearanceListItem:
                    this.zzrE = com_google_android_gms_internal_ahw.zzLS();
                    break;
                case R.styleable.AppCompatTheme_toolbarStyle:
                    this.zzcwN = com_google_android_gms_internal_ahw.readString();
                    break;
                case R.styleable.AppCompatTheme_windowFixedWidthMajor:
                    this.zzcwP = com_google_android_gms_internal_ahw.readString();
                    break;
                case 120:
                    this.zzcwQ = com_google_android_gms_internal_ahw.zzLU();
                    break;
                case 130:
                    if (this.zzcwR == null) {
                        this.zzcwR = new ajb();
                    }
                    com_google_android_gms_internal_ahw.zzb(this.zzcwR);
                    break;
                case 136:
                    this.zzcwF = com_google_android_gms_internal_ahw.zzLR();
                    break;
                case 146:
                    this.zzcwS = com_google_android_gms_internal_ahw.readBytes();
                    break;
                case 152:
                    zzb = com_google_android_gms_internal_ahw.getPosition();
                    zzLS = com_google_android_gms_internal_ahw.zzLS();
                    switch (zzLS) {
                        case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                        case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        case RainSurfaceView.RAIN_LEVEL_SHOWER:
                            this.zzcwU = zzLS;
                            break;
                        default:
                            com_google_android_gms_internal_ahw.zzco(zzb);
                            zza(com_google_android_gms_internal_ahw, zzLQ);
                            break;
                    }
                    break;
                case 160:
                    zzb = aij.zzb(com_google_android_gms_internal_ahw, 160);
                    zzLQ = this.zzcwV == null ? 0 : this.zzcwV.length;
                    obj = new Object[(zzb + zzLQ)];
                    if (zzLQ != 0) {
                        System.arraycopy(this.zzcwV, 0, obj, 0, zzLQ);
                    }
                    while (zzLQ < obj.length - 1) {
                        obj[zzLQ] = com_google_android_gms_internal_ahw.zzLS();
                        com_google_android_gms_internal_ahw.zzLQ();
                        zzLQ++;
                    }
                    obj[zzLQ] = com_google_android_gms_internal_ahw.zzLS();
                    this.zzcwV = obj;
                    break;
                case 162:
                    zzLS = com_google_android_gms_internal_ahw.zzcm(com_google_android_gms_internal_ahw.zzLV());
                    zzb = com_google_android_gms_internal_ahw.getPosition();
                    zzLQ = 0;
                    while (com_google_android_gms_internal_ahw.zzMa() > 0) {
                        com_google_android_gms_internal_ahw.zzLS();
                        zzLQ++;
                    }
                    com_google_android_gms_internal_ahw.zzco(zzb);
                    zzb = this.zzcwV == null ? 0 : this.zzcwV.length;
                    Object obj2 = new Object[(zzLQ + zzb)];
                    if (zzb != 0) {
                        System.arraycopy(this.zzcwV, 0, obj2, 0, zzb);
                    }
                    while (zzb < obj2.length) {
                        obj2[zzb] = com_google_android_gms_internal_ahw.zzLS();
                        zzb++;
                    }
                    this.zzcwV = obj2;
                    com_google_android_gms_internal_ahw.zzcn(zzLS);
                    break;
                case 168:
                    this.zzcwG = com_google_android_gms_internal_ahw.zzLR();
                    break;
                case 176:
                    this.zzcwW = com_google_android_gms_internal_ahw.zzLR();
                    break;
                case 186:
                    if (this.zzcpG == null) {
                        this.zzcpG = new aje();
                    }
                    com_google_android_gms_internal_ahw.zzb(this.zzcpG);
                    break;
                case 194:
                    this.zzcwT = com_google_android_gms_internal_ahw.readString();
                    break;
                default:
                    if (!super.zza(com_google_android_gms_internal_ahw, zzLQ)) {
                        return this;
                    }
            }
        }
    }

    public final void zza(ahx com_google_android_gms_internal_ahx) throws IOException {
        int i = 0;
        if (this.zzcwE != 0) {
            com_google_android_gms_internal_ahx.zzb(1, this.zzcwE);
        }
        if (!(this.tag == null || this.tag.equals(StringUtils.EMPTY_STRING))) {
            com_google_android_gms_internal_ahx.zzl(RainSurfaceView.RAIN_LEVEL_SHOWER, this.tag);
        }
        if (this.zzcwI != null && this.zzcwI.length > 0) {
            for (int i2 = 0; i2 < this.zzcwI.length; i2++) {
                aif com_google_android_gms_internal_aif = this.zzcwI[i2];
                if (com_google_android_gms_internal_aif != null) {
                    com_google_android_gms_internal_ahx.zza((int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, com_google_android_gms_internal_aif);
                }
            }
        }
        if (!Arrays.equals(this.zzcwJ, aij.zzcvs)) {
            com_google_android_gms_internal_ahx.zzb((int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzcwJ);
        }
        if (!Arrays.equals(this.zzcwL, aij.zzcvs)) {
            com_google_android_gms_internal_ahx.zzb((int) ConnectionResult.RESOLUTION_REQUIRED, this.zzcwL);
        }
        if (this.zzcwO != null) {
            com_google_android_gms_internal_ahx.zza((int) DetectedActivity.WALKING, this.zzcwO);
        }
        if (!(this.zzcwM == null || this.zzcwM.equals(StringUtils.EMPTY_STRING))) {
            com_google_android_gms_internal_ahx.zzl(DetectedActivity.RUNNING, this.zzcwM);
        }
        if (this.zzcwK != null) {
            com_google_android_gms_internal_ahx.zza((int) ConnectionResult.SERVICE_INVALID, this.zzcwK);
        }
        if (this.zzcfX) {
            com_google_android_gms_internal_ahx.zzk(ConnectionResult.DEVELOPER_ERROR, this.zzcfX);
        }
        if (this.zzcwH != 0) {
            com_google_android_gms_internal_ahx.zzr(ConnectionResult.LICENSE_CHECK_FAILED, this.zzcwH);
        }
        if (this.zzrE != 0) {
            com_google_android_gms_internal_ahx.zzr(WeatherCircleView.ARC_DIN, this.zzrE);
        }
        if (!(this.zzcwN == null || this.zzcwN.equals(StringUtils.EMPTY_STRING))) {
            com_google_android_gms_internal_ahx.zzl(ConnectionResult.CANCELED, this.zzcwN);
        }
        if (!(this.zzcwP == null || this.zzcwP.equals(StringUtils.EMPTY_STRING))) {
            com_google_android_gms_internal_ahx.zzl(ConnectionResult.TIMEOUT, this.zzcwP);
        }
        if (this.zzcwQ != 180000) {
            com_google_android_gms_internal_ahx.zzd(ConnectionResult.INTERRUPTED, this.zzcwQ);
        }
        if (this.zzcwR != null) {
            com_google_android_gms_internal_ahx.zza((int) ConnectionResult.API_UNAVAILABLE, this.zzcwR);
        }
        if (this.zzcwF != 0) {
            com_google_android_gms_internal_ahx.zzb((int) ConnectionResult.SIGN_IN_FAILED, this.zzcwF);
        }
        if (!Arrays.equals(this.zzcwS, aij.zzcvs)) {
            com_google_android_gms_internal_ahx.zzb((int) ConnectionResult.SERVICE_UPDATING, this.zzcwS);
        }
        if (this.zzcwU != 0) {
            com_google_android_gms_internal_ahx.zzr(ConnectionResult.SERVICE_MISSING_PERMISSION, this.zzcwU);
        }
        if (this.zzcwV != null && this.zzcwV.length > 0) {
            while (i < this.zzcwV.length) {
                com_google_android_gms_internal_ahx.zzr(ConnectionResult.RESTRICTED_PROFILE, this.zzcwV[i]);
                i++;
            }
        }
        if (this.zzcwG != 0) {
            com_google_android_gms_internal_ahx.zzb((int) R.styleable.Toolbar_titleMargin, this.zzcwG);
        }
        if (this.zzcwW != 0) {
            com_google_android_gms_internal_ahx.zzb((int) R.styleable.Toolbar_titleMarginBottom, this.zzcwW);
        }
        if (this.zzcpG != null) {
            com_google_android_gms_internal_ahx.zza((int) R.styleable.Toolbar_titleMarginEnd, this.zzcpG);
        }
        if (!(this.zzcwT == null || this.zzcwT.equals(StringUtils.EMPTY_STRING))) {
            com_google_android_gms_internal_ahx.zzl(R.styleable.Toolbar_titleMarginStart, this.zzcwT);
        }
        super.zza(com_google_android_gms_internal_ahx);
    }

    protected final int zzn() {
        int i;
        int i2 = 0;
        int zzn = super.zzn();
        if (this.zzcwE != 0) {
            zzn += ahx.zze(1, this.zzcwE);
        }
        if (!(this.tag == null || this.tag.equals(StringUtils.EMPTY_STRING))) {
            zzn += ahx.zzm(RainSurfaceView.RAIN_LEVEL_SHOWER, this.tag);
        }
        if (this.zzcwI != null && this.zzcwI.length > 0) {
            i = zzn;
            for (zzn = 0; zzn < this.zzcwI.length; zzn++) {
                aif com_google_android_gms_internal_aif = this.zzcwI[zzn];
                if (com_google_android_gms_internal_aif != null) {
                    i += ahx.zzb((int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, com_google_android_gms_internal_aif);
                }
            }
            zzn = i;
        }
        if (!Arrays.equals(this.zzcwJ, aij.zzcvs)) {
            zzn += ahx.zzc((int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzcwJ);
        }
        if (!Arrays.equals(this.zzcwL, aij.zzcvs)) {
            zzn += ahx.zzc((int) ConnectionResult.RESOLUTION_REQUIRED, this.zzcwL);
        }
        if (this.zzcwO != null) {
            zzn += ahx.zzb((int) DetectedActivity.WALKING, this.zzcwO);
        }
        if (!(this.zzcwM == null || this.zzcwM.equals(StringUtils.EMPTY_STRING))) {
            zzn += ahx.zzm(DetectedActivity.RUNNING, this.zzcwM);
        }
        if (this.zzcwK != null) {
            zzn += ahx.zzb((int) ConnectionResult.SERVICE_INVALID, this.zzcwK);
        }
        if (this.zzcfX) {
            zzn += ahx.zzcs(ConnectionResult.DEVELOPER_ERROR) + 1;
        }
        if (this.zzcwH != 0) {
            zzn += ahx.zzs(ConnectionResult.LICENSE_CHECK_FAILED, this.zzcwH);
        }
        if (this.zzrE != 0) {
            zzn += ahx.zzs(WeatherCircleView.ARC_DIN, this.zzrE);
        }
        if (!(this.zzcwN == null || this.zzcwN.equals(StringUtils.EMPTY_STRING))) {
            zzn += ahx.zzm(ConnectionResult.CANCELED, this.zzcwN);
        }
        if (!(this.zzcwP == null || this.zzcwP.equals(StringUtils.EMPTY_STRING))) {
            zzn += ahx.zzm(ConnectionResult.TIMEOUT, this.zzcwP);
        }
        if (this.zzcwQ != 180000) {
            zzn += ahx.zzf(ConnectionResult.INTERRUPTED, this.zzcwQ);
        }
        if (this.zzcwR != null) {
            zzn += ahx.zzb((int) ConnectionResult.API_UNAVAILABLE, this.zzcwR);
        }
        if (this.zzcwF != 0) {
            zzn += ahx.zze(ConnectionResult.SIGN_IN_FAILED, this.zzcwF);
        }
        if (!Arrays.equals(this.zzcwS, aij.zzcvs)) {
            zzn += ahx.zzc((int) ConnectionResult.SERVICE_UPDATING, this.zzcwS);
        }
        if (this.zzcwU != 0) {
            zzn += ahx.zzs(ConnectionResult.SERVICE_MISSING_PERMISSION, this.zzcwU);
        }
        if (this.zzcwV != null && this.zzcwV.length > 0) {
            i = 0;
            while (i2 < this.zzcwV.length) {
                i += ahx.zzcq(this.zzcwV[i2]);
                i2++;
            }
            zzn = (zzn + i) + (this.zzcwV.length * 2);
        }
        if (this.zzcwG != 0) {
            zzn += ahx.zze(R.styleable.Toolbar_titleMargin, this.zzcwG);
        }
        if (this.zzcwW != 0) {
            zzn += ahx.zze(R.styleable.Toolbar_titleMarginBottom, this.zzcwW);
        }
        if (this.zzcpG != null) {
            zzn += ahx.zzb((int) R.styleable.Toolbar_titleMarginEnd, this.zzcpG);
        }
        return (this.zzcwT == null || this.zzcwT.equals(StringUtils.EMPTY_STRING)) ? zzn : zzn + ahx.zzm(R.styleable.Toolbar_titleMarginStart, this.zzcwT);
    }
}
