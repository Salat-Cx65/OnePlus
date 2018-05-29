package com.google.android.gms.location;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzl extends zzed implements zzj {
    zzl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.ILocationCallback");
    }

    public final void onLocationAvailability(LocationAvailability locationAvailability) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (Parcelable) locationAvailability);
        zzc(RainSurfaceView.RAIN_LEVEL_SHOWER, zzY);
    }

    public final void onLocationResult(LocationResult locationResult) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (Parcelable) locationResult);
        zzc(1, zzY);
    }
}
