package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class LocationSettingsRequest extends zza {
    public static final Creator<LocationSettingsRequest> CREATOR;
    private final List<LocationRequest> zzaXc;
    private final boolean zzbim;
    private final boolean zzbin;
    private zzt zzbio;

    public static final class Builder {
        private boolean zzbim;
        private boolean zzbin;
        private zzt zzbio;
        private final ArrayList<LocationRequest> zzbip;

        public Builder() {
            this.zzbip = new ArrayList();
            this.zzbim = false;
            this.zzbin = false;
            this.zzbio = null;
        }

        public final com.google.android.gms.location.LocationSettingsRequest.Builder addAllLocationRequests(Collection<LocationRequest> collection) {
            for (LocationRequest locationRequest : collection) {
                if (locationRequest != null) {
                    this.zzbip.add(locationRequest);
                }
            }
            return this;
        }

        public final com.google.android.gms.location.LocationSettingsRequest.Builder addLocationRequest(@NonNull LocationRequest locationRequest) {
            if (locationRequest != null) {
                this.zzbip.add(locationRequest);
            }
            return this;
        }

        public final LocationSettingsRequest build() {
            return new LocationSettingsRequest(this.zzbip, this.zzbim, this.zzbin, null);
        }

        public final com.google.android.gms.location.LocationSettingsRequest.Builder setAlwaysShow(boolean z) {
            this.zzbim = z;
            return this;
        }

        public final com.google.android.gms.location.LocationSettingsRequest.Builder setNeedBle(boolean z) {
            this.zzbin = z;
            return this;
        }
    }

    static {
        CREATOR = new zzv();
    }

    LocationSettingsRequest(List<LocationRequest> list, boolean z, boolean z2, zzt com_google_android_gms_location_zzt) {
        this.zzaXc = list;
        this.zzbim = z;
        this.zzbin = z2;
        this.zzbio = com_google_android_gms_location_zzt;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, Collections.unmodifiableList(this.zzaXc), false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzbim);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzbin);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.zzbio, i, false);
        zzd.zzI(parcel, zze);
    }
}
