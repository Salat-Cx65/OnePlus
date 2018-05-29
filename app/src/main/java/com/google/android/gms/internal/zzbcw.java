package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzq;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

final class zzbcw implements zzbfb {
    private final Context mContext;
    private final zzbeb zzaCn;
    private final zzbej zzaCo;
    private final zzbej zzaCp;
    private final Map<zzc<?>, zzbej> zzaCq;
    private final Set<zzbfu> zzaCr;
    private final zze zzaCs;
    private Bundle zzaCt;
    private ConnectionResult zzaCu;
    private ConnectionResult zzaCv;
    private boolean zzaCw;
    private final Lock zzaCx;
    private int zzaCy;
    private final Looper zzrP;

    private zzbcw(Context context, zzbeb com_google_android_gms_internal_zzbeb, Lock lock, Looper looper, com.google.android.gms.common.zze com_google_android_gms_common_zze, Map<zzc<?>, zze> map, Map<zzc<?>, zze> map2, zzq com_google_android_gms_common_internal_zzq, zza<? extends zzcuw, zzcux> com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux, zze com_google_android_gms_common_api_Api_zze, ArrayList<zzbcu> arrayList, ArrayList<zzbcu> arrayList2, Map<Api<?>, Boolean> map3, Map<Api<?>, Boolean> map4) {
        this.zzaCr = Collections.newSetFromMap(new WeakHashMap());
        this.zzaCu = null;
        this.zzaCv = null;
        this.zzaCw = false;
        this.zzaCy = 0;
        this.mContext = context;
        this.zzaCn = com_google_android_gms_internal_zzbeb;
        this.zzaCx = lock;
        this.zzrP = looper;
        this.zzaCs = com_google_android_gms_common_api_Api_zze;
        this.zzaCo = new zzbej(context, this.zzaCn, lock, looper, com_google_android_gms_common_zze, map2, null, map4, null, arrayList2, new zzbcy());
        this.zzaCp = new zzbej(context, this.zzaCn, lock, looper, com_google_android_gms_common_zze, map, com_google_android_gms_common_internal_zzq, map3, com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux, arrayList, new zzbcz());
        Map arrayMap = new ArrayMap();
        for (zzc com_google_android_gms_common_api_Api_zzc : map2.keySet()) {
            arrayMap.put(com_google_android_gms_common_api_Api_zzc, this.zzaCo);
        }
        for (zzc com_google_android_gms_common_api_Api_zzc2 : map.keySet()) {
            arrayMap.put(com_google_android_gms_common_api_Api_zzc2, this.zzaCp);
        }
        this.zzaCq = Collections.unmodifiableMap(arrayMap);
    }

    public static zzbcw zza(Context context, zzbeb com_google_android_gms_internal_zzbeb, Lock lock, Looper looper, com.google.android.gms.common.zze com_google_android_gms_common_zze, Map<zzc<?>, zze> map, zzq com_google_android_gms_common_internal_zzq, Map<Api<?>, Boolean> map2, zza<? extends zzcuw, zzcux> com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux, ArrayList<zzbcu> arrayList) {
        zze com_google_android_gms_common_api_Api_zze = null;
        Map arrayMap = new ArrayMap();
        Map arrayMap2 = new ArrayMap();
        for (Entry entry : map.entrySet()) {
            zze com_google_android_gms_common_api_Api_zze2 = (zze) entry.getValue();
            if (com_google_android_gms_common_api_Api_zze2.zzmE()) {
                com_google_android_gms_common_api_Api_zze = com_google_android_gms_common_api_Api_zze2;
            }
            if (com_google_android_gms_common_api_Api_zze2.zzmt()) {
                arrayMap.put((zzc) entry.getKey(), com_google_android_gms_common_api_Api_zze2);
            } else {
                arrayMap2.put((zzc) entry.getKey(), com_google_android_gms_common_api_Api_zze2);
            }
        }
        zzbr.zza(!arrayMap.isEmpty(), (Object) "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        Map arrayMap3 = new ArrayMap();
        Map arrayMap4 = new ArrayMap();
        for (Api api : map2.keySet()) {
            zzc zzpb = api.zzpb();
            if (arrayMap.containsKey(zzpb)) {
                arrayMap3.put(api, (Boolean) map2.get(api));
            } else if (arrayMap2.containsKey(zzpb)) {
                arrayMap4.put(api, (Boolean) map2.get(api));
            } else {
                throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = arrayList;
        int size = arrayList4.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList4.get(i);
            i++;
            zzbcu com_google_android_gms_internal_zzbcu = (zzbcu) obj;
            if (arrayMap3.containsKey(com_google_android_gms_internal_zzbcu.zzayY)) {
                arrayList2.add(com_google_android_gms_internal_zzbcu);
            } else if (arrayMap4.containsKey(com_google_android_gms_internal_zzbcu.zzayY)) {
                arrayList3.add(com_google_android_gms_internal_zzbcu);
            } else {
                throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
            }
        }
        return new zzbcw(context, com_google_android_gms_internal_zzbeb, lock, looper, com_google_android_gms_common_zze, arrayMap, arrayMap2, com_google_android_gms_common_internal_zzq, com_google_android_gms_common_api_Api_zza__extends_com_google_android_gms_internal_zzcuw__com_google_android_gms_internal_zzcux, com_google_android_gms_common_api_Api_zze, arrayList2, arrayList3, arrayMap3, arrayMap4);
    }

    private final void zza(ConnectionResult connectionResult) {
        switch (this.zzaCy) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                zzpE();
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                this.zzaCn.zzc(connectionResult);
                zzpE();
                break;
            default:
                Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
                break;
        }
        this.zzaCy = 0;
    }

    private static boolean zzb(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    private final void zzd(int i, boolean z) {
        this.zzaCn.zze(i, z);
        this.zzaCv = null;
        this.zzaCu = null;
    }

    private final boolean zzf(zzbck<? extends Result, ? extends zzb> com_google_android_gms_internal_zzbck__extends_com_google_android_gms_common_api_Result___extends_com_google_android_gms_common_api_Api_zzb) {
        zzc zzpb = com_google_android_gms_internal_zzbck__extends_com_google_android_gms_common_api_Result___extends_com_google_android_gms_common_api_Api_zzb.zzpb();
        zzbr.zzb(this.zzaCq.containsKey(zzpb), (Object) "GoogleApiClient is not configured to use the API required for this call.");
        return ((zzbej) this.zzaCq.get(zzpb)).equals(this.zzaCp);
    }

    private final void zzl(Bundle bundle) {
        if (this.zzaCt == null) {
            this.zzaCt = bundle;
        } else if (bundle != null) {
            this.zzaCt.putAll(bundle);
        }
    }

    private final void zzpD() {
        if (zzb(this.zzaCu)) {
            if (zzb(this.zzaCv) || zzpF()) {
                switch (this.zzaCy) {
                    case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                        zzpE();
                        break;
                    case RainSurfaceView.RAIN_LEVEL_SHOWER:
                        this.zzaCn.zzm(this.zzaCt);
                        zzpE();
                        break;
                    default:
                        Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
                        break;
                }
                this.zzaCy = 0;
            } else if (this.zzaCv == null) {
            } else {
                if (this.zzaCy == 1) {
                    zzpE();
                    return;
                }
                zza(this.zzaCv);
                this.zzaCo.disconnect();
            }
        } else if (this.zzaCu != null && zzb(this.zzaCv)) {
            this.zzaCp.disconnect();
            zza(this.zzaCu);
        } else if (this.zzaCu != null && this.zzaCv != null) {
            ConnectionResult connectionResult = this.zzaCu;
            if (this.zzaCp.zzaDZ < this.zzaCo.zzaDZ) {
                connectionResult = this.zzaCv;
            }
            zza(connectionResult);
        }
    }

    private final void zzpE() {
        for (zzbfu com_google_android_gms_internal_zzbfu : this.zzaCr) {
            com_google_android_gms_internal_zzbfu.zzmD();
        }
        this.zzaCr.clear();
    }

    private final boolean zzpF() {
        return this.zzaCv != null && this.zzaCv.getErrorCode() == 4;
    }

    @Nullable
    private final PendingIntent zzpG() {
        return this.zzaCs == null ? null : PendingIntent.getActivity(this.mContext, System.identityHashCode(this.zzaCn), this.zzaCs.zzmF(), 134217728);
    }

    public final ConnectionResult blockingConnect() {
        throw new UnsupportedOperationException();
    }

    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    public final void connect() {
        this.zzaCy = 2;
        this.zzaCw = false;
        this.zzaCv = null;
        this.zzaCu = null;
        this.zzaCo.connect();
        this.zzaCp.connect();
    }

    public final void disconnect() {
        this.zzaCv = null;
        this.zzaCu = null;
        this.zzaCy = 0;
        this.zzaCo.disconnect();
        this.zzaCp.disconnect();
        zzpE();
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("authClient").println(":");
        this.zzaCp.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
        printWriter.append(str).append("anonClient").println(":");
        this.zzaCo.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
    }

    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        return ((zzbej) this.zzaCq.get(api.zzpb())).equals(this.zzaCp) ? zzpF() ? new ConnectionResult(4, zzpG()) : this.zzaCp.getConnectionResult(api) : this.zzaCo.getConnectionResult(api);
    }

    public final boolean isConnected() {
        boolean z = true;
        this.zzaCx.lock();
        if (!(this.zzaCo.isConnected() && (this.zzaCp.isConnected() || zzpF() || this.zzaCy == 1))) {
            z = false;
        }
        this.zzaCx.unlock();
        return z;
    }

    public final boolean isConnecting() {
        this.zzaCx.lock();
        boolean z = this.zzaCy == 2;
        this.zzaCx.unlock();
        return z;
    }

    public final boolean zza(zzbfu com_google_android_gms_internal_zzbfu) {
        this.zzaCx.lock();
        if ((isConnecting() || isConnected()) && !this.zzaCp.isConnected()) {
            this.zzaCr.add(com_google_android_gms_internal_zzbfu);
            if (this.zzaCy == 0) {
                this.zzaCy = 1;
            }
            this.zzaCv = null;
            this.zzaCp.connect();
            this.zzaCx.unlock();
            return true;
        }
        this.zzaCx.unlock();
        return false;
    }

    public final <A extends zzb, R extends Result, T extends zzbck<R, A>> T zzd(@NonNull T t) {
        if (!zzf((zzbck) t)) {
            return this.zzaCo.zzd(t);
        }
        if (!zzpF()) {
            return this.zzaCp.zzd(t);
        }
        t.zzr(new Status(4, null, zzpG()));
        return t;
    }

    public final <A extends zzb, T extends zzbck<? extends Result, A>> T zze(@NonNull T t) {
        if (!zzf((zzbck) t)) {
            return this.zzaCo.zze(t);
        }
        if (!zzpF()) {
            return this.zzaCp.zze(t);
        }
        t.zzr(new Status(4, null, zzpG()));
        return t;
    }

    public final void zzpC() {
        this.zzaCo.zzpC();
        this.zzaCp.zzpC();
    }

    public final void zzpj() {
        this.zzaCx.lock();
        boolean isConnecting = isConnecting();
        this.zzaCp.disconnect();
        this.zzaCv = new ConnectionResult(4);
        if (isConnecting) {
            new Handler(this.zzrP).post(new zzbcx(this));
        } else {
            zzpE();
        }
        this.zzaCx.unlock();
    }
}
