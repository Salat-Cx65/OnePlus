package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.Arrays;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzy extends zza {
    public static final Creator<zzy> CREATOR;
    private long zzbiA;
    private int zzbix;
    private int zzbiy;
    private long zzbiz;

    static {
        CREATOR = new zzz();
    }

    zzy(int i, int i2, long j, long j2) {
        this.zzbix = i;
        this.zzbiy = i2;
        this.zzbiz = j;
        this.zzbiA = j2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzy com_google_android_gms_location_zzy = (zzy) obj;
        return this.zzbix == com_google_android_gms_location_zzy.zzbix && this.zzbiy == com_google_android_gms_location_zzy.zzbiy && this.zzbiz == com_google_android_gms_location_zzy.zzbiz && this.zzbiA == com_google_android_gms_location_zzy.zzbiA;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzbiy), Integer.valueOf(this.zzbix), Long.valueOf(this.zzbiA), Long.valueOf(this.zzbiz)});
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder("NetworkLocationStatus:");
        stringBuilder.append(" Wifi status: ").append(this.zzbix).append(" Cell status: ").append(this.zzbiy).append(" elapsed time NS: ").append(this.zzbiA).append(" system time ms: ").append(this.zzbiz);
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzbix);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzbiy);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzbiz);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzbiA);
        zzd.zzI(parcel, zze);
    }
}
