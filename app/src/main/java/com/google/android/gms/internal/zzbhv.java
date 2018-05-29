package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbh;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;
import java.util.Map;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbhv<I, O> extends zza {
    public static final zzbhy CREATOR;
    protected final int zzaIJ;
    protected final boolean zzaIK;
    protected final int zzaIL;
    protected final boolean zzaIM;
    protected final String zzaIN;
    protected final int zzaIO;
    protected final Class<? extends zzbhu> zzaIP;
    private String zzaIQ;
    private zzbia zzaIR;
    private zzbhw<I, O> zzaIS;
    private final int zzakw;

    static {
        CREATOR = new zzbhy();
    }

    zzbhv(int i, int i2, boolean z, int i3, boolean z2, String str, int i4, String str2, zzbho com_google_android_gms_internal_zzbho) {
        this.zzakw = i;
        this.zzaIJ = i2;
        this.zzaIK = z;
        this.zzaIL = i3;
        this.zzaIM = z2;
        this.zzaIN = str;
        this.zzaIO = i4;
        if (str2 == null) {
            this.zzaIP = null;
            this.zzaIQ = null;
        } else {
            this.zzaIP = zzbif.class;
            this.zzaIQ = str2;
        }
        if (com_google_android_gms_internal_zzbho == null) {
            this.zzaIS = null;
        } else {
            this.zzaIS = com_google_android_gms_internal_zzbho.zzrJ();
        }
    }

    private zzbhv(int i, boolean z, int i2, boolean z2, String str, int i3, Class<? extends zzbhu> cls, zzbhw<I, O> com_google_android_gms_internal_zzbhw_I__O) {
        this.zzakw = 1;
        this.zzaIJ = i;
        this.zzaIK = z;
        this.zzaIL = i2;
        this.zzaIM = z2;
        this.zzaIN = str;
        this.zzaIO = i3;
        this.zzaIP = cls;
        if (cls == null) {
            this.zzaIQ = null;
        } else {
            this.zzaIQ = cls.getCanonicalName();
        }
        this.zzaIS = com_google_android_gms_internal_zzbhw_I__O;
    }

    public static zzbhv zza(String str, int i, zzbhw<?, ?> com_google_android_gms_internal_zzbhw___, boolean z) {
        return new zzbhv(7, false, 0, false, str, i, null, com_google_android_gms_internal_zzbhw___);
    }

    public static <T extends zzbhu> zzbhv<T, T> zza(String str, int i, Class<T> cls) {
        return new zzbhv(11, false, 11, false, str, i, cls, null);
    }

    public static <T extends zzbhu> zzbhv<ArrayList<T>, ArrayList<T>> zzb(String str, int i, Class<T> cls) {
        return new zzbhv(11, true, 11, true, str, i, cls, null);
    }

    public static zzbhv<Integer, Integer> zzj(String str, int i) {
        return new zzbhv(0, false, 0, false, str, i, null, null);
    }

    public static zzbhv<Boolean, Boolean> zzk(String str, int i) {
        return new zzbhv(6, false, 6, false, str, i, null, null);
    }

    public static zzbhv<String, String> zzl(String str, int i) {
        return new zzbhv(7, false, 7, false, str, i, null, null);
    }

    private String zzrM() {
        return this.zzaIQ == null ? null : this.zzaIQ;
    }

    public final I convertBack(O o) {
        return this.zzaIS.convertBack(o);
    }

    public final String toString() {
        zzbj zzg = zzbh.zzt(this).zzg("versionCode", Integer.valueOf(this.zzakw)).zzg("typeIn", Integer.valueOf(this.zzaIJ)).zzg("typeInArray", Boolean.valueOf(this.zzaIK)).zzg("typeOut", Integer.valueOf(this.zzaIL)).zzg("typeOutArray", Boolean.valueOf(this.zzaIM)).zzg("outputFieldName", this.zzaIN).zzg("safeParcelFieldId", Integer.valueOf(this.zzaIO)).zzg("concreteTypeName", zzrM());
        Class cls = this.zzaIP;
        if (cls != null) {
            zzg.zzg("concreteType.class", cls.getCanonicalName());
        }
        if (this.zzaIS != null) {
            zzg.zzg("converterName", this.zzaIS.getClass().getCanonicalName());
        }
        return zzg.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzaIJ);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzaIK);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzaIL);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.zzaIM);
        zzd.zza(parcel, (int) ConnectionResult.RESOLUTION_REQUIRED, this.zzaIN, false);
        zzd.zzc(parcel, DetectedActivity.WALKING, this.zzaIO);
        zzd.zza(parcel, (int) DetectedActivity.RUNNING, zzrM(), false);
        zzd.zza(parcel, (int) ConnectionResult.SERVICE_INVALID, this.zzaIS == null ? null : zzbho.zza(this.zzaIS), i, false);
        zzd.zzI(parcel, zze);
    }

    public final void zza(zzbia com_google_android_gms_internal_zzbia) {
        this.zzaIR = com_google_android_gms_internal_zzbia;
    }

    public final int zzrL() {
        return this.zzaIO;
    }

    public final boolean zzrN() {
        return this.zzaIS != null;
    }

    public final Map<String, zzbhv<?, ?>> zzrO() {
        zzbr.zzu(this.zzaIQ);
        zzbr.zzu(this.zzaIR);
        return this.zzaIR.zzcJ(this.zzaIQ);
    }
}
