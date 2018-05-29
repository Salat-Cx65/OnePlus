package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zze;
import com.google.android.gms.location.DetectedActivity;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzy extends zza {
    public static final Creator<zzy> CREATOR;
    private int version;
    Scope[] zzaHA;
    Bundle zzaHB;
    Account zzaHC;
    zzc[] zzaHD;
    private int zzaHw;
    private int zzaHx;
    String zzaHy;
    IBinder zzaHz;

    static {
        CREATOR = new zzz();
    }

    public zzy(int i) {
        this.version = 3;
        this.zzaHx = zze.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.zzaHw = i;
    }

    zzy(int i, int i2, int i3, String str, IBinder iBinder, Scope[] scopeArr, Bundle bundle, Account account, zzc[] com_google_android_gms_common_zzcArr) {
        zzam com_google_android_gms_common_internal_zzam = null;
        this.version = i;
        this.zzaHw = i2;
        this.zzaHx = i3;
        if (GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE.equals(str)) {
            this.zzaHy = GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE;
        } else {
            this.zzaHy = str;
        }
        if (i < 2) {
            Account zza;
            if (iBinder != null) {
                if (iBinder != null) {
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
                    com_google_android_gms_common_internal_zzam = queryLocalInterface instanceof zzam ? (zzam) queryLocalInterface : new zzao(iBinder);
                }
                zza = zza.zza(com_google_android_gms_common_internal_zzam);
            }
            this.zzaHC = zza;
        } else {
            this.zzaHz = iBinder;
            this.zzaHC = account;
        }
        this.zzaHA = scopeArr;
        this.zzaHB = bundle;
        this.zzaHD = com_google_android_gms_common_zzcArr;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.version);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzaHw);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzaHx);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzaHy, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.zzaHz, false);
        zzd.zza(parcel, (int) ConnectionResult.RESOLUTION_REQUIRED, this.zzaHA, i, false);
        zzd.zza(parcel, (int) DetectedActivity.WALKING, this.zzaHB, false);
        zzd.zza(parcel, (int) DetectedActivity.RUNNING, this.zzaHC, i, false);
        zzd.zza(parcel, (int) ConnectionResult.DEVELOPER_ERROR, this.zzaHD, i, false);
        zzd.zzI(parcel, zze);
    }

    public final Bundle zzrw() {
        return this.zzaHB;
    }
}
