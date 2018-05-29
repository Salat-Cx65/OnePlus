package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbig implements Creator<zzbif> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        zzbia com_google_android_gms_internal_zzbia = null;
        int zzd = zzb.zzd(parcel);
        int i = 0;
        Parcel parcel2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    parcel2 = zzb.zzD(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    com_google_android_gms_internal_zzbia = (zzbia) zzb.zza(parcel, readInt, zzbia.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbif(i, parcel2, com_google_android_gms_internal_zzbia);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbif[i];
    }
}
