package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class zzaw extends zzee implements zzav {
    public zzaw() {
        attachInterface(this, "com.google.android.gms.common.internal.IGmsCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                zza(parcel.readInt(), parcel.readStrongBinder(), (Bundle) zzef.zza(parcel, Bundle.CREATOR));
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                zza(parcel.readInt(), (Bundle) zzef.zza(parcel, Bundle.CREATOR));
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
