package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.widget.AutoScrollHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.DetectedActivity;
import java.util.List;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzd implements Creator<WakeLockEvent> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        long j = 0;
        int i2 = 0;
        String str = null;
        int i3 = 0;
        List list = null;
        String str2 = null;
        long j2 = 0;
        int i4 = 0;
        String str3 = null;
        String str4 = null;
        float f = AutoScrollHelper.RELATIVE_UNSPECIFIED;
        long j3 = 0;
        String str5 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED:
                    list = zzb.zzC(parcel, readInt);
                    break;
                case DetectedActivity.RUNNING:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case ConnectionResult.DEVELOPER_ERROR:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case ConnectionResult.LICENSE_CHECK_FAILED:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case WeatherCircleView.ARC_DIN:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case ConnectionResult.CANCELED:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case ConnectionResult.TIMEOUT:
                    i4 = zzb.zzg(parcel, readInt);
                    break;
                case ConnectionResult.INTERRUPTED:
                    f = zzb.zzl(parcel, readInt);
                    break;
                case ConnectionResult.API_UNAVAILABLE:
                    j3 = zzb.zzi(parcel, readInt);
                    break;
                case ConnectionResult.SIGN_IN_FAILED:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new WakeLockEvent(i, j, i2, str, i3, list, str2, j2, i4, str3, str4, f, j3, str5);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new WakeLockEvent[i];
    }
}
