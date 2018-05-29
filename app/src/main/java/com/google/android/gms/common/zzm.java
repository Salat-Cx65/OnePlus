package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzat;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzm extends zza {
    public static final Creator<zzm> CREATOR;
    private final String zzaAn;
    private final zzg zzaAo;
    private final boolean zzaAp;

    static {
        CREATOR = new zzn();
    }

    zzm(String str, IBinder iBinder, boolean z) {
        this.zzaAn = str;
        this.zzaAo = zzG(iBinder);
        this.zzaAp = z;
    }

    zzm(String str, zzg com_google_android_gms_common_zzg, boolean z) {
        this.zzaAn = str;
        this.zzaAo = com_google_android_gms_common_zzg;
        this.zzaAp = z;
    }

    private static zzg zzG(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        try {
            IObjectWrapper zzoW = zzat.zzI(iBinder).zzoW();
            byte[] bArr = zzoW == null ? null : (byte[]) zzn.zzE(zzoW);
            zzh com_google_android_gms_common_zzh;
            if (bArr != null) {
                com_google_android_gms_common_zzh = new zzh(bArr);
            } else {
                Log.e("GoogleCertificatesQuery", "Could not unwrap certificate");
                com_google_android_gms_common_zzh = null;
            }
            return r0;
        } catch (Throwable e) {
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate", e);
            return null;
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder;
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzaAn, false);
        if (this.zzaAo == null) {
            Log.w("GoogleCertificatesQuery", "certificate binder is null");
            iBinder = null;
        } else {
            iBinder = this.zzaAo.asBinder();
        }
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, iBinder, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzaAp);
        zzd.zzI(parcel, zze);
    }
}
