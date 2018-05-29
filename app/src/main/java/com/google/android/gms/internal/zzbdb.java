package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zze;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class zzbdb implements zzbfb {
    private final zzben zzaAP;
    private final zzq zzaCC;
    private final Map<zzc<?>, zzbda<?>> zzaCD;
    private final Map<zzc<?>, zzbda<?>> zzaCE;
    private final Map<Api<?>, Boolean> zzaCF;
    private final zzbeb zzaCG;
    private final zze zzaCH;
    private final Condition zzaCI;
    private final boolean zzaCJ;
    private final boolean zzaCK;
    private final Queue<zzbck<?, ?>> zzaCL;
    private boolean zzaCM;
    private Map<zzbcf<?>, ConnectionResult> zzaCN;
    private Map<zzbcf<?>, ConnectionResult> zzaCO;
    private zzbde zzaCP;
    private ConnectionResult zzaCQ;
    private final Lock zzaCx;
    private final Looper zzrP;

    public zzbdb(Context context, Lock lock, Looper looper, zze com_google_android_gms_common_zze, Map<zzc<?>, Api.zze> map, zzq com_google_android_gms_common_internal_zzq, Map<Api<?>, Boolean> map2, zza<? extends zzcuw, zzcux> com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux, ArrayList<zzbcu> arrayList, zzbeb com_google_android_gms_internal_zzbeb, boolean z) {
        Object obj;
        this.zzaCD = new HashMap();
        this.zzaCE = new HashMap();
        this.zzaCL = new LinkedList();
        this.zzaCx = lock;
        this.zzrP = looper;
        this.zzaCI = lock.newCondition();
        this.zzaCH = com_google_android_gms_common_zze;
        this.zzaCG = com_google_android_gms_internal_zzbeb;
        this.zzaCF = map2;
        this.zzaCC = com_google_android_gms_common_internal_zzq;
        this.zzaCJ = z;
        Map hashMap = new HashMap();
        for (Api api : map2.keySet()) {
            hashMap.put(api.zzpb(), api);
        }
        Map hashMap2 = new HashMap();
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList2.get(i);
            i++;
            zzbcu com_google_android_gms_internal_zzbcu = (zzbcu) obj2;
            hashMap2.put(com_google_android_gms_internal_zzbcu.zzayY, com_google_android_gms_internal_zzbcu);
        }
        int i2 = 1;
        Object obj3 = null;
        Object obj4 = null;
        for (Entry entry : map.entrySet()) {
            Object obj5;
            Object obj6;
            Object obj7;
            Api api2 = (Api) hashMap.get(entry.getKey());
            Api.zze com_google_android_gms_common_api_Api_zze = (Api.zze) entry.getValue();
            if (com_google_android_gms_common_api_Api_zze.zzpc()) {
                obj5 = 1;
                if (((Boolean) this.zzaCF.get(api2)).booleanValue()) {
                    obj6 = obj;
                    obj7 = obj3;
                } else {
                    obj6 = obj;
                    int i3 = 1;
                }
            } else {
                obj5 = obj4;
                obj6 = null;
                obj7 = obj3;
            }
            zzbda com_google_android_gms_internal_zzbda = new zzbda(context, api2, looper, com_google_android_gms_common_api_Api_zze, (zzbcu) hashMap2.get(api2), com_google_android_gms_common_internal_zzq, com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux);
            this.zzaCD.put((zzc) entry.getKey(), com_google_android_gms_internal_zzbda);
            if (com_google_android_gms_common_api_Api_zze.zzmt()) {
                this.zzaCE.put((zzc) entry.getKey(), com_google_android_gms_internal_zzbda);
            }
            obj4 = obj5;
            obj = obj6;
            obj3 = obj7;
        }
        boolean z2 = obj4 != null && obj == null && obj3 == null;
        this.zzaCK = z2;
        this.zzaAP = zzben.zzqi();
    }

    private final boolean zza(zzbda<?> com_google_android_gms_internal_zzbda_, ConnectionResult connectionResult) {
        return !connectionResult.isSuccess() && !connectionResult.hasResolution() && ((Boolean) this.zzaCF.get(com_google_android_gms_internal_zzbda_.zzpe())).booleanValue() && com_google_android_gms_internal_zzbda_.zzpH().zzpc() && this.zzaCH.isUserResolvableError(connectionResult.getErrorCode());
    }

    @Nullable
    private final ConnectionResult zzb(@NonNull zzc<?> com_google_android_gms_common_api_Api_zzc_) {
        this.zzaCx.lock();
        zzbda com_google_android_gms_internal_zzbda = (zzbda) this.zzaCD.get(com_google_android_gms_common_api_Api_zzc_);
        if (this.zzaCN == null || com_google_android_gms_internal_zzbda == null) {
            this.zzaCx.unlock();
            return null;
        }
        ConnectionResult connectionResult = (ConnectionResult) this.zzaCN.get(com_google_android_gms_internal_zzbda.zzpf());
        this.zzaCx.unlock();
        return connectionResult;
    }

    private final <T extends zzbck<? extends Result, ? extends zzb>> boolean zzg(@NonNull T t) {
        zzc zzpb = t.zzpb();
        ConnectionResult zzb = zzb(zzpb);
        if (zzb == null || zzb.getErrorCode() != 4) {
            return false;
        }
        t.zzr(new Status(4, null, this.zzaAP.zza(((zzbda) this.zzaCD.get(zzpb)).zzpf(), System.identityHashCode(this.zzaCG))));
        return true;
    }

    private final boolean zzpI() {
        this.zzaCx.lock();
        if (this.zzaCM && this.zzaCJ) {
            for (zzc com_google_android_gms_common_api_Api_zzc : this.zzaCE.keySet()) {
                ConnectionResult zzb = zzb(com_google_android_gms_common_api_Api_zzc);
                if (zzb != null) {
                    if (!zzb.isSuccess()) {
                    }
                }
                this.zzaCx.unlock();
                return false;
            }
            this.zzaCx.unlock();
            return true;
        }
        this.zzaCx.unlock();
        return false;
    }

    private final void zzpJ() {
        if (this.zzaCC == null) {
            this.zzaCG.zzaDI = Collections.emptySet();
            return;
        }
        Set hashSet = new HashSet(this.zzaCC.zzrl());
        Map zzrn = this.zzaCC.zzrn();
        for (Api api : zzrn.keySet()) {
            ConnectionResult connectionResult = getConnectionResult(api);
            if (connectionResult != null && connectionResult.isSuccess()) {
                hashSet.addAll(((zzr) zzrn.get(api)).zzamg);
            }
        }
        this.zzaCG.zzaDI = hashSet;
    }

    private final void zzpK() {
        while (!this.zzaCL.isEmpty()) {
            zze((zzbck) this.zzaCL.remove());
        }
        this.zzaCG.zzm(null);
    }

    @Nullable
    private final ConnectionResult zzpL() {
        int i;
        ConnectionResult connectionResult;
        int i2;
        Object obj = null;
        ConnectionResult connectionResult2 = null;
        Object obj2 = null;
        Object obj3 = null;
        for (zzbda com_google_android_gms_internal_zzbda : this.zzaCD.values()) {
            Api zzpe = com_google_android_gms_internal_zzbda.zzpe();
            ConnectionResult connectionResult3 = (ConnectionResult) this.zzaCN.get(com_google_android_gms_internal_zzbda.zzpf());
            if (!connectionResult3.isSuccess()) {
                if (!((Boolean) this.zzaCF.get(zzpe)).booleanValue() || connectionResult3.hasResolution() || this.zzaCH.isUserResolvableError(connectionResult3.getErrorCode())) {
                    int priority;
                    if (connectionResult3.getErrorCode() == 4 && this.zzaCJ) {
                        priority = zzpe.zzoZ().getPriority();
                        if (connectionResult2 == null || i > priority) {
                            i = priority;
                            connectionResult2 = connectionResult3;
                        }
                    } else {
                        ConnectionResult connectionResult4;
                        int i3;
                        priority = zzpe.zzoZ().getPriority();
                        if (connectionResult == null || i2 > priority) {
                            int i4 = priority;
                            connectionResult4 = connectionResult3;
                            i3 = i4;
                        } else {
                            i3 = i2;
                            connectionResult4 = connectionResult;
                        }
                        i2 = i3;
                        connectionResult = connectionResult4;
                    }
                }
            }
        }
        return (connectionResult == null || connectionResult2 == null || i2 <= i) ? connectionResult : connectionResult2;
    }

    public final ConnectionResult blockingConnect() {
        connect();
        while (isConnecting()) {
            try {
                this.zzaCI.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        return isConnected() ? ConnectionResult.zzazZ : this.zzaCQ != null ? this.zzaCQ : new ConnectionResult(13, null);
    }

    public final ConnectionResult blockingConnect(long j, TimeUnit timeUnit) {
        connect();
        long toNanos = timeUnit.toNanos(j);
        while (isConnecting()) {
            try {
                if (toNanos <= 0) {
                    disconnect();
                    return new ConnectionResult(14, null);
                }
                toNanos = this.zzaCI.awaitNanos(toNanos);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        return isConnected() ? ConnectionResult.zzazZ : this.zzaCQ != null ? this.zzaCQ : new ConnectionResult(13, null);
    }

    public final void connect() {
        this.zzaCx.lock();
        if (this.zzaCM) {
            this.zzaCx.unlock();
            return;
        }
        this.zzaCM = true;
        this.zzaCN = null;
        this.zzaCO = null;
        this.zzaCP = null;
        this.zzaCQ = null;
        this.zzaAP.zzpq();
        this.zzaAP.zza(this.zzaCD.values()).addOnCompleteListener(new zzbih(this.zzrP), new zzbdd());
        this.zzaCx.unlock();
    }

    public final void disconnect() {
        this.zzaCx.lock();
        this.zzaCM = false;
        this.zzaCN = null;
        this.zzaCO = null;
        if (this.zzaCP != null) {
            this.zzaCP.cancel();
            this.zzaCP = null;
        }
        this.zzaCQ = null;
        while (!this.zzaCL.isEmpty()) {
            zzbck com_google_android_gms_internal_zzbck = (zzbck) this.zzaCL.remove();
            com_google_android_gms_internal_zzbck.zza(null);
            com_google_android_gms_internal_zzbck.cancel();
        }
        this.zzaCI.signalAll();
        this.zzaCx.unlock();
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        return zzb(api.zzpb());
    }

    public final boolean isConnected() {
        this.zzaCx.lock();
        boolean z = this.zzaCN != null && this.zzaCQ == null;
        this.zzaCx.unlock();
        return z;
    }

    public final boolean isConnecting() {
        this.zzaCx.lock();
        boolean z = this.zzaCN == null && this.zzaCM;
        this.zzaCx.unlock();
        return z;
    }

    public final boolean zza(zzbfu com_google_android_gms_internal_zzbfu) {
        this.zzaCx.lock();
        if (!this.zzaCM || zzpI()) {
            this.zzaCx.unlock();
            return false;
        }
        this.zzaAP.zzpq();
        this.zzaCP = new zzbde(this, com_google_android_gms_internal_zzbfu);
        this.zzaAP.zza(this.zzaCE.values()).addOnCompleteListener(new zzbih(this.zzrP), this.zzaCP);
        this.zzaCx.unlock();
        return true;
    }

    public final <A extends zzb, R extends Result, T extends zzbck<R, A>> T zzd(@NonNull T t) {
        if (this.zzaCJ && zzg((zzbck) t)) {
            return t;
        }
        if (isConnected()) {
            this.zzaCG.zzaDN.zzb(t);
            return ((zzbda) this.zzaCD.get(t.zzpb())).zza(t);
        }
        this.zzaCL.add(t);
        return t;
    }

    public final <A extends zzb, T extends zzbck<? extends Result, A>> T zze(@NonNull T t) {
        zzc zzpb = t.zzpb();
        if (this.zzaCJ && zzg((zzbck) t)) {
            return t;
        }
        this.zzaCG.zzaDN.zzb(t);
        return ((zzbda) this.zzaCD.get(zzpb)).zzb(t);
    }

    public final void zzpC() {
    }

    public final void zzpj() {
        this.zzaCx.lock();
        this.zzaAP.zzpj();
        if (this.zzaCP != null) {
            this.zzaCP.cancel();
            this.zzaCP = null;
        }
        if (this.zzaCO == null) {
            this.zzaCO = new ArrayMap(this.zzaCE.size());
        }
        ConnectionResult connectionResult = new ConnectionResult(4);
        for (zzbda com_google_android_gms_internal_zzbda : this.zzaCE.values()) {
            this.zzaCO.put(com_google_android_gms_internal_zzbda.zzpf(), connectionResult);
        }
        if (this.zzaCN != null) {
            this.zzaCN.putAll(this.zzaCO);
        }
        this.zzaCx.unlock();
    }
}
