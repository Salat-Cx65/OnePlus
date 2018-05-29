package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzam;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.widget.WeatherCircleView;

public final class zzcvf extends zzed implements zzcve {
    zzcvf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.signin.internal.ISignInService");
    }

    public final void zza(zzam com_google_android_gms_common_internal_zzam, int i, boolean z) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) com_google_android_gms_common_internal_zzam);
        zzY.writeInt(i);
        zzef.zza(zzY, z);
        zzb(ConnectionResult.SERVICE_INVALID, zzY);
    }

    public final void zza(zzcvh com_google_android_gms_internal_zzcvh, zzcvc com_google_android_gms_internal_zzcvc) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (Parcelable) com_google_android_gms_internal_zzcvh);
        zzef.zza(zzY, (IInterface) com_google_android_gms_internal_zzcvc);
        zzb(WeatherCircleView.ARC_DIN, zzY);
    }

    public final void zzbu(int i) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeInt(i);
        zzb(DetectedActivity.WALKING, zzY);
    }
}
