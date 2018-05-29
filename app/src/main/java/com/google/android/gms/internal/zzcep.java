package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.zzj;
import com.google.android.gms.location.zzm;
import java.util.HashMap;
import java.util.Map;

public final class zzcep {
    private final Context mContext;
    private final Map<zzbfk<LocationListener>, zzcet> zzaWY;
    private final zzcff<zzcel> zzbiF;
    private ContentProviderClient zzbiQ;
    private boolean zzbiR;
    private final Map<zzbfk<LocationCallback>, zzceq> zzbiS;

    public zzcep(Context context, zzcff<zzcel> com_google_android_gms_internal_zzcff_com_google_android_gms_internal_zzcel) {
        this.zzbiQ = null;
        this.zzbiR = false;
        this.zzaWY = new HashMap();
        this.zzbiS = new HashMap();
        this.mContext = context;
        this.zzbiF = com_google_android_gms_internal_zzcff_com_google_android_gms_internal_zzcel;
    }

    private final zzcet zzf(zzbfi<LocationListener> com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationListener) {
        zzcet com_google_android_gms_internal_zzcet;
        synchronized (this.zzaWY) {
            com_google_android_gms_internal_zzcet = (zzcet) this.zzaWY.get(com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationListener.zzqE());
            if (com_google_android_gms_internal_zzcet == null) {
                com_google_android_gms_internal_zzcet = new zzcet(com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationListener);
            }
            this.zzaWY.put(com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationListener.zzqE(), com_google_android_gms_internal_zzcet);
        }
        return com_google_android_gms_internal_zzcet;
    }

    private final zzceq zzg(zzbfi<LocationCallback> com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationCallback) {
        zzceq com_google_android_gms_internal_zzceq;
        synchronized (this.zzbiS) {
            com_google_android_gms_internal_zzceq = (zzceq) this.zzbiS.get(com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationCallback.zzqE());
            if (com_google_android_gms_internal_zzceq == null) {
                com_google_android_gms_internal_zzceq = new zzceq(com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationCallback);
            }
            this.zzbiS.put(com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationCallback.zzqE(), com_google_android_gms_internal_zzceq);
        }
        return com_google_android_gms_internal_zzceq;
    }

    public final Location getLastLocation() {
        this.zzbiF.zzrc();
        try {
            return ((zzcel) this.zzbiF.zzrd()).zzdw(this.mContext.getPackageName());
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public final void removeAllListeners() {
        try {
            synchronized (this.zzaWY) {
                for (zzm com_google_android_gms_location_zzm : this.zzaWY.values()) {
                    if (com_google_android_gms_location_zzm != null) {
                        ((zzcel) this.zzbiF.zzrd()).zza(zzcfb.zza(com_google_android_gms_location_zzm, null));
                    }
                }
                this.zzaWY.clear();
            }
            synchronized (this.zzbiS) {
                for (zzj com_google_android_gms_location_zzj : this.zzbiS.values()) {
                    if (com_google_android_gms_location_zzj != null) {
                        ((zzcel) this.zzbiF.zzrd()).zza(zzcfb.zza(com_google_android_gms_location_zzj, null));
                    }
                }
                this.zzbiS.clear();
            }
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        } catch (Throwable th) {
        }
    }

    public final void zza(PendingIntent pendingIntent, zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        this.zzbiF.zzrc();
        ((zzcel) this.zzbiF.zzrd()).zza(new zzcfb(2, null, null, pendingIntent, null, com_google_android_gms_internal_zzceg != null ? com_google_android_gms_internal_zzceg.asBinder() : null));
    }

    public final void zza(zzbfk<LocationListener> com_google_android_gms_internal_zzbfk_com_google_android_gms_location_LocationListener, zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        this.zzbiF.zzrc();
        zzbr.zzb((Object) com_google_android_gms_internal_zzbfk_com_google_android_gms_location_LocationListener, (Object) "Invalid null listener key");
        synchronized (this.zzaWY) {
            zzm com_google_android_gms_location_zzm = (zzcet) this.zzaWY.remove(com_google_android_gms_internal_zzbfk_com_google_android_gms_location_LocationListener);
            if (com_google_android_gms_location_zzm != null) {
                com_google_android_gms_location_zzm.release();
                ((zzcel) this.zzbiF.zzrd()).zza(zzcfb.zza(com_google_android_gms_location_zzm, com_google_android_gms_internal_zzceg));
            }
        }
    }

    public final void zza(zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        this.zzbiF.zzrc();
        ((zzcel) this.zzbiF.zzrd()).zza(com_google_android_gms_internal_zzceg);
    }

    public final void zza(zzcez com_google_android_gms_internal_zzcez, zzbfi<LocationCallback> com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationCallback, zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        this.zzbiF.zzrc();
        ((zzcel) this.zzbiF.zzrd()).zza(new zzcfb(1, com_google_android_gms_internal_zzcez, null, null, zzg(com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationCallback).asBinder(), com_google_android_gms_internal_zzceg != null ? com_google_android_gms_internal_zzceg.asBinder() : null));
    }

    public final void zza(LocationRequest locationRequest, PendingIntent pendingIntent, zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        this.zzbiF.zzrc();
        ((zzcel) this.zzbiF.zzrd()).zza(new zzcfb(1, zzcez.zza(locationRequest), null, pendingIntent, null, com_google_android_gms_internal_zzceg != null ? com_google_android_gms_internal_zzceg.asBinder() : null));
    }

    public final void zza(LocationRequest locationRequest, zzbfi<LocationListener> com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationListener, zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        this.zzbiF.zzrc();
        ((zzcel) this.zzbiF.zzrd()).zza(new zzcfb(1, zzcez.zza(locationRequest), zzf(com_google_android_gms_internal_zzbfi_com_google_android_gms_location_LocationListener).asBinder(), null, null, com_google_android_gms_internal_zzceg != null ? com_google_android_gms_internal_zzceg.asBinder() : null));
    }

    public final void zzai(boolean z) throws RemoteException {
        this.zzbiF.zzrc();
        ((zzcel) this.zzbiF.zzrd()).zzai(z);
        this.zzbiR = z;
    }

    public final void zzb(zzbfk<LocationCallback> com_google_android_gms_internal_zzbfk_com_google_android_gms_location_LocationCallback, zzceg com_google_android_gms_internal_zzceg) throws RemoteException {
        this.zzbiF.zzrc();
        zzbr.zzb((Object) com_google_android_gms_internal_zzbfk_com_google_android_gms_location_LocationCallback, (Object) "Invalid null listener key");
        synchronized (this.zzbiS) {
            zzj com_google_android_gms_location_zzj = (zzceq) this.zzbiS.remove(com_google_android_gms_internal_zzbfk_com_google_android_gms_location_LocationCallback);
            if (com_google_android_gms_location_zzj != null) {
                com_google_android_gms_location_zzj.release();
                ((zzcel) this.zzbiF.zzrd()).zza(zzcfb.zza(com_google_android_gms_location_zzj, com_google_android_gms_internal_zzceg));
            }
        }
    }

    public final void zzc(Location location) throws RemoteException {
        this.zzbiF.zzrc();
        ((zzcel) this.zzbiF.zzrd()).zzc(location);
    }

    public final LocationAvailability zzvP() {
        this.zzbiF.zzrc();
        try {
            return ((zzcel) this.zzbiF.zzrd()).zzdx(this.mContext.getPackageName());
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public final void zzvQ() {
        if (this.zzbiR) {
            try {
                zzai(false);
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
