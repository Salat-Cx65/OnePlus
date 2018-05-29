package com.google.android.gms.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.zzbk;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbce;
import com.google.android.gms.internal.zzbfi;
import com.google.android.gms.internal.zzbfk;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfq;
import com.google.android.gms.internal.zzbgd;
import com.google.android.gms.internal.zzbgk;
import com.google.android.gms.internal.zzcea;
import com.google.android.gms.internal.zzceh;
import com.google.android.gms.internal.zzcez;
import com.google.android.gms.internal.zzcfn;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public class FusedLocationProviderClient extends GoogleApi<NoOptions> {

    static class zza extends zzceh {
        private final TaskCompletionSource<Void> zzalG;

        public zza(TaskCompletionSource<Void> taskCompletionSource) {
            this.zzalG = taskCompletionSource;
        }

        public final void zza(zzcea com_google_android_gms_internal_zzcea) {
            zzbgd.zza(com_google_android_gms_internal_zzcea.getStatus(), null, this.zzalG);
        }
    }

    public FusedLocationProviderClient(@NonNull Activity activity) {
        super(activity, LocationServices.API, null, new zzbce());
    }

    public FusedLocationProviderClient(@NonNull Context context) {
        super(context, LocationServices.API, null, new zzbce());
    }

    public Task<Void> flushLocations() {
        return zzbk.zzb(LocationServices.FusedLocationApi.flushLocations(zzpg()));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public Task<Location> getLastLocation() {
        return zza(new zze(this));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public Task<LocationAvailability> getLocationAvailability() {
        return zza(new zzf(this));
    }

    public Task<Void> removeLocationUpdates(PendingIntent pendingIntent) {
        return zzbk.zzb(LocationServices.FusedLocationApi.removeLocationUpdates(zzpg(), pendingIntent));
    }

    public Task<Void> removeLocationUpdates(LocationCallback locationCallback) {
        zzbfk zza = zzbfm.zza(locationCallback, LocationCallback.class.getSimpleName());
        zzbr.zzb((Object) zza, (Object) "Listener key cannot be null.");
        return this.zzaAP.zza((GoogleApi) this, zza);
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public Task<Void> requestLocationUpdates(LocationRequest locationRequest, PendingIntent pendingIntent) {
        return zzbk.zzb(LocationServices.FusedLocationApi.requestLocationUpdates(zzpg(), locationRequest, pendingIntent));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public Task<Void> requestLocationUpdates(LocationRequest locationRequest, LocationCallback locationCallback, @Nullable Looper looper) {
        zzcez zza = zzcez.zza(locationRequest);
        zzbfi zzb = zzbfm.zzb(locationCallback, zzcfn.zzb(looper), LocationCallback.class.getSimpleName());
        zzbfq com_google_android_gms_location_zzg = new zzg(this, zzb, zza, zzb);
        zzbgk com_google_android_gms_location_zzh = new zzh(this, zzb.zzqE());
        zzbr.zzu(com_google_android_gms_location_zzg);
        zzbr.zzu(com_google_android_gms_location_zzh);
        zzbr.zzb(com_google_android_gms_location_zzg.zzqE(), (Object) "Listener has already been released.");
        zzbr.zzb(com_google_android_gms_location_zzh.zzqE(), (Object) "Listener has already been released.");
        zzbr.zzb(com_google_android_gms_location_zzg.zzqE().equals(com_google_android_gms_location_zzh.zzqE()), (Object) "Listener registration and unregistration methods must be constructed with the same ListenerHolder.");
        return this.zzaAP.zza((GoogleApi) this, com_google_android_gms_location_zzg, com_google_android_gms_location_zzh);
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public Task<Void> setMockLocation(Location location) {
        return zzbk.zzb(LocationServices.FusedLocationApi.setMockLocation(zzpg(), location));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public Task<Void> setMockMode(boolean z) {
        return zzbk.zzb(LocationServices.FusedLocationApi.setMockMode(zzpg(), z));
    }
}
