package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.zzc;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzz implements Creator<zzy> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int i = 0;
        zzc[] com_google_android_gms_common_zzcArr = null;
        int zzd = zzb.zzd(parcel);
        Account account = null;
        Bundle bundle = null;
        Scope[] scopeArr = null;
        IBinder iBinder = null;
        String str = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED:
                    scopeArr = (Scope[]) zzb.zzb(parcel, readInt, Scope.CREATOR);
                    break;
                case DetectedActivity.WALKING:
                    bundle = zzb.zzs(parcel, readInt);
                    break;
                case DetectedActivity.RUNNING:
                    account = (Account) zzb.zza(parcel, readInt, Account.CREATOR);
                    break;
                case ConnectionResult.DEVELOPER_ERROR:
                    com_google_android_gms_common_zzcArr = (zzc[]) zzb.zzb(parcel, readInt, zzc.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzy(i3, i2, i, str, iBinder, scopeArr, bundle, account, com_google_android_gms_common_zzcArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzy[i];
    }
}
