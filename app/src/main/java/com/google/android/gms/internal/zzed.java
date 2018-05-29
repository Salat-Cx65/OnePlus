package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public class zzed implements IInterface {
    private final IBinder zzrG;
    private final String zzrH;

    protected zzed(IBinder iBinder, String str) {
        this.zzrG = iBinder;
        this.zzrH = str;
    }

    public IBinder asBinder() {
        return this.zzrG;
    }

    protected final Parcel zzY() {
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken(this.zzrH);
        return obtain;
    }

    protected final Parcel zza(int i, Parcel parcel) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            this.zzrG.transact(i, parcel, obtain, 0);
            obtain.readException();
            parcel.recycle();
            return obtain;
        } catch (RuntimeException e) {
            obtain.recycle();
            throw e;
        } catch (Throwable th) {
        }
    }

    protected final void zzb(int i, Parcel parcel) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        this.zzrG.transact(i, parcel, obtain, 0);
        obtain.readException();
        parcel.recycle();
        obtain.recycle();
    }

    protected final void zzc(int i, Parcel parcel) throws RemoteException {
        this.zzrG.transact(i, parcel, null, 1);
        parcel.recycle();
    }
}
