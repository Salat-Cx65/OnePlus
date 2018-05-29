package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.Arrays;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class LocationRequest extends zza implements ReflectedParcelable {
    public static final Creator<LocationRequest> CREATOR;
    public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;
    public static final int PRIORITY_HIGH_ACCURACY = 100;
    public static final int PRIORITY_LOW_POWER = 104;
    public static final int PRIORITY_NO_POWER = 105;
    private int mPriority;
    private boolean zzaXh;
    private long zzbhK;
    private long zzbib;
    private long zzbic;
    private int zzbid;
    private float zzbie;
    private long zzbif;

    static {
        CREATOR = new zzq();
    }

    public LocationRequest() {
        this.mPriority = 102;
        this.zzbib = 3600000;
        this.zzbic = 600000;
        this.zzaXh = false;
        this.zzbhK = Long.MAX_VALUE;
        this.zzbid = Integer.MAX_VALUE;
        this.zzbie = 0.0f;
        this.zzbif = 0;
    }

    LocationRequest(int i, long j, long j2, boolean z, long j3, int i2, float f, long j4) {
        this.mPriority = i;
        this.zzbib = j;
        this.zzbic = j2;
        this.zzaXh = z;
        this.zzbhK = j3;
        this.zzbid = i2;
        this.zzbie = f;
        this.zzbif = j4;
    }

    public static LocationRequest create() {
        return new LocationRequest();
    }

    private static void zzI(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(new StringBuilder(38).append("invalid interval: ").append(j).toString());
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocationRequest)) {
            return false;
        }
        LocationRequest locationRequest = (LocationRequest) obj;
        return this.mPriority == locationRequest.mPriority && this.zzbib == locationRequest.zzbib && this.zzbic == locationRequest.zzbic && this.zzaXh == locationRequest.zzaXh && this.zzbhK == locationRequest.zzbhK && this.zzbid == locationRequest.zzbid && this.zzbie == locationRequest.zzbie && getMaxWaitTime() == locationRequest.getMaxWaitTime();
    }

    public final long getExpirationTime() {
        return this.zzbhK;
    }

    public final long getFastestInterval() {
        return this.zzbic;
    }

    public final long getInterval() {
        return this.zzbib;
    }

    public final long getMaxWaitTime() {
        long j = this.zzbif;
        return j < this.zzbib ? this.zzbib : j;
    }

    public final int getNumUpdates() {
        return this.zzbid;
    }

    public final int getPriority() {
        return this.mPriority;
    }

    public final float getSmallestDisplacement() {
        return this.zzbie;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.mPriority), Long.valueOf(this.zzbib), Float.valueOf(this.zzbie), Long.valueOf(this.zzbif)});
    }

    public final LocationRequest setExpirationDuration(long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (j > Long.MAX_VALUE - elapsedRealtime) {
            this.zzbhK = Long.MAX_VALUE;
        } else {
            this.zzbhK = elapsedRealtime + j;
        }
        if (this.zzbhK < 0) {
            this.zzbhK = 0;
        }
        return this;
    }

    public final LocationRequest setExpirationTime(long j) {
        this.zzbhK = j;
        if (this.zzbhK < 0) {
            this.zzbhK = 0;
        }
        return this;
    }

    public final LocationRequest setFastestInterval(long j) {
        zzI(j);
        this.zzaXh = true;
        this.zzbic = j;
        return this;
    }

    public final LocationRequest setInterval(long j) {
        zzI(j);
        this.zzbib = j;
        if (!this.zzaXh) {
            this.zzbic = (long) (((double) this.zzbib) / 6.0d);
        }
        return this;
    }

    public final LocationRequest setMaxWaitTime(long j) {
        zzI(j);
        this.zzbif = j;
        return this;
    }

    public final LocationRequest setNumUpdates(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException(new StringBuilder(31).append("invalid numUpdates: ").append(i).toString());
        }
        this.zzbid = i;
        return this;
    }

    public final LocationRequest setPriority(int i) {
        switch (i) {
            case PRIORITY_HIGH_ACCURACY:
            case PRIORITY_BALANCED_POWER_ACCURACY:
            case PRIORITY_LOW_POWER:
            case PRIORITY_NO_POWER:
                this.mPriority = i;
                return this;
            default:
                throw new IllegalArgumentException(new StringBuilder(28).append("invalid quality: ").append(i).toString());
        }
    }

    public final LocationRequest setSmallestDisplacement(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException(new StringBuilder(37).append("invalid displacement: ").append(f).toString());
        }
        this.zzbie = f;
        return this;
    }

    public final String toString() {
        String str;
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder append = stringBuilder.append("Request[");
        switch (this.mPriority) {
            case PRIORITY_HIGH_ACCURACY:
                str = "PRIORITY_HIGH_ACCURACY";
                break;
            case PRIORITY_BALANCED_POWER_ACCURACY:
                str = "PRIORITY_BALANCED_POWER_ACCURACY";
                break;
            case PRIORITY_LOW_POWER:
                str = "PRIORITY_LOW_POWER";
                break;
            case PRIORITY_NO_POWER:
                str = "PRIORITY_NO_POWER";
                break;
            default:
                str = "???";
                break;
        }
        append.append(str);
        if (this.mPriority != 105) {
            stringBuilder.append(" requested=");
            stringBuilder.append(this.zzbib).append("ms");
        }
        stringBuilder.append(" fastest=");
        stringBuilder.append(this.zzbic).append("ms");
        if (this.zzbif > this.zzbib) {
            stringBuilder.append(" maxWait=");
            stringBuilder.append(this.zzbif).append("ms");
        }
        if (this.zzbie > 0.0f) {
            stringBuilder.append(" smallestDisplacement=");
            stringBuilder.append(this.zzbie).append("m");
        }
        if (this.zzbhK != Long.MAX_VALUE) {
            long elapsedRealtime = this.zzbhK - SystemClock.elapsedRealtime();
            stringBuilder.append(" expireIn=");
            stringBuilder.append(elapsedRealtime).append("ms");
        }
        if (this.zzbid != Integer.MAX_VALUE) {
            stringBuilder.append(" num=").append(this.zzbid);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.mPriority);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzbib);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzbic);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzaXh);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.zzbhK);
        zzd.zzc(parcel, ConnectionResult.RESOLUTION_REQUIRED, this.zzbid);
        zzd.zza(parcel, (int) DetectedActivity.WALKING, this.zzbie);
        zzd.zza(parcel, (int) DetectedActivity.RUNNING, this.zzbif);
        zzd.zzI(parcel, zze);
    }
}
