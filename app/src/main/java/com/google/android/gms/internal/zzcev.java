package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.zzaa;

public final class zzcev extends zzcdj {
    private final zzcep zzbiW;

    public zzcev(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str) {
        this(context, looper, connectionCallbacks, onConnectionFailedListener, str, zzq.zzaA(context));
    }

    public zzcev(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str, zzq com_google_android_gms_common_internal_zzq) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, str, com_google_android_gms_common_internal_zzq);
        this.zzbiW = new zzcep(context, this.zzbiF);
    }

    public final void disconnect() {
        synchronized (this.zzbiW) {
            if (isConnected()) {
                try {
                    this.zzbiW.removeAllListeners();
                    this.zzbiW.zzvQ();
                } catch (Throwable e) {
                    Log.e("LocationClientImpl", "Client disconnected before listeners could be cleaned up", e);
                }
            }
            super.disconnect();
        }
    }

    public final Location getLastLocation() {
        return this.zzbiW.getLastLocation();
    }

    public final void zza(long j, PendingIntent pendingIntent) throws RemoteException {
        zzrc();
        zzbr.zzu(pendingIntent);
        zzbr.zzb(j >= 0, (Object) "detectionIntervalMillis must be >= 0");
        ((zzcel) zzrd()).zza(j, true, pendingIntent);
    }

    public final void zza(PendingIntent pendingIntent, zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        this.zzbiW.zza(pendingIntent, com_google_android_gms_internal_zzceg);
    }

    public final void zza(zzbfk<LocationListener> com_google_android_gms_internal_zzbfk_com_google_android_gms_location_LocationListener, zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        this.zzbiW.zza((zzbfk) com_google_android_gms_internal_zzbfk_com_google_android_gms_location_LocationListener, com_google_android_gms_internal_zzceg);
    }

    public final void zza(zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        this.zzbiW.zza(com_google_android_gms_internal_zzceg);
    }

    public final void zza(zzcez com_google_android_gms_internal_zzcez, zzbfi<LocationCallback> com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationCallback, zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        synchronized (this.zzbiW) {
            this.zzbiW.zza(com_google_android_gms_internal_zzcez, (zzbfi) com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationCallback, com_google_android_gms_internal_zzceg);
        }
    }

    public final void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzbcl<Status> com_google_android_gms_internal_zzbcl_com_google_android_gms_common_api_Status) throws RemoteException {
        zzrc();
        zzbr.zzb((Object) geofencingRequest, (Object) "geofencingRequest can't be null.");
        zzbr.zzb((Object) pendingIntent, (Object) "PendingIntent must be specified.");
        zzbr.zzb((Object) com_google_android_gms_internal_zzbcl_com_google_android_gms_common_api_Status, (Object) "ResultHolder not provided.");
        ((zzcel) zzrd()).zza(geofencingRequest, pendingIntent, new zzcew(com_google_android_gms_internal_zzbcl_com_google_android_gms_common_api_Status));
    }

    public final void zza(LocationRequest locationRequest, PendingIntent pendingIntent, zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        this.zzbiW.zza(locationRequest, pendingIntent, com_google_android_gms_internal_zzceg);
    }

    public final void zza(LocationRequest locationRequest, zzbfi<LocationListener> com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationListener, zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        synchronized (this.zzbiW) {
            this.zzbiW.zza(locationRequest, (zzbfi) com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationListener, com_google_android_gms_internal_zzceg);
        }
    }

    public final void zza(LocationSettingsRequest locationSettingsRequest, zzbcl<LocationSettingsResult> com_google_android_gms_internal_zzbcl_com_google_android_gms_location_LocationSettingsResult, String str) throws RemoteException {
        boolean z = true;
        zzrc();
        zzbr.zzb(locationSettingsRequest != null, (Object) "locationSettingsRequest can't be null nor empty.");
        if (com_google_android_gms_internal_zzbcl_com_google_android_gms_location_LocationSettingsResult == null) {
            z = false;
        }
        zzbr.zzb(z, (Object) "listener can't be null.");
        ((zzcel) zzrd()).zza(locationSettingsRequest, new zzcey(com_google_android_gms_internal_zzbcl_com_google_android_gms_location_LocationSettingsResult), str);
    }

    public final void zza(zzaa com_google_android_gms_location_zzaa, zzbcl<Status> com_google_android_gms_internal_zzbcl_com_google_android_gms_common_api_Status) throws RemoteException {
        zzrc();
        zzbr.zzb((Object) com_google_android_gms_location_zzaa, (Object) "removeGeofencingRequest can't be null.");
        zzbr.zzb((Object) com_google_android_gms_internal_zzbcl_com_google_android_gms_common_api_Status, (Object) "ResultHolder not provided.");
        ((zzcel) zzrd()).zza(com_google_android_gms_location_zzaa, new zzcex(com_google_android_gms_internal_zzbcl_com_google_android_gms_common_api_Status));
    }

    public final void zzai(boolean z) throws RemoteException {
        this.zzbiW.zzai(z);
    }

    public final void zzb(zzbfk<LocationCallback> com_google_android_gms_internal_zzbfk_com_google_android_gms_location_LocationCallback, zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        this.zzbiW.zzb(com_google_android_gms_internal_zzbfk_com_google_android_gms_location_LocationCallback, com_google_android_gms_internal_zzceg);
    }

    public final void zzc(PendingIntent pendingIntent) throws RemoteException {
        zzrc();
        zzbr.zzu(pendingIntent);
        ((zzcel) zzrd()).zzc(pendingIntent);
    }

    public final void zzc(Location location) throws RemoteException {
        this.zzbiW.zzc(location);
    }

    public final LocationAvailability zzvP() {
        return this.zzbiW.zzvP();
    }
}
