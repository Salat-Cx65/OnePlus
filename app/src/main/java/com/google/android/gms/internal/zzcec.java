package com.google.android.gms.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.GeofencingRequest.Builder;
import com.google.android.gms.location.zzaa;
import java.util.List;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcec implements GeofencingApi {
    private final PendingResult<Status> zza(GoogleApiClient googleApiClient, zzaa com_google_android_gms_location_zzaa) {
        return googleApiClient.zze(new zzcee(this, googleApiClient, com_google_android_gms_location_zzaa));
    }

    public final PendingResult<Status> addGeofences(GoogleApiClient googleApiClient, GeofencingRequest geofencingRequest, PendingIntent pendingIntent) {
        return googleApiClient.zze(new zzced(this, googleApiClient, geofencingRequest, pendingIntent));
    }

    @Deprecated
    public final PendingResult<Status> addGeofences(GoogleApiClient googleApiClient, List<Geofence> list, PendingIntent pendingIntent) {
        Builder builder = new Builder();
        builder.addGeofences(list);
        builder.setInitialTrigger(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER);
        return addGeofences(googleApiClient, builder.build(), pendingIntent);
    }

    public final PendingResult<Status> removeGeofences(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return zza(googleApiClient, zzaa.zzb(pendingIntent));
    }

    public final PendingResult<Status> removeGeofences(GoogleApiClient googleApiClient, List<String> list) {
        return zza(googleApiClient, zzaa.zzB(list));
    }
}
