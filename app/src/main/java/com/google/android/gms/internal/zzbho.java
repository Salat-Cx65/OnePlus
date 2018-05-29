package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbho extends zza {
    public static final Creator<zzbho> CREATOR;
    private final zzbhq zzaID;
    private int zzakw;

    static {
        CREATOR = new zzbhp();
    }

    zzbho(int i, zzbhq com_google_android_gms_internal_zzbhq) {
        this.zzakw = i;
        this.zzaID = com_google_android_gms_internal_zzbhq;
    }

    private zzbho(zzbhq com_google_android_gms_internal_zzbhq) {
        this.zzakw = 1;
        this.zzaID = com_google_android_gms_internal_zzbhq;
    }

    public static zzbho zza(zzbhw<?, ?> com_google_android_gms_internal_zzbhw___) {
        if (com_google_android_gms_internal_zzbhw___ instanceof zzbhq) {
            return new zzbho((zzbhq) com_google_android_gms_internal_zzbhw___);
        }
        throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzaID, i, false);
        zzd.zzI(parcel, zze);
    }

    public final zzbhw<?, ?> zzrJ() {
        if (this.zzaID != null) {
            return this.zzaID;
        }
        throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
    }
}
