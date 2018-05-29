package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbhy implements Creator<zzbhv> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        zzbho com_google_android_gms_internal_zzbho = null;
        int i = 0;
        int zzd = zzb.zzd(parcel);
        String str = null;
        String str2 = null;
        boolean z = false;
        int i2 = 0;
        boolean z2 = false;
        int i3 = 0;
        int i4 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i4 = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case DetectedActivity.WALKING:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case DetectedActivity.RUNNING:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case ConnectionResult.SERVICE_INVALID:
                    com_google_android_gms_internal_zzbho = (zzbho) zzb.zza(parcel, readInt, zzbho.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbhv(i4, i3, z2, i2, z, str2, i, str, com_google_android_gms_internal_zzbho);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbhv[i];
    }
}
