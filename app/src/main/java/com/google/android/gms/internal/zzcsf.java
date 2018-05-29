package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcsf implements Creator<zzcrz> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        byte[][] bArr = null;
        int zzd = zzb.zzd(parcel);
        int[] iArr = null;
        byte[][] bArr2 = null;
        byte[][] bArr3 = null;
        byte[][] bArr4 = null;
        byte[][] bArr5 = null;
        byte[] bArr6 = null;
        String str = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    bArr6 = zzb.zzt(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    bArr5 = zzb.zzu(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    bArr4 = zzb.zzu(parcel, readInt);
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED:
                    bArr3 = zzb.zzu(parcel, readInt);
                    break;
                case DetectedActivity.WALKING:
                    bArr2 = zzb.zzu(parcel, readInt);
                    break;
                case DetectedActivity.RUNNING:
                    iArr = zzb.zzw(parcel, readInt);
                    break;
                case ConnectionResult.SERVICE_INVALID:
                    bArr = zzb.zzu(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcrz(str, bArr6, bArr5, bArr4, bArr3, bArr2, iArr, bArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcrz[i];
    }
}
