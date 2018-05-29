package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.os.EnvironmentCompat;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbh;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.common.internal.zzbr;
import java.util.Arrays;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class PlaceReport extends zza implements ReflectedParcelable {
    public static final Creator<PlaceReport> CREATOR;
    private final String mTag;
    private final String zzaeM;
    private int zzakw;
    private final String zzbjM;

    static {
        CREATOR = new zzl();
    }

    PlaceReport(int i, String str, String str2, String str3) {
        this.zzakw = i;
        this.zzbjM = str;
        this.mTag = str2;
        this.zzaeM = str3;
    }

    public static PlaceReport create(String str, String str2) {
        boolean z = false;
        String str3 = EnvironmentCompat.MEDIA_UNKNOWN;
        zzbr.zzu(str);
        zzbr.zzcF(str2);
        zzbr.zzcF(str3);
        Object obj = -1;
        switch (str3.hashCode()) {
            case -1436706272:
                if (str3.equals("inferredGeofencing")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case -1194968642:
                if (str3.equals("userReported")) {
                    int i = 1;
                }
                break;
            case -284840886:
                if (str3.equals(EnvironmentCompat.MEDIA_UNKNOWN)) {
                    boolean z2 = false;
                }
                break;
            case -262743844:
                if (str3.equals("inferredReverseGeocoding")) {
                    obj = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                }
                break;
            case 1164924125:
                if (str3.equals("inferredSnappedToRoad")) {
                    obj = RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
                }
                break;
            case 1287171955:
                if (str3.equals("inferredRadioSignals")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
        }
        switch (z2) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                z = true;
                break;
        }
        zzbr.zzb(z, (Object) "Invalid source");
        return new PlaceReport(1, str, str2, str3);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PlaceReport)) {
            return false;
        }
        PlaceReport placeReport = (PlaceReport) obj;
        return zzbh.equal(this.zzbjM, placeReport.zzbjM) && zzbh.equal(this.mTag, placeReport.mTag) && zzbh.equal(this.zzaeM, placeReport.zzaeM);
    }

    public String getPlaceId() {
        return this.zzbjM;
    }

    public String getTag() {
        return this.mTag;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbjM, this.mTag, this.zzaeM});
    }

    public String toString() {
        zzbj zzt = zzbh.zzt(this);
        zzt.zzg("placeId", this.zzbjM);
        zzt.zzg("tag", this.mTag);
        if (!EnvironmentCompat.MEDIA_UNKNOWN.equals(this.zzaeM)) {
            zzt.zzg("source", this.zzaeM);
        }
        return zzt.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, getPlaceId(), false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, getTag(), false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzaeM, false);
        zzd.zzI(parcel, zze);
    }
}
