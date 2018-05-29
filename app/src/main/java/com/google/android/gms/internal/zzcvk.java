package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.zzbu;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcvk implements Creator<zzcvj> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ConnectionResult connectionResult = null;
        int i = 0;
        zzbu com_google_android_gms_common_internal_zzbu = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    connectionResult = (ConnectionResult) zzb.zza(parcel, readInt, ConnectionResult.CREATOR);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    com_google_android_gms_common_internal_zzbu = (zzbu) zzb.zza(parcel, readInt, zzbu.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcvj(i, connectionResult, com_google_android_gms_common_internal_zzbu);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcvj[i];
    }
}
