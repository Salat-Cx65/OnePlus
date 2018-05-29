package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzam;
import com.google.android.gms.common.internal.zzbu;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbdp implements zzbei {
    private final Context mContext;
    private final zza<? extends zzcuw, zzcux> zzaBg;
    private final zzq zzaCC;
    private final Map<Api<?>, Boolean> zzaCF;
    private final zze zzaCH;
    private ConnectionResult zzaCQ;
    private final Lock zzaCx;
    private final zzbej zzaDb;
    private int zzaDe;
    private int zzaDf;
    private int zzaDg;
    private final Bundle zzaDh;
    private final Set<zzc> zzaDi;
    private zzcuw zzaDj;
    private boolean zzaDk;
    private boolean zzaDl;
    private boolean zzaDm;
    private zzam zzaDn;
    private boolean zzaDo;
    private boolean zzaDp;
    private ArrayList<Future<?>> zzaDq;

    public zzbdp(zzbej com_google_android_gms_internal_zzbej, zzq com_google_android_gms_common_internal_zzq, Map<Api<?>, Boolean> map, zze com_google_android_gms_common_zze, zza<? extends zzcuw, zzcux> com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux, Lock lock, Context context) {
        this.zzaDf = 0;
        this.zzaDh = new Bundle();
        this.zzaDi = new HashSet();
        this.zzaDq = new ArrayList();
        this.zzaDb = com_google_android_gms_internal_zzbej;
        this.zzaCC = com_google_android_gms_common_internal_zzq;
        this.zzaCF = map;
        this.zzaCH = com_google_android_gms_common_zze;
        this.zzaBg = com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux;
        this.zzaCx = lock;
        this.mContext = context;
    }

    private final void zza(zzcvj com_google_android_gms_internal_zzcvj) {
        if (zzan(0)) {
            ConnectionResult zzpx = com_google_android_gms_internal_zzcvj.zzpx();
            if (zzpx.isSuccess()) {
                zzbu zzAv = com_google_android_gms_internal_zzcvj.zzAv();
                ConnectionResult zzpx2 = zzAv.zzpx();
                if (zzpx2.isSuccess()) {
                    this.zzaDm = true;
                    this.zzaDn = zzAv.zzrG();
                    this.zzaDo = zzAv.zzrH();
                    this.zzaDp = zzAv.zzrI();
                    zzpV();
                    return;
                }
                String valueOf = String.valueOf(zzpx2);
                Log.wtf("GoogleApiClientConnecting", new StringBuilder(String.valueOf(valueOf).length() + 48).append("Sign-in succeeded with resolve account failure: ").append(valueOf).toString(), new Exception());
                zze(zzpx2);
            } else if (zzd(zzpx)) {
                zzpX();
                zzpV();
            } else {
                zze(zzpx);
            }
        }
    }

    private final void zzad(boolean z) {
        if (this.zzaDj != null) {
            if (this.zzaDj.isConnected() && z) {
                this.zzaDj.zzAo();
            }
            this.zzaDj.disconnect();
            this.zzaDn = null;
        }
    }

    private final boolean zzan(int i) {
        if (this.zzaDf == i) {
            return true;
        }
        Log.w("GoogleApiClientConnecting", this.zzaDb.zzaCn.zzqe());
        String valueOf = String.valueOf(this);
        Log.w("GoogleApiClientConnecting", new StringBuilder(String.valueOf(valueOf).length() + 23).append("Unexpected callback in ").append(valueOf).toString());
        Log.w("GoogleApiClientConnecting", new StringBuilder(33).append("mRemainingConnections=").append(this.zzaDg).toString());
        valueOf = String.valueOf(zzao(this.zzaDf));
        String valueOf2 = String.valueOf(zzao(i));
        Log.wtf("GoogleApiClientConnecting", new StringBuilder((String.valueOf(valueOf).length() + 70) + String.valueOf(valueOf2).length()).append("GoogleApiClient connecting is in step ").append(valueOf).append(" but received callback for step ").append(valueOf2).toString(), new Exception());
        zze(new ConnectionResult(8, null));
        return false;
    }

    private static String zzao(int i) {
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return "STEP_GETTING_REMOTE_SERVICE";
            default:
                return "UNKNOWN";
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzb(com.google.android.gms.common.ConnectionResult r6, com.google.android.gms.common.api.Api<?> r7, boolean r8) {
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbdp.zzb(com.google.android.gms.common.ConnectionResult, com.google.android.gms.common.api.Api, boolean):void");
        /*
        this = this;
        r1 = 0;
        r0 = 1;
        r2 = r7.zzoZ();
        r3 = r2.getPriority();
        if (r8 == 0) goto L_0x0015;
    L_0x000c:
        r2 = r6.hasResolution();
        if (r2 == 0) goto L_0x002f;
    L_0x0012:
        r2 = r0;
    L_0x0013:
        if (r2 == 0) goto L_0x003f;
    L_0x0015:
        r2 = r5.zzaCQ;
        if (r2 == 0) goto L_0x001d;
    L_0x0019:
        r2 = r5.zzaDe;
        if (r3 >= r2) goto L_0x003f;
    L_0x001d:
        if (r0 == 0) goto L_0x0023;
    L_0x001f:
        r5.zzaCQ = r6;
        r5.zzaDe = r3;
    L_0x0023:
        r0 = r5.zzaDb;
        r0 = r0.zzaDW;
        r1 = r7.zzpb();
        r0.put(r1, r6);
        return;
    L_0x002f:
        r2 = r5.zzaCH;
        r4 = r6.getErrorCode();
        r2 = r2.zzak(r4);
        if (r2 == 0) goto L_0x003d;
    L_0x003b:
        r2 = r0;
        goto L_0x0013;
    L_0x003d:
        r2 = r1;
        goto L_0x0013;
    L_0x003f:
        r0 = r1;
        goto L_0x001d;
        */
    }

    private final boolean zzd(ConnectionResult connectionResult) {
        return this.zzaDk && !connectionResult.hasResolution();
    }

    private final void zze(ConnectionResult connectionResult) {
        zzpY();
        zzad(!connectionResult.hasResolution());
        this.zzaDb.zzg(connectionResult);
        this.zzaDb.zzaEa.zzc(connectionResult);
    }

    private final boolean zzpU() {
        this.zzaDg--;
        if (this.zzaDg > 0) {
            return false;
        }
        if (this.zzaDg < 0) {
            Log.w("GoogleApiClientConnecting", this.zzaDb.zzaCn.zzqe());
            Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            zze(new ConnectionResult(8, null));
            return false;
        } else if (this.zzaCQ == null) {
            return true;
        } else {
            this.zzaDb.zzaDZ = this.zzaDe;
            zze(this.zzaCQ);
            return false;
        }
    }

    private final void zzpV() {
        if (this.zzaDg == 0) {
            if (!this.zzaDl || this.zzaDm) {
                ArrayList arrayList = new ArrayList();
                this.zzaDf = 1;
                this.zzaDg = this.zzaDb.zzaDH.size();
                for (zzc com_google_android_gms_common_api_Api_zzc : this.zzaDb.zzaDH.keySet()) {
                    if (!this.zzaDb.zzaDW.containsKey(com_google_android_gms_common_api_Api_zzc)) {
                        arrayList.add((Api.zze) this.zzaDb.zzaDH.get(com_google_android_gms_common_api_Api_zzc));
                    } else if (zzpU()) {
                        zzpW();
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.zzaDq.add(zzbem.zzqh().submit(new zzbdv(this, arrayList)));
                }
            }
        }
    }

    private final void zzpW() {
        this.zzaDb.zzqg();
        zzbem.zzqh().execute(new zzbdq(this));
        if (this.zzaDj != null) {
            if (this.zzaDo) {
                this.zzaDj.zza(this.zzaDn, this.zzaDp);
            }
            zzad(false);
        }
        for (zzc com_google_android_gms_common_api_Api_zzc : this.zzaDb.zzaDW.keySet()) {
            ((Api.zze) this.zzaDb.zzaDH.get(com_google_android_gms_common_api_Api_zzc)).disconnect();
        }
        this.zzaDb.zzaEa.zzm(this.zzaDh.isEmpty() ? null : this.zzaDh);
    }

    private final void zzpX() {
        this.zzaDl = false;
        this.zzaDb.zzaCn.zzaDI = Collections.emptySet();
        for (zzc com_google_android_gms_common_api_Api_zzc : this.zzaDi) {
            if (!this.zzaDb.zzaDW.containsKey(com_google_android_gms_common_api_Api_zzc)) {
                this.zzaDb.zzaDW.put(com_google_android_gms_common_api_Api_zzc, new ConnectionResult(17, null));
            }
        }
    }

    private final void zzpY() {
        ArrayList arrayList = this.zzaDq;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Future) obj).cancel(true);
        }
        this.zzaDq.clear();
    }

    private final Set<Scope> zzpZ() {
        if (this.zzaCC == null) {
            return Collections.emptySet();
        }
        Set<Scope> hashSet = new HashSet(this.zzaCC.zzrl());
        Map zzrn = this.zzaCC.zzrn();
        for (Api api : zzrn.keySet()) {
            if (!this.zzaDb.zzaDW.containsKey(api.zzpb())) {
                hashSet.addAll(((zzr) zzrn.get(api)).zzamg);
            }
        }
        return hashSet;
    }

    public final void begin() {
        this.zzaDb.zzaDW.clear();
        this.zzaDl = false;
        this.zzaCQ = null;
        this.zzaDf = 0;
        this.zzaDk = true;
        this.zzaDm = false;
        this.zzaDo = false;
        Map hashMap = new HashMap();
        int i = 0;
        for (Api api : this.zzaCF.keySet()) {
            Api.zze com_google_android_gms_common_api_Api_zze = (Api.zze) this.zzaDb.zzaDH.get(api.zzpb());
            int i2 = (api.zzoZ().getPriority() == 1 ? 1 : 0) | i;
            boolean booleanValue = ((Boolean) this.zzaCF.get(api)).booleanValue();
            if (com_google_android_gms_common_api_Api_zze.zzmt()) {
                this.zzaDl = true;
                if (booleanValue) {
                    this.zzaDi.add(api.zzpb());
                } else {
                    this.zzaDk = false;
                }
            }
            hashMap.put(com_google_android_gms_common_api_Api_zze, new zzbdr(this, api, booleanValue));
            i = i2;
        }
        if (i != 0) {
            this.zzaDl = false;
        }
        if (this.zzaDl) {
            this.zzaCC.zzc(Integer.valueOf(System.identityHashCode(this.zzaDb.zzaCn)));
            ConnectionCallbacks com_google_android_gms_internal_zzbdy = new zzbdy();
            this.zzaDj = (zzcuw) this.zzaBg.zza(this.mContext, this.zzaDb.zzaCn.getLooper(), this.zzaCC, this.zzaCC.zzrr(), com_google_android_gms_internal_zzbdy, com_google_android_gms_internal_zzbdy);
        }
        this.zzaDg = this.zzaDb.zzaDH.size();
        this.zzaDq.add(zzbem.zzqh().submit(new zzbds(this, hashMap)));
    }

    public final void connect() {
    }

    public final boolean disconnect() {
        zzpY();
        zzad(true);
        this.zzaDb.zzg(null);
        return true;
    }

    public final void onConnected(Bundle bundle) {
        if (zzan(1)) {
            if (bundle != null) {
                this.zzaDh.putAll(bundle);
            }
            if (zzpU()) {
                zzpW();
            }
        }
    }

    public final void onConnectionSuspended(int i) {
        zze(new ConnectionResult(8, null));
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (zzan(1)) {
            zzb(connectionResult, api, z);
            if (zzpU()) {
                zzpW();
            }
        }
    }

    public final <A extends zzb, R extends Result, T extends zzbck<R, A>> T zzd(T t) {
        this.zzaDb.zzaCn.zzaCL.add(t);
        return t;
    }

    public final <A extends zzb, T extends zzbck<? extends Result, A>> T zze(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}
