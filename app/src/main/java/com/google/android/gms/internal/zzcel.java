package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.zzaa;

public interface zzcel extends IInterface {
    void zza(long j, boolean z, PendingIntent pendingIntent) throws RemoteException;

    void zza(zzceg com_google_android_gms_internal_zzceg) throws RemoteException;

    void zza(zzcfb com_google_android_gms_internal_zzcfb) throws RemoteException;

    void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzcej com_google_android_gms_internal_zzcej) throws RemoteException;

    void zza(LocationSettingsRequest locationSettingsRequest, zzcen com_google_android_gms_internal_zzcen, String str) throws RemoteException;

    void zza(zzaa com_google_android_gms_location_zzaa, zzcej com_google_android_gms_internal_zzcej) throws RemoteException;

    void zzai(boolean z) throws RemoteException;

    void zzc(PendingIntent pendingIntent) throws RemoteException;

    void zzc(Location location) throws RemoteException;

    Location zzdw(String str) throws RemoteException;

    LocationAvailability zzdx(String str) throws RemoteException;
}
