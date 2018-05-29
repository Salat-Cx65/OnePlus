package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzed;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzau extends zzed implements zzas {
    zzau(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ICertData");
    }

    public final IObjectWrapper zzoW() throws RemoteException {
        Parcel zza = zza(1, zzY());
        IObjectWrapper zzM = zza.zzM(zza.readStrongBinder());
        zza.recycle();
        return zzM;
    }

    public final int zzoX() throws RemoteException {
        Parcel zza = zza(RainSurfaceView.RAIN_LEVEL_SHOWER, zzY());
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }
}
