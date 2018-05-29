package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbs;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcvh extends zza {
    public static final Creator<zzcvh> CREATOR;
    private int zzakw;
    private zzbs zzbCY;

    static {
        CREATOR = new zzcvi();
    }

    zzcvh(int i, zzbs com_google_android_gms_common_internal_zzbs) {
        this.zzakw = i;
        this.zzbCY = com_google_android_gms_common_internal_zzbs;
    }

    public zzcvh(zzbs com_google_android_gms_common_internal_zzbs) {
        this(1, com_google_android_gms_common_internal_zzbs);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzbCY, i, false);
        zzd.zzI(parcel, zze);
    }
}
