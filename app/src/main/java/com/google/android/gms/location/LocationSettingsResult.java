package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class LocationSettingsResult extends zza implements Result {
    public static final Creator<LocationSettingsResult> CREATOR;
    private final Status mStatus;
    private final LocationSettingsStates zzbiq;

    static {
        CREATOR = new zzw();
    }

    public LocationSettingsResult(Status status) {
        this(status, null);
    }

    public LocationSettingsResult(Status status, LocationSettingsStates locationSettingsStates) {
        this.mStatus = status;
        this.zzbiq = locationSettingsStates;
    }

    public final LocationSettingsStates getLocationSettingsStates() {
        return this.zzbiq;
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, getStatus(), i, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, getLocationSettingsStates(), i, false);
        zzd.zzI(parcel, zze);
    }
}
