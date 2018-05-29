package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.location.DetectedActivity;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzd {
    private static int zzG(Parcel parcel, int i) {
        parcel.writeInt(-65536 | i);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    private static void zzH(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        int i2 = dataPosition - i;
        parcel.setDataPosition(i - 4);
        parcel.writeInt(i2);
        parcel.setDataPosition(dataPosition);
    }

    public static void zzI(Parcel parcel, int i) {
        zzH(parcel, i);
    }

    public static void zza(Parcel parcel, int i, byte b) {
        zzb(parcel, i, RainSurfaceView.RAIN_LEVEL_RAINSTORM);
        parcel.writeInt(b);
    }

    public static void zza(Parcel parcel, int i, double d) {
        zzb(parcel, i, DetectedActivity.RUNNING);
        parcel.writeDouble(d);
    }

    public static void zza(Parcel parcel, int i, float f) {
        zzb(parcel, i, RainSurfaceView.RAIN_LEVEL_RAINSTORM);
        parcel.writeFloat(f);
    }

    public static void zza(Parcel parcel, int i, long j) {
        zzb(parcel, i, DetectedActivity.RUNNING);
        parcel.writeLong(j);
    }

    public static void zza(Parcel parcel, int i, Bundle bundle, boolean z) {
        if (bundle != null) {
            int zzG = zzG(parcel, i);
            parcel.writeBundle(bundle);
            zzH(parcel, zzG);
        }
    }

    public static void zza(Parcel parcel, int i, IBinder iBinder, boolean z) {
        if (iBinder != null) {
            int zzG = zzG(parcel, i);
            parcel.writeStrongBinder(iBinder);
            zzH(parcel, zzG);
        }
    }

    public static void zza(Parcel parcel, int i, Parcel parcel2, boolean z) {
        if (parcel2 != null) {
            int zzG = zzG(parcel, RainSurfaceView.RAIN_LEVEL_SHOWER);
            parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            zzH(parcel, zzG);
        }
    }

    public static void zza(Parcel parcel, int i, Parcelable parcelable, int i2, boolean z) {
        if (parcelable != null) {
            int zzG = zzG(parcel, i);
            parcelable.writeToParcel(parcel, i2);
            zzH(parcel, zzG);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zza(Parcel parcel, int i, Boolean bool, boolean z) {
        if (bool != null) {
            zzb(parcel, RainSurfaceView.RAIN_LEVEL_DOWNPOUR, RainSurfaceView.RAIN_LEVEL_RAINSTORM);
            parcel.writeInt(bool.booleanValue() ? 1 : 0);
        }
    }

    public static void zza(Parcel parcel, int i, Double d, boolean z) {
        if (d != null) {
            zzb(parcel, DetectedActivity.RUNNING, DetectedActivity.RUNNING);
            parcel.writeDouble(d.doubleValue());
        }
    }

    public static void zza(Parcel parcel, int i, Float f, boolean z) {
        if (f != null) {
            zzb(parcel, i, RainSurfaceView.RAIN_LEVEL_RAINSTORM);
            parcel.writeFloat(f.floatValue());
        }
    }

    public static void zza(Parcel parcel, int i, Integer num, boolean z) {
        if (num != null) {
            zzb(parcel, i, RainSurfaceView.RAIN_LEVEL_RAINSTORM);
            parcel.writeInt(num.intValue());
        }
    }

    public static void zza(Parcel parcel, int i, Long l, boolean z) {
        if (l != null) {
            zzb(parcel, i, DetectedActivity.RUNNING);
            parcel.writeLong(l.longValue());
        }
    }

    public static void zza(Parcel parcel, int i, String str, boolean z) {
        if (str != null) {
            int zzG = zzG(parcel, i);
            parcel.writeString(str);
            zzH(parcel, zzG);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zza(Parcel parcel, int i, List<Integer> list, boolean z) {
        if (list != null) {
            int zzG = zzG(parcel, i);
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeInt(((Integer) list.get(i2)).intValue());
            }
            zzH(parcel, zzG);
        }
    }

    public static void zza(Parcel parcel, int i, short s) {
        zzb(parcel, RainSurfaceView.RAIN_LEVEL_DOWNPOUR, RainSurfaceView.RAIN_LEVEL_RAINSTORM);
        parcel.writeInt(s);
    }

    public static void zza(Parcel parcel, int i, boolean z) {
        zzb(parcel, i, RainSurfaceView.RAIN_LEVEL_RAINSTORM);
        parcel.writeInt(z ? 1 : 0);
    }

    public static void zza(Parcel parcel, int i, byte[] bArr, boolean z) {
        if (bArr != null) {
            int zzG = zzG(parcel, i);
            parcel.writeByteArray(bArr);
            zzH(parcel, zzG);
        }
    }

    public static void zza(Parcel parcel, int i, float[] fArr, boolean z) {
        if (fArr != null) {
            int zzG = zzG(parcel, DetectedActivity.WALKING);
            parcel.writeFloatArray(fArr);
            zzH(parcel, zzG);
        }
    }

    public static void zza(Parcel parcel, int i, int[] iArr, boolean z) {
        if (iArr != null) {
            int zzG = zzG(parcel, i);
            parcel.writeIntArray(iArr);
            zzH(parcel, zzG);
        }
    }

    public static void zza(Parcel parcel, int i, long[] jArr, boolean z) {
        if (jArr != null) {
            int zzG = zzG(parcel, i);
            parcel.writeLongArray(jArr);
            zzH(parcel, zzG);
        }
    }

    public static <T extends Parcelable> void zza(Parcel parcel, int i, T[] tArr, int i2, boolean z) {
        if (tArr != null) {
            int zzG = zzG(parcel, i);
            int length = tArr.length;
            parcel.writeInt(length);
            for (int i3 = 0; i3 < length; i3++) {
                Parcelable parcelable = tArr[i3];
                if (parcelable == null) {
                    parcel.writeInt(0);
                } else {
                    zza(parcel, parcelable, i2);
                }
            }
            zzH(parcel, zzG);
        }
    }

    public static void zza(Parcel parcel, int i, String[] strArr, boolean z) {
        if (strArr != null) {
            int zzG = zzG(parcel, i);
            parcel.writeStringArray(strArr);
            zzH(parcel, zzG);
        }
    }

    public static void zza(Parcel parcel, int i, boolean[] zArr, boolean z) {
        if (zArr != null) {
            int zzG = zzG(parcel, i);
            parcel.writeBooleanArray(zArr);
            zzH(parcel, zzG);
        }
    }

    public static void zza(Parcel parcel, int i, byte[][] bArr, boolean z) {
        if (bArr != null) {
            int zzG = zzG(parcel, i);
            parcel.writeInt(r2);
            for (byte[] bArr2 : bArr) {
                parcel.writeByteArray(bArr2);
            }
            zzH(parcel, zzG);
        }
    }

    private static <T extends Parcelable> void zza(Parcel parcel, T t, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(1);
        int dataPosition2 = parcel.dataPosition();
        t.writeToParcel(parcel, i);
        int dataPosition3 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition3 - dataPosition2);
        parcel.setDataPosition(dataPosition3);
    }

    private static void zzb(Parcel parcel, int i, int i2) {
        if (i2 >= 65535) {
            parcel.writeInt(-65536 | i);
            parcel.writeInt(i2);
            return;
        }
        parcel.writeInt((i2 << 16) | i);
    }

    public static void zzb(Parcel parcel, int i, List<String> list, boolean z) {
        if (list != null) {
            int zzG = zzG(parcel, i);
            parcel.writeStringList(list);
            zzH(parcel, zzG);
        }
    }

    public static void zzc(Parcel parcel, int i, int i2) {
        zzb(parcel, i, RainSurfaceView.RAIN_LEVEL_RAINSTORM);
        parcel.writeInt(i2);
    }

    public static <T extends Parcelable> void zzc(Parcel parcel, int i, List<T> list, boolean z) {
        if (list != null) {
            int zzG = zzG(parcel, i);
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                Parcelable parcelable = (Parcelable) list.get(i2);
                if (parcelable == null) {
                    parcel.writeInt(0);
                } else {
                    zza(parcel, parcelable, 0);
                }
            }
            zzH(parcel, zzG);
        } else if (z) {
            zzb(parcel, i, 0);
        }
    }

    public static void zzd(Parcel parcel, int i, List list, boolean z) {
        if (list != null) {
            int zzG = zzG(parcel, i);
            parcel.writeList(list);
            zzH(parcel, zzG);
        }
    }

    public static int zze(Parcel parcel) {
        return zzG(parcel, 20293);
    }
}