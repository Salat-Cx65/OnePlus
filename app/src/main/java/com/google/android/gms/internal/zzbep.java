package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzca;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import net.oneplus.weather.widget.WeatherCircleView;

public final class zzbep<O extends ApiOptions> implements ConnectionCallbacks, OnConnectionFailedListener, zzbcv {
    private final zzbcf<O> zzaAM;
    private final zze zzaCA;
    private boolean zzaDC;
    private /* synthetic */ zzben zzaEo;
    private final Queue<zzbby> zzaEp;
    private final zzb zzaEq;
    private final zzbdf zzaEr;
    private final Set<zzbch> zzaEs;
    private final Map<zzbfk<?>, zzbfr> zzaEt;
    private final int zzaEu;
    private final zzbfv zzaEv;
    private ConnectionResult zzaEw;

    @WorkerThread
    public zzbep(zzben com_google_android_gms_internal_zzben, GoogleApi<O> googleApi) {
        this.zzaEo = com_google_android_gms_internal_zzben;
        this.zzaEp = new LinkedList();
        this.zzaEs = new HashSet();
        this.zzaEt = new HashMap();
        this.zzaEw = null;
        this.zzaCA = googleApi.zza(com_google_android_gms_internal_zzben.mHandler.getLooper(), this);
        if (this.zzaCA instanceof zzca) {
            zzca com_google_android_gms_common_internal_zzca = (zzca) this.zzaCA;
            this.zzaEq = null;
        } else {
            this.zzaEq = this.zzaCA;
        }
        this.zzaAM = googleApi.zzpf();
        this.zzaEr = new zzbdf();
        this.zzaEu = googleApi.getInstanceId();
        if (this.zzaCA.zzmt()) {
            this.zzaEv = googleApi.zza(com_google_android_gms_internal_zzben.mContext, com_google_android_gms_internal_zzben.mHandler);
        } else {
            this.zzaEv = null;
        }
    }

    @WorkerThread
    private final void zzb(zzbby com_google_android_gms_internal_zzbby) {
        com_google_android_gms_internal_zzbby.zza(this.zzaEr, zzmt());
        try {
            com_google_android_gms_internal_zzbby.zza(this);
        } catch (DeadObjectException e) {
            onConnectionSuspended(1);
            this.zzaCA.disconnect();
        }
    }

    @WorkerThread
    private final void zzi(ConnectionResult connectionResult) {
        for (zzbch com_google_android_gms_internal_zzbch : this.zzaEs) {
            com_google_android_gms_internal_zzbch.zza(this.zzaAM, connectionResult);
        }
        this.zzaEs.clear();
    }

    @WorkerThread
    private final void zzqo() {
        zzqr();
        zzi(ConnectionResult.zzazZ);
        zzqt();
        for (zzbfr com_google_android_gms_internal_zzbfr : this.zzaEt.values()) {
            try {
                com_google_android_gms_internal_zzbfr.zzaBw.zzb(this.zzaEq, new TaskCompletionSource());
            } catch (DeadObjectException e) {
                onConnectionSuspended(1);
                this.zzaCA.disconnect();
            } catch (RemoteException e2) {
            }
        }
        while (this.zzaCA.isConnected() && !this.zzaEp.isEmpty()) {
            zzb((zzbby) this.zzaEp.remove());
        }
        zzqu();
    }

    @WorkerThread
    private final void zzqp() {
        zzqr();
        this.zzaDC = true;
        this.zzaEr.zzpO();
        this.zzaEo.mHandler.sendMessageDelayed(Message.obtain(this.zzaEo.mHandler, ConnectionResult.SERVICE_INVALID, this.zzaAM), this.zzaEo.zzaDE);
        this.zzaEo.mHandler.sendMessageDelayed(Message.obtain(this.zzaEo.mHandler, ConnectionResult.LICENSE_CHECK_FAILED, this.zzaAM), this.zzaEo.zzaDD);
        this.zzaEo.zzaEi = -1;
    }

    @WorkerThread
    private final void zzqt() {
        if (this.zzaDC) {
            this.zzaEo.mHandler.removeMessages(ConnectionResult.LICENSE_CHECK_FAILED, this.zzaAM);
            this.zzaEo.mHandler.removeMessages(ConnectionResult.SERVICE_INVALID, this.zzaAM);
            this.zzaDC = false;
        }
    }

    private final void zzqu() {
        this.zzaEo.mHandler.removeMessages(WeatherCircleView.ARC_DIN, this.zzaAM);
        this.zzaEo.mHandler.sendMessageDelayed(this.zzaEo.mHandler.obtainMessage(WeatherCircleView.ARC_DIN, this.zzaAM), this.zzaEo.zzaEg);
    }

    @WorkerThread
    public final void connect() {
        zzbr.zza(this.zzaEo.mHandler);
        if (!this.zzaCA.isConnected() && !this.zzaCA.isConnecting()) {
            if (this.zzaCA.zzpc() && this.zzaEo.zzaEi != 0) {
                this.zzaEo.zzaEi = this.zzaEo.zzaBf.isGooglePlayServicesAvailable(this.zzaEo.mContext);
                if (this.zzaEo.zzaEi != 0) {
                    onConnectionFailed(new ConnectionResult(this.zzaEo.zzaEi, null));
                    return;
                }
            }
            Object com_google_android_gms_internal_zzbet = new zzbet(this.zzaEo, this.zzaCA, this.zzaAM);
            if (this.zzaCA.zzmt()) {
                this.zzaEv.zza(com_google_android_gms_internal_zzbet);
            }
            this.zzaCA.zza(com_google_android_gms_internal_zzbet);
        }
    }

    public final int getInstanceId() {
        return this.zzaEu;
    }

    final boolean isConnected() {
        return this.zzaCA.isConnected();
    }

    public final void onConnected(@Nullable Bundle bundle) {
        if (Looper.myLooper() == this.zzaEo.mHandler.getLooper()) {
            zzqo();
        } else {
            this.zzaEo.mHandler.post(new zzbeq(this));
        }
    }

    @WorkerThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zzbr.zza(this.zzaEo.mHandler);
        if (this.zzaEv != null) {
            this.zzaEv.zzqG();
        }
        zzqr();
        this.zzaEo.zzaEi = -1;
        zzi(connectionResult);
        if (connectionResult.getErrorCode() == 4) {
            zzt(zzben.zzaEf);
        } else if (this.zzaEp.isEmpty()) {
            this.zzaEw = connectionResult;
        } else {
            synchronized (zzben.zzuI) {
                if (this.zzaEo.zzaEl == null || !this.zzaEo.zzaEm.contains(this.zzaAM)) {
                    if (!this.zzaEo.zzc(connectionResult, this.zzaEu)) {
                        if (connectionResult.getErrorCode() == 18) {
                            this.zzaDC = true;
                        }
                        if (this.zzaDC) {
                            this.zzaEo.mHandler.sendMessageDelayed(Message.obtain(this.zzaEo.mHandler, ConnectionResult.SERVICE_INVALID, this.zzaAM), this.zzaEo.zzaDE);
                            return;
                        }
                        String valueOf = String.valueOf(this.zzaAM.zzpp());
                        zzt(new Status(17, new StringBuilder(String.valueOf(valueOf).length() + 38).append("API: ").append(valueOf).append(" is not available on this device.").toString()));
                        return;
                    }
                    return;
                }
                this.zzaEo.zzaEl.zzb(connectionResult, this.zzaEu);
            }
        }
    }

    public final void onConnectionSuspended(int i) {
        if (Looper.myLooper() == this.zzaEo.mHandler.getLooper()) {
            zzqp();
        } else {
            this.zzaEo.mHandler.post(new zzber(this));
        }
    }

    @WorkerThread
    public final void resume() {
        zzbr.zza(this.zzaEo.mHandler);
        if (this.zzaDC) {
            connect();
        }
    }

    @WorkerThread
    public final void signOut() {
        zzbr.zza(this.zzaEo.mHandler);
        zzt(zzben.zzaEe);
        this.zzaEr.zzpN();
        for (zzbfk com_google_android_gms_internal_zzbfk : this.zzaEt.keySet()) {
            zza(new zzbcd(com_google_android_gms_internal_zzbfk, new TaskCompletionSource()));
        }
        zzi(new ConnectionResult(4));
        this.zzaCA.disconnect();
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (Looper.myLooper() == this.zzaEo.mHandler.getLooper()) {
            onConnectionFailed(connectionResult);
        } else {
            this.zzaEo.mHandler.post(new zzbes(this, connectionResult));
        }
    }

    @WorkerThread
    public final void zza(zzbby com_google_android_gms_internal_zzbby) {
        zzbr.zza(this.zzaEo.mHandler);
        if (this.zzaCA.isConnected()) {
            zzb(com_google_android_gms_internal_zzbby);
            zzqu();
            return;
        }
        this.zzaEp.add(com_google_android_gms_internal_zzbby);
        if (this.zzaEw == null || !this.zzaEw.hasResolution()) {
            connect();
        } else {
            onConnectionFailed(this.zzaEw);
        }
    }

    @WorkerThread
    public final void zza(zzbch com_google_android_gms_internal_zzbch) {
        zzbr.zza(this.zzaEo.mHandler);
        this.zzaEs.add(com_google_android_gms_internal_zzbch);
    }

    @WorkerThread
    public final void zzh(@NonNull ConnectionResult connectionResult) {
        zzbr.zza(this.zzaEo.mHandler);
        this.zzaCA.disconnect();
        onConnectionFailed(connectionResult);
    }

    public final boolean zzmt() {
        return this.zzaCA.zzmt();
    }

    public final zze zzpH() {
        return this.zzaCA;
    }

    @WorkerThread
    public final void zzqb() {
        zzbr.zza(this.zzaEo.mHandler);
        if (this.zzaDC) {
            zzqt();
            zzt(this.zzaEo.zzaBf.isGooglePlayServicesAvailable(this.zzaEo.mContext) == 18 ? new Status(8, "Connection timed out while waiting for Google Play services update to complete.") : new Status(8, "API failed to connect while resuming due to an unknown error."));
            this.zzaCA.disconnect();
        }
    }

    public final Map<zzbfk<?>, zzbfr> zzqq() {
        return this.zzaEt;
    }

    @WorkerThread
    public final void zzqr() {
        zzbr.zza(this.zzaEo.mHandler);
        this.zzaEw = null;
    }

    @WorkerThread
    public final ConnectionResult zzqs() {
        zzbr.zza(this.zzaEo.mHandler);
        return this.zzaEw;
    }

    @WorkerThread
    public final void zzqv() {
        zzbr.zza(this.zzaEo.mHandler);
        if (!this.zzaCA.isConnected() || this.zzaEt.size() != 0) {
            return;
        }
        if (this.zzaEr.zzpM()) {
            zzqu();
        } else {
            this.zzaCA.disconnect();
        }
    }

    final zzcuw zzqw() {
        return this.zzaEv == null ? null : this.zzaEv.zzqw();
    }

    @WorkerThread
    public final void zzt(Status status) {
        zzbr.zza(this.zzaEo.mHandler);
        for (zzbby com_google_android_gms_internal_zzbby : this.zzaEp) {
            com_google_android_gms_internal_zzbby.zzp(status);
        }
        this.zzaEp.clear();
    }
}
