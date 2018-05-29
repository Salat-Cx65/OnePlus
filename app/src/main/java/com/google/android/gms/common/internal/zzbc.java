package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.zzm;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class zzbc extends zzee implements zzbb {
    public static zzbb zzJ(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGoogleCertificatesApi");
        return queryLocalInterface instanceof zzbb ? (zzbb) queryLocalInterface : new zzbd(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        IInterface zzrE;
        boolean zze;
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                zzrE = zzrE();
                parcel2.writeNoException();
                zzef.zza(parcel2, zzrE);
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                zzrE = zzrF();
                parcel2.writeNoException();
                zzef.zza(parcel2, zzrE);
                break;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                zze = zze(parcel.readString(), zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzef.zza(parcel2, zze);
                break;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                zze = zzf(parcel.readString(), zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzef.zza(parcel2, zze);
                break;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                zze = zza((zzm) zzef.zza(parcel, zzm.CREATOR), zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzef.zza(parcel2, zze);
                break;
            default:
                return false;
        }
        return true;
    }
}
