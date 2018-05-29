package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import com.google.android.gms.internal.zzcfd;
import java.util.ArrayList;
import java.util.List;

public class GeofencingEvent {
    private final int mErrorCode;
    private final int zzbhR;
    private final List<Geofence> zzbhS;
    private final Location zzbhT;

    private GeofencingEvent(int i, int i2, List<Geofence> list, Location location) {
        this.mErrorCode = i;
        this.zzbhR = i2;
        this.zzbhS = list;
        this.zzbhT = location;
    }

    public static GeofencingEvent fromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        List list;
        int intExtra = intent.getIntExtra("gms_error_code", -1);
        int intExtra2 = intent.getIntExtra("com.google.android.location.intent.extra.transition", -1);
        int i = (intExtra2 == -1 || !(intExtra2 == 1 || intExtra2 == 2 || intExtra2 == 4)) ? -1 : intExtra2;
        ArrayList arrayList = (ArrayList) intent.getSerializableExtra("com.google.android.location.intent.extra.geofence_list");
        if (arrayList == null) {
            list = null;
        } else {
            ArrayList arrayList2 = new ArrayList(arrayList.size());
            arrayList = arrayList;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                arrayList2.add(zzcfd.zzl((byte[]) obj));
            }
            ArrayList arrayList3 = arrayList2;
        }
        return new GeofencingEvent(intExtra, i, list, (Location) intent.getParcelableExtra("com.google.android.location.intent.extra.triggering_location"));
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public int getGeofenceTransition() {
        return this.zzbhR;
    }

    public List<Geofence> getTriggeringGeofences() {
        return this.zzbhS;
    }

    public Location getTriggeringLocation() {
        return this.zzbhT;
    }

    public boolean hasError() {
        return this.mErrorCode != -1;
    }
}
