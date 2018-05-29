package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbic extends zza {
    public static final Creator<zzbic> CREATOR;
    final String key;
    private int versionCode;
    final zzbhv<?, ?> zzaIX;

    static {
        CREATOR = new zzbhz();
    }

    zzbic(int i, String str, zzbhv<?, ?> com_google_android_gms_internal_zzbhv___) {
        this.versionCode = i;
        this.key = str;
        this.zzaIX = com_google_android_gms_internal_zzbhv___;
    }

    zzbic(String str, zzbhv<?, ?> com_google_android_gms_internal_zzbhv___) {
        this.versionCode = 1;
        this.key = str;
        this.zzaIX = com_google_android_gms_internal_zzbhv___;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.key, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzaIX, i, false);
        zzd.zzI(parcel, zze);
    }
}
