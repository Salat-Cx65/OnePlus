package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.zzaa;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzcem extends zzed implements zzcel {
    zzcem(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.internal.IGoogleLocationManagerService");
    }

    public final void zza(long j, boolean z, PendingIntent pendingIntent) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeLong(j);
        zzef.zza(zzY, true);
        zzef.zza(zzY, (Parcelable) pendingIntent);
        zzb(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, zzY);
    }

    public final void zza(zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (IInterface) com_google_android_gms_internal_zzceg);
        zzb(R.styleable.AppCompatTheme_editTextStyle, zzY);
    }

    public final void zza(zzcfb com_google_android_gms_internal_zzcfb) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (Parcelable) com_google_android_gms_internal_zzcfb);
        zzb(R.styleable.AppCompatTheme_dialogPreferredPadding, zzY);
    }

    public final void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzcej com_google_android_gms_internal_zzcej) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (Parcelable) geofencingRequest);
        zzef.zza(zzY, (Parcelable) pendingIntent);
        zzef.zza(zzY, (IInterface) com_google_android_gms_internal_zzcej);
        zzb(R.styleable.AppCompatTheme_colorSwitchThumbNormal, zzY);
    }

    public final void zza(LocationSettingsRequest locationSettingsRequest, zzcen com_google_android_gms_internal_zzcen, String str) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (Parcelable) locationSettingsRequest);
        zzef.zza(zzY, (IInterface) com_google_android_gms_internal_zzcen);
        zzY.writeString(str);
        zzb(R.styleable.AppCompatTheme_dropDownListViewStyle, zzY);
    }

    public final void zza(zzaa com_google_android_gms_location_zzaa, zzcej com_google_android_gms_internal_zzcej) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (Parcelable) com_google_android_gms_location_zzaa);
        zzef.zza(zzY, (IInterface) com_google_android_gms_internal_zzcej);
        zzb(R.styleable.AppCompatTheme_listPreferredItemHeight, zzY);
    }

    public final void zzai(boolean z) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, z);
        zzb(WeatherCircleView.ARC_DIN, zzY);
    }

    public final void zzc(PendingIntent pendingIntent) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (Parcelable) pendingIntent);
        zzb(ConnectionResult.RESOLUTION_REQUIRED, zzY);
    }

    public final void zzc(Location location) throws RemoteException {
        Parcel zzY = zzY();
        zzef.zza(zzY, (Parcelable) location);
        zzb(ConnectionResult.CANCELED, zzY);
    }

    public final Location zzdw(String str) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        Parcel zza = zza(R.styleable.Toolbar_titleMargin, zzY);
        Location location = (Location) zzef.zza(zza, Location.CREATOR);
        zza.recycle();
        return location;
    }

    public final LocationAvailability zzdx(String str) throws RemoteException {
        Parcel zzY = zzY();
        zzY.writeString(str);
        Parcel zza = zza(R.styleable.OneplusTheme_op_buttonPanelSideLayout, zzY);
        LocationAvailability locationAvailability = (LocationAvailability) zzef.zza(zza, LocationAvailability.CREATOR);
        zza.recycle();
        return locationAvailability;
    }
}
