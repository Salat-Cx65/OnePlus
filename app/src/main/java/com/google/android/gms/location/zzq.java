package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.widget.AutoScrollHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.oneplus.lib.preference.Preference;
import net.oneplus.weather.util.GlobalConfig;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzq implements Creator<LocationRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
        long j = GlobalConfig.ONE_HOUR_TIME;
        long j2 = 600000;
        boolean z = false;
        long j3 = Long.MAX_VALUE;
        int i2 = Preference.DEFAULT_ORDER;
        float f = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        long j4 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    j3 = zzb.zzi(parcel, readInt);
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case DetectedActivity.WALKING:
                    f = zzb.zzl(parcel, readInt);
                    break;
                case DetectedActivity.RUNNING:
                    j4 = zzb.zzi(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new LocationRequest(i, j, j2, z, j3, i2, f, j4);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new LocationRequest[i];
    }
}
