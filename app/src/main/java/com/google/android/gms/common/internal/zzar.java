package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.internal.zzed;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzar extends zzed implements zzap {
    zzar(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ICancelToken");
    }

    public final void cancel() throws RemoteException {
        zzc(RainSurfaceView.RAIN_LEVEL_SHOWER, zzY());
    }
}
