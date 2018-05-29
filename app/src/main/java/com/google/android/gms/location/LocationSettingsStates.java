package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.safeparcel.zze;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class LocationSettingsStates extends zza {
    public static final Creator<LocationSettingsStates> CREATOR;
    private final boolean zzbir;
    private final boolean zzbis;
    private final boolean zzbit;
    private final boolean zzbiu;
    private final boolean zzbiv;
    private final boolean zzbiw;

    static {
        CREATOR = new zzx();
    }

    public LocationSettingsStates(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.zzbir = z;
        this.zzbis = z2;
        this.zzbit = z3;
        this.zzbiu = z4;
        this.zzbiv = z5;
        this.zzbiw = z6;
    }

    public static LocationSettingsStates fromIntent(Intent intent) {
        return (LocationSettingsStates) zze.zza(intent, "com.google.android.gms.location.LOCATION_SETTINGS_STATES", CREATOR);
    }

    public final boolean isBlePresent() {
        return this.zzbiw;
    }

    public final boolean isBleUsable() {
        return this.zzbit;
    }

    public final boolean isGpsPresent() {
        return this.zzbiu;
    }

    public final boolean isGpsUsable() {
        return this.zzbir;
    }

    public final boolean isLocationPresent() {
        return this.zzbiu || this.zzbiv;
    }

    public final boolean isLocationUsable() {
        return this.zzbir || this.zzbis;
    }

    public final boolean isNetworkLocationPresent() {
        return this.zzbiv;
    }

    public final boolean isNetworkLocationUsable() {
        return this.zzbis;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, isGpsUsable());
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, isNetworkLocationUsable());
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, isBleUsable());
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, isGpsPresent());
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, isNetworkLocationPresent());
        zzd.zza(parcel, (int) ConnectionResult.RESOLUTION_REQUIRED, isBlePresent());
        zzd.zzI(parcel, zze);
    }
}
