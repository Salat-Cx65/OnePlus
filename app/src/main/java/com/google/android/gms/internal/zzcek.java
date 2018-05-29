package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.RemoteException;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class zzcek extends zzee implements zzcej {
    public zzcek() {
        attachInterface(this, "com.google.android.gms.location.internal.IGeofencerCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                zza(parcel.readInt(), parcel.createStringArray());
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                zzb(parcel.readInt(), parcel.createStringArray());
                break;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                zza(parcel.readInt(), (PendingIntent) zzef.zza(parcel, PendingIntent.CREATOR));
                break;
            default:
                return false;
        }
        return true;
    }
}
