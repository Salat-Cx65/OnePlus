package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbf extends zzed implements zzbe {
    zzbf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ISignInButtonCreator");
    }

    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, zzbw com_google_android_gms_common_internal_zzbw) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) iObjectWrapper);
        zzef.zza(zzY, (Parcelable) com_google_android_gms_common_internal_zzbw);
        zzY = zza(RainSurfaceView.RAIN_LEVEL_SHOWER, zzY);
        IObjectWrapper zzM = zza.zzM(zzY.readStrongBinder());
        zzY.recycle();
        return zzM;
    }
}
