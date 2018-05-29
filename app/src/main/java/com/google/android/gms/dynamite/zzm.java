package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzm extends zzed implements zzl {
    zzm(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoaderV2");
    }

    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String str, int i, IObjectWrapper iObjectWrapper2) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) iObjectWrapper);
        zzY.writeString(str);
        zzY.writeInt(i);
        zzef.zza(zzY, (IInterface) iObjectWrapper2);
        zzY = zza(RainSurfaceView.RAIN_LEVEL_SHOWER, zzY);
        IObjectWrapper zzM = zza.zzM(zzY.readStrongBinder());
        zzY.recycle();
        return zzM;
    }
}
