package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.widget.AutoScrollHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcfe implements Creator<zzcfd> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        int i = 0;
        short s = (short) 0;
        double d = 0.0d;
        double d2 = 0.0d;
        float f = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        long j = 0;
        int i2 = 0;
        int i3 = -1;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    s = zzb.zzf(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    d = zzb.zzn(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    d2 = zzb.zzn(parcel, readInt);
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED:
                    f = zzb.zzl(parcel, readInt);
                    break;
                case DetectedActivity.WALKING:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case DetectedActivity.RUNNING:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case ConnectionResult.SERVICE_INVALID:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcfd(str, i, s, d, d2, f, j, i2, i3);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcfd[i];
    }
}
