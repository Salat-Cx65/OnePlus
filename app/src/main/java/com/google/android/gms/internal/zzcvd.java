package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class zzcvd extends zzee implements zzcvc {
    public zzcvd() {
        attachInterface(this, "com.google.android.gms.signin.internal.ISignInCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                zzef.zza(parcel, ConnectionResult.CREATOR);
                zzef.zza(parcel, zzcuz.CREATOR);
                break;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                zzef.zza(parcel, Status.CREATOR);
                break;
            case ConnectionResult.RESOLUTION_REQUIRED:
                zzef.zza(parcel, Status.CREATOR);
                break;
            case DetectedActivity.WALKING:
                zzef.zza(parcel, Status.CREATOR);
                zzef.zza(parcel, GoogleSignInAccount.CREATOR);
                break;
            case DetectedActivity.RUNNING:
                zzb((zzcvj) zzef.zza(parcel, zzcvj.CREATOR));
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
