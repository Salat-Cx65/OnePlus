package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class zzcbp extends zzee implements zzcbo {
    public zzcbp() {
        attachInterface(this, "com.google.android.gms.flags.IFlagProvider");
    }

    public static zzcbo asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.flags.IFlagProvider");
        return queryLocalInterface instanceof zzcbo ? (zzcbo) queryLocalInterface : new zzcbq(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                init(zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                boolean booleanFlagValue = getBooleanFlagValue(parcel.readString(), zzef.zza(parcel), parcel.readInt());
                parcel2.writeNoException();
                zzef.zza(parcel2, booleanFlagValue);
                return true;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                int intFlagValue = getIntFlagValue(parcel.readString(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(intFlagValue);
                return true;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                long longFlagValue = getLongFlagValue(parcel.readString(), parcel.readLong(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeLong(longFlagValue);
                return true;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                String stringFlagValue = getStringFlagValue(parcel.readString(), parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeString(stringFlagValue);
                return true;
            default:
                return false;
        }
    }
}
