package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbhq extends zza implements zzbhw<String, Integer> {
    public static final Creator<zzbhq> CREATOR;
    private final HashMap<String, Integer> zzaIE;
    private final SparseArray<String> zzaIF;
    private final ArrayList<zzbhr> zzaIG;
    private int zzakw;

    static {
        CREATOR = new zzbhs();
    }

    public zzbhq() {
        this.zzakw = 1;
        this.zzaIE = new HashMap();
        this.zzaIF = new SparseArray();
        this.zzaIG = null;
    }

    zzbhq(int i, ArrayList<zzbhr> arrayList) {
        this.zzakw = i;
        this.zzaIE = new HashMap();
        this.zzaIF = new SparseArray();
        this.zzaIG = null;
        zzd(arrayList);
    }

    private final void zzd(ArrayList<zzbhr> arrayList) {
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            zzbhr com_google_android_gms_internal_zzbhr = (zzbhr) obj;
            zzi(com_google_android_gms_internal_zzbhr.zzaIH, com_google_android_gms_internal_zzbhr.zzaII);
        }
    }

    public final /* synthetic */ Object convertBack(Object obj) {
        String str = (String) this.zzaIF.get(((Integer) obj).intValue());
        return (str == null && this.zzaIE.containsKey("gms_unknown")) ? "gms_unknown" : str;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzakw);
        List arrayList = new ArrayList();
        for (String str : this.zzaIE.keySet()) {
            arrayList.add(new zzbhr(str, ((Integer) this.zzaIE.get(str)).intValue()));
        }
        zzd.zzc(parcel, RainSurfaceView.RAIN_LEVEL_SHOWER, arrayList, false);
        zzd.zzI(parcel, zze);
    }

    public final zzbhq zzi(String str, int i) {
        this.zzaIE.put(str, Integer.valueOf(i));
        this.zzaIF.put(i, str);
        return this;
    }
}
