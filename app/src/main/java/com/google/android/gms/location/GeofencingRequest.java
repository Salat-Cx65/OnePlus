package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzcfd;
import java.util.ArrayList;
import java.util.List;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class GeofencingRequest extends zza {
    public static final Creator<GeofencingRequest> CREATOR;
    public static final int INITIAL_TRIGGER_DWELL = 4;
    public static final int INITIAL_TRIGGER_ENTER = 1;
    public static final int INITIAL_TRIGGER_EXIT = 2;
    private final String mTag;
    private final List<zzcfd> zzbhU;
    private final int zzbhV;

    public static final class Builder {
        private String mTag;
        private final List<zzcfd> zzbhU;
        private int zzbhV;

        public Builder() {
            this.zzbhU = new ArrayList();
            this.zzbhV = 5;
            this.mTag = StringUtils.EMPTY_STRING;
        }

        public final com.google.android.gms.location.GeofencingRequest.Builder addGeofence(Geofence geofence) {
            zzbr.zzb((Object) geofence, (Object) "geofence can't be null.");
            zzbr.zzb(geofence instanceof zzcfd, (Object) "Geofence must be created using Geofence.Builder.");
            this.zzbhU.add((zzcfd) geofence);
            return this;
        }

        public final com.google.android.gms.location.GeofencingRequest.Builder addGeofences(List<Geofence> list) {
            if (!(list == null || list.isEmpty())) {
                for (Geofence geofence : list) {
                    if (geofence != null) {
                        addGeofence(geofence);
                    }
                }
            }
            return this;
        }

        public final GeofencingRequest build() {
            zzbr.zzb(!this.zzbhU.isEmpty(), (Object) "No geofence has been added to this request.");
            return new GeofencingRequest(this.zzbhU, this.zzbhV, this.mTag);
        }

        public final com.google.android.gms.location.GeofencingRequest.Builder setInitialTrigger(int i) {
            this.zzbhV = i & 7;
            return this;
        }
    }

    static {
        CREATOR = new zzi();
    }

    GeofencingRequest(List<zzcfd> list, int i, String str) {
        this.zzbhU = list;
        this.zzbhV = i;
        this.mTag = str;
    }

    public List<Geofence> getGeofences() {
        List<Geofence> arrayList = new ArrayList();
        arrayList.addAll(this.zzbhU);
        return arrayList;
    }

    public int getInitialTrigger() {
        return this.zzbhV;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, INITIAL_TRIGGER_ENTER, this.zzbhU, false);
        zzd.zzc(parcel, INITIAL_TRIGGER_EXIT, getInitialTrigger());
        zzd.zza(parcel, (int) RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.mTag, false);
        zzd.zzI(parcel, zze);
    }
}
