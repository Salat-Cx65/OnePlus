package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.location.zzj;
import com.google.android.gms.location.zzk;
import com.google.android.gms.location.zzm;
import com.google.android.gms.location.zzn;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcfb extends zza {
    public static final Creator<zzcfb> CREATOR;
    private PendingIntent mPendingIntent;
    private int zzbje;
    private zzcez zzbjf;
    private zzm zzbjg;
    private zzj zzbjh;
    private zzceg zzbji;

    static {
        CREATOR = new zzcfc();
    }

    zzcfb(int i, zzcez com_google_android_gms_internal_zzcez, IBinder iBinder, PendingIntent pendingIntent, IBinder iBinder2, IBinder iBinder3) {
        zzceg com_google_android_gms_internal_zzceg = null;
        this.zzbje = i;
        this.zzbjf = com_google_android_gms_internal_zzcez;
        this.zzbjg = iBinder == null ? null : zzn.zzZ(iBinder);
        this.mPendingIntent = pendingIntent;
        this.zzbjh = iBinder2 == null ? null : zzk.zzY(iBinder2);
        if (!(iBinder3 == null || iBinder3 == null)) {
            IInterface queryLocalInterface = iBinder3.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
            com_google_android_gms_internal_zzceg = queryLocalInterface instanceof zzceg ? (zzceg) queryLocalInterface : new zzcei(iBinder3);
        }
        this.zzbji = com_google_android_gms_internal_zzceg;
    }

    public static zzcfb zza(zzj com_google_android_gms_location_zzj, @Nullable zzceg com_google_android_gms_internal_zzceg) {
        return new zzcfb(2, null, null, null, com_google_android_gms_location_zzj.asBinder(), com_google_android_gms_internal_zzceg != null ? com_google_android_gms_internal_zzceg.asBinder() : null);
    }

    public static zzcfb zza(zzm com_google_android_gms_location_zzm, @Nullable zzceg com_google_android_gms_internal_zzceg) {
        return new zzcfb(2, null, com_google_android_gms_location_zzm.asBinder(), null, null, com_google_android_gms_internal_zzceg != null ? com_google_android_gms_internal_zzceg.asBinder() : null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder = null;
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzbje);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzbjf, i, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzbjg == null ? null : this.zzbjg.asBinder(), false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.mPendingIntent, i, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.zzbjh == null ? null : this.zzbjh.asBinder(), false);
        if (this.zzbji != null) {
            iBinder = this.zzbji.asBinder();
        }
        zzd.zza(parcel, (int) ConnectionResult.RESOLUTION_REQUIRED, iBinder, false);
        zzd.zzI(parcel, zze);
    }
}
