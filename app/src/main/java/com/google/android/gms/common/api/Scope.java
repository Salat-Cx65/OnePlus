package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbr;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class Scope extends zza implements ReflectedParcelable {
    public static final Creator<Scope> CREATOR;
    private final String zzaBn;
    private int zzakw;

    static {
        CREATOR = new zze();
    }

    Scope(int i, String str) {
        zzbr.zzh(str, "scopeUri must not be null or empty");
        this.zzakw = i;
        this.zzaBn = str;
    }

    public Scope(String str) {
        this(1, str);
    }

    public final boolean equals(Object obj) {
        return this == obj ? true : !(obj instanceof Scope) ? false : this.zzaBn.equals(((Scope) obj).zzaBn);
    }

    public final int hashCode() {
        return this.zzaBn.hashCode();
    }

    public final String toString() {
        return this.zzaBn;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzaBn, false);
        zzd.zzI(parcel, zze);
    }

    public final String zzpn() {
        return this.zzaBn;
    }
}
