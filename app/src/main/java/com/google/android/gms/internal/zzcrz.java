package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Base64;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcrz extends zza {
    public static final Creator<zzcrz> CREATOR;
    private static byte[][] zzazk;
    private static zzcrz zzbAg;
    private static final zzcse zzbAp;
    private static final zzcse zzbAq;
    private static final zzcse zzbAr;
    private static final zzcse zzbAs;
    private String zzbAh;
    private byte[] zzbAi;
    private byte[][] zzbAj;
    private byte[][] zzbAk;
    private byte[][] zzbAl;
    private byte[][] zzbAm;
    private int[] zzbAn;
    private byte[][] zzbAo;

    static {
        CREATOR = new zzcsf();
        zzazk = new byte[0][];
        zzbAg = new zzcrz(StringUtils.EMPTY_STRING, null, zzazk, zzazk, zzazk, zzazk, null, null);
        zzbAp = new zzcsa();
        zzbAq = new zzcsb();
        zzbAr = new zzcsc();
        zzbAs = new zzcsd();
    }

    public zzcrz(String str, byte[] bArr, byte[][] bArr2, byte[][] bArr3, byte[][] bArr4, byte[][] bArr5, int[] iArr, byte[][] bArr6) {
        this.zzbAh = str;
        this.zzbAi = bArr;
        this.zzbAj = bArr2;
        this.zzbAk = bArr3;
        this.zzbAl = bArr4;
        this.zzbAm = bArr5;
        this.zzbAn = iArr;
        this.zzbAo = bArr6;
    }

    private static void zza(StringBuilder stringBuilder, String str, int[] iArr) {
        stringBuilder.append(str);
        stringBuilder.append("=");
        if (iArr == null) {
            stringBuilder.append("null");
            return;
        }
        stringBuilder.append("(");
        int length = iArr.length;
        int i = 1;
        int i2 = 0;
        while (i2 < length) {
            int i3 = iArr[i2];
            if (r2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(i3);
            i2++;
            Object obj = null;
        }
        stringBuilder.append(")");
    }

    private static void zza(StringBuilder stringBuilder, String str, byte[][] bArr) {
        stringBuilder.append(str);
        stringBuilder.append("=");
        if (bArr == null) {
            stringBuilder.append("null");
            return;
        }
        stringBuilder.append("(");
        int length = bArr.length;
        int i = 1;
        int i2 = 0;
        while (i2 < length) {
            byte[] bArr2 = bArr[i2];
            if (r2 == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("'");
            stringBuilder.append(Base64.encodeToString(bArr2, RainSurfaceView.RAIN_LEVEL_DOWNPOUR));
            stringBuilder.append("'");
            i2++;
            Object obj = null;
        }
        stringBuilder.append(")");
    }

    private static List<String> zzb(byte[][] bArr) {
        if (bArr == null) {
            return Collections.emptyList();
        }
        List<String> arrayList = new ArrayList(bArr.length);
        for (byte[] bArr2 : bArr) {
            arrayList.add(Base64.encodeToString(bArr2, RainSurfaceView.RAIN_LEVEL_DOWNPOUR));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private static List<Integer> zzc(int[] iArr) {
        if (iArr == null) {
            return Collections.emptyList();
        }
        List<Integer> arrayList = new ArrayList(iArr.length);
        for (int i : iArr) {
            arrayList.add(Integer.valueOf(i));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzcrz)) {
            return false;
        }
        zzcrz com_google_android_gms_internal_zzcrz = (zzcrz) obj;
        return zzcsg.equals(this.zzbAh, com_google_android_gms_internal_zzcrz.zzbAh) && Arrays.equals(this.zzbAi, com_google_android_gms_internal_zzcrz.zzbAi) && zzcsg.equals(zzb(this.zzbAj), zzb(com_google_android_gms_internal_zzcrz.zzbAj)) && zzcsg.equals(zzb(this.zzbAk), zzb(com_google_android_gms_internal_zzcrz.zzbAk)) && zzcsg.equals(zzb(this.zzbAl), zzb(com_google_android_gms_internal_zzcrz.zzbAl)) && zzcsg.equals(zzb(this.zzbAm), zzb(com_google_android_gms_internal_zzcrz.zzbAm)) && zzcsg.equals(zzc(this.zzbAn), zzc(com_google_android_gms_internal_zzcrz.zzbAn)) && zzcsg.equals(zzb(this.zzbAo), zzb(com_google_android_gms_internal_zzcrz.zzbAo));
    }

    public final String toString() {
        String str;
        StringBuilder stringBuilder = new StringBuilder("ExperimentTokens");
        stringBuilder.append("(");
        if (this.zzbAh == null) {
            str = "null";
        } else {
            str = String.valueOf("'");
            String str2 = this.zzbAh;
            String valueOf = String.valueOf("'");
            str = new StringBuilder((String.valueOf(str).length() + String.valueOf(str2).length()) + String.valueOf(valueOf).length()).append(str).append(str2).append(valueOf).toString();
        }
        stringBuilder.append(str);
        stringBuilder.append(", ");
        byte[] bArr = this.zzbAi;
        stringBuilder.append("direct");
        stringBuilder.append("=");
        if (bArr == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append("'");
            stringBuilder.append(Base64.encodeToString(bArr, RainSurfaceView.RAIN_LEVEL_DOWNPOUR));
            stringBuilder.append("'");
        }
        stringBuilder.append(", ");
        zza(stringBuilder, "GAIA", this.zzbAj);
        stringBuilder.append(", ");
        zza(stringBuilder, "PSEUDO", this.zzbAk);
        stringBuilder.append(", ");
        zza(stringBuilder, "ALWAYS", this.zzbAl);
        stringBuilder.append(", ");
        zza(stringBuilder, "OTHER", this.zzbAm);
        stringBuilder.append(", ");
        zza(stringBuilder, "weak", this.zzbAn);
        stringBuilder.append(", ");
        zza(stringBuilder, "directs", this.zzbAo);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzbAh, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzbAi, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzbAj, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.zzbAk, false);
        zzd.zza(parcel, (int) ConnectionResult.RESOLUTION_REQUIRED, this.zzbAl, false);
        zzd.zza(parcel, (int) DetectedActivity.WALKING, this.zzbAm, false);
        zzd.zza(parcel, (int) DetectedActivity.RUNNING, this.zzbAn, false);
        zzd.zza(parcel, (int) ConnectionResult.SERVICE_INVALID, this.zzbAo, false);
        zzd.zzI(parcel, zze);
    }
}
