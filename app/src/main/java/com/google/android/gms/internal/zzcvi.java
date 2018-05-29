package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.zzbs;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcvi implements Creator<zzcvh> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        zzbs com_google_android_gms_common_internal_zzbs = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    com_google_android_gms_common_internal_zzbs = (zzbs) zzb.zza(parcel, readInt, zzbs.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcvh(i, com_google_android_gms_common_internal_zzbs);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcvh[i];
    }
}
