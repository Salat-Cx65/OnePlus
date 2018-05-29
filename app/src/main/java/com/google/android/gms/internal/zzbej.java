package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.zze;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzbej implements zzbcv, zzbfb {
    private final Context mContext;
    private zza<? extends zzcuw, zzcux> zzaBg;
    private zzq zzaCC;
    private Map<Api<?>, Boolean> zzaCF;
    private final zze zzaCH;
    final zzbeb zzaCn;
    private final Lock zzaCx;
    final Map<zzc<?>, Api.zze> zzaDH;
    private final Condition zzaDU;
    private final zzbel zzaDV;
    final Map<zzc<?>, ConnectionResult> zzaDW;
    private volatile zzbei zzaDX;
    private ConnectionResult zzaDY;
    int zzaDZ;
    final zzbfc zzaEa;

    public zzbej(Context context, zzbeb com_google_android_gms_internal_zzbeb, Lock lock, Looper looper, zze com_google_android_gms_common_zze, Map<zzc<?>, Api.zze> map, zzq com_google_android_gms_common_internal_zzq, Map<Api<?>, Boolean> map2, zza<? extends zzcuw, zzcux> com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux, ArrayList<zzbcu> arrayList, zzbfc com_google_android_gms_internal_zzbfc) {
        this.zzaDW = new HashMap();
        this.zzaDY = null;
        this.mContext = context;
        this.zzaCx = lock;
        this.zzaCH = com_google_android_gms_common_zze;
        this.zzaDH = map;
        this.zzaCC = com_google_android_gms_common_internal_zzq;
        this.zzaCF = map2;
        this.zzaBg = com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux;
        this.zzaCn = com_google_android_gms_internal_zzbeb;
        this.zzaEa = com_google_android_gms_internal_zzbfc;
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            ((zzbcu) obj).zza(this);
        }
        this.zzaDV = new zzbel(this, looper);
        this.zzaDU = lock.newCondition();
        this.zzaDX = new zzbea(this);
    }

    public final ConnectionResult blockingConnect() {
        connect();
        while (isConnecting()) {
            try {
                this.zzaDU.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        return isConnected() ? ConnectionResult.zzazZ : this.zzaDY != null ? this.zzaDY : new ConnectionResult(13, null);
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
                toNanos = this.zzaDU.awaitNanos(toNanos);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        return isConnected() ? ConnectionResult.zzazZ : this.zzaDY != null ? this.zzaDY : new ConnectionResult(13, null);
    }

    public final void connect() {
        this.zzaDX.connect();
    }

    public final void disconnect() {
        if (this.zzaDX.disconnect()) {
            this.zzaDW.clear();
        }
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String concat = String.valueOf(str).concat("  ");
        printWriter.append(str).append("mState=").println(this.zzaDX);
        for (Api api : this.zzaCF.keySet()) {
            printWriter.append(str).append(api.getName()).println(":");
            ((Api.zze) this.zzaDH.get(api.zzpb())).dump(concat, fileDescriptor, printWriter, strArr);
        }
    }

    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        zzc zzpb = api.zzpb();
        if (this.zzaDH.containsKey(zzpb)) {
            if (((Api.zze) this.zzaDH.get(zzpb)).isConnected()) {
                return ConnectionResult.zzazZ;
            }
            if (this.zzaDW.containsKey(zzpb)) {
                return (ConnectionResult) this.zzaDW.get(zzpb);
            }
        }
        return null;
    }

    public final boolean isConnected() {
        return this.zzaDX instanceof zzbdm;
    }

    public final boolean isConnecting() {
        return this.zzaDX instanceof zzbdp;
    }

    public final void onConnected(@Nullable Bundle bundle) {
        this.zzaCx.lock();
        this.zzaDX.onConnected(bundle);
        this.zzaCx.unlock();
    }

    public final void onConnectionSuspended(int i) {
        this.zzaCx.lock();
        this.zzaDX.onConnectionSuspended(i);
        this.zzaCx.unlock();
    }

    public final void zza(@NonNull ConnectionResult connectionResult, @NonNull Api<?> api, boolean z) {
        this.zzaCx.lock();
        this.zzaDX.zza(connectionResult, api, z);
        this.zzaCx.unlock();
    }

    final void zza(zzbek com_google_android_gms_internal_zzbek) {
        this.zzaDV.sendMessage(this.zzaDV.obtainMessage(1, com_google_android_gms_internal_zzbek));
    }

    final void zza(RuntimeException runtimeException) {
        this.zzaDV.sendMessage(this.zzaDV.obtainMessage(RainSurfaceView.RAIN_LEVEL_SHOWER, runtimeException));
    }

    public final boolean zza(zzbfu com_google_android_gms_internal_zzbfu) {
        return false;
    }

    public final <A extends zzb, R extends Result, T extends zzbck<R, A>> T zzd(@NonNull T t) {
        t.zzpA();
        return this.zzaDX.zzd(t);
    }

    public final <A extends zzb, T extends zzbck<? extends Result, A>> T zze(@NonNull T t) {
        t.zzpA();
        return this.zzaDX.zze(t);
    }

    final void zzg(ConnectionResult connectionResult) {
        this.zzaCx.lock();
        this.zzaDY = connectionResult;
        this.zzaDX = new zzbea(this);
        this.zzaDX.begin();
        this.zzaDU.signalAll();
        this.zzaCx.unlock();
    }

    public final void zzpC() {
        if (isConnected()) {
            ((zzbdm) this.zzaDX).zzpS();
        }
    }

    public final void zzpj() {
    }

    final void zzqf() {
        this.zzaCx.lock();
        this.zzaDX = new zzbdp(this, this.zzaCC, this.zzaCF, this.zzaCH, this.zzaBg, this.zzaCx, this.mContext);
        this.zzaDX.begin();
        this.zzaDU.signalAll();
        this.zzaCx.unlock();
    }

    final void zzqg() {
        this.zzaCx.lock();
        this.zzaCn.zzqc();
        this.zzaDX = new zzbdm(this);
        this.zzaDX.begin();
        this.zzaDU.signalAll();
        this.zzaCx.unlock();
    }
}
