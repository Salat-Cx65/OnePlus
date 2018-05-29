package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class LocationResult extends zza implements ReflectedParcelable {
    public static final Creator<LocationResult> CREATOR;
    static final List<Location> zzbig;
    private final List<Location> zzbih;

    static {
        zzbig = Collections.emptyList();
        CREATOR = new zzr();
    }

    LocationResult(List<Location> list) {
        this.zzbih = list;
    }

    public static LocationResult create(List<Location> list) {
        List list2;
        if (list == null) {
            list2 = zzbig;
        }
        return new LocationResult(list2);
    }

    public static LocationResult extractResult(Intent intent) {
        return !hasResult(intent) ? null : (LocationResult) intent.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
    }

    public static boolean hasResult(Intent intent) {
        return intent == null ? false : intent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof LocationResult)) {
            return false;
        }
        LocationResult locationResult = (LocationResult) obj;
        if (locationResult.zzbih.size() != this.zzbih.size()) {
            return false;
        }
        Iterator it = this.zzbih.iterator();
        for (Location location : locationResult.zzbih) {
            if (((Location) it.next()).getTime() != location.getTime()) {
                return false;
            }
        }
        return true;
    }

    public final Location getLastLocation() {
        int size = this.zzbih.size();
        return size == 0 ? null : (Location) this.zzbih.get(size - 1);
    }

    @NonNull
    public final List<Location> getLocations() {
        return this.zzbih;
    }

    public final int hashCode() {
        int i = 17;
        for (Location location : this.zzbih) {
            long time = location.getTime();
            i = ((int) (time ^ (time >>> 32))) + (i * 31);
        }
        return i;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzbih);
        return new StringBuilder(String.valueOf(valueOf).length() + 27).append("LocationResult[locations: ").append(valueOf).append("]").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, getLocations(), false);
        zzd.zzI(parcel, zze);
    }
}
