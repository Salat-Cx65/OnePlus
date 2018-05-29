package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzc extends zza {
    public static final Creator<zzc> CREATOR;
    private String name;
    private int version;

    static {
        CREATOR = new zzd();
    }

    public zzc(String str, int i) {
        this.name = str;
        this.version = i;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.name, false);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_SHOWER, this.version);
        zzd.zzI(parcel, zze);
    }
}
