package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.internal.zzn;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzd implements Creator<GoogleSignInOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean z = false;
        ArrayList arrayList = null;
        int zzd = zzb.zzd(parcel);
        String str = null;
        String str2 = null;
        boolean z2 = false;
        boolean z3 = false;
        Account account = null;
        ArrayList arrayList2 = null;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    arrayList2 = zzb.zzc(parcel, readInt, Scope.CREATOR);
                    break;
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    account = (Account) zzb.zza(parcel, readInt, Account.CREATOR);
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    z3 = zzb.zzc(parcel, readInt);
                    break;
                case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case DetectedActivity.WALKING:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case DetectedActivity.RUNNING:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case ConnectionResult.SERVICE_INVALID:
                    arrayList = zzb.zzc(parcel, readInt, zzn.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new GoogleSignInOptions(i, arrayList2, account, z3, z2, z, str2, str, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new GoogleSignInOptions[i];
    }
}
