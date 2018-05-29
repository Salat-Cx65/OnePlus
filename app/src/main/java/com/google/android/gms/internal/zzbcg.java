package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzbr;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zzbcg extends zzbcm {
    private final SparseArray<zza> zzaBD;

    class zza implements OnConnectionFailedListener {
        public final int zzaBE;
        public final GoogleApiClient zzaBF;
        public final OnConnectionFailedListener zzaBG;

        public zza(int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
            this.zzaBE = i;
            this.zzaBF = googleApiClient;
            this.zzaBG = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            String valueOf = String.valueOf(connectionResult);
            Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 27).append("beginFailureResolution for ").append(valueOf).toString());
            zzbcg.this.zzb(connectionResult, this.zzaBE);
        }
    }

    private zzbcg(zzbff com_google_android_gms_internal_zzbff) {
        super(com_google_android_gms_internal_zzbff);
        this.zzaBD = new SparseArray();
        this.zzaEI.zza("AutoManageHelper", (zzbfe) this);
    }

    public static zzbcg zza(zzbfd com_google_android_gms_internal_zzbfd) {
        zzbff zzb = zzb(com_google_android_gms_internal_zzbfd);
        zzbcg com_google_android_gms_internal_zzbcg = (zzbcg) zzb.zza("AutoManageHelper", zzbcg.class);
        return com_google_android_gms_internal_zzbcg != null ? com_google_android_gms_internal_zzbcg : new zzbcg(zzb);
    }

    @Nullable
    private final zza zzam(int i) {
        return this.zzaBD.size() <= i ? null : (zza) this.zzaBD.get(this.zzaBD.keyAt(i));
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (int i = 0; i < this.zzaBD.size(); i++) {
            zza zzam = zzam(i);
            if (zzam != null) {
                printWriter.append(str).append("GoogleApiClient #").print(zzam.zzaBE);
                printWriter.println(":");
                zzam.zzaBF.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
            }
        }
    }

    public final void onStart() {
        super.onStart();
        boolean z = this.mStarted;
        String valueOf = String.valueOf(this.zzaBD);
        Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 14).append("onStart ").append(z).append(" ").append(valueOf).toString());
        if (this.zzaBP.get() == null) {
            for (int i = 0; i < this.zzaBD.size(); i++) {
                zza zzam = zzam(i);
                if (zzam != null) {
                    zzam.zzaBF.connect();
                }
            }
        }
    }

    public final void onStop() {
        super.onStop();
        for (int i = 0; i < this.zzaBD.size(); i++) {
            zza zzam = zzam(i);
            if (zzam != null) {
                zzam.zzaBF.disconnect();
            }
        }
    }

    public final void zza(int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
        zzbr.zzb((Object) googleApiClient, (Object) "GoogleApiClient instance cannot be null");
        zzbr.zza(this.zzaBD.indexOfKey(i) < 0, new StringBuilder(54).append("Already managing a GoogleApiClient with id ").append(i).toString());
        zzbcn com_google_android_gms_internal_zzbcn = (zzbcn) this.zzaBP.get();
        boolean z = this.mStarted;
        String valueOf = String.valueOf(com_google_android_gms_internal_zzbcn);
        Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf).length() + 49).append("starting AutoManage for client ").append(i).append(" ").append(z).append(" ").append(valueOf).toString());
        this.zzaBD.put(i, new zza(i, googleApiClient, onConnectionFailedListener));
        if (this.mStarted && com_google_android_gms_internal_zzbcn == null) {
            String valueOf2 = String.valueOf(googleApiClient);
            Log.d("AutoManageHelper", new StringBuilder(String.valueOf(valueOf2).length() + 11).append("connecting ").append(valueOf2).toString());
            googleApiClient.connect();
        }
    }

    protected final void zza(ConnectionResult connectionResult, int i) {
        Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
        if (i < 0) {
            Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
            return;
        }
        zza com_google_android_gms_internal_zzbcg_zza = (zza) this.zzaBD.get(i);
        if (com_google_android_gms_internal_zzbcg_zza != null) {
            zzal(i);
            OnConnectionFailedListener onConnectionFailedListener = com_google_android_gms_internal_zzbcg_zza.zzaBG;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
    }

    public final void zzal(int i) {
        zza com_google_android_gms_internal_zzbcg_zza = (zza) this.zzaBD.get(i);
        this.zzaBD.remove(i);
        if (com_google_android_gms_internal_zzbcg_zza != null) {
            com_google_android_gms_internal_zzbcg_zza.zzaBF.unregisterConnectionFailedListener(com_google_android_gms_internal_zzbcg_zza);
            com_google_android_gms_internal_zzbcg_zza.zzaBF.disconnect();
        }
    }

    protected final void zzpq() {
        for (int i = 0; i < this.zzaBD.size(); i++) {
            zza zzam = zzam(i);
            if (zzam != null) {
                zzam.zzaBF.connect();
            }
        }
    }
}
