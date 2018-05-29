package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbs extends zza {
    public static final Creator<zzbs> CREATOR;
    private final int zzaIq;
    private final GoogleSignInAccount zzaIr;
    private final Account zzajd;
    private int zzakw;

    static {
        CREATOR = new zzbt();
    }

    zzbs(int i, Account account, int i2, GoogleSignInAccount googleSignInAccount) {
        this.zzakw = i;
        this.zzajd = account;
        this.zzaIq = i2;
        this.zzaIr = googleSignInAccount;
    }

    public zzbs(Account account, int i, GoogleSignInAccount googleSignInAccount) {
        this(2, account, i, googleSignInAccount);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzajd, i, false);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzaIq);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzaIr, i, false);
        zzd.zzI(parcel, zze);
    }
}
