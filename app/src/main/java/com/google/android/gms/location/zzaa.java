package com.google.android.gms.location;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbr;
import java.util.Collections;
import java.util.List;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzaa extends zza {
    public static final Creator<zzaa> CREATOR;
    private final PendingIntent mPendingIntent;
    private final String mTag;
    private final List<String> zzbiB;

    static {
        CREATOR = new zzab();
    }

    zzaa(@Nullable List<String> list, @Nullable PendingIntent pendingIntent, String str) {
        this.zzbiB = list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
        this.mPendingIntent = pendingIntent;
        this.mTag = str;
    }

    public static zzaa zzB(List<String> list) {
        zzbr.zzb((Object) list, (Object) "geofence can't be null.");
        zzbr.zzb(!list.isEmpty(), (Object) "Geofences must contains at least one id.");
        return new zzaa(list, null, StringUtils.EMPTY_STRING);
    }

    public static zzaa zzb(PendingIntent pendingIntent) {
        zzbr.zzb((Object) pendingIntent, (Object) "PendingIntent can not be null.");
        return new zzaa(null, pendingIntent, StringUtils.EMPTY_STRING);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzb(parcel, 1, this.zzbiB, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.mPendingIntent, i, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.mTag, false);
        zzd.zzI(parcel, zze);
    }
}
