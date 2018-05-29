package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationRequest;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcfa implements Creator<zzcez> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String str = null;
        boolean z = false;
        int zzd = zzb.zzd(parcel);
        List list = zzcez.zzbiY;
        boolean z2 = false;
        boolean z3 = false;
        String str2 = null;
        LocationRequest locationRequest = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    locationRequest = (LocationRequest) zzb.zza(parcel, readInt, LocationRequest.CREATOR);
                    break;
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    list = zzb.zzc(parcel, readInt, zzcdl.CREATOR);
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case DetectedActivity.WALKING:
                    z3 = zzb.zzc(parcel, readInt);
                    break;
                case DetectedActivity.RUNNING:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case ConnectionResult.SERVICE_INVALID:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case ConnectionResult.DEVELOPER_ERROR:
                    str = zzb.zzq(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcez(locationRequest, list, str2, z3, z2, z, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcez[i];
    }
}
