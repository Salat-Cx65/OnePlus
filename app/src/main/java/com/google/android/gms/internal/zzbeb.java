package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzad;
import com.google.android.gms.common.internal.zzae;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzq;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbeb extends GoogleApiClient implements zzbfc {
    private final Context mContext;
    private final int zzaBd;
    private final GoogleApiAvailability zzaBf;
    private zza<? extends zzcuw, zzcux> zzaBg;
    private boolean zzaBj;
    private zzq zzaCC;
    private Map<Api<?>, Boolean> zzaCF;
    final Queue<zzbck<?, ?>> zzaCL;
    private final Lock zzaCx;
    private final zzad zzaDA;
    private zzbfb zzaDB;
    private volatile boolean zzaDC;
    private long zzaDD;
    private long zzaDE;
    private final zzbeg zzaDF;
    private zzbew zzaDG;
    final Map<zzc<?>, zze> zzaDH;
    Set<Scope> zzaDI;
    private final zzbfm zzaDJ;
    private final ArrayList<zzbcu> zzaDK;
    private Integer zzaDL;
    Set<zzbge> zzaDM;
    final zzbgh zzaDN;
    private final zzae zzaDO;
    private final Looper zzrP;

    public zzbeb(Context context, Lock lock, Looper looper, zzq com_google_android_gms_common_internal_zzq, GoogleApiAvailability googleApiAvailability, zza<? extends zzcuw, zzcux> com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux, Map<Api<?>, Boolean> map, List<ConnectionCallbacks> list, List<OnConnectionFailedListener> list2, Map<zzc<?>, zze> map2, int i, int i2, ArrayList<zzbcu> arrayList, boolean z) {
        this.zzaDB = null;
        this.zzaCL = new LinkedList();
        this.zzaDD = 120000;
        this.zzaDE = 5000;
        this.zzaDI = new HashSet();
        this.zzaDJ = new zzbfm();
        this.zzaDL = null;
        this.zzaDM = null;
        this.zzaDO = new zzbec(this);
        this.mContext = context;
        this.zzaCx = lock;
        this.zzaBj = false;
        this.zzaDA = new zzad(looper, this.zzaDO);
        this.zzrP = looper;
        this.zzaDF = new zzbeg(this, looper);
        this.zzaBf = googleApiAvailability;
        this.zzaBd = i;
        if (this.zzaBd >= 0) {
            this.zzaDL = Integer.valueOf(i2);
        }
        this.zzaCF = map;
        this.zzaDH = map2;
        this.zzaDK = arrayList;
        this.zzaDN = new zzbgh(this.zzaDH);
        for (ConnectionCallbacks connectionCallbacks : list) {
            this.zzaDA.registerConnectionCallbacks(connectionCallbacks);
        }
        for (OnConnectionFailedListener onConnectionFailedListener : list2) {
            this.zzaDA.registerConnectionFailedListener(onConnectionFailedListener);
        }
        this.zzaCC = com_google_android_gms_common_internal_zzq;
        this.zzaBg = com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux;
    }

    private final void resume() {
        this.zzaCx.lock();
        if (this.zzaDC) {
            zzqa();
        }
        this.zzaCx.unlock();
    }

    public static int zza(Iterable<zze> iterable, boolean z) {
        int i;
        int i2;
        Object obj = null;
        Object obj2 = null;
        for (zze com_google_android_gms_common_api_Api_zze : iterable) {
            if (com_google_android_gms_common_api_Api_zze.zzmt()) {
                i = 1;
            }
            i2 = com_google_android_gms_common_api_Api_zze.zzmE() ? 1 : i2;
        }
        return i != 0 ? (i2 == 0 || !z) ? 1 : RainSurfaceView.RAIN_LEVEL_SHOWER : RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
    }

    private final void zza(GoogleApiClient googleApiClient, zzbfz com_google_android_gms_internal_zzbfz, boolean z) {
        zzbha.zzaIA.zzd(googleApiClient).setResultCallback(new zzbef(this, com_google_android_gms_internal_zzbfz, z, googleApiClient));
    }

    private final void zzap(int i) {
        if (this.zzaDL == null) {
            this.zzaDL = Integer.valueOf(i);
        } else if (this.zzaDL.intValue() != i) {
            String valueOf = String.valueOf(zzaq(i));
            String valueOf2 = String.valueOf(zzaq(this.zzaDL.intValue()));
            throw new IllegalStateException(new StringBuilder((String.valueOf(valueOf).length() + 51) + String.valueOf(valueOf2).length()).append("Cannot use sign-in mode: ").append(valueOf).append(". Mode was already set to ").append(valueOf2).toString());
        }
        if (this.zzaDB == null) {
            boolean z;
            boolean z2;
            Object obj = null;
            Object obj2 = null;
            for (zze com_google_android_gms_common_api_Api_zze : this.zzaDH.values()) {
                if (com_google_android_gms_common_api_Api_zze.zzmt()) {
                    z = true;
                }
                z2 = com_google_android_gms_common_api_Api_zze.zzmE() ? true : z2;
            }
            switch (this.zzaDL.intValue()) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    if (!z) {
                        throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
                    } else if (z2) {
                        throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
                    }
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    if (z) {
                        if (this.zzaBj) {
                            this.zzaDB = new zzbdb(this.mContext, this.zzaCx, this.zzrP, this.zzaBf, this.zzaDH, this.zzaCC, this.zzaCF, this.zzaBg, this.zzaDK, this, true);
                            return;
                        } else {
                            this.zzaDB = zzbcw.zza(this.mContext, this, this.zzaCx, this.zzrP, this.zzaBf, this.zzaDH, this.zzaCC, this.zzaCF, this.zzaBg, this.zzaDK);
                            return;
                        }
                    }
            }
            if (!this.zzaBj || z2) {
                this.zzaDB = new zzbej(this.mContext, this, this.zzaCx, this.zzrP, this.zzaBf, this.zzaDH, this.zzaCC, this.zzaCF, this.zzaBg, this.zzaDK, this);
            } else {
                this.zzaDB = new zzbdb(this.mContext, this.zzaCx, this.zzrP, this.zzaBf, this.zzaDH, this.zzaCC, this.zzaCF, this.zzaBg, this.zzaDK, this, false);
            }
        }
    }

    private static String zzaq(int i) {
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return "SIGN_IN_MODE_REQUIRED";
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return "SIGN_IN_MODE_OPTIONAL";
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return "SIGN_IN_MODE_NONE";
            default:
                return "UNKNOWN";
        }
    }

    private final void zzqa() {
        this.zzaDA.zzrz();
        this.zzaDB.connect();
    }

    private final void zzqb() {
        this.zzaCx.lock();
        if (zzqc()) {
            zzqa();
        }
        this.zzaCx.unlock();
    }

    public final ConnectionResult blockingConnect() {
        boolean z = true;
        zzbr.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "blockingConnect must not be called on the UI thread");
        this.zzaCx.lock();
        if (this.zzaBd >= 0) {
            if (this.zzaDL == null) {
                z = false;
            }
            zzbr.zza(z, (Object) "Sign-in mode should have been set explicitly by auto-manage.");
        } else if (this.zzaDL == null) {
            this.zzaDL = Integer.valueOf(zza(this.zzaDH.values(), false));
        } else if (this.zzaDL.intValue() == 2) {
            throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
        }
        zzap(this.zzaDL.intValue());
        this.zzaDA.zzrz();
        ConnectionResult blockingConnect = this.zzaDB.blockingConnect();
        this.zzaCx.unlock();
        return blockingConnect;
    }

    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        boolean z = false;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            z = true;
        }
        zzbr.zza(z, (Object) "blockingConnect must not be called on the UI thread");
        zzbr.zzb((Object) timeUnit, (Object) "TimeUnit must not be null");
        this.zzaCx.lock();
        if (this.zzaDL == null) {
            this.zzaDL = Integer.valueOf(zza(this.zzaDH.values(), false));
        } else if (this.zzaDL.intValue() == 2) {
            throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
        }
        zzap(this.zzaDL.intValue());
        this.zzaDA.zzrz();
        ConnectionResult blockingConnect = this.zzaDB.blockingConnect(j, timeUnit);
        this.zzaCx.unlock();
        return blockingConnect;
    }

    public final PendingResult<Status> clearDefaultAccountAndReconnect() {
        zzbr.zza(isConnected(), (Object) "GoogleApiClient is not connected yet.");
        zzbr.zza(this.zzaDL.intValue() != 2, (Object) "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        PendingResult com_google_android_gms_internal_zzbfz = new zzbfz((GoogleApiClient) this);
        if (this.zzaDH.containsKey(zzbha.zzajT)) {
            zza(this, com_google_android_gms_internal_zzbfz, false);
        } else {
            AtomicReference atomicReference = new AtomicReference();
            GoogleApiClient build = new Builder(this.mContext).addApi(zzbha.API).addConnectionCallbacks(new zzbed(this, atomicReference, com_google_android_gms_internal_zzbfz)).addOnConnectionFailedListener(new zzbee(this, com_google_android_gms_internal_zzbfz)).setHandler(this.zzaDF).build();
            atomicReference.set(build);
            build.connect();
        }
        return com_google_android_gms_internal_zzbfz;
    }

    public final void connect() {
        boolean z = false;
        this.zzaCx.lock();
        if (this.zzaBd >= 0) {
            if (this.zzaDL != null) {
                z = true;
            }
            zzbr.zza(z, (Object) "Sign-in mode should have been set explicitly by auto-manage.");
        } else if (this.zzaDL == null) {
            this.zzaDL = Integer.valueOf(zza(this.zzaDH.values(), false));
        } else if (this.zzaDL.intValue() == 2) {
            throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
        }
        connect(this.zzaDL.intValue());
        this.zzaCx.unlock();
    }

    public final void connect(int i) {
        boolean z = true;
        this.zzaCx.lock();
        if (!(i == 3 || i == 1 || i == 2)) {
            z = false;
        }
        zzbr.zzb(z, new StringBuilder(33).append("Illegal sign-in mode: ").append(i).toString());
        zzap(i);
        zzqa();
        this.zzaCx.unlock();
    }

    public final void disconnect() {
        this.zzaCx.lock();
        this.zzaDN.release();
        if (this.zzaDB != null) {
            this.zzaDB.disconnect();
        }
        this.zzaDJ.release();
        for (zzbck com_google_android_gms_internal_zzbck : this.zzaCL) {
            com_google_android_gms_internal_zzbck.zza(null);
            com_google_android_gms_internal_zzbck.cancel();
        }
        this.zzaCL.clear();
        if (this.zzaDB == null) {
            this.zzaCx.unlock();
            return;
        }
        zzqc();
        this.zzaDA.zzry();
        this.zzaCx.unlock();
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("mContext=").println(this.mContext);
        printWriter.append(str).append("mResuming=").print(this.zzaDC);
        printWriter.append(" mWorkQueue.size()=").print(this.zzaCL.size());
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.zzaDN.zzaFn.size());
        if (this.zzaDB != null) {
            this.zzaDB.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    @NonNull
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        this.zzaCx.lock();
        if (!isConnected() && !this.zzaDC) {
            throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
        } else if (this.zzaDH.containsKey(api.zzpb())) {
            ConnectionResult connectionResult = this.zzaDB.getConnectionResult(api);
            if (connectionResult != null) {
                this.zzaCx.unlock();
                return connectionResult;
            } else if (this.zzaDC) {
                connectionResult = ConnectionResult.zzazZ;
                this.zzaCx.unlock();
                return connectionResult;
            } else {
                Log.w("GoogleApiClientImpl", zzqe());
                Log.wtf("GoogleApiClientImpl", String.valueOf(api.getName()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map"), new Exception());
                connectionResult = new ConnectionResult(8, null);
                this.zzaCx.unlock();
                return connectionResult;
            }
        } else {
            throw new IllegalArgumentException(String.valueOf(api.getName()).concat(" was never registered with GoogleApiClient"));
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Looper getLooper() {
        return this.zzrP;
    }

    public final boolean hasConnectedApi(@NonNull Api<?> api) {
        if (!isConnected()) {
            return false;
        }
        zze com_google_android_gms_common_api_Api_zze = (zze) this.zzaDH.get(api.zzpb());
        return com_google_android_gms_common_api_Api_zze != null && com_google_android_gms_common_api_Api_zze.isConnected();
    }

    public final boolean isConnected() {
        return this.zzaDB != null && this.zzaDB.isConnected();
    }

    public final boolean isConnecting() {
        return this.zzaDB != null && this.zzaDB.isConnecting();
    }

    public final boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks) {
        return this.zzaDA.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    public final boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        return this.zzaDA.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    public final void reconnect() {
        disconnect();
        connect();
    }

    public final void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        this.zzaDA.registerConnectionCallbacks(connectionCallbacks);
    }

    public final void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        this.zzaDA.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public final void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        zzbfd com_google_android_gms_internal_zzbfd = new zzbfd(fragmentActivity);
        if (this.zzaBd >= 0) {
            zzbcg.zza(com_google_android_gms_internal_zzbfd).zzal(this.zzaBd);
            return;
        }
        throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
    }

    public final void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        this.zzaDA.unregisterConnectionCallbacks(connectionCallbacks);
    }

    public final void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        this.zzaDA.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @NonNull
    public final <C extends zze> C zza(@NonNull zzc<C> com_google_android_gms_common_api_Api_zzc_C) {
        Object obj = (zze) this.zzaDH.get(com_google_android_gms_common_api_Api_zzc_C);
        zzbr.zzb(obj, (Object) "Appropriate Api was not requested.");
        return obj;
    }

    public final void zza(zzbge com_google_android_gms_internal_zzbge) {
        this.zzaCx.lock();
        if (this.zzaDM == null) {
            this.zzaDM = new HashSet();
        }
        this.zzaDM.add(com_google_android_gms_internal_zzbge);
        this.zzaCx.unlock();
    }

    public final boolean zza(@NonNull Api<?> api) {
        return this.zzaDH.containsKey(api.zzpb());
    }

    public final boolean zza(zzbfu com_google_android_gms_internal_zzbfu) {
        return this.zzaDB != null && this.zzaDB.zza(com_google_android_gms_internal_zzbfu);
    }

    public final void zzb(zzbge com_google_android_gms_internal_zzbge) {
        this.zzaCx.lock();
        if (this.zzaDM == null) {
            Log.wtf("GoogleApiClientImpl", "Attempted to remove pending transform when no transforms are registered.", new Exception());
        } else if (!this.zzaDM.remove(com_google_android_gms_internal_zzbge)) {
            Log.wtf("GoogleApiClientImpl", "Failed to remove pending transform - this may lead to memory leaks!", new Exception());
        } else if (!zzqd()) {
            this.zzaDB.zzpC();
        }
        this.zzaCx.unlock();
    }

    public final void zzc(ConnectionResult connectionResult) {
        if (!com.google.android.gms.common.zze.zze(this.mContext, connectionResult.getErrorCode())) {
            zzqc();
        }
        if (!this.zzaDC) {
            this.zzaDA.zzk(connectionResult);
            this.zzaDA.zzry();
        }
    }

    public final <A extends zzb, R extends Result, T extends zzbck<R, A>> T zzd(@NonNull T t) {
        zzbr.zzb(t.zzpb() != null, (Object) "This task can not be enqueued (it's probably a Batch or malformed)");
        boolean containsKey = this.zzaDH.containsKey(t.zzpb());
        String name = t.zzpe() != null ? t.zzpe().getName() : "the API";
        zzbr.zzb(containsKey, new StringBuilder(String.valueOf(name).length() + 65).append("GoogleApiClient is not configured to use ").append(name).append(" required for this call.").toString());
        this.zzaCx.lock();
        if (this.zzaDB == null) {
            this.zzaCL.add(t);
            this.zzaCx.unlock();
            return t;
        }
        t = this.zzaDB.zzd(t);
        this.zzaCx.unlock();
        return t;
    }

    public final <A extends zzb, T extends zzbck<? extends Result, A>> T zze(@NonNull T t) {
        zzbr.zzb(t.zzpb() != null, (Object) "This task can not be executed (it's probably a Batch or malformed)");
        boolean containsKey = this.zzaDH.containsKey(t.zzpb());
        String name = t.zzpe() != null ? t.zzpe().getName() : "the API";
        zzbr.zzb(containsKey, new StringBuilder(String.valueOf(name).length() + 65).append("GoogleApiClient is not configured to use ").append(name).append(" required for this call.").toString());
        this.zzaCx.lock();
        if (this.zzaDB == null) {
            throw new IllegalStateException("GoogleApiClient is not connected yet.");
        } else if (this.zzaDC) {
            this.zzaCL.add(t);
            while (!this.zzaCL.isEmpty()) {
                zzbck com_google_android_gms_internal_zzbck = (zzbck) this.zzaCL.remove();
                this.zzaDN.zzb(com_google_android_gms_internal_zzbck);
                com_google_android_gms_internal_zzbck.zzr(Status.zzaBq);
            }
            this.zzaCx.unlock();
            return t;
        } else {
            t = this.zzaDB.zze(t);
            this.zzaCx.unlock();
            return t;
        }
    }

    public final void zze(int i, boolean z) {
        if (!(i != 1 || z || this.zzaDC)) {
            this.zzaDC = true;
            if (this.zzaDG == null) {
                this.zzaDG = GoogleApiAvailability.zza(this.mContext.getApplicationContext(), new zzbeh(this));
            }
            this.zzaDF.sendMessageDelayed(this.zzaDF.obtainMessage(1), this.zzaDD);
            this.zzaDF.sendMessageDelayed(this.zzaDF.obtainMessage(RainSurfaceView.RAIN_LEVEL_SHOWER), this.zzaDE);
        }
        this.zzaDN.zzqK();
        this.zzaDA.zzaA(i);
        this.zzaDA.zzry();
        if (i == 2) {
            zzqa();
        }
    }

    public final void zzm(Bundle bundle) {
        while (!this.zzaCL.isEmpty()) {
            zze((zzbck) this.zzaCL.remove());
        }
        this.zzaDA.zzn(bundle);
    }

    public final <L> zzbfi<L> zzp(@NonNull L l) {
        this.zzaCx.lock();
        zzbfi<L> zza = this.zzaDJ.zza(l, this.zzrP, "NO_TYPE");
        this.zzaCx.unlock();
        return zza;
    }

    public final void zzpj() {
        if (this.zzaDB != null) {
            this.zzaDB.zzpj();
        }
    }

    final boolean zzqc() {
        if (!this.zzaDC) {
            return false;
        }
        this.zzaDC = false;
        this.zzaDF.removeMessages(RainSurfaceView.RAIN_LEVEL_SHOWER);
        this.zzaDF.removeMessages(1);
        if (this.zzaDG != null) {
            this.zzaDG.unregister();
            this.zzaDG = null;
        }
        return true;
    }

    final boolean zzqd() {
        boolean z = false;
        this.zzaCx.lock();
        if (this.zzaDM == null) {
            this.zzaCx.unlock();
        } else {
            if (!this.zzaDM.isEmpty()) {
                Object obj = 1;
            }
            this.zzaCx.unlock();
        }
        return z;
    }

    final String zzqe() {
        Writer stringWriter = new StringWriter();
        dump(StringUtils.EMPTY_STRING, null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }
}
