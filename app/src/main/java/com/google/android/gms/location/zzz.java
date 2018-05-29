package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzz implements Creator<zzy> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long j = -1;
        int i = 1;
        int zzd = zzb.zzd(parcel);
        long j2 = -1;
        int i2 = 1;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    j = zzb.zzi(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzy(i2, i, j2, j);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzy[i];
    }
}
