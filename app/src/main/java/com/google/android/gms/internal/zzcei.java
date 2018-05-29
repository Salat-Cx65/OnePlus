package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public final class zzcei extends zzed implements zzceg {
    zzcei(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.internal.IFusedLocationProviderCallback");
    }

    public final void zza(zzcea com_google_android_gms_internal_zzcea) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (Parcelable) com_google_android_gms_internal_zzcea);
        zzc(1, zzY);
    }
}
