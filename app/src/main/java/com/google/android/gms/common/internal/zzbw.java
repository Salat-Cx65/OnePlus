package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbw extends zza {
    public static final Creator<zzbw> CREATOR;
    private final int zzaIu;
    private final int zzaIv;
    @Deprecated
    private final Scope[] zzaIw;
    private int zzakw;

    static {
        CREATOR = new zzbx();
    }

    zzbw(int i, int i2, int i3, Scope[] scopeArr) {
        this.zzakw = i;
        this.zzaIu = i2;
        this.zzaIv = i3;
        this.zzaIw = scopeArr;
    }

    public zzbw(int i, int i2, Scope[] scopeArr) {
        this(1, i, i2, null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzaIu);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzaIv);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzaIw, i, false);
        zzd.zzI(parcel, zze);
    }
}
