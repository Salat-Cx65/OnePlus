package com.google.android.gms.internal;

public final class zzbfk<L> {
    private final L mListener;
    private final String zzaER;

    zzbfk(L l, String str) {
        this.mListener = l;
        this.zzaER = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbfk)) {
            return false;
        }
        zzbfk com_google_android_gms_internal_zzbfk = (zzbfk) obj;
        return this.mListener == com_google_android_gms_internal_zzbfk.mListener && this.zzaER.equals(com_google_android_gms_internal_zzbfk.zzaER);
    }

    public final int hashCode() {
        return (System.identityHashCode(this.mListener) * 31) + this.zzaER.hashCode();
    }
}
