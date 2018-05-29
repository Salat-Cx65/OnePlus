package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzt extends zza {
    public static final Creator<zzt> CREATOR;
    private final String zzbii;
    private final String zzbij;
    private final int zzbik;
    private final boolean zzbil;

    static {
        CREATOR = new zzu();
    }

    zzt(String str, String str2, int i, boolean z) {
        this.zzbii = str;
        this.zzbij = str2;
        this.zzbik = i;
        this.zzbil = z;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzbii, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzbij, false);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzbik);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzbil);
        zzd.zzI(parcel, zze);
    }
}
