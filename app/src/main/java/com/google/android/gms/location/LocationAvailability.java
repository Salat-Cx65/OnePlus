package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.Arrays;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class LocationAvailability extends zza implements ReflectedParcelable {
    public static final Creator<LocationAvailability> CREATOR;
    @Deprecated
    private int zzbhW;
    @Deprecated
    private int zzbhX;
    private long zzbhY;
    private int zzbhZ;
    private zzy[] zzbia;

    static {
        CREATOR = new zzp();
    }

    LocationAvailability(int i, int i2, int i3, long j, zzy[] com_google_android_gms_location_zzyArr) {
        this.zzbhZ = i;
        this.zzbhW = i2;
        this.zzbhX = i3;
        this.zzbhY = j;
        this.zzbia = com_google_android_gms_location_zzyArr;
    }

    public static LocationAvailability extractLocationAvailability(Intent intent) {
        return !hasLocationAvailability(intent) ? null : (LocationAvailability) intent.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_AVAILABILITY");
    }

    public static boolean hasLocationAvailability(Intent intent) {
        return intent == null ? false : intent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_AVAILABILITY");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LocationAvailability locationAvailability = (LocationAvailability) obj;
        return this.zzbhW == locationAvailability.zzbhW && this.zzbhX == locationAvailability.zzbhX && this.zzbhY == locationAvailability.zzbhY && this.zzbhZ == locationAvailability.zzbhZ && Arrays.equals(this.zzbia, locationAvailability.zzbia);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzbhZ), Integer.valueOf(this.zzbhW), Integer.valueOf(this.zzbhX), Long.valueOf(this.zzbhY), this.zzbia});
    }

    public final boolean isLocationAvailable() {
        return this.zzbhZ < 1000;
    }

    public final String toString() {
        return new StringBuilder(48).append("LocationAvailability[isLocationAvailable: ").append(isLocationAvailable()).append("]").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzbhW);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzbhX);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzbhY);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzbhZ);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.zzbia, i, false);
        zzd.zzI(parcel, zze);
    }
}
