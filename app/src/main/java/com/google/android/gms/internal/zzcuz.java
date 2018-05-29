package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcuz extends zza implements Result {
    public static final Creator<zzcuz> CREATOR;
    private int zzakw;
    private int zzbCV;
    private Intent zzbCW;

    static {
        CREATOR = new zzcva();
    }

    public zzcuz() {
        this(0, null);
    }

    zzcuz(int i, int i2, Intent intent) {
        this.zzakw = i;
        this.zzbCV = i2;
        this.zzbCW = intent;
    }

    private zzcuz(int i, Intent intent) {
        this(2, 0, null);
    }

    public final Status getStatus() {
        return this.zzbCV == 0 ? Status.zzaBo : Status.zzaBs;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzbCV);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzbCW, i, false);
        zzd.zzI(parcel, zze);
    }
}
