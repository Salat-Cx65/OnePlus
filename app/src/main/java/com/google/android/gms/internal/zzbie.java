package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbie implements Creator<zzbib> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        ArrayList arrayList = null;
        int zzd = zzb.zzd(parcel);
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    arrayList = zzb.zzc(parcel, readInt, zzbic.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbib(i, str, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbib[i];
    }
}
