package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbhr extends zza {
    public static final Creator<zzbhr> CREATOR;
    private int versionCode;
    final String zzaIH;
    final int zzaII;

    static {
        CREATOR = new zzbht();
    }

    zzbhr(int i, String str, int i2) {
        this.versionCode = i;
        this.zzaIH = str;
        this.zzaII = i2;
    }

    zzbhr(String str, int i) {
        this.versionCode = 1;
        this.zzaIH = str;
        this.zzaII = i;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzaIH, false);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzaII);
        zzd.zzI(parcel, zze);
    }
}
