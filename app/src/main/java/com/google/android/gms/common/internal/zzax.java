package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzax extends zzed implements zzav {
    zzax(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IGmsCallbacks");
    }

    public final void zza(int i, Bundle bundle) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeInt(i);
        zzef.zza(zzY, (Parcelable) bundle);
        zzb(RainSurfaceView.RAIN_LEVEL_SHOWER, zzY);
    }

    public final void zza(int i, IBinder iBinder, Bundle bundle) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeInt(i);
        zzY.writeStrongBinder(iBinder);
        zzef.zza(zzY, (Parcelable) bundle);
        zzb(1, zzY);
    }
}
