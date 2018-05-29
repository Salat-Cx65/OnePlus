package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.GeofenceStatusCodes;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzf implements Creator<DataHolder> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int i = 0;
        Bundle bundle = null;
        int zzd = zzb.zzd(parcel);
        CursorWindow[] cursorWindowArr = null;
        String[] strArr = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    strArr = zzb.zzA(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    cursorWindowArr = (CursorWindow[]) zzb.zzb(parcel, readInt, CursorWindow.CREATOR);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    bundle = zzb.zzs(parcel, readInt);
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        DataHolder dataHolder = new DataHolder(i2, strArr, cursorWindowArr, i, bundle);
        dataHolder.zzqP();
        return dataHolder;
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DataHolder[i];
    }
}
