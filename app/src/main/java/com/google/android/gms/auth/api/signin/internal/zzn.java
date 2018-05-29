package com.google.android.gms.auth.api.signin.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzn extends zza {
    public static final Creator<zzn> CREATOR;
    private Bundle mBundle;
    private int versionCode;
    private int zzamt;

    static {
        CREATOR = new zzm();
    }

    zzn(int i, int i2, Bundle bundle) {
        this.versionCode = i;
        this.zzamt = i2;
        this.mBundle = bundle;
    }

    public zzn(GoogleSignInOptionsExtension googleSignInOptionsExtension) {
        this(1, 1, googleSignInOptionsExtension.toBundle());
    }

    public final int getType() {
        return this.zzamt;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzamt);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.mBundle, false);
        zzd.zzI(parcel, zze);
    }
}
