package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbh;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcez extends zza {
    public static final Creator<zzcez> CREATOR;
    static final List<zzcdl> zzbiY;
    @Nullable
    private String mTag;
    private LocationRequest zzaXf;
    @Nullable
    private String zzanM;
    private List<zzcdl> zzbiZ;
    private boolean zzbja;
    private boolean zzbjb;
    private boolean zzbjc;
    private boolean zzbjd;

    static {
        zzbiY = Collections.emptyList();
        CREATOR = new zzcfa();
    }

    zzcez(LocationRequest locationRequest, List<zzcdl> list, @Nullable String str, boolean z, boolean z2, boolean z3, String str2) {
        this.zzbjd = true;
        this.zzaXf = locationRequest;
        this.zzbiZ = list;
        this.mTag = str;
        this.zzbja = z;
        this.zzbjb = z2;
        this.zzbjc = z3;
        this.zzanM = str2;
    }

    @Deprecated
    public static zzcez zza(LocationRequest locationRequest) {
        return new zzcez(locationRequest, zzbiY, null, false, false, false, null);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzcez)) {
            return false;
        }
        zzcez com_google_android_gms_internal_zzcez = (zzcez) obj;
        return zzbh.equal(this.zzaXf, com_google_android_gms_internal_zzcez.zzaXf) && zzbh.equal(this.zzbiZ, com_google_android_gms_internal_zzcez.zzbiZ) && zzbh.equal(this.mTag, com_google_android_gms_internal_zzcez.mTag) && this.zzbja == com_google_android_gms_internal_zzcez.zzbja && this.zzbjb == com_google_android_gms_internal_zzcez.zzbjb && this.zzbjc == com_google_android_gms_internal_zzcez.zzbjc && zzbh.equal(this.zzanM, com_google_android_gms_internal_zzcez.zzanM);
    }

    public final int hashCode() {
        return this.zzaXf.hashCode();
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.zzaXf.toString());
        if (this.mTag != null) {
            stringBuilder.append(" tag=").append(this.mTag);
        }
        if (this.zzanM != null) {
            stringBuilder.append(" moduleId=").append(this.zzanM);
        }
        stringBuilder.append(" hideAppOps=").append(this.zzbja);
        stringBuilder.append(" clients=").append(this.zzbiZ);
        stringBuilder.append(" forceCoarseLocation=").append(this.zzbjb);
        if (this.zzbjc) {
            stringBuilder.append(" exemptFromBackgroundThrottle");
        }
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzaXf, i, false);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.zzbiZ, false);
        zzd.zza(parcel, (int) ConnectionResult.RESOLUTION_REQUIRED, this.mTag, false);
        zzd.zza(parcel, (int) DetectedActivity.WALKING, this.zzbja);
        zzd.zza(parcel, (int) DetectedActivity.RUNNING, this.zzbjb);
        zzd.zza(parcel, (int) ConnectionResult.SERVICE_INVALID, this.zzbjc);
        zzd.zza(parcel, (int) ConnectionResult.DEVELOPER_ERROR, this.zzanM, false);
        zzd.zzI(parcel, zze);
    }
}
