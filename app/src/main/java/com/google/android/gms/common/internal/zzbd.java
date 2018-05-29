package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.zzm;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbd extends zzed implements zzbb {
    zzbd(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IGoogleCertificatesApi");
    }

    public final boolean zza(zzm com_google_android_gms_common_zzm, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (Parcelable) com_google_android_gms_common_zzm);
        zzef.zza(zzY, (IInterface) iObjectWrapper);
        zzY = zza(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, zzY);
        boolean zza = zzef.zza(zzY);
        zzY.recycle();
        return zza;
    }

    public final boolean zze(String str, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzef.zza(zzY, (IInterface) iObjectWrapper);
        zzY = zza(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, zzY);
        boolean zza = zzef.zza(zzY);
        zzY.recycle();
        return zza;
    }

    public final boolean zzf(String str, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        zzef.zza(zzY, (IInterface) iObjectWrapper);
        zzY = zza(RainSurfaceView.RAIN_LEVEL_RAINSTORM, zzY);
        boolean zza = zzef.zza(zzY);
        zzY.recycle();
        return zza;
    }

    public final IObjectWrapper zzrE() throws RemoteException {
        Parcel zza = zza(1, zzY());
        IObjectWrapper zzM = zza.zzM(zza.readStrongBinder());
        zza.recycle();
        return zzM;
    }

    public final IObjectWrapper zzrF() throws RemoteException {
        Parcel zza = zza(RainSurfaceView.RAIN_LEVEL_SHOWER, zzY());
        IObjectWrapper zzM = zza.zzM(zza.readStrongBinder());
        zza.recycle();
        return zzM;
    }
}
