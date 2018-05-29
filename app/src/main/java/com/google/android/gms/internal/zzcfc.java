package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcfc implements Creator<zzcfb> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        IBinder iBinder = null;
        int zzd = zzb.zzd(parcel);
        int i = 1;
        IBinder iBinder2 = null;
        PendingIntent pendingIntent = null;
        IBinder iBinder3 = null;
        zzcez com_google_android_gms_internal_zzcez = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    com_google_android_gms_internal_zzcez = (zzcez) zzb.zza(parcel, readInt, zzcez.CREATOR);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    iBinder3 = zzb.zzr(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    pendingIntent = (PendingIntent) zzb.zza(parcel, readInt, PendingIntent.CREATOR);
                    break;
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    iBinder2 = zzb.zzr(parcel, readInt);
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcfb(i, com_google_android_gms_internal_zzcez, iBinder3, pendingIntent, iBinder2, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcfb[i];
    }
}
