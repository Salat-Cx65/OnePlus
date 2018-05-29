package com.google.android.gms.internal;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public abstract class zzbhx extends zzbhu implements SafeParcelable {
    public final int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!getClass().isInstance(obj)) {
            return false;
        }
        zzbhu com_google_android_gms_internal_zzbhu = (zzbhu) obj;
        for (zzbhv com_google_android_gms_internal_zzbhv : zzrK().values()) {
            if (zza(com_google_android_gms_internal_zzbhv)) {
                if (!com_google_android_gms_internal_zzbhu.zza(com_google_android_gms_internal_zzbhv)) {
                    return false;
                }
                if (!zzb(com_google_android_gms_internal_zzbhv).equals(com_google_android_gms_internal_zzbhu.zzb(com_google_android_gms_internal_zzbhv))) {
                    return false;
                }
            } else if (com_google_android_gms_internal_zzbhu.zza(com_google_android_gms_internal_zzbhv)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        for (zzbhv com_google_android_gms_internal_zzbhv : zzrK().values()) {
            int hashCode;
            if (zza(com_google_android_gms_internal_zzbhv)) {
                hashCode = zzb(com_google_android_gms_internal_zzbhv).hashCode() + (i * 31);
            } else {
                hashCode = i;
            }
            i = hashCode;
        }
        return i;
    }

    public Object zzcH(String str) {
        return null;
    }

    public boolean zzcI(String str) {
        return false;
    }
}
