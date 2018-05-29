package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public final class zzbhm extends zzed implements zzbhl {
    zzbhm(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.service.ICommonService");
    }

    public final void zza(zzbhj com_google_android_gms_internal_zzbhj) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) com_google_android_gms_internal_zzbhj);
        zzc(1, zzY);
    }
}
