package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbv implements Creator<zzbu> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        ConnectionResult connectionResult = null;
        boolean z = false;
        int zzd = zzb.zzd(parcel);
        boolean z2 = false;
        IBinder iBinder = null;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    connectionResult = (ConnectionResult) zzb.zza(parcel, readInt, ConnectionResult.CREATOR);
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    z = zzb.zzc(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbu(i, iBinder, connectionResult, z2, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbu[i];
    }
}
