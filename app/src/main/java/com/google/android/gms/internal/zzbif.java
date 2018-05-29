package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.zzc;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.common.util.zzr;
import com.google.android.gms.location.DetectedActivity;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class zzbif extends zzbhx {
    public static final Creator<zzbif> CREATOR;
    private final String mClassName;
    private final zzbia zzaIR;
    private final Parcel zzaIY;
    private final int zzaIZ;
    private int zzaJa;
    private int zzaJb;
    private final int zzakw;

    static {
        CREATOR = new zzbig();
    }

    zzbif(int i, Parcel parcel, zzbia com_google_android_gms_internal_zzbia) {
        this.zzakw = i;
        this.zzaIY = (Parcel) zzbr.zzu(parcel);
        this.zzaIZ = 2;
        this.zzaIR = com_google_android_gms_internal_zzbia;
        if (this.zzaIR == null) {
            this.mClassName = null;
        } else {
            this.mClassName = this.zzaIR.zzrQ();
        }
        this.zzaJa = 2;
    }

    private static void zza(StringBuilder stringBuilder, int i, Object obj) {
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
            case ConnectionResult.RESOLUTION_REQUIRED:
                stringBuilder.append(obj);
            case DetectedActivity.WALKING:
                stringBuilder.append("\"").append(zzq.zzcK(obj.toString())).append("\"");
            case DetectedActivity.RUNNING:
                stringBuilder.append("\"").append(zzd.zzg((byte[]) obj)).append("\"");
            case ConnectionResult.SERVICE_INVALID:
                stringBuilder.append("\"").append(zzd.zzh((byte[]) obj));
                stringBuilder.append("\"");
            case ConnectionResult.DEVELOPER_ERROR:
                zzr.zza(stringBuilder, (HashMap) obj);
            case ConnectionResult.LICENSE_CHECK_FAILED:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException(new StringBuilder(26).append("Unknown type = ").append(i).toString());
        }
    }

    private final void zza(StringBuilder stringBuilder, zzbhv<?, ?> com_google_android_gms_internal_zzbhv___, Parcel parcel, int i) {
        double[] dArr = null;
        int i2 = 0;
        int length;
        if (com_google_android_gms_internal_zzbhv___.zzaIM) {
            stringBuilder.append("[");
            int dataPosition;
            switch (com_google_android_gms_internal_zzbhv___.zzaIL) {
                case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                    int[] zzw = zzb.zzw(parcel, i);
                    length = zzw.length;
                    while (i2 < length) {
                        if (i2 != 0) {
                            stringBuilder.append(",");
                        }
                        stringBuilder.append(Integer.toString(zzw[i2]));
                        i2++;
                    }
                    break;
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    Object[] objArr;
                    length = zzb.zza(parcel, i);
                    dataPosition = parcel.dataPosition();
                    if (length != 0) {
                        int readInt = parcel.readInt();
                        objArr = new Object[readInt];
                        while (i2 < readInt) {
                            objArr[i2] = new BigInteger(parcel.createByteArray());
                            i2++;
                        }
                        parcel.setDataPosition(length + dataPosition);
                    }
                    zzc.zza(stringBuilder, objArr);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    zzc.zza(stringBuilder, zzb.zzx(parcel, i));
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    zzc.zza(stringBuilder, zzb.zzy(parcel, i));
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    length = zzb.zza(parcel, i);
                    i2 = parcel.dataPosition();
                    if (length != 0) {
                        dArr = parcel.createDoubleArray();
                        parcel.setDataPosition(length + i2);
                    }
                    zzc.zza(stringBuilder, dArr);
                    break;
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    zzc.zza(stringBuilder, zzb.zzz(parcel, i));
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED:
                    zzc.zza(stringBuilder, zzb.zzv(parcel, i));
                    break;
                case DetectedActivity.WALKING:
                    zzc.zza(stringBuilder, zzb.zzA(parcel, i));
                    break;
                case DetectedActivity.RUNNING:
                case ConnectionResult.SERVICE_INVALID:
                case ConnectionResult.DEVELOPER_ERROR:
                    throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                case ConnectionResult.LICENSE_CHECK_FAILED:
                    Parcel[] zzE = zzb.zzE(parcel, i);
                    dataPosition = zzE.length;
                    for (int i3 = 0; i3 < dataPosition; i3++) {
                        if (i3 > 0) {
                            stringBuilder.append(",");
                        }
                        zzE[i3].setDataPosition(0);
                        zza(stringBuilder, com_google_android_gms_internal_zzbhv___.zzrO(), zzE[i3]);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown field type out.");
            }
            stringBuilder.append("]");
            return;
        }
        switch (com_google_android_gms_internal_zzbhv___.zzaIL) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                stringBuilder.append(zzb.zzg(parcel, i));
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                stringBuilder.append(zzb.zzk(parcel, i));
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                stringBuilder.append(zzb.zzi(parcel, i));
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                stringBuilder.append(zzb.zzl(parcel, i));
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                stringBuilder.append(zzb.zzn(parcel, i));
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                stringBuilder.append(zzb.zzp(parcel, i));
            case ConnectionResult.RESOLUTION_REQUIRED:
                stringBuilder.append(zzb.zzc(parcel, i));
            case DetectedActivity.WALKING:
                stringBuilder.append("\"").append(zzq.zzcK(zzb.zzq(parcel, i))).append("\"");
            case DetectedActivity.RUNNING:
                stringBuilder.append("\"").append(zzd.zzg(zzb.zzt(parcel, i))).append("\"");
            case ConnectionResult.SERVICE_INVALID:
                stringBuilder.append("\"").append(zzd.zzh(zzb.zzt(parcel, i)));
                stringBuilder.append("\"");
            case ConnectionResult.DEVELOPER_ERROR:
                Bundle zzs = zzb.zzs(parcel, i);
                Set<String> keySet = zzs.keySet();
                keySet.size();
                stringBuilder.append("{");
                length = 1;
                for (String str : keySet) {
                    if (length == 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append("\"").append(str).append("\"");
                    stringBuilder.append(":");
                    stringBuilder.append("\"").append(zzq.zzcK(zzs.getString(str))).append("\"");
                    length = 0;
                }
                stringBuilder.append("}");
            case ConnectionResult.LICENSE_CHECK_FAILED:
                Parcel zzD = zzb.zzD(parcel, i);
                zzD.setDataPosition(0);
                zza(stringBuilder, com_google_android_gms_internal_zzbhv___.zzrO(), zzD);
            default:
                throw new IllegalStateException("Unknown field type out");
        }
    }

    private final void zza(StringBuilder stringBuilder, Map<String, zzbhv<?, ?>> map, Parcel parcel) {
        Entry entry;
        SparseArray sparseArray = new SparseArray();
        for (Entry entry2 : map.entrySet()) {
            sparseArray.put(((zzbhv) entry2.getValue()).zzaIO, entry2);
        }
        stringBuilder.append('{');
        int zzd = zzb.zzd(parcel);
        Object obj = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            entry2 = (Entry) sparseArray.get(65535 & readInt);
            if (entry2 != null) {
                if (obj != null) {
                    stringBuilder.append(",");
                }
                String str = (String) entry2.getKey();
                zzbhv com_google_android_gms_internal_zzbhv = (zzbhv) entry2.getValue();
                stringBuilder.append("\"").append(str).append("\":");
                if (com_google_android_gms_internal_zzbhv.zzrN()) {
                    switch (com_google_android_gms_internal_zzbhv.zzaIL) {
                        case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                            zzb(stringBuilder, com_google_android_gms_internal_zzbhv, zza(com_google_android_gms_internal_zzbhv, Integer.valueOf(zzb.zzg(parcel, readInt))));
                            break;
                        case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                            zzb(stringBuilder, com_google_android_gms_internal_zzbhv, zza(com_google_android_gms_internal_zzbhv, zzb.zzk(parcel, readInt)));
                            break;
                        case RainSurfaceView.RAIN_LEVEL_SHOWER:
                            zzb(stringBuilder, com_google_android_gms_internal_zzbhv, zza(com_google_android_gms_internal_zzbhv, Long.valueOf(zzb.zzi(parcel, readInt))));
                            break;
                        case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                            zzb(stringBuilder, com_google_android_gms_internal_zzbhv, zza(com_google_android_gms_internal_zzbhv, Float.valueOf(zzb.zzl(parcel, readInt))));
                            break;
                        case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                            zzb(stringBuilder, com_google_android_gms_internal_zzbhv, zza(com_google_android_gms_internal_zzbhv, Double.valueOf(zzb.zzn(parcel, readInt))));
                            break;
                        case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                            zzb(stringBuilder, com_google_android_gms_internal_zzbhv, zza(com_google_android_gms_internal_zzbhv, zzb.zzp(parcel, readInt)));
                            break;
                        case ConnectionResult.RESOLUTION_REQUIRED:
                            zzb(stringBuilder, com_google_android_gms_internal_zzbhv, zza(com_google_android_gms_internal_zzbhv, Boolean.valueOf(zzb.zzc(parcel, readInt))));
                            break;
                        case DetectedActivity.WALKING:
                            zzb(stringBuilder, com_google_android_gms_internal_zzbhv, zza(com_google_android_gms_internal_zzbhv, zzb.zzq(parcel, readInt)));
                            break;
                        case DetectedActivity.RUNNING:
                        case ConnectionResult.SERVICE_INVALID:
                            zzb(stringBuilder, com_google_android_gms_internal_zzbhv, zza(com_google_android_gms_internal_zzbhv, zzb.zzt(parcel, readInt)));
                            break;
                        case ConnectionResult.DEVELOPER_ERROR:
                            zzb(stringBuilder, com_google_android_gms_internal_zzbhv, zza(com_google_android_gms_internal_zzbhv, zzo(zzb.zzs(parcel, readInt))));
                            break;
                        case ConnectionResult.LICENSE_CHECK_FAILED:
                            throw new IllegalArgumentException("Method does not accept concrete type.");
                        default:
                            throw new IllegalArgumentException(new StringBuilder(36).append("Unknown field out type = ").append(com_google_android_gms_internal_zzbhv.zzaIL).toString());
                    }
                }
                zza(stringBuilder, com_google_android_gms_internal_zzbhv, parcel, readInt);
                int i = 1;
            }
        }
        if (parcel.dataPosition() != zzd) {
            throw new com.google.android.gms.common.internal.safeparcel.zzc(new StringBuilder(37).append("Overread allowed size end=").append(zzd).toString(), parcel);
        }
        stringBuilder.append('}');
    }

    private final void zzb(StringBuilder stringBuilder, zzbhv<?, ?> com_google_android_gms_internal_zzbhv___, Object obj) {
        if (com_google_android_gms_internal_zzbhv___.zzaIK) {
            ArrayList arrayList = (ArrayList) obj;
            stringBuilder.append("[");
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (i != 0) {
                    stringBuilder.append(",");
                }
                zza(stringBuilder, com_google_android_gms_internal_zzbhv___.zzaIJ, arrayList.get(i));
            }
            stringBuilder.append("]");
            return;
        }
        zza(stringBuilder, com_google_android_gms_internal_zzbhv___.zzaIJ, obj);
    }

    private static HashMap<String, String> zzo(Bundle bundle) {
        HashMap<String, String> hashMap = new HashMap();
        for (String str : bundle.keySet()) {
            hashMap.put(str, bundle.getString(str));
        }
        return hashMap;
    }

    private Parcel zzrS() {
        switch (this.zzaJa) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                this.zzaJb = com.google.android.gms.common.internal.safeparcel.zzd.zze(this.zzaIY);
                com.google.android.gms.common.internal.safeparcel.zzd.zzI(this.zzaIY, this.zzaJb);
                this.zzaJa = 2;
                break;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                com.google.android.gms.common.internal.safeparcel.zzd.zzI(this.zzaIY, this.zzaJb);
                this.zzaJa = 2;
                break;
        }
        return this.zzaIY;
    }

    public String toString() {
        zzbr.zzb(this.zzaIR, (Object) "Cannot convert to JSON on client side.");
        Parcel zzrS = zzrS();
        zzrS.setDataPosition(0);
        StringBuilder stringBuilder = new StringBuilder(100);
        zza(stringBuilder, this.zzaIR.zzcJ(this.mClassName), zzrS);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        Parcelable parcelable;
        int zze = com.google.android.gms.common.internal.safeparcel.zzd.zze(parcel);
        com.google.android.gms.common.internal.safeparcel.zzd.zzc(parcel, 1, this.zzakw);
        com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, zzrS(), false);
        switch (this.zzaIZ) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                parcelable = null;
                break;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                parcelable = this.zzaIR;
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                parcelable = this.zzaIR;
                break;
            default:
                throw new IllegalStateException(new StringBuilder(34).append("Invalid creation type: ").append(this.zzaIZ).toString());
        }
        com.google.android.gms.common.internal.safeparcel.zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, parcelable, i, false);
        com.google.android.gms.common.internal.safeparcel.zzd.zzI(parcel, zze);
    }

    public final Object zzcH(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public final boolean zzcI(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public final Map<String, zzbhv<?, ?>> zzrK() {
        return this.zzaIR == null ? null : this.zzaIR.zzcJ(this.mClassName);
    }
}
