package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.location.DetectedActivity;
import java.util.List;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class WakeLockEvent extends StatsEvent {
    public static final Creator<WakeLockEvent> CREATOR;
    private final long mTimeout;
    private final float zzaJA;
    private long zzaJB;
    private final long zzaJp;
    private int zzaJq;
    private final String zzaJr;
    private final String zzaJs;
    private final String zzaJt;
    private final int zzaJu;
    private final List<String> zzaJv;
    private final String zzaJw;
    private final long zzaJx;
    private int zzaJy;
    private final String zzaJz;
    private int zzakw;

    static {
        CREATOR = new zzd();
    }

    WakeLockEvent(int i, long j, int i2, String str, int i3, List<String> list, String str2, long j2, int i4, String str3, String str4, float f, long j3, String str5) {
        this.zzakw = i;
        this.zzaJp = j;
        this.zzaJq = i2;
        this.zzaJr = str;
        this.zzaJs = str3;
        this.zzaJt = str5;
        this.zzaJu = i3;
        this.zzaJB = -1;
        this.zzaJv = list;
        this.zzaJw = str2;
        this.zzaJx = j2;
        this.zzaJy = i4;
        this.zzaJz = str4;
        this.zzaJA = f;
        this.mTimeout = j3;
    }

    public WakeLockEvent(long j, int i, String str, int i2, List<String> list, String str2, long j2, int i3, String str3, String str4, float f, long j3, String str5) {
        this(2, j, i, str, i2, list, str2, j2, i3, str3, str4, f, j3, str5);
    }

    public final int getEventType() {
        return this.zzaJq;
    }

    public final long getTimeMillis() {
        return this.zzaJp;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzaJp);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzaJr, false);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.zzaJu);
        zzd.zzb(parcel, ConnectionResult.RESOLUTION_REQUIRED, this.zzaJv, false);
        zzd.zza(parcel, (int) DetectedActivity.RUNNING, this.zzaJx);
        zzd.zza(parcel, (int) ConnectionResult.DEVELOPER_ERROR, this.zzaJs, false);
        zzd.zzc(parcel, ConnectionResult.LICENSE_CHECK_FAILED, this.zzaJq);
        zzd.zza(parcel, (int) WeatherCircleView.ARC_DIN, this.zzaJw, false);
        zzd.zza(parcel, (int) ConnectionResult.CANCELED, this.zzaJz, false);
        zzd.zzc(parcel, ConnectionResult.TIMEOUT, this.zzaJy);
        zzd.zza(parcel, (int) ConnectionResult.INTERRUPTED, this.zzaJA);
        zzd.zza(parcel, (int) ConnectionResult.API_UNAVAILABLE, this.mTimeout);
        zzd.zza(parcel, (int) ConnectionResult.SIGN_IN_FAILED, this.zzaJt, false);
        zzd.zzI(parcel, zze);
    }

    public final long zzrU() {
        return this.zzaJB;
    }

    public final String zzrV() {
        String valueOf = String.valueOf("\t");
        String valueOf2 = String.valueOf(this.zzaJr);
        String valueOf3 = String.valueOf("\t");
        int i = this.zzaJu;
        String valueOf4 = String.valueOf("\t");
        String join = this.zzaJv == null ? StringUtils.EMPTY_STRING : TextUtils.join(",", this.zzaJv);
        String valueOf5 = String.valueOf("\t");
        int i2 = this.zzaJy;
        String valueOf6 = String.valueOf("\t");
        String str = this.zzaJs == null ? StringUtils.EMPTY_STRING : this.zzaJs;
        String valueOf7 = String.valueOf("\t");
        String str2 = this.zzaJz == null ? StringUtils.EMPTY_STRING : this.zzaJz;
        String valueOf8 = String.valueOf("\t");
        float f = this.zzaJA;
        String valueOf9 = String.valueOf("\t");
        String str3 = this.zzaJt == null ? StringUtils.EMPTY_STRING : this.zzaJt;
        return new StringBuilder(((((((((((((String.valueOf(valueOf).length() + 37) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length()) + String.valueOf(valueOf4).length()) + String.valueOf(join).length()) + String.valueOf(valueOf5).length()) + String.valueOf(valueOf6).length()) + String.valueOf(str).length()) + String.valueOf(valueOf7).length()) + String.valueOf(str2).length()) + String.valueOf(valueOf8).length()) + String.valueOf(valueOf9).length()) + String.valueOf(str3).length()).append(valueOf).append(valueOf2).append(valueOf3).append(i).append(valueOf4).append(join).append(valueOf5).append(i2).append(valueOf6).append(str).append(valueOf7).append(str2).append(valueOf8).append(f).append(valueOf9).append(str3).toString();
    }
}
