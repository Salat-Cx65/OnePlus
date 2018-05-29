package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzff extends zzed implements zzfd {
    zzff(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
    }

    public final String getId() throws RemoteException {
        Parcel zza = zza(1, zzY());
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    public final boolean zzb(boolean z) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, z);
        zzY = zza(RainSurfaceView.RAIN_LEVEL_SHOWER, zzY);
        boolean zza = zzef.zza(zzY);
        zzY.recycle();
        return zza;
    }

    public final void zzc(String str, boolean z) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzef.zza(zzY, z);
        zzb(RainSurfaceView.RAIN_LEVEL_RAINSTORM, zzY);
    }

    public final String zzq(String str) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzY = zza(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, zzY);
        String readString = zzY.readString();
        zzY.recycle();
        return readString;
    }
}
