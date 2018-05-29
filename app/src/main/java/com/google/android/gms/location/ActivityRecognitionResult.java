package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.safeparcel.zze;
import com.google.android.gms.common.internal.zzbh;
import com.google.android.gms.common.internal.zzbr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class ActivityRecognitionResult extends zza implements ReflectedParcelable {
    public static final Creator<ActivityRecognitionResult> CREATOR;
    private Bundle extras;
    private long zzbhA;
    private int zzbhB;
    private List<DetectedActivity> zzbhy;
    private long zzbhz;

    static {
        CREATOR = new zzb();
    }

    public ActivityRecognitionResult(DetectedActivity detectedActivity, long j, long j2) {
        this(detectedActivity, j, j2, 0, null);
    }

    private ActivityRecognitionResult(DetectedActivity detectedActivity, long j, long j2, int i, Bundle bundle) {
        this(Collections.singletonList(detectedActivity), j, j2, 0, null);
    }

    public ActivityRecognitionResult(List<DetectedActivity> list, long j, long j2) {
        this((List) list, j, j2, 0, null);
    }

    public ActivityRecognitionResult(List<DetectedActivity> list, long j, long j2, int i, Bundle bundle) {
        boolean z = true;
        boolean z2 = list != null && list.size() > 0;
        zzbr.zzb(z2, (Object) "Must have at least 1 detected activity");
        if (j <= 0 || j2 <= 0) {
            z = false;
        }
        zzbr.zzb(z, (Object) "Must set times");
        this.zzbhy = list;
        this.zzbhz = j;
        this.zzbhA = j2;
        this.zzbhB = i;
        this.extras = bundle;
    }

    public static ActivityRecognitionResult extractResult(Intent intent) {
        ActivityRecognitionResult activityRecognitionResult;
        if (hasResult(intent)) {
            Object obj = intent.getExtras().get("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
            if (obj instanceof byte[]) {
                activityRecognitionResult = (ActivityRecognitionResult) zze.zza((byte[]) obj, CREATOR);
            } else if (obj instanceof ActivityRecognitionResult) {
                activityRecognitionResult = (ActivityRecognitionResult) obj;
            }
            if (activityRecognitionResult != null) {
                return activityRecognitionResult;
            }
            List zzj = zzj(intent);
            return (zzj != null || zzj.isEmpty()) ? null : (ActivityRecognitionResult) zzj.get(zzj.size() - 1);
        }
        activityRecognitionResult = null;
        if (activityRecognitionResult != null) {
            return activityRecognitionResult;
        }
        List zzj2 = zzj(intent);
        if (zzj2 != null) {
        }
    }

    public static boolean hasResult(Intent intent) {
        if (intent == null) {
            return false;
        }
        if (intent == null ? false : intent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT")) {
            return true;
        }
        List zzj = zzj(intent);
        return (zzj == null || zzj.isEmpty()) ? false : true;
    }

    private static boolean zzc(Bundle bundle, Bundle bundle2) {
        if (bundle == null && bundle2 == null) {
            return true;
        }
        if ((bundle == null && bundle2 != null) || (bundle != null && bundle2 == null)) {
            return false;
        }
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (String str : bundle.keySet()) {
            if (!bundle2.containsKey(str)) {
                return false;
            }
            if (bundle.get(str) == null) {
                if (bundle2.get(str) != null) {
                    return false;
                }
            } else if (bundle.get(str) instanceof Bundle) {
                if (!zzc(bundle.getBundle(str), bundle2.getBundle(str))) {
                    return false;
                }
            } else if (!bundle.get(str).equals(bundle2.get(str))) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    private static List<ActivityRecognitionResult> zzj(Intent intent) {
        int i = 0;
        if (!(intent == null ? false : intent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT_LIST"))) {
            return null;
        }
        Creator creator = CREATOR;
        ArrayList arrayList = (ArrayList) intent.getSerializableExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT_LIST");
        if (arrayList == null) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        arrayList = arrayList;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            arrayList2.add(zze.zza((byte[]) obj, creator));
        }
        return arrayList2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ActivityRecognitionResult activityRecognitionResult = (ActivityRecognitionResult) obj;
        return this.zzbhz == activityRecognitionResult.zzbhz && this.zzbhA == activityRecognitionResult.zzbhA && this.zzbhB == activityRecognitionResult.zzbhB && zzbh.equal(this.zzbhy, activityRecognitionResult.zzbhy) && zzc(this.extras, activityRecognitionResult.extras);
    }

    public int getActivityConfidence(int i) {
        for (DetectedActivity detectedActivity : this.zzbhy) {
            if (detectedActivity.getType() == i) {
                return detectedActivity.getConfidence();
            }
        }
        return 0;
    }

    public long getElapsedRealtimeMillis() {
        return this.zzbhA;
    }

    public DetectedActivity getMostProbableActivity() {
        return (DetectedActivity) this.zzbhy.get(0);
    }

    public List<DetectedActivity> getProbableActivities() {
        return this.zzbhy;
    }

    public long getTime() {
        return this.zzbhz;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zzbhz), Long.valueOf(this.zzbhA), Integer.valueOf(this.zzbhB), this.zzbhy, this.extras});
    }

    public String toString() {
        String valueOf = String.valueOf(this.zzbhy);
        long j = this.zzbhz;
        return new StringBuilder(String.valueOf(valueOf).length() + 124).append("ActivityRecognitionResult [probableActivities=").append(valueOf).append(", timeMillis=").append(j).append(", elapsedRealtimeMillis=").append(this.zzbhA).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzbhy, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.zzbhz);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzbhA);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.zzbhB);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, this.extras, false);
        zzd.zzI(parcel, zze);
    }
}
