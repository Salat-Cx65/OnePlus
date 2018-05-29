package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcbq extends zzed implements zzcbo {
    zzcbq(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.flags.IFlagProvider");
    }

    public final boolean getBooleanFlagValue(String str, boolean z, int i) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzef.zza(zzY, z);
        zzY.writeInt(i);
        zzY = zza(RainSurfaceView.RAIN_LEVEL_SHOWER, zzY);
        boolean zza = zzef.zza(zzY);
        zzY.recycle();
        return zza;
    }

    public final int getIntFlagValue(String str, int i, int i2) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzY.writeInt(i);
        zzY.writeInt(i2);
        zzY = zza(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, zzY);
        int readInt = zzY.readInt();
        zzY.recycle();
        return readInt;
    }

    public final long getLongFlagValue(String str, long j, int i) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzY.writeLong(j);
        zzY.writeInt(i);
        zzY = zza(RainSurfaceView.RAIN_LEVEL_RAINSTORM, zzY);
        long readLong = zzY.readLong();
        zzY.recycle();
        return readLong;
    }

    public final String getStringFlagValue(String str, String str2, int i) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzY.writeString(str2);
        zzY.writeInt(i);
        zzY = zza(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, zzY);
        String readString = zzY.readString();
        zzY.recycle();
        return readString;
    }

    public final void init(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) iObjectWrapper);
        zzb(1, zzY);
    }
}
