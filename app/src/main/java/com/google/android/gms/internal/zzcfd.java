package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.Geofence;
import java.util.Locale;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcfd extends zza implements Geofence {
    public static final Creator<zzcfd> CREATOR;
    private final String zzQz;
    private final int zzbhJ;
    private final short zzbhL;
    private final double zzbhM;
    private final double zzbhN;
    private final float zzbhO;
    private final int zzbhP;
    private final int zzbhQ;
    private final long zzbjj;

    static {
        CREATOR = new zzcfe();
    }

    public zzcfd(String str, int i, short s, double d, double d2, float f, long j, int i2, int i3) {
        if (str == null || str.length() > 100) {
            String str2 = "requestId is null or too long: ";
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        } else if (f <= 0.0f) {
            throw new IllegalArgumentException(new StringBuilder(31).append("invalid radius: ").append(f).toString());
        } else if (d > 90.0d || d < -90.0d) {
            throw new IllegalArgumentException(new StringBuilder(42).append("invalid latitude: ").append(d).toString());
        } else if (d2 > 180.0d || d2 < -180.0d) {
            throw new IllegalArgumentException(new StringBuilder(43).append("invalid longitude: ").append(d2).toString());
        } else {
            int i4 = i & 7;
            if (i4 == 0) {
                throw new IllegalArgumentException(new StringBuilder(46).append("No supported transition specified: ").append(i).toString());
            }
            this.zzbhL = s;
            this.zzQz = str;
            this.zzbhM = d;
            this.zzbhN = d2;
            this.zzbhO = f;
            this.zzbjj = j;
            this.zzbhJ = i4;
            this.zzbhP = i2;
            this.zzbhQ = i3;
        }
    }

    public static zzcfd zzl(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        zzcfd com_google_android_gms_internal_zzcfd = (zzcfd) CREATOR.createFromParcel(obtain);
        obtain.recycle();
        return com_google_android_gms_internal_zzcfd;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof zzcfd)) {
            return false;
        }
        zzcfd com_google_android_gms_internal_zzcfd = (zzcfd) obj;
        return this.zzbhO != com_google_android_gms_internal_zzcfd.zzbhO ? false : this.zzbhM != com_google_android_gms_internal_zzcfd.zzbhM ? false : this.zzbhN != com_google_android_gms_internal_zzcfd.zzbhN ? false : this.zzbhL == com_google_android_gms_internal_zzcfd.zzbhL;
    }

    public final String getRequestId() {
        return this.zzQz;
    }

    public final int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.zzbhM);
        int i = ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) + 31;
        long doubleToLongBits2 = Double.doubleToLongBits(this.zzbhN);
        return (((((((i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)))) * 31) + Float.floatToIntBits(this.zzbhO)) * 31) + this.zzbhL) * 31) + this.zzbhJ;
    }

    public final String toString() {
        String str;
        Locale locale = Locale.US;
        String str2 = "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]";
        Object[] objArr = new Object[9];
        switch (this.zzbhL) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                str = "CIRCLE";
                break;
            default:
                str = null;
                break;
        }
        objArr[0] = str;
        objArr[1] = this.zzQz;
        objArr[2] = Integer.valueOf(this.zzbhJ);
        objArr[3] = Double.valueOf(this.zzbhM);
        objArr[4] = Double.valueOf(this.zzbhN);
        objArr[5] = Float.valueOf(this.zzbhO);
        objArr[6] = Integer.valueOf(this.zzbhP / 1000);
        objArr[7] = Integer.valueOf(this.zzbhQ);
        objArr[8] = Long.valueOf(this.zzbjj);
        return String.format(locale, str2, objArr);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, getRequestId(), false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzbjj);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzbhL);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzbhM);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.zzbhN);
        zzd.zza(parcel, (int) ConnectionResult.RESOLUTION_REQUIRED, this.zzbhO);
        zzd.zzc(parcel, DetectedActivity.WALKING, this.zzbhJ);
        zzd.zzc(parcel, DetectedActivity.RUNNING, this.zzbhP);
        zzd.zzc(parcel, ConnectionResult.SERVICE_INVALID, this.zzbhQ);
        zzd.zzI(parcel, zze);
    }
}
