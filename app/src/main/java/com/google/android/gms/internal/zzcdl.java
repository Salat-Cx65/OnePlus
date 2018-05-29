package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbh;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcdl extends zza {
    public static final Creator<zzcdl> CREATOR;
    private String packageName;
    private int uid;

    static {
        CREATOR = new zzcdm();
    }

    public zzcdl(int i, String str) {
        this.uid = i;
        this.packageName = str;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof zzcdl)) {
            return false;
        }
        zzcdl com_google_android_gms_internal_zzcdl = (zzcdl) obj;
        return com_google_android_gms_internal_zzcdl.uid == this.uid && zzbh.equal(com_google_android_gms_internal_zzcdl.packageName, this.packageName);
    }

    public final int hashCode() {
        return this.uid;
    }

    public final String toString() {
        return String.format("%d:%s", new Object[]{Integer.valueOf(this.uid), this.packageName});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.uid);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.packageName, false);
        zzd.zzI(parcel, zze);
    }
}
