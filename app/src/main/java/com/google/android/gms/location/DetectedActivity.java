package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.Arrays;
import java.util.Comparator;

public class DetectedActivity extends zza {
    public static final Creator<DetectedActivity> CREATOR;
    public static final int IN_VEHICLE = 0;
    public static final int ON_BICYCLE = 1;
    public static final int ON_FOOT = 2;
    public static final int RUNNING = 8;
    public static final int STILL = 3;
    public static final int TILTING = 5;
    public static final int UNKNOWN = 4;
    public static final int WALKING = 7;
    private static Comparator<DetectedActivity> zzbhC;
    private static int[] zzbhD;
    private static int[] zzbhE;
    private int zzbhF;
    private int zzbhG;

    static {
        zzbhC = new zzc();
        zzbhD = new int[]{9, 10};
        zzbhE = new int[]{0, 1, 2, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 16, 17};
        CREATOR = new zzd();
    }

    public DetectedActivity(int i, int i2) {
        this.zzbhF = i;
        this.zzbhG = i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DetectedActivity detectedActivity = (DetectedActivity) obj;
        return this.zzbhF == detectedActivity.zzbhF && this.zzbhG == detectedActivity.zzbhG;
    }

    public int getConfidence() {
        return this.zzbhG;
    }

    public int getType() {
        int i = this.zzbhF;
        return i > 17 ? UNKNOWN : i;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzbhF), Integer.valueOf(this.zzbhG)});
    }

    public String toString() {
        Object obj;
        int type = getType();
        switch (type) {
            case IN_VEHICLE:
                obj = "IN_VEHICLE";
                break;
            case ON_BICYCLE:
                obj = "ON_BICYCLE";
                break;
            case ON_FOOT:
                obj = "ON_FOOT";
                break;
            case STILL:
                obj = "STILL";
                break;
            case UNKNOWN:
                obj = "UNKNOWN";
                break;
            case TILTING:
                obj = "TILTING";
                break;
            case WALKING:
                obj = "WALKING";
                break;
            case RUNNING:
                obj = "RUNNING";
                break;
            case ConnectionResult.API_UNAVAILABLE:
                obj = "IN_ROAD_VEHICLE";
                break;
            case ConnectionResult.SIGN_IN_FAILED:
                obj = "IN_RAIL_VEHICLE";
                break;
            default:
                obj = Integer.toString(type);
                break;
        }
        String valueOf = String.valueOf(obj);
        return new StringBuilder(String.valueOf(valueOf).length() + 48).append("DetectedActivity [type=").append(valueOf).append(", confidence=").append(this.zzbhG).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, ON_BICYCLE, this.zzbhF);
        zzd.zzc(parcel, ON_FOOT, this.zzbhG);
        zzd.zzI(parcel, zze);
    }
}
