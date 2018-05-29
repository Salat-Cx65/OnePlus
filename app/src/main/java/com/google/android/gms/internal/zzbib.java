package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbib extends zza {
    public static final Creator<zzbib> CREATOR;
    final String className;
    private int versionCode;
    private ArrayList<zzbic> zzaIW;

    static {
        CREATOR = new zzbie();
    }

    zzbib(int i, String str, ArrayList<zzbic> arrayList) {
        this.versionCode = i;
        this.className = str;
        this.zzaIW = arrayList;
    }

    zzbib(String str, Map<String, zzbhv<?, ?>> map) {
        ArrayList arrayList;
        this.versionCode = 1;
        this.className = str;
        if (map == null) {
            arrayList = null;
        } else {
            ArrayList arrayList2 = new ArrayList();
            for (String str2 : map.keySet()) {
                arrayList2.add(new zzbic(str2, (zzbhv) map.get(str2)));
            }
            arrayList = arrayList2;
        }
        this.zzaIW = arrayList;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_SHOWER, this.className, false);
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzaIW, false);
        zzd.zzI(parcel, zze);
    }

    final HashMap<String, zzbhv<?, ?>> zzrR() {
        HashMap<String, zzbhv<?, ?>> hashMap = new HashMap();
        int size = this.zzaIW.size();
        for (int i = 0; i < size; i++) {
            zzbic com_google_android_gms_internal_zzbic = (zzbic) this.zzaIW.get(i);
            hashMap.put(com_google_android_gms_internal_zzbic.key, com_google_android_gms_internal_zzbic.zzaIX);
        }
        return hashMap;
    }
}
