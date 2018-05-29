package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.zzy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzbu;
import com.google.android.gms.common.internal.zzq;
import java.util.HashSet;
import java.util.Set;

public final class zzbfv extends zzcvb implements ConnectionCallbacks, OnConnectionFailedListener {
    private static zza<? extends zzcuw, zzcux> zzaEX;
    private final Context mContext;
    private final Handler mHandler;
    private final zza<? extends zzcuw, zzcux> zzaAz;
    private zzq zzaCC;
    private zzcuw zzaDj;
    private final boolean zzaEY;
    private zzbfx zzaEZ;
    private Set<Scope> zzamg;

    static {
        zzaEX = zzcus.zzajU;
    }

    @WorkerThread
    public zzbfv(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
        this.zzaAz = zzaEX;
        this.zzaEY = true;
    }

    @WorkerThread
    public zzbfv(Context context, Handler handler, @NonNull zzq com_google_android_gms_common_internal_zzq, zza<? extends zzcuw, zzcux> com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux) {
        this.mContext = context;
        this.mHandler = handler;
        this.zzaCC = (zzq) zzbr.zzb((Object) com_google_android_gms_common_internal_zzq, (Object) "ClientSettings must not be null");
        this.zzamg = com_google_android_gms_common_internal_zzq.zzrl();
        this.zzaAz = com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux;
        this.zzaEY = false;
    }

    @WorkerThread
    private final void zzc(zzcvj com_google_android_gms_internal_zzcvj) {
        ConnectionResult zzpx = com_google_android_gms_internal_zzcvj.zzpx();
        if (zzpx.isSuccess()) {
            zzbu zzAv = com_google_android_gms_internal_zzcvj.zzAv();
            ConnectionResult zzpx2 = zzAv.zzpx();
            if (zzpx2.isSuccess()) {
                this.zzaEZ.zzb(zzAv.zzrG(), this.zzamg);
            } else {
                String valueOf = String.valueOf(zzpx2);
                Log.wtf("SignInCoordinator", new StringBuilder(String.valueOf(valueOf).length() + 48).append("Sign-in succeeded with resolve account failure: ").append(valueOf).toString(), new Exception());
                this.zzaEZ.zzh(zzpx2);
                this.zzaDj.disconnect();
                return;
            }
        }
        this.zzaEZ.zzh(zzpx);
        this.zzaDj.disconnect();
    }

    @WorkerThread
    public final void onConnected(@Nullable Bundle bundle) {
        this.zzaDj.zza(this);
    }

    @WorkerThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzaEZ.zzh(connectionResult);
    }

    @WorkerThread
    public final void onConnectionSuspended(int i) {
        this.zzaDj.disconnect();
    }

    @WorkerThread
    public final void zza(zzbfx com_google_android_gms_internal_zzbfx) {
        if (this.zzaDj != null) {
            this.zzaDj.disconnect();
        }
        if (this.zzaEY) {
            GoogleSignInOptions zzmM = zzy.zzaj(this.mContext).zzmM();
            this.zzamg = zzmM == null ? new HashSet() : new HashSet(zzmM.zzmy());
            this.zzaCC = new zzq(null, this.zzamg, null, 0, null, null, null, zzcux.zzbCQ);
        }
        this.zzaCC.zzc(Integer.valueOf(System.identityHashCode(this)));
        this.zzaDj = (zzcuw) this.zzaAz.zza(this.mContext, this.mHandler.getLooper(), this.zzaCC, this.zzaCC.zzrr(), this, this);
        this.zzaEZ = com_google_android_gms_internal_zzbfx;
        this.zzaDj.connect();
    }

    @BinderThread
    public final void zzb(zzcvj com_google_android_gms_internal_zzcvj) {
        this.mHandler.post(new zzbfw(this, com_google_android_gms_internal_zzcvj));
    }

    public final void zzqG() {
        if (this.zzaDj != null) {
            this.zzaDj.disconnect();
        }
    }

    public final zzcuw zzqw() {
        return this.zzaDj;
    }
}
