package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbht implements Creator<zzbhr> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int i = 0;
        int zzd = zzb.zzd(parcel);
        String str = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    i = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbhr(i2, str, i);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbhr[i];
    }
}
