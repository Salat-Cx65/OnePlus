package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzp implements Creator<LocationAvailability> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int i = 1;
        int zzd = zzb.zzd(parcel);
        int i2 = GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE;
        long j = 0;
        zzy[] com_google_android_gms_location_zzyArr = null;
        int i3 = 1;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    com_google_android_gms_location_zzyArr = (zzy[]) zzb.zzb(parcel, readInt, zzy.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new LocationAvailability(i2, i3, i, j, com_google_android_gms_location_zzyArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new LocationAvailability[i];
    }
}
