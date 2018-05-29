package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbia extends zza {
    public static final Creator<zzbia> CREATOR;
    private final HashMap<String, Map<String, zzbhv<?, ?>>> zzaIT;
    private final ArrayList<zzbib> zzaIU;
    private final String zzaIV;
    private int zzakw;

    static {
        CREATOR = new zzbid();
    }

    zzbia(int i, ArrayList<zzbib> arrayList, String str) {
        this.zzakw = i;
        this.zzaIU = null;
        HashMap hashMap = new HashMap();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            zzbib com_google_android_gms_internal_zzbib = (zzbib) arrayList.get(i2);
            hashMap.put(com_google_android_gms_internal_zzbib.className, com_google_android_gms_internal_zzbib.zzrR());
        }
        this.zzaIT = hashMap;
        this.zzaIV = (String) zzbr.zzu(str);
        zzrP();
    }

    private final void zzrP() {
        for (String str : this.zzaIT.keySet()) {
            Map map = (Map) this.zzaIT.get(str);
            for (String str2 : map.keySet()) {
                ((zzbhv) map.get(str2)).zza(this);
            }
        }
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : this.zzaIT.keySet()) {
            stringBuilder.append(str).append(":\n");
            Map map = (Map) this.zzaIT.get(str);
            for (String str2 : map.keySet()) {
                stringBuilder.append("  ").append(str2).append(": ");
                stringBuilder.append(map.get(str2));
            }
        }
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        List arrayList = new ArrayList();
        for (String str : this.zzaIT.keySet()) {
            arrayList.add(new zzbib(str, (Map) this.zzaIT.get(str)));
        }
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_SHOWER, arrayList, false);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzaIV, false);
        zzd.zzI(parcel, zze);
    }

    public final Map<String, zzbhv<?, ?>> zzcJ(String str) {
        return (Map) this.zzaIT.get(str);
    }

    public final String zzrQ() {
        return this.zzaIV;
    }
}
