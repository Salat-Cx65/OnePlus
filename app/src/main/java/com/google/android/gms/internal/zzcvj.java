package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbu;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcvj extends zza {
    public static final Creator<zzcvj> CREATOR;
    private final ConnectionResult zzaBS;
    private int zzakw;
    private final zzbu zzbCZ;

    static {
        CREATOR = new zzcvk();
    }

    public zzcvj(int i) {
        this(new ConnectionResult(8, null), null);
    }

    zzcvj(int i, ConnectionResult connectionResult, zzbu com_google_android_gms_common_internal_zzbu) {
        this.zzakw = i;
        this.zzaBS = connectionResult;
        this.zzbCZ = com_google_android_gms_common_internal_zzbu;
    }

    private zzcvj(ConnectionResult connectionResult, zzbu com_google_android_gms_common_internal_zzbu) {
        this(1, connectionResult, null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzaBS, i, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzbCZ, i, false);
        zzd.zzI(parcel, zze);
    }

    public final zzbu zzAv() {
        return this.zzbCZ;
    }

    public final ConnectionResult zzpx() {
        return this.zzaBS;
    }
}
