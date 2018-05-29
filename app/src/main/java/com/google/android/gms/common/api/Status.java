package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender.SendIntentException;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbh;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.Arrays;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class Status extends zza implements Result, ReflectedParcelable {
    public static final Creator<Status> CREATOR;
    public static final Status zzaBo;
    public static final Status zzaBp;
    public static final Status zzaBq;
    public static final Status zzaBr;
    public static final Status zzaBs;
    private static Status zzaBt;
    private static Status zzaBu;
    private final PendingIntent mPendingIntent;
    private final String zzaAa;
    private int zzakw;
    private final int zzaxw;

    static {
        zzaBo = new Status(0);
        zzaBp = new Status(14);
        zzaBq = new Status(8);
        zzaBr = new Status(15);
        zzaBs = new Status(16);
        zzaBt = new Status(17);
        zzaBu = new Status(18);
        CREATOR = new zzf();
    }

    public Status(int i) {
        this(i, null);
    }

    Status(int i, int i2, String str, PendingIntent pendingIntent) {
        this.zzakw = i;
        this.zzaxw = i2;
        this.zzaAa = str;
        this.mPendingIntent = pendingIntent;
    }

    public Status(int i, String str) {
        this(1, i, str, null);
    }

    public Status(int i, String str, PendingIntent pendingIntent) {
        this(1, i, str, pendingIntent);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Status)) {
            return false;
        }
        Status status = (Status) obj;
        return this.zzakw == status.zzakw && this.zzaxw == status.zzaxw && zzbh.equal(this.zzaAa, status.zzaAa) && zzbh.equal(this.mPendingIntent, status.mPendingIntent);
    }

    public final PendingIntent getResolution() {
        return this.mPendingIntent;
    }

    public final Status getStatus() {
        return this;
    }

    public final int getStatusCode() {
        return this.zzaxw;
    }

    @Nullable
    public final String getStatusMessage() {
        return this.zzaAa;
    }

    public final boolean hasResolution() {
        return this.mPendingIntent != null;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzakw), Integer.valueOf(this.zzaxw), this.zzaAa, this.mPendingIntent});
    }

    public final boolean isCanceled() {
        return this.zzaxw == 16;
    }

    public final boolean isInterrupted() {
        return this.zzaxw == 14;
    }

    public final boolean isSuccess() {
        return this.zzaxw <= 0;
    }

    public final void startResolutionForResult(Activity activity, int i) throws SendIntentException {
        if (hasResolution()) {
            activity.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), i, null, 0, 0, 0);
        }
    }

    public final String toString() {
        return zzbh.zzt(this).zzg("statusCode", zzpo()).zzg("resolution", this.mPendingIntent).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, getStatusCode());
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, getStatusMessage(), false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.mPendingIntent, i, false);
        zzd.zzc(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, this.zzakw);
        zzd.zzI(parcel, zze);
    }

    public final String zzpo() {
        return this.zzaAa != null ? this.zzaAa : CommonStatusCodes.getStatusCodeString(this.zzaxw);
    }
}
