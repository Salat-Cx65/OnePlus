package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;

public abstract class zzceh extends zzee implements zzceg {
    public zzceh() {
        attachInterface(this, "com.google.android.gms.location.internal.IFusedLocationProviderCallback");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zza((zzcea) zzef.zza(parcel, zzcea.CREATOR));
        return true;
    }
}
