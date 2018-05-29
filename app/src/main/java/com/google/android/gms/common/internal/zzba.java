package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import net.oneplus.weather.R;

final class zzba implements zzay {
    private final IBinder zzrG;

    zzba(IBinder iBinder) {
        this.zzrG = iBinder;
    }

    public final IBinder asBinder() {
        return this.zzrG;
    }

    public final void zza(zzav com_google_android_gms_common_internal_zzav, zzy com_google_android_gms_common_internal_zzy) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
        obtain.writeStrongBinder(com_google_android_gms_common_internal_zzav != null ? com_google_android_gms_common_internal_zzav.asBinder() : null);
        if (com_google_android_gms_common_internal_zzy != null) {
            obtain.writeInt(1);
            com_google_android_gms_common_internal_zzy.writeToParcel(obtain, 0);
        } else {
            obtain.writeInt(0);
        }
        this.zzrG.transact(R.styleable.AppCompatTheme_checkboxStyle, obtain, obtain2, 0);
        obtain2.readException();
        obtain2.recycle();
        obtain.recycle();
    }
}
